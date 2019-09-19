package gov.nwcg.isuite.core.rules.iap.deleteposition;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.framework.core.rules.IRule;

public class PositionDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0);
		
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
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
		}
		
		return ruleInstance;
	}

}
