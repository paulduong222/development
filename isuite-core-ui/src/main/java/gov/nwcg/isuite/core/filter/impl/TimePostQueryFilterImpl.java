package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

public class TimePostQueryFilterImpl {
	private Long incidentResourceId;
	private Long assignmentTimePostId;
	private Boolean invoiceOnly=false;
	private Date startDate;
	private Date stopDate;
	private String postType;
	private String unitOfMeasure;

	private Long assignmentTimeId;
	
	private String specialPay;
	
	private Boolean includeTime=false;
	private Boolean compareTime=false;
	
	private Boolean onlyPrimary=false;
	private Boolean onlySpecial=false;
	private Boolean onlyGuarantee=false;
	
	private Boolean excludePrimary=false;
	private Boolean excludeSpecial=false;
	private Boolean excludeGuarantee=false;
	private Long excludeAssignmentTimePostId;
	private Long excludeParentAssignmentTimePostId;
	private Boolean excludeInvoiced=false;
	
	private String accountingCode;
	
	public TimePostQueryFilterImpl(){
		
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the assignmentTimePostId
	 */
	public Long getAssignmentTimePostId() {
		return assignmentTimePostId;
	}

	/**
	 * @param assignmentTimePostId the assignmentTimePostId to set
	 */
	public void setAssignmentTimePostId(Long assignmentTimePostId) {
		this.assignmentTimePostId = assignmentTimePostId;
	}

	/**
	 * @return the invoiceOnly
	 */
	public Boolean getInvoiceOnly() {
		return invoiceOnly;
	}

	/**
	 * @param invoiceOnly the invoiceOnly to set
	 */
	public void setInvoiceOnly(Boolean invoiceOnly) {
		this.invoiceOnly = invoiceOnly;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the stopDate
	 */
	public Date getStopDate() {
		return stopDate;
	}

	/**
	 * @param stopDate the stopDate to set
	 */
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}

	/**
	 * @param postType the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the excludePrimary
	 */
	public Boolean getExcludePrimary() {
		return excludePrimary;
	}

	/**
	 * @param excludePrimary the excludePrimary to set
	 */
	public void setExcludePrimary(Boolean excludePrimary) {
		this.excludePrimary = excludePrimary;
	}

	/**
	 * @return the excludeSpecial
	 */
	public Boolean getExcludeSpecial() {
		return excludeSpecial;
	}

	/**
	 * @param excludeSpecial the excludeSpecial to set
	 */
	public void setExcludeSpecial(Boolean excludeSpecial) {
		this.excludeSpecial = excludeSpecial;
	}

	/**
	 * @return the excludeGuarantee
	 */
	public Boolean getExcludeGuarantee() {
		return excludeGuarantee;
	}

	/**
	 * @param excludeGuarantee the excludeGuarantee to set
	 */
	public void setExcludeGuarantee(Boolean excludeGuarantee) {
		this.excludeGuarantee = excludeGuarantee;
	}

	/**
	 * @return the excludeAssignmentTimePostId
	 */
	public Long getExcludeAssignmentTimePostId() {
		return excludeAssignmentTimePostId;
	}

	/**
	 * @param excludeAssignmentTimePostId the excludeAssignmentTimePostId to set
	 */
	public void setExcludeAssignmentTimePostId(Long excludeAssignmentTimePostId) {
		this.excludeAssignmentTimePostId = excludeAssignmentTimePostId;
	}


	/**
	 * @return the onlyPrimary
	 */
	public Boolean getOnlyPrimary() {
		return onlyPrimary;
	}

	/**
	 * @param onlyPrimary the onlyPrimary to set
	 */
	public void setOnlyPrimary(Boolean onlyPrimary) {
		this.onlyPrimary = onlyPrimary;
	}

	/**
	 * @return the onlySpecial
	 */
	public Boolean getOnlySpecial() {
		return onlySpecial;
	}

	/**
	 * @param onlySpecial the onlySpecial to set
	 */
	public void setOnlySpecial(Boolean onlySpecial) {
		this.onlySpecial = onlySpecial;
	}

	/**
	 * @return the onlyGuarantee
	 */
	public Boolean getOnlyGuarantee() {
		return onlyGuarantee;
	}

	/**
	 * @param onlyGuarantee the onlyGuarantee to set
	 */
	public void setOnlyGuarantee(Boolean onlyGuarantee) {
		this.onlyGuarantee = onlyGuarantee;
	}

	/**
	 * @return the includeTime
	 */
	public Boolean getIncludeTime() {
		return includeTime;
	}

	/**
	 * @param includeTime the includeTime to set
	 */
	public void setIncludeTime(Boolean includeTime) {
		this.includeTime = includeTime;
	}

	/**
	 * @return the compareTime
	 */
	public Boolean getCompareTime() {
		return compareTime;
	}

	/**
	 * @param compareTime the compareTime to set
	 */
	public void setCompareTime(Boolean compareTime) {
		this.compareTime = compareTime;
	}

	/**
	 * @return the accountingCode
	 */
	public String getAccountingCode() {
		return accountingCode;
	}

	/**
	 * @param accountingCode the accountingCode to set
	 */
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	/**
	 * @return the excludeInvoiced
	 */
	public Boolean getExcludeInvoiced() {
		return excludeInvoiced;
	}

	/**
	 * @param excludeInvoiced the excludeInvoiced to set
	 */
	public void setExcludeInvoiced(Boolean excludeInvoiced) {
		this.excludeInvoiced = excludeInvoiced;
	}

	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}

	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}

	/**
	 * @return the excludeParentAssignmentTimePostId
	 */
	public Long getExcludeParentAssignmentTimePostId() {
		return excludeParentAssignmentTimePostId;
	}

	/**
	 * @param excludeParentAssignmentTimePostId the excludeParentAssignmentTimePostId to set
	 */
	public void setExcludeParentAssignmentTimePostId(
			Long excludeParentAssignmentTimePostId) {
		this.excludeParentAssignmentTimePostId = excludeParentAssignmentTimePostId;
	}

	/**
	 * @return the specialPay
	 */
	public String getSpecialPay() {
		return specialPay;
	}

	/**
	 * @param specialPay the specialPay to set
	 */
	public void setSpecialPay(String specialPay) {
		this.specialPay = specialPay;
	}


}
