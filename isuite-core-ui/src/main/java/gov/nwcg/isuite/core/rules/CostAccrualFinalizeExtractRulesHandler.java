package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costaccruals.finalize.FinalizeExtractRuleFactory;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CostAccrualFinalizeExtractRulesHandler extends AbstractRuleHandler {
	
	public CostAccrualFinalizeExtractRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(CostAccrualExtractVo vo,DialogueVo dialogueVo) throws Exception {
		
		try {

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, vo.getIncidentId(), "INCIDENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			for(FinalizeExtractRuleFactory.RuleEnum ruleEnum : FinalizeExtractRuleFactory.RuleEnum.values()) {
				IRule rule = FinalizeExtractRuleFactory.getInstance(ruleEnum, context, vo);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costAccruals", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int executeProcessedActions() throws Exception{
		return _OK;
	}

}
