package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlAccrualCode", table = "iswl_accrual_code")
public class IswlAccrualCode {
	
	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_ACCRUAL_CODE", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "RcLineNumber", sqlname = "RC_LINE_NUMBER", type="STRING")
	private String rcLineNumber;

	@XmlTransferField(name = "Reportable", sqlname = "REPORTABLE", type="STRING")
	private String reportable;

	public IswlAccrualCode() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @param standard
	 *            the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the rcLineNumber
	 */
	public String getRcLineNumber() {
		return rcLineNumber;
	}

	/**
	 * @param rcLineNumber
	 *            the rcLineNumber to set
	 */
	public void setRcLineNumber(String rcLineNumber) {
		this.rcLineNumber = rcLineNumber;
	}

	/**
	 * @return the reportable
	 */
	public String getReportable() {
		return reportable;
	}

	/**
	 * @param reportable
	 *            the reportable to set
	 */
	public void setReportable(String reportable) {
		this.reportable = reportable;
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
}
