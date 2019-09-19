package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

/**
 * Report data object for VendorResourceSummaryReport.jrxml.
 */
public class VendorResourceSummaryReportData {

	private String requestNumber = "";
	private String resourceName = "";
	private String itemCode = "";
	private String status = "";
	private String vendorAgreementNumber = "";
	private String uniqueName = "";
	private Date hireDate = null;
	private String hireTime = "";
	private Date releaseDate = null;
	private String releaseTime = "";
	
	public VendorResourceSummaryReportData() {}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the vendorAgreementNumber
	 */
	public String getVendorAgreementNumber() {
		return vendorAgreementNumber;
	}

	/**
	 * @param vendorAgreementNumber the vendorAgreementNumber to set
	 */
	public void setVendorAgreementNumber(String vendorAgreementNumber) {
		this.vendorAgreementNumber = vendorAgreementNumber;
	}

	/**
	 * @return the uniqueName
	 */
	public String getUniqueName() {
		return uniqueName;
	}

	/**
	 * @param uniqueName the uniqueName to set
	 */
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	/**
	 * @return the hireDate
	 */
	public Date getHireDate() {
		return hireDate;
	}

	/**
	 * @param hireDate the hireDate to set
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	/**
	 * @return the hireTime
	 */
	public String getHireTime() {
		return hireTime;
	}

	/**
	 * @param hireTime the hireTime to set
	 */
	public void setHireTime(String hireTime) {
		this.hireTime = hireTime;
	}

	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the releaseTime
	 */
	public String getReleaseTime() {
		return releaseTime;
	}

	/**
	 * @param releaseTime the releaseTime to set
	 */
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
