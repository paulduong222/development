package gov.nwcg.isuite.core.rules.incident.savecostdefaults;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentSaveCostDefaultsRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
		,INCIDENT_DAO
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
	
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                Incident entity,
	                                IncidentDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_DAO.name());
		}
		
		return ruleInstance;
	}
	
	
}
