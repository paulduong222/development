package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.rules.AdminOfficeDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.AdminOfficeSaveRulesHandler;
import gov.nwcg.isuite.core.service.AdminOfficeService;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * 
 * @author Geoff Dyer, Modified by Tamra O'Neil Dec 2010
 */
public class AdminOfficeServiceImpl extends BaseService implements AdminOfficeService {
   private AdminOfficeDao adminOfficeDao;
   
   public AdminOfficeServiceImpl(){
	   
   }
   
   public void initialization(){
	   adminOfficeDao = (AdminOfficeDao)super.context.getBean("adminOfficeDao");
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AdminOfficeService#deleteAdminOffice2(gov.nwcg.isuite.core.vo.AdminOfficeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
    */
   public DialogueVo deleteAdminOffice2(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException {
	   if(null == dialogueVo) dialogueVo = new DialogueVo();
	   
	   try{

		   if((null == vo) || !LongUtility.hasValue(vo.getId()))
				   throw new ServiceException("Unable to delete unknown admin office.");
		   
		   AdminOfficeDeleteRulesHandler ruleHandler = new AdminOfficeDeleteRulesHandler(context);
		   if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
			   
			   AdminOffice adminOffice = adminOfficeDao.getById(vo.getId(), AdminOfficeImpl.class);

			   if(null != adminOffice){
				   /*
				    * Delete any associated contractor agreements 
				    * 
				    * Use case: 	
				    * When the user deletes an Admin Office for Payment that was associated 
				    * with an agreement, the system must remove the Admin Office for Payment 
				    * from the agreement data.
				    */				   
				   Collection<ContractorAgreement> contractorAgreements = adminOffice.getContractorAgreements();
				   for(ContractorAgreement contractorAgreement : contractorAgreements){
					  
					   ContractorAgreementDao contractorAgreementDao = (ContractorAgreementDao)super.context.getBean("contractorAgreementDao");
					   contractorAgreement.setAdminOffice(null);
					   // or mark the contractorAgreement record as deleted if that is preferred when the associated admin office is marked deleted.
					   //contractorAgreement.setDeletedDate(Calendar.getInstance().getTime());
					   contractorAgreementDao.save(contractorAgreement);
				   }
				   
				   /*
				    * For resource entities, we only set the deleted_date field
				    * instead of physically deleting the entity record.
				    */
				   adminOffice.setDeletedDate(Calendar.getInstance().getTime());				   
				   adminOfficeDao.save(adminOffice);				   
				   
				   CourseOfActionVo coaVo = new CourseOfActionVo();
				   coaVo.setCoaName("DELETE_ADMIN_OFFICE");
				   coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				   coaVo.setMessageVo(new MessageVo("text.adminOffice", "info.0028" , new String[]{"Admin Office"}, MessageTypeEnum.INFO));
				   coaVo.setIsDialogueEnding(true);
				   dialogueVo.setResultObject(vo);
				   dialogueVo.setCourseOfActionVo(coaVo);
			   }
		   }
		   
	   }catch(Exception e){
		   super.dialogueException(dialogueVo, e);
	   }
	   
	   return dialogueVo;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AdminOfficeService#delete(gov.nwcg.isuite.core.vo.AdminOfficeVo)
    */
   public void deleteAdminOffice(AdminOfficeVo persistableVo) throws ServiceException {
	   try {
		   if( null != persistableVo.getId()){
			   AdminOffice adminOffice = adminOfficeDao.getById(persistableVo.getId(), AdminOfficeImpl.class);
			   if(null!=adminOffice){
				   
				   /*
				    * Delete any associated contractor agreements 
				    * 
				    * Use case: 	
				    * When the user deletes an Admin Office for Payment that was associated 
				    * with an agreement, the system must remove the Admin Office for Payment 
				    * from the agreement data.
				    */				   
				   Collection<ContractorAgreement> contractorAgreements = adminOffice.getContractorAgreements();
				   for(ContractorAgreement contractorAgreement : contractorAgreements){
					  
					   ContractorAgreementDao contractorAgreementDao = (ContractorAgreementDao)super.context.getBean("contractorAgreementDao");
					   contractorAgreement.setAdminOffice(null);
					   // or mark the contractorAgreement record as deleted if that is preferred when the associated admin office is marked deleted.
					   //contractorAgreement.setDeletedDate(Calendar.getInstance().getTime());
					   contractorAgreementDao.save(contractorAgreement);
				   }
				   
				   /*
				    * For resource entities, we only set the deleted_date field
				    * instead of physically deleting the entity record.
				    */
				   adminOffice.setDeletedDate(Calendar.getInstance().getTime());				   
				   adminOfficeDao.save(adminOffice);				   
			   }
		   }
	   } catch (PersistenceException pe) {
		   throw new ServiceException(pe);
	   } catch (Exception e){
		   throw new ServiceException(e);
	   }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AdminOfficeService#saveAdminOffice(gov.nwcg.isuite.core.vo.AdminOfficeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
    */
   public DialogueVo saveAdminOffice(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException {
	   if(null == dialogueVo) dialogueVo = new DialogueVo();
	   
	   try{		   
		   boolean newEntity=true; // determine if new or edited entity
   
		   AdminOffice adminOfficeEntity = null;
			if(LongUtility.hasValue(vo.getId())){
				adminOfficeEntity = adminOfficeDao.getById(vo.getId());
				 newEntity = false;
			}
		
		   AdminOfficeSaveRulesHandler ruleHandler = new AdminOfficeSaveRulesHandler(context);
		   if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
			  
			   
			   adminOfficeEntity = AdminOfficeVo.toEntity(vo, true);
			   
			   // try and persist the entity object		   	
			   if (newEntity) {
				   adminOfficeDao.save(adminOfficeEntity);
			   } else {
				   adminOfficeDao.merge(adminOfficeEntity);
			   }
			   		  
			   //update the reference vo with the generated id
			   vo.setId(adminOfficeEntity.getId());	
			   
			   adminOfficeEntity = adminOfficeDao.getById(adminOfficeEntity.getId(), AdminOfficeImpl.class);
			   vo = AdminOfficeVo.getInstance(adminOfficeEntity, true);
		

				// build coa				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.adminOffice", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(vo);
		   }
		   
	   }catch(Exception e){
		   super.dialogueException(dialogueVo, e);
	   }
	   
	   return dialogueVo;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AdminOfficeService#save(gov.nwcg.isuite.core.vo.AdminOfficeVo)
    */
   public AdminOfficeVo save(AdminOfficeVo persistableVo) throws ServiceException,ValidationException {
	   try
	   {   
		   boolean newEntity=true; // determine if new or edited entity
		   
		   // determine if new or edited entity
		   if( (null != persistableVo.getId()) && (persistableVo.getId() > 0) ) {
			   newEntity = false;
		   }
		   		   
		   // create an entity from the vo object
		   AdminOffice adminOffice = AdminOfficeVo.toEntity(persistableVo,true);
		   	       
		   if(newEntity) {
			   /* 
			    * For new admin office, check if officename is unique
			    */			   
			   if(null != adminOfficeDao.getByAdminOfficeName(persistableVo.getOfficeName().trim().toUpperCase())) {
				   super.handleException(ErrorEnum.DUPLICATE_ADMIN_OFFICE_NAME, persistableVo.getOfficeName());
			   }
		   }
		   else {

			   /* 
			    * For edited admin office name, check if officename is unique
			    */
			   AdminOffice checkAdminOffice = adminOfficeDao.getById(persistableVo.getId(), AdminOfficeImpl.class);
			   if(!persistableVo.getOfficeName().trim().toUpperCase().equals(checkAdminOffice.getOfficeName().trim().toUpperCase())){
				   if(null != adminOfficeDao.getByAdminOfficeName(persistableVo.getOfficeName().trim().toUpperCase())) {					   
					   super.handleException(ErrorEnum.DUPLICATE_ADMIN_OFFICE_NAME, persistableVo.getOfficeName());						   
				   }
			   }
		   }

		   // try and persist the entity object		   	
		   if (newEntity) {
			   adminOfficeDao.save(adminOffice);
		   } else {
			   adminOfficeDao.merge(adminOffice);
		   }
		   		  
		   //update the reference vo with the generated id
		   persistableVo.setId(adminOffice.getId());	
		   
		   adminOffice = adminOfficeDao.getById(adminOffice.getId(), AdminOfficeImpl.class);
		   return AdminOfficeVo.getInstance(adminOffice, true);

		   
	   } catch ( Exception e ) {
			super.handleException(e);
		}
	   return null;
   }
   
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<AdminOfficeVo> persistableList) throws ServiceException, ValidationException{
		Collection<AdminOffice> adminOfficeList = new ArrayList<AdminOffice>();
		
		try {
			for(AdminOfficeVo persistableVo : persistableList)
			{
			   // create an entity from the vo object
				AdminOffice adminOffice = AdminOfficeVo.toEntity(persistableVo,true);
				// add entity to the list
				adminOfficeList.add(adminOffice);
			}

			// try and persist the entity collection
			adminOfficeDao.saveAll(adminOfficeList); 

		} catch (PersistenceException pe) {
			throw new ServiceException(pe);
		} catch (ValidationException ve){
			throw ve;
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService#getById(java.lang.Long)
	 */
	public AdminOfficeVo getById(Long id) throws ServiceException {
		try
		{
  		   AdminOffice adminOffice = adminOfficeDao.getById(id, AdminOfficeImpl.class);

		   return AdminOfficeVo.getInstance(adminOffice,true);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService#getGrid(gov.nwcg.isuite.core.filter.impl.AdminOfficeFilter)
	 */
	public Collection<AdminOfficeGridVo> getGrid(AdminOfficeFilter filter) throws ServiceException {
		
		Collection<AdminOfficeGridVo> list = null;
		
		try{
			
			list = adminOfficeDao.getGrid(filter);
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return list;
	}
   
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService#getPicklist()
	 */
	public Collection<AdminOfficeVo> getPicklist() throws ServiceException {
		Collection<AdminOfficeVo> vos = new ArrayList<AdminOfficeVo>();
		
		try {
			vos = adminOfficeDao.getPicklist();
		} catch (Exception e){
			super.handleException(e);
		}
		
		return vos;
	}
   
}
