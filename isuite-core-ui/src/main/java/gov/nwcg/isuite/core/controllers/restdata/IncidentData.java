package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;

public class IncidentData extends DialogueData {

	private IncidentVo incidentVo;
	private WorkAreaVo workAreaVo;

	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	public WorkAreaVo getWorkAreaVo() {
		return workAreaVo;
	}
	
	public void setWorkAreaVo(WorkAreaVo workAreaVo) {
		this.workAreaVo = workAreaVo;
	}
}
