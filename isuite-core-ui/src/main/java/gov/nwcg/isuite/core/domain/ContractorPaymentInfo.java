package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ContractorPaymentInfo extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime() ;

	/**
	 * @param assignmentTime the assignmentTime to set
	 */
	public void setAssignmentTime(AssignmentTime assignmentTime) ;

	/**
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId(); 

	/**
	 * @param assignmentTimeId the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) ;

	/**
	 * @return the contractor
	 */
	public Contractor getContractor() ;

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) ;

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() ;

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) ;

	/**
	 * @return the vinName
	 */
	public String getVinName() ;

	/**
	 * @param vinName the vinName to set
	 */
	public void setVinName(String vinName) ;

	/**
	 * @return the desc1
	 */
	public String getDesc1() ;

	/**
	 * @param desc1 the desc1 to set
	 */
	public void setDesc1(String desc1) ;

	/**
	 * @return the desc2
	 */
	public String getDesc2() ;

	/**
	 * @param desc2 the desc2 to set
	 */
	public void setDesc2(String desc2) ;

	/**
	 * @return the contractorAgreement
	 */
	public ContractorAgreement getContractorAgreement() ;

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement); 

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() ;

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) ;

	/**
	 * @return the hiredDate
	 */
	public Date getHiredDate() ;

	/**
	 * @param hiredDate the hiredDate to set
	 */
	public void setHiredDate(Date hiredDate) ;

	/**
	 * @return the operation
	 */
	public Boolean getOperation() ;

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Boolean operation) ;

	/**
	 * @return the supplies
	 */
	public Boolean getSupplies() ;

	/**
	 * @param supplies the supplies to set
	 */
	public void setSupplies(Boolean supplies) ;

	/**
	 * @return the withdrawn
	 */
	public Boolean getWithdrawn() ;


	/**
	 * @param withdrawn the withdrawn to set
	 */
	public void setWithdrawn(Boolean withdrawn) ;

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() ;

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);
	
	/**
	 * Returns the pointOfHire.
	 *
	 * @return 
	 *		the pointOfHire to return
	 */
	public String getPointOfHire();

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire);

	/**
	 * @return the contractorRates
	 */
	public Collection<ContractorRate> getContractorRates() ;

	/**
	 * @param contractorRates the contractorRates to set
	 */
	public void setContractorRates(Collection<ContractorRate> contractorRates) ;

}
