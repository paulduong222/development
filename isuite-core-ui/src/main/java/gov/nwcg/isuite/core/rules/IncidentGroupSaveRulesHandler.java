package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.save.IncidentGroupSaveRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentGroupSaveRulesHandler extends AbstractRuleHandler {
	
	public IncidentGroupSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(IncidentGroupVo originalVo, IncidentGroupVo vo, IncidentGroup entity,DialogueVo dialogueVo) throws Exception {
		
		try{
			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(IncidentGroupSaveRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveRuleFactory.getInstance(ruleEnum, context, vo, originalVo, entity, dao);
				
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
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(IncidentGroupVo originalVo, IncidentGroupVo newVo, IncidentGroup entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentGroupSaveRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveRuleFactory.getInstance(ruleEnum, context, newVo, originalVo, entity, dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			for(IncidentGroupSaveRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveRuleFactory.getInstance(ruleEnum, context, null, null,null, null);
			
				if(null != rule){
					rule.addAdditionalMessages(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
