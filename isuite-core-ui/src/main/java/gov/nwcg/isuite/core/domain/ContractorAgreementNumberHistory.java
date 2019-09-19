package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ContractorAgreementNumberHistory extends Persistable {
	
	/**
	 * Returns the contractorAgreement
	 * 
	 * @return
	 * 		the contractorAgreement to return
	 */
	public ContractorAgreement getContractorAgreement();
	
	/**
	 * Sets the contractorAgreement
	 * 
	 * @param contractorAgreement
	 * 			the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement);
	
	/**
	 * Returns the contractorAgreementId
	 * 
	 * @return
	 * 		the contractorAgreeementId to return
	 */
	public Long getContractorAgreementId();
	
	/**
	 * Sets the contractorAgreementId
	 * 
	 * @param contractorAgreementId
	 * 			the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId);
	
	/**
	 * Returns the reasonText
	 * 
	 * @return
	 * 		the reasonText to return
	 */
	public String getReasonText();
	
	/**
	 * Sets the reasonText
	 * 
	 * @param reasonText
	 * 			the reasonText to set
	 */
	public void setReasonText(String reasonText);
	
	/**
	 * Returns the newAgreementNumber
	 * 
	 * @return
	 * 		the newAgreementNumber to return
	 */
	public String getNewAgreementNumber();
	
	/**
	 * Sets the newAgreementNumber
	 * 
	 * @param newAgreementNumber
	 * 			the newAgreementNumber to set
	 */
	public void setNewAgreementNumber(String newAgreementNumber);
	
	/**
	 * Returns the oldAgreementNumber
	 * 
	 * @return
	 * 		the oldAgreementNumber to return
	 */
	public String getOldAgreementNumber();
	
	/**
	 * Sets the oldAgreementNumber
	 * 
	 * @param oldAgreementNumber
	 * 			the oldAgreementNumber to set
	 */
	public void setOldAgreementNumber(String oldAgreementNumber);

}
