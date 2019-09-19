package gov.nwcg.isuite.framework.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.ReportSelectVo;

import java.util.Date;

public class TimeReportFilter {

	private Date actualReleaseDate;
	private Date actualReleaseTime;
	private String actualReleaseTimeAsString;
	private DateTransferVo actualReleaseDateTransferVo;

	protected Date endDate;
	protected Date startDate;
	private Date firstDateToIncludeOnReport;
	private Date lastDateToIncludeOnReport;

	private Boolean finalInvoice;

	private Boolean printDraftInvoice;
	private Boolean printOriginalInvoice;
	private Boolean printDuplicateOriginalInvoice;

	private Boolean printInvoiceOnly;
	private Boolean printDeductionsAndInvoice;
	private Boolean printItemizedDeductionsOnly;

	private Boolean print;

	private Long incidentResourceId;
	private Long resourceId;
	private String requestNumber;
	private String resourceName;
	private String crewName;
	private String personName;

	private String sortBy;

	private Long incidentId;
	private Long incidentGroupId;
	private String incidentTag;
	private String incidentNumber; 	// To hold incident or incident group number
	private String incidentName;	// To hold incident or incident group name

	private Long timeInvoiceId;
	private String reasonForDelete;
	private String reasonForReprint;
	
	// The following ReportSelectVo objects hold the user selected
	// Resource or Request Number information.
	private ReportSelectVo resourceReportSelectVo;
	private ReportSelectVo requestNumberReportSelectVo;
	
	private boolean excludeDemob = true;
	private Boolean isCrew=false;
	private Boolean isPerson=false;
	
	private boolean exportToExcel = false;
	
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();
	
	private DateTransferVo firstDateToIncludeOnReportVo=new DateTransferVo();
	private DateTransferVo lastDateToIncludeOnReportVo=new DateTransferVo();
	
	public TimeReportFilter() {
	}
	
	public DateTransferVo getFirstDateToIncludeOnReportVo() {
		return firstDateToIncludeOnReportVo;
	}

	public void setFirstDateToIncludeOnReportVo(
			DateTransferVo firstDateToIncludeOnReportVo) {
		this.firstDateToIncludeOnReportVo = firstDateToIncludeOnReportVo;
	}

	public DateTransferVo getLastDateToIncludeOnReportVo() {
		return lastDateToIncludeOnReportVo;
	}

	public void setLastDateToIncludeOnReportVo(
			DateTransferVo lastDateToIncludeOnReportVo) {
		this.lastDateToIncludeOnReportVo = lastDateToIncludeOnReportVo;
	}

	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}

	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	/**
	 * @return the actualReleaseTime
	 */
	public Date getActualReleaseTime() {
		return actualReleaseTime;
	}

	/**
	 * @return the crewName
	 */
	public String getCrewName() {
		return crewName;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the finalInvoice
	 */
	public Boolean getFinalInvoice() {
		return finalInvoice;
	}

	/**
	 * @return the printDeductionsAndInvoice
	 */
	public Boolean getPrintDeductionsAndInvoice() {
		return printDeductionsAndInvoice;
	}

	/**
	 * @return the printDraftInvoice
	 */
	public Boolean getPrintDraftInvoice() {
		return printDraftInvoice;
	}

	/**
	 * @return the printDuplicateOriginalInvoice
	 */
	public Boolean getPrintDuplicateOriginalInvoice() {
		return printDuplicateOriginalInvoice;
	}

	/**
	 * @return the printItemizedDeductionsOnly
	 */
	public Boolean getPrintItemizedDeductionsOnly() {
		return printItemizedDeductionsOnly;
	}

	/**
	 * @return the printOriginalInvoice
	 */
	public Boolean getPrintOriginalInvoice() {
		return printOriginalInvoice;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param actualReleaseDate
	 *            the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}

	/**
	 * @param actualReleaseTime
	 *            the actualReleaseTime to set
	 */
	public void setActualReleaseTime(Date actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

	/**
	 * @param crewName
	 *            the crewName to set
	 */
	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param finalInvoice
	 *            the finalInvoice to set
	 */
	public void setFinalInvoice(Boolean finalInvoice) {
		this.finalInvoice = finalInvoice;
	}

	/**
	 * @param printDeductionsAndInvoice
	 *            the printDeductionsAndInvoice to set
	 */
	public void setPrintDeductionsAndInvoice(Boolean printDeductionsAndInvoice) {
		this.printDeductionsAndInvoice = printDeductionsAndInvoice;
	}

	/**
	 * @param printDraftInvoice
	 *            the printDraftInvoice to set
	 */
	public void setPrintDraftInvoice(Boolean printDraftInvoice) {
		this.printDraftInvoice = printDraftInvoice;
	}

	/**
	 * @param printDuplicateOriginalInvoice
	 *            the printDuplicateOriginalInvoice to set
	 */
	public void setPrintDuplicateOriginalInvoice(Boolean printDuplicateOriginalInvoice) {
		this.printDuplicateOriginalInvoice = printDuplicateOriginalInvoice;
	}

	/**
	 * @param printItemizedDeductionsOnly
	 *            the printItemizedDeductionsOnly to set
	 */
	public void setPrintItemizedDeductionsOnly(Boolean printItemizedDeductionsOnly) {
		this.printItemizedDeductionsOnly = printItemizedDeductionsOnly;
	}

	/**
	 * @param printOriginalInvoice
	 *            the printOriginalInvoice to set
	 */
	public void setPrintOriginalInvoice(Boolean printOriginalInvoice) {
		this.printOriginalInvoice = printOriginalInvoice;
	}

	/**
	 * @param requestNumber
	 *            the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the actualReleaseTimeAsString
	 */
	public String getActualReleaseTimeAsString() {
		return actualReleaseTimeAsString;
	}

	/**
	 * @param actualReleaseTimeAsString
	 *            the actualReleaseTimeAsString to set
	 */
	public void setActualReleaseTimeAsString(String actualReleaseTimeAsString) {
		this.actualReleaseTimeAsString = actualReleaseTimeAsString;
	}

	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @param personName
	 *            the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy
	 *            the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the firstDateToIncludeOnReport
	 */
	public Date getFirstDateToIncludeOnReport() {
		return firstDateToIncludeOnReport;
	}

	/**
	 * @param firstDateToIncludeOnReport
	 *            the firstDateToIncludeOnReport to set
	 */
	public void setFirstDateToIncludeOnReport(Date firstDateToIncludeOnReport) {
		this.firstDateToIncludeOnReport = firstDateToIncludeOnReport;
	}

	/**
	 * @return the lastDateToIncludeOnReport
	 */
	public Date getLastDateToIncludeOnReport() {
		return lastDateToIncludeOnReport;
	}

	/**
	 * @param lastDateToIncludeOnReport
	 *            the lastDateToIncludeOnReport to set
	 */
	public void setLastDateToIncludeOnReport(Date lastDateToIncludeOnReport) {
		this.lastDateToIncludeOnReport = lastDateToIncludeOnReport;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getPrintInvoiceOnly() {
		return printInvoiceOnly;
	}

	/**
	 * 
	 * @param printInvoiceOnly
	 */

	public void setPrintInvoiceOnly(Boolean printInvoiceOnly) {
		this.printInvoiceOnly = printInvoiceOnly;
	}

	/**
	 * If true, Print the invoice; otherwise preview
	 * 
	 * @return
	 */

	public Boolean getPrint() {
		return print;
	}

	/**
	 * If true, Print the invoice; otherwise preview
	 * 
	 * @param print
	 */

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Long getTimeInvoiceId() {
		return timeInvoiceId;
	}

	public void setTimeInvoiceId(Long timeInvoiceId) {
		this.timeInvoiceId = timeInvoiceId;
	}

	public void setReasonForDelete(String reasonForDelete) {
		this.reasonForDelete = reasonForDelete;
	}

	public String getReasonForDelete() {
		return reasonForDelete;
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentTag
	 */
	public String getIncidentTag() {
		return incidentTag;
	}

	/**
	 * @param incidentTag the incidentTag to set
	 */
	public void setIncidentTag(String incidentTag) {
		this.incidentTag = incidentTag;
	}

	public ReportSelectVo getResourceReportSelectVo() {
		return resourceReportSelectVo;
	}

	public void setResourceReportSelectVo(ReportSelectVo resourceReportSelectVo) {
		this.resourceReportSelectVo = resourceReportSelectVo;
	}

	public ReportSelectVo getRequestNumberReportSelectVo() {
		return requestNumberReportSelectVo;
	}

	public void setRequestNumberReportSelectVo(
			ReportSelectVo requestNumberReportSelectVo) {
		this.requestNumberReportSelectVo = requestNumberReportSelectVo;
	}

	public boolean isExcludeDemob() {
		return excludeDemob;
	}

	public void setExcludeDemob(boolean excludeDemob) {
		this.excludeDemob = excludeDemob;
	}

	/**
	 * @return the isCrew
	 */
	public Boolean getIsCrew() {
		return isCrew;
	}

	/**
	 * @param isCrew the isCrew to set
	 */
	public void setIsCrew(Boolean isCrew) {
		this.isCrew = isCrew;
	}

	/**
	 * @return the exportToExcel
	 */
	public boolean isExportToExcel() {
		return exportToExcel;
	}

	/**
	 * @param exportToExcel the exportToExcel to set
	 */
	public void setExportToExcel(boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}

	/**
	 * @return the reasonForReprint
	 */
	public String getReasonForReprint() {
		return reasonForReprint;
	}

	/**
	 * @param reasonForReprint the reasonForReprint to set
	 */
	public void setReasonForReprint(String reasonForReprint) {
		this.reasonForReprint = reasonForReprint;
	}

	/**
	 * @return the isPerson
	 */
	public Boolean getIsPerson() {
		return isPerson;
	}

	/**
	 * @param isPerson the isPerson to set
	 */
	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	/**
	 * @return the actualReleaseDateTransferVo
	 */
	public DateTransferVo getActualReleaseDateTransferVo() {
		return actualReleaseDateTransferVo;
	}

	/**
	 * @param actualReleaseDateTransferVo the actualReleaseDateTransferVo to set
	 */
	public void setActualReleaseDateTransferVo(
			DateTransferVo actualReleaseDateTransferVo) {
		this.actualReleaseDateTransferVo = actualReleaseDateTransferVo;
	}

}
