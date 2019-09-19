package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.TrainingSettingsVo;

public class TrainingSettingsData extends DialogueData {

	private TrainingSettingsVo trainingSettingsVo;
	
	public TrainingSettingsVo getTrainingSettingsVo() {
		return trainingSettingsVo;
	}
	
	public void setTrainingSettingsVo(TrainingSettingsVo trainingSettingsVo) {
		this.trainingSettingsVo = trainingSettingsVo;
	}
}
