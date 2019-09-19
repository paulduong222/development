package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentBranch extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @return the branchName
	 */
	public String getBranchName();

	/**
	 * @param branchName
	 */
	public void setBranchName(String branchName);

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder();

	/**
	 * @param displayOrder
	 */
	public void setDisplayOrder(Short displayOrder);
	
	/**
	 * @param iapBranchPosItemCodes the iapBranchPosItemCodes to set
	 */
	public void setIapBranchPosItemCodes(Collection<IapBranchPosItemCode> iapBranchPosItemCodes);
	
	/**
	 * @return the iapBranchPosItemCodes
	 */
	public Collection<IapBranchPosItemCode> getIapBranchPosItemCodes();

}
