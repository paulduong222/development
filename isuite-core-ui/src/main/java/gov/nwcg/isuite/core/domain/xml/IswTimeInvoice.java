package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswTimeInvoice", table = "isw_time_invoice")
public class IswTimeInvoice {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TIME_INVOICE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "InvoiceNumber", sqlname = "invoice_number", type="STRING")
	private String invoiceNumber;

	@XmlTransferField(name = "FirstIncludeDate", sqlname = "first_include_date", type="DATE")
	private Date firstIncludeDate;

	@XmlTransferField(name = "LastIncludeDate", sqlname = "last_include_date", type="DATE")
	private Date lastIncludeDate;

	@XmlTransferField(name = "DeletedDate", sqlname = "deleted_date", type="DATE")
	private Date deletedDate;

	@XmlTransferField(name = "DeletedReason", sqlname = "deleted_reason", type="STRING")
	private String deletedReason;

	@XmlTransferField(name = "IsDraft", sqlname = "is_draft", type="BOOLEAN")
	private Boolean isDraft;

	@XmlTransferField(name = "IsDuplicate", sqlname = "is_duplicate", type="BOOLEAN")
	private Boolean isDuplicate;

	@XmlTransferField(name = "IsFinal", sqlname = "is_final", type="BOOLEAN")
	private Boolean isFinal;

	@XmlTransferField(name = "IsInvoiceAdjust", sqlname = "is_invoice_adjust", type="BOOLEAN")
	private Boolean isInvoiceAdjust;

	@XmlTransferField(name = "IsInvoiceOnly", sqlname = "is_invoice_only", type="BOOLEAN")
	private Boolean isInvoiceOnly;

	@XmlTransferField(name = "IsAdjustOnly", sqlname = "is_adjust_only", type="BOOLEAN")
	private Boolean isAdjustOnly;

	@XmlTransferField(name = "Exported", sqlname = "is_exported", type="STRING")
	private String exported;

	@XmlTransferField(name = "ExportDate", sqlname = "export_date", type="DATE")
	private Date exportDate;

	@XmlTransferField(name = "InvoiceReport", type="COMPLEX",target=IswReport.class
			,lookupname="Id", sourcename="InvoiceReportId")
	private IswReport invoiceReport;

	@XmlTransferField(name = "InvoiceReportId", sqlname="INVOICE_REPORT_ID", type="LONG"
			,derived=true, derivedfield="InvoiceReport")
	private Long invoiceReportId;
	
	@XmlTransferField(name = "AdjustmentReport", type="COMPLEX",target=IswReport.class
			,lookupname="Id", sourcename="AdjustmentReportId")
	private IswReport adjustmentReport;

	@XmlTransferField(name = "AdjustmentReportId", sqlname="ADJUSTMENT_REPORT_ID", type="LONG"
			,derived=true, derivedfield="AdjustmentReport")
	private Long adjustmentReportId;

	@XmlTransferField(name = "ResourceInvoice", type="COMPLEX", target=IswResourceInvoice.class
			, lookupname="TimeInvoiceId", sourcename="Id"
			, cascade=true)
	private IswResourceInvoice resourceInvoice;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceReport
	 */
	public IswReport getInvoiceReport() {
		return invoiceReport;
	}

	/**
	 * @param invoiceReport the invoiceReport to set
	 */
	public void setInvoiceReport(IswReport invoiceReport) {
		this.invoiceReport = invoiceReport;
	}

	/**
	 * @return the invoiceReportId
	 */
	public Long getInvoiceReportId() {
		return invoiceReportId;
	}

	/**
	 * @param invoiceReportId the invoiceReportId to set
	 */
	public void setInvoiceReportId(Long invoiceReportId) {
		this.invoiceReportId = invoiceReportId;
	}

	/**
	 * @return the adjustmentReport
	 */
	public IswReport getAdjustmentReport() {
		return adjustmentReport;
	}

	/**
	 * @param adjustmentReport the adjustmentReport to set
	 */
	public void setAdjustmentReport(IswReport adjustmentReport) {
		this.adjustmentReport = adjustmentReport;
	}

	/**
	 * @return the adjustmentReportId
	 */
	public Long getAdjustmentReportId() {
		return adjustmentReportId;
	}

	/**
	 * @param adjustmentReportId the adjustmentReportId to set
	 */
	public void setAdjustmentReportId(Long adjustmentReportId) {
		this.adjustmentReportId = adjustmentReportId;
	}

	/**
	 * @return the firstIncludeDate
	 */
	public Date getFirstIncludeDate() {
		return firstIncludeDate;
	}

	/**
	 * @param firstIncludeDate the firstIncludeDate to set
	 */
	public void setFirstIncludeDate(Date firstIncludeDate) {
		this.firstIncludeDate = firstIncludeDate;
	}

	/**
	 * @return the lastIncludeDate
	 */
	public Date getLastIncludeDate() {
		return lastIncludeDate;
	}

	/**
	 * @param lastIncludeDate the lastIncludeDate to set
	 */
	public void setLastIncludeDate(Date lastIncludeDate) {
		this.lastIncludeDate = lastIncludeDate;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the deletedReason
	 */
	public String getDeletedReason() {
		return deletedReason;
	}

	/**
	 * @param deletedReason the deletedReason to set
	 */
	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
	}

	/**
	 * @return the isDraft
	 */
	public Boolean getIsDraft() {
		return isDraft;
	}

	/**
	 * @param isDraft the isDraft to set
	 */
	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	/**
	 * @return the isDuplicate
	 */
	public Boolean getIsDuplicate() {
		return isDuplicate;
	}

	/**
	 * @param isDuplicate the isDuplicate to set
	 */
	public void setIsDuplicate(Boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * @return the isFinal
	 */
	public Boolean getIsFinal() {
		return isFinal;
	}

	/**
	 * @param isFinal the isFinal to set
	 */
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * @return the isInvoiceAdjust
	 */
	public Boolean getIsInvoiceAdjust() {
		return isInvoiceAdjust;
	}

	/**
	 * @param isInvoiceAdjust the isInvoiceAdjust to set
	 */
	public void setIsInvoiceAdjust(Boolean isInvoiceAdjust) {
		this.isInvoiceAdjust = isInvoiceAdjust;
	}

	/**
	 * @return the isInvoiceOnly
	 */
	public Boolean getIsInvoiceOnly() {
		return isInvoiceOnly;
	}

	/**
	 * @param isInvoiceOnly the isInvoiceOnly to set
	 */
	public void setIsInvoiceOnly(Boolean isInvoiceOnly) {
		this.isInvoiceOnly = isInvoiceOnly;
	}

	/**
	 * @return the isAdjustOnly
	 */
	public Boolean getIsAdjustOnly() {
		return isAdjustOnly;
	}

	/**
	 * @param isAdjustOnly the isAdjustOnly to set
	 */
	public void setIsAdjustOnly(Boolean isAdjustOnly) {
		this.isAdjustOnly = isAdjustOnly;
	}

	/**
	 * @return the exported
	 */
	public String getExported() {
		return exported;
	}

	/**
	 * @param exported the exported to set
	 */
	public void setExported(String exported) {
		this.exported = exported;
	}

	/**
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the resourceInvoice
	 */
	public IswResourceInvoice getResourceInvoice() {
		return resourceInvoice;
	}

	/**
	 * @param resourceInvoice the resourceInvoice to set
	 */
	public void setResourceInvoice(IswResourceInvoice resourceInvoice) {
		this.resourceInvoice = resourceInvoice;
	}


}
