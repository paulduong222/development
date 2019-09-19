package gov.nwcg.isuite.core.domain.xml;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswReport", table = "isw_report")
public class IswReport {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_REPORT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "DateGenerated", sqlname="DATE_GENERATED", type="DATE")
	private Date dateGenerated;
	
	@XmlTransferField(name = "DateRequested", sqlname="DATE_REQUESTED", type="DATE")
	private Date dateRequested;
	
	@XmlTransferField(name = "ErrorDesc", sqlname="ERROR_DESC", type="STRING")
	private String errorDesc;
	
	@XmlTransferField(name = "FileName", sqlname="FILE_NAME", type="STRING")
	private String fileName;
	
	@XmlTransferField(name = "ReportName", sqlname="REPORT_NAME", type="STRING")
	private String reportName;
	
	@XmlTransferField(name = "ResultCode", sqlname="RESULT_CODE", type="STRING")
	private String resultCode;
	
	public IswReport() {	
	}

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
	 * @return the dateGenerated
	 */
	public Date getDateGenerated() {
		return dateGenerated;
	}

	/**
	 * @param dateGenerated the dateGenerated to set
	 */
	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	/**
	 * @return the dateRequested
	 */
	public Date getDateRequested() {
		return dateRequested;
	}

	/**
	 * @param dateRequested the dateRequested to set
	 */
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	

}
