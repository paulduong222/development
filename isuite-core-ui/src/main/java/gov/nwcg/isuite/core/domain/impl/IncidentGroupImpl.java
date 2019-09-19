package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class IncidentGroupImpl.
 */
@Entity
@SequenceGenerator(name="SEQ_INCIDENT_GROUP", sequenceName="SEQ_INCIDENT_GROUP")
@Table(name="isw_incident_group")
public class IncidentGroupImpl extends PersistableImpl implements IncidentGroup {
   
   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_GROUP")
   private Long id = 0L;
   
   @Column(name="GROUP_NAME", length=65, unique=true)
   private String groupName;
   
   @Column(name="COST_AUTORUN")
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum costAutoRun = StringBooleanEnum.N;
   
   @ManyToMany(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinTable(name="isw_incident_group_incident", 
      joinColumns={ @JoinColumn(name="INCIDENT_GROUP_ID") },
      inverseJoinColumns= { @JoinColumn(name="INCIDENT_ID") })
	@OnDelete(action=OnDeleteAction.NO_ACTION)
   //@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<Incident> incidents;
   
   @OneToMany(targetEntity=IncidentGroupUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<IncidentGroupUser> incidentGroupUsers = new ArrayList<IncidentGroupUser>();

   @OneToMany(targetEntity=IncidentGroupQuestionImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   @OrderBy("position")
   private Collection<IncidentGroupQuestion> incidentGroupQuestions;
   
   @OneToMany(targetEntity=IncidentGroupPrefsImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
   @OrderBy("position")
   private Collection<IncidentGroupPrefs> incidentGroupPrefs = new ArrayList<IncidentGroupPrefs>();
   
   @OneToMany(targetEntity=IncidentGroupQSKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<IncidentGroupQSKind> incidentGroupQSKinds;
   
	@Column (name= "COST_DEFAULT_HOURS")
	private Integer incidentGroupCostDefaultHours;

	@Column(name = "IAP_PERSON_NAME_ORDER")
	private Short iapPersonNameOrder;
	
	@Column(name = "INCLUDE_FILLED", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum includeFilled;
	
	@Column(name = "IAP_RESOURCE_TO_DISPLAY_FROM")
	private Short iapResourceToDisplayFrom;

	@Column(name = "IAP_TREEVIEW_DISPLAY")
	private Short iapTreeviewDisplay;
	
	@Column(name = "BY_DATE")
	private Date byDate;
	
	@Column(name = "NBR_OF_DAYS_PRIOR")
	private Short nbrOfDaysPrior;
	
	@OneToMany(targetEntity=IncidentCostRateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<IncidentCostRate> incidentCostRates;
	
	@OneToMany(targetEntity=Sit209Impl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<Sit209> sit209s = new ArrayList<Sit209>();
	
	@OneToMany(targetEntity=AgencyImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<Agency> agencies = new ArrayList<Agency>();
	
	@OneToMany(targetEntity=AgencyGroupImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<AgencyGroup> agencyGroups = new ArrayList<AgencyGroup>();
	
	@OneToMany(targetEntity=OrganizationImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<Organization> organizations = new ArrayList<Organization>();
	
	@OneToMany(targetEntity=JetPortImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<JetPort> jetPorts = new ArrayList<JetPort>();
	
	@OneToMany(targetEntity=KindImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<Kind> kinds = new ArrayList<Kind>();
	
	@OneToMany(targetEntity=SubGroupCategoryImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<SubGroupCategory> subGroupCategories = new ArrayList<SubGroupCategory>();
	
	@OneToMany(targetEntity=ProjectionImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<Projection> projections;
	
	@OneToMany(targetEntity=IapMasterFrequencyImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<IapMasterFrequency> iapMasterFrequencies = new ArrayList<IapMasterFrequency>();
	
	@OneToMany(targetEntity=IapPositionItemCodeImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<IapPositionItemCode> iapPositionItemCodes = new ArrayList<IapPositionItemCode>();

	@OneToMany(targetEntity=CostAccrualExtractImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentGroup")
	private Collection<CostAccrualExtract> costAccrualExtracts;

	@OneToMany(targetEntity=BranchSettingImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
    @OrderBy("positionNum")
	private Collection<BranchSetting> branchSettings = new ArrayList<BranchSetting>();
	
	@Column(name="PRIMARY_INCIDENT_ID")
	private Long primaryIncidentId;

	@Column(name="SEQUENCE_NUMBER")
	private Short accrualExtractNumber;

	@OneToOne(targetEntity=IncidentPrefsOtherFieldsImpl.class,cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="incidentGroup")
	private IncidentPrefsOtherFields incidentPrefsOtherFields;

	@Column(name = "IS_SITE_MANAGED", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isSiteManaged;
	
	@OneToMany(targetEntity=FinancialExportImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incidentGroup")
	private Collection<FinancialExport> financialExports = new ArrayList<FinancialExport>();
	
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

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentGroup#getGroupName()
    */
   public String getGroupName() {
      return this.groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentGroup#setGroupName(java.lang.String)
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentGroup#getIncidents()
    */
   public Collection<Incident> getIncidents() {
	   if(null==this.incidents)
		   incidents = new ArrayList<Incident>();
      return this.incidents;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentGroup#setIncidents(java.util.Collection)
    */
   public void setIncidents(Collection<Incident> incidents) {
      this.incidents = incidents;
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
      IncidentGroupImpl o = (IncidentGroupImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,groupName},
      			new Object[]{o.id,o.groupName})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,groupName})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("groupName", groupName)
	       .appendSuper(super.toString())
	       .toString();
   }

   public Collection<IncidentGroupUser> getIncidentGroupUsers() {
	   if(null==incidentGroupUsers)
		   incidentGroupUsers = new ArrayList<IncidentGroupUser>();
	   return incidentGroupUsers;
   }

   public void setIncidentGroupUsers(
		   Collection<IncidentGroupUser> incidentGroupUsers) {
	   this.incidentGroupUsers = incidentGroupUsers;
   }

	/**
	 * @return the incidentGroupQuestions
	 */
	public Collection<IncidentGroupQuestion> getIncidentGroupQuestions() {
		return incidentGroupQuestions;
	}
	
	/**
	 * @param incidentGroupQuestions the incidentGroupQuestions to set
	 */
	public void setIncidentGroupQuestions(
			Collection<IncidentGroupQuestion> incidentGroupQuestions) {
		this.incidentGroupQuestions = incidentGroupQuestions;
	}

	/**
	 * @return the incidentGroupPrefs
	 */
	public Collection<IncidentGroupPrefs> getIncidentGroupPrefs() {
		return incidentGroupPrefs;
	}

	/**
	 * @param incidentGroupPrefs the incidentGroupPrefs to set
	 */
	public void setIncidentGroupPrefs(
			Collection<IncidentGroupPrefs> incidentGroupPrefs) {
		this.incidentGroupPrefs = incidentGroupPrefs;
	}

	/**
	 * @return the incidentGroupQSKinds
	 */
	public Collection<IncidentGroupQSKind> getIncidentGroupQSKinds() {
		return incidentGroupQSKinds;
	}

	/**
	 * @param incidentGroupQSKinds the incidentGroupQSKinds to set
	 */
	public void setIncidentGroupQSKinds(
			Collection<IncidentGroupQSKind> incidentGroupQSKinds) {
		this.incidentGroupQSKinds = incidentGroupQSKinds;
	}   
	
	/**
	    * @return the incident cost default hours
	*/
	public Integer getIncidentGroupCostDefaultHours() {
		return this.incidentGroupCostDefaultHours;
	}
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentGroupCostDefaultHours(Integer incidentGroupCostDefaultHours) {
		this.incidentGroupCostDefaultHours = incidentGroupCostDefaultHours;
	}

	/**
	 * @return the costAutoRun
	 */
	public StringBooleanEnum getCostAutoRun() {
		return this.costAutoRun;
	}

	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(StringBooleanEnum costAutoRun) {
		this.costAutoRun = costAutoRun;
	}
	
	
	/**
	 * @return the incidentCostRates
	 */
	public Collection<IncidentCostRate> getIncidentCostRates() {
		return incidentCostRates;
	}

	/**
	 * @param incidentCostRates the incidentCostRates to set
	 */
	public void setIncidentCostRates(Collection<IncidentCostRate> incidentCostRates) {
		this.incidentCostRates = incidentCostRates;
	}

	/**
	 * @param sit209s the sit209s to set
	 */
	public void setSit209s(Collection<Sit209> sit209s) {
		this.sit209s = sit209s;
	}

	/**
	 * @return the sit209s
	 */
	public Collection<Sit209> getSit209s() {
		return sit209s;
	}
	
	/**
	 * @param agencies the agencies to set
	 */
	public void setAgencies(Collection<Agency> agencies) {
		this.agencies = agencies;
	}

	/**
	 * @return the agencies
	 */
	public Collection<Agency> getAgencies() {
		return agencies;
	}

	/**
	 * @param agencyGroups the agencyGroups to set
	 */
	public void setAgencyGroups(Collection<AgencyGroup> agencyGroups) {
		this.agencyGroups = agencyGroups;
	}

	/**
	 * @return the agencyGroups
	 */
	public Collection<AgencyGroup> getAgencyGroups() {
		return agencyGroups;
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
	 * @param jetPorts the jetPorts to set
	 */
	public void setJetPorts(Collection<JetPort> jetPorts) {
		this.jetPorts = jetPorts;
	}

	/**
	 * @return the jetPorts
	 */
	public Collection<JetPort> getJetPorts() {
		return jetPorts;
	}

	/**
	 * @param kinds the kinds to set
	 */
	public void setKinds(Collection<Kind> kinds) {
		this.kinds = kinds;
	}

	/**
	 * @return the kinds
	 */
	public Collection<Kind> getKinds() {
		return kinds;
	}

	/**
	 * @param subGroupCategories the subGroupCategories to set
	 */
	public void setSubGroupCategories(Collection<SubGroupCategory> subGroupCategories) {
		this.subGroupCategories = subGroupCategories;
	}

	/**
	 * @return the subGroupCategories
	 */
	public Collection<SubGroupCategory> getSubGroupCategories() {
		return subGroupCategories;
	}

	/**
	 * @return the projections
	 */
	public Collection<Projection> getProjections() {
		return projections;
	}

	/**
	 * @param projections the projections to set
	 */
	public void setProjections(Collection<Projection> projections) {
		this.projections = projections;
	}

	/**
	 * @return the iapPersonNameOrder
	 */
	public Short getIapPersonNameOrder() {
		return iapPersonNameOrder;
	}

	/**
	 * @param iapPersonNameOrder the iapPersonNameOrder to set
	 */
	public void setIapPersonNameOrder(Short iapPersonNameOrder) {
		this.iapPersonNameOrder = iapPersonNameOrder;
	}

	/**
	 * @return the includeFilled
	 */
	public StringBooleanEnum getIncludeFilled() {
		return includeFilled;
	}

	/**
	 * @param includeFilled the includeFilled to set
	 */
	public void setIncludeFilled(StringBooleanEnum includeFilled) {
		this.includeFilled = includeFilled;
	}

	/**
	 * @return the iapResourceToDisplayFrom
	 */
	public Short getIapResourceToDisplayFrom() {
		return iapResourceToDisplayFrom;
	}

	/**
	 * @param iapResourceToDisplayFrom the iapResourceToDisplayFrom to set
	 */
	public void setIapResourceToDisplayFrom(Short iapResourceToDisplayFrom) {
		this.iapResourceToDisplayFrom = iapResourceToDisplayFrom;
	}

	/**
	 * @return the iapMasterFrequencies
	 */
	public Collection<IapMasterFrequency> getIapMasterFrequencies() {
		return iapMasterFrequencies;
	}

	/**
	 * @param iapMasterFrequencies the iapMasterFrequencies to set
	 */
	public void setIapMasterFrequencies(
			Collection<IapMasterFrequency> iapMasterFrequencies) {
		this.iapMasterFrequencies = iapMasterFrequencies;
	}

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes() {
		return iapPositionItemCodes;
	}

	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(
			Collection<IapPositionItemCode> iapPositionItemCodes) {
		this.iapPositionItemCodes = iapPositionItemCodes;
	}

	/**
	 * @return the costAccrualExtracts
	 */
	public Collection<CostAccrualExtract> getCostAccrualExtracts() {
		return costAccrualExtracts;
	}

	/**
	 * @param costAccrualExtracts the costAccrualExtracts to set
	 */
	public void setCostAccrualExtracts(
			Collection<CostAccrualExtract> costAccrualExtracts) {
		this.costAccrualExtracts = costAccrualExtracts;
	}

	/**
	 * @return the primaryIncidentId
	 */
	public Long getPrimaryIncidentId() {
		return primaryIncidentId;
	}

	/**
	 * @param primaryIncidentId the primaryIncidentId to set
	 */
	public void setPrimaryIncidentId(Long primaryIncidentId) {
		this.primaryIncidentId = primaryIncidentId;
	}

	/**
	 * @return the accrualExtractNumber
	 */
	public Short getAccrualExtractNumber() {
		return accrualExtractNumber;
	}

	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Short accrualExtractNumber) {
		this.accrualExtractNumber = accrualExtractNumber;
	}

	/**
	 * @return the branchSettings
	 */
	public Collection<BranchSetting> getBranchSettings() {
		return branchSettings;
	}

	/**
	 * @param branchSettings the branchSettings to set
	 */
	public void setBranchSettings(Collection<BranchSetting> branchSettings) {
		this.branchSettings = branchSettings;
	}

	/**
	 * @return the incidentPrefsOtherFields
	 */
	public IncidentPrefsOtherFields getIncidentPrefsOtherFields() {
		return incidentPrefsOtherFields;
	}

	/**
	 * @param incidentPrefsOtherFields the incidentPrefsOtherFields to set
	 */
	public void setIncidentPrefsOtherFields(
			IncidentPrefsOtherFields incidentPrefsOtherFields) {
		this.incidentPrefsOtherFields = incidentPrefsOtherFields;
	}
	
	/**
	 * @return the isSiteManaged
	 */
	public StringBooleanEnum getIsSiteManaged() {
		return isSiteManaged;
	}

	/**
	 * @param isSiteManaged the isSiteManaged to set
	 */
	public void setIsSiteManaged(StringBooleanEnum isSiteManaged) {
		this.isSiteManaged = isSiteManaged;
	}

	/**
	 * @param financialExports the financialExports to set
	 */
	public void setFinancialExports(Collection<FinancialExport> financialExports) {
		this.financialExports = financialExports;
	}

	/**
	 * @return the financialExports
	 */
	public Collection<FinancialExport> getFinancialExports() {
		return financialExports;
	}

	/**
	 * @return the iapTreeviewDisplay
	 */
	public Short getIapTreeviewDisplay() {
		return iapTreeviewDisplay;
	}

	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(Short iapTreeviewDisplay) {
		this.iapTreeviewDisplay = iapTreeviewDisplay;
	}

	/**
	 * @return the byDate
	 */
	public Date getByDate() {
		return byDate;
	}

	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate) {
		this.byDate = byDate;
	}

	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior() {
		return nbrOfDaysPrior;
	}

	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior) {
		this.nbrOfDaysPrior = nbrOfDaysPrior;
	}

}
