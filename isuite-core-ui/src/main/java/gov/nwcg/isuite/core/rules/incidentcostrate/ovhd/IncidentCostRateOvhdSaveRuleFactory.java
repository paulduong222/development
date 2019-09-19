package gov.nwcg.isuite.core.rules.incidentcostrate.ovhd;

import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentCostRateOvhdSaveRuleFactory {

	public enum ObjectTypeEnum {
		INCCOST_RATE_OVHD_VO
		,COST_RATE_CATEGORY
		,INCIDENT_ID
		,INCIDENT_GROUP_ID
	}
	
	public enum RuleEnum {
		OVERWRITE_DEFAULT_KIND_RATES(0)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param vo
	 * @param costRateCategory
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                IncidentCostRateOvhdVo vo,
	                                String costRateCategory,
	                                Long incidentId,
	                                Long groupId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new OverwriteDefaultKindRatesRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCCOST_RATE_OVHD_VO.name());
			ruleInstance.setObject(costRateCategory, ObjectTypeEnum.COST_RATE_CATEGORY.name());
			ruleInstance.setObject(incidentId, ObjectTypeEnum.INCIDENT_ID.name());
			ruleInstance.setObject(groupId, ObjectTypeEnum.INCIDENT_GROUP_ID.name());
		}
		
		return ruleInstance;
	}
	

	
}
