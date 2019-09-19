package gov.nwcg.isuite.core.rules.incidentcostrate.state;

import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentCostRateStateSaveRuleFactory {

	public enum ObjectTypeEnum {
		INCCOST_RATE_STATE_VO
		,COST_RATE_CATEGORY
		,INCIDENT_ID
		,GROUP_ID
	}
	
	public enum RuleEnum {
		OVERWRITE_DEFAULT_STATE_KIND_RATES(0)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                IncidentCostRateStateVo vo,
	                                String costRateCategory,
	                                Long incidentId,
	                                Long groupId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new OverwriteDefaultStateKindRatesRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCCOST_RATE_STATE_VO.name());
			ruleInstance.setObject(costRateCategory, ObjectTypeEnum.COST_RATE_CATEGORY.name());
			ruleInstance.setObject(incidentId, ObjectTypeEnum.INCIDENT_ID.name());
			ruleInstance.setObject(groupId, ObjectTypeEnum.GROUP_ID.name());
		}
		
		return ruleInstance;
	}
	

	
}
