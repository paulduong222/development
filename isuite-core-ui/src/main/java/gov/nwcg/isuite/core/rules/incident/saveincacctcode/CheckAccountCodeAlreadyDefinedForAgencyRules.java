package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckAccountCodeAlreadyDefinedForAgencyRules extends AbstractSaveIncAcctCodeRule implements IRule {

	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_ACCT_CODE_ALREADY_DEFINED_FOR_AGENCY.name();

	public CheckAccountCodeAlreadyDefinedForAgencyRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * 4.0001 – Manage Accounting Codes
		 * 
		 *   Validation on Save
		 * 
		 * 7.	If the user adds an Accounting Code for an Agency and there was 
		 * 		already an different Accounting Code defined for that same Agency for the selected Incident, 
		 * 		the system must confirm that the user wants to add an additional Accounting Code for the same Agency. 
		 * 		Yes and No buttons are available.[Message 0281]
		 * 7.1.	If the user selects Yes in response to Message 0281, 
		 * 		the system must add the Accounting Code to the Incident.
		 * 7.2.	If the user selects No in response to Message 0281, 
		 * 		the system must not add the Accounting Code to the Incident.
		 * 
		 * Message 0281	
		 * 		An accounting code already exists for agency <Agency Name> in the <Incident Name> incident, 
		 * 		do you want to continue? [Yes] [No]
		 */

		if(!LongUtility.hasValue(vo.getId())){
			if(dao.getByAgencyAndIncident(vo.getAccountCodeVo().getAgencyVo().getId(), incidentEntity.getId(),vo.getId()).size() > 0) {

				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setPromptVo(
						new PromptVo(
								"text.accountingFireCodes",
								"action.0281",
								new String[] {
											vo.getAccountCodeVo().getAgencyVo().getAgencyNm(),
											incidentEntity.getIncidentName()
										},
								PromptVo._YES | PromptVo._NO));
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				return _FAIL;
			}
			
		}
		
		return _OK;
	}

	/**
	 * 
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

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
