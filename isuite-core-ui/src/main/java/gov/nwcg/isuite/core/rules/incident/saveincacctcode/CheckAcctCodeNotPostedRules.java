package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckAcctCodeNotPostedRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_ACCT_CODE_NOT_POSTED.name();

	public CheckAcctCodeNotPostedRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

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

		/*
		 * B.R. 4.0002  Applies to Edit Incident Account Code
		 * 
		 * 	If the user changes an Accounting Code that was included in any time postings 
		 *  or cost transactions for the selected Incident, the system must display a 
		 *  message stating that the system will change the Accounting Code for those transactions.
		 *  The user must indicate whether they want to continue. 
		 *  Yes and No buttons are available. 
		 *  [Message 0123]
		 *  
		 *  2.1	If the user selects Yes in response to Message 0123, 
		 *  the system must save the change to the Accounting Code 
		 *  and change the Accounting Code for any transactions associated 
		 *  with that Accounting Code.
		 *  
		 *  2.2	If the user selects No in response to Message 0123, 
		 *  the system must not save the Accounting Code and must not 
		 *  change the Accounting Code for any transactions associated 
		 *  with that Accounting Code.
		 * 
		 */
		
		if(dao.getTimePostingCount(vo.getId(), incidentEntity.getId()) > 0) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setPromptVo(
					new PromptVo(
							"text.accountingFireCodes",
							"action.0123",
							new String[]{vo.getAccountCodeVo().getAccountCode()},
							PromptVo._YES | PromptVo._NO));
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			
			// add to processed
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
			// continue;
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){

			// cannot continue if prompt was cancel post
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"save incident account code"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
