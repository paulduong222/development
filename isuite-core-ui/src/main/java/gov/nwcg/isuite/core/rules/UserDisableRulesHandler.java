package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.user.disableusers.DisableUsersRuleFactory;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class UserDisableRulesHandler extends AbstractRuleHandler {
	
	public UserDisableRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Collection<User> userEntities, Collection<UserVo> userVos, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			UserDao userDao = (UserDao)context.getBean("userDao");
			
			for(DisableUsersRuleFactory.RuleEnum ruleEnum : DisableUsersRuleFactory.RuleEnum.values()){
				IRule rule = DisableUsersRuleFactory.getInstance(ruleEnum, context, userVos, userEntities);
				
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
			coaVo.setMessageVo(new MessageVo("text.userAccounts", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(Collection<User> userEntities, Collection<UserVo> userVos, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			UserDao userDao = (UserDao)context.getBean("userDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(DisableUsersRuleFactory.RuleEnum ruleEnum : DisableUsersRuleFactory.RuleEnum.values()){
				IRule rule = DisableUsersRuleFactory.getInstance(ruleEnum, context, userVos, userEntities);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
