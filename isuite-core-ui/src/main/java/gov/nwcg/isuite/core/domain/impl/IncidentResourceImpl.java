package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.domain.TrainingContact;

import gov.nwcg.isuite.core.domain.WorkPeriod;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_RESOURCE", sequenceName="SEQ_INCIDENT_RESOURCE")
@Table(name="isw_incident_resource")
public class IncidentResourceImpl extends PersistableImpl implements IncidentResource {
   
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_RESOURCE")
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
   private Incident incident;

   @Column(name="INCIDENT_ID", insertable=false, updatable=false, nullable=true, unique=false)
   private Long incidentId;

   @Column(name="RES_NUM_ID", insertable=true, updatable=true, nullable=true, unique=false)
   private Long resNumId;
   
   @ManyToOne(targetEntity=ResourceImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "RESOURCE_ID", insertable=true, updatable=true, nullable = false)
   private Resource resource;
   
   @Column(name="RESOURCE_ID", insertable=false,updatable=false,nullable=false, unique=false)
   private Long resourceId;

   @OneToMany(targetEntity=IncidentResourceDailyCostImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentResource")   
   private Collection<IncidentResourceDailyCost> incidentResourceDailyCosts;
   
   @OneToOne(targetEntity=WorkPeriodImpl.class,
		   	 cascade = CascadeType.ALL, 
		   	 fetch = FetchType.LAZY, 
		   	 mappedBy = "incidentResource")
   private WorkPeriod workPeriod;
   
   @Column(name="NAME_AT_INCIDENT", length=100)
   private String nameAtIncident;

   @Column(name="ACTIVE", nullable=false)
   private Boolean active = true; //default to true
   
   @OneToOne(targetEntity=CostDataImpl.class,fetch = FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name = "COST_DATA_ID", updatable = true, insertable=true)
   private CostData costData;

   @Column(name="COST_DATA_ID", updatable=false,insertable=false)
   private Long costDataId;

   @Column(name="ROSS_RES_REQ_ID")
   private Long rossResReqId;

   @Column(name="DAILY_COST_EXCEPTION", length=255)
   private String dailyCostException;
   
   @OneToMany(targetEntity=IapPersonnelImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<IapPersonnel> iapPersonnels = new ArrayList<IapPersonnel>();
   
   @OneToMany(targetEntity=IapBranchPersonnelImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<IapBranchPersonnel> iapBranchPersonnels = new ArrayList<IapBranchPersonnel>();
   
   @OneToMany(targetEntity=ResourceTrainingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
   
   @OneToMany(targetEntity=ResourceHomeUnitContactImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<ResourceHomeUnitContact> resourceHomeUnitContacts = new ArrayList<ResourceHomeUnitContact>();
   
   @OneToMany(targetEntity=TrainingContactImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<TrainingContact> trainingContacts = new ArrayList<TrainingContact>();
   
   //@OneToOne(targetEntity=RscTrainingTrainerImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   //private RscTrainingTrainer rscTrainingTrainer;
   
   @OneToMany(targetEntity=RscTrainingTrainerImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResource")
   private Collection<RscTrainingTrainer> rscTrainingTrainers = new ArrayList<RscTrainingTrainer>();
   
   /* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#getIncident()
	 */
	public Incident getIncident() {
		return incident;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#setIncident(gov.nwcg.isuite.domain.incident.Incident)
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#setIncidentId(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#getResource()
	 */
	public Resource getResource() {
		return resource;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#setResource(gov.nwcg.isuite.domain.resource.Resource)
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident_resource.IncidentResource#setResourceId(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResource#getNameAtIncident()
	 */
	public String getNameAtIncident() {
		return nameAtIncident;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResource#setNameAtIncident(java.lang.String)
	 */
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;
	}

	public WorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	public void setWorkPeriod(WorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
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

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      IncidentResourceImpl o = (IncidentResourceImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,incidentId,nameAtIncident,resourceId},
      			new Object[]{o.id,o.incidentId,o.nameAtIncident,o.resourceId})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,incidentId,nameAtIncident,resourceId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("incidentId", incidentId)
	       .append("nameAtIncident", nameAtIncident)
	       .append("resourceId",resourceId)
	       .appendSuper(super.toString())
	       .toString();
   }

   /**
    * @return the active
    */
   public Boolean getActive() {
   	return active;
   }

   /**
    * @param active the active to set
    */
   public void setActive(Boolean active) {
   	this.active = active;
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

	public Long getRossResReqId() {
		return rossResReqId;
	}
	
	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
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
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels) {
		this.iapPersonnels = iapPersonnels;
	}

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels() {
		return iapPersonnels;
	}

	/**
	 * @param iapBranchPersonnels the iapBranchPersonnels to set
	 */
	public void setIapBranchPersonnels(Collection<IapBranchPersonnel> iapBranchPersonnels) {
		this.iapBranchPersonnels = iapBranchPersonnels;
	}

	/**
	 * @return the iapBranchPersonnels
	 */
	public Collection<IapBranchPersonnel> getIapBranchPersonnels() {
		return iapBranchPersonnels;
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

	/**
	 * @return the dailyCostException
	 */
	public String getDailyCostException() {
		return dailyCostException;
	}

	/**
	 * @param dailyCostException the dailyCostException to set
	 */
	public void setDailyCostException(String dailyCostException) {
		this.dailyCostException = dailyCostException;
	}

	/**
	 * @return the resNumId
	 */
	public Long getResNumId() {
		return resNumId;
	}

	/**
	 * @param resNumId the resNumId to set
	 */
	public void setResNumId(Long resNumId) {
		this.resNumId = resNumId;
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
	 * @param resourceHomeUnitContacts the resourceHomeUnitContacts to set
	 */
	public void setResourceHomeUnitContacts(Collection<ResourceHomeUnitContact> resourceHomeUnitContacts) {
		this.resourceHomeUnitContacts = resourceHomeUnitContacts;
	}

	/**
	 * @return the resourceHomeUnitContacts
	 */
	public Collection<ResourceHomeUnitContact> getResourceHomeUnitContacts() {
		return resourceHomeUnitContacts;
	}

	/**
	 * @param trainingContacts the trainingContacts to set
	 */
	public void setTrainingContacts(Collection<TrainingContact> trainingContacts) {
		this.trainingContacts = trainingContacts;
	}

	/**
	 * @return the trainingContacts
	 */
	public Collection<TrainingContact> getTrainingContacts() {
		return trainingContacts;
	}

	/**
	 * @param rscTrainingTrainer the rscTrainingTrainer to set
	 */
	//public void setRscTrainingTrainer(RscTrainingTrainer rscTrainingTrainer) {
	//	this.rscTrainingTrainer = rscTrainingTrainer;
	//}

	/**
	 * @return the rscTrainingTrainer
	 */
	//public RscTrainingTrainer getRscTrainingTrainer() {
	//	return rscTrainingTrainer;
	//}

	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers) {
		this.rscTrainingTrainers = rscTrainingTrainers;
	}

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers() {
		return rscTrainingTrainers;
	}   
}

