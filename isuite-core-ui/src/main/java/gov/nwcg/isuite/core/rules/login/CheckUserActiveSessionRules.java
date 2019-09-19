package gov.nwcg.isuite.core.rules.login;

import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckUserActiveSessionRules extends AbstractLoginRule implements IRule{
	public static final String _RULE_NAME=LoginRuleFactory.RuleEnum.CHECK_ACTIVE_SESSIONS.name();

	public CheckUserActiveSessionRules(ApplicationContext ctx)
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
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(super.fromServlet==true)
			return _OK;
		
		// verify user does not have a session open
		UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
		if(null != super.getUserSessionVo() && StringUtility.hasValue(super.getUserSessionVo().getSiteDatabaseName())) {
			if(usms2.isUserLoggedIn(user.getId(), super.getUserSessionVo().getSiteDatabaseName())){
				createLogoutUserPrompt(user, dialogueVo);
				return _FAIL;
			}
			
		}

		user.setFailedLoginAttempts(0);
		userDao.setSkipSetAuditInfo(true);
		userDao.save(user);
		userDao.resetSkipAuditInfo();
		
		/*
		if(!sessionAdded) {
	    	createLogoutUserPrompt(user, dialogueVo);
	    	return _FAIL;
	    }
	    */
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

		
	}

}
