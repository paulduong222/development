package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswCostData", table = "isw_cost_data")
public class IswCostData {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_DATA", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AccrualCodeTransferableIdentity", alias="acti", type="STRING"
					, lookupname="TransferableIdentity", sourcename="AccrualCodeId"
					, disjoined=true, disjoinedtable="iswl_accrual_code", disjoinedfield="transferable_identity",disjoinedsource="ACCRUAL_CODE_ID")
	private String accrualCodeTransferableIdentity;

	@XmlTransferField(name = "AccrualCodeId", sqlname="ACCRUAL_CODE_ID", type="LONG"
						,derived=true, derivedfield="AccrualCodeTransferableIdentity")
	private Long accrualCodeId;
	
	@XmlTransferField(name = "PaymentAgencyTransferableIdentity", alias="pmtagti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="PaymentAgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="PAYMENT_AGENCY_ID")
	private String paymentAgencyTransferableIdentity;

	@XmlTransferField(name = "PaymentAgencyId", sqlname="PAYMENT_AGENCY_ID", type="LONG"
						,derived=true, derivedfield="PaymentAgencyTransferableIdentity")
	private Long paymentAgencyId;
	
	@XmlTransferField(name = "AssignDate", sqlname = "ASSIGN_DATE", type = "DATE")
	private Date assignDate;

	@XmlTransferField(name = "AccrualLocked", sqlname = "IS_ACCRUAL_LOCKED", type = "BOOLEAN")
	private Boolean accrualLocked;

	@XmlTransferField(name = "UseAccrualsOnly", sqlname = "IS_USE_ACCRUALS_ONLY", type = "BOOLEAN")
	private Boolean useAccrualsOnly;

	@XmlTransferField(name = "GenerateCosts", sqlname = "IS_GENERATE_COSTS", type = "BOOLEAN")
	private Boolean generateCosts;

	@XmlTransferField(name = "GenerateCostsSys", sqlname = "IS_GENERATE_COSTS_SYS", type = "STRING", defaultvalue="N")
	private String generateCostsSys;

	@XmlTransferField(name = "CostRemarks", sqlname = "COST_REMARKS", type = "STRING")
	private String costRemarks;

	@XmlTransferField(name = "CostOther1", sqlname = "COST_OTHER_1", type = "STRING")
	private String costOther1;

	@XmlTransferField(name = "CostOther2", sqlname = "COST_OTHER_2", type = "STRING")
	private String costOther2;

	@XmlTransferField(name = "CostOther3", sqlname = "COST_OTHER_3", type = "STRING")
	private String costOther3;

	@XmlTransferField(name = "DefCostGroupTransferableIdentity", alias="defcgti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="DefaultCostGroupId"
		, disjoined=true, disjoinedtable="isw_cost_group", disjoinedfield="transferable_identity",disjoinedsource="DEFAULT_COST_GROUP_ID")
	private String defCostGroupTransferableIdentity;
	
	@XmlTransferField(name = "DefaultCostGroupId", sqlname = "DEFAULT_COST_GROUP_ID", type = "LONG"
							, derived = true, derivedfield = "DefCostGroupTransferableIdentity")
	private Long defaultCostGroupId;

	@XmlTransferField(name = "DefIncidentShiftTransferableIdentity", alias="defshiftti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="DefIncidentShiftId"
						, disjoined=true, disjoinedtable="isw_incident_shift", disjoinedfield="transferable_identity",disjoinedsource="DEFAULT_INC_SHIFT_ID")
	private String defIncidentShiftTransferableIdentity;

	@XmlTransferField(name = "DefIncidentShiftId", sqlname = "DEFAULT_INC_SHIFT_ID", type = "LONG"
						, derived = true, derivedfield = "DefIncidentShiftTransferableIdentity")
	private Long defIncidentShiftId;

	public IswCostData() {
		super();
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
	 * @return the accrualCodeTransferableIdentity
	 */
	public String getAccrualCodeTransferableIdentity() {
		return accrualCodeTransferableIdentity;
	}

	/**
	 * @param accrualCodeTransferableIdentity the accrualCodeTransferableIdentity to set
	 */
	public void setAccrualCodeTransferableIdentity(
			String accrualCodeTransferableIdentity) {
		this.accrualCodeTransferableIdentity = accrualCodeTransferableIdentity;
	}

	/**
	 * @return the accrualCodeId
	 */
	public Long getAccrualCodeId() {
		return accrualCodeId;
	}

	/**
	 * @param accrualCodeId the accrualCodeId to set
	 */
	public void setAccrualCodeId(Long accrualCodeId) {
		this.accrualCodeId = accrualCodeId;
	}

	/**
	 * @return the paymentAgencyTransferableIdentity
	 */
	public String getPaymentAgencyTransferableIdentity() {
		return paymentAgencyTransferableIdentity;
	}

	/**
	 * @param paymentAgencyTransferableIdentity the paymentAgencyTransferableIdentity to set
	 */
	public void setPaymentAgencyTransferableIdentity(
			String paymentAgencyTransferableIdentity) {
		this.paymentAgencyTransferableIdentity = paymentAgencyTransferableIdentity;
	}

	/**
	 * @return the paymentAgencyId
	 */
	public Long getPaymentAgencyId() {
		return paymentAgencyId;
	}

	/**
	 * @param paymentAgencyId the paymentAgencyId to set
	 */
	public void setPaymentAgencyId(Long paymentAgencyId) {
		this.paymentAgencyId = paymentAgencyId;
	}

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return the accrualLocked
	 */
	public Boolean getAccrualLocked() {
		return accrualLocked;
	}

	/**
	 * @param accrualLocked the accrualLocked to set
	 */
	public void setAccrualLocked(Boolean accrualLocked) {
		this.accrualLocked = accrualLocked;
	}

	/**
	 * @return the useAccrualsOnly
	 */
	public Boolean getUseAccrualsOnly() {
		return useAccrualsOnly;
	}

	/**
	 * @param useAccrualsOnly the useAccrualsOnly to set
	 */
	public void setUseAccrualsOnly(Boolean useAccrualsOnly) {
		this.useAccrualsOnly = useAccrualsOnly;
	}

	/**
	 * @return the generateCosts
	 */
	public Boolean getGenerateCosts() {
		return generateCosts;
	}

	/**
	 * @param generateCosts the generateCosts to set
	 */
	public void setGenerateCosts(Boolean generateCosts) {
		this.generateCosts = generateCosts;
	}

	/**
	 * @return the generateCostsSys
	 */
	public String getGenerateCostsSys() {
		return generateCostsSys;
	}

	/**
	 * @param generateCostsSys the generateCostsSys to set
	 */
	public void setGenerateCostsSys(String generateCostsSys) {
		this.generateCostsSys = generateCostsSys;
	}

	/**
	 * @return the costRemarks
	 */
	public String getCostRemarks() {
		return costRemarks;
	}

	/**
	 * @param costRemarks the costRemarks to set
	 */
	public void setCostRemarks(String costRemarks) {
		this.costRemarks = costRemarks;
	}

	/**
	 * @return the costOther1
	 */
	public String getCostOther1() {
		return costOther1;
	}

	/**
	 * @param costOther1 the costOther1 to set
	 */
	public void setCostOther1(String costOther1) {
		this.costOther1 = costOther1;
	}

	/**
	 * @return the costOther2
	 */
	public String getCostOther2() {
		return costOther2;
	}

	/**
	 * @param costOther2 the costOther2 to set
	 */
	public void setCostOther2(String costOther2) {
		this.costOther2 = costOther2;
	}

	/**
	 * @return the costOther3
	 */
	public String getCostOther3() {
		return costOther3;
	}

	/**
	 * @param costOther3 the costOther3 to set
	 */
	public void setCostOther3(String costOther3) {
		this.costOther3 = costOther3;
	}

	/**
	 * @return the defaultCostGroupId
	 */
	public Long getDefaultCostGroupId() {
		return defaultCostGroupId;
	}

	/**
	 * @param defaultCostGroupId the defaultCostGroupId to set
	 */
	public void setDefaultCostGroupId(Long defaultCostGroupId) {
		this.defaultCostGroupId = defaultCostGroupId;
	}

	/**
	 * @return the defIncidentShiftTransferableIdentity
	 */
	public String getDefIncidentShiftTransferableIdentity() {
		return defIncidentShiftTransferableIdentity;
	}

	/**
	 * @param defIncidentShiftTransferableIdentity the defIncidentShiftTransferableIdentity to set
	 */
	public void setDefIncidentShiftTransferableIdentity(
			String defIncidentShiftTransferableIdentity) {
		this.defIncidentShiftTransferableIdentity = defIncidentShiftTransferableIdentity;
	}

	/**
	 * @return the defIncidentShiftId
	 */
	public Long getDefIncidentShiftId() {
		return defIncidentShiftId;
	}

	/**
	 * @param defIncidentShiftId the defIncidentShiftId to set
	 */
	public void setDefIncidentShiftId(Long defIncidentShiftId) {
		this.defIncidentShiftId = defIncidentShiftId;
	}

	/**
	 * @return the defCostGroupTransferableIdentity
	 */
	public String getDefCostGroupTransferableIdentity() {
		return defCostGroupTransferableIdentity;
	}

	/**
	 * @param defCostGroupTransferableIdentity the defCostGroupTransferableIdentity to set
	 */
	public void setDefCostGroupTransferableIdentity(
			String defCostGroupTransferableIdentity) {
		this.defCostGroupTransferableIdentity = defCostGroupTransferableIdentity;
	}


}
