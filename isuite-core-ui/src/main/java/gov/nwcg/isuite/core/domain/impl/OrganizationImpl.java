package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.input.impl.OrganizationInputData;
import gov.nwcg.isuite.framework.types.OrganizationTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Implementation of an organization.
 * <p>
 * Organizations are read-only, that is there is no public way to create
 * Organizations.
 * </p>
 * 
 * @author doug
 * 
 */
@Entity
@SequenceGenerator(name="SEQ_ORGANIZATION", sequenceName="SEQ_ORGANIZATION")
@NamedQuery(name="org.idByUnitCode", query="select id from OrganizationImpl where unitCode = :unitCode")
@Table(name="isw_organization")
public class OrganizationImpl extends PersistableImpl implements Organization {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ORGANIZATION")
   private Long id = 0L;
   
   @Column(name="ORGANIZATION_TYPE", length=75)
   @Enumerated(EnumType.STRING)
   private OrganizationTypeEnum organizationType;
   
   // name of organization, will not be null or empty
   @Column(name="ORGANIZATION_NAME", length=255)
   private String name;
   
   @Column(name="UNIT_CODE",  length=20)
   private String unitCode;
   
   @Column(name="COUNTRY_SUBDIVISION", length=50, insertable = false, updatable = false)
   private String countrySubAbbreviation;
   
   /* GACC Organization Mappings */
   @Transient
//   @ManyToOne(targetEntity=OrganizationImpl.class)
//   @JoinColumn(name = "GACC_ORGANIZATION_ID", insertable = true, updatable = true, unique = false, nullable = true)
//   @ForeignKey(name="FK_ORG__ORG_GACC_ORG_ID")
//   @NotFound(action=NotFoundAction.IGNORE)
//   @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
   private Organization gaccOrganization;
   
   @Transient
//   @Column(name="GACC_ORGANIZATION_ID", length=19, insertable = false, updatable = false, nullable = true)
   private Long gaccOrganizationId;
   
   @Transient
//   @OneToMany(cascade=CascadeType.REMOVE, 
//            targetEntity=OrganizationImpl.class)
//   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<Organization> gaccOrganizations;
   
   /* Reporting Organization Mappings */
   @Transient
//   @ManyToOne(targetEntity=OrganizationImpl.class)
//   @JoinColumn(name = "REPORTING_ORGANIZATION_ID", insertable = true, updatable = true, unique = false, nullable = true)
//   @ForeignKey(name="FK_ORG__ORG_REPORTING_ORG_ID")
//   @NotFound(action=NotFoundAction.IGNORE)
//   @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
   private Organization reportingOrganization;
   
   @Transient
//   @Column(name="REPORTING_ORGANIZATION_ID", length=19, insertable = false, updatable = false, nullable = true)
   private Long reportingOrganizationId;
   
   @Transient
//   @OneToMany(cascade=CascadeType.REMOVE, 
//            targetEntity=OrganizationImpl.class)
//   @JoinColumn(name="REPORTING_ORGANIZATION_ID")
//   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<Organization> reportingOrganizations;
   
   @OneToOne(targetEntity=AgencyImpl.class)
   @JoinColumn(name = "AGENCY_ID", insertable = true, updatable = true, unique = false, nullable = true)
   @ForeignKey(name="FK_ORG__AGENCY_ID")
   private Agency agency;

   @Column(name="AGENCY_ID", length=19, insertable = false, updatable = false, nullable = true)
   private Long agencyId;

   @Column(name="IS_DISPATCH_CENTER")
   private Boolean dispatchCenter;
   
   @Column(name="IS_SUPPLY_CACHE")
   private Boolean supplyCache;
   
   @Column(name="IS_STANDARD")
   private Boolean standard;
   
   @Column(name="IS_LOCAL")
   private Boolean local;
   
   @ManyToMany(targetEntity=UserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizations")
   private Collection<User> users;

   @ManyToMany(targetEntity=OrganizationImpl.class,cascade = CascadeType.ALL)
   @JoinTable(
		   name = "isw_organization_pdc", 
		   joinColumns = { 
				   @JoinColumn(name = "organization_id", nullable = false, updatable = false) }, 
				   inverseJoinColumns = { @JoinColumn(name = "pdc_id", nullable = false, updatable = false) 
	       }
   )
	@BatchSize(size=300)
   private Collection<Organization> dispatchCenters;

   @ManyToMany(targetEntity=ResourceImpl.class, fetch = FetchType.LAZY, mappedBy = "nonStandardOrganizations")
   @OnDelete(action=OnDeleteAction.NO_ACTION)
   private Collection<Resource> nonStandardResources;
   
   @OneToMany(targetEntity=ResourceImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="organization")
   private Collection<Resource> resources;
   
   @OneToMany(targetEntity=IncidentImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="homeUnit")
   private Collection<Incident> incidents;
   
   @ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID")
   private Incident incident;
   
   @Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false)
   private Long incidentId;
   
   @ManyToOne(targetEntity=IncidentGroupImpl.class)
   @JoinColumn(name = "INCIDENT_GROUP_ID")
   private IncidentGroup incidentGroup;
   
   @Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false)
   private Long incidentGroupId;
   
   @Column(name = "IS_ACTIVE",nullable=false)
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum active;
   
   //@OneToMany(targetEntity=IncidentOrgImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="organization")
   @OneToMany(targetEntity=IncidentOrgImpl.class, cascade=CascadeType.ALL, mappedBy="organization")
   @BatchSize(size=300)
   private Collection<IncidentOrg> incidentOrgs;
   
   @OneToMany(targetEntity=ResourceHomeUnitContactImpl.class, fetch = FetchType.LAZY, mappedBy = "unit")
   private Collection<ResourceHomeUnitContact> resourceHomeUnitContacts = new ArrayList<ResourceHomeUnitContact>();
   
   @OneToMany(targetEntity=RscTrainingTrainerImpl.class, fetch = FetchType.LAZY, mappedBy = "unit")
   private Collection<RscTrainingTrainer> rscTrainingTrainers = new ArrayList<RscTrainingTrainer>();
   

   //default constructor
   public OrganizationImpl(){}
   
   /**
    * Full constructor
    * 
    * @param name
    *            name of organization, can not be null or empty
    */
   public OrganizationImpl(String name) {
      setName(name);
   }

   /**
    * Name constructor
    * @param data a populated {@link OrganizationInputData} object.
    */
   //public OrganizationImpl(OrganizationInputData data) {
    //  setName(data.getName());
      // other setters as needed
   //}

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Organization#getName()
    */
   public String getName() {
      return name;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setName(java.lang.String)
    */
   final public void setName(String name) {
//      if (name == null || name.length() == 0) {
//         throw new IllegalArgumentException("name can not be null or empty");
//      }
      this.name = name;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.Organization#update(gov.nwcg.isuite.domain.sync.OrganizationData)
    */
   //public final void update(OrganizationInputData data) {
      // call setters on organization based on getters from OrganizationInputData
   //}
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getUnitCode()
    */
   public String getUnitCode() {
      return this.unitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setUnitCode(java.lang.String)
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getOrganizationType()
    */
   public OrganizationTypeEnum getOrganizationType() {
      return this.organizationType;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setOrganizationType(gov.nwcg.isuite.domain.organization.impl.OrganizationType)
    */
   public void setOrganizationType(OrganizationTypeEnum type) {
      this.organizationType = type;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getGaccOrganization()
    */
   public Organization getGaccOrganization() {
      return gaccOrganization;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setGaccOrganization(gov.nwcg.isuite.domain.organization.Organization)
    */
   public void setGaccOrganization(Organization gaccOrganization) {
      this.gaccOrganization = gaccOrganization;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getGaccOrganizationId()
    */
   public Long getGaccOrganizationId() {
      return gaccOrganizationId;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setGaccOrganizationId(java.lang.Long)
    */
   public void setGaccOrganizationId(Long gaccOrganizationId) {
      this.gaccOrganizationId = gaccOrganizationId;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#getGaccOrganizations()
    */
   public Collection<Organization> getGaccOrganizations() {
      return gaccOrganizations;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.Organization#setGaccOrganizations(java.util.Collection)
    */
   public void setGaccOrganizations(Collection<Organization> gaccOrganizations) {
      this.gaccOrganizations = gaccOrganizations;
   }


   /**
    * @return the agency
    */
   public Agency getAgency() {
      return agency;
   }


   /**
    * @param agency the agency to set
    */
   public void setAgency(Agency agency) {
      this.agency = agency;
   }


   /**
    * @return the agencyId
    */
   public Long getAgencyId() {
      return agencyId;
   }


   /**
    * @param agencyId the agencyId to set
    */
   public void setAgencyId(Long agencyId) {
      this.agencyId = agencyId;
   }

   /**
    * @param countrySubAbbreviation the countrySubAbbreviation to set
    */
   public void setCountrySubAbbreviation(String countrySubAbbreviation) {
      this.countrySubAbbreviation = countrySubAbbreviation;
   }

   /**
    * @return the countrySubAbbreviation
    */
   public String getCountrySubAbbreviation() {
      return countrySubAbbreviation;
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
    * @see gov.nwcg.isuite.core.domain.Organization#isDispatchCenter()
    */
   public Boolean isDispatchCenter() {
      return dispatchCenter;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Organization#setDispatchCenter(java.lang.Boolean)
    */
   public final void setDispatchCenter(Boolean dispatchCenter) {
      this.dispatchCenter = dispatchCenter;
   }

   /**
    * Returns the users.
    *
    * @return 
    *		the users to return
    */
   public Collection<User> getUsers() {
	   return users;
   }

   /**
    * Sets the users.
    *
    * @param users 
    *			the users to set
    */
   public void setUsers(Collection<User> users) {
	   this.users = users;
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
      OrganizationImpl o = (OrganizationImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,agencyId,countrySubAbbreviation,gaccOrganizationId
      						,name,organizationType,reportingOrganizationId
      						,unitCode},
      			new Object[]{o.id,o.agencyId,o.countrySubAbbreviation,o.gaccOrganizationId
					,o.name,o.organizationType,o.reportingOrganizationId
					,o.unitCode})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,agencyId,countrySubAbbreviation,gaccOrganizationId
	  						,name,organizationType,reportingOrganizationId
	  						,unitCode})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("agencyId", agencyId)
	       .append("countrySubAbbreviation", countrySubAbbreviation)
	       .append("gaccOrganizationId",gaccOrganizationId)
	       .append("name", name)
	       .append("organizationType", organizationType)
	       .append("reportingOrganizationId", reportingOrganizationId)
	       .append("unitCode", unitCode)
	       .appendSuper(super.toString())
	       .toString();
   }

   /**
    * Returns the dispatchCenters.
    *
    * @return 
    *		the dispatchCenters to return
    */
   public Collection<Organization> getDispatchCenters() {
	   return dispatchCenters;
   }

   /**
    * Sets the dispatchCenters.
    *
    * @param dispatchCenters 
    *			the dispatchCenters to set
    */
   public void setDispatchCenters(Collection<Organization> dispatchCenters) {
	   this.dispatchCenters = dispatchCenters;
   }

   /**
	* @param nonStandardResources the nonStandardResources to set
	*/
   public void setNonStandardResources(Collection<Resource> nonStandardResources) {
	   this.nonStandardResources = nonStandardResources;
   }
	
   /**
	* @return the nonStandardResources
	*/
   public Collection<Resource> getNonStandardResources() {
	   return nonStandardResources;
   }

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#setSupplyCache(java.lang.Boolean)
	 */
	public void setSupplyCache(Boolean supplyCache) {
		this.supplyCache = supplyCache;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#isSupplyCache()
	 */
	public Boolean isSupplyCache() {
		return supplyCache;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#isStandard()
	 */
	public Boolean isStandard() {
		return standard;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#isLocal()
	 */
	public Boolean isLocal() {
		return local;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#setLocal(java.lang.Boolean)
	 */
	public void setLocal(Boolean local) {
		this.local = local;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#setResources(java.util.Collection)
	 */
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#getResources()
	 */
	public Collection<Resource> getResources() {
		return resources;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#setIncidents(java.util.Collection)
	 */
	public void setIncidents(Collection<Incident> incidents) {
		this.incidents = incidents;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Organization#getIncidents()
	 */
	public Collection<Incident> getIncidents() {
		return incidents;
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

	/**
	 * @param incidentOrgs the incidentOrgs to set
	 */
	public void setIncidentOrgs(Collection<IncidentOrg> incidentOrgs) {
		this.incidentOrgs = incidentOrgs;
	}

	/**
	 * @return the incidentOrgs
	 */
	public Collection<IncidentOrg> getIncidentOrgs() {
		return incidentOrgs;
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
