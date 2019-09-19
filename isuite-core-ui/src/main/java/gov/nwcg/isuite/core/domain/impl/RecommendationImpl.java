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

import gov.nwcg.isuite.core.domain.Recommendation;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_RECOMMENDATION", sequenceName="SEQ_RECOMMENDATION")
@Table(name="iswl_recommendation")
public class RecommendationImpl extends PersistableImpl implements Recommendation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RECOMMENDATION")
	private Long id;
	
	@Column(name="CODE", length=40)
	private String code;
	   
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
	@OneToMany(targetEntity=ResourceTrainingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "recommendation")
	private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
	
	/**
	 * Default Constructor
	 */
	public RecommendationImpl() {
		super();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
     */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
