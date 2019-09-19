package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class ShiftsInExcessOfStandardHoursReportFilter extends TimeReportFilter {

	private Double standardHours;
	
	public ShiftsInExcessOfStandardHoursReportFilter() {

	}

	/**
	 * @return the standardHours
	 */
	public Double getStandardHours() {
		return standardHours;
	}

	/**
	 * @param standardHours the standardHours to set
	 */
	public void setStandardHours(Double standardHours) {
		this.standardHours = standardHours;
	}

}
