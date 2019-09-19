package gov.nwcg.isuite.core.rules.syscostrate.state;

import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SysCostRateStateSaveRuleFactory {

	public enum ObjectTypeEnum {
		SYSCOST_RATE_STATE_VO
	}
	
	public enum RuleEnum {
		OVERWRITE_DEFAULT_STATE_KIND_RATES(0)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, SysCostRateStateVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new OverwriteDefaultStateKindRatesRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.SYSCOST_RATE_STATE_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
