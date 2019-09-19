package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;


@Entity
@SequenceGenerator(name="SEQ_PROJECTION", sequenceName="SEQ_PROJECTION")
@Table(name="ISW_PROJECTION")
public class ProjectionImpl extends PersistableImpl implements Projection {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_PROJECTION")
	private Long id = 0L;

	@Column(name = "PROJECTION_NAME", nullable = false, length = 50)
	private String projectionName;

	@Column(name = "START_DATE", nullable = false)
	private Date startDate;

	@Column(name = "NUMBER_OF_DAYS", nullable = false)
	private Short numberOfDays;

	@OneToMany(targetEntity=ProjectionItemImpl.class,  fetch = FetchType.LAZY, mappedBy = "projection")
	@Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Collection<ProjectionItem> projectionItems = null;

	//cascade = CascadeType.ALL,
	
	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID")
	private Incident incident;

	@Column(name="INCIDENT_GROUP_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentGroupId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID")
	private IncidentGroup incidentGroup;

	/**
	 * Default constructor.
	 *
	 */
	public ProjectionImpl() {
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
	 * @return the projectionName
	 */
	public String getProjectionName() {
		return projectionName;
	}


	/**
	 * @param projectionName the projectionName to set
	 */
	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
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
	 * @return the numberOfDays
	 */
	public Short getNumberOfDays() {
		return numberOfDays;
	}


	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(Short numberOfDays) {
		this.numberOfDays = numberOfDays;
	}


	/**
	 * @return the projectionItems
	 */
	public Collection<ProjectionItem> getProjectionItems() {
		//if(!CollectionUtility.hasValue(projectionItems))
		if(projectionItems == null)
			projectionItems = new ArrayList<ProjectionItem>();
		return projectionItems;
	}


	/**
	 * @param projectionItems the projectionItems to set
	 */
	public void setProjectionItems(Collection<ProjectionItem> projectionItems) {
		//this.projectionItems = projectionItems;
		
		//in this way to provent assign a new list to projectionItems, that cause hibernate save problem
		this.projectionItems.clear();
		this.projectionItems.addAll(projectionItems);
	}

	public Long getIncidentId() {
		return incidentId;
	}


	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	public Long getIncidentGroupId() {
		return incidentGroupId;
	}


	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		ProjectionImpl o = (ProjectionImpl)obj;
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
	
	@Transient
	public void addProjectionItem(ProjectionItem pi) { 
		getProjectionItems().add(pi); 
		pi.setProjection(this); 
	}
	
	@Transient
	public void addProjectionItems(List<ProjectionItem> pis) { 
		getProjectionItems().addAll(pis);
	}
	
	@Transient
	public void updateSupportingItem(ProjectionItem entity) {
		for(ProjectionItem pi : this.projectionItems) {
			if(pi.getIsSupportCost().getValue()) {
				if(LongUtility.hasValue(entity.getId()))
					pi.setNumberOfPersonnel(pi.getNumberOfPersonnel()-entity.getNumberOfPersonnel());
				else
					pi.setNumberOfPersonnel(pi.getNumberOfPersonnel()+entity.getNumberOfPersonnel());
//				for(ProjectionItemWorksheet piwksht : pi.getProjectionItemWorksheets())
//					piwksht.setNumberOfPersonnel(piwksht.getNumberOfPersonnel()+entity.getNumberOfPersonnel());
			}
		}
	}
	
	@Transient
	public void removeProjectionItem(Long id) {		
		Iterator<ProjectionItem> it = this.projectionItems.iterator();
		while (it.hasNext()) {
			ProjectionItem pi = it.next();
			if(pi.getId().equals(id)) 
				it.remove();
	    }
	}	
		
}
