package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

public interface BranchSetting extends Persistable {

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the branchName
	 */
	public String getBranchName() ;
	
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName);

	/**
	 * @return the branchSettingPositions
	 */
	public Collection<BranchSettingPosition> getBranchSettingPositions() ;
	
	/**
	 * @param branchSettingPositions the branchSettingPositions to set
	 */
	public void setBranchSettingPositions(
			Collection<BranchSettingPosition> branchSettingPositions);

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() ;

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) ;

	/**
	 * @return the incident
	 */
	public Incident getIncident() ;

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) ;

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);
	
	/**
	 * @return the iapPersonnelPositions
	 */
	public Collection<IapPersonnel> getIapPersonnelPositions();

	/**
	 * @param iapPersonnelPositions the iapPersonnelPositions to set
	 */
	public void setIapPersonnelPositions(
			Collection<IapPersonnel> iapPersonnelPositions);
	
}