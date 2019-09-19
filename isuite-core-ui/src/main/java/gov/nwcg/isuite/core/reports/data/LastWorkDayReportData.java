package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;

import java.math.BigInteger;
import java.util.Date;

/**
 * Report data object for LastWorkDayReport.jrxml.
 */
public class LastWorkDayReportData extends BaseReportData {
	private String requestNumber = "";
	private Date lastWorkDate = null;
	private Date startDate = null;
	private BigInteger lengthAtAssignment;
	private String section = "";
	private String resourceCategory = "";
	private String kindCode = "";
	private String resName = "";
	
	public LastWorkDayReportData() {
	}
	
	/**
	 * Sets the requestNumber
	 * 
	 * @param requestNumber
	 * 		the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the requestNumber
	 * 
	 * @return
	 * 		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the section
	 * 
	 * @param section
	 * 		the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * Returns the section
	 * 
	 * @return
	 * 		the section to return
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the resourceCategory
	 * 
	 * @param resourceCategory
	 * 		the resourceCategory to set
	 */
	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}

	/**
	 * Returns the resourceCategory
	 * 
	 * @return
	 * 		the resourceCategory to return
	 */
	public String getResourceCategory() {
		return resourceCategory;
	}

	/**
	 * Sets the kindCode
	 * 
	 * @param kindCode
	 * 		the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * Returns the kindCode
	 * 
	 * @return
	 * 		the kindCode to return
	 */
	public String getKindCode() {
		return kindCode;
	}

	/**
	 * Sets the lastWorkDate
	 * 
	 * @param lastWorkDate
	 * 		the lastWorkDate to set
	 */
	public void setLastWorkDate(Date lastWorkDate) {
		this.lastWorkDate = lastWorkDate;
	}

	/**
	 * Returns the lastWorkDate
	 * 
	 * @return
	 * 		the lastWorkDate to return
	 */
	public Date getLastWorkDate() {
		return this.lastWorkDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param lengthAtAssignment the lengthAtAssignment to set
	 */
	public void setLengthAtAssignment(BigInteger lengthAtAssignment) {
		this.lengthAtAssignment = lengthAtAssignment;
	}

	/**
	 * @return the lengthAtAssignment
	 */
	public BigInteger getLengthAtAssignment() {
		return lengthAtAssignment;
	}

	/**
	 * Sets the resName
	 * 
	 * @param resName
	 * 		the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * Returns the resName
	 * 
	 * @return
	 * 		the resName to return
	 */
	public String getResName() {
		return resName;
	}
}
