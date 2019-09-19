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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_FUEL_TYPE", sequenceName="SEQ_FUEL_TYPE")
@Table(name="iswl_fuel_type")
public class FuelTypeImpl extends PersistableImpl implements FuelType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_FUEL_TYPE")
	private Long id;
	
	@Column(name="CODE", length=40)
	private String code;
	
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
	@ManyToMany(targetEntity=ResourceTrainingImpl.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_RSC_TRAINING_FUEL_TYPE", 
			joinColumns = 
			{@JoinColumn(name = "FUEL_TYPE_ID",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "RESOURCE_TRAINING_ID", nullable = false, updatable = false) }
	)
	private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
	
	@ManyToMany(targetEntity=TrainingSettingsImpl.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_TRAINING_SET_FUEL_TYPE", 
			joinColumns = 
			{@JoinColumn(name = "FUEL_TYPE_ID",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "TRAINING_SETTINGS_ID", nullable = false, updatable = false) }
	)
	private Collection<TrainingSettings> trainingSettings = new ArrayList<TrainingSettings>();
	
	
	/**
	 * Default constructor.
	 *
	*/
	public FuelTypeImpl() {
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings) {
		this.resourceTrainings = resourceTrainings;
	}

	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings() {
		return resourceTrainings;
	}


	/**
	 * @param trainingSettings the trainingSettings to set
	 */
	public void setTrainingSettings(Collection<TrainingSettings> trainingSettings) {
		this.trainingSettings = trainingSettings;
	}


	/**
	 * @return the trainingSettings
	 */
	public Collection<TrainingSettings> getTrainingSettings() {
		return trainingSettings;
	}

}
