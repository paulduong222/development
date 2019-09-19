package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;

public class IncidentSelector2Data extends DialogueData {

	Collection<IncidentSelector2Vo> incidentSelector2Vos;
	
	public Collection<IncidentSelector2Vo> getIncidentSelector2Vos() {
		return incidentSelector2Vos;
	}
	
	public void setIncidentSelector2Vos(Collection<IncidentSelector2Vo> incidentSelector2Vos) {
		this.incidentSelector2Vos = incidentSelector2Vos;
	}
}
