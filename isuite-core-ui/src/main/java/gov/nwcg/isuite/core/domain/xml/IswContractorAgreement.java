package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswlContractorAgreement", table = "isw_contractor_agreement")
public class IswContractorAgreement {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR_AGREEMENT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AdminOffice", type = "COMPLEX", target = IswAdminOffice.class
						, lookupname = "Id", sourcename = "AdminOfficeId")
	private IswAdminOffice adminOffice;

	@XmlTransferField(name = "AdminOfficeId", sqlname = "ADMIN_OFFICE_ID", type = "LONG"
						, derived = true, derivedfield = "AdminOffice")
	private Long adminOfficeId;

	@XmlTransferField(name = "AgreementNumber", sqlname = "agreement_number", type="STRING")
	private String agreementNumber;

	@XmlTransferField(name = "StartDate", sqlname = "start_date", type="DATE")
	private Date startDate;

	@XmlTransferField(name = "EndDate", sqlname = "end_date", type="DATE")
	private Date endDate;

	@XmlTransferField(name = "PointOfHire", sqlname = "point_of_hire", type="STRING")
	private String pointOfHire;

	@XmlTransferField(name = "Contractor", type = "COMPLEX", target=IswContractor.class
						,lookupname="Id", sourcename="ContractorId")
	private IswContractor contractor;
	
	@XmlTransferField(name = "ContractorId", sqlname = "CONTRACTOR_ID", type = "LONG"
						, derived = true, derivedfield = "Contractor")
	private Long contractorId;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type="DATE")
	private Date deletedDate;

	@XmlTransferField(name = "Enabled", sqlname = "IS_ENABLED", type="BOOLEAN")
	private Boolean enabled;


	public IswContractorAgreement() {

	}

	/**
	 * Returns the id.
	 * 
	 * @return the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the adminOffice.
	 * 
	 * @return the adminOffice to return
	 */
	public IswAdminOffice getAdminOffice() {
		return adminOffice;
	}

	/**
	 * Sets the adminOffice.
	 * 
	 * @param adminOffice
	 *            the adminOffice to set
	 */
	public void setAdminOffice(IswAdminOffice adminOffice) {
		this.adminOffice = adminOffice;
	}

	/**
	 * Returns the adminOfficeId.
	 * 
	 * @return the adminOfficeId to return
	 */
	public Long getAdminOfficeId() {
		return adminOfficeId;
	}

	/**
	 * Sets the adminOfficeId.
	 * 
	 * @param adminOfficeId
	 *            the adminOfficeId to set
	 */
	public void setAdminOfficeId(Long adminOfficeId) {
		this.adminOfficeId = adminOfficeId;
	}

	/**
	 * Returns the agreementNumber.
	 * 
	 * @return the agreementNumber to return
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * Sets the agreementNumber.
	 * 
	 * @param agreementNumber
	 *            the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	/**
	 * Returns the startDate.
	 * 
	 * @return the startDate to return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the startDate.
	 * 
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the endDate.
	 * 
	 * @return the endDate to return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the endDate.
	 * 
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the pointOfHire.
	 * 
	 * @return the pointOfHire to return
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * Sets the pointOfHire.
	 * 
	 * @param pointOfHire
	 *            the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * Returns the deletedDate.
	 * 
	 * @return the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 * 
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
	public Boolean isEnabled() {
		return enabled;
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
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
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

}
