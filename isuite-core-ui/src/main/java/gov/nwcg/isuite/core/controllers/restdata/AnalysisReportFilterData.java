package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;

public class AnalysisReportFilterData extends DialogueData {

	private AnalysisReportFilter filter;
	
	public AnalysisReportFilter getFilter() {
		return filter;
	}
	
	public void setFilter(AnalysisReportFilter filter) {
		this.filter = filter;
	}
}
