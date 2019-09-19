package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.controllers.restdata.DropdownData;
import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.filter.impl.AdminOfficeFilterImpl;
import gov.nwcg.isuite.core.persistence.AdminOfficeDao;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.rules.AdminOfficeDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.AdminOfficeSaveRulesHandler;
import gov.nwcg.isuite.core.service.AdminOfficeService2;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Calendar;
import java.util.Collection;

public class AdminOfficeService2Impl extends BaseService implements AdminOfficeService2 {
	private AdminOfficeDao dao;

	public AdminOfficeService2Impl(){

	}

	public void initialization(){
		dao = (AdminOfficeDao)super.context.getBean("adminOfficeDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService2#deleteAdminOffice(gov.nwcg.isuite.core.vo.AdminOfficeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAdminOffice(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown admin office.");

			AdminOfficeDeleteRulesHandler ruleHandler = new AdminOfficeDeleteRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){

				AdminOffice adminOffice = dao.getById(vo.getId(), AdminOfficeImpl.class);

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
					dao.save(adminOffice);				   

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
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService2#save(gov.nwcg.isuite.core.vo.AdminOfficeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   
			AdminOffice entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId());

			AdminOfficeSaveRulesHandler ruleHandler = new AdminOfficeSaveRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){

				entity = AdminOfficeVo.toEntity(vo, true);

				// try and persist the entity object		   	
				if(!LongUtility.hasValue(entity.getId())) 
					dao.save(entity);
				else 
					dao.merge(entity);

				// update the entity object from db
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(),AdminOfficeImpl.class);

				// build updated vo
				vo = AdminOfficeVo.getInstance(entity, true);

				// build the corresponding gridVo
				AdminOfficeGridVo gridVo = null;
			
				AdminOfficeFilter filter = new AdminOfficeFilterImpl();
				filter.setId(entity.getId());
				Collection<AdminOfficeGridVo> vos = dao.getGrid(filter);
				if(CollectionUtility.hasValue(vos))
					gridVo = vos.iterator().next();
				
				// build coa				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_ADMIN_OFFICE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.adminOffice", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
				
				// set the result object alternate with updated corresponding grid vo
				dialogueVo.setResultObjectAlternate(gridVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			AdminOffice entity = dao.getById(id, AdminOfficeImpl.class);

			AdminOfficeVo vo = null;
			if(null != entity)
				vo = AdminOfficeVo.getInstance(entity, true);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_BY_ID_ADMIN_OFFICE");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(vo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AdminOfficeService2#getGrid(gov.nwcg.isuite.core.filter.AdminOfficeFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(AdminOfficeFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<AdminOfficeGridVo> vos = dao.getGrid(filter);
			Collection<AdminOfficeVo> vos2 = dao.getPicklist();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_GRID_ADMIN_OFFICES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			dialogueVo.setResultObjectAlternate(vos2);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getDropdownList(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<AdminOfficeGridVo> vos = dao.getGrid(new AdminOfficeFilterImpl());
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_DD_LIST_ADMIN_OFFICES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(DropdownData.convertFromAdminOffices(vos));
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
		
	}

}
