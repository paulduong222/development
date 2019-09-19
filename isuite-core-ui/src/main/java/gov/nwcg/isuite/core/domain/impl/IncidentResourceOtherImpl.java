package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_RESOURCE_OTHER", sequenceName="SEQ_INCIDENT_RESOURCE_OTHER")
@Table(name = "isw_incident_resource_other")
public class IncidentResourceOtherImpl extends PersistableImpl implements IncidentResourceOther {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_RESOURCE_OTHER")
	private Long id = 0L;

	//@ManyToOne(targetEntity=CostDataImpl.class,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@OneToOne(targetEntity=CostDataImpl.class,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "COST_DATA_ID", updatable=true, insertable=true)
	private CostData costData;

	@Column(name="COST_DATA_ID", updatable=false, insertable=false)
	private Long costDataId;
	
	@ManyToOne(targetEntity=ResourceOtherImpl.class,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	//@OneToOne(targetEntity=ResourceOtherImpl.class,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "RESOURCE_OTHER_ID")
	private ResourceOther resourceOther;
	
	@ManyToOne(targetEntity=IncidentImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID")
	private Incident incident;
	
	@Column(name="ASSIGNMENT_STATUS")
	@Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;
	
    @OneToMany(targetEntity=IncidentResourceDailyCostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResourceOther")   
	private Collection<IncidentResourceDailyCost> incidentResourceDailyCosts;
	
	public IncidentResourceOtherImpl() {
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
	 * @return the costData
	 */
	public CostData getCostData() {
		return costData;
	}



	/**
	 * @param costData the costData to set
	 */
	public void setCostData(CostData costData) {
		this.costData = costData;
	}



	/**
	 * @return the resourceOther
	 */
	public ResourceOther getResourceOther() {
		return resourceOther;
	}



	/**
	 * @param resourceOther the resourceOther to set
	 */
	public void setResourceOther(ResourceOther resourceOther) {
		this.resourceOther = resourceOther;
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
	 * @return the assignmentStatus
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * @param assignmentStatus the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
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
		IncidentResourceOtherImpl o = (IncidentResourceOtherImpl)obj;
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
	 * @return the incidentResourceDailyCosts
	 */
	public Collection<IncidentResourceDailyCost> getIncidentResourceDailyCosts() {
		return incidentResourceDailyCosts;
	}

	/**
	 * @param incidentResourceDailyCosts the incidentResourceDailyCosts to set
	 */
	public void setIncidentResourceDailyCosts(
			Collection<IncidentResourceDailyCost> incidentResourceDailyCosts) {
		this.incidentResourceDailyCosts = incidentResourceDailyCosts;
	}

	/**
	 * @return the costDataId
	 */
	public Long getCostDataId() {
		return costDataId;
	}

	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId) {
		this.costDataId = costDataId;
	}


}
