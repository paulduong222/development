package gov.nwcg.isuite.core.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

public interface AssignmentTime extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the assignment
	 */
	public Assignment getAssignment() ;

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(Assignment assignment) ;

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() ;

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) ;

	/**
	 * @return the employmentType
	 */
	public EmploymentTypeEnum getEmploymentType() ;

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(EmploymentTypeEnum employmentType) ;

	/**
	 * @return the mailingAddress
	 */
	public Address getMailingAddress() ;

	/**
	 * @param mailingAddress the mailingAddress to set
	 */
	public void setMailingAddress(Address mailingAddress) ;

	/**
	 * @return the mailingAddressId
	 */
	public Long getMailingAddressId() ;

	/**
	 * @param mailingAddressId the mailingAddressId to set
	 */
	public void setMailingAddressId(Long mailingAddressId) ;

	/**
	 * @return the phone
	 */
	public String getPhone() ;

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) ;

	/**
	 * @return the fax
	 */
	public String getFax() ;

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) ;

	/**
	 * @return the ofRemarks
	 */
	public String getOfRemarks() ;

	/**
	 * @param ofRemarks the ofRemarks to set
	 */
	public void setOfRemarks(String ofRemarks) ;

	/**
	 * @return the otherDefaultRate
	 */
	public BigDecimal getOtherDefaultRate() ;

	/**
	 * @param otherDefaultRate the otherDefaultRate to set
	 */
	public void setOtherDefaultRate(BigDecimal otherDefaultRate) ;

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() ;

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) ;

	/**
	 * @return the adPaymentInfo
	 */
	public AdPaymentInfo getAdPaymentInfo() ;

	/**
	 * @param adPaymentInfo the adPaymentInfo to set
	 */
	public void setAdPaymentInfo(AdPaymentInfo adPaymentInfo) ;

	/**
	 * @return the contractorPaymentInfo
	 */
	public ContractorPaymentInfo getContractorPaymentInfo() ;

	/**
	 * @param contractorPaymentInfos the contractorPaymentInfos to set
	 */
	public void setContractorPaymentInfo(ContractorPaymentInfo contractorPaymentInfo) ;

	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts() ;

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(Collection<AssignmentTimePost> assignmentTimePosts);

	public String getHiringUnitName();

	public void setHiringUnitName(String hiringUnitName);

	public String getHiringPhone();

	public void setHiringPhone(String hiringPhone);

	public String getHiringFax();

	public void setHiringFax(String hiringFax);


}

