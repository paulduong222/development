package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class OF286TimeInvoiceReportFilter extends TimeReportFilter {

	private Boolean selectByRequestNumber;
	private Boolean selectByResourceName;
	
	public OF286TimeInvoiceReportFilter() {
		
	}

	/**
	 * @return the selectByRequestNumber
	 */
	public Boolean getSelectByRequestNumber() {
		return selectByRequestNumber;
	}

	/**
	 * @param selectByRequestNumber the selectByRequestNumber to set
	 */
	public void setSelectByRequestNumber(Boolean selectByRequestNumber) {
		this.selectByRequestNumber = selectByRequestNumber;
	}

	/**
	 * @return the selectByResourceName
	 */
	public Boolean getSelectByResourceName() {
		return selectByResourceName;
	}

	/**
	 * @param selectByResourceName the selectByResourceName to set
	 */
	public void setSelectByResourceName(Boolean selectByResourceName) {
		this.selectByResourceName = selectByResourceName;
	}
}
