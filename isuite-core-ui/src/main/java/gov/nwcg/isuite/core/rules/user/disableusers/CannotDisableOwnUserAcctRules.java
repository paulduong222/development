package gov.nwcg.isuite.core.rules.user.disableusers;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CannotDisableOwnUserAcctRules extends AbstractDisableUserRule implements IRule {

	public static final String _RULE_NAME=DisableUsersRuleFactory.RuleEnum.CANNOT_DISABLE_OWN_USER_ACCT_RULE.name();

	public CannotDisableOwnUserAcctRules(ApplicationContext ctx) {
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
		/*E.	2.0004 – Enable or Disable User Accounts
		
		Business Requirements

		General Requirements

			Disable User
			
			1.	The system must not allow a user to 
				disable one’s own User Account, regardless 
				of the role assigned to that User Account.
			
				0043	The user attempted to disable the User 
						Account into which they are currently logged.
				*/
		
		for(User userEntity : userEntities) {
			if(super.getUserSessionVo().getId().equals(userEntity.getId())) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_MSG_FINISHED);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.userAccounts",
								"error.0043",
								new String[]{userEntity.getLoginName()},
								MessageTypeEnum.CRITICAL));
				courseOfActionVo.setIsDialogueEnding(true);
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
			coaVo.setMessageVo(new MessageVo(
					"text.userAccounts", "text.abortProcess" , new String[]{"disable user"}, MessageTypeEnum.INFO));
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
