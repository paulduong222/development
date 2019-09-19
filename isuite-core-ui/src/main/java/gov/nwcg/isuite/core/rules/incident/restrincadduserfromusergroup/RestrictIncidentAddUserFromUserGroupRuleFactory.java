package gov.nwcg.isuite.core.rules.incident.restrincadduserfromusergroup;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RestrictIncidentAddUserFromUserGroupRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
		,USER_GROUP_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule 
	                                ,ApplicationContext ctx
	                                ,Incident entity
	                                ,UserGroup ugEntity) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(ugEntity, ObjectTypeEnum.USER_GROUP_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
