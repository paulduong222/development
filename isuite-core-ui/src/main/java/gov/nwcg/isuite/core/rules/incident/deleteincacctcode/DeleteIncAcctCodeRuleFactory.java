package gov.nwcg.isuite.core.rules.incident.deleteincacctcode;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DeleteIncAcctCodeRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ACCOUNT_CODE_ENTITY
		,INCIDENT_ACCOUNT_CODE_DAO
		,INCIDENT_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_NOT_DEFAULT(5)
		,CHECK_NOT_USED_IN_TIME_POSTINGS(10)
		,CHECK_NOT_USED_IN_TIME_ADJUSTMENTS(13)
		,CHECK_NOT_ASSIGNED_TO_RESOURCE(15)
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
										, IncidentAccountCode entity
										, Incident incidentEntity
										, IncidentAccountCodeDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckNotDefaultRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckNotUsedInTimePostingsRules(ctx);
				break;
			case 13:
				ruleInstance = new CheckNotUsedInTimeAdjustmentsRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckNotAssignedToResourceRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_ENTITY.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
