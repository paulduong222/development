package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_INCCOST_RATE", sequenceName="SEQ_INCCOST_RATE")
@Table(name="isw_inccost_rate")
public class IncidentCostRateImpl extends PersistableImpl implements IncidentCostRate {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCCOST_RATE")
	private Long id = 0L;

	@Column(name = "COST_RATE_CATEGORY", nullable = false, length = 30)
	private String costRateCategory;

	@ManyToOne(targetEntity=IncidentImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable=true, updatable=true, nullable = true)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable=false, updatable=false, nullable = true)
	private Long incidentId;

	@ManyToOne(targetEntity=IncidentGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", insertable=true, updatable=true, nullable = true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", insertable=false, updatable=false, nullable = true)
	private Long incidentGroupId;
	
	@ManyToMany(targetEntity=IncidentCostRateStateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateState> incidentCostRateStates = new ArrayList<IncidentCostRateState>();

	@ManyToMany(targetEntity=IncidentCostRateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateKind> incidentCostRateKinds = new ArrayList<IncidentCostRateKind>();

	@ManyToMany(targetEntity=IncidentCostRateOvhdImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateOvhd> incidentCostRateOvhds = new ArrayList<IncidentCostRateOvhd>();
	
	/**
	 * Default constructor.
	 *
	 */
	public IncidentCostRateImpl() {
		super();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		IncidentCostRateImpl o = (IncidentCostRateImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(id)
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
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
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() {
		return costRateCategory;
	}



	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
	}



	/**
	 * @return the incidentCostRateStates
	 */
	public Collection<IncidentCostRateState> getIncidentCostRateStates() {
		return incidentCostRateStates;
	}



	/**
	 * @param incidentCostRateStates the incidentCostRateStates to set
	 */
	public void setIncidentCostRateStates(Collection<IncidentCostRateState> incidentCostRateStates) {
		this.incidentCostRateStates = incidentCostRateStates;
	}



	/**
	 * @return the incidentCostRateKinds
	 */
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds() {
		return incidentCostRateKinds;
	}



	/**
	 * @param incidentCostRateKinds the incidentCostRateKinds to set
	 */
	public void setIncidentCostRateKinds(Collection<IncidentCostRateKind> incidentCostRateKinds) {
		this.incidentCostRateKinds = incidentCostRateKinds;
	}



	/**
	 * @return the incidentCostRateOvhds
	 */
	public Collection<IncidentCostRateOvhd> getIncidentCostRateOvhds() {
		return incidentCostRateOvhds;
	}



	/**
	 * @param incidentCostRateOvhds the incidentCostRateOvhds to set
	 */
	public void setIncidentCostRateOvhds(Collection<IncidentCostRateOvhd> incidentCostRateOvhds) {
		this.incidentCostRateOvhds = incidentCostRateOvhds;
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
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}



	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}



	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}



	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}



}
