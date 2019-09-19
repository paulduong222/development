package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.user.saveuser.SaveUserRuleFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserSaveRulesHandler extends AbstractRuleHandler {
	
	public UserSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(User userEntity, UserVo userVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			UserDao userDao = (UserDao)context.getBean("userDao");
			
			for(SaveUserRuleFactory.RuleEnum ruleEnum : SaveUserRuleFactory.RuleEnum.values()){
				IRule rule = SaveUserRuleFactory.getInstance(ruleEnum, context, userVo, userEntity, userDao, null);
				
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
			coaVo.setMessageVo(
					new MessageVo(
							"text.userAccounts", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(User userEntity, UserVo userVo, String dbName, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			UserDao userDao = (UserDao)context.getBean("userDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(SaveUserRuleFactory.RuleEnum ruleEnum : SaveUserRuleFactory.RuleEnum.values()){
				IRule rule = SaveUserRuleFactory.getInstance(ruleEnum, context, userVo, userEntity, userDao, dbName);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	

	public void addAdditionalMessages(UserVo userVo, DialogueVo dialogueVo) throws Exception {
		for(SaveUserRuleFactory.RuleEnum ruleEnum : SaveUserRuleFactory.RuleEnum.values()){

			IRule rule = SaveUserRuleFactory.getInstance(ruleEnum, context, userVo, null, null, null);
			
			if(null != rule){
				rule.addAdditionalMessages(dialogueVo);
			}
		}
	}
}
