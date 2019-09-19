package gov.nwcg.isuite.core.rules.cost.dailycost;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DailyCostRuleFactory {
	
	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE
		,INCIDENT_RESOURCE_OTHER
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_AGENCY(5)
		,CHECK_VALID_START_DATE(10)
		,CHECK_ACTUALS_ONLY(15)
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
										IncidentResource irEntity, 
										IncidentResourceOther iroEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				break;
			case 5:
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(irEntity, ObjectTypeEnum.INCIDENT_RESOURCE.name());
			ruleInstance.setObject(iroEntity, ObjectTypeEnum.INCIDENT_RESOURCE_OTHER.name());
		}
		
		return ruleInstance;
	}
}
