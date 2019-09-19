package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

/**
 * Object Relational Mapping to a Work Area.
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_WORK_AREA", sequenceName="SEQ_WORK_AREA")
@Table(name="isw_work_area")
public class WorkAreaImpl extends PersistableImpl implements WorkArea {
   
   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_WORK_AREA")
   private Long id = 0L;

   @Column(name="NAME", length=75, nullable=false, unique=true)
   private String name;
   
   @Column(name="DESCRIPTION", length=200, nullable=false)
   private String description;
      
   @Column(name="SHARED_OUT_FLG")
   private Boolean sharedOut = Boolean.FALSE;
   
   @OneToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="STANDARD_ORGANIZATION_ID", insertable=true, updatable=true, unique=false, nullable=true)
   @ForeignKey(name="FK_WA__ORGANIZATION_ID")
   private Organization standardOrganization;
   
   /* The ID of an Organization if this is a Standard Work Area */
   @Column(name="STANDARD_ORGANIZATION_ID", insertable=false, updatable=false, unique=false, nullable=true)
   private Long standardOrganizationId;
   
   @OneToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=true)
   @ForeignKey(name="FK_WA__USER_ID")
   private User user;
   
   /* The ID of a user if this is a Custom Work Area */
   @Column(name="USER_ID", insertable=false, updatable=false, unique=false, nullable=true)
   private Long userId;
   
   @ManyToMany(targetEntity=IncidentImpl.class,cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
   @JoinTable(name="isw_work_area_incident",
      joinColumns={ @JoinColumn(name="WORK_AREA_ID") },
      inverseJoinColumns= { @JoinColumn(name="INCIDENT_ID") })
   private Collection<Incident> workAreaIncidents;
   
   @ManyToMany(targetEntity=ResourceImpl.class, cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
   @JoinTable(name="isw_work_area_resource", 
      joinColumns={ @JoinColumn(name="WORK_AREA_ID") },
      inverseJoinColumns= { @JoinColumn(name="RESOURCE_ID") })
   private Collection<Resource> workAreaResources;
   
   @ManyToMany(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
   @JoinTable(name="isw_work_area_organization", 
      joinColumns={ @JoinColumn(name="WORK_AREA_ID") },
      inverseJoinColumns= { @JoinColumn(name="ORGANIZATION_ID", nullable = false, updatable = false) })
   @OrderBy("unitCode")
   private Collection<Organization> filteredOrganizations;
   
   @OneToMany(targetEntity=WorkAreaUserImpl.class, cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "workArea")
   private Collection<WorkAreaUser> workAreaUsers;
   
	@ManyToMany(targetEntity=IncidentImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_work_area_incident", 
				joinColumns = {@JoinColumn(name = "work_area_id", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "incident_id", nullable = false, updatable = false) })
   private Collection<Incident> incidents;
   
   /**
    * Standard Constructor
    */
   public WorkAreaImpl() {
      super();
   }
  
   /**
    * Copy Constructor
    *
    * @param workAreaImpl a <code>WorkAreaImpl</code> object
    */
   public WorkAreaImpl(WorkArea workAreaToCopy) 
   {
      super();
      // Create a new Work Area.
      setId(0L);
      
      // copied work area cannot be restricted or shared out.
      this.sharedOut = Boolean.FALSE;
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
    * @return the filteredOrganizations
    */
   public Collection<Organization> getFilteredOrganizations() {
      return filteredOrganizations;
   }

   /**
    * @param filteredOrganizations the filteredOrganizations to set
    */
   public void setFilteredOrganizations(Collection<Organization> filteredOrganizations) {
      this.filteredOrganizations = filteredOrganizations;
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

    /**
    * @return the sharedOut
    */
   public Boolean isSharedOut() {
      return sharedOut;
   }

   /**
    * @param sharedOut the sharedOut to set
    */
   public void setSharedOut(Boolean sharedOut) {
      this.sharedOut = sharedOut;
   }

   /**
    * @return the workAreaIncidents
    */
   public Collection<Incident> getWorkAreaIncidents() {
      return workAreaIncidents;
   }

   /**
    * @param workAreaIncidents the workAreaIncidents to set
    */
   public void setWorkAreaIncidents(Collection<Incident> workAreaIncidents) {
      this.workAreaIncidents = workAreaIncidents;
   }

   /**
    * @return the workAreaResources
    */
   public Collection<Resource> getWorkAreaResources() {
	  if(null==workAreaResources)
		  workAreaResources=new ArrayList<Resource>();
	  
      return workAreaResources;
   }

   /**
    * @param workAreaResources the workAreaResources to set
    */
   public void setWorkAreaResources(Collection<Resource> workAreaResources) {
      this.workAreaResources = workAreaResources;
   }

   /**
    * @return the standardOrganization
    */
   public Organization getStandardOrganization() {
      return standardOrganization;
   }

   /**
    * @param standardOrganization the standardOrganization to set
    */
   public void setStandardOrganization(Organization standardOrganization) {
      this.standardOrganization = standardOrganization;
   }

   /**
    * @return the standardOrganizationId
    */
   public Long getStandardOrganizationId() {
      return standardOrganizationId;
   }

   /**
    * @param standardOrganizationId the standardOrganizationId to set
    */
   public void setStandardOrganizationId(Long standardOrganizationId) {
      this.standardOrganizationId = standardOrganizationId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#getWorkAreaUsers()
    */
   public Collection<WorkAreaUser> getWorkAreaUsers() {
      return this.workAreaUsers;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#setWorkAreaUsers(java.util.Collection)
    */
   public void setWorkAreaUsers(Collection<WorkAreaUser> workAreaUsers) {
      this.workAreaUsers = workAreaUsers;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#getUser()
    */
   public User getUser() {
      return this.user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#setUser(gov.nwcg.isuite.domain.access.User)
    */
   public void setUser(User user) {
      this.user = user;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#getUserId()
    */
   public Long getUserId() {
      return userId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkArea#setUserId(java.lang.Long)
    */
   public void setUserId(Long userId) {
      this.userId = userId;
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
      WorkAreaImpl o = (WorkAreaImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,description,name,sharedOut,standardOrganizationId,userId},
      			new Object[]{o.id,o.description,o.name,o.sharedOut,o.standardOrganizationId,o.userId})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,description,name,sharedOut,standardOrganizationId,userId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("description", description)
	       .append("name", name)
	       .append("sharedOut",sharedOut)
	       .append("standardOrganizationId",standardOrganizationId)
	       .append("userId",userId)
	       .appendSuper(super.toString())
	       .toString();
   }

	/**
	 * Returns the incidents.
	 *
	 * @return 
	 *		the incidents to return
	 */
	public Collection<Incident> getIncidents() {
		return incidents;
	}
	
	/**
	 * Sets the incidents.
	 *
	 * @param incidents 
	 *			the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents) {
		this.incidents = incidents;
	}   

}
