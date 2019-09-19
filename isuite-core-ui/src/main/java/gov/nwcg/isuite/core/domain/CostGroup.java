package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CostGroup extends Persistable{

	/**
	 * @return the incident
	 */
	public Incident getIncident();

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) ;
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @return the costGroupName
	 */
	public String getCostGroupName();

	/**
	 * @param costGroupName the costGroupName to set
	 */
	public void setCostGroupName(String costGroupName);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) ;

	/**
	 * @return the startDate
	 */
	public Date getStartDate();

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) ;

	/**
	 * @return the incidentShift
	 */
	public IncidentShift getIncidentShift() ;

	/**
	 * @param incidentShift the incidentShift to set
	 */
	public void setIncidentShift(IncidentShift incidentShift) ;
	
	/**
	 * @param incidentShiftId the incidentShiftId to set
	 */
	public void setIncidentShiftId(Long incidentShiftId);


	/**
	 * @return the incidentShiftId
	 */
	public Long getIncidentShiftId();
	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() ;
	
	/**
	 * @param deletedDate
	 */
	public void setDeletedDate(Date deletedDate) ;

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources() ;

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources) ;

	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() ;

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers) ;

	/**
	 * @return the costGroupAgencyDayShares
	 */
	public Collection<CostGroupAgencyDayShare> getCostGroupAgencyDayShares() ;

	/**
	 * @param costGroupAgencyDayShares the costGroupAgencyDayShares to set
	 */
	public void setCostGroupAgencyDayShares(
			Collection<CostGroupAgencyDayShare> costGroupAgencyDayShares) ;	

	/**
	 * @return the costData
	 */
	public Collection<CostData> getCostDatas() ;


	/**
	 * @param costData the costData to set
	 */
	public void setCostDatas(Collection<CostData> costData);

	/**
	 * @return the costGroupDfAgPcts
	 */
	public Collection<CostGroupDefaultAgencyDaySharePercentage> getCostGroupDfAgPcts();


	/**
	 * @param costGroupDfAgPcts the costGroupDfAgPcts to set
	 */
	public void setCostGroupDfAgPcts(
			Collection<CostGroupDefaultAgencyDaySharePercentage> costGroupDfAgPcts);
	
}
