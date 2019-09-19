package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveIRORuleFactory {
	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_OTHER_VO
		,INCIDENT_RESOURCE_OTHER_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_IRO_DATA(10)
		,CHECK_REQUEST_NUMBER_FORMAT(20)
		,CHECK_ASSIGN_DATE(30)
		,GENERATE_DEFAULT_ACCRUAL_CODE(40)
		,COST_ACCRUAL_CHANGE(100)
		,COST_ACCRUAL_CHANGE2(110)
		,CHECK_RUN_COST_AUTO(200)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentResourceOtherVo iroVo,IncidentResourceOther entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 10:
				ruleInstance = new ValidateIRODataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckIRORequestNumberFormatRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckIROAssignDateRules(ctx);
				break;
			case 40:
				//ruleInstance = new DefaultAccrualCodeRules(ctx);
				break;
			case 100:
				ruleInstance = new CostAccrualChangeRules(ctx);
				break;
			case 110:
				ruleInstance = new CostAccrualChangeRules2(ctx);
				break;
			case 200:
				ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(iroVo, ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name());
		}
		
		return ruleInstance;
	}
}
