package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveDailyCostRuleFactory {
	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_DAILY_COST_VO
		,INCIDENT_RESOURCE_DAILY_COST_ENTITY
		,INCIDENT_RESOURCE_ENTITY
		,INCIDENT_RESOURCE_OTHER_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_NONAIRCRAFT_COST_DATA(10)
		,VALIDATE_AIRCRAFT_COST_DATA(20)
		,CHECK_ASSIGN_DATE(30)
		,CHECK_AGENCY(40)
		,CHECK_ACTIVITY_DATE(60)
		,CHECK_DEFAULT_COST_GROUP(70)
		,CHECK_DEFAULT_SHIFT(80)
		,CHECK_PROPAGATE_COST_GROUP(85)
		,CHECK_PROPAGATE_FLOWDOWN(90)
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
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,IncidentResourceDailyCostVo irdcVo, IncidentResourceDailyCost dailyCost ,IncidentResource irEntity,IncidentResourceOther iroEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateNonAircraftCostDataRules(ctx);
				break;
			case 20:
				ruleInstance = new ValidateAircraftCostDataRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckAssignDateRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckAgencyRules(ctx);
				break;
			case 60:
				ruleInstance = new CheckActivityDateRules(ctx);
				break;
			case 70:
				ruleInstance = new CheckDefaultCostGroupRules(ctx);
				break;
			case 80:
				ruleInstance = new CheckDefaultShiftRules(ctx);
				break;
			case 85:
				//ruleInstance = new CheckPropagateCostGroupRules(ctx);
				break;
			case 90:
				//ruleInstance = new CheckPropagateFlowdownRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(irdcVo, ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_VO.name());
			ruleInstance.setObject(dailyCost, ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_ENTITY.name());
			ruleInstance.setObject(irEntity, ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name());
			ruleInstance.setObject(iroEntity, ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
