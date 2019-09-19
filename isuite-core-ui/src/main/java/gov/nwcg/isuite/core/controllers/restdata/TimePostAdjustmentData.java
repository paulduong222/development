package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;

public class TimePostAdjustmentData extends DialogueData {

	private IncidentResourceVo incidentResourceVo;
	private TimeAssignAdjustVo timeAssignAdjustVo;
	private Collection<Integer> crewIds;

	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}

	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
	}

	public TimeAssignAdjustVo getTimeAssignAdjustVo() {
		return timeAssignAdjustVo;
	}

	public void setTimeAssignAdjustVo(TimeAssignAdjustVo timeAssignAdjustVo) {
		this.timeAssignAdjustVo = timeAssignAdjustVo;
	}
	
	public Collection<Integer> getCrewIds() {
		return crewIds;
	}
	
	public void setCrewIds(Collection<Integer> crewIds) {
		this.crewIds = crewIds;
	}

}
