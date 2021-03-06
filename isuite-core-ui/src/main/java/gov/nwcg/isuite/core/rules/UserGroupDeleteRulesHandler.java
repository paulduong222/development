package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.rules.usergroup.delete.UserGroupDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserGroupDeleteRulesHandler extends AbstractRuleHandler {
	
	public UserGroupDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(UserGroup entity, DialogueVo dialogueVo)throws Exception {
		
		try{
			UserGroupDao userGroupDao = (UserGroupDao)context.getBean("userGroupDao");
			
			for(UserGroupDeleteRuleFactory.RuleEnum ruleEnum : UserGroupDeleteRuleFactory.RuleEnum.values()){
				IRule rule = UserGroupDeleteRuleFactory.getInstance(ruleEnum, context, entity, userGroupDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.incident", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(UserGroup entity, UserGroupVo vo, DialogueVo dialogueVo) throws Exception {
		try{

		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
