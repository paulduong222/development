package gov.nwcg.isuite.core.rules.contractorrate.save;

import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ContractorRateSaveRuleFactory {

	public enum ObjectTypeEnum {
		CONTRACTOR_RATE_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,ContractorRateVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.CONTRACTOR_RATE_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
