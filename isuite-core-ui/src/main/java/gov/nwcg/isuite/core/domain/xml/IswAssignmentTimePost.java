package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswAssignmentTimePost", table="isw_assign_time_post")
public class IswAssignmentTimePost {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGN_TIME_POST", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AssignmentTimeId", sqlname="ASSIGNMENT_TIME_ID", type="LONG",updateable=false)
	private Long assignmentTimeId;

	@XmlTransferField(name = "RateClassRateTransferableIdentity", alias="rcrti", type="STRING"
					, lookupname="TransferableIdentity", sourcename="RateClassRateId"
					, disjoined=true, disjoinedtable="iswl_rate_class_rate", disjoinedfield="transferable_identity",disjoinedsource="RATE_CLASS_RATE_ID")
	private String rateClassRateTransferableIdentity;
	
	@XmlTransferField(name = "RateClassRateId", sqlname="RATE_CLASS_RATE_ID", type="LONG"
						,derived=true,derivedfield="RateClassRateTransferableIdentity")
	private Long rateClassRateId;
	
	@XmlTransferField(name = "IncidentAccountCode", type="COMPLEX", target=IswIncidentAccountCode.class
						,lookupname="Id",sourcename="IncidentAccountCodeId")
	private IswIncidentAccountCode incidentAccountCode;
	
	@XmlTransferField(name = "IncidentAccountCodeId", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="LONG"
						,derived=true,derivedfield="IncidentAccountCode")
	private Long incidentAccountCodeId;
    
	@XmlTransferField(name = "KindTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="KindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname = "KIND_ID", type = "LONG"
						, derived = true, derivedfield = "KindTransferableIdentity")
	private Long kindId;

	@XmlTransferField(name = "RefContrRateTransferableIdentity", alias="refcrti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="RefContractorRateId"
						, disjoined=true, disjoinedtable="isw_contractor_rate", disjoinedfield="transferable_identity",disjoinedsource="REF_CONTRACTOR_RATE_ID")
	private String refContrRateTransferableIdentity;

	@XmlTransferField(name = "RefContractorRateId", sqlname="REF_CONTRACTOR_RATE_ID", type="LONG"
						,derived=true,derivedfield="RefContrRateTransferableIdentity")
	private Long refContractorRateId;
	
	@XmlTransferField(name = "SpecialPayTransferableIdentity", alias="spti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="SpecialPayId"
						, disjoined=true, disjoinedtable="iswl_special_pay", disjoinedfield="transferable_identity",disjoinedsource="SPECIAL_PAY_ID")
	private String specialPay;
	
	@XmlTransferField(name = "SpecialPayId", sqlname="SPECIAL_PAY_ID", type="LONG"
						,derived=true,derivedfield="SpecialPayTransferableIdentity")
	private Long specialPayId;

	@XmlTransferField(name = "EmploymentType", sqlname="EMPLOYMENT_TYPE", type="STRING")
    private String employmentType;
    
	@XmlTransferField(name = "PostStartDate", sqlname="POST_START_DATE", type="DATE")
	private Date postStartDate;
	
	@XmlTransferField(name = "PostStopDate", sqlname="POST_STOP_DATE", type="DATE")
	private Date postStopDate;
	
	@XmlTransferField(name = "OtherRate", sqlname="OTHER_RATE", type="BIGDECIMAL")
	private BigDecimal otherRate;
	
	@XmlTransferField(name = "RateType", sqlname="RATE_TYPE", type="STRING")
	private String rateType;
	
	@XmlTransferField(name = "UnitOfMeasure", sqlname="UNIT_OF_MEASURE", type="STRING")
	private String unitOfMeasure;

	@XmlTransferField(name = "RateAmount", sqlname="RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal rateAmount;
	
	@XmlTransferField(name = "GuaranteeAmount", sqlname="GUARANTEE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal guaranteeAmount;
	
	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;
	
	@XmlTransferField(name = "IsHalfRate", sqlname="IS_HALF_RATE", type="BOOLEAN")
	private Boolean isHalfRate;
	
	@XmlTransferField(name = "Quantity", sqlname="QUANTITY", type="BIGDECIMAL")
	private BigDecimal quantity;
	
	@XmlTransferField(name = "AssignTimePostInvoice", type = "COMPLEX", target=IswAssignmentTimePostInvoice.class
			,lookupname="AssignTimePostId", sourcename="Id"
			, cascade=true)
	private Collection<IswAssignmentTimePostInvoice> assignTimePostInvoices = new ArrayList<IswAssignmentTimePostInvoice>();
	
	@XmlTransferField(name = "Training", sqlname="IS_TRAINING", type="BOOLEAN")
	private Boolean training;

	@XmlTransferField(name = "ReturnTravelStartOnly", sqlname="RTN_TRAVEL_START_ONLY", type="BOOLEAN")
	private Boolean returnTravelStartOnly;
	
	@XmlTransferField(name = "PrimaryPosting", sqlname="IS_PRIMARY_POSTING", type="BOOLEAN")
	private Boolean primaryPosting;

	@XmlTransferField(name = "SpecialPosting", sqlname="IS_SPECIAL_POSTING", type="BOOLEAN")
	private Boolean specialPosting;

	@XmlTransferField(name = "GuaranteePosting", sqlname="IS_GUARANTEE_POSTING", type="BOOLEAN")
	private Boolean guaranteePosting;

	@XmlTransferField(name = "InvoicedAmount", sqlname="INVOICED_AMOUNT", type="BIGDECIMAL")
	private BigDecimal invoicedAmount;
	
	@XmlTransferField(name = "ContractorPostType", sqlname="CONTRACTOR_POST_TYPE", type="STRING")
	private String contractorPostType;

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
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}

	/**
	 * @param assignmentTimeId the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}

	/**
	 * @return the rateClassRateTransferableIdentity
	 */
	public String getRateClassRateTransferableIdentity() {
		return rateClassRateTransferableIdentity;
	}

	/**
	 * @param rateClassRateTransferableIdentity the rateClassRateTransferableIdentity to set
	 */
	public void setRateClassRateTransferableIdentity(
			String rateClassRateTransferableIdentity) {
		this.rateClassRateTransferableIdentity = rateClassRateTransferableIdentity;
	}

	/**
	 * @return the rateClassRateId
	 */
	public Long getRateClassRateId() {
		return rateClassRateId;
	}

	/**
	 * @param rateClassRateId the rateClassRateId to set
	 */
	public void setRateClassRateId(Long rateClassRateId) {
		this.rateClassRateId = rateClassRateId;
	}

	/**
	 * @return the incidentAccountCode
	 */
	public IswIncidentAccountCode getIncidentAccountCode() {
		return incidentAccountCode;
	}

	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IswIncidentAccountCode incidentAccountCode) {
		this.incidentAccountCode = incidentAccountCode;
	}

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}

	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}

	/**
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the refContrRateTransferableIdentity
	 */
	public String getRefContrRateTransferableIdentity() {
		return refContrRateTransferableIdentity;
	}

	/**
	 * @param refContrRateTransferableIdentity the refContrRateTransferableIdentity to set
	 */
	public void setRefContrRateTransferableIdentity(
			String refContrRateTransferableIdentity) {
		this.refContrRateTransferableIdentity = refContrRateTransferableIdentity;
	}

	/**
	 * @return the refContractorRateId
	 */
	public Long getRefContractorRateId() {
		return refContractorRateId;
	}

	/**
	 * @param refContractorRateId the refContractorRateId to set
	 */
	public void setRefContractorRateId(Long refContractorRateId) {
		this.refContractorRateId = refContractorRateId;
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

	/**
	 * @return the specialPayId
	 */
	public Long getSpecialPayId() {
		return specialPayId;
	}

	/**
	 * @param specialPayId the specialPayId to set
	 */
	public void setSpecialPayId(Long specialPayId) {
		this.specialPayId = specialPayId;
	}

	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the postStartDate
	 */
	public Date getPostStartDate() {
		return postStartDate;
	}

	/**
	 * @param postStartDate the postStartDate to set
	 */
	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}

	/**
	 * @return the postStopDate
	 */
	public Date getPostStopDate() {
		return postStopDate;
	}

	/**
	 * @param postStopDate the postStopDate to set
	 */
	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}

	/**
	 * @return the otherRate
	 */
	public BigDecimal getOtherRate() {
		return otherRate;
	}

	/**
	 * @param otherRate the otherRate to set
	 */
	public void setOtherRate(BigDecimal otherRate) {
		this.otherRate = otherRate;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
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
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isHalfRate
	 */
	public Boolean getIsHalfRate() {
		return isHalfRate;
	}

	/**
	 * @param isHalfRate the isHalfRate to set
	 */
	public void setIsHalfRate(Boolean isHalfRate) {
		this.isHalfRate = isHalfRate;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the training
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * @return the returnTravelStartOnly
	 */
	public Boolean getReturnTravelStartOnly() {
		return returnTravelStartOnly;
	}

	/**
	 * @param returnTravelStartOnly the returnTravelStartOnly to set
	 */
	public void setReturnTravelStartOnly(Boolean returnTravelStartOnly) {
		this.returnTravelStartOnly = returnTravelStartOnly;
	}

	/**
	 * @return the primaryPosting
	 */
	public Boolean getPrimaryPosting() {
		return primaryPosting;
	}

	/**
	 * @param primaryPosting the primaryPosting to set
	 */
	public void setPrimaryPosting(Boolean primaryPosting) {
		this.primaryPosting = primaryPosting;
	}

	/**
	 * @return the specialPosting
	 */
	public Boolean getSpecialPosting() {
		return specialPosting;
	}

	/**
	 * @param specialPosting the specialPosting to set
	 */
	public void setSpecialPosting(Boolean specialPosting) {
		this.specialPosting = specialPosting;
	}

	/**
	 * @return the guaranteePosting
	 */
	public Boolean getGuaranteePosting() {
		return guaranteePosting;
	}

	/**
	 * @param guaranteePosting the guaranteePosting to set
	 */
	public void setGuaranteePosting(Boolean guaranteePosting) {
		this.guaranteePosting = guaranteePosting;
	}

	/**
	 * @return the invoicedAmount
	 */
	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	/**
	 * @param invoicedAmount the invoicedAmount to set
	 */
	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	/**
	 * @return the contractorPostType
	 */
	public String getContractorPostType() {
		return contractorPostType;
	}

	/**
	 * @param contractorPostType the contractorPostType to set
	 */
	public void setContractorPostType(String contractorPostType) {
		this.contractorPostType = contractorPostType;
	}

	/**
	 * @return the assignTimePostInvoices
	 */
	public Collection<IswAssignmentTimePostInvoice> getAssignTimePostInvoices() {
		return assignTimePostInvoices;
	}

	/**
	 * @param assignTimePostInvoices the assignTimePostInvoices to set
	 */
	public void setAssignTimePostInvoices(
			Collection<IswAssignmentTimePostInvoice> assignTimePostInvoices) {
		this.assignTimePostInvoices = assignTimePostInvoices;
	}

	

}