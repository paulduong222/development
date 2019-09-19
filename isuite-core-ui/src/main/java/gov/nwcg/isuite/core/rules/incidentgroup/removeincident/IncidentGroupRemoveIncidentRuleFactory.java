package gov.nwcg.isuite.core.rules.incidentgroup.removeincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupRemoveIncidentRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_ENTITY
		,INCIDENT_ENTITY
		,INCIDENT_GROUP_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,PROMPT_REMOVE_INCIDENT(5)
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
	 * @param incidentVo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentGroup igEntity, Incident incidentEntity, IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance=new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance=new PromptRemoveIncidentRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(igEntity, ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
