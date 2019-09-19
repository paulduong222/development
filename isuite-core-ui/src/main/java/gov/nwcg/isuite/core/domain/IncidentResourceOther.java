package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

public interface IncidentResourceOther extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;


	/**
	 * @return the costData
	 */
	public CostData getCostData() ;


	/**
	 * @param costData the costData to set
	 */
	public void setCostData(CostData costData) ;


	/**
	 * @return the resourceOther
	 */
	public ResourceOther getResourceOther() ;


	/**
	 * @param resourceOther the resourceOther to set
	 */
	public void setResourceOther(ResourceOther resourceOther);


	/**
	 * @return the incident
	 */
	public Incident getIncident() ;


	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) ;	
	
	/**
	 * @return the assignmentStatus
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus();
	
	/**
	 * @param assignmentStatus the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus);

	/**
	 * @return the incidentResourceDailyCosts
	 */
	public Collection<IncidentResourceDailyCost> getIncidentResourceDailyCosts();

	/**
	 * @param incidentResourceDailyCosts the incidentResourceDailyCosts to set
	 */
	public void setIncidentResourceDailyCosts(Collection<IncidentResourceDailyCost> incidentResourceDailyCosts);

	/**
	 * @return the costDataId
	 */
	public Long getCostDataId() ;

	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId);
	
}
