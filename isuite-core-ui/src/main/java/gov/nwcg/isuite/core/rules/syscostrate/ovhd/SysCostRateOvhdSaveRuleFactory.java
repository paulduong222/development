package gov.nwcg.isuite.core.rules.syscostrate.ovhd;

import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SysCostRateOvhdSaveRuleFactory {

	public enum ObjectTypeEnum {
		SYSCOST_RATE_OVHD_VO
		,COST_RATE_CATEGORY
	}
	
	public enum RuleEnum {
		OVERWRITE_DEFAULT_KIND_RATES(0)
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
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, SysCostRateOvhdVo vo, String costRateCategory) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new OverwriteDefaultKindRatesRules(ctx);
				break;
		}
	
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.SYSCOST_RATE_OVHD_VO.name());
			ruleInstance.setObject(costRateCategory, ObjectTypeEnum.COST_RATE_CATEGORY.name());
		}
		
		return ruleInstance;
	}
	

	
}
