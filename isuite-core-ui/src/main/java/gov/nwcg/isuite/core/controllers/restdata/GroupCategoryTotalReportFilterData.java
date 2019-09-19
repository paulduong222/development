package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;

public class GroupCategoryTotalReportFilterData extends DialogueData {

	private GroupCategoryTotalReportFilter filter;
	
	public GroupCategoryTotalReportFilter getFilter() {
		return filter;
	}
	
	public void setFilter(GroupCategoryTotalReportFilter filter) {
		this.filter = filter;
	}
}
