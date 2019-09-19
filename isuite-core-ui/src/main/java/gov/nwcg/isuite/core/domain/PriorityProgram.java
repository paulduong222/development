package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface PriorityProgram extends Persistable {
	
	/**
	 * @return the code
	 */
	public String getCode();

	/**
	 * @param code
	 */
	public void setCode(String code);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description
	 */
	public void setDescription(String description);
	
	/** 
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings);
	
	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);

	/**
	 * @return the incident
	 */
	public Incident getIncident();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

}
