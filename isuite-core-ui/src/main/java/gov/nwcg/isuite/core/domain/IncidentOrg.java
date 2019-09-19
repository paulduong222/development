package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentOrg extends Persistable  {
	
	/**
	 * @param incident_id the incident_id to set
	 */
	public void setIncident_id(Long incident_id);

	/**
	 * @return the incident_id
	 */
	public Long getIncident_id();

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);

	/**
	 * @return the incident
	 */
	public Incident getIncident();

	/**
	 * @param organization_id the organization_id to set
	 */
	public void setOrganization_id(Long organization_id);

	/**
	 * @return the organization_id
	 */
	public Long getOrganization_id();

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization);

	/**
	 * @return the organization
	 */
	public Organization getOrganization();
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type);

	/**
	 * @return the type
	 */
	public String getType();

}
