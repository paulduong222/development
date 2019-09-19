package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

@Entity
@SequenceGenerator(name="SEQ_SUB_GROUP_CATEGORY", sequenceName="SEQ_SUB_GROUP_CATEGORY")
@Table(name = "iswl_sub_group_category")
public class SubGroupCategoryImpl extends PersistableImpl implements SubGroupCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SUB_GROUP_CATEGORY")
	private Long id = 0L;
   
	@Column(name = "CODE", length = 10, nullable=false)
	private String code;
   
	@Column(name = "DESCRIPTION", length = 75, nullable=false)
	private String description;
   
	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;
	
	@OneToMany(targetEntity=KindImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="subGroupCategory")
	private Collection<Kind> kinds;
	
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
   
	@Column(name = "IS_ACTIVE",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum active;
	
	public SubGroupCategoryImpl() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#setKinds(java.util.Collection)
	 */
	public void setKinds(Collection<Kind> kinds) {
		this.kinds = kinds;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.SubGroupCategory#getKinds()
	 */
	public Collection<Kind> getKinds() {
		return kinds;
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
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive() {
		return active;
	}

}
