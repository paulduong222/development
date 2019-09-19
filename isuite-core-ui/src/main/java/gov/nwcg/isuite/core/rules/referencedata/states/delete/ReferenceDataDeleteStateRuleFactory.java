package gov.nwcg.isuite.core.rules.referencedata.states.delete;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataDeleteStateRuleFactory {

	public enum ObjectTypeEnum {

	}

	public enum RuleEnum {
		CHECK_IF_STATE_CODE_IS_CURRENTLY_IN_USE(0),
		PREVENT_STANDARD_STATE_CODE_DELETION(5)
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
			ApplicationContext ctx) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckIfStateCodeIsCurrentlyInUseRules(ctx);
				break;
			case 5:
				ruleInstance = new PreventStandardStateCodeDeletionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			
		}

		return ruleInstance;
	}
}
