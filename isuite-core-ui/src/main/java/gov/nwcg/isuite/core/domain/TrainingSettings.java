package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface TrainingSettings extends Persistable {
	
	 /**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

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

	/**
	 * @param complexity the complexity to set
	 */
	public void setComplexity(Complexity complexity);

	/**
	 * @return the complexity
	 */
	public Complexity getComplexity();

	/**
	 * @param complexityId the complexityId to set
	 */
	public void setComplexityId(Long complexityId);

	/**
	 * @return the complexityId
	 */
	public Long getComplexityId();

	/**
	 * @param numberOfAcres the numberOfAcres to set
	 */
	public void setNumberOfAcres(Long numberOfAcres);

	/**
	 * @return the numberOfAcres
	 */
	public Long getNumberOfAcres();
	
	/**
	 * @param fuelTypes the fuelTypes to set
	 */
	public void setFuelTypes(Collection<FuelType> fuelTypes);

	/**
	 * @return the fuelTypes
	 */
	public Collection<FuelType> getFuelTypes();

}
