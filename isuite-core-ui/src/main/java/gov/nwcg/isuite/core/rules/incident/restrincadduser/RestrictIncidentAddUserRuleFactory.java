package gov.nwcg.isuite.core.rules.incident.restrincadduser;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class RestrictIncidentAddUserRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
		,RESTRICTED_INCIDENT_USER_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IF_OWNER_CAN_ADD_USER(5)
		,CHECK_IF_PRIV_USER_CAN_ADD_USER(10)
		,CHECK_USER_NOT_ALREADY_ADDED(15)
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
	 * @param incidentEntity
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx,
	                                Incident incidentEntity,
	                                Collection<RestrictedIncidentUserVo> vos) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckIfOwnerCanAddUserRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckIfPrivUserCanAddUserRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckUserNotAlreadyAddedRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(incidentEntity,ObjectTypeEnum.INCIDENT_ENTITY.name() );
			ruleInstance.setObject(vos, ObjectTypeEnum.RESTRICTED_INCIDENT_USER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
