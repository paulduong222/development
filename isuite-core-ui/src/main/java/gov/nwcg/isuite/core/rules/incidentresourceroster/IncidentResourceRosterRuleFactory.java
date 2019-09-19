package gov.nwcg.isuite.core.rules.incidentresourceroster;

import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentResourceRosterRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_VO
		,WORK_AREA_ID
	}
	
	public enum RuleEnum {
		DEFINE_RULE(0);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static RuleEnum getRuleEnum(int ruleIdx) {
		
		return null;
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentResourceVo vo, Long workAreaId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
		}
		
		return ruleInstance;
	}
	

	
}
