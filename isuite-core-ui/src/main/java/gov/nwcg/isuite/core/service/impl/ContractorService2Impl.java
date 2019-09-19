package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.impl.ContractorFilterImpl;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.ContractorAgreementDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.ContractorDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.ContractorSaveAgreementRulesHandler;
import gov.nwcg.isuite.core.rules.ContractorSaveRulesHandler;
import gov.nwcg.isuite.core.service.ContractorService2;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class ContractorService2Impl extends BaseService implements ContractorService2 {
	private ContractorDao contractorDao;

	public ContractorService2Impl(){
		
	}
	
	/**
	 * 
	 */
	public void initialization(){
		contractorDao = (ContractorDao)super.context.getBean("contractorDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#addContractorsToIncident(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addContractorsToIncident(Collection<ContractorGridVo> contractors, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#deleteAgreement(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAgreement(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			ContractorAgreementDeleteRulesHandler rulesHandler = new ContractorAgreementDeleteRulesHandler(context);

			ContractorAgreementDao dao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");
			ContractorAgreement contractorAgreement = dao.getById(id, ContractorAgreementImpl.class);
			
			if(rulesHandler.execute(contractorAgreement, dialogueVo) == ContractorAgreementDeleteRulesHandler._OK) {

				/*
				 * We only set the deleted_date field
				 * instead of physically deleting the entity record.
				 */
				contractorAgreement.setDeletedDate(Calendar.getInstance().getTime());
				dao.save(contractorAgreement);
				
				Collection<Long> agreementIds = new ArrayList<Long>();
				agreementIds.add(contractorAgreement.getId());
				contractorDao.removeContrAgreementFromContrPaymentInfo(agreementIds);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_CONTRACTOR_AGREEMENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.contractor","info.generic", new String[]{"The Contractor Agreement" + contractorAgreement.getAgreementNumber() +" was deleted."} , MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(id);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#deleteContractor(gov.nwcg.isuite.core.vo.ContractorVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteContractor(ContractorVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			ContractorDeleteRulesHandler rulesHandler = new ContractorDeleteRulesHandler(context);
			
			Contractor entity = contractorDao.getById(vo.getId(),ContractorImpl.class);
			
			if(rulesHandler.execute(entity, dialogueVo) == ContractorDeleteRulesHandler._OK) {
				entity.setDeletedDate(Calendar.getInstance().getTime());

				Collection<Long> agreementIds = new ArrayList<Long>();
				
				for (ContractorAgreement ca : entity.getContractorAgreements()) {
					ca.setDeletedDate(Calendar.getInstance().getTime());
					agreementIds.add(ca.getId());
				}
				
				contractorDao.save(entity);
				
				//Remove Contractor from Contractor Payment Info
				contractorDao.removeContrFromContrPaymentInfo(entity.getId());
				
				//Remove Contractor Agreement from Contractor Payment Info
				if (CollectionUtility.hasValue(agreementIds)) {
					contractorDao.removeContrAgreementFromContrPaymentInfo(agreementIds);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_CONTRACTOR");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.contractor","info.generic", new String[]{"The Contractor " + vo.getName() +" was deleted."} , MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.ContractorService2#disableContractors(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo disableContractors(Collection<ContractorGridVo> contractors, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#enableContractors(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo enableContractors( Collection<ContractorGridVo> contractors, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
			Contractor entity = contractorDao.getById(id, ContractorImpl.class);
			
			ContractorVo vo = ContractorVo.getInstance(entity, true);

			// remove any deleted agrements
			Collection<ContractorAgreementVo> vos = ContractorVo.removeDeletedAgreements(vo.getContractorAgreementVos());
			vo.setContractorAgreementVos(vos);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_BY_ID_CONTRACTOR");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#getContractedResourcesWithOriginalInvoices(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getContractedResourcesWithOriginalInvoices(Long contractorId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#getContractors(gov.nwcg.isuite.core.filter.ContractorFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getContractors(ContractorFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Collection<ContractorVo> vos = new ArrayList<ContractorVo>();
			
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#getGrid(gov.nwcg.isuite.core.filter.ContractorFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(ContractorFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			// get group incident ids if needed
			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				Collection<Long> ids = new ArrayList<Long>();
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup igEntity = igDao.getById(filter.getIncidentGroupId(), IncidentGroupImpl.class);
				ids.addAll(igDao.getIncidentIdsInGroup(filter.getIncidentGroupId()));
				/*
				if((null != igEntity) && (CollectionUtility.hasValue(igEntity.getIncidents())) ){
					for (Incident inc : igEntity.getIncidents()){
						ids.add(inc.getId());
					}
				}
				*/
				if(CollectionUtility.hasValue(ids))
					filter.setIncidentIds(ids);
			}
			
			// get grid vos
			Collection<ContractorGridVo> gridVos = contractorDao.getGrid(filter);

			// get reg vos
			Collection<ContractorVo> vos = contractorDao.getAll(filter);
				
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_GRID_CONTRACTORS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(gridVos);
			dialogueVo.setResultObjectAlternate(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#save(gov.nwcg.isuite.core.vo.ContractorVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(ContractorVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Contractor entity = null;
			ContractorVo originalVo = null;
			
			// get entity if id available
			if(LongUtility.hasValue(vo.getId())) 
				entity = contractorDao.getById(vo.getId(), ContractorImpl.class);
			
			if(entity != null)
				originalVo = ContractorVo.getInstance(entity, true);	

			// check the business rules
			ContractorSaveRulesHandler ruleHandler = new ContractorSaveRulesHandler(context);
			if(ruleHandler.execute(vo, originalVo, entity, dialogueVo)==AbstractRule._OK){
				
				entity = ContractorVo.toEntity(entity, vo, true);
				
				if(CollectionUtility.hasValue(vo.getIncidentVos()))
					entity.setIncidents(IncidentVo.toEntityList(vo.getIncidentVos(), false));
				else if(AbstractVo.hasValue(vo.getIncidentVo())){
					entity.getIncidents().add(IncidentVo.toEntity(null,vo.getIncidentVo(), false));
				}
				
				contractorDao.save(entity);
				contractorDao.flushAndEvict(entity);
				
				ruleHandler.executeProcessedActions(vo, originalVo, entity, dialogueVo);
				
				entity=contractorDao.getById(entity.getId(), ContractorImpl.class);

				ContractorGridVo gridVo = new ContractorGridVo();
				ContractorFilter filter = new ContractorFilterImpl();
				filter.setId(entity.getId());
				
				Collection<ContractorGridVo> gridVos = contractorDao.getGrid(filter);
				if(CollectionUtility.hasValue(gridVos))
					gridVo=gridVos.iterator().next();
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CONTRACTOR");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.contractor","info.0030.01", new String[]{"Contractor " + entity.getName()} , MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(ContractorVo.getInstance(entity, true));
				dialogueVo.setResultObjectAlternate(gridVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService2#saveAgreement(gov.nwcg.isuite.core.vo.ContractorAgreementVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveAgreement(ContractorAgreementVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			ContractorAgreementDao dao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");
			ContractorAgreement entity = null;
			ContractorAgreementVo originalVo = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), ContractorAgreementImpl.class);
			}
			
			if(entity != null) {
				originalVo = ContractorAgreementVo.getInstance(entity, true);
			}

			ContractorSaveAgreementRulesHandler rulesHandler = new ContractorSaveAgreementRulesHandler(context);
			if(rulesHandler.execute(vo,entity,originalVo,dialogueVo)==AbstractRule._OK){

				Contractor contractorEntity = this.contractorDao.getById(vo.getContractorVo().getId(), ContractorImpl.class);
				entity = ContractorAgreementVo.toEntity(entity, vo, true, contractorEntity);
				
				dao.save(entity);
				dao.flushAndEvict(entity);
				if(null != contractorEntity)
					contractorDao.flushAndEvict(contractorEntity);
				
				rulesHandler.executeProcessedActions(vo, entity, originalVo, dialogueVo);
				
				entity = dao.getById(entity.getId(), ContractorAgreementImpl.class);
				vo = ContractorAgreementVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CONTRACTOR_AGREEMENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.contractor","info.0030.01", new String[]{"Contractor Agreement " + entity.getAgreementNumber()} , MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
}
