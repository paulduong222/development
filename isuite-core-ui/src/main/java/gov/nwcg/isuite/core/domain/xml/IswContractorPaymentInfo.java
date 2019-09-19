package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswContractorPaymentInfo", table = "isw_contr_payment_info")
public class IswContractorPaymentInfo {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTR_PAYMENT_INFO", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AssignmentTimeId", sqlname = "ASSIGNMENT_TIME_ID", type = "LONG",updateable=false)
	private Long assignmentTimeId;

	@XmlTransferField(name = "Contractor", type = "COMPLEX", target = IswContractor.class
						, lookupname = "Id", sourcename = "ContractorId")
	private IswContractor contractor;

	@XmlTransferField(name = "ContractorId", sqlname = "CONTRACTOR_ID", type = "LONG"
						, derived = true, derivedfield = "Contractor")
	private Long contractorId;

	@XmlTransferField(name = "VinName", sqlname = "VIN_NAME", type = "STRING")
	private String vinName;

	@XmlTransferField(name = "Desc1", sqlname = "DESC1", type = "STRING")
	private String desc1;

	@XmlTransferField(name = "Desc2", sqlname = "DESC2", type = "STRING")
	private String desc2;

	@XmlTransferField(name = "ContractorAgreement", type = "COMPLEX", target = IswContractorAgreement.class
							, lookupname = "Id", sourcename = "ContractorAgreementId")
	private IswContractorAgreement contractorAgreement;
	
	@XmlTransferField(name = "ContractorAgreementId", sqlname = "CONTRACTOR_AGREEMENT_ID", type = "LONG"
						, derived = true, derivedfield = "ContractorAgreement")
	private Long contractorAgreementId;

	@XmlTransferField(name = "HiredDate", sqlname = "HIRED_DATE", type = "DATE")
	private Date hiredDate;

	@XmlTransferField(name = "PointOfHire", sqlname = "POINT_OF_HIRE", type = "STRING")
	private String pointOfHire;

	@XmlTransferField(name = "Operation", sqlname = "IS_OPERATION", type = "BOOLEAN")
	private Boolean operation;

	@XmlTransferField(name = "Supplies", sqlname = "IS_SUPPLIES", type = "BOOLEAN")
	private Boolean supplies;

	@XmlTransferField(name = "Withdrawn", sqlname = "IS_WITHDRAWN", type = "BOOLEAN")
	private Boolean withdrawn;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "ContractorPayinfoRate", type = "COMPLEX", target=IswContrPayinfoRate.class
						,lookupname="ContractorPayInfoId", sourcename="Id"
						, cascade=true)
	private Collection<IswContrPayinfoRate> contractorPayinfoRates = new ArrayList<IswContrPayinfoRate>();

	public IswContractorPaymentInfo() {

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
	 * @return the contractor
	 */
	public IswContractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(IswContractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * @return the vinName
	 */
	public String getVinName() {
		return vinName;
	}

	/**
	 * @param vinName the vinName to set
	 */
	public void setVinName(String vinName) {
		this.vinName = vinName;
	}

	/**
	 * @return the desc1
	 */
	public String getDesc1() {
		return desc1;
	}

	/**
	 * @param desc1 the desc1 to set
	 */
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	/**
	 * @return the desc2
	 */
	public String getDesc2() {
		return desc2;
	}

	/**
	 * @param desc2 the desc2 to set
	 */
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	/**
	 * @return the contractorAgreement
	 */
	public IswContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(IswContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * @return the hiredDate
	 */
	public Date getHiredDate() {
		return hiredDate;
	}

	/**
	 * @param hiredDate the hiredDate to set
	 */
	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param pointOfHire the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * @return the operation
	 */
	public Boolean getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Boolean operation) {
		this.operation = operation;
	}

	/**
	 * @return the supplies
	 */
	public Boolean getSupplies() {
		return supplies;
	}

	/**
	 * @param supplies the supplies to set
	 */
	public void setSupplies(Boolean supplies) {
		this.supplies = supplies;
	}

	/**
	 * @return the withdrawn
	 */
	public Boolean getWithdrawn() {
		return withdrawn;
	}

	/**
	 * @param withdrawn the withdrawn to set
	 */
	public void setWithdrawn(Boolean withdrawn) {
		this.withdrawn = withdrawn;
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
	 * @return the contractorPayinfoRates
	 */
	public Collection<IswContrPayinfoRate> getContractorPayinfoRates() {
		return contractorPayinfoRates;
	}

	/**
	 * @param contractorPayinfoRates the contractorPayinfoRates to set
	 */
	public void setContractorPayinfoRates(
			Collection<IswContrPayinfoRate> contractorPayinfoRates) {
		this.contractorPayinfoRates = contractorPayinfoRates;
	}


}
