package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostDefaults;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_COST_DEFAULTS", sequenceName="SEQ_INCIDENT_COST_DEFAULTS")
@Table(name="isw_incident_cost_defaults")
public class IncidentCostDefaultsImpl extends PersistableImpl implements IncidentCostDefaults {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_COST_DEFAULTS")
	private Long id = 0L;
	
	@OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private Incident incident;

	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = false)
	private Long incidentId;
	
	@Column(name = "AIR_TRAVEL_AMOUNT", precision = 22)
	private BigDecimal airTravelAmount;
	
	@Column(name = "RENTAL_VEHICLE_AMOUNT", precision = 22)
	private BigDecimal rentalVehicleAmount;
	
	@Column(name = "OTHER_AMOUNT", precision = 22)
	private BigDecimal otherAmount;

	@Column(name = "OTHER_DESCRIPTION", length = 75)
	private String otherDescription;
	
	@Column(name = "DEFAULT_HOURS", precision = 4, scale = 0)
	private Integer defaultHours;
	
	public IncidentCostDefaultsImpl(){
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the airTravelAmount
	 */
	public BigDecimal getAirTravelAmount() {
		return airTravelAmount;
	}

	/**
	 * @param airTravelAmount the airTravelAmount to set
	 */
	public void setAirTravelAmount(BigDecimal airTravelAmount) {
		this.airTravelAmount = airTravelAmount;
	}

	/**
	 * @return the rentalVehicleAmount
	 */
	public BigDecimal getRentalVehicleAmount() {
		return rentalVehicleAmount;
	}

	/**
	 * @param rentalVehicleAmount the rentalVehicleAmount to set
	 */
	public void setRentalVehicleAmount(BigDecimal rentalVehicleAmount) {
		this.rentalVehicleAmount = rentalVehicleAmount;
	}

	/**
	 * @return the otherAmount
	 */
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @return the otherDescription
	 */
	public String getOtherDescription() {
		return otherDescription;
	}

	/**
	 * @param otherDescription the otherDescription to set
	 */
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	/**
	 * @return the defaultHours
	 */
	public Integer getDefaultHours() {
		return defaultHours;
	}

	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(Integer defaultHours) {
		this.defaultHours = defaultHours;
	}
	
	
	
}
