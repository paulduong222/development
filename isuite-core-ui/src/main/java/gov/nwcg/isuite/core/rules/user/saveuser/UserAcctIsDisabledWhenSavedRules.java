package gov.nwcg.isuite.core.rules.user.saveuser;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserAcctIsDisabledWhenSavedRules extends AbstractSaveUserRule implements IRule {

	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.USER_ACCT_IS_DISABLED_WHEN_SAVED_RULE.name();

	public UserAcctIsDisabledWhenSavedRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;

			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));

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
		/*A.	2.0000 – Manage User Accounts on Enterprise

			Validation on Save
			
			2.	If the enabled state of a User Account 
				is set to disabled when it is saved, the 
				system must save the account with a disabled 
				state and display a message. [Message 0022]
				
				0022	You have disabled <username>.
		*/
		
		if(!userVo.getEnabled() && userEntity != null && userEntity.isEnabled()) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(ruleName);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"info.0022",
							new String[]{userVo.getLoginName()},
							MessageTypeEnum.INFO));
			courseOfActionVo.setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			
			return _OK;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			UserSessionManagementService usms = (UserSessionManagementService)context.getBean("userSessionManagementService");

			Collection<Long> userIds = new ArrayList<Long>();
			userIds.add(userVo.getId());
			
			usms.closeDisabledSessions(userIds, super.getUserSessionVo().getUserId());

			// remove user from incidents/incident groups access lists
			super.userDao.removeUserFromAccessLists(userVo.getId());
		}
	}

	@Override
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {
		
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
		
		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != dialogueVo.getCourseOfActionVo().getMessageVo()){
				dialogueVo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos().add(coaVo.getMessageVo());
				
			}
		}

	}
	
}
