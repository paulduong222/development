package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.rules.incident.restrincadduser.RestrictIncidentAddUserRuleFactory;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
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

public class IncidentAddRestrictedUserRulesHandler extends AbstractRuleHandler {
	
	public IncidentAddRestrictedUserRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Incident entity, Collection<RestrictedIncidentUserVo> vos, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(RestrictIncidentAddUserRuleFactory.RuleEnum ruleEnum : RestrictIncidentAddUserRuleFactory.RuleEnum.values()){
				IRule rule = RestrictIncidentAddUserRuleFactory.getInstance(ruleEnum, context, entity, vos);
				
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

	public void executeProcessedActions(Incident entity, Collection<RestrictedIncidentUserVo> vos, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(RestrictIncidentAddUserRuleFactory.RuleEnum ruleEnum : RestrictIncidentAddUserRuleFactory.RuleEnum.values()){
				IRule rule = RestrictIncidentAddUserRuleFactory.getInstance(ruleEnum, context, entity, vos);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
