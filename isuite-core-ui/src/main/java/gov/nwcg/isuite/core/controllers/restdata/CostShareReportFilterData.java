package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;

public class CostShareReportFilterData extends DialogueData {

	private CostShareReportFilter filter;
	
	public CostShareReportFilter getFilter() {
		return filter;
	}
	
	public void setFilter(CostShareReportFilter filter) {
		this.filter = filter;
	}
}
