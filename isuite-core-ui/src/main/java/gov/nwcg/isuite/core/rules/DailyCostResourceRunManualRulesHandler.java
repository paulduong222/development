package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.rules.dailycost.runmanualcost.RunManualDailyCostRuleFactory;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class DailyCostResourceRunManualRulesHandler extends AbstractRuleHandler {
	
	public DailyCostResourceRunManualRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(CostResourceDataVo costResourceDataVo, IncidentResourceOther iroEntity, DialogueVo dialogueVo) throws Exception {
		
		try{

			for(RunManualDailyCostRuleFactory.RuleEnum ruleEnum : RunManualDailyCostRuleFactory.RuleEnum.values()){
				IRule rule = RunManualDailyCostRuleFactory.getInstance(ruleEnum, context, costResourceDataVo, iroEntity);
				
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
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(CostResourceDataVo costResourceDataVo, IncidentResourceOther iroEntity,DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			for(RunManualDailyCostRuleFactory.RuleEnum ruleEnum : RunManualDailyCostRuleFactory.RuleEnum.values()){
				IRule rule = RunManualDailyCostRuleFactory.getInstance(ruleEnum, context, costResourceDataVo, iroEntity);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
