package gov.nwcg.isuite.core.filter;

import java.util.Collection;
import java.util.Date;

public interface WorkAreaResourceFilter {
	
	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() ;

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) ;

	/**
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
	public boolean isExcludeFilled() ;

	/**
	 * Sets the excludeFilled.
	 *
	 * @param excludeFilled 
	 *			the excludeFilled to set
	 */
	public void setExcludeFilled(boolean excludeFilled) ;

	/**
	 * Returns the includeDemob.
	 *
	 * @return 
	 *		the includeDemob to return
	 */
	public boolean isIncludeDemob() ;

	/**
	 * Sets the includeDemob.
	 *
	 * @param includeDemob 
	 *			the includeDemob to set
	 */
	public void setIncludeDemob(boolean includeDemob) ;

	/**
	 * Returns the assignedToIncident.
	 *
	 * @return 
	 *		the assignedToIncident to return
	 */
	public boolean isAssignedToIncident() ;

	/**
	 * Sets the assignedToIncident.
	 *
	 * @param assignedToIncident 
	 *			the assignedToIncident to set
	 */
	public void setAssignedToIncident(boolean assignedToIncident) ;

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean isPermanent() ;

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(boolean permanent) ;

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
	public boolean isOverhead() ;

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
	public boolean getOverhead() ;
	
	/**
	 * Sets the overhead.
	 *
	 * @param overhead 
	 *			the overhead to set
	 */
	public void setOverhead(boolean overhead) ;

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
	public boolean isAircraft() ;

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
	public boolean getAircraft() ;
	
	/**
	 * Sets the aircraft.
	 *
	 * @param aircraft 
	 *			the aircraft to set
	 */
	public void setAircraft(boolean aircraft) ;

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
	public boolean isPersonnel() ;

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
	public boolean getPersonnel() ;
	
	/**
	 * Sets the personnel.
	 *
	 * @param personnel 
	 *			the personnel to set
	 */
	public void setPersonnel(boolean personnel) ;

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
	public boolean isCrews() ;

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
	public boolean getCrews() ;
	
	/**
	 * Sets the crews.
	 *
	 * @param crews 
	 *			the crews to set
	 */
	public void setCrews(boolean crews) ;

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
	public boolean isEquipment() ;

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
	public boolean getEquipment() ;
	
	/**
	 * Sets the equipment.
	 *
	 * @param equipment 
	 *			the equipment to set
	 */
	public void setEquipment(boolean equipment) ;

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public boolean isEnabled() ;

	public boolean getEnabled();
	
	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(boolean enabled) ;

	
	
	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() ;

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) ;

	/**
	 * Returns the person.
	 *
	 * @return 
	 *		the person to return
	 */
	public Boolean getPerson() ;

	/**
	 * Sets the person.
	 *
	 * @param person 
	 *			the person to set
	 */
	public void setPerson(Boolean person) ;

	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted() ;

	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted) ;

	/**
	 * Returns the leader.
	 *
	 * @return 
	 *		the leader to return
	 */
	public Boolean getLeader() ;

	/**
	 * Sets the leader.
	 *
	 * @param leader 
	 *			the leader to set
	 */
	public void setLeader(Boolean leader) ;

	/**
	 * Returns the other1.
	 *
	 * @return 
	 *		the other1 to return
	 */
	public String getOther1() ;

	/**
	 * Sets the other1.
	 *
	 * @param other1 
	 *			the other1 to set
	 */
	public void setOther1(String other1) ;

	/**
	 * Returns the other2.
	 *
	 * @return 
	 *		the other2 to return
	 */
	public String getOther2() ;

	/**
	 * Sets the other2.
	 *
	 * @param other2 
	 *			the other2 to set
	 */
	public void setOther2(String other2) ;

	/**
	 * Returns the other3.
	 *
	 * @return 
	 *		the other3 to return
	 */
	public String getOther3() ;

	/**
	 * Sets the other3.
	 *
	 * @param other3 
	 *			the other3 to set
	 */
	public void setOther3(String other3) ;

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() ;

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) ;

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() ;

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) ;

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() ;

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) ;

	/**
	 * Returns the resourceStatus.
	 *
	 * @return 
	 *		the resourceStatus to return
	 */
	public String getResourceStatus() ;

	/**
	 * Sets the resourceStatus.
	 *
	 * @param resourceStatus 
	 *			the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus) ;

	/**
	 * Returns the numberOfPersonnel.
	 *
	 * @return 
	 *		the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel() ;

	/**
	 * Sets the numberOfPersonnel.
	 *
	 * @param numberOfPersonnel 
	 *			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) ;

	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName() ;

	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName) ;

	/**
	 * Returns the resourceAgencyName.
	 *
	 * @return 
	 *		the resourceAgencyName to return
	 */
	public String getResourceAgencyName() ;

	/**
	 * Sets the resourceAgencyName.
	 *
	 * @param resourceAgencyName 
	 *			the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName) ;

	/**
	 * Returns the resourceOrganizationName.
	 *
	 * @return 
	 *		the resourceOrganizationName to return
	 */
	public String getResourceOrganizationName() ;

	/**
	 * Sets the resourceOrganizationName.
	 *
	 * @param resourceOrganizationName 
	 *			the resourceOrganizationName to set
	 */
	public void setResourceOrganizationName(String resourceOrganizationName) ;

	/**
	 * Returns the workAreaResourceId.
	 *
	 * @return 
	 *		the workAreaResourceId to return
	 */
	public Long getWorkAreaResourceId() ;

	/**
	 * Sets the workAreaResourceId.
	 *
	 * @param workAreaResourceId 
	 *			the workAreaResourceId to set
	 */
	public void setWorkAreaResourceId(Long workAreaResourceId);

//	/**
//	 * Returns the unitCode.
//	 *
//	 * @return 
//	 *		the unitCode to return
//	 */
//	public String getUnitCode();
//
//	/**
//	 * Sets the unitCode.
//	 *
//	 * @param unitCode 
//	 *			the unitCode to set
//	 */
//	public void setUnitCode(String unitCode);
	
	
	/**
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode();

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode);

	/**
	 * Returns the agencyCode.
	 *
	 * @return 
	 *		the agencyCode to return
	 */
	public String getAgencyCode();

	/**
	 * Sets the agencyCode.
	 *
	 * @param agencyCode 
	 *			the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode);

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() ;

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public String getAssignmentStatus();

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(String assignmentStatus);
	
	
	/**
	 * Returns the kindDescription
	 * 
	 * @return
	 * 		the kindDescriptin to return
	 */
	public String getKindDescription();
	
	/**
	 * Sets the kindDescription
	 * 
	 * @param kindDescription
	 * 		the kindDescriptin to set
	 */
	public void setKindDescription(String kindDescription);
	
	/**
	 * Returns the activeStatus
	 * 
	 * @return
	 * 		the activeStatus to return
	 */
	public String getActiveStatus();
	
	/**
	 * Sets the activeStatus
	 * 
	 * @param activeStatus
	 * 		the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus);
	
	/**
	 * @return the mobilizationStartDate
	 */
	public Date getMobilizationStartDate();

	/**
	 * @param mobilizationStartDate the mobilizationStartDate to set
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate);
	
	/**
	 * 
	 * @param crypticDateFilterCode
	 */
	public void setCrypticDateFilterCode(String crypticDateFilterCode);
	
	/**
	 * 
	 * @return
	 */
	public String getCrypticDateFilterCode();
	
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber();

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber);

	public Collection<Integer> getExcludedResourceIds() ;

	public void setExcludedResourceIds(Collection<Integer> excludedResourceIds) ;
		
}
