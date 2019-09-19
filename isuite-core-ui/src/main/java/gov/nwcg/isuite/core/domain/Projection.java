package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface Projection extends Persistable {
   
	/**
	 * @return the id
	 */
	public Long getId();


	/**
	 * @param id the id to set
	 */
	public void setId(Long id);


	/**
	 * @return the projectionName
	 */
	public String getProjectionName();


	/**
	 * @param projectionName the projectionName to set
	 */
	public void setProjectionName(String projectionName);


	/**
	 * @return the startDate
	 */
	public Date getStartDate();


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate);


	/**
	 * @return the numberOfDays
	 */
	public Short getNumberOfDays();


	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(Short numberOfDays);


	/**
	 * @return the projectionItems
	 */
	public Collection<ProjectionItem> getProjectionItems();


	/**
	 * @param projectionItems the projectionItems to set
	 */
	public void setProjectionItems(Collection<ProjectionItem> projectionItems);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incident
	 */
	public Incident getIncident();


	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentGroupId();


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();


	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);
	
	public void addProjectionItem(ProjectionItem pi);
	public void addProjectionItems(List<ProjectionItem> pis);
	public void updateSupportingItem(ProjectionItem entity);
	public void removeProjectionItem(Long id);
}
