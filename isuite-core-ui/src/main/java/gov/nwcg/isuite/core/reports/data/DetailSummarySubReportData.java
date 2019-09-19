package gov.nwcg.isuite.core.reports.data;

import java.math.BigInteger;

/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class DetailSummarySubReportData {

	private BigInteger resourceid;
	private String grouping;
	private String groupingdata;
	private String grouping1;
	private String grouping2;
	private String grouping3;
	private Double cost;

	public DetailSummarySubReportData() {}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(BigInteger resourceid) {
		this.resourceid = resourceid;
	}

	/**
	 * @return the resourceId
	 */
	public BigInteger getResourceid() {
		return resourceid;
	}

	/**
	 * @param grouping the grouping to set
	 */
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	/**
	 * @return the grouping
	 */
	public String getGrouping() {
		return grouping;
	}

	/**
	 * @param groupingdata the groupingdata to set
	 */
	public void setGroupingdata(String groupingdata) {
		this.groupingdata = groupingdata;
	}

	/**
	 * @return the groupingdata
	 */
	public String getGroupingdata() {
		return groupingdata;
	}

	/**
	 * @param grouping1 the grouping1 to set
	 */
	public void setGrouping1(String grouping1) {
		this.grouping1 = grouping1;
	}

	/**
	 * @return the grouping1
	 */
	public String getGrouping1() {
		return grouping1;
	}

	/**
	 * @param grouping2 the grouping2 to set
	 */
	public void setGrouping2(String grouping2) {
		this.grouping2 = grouping2;
	}

	/**
	 * @return the grouping2
	 */
	public String getGrouping2() {
		return grouping2;
	}

	/**
	 * @param grouping3 the grouping3 to set
	 */
	public void setGrouping3(String grouping3) {
		this.grouping3 = grouping3;
	}

	/**
	 * @return the grouping3
	 */
	public String getGrouping3() {
		return grouping3;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

}
