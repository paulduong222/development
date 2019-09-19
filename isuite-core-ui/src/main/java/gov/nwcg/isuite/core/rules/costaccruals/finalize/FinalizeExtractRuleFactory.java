package gov.nwcg.isuite.core.rules.costaccruals.finalize;

import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class FinalizeExtractRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_ACCRUAL_EXTRACT_VO
	}
	
	public enum RuleEnum {
		VALIDATION(10)
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
	 * @param extractDate
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostAccrualExtractVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.COST_ACCRUAL_EXTRACT_VO.name());
		}
		
		return ruleInstance;
	}
}
