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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_PRIORITY_PROGRAM", sequenceName="SEQ_PRIORITY_PROGRAM")
@Table(name="iswl_priority_program")
public class PriorityProgramImpl extends PersistableImpl implements PriorityProgram {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_PRIORITY_PROGRAM")
	private Long id;
	
	@Column(name="CODE", length=40)
	private String code;
	
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Incident incident;

	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = false)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", nullable = true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentGroupId;
	
	@OneToMany(targetEntity=ResourceTrainingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "priorityProgram")
	private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
	
	/**
	 * Default constructor.
	 */
	public PriorityProgramImpl() {
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

}
