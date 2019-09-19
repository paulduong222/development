package gov.nwcg.isuite.core.rules.login;

import javax.servlet.http.HttpServletRequest;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.apache.commons.lang.text.StrTokenizer;
import org.springframework.context.ApplicationContext;

public class AbstractLoginRule extends AbstractRule {
	protected String userName;
	protected String password;
	protected UserDao userDao;
	protected User user;
	protected UserSessionManagementService usms;
	protected Boolean fromServlet=false;
	
	Boolean sessionAdded = false;
	
	public AbstractLoginRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.USER_ENTITY.name()))
			user = (User)object;
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.USER_NAME.name()))
			userName = (String)object;
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.PASSWORD.name()))
			password = (String)object;
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.USER_DAO.name()))
			userDao = (UserDao)object;
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.USER_SESSION_MANAGEMENT_SERVICE.name()))
			usms = (UserSessionManagementService)object;
		if(objectName.equals(LoginRuleFactory.ObjectTypeEnum.FROM_SERVLET.name()))
			fromServlet = (Boolean)object;
	}
	
	protected DialogueVo createLogoutUserPrompt(User user, DialogueVo dialogueVo) throws Exception {
		PromptVo promptVo = new PromptVo();
        promptVo.setMessageProperty("action.0246");
        promptVo.setTitleProperty("text.login");
        promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
        CourseOfActionVo coa = new CourseOfActionVo();
        coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
        coa.setPromptVo(promptVo);
        dialogueVo.setCourseOfActionVo(coa);
        UserSessionVo userSessionVo = UserSessionVo.getInstance(user);
        dialogueVo.setResultObject(userSessionVo);
        return dialogueVo;
	}
	
	protected String getIpFromRequest(HttpServletRequest request) {
		String ip="";

		if ((ip = request.getHeader("x-forwarded-for")) != null) {
			StrTokenizer tokenizer = new StrTokenizer(ip, ",");
			while (tokenizer.hasNext()) {
				ip = tokenizer.nextToken().trim();
				break;
			}
		}

		if(!StringUtility.hasValue(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;

	}	
	
}
