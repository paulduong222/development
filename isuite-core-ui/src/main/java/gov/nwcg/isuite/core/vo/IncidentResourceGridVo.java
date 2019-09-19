package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class IncidentResourceGridVo {
	// hierarchalgroupfield is the field ag-grid will use for the grid tree
	private Collection<String> hierachalGroupField = new ArrayList<String>();
	private String groupTag;
	private Boolean group;
	private Long resourceId;
	private Long parentResourceId;
	private Long incidentId;
	private Long incidentResourceId;
	private Long workPeriodId;
	private Long assignmentId;
    private Long workAreaId;
    private Long workAreaIncidentId;

    private Boolean isPerson;
    
	private String requestNumber;
	private String requestNumberSortValue;
	private String resourceName;
	private String lastName;
	private String firstName;
    private String itemCode;
    private String itemName;
	private Boolean trainee;
	private AssignmentStatusTypeEnum assignmentStatus;
    private String agency;
    private String unitId;
    private Date actualReleaseDate;
    private String actualReleaseTime;
	private DateTransferVo actualReleaseDateVo=new DateTransferVo();
	private String incidentNumber;
	private String incidentName;
	private Date ciCheckInDate;
	private String ciCheckInTime;
	private DateTransferVo ciCheckInDateVo=new DateTransferVo();
	private Long numberOfPersonnel;
	private ResourceClassificationEnum resourceClassification;
	private String dmTentativeDemobCity;
	private String dmTentativeDemobState;
	private String ciArrivalJetPort;
    private TravelMethodTypeEnum dmTravelMethod;
    private TravelMethodTypeEnum ciTravelMethod;
	private Date mobilizationStartDate;
	private Date ciFirstWorkDate;
	private DateTransferVo ciFirstWorkDateVo=new DateTransferVo();
	private String plansRemarks;
	private Boolean leader;
    private String leaderName;
    private String leaderLastName;
    private String leaderFirstName;
    private Boolean leaderIsTrainee;
	private String other1;
	private String other2;
	private String other3;
	private String requestCategory;
	private Long ciLengthAtAssignment;
	private String sectionDescription;
	private String subSectionDescription;
	private String vehicleId;
	private Date dmTentativeDemobDate;
	private DateTransferVo dmTentativeDemobDateVo=new DateTransferVo();
	private String accountingCode;
	private String cellPhoneNumber;
	private String strikeTeamTaskForce;
	private Boolean dmReassignable;
    private Boolean dmCheckoutFormPrinted;
    private Boolean dmPlanningDispatchNotified;
    private Boolean dmReleaseDispatchNotified;
    private Boolean dmRestOvernight;
    private String dmReleaseRemarks;
    private Date dmEstimatedArrivalDate;
	private String dmEstimatedArrivalTime;
	private DateTransferVo dmEstimatedArrivalDateVo=new DateTransferVo();
    private Boolean dmAirDispatchNotified;
    private String airRemarks;
    private Integer hoursToAirport;
    private Integer minutesToAirport;
    private Boolean itineraryReceived;
    private String nameOnPictureId;
    private String departFromJetport;
    private String overnightRemarks;
	private String departmentCode;
	private String departmentSubCode;
	private String departmentDesc;
	private String departmentSubDesc;
	private Long assignmentTimeId;
	private String leaderType;
	
	private Boolean permanent;
	private Boolean enabled;
	private Boolean contracted;
	private String resourceStatus;
	private String resourceAgencyName;
	private String resourceAgencyCode;
	private Date resourceDeletedDate;
	private String resourceOrganizationName;
	private String resourceUnitCode;
	private String nameAtIncident;
	private String ciPrePlanningRemarks;
	private Date dmTentativeReleaseDate;
	private String dmTentativeReleaseTime;
	private DateTransferVo dmTentativeReleaseDateVo=new DateTransferVo();
	private String kindCode;
	private String kindDescription;
	private Boolean strikeTeam;
	private Boolean deletable;
	
	private String dmReturnTravelMethod;
	private String dmTentativeReleaseRemarks;

	private String specialInstructions;
	private Boolean supplies; 

	private String employmentType;

	private String vinName;
	private String contractorName;
	private String contractorAgreementNumber;
	
	private String paymentAgency;
	private String accrualCode;
	private Date assignDate;
	private DateTransferVo assignDateVo=new DateTransferVo();
	
    private Collection<IncidentResourceGridVo> children=new ArrayList<IncidentResourceGridVo>();
	
	private Boolean isLineOverhead;
	private String subGroupCategoryCode;
	
	private String costRemarks;
	
	private Boolean hasTnspRecord=false;
	
    public Boolean getIsLineOverhead() {
		return isLineOverhead;
	}

	public void setIsLineOverhead(Boolean isLineOverhead) {
		this.isLineOverhead = isLineOverhead;
	}

	public String getSubGroupCategoryCode() {
		return subGroupCategoryCode;
	}

	public void setSubGroupCategoryCode(String subGroupCategoryCode) {
		this.subGroupCategoryCode = subGroupCategoryCode;
	}
	
    public IncidentResourceGridVo(){
    	
    }

	/**
	 * Returns the groupTag.
	 *
	 * @return 
	 *		the groupTag to return
	 */
	public String getGroupTag() {
		return groupTag;
	}

	/**
	 * Sets the groupTag.
	 *
	 * @param groupTag 
	 *			the groupTag to set
	 */
	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
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
	 *		the enabled to returnR
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
	
	
	public Date getResourceDeletedDate() {
		return resourceDeletedDate;
	}

	public void setResourceDeletedDate(Date resourceDeletedDate) {
		this.resourceDeletedDate = resourceDeletedDate;
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
		this.incidentNumber = incidentNumber;
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
	 * Returns the ciCheckInDate.
	 *
	 * @return 
	 *		the ciCheckInDate to return
	 */
	public Date getCiCheckInDate() {
		return ciCheckInDate;
	}

	/**
	 * Sets the ciCheckInDate.
	 *
	 * @param ciCheckInDate 
	 *			the ciCheckInDate to set
	 */
	public void setCiCheckInDate(Date ciCheckInDate) {
		this.ciCheckInDate = ciCheckInDate;
		if(null != this.ciCheckInDate){
			this.ciCheckInTime=DateUtil.toMilitaryTime(this.ciCheckInDate);
			this.ciCheckInDateVo.setDateString(DateUtil.toDateString(ciCheckInDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.ciCheckInDateVo.setTimeString(ciCheckInTime);			
		}
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
		if(null != this.ciFirstWorkDate){
			this.ciFirstWorkDateVo.setDateString(DateUtil.toDateString(ciFirstWorkDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.ciFirstWorkDateVo.setTimeString("");			
		}
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
		if(null != this.dmTentativeReleaseDate){
			this.dmTentativeReleaseDateVo.setDateString(DateUtil.toDateString(dmTentativeReleaseDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.dmEstimatedArrivalDateVo.setTimeString("");			
		}
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

	public void setStatusCode(String statusCode){
		if(StringUtility.hasValue(statusCode)){
			this.assignmentStatus=AssignmentStatusTypeEnum.getByCode(statusCode);
		}
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
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * Returns the workAreaIncidentId.
	 *
	 * @return 
	 *		the workAreaIncidentId to return
	 */
	public Long getWorkAreaIncidentId() {
		return workAreaIncidentId;
	}

	/**
	 * Sets the workAreaIncidentId.
	 *
	 * @param workAreaIncidentId 
	 *			the workAreaIncidentId to set
	 */
	public void setWorkAreaIncidentId(Long workAreaIncidentId) {
		this.workAreaIncidentId = workAreaIncidentId;
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
	 * Returns the children.
	 *
	 * @return 
	 *		the children to return
	 */
	public Collection<IncidentResourceGridVo> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children 
	 *			the children to set
	 */
	public void setChildren(Collection<IncidentResourceGridVo> children) {
		this.children = children;
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
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode() {
		return resourceUnitCode;
	}

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode) {
		this.resourceUnitCode = resourceUnitCode;
	}
	
	/**
	 * Creates and returns a hierarchal collection of IncidentResourceGridVo's.
	 * Hierarchy is based on the request number sequence.
	 * 
	 * @param vos
	 * 			the source collection of incidentResourceGridVo's.
	 * @return
	 * 			hierarchal collection of incidentResourceGridVo's 
	 * @throws Exception
	 */
	public static Collection<IncidentResourceGridVo> ttoHierarchyCollection(Collection<IncidentResourceGridVo> vos) throws Exception {
		Collection<IncidentResourceGridVo> rtnVos = new ArrayList<IncidentResourceGridVo>();
		
		for(IncidentResourceGridVo vo : vos){
			int lastDotIndex=StringUtils.lastIndexOf(vo.getRequestNumber(), ".");
			   
			if(lastDotIndex>0){
				if( (null!=vo.getFirstName()) && (vo.getFirstName().equalsIgnoreCase("RALPH")) ){
					//System.out.println(vo.getFirstName());
				}
				String parentRequestNumber=StringUtils.substringBeforeLast(vo.getRequestNumber(), ".");
				vo.setGroupTag(parentRequestNumber);
				aaddToParent(vos,parentRequestNumber,vo);
			}else{
				vo.setGroupTag(vo.getRequestNumber());
				rtnVos.add(vo);
			}
		}

		return rtnVos;
	}
	
	/**
	 * Adds a vo to it's parent vo based on request number sequence.
	 * 
	 * @param vos
	 * 			source collection of vo's.
	 * @param parentRequestNumber
	 * 			the parent vo request number
	 * @param voToAdd
	 * 			the vo to add to it's parent (if found)
	 * @throws Exception
	 */
	private static void aaddToParent(Collection<IncidentResourceGridVo> vos, String parentRequestNumber, IncidentResourceGridVo voToAdd) throws Exception {
		for(IncidentResourceGridVo vo : vos){
			if(vo.getRequestNumber().equals(parentRequestNumber)){
				vo.getChildren().add(voToAdd);
				return;
			}
			if(vo.children.size()>0){
				for(IncidentResourceGridVo cvo : vo.children){
					if(cvo.getRequestNumber().equals(parentRequestNumber)){
						aaddToParent(vo.children,parentRequestNumber, voToAdd);
						return;
					}
				}
			}
		}		
	}


	/**
	 * Creates and returns a hierarchal collection of IncidentResourceGridVo's.
	 * 
	 * @param vos
	 * 			the source collection of incidentResourceGridVo's.
	 * @return
	 * 			hierarchal collection of incidentResourceGridVo's 
	 * @throws Exception
	 */
	public static Collection<IncidentResourceGridVo> toHierarchyCollection(Collection<IncidentResourceGridVo> vos, Long startResourceId) throws Exception {
		Collection<IncidentResourceGridVo> rtnVos = new ArrayList<IncidentResourceGridVo>();
		Collection<IncidentResourceGridVo> redoVos = new ArrayList<IncidentResourceGridVo>();
		
		for(IncidentResourceGridVo vo : vos){
			Long parentResourceId = vo.getParentResourceId();
			vo.setGroup(false);

			if(null != vo.getLastName() && vo.getLastName().length()>0)
				vo.setResourceName(vo.getLastName() + ", " + vo.getFirstName());
				
			//System.out.println(vo.getResourceId() + " " + vo.getResourceName());
			
			if((null!=parentResourceId) &&(parentResourceId>0L)){
				if(hasParent(vos,parentResourceId)){
					vo.setGroupTag(parentResourceId.toString());
					addToParent(vos,parentResourceId,vo);
				}else{
					rtnVos.add(vo);
				}
			}else{
				vo.setGroupTag("");
				rtnVos.add(vo);
			}
		}

		if(rtnVos.size()==0){
			if(null != startResourceId){
				for(IncidentResourceGridVo vo : vos){
					if(vo.getResourceId().compareTo(startResourceId)==0)
						rtnVos.add(vo);
				}
			}
		}
		
		// apply sort on requestNumber
		Collections.sort((List)rtnVos,new RequestNumberComparator());
		
		return rtnVos;
	}

	// do a recursive loop and determine if we have the parent object
	private static boolean hasParent(Collection<IncidentResourceGridVo> vos, Long parentResourceId) throws Exception {
		boolean rtn=false;
		for(IncidentResourceGridVo vo : vos){
			
			if(vo.getResourceId().compareTo(parentResourceId)==0){
				rtn=true;
			}
			
			if(!rtn && vo.children.size()>0){
				rtn=hasParent(vo.children,parentResourceId);
			}
		
			if(rtn)
				return rtn;
		}
		return rtn;
		
	}

	
	/**
	 * Adds a vo to it's parent vo based on request number sequence.
	 * 
	 * @param vos
	 * 			source collection of vo's.
	 * @param parentResourceId
	 * 			the parent resource Id
	 * @param voToAdd
	 * 			the vo to add to it's parent (if found)
	 * @throws Exception
	 */
	private static void addToParent(Collection<IncidentResourceGridVo> vos, Long parentResourceId, IncidentResourceGridVo voToAdd) throws Exception {

		for(IncidentResourceGridVo vo : vos){
			if(vo.getResourceId().equals(parentResourceId)){
				vo.setGroup(true);
				vo.getChildren().add(voToAdd);
				return;
			}
			if(vo.children.size()>0){
				for(IncidentResourceGridVo cvo : vo.children){
					if(cvo.getResourceId().equals(parentResourceId)){
						cvo.getChildren().add(voToAdd);
						return;
					}
				}
			}
		}		
	}

	static class RequestNumberComparator implements Comparator{

		public int compare(Object vo1, Object vo2){

//			String val1 = ( (IncidentResourceGridVo) vo1).getRequestNumber();
//			String val2 = ( (IncidentResourceGridVo) vo2).getRequestNumber();
			
			String val1 = ( (IncidentResourceGridVo) vo1).getRequestNumberSortValue();
			String val2 = ( (IncidentResourceGridVo) vo2).getRequestNumberSortValue();

			if(null == val1)val1="";
			if(null == val2)val2="";
			
			return val1.compareTo(val2);
		}
	}
	
	public static Collection<IncidentResourceGridVo> reGroup(Collection<IncidentResourceGridVo> vos, Collection<String> parentControlNames, Collection<Long> irIds ) {
		Collection<IncidentResourceGridVo> newVos = new ArrayList<IncidentResourceGridVo>();
		for(IncidentResourceGridVo vo : vos ) {
			if ( !irIds.contains(vo.getIncidentResourceId())){
				Collection<String> controlNamesThis = new ArrayList<String>();
				controlNamesThis.addAll(parentControlNames);
				controlNamesThis.add(vo.getRequestNumber());
				vo.setHierachalGroupField(controlNamesThis);
				newVos.add(vo);
				irIds.add(vo.getIncidentResourceId());
				if(CollectionUtility.hasValue(vo.getChildren())){
					newVos.addAll(IncidentResourceGridVo.reGroup(vo.getChildren(), controlNamesThis, irIds));
				}
			}
		}
		
		return newVos;
	}
	
	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}

	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
		if(null != this.actualReleaseDate){
			this.actualReleaseTime=DateUtil.toMilitaryTime(this.actualReleaseDate);
			this.actualReleaseDateVo.setDateString(DateUtil.toDateString(this.actualReleaseDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.actualReleaseDateVo.setTimeString(actualReleaseTime);			
		}
	}

	public String getActualReleaseTime() {
		return actualReleaseTime;
	}

	public void setActualReleaseTime(String actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

	public String getCiCheckInTime() {
		return ciCheckInTime;
	}

	public void setCiCheckInTime(String ciCheckInTime) {
		this.ciCheckInTime = ciCheckInTime;
	}

	public String getPlansRemarks() {
		return plansRemarks;
	}

	public void setPlansRemarks(String plansRemarks) {
		this.plansRemarks = plansRemarks;
	}

	public String getSectionDescription() {
		return sectionDescription;
	}

	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}

	public String getSubSectionDescription() {
		return subSectionDescription;
	}

	public void setSubSectionDescription(String subSectionDescription) {
		this.subSectionDescription = subSectionDescription;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Date getDmTentativeDemobDate() {
		return dmTentativeDemobDate;
	}

	public void setDmTentativeDemobDate(Date dmTentativeDemobDate) {
		this.dmTentativeDemobDate = dmTentativeDemobDate;
		if(null != this.dmTentativeDemobDate){
			this.dmTentativeDemobDateVo.setDateString(DateUtil.toDateString(dmTentativeDemobDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.dmTentativeDemobDateVo.setTimeString("");			
		}
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getStrikeTeamTaskForce() {
		return strikeTeamTaskForce;
	}

	public void setStrikeTeamTaskForce(String strikeTeamTaskForce) {
		this.strikeTeamTaskForce = strikeTeamTaskForce;
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
		if(null != this.dmEstimatedArrivalDate){
			this.dmEstimatedArrivalTime=DateUtil.toMilitaryTime(this.dmEstimatedArrivalDate);
			this.dmEstimatedArrivalDateVo.setDateString(DateUtil.toDateString(dmEstimatedArrivalDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.dmEstimatedArrivalDateVo.setTimeString(dmEstimatedArrivalTime);			
		}
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

	public String getDmEstimatedArrivalTime() {
		return dmEstimatedArrivalTime;
	}

	public void setDmEstimatedArrivalTime(String dmEstimatedArrivalTime) {
		this.dmEstimatedArrivalTime = dmEstimatedArrivalTime;
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
	
	public Boolean getDeletable() {		
		return deletable;
	}

	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}

	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}

	public String getDmTentativeReleaseTime() {
		return dmTentativeReleaseTime;
	}

	public void setDmTentativeReleaseTime(String dmTentativeReleaseTime) {
		this.dmTentativeReleaseTime = dmTentativeReleaseTime;
	}

	public String getDmReturnTravelMethod() {
		return dmReturnTravelMethod;
	}

	public void setDmReturnTravelMethod(String dmReturnTravelMethod) {
		this.dmReturnTravelMethod = dmReturnTravelMethod;
	}

	public String getDmTentativeReleaseRemarks() {
		return dmTentativeReleaseRemarks;
	}

	public void setDmTentativeReleaseRemarks(String dmTentativeReleaseRemarks) {
		this.dmTentativeReleaseRemarks = dmTentativeReleaseRemarks;
	}


	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public Boolean getSupplies() {
		return supplies;
	}

	public void setSupplies(Boolean supplies) {
		this.supplies = supplies;
	}

	public String getLeaderType() {
		return leaderType;
	}

	public void setLeaderType(String leaderType) {
		this.leaderType = leaderType;
	}

	public void setLeaderTypeNumber(Integer val){
		if(null != val && val.intValue()>-1){
			if(val.intValue() == 1)
				leaderType="PRIMARY";
			else if(val.intValue()==2)
				leaderType="SECONDARY";
			else
				leaderType="";
		}else
			leaderType="";
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

	public void setRequestNumberSortValue(String requestNumberSortValue) {
		this.requestNumberSortValue = requestNumberSortValue;
	}

	public String getRequestNumberSortValue() {
		return requestNumberSortValue;
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
		if(null != this.assignDate){
			this.assignDateVo.setDateString(DateUtil.toDateString(assignDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			this.assignDateVo.setTimeString("");			
		}
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
	 * @return the ciCheckInDateVo
	 */
	public DateTransferVo getCiCheckInDateVo() {
		return ciCheckInDateVo;
	}

	/**
	 * @param ciCheckInDateVo the ciCheckInDateVo to set
	 */
	public void setCiCheckInDateVo(DateTransferVo ciCheckInDateVo) {
		this.ciCheckInDateVo = ciCheckInDateVo;
	}

	/**
	 * @return the actualReleaseDateVo
	 */
	public DateTransferVo getActualReleaseDateVo() {
		return actualReleaseDateVo;
	}

	/**
	 * @param actualReleaseDateVo the actualReleaseDateVo to set
	 */
	public void setActualReleaseDateVo(DateTransferVo actualReleaseDateVo) {
		this.actualReleaseDateVo = actualReleaseDateVo;
	}

	/**
	 * @return the ciFirstWorkDateVo
	 */
	public DateTransferVo getCiFirstWorkDateVo() {
		return ciFirstWorkDateVo;
	}

	/**
	 * @param ciFirstWorkDateVo the ciFirstWorkDateVo to set
	 */
	public void setCiFirstWorkDateVo(DateTransferVo ciFirstWorkDateVo) {
		this.ciFirstWorkDateVo = ciFirstWorkDateVo;
	}

	/**
	 * @return the dmTentativeDemobDateVo
	 */
	public DateTransferVo getDmTentativeDemobDateVo() {
		return dmTentativeDemobDateVo;
	}

	/**
	 * @param dmTentativeDemobDateVo the dmTentativeDemobDateVo to set
	 */
	public void setDmTentativeDemobDateVo(DateTransferVo dmTentativeDemobDateVo) {
		this.dmTentativeDemobDateVo = dmTentativeDemobDateVo;
	}

	/**
	 * @return the dmEstimatedArrivalDateVo
	 */
	public DateTransferVo getDmEstimatedArrivalDateVo() {
		return dmEstimatedArrivalDateVo;
	}

	/**
	 * @param dmEstimatedArrivalDateVo the dmEstimatedArrivalDateVo to set
	 */
	public void setDmEstimatedArrivalDateVo(DateTransferVo dmEstimatedArrivalDateVo) {
		this.dmEstimatedArrivalDateVo = dmEstimatedArrivalDateVo;
	}

	/**
	 * @return the dmTentativeReleaseDateVo
	 */
	public DateTransferVo getDmTentativeReleaseDateVo() {
		return dmTentativeReleaseDateVo;
	}

	/**
	 * @param dmTentativeReleaseDateVo the dmTentativeReleaseDateVo to set
	 */
	public void setDmTentativeReleaseDateVo(DateTransferVo dmTentativeReleaseDateVo) {
		this.dmTentativeReleaseDateVo = dmTentativeReleaseDateVo;
	}

	/**
	 * @return the assignDateVo
	 */
	public DateTransferVo getAssignDateVo() {
		return assignDateVo;
	}

	/**
	 * @param assignDateVo the assignDateVo to set
	 */
	public void setAssignDateVo(DateTransferVo assignDateVo) {
		this.assignDateVo = assignDateVo;
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
		if(null != leaderIsTrainee)
			return leaderIsTrainee;
		else
			return false;
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

	public Collection<String> getHierachalGroupField() {
		return hierachalGroupField;
	}

	public void setHierachalGroupField(Collection<String> hierachalGroupField) {
		this.hierachalGroupField = hierachalGroupField;
	}

	public Boolean getGroup() {
		return group;
	}

	public void setGroup(Boolean group) {
		this.group = group;
	}


}
