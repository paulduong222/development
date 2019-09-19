package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswAssignmentTime", table = "isw_assignment_time")
public class IswAssignmentTime {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGNMENT_TIME", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AssignmentId", sqlname = "ASSIGNMENT_ID", type="LONG",updateable=false)
	private Long assignmentId;

	@XmlTransferField(name = "EmploymentType", sqlname = "EMPLOYMENT_TYPE", type="STRING",nullwhenempty=true)
	private String employmentType;

	@XmlTransferField(name = "MailingAddress", type="COMPLEX", target=IswAddress.class
						, lookupname="Id", sourcename="MailingAddressId")
	private IswAddress mailingAddress;

	@XmlTransferField(name = "MailingAddressId", sqlname = "MAILING_ADDRESS_ID", type="LONG"
						,derived=true,derivedfield="MailingAddress")
	private Long mailingAddressId;

	@XmlTransferField(name = "Phone", sqlname = "PHONE", type="STRING")
	private String phone;

	@XmlTransferField(name = "Fax", sqlname = "FAX", type="STRING")
	private String fax;

	@XmlTransferField(name = "OfRemarks", sqlname = "OF_REMARKS", type="STRING")
	private String ofRemarks;

	@XmlTransferField(name = "OtherDefaultRate", sqlname = "OTHER_DEFAULT_RATE", type = "BIGDECIMAL")
	private BigDecimal otherDefaultRate;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "AdPaymentInfo", type="COMPLEX", target=IswAdPaymentInfo.class
			, lookupname="AssignmentTimeId", sourcename="Id"
				, cascade=true)
	private IswAdPaymentInfo adPaymentInfo;

	@XmlTransferField(name = "ContractorPaymentInfo", type="COMPLEX", target=IswContractorPaymentInfo.class
			, lookupname="AssignmentTimeId", sourcename="Id"
				, cascade=true)
	private IswContractorPaymentInfo contractorPaymentInfo;

	@XmlTransferField(name = "AssignmentTimePost", type="COMPLEX", target=IswAssignmentTimePost.class
			, lookupname="AssignmentTimeId", sourcename="Id"
				, cascade=true)
	private Collection<IswAssignmentTimePost> assignmentTimePosts = new ArrayList<IswAssignmentTimePost>();
	
	public IswAssignmentTime() {

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
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
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
	 * @return the iswMailingAddress
	 */
	public IswAddress getMailingAddress() {
		return mailingAddress;
	}

	/**
	 * @param iswMailingAddress the iswMailingAddress to set
	 */
	public void setMailingAddress(IswAddress iswMailingAddress) {
		this.mailingAddress = iswMailingAddress;
	}

	/**
	 * @return the mailingAddressId
	 */
	public Long getMailingAddressId() {
		return mailingAddressId;
	}

	/**
	 * @param mailingAddressId the mailingAddressId to set
	 */
	public void setMailingAddressId(Long mailingAddressId) {
		this.mailingAddressId = mailingAddressId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the ofRemarks
	 */
	public String getOfRemarks() {
		return ofRemarks;
	}

	/**
	 * @param ofRemarks the ofRemarks to set
	 */
	public void setOfRemarks(String ofRemarks) {
		this.ofRemarks = ofRemarks;
	}

	/**
	 * @return the otherDefaultRate
	 */
	public BigDecimal getOtherDefaultRate() {
		return otherDefaultRate;
	}

	/**
	 * @param otherDefaultRate the otherDefaultRate to set
	 */
	public void setOtherDefaultRate(BigDecimal otherDefaultRate) {
		this.otherDefaultRate = otherDefaultRate;
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
	 * @return the iswAdPaymentInfo
	 */
	public IswAdPaymentInfo getAdPaymentInfo() {
		return adPaymentInfo;
	}

	/**
	 * @param iswAdPaymentInfo the iswAdPaymentInfo to set
	 */
	public void setAdPaymentInfo(IswAdPaymentInfo iswAdPaymentInfo) {
		this.adPaymentInfo = iswAdPaymentInfo;
	}

	/**
	 * @return the iswContractorPaymentInfo
	 */
	public IswContractorPaymentInfo getContractorPaymentInfo() {
		return contractorPaymentInfo;
	}

	/**
	 * @param iswContractorPaymentInfo the iswContractorPaymentInfo to set
	 */
	public void setContractorPaymentInfo(
			IswContractorPaymentInfo iswContractorPaymentInfo) {
		this.contractorPaymentInfo = iswContractorPaymentInfo;
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
	 * @return the assignmentTimePosts
	 */
	public Collection<IswAssignmentTimePost> getAssignmentTimePosts() {
		return assignmentTimePosts;
	}

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(
			Collection<IswAssignmentTimePost> assignmentTimePosts) {
		this.assignmentTimePosts = assignmentTimePosts;
	}

}
