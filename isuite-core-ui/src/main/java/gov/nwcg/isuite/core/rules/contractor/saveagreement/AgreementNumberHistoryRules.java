package gov.nwcg.isuite.core.rules.contractor.saveagreement;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementNumberHistoryImpl;
import gov.nwcg.isuite.core.persistence.ContractorAgreementNumberHistoryDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class AgreementNumberHistoryRules extends AbstractSaveAgreementRule implements IRule {
	public static final String _RULE_NAME=ContractorSaveAgreementRuleFactory.RuleEnum.NUMBER_HISTORY.name();

	public AgreementNumberHistoryRules(ApplicationContext ctx) {
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{

				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {	
		
		/*When the user changes the Contract/Agreement Number and saves the
		 * contract/agreement data, the system must display and action message 
		 * requesting that the user confirm the change.
		 */
		
		
		//do not need to check if new contractor agreement number
		if (LongUtility.hasValue(vo.getId())) {
			int cnt=dao.getAssignmentTimePostingCount(vo.getId());
			
			if(cnt > 0){
				if(!vo.getAgreementNumber().equalsIgnoreCase(entity.getAgreementNumber())) {
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0149",new String[]{vo.getAgreementNumber().toUpperCase()},PromptVo._YES | PromptVo._NO));
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
				}
			}
		}

		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contractor", "text.abortProcess" , new String[]{"text.contractor"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}

		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		//save contractor agreement number history if contractor agreement number
		//was included on an original OF-286 invoice
		
		//do not need to check if new contractor agreement
		if ((null != originalVo) && LongUtility.hasValue(originalVo.getId())
				&& vo.getContractorAgreementNumberHistoryVo() != null) {
			if(StringUtility.hasValue(vo.getContractorAgreementNumberHistoryVo().getReasonText())){
				ContractorAgreementNumberHistoryDao canhDao=(ContractorAgreementNumberHistoryDao)context.getBean("contractorAgreementNumberHistoryDao");
				ContractorAgreementNumberHistory canhEntity = new ContractorAgreementNumberHistoryImpl();
				canhEntity.setContractorAgreement(entity);
				canhEntity.setReasonText(vo.getContractorAgreementNumberHistoryVo().getReasonText());
				canhEntity.setOldAgreementNumber(originalVo.getAgreementNumber());
				canhEntity.setNewAgreementNumber(vo.getAgreementNumber());
				
				canhDao.save(canhEntity);
				canhDao.flushAndEvict(canhEntity);
			}
		}
	}



}
