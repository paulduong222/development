package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentAccountCode", table="isw_incident_account_code")
public class IswIncidentAccountCode  {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_ACCOUNT_CODE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AccountCode", type="COMPLEX", target=IswAccountCode.class
			, lookupname="Id", sourcename="AccountCodeId")
	private IswAccountCode accountCode;

	@XmlTransferField(name = "AccountCodeId", sqlname="ACCOUNT_CODE_ID", type="LONG"
							,derived=true, derivedfield="AccountCode")
	private Long accountCodeId;

	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentId"
						, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
	private String incidentTransferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
						,derived=true,derivedfield="IncidentTransferableIdentity")
	private Long incidentId;

	@XmlTransferField(name = "OverrideAccountCode", type="COMPLEX", target=IswAccountCode.class
			, lookupname="Id", sourcename="OverrideAccountCodeId")
	private IswAccountCode overrideAccountCode;

	@XmlTransferField(name = "OverrideAccountCodeId", sqlname="OVERRIDE_ACCOUNT_CODE_ID", type="LONG"
							,derived=true, derivedfield="OverrideAccountCode")
	private Long overrideAccountCodeId;

	@XmlTransferField(name = "DefaultFlag", sqlname="DEFAULT_FLG", type="BOOLEAN")
	private Boolean defaultFlag;

	@XmlTransferField(name = "AccrualAccountCode", sqlname="ACCRUAL_ACCOUNT_CODE", type="STRING")
	private String accrualAccountCode;

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
	 * @return the accountCode
	 */
	public IswAccountCode getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(IswAccountCode accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountCodeId
	 */
	public Long getAccountCodeId() {
		return accountCodeId;
	}

	/**
	 * @param accountCodeId the accountCodeId to set
	 */
	public void setAccountCodeId(Long accountCodeId) {
		this.accountCodeId = accountCodeId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the overrideAccountCode
	 */
	public IswAccountCode getOverrideAccountCode() {
		return overrideAccountCode;
	}

	/**
	 * @param overrideAccountCode the overrideAccountCode to set
	 */
	public void setOverrideAccountCode(IswAccountCode overrideAccountCode) {
		this.overrideAccountCode = overrideAccountCode;
	}

	/**
	 * @return the overrideAccountCodeId
	 */
	public Long getOverrideAccountCodeId() {
		return overrideAccountCodeId;
	}

	/**
	 * @param overrideAccountCodeId the overrideAccountCodeId to set
	 */
	public void setOverrideAccountCodeId(Long overrideAccountCodeId) {
		this.overrideAccountCodeId = overrideAccountCodeId;
	}

	/**
	 * @return the defaultFlag
	 */
	public Boolean getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * @param defaultFlag the defaultFlag to set
	 */
	public void setDefaultFlag(Boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * @return the accrualAccountCode
	 */
	public String getAccrualAccountCode() {
		return accrualAccountCode;
	}

	/**
	 * @param accrualAccountCode the accrualAccountCode to set
	 */
	public void setAccrualAccountCode(String accrualAccountCode) {
		this.accrualAccountCode = accrualAccountCode;
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
	 * @return the incidentTransferableIdentity
	 */
	public String getIncidentTransferableIdentity() {
		return incidentTransferableIdentity;
	}

	/**
	 * @param incidentTransferableIdentity the incidentTransferableIdentity to set
	 */
	public void setIncidentTransferableIdentity(String incidentTransferableIdentity) {
		this.incidentTransferableIdentity = incidentTransferableIdentity;
	}


}
