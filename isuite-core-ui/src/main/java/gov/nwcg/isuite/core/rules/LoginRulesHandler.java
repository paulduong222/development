package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.login.LoginRuleFactory;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class LoginRulesHandler extends AbstractRuleHandler {
	
	public LoginRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(String userName, String password, Boolean fromServlet, DialogueVo dialogueVo) throws Exception {
		
		try{

			UserDao dao = (UserDao)context.getBean("userDao");
			UserSessionManagementService usms = (UserSessionManagementService)context.getBean("userSessionManagementService");
			
			/*
			 * Read the user table for userId
			 */
			User user = dao.getByLoginName(userName);
			
			for(LoginRuleFactory.RuleEnum ruleEnum : LoginRuleFactory.RuleEnum.values()){
				IRule rule = LoginRuleFactory.getInstance(ruleEnum
														, context
														, dao
														, userName
														, password
														, fromServlet
														,user
														, usms);
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
				
			}
				
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.login", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(String userName, String password, DialogueVo dialogueVo) throws ServiceException {
		
	}
	
}
