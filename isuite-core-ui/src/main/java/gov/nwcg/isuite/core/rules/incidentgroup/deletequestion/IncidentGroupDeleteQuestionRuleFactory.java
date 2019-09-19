package gov.nwcg.isuite.core.rules.incidentgroup.deletequestion;

import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupDeleteQuestionRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_QUESTION_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_NON_STANDARD_QUESTION(5)
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
	                                IncidentGroupQuestion entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckNonStandardQuestionRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_GROUP_QUESTION_ENTITY.name());
		}
		
		return ruleInstance;
	}
	
	
}
