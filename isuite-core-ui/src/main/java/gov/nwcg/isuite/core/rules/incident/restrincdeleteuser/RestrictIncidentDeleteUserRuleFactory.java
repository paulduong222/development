package gov.nwcg.isuite.core.rules.incident.restrincdeleteuser;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.rules.incident.restrincadduser.RestrictIncidentAddUserRuleFactory.RuleEnum;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RestrictIncidentDeleteUserRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_VO
		,INCIDENT_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IF_OWNER_CAN_DELETE_USER(5)
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
						            Incident incidentEntity,
						            Collection<RestrictedIncidentUserVo> vos) throws Exception {

		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				break;
			case 5:
				break;
			case 10:
				break;
		}

		if(null != ruleInstance){
		}
		
		return ruleInstance;
	}
	

	
}
