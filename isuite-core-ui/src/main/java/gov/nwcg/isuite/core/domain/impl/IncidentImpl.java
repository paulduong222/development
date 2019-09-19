package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentBranch;
import gov.nwcg.isuite.core.domain.IncidentCostDefaults;
import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents an incident.
 */
@Entity
@SequenceGenerator(name="SEQ_INCIDENT", sequenceName="SEQ_INCIDENT")
@Table(name="isw_incident")
public class IncidentImpl extends PersistableImpl implements Incident {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT")
	private Long id = 0L;

	@Column(name="INCIDENT_NAME", length=50)
	private String incidentName;

	@Column(name="INCIDENT_DESCRIPTION", length=1024)
	private String incidentDescription;

	@OneToOne(targetEntity=CountrySubdivisionImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_SUBDIVISION_ID", insertable = true, updatable = true, unique = false, nullable = true)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private CountrySubdivision countrySubdivision;

	@Column(name="COUNTRY_SUBDIVISION_ID", insertable = false, updatable = false, nullable = true)
	private Long countrySubdivisionId;

	@Column(name = "NBR")
	private String nbr;

	@OneToOne(targetEntity=AgencyImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID", insertable = true, updatable = true, unique = false, nullable = false)
	@ForeignKey(name="FK_INCIDENT__AGENCY_ID")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private Agency agency;

	@Column(name="AGENCY_ID", insertable = false, updatable = false, nullable = false)
	private Long agencyId;

	@Column(name="LOCATION", length=15)
	private String location;

	@Column(name="LATITUDE", length=20)
	private String latitude;

	@Column(name="LONGITUDE", length=20)
	private String longitude;

	@Column(name = "INCIDENT_START_DATE")
	private Date incidentStartDate;

	@Column(name = "INCIDENT_END_DATE")
	private Date incidentEndDate;

	@Column(name = "INCIDENT_YEAR")
	private Integer incidentYear;

	@OneToOne(targetEntity=EventTypeImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "EVENT_TYPE_ID", insertable = true, updatable = true, unique = false, nullable = false)
	@ForeignKey(name="FK_INCIDENT__EVENT_TYPE_ID")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private EventType eventType;

	@Column(name="EVENT_TYPE_ID", insertable = false, updatable = false, nullable = false)
	private Long eventTypeId;

	@OneToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	@ForeignKey(name="FK_INCIDENT__ORGANIZATION_ID")
	private Organization homeUnit;

	@Column(name="UNIT_ID", insertable = false, updatable = false, nullable = false)
	private Long homeUnitId;

	@OneToOne(targetEntity = OrganizationImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PDC_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Organization pdc;

	@Column(name = "PDC_ID", length = 19, insertable = false, updatable = false)
	private Long pdcId;
	
	@OneToMany(targetEntity=IncidentAccountCodeImpl.class,fetch=FetchType.LAZY,cascade=CascadeType.MERGE,mappedBy="incident")
	private Collection<IncidentAccountCode> incidentAccountCodes;

	@OneToMany(targetEntity=IncidentShiftImpl.class,fetch=FetchType.LAZY,cascade=CascadeType.MERGE,mappedBy="incident")
	private Collection<IncidentShift> incidentShifts;

	@ManyToMany(targetEntity=ResourceImpl.class, fetch=FetchType.LAZY)
	@JoinTable(name="isw_incident_resource", 
			joinColumns={ @JoinColumn(name="INCIDENT_ID") },
			inverseJoinColumns= { @JoinColumn(name="RESOURCE_ID") })
			private Collection<Resource> resources;

	@OneToMany(targetEntity=RestrictedIncidentUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<RestrictedIncidentUser> restrictedIncidentUsers;

	@Column(name="RESTRICTED_FLG", length=1, nullable = true)
	private Boolean restricted;

	@ManyToMany(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY, mappedBy = "incidents")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private Collection<IncidentGroup> incidentGroups;

	@OneToOne(targetEntity=IncidentPrefsOtherFieldsImpl.class,cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="incident")
	private IncidentPrefsOtherFields incidentPrefsOtherFields;

	@OneToOne(targetEntity=RateClassImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "RATE_CLASS_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private RateClass rateClass;

	@Column(name="RATE_CLASS_ID", insertable = false, updatable = false, nullable = false)
	private Long rateClassId;

	@Column(name = "ROSS_INCIDENT_ID", length=30)
	private String rossIncidentId;

	@Column(name="ROSS_XML_FILE_ID",nullable=true)
	private Long rossXmlFileId;

	@OneToMany(targetEntity=IncidentCostRateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	private Collection<IncidentCostRate> incidentCostRates;

	@OneToMany(targetEntity=CostGroupImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	private Collection<CostGroup> costGroups = new ArrayList<CostGroup>();

	@OneToMany(targetEntity=IncidentQSKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<IncidentQSKind> incidentQSKinds;

	@OneToMany(targetEntity=IncidentQuestionImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("position")
    private Collection<IncidentQuestion> incidentQuestions;

	@OneToOne(targetEntity=IncidentCostDefaultsImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	private IncidentCostDefaults incidentCostDefaults;
	
	@OneToMany(targetEntity=IncidentPrefsImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	@OrderBy("position")
	private Collection<IncidentPrefs> incidentPrefs = new ArrayList<IncidentPrefs>();
	
	@Column(name = "IAP_PERSON_NAME_ORDER")
	private Short iapPersonNameOrder;
	
	@Column(name = "INCLUDE_FILLED", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum includeFilled;

	@Column(name = "IS_SITE_MANAGED", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isSiteManaged;
	
	@Column(name = "IAP_RESOURCE_TO_DISPLAY_FROM")
	private Short iapResourceToDisplayFrom;
	
	@Column(name = "IAP_TREEVIEW_DISPLAY")
	private Short iapTreeviewDisplay;
	
	@Column(name = "BY_DATE")
	private Date byDate;
	
	@Column(name = "NBR_OF_DAYS_PRIOR")
	private Short nbrOfDaysPrior;
	
	@Column (name= "COST_DEFAULT_HOURS")
	private Integer incidentCostDefaultHours;

	@Column(name="COST_AUTORUN")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum costAutoRun;
	
	@OneToMany(targetEntity=IapMasterFrequencyImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	private Collection<IapMasterFrequency> iapMasterFrequencies = new ArrayList<IapMasterFrequency>();
	
	@OneToMany(targetEntity=IapPositionItemCodeImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<IapPositionItemCode> iapPositionItemCodes = new ArrayList<IapPositionItemCode>();
	
	@OneToMany(targetEntity=BranchSettingImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
    @OrderBy("positionNum")
	private Collection<BranchSetting> branchSettings = new ArrayList<BranchSetting>();
	
	@OneToMany(targetEntity=IncidentBranchImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<IncidentBranch> incidentBranches = new ArrayList<IncidentBranch>();
	
	@OneToMany(targetEntity=CostAccrualExtractImpl.class, fetch = FetchType.LAZY, mappedBy = "incident")
	private Collection<CostAccrualExtract> costAccrualExtracts;
	
	@OneToMany(targetEntity=Sit209Impl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<Sit209> sit209s = new ArrayList<Sit209>();
	
	@OneToMany(targetEntity=AgencyImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<Agency> agencies = new ArrayList<Agency>();
	
	@OneToMany(targetEntity=AgencyGroupImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<AgencyGroup> agencyGroups = new ArrayList<AgencyGroup>();
	
	@OneToMany(targetEntity=OrganizationImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<Organization> organizations = new ArrayList<Organization>();
	
	@OneToMany(targetEntity=JetPortImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<JetPort> jetPorts = new ArrayList<JetPort>();
	
	@OneToMany(targetEntity=KindImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<Kind> kinds = new ArrayList<Kind>();
	
	@OneToMany(targetEntity=SubGroupCategoryImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<SubGroupCategory> subGroupCategories = new ArrayList<SubGroupCategory>();
	
	@OneToMany(targetEntity=ProjectionImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incident")
	private Collection<Projection> projections;
	
	@Column(name="ACCRUAL_EXTRACT_NUMBER")
	private Integer accrualExtractNumber;
	
	@OneToMany(targetEntity=IncidentOrgImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<IncidentOrg> incidentOrgs;
	
	@OneToMany(targetEntity=FinancialExportImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="incident")
	private Collection<FinancialExport> financialExports = new ArrayList<FinancialExport>();
	
	@ManyToOne(targetEntity=ComplexityImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "COMPLEXITY_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Complexity complexity;
    
    @Column(name="COMPLEXITY_ID", insertable = false, updatable = false, nullable = true)
	private Long complexityId;
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setIncidentName(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getIncidentDescription()
	 */
	public String getIncidentDescription() {
		return this.incidentDescription;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setIncidentDescription(java.lang.String)
	 */
	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getIncidentNumber()
	 */
	public String getIncidentNumber() {
		String dash = "-";
		StringBuffer b = new StringBuffer();
		if (this.homeUnit != null && this.countrySubdivision != null) {
			b.append(this.countrySubdivision.getCountryCode().getAbbreviation()).append(dash)
			.append(this.countrySubdivision.getAbbreviation()).append(dash)
			.append(this.homeUnit.getUnitCode().substring(3)).append(dash)
			.append(this.nbr);   
		} else {
			b.append("N/A");
		}

		return b.toString();
	}

	public String getIncidentNumber2() {
		String dash = "-";
		StringBuffer b = new StringBuffer();
		if (this.homeUnit != null) {
			b.append(this.homeUnit.getUnitCode()).append(dash)
			.append(this.nbr);   
		} else {
			b.append("N/A");
		}

		return b.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getCountrySubdivisionId()
	 */
	public Long getCountrySubdivisionId() {
		return countrySubdivisionId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setCountrySubdivisionId(java.lang.Long)
	 */
	public void setCountrySubdivisionId(Long countrySubdivisionId) {
		this.countrySubdivisionId = countrySubdivisionId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getCountrySubdivision()
	 */
	public CountrySubdivision getCountrySubdivision() {
		return this.countrySubdivision;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setCountrySubdivision(gov.nwcg.isuite.domain.access.CountryCodeSubdivision)
	 */
	public void setCountrySubdivision(CountrySubdivision countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getNbr()
	 */
	public String getNbr() {
		return nbr;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setNbr(java.lang.Long)
	 */
	public void setNbr(String nbr) {
		this.nbr = nbr;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getAgencyId()
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setAgencyId(java.lang.Long)
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getAgency()
	 */
	public Agency getAgency() {
		return agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setAgency(gov.nwcg.isuite.domain.access.Agency)
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getLocation()
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setLocation(java.lang.String)
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getLatitude()
	 */
	public String getLatitude() {
		return latitude;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setLatitude(java.lang.String)
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getLongitude()
	 */
	public String getLongitude() {
		return longitude;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setLongitude(java.lang.String)
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getIncidentStartDate()
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setIncidentStartDate(java.util.Date)
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getIncidentYear()
	 */
	public Integer getIncidentYear() {
		return incidentYear;
	}

	public void setIncidentYear(Integer incidentYear) {
		this.incidentYear = incidentYear;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getEventTypeId()
	 */
	public Long getEventTypeId() {
		return eventTypeId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setEventTypeId(java.lang.Long)
	 */
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getEventType()
	 */
	public EventType getEventType() {
		return eventType;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setEventType(gov.nwcg.isuite.domain.access.EventType)
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getHomeUnit()
	 */
	public Organization getHomeUnit() {
		return this.homeUnit;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getHomeUnitId()
	 */
	public Long getHomeUnitId() {
		return this.homeUnitId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setHomeUnit(gov.nwcg.isuite.domain.organization.Organization)
	 */
	public void setHomeUnit(Organization homeUnit) {
		this.homeUnit = homeUnit;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setHomeUnitId(java.lang.Long)
	 */
	public void setHomeUnitId(Long homeUnitId) {
		this.homeUnitId = homeUnitId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#getResources()
	 */
	public Collection<Resource> getResources() {
		return resources;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setResources(java.util.Collection)
	 */
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#isRestricted()
	 */
	public Boolean getRestricted() {
		return this.restricted;
	}   

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.Incident#setRestricted(java.lang.Boolean)
	 */
	public void setRestricted(Boolean isRestricted) {
		this.restricted = isRestricted;
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

	/**
	 * Returns the restrictedIncidentUsers.
	 *
	 * @return 
	 *		the restrictedIncidentUsers to return
	 */
	public Collection<RestrictedIncidentUser> getRestrictedIncidentUsers() {
		return restrictedIncidentUsers;
	}

	/**
	 * Sets the restrictedIncidentUsers.
	 *
	 * @param restrictedIncidentUsers 
	 *			the restrictedIncidentUsers to set
	 */
	public void setRestrictedIncidentUsers(Collection<RestrictedIncidentUser> restrictedIncidentUsers) {
		this.restrictedIncidentUsers = restrictedIncidentUsers;
	}   

	/**
	 * Returns the incidentAccountCodes.
	 *
	 * @return 
	 *		the incidentAccountCodes to return
	 */
	public Collection<IncidentAccountCode> getIncidentAccountCodes() {
		return incidentAccountCodes;
	}

	/**
	 * Sets the incidentAccountCodes.
	 *
	 * @param incidentAccountCodes 
	 *			the incidentAccountCodes to set
	 */
	public void setIncidentAccountCodes(
			Collection<IncidentAccountCode> incidentAccountCodes) {
		this.incidentAccountCodes = incidentAccountCodes;
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
		IncidentImpl o = (IncidentImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,agencyId,countrySubdivisionId,eventTypeId,homeUnitId
				,incidentDescription,incidentName,incidentStartDate
				,incidentYear,latitude,location,longitude
				,nbr,restricted,resources},
				new Object[]{o.id,agencyId,o.countrySubdivisionId,o.eventTypeId,o.homeUnitId
				,o.incidentDescription,o.incidentName,o.incidentStartDate
				,o.incidentYear,o.latitude,o.location,o.longitude
				,o.nbr,o.restricted,o.resources})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,agencyId,countrySubdivisionId,eventTypeId,homeUnitId
				,incidentDescription,incidentName,incidentStartDate
				,incidentYear,latitude,location,longitude
				,nbr,restricted,resources})
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("agencyId", agencyId)
		.append("countrySubdivisionId", countrySubdivisionId)
		.append("eventTypeId", eventTypeId)
		.append("homeUnitId", homeUnitId)
		.append("incidentDescription", incidentDescription)
		.append("incidentName", incidentName)
		.append("incidentStartDate", incidentStartDate)
		.append("incidentYear", incidentYear)
		.append("latitude", latitude)
		.append("location", location)
		.append("longitude", longitude)
		.append("nbr", nbr)
		.append("restricted", restricted)
		.append("resources", resources)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * Returns the incidentEndDate.
	 *
	 * @return 
	 *		the incidentEndDate to return
	 */
	public Date getIncidentEndDate() {
		return incidentEndDate;
	}

	/**
	 * Sets the incidentEndDate.
	 *
	 * @param incidentEndDate 
	 *			the incidentEndDate to set
	 */
	public void setIncidentEndDate(Date incidentEndDate) {
		this.incidentEndDate = incidentEndDate;
	}

	/**
	 * @return the incidentGroups
	 */
	public Collection<IncidentGroup> getIncidentGroups() {
		return incidentGroups;
	}

	/**
	 * @param incidentGroups the incidentGroups to set
	 */
	public void setIncidentGroups(Collection<IncidentGroup> incidentGroups) {
		this.incidentGroups = incidentGroups;
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

	public RateClass getRateClass() {
		return rateClass;
	}

	public void setRateClass(RateClass rateClass) {
		this.rateClass = rateClass;
	}

	public Long getRateClassId() {
		return rateClassId;
	}

	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	/**
	 * @return the incidentCostRate
	 */
	public Collection<IncidentCostRate> getIncidentCostRates() {
		return incidentCostRates;
	}

	/**
	 * @param incidentCostRate the incidentCostRate to set
	 */
	public void setIncidentCostRates(Collection<IncidentCostRate> incidentCostRate) {
		this.incidentCostRates = incidentCostRate;
	}

	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() {
		return costGroups;
	}

	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) {
		this.costGroups = costGroups;
	}

	/**
	 * @param incidentShifts the incidentShifts to set
	 */
	public void setIncidentShifts(Collection<IncidentShift> incidentShifts) {
		this.incidentShifts = incidentShifts;
	}

	/**
	 * @return the incidentShifts
	 */
	public Collection<IncidentShift> getIncidentShifts() {
		return incidentShifts;
	}

	/**
	 * @return the incidentQSKinds
	 */
	public Collection<IncidentQSKind> getIncidentQSKinds() {
		return incidentQSKinds;
	}

	/**
	 * @param incidentQSKinds the incidentQSKinds to set
	 */
	public void setIncidentQSKinds(Collection<IncidentQSKind> incidentQSKinds) {
		this.incidentQSKinds = incidentQSKinds;
	}

	/**
	 * @return the incidentQuestions
	 */
	public Collection<IncidentQuestion> getIncidentQuestions() {
		return incidentQuestions;
	}

	/**
	 * @param incidentQuestions the incidentQuestions to set
	 */
	public void setIncidentQuestions(Collection<IncidentQuestion> incidentQuestions) {
		this.incidentQuestions = incidentQuestions;
	}

	/**
	 * @return the incidentCostDefaults
	 */
	public IncidentCostDefaults getIncidentCostDefaults() {
		return incidentCostDefaults;
	}

	/**
	 * @param incidentCostDefaults the incidentCostDefaults to set
	 */
	public void setIncidentCostDefaults(IncidentCostDefaults incidentCostDefaults) {
		this.incidentCostDefaults = incidentCostDefaults;
	}

	@Override
	public Collection<IncidentPrefs> getIncidentPrefs() {
		return incidentPrefs;
	}

	@Override
	public void setIncidentPrefs(Collection<IncidentPrefs> incidentPrefs) {
		this.incidentPrefs = incidentPrefs;
	}

	/**
	 * @return the pdc
	 */
	public Organization getPdc() {
		return pdc;
	}

	/**
	 * @param pdc the pdc to set
	 */
	public void setPdc(Organization pdc) {
		this.pdc = pdc;
	}

	/**
	 * @return the pdcId
	 */
	public Long getPdcId() {
		return pdcId;
	}

	/**
	 * @param pdcId the pdcId to set
	 */
	public void setPdcId(Long pdcId) {
		this.pdcId = pdcId;
	}

	/**
	 * @param iapMasterFrequencies the iapMasterFrequencies to set
	 */
//	public void setIapMasterFrequencies(Collection<IapMasterFrequency> iapMasterFrequencies) {
//		this.iapMasterFrequencies = iapMasterFrequencies;
//	}

	/**
	 * @return the iapMasterFrequencies
	 */
//	public Collection<IapMasterFrequency> getIapMasterFrequencies() {
//		return iapMasterFrequencies;
//	}
	
	/**
	 * @return theIapPersonNameOrder
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
	 * @return includeFilled
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
	 * @return byDate
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
	
	/**
	    * @return the incident cost default hours
	*/
	public Integer getIncidentCostDefaultHours() {
		return this.incidentCostDefaultHours;
	}
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentCostDefaultHours(Integer incidentCostDefaultHours) {
		this.incidentCostDefaultHours = incidentCostDefaultHours;
	}

	/**
	 * @return the costAutoRun
	 */
	public StringBooleanEnum getCostAutoRun() {
		return costAutoRun;
	}

	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(StringBooleanEnum costAutoRun) {
		this.costAutoRun = costAutoRun;
	}

	/**
	 * @return the iapMasterFrequencies
	 */
	public Collection<IapMasterFrequency> getIapMasterFrequencies() {
		return iapMasterFrequencies;
	}

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes() {
		return iapPositionItemCodes;
	}

	/**
	 * @return the iapMasterFrequencies
	 */
	public Collection<IncidentBranch> getIncidentBranches() {
		return incidentBranches;
	}

	/**
	 * @param iapMasterFrequencies the iapMasterFrequencies to set
	 */
	public void setIapMasterFrequencies(Collection<IapMasterFrequency> iapMasterFrequencies) {
		this.iapMasterFrequencies = iapMasterFrequencies;
	}

	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes) {
		this.iapPositionItemCodes = iapPositionItemCodes;
	}

	/**
	 * @param incidentBranches the incidentBranches to set
	 */
	public void setIncidentBranches(Collection<IncidentBranch> incidentBranches) {
		this.incidentBranches = incidentBranches;
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
	 * @return the accrualExtractNumber
	 */
	public Integer getAccrualExtractNumber() {
		return accrualExtractNumber;
	}

	/**
	 * @param accrualExtractNumber the accrualExtractNumber to set
	 */
	public void setAccrualExtractNumber(Integer accrualExtractNumber) {
		this.accrualExtractNumber = accrualExtractNumber;
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
	
	//irwin
	
	@Column(name="IRWIN_IRWINID", length=38)
	private String irwinIrwinId;
	
	@Column(name="IRWIN_FIREDISCOVERYDATETIME", length=24)
	private String irwinFireDiscoveryDateTime;
	
	@Column(name="IRWIN_POORESPONSIBLEUNIT", length=6)
	private String irwinPooResponsibleUnit;
	
	@Column(name="IRWIN_LOCALINCIDENTIDENTIFIER", length=10)
	private String irwinLocalIncidentIdentifier;
	
	@Column(name="IRWIN_INCIDENTNAME", length=50)
	private String irwinIncidentName;
	
	@Column(name="IRWIN_INCIDENTTYPEKIND", length=2)
	private String irwinIncidentTypeKind;
	
	@Column(name="IRWIN_INCIDENTTYPECATEGORY", length=2)
	private String irwinIncidentTypeCategory;
	
	@Column(name="IRWIN_FIRECODE", length=4)
	private String irwinFireCode;
	
	@Column(name="IRWIN_FSJOBCODE", length=2)
	private String irwinFsJobCode;
	
	@Column(name="IRWIN_FSOVERRIDECODE", length=4)
	private String irwinFsOverrideCode;
	
	@Column(name="IRWIN_ISACTIVE", length=5)
	private String irwinIsActive;
	
	@Column(name="IRWIN_RECORDSOURCE", length=255)
	private String irwinRecordSource;
	
	@Column(name="IRWIN_CREATEDBYSYSTEM", length=255)
	private String irwinCreatedBySystem;
	
	@Column(name="IRWIN_CREATEDONDATETIME", length=24)
	private String irwinCreatedOnDateTime;
	
	@Column(name="IRWIN_MODIFIEDBYSYSTEM", length=255)
	private String irwinModifiedBySystem;
	
	@Column(name="IRWIN_MODIFIEDONDATETIME", length=24)
	private String irwinModifiedOnDateTime;
	
	@Column(name="IRWIN_POOPROTECTINGUNIT", length=6)
	private String irwinPooProtectingUnit;
	
	@Column(name="IRWIN_ISCOMPLEX", length=5)
	private String irwinIsComplex;
	
	@Column(name="IRWIN_COMPLEXPARENTIRWINID", length=38)
	private String irwinComplexParentIrwinId;
	
	@Column(name="IRWIN_UNIQUEFIREIDENTIFIER", length=22)
	private String irwinUniqueFireIdentifier;
	
	@Column(name="IRWIN_ABCDMISC", length=4)
	private String irwinAbcdMisc;
	
	@Column(name="IRWIN_STATUS", length=50)
	private String irwinStatus;
	
	@Column(name="IRWIN_ISVALID", length=5)
	private String irwinIsValid;

	public String getIrwinIrwinId() {
		return irwinIrwinId;
	}

	public void setIrwinIrwinId(String irwinIrwinId) {
		this.irwinIrwinId = irwinIrwinId;
	}

	public String getIrwinFireDiscoveryDateTime() {
		return irwinFireDiscoveryDateTime;
	}

	public void setIrwinFireDiscoveryDateTime(String irwinFireDiscoveryDateTime) {
		this.irwinFireDiscoveryDateTime = irwinFireDiscoveryDateTime;
	}

	public String getIrwinPooResponsibleUnit() {
		return irwinPooResponsibleUnit;
	}

	public void setIrwinPooResponsibleUnit(String irwinPooResponsibleUnit) {
		this.irwinPooResponsibleUnit = irwinPooResponsibleUnit;
	}
	
	public String getIrwinLocalIncidentIdentifier() {
		return irwinLocalIncidentIdentifier;
	}

	public void setIrwinLocalIncidentIdentifier(String irwinLocalIncidentIdentifier) {
		this.irwinLocalIncidentIdentifier= irwinLocalIncidentIdentifier;
	}

	public String getIrwinIncidentName() {
		return irwinIncidentName;
	}

	public void setIrwinIncidentName(String irwinIncidentName) {
		this.irwinIncidentName = irwinIncidentName;
	}

	public String getIrwinIncidentTypeKind() {
		return irwinIncidentTypeKind;
	}

	public void setIrwinIncidentTypeKind(String irwinIncidentTypeKind) {
		this.irwinIncidentTypeKind = irwinIncidentTypeKind;
	}

	public String getIrwinIncidentTypeCategory() {
		return irwinIncidentTypeCategory;
	}

	public void setIrwinIncidentTypeCategory(String irwinIncidentTypeCategory) {
		this.irwinIncidentTypeCategory = irwinIncidentTypeCategory;
	}

	public String getIrwinFireCode() {
		return irwinFireCode;
	}

	public void setIrwinFireCode(String irwinFireCode) {
		this.irwinFireCode = irwinFireCode;
	}

	public String getIrwinFsJobCode() {
		return irwinFsJobCode;
	}

	public void setIrwinFsJobCode(String irwinFsJobCode) {
		this.irwinFsJobCode = irwinFsJobCode;
	}

	public String getIrwinFsOverrideCode() {
		return irwinFsOverrideCode;
	}

	public void setIrwinFsOverrideCode(String irwinFsOverrideCode) {
		this.irwinFsOverrideCode = irwinFsOverrideCode;
	}

	public String getIrwinIsActive() {
		return irwinIsActive;
	}

	public void setIrwinIsActive(String irwinIsActive) {
		this.irwinIsActive = irwinIsActive;
	}

	public String getIrwinRecordSource() {
		return irwinRecordSource;
	}

	public void setIrwinRecordSource(String irwinRecordSource) {
		this.irwinRecordSource = irwinRecordSource;
	}

	public String getIrwinCreatedBySystem() {
		return irwinCreatedBySystem;
	}

	public void setIrwinCreatedBySystem(String irwinCreatedBySystem) {
		this.irwinCreatedBySystem = irwinCreatedBySystem;
	}

	public String getIrwinCreatedOnDateTime() {
		return irwinCreatedOnDateTime;
	}

	public void setIrwinCreatedOnDateTime(String irwinCreatedOnDateTime) {
		this.irwinCreatedOnDateTime = irwinCreatedOnDateTime;
	}

	public String getIrwinModifiedBySystem() {
		return irwinModifiedBySystem;
	}

	public void setIrwinModifiedBySystem(String irwinModifiedBySystem) {
		this.irwinModifiedBySystem = irwinModifiedBySystem;
	}

	public String getIrwinModifiedOnDateTime() {
		return irwinModifiedOnDateTime;
	}

	public void setIrwinModifiedOnDateTime(String irwinModifiedOnDateTime) {
		this.irwinModifiedOnDateTime = irwinModifiedOnDateTime;
	}
	
	public String getIrwinPooProtectingUnit() {
	    return irwinPooProtectingUnit;
    }

    public void setIrwinPooProtectingUnit(String irwinPooProtectingUnit) {
	    this.irwinPooProtectingUnit = irwinPooProtectingUnit;
    }
    
	public String getIrwinUniqueFireIdentifier() {
		return irwinUniqueFireIdentifier;
	}
	
	public void setIrwinUniqueFireIdentifier(String irwinUniqueFireIdentifier) {
		this.irwinUniqueFireIdentifier = irwinUniqueFireIdentifier;
	}
	
	public String getIrwinIsComplex() {
		return irwinIsComplex;
	}
	
	public void setIrwinIsComplex(String irwinIsComplex) {
		this.irwinIsComplex = irwinIsComplex;
	}
	
	public String getIrwinComplexParentIrwinId() {
		return irwinComplexParentIrwinId;
	}
	
	public void setIrwinComplexParentIrwinId(String irwinComplexParentIrwinId) {
		this.irwinComplexParentIrwinId = irwinComplexParentIrwinId;
	}
	
	public String getIrwinAbcdMisc() {
		return irwinAbcdMisc;
	}
	
	public void setIrwinAbcdMisc(String irwinAbcdMisc) {
		this.irwinAbcdMisc = irwinAbcdMisc;
	}
	
	public String getIrwinStatus() {
		return irwinStatus;
	}
	
	public void setIrwinStatus(String irwinStatus) {
		this.irwinStatus = irwinStatus;
	}
	
	public String getIrwinIsValid() {
		return irwinIsValid;
	}

	public void setIrwinIsValid(String irwinIsValid) {
		this.irwinIsValid = irwinIsValid;
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
	 * @param complexity the complexity to set
	 */
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	/**
	 * @return the complexity
	 */
	public Complexity getComplexity() {
		return complexity;
	}

	/**
	 * @param complexityId the complexityId to set
	 */
	public void setComplexityId(Long complexityId) {
		this.complexityId = complexityId;
	}

	/**
	 * @return the complexityId
	 */
	public Long getComplexityId() {
		return complexityId;
	}
	
}