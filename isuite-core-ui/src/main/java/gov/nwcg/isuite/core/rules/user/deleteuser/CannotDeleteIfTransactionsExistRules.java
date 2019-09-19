package gov.nwcg.isuite.core.rules.user.deleteuser;

import gov.nwcg.isuite.core.persistence.UserSessionLogDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CannotDeleteIfTransactionsExistRules extends AbstractDeleteUserRule implements IRule {

	public static final String _RULE_NAME=DeleteUsersRuleFactory.RuleEnum.CANNOT_DELETE_IF_TRANSACTIONS_EXIST_RULE.name();

	public CannotDeleteIfTransactionsExistRules(ApplicationContext ctx) {
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
		/*D.	2.0003 – Delete User Accounts
		
		Business Requirements
		
		General Requirements
		
		2.	The system must allow an Account Manager 
			to delete a User Account when there are no 
			transactions (e.g., user logs into the system) 
			associated with that User Account.

		Validation on Save
			2.	If the user attempts to delete a User Account 
				that cannot be deleted, the system must not 
				delete the User Account and must display a message. 
				[Message 0240]  
			*/ 
		
		UserSessionLogDao usldao = (UserSessionLogDao)context.getBean("userSessionLogDao");
		int cnt=usldao.getUserLoginActivityCount(super.userEntity.getId());
		if(cnt > 0){
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"error.0240",
							new String[]{userEntity.getLoginName()},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		/*
		if(userEntity.getLastLoginDate() != null) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"error.0240",
							new String[]{userEntity.getLoginName()},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		*/
		
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
					"text.userAccounts", "text.abortProcess" , new String[]{"delete user"}, MessageTypeEnum.INFO));
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
