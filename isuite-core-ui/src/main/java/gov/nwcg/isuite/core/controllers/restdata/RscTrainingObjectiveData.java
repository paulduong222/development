package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.RscTrainingObjectiveVo;

public class RscTrainingObjectiveData extends DialogueData {

	private Collection<RscTrainingObjectiveVo> rscTrainingObjectiveVos;
	
	public Collection<RscTrainingObjectiveVo> getRscTrainingObjectiveVos() {
		return rscTrainingObjectiveVos;
	}
	
	public void setRscTrainingObjectiveVos(Collection<RscTrainingObjectiveVo> rscTrainingObjectiveVos) {
		this.rscTrainingObjectiveVos = rscTrainingObjectiveVos;
	}
}
