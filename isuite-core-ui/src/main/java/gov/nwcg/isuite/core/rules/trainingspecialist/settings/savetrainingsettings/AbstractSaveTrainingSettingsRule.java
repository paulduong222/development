package gov.nwcg.isuite.core.rules.trainingspecialist.settings.savetrainingsettings;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveTrainingSettingsRule extends AbstractRule {
	
	TrainingSettingsVo trainingSettingsVo;
	
	public AbstractSaveTrainingSettingsRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TrainingSettingsSaveRuleFactory.ObjectTypeEnum.TRAINING_SETTINGS_VO.name()))
			trainingSettingsVo = (TrainingSettingsVo)object;
	}

}
