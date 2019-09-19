package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.IncidentResourceView2;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
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
 */
@Entity
@Table(name = "iswv_resource_incident2")
public class IncidentResourceView2Impl implements IncidentResourceView2 {

	@Id
	@Column(name = "RESOURCE_ID", insertable = false, updatable = false)
	private Long resourceId;

	@Column(name="PARENT_RESOURCE_ID")
	private Long parentResourceId;
	
	@Column(name="CELL_PHONE_NUMBER")
	private String cellPhoneNumber;
	
	@Column(name = "IS_PERMANENT", insertable = false, updatable = false)
	private Boolean permanent;

	@Column(name = "IS_ENABLED", insertable = false, updatable = false)
	private Boolean enabled;

	@Column(name = "IS_CONTRACTED", insertable = false, updatable = false)
	private Boolean contracted;
	
	@Column(name = "IS_LEADER", insertable = false, updatable = false)
	private Boolean leader;

	@Column(name = "OTHER_1", insertable=false, updatable=false)
	private String other1;
	
	@Column(name = "OTHER_2", insertable=false, updatable=false)
	private String other2;

	@Column(name = "OTHER_3", insertable=false, updatable=false)
	private String other3;
	
	@Column(name = "REQUEST_CATEGORY", insertable=false, updatable=false)
	private String requestCategory;

	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;

	@Column(name = "LAST_NAME", insertable = false, updatable = false)
	private String lastName;

	@Column(name = "FIRST_NAME", insertable = false, updatable = false)
	private String firstName;

	@Column(name = "RESOURCE_STATUS", insertable = false, updatable = false)
	private String resourceStatus;

	@Column(name="RESOURCE_CLASSIFICATION", length=3)
	@Enumerated(EnumType.STRING)
	private ResourceClassificationEnum resourceClassification;
	
	@Column(name = "NUMBER_OF_PERSONNEL", insertable = false, updatable = false)
	private Long numberOfPersonnel;

	@Column(name = "RESOURCE_AGENCY_NAME", insertable = false, updatable = false)
	private String resourceAgencyName;
	
	@Column(name = "RESOURCE_AGENCY_CODE", insertable = false, updatable = false)
	private String resourceAgencyCode;
	
	@Column(name = "RESOURCE_ORGANIZATION_NAME", insertable = false, updatable = false)
	private String resourceOrganizationName;

	@Column(name = "RESOURCE_UNIT_CODE", insertable = false, updatable = false)
	private String resourceUnitCode;
	
	@Column(name = "MOBILIZATION_START_DATE", insertable = false, updatable = false, nullable=true)
	private Date mobilizationStartDate;

	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;
	
	@Column(name = "INCIDENT_NAME", insertable = false, updatable = false)
	private String incidentName;
	
	@Column(name = "INCIDENT_NUMBER", insertable = false, updatable = false)
	private String incidentNumber;

	@Column(name="INCIDENT_RESOURCE_ID")
	private Long incidentResourceId;

	@Column(name="NAME_AT_INCIDENT", insertable = false, updatable = false)
	private String nameAtIncident;

	@Column(name="WORK_PERIOD_ID", insertable = false, updatable = false)
	private Long workPeriodId;

	@Column(name = "CI_ARRIVAL_JET_PORT", insertable = false, updatable = false)
	private String ciArrivalJetPort;

	@Column(name="CI_CHECK_IN_DATE", insertable = false, updatable = false)
	private Date ciCheckInDate;
	
	@Column(name="CI_FIRST_WORK_DATE", insertable = false, updatable = false)
	private Date ciFirstWorkDate;
	
	@Column(name="CI_LENGTH_AT_ASSIGNMENT", insertable = false, updatable = false)
	private Long ciLengthAtAssignment;
	
	@Column(name="CI_PRE_PLANNING_REMARKS", insertable = false, updatable = false)
	private String ciPrePlanningRemarks;
	
    @Column(name = "DM_TENTATIVE_DEMOB_CITY", insertable = false, updatable = false, nullable=true)
	private String dmTentativeDemobCity;
	
	@Column(name = "DM_TENTATIVE_DEMOB_STATE", insertable = false, updatable = false, nullable=true)
	private String dmTentativeDemobState;

	@Column(name = "DM_TENTATIVE_Release_DATE", insertable = false, updatable = false, nullable=true)
	private Date dmTentativeReleaseDate;

	@Column(name = "DM_Release_DATE", insertable = false, updatable = false, nullable=true)
	private Date dmReleaseDate;
	
	@Column(name = "DM_TRAVEL_METHOD", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TravelMethodTypeEnum dmTravelMethod;
	
	@Column(name = "CI_TRAVEL_METHOD", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TravelMethodTypeEnum ciTravelMethod;
	
	@Column(name = "KIND_CODE", insertable = false, updatable = false)
	private String kindCode;
	
	@Column(name = "KIND_DESCRIPTION", insertable = false, updatable = false)
	private String kindDescription;

	@Column(name="REQUEST_NUMBER", insertable = false, updatable = false)
	private String requestNumber;
	
	@Column(name="IS_TRAINING", insertable = false, updatable = false)
	private Boolean trainee;
	
	@Column(name="ASSIGNMENT_STATUS", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;
	
	@Column(name="ASSIGNMENT_ID", insertable = false, updatable = false)
	private Long assignmentId;
	
    @Column(name="LEADER_NAME",insertable=false,updatable=false)
    private String leaderName;
    
    @Column(name="LEADER_LAST_NAME",insertable=false,updatable=false)
    private String leaderLastName;
    
    @Column(name="LEADER_FIRST_NAME",insertable=false,updatable=false)
    private String leaderFirstName;

	@Column(name="LEADER_IS_TRAINING", insertable = false, updatable = false)
	private Boolean leaderIsTrainee;
    
    @Column(name="DM_IS_REASSIGNABLE",insertable=false,updatable=false)
    private Boolean dmReassignable;
    
    @Column(name="DM_IS_CHECKOUT_FORM_PRINTED",insertable=false,updatable=false)
    private Boolean dmCheckoutFormPrinted;

    @Column(name="DM_IS_PLANNING_DISPATCH_NOTIF",insertable=false,updatable=false)
    private Boolean dmPlanningDispatchNotified;

    @Column(name="DM_IS_RELEASE_DISPATCH_NOTIF",insertable=false,updatable=false)
    private Boolean dmReleaseDispatchNotified;
    
    @Column(name="DM_IS_REST_OVERNIGHT",insertable=false,updatable=false)
    private Boolean dmRestOvernight;

    @Column(name="DM_RELEASE_REMARKS",insertable=false,updatable=false)
    private String dmReleaseRemarks;
    
    @Column(name="DM_ESTIMATED_ARRIVAL_DATE",insertable=false,updatable=false)
    private Date dmEstimatedArrivalDate;

    @Column(name="AIR_IS_DISPATCH_NOTIFIED",insertable=false,updatable=false)
    private Boolean dmAirDispatchNotified;
    
    @Column(name="AIR_REMARKS",insertable=false,updatable=false)
    private String airRemarks;
    
    @Column(name="HOURS_TO_AIRPORT",insertable=false,updatable=false)
    private Integer hoursToAirport;
    
    @Column(name="MINUTES_TO_AIRPORT",insertable=false,updatable=false)
    private Integer minutesToAirport;
    
    @Column(name="IS_ITINERARY_RECEIVED",insertable=false,updatable=false)
    private Boolean itineraryReceived;
    
    @Column(name="NAME_ON_PICTURE_ID",insertable=false,updatable=false)
    private String nameOnPictureId;

    @Column(name="DEPART_FROM_JETPORT",insertable=false,updatable=false)
    private String departFromJetport;
    
    @Column(name="OSI_REMARKS",insertable=false,updatable=false)
    private String overnightRemarks;
    
    @Column(name="IR_ACTIVE", insertable=false, updatable=false)
    private Boolean irActive;

    @Column(name="DEPARTMENT_CODE", insertable=false, updatable=false)
    private String departmentCode;

    @Column(name="DEPARTMENT_SUB_CODE", insertable=false, updatable=false)
    private String departmentSubCode;
    
    @Column(name="DEPARTMENT_DESC", insertable=false, updatable=false)
    private String departmentDesc;

    @Column(name="DEPARTMENT_SUB_DESC", insertable=false, updatable=false)
    private String departmentSubDesc;

	@Column(name="ASSIGNMENT_TIME_ID")
	private Long assignmentTimeId;
	
	@Column(name="LEADER_TYPE")
	private Integer leaderType;
	
	@Column(name="ACCOUNT_CODE")
	private String accountCode;
    
	@Column(name="EMPLOYMENT_TYPE")
	private String employmentType;

	@Column(name = "VIN_NAME")
	private String vinName;

	@Column(name = "CONTRACTOR_NAME")
	private String contractorName;

	@Column(name = "CONTRACTOR_AGREEMENT_NUMBER")
	private String contractorAgreementNumber;
	
	@Column(name = "REQUEST_NUMBER_SORTVALUE")
	private String requestNumberSortValue;

	@Column(name = "ASSIGN_DATE", insertable = false, updatable = false)
	private Date assignDate;
	
	@Column(name = "PAYMENT_AGENCY")
	private String paymentAgency;
	
	@Column(name = "ACCRUAL_CODE")
	private String accrualCode;
	
	@Column(name = "IS_STRIKE_TEAM", insertable = false, updatable = false)
	private Boolean strikeTeam;
	
	@Column(name = "IS_PERSON", insertable = false, updatable = false)
	private Boolean isPerson;

	@Column(name = "IS_LINE_OVERHEAD", insertable = false, updatable = false)
	private Boolean lineOverhead;

	@Column(name = "SUB_GROUP_CATEGORY_CODE")
	private String subGroupCategoryCode;

//	@Column(name = "COST_REMARKS", insertable = false, updatable = false)
	@Column(name = "COST_REMARKS")
	private String costRemarks;

	@Column(name = "HAS_TNSP_RECORD", insertable = false, updatable = false)
	private Boolean hasTnspRecord;
	
    public IncidentResourceView2Impl() {

	}


	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId() {
		return resourceId;
	}


	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}


	/**
	 * Returns the parentResourceId.
	 *
	 * @return 
	 *		the parentResourceId to return
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}


	/**
	 * Sets the parentResourceId.
	 *
	 * @param parentResourceId 
	 *			the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}


	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public Boolean getPermanent() {
		return permanent;
	}


	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}


	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() {
		return enabled;
	}


	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted() {
		return contracted;
	}


	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	/**
	 * Returns the leader.
	 *
	 * @return 
	 *		the leader to return
	 */
	public Boolean getLeader() {
		return leader;
	}


	/**
	 * Sets the leader.
	 *
	 * @param leader 
	 *			the leader to set
	 */
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	/**
	 * Returns the other1.
	 *
	 * @return 
	 *		the other1 to return
	 */
	public String getOther1() {
		return other1;
	}


	/**
	 * Sets the other1.
	 *
	 * @param other1 
	 *			the other1 to set
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}


	/**
	 * Returns the other2.
	 *
	 * @return 
	 *		the other2 to return
	 */
	public String getOther2() {
		return other2;
	}


	/**
	 * Sets the other2.
	 *
	 * @param other2 
	 *			the other2 to set
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}


	/**
	 * Returns the other3.
	 *
	 * @return 
	 *		the other3 to return
	 */
	public String getOther3() {
		return other3;
	}


	/**
	 * Sets the other3.
	 *
	 * @param other3 
	 *			the other3 to set
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
	}


	/**
	 * Returns the requestCategory.
	 *
	 * @return 
	 *		the requestCategory to return
	 */
	public String getRequestCategory() {
		return requestCategory;
	}


	/**
	 * Sets the requestCategory.
	 *
	 * @param requestCategory 
	 *			the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}


	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}


	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Returns the resourceStatus.
	 *
	 * @return 
	 *		the resourceStatus to return
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}


	/**
	 * Sets the resourceStatus.
	 *
	 * @param resourceStatus 
	 *			the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	/**
	 * Returns the numberOfPersonnel.
	 *
	 * @return 
	 *		the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}


	/**
	 * Sets the numberOfPersonnel.
	 *
	 * @param numberOfPersonnel 
	 *			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * Returns the resourceAgencyName.
	 *
	 * @return 
	 *		the resourceAgencyName to return
	 */
	public String getResourceAgencyName() {
		return resourceAgencyName;
	}


	/**
	 * Sets the resourceAgencyName.
	 *
	 * @param resourceAgencyName 
	 *			the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName) {
		this.resourceAgencyName = resourceAgencyName;
	}


	/**
	 * Returns the resourceOrganizationName.
	 *
	 * @return 
	 *		the resourceOrganizationName to return
	 */
	public String getResourceOrganizationName() {
		return resourceOrganizationName;
	}


	/**
	 * Sets the resourceOrganizationName.
	 *
	 * @param resourceOrganizationName 
	 *			the resourceOrganizationName to set
	 */
	public void setResourceOrganizationName(String resourceOrganizationName) {
		this.resourceOrganizationName = resourceOrganizationName;
	}


	/**
	 * Returns the mobilizationStartDate.
	 *
	 * @return 
	 *		the mobilizationStartDate to return
	 */
	public Date getMobilizationStartDate() {
		return mobilizationStartDate;
	}


	/**
	 * Sets the mobilizationStartDate.
	 *
	 * @param mobilizationStartDate 
	 *			the mobilizationStartDate to set
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate) {
		this.mobilizationStartDate = mobilizationStartDate;
	}


	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}


	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}


	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}


	/**
	 * Returns the incidentNumber.
	 *
	 * @return 
	 *		the incidentNumber to return
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}


	/**
	 * Sets the incidentNumber.
	 *
	 * @param incidentNumber 
	 *			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber= incidentNumber;
	}

	
	/**
	 * Returns the incidentResourceId.
	 *
	 * @return 
	 *		the incidentResourceId to return
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}


	/**
	 * Sets the incidentResourceId.
	 *
	 * @param incidentResourceId 
	 *			the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}


	/**
	 * Returns the nameAtIncident.
	 *
	 * @return 
	 *		the nameAtIncident to return
	 */
	public String getNameAtIncident() {
		return nameAtIncident;
	}


	/**
	 * Sets the nameAtIncident.
	 *
	 * @param nameAtIncident 
	 *			the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;
	}


	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}


	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}


	/**
	 * Returns the ciArrivalJetPort.
	 *
	 * @return 
	 *		the ciArrivalJetPort to return
	 */
	public String getCiArrivalJetPort() {
		return ciArrivalJetPort;
	}


	/**
	 * Sets the ciArrivalJetPort.
	 *
	 * @param ciArrivalJetPort 
	 *			the ciArrivalJetPort to set
	 */
	public void setCiArrivalJetPort(String ciArrivalJetPort) {
		this.ciArrivalJetPort = ciArrivalJetPort;
	}


	/**
	 * Returns the checkInDate.
	 *
	 * @return 
	 *		the checkInDate to return
	 */
	public Date getCiCheckInDate() {
		return ciCheckInDate;
	}


	/**
	 * Sets the checkInDate.
	 *
	 * @param checkInDate 
	 *			the checkInDate to set
	 */
	public void setCiCheckInDate(Date checkInDate) {
		this.ciCheckInDate = checkInDate;
	}


	/**
	 * Returns the ciFirstWorkDate.
	 *
	 * @return 
	 *		the ciFirstWorkDate to return
	 */
	public Date getCiFirstWorkDate() {
		return ciFirstWorkDate;
	}


	/**
	 * Sets the ciFirstWorkDate.
	 *
	 * @param ciFirstWorkDate 
	 *			the ciFirstWorkDate to set
	 */
	public void setCiFirstWorkDate(Date ciFirstWorkDate) {
		this.ciFirstWorkDate = ciFirstWorkDate;
	}


	/**
	 * Returns the ciLengthAtAssignment.
	 *
	 * @return 
	 *		the ciLengthAtAssignment to return
	 */
	public Long getCiLengthAtAssignment() {
		return ciLengthAtAssignment;
	}


	/**
	 * Sets the ciLengthAtAssignment.
	 *
	 * @param ciLengthAtAssignment 
	 *			the ciLengthAtAssignment to set
	 */
	public void setCiLengthAtAssignment(Long ciLengthAtAssignment) {
		this.ciLengthAtAssignment = ciLengthAtAssignment;
	}


	/**
	 * Returns the ciPrePlanningRemarks.
	 *
	 * @return 
	 *		the ciPrePlanningRemarks to return
	 */
	public String getCiPrePlanningRemarks() {
		return ciPrePlanningRemarks;
	}


	/**
	 * Sets the ciPrePlanningRemarks.
	 *
	 * @param ciPrePlanningRemarks 
	 *			the ciPrePlanningRemarks to set
	 */
	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks) {
		this.ciPrePlanningRemarks = ciPrePlanningRemarks;
	}


	/**
	 * Returns the dmTentativeDemobCity.
	 *
	 * @return 
	 *		the dmTentativeDemobCity to return
	 */
	public String getDmTentativeDemobCity() {
		return dmTentativeDemobCity;
	}


	/**
	 * Sets the dmTentativeDemobCity.
	 *
	 * @param dmTentativeDemobCity 
	 *			the dmTentativeDemobCity to set
	 */
	public void setDmTentativeDemobCity(String dmTentativeDemobCity) {
		this.dmTentativeDemobCity = dmTentativeDemobCity;
	}


	/**
	 * Returns the dmTentativeDemobState.
	 *
	 * @return 
	 *		the dmTentativeDemobState to return
	 */
	public String getDmTentativeDemobState() {
		return dmTentativeDemobState;
	}


	/**
	 * Sets the dmTentativeDemobState.
	 *
	 * @param dmTentativeDemobState 
	 *			the dmTentativeDemobState to set
	 */
	public void setDmTentativeDemobState(String dmTentativeDemobState) {
		this.dmTentativeDemobState = dmTentativeDemobState;
	}


	/**
	 * Returns the dmTentativeReleaseDate.
	 *
	 * @return 
	 *		the dmTentativeReleaseDate to return
	 */
	public Date getDmTentativeReleaseDate() {
		return dmTentativeReleaseDate;
	}


	/**
	 * Sets the dmTentativeReleaseDate.
	 *
	 * @param dmTentativeReleaseDate 
	 *			the dmTentativeReleaseDate to set
	 */
	public void setDmTentativeReleaseDate(Date dmTentativeReleaseDate) {
		this.dmTentativeReleaseDate = dmTentativeReleaseDate;
	}


	/**
	 * Returns the dmTravelMethod.
	 *
	 * @return 
	 *		the dmTravelMethod to return
	 */
	public TravelMethodTypeEnum getDmTravelMethod() {
		return dmTravelMethod;
	}


	/**
	 * Sets the dmTravelMethod.
	 *
	 * @param dmTravelMethod 
	 *			the dmTravelMethod to set
	 */
	public void setDmTravelMethod(TravelMethodTypeEnum dmTravelMethod) {
		this.dmTravelMethod = dmTravelMethod;
	}


	/**
	 * Returns the kindCode.
	 *
	 * @return 
	 *		the kindCode to return
	 */
	public String getKindCode() {
		return kindCode;
	}


	/**
	 * Sets the kindCode.
	 *
	 * @param kindCode 
	 *			the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}


	/**
	 * Returns the kindDescription.
	 *
	 * @return 
	 *		the kindDescription to return
	 */
	public String getKindDescription() {
		return kindDescription;
	}


	/**
	 * Sets the kindDescription.
	 *
	 * @param kindDescription 
	 *			the kindDescription to set
	 */
	public void setKindDescription(String kindDescription) {
		this.kindDescription = kindDescription;
	}


	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}


	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}


	/**
	 * Returns the trainee.
	 *
	 * @return 
	 *		the trainee to return
	 */
	public Boolean getTrainee() {
		return trainee;
	}


	/**
	 * Sets the trainee.
	 *
	 * @param trainee 
	 *			the trainee to set
	 */
	public void setTrainee(Boolean trainee) {
		this.trainee = trainee;
	}


	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}


	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}


	/**
	 * Returns the assignmentId.
	 *
	 * @return 
	 *		the assignmentId to return
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}


	/**
	 * Sets the assignmentId.
	 *
	 * @param assignmentId 
	 *			the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName() {
		return leaderName;
	}


	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}


	/**
	 * Returns the resourceAgencyCode.
	 *
	 * @return 
	 *		the resourceAgencyCode to return
	 */
	public String getResourceAgencyCode() {
		return resourceAgencyCode;
	}


	/**
	 * Sets the resourceAgencyCode.
	 *
	 * @param resourceAgencyCode 
	 *			the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode) {
		this.resourceAgencyCode = resourceAgencyCode;
	}


	/**
	 * Returns the resourceUnitcode.
	 *
	 * @return 
	 *		the resourceUnitcode to return
	 */
	public String getResourceUnitCode() {
		return resourceUnitCode;
	}


	/**
	 * Sets the resourceUnitcode.
	 *
	 * @param resourceUnitcode 
	 *			the resourceUnitcode to set
	 */
	public void setResourceUnitCode(String resourceUnitcode) {
		this.resourceUnitCode = resourceUnitcode;
	}


	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}


	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}


	public Date getDmReleaseDate() {
		return dmReleaseDate;
	}


	public void setDmReleaseDate(Date dmReleaseDate) {
		this.dmReleaseDate = dmReleaseDate;
	}


	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}


	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}


	public Boolean getDmReassignable() {
		return dmReassignable;
	}


	public void setDmReassignable(Boolean dmReassignable) {
		this.dmReassignable = dmReassignable;
	}


	public Boolean getDmCheckoutFormPrinted() {
		return dmCheckoutFormPrinted;
	}


	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		this.dmCheckoutFormPrinted = dmCheckoutFormPrinted;
	}


	public Boolean getDmPlanningDispatchNotified() {
		return dmPlanningDispatchNotified;
	}


	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		this.dmPlanningDispatchNotified = dmPlanningDispatchNotified;
	}


	public Boolean getDmReleaseDispatchNotified() {
		return dmReleaseDispatchNotified;
	}


	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		this.dmReleaseDispatchNotified = dmReleaseDispatchNotified;
	}


	public Boolean getDmRestOvernight() {
		return dmRestOvernight;
	}


	public void setDmRestOvernight(Boolean dmRestOvernight) {
		this.dmRestOvernight = dmRestOvernight;
	}


	public String getDmReleaseRemarks() {
		return dmReleaseRemarks;
	}


	public void setDmReleaseRemarks(String dmReleaseRemarks) {
		this.dmReleaseRemarks = dmReleaseRemarks;
	}


	public Date getDmEstimatedArrivalDate() {
		return dmEstimatedArrivalDate;
	}


	public void setDmEstimatedArrivalDate(Date dmEstimatedArrivalDate) {
		this.dmEstimatedArrivalDate = dmEstimatedArrivalDate;
	}


	public Boolean getDmAirDispatchNotified() {
		return dmAirDispatchNotified;
	}


	public void setDmAirDispatchNotified(Boolean dmAirDispatchNotified) {
		this.dmAirDispatchNotified = dmAirDispatchNotified;
	}


	public String getAirRemarks() {
		return airRemarks;
	}


	public void setAirRemarks(String airRemarks) {
		this.airRemarks = airRemarks;
	}


	public Integer getHoursToAirport() {
		return hoursToAirport;
	}


	public void setHoursToAirport(Integer hoursToAirport) {
		this.hoursToAirport = hoursToAirport;
	}


	public Integer getMinutesToAirport() {
		return minutesToAirport;
	}


	public void setMinutesToAirport(Integer minutesToAirport) {
		this.minutesToAirport = minutesToAirport;
	}


	public Boolean getItineraryReceived() {
		return itineraryReceived;
	}


	public void setItineraryReceived(Boolean itineraryReceived) {
		this.itineraryReceived = itineraryReceived;
	}


	public String getNameOnPictureId() {
		return nameOnPictureId;
	}


	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}


	public String getDepartFromJetport() {
		return departFromJetport;
	}


	public void setDepartFromJetport(String departFromJetport) {
		this.departFromJetport = departFromJetport;
	}


	public String getOvernightRemarks() {
		return overnightRemarks;
	}


	public void setOvernightRemarks(String overnightRemarks) {
		this.overnightRemarks = overnightRemarks;
	}


	public void setIrActive(Boolean irActive) {
		this.irActive = irActive;
	}


	public Boolean getIrActive() {
		return irActive;
	}


	public String getDepartmentCode() {
		return departmentCode;
	}


	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}


	public String getDepartmentSubCode() {
		return departmentSubCode;
	}


	public void setDepartmentSubCode(String departmentSubCode) {
		this.departmentSubCode = departmentSubCode;
	}


	public String getDepartmentDesc() {
		return departmentDesc;
	}


	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}


	public String getDepartmentSubDesc() {
		return departmentSubDesc;
	}


	public void setDepartmentSubDesc(String departmentSubDesc) {
		this.departmentSubDesc = departmentSubDesc;
	}


	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}


	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}


	public Integer getLeaderType() {
		return leaderType;
	}


	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}


	public String getAccountCode() {
		return accountCode;
	}


	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}


	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}


	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}


	/**
	 * @return the vinName
	 */
	public String getVinName() {
		return vinName;
	}


	/**
	 * @param vinName the vinName to set
	 */
	public void setVinName(String vinName) {
		this.vinName = vinName;
	}


	/**
	 * @return the contractorName
	 */
	public String getContractorName() {
		return contractorName;
	}


	/**
	 * @param contractorName the contractorName to set
	 */
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}


	/**
	 * @return the contractorAgreementNumber
	 */
	public String getContractorAgreementNumber() {
		return contractorAgreementNumber;
	}


	/**
	 * @param contractorAgreementNumber the contractorAgreementNumber to set
	 */
	public void setContractorAgreementNumber(String contractorAgreementNumber) {
		this.contractorAgreementNumber = contractorAgreementNumber;
	}


	public String getRequestNumberSortValue() {
		return requestNumberSortValue;
	}


	public void setRequestNumberSortValue(String requestNumberSortValue) {
		this.requestNumberSortValue = requestNumberSortValue;
	}


	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}


	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}


	/**
	 * @return the paymentAgency
	 */
	public String getPaymentAgency() {
		return paymentAgency;
	}


	/**
	 * @param paymentAgency the paymentAgency to set
	 */
	public void setPaymentAgency(String paymentAgency) {
		this.paymentAgency = paymentAgency;
	}


	/**
	 * @return the accrualCode
	 */
	public String getAccrualCode() {
		return accrualCode;
	}


	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(String accrualCode) {
		this.accrualCode = accrualCode;
	}


	/**
	 * @param strikeTeam the strikeTeam to set
	 */
	public void setStrikeTeam(Boolean strikeTeam) {
		this.strikeTeam = strikeTeam;
	}


	/**
	 * @return the strikeTeam
	 */
	public Boolean getStrikeTeam() {
		return strikeTeam;
	}


	/**
	 * @return the isPerson
	 */
	public Boolean getIsPerson() {
		return isPerson;
	}


	/**
	 * @param isPerson the isPerson to set
	 */
	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}


	/**
	 * @return the lineOverhead
	 */
	public Boolean getLineOverhead() {
		return lineOverhead;
	}


	/**
	 * @param lineOverhead the lineOverhead to set
	 */
	public void setLineOverhead(Boolean lineOverhead) {
		this.lineOverhead = lineOverhead;
	}


	/**
	 * @return the subGroupCategoryCode
	 */
	public String getSubGroupCategoryCode() {
		return subGroupCategoryCode;
	}


	/**
	 * @param subGroupCategoryCode the subGroupCategoryCode to set
	 */
	public void setSubGroupCategoryCode(String subGroupCategoryCode) {
		this.subGroupCategoryCode = subGroupCategoryCode;
	}

	/**
	 * @return the costRemarks
	 */
	public String getCostRemarks() {
		return costRemarks;
	}


	/**
	 * @param costRemarks the costRemarks to set
	 */
	public void setCostRemarks(String costRemarks) {
		this.costRemarks = costRemarks;
	}


	/**
	 * @return the ciTravelMethod
	 */
	public TravelMethodTypeEnum getCiTravelMethod() {
		return ciTravelMethod;
	}


	/**
	 * @param ciTravelMethod the ciTravelMethod to set
	 */
	public void setCiTravelMethod(TravelMethodTypeEnum ciTravelMethod) {
		this.ciTravelMethod = ciTravelMethod;
	}


	/**
	 * @return the leaderLastName
	 */
	public String getLeaderLastName() {
		return leaderLastName;
	}


	/**
	 * @param leaderLastName the leaderLastName to set
	 */
	public void setLeaderLastName(String leaderLastName) {
		this.leaderLastName = leaderLastName;
	}


	/**
	 * @return the leaderFirstName
	 */
	public String getLeaderFirstName() {
		return leaderFirstName;
	}


	/**
	 * @param leaderFirstName the leaderFirstName to set
	 */
	public void setLeaderFirstName(String leaderFirstName) {
		this.leaderFirstName = leaderFirstName;
	}


	/**
	 * @return the leaderIsTrainee
	 */
	public Boolean getLeaderIsTrainee() {
		return leaderIsTrainee;
	}


	/**
	 * @param leaderIsTrainee the leaderIsTrainee to set
	 */
	public void setLeaderIsTrainee(Boolean leaderIsTrainee) {
		this.leaderIsTrainee = leaderIsTrainee;
	}


	public Boolean getHasTnspRecord() {
		return hasTnspRecord;
	}


	public void setHasTnspRecord(Boolean hasTnspRecord) {
		this.hasTnspRecord = hasTnspRecord;
	}
    
}
