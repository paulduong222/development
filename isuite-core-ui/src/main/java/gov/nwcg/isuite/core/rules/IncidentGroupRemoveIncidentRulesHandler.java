package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.removeincident.IncidentGroupRemoveIncidentRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentGroupRemoveIncidentRulesHandler extends AbstractRuleHandler {
	
	public IncidentGroupRemoveIncidentRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param incidentVo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentGroup igEntity, Incident incidentEntity, DialogueVo dialogueVo) throws Exception {
		
		try{
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(IncidentGroupRemoveIncidentRuleFactory.RuleEnum ruleEnum : IncidentGroupRemoveIncidentRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupRemoveIncidentRuleFactory.getInstance(ruleEnum, context, igEntity, incidentEntity, incidentGroupDao);
				
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

	public void executeProcessedActions(IncidentGroup igEntity, Incident incidentEntity, IncidentGroup entity,DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentGroupRemoveIncidentRuleFactory.RuleEnum ruleEnum : IncidentGroupRemoveIncidentRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupRemoveIncidentRuleFactory.getInstance(ruleEnum, context, igEntity, incidentEntity, incidentGroupDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
