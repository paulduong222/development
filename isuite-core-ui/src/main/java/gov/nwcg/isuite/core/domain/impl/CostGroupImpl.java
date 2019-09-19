package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupDefaultAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;

@Entity
@SequenceGenerator(name="SEQ_COST_GROUP", sequenceName="SEQ_COST_GROUP")
@Table(name = "isw_cost_group")
public class CostGroupImpl extends PersistableImpl implements CostGroup {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_GROUP")
	private Long id = 0L;

	@ManyToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private Incident incident;

	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = false)
	private Long incidentId;

	@Column(name = "COST_GROUP_NAME", nullable = false, length = 60)
	private String costGroupName;

	@Column(name = "DESCRIPTION", length = 75)
	private String description;

	@Column(name = "START_DATE", length = 11)
	private Date startDate;

	@ManyToOne(targetEntity=IncidentShiftImpl.class)
	@JoinColumn(name = "INCIDENT_SHIFT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private IncidentShift incidentShift;

	@Column(name="INCIDENT_SHIFT_ID", insertable = false, updatable = false, nullable = false)
	private Long incidentShiftId;

	@Column(name="DELETED_DATE")
	private Date deletedDate;

	@ManyToMany(targetEntity=ResourceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_cost_group_resource", 
			joinColumns = 
			{@JoinColumn(name = "cost_group_id",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "resource_id", nullable = false, updatable = false) }
	)
	private Collection<Resource> resources = new ArrayList<Resource>();

	@ManyToMany(targetEntity=ResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_cost_group_resource_other", 
			joinColumns = 
			{@JoinColumn(name = "cost_group_id",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "resource_other_id", nullable = false, updatable = false) }
	)
	private Collection<ResourceOther> resourceOthers = new ArrayList<ResourceOther>();

	@OneToMany(targetEntity=CostGroupAgencyDayShareImpl.class,cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "costGroup")
	//@BatchSize(size=100)
	private Collection<CostGroupAgencyDayShare> costGroupAgencyDayShares = new ArrayList<CostGroupAgencyDayShare>();

	@OneToMany(targetEntity=CostDataImpl.class,fetch = FetchType.LAZY,cascade=CascadeType.ALL, mappedBy="defaultCostGroup")
	private Collection<CostData> costDatas;

	@OneToMany(targetEntity=CostGroupDefaultAgencyDaySharePercentageImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "costGroup")
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<CostGroupDefaultAgencyDaySharePercentage> costGroupDfAgPcts=null;
	
	public CostGroupImpl() {
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
	 * @return the costGroupName
	 */
	public String getCostGroupName() {
		return costGroupName;
	}


	/**
	 * @param costGroupName the costGroupName to set
	 */
	public void setCostGroupName(String costGroupName) {
		this.costGroupName = costGroupName;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param incidentShift the incidentShift to set
	 */
	public void setIncidentShift(IncidentShift incidentShift) {
		this.incidentShift = incidentShift;
	}

	/**
	 * @return the incidentShift
	 */
	public IncidentShift getIncidentShift() {
		return incidentShift;
	}

	/**
	 * @param incidentShiftId the incidentShiftId to set
	 */
	public void setIncidentShiftId(Long incidentShiftId) {
		this.incidentShiftId = incidentShiftId;
	}

	/**
	 * @return the incidentShiftId
	 */
	public Long getIncidentShiftId() {
		return incidentShiftId;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}


	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}


	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources() {
		return resources;
	}


	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}


	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() {
		return resourceOthers;
	}


	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers) {
		this.resourceOthers = resourceOthers;
	}


	/**
	 * @return the costGroupAgencyDayShares
	 */
	public Collection<CostGroupAgencyDayShare> getCostGroupAgencyDayShares() {
		return costGroupAgencyDayShares;
	}


	/**
	 * @param costGroupAgencyDayShares the costGroupAgencyDayShares to set
	 */
	public void setCostGroupAgencyDayShares(
			Collection<CostGroupAgencyDayShare> costGroupAgencyDayShares) {
		this.costGroupAgencyDayShares = costGroupAgencyDayShares;
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
		CostGroupImpl o = (CostGroupImpl)obj;
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
	 * @return the costData
	 */
	public Collection<CostData> getCostDatas() {
		return costDatas;
	}


	/**
	 * @param costData the costData to set
	 */
	public void setCostDatas(Collection<CostData> costDatas) {
		this.costDatas = costDatas;
	}


	/**
	 * @return the costGroupDfAgPcts
	 */
	public Collection<CostGroupDefaultAgencyDaySharePercentage> getCostGroupDfAgPcts() {
		if(null == costGroupDfAgPcts)
			costGroupDfAgPcts = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();
		return costGroupDfAgPcts;
	}


	/**
	 * @param costGroupDfAgPcts the costGroupDfAgPcts to set
	 */
	public void setCostGroupDfAgPcts(
			Collection<CostGroupDefaultAgencyDaySharePercentage> costGroupDfAgPcts) {
		this.costGroupDfAgPcts = costGroupDfAgPcts;
	}

}
