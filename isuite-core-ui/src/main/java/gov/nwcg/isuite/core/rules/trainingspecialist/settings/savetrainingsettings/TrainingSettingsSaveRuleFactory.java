package gov.nwcg.isuite.core.rules.trainingspecialist.settings.savetrainingsettings;

import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TrainingSettingsSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		TRAINING_SETTINGS_VO
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
			TrainingSettingsVo trainingSettingsVo) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(trainingSettingsVo, ObjectTypeEnum.TRAINING_SETTINGS_VO.name());
		}

		return ruleInstance;
	}

}
