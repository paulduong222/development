package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.addincident.IncidentGroupAddIncidentRuleFactory;
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

public class IncidentGroupAddIncidentRulesHandler extends AbstractRuleHandler {
	
	public IncidentGroupAddIncidentRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param incidentVo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentGroupVo newVo, IncidentGroupVo originalVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(IncidentGroupAddIncidentRuleFactory.RuleEnum ruleEnum : IncidentGroupAddIncidentRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupAddIncidentRuleFactory.getInstance(ruleEnum, context, newVo, originalVo, incidentGroupDao);
				
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

	public void executeProcessedActions(IncidentGroupVo newVo, IncidentGroupVo originalVo, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentGroupAddIncidentRuleFactory.RuleEnum ruleEnum : IncidentGroupAddIncidentRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupAddIncidentRuleFactory.getInstance(ruleEnum, context, newVo, originalVo, incidentGroupDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {
		for(IncidentGroupAddIncidentRuleFactory.RuleEnum ruleEnum : IncidentGroupAddIncidentRuleFactory.RuleEnum.values()){

			IRule rule = IncidentGroupAddIncidentRuleFactory.getInstance(ruleEnum, context, null, null, null);
			
			if(null != rule){
				rule.addAdditionalMessages(dialogueVo);
			}
		}
	}
}
