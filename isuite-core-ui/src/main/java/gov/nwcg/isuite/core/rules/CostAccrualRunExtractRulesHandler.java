package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.costaccruals.runextract.RunExtractRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CostAccrualRunExtractRulesHandler extends AbstractRuleHandler {
	
	public CostAccrualRunExtractRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(String extractDate,CostAccrualExtractDao dao, Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws Exception {
		
		try {
			Long id=0L;
			String type="";
			if(LongUtility.hasValue(incidentId)){
				id=incidentId;
				type="INCIDENT";
			}else if(LongUtility.hasValue(incidentGroupId)){
				id=incidentGroupId;
				type="INCIDENTGROUP";
			}
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			for(RunExtractRuleFactory.RuleEnum ruleEnum : RunExtractRuleFactory.RuleEnum.values()) {
				IRule rule = RunExtractRuleFactory.getInstance(ruleEnum, context, dao, extractDate, incidentId, incidentGroupId);
				
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
