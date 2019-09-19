package gov.nwcg.isuite.core.rules.mbadmin.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.framework.core.rules.IRule;

public class SaveMessageRuleFactory {
	
	public enum ObjectTypeEnum {
		
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx) throws Exception {
	
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
		}

		return ruleInstance;
		
	}

}
