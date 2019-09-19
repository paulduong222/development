package gov.nwcg.isuite.core.rules.training;

import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class TrainingDateSaveRuleFactory {

	public enum ObjectTypeEnum {
		TRAINING_DATE
	}
	
	public enum RuleEnum {
		CHECK_INCIDENT_START_DATE(10)
		,CLEAN_COST_GROUP(20)
		,CLEAN_COST(30)
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
									ApplicationContext ctx,
									Date newDate) throws Exception {
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckIncidentStartDateRules(ctx);
				break;
			case 20:
				ruleInstance = new CleanCostGroupRules(ctx);
				break;
			case 30:
				ruleInstance = new CleanCostRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(newDate, ObjectTypeEnum.TRAINING_DATE.name());
		}
		
		return ruleInstance;
	}
	
}
