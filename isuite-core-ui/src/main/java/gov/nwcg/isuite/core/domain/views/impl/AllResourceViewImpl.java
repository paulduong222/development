package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.AllResourceView;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceRequestCategoryEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author dprice
 */
@Entity
@Table(name = "iswv_resource_all")
public class AllResourceViewImpl implements AllResourceView {

	@Column(name = "ARRIVAL_JET_PORT", insertable = false, updatable = false)
	private String arrivalJetPort;
	
	@Column(name="ASSIGNMENT_ID")
	private Long assignmentId;
	
	@Column(name="ASSIGNMENT_STATUS")
    @Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;
	
	@Column(name="CHECK_IN_DATE")
	private Date checkInDate;
	
	@Column(name = "DEMOB_CITY", insertable = false, updatable = false, nullable=true)
	private String demobCity;
	
	@Column(name = "DEMOB_DATE", insertable = false, updatable = false, nullable=true)
	private Date demobDate;

	@Column(name = "DEMOB_STATE", insertable = false, updatable = false, nullable=true)
	private String demobState;
	
	@Column(name = "FIRST_NAME", insertable = false, updatable = false)
	private String firstName;

	@Column(name="FIRST_WORK_DATE")
	private Date firstWorkDate;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;
	
	@Column(name = "INCIDENT_NAME", insertable = false, updatable = false)
	private String incidentName;

	@Column(name="INCIDENT_RESOURCE_ID")
	private Long incidentResourceId;
	
	@Column(name = "KIND_CODE", insertable = false, updatable = false)
	private String kindCode;
	
	@Column(name = "KIND_CODE_DESCRIPTION", insertable = false, updatable = false)
	private String kindCodeDescription;
	
	@Column(name = "LAST_NAME", insertable = false, updatable = false)
	private String lastName;
	
	@Column(name="LENGTH_AT_ASSIGNMENT")
	private Long lengthAtAssignment;
	
	@Column(name = "MOBILIZATION_START_DATE", insertable = false, updatable = false, nullable=true)
	private Date mobilizationStartDate;
	
	@Column(name="PARENT_RESOURCE_ID")
	private Long parentResourceId;
	
	@Column(name = "PERMANENT", insertable = false, updatable = false)
	private Boolean permanent;

	@Column(name = "ENABLED", insertable = false, updatable = false)
	private Boolean enabled;

	@Column(name = "CONTRACTED", insertable = false, updatable = false)
	private Boolean contracted;
	
	@Column(name="PRE_PLANNING_REMARKS")
	private String prePlanningRemarks;

	@Column(name="REQUEST_NUMBER")
	private String requestNumber;
	
	@Column(name = "RESOURCE_AGENCY_NAME", insertable = false, updatable = false)
	private String resourceAgencyName;
	
	@Id
	@Column(name = "RESOURCE_ID", insertable = false, updatable = false)
	private Long resourceId;
	
	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;
	
	@Column(name = "RESOURCE_ORGANIZATION_NAME", insertable = false, updatable = false)
	private String resourceOrganizationName;
	
	@Column(name = "RESOURCE_STATUS", insertable = false, updatable = false)
	private String resourceStatus;

	@Column(name = "RESOURCE_TYPE", length=15)
    @Enumerated(EnumType.STRING)
	private ResourceRequestCategoryEnum resourceRequestCategoryEnum;
	
	@Column(name="TRAINING")
	private Boolean trainee;
	
    @Column(name = "TRAVEL_METHOD")
    @Enumerated(EnumType.STRING)
    private TravelMethodTypeEnum travelMethod;
	
    @Column(name="WORK_AREA_ID")
    private Long workAreaId;
    
    @Column(name="WORK_AREA_INCIDENT_ID")
    private Long workAreaIncidentId;
    
    
	public AllResourceViewImpl() {

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getArrivalJetPort()
	 */
	public String getArrivalJetPort() {
		return arrivalJetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getAssignmentId()
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getAssignmentStatus()
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getCheckInDate()
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getDemobCity()
	 */
	public String getDemobCity() {
		return demobCity;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getDemobDate()
	 */
	public Date getDemobDate() {
		return demobDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getDemobState()
	 */
	public String getDemobState() {
		return demobState;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getFirstName()
	 */
	public String getFirstName() {
		return firstName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getFirstWorkDate()
	 */
	public Date getFirstWorkDate() {
		return firstWorkDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getIncidentResourceId()
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getKindCode()
	 */
	public String getKindCode() {
		return kindCode;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getKindCodeDescription()
	 */
	public String getKindCodeDescription() {
		return kindCodeDescription;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getLastName()
	 */
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getLengthAtAssignment()
	 */
	public Long getLengthAtAssignment() {
		return lengthAtAssignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getMobilizationStartDate()
	 */
	public Date getMobilizationStartDate() {
		return mobilizationStartDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getParentResourceId()
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getPrePlanningRemarks()
	 */
	public String getPrePlanningRemarks() {
		return prePlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getRequestNumber()
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceAgencyName()
	 */
	public String getResourceAgencyName() {
		return resourceAgencyName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceName()
	 */
	public String getResourceName() {
		return resourceName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceOrganizationName()
	 */
	public String getResourceOrganizationName() {
		return resourceOrganizationName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceStatus()
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getResourceType()
	 */
	public ResourceRequestCategoryEnum getResourceRequestCategory() {
		return resourceRequestCategoryEnum;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getTravelMethod()
	 */
	public TravelMethodTypeEnum getTravelMethod() {
		return travelMethod;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getPermanent()
	 */
	public Boolean isPermanent() {
		return permanent;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#getTrainee()
	 */
	public Boolean isTrainee() {
		return trainee;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setArrivalJetPort(java.lang.String)
	 */
	public void setArrivalJetPort(String arrivalJetPort) {
		this.arrivalJetPort = arrivalJetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setAssignmentId(java.lang.Long)
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setAssignmentStatus(gov.nwcg.isuite.domain.resource.AssignmentStatusTypeEnum)
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setCheckInDate(java.util.Date)
	 */
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setDemobCity(java.lang.String)
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setDemobDate(java.util.Date)
	 */
	public void setDemobDate(Date demobDate) {
		this.demobDate = demobDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setDemobState(java.lang.String)
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setFirstName(java.lang.String)
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setFirstWorkDate(java.util.Date)
	 */
	public void setFirstWorkDate(Date firstWorkDate) {
		this.firstWorkDate = firstWorkDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setIncidentId(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setIncidentName(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setIncidentResourceId(java.lang.Long)
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setKindCode(java.lang.String)
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setKindCodeDescription(java.lang.String)
	 */
	public void setKindCodeDescription(String kindCodeDescription) {
		this.kindCodeDescription = kindCodeDescription;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setLastName(java.lang.String)
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setLengthAtAssignment(java.lang.String)
	 */
	public void setLengthAtAssignment(Long lengthAtAssignment) {
		this.lengthAtAssignment = lengthAtAssignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setMobilizationStartDate(java.util.Date)
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate) {
		this.mobilizationStartDate = mobilizationStartDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setParentResourceId(java.lang.Long)
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setPermanent(java.lang.Boolean)
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setPrePlanningRemarks(java.lang.String)
	 */
	public void setPrePlanningRemarks(String prePlanningRemarks) {
		this.prePlanningRemarks = prePlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setRequestNumber(java.lang.String)
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceAgencyName(java.lang.String)
	 */
	public void setResourceAgencyName(String resourceAgencyName) {
		this.resourceAgencyName = resourceAgencyName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceId(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceName(java.lang.String)
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceOrganizationName(java.lang.String)
	 */
	public void setResourceOrganizationName(String resourceOrganizationName) {
		this.resourceOrganizationName = resourceOrganizationName;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceStatus(java.lang.String)
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setResourceType(gov.nwcg.isuite.domain.resource.ResourceTypeEnum)
	 */
	public void setResourceRequestCategory(ResourceRequestCategoryEnum resourceRequestCategoryEnum) {
		this.resourceRequestCategoryEnum = resourceRequestCategoryEnum;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setTrainee(java.lang.Boolean)
	 */
	public void setTrainee(Boolean trainee) {
		this.trainee = trainee;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.IncidentResourceView#setTravelMethod(gov.nwcg.isuite.domain.resource.TravelMethodTypeEnum)
	 */
	public void setTravelMethod(TravelMethodTypeEnum travelMethod) {
		this.travelMethod = travelMethod;
	}

	/**
	 * @return the workAreaId
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * @param workAreaId the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * @return the workAreaIncidentId
	 */
	public Long getWorkAreaIncidentId() {
		return workAreaIncidentId;
	}

	/**
	 * @param workAreaIncidentId the workAreaIncidentId to set
	 */
	public void setWorkAreaIncidentId(Long workAreaIncidentId) {
		this.workAreaIncidentId = workAreaIncidentId;
	}

	/**
	 * @return the permanent
	 */
	public Boolean getPermanent() {
		return permanent;
	}

	/**
	 * @return the trainee
	 */
	public Boolean getTrainee() {
		return trainee;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the contracted
	 */
	public Boolean getContracted() {
		return contracted;
	}

	/**
	 * @param contracted the contracted to set
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}
}
