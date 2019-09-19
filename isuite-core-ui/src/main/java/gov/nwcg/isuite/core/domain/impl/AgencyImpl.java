package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RateGroup;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author mpoll
 * 
 */
@Entity
@Table(name = "iswl_agency")
@SequenceGenerator(name="SEQ_AGENCY", sequenceName="SEQ_AGENCY")
@NamedQueries(value = {
         @NamedQuery(name = "agency.getWildlandFireAgencies", query = "SELECT AG.id as id, AG.agencyType as theAgencyType, AG.agencyCode as agencyCd, AG.agencyName as agencyNm FROM AgencyImpl AG WHERE AG.agencyCode = :fed OR AG.agencyType != :federal"),
         @NamedQuery(name = "agency.getNonWildlandFireAgencies", query = "SELECT AG.id as id, AG.agencyType as theAgencyType, AG.agencyCode as agencyCd, AG.agencyName as agencyNm FROM AgencyImpl AG WHERE AG.agencyCode != :fed")
})
public class AgencyImpl extends PersistableImpl implements Agency {

   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_AGENCY")
   private Long id = 0L;
   
   @Column(name = "AGENCY_CODE", length = 4)
   private String agencyCode;

   @Column(name = "AGENCY_NAME", length = 75)
   private String agencyName;

   @Enumerated(EnumType.STRING)
   @Column(name = "AGENCY_TYPE", length = 10)
   private AgencyTypeEnum agencyType;

   @OneToMany(targetEntity=CostDataImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paymentAgency")
   private Collection<CostData> costDatas = new ArrayList<CostData>();

   @OneToMany(targetEntity=CostGroupAgencyDaySharePercentageImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agency")
   private Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages = new ArrayList<CostGroupAgencyDaySharePercentage>();

   @Column(name = "IS_STANDARD")
   private Boolean standard;

   @Column(name = "IS_STATE")
   private Boolean state;
   
   @ManyToOne(targetEntity=AgencyGroupImpl.class, fetch=FetchType.EAGER)
   @JoinColumn(name="AGENCY_GROUP_ID", insertable=true, updatable=true)
   private AgencyGroup agencyGroup;
   
   @Column(name="AGENCY_GROUP_ID", insertable=false, updatable=false)
   private Long agencyGroupId;
   
   @ManyToOne(targetEntity=RateGroupImpl.class, fetch=FetchType.EAGER)
   @JoinColumn(name="RATE_GROUP_ID", insertable=true, updatable=true)
   private RateGroup rateGroup;
   
   @Column(name="RATE_GROUP_ID", insertable=false, updatable=false)
   private Long rateGroupId;
   
   @OneToMany(targetEntity=AccountCodeImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<AccountCode> accountCodes;
   
   @OneToMany(targetEntity=IncidentImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<Incident> incidents;
   
   @OneToMany(targetEntity=OrganizationImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<Organization> organizations;
   
   @OneToMany(targetEntity=ResourceImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<Resource> resources;
   
   @OneToMany(targetEntity=ResourceOtherImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<ResourceOther> resourceOthers;
   
   @OneToMany(targetEntity=IncidentCostRateStateImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<IncidentCostRateState> incidentCostRateStates;
   
   @OneToMany(targetEntity=SysCostRateStateImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="agency")
   private Collection<SysCostRateState> sysCostRateStates;
   
   @OneToMany(targetEntity=IapPositionItemCodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agency")
   private Collection<IapPositionItemCode> iapPositionItemCodes = new ArrayList<IapPositionItemCode>();
   
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
   
   @OneToMany(targetEntity=CostGroupDefaultAgencyDaySharePercentageImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agency")
   private Collection<CostGroupAgencyDaySharePercentage> costGroupDefaultAgencyDaySharePercentages; 
   
   public AgencyImpl(String agencyCode, String agencyName, AgencyTypeEnum agencyType) {
      super();
      if ( agencyCode == null ) {
         throw new IllegalArgumentException("Agency Code cannot be null.");
      }
      this.agencyCode = agencyCode;

      if ( agencyName == null ) {
         throw new IllegalArgumentException("Agency Name cannot be null.");
      }
      this.agencyName = agencyName;

      if ( agencyType == null ) {
         throw new IllegalArgumentException("Agency Type cannot be null.");
      }
      this.agencyType = agencyType;
   }

   /**
    * Default constructor.
    * 
    */
   public AgencyImpl() {
      super();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Agency#getAgencyCode()
    */
   public String getAgencyCode() {
      return this.agencyCode;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Agency#setAgencyCode(java.lang.String)
    */
   public void setAgencyCode(String agencyCode) {
      this.agencyCode = agencyCode;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Agency#getAgencyType()
    */
   public AgencyTypeEnum getAgencyType() {
      return this.agencyType;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Agency#setAgencyType(gov.nwcg.isuite.domain.access.AgencyTypeEnum)
    */
   public void setAgencyType(AgencyTypeEnum agencyType) {
      this.agencyType = agencyType;
   }

   /**
    * @return the agencyName
    */
   public String getAgencyName() {
      return this.agencyName;
   }

   /**
    * @param agencyName
    *           the agencyName to set
    */
   public void setAgencyName(String agencyName) {
      this.agencyName = agencyName;
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
      AgencyImpl o = (AgencyImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,agencyCode,agencyName,agencyType},
      			new Object[]{o.id,o.agencyCode,o.agencyName,o.agencyType})
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
	  	.append(agencyCode)
	  	.append(agencyType)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("agencyCode", agencyCode)
	       .append("agencyType", agencyType)
	       .appendSuper(super.toString())
	       .toString();
   }

   /**
    * @return the costDatas
    */
   public Collection<CostData> getCostDatas() {
	   return costDatas;
   }

   /**
    * @param costDatas the costDatas to set
    */
   public void setCostDatas(Collection<CostData> costDatas) {
	   this.costDatas = costDatas;
   }

	/**
	 * @return the costGroupAgencyDaySharePercentages
	 */
	public Collection<CostGroupAgencyDaySharePercentage> getCostGroupAgencyDaySharePercentages() {
		return costGroupAgencyDaySharePercentages;
	}
	
	/**
	 * @param costGroupAgencyDaySharePercentages the costGroupAgencyDaySharePercentages to set
	 */
	public void setCostGroupAgencyDaySharePercentages(Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages) {
		this.costGroupAgencyDaySharePercentages = costGroupAgencyDaySharePercentages;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Agency#getStandard()
	 */
	public Boolean getStandard() {
		return standard;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Agency#isStandard()
	 */
	public Boolean isStandard() {
		return standard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Agency#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @param agencyGroup the agencyGroup to set
	 */
	public void setAgencyGroup(AgencyGroup agencyGroup) {
		this.agencyGroup = agencyGroup;
	}

	/**
	 * @return the agencyGroup
	 */
	public AgencyGroup getAgencyGroup() {
		return agencyGroup;
	}

	/**
	 * @param agencyGroupId the agencyGroupId to set
	 */
	public void setAgencyGroupId(Long agencyGroupId) {
		this.agencyGroupId = agencyGroupId;
	}

	/**
	 * @return the agencyGroupId
	 */
	public Long getAgencyGroupId() {
		return agencyGroupId;
	}

	/**
	 * @param rateGroup the rateGroup to set
	 */
	public void setRateGroup(RateGroup rateGroup) {
		this.rateGroup = rateGroup;
	}

	/**
	 * @return the rateGroup
	 */
	public RateGroup getRateGroup() {
		return rateGroup;
	}

	/**
	 * @param rateGroupId the rateGroupId to set
	 */
	public void setRateGroupId(Long rateGroupId) {
		this.rateGroupId = rateGroupId;
	}

	/**
	 * @return the rateGroupId
	 */
	public Long getRateGroupId() {
		return rateGroupId;
	}

	/**
	 * @param accountCodes the accountCodes to set
	 */
	public void setAccountCodes(Collection<AccountCode> accountCodes) {
		this.accountCodes = accountCodes;
	}

	/**
	 * @return the accountCodes
	 */
	public Collection<AccountCode> getAccountCodes() {
		return accountCodes;
	}

	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents) {
		this.incidents = incidents;
	}

	/**
	 * @return the incidents
	 */
	public Collection<Incident> getIncidents() {
		return incidents;
	}

	/**
	 * @param organizations the organizations to set
	 */
	public void setOrganizations(Collection<Organization> organizations) {
		this.organizations = organizations;
	}

	/**
	 * @return the organizations
	 */
	public Collection<Organization> getOrganizations() {
		return organizations;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources() {
		return resources;
	}

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers) {
		this.resourceOthers = resourceOthers;
	}

	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() {
		return resourceOthers;
	}

	/**
	 * @param incidentCostRateStates the incidentCostRateStates to set
	 */
	public void setIncidentCostRateStates(Collection<IncidentCostRateState> incidentCostRateStates) {
		this.incidentCostRateStates = incidentCostRateStates;
	}

	/**
	 * @return the incidentCostRateStates
	 */
	public Collection<IncidentCostRateState> getIncidentCostRateStates() {
		return incidentCostRateStates;
	}

	/**
	 * @param sysCostRateStates the sysCostRateStates to set
	 */
	public void setSysCostRateStates(Collection<SysCostRateState> sysCostRateStates) {
		this.sysCostRateStates = sysCostRateStates;
	}

	/**
	 * @return the sysCostRateStates
	 */
	public Collection<SysCostRateState> getSysCostRateStates() {
		return sysCostRateStates;
	}

	/**
	 * @return the state
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Boolean state) {
		this.state = state;
	}

	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes) {
		this.iapPositionItemCodes = iapPositionItemCodes;
	}

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes() {
		return iapPositionItemCodes;
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
	 * @return the costGroupDefaultAgencyDaySharePercentages
	 */
	public Collection<CostGroupAgencyDaySharePercentage> getCostGroupDefaultAgencyDaySharePercentages() {
		return costGroupDefaultAgencyDaySharePercentages;
	}

	/**
	 * @param costGroupDefaultAgencyDaySharePercentages the costGroupDefaultAgencyDaySharePercentages to set
	 */
	public void setCostGroupDefaultAgencyDaySharePercentages(
			Collection<CostGroupAgencyDaySharePercentage> costGroupDefaultAgencyDaySharePercentages) {
		this.costGroupDefaultAgencyDaySharePercentages = costGroupDefaultAgencyDaySharePercentages;
	}
   
}
