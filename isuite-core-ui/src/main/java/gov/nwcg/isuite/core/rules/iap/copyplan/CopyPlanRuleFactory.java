package gov.nwcg.isuite.core.rules.iap.copyplan;

import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CopyPlanRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_PLAN_SOURCE_VO
		, IAP_PLAN_DESTINATION_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		, VALIDATE_PLAN_NAME(20)
		, VALIDATE_PLAN_DATE(30)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
		
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapCopyVo vo) throws Exception {
			IRule ruleInstance = null;
			
			switch(rule.getRuleIdx())
			{
				case 10:
					ruleInstance = new ValidateDataRules(ctx);
					break;
				case 20:
					ruleInstance = new ValidatePlanNameRules(ctx);
					break;
				case 30:
					ruleInstance = new ValidatePlanDateRules(ctx);
					break;
			}
			
			if(null != ruleInstance){
				ruleInstance.setObject(vo.getIapPlanSourceVo(), ObjectTypeEnum.IAP_PLAN_SOURCE_VO.name());
				ruleInstance.setObject(vo.getIapPlanDestinationVo(), ObjectTypeEnum.IAP_PLAN_DESTINATION_VO.name());
			}
			
			return ruleInstance;
	}

}
