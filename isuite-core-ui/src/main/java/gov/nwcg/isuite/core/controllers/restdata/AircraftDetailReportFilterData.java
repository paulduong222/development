package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;

public class AircraftDetailReportFilterData extends DialogueData {

	private AircraftDetailReportFilter filter;
	
	public AircraftDetailReportFilter getFilter() {
		return filter;
	}
	
	public void setFilter(AircraftDetailReportFilter filter) {
		this.filter = filter;
	}
}
