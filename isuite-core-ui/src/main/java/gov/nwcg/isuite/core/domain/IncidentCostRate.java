package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentCostRate extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id); 



	/**
	 * @return the costRateCategory
	 */
	public String getCostRateCategory();



	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) ;



	/**
	 * @return the incidentCostRateStates
	 */
	public Collection<IncidentCostRateState> getIncidentCostRateStates();



	/**
	 * @param incidentCostRateStates the incidentCostRateStates to set
	 */
	public void setIncidentCostRateStates(Collection<IncidentCostRateState> incidentCostRateStates) ;



	/**
	 * @return the incidentCostRateKinds
	 */
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds();



	/**
	 * @param incidentCostRateKinds the incidentCostRateKinds to set
	 */
	public void setIncidentCostRateKinds(Collection<IncidentCostRateKind> incidentCostRateKinds);



	/**
	 * @return the incidentCostRateOvhds
	 */
	public Collection<IncidentCostRateOvhd> getIncidentCostRateOvhds();



	/**
	 * @param incidentCostRateOvhds the incidentCostRateOvhds to set
	 */
	public void setIncidentCostRateOvhds(Collection<IncidentCostRateOvhd> incidentCostRateOvhds);

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
	public Long getIncidentId();
	


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() ;



	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);



	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();



	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);

	
}
