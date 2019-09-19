package gov.nwcg.isuite.core.rules.trainingspecialist.savetrainingtrainer;

import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TrainingTrainerSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		RSC_TRAINING_TRAINER_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
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
			RscTrainingTrainerVo trainerVo) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(trainerVo, ObjectTypeEnum.RSC_TRAINING_TRAINER_VO.name());
		}
		
		return ruleInstance;
		
	}

}
