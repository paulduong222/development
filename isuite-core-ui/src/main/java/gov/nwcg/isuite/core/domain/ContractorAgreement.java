package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ContractorAgreement extends Persistable{

	/**
	 * Returns the adminOffice.
	 *
	 * @return 
	 *		the adminOffice to return
	 */
	public AdminOffice getAdminOffice();

	/**
	 * Sets the adminOffice.
	 *
	 * @param adminOffice 
	 *			the adminOffice to set
	 */
	public void setAdminOffice(AdminOffice adminOffice);


	/**
	 * Returns the adminOfficeId.
	 *
	 * @return 
	 *		the adminOfficeId to return
	 */
	public Long getAdminOfficeId();

	/**
	 * Sets the adminOfficeId.
	 *
	 * @param adminOfficeId 
	 *			the adminOfficeId to set
	 */
	public void setAdminOfficeId(Long adminOfficeId);

	/**
	 * Returns the agreementNumber.
	 *
	 * @return 
	 *		the agreementNumber to return
	 */
	public String getAgreementNumber();

	/**
	 * Sets the agreementNumber.
	 *
	 * @param agreementNumber 
	 *			the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber);

	/**
	 * Returns the startDate.
	 *
	 * @return 
	 *		the startDate to return
	 */
	public Date getStartDate();


	/**
	 * Sets the startDate.
	 *
	 * @param startDate 
	 *			the startDate to set
	 */
	public void setStartDate(Date startDate);

	/**
	 * Returns the endDate.
	 *
	 * @return 
	 *		the endDate to return
	 */
	public Date getEndDate();

	/**
	 * Sets the endDate.
	 *
	 * @param endDate 
	 *			the endDate to set
	 */
	public void setEndDate(Date endDate);

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
	 * Returns the contractor.
	 *
	 * @return 
	 *		the contractor to return
	 */
	public Contractor getContractor();

	/**
	 * Sets the contractor.
	 *
	 * @param contractor 
	 *			the contractor to set
	 */
	public void setContractor(Contractor contractor);

	/**
	 * Returns the contractorId.
	 *
	 * @return 
	 *		the contractorId to return
	 */
	public Long getContractorId();

	/**
	 * Sets the contractorId.
	 *
	 * @param contractorId 
	 *			the contractorId to set
	 */
	public void setContractorId(Long contractorId);
	
	/**
	 * Returns the deleted date.
	 * 
	 * @return
	 * 		the date the resource is deleted
	 */
	public Date getDeletedDate();

	/**
	 * Sets the deleted date.
	 * 
	 * @param deletedDate
	 * 		the date the resource is deleted
	 */
	public void setDeletedDate(Date deletedDate);
	
	/**
	 * Sets the enabled status of the contractor agreement.
	 * 
	 * @param enabled
	 */
	public void setEnabled(Boolean enabled);
	
	/**
	 * Returns if the contractor agreement is enabled.
	 * 
	 * @return the enabled status of the contractor agreement
	 */
	public Boolean isEnabled();
	
	/**
	 * Sets time invoices
	 * 
	 * @param timeInvoices
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices);
	
	/**
	 * Returns time invoices
	 * 
	 * @return
	 */
	public Collection<TimeInvoice> getTimeInvoices();
	
	/**
	 * @param contractorAgreementNumberHistories the contractorAgreementNumberHistories to set
	 */
	public void setContractorAgreementNumberHistories(
			Collection<ContractorAgreementNumberHistory> contractorAgreementNumberHistories);

	/**
	 * @return the contractorAgreementNumberHistories
	 */
	public Collection<ContractorAgreementNumberHistory> getContractorAgreementNumberHistories();

	
}
