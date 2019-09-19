package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswCostAccrualExtractRsc", table = "isw_cost_accrual_ext_rsc")
public class IswCostAccrualExtractRsc {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_ACCRUAL_EXT_RSC", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "CostAccrualExtractId", sqlname = "COST_ACCRUAL_EXTRACT_ID", type = "LONG")
	private Long costAccrualExtractId;
	
	@XmlTransferField(name = "IncidentResourceTransferableIdentity", alias="irti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentResourceId"
						, disjoined=true, disjoinedtable="isw_incident_resource", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_RESOURCE_ID")
	private String incidentResourceTransferableIdentity;

	@XmlTransferField(name = "IncidentResourceId", sqlname = "INCIDENT_RESOURCE_ID", type = "LONG"
						, derived = true, derivedfield = "IncidentResourceTransferableIdentity")
	private Long incidentResourceId;

	@XmlTransferField(name = "TotalAmount", sqlname = "TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal totalAmount;

	@XmlTransferField(name = "ChangeAmount", sqlname = "CHANGE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal changeAmount;

	@XmlTransferField(name = "CostAccrualCode", sqlname = "COST_ACCRUAL_CODE", type="STRING")
	private String costAccrualCode;

	@XmlTransferField(name = "AccountCode", sqlname = "ACCOUNT_CODE", type="STRING")
	private String accountCode;

	@XmlTransferField(name = "FiscalYear", sqlname = "FISCAL_YEAR", type="STRING")
	private String fiscalYear;

	@XmlTransferField(name = "DrawDown", sqlname = "DRAW_DOWN", type="STRING")
	private String drawDown;

	@XmlTransferField(name = "AccountCodeTransferableIdentity", alias="acti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="AccountCodeId"
		, disjoined=true, disjoinedtable="isw_account_code", disjoinedfield="transferable_identity",disjoinedsource="ACCOUNT_CODE_ID")
	private String accountCodeTransferableIdentity;

	@XmlTransferField(name = "AccountCodeId", sqlname="ACCOUNT_CODE_ID", type="LONG"
		,derived=true, derivedfield="AccountCodeTransferableIdentity")
	private Long accountCodeId;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswCostAccrualExtractRsc() {
		super();
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
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount
	 *            the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public Long getCostAccrualExtractId() {
		return costAccrualExtractId;
	}

	public void setCostAccrualExtractId(Long costAccrualExtractId) {
		this.costAccrualExtractId = costAccrualExtractId;
	}

	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the costAccrualCode
	 */
	public String getCostAccrualCode() {
		return costAccrualCode;
	}

	/**
	 * @param costAccrualCode
	 *            the costAccrualCode to set
	 */
	public void setCostAccrualCode(String costAccrualCode) {
		this.costAccrualCode = costAccrualCode;
	}

	/**
	 * @param accountCode
	 *            the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param fiscalYear
	 *            the fiscalYear to set
	 */
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return the fiscalYear
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @param drawDown
	 *            the drawDown to set
	 */
	public void setDrawDown(String drawDown) {
		this.drawDown = drawDown;
	}

	/**
	 * @return the drawDown
	 */
	public String getDrawDown() {
		return drawDown;
	}

	/**
	 * @return the incidentResourceTransferableIdentity
	 */
	public String getIncidentResourceTransferableIdentity() {
		return incidentResourceTransferableIdentity;
	}

	/**
	 * @param incidentResourceTransferableIdentity the incidentResourceTransferableIdentity to set
	 */
	public void setIncidentResourceTransferableIdentity(
			String incidentResourceTransferableIdentity) {
		this.incidentResourceTransferableIdentity = incidentResourceTransferableIdentity;
	}

	/**
	 * @return the accountCodeTransferableIdentity
	 */
	public String getAccountCodeTransferableIdentity() {
		return accountCodeTransferableIdentity;
	}

	/**
	 * @param accountCodeTransferableIdentity the accountCodeTransferableIdentity to set
	 */
	public void setAccountCodeTransferableIdentity(
			String accountCodeTransferableIdentity) {
		this.accountCodeTransferableIdentity = accountCodeTransferableIdentity;
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

}
