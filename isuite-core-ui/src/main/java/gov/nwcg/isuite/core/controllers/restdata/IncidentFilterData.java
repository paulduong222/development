package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.filter.IncidentFilter;

public class IncidentFilterData extends DialogueData {

	private IncidentFilter incidentFilter;
	
	public IncidentFilter getIncidentFilter() {
		return incidentFilter;
	}
	
	public void setIncidentFilter(IncidentFilter incidentFilter) {
		this.incidentFilter = incidentFilter;
	}
}
