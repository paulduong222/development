package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.incidentcostrate.state.IncidentCostRateStateSaveRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentCostRateStateSaveRulesHandler extends AbstractRuleHandler {
	
	public IncidentCostRateStateSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(IncidentCostRateStateVo vo, String costRateCategory,
						Long incidentId, Long groupId, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(IncidentCostRateStateSaveRuleFactory.RuleEnum ruleEnum : IncidentCostRateStateSaveRuleFactory.RuleEnum.values()){
				IRule rule = IncidentCostRateStateSaveRuleFactory.getInstance(ruleEnum, context, vo, costRateCategory, incidentId, groupId);
				
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
			coaVo.setMessageVo(new MessageVo("text.costRate", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param vo
	 * @param costRateCategory
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IncidentCostRateStateVo vo, String costRateCategory,
					Long incidentId, Long groupId, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentCostRateStateSaveRuleFactory.RuleEnum ruleEnum : IncidentCostRateStateSaveRuleFactory.RuleEnum.values()){
				IRule rule = IncidentCostRateStateSaveRuleFactory.getInstance(ruleEnum, context, vo, costRateCategory, incidentId, groupId);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
