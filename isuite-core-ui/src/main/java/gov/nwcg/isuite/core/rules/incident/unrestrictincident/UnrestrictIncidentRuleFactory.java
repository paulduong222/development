package gov.nwcg.isuite.core.rules.incident.unrestrictincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.rules.incident.restrictincident.CheckAlreadyRestrictedRules;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class UnrestrictIncidentRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_VO
		,INCIDENT_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)				
		,CHECK_ALREADY_UNRESTRICTED(8)
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
	                                IncidentVo vo,
	                                Incident incidentEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 8:
				ruleInstance = new CheckAlreadyUnrestrictedRules(ctx);
				break;
			case 10:
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCIDENT_VO.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
		}
		
		return ruleInstance;
	}
	
	
}
