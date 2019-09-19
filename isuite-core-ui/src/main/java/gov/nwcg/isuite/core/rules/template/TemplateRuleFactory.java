package gov.nwcg.isuite.core.rules.template;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TemplateRuleFactory {

	public enum ObjectTypeEnum {
	}
	
	public enum RuleEnum {
		SAMPLE_PROMPT_RULE(5)
		,SAMPLE_MESSAGE_RULE(8)
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
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new SamplePromptRules(ctx);
				break;
			case 10:
				ruleInstance = new SampleMessageRules(ctx);
				break;
		}

		if(null != ruleInstance){
		}
		
		return ruleInstance;
	}
	

	
}
