package gov.nwcg.isuite.core.rules.trainingspecialist.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class ResourceTrainingSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		RESOURCE_TRAINING_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5),
		ASSIGNMENT_DATES(10),
		START_END_DATES(15),
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx,
			ResourceTrainingVo resourceTrainingVo) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckAssignmentDatesRules(ctx);
				break;
			case 15:
				ruleInstance = new StartBeforeEndDateRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(resourceTrainingVo, ObjectTypeEnum.RESOURCE_TRAINING_VO.name());
		}
		
		return ruleInstance;
	}

}
