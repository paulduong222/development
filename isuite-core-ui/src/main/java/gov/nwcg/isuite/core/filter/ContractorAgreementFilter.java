package gov.nwcg.isuite.core.filter;
import java.util.Date;
import gov.nwcg.isuite.framework.core.filter.Filter;

public interface ContractorAgreementFilter extends Filter {
	
	/**
	 * Returns the groupByContractorAgreement.
	 *
	 * @return 
	 *		the groupByContractorAgreement to return
	 */
	public Boolean getGroupByContractorAgreement();


	/**
	 * Sets the groupByContractorAgreement.
	 *
	 * @param groupByContractorAgreement 
	 *			the groupByContractorAgreement to set
	 */
	public void setGroupByContractorAgreement(Boolean groupByContractorAgreement);


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
	public Date getStartDate() ;
	
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
	public String getPointOfHire() ;

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire);
	
	
	/**
	 * Returns the adminOfficeId.
	 *
	 * @return 
	 *		the adminOfficeId to return
	 */
	public Long getAdminOfficeId() ;

	/**
	 * Sets the adminOfficeId.
	 *
	 * @param adminOfficeId 
	 *			the adminOfficeId to set
	 */
	public void setAdminOfficeId(Long adminOfficeId);
	
	/**
	 * Returns the contractorId.
	 *
	 * @return 
	 *		the contractorId to return
	 */
	public Long getContractorId() ;

	/**
	 * Sets the contractorId.
	 *
	 * @param contractorId 
	 *			the contractorId to set
	 */
	public void setContractorId(Long contractorId);
	
	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate();

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);
	
	/**
	 * @return deletable
	 */
	public Boolean getDeletable();

	/**
	 * @param deletable
	 */
	public void setDeletable(Boolean deletable);
	
	/**
	 * @return the adminOffice
	 */
	public String getAdminOffice();
	
	/**
	 * @param adminOffice
	 */
	public void setAdminOffice(String adminOffice);
	
	/**
	 * @return the crypticDateFilterCodeStartDate
	 */
	public String getCrypticDateFilterCodeStartDate();

	/**
	 * @param crypticDateFilterCodeStartDate the crypticDateFilterCodeStartDate to set
	 */
	public void setCrypticDateFilterCodeStartDate(String crypticDateFilterCodeStartDate); 

	/**
	 * @return the crypticDateFilterCodeEndDate
	 */
	public String getCrypticDateFilterCodeEndDate();

	/**
	 * @param crypticDateFilterCodeEndDate the crypticDateFilterCodeEndDate to set
	 */
	public void setCrypticDateFilterCodeEndDate(String crypticDateFilterCodeEndDate);

	
}
