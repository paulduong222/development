package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.views.IncidentResourceAssignmentView;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name="isw_incident_resource")
public class IncidentResourceAssignmentViewImpl extends PersistableImpl implements IncidentResourceAssignmentView {
   
   @Column(name="INCIDENT_ID")
   private Long incidentId;

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="INCIDENT_ID", insertable=false, updatable=false)
   private Incident incident;

   @Column(name="RESOURCE_ID", insertable=false, updatable=false)
   private Long resourceId;

   @OneToOne(targetEntity=ResourceImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="RESOURCE_ID")
   private Resource resource;
   
   @Column(name="NAME_AT_INCIDENT",length=100)
   private String nameAtIncident;

   @OneToMany(targetEntity=WorkPeriodImpl.class, fetch=FetchType.LAZY)
   @JoinTable(name="isw_work_period",
      joinColumns={ @JoinColumn(name="INCIDENT_RESOURCE_ID") },
      inverseJoinColumns= { @JoinColumn(name="ID") })
   private Collection<WorkPeriod> workPeriods;

   
   public String getNameAtIncident() {
   	return nameAtIncident;
   }
	
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Collection<WorkPeriod> getWorkPeriods() {
		return workPeriods;
	}

	public void setWorkPeriods(
			Collection<WorkPeriod> workPeriods) {
		this.workPeriods = workPeriods;
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
}
