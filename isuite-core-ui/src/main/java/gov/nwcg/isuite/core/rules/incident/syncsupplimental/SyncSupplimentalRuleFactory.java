package gov.nwcg.isuite.core.rules.incident.syncsupplimental;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.rules.incident.deleteincacctcode.DeleteIncAcctCodeRuleFactory.ObjectTypeEnum;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SyncSupplimentalRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
	}
	
	public enum RuleEnum {
		SYNC_COST_GROUP_AGENCIES(20)
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
										, ApplicationContext ctx
										, Incident incidentEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 20:
				ruleInstance = new SyncCostGroupAgenciesRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
		}
		
		return ruleInstance;
	}
	
	
}
