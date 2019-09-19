package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.ResourceStatusTypeEnum;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * The resource implementation.
 * 
 * @author bsteiner
 */
@Entity
@NamedQueries({
	@NamedQuery(name=ResourceQuery.DISABLE_RESOURCES,query=ResourceQuery.DISABLE_RESOURCES_QUERY)
	,@NamedQuery(name=ResourceQuery.DISABLE_RESOURCES_REMOVE_PARENT_ASSOC,query=ResourceQuery.DISABLE_RESOURCES_REMOVE_PARENT_ASSOC_QUERY)
	,@NamedQuery(name=ResourceQuery.ENABLE_RESOURCES,query=ResourceQuery.ENABLE_RESOURCES_QUERY)
})
@SequenceGenerator(name="SEQ_RESOURCE", sequenceName="SEQ_RESOURCE")
@Table(name="isw_resource")
public class ResourceImpl extends PersistableImpl implements Resource {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE")
	private Long id = 0L;

	@Column(name="RESOURCE_NAME",length=55)
	private String resourceName;

	@Column(name="FIRST_NAME",length=35)
	private String firstName;

	@Column(name="LAST_NAME",length=35)
	private String lastName;

	@ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.ResourceImpl.class,cascade=CascadeType.PERSIST)
	@JoinColumn(name = "PARENT_RESOURCE_ID", insertable = true, updatable = true, unique = false, nullable = true)
	//@ForeignKey(name="FK_RESOURCE__RESOURCE_ID")
	private Resource parentResource;

	@Column(name="PARENT_RESOURCE_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long parentResourceId;

	@ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.ResourceImpl.class)
	@JoinColumn(name = "PERMANENT_RESOURCE_ID", insertable = true, updatable = true, unique = false, nullable = true)
	@ForeignKey(name="FK_RESOURCE__PERM_RES_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Resource permanentResource; //TODO: Refactor this to WorkAreaResource. -dbudge

	@Column(name="PERMANENT_RESOURCE_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long permanentResourceId;

	@OneToMany(cascade=CascadeType.ALL, 
			targetEntity=ResourceImpl.class,
			 fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_RESOURCE_ID")
			//@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	//@BatchSize(size=100)
	private Collection<Resource> childResources;

	@OneToMany(targetEntity=IncidentResourceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	private Collection<IncidentResource> incidentResources;

	@OneToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="ORGANIZATION_ID", insertable=true, updatable=true, unique=false, nullable=true)
	@ForeignKey(name="FK_RESOURCE__ORGANIZATION_ID")
	private Organization organization;

	@Column(name="ORGANIZATION_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long organizationId;

	@OneToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="PRIMARY_DISP_CTR_ID", insertable=true, updatable=true, unique=false, nullable=true)
	@ForeignKey(name="FK_RES__PDC")
	private Organization primaryDispatchCenter;

	@Column(name="PRIMARY_DISP_CTR_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long primaryDispatchCenterId;

	@OneToMany(targetEntity=ResourceMobilizationImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	private Collection<ResourceMobilization> resourceMobilizations;

	@ManyToMany(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinTable(name="isw_incident_resource", 
			joinColumns={ @JoinColumn(name="RESOURCE_ID") },
			inverseJoinColumns= { @JoinColumn(name="INCIDENT_ID") })
			private Collection<Incident> incidents;

	@Column(name="RESOURCE_CLASSIFICATION", length=3)
	@Enumerated(EnumType.STRING)
	private ResourceClassificationEnum resourceClassification;

	@Column(name="RESOURCE_STATUS", length=10, nullable=true)
	@Enumerated(EnumType.STRING)
	private ResourceStatusTypeEnum resourceStatus;

	@OneToOne(targetEntity=AgencyImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="AGENCY_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Agency agency;

	@Column(name="AGENCY_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long agencyId;

	@Column(name="IS_PERSON",nullable=false)
	private Boolean person;

	@Column(name="IS_CONTRACTED",nullable=false)
	private Boolean contracted;

	@Column(name = "IS_LEADER", nullable = false)
	private Boolean leader;

	@Column(name="IS_COMPONENT",nullable=false)
	private Boolean component=false;

	@Column(name="NAME_ON_PICTURE_ID",length=70)
	private String nameOnPictureId;

	@Column(name="CONTACT_NAME", length=55)
	private String contactName;

	@Column(name="PHONE",length=12)
	private String phone;

	@Column(name="EMAIL",length=50)
	private String email;

	@Column(name="OTHER_1",length=15)
	private String other1;

	@Column(name="OTHER_2",length=15)
	private String other2;

	@Column(name="OTHER_3",length=15)
	private String other3;

	@Column(name="IS_ENABLED",nullable=false)
	private Boolean enabled;

	@Column(name="IS_PERMANENT",nullable=false)
	private Boolean permanent;

	@Column(name="LEADER_TYPE")
	private Integer leaderType;

	@Column(name = "EMPLOYMENT_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	private EmploymentTypeEnum employmentType;

	@OneToMany(targetEntity=ResourceKindImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<ResourceKind> resourceKinds;

	@Column(name="NUMBER_OF_PERSONNEL")
	private Long numberOfPersonnel;

	@Column(name="DELETED_DATE")
	private Date deletedDate;
	
	@ManyToMany(targetEntity=ResourceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_RESOURCE_CONTRACTOR", joinColumns = { @JoinColumn(name = "RESOURCE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "CONTRACTOR_ID", nullable = false, updatable = false) })
	private Collection<Contractor> contractors;

	@Column(name="ROSS_RES_ID", length=19, nullable = true)
	private Long rossResId;

	@ManyToMany(targetEntity=CostGroupImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_COST_GROUP_RESOURCE", 
			joinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "COST_GROUP_ID", updatable = false) })
	private Collection<CostGroup> costGroups = new ArrayList<CostGroup>();
	
	@OneToOne(targetEntity=ContractorImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="DEFAULT_CONTRACTOR_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Contractor contractor;

	@Column(name="DEFAULT_CONTRACTOR_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long contractorId;
	
	@OneToOne(targetEntity=ContractorAgreementImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="DEFAULT_AGREEMENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private ContractorAgreement contractorAgreement;

	@Column(name="DEFAULT_AGREEMENT_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long contractorAgreementId;
	
	@ManyToMany(targetEntity=OrganizationImpl.class,cascade = CascadeType.ALL)
	@JoinTable(name = "isw_organization_nonstd_res", 
			joinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ORGANIZATION_ID", updatable = false) })
	@BatchSize(size=100)
	private Collection<Organization> nonStandardOrganizations;
	
	@Column(name="ROSS_RESOURCE_NAME",length=55)
	private String rossResourceName;

	@Column(name="ROSS_FIRST_NAME",length=35)
	private String rossFirstName;

	@Column(name="ROSS_LAST_NAME",length=35)
	private String rossLastName;
	
	@Column(name="LAST_ROSS_UPDATED_DATE")
	private Date lastRossUpdatedDate;
	
	@Column(name="ROSS_GROUP_ASSIGNMENT",length=50)
	private String rossGroupAssignment;
	
	public ResourceImpl(){

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getResourceName()
	 */
	public String getResourceName() {
		return this.resourceName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getParentResource()
	 */
	public Resource getParentResource() {
		return this.parentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setResourceName(java.lang.String)
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setParentResource(gov.nwcg.isuite.core.domain.Resource)
	 */
	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getParentResourceId()
	 */
	public Long getParentResourceId() {
		return this.parentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setParentResourceId(java.lang.Long)
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getChildResources()
	 */
	public Collection<Resource> getChildResources() {
		if(null==childResources)
			childResources = new ArrayList<Resource>();
		return this.childResources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setChildResources(java.util.Collection)
	 */
	public void setChildResources(Collection<Resource> childResources) {
		this.childResources = childResources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getOrganization()
	 */
	public Organization getOrganization() {
		return organization;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganization(gov.nwcg.isuite.core.domain.Organization)
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getOrganizationId()
	 */
	public Long getOrganizationId() {
		return organizationId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganizationId(java.lang.Long)
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganization(gov.nwcg.isuite.core.domain.Organization)
	 */
	public Organization getPrimaryDispatchCenter() {
		return primaryDispatchCenter;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganization(gov.nwcg.isuite.core.domain.Organization)
	 */
	public void setPrimaryDispatchCenter(Organization primaryDispatchCenter) {
		this.primaryDispatchCenter = primaryDispatchCenter;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganizationId(java.lang.Long)
	 */
	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOrganizationId(java.lang.Long)
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId) {
		this.primaryDispatchCenterId = primaryDispatchCenterId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getFirstName()
	 */
	public String getFirstName() {
		return firstName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setFirstName(java.lang.String)
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getLastName()
	 */
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setLastName(java.lang.String)
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getAgency()
	 */
	public Agency getAgency() {
		return agency;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setAgency(gov.nwcg.isuite.core.domain.Agency)
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getAgencyId()
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setAgencyId(java.lang.Long)
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#isContracted()
	 */
	public Boolean isContracted() {
		return contracted;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setContracted(java.lang.Boolean)
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getContactName()
	 */
	public String getContactName() {
		return contactName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setContactName(java.lang.String)
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#isEnabled()
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setEnabled(java.lang.Boolean)
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#isPermanent()
	 */
	public Boolean isPermanent() {
		return permanent;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setPermanent(java.lang.Boolean)
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getNameOnPictureId()
	 */
	public String getNameOnPictureId() {
		return nameOnPictureId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setNameOnPictureId(java.lang.String)
	 */
	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getIncidents()
	 */
	public Collection<Incident> getIncidents() {
		return incidents;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setIncidents(java.util.Collection)
	 */
	public void setIncidents(Collection<Incident> incidents) {
		this.incidents = incidents;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getPhone()
	 */
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setPhone(java.lang.String)
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getResourceClassification()
	 */
	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setResourceClassification(gov.nwcg.isuite.framework.types.ResourceClassificationEnum)
	 */
	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#isPerson()
	 */
	public Boolean isPerson() {
		return person;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setPerson(java.lang.Boolean)
	 */
	public void setPerson(Boolean person) {
		this.person = person;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getIncidentResources()
	 */
	public Collection<IncidentResource> getIncidentResources() {
		if(null==incidentResources)
			incidentResources=new ArrayList<IncidentResource>();
		return incidentResources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setIncidentResources(java.util.Collection)
	 */
	public void setIncidentResources(Collection<IncidentResource> incidentResources) {
		this.incidentResources = incidentResources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#isLeader()
	 */
	public Boolean isLeader() {
		return leader;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setLeader(java.lang.Boolean)
	 */
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getOther1()
	 */
	public String getOther1() {
		return other1;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOther1(java.lang.String)
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getOther2()
	 */
	public String getOther2() {
		return other2;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOther2(java.lang.String)
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getOther3()
	 */
	public String getOther3() {
		return other3;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setOther3(java.lang.String)
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
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

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getResourceKinds()
	 */
	public Collection<ResourceKind> getResourceKinds() {
		if(null==resourceKinds)
			resourceKinds=new ArrayList<ResourceKind>();
		return resourceKinds;
	}

	/*
	 * keep method private, use addResourceKind instead for cascade_delete_orphan to work properly
	 */
	private void setResourceKinds(Collection<ResourceKind> resourceKinds) {
		this.resourceKinds = resourceKinds;
	}

	/*
	 * Implementing this method so delete orphan can be used without exception.
	 */
	public void addResourceKind(ResourceKind rk){
		this.getResourceKinds().add(rk);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getResourceMobilizations()
	 */
	public Collection<ResourceMobilization> getResourceMobilizations() {
		return resourceMobilizations;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setResourceMobilizations(java.util.Collection)
	 */
	public void setResourceMobilizations(Collection<ResourceMobilization> resourceMobilizations) {
		this.resourceMobilizations = resourceMobilizations;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getPermanentResourceId()
	 */
	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setPermanentResourceId(java.lang.Long)
	 */
	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getPermanentResource()
	 */
	public Resource getPermanentResource() {
		return permanentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setPermanentResource(gov.nwcg.isuite.core.domain.Resource)
	 */
	public void setPermanentResource(Resource permanentResource) {
		this.permanentResource = permanentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getResourceStatus()
	 */
	public ResourceStatusTypeEnum getResourceStatus() {
		return resourceStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setResourceStatus(gov.nwcg.isuite.framework.types.ResourceStatusTypeEnum)
	 */
	public void setResourceStatus(ResourceStatusTypeEnum val) {
		this.resourceStatus = val;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#getDeletedDate()
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Resource#setDeletedDate(java.util.Date)
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
		ResourceImpl o = (ResourceImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,agencyId,contactName,contracted,email
				,enabled,firstName,lastName
				,nameOnPictureId,organizationId,parentResourceId
				,permanent,person,phone,resourceClassification
				,resourceName,leader,leaderType,other1,other2,other3
				,deletedDate},
				new Object[]{o.id,o.agencyId,o.contactName,o.contracted,o.email
				,o.enabled,o.firstName,o.lastName
				,o.nameOnPictureId,o.organizationId,o.parentResourceId
				,o.permanent,o.person,o.phone,o.resourceClassification
				,o.resourceName,o.leader,o.leaderType,o.other1,o.other2,o.other3
				,o.deletedDate})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,agencyId,contactName,contracted,email
				,enabled,firstName,lastName
				,nameOnPictureId,organizationId,parentResourceId
				,permanent,person,phone,resourceClassification
				,resourceName,leader,leaderType,other1,other2,other3
				,deletedDate})
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("agencyId", agencyId)
		.append("contactName", contactName)
		.append("contracted",contracted)
		.append("leader",leader)
		.append("leaderType",leaderType)
		.append("email",email)
		.append("enabled",enabled)
		.append("firstName",firstName)
		.append("lastName",lastName)
		.append("nameOnPictureId",nameOnPictureId)
		.append("organizationId",organizationId)
		.append("parentResourceId",parentResourceId)
		.append("permanent",permanent)
		.append("person",person)
		.append("phone",phone)
		.append("other1",other1)
		.append("other2",other2)
		.append("other3",other3)
		.append("resourceClassification",resourceClassification)
		.append("resourceName",resourceName)
		.append("deletedDate",deletedDate)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * Sets the number of personnel
	 * 
	 * @param number
	 * 			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long number) {
		this.numberOfPersonnel = number;
	}

	/**
	 * Returns the number of personnel
	 * 
	 * @return
	 * 			the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel(){
		return this.numberOfPersonnel;
	}

	/**
	 * Returns the leaderType.
	 *
	 * @return 
	 *		the leaderType to return
	 */
	public Integer getLeaderType() {
		return leaderType;
	}

	/**
	 * Sets the leaderType.
	 *
	 * @param leaderType 
	 *			the leaderType to set
	 */
	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}

	/**
	 * @return the contractors
	 */
	public Collection<Contractor> getContractors() {
		return contractors;
	}

	/**
	 * @param contractors the contractors to set
	 */
	public void setContractors(Collection<Contractor> contractors) {
		this.contractors = contractors;
	}

	/**
	 * @return the employmentType
	 */
	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}

	public Boolean getComponent() {
		return component;
	}

	public void setComponent(Boolean component) {
		this.component = component;
	}

	/**
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
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
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * @return the contractor
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * @return the contractorAgreement
	 */
	public ContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * @param nonStandardOrganizations the nonStandardOrganizations to set
	 */
	public void setNonStandardOrganizations(Collection<Organization> nonStandardOrganizations) {
		this.nonStandardOrganizations = nonStandardOrganizations;
	}

	/**
	 * @return the nonStandardOrganizations
	 */
	public Collection<Organization> getNonStandardOrganizations() {
		return nonStandardOrganizations;
	}

	/**
	 * @param lastRossUpdatedDate the lastRossUpdatedDate to set
	 */
	public void setLastRossUpdatedDate(Date lastRossUpdatedDate) {
		this.lastRossUpdatedDate = lastRossUpdatedDate;
	}

	/**
	 * @return the lastRossUpdatedDate
	 */
	public Date getLastRossUpdatedDate() {
		return lastRossUpdatedDate;
	}

	/**
	 * @param rossResourceName the rossResourceName to set
	 */
	public void setRossResourceName(String rossResourceName) {
		this.rossResourceName = rossResourceName;
	}

	/**
	 * @return the rossResourceName
	 */
	public String getRossResourceName() {
		return rossResourceName;
	}

	/**
	 * @param rossFirstName the rossFirstName to set
	 */
	public void setRossFirstName(String rossFirstName) {
		this.rossFirstName = rossFirstName;
	}

	/**
	 * @return the rossFirstName
	 */
	public String getRossFirstName() {
		return rossFirstName;
	}

	/**
	 * @param rossLastName the rossLastName to set
	 */
	public void setRossLastName(String rossLastName) {
		this.rossLastName = rossLastName;
	}

	/**
	 * @return the rossLastName
	 */
	public String getRossLastName() {
		return rossLastName;
	}

	public String getRossGroupAssignment() {
		return rossGroupAssignment;
	}

	public void setRossGroupAssignment(String rossGroupAssignment) {
		this.rossGroupAssignment = rossGroupAssignment;
	}
	
}