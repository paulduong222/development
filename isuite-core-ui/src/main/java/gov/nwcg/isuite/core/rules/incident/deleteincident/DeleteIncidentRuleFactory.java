package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DeleteIncidentRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_VO
		,INCIDENT_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IF_USER_IS_RESTRICTED_OWNER(5)
		,CHECK_NO_CRITICAL_DATA(10)
		,CHECK_IF_ROSS_INCIDENT(15)
		,CHECK_CROSS_TIME_POSTINGS(30)
		,CHECK_CROSS_TIME_POSTINGS_INVERSE(33)
		,CHECK_CROSS_COST_RECORDS(36)
		,CHECK_CROSS_COST_RECORDS_INVERSE(39)
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
			case 5:
				ruleInstance = new CheckRestrictedOwnerRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckNoCriticalDataRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckRossIncidentRules(ctx);
				break;
			/*
			case 30:
				ruleInstance=new CheckCrossTimePostingsRules(ctx, rule.name());
				break;
			case 33:
				ruleInstance=new CheckCrossTimePostingsInverseRules(ctx, rule.name());
				break;
			case 36:
				ruleInstance=new CheckCrossCostRecordsRules(ctx, rule.name());
				break;
			case 39:
				ruleInstance=new CheckCrossCostRecordsInverseRules(ctx, rule.name());
				break;
			*/
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCIDENT_VO.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
