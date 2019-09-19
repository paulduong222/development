package gov.nwcg.isuite.core.rules.iap.saveplan;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SavePlanRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_PLAN_VO
		,IAP_PLAN_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_PLAN_DATA(10)
		,
		VALIDATE_PLAN_NAME(20)
		,
		VALIDATE_LOCK_FORM(30)
		,
		VALIDATE_PLAN_DATE(40)
		;
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapPlanVo vo, IapPlan entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidatePlanDataRules(ctx);
				break;
			case 20: 
				ruleInstance = new ValidatePlanNameRules(ctx);
				break;
			case 30:
				ruleInstance = new ValidatePlanLockedRules(ctx);
				break;
			case 40:
				ruleInstance = new ValidatePlanDateRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_PLAN_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_PLAN_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

