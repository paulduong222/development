package gov.nwcg.isuite.core.rules.user.disableusers;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class ImmediatelyLogOutDisabledUserRules extends AbstractDisableUserRule implements IRule {

	public static final String _RULE_NAME=DisableUsersRuleFactory.RuleEnum.IMMEDIATELY_LOG_OUT_DISABLED_USER_RULE.name();

	public ImmediatelyLogOutDisabledUserRules(ApplicationContext ctx) {
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
		/*A.   2.0000 – Manage User Accounts on Enterprise

		Business Requirements
		General Requirements

		Edit a User Account

			1.  If the User Account is disabled while 
				the user of that account is logged into 
				the e-ISuite System, the user will be 
				immediately logged out of the system.
		*/
			
		
		//TODO:  display message:
	    //		 "<disabled user(s)>" has(have) been logged out.
		
		UserSessionManagementService usms = (UserSessionManagementService)context.getBean("userSessionManagementService");

		Collection<Long> userIds = new ArrayList<Long>();
		for(User user : userEntities) {
			userIds.add(user.getId());
		}

		usms.closeDisabledSessions(userIds, super.getUserSessionVo().getUserId());
		
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
