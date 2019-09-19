package gov.nwcg.isuite.framework.report.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class TimeInvoiceData {

	protected final String boxChecked = "x";
	protected final String printFinal = "FINAL";
	protected final String printInterim = "INTERIM";
	private Boolean draftInvoice = false;
	private Boolean duplicateOriginalInvoice = false;
	private Boolean finalInvoice = false;
	protected Date postStartDate;
	protected Date postStopDate;
	private Long resourceId;
	private String identificationNumber;
	private String officialNumber;
	private String pointOfHire;
	private String remarks;
	private Collection<String> previousInvoices = new ArrayList<String>();
	private Date reportPrintedDate;

	public TimeInvoiceData() {
	}

	/**
	 * return boxChecked value for the report checkbox if the value is true
	 */
	protected String getBoxChecked(Boolean value) {
		if (value != null && value == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * Return text to indicate if this is a Final invoice or not.
	 * 
	 * @return String InvoiceType
	 */
	public String getInvoiceType() {
		if (finalInvoice)
			return printFinal;
		else
			return printInterim;
	}

	/**
	 * Return text to be put in signature areas and other locations on the OF288
	 * specifying this is a draft document, not an original document.
	 * 
	 * @return String draft
	 */
	public String getDraft() {
		if (draftInvoice)
			return "DRAFT ONLY - NOT FOR PAYMENT";
		else
			return null;
	}

	/**
	 * Return text to be put in signature areas on the invoice specifying this
	 * is an duplicate copy, or draft document.
	 * 
	 * @return String
	 */
	public String getReportType() {
		if (draftInvoice)
			return "DRAFT ONLY - NOT FOR PAYMENT";
		else {
			if (duplicateOriginalInvoice) {
				return "Duplicate of Original";
			} else {
				return "";
			}
		}
	}

	/**
	 * Return text to be put in subtitle header area on the OF288 specifying
	 * this is an original document, duplicate copy, or draft document.
	 * 
	 * @return String original
	 */
	public String getReportTypeHeader() {
		if (draftInvoice)
			return "DRAFT ONLY - NOT FOR PAYMENT";
		else {
			if (duplicateOriginalInvoice) {
				return "Duplicate of Original";
			} else {
				return "Original";
			}
		}
	}

	public String getFinalInvoice() {
		return getBoxChecked(finalInvoice);
	}

	public Boolean isFinalInvoice() {
		return finalInvoice;
	}

	public void setFinalInvoice(Boolean finalInvoice) {
		this.finalInvoice = finalInvoice;
	}

	public String getDraftInvoice() {
		return getBoxChecked(draftInvoice);
	}

	public void setDraftInvoice(Boolean draftInvoice) {
		this.draftInvoice = draftInvoice;
	}

	public void setDuplicateOriginalInvoice(Boolean duplicateOriginalInvoice) {
		this.duplicateOriginalInvoice = duplicateOriginalInvoice;
	}

	public String getPointOfHire() {
		return pointOfHire;
	}

	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPreviousInvoices() {
		String str = "";
		int count = 0;
		for (String pi : previousInvoices) {
			if (str.length() > 0)
				str = str + "  ";
			str = str + pi;
			
		}
		return str;
	}

	public void setPreviousInvoices(Collection<String> previousInvoices) {
		this.previousInvoices = previousInvoices;
	}

	public Date getReportPrintedDate() {
		return reportPrintedDate;
	}

	public void setReportPrintedDate(Date reportPrintedDate) {
		this.reportPrintedDate = reportPrintedDate;
	}

	public abstract Date getPostStartDate();

	public abstract Date getPostStopDate();

	public abstract Collection<? extends TimeInvoiceDetail> getTimeInvoiceDetails();

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getOfficialNumber() {
		return officialNumber;
	}

	public void setOfficialNumber(String officialNumber) {
		this.officialNumber = officialNumber;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
