package gov.nwcg.isuite.core.rules.contractor.save;

import gov.nwcg.isuite.core.domain.ContractorNameHistory;
import gov.nwcg.isuite.core.domain.impl.ContractorNameHistoryImpl;
import gov.nwcg.isuite.core.persistence.ContractorNameHistoryDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ContractorNameHistoryRules extends AbstractContractorSaveRule implements IRule{
	public static final String _RULE_NAME=ContractorSaveRuleFactory.RuleEnum.NAME_HISTORY.name();

	public ContractorNameHistoryRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
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
	 * @throws PersistenceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {	
			
		/*When the user changes the Contractor/Cooperator Name and the
		 * Contractor/Cooperator is associated with an Original OF-286 
		 * Invoice, an action message displays requesting that the 
		 * user confirm the change.
		 */
		
		
		//do not need to check if new contractor
		if (LongUtility.hasValue(vo.getId())) {
			int cnt=dao.getAssignmentTimePostingCount(vo.getId());
			
			if(cnt > 0){
				if(!vo.getName().equalsIgnoreCase(entity.getName())) {
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0148",new String[]{vo.getName().toUpperCase()},PromptVo._YES | PromptVo._NO));
					dialogueVo.setCourseOfActionVo(coaVo);
								
					return _FAIL;
				}
			}
		}

		return _OK;
	}
	
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		//save contractor name history if contractor name was included on an original
		//OF-286 invoice
		
		//do not need to check if new contractor
		if ( (null != originalVo) && LongUtility.hasValue(originalVo.getId())) {
			
			if(!originalVo.getName().equalsIgnoreCase(entity.getName())) {
				ContractorNameHistoryDao cnhDao=(ContractorNameHistoryDao)context.getBean("contractorNameHistoryDao");
				ContractorNameHistory cnhEntity = new ContractorNameHistoryImpl();
				cnhEntity.setContractor(entity);
				cnhEntity.setOldContractorName(originalVo.getName());
				cnhEntity.setNewContractorName(entity.getName());
				
				cnhDao.save(cnhEntity);
				cnhDao.flushAndEvict(cnhEntity);
			}
		}
	}

}
