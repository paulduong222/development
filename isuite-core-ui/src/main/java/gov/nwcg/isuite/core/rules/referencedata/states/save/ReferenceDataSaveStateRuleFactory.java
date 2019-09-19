package gov.nwcg.isuite.core.rules.referencedata.states.save;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveStateRuleFactory {

	public enum ObjectTypeEnum {

	}

	public enum RuleEnum {
		CHECK_FOR_DUPLICATE_STATE(0),
		PREVENT_STANDARD_STATE_CHANGE(5)
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
				ruleInstance = new CheckForDuplicateStateRules(ctx);
				break;
			case 5:
				ruleInstance = new PreventStandardStateChangeRules(ctx);
				break;
		}

		if(null != ruleInstance){
			
		}

		return ruleInstance;
	}
}
