package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class VendorResourceSummaryReportFilter extends TimeReportFilter {
	
	private boolean groupByNone = true;
	private boolean groupByItemCode;
	private boolean groupByVendor;
	private boolean groupByHireDate;

	private boolean sortByRequestNumber = true;
	private boolean sortByItemCode;
	private boolean sortByVendor;
	private boolean sortByHireDate;

	//private boolean excludeDemob = true;
//	private boolean print;

	public VendorResourceSummaryReportFilter() {
	}

	/**
	 * @return the groupByNone
	 */
	public boolean isGroupByNone() {
		return groupByNone;
	}

	/**
	 * @param groupByNone the groupByNone to set
	 */
	public void setGroupByNone(boolean groupByNone) {
		this.groupByNone = groupByNone;
	}

	/**
	 * @return the groupByItemCode
	 */
	public boolean isGroupByItemCode() {
		return groupByItemCode;
	}

	/**
	 * @param groupByItemCode the groupByItemCode to set
	 */
	public void setGroupByItemCode(boolean groupByItemCode) {
		this.groupByItemCode = groupByItemCode;
	}

	/**
	 * @return the groupByVendor
	 */
	public boolean isGroupByVendor() {
		return groupByVendor;
	}

	/**
	 * @param groupByVendor the groupByVendor to set
	 */
	public void setGroupByVendor(boolean groupByVendor) {
		this.groupByVendor = groupByVendor;
	}

	/**
	 * @return the groupByHireDate
	 */
	public boolean isGroupByHireDate() {
		return groupByHireDate;
	}

	/**
	 * @param groupByHireDate the groupByHireDate to set
	 */
	public void setGroupByHireDate(boolean groupByHireDate) {
		this.groupByHireDate = groupByHireDate;
	}

	/**
	 * @return the sortByRequestNumber
	 */
	public boolean isSortByRequestNumber() {
		return sortByRequestNumber;
	}

	/**
	 * @param sortByRequestNumber the sortByRequestNumber to set
	 */
	public void setSortByRequestNumber(boolean sortByRequestNumber) {
		this.sortByRequestNumber = sortByRequestNumber;
	}

	/**
	 * @return the sortByItemCode
	 */
	public boolean isSortByItemCode() {
		return sortByItemCode;
	}

	/**
	 * @param sortByItemCode the sortByItemCode to set
	 */
	public void setSortByItemCode(boolean sortByItemCode) {
		this.sortByItemCode = sortByItemCode;
	}

	/**
	 * @return the sortByVendor
	 */
	public boolean isSortByVendor() {
		return sortByVendor;
	}

	/**
	 * @param sortByVendor the sortByVendor to set
	 */
	public void setSortByVendor(boolean sortByVendor) {
		this.sortByVendor = sortByVendor;
	}

	/**
	 * @return the sortByHireDate
	 */
	public boolean isSortByHireDate() {
		return sortByHireDate;
	}

	/**
	 * @param sortByHireDate the sortByHireDate to set
	 */
	public void setSortByHireDate(boolean sortByHireDate) {
		this.sortByHireDate = sortByHireDate;
	}

//	/**
//	 * @return the excludeDemob
//	 */
//	public boolean isExcludeDemob() {
//		return excludeDemob;
//	}
//
//	/**
//	 * @param excludeDemob the excludeDemob to set
//	 */
//	public void setExcludeDemob(boolean excludeDemob) {
//		this.excludeDemob = excludeDemob;
//	}

	
//	public String getReportType() {
//		if(isReportOnly)  
//			return "reportsOnly";
//		else if(isReportAndGraph)
//			return "reportsAndGraph";
//		else if(isGraphOnly)
//			return "graphOnly";
//		else
//			return "";
//	}
}
