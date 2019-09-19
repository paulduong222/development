package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.IncidentResourceVo;

public class IncidentResourceData extends DialogueData {
	private Boolean propagateToChildren;
	private IncidentResourceVo incidentResourceVo;
	
	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}
	
	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
	}

	public Boolean getPropagateToChildren() {
		return propagateToChildren;
	}

	public void setPropagateToChildren(Boolean propagateToChildren) {
		this.propagateToChildren = propagateToChildren;
	}
}
