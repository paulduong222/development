package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorRateImpl;
import gov.nwcg.isuite.core.filter.ContractorRateFilter;
import gov.nwcg.isuite.core.persistence.ContractorPaymentInfoDao;
import gov.nwcg.isuite.core.persistence.ContractorRateDao;
import gov.nwcg.isuite.core.rules.ContractorRateDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.ContractorRateSaveRulesHandler;
import gov.nwcg.isuite.core.service.ContractorRateService;
import gov.nwcg.isuite.core.vo.ContractorRateGridVo;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class ContractorRateServiceImpl extends BaseService implements ContractorRateService {
	private ContractorRateDao contractorRateDao;

	public ContractorRateServiceImpl(){
		
	}
	
	/**
	 * 
	 */
	public void initialization(){
		contractorRateDao = (ContractorRateDao)super.context.getBean("contractorRateDao");
	}
	
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		ContractorRateVo vo = null;
		
		try{
			ContractorRate entity = contractorRateDao.getById(id,ContractorRateImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"ContractorRate["+id+"]");

			vo = ContractorRateVo.getInstance(entity,true);
		
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CONTRACTOR_RATE_BY_ID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getGrid(ContractorRateFilter filter,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		Collection<ContractorRateGridVo> vos = new ArrayList<ContractorRateGridVo>();
		
		try{
			vos = contractorRateDao.getGrid(filter);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CONTRACTOR_RATES");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo save(ContractorRateVo vo, WorkPeriodVo workPeriodVo,DialogueVo dialogueVo) throws ServiceException, ValidationException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{			
			ContractorRate entity = null;
			
			if(vo.getId() > 0L){
				entity=contractorRateDao.getById(vo.getId(), ContractorRateImpl.class);
			}

			Long assignmentTimeId=0L;
			if(workPeriodVo.getCurrentAssignmentVo() != null && 
					workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo() != null){
				assignmentTimeId=workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo().getId();
			}
			
			ContractorRateSaveRulesHandler rulesHandler = new ContractorRateSaveRulesHandler(context);
			if(rulesHandler.execute(vo, assignmentTimeId,dialogueVo)==AbstractRule._OK){
				
				entity = ContractorRateVo.toEntity(entity,vo,true);
				contractorRateDao.save(entity);
				contractorRateDao.flushAndEvict(entity);
				entity=contractorRateDao.getById(entity.getId(), ContractorRateImpl.class);
				
				// if new rate, add record to associate with currentAssignmentvo
				if(vo.getId() == 0L){
					if(workPeriodVo.getCurrentAssignmentVo() != null && 
							workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo() != null)
					{	
						Long contractorPaymentInfoId = workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getId();
						if(contractorPaymentInfoId > 0) {
							ContractorPaymentInfoDao contractorPaymentInfoDao = (ContractorPaymentInfoDao)super.context.getBean("contractorPaymentInfoDao");
							ContractorPaymentInfo contractorPaymentInfoEntity = contractorPaymentInfoDao.getById(contractorPaymentInfoId, ContractorPaymentInfoImpl.class);
							contractorPaymentInfoEntity.getContractorRates().add(entity);
							contractorPaymentInfoDao.save(contractorPaymentInfoEntity);	
						}
					}
				}
				
				vo= ContractorRateVo.getInstance(entity, true);

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CONTRACTOR_RATE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.contractor","info.generic", new String[]{"The Contractor Rate was saved."} , MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo deleteContractorRate(ContractorRateVo vo, WorkPeriodVo workPeriodVo,DialogueVo dialogueVo) throws ServiceException{
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		if(null == vo || !LongUtility.hasValue(vo.getId())) {
			super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"ContractorRate["+vo.getId()+"]");
		}
		
		try {
			ContractorRateDeleteRulesHandler rulesHandler = new ContractorRateDeleteRulesHandler(context);
			
			ContractorRate contractorRate = contractorRateDao.getById(vo.getId(), ContractorRateImpl.class);

			Long assignmentTimeId=0L;
			if(workPeriodVo.getCurrentAssignmentVo() != null && 
					workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo() != null){
				assignmentTimeId=workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo().getId();
			}
			
			if(rulesHandler.execute(contractorRate, assignmentTimeId,dialogueVo) == ContractorRateDeleteRulesHandler._OK) {
				//remove contractor payment info rate record, prior to deleting the rate					
				if(workPeriodVo.getCurrentAssignmentVo() != null && 
						workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo() != null)
				{	
					Long contractorPaymentInfoId = workPeriodVo.getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getId();
					if(contractorPaymentInfoId > 0) {
						ContractorPaymentInfoDao contractorPaymentInfoDao = (ContractorPaymentInfoDao)super.context.getBean("contractorPaymentInfoDao");
						ContractorPaymentInfo contractorPaymentInfoEntity = contractorPaymentInfoDao.getById(contractorPaymentInfoId, ContractorPaymentInfoImpl.class);
						contractorPaymentInfoEntity.getContractorRates().remove(contractorRate);
						contractorPaymentInfoDao.save(contractorPaymentInfoEntity);	
					}
				}
				contractorRateDao.delete(contractorRate);
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_CONTRACTOR_RATE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.contractor", 
								"text.affirmDelete", 
								new String[]{"the Contractor Rate"}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
}