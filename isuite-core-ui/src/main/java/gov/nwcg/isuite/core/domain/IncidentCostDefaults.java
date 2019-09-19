package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;

public interface IncidentCostDefaults extends Persistable {

	
	/**
	 * @return the incident
	 */
	public Incident getIncident() ;

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
	public void setIncidentId(Long incidentId) ;

	/**
	 * @return the airTravelAmount
	 */
	public BigDecimal getAirTravelAmount() ;

	/**
	 * @param airTravelAmount the airTravelAmount to set
	 */
	public void setAirTravelAmount(BigDecimal airTravelAmount);

	/**
	 * @return the rentalVehicleAmount
	 */
	public BigDecimal getRentalVehicleAmount() ;

	/**
	 * @param rentalVehicleAmount the rentalVehicleAmount to set
	 */
	public void setRentalVehicleAmount(BigDecimal rentalVehicleAmount);
	/**
	 * @return the otherAmount
	 */
	public BigDecimal getOtherAmount() ;

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(BigDecimal otherAmount) ;

	/**
	 * @return the otherDescription
	 */
	public String getOtherDescription();

	/**
	 * @param otherDescription the otherDescription to set
	 */
	public void setOtherDescription(String otherDescription);

	/**
	 * @return the defaultHours
	 */
	public Integer getDefaultHours();

	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(Integer defaultHours);
	
}
