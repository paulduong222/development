package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ContractorNameHistory extends Persistable {
	
	/**
	 * Returns the contractor
	 * 
	 * @return
	 * 		the contractor to return
	 */
	public Contractor getContractor();
	
	/**
	 * Sets the contractor
	 * 
	 * @param contractor
	 * 			the contractor to set
	 */
	public void setContractor(Contractor contractor);
	
	/**
	 * Returns the contractorId
	 * 
	 * @return
	 * 		the contractorId to return
	 */
	public Long getContractorId();
	
	/**
	 * Sets the contractorId
	 * 
	 * @param contractorId
	 * 			the contractorId to set
	 */
	public void setContractorId(Long contractorId);
	
	/**
	 * Returns the reasonText
	 * 
	 * @return
	 * 		the reastonText to return
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
	 * Returns the newContractorName
	 * 
	 * @return
	 * 		the newContractorName to return
	 */
	public String getNewContractorName();
	
	/**
	 * Sets the newContractorName
	 * 
	 * @param newContractorName
	 * 			the newContractorName to set
	 */
	public void setNewContractorName(String newContractorName);
	
	/**
	 * Returns the oldContractorName
	 * 
	 * @return
	 * 		the oldContractorName to return
	 */
	public String getOldContractorName();
	
	/**
	 * Sets the oldContractorName
	 * 
	 * @param oldContractorName
	 * 			the oldContractorName to set
	 */
	public void setOldContractorName(String oldContractorName);

}
