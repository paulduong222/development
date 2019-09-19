package gov.nwcg.isuite.core.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_COMPLEXITY", sequenceName="SEQ_COMPLEXITY")
@Table(name="iswl_complexity")
public class ComplexityImpl extends PersistableImpl implements Complexity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COMPLEXITY")
	private Long id;
	
	@Column(name="CODE", length=40)
	private String code;
	
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
//	@OneToMany(targetEntity=RscTrainingTrainerImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "complexity")
//	private Collection<RscTrainingTrainer> rscTrainingTrainers = new ArrayList<RscTrainingTrainer>();
	
	@OneToMany(targetEntity=IncidentImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "complexity")
	private Collection<Incident> incidents = new ArrayList<Incident>();
	
	@OneToMany(targetEntity=ResourceTrainingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "complexity")
	private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
	
	
	/**
	 * Default constructor
	 */
	public ComplexityImpl(){
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
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
//	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers) {
//		this.rscTrainingTrainers = rscTrainingTrainers;
//	}

	/**
	 * @return the rscTrainingTrainers
	 */
//	public Collection<RscTrainingTrainer> getRscTrainingTrainers() {
//		return rscTrainingTrainers;
//	}

	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents) {
		this.incidents = incidents;
	}

	/**
	 * @return the incidents
	 */
	public Collection<Incident> getIncidents() {
		return incidents;
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

}
