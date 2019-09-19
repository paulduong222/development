package gov.nwcg.isuite.core.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_TRAINING_SETTINGS", sequenceName="SEQ_TRAINING_SETTINGS")
@Table(name="isw_training_settings")
public class TrainingSettingsImpl extends PersistableImpl implements
		TrainingSettings {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TRAINING_SETTINGS")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID")
	private Incident incident;
   
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID")
	private IncidentGroup incidentGroup;
   
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false)
	private Long incidentGroupId;

	@ManyToOne(targetEntity=ComplexityImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "COMPLEXITY_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Complexity complexity;
    
    @Column(name="COMPLEXITY_ID", insertable = false, updatable = false, nullable = true)
	private Long complexityId;
    
    @Column(name = "NUMBER_OF_ACRES", nullable = true)
	private Long numberOfAcres;
    
    @ManyToMany(targetEntity=FuelTypeImpl.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_TRAINING_SET_FUEL_TYPE", 
			joinColumns = 
			{@JoinColumn(name = "TRAINING_SETTINGS_ID",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "FUEL_TYPE_ID", nullable = false, updatable = false) }
	)
	private Collection<FuelType> fuelTypes = new ArrayList<FuelType>();
    
    /**
	 * Default constructor
	 */
    public TrainingSettingsImpl() {
    	super();
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
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param complexity the complexity to set
	 */
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	/**
	 * @return the complexity
	 */
	public Complexity getComplexity() {
		return complexity;
	}

	/**
	 * @param complexityId the complexityId to set
	 */
	public void setComplexityId(Long complexityId) {
		this.complexityId = complexityId;
	}

	/**
	 * @return the complexityId
	 */
	public Long getComplexityId() {
		return complexityId;
	}

	/**
	 * @param numberOfAcres the numberOfAcres to set
	 */
	public void setNumberOfAcres(Long numberOfAcres) {
		this.numberOfAcres = numberOfAcres;
	}

	/**
	 * @return the numberOfAcres
	 */
	public Long getNumberOfAcres() {
		return numberOfAcres;
	}

	/**
	 * @param fuelTypes the fuelTypes to set
	 */
	public void setFuelTypes(Collection<FuelType> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}

	/**
	 * @return the fuelTypes
	 */
	public Collection<FuelType> getFuelTypes() {
		return fuelTypes;
	}

}
