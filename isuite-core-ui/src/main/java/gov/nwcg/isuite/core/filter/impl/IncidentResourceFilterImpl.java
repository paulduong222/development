package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IncidentResourceCheckboxFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * @author bsteiner
 *
 */
public class IncidentResourceFilterImpl extends FilterImpl implements IncidentResourceFilter {
	
	private static final long serialVersionUID = -3098056085012683579L;
	private Long incidentId=0L;
	private Long incidentGroupId=0L;
	
	private Long workAreaId=0L;
	private Long workAreaIncidentId=0L;
	private Long resourceId=0L;
	private Long workAreaIncidentGroupId=0L;
	private Long incidentResourceId=0L;
	private Long assignmentId=0L;
	
	private boolean assignedToIncident=false;
	private boolean permanent=false;

	private Boolean deletable;	
	private String deletableString;

	private String itemCode;
	private String itemName;
	private String cellPhoneNumber;
	private String vehicleId;
	private String accountingCode;
	private String sectionDescription;
	private String subSectionDescription;
	private String agency;
	private String unitId;
	private Boolean leader;
	private Date actualReleaseDate;
	
	private String other1;
	private String other2;
	private String other3;
	private String requestCategory;
	private String resourceName;
	private String lastName;
	private String firstName;
	private String resourceStatus;
	private Long numberOfPersonnel;
	private String resourceAgencyName;
	private String resourceOrganizationName;
	private String resourceUnitCode;
	private Date mobilizationStartDate;
	private String incidentName;
	private String incidentNumber;
	private String nameAtIncident;
	private String ciArrivalJetPort;
	private Date ciCheckInDate;
	private Date ciFirstWorkDate;
	private Long ciLengthAtAssignment;
	private String ciPrePlanningRemarks;
	private String dmTentativeDemobCity;
	private String dmTentativeDemobState;
	private Date dmTentativeReleaseDate;
    private TravelMethodTypeEnum dmTravelMethod;
	private String kindCode;
	private String kindDescription;
	private String requestNumber;
	private Boolean trainee;
	private AssignmentStatusTypeEnum assignmentStatus;
    private String leaderName;
    private Collection<String> assignmentStatusCodes;
    private Collection<String> requestCategoryCodes;

	private Boolean dmReassignable;
    private Boolean dmCheckoutFormPrinted;
    private Boolean dmPlanningDispatchNotified;
    private Boolean dmReleaseDispatchNotified;
    private Boolean dmRestOvernight;
    private String dmReleaseRemarks;
    private Date dmEstimatedArrivalDate;
    private Boolean dmAirDispatchNotified;
    private String airRemarks;
    private Integer hoursToAirport;
    private Integer minutesToAirport;
    private Boolean itineraryReceived;
    private String nameOnPictureId;
    private String departFromJetport;
    private String overnightRemarks;

    private Long rosterParentId;

    private Date dmTentativeDemobDate;
    
    private String crypticFilterCodeForCiCheckInDate;
    private String crypticFilterCodeForMobilizationStartDate;
    private String crypticFilterCodeForCiFirstWorkDate;
    private String crypticFilterCodeForTentativeDemobDate;

	private String crypticDateFilterCodeForActualReleaseDate;
	private String crypticDateFilterCodeForTentativeReleaseDate;
	private String crypticDateFilterCodeForCheckInDate;
	private String crypticFilterCodeForEstimatedArrivalDate;
    
	private String leaderType;
	private String resourceClassification;
	private String traineeString;		
	private String actualReleaseTime;
	private String checkInTime;
	private String arrivalTime;
	private String tentativeReleaseTime;
	private String hoursToAirportString;
	private String minutesToAirportString;
	private String numberOfPersonnelString;
	private String ciLengthAtAssignmentString;
	private String contractorCooperatorName;
	private String returnTravelMethod;
	private String specialInstructions;
	private String supplies;
	private String dmAirDispatchNotifiedString;
	private String dmCheckoutFormPrintedString;
	private String dmReleaseDispatchNotifiedString;
	private String contractedString;
	private String itineraryReceivedString;
	private String dmPlanningDispatchNotifiedString;
	private String dmReassignableString;
	private String dmRestOvernightString;

	private String vinName;
	private String employmentType;
	private String contractorAgreementNumber;
	
	private IncidentResourceCheckboxFilter checkboxFilter; 

	private Boolean unRosteredOnly=false;
	private Long excludeResourceId;

	private Boolean hierarchal = true;
	
	public IncidentResourceFilterImpl(){
		
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
	 * Returns the assignedToIncident.
	 *
	 * @return 
	 *		the assignedToIncident to return
	 */
	public boolean isAssignedToIncident() {
		return assignedToIncident;
	}

	/**
	 * Returns the assignedToIncident.
	 *
	 * @return 
	 *		the assignedToIncident to return
	 */
	public boolean getAssignedToIncident(){
		return isAssignedToIncident();
	}
	
	/**
	 * Sets the assignedToIncident.
	 *
	 * @param assignedToIncident 
	 *			the assignedToIncident to set
	 */
	public void setAssignedToIncident(boolean assignedToIncident) {
		this.assignedToIncident = assignedToIncident;
	}

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean getPermanent() {
		return isPermanent();
	}

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
//	public boolean isOverhead() {
//		return overhead;
//	}

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
//	public boolean getOverhead() {
//		return isOverhead();
//	}

	/**
	 * Sets the overhead.
	 *
	 * @param overhead 
	 *			the overhead to set
	 */
//	public void setOverhead(boolean overhead) {
//		this.overhead = overhead;
//	}

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
//	public boolean isAircraft() {
//		return aircraft;
//	}

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
//	public boolean getAircraft() {
//		return isAircraft();
//	}

	/**
	 * Sets the aircraft.
	 *
	 * @param aircraft 
	 *			the aircraft to set
	 */
//	public void setAircraft(boolean aircraft) {
//		this.aircraft = aircraft;
//	}

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
//	public boolean isPersonnel() {
//		return personnel;
//	}

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
//	public boolean getPersonnel() {
//		return isPersonnel();
//	}

	/**
	 * Sets the personnel.
	 *
	 * @param personnel 
	 *			the personnel to set
	 */
//	public void setPersonnel(boolean personnel) {
//		this.personnel = personnel;
//	}

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
//	public boolean isCrews() {
//		return crews;
//	}

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
//	public boolean getCrews() {
//		return isCrews();
//	}

	/**
	 * Sets the crews.
	 *
	 * @param crews 
	 *			the crews to set
	 */
//	public void setCrews(boolean crews) {
//		this.crews = crews;
//	}

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
//	public boolean isEquipment() {
//		return equipment;
//	}

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
//	public boolean getEquipment() {
//		return isEquipment();
//	}

	/**
	 * Sets the equipment.
	 *
	 * @param equipment 
	 *			the equipment to set
	 */
//	public void setEquipment(boolean equipment) {
//		this.equipment = equipment;
//	}
	
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
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
//	public boolean isExcludeFilled() {
//		return excludeFilled;
//	}

	/**
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
//	public boolean getExcludeFilled() {
//		return isExcludeFilled();
//	}

	/**
	 * Sets the excludeFilled.
	 *
	 * @param excludeFilled 
	 *			the excludeFilled to set
	 */
//	public void setExcludeFilled(boolean excludeFilled) {
//		this.excludeFilled = excludeFilled;
//	}

	/**
	 * Returns the excludeDemob.
	 *
	 * @return 
	 *		the excludeDemob to return
	 */
//	public boolean isExcludeDemob() {
//		return excludeDemob;
//	}

	/**
	 * Returns the excludeDemob.
	 *
	 * @return 
	 *		the excludeDemob to return
	 */
//	public boolean getExcludeDemob() {
//		return isExcludeDemob();
//	}

	/**
	 * Sets the excludeDemob.
	 *
	 * @param excludeDemob 
	 *			the excludeDemob to set
	 */
//	public void setExcludeDemob(boolean excludeDemob) {
//		this.excludeDemob = excludeDemob;
//	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#setAssignmentStatusCodes(java.util.Collection)
	 */
	public void setAssignmentStatusCodes(Collection<String> assignmentStatusCodes) {
		this.assignmentStatusCodes = assignmentStatusCodes;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#getAssignmentStatusCodes()
	 */
	public Collection<String> getAssignmentStatusCodes() {
		return assignmentStatusCodes;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#setRequestCategoryIds(java.util.Collection)
	 */
	public void setRequestCategoryCodes(Collection<String> requestCategoryCodes) {
		this.requestCategoryCodes = requestCategoryCodes;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#getRequestCategoryIds()
	 */
	public Collection<String> getRequestCategoryCodes() {
		return requestCategoryCodes;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
//	public Boolean isEnabled() {
//		return enabled;
//	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
//	public Object getEnabledObject() {
//		return this.enabledObject;
//	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
//	public void setEnabledObject(Object object) {
//		this.enabledObject = object;
//		
//		if(this.enabledObject == Boolean.TRUE) {
//			this.setEnabled(true);
//		} else if(this.enabledObject == Boolean.FALSE) {
//			this.setEnabled(false);
//		} else {
//			this.setEnabled(null);
//		}
//	}
	
	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
//	public Boolean getEnabled() {
//		return isEnabled();
//	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
//	public void setEnabled(Boolean enabled) {
//		this.enabled = enabled;
//	}

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}
	
	
	/**
	 * @return the deletableString
	 */
	public String getDeletableString() {
		return deletableString;
	}

	/**
	 * @param deletableString the deletableString to set
	 */
	public void setDeletableString(String deletableString) {
		this.deletableString = deletableString;
		this.setDeletable(super.determineDeletableValue(this.deletableString));
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
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(IncidentResourceFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS
		criteria.add( null != filter.getWorkAreaId() && filter.getWorkAreaId() > 0L ? new FilterCriteria("this.workAreaId",filter.getWorkAreaId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getWorkAreaIncidentId() && filter.getWorkAreaIncidentId() > 0L ? new FilterCriteria("this.workAreaIncidentId",filter.getWorkAreaIncidentId(),FilterCriteria.TYPE_EQUAL) : null);
		//criteria.add( null != filter.getWorkAreaIncidentId() && filter.getWorkAreaIncidentId() > 0L ? new FilterCriteria("this.workAreaIncidentId",filter.getWorkAreaIncidentId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getResourceId() && filter.getResourceId() > 0L ? new FilterCriteria("this.resourceId",filter.getResourceId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getTrainee() && filter.getTrainee() ? new FilterCriteria("this.trainee",Boolean.TRUE,FilterCriteria.TYPE_EQUAL) : null);
		
		//TODO: this needs to be moved to IRCFI
//		if(filter.getEnabled())
//			criteria.add( new FilterCriteria("this.enabled",Boolean.TRUE,FilterCriteria.TYPE_EQUAL));
//		else
//			criteria.add( new FilterCriteria("this.enabled",Boolean.FALSE,FilterCriteria.TYPE_EQUAL));

		criteria.add( null != filter.getAssignmentStatus() && !filter.getAssignmentStatus().equals("") ? new FilterCriteria("this.assignmentStatus",filter.getAssignmentStatus(),FilterCriteria.TYPE_EQUAL) : null) ;
		criteria.add( null != filter.getDmTravelMethod() ? new FilterCriteria("this.dmTravelMethod",filter.getDmTravelMethod(),FilterCriteria.TYPE_EQUAL) : null);
		
		// TYPE_NOT_EQUAL
		//criteria.add( filter.isExcludeFilled() ? new FilterCriteria("this.assignmentStatus",AssignmentStatusTypeEnum.F ,FilterCriteria.TYPE_NOT_EQUAL) : null);
		//criteria.add( filter.isExcludeDemob() ? new FilterCriteria("this.assignmentStatus",AssignmentStatusTypeEnum.D ,FilterCriteria.TYPE_NOT_EQUAL) : null);

		//TODO: this needs to be moved to IRCFI
//		Collection<String> reqCatList = new ArrayList<String>();
//		if(filter.getOverhead())reqCatList.add("O");
//		if(filter.getAircraft())reqCatList.add("A");
//		if(filter.getCrews())reqCatList.add("C");
//		if(filter.getEquipment())reqCatList.add("E");
		
		// TYPE_IN_STRING
//		criteria.add( null != reqCatList && reqCatList.size() > 0 ? new FilterCriteria("this.requestCategory",reqCatList,FilterCriteria.TYPE_IN_STRING) : null);
		
		// TYPE_ILIKE
		criteria.add( null != filter.getRequestNumber() && !filter.getRequestNumber().isEmpty() ? new FilterCriteria("this.requestNumber",filter.getRequestNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getIncidentNumber() && !filter.getIncidentNumber().isEmpty() ? new FilterCriteria("this.incidentNumber",filter.getIncidentNumber().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getIncidentName() && !filter.getIncidentName().isEmpty() ? new FilterCriteria("this.incidentName",filter.getIncidentName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getNameAtIncident() && !filter.getNameAtIncident().isEmpty() ? new FilterCriteria("this.nameAtIncidentName",filter.getNameAtIncident().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getLeaderName() && !filter.getLeaderName().isEmpty() ? new FilterCriteria("this.leaderName",filter.getLeaderName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther1() && !filter.getOther1().isEmpty() ? new FilterCriteria("this.other1",filter.getOther1().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther2() && !filter.getOther2().isEmpty() ? new FilterCriteria("this.other2",filter.getOther2().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther3() && !filter.getOther3().isEmpty() ? new FilterCriteria("this.other3",filter.getOther3().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		/*
		criteria.add( null != filter.getResourceName() && !filter.getResourceName().isEmpty() ? new FilterCriteria("this.resourceName",filter.getResourceName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getLastName() && !filter.getLastName().isEmpty() ? new FilterCriteria("this.lastName",filter.getLastName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getFirstName() && !filter.getOther3().isEmpty() ? new FilterCriteria("this.firstName",filter.getFirstName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		*/
		criteria.add( null != filter.getResourceStatus() && !filter.getResourceStatus().isEmpty() ? new FilterCriteria("this.resourceStatus",filter.getResourceStatus().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceAgencyName() && !filter.getResourceAgencyName().isEmpty() ? new FilterCriteria("this.resourceAgencyName",filter.getResourceAgencyName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceOrganizationName() && !filter.getResourceOrganizationName().isEmpty() ? new FilterCriteria("this.resourceOrganizationName",filter.getResourceOrganizationName().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceUnitCode() && !filter.getResourceUnitCode().isEmpty() ? new FilterCriteria("this.resourceUnitCode",filter.getResourceUnitCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getKindDescription() && !filter.getKindDescription().isEmpty() ? new FilterCriteria("this.kindDescription",filter.getKindDescription().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getKindCode() && !filter.getKindCode().isEmpty() ? new FilterCriteria("this.kindCode",filter.getKindCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_ISNULL
		//criteria.add( filter.getDeletable() ? new FilterCriteria("this.permanentResourceId",filter.getDeletable(),FilterCriteria.TYPE_ISNULL) : null);
		
		// filter by deletable true/false
		//TODO: Add additional criteria to determine if resource is deletable such as:
		// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
		if (null != filter.getDeletable()) {
			if(filter.getDeletable()) {
				criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNULL));				
			}
			else {
				criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNOTNULL));
			}			
		}
		
		return criteria;
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

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
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

	public Boolean getLeader() {
		return leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
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

	/**
	 * @return the workAreaIncidentGroupId
	 */
	public Long getWorkAreaIncidentGroupId() {
		return workAreaIncidentGroupId;
	}

	/**
	 * @param workAreaIncidentGroupId the workAreaIncidentGroupId to set
	 */
	public void setWorkAreaIncidentGroupId(Long workAreaIncidentGroupId) {
		this.workAreaIncidentGroupId = workAreaIncidentGroupId;
	}

	/**
	 * @return the rosterParentId
	 */
	public Long getRosterParentId() {
		return rosterParentId;
	}

	/**
	 * @param rosterParentId the rosterParentId to set
	 */
	public void setRosterParentId(Long rosterParentId) {
		this.rosterParentId = rosterParentId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#getCrypticFilterCodeForCiCheckInDate()
	 */
	public String getCrypticFilterCodeForCiCheckInDate() {
		return crypticFilterCodeForCiCheckInDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#setCrypticFilterCodeForCiCheckInDate(java.lang.String)
	 */
	public void setCrypticFilterCodeForCiCheckInDate(String crypticFilterCodeForCiCheckInDate) {
		this.crypticFilterCodeForCiCheckInDate = crypticFilterCodeForCiCheckInDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#getCrypticFilterCodeForMobilizationStartDate()
	 */
	public String getCrypticFilterCodeForMobilizationStartDate() {
		return crypticFilterCodeForMobilizationStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#setCrypticFilterCodeForMobilizationStartDate(java.lang.String)
	 */
	public void setCrypticFilterCodeForMobilizationStartDate(String crypticFilterCodeForMobilizationStartDate) {
		this.crypticFilterCodeForMobilizationStartDate = crypticFilterCodeForMobilizationStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#getCrypticFilterCodeForCiFirstWorkDate()
	 */
	public String getCrypticFilterCodeForCiFirstWorkDate() {
		return crypticFilterCodeForCiFirstWorkDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentResourceFilter#setCrypticFilterCodeForCiFirstWorkDate(java.lang.String)
	 */
	public void setCrypticFilterCodeForCiFirstWorkDate(String crypticFilterCodeForCiFirstWorkDate) {
		this.crypticFilterCodeForCiFirstWorkDate = crypticFilterCodeForCiFirstWorkDate;
	}

	public Date getDmTentativeDemobDate() {
		return dmTentativeDemobDate;
	}

	public void setDmTentativeDemobDate(Date dmTentativeDemobDate) {
		this.dmTentativeDemobDate = dmTentativeDemobDate;
	}

	public String getCrypticFilterCodeForTentativeDemobDate() {
		return crypticFilterCodeForTentativeDemobDate;
	}

	public void setCrypticFilterCodeForTentativeDemobDate(
			String crypticFilterCodeForTentativeDemobDate) {
		this.crypticFilterCodeForTentativeDemobDate = crypticFilterCodeForTentativeDemobDate;
	}

	public String getLeaderType() {
		return leaderType;
	}

	public void setLeaderType(String leaderType) {
		this.leaderType = leaderType;
	}

	public String getResourceClassification() {
		return resourceClassification;
	}

	public void setResourceClassification(String resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	public String getTraineeString() {
		return traineeString;
	}

	public void setTraineeString(String traineeString) {
		this.traineeString = traineeString;
	}

	public String getActualReleaseTime() {
		return actualReleaseTime;
	}

	public void setActualReleaseTime(String actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getTentativeReleaseTime() {
		return tentativeReleaseTime;
	}

	public void setTentativeReleaseTime(String tentativeReleaseTime) {
		this.tentativeReleaseTime = tentativeReleaseTime;
	}

	public String getHoursToAirportString() {
		return hoursToAirportString;
	}

	public void setHoursToAirportString(String hoursToAirportString) {
		this.hoursToAirportString = hoursToAirportString;
	}

	public String getMinutesToAirportString() {
		return minutesToAirportString;
	}

	public void setMinutesToAirportString(String minutesToAirportString) {
		this.minutesToAirportString = minutesToAirportString;
	}

	public String getNumberOfPersonnelString() {
		return numberOfPersonnelString;
	}

	public void setNumberOfPersonnelString(String numberOfPersonnelString) {
		this.numberOfPersonnelString = numberOfPersonnelString;
	}

	public String getCiLengthAtAssignmentString() {
		return ciLengthAtAssignmentString;
	}

	public void setCiLengthAtAssignmentString(String ciLengthAtAssignmentString) {
		this.ciLengthAtAssignmentString = ciLengthAtAssignmentString;
	}

	public String getContractorCooperatorName() {
		return contractorCooperatorName;
	}

	public void setContractorCooperatorName(String contractorCooperatorName) {
		this.contractorCooperatorName = contractorCooperatorName;
	}

	public String getReturnTravelMethod() {
		return returnTravelMethod;
	}

	public void setReturnTravelMethod(String returnTravelMethod) {
		this.returnTravelMethod = returnTravelMethod;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getSupplies() {
		return supplies;
	}

	public void setSupplies(String supplies) {
		this.supplies = supplies;
	}

	public String getDmAirDispatchNotifiedString() {
		return dmAirDispatchNotifiedString;
	}

	public void setDmAirDispatchNotifiedString(String dmAirDispatchNotifiedString) {
		this.dmAirDispatchNotifiedString = dmAirDispatchNotifiedString;
	}

	public String getDmCheckoutFormPrintedString() {
		return dmCheckoutFormPrintedString;
	}

	public void setDmCheckoutFormPrintedString(String dmCheckoutFormPrintedString) {
		this.dmCheckoutFormPrintedString = dmCheckoutFormPrintedString;
	}

	public String getDmReleaseDispatchNotifiedString() {
		return dmReleaseDispatchNotifiedString;
	}

	public void setDmReleaseDispatchNotifiedString(
			String dmReleaseDispatchNotifiedString) {
		this.dmReleaseDispatchNotifiedString = dmReleaseDispatchNotifiedString;
	}

	public String getContractedString() {
		return contractedString;
	}

	public void setContractedString(String contractedString) {
		this.contractedString = contractedString;
	}

	public String getItineraryReceivedString() {
		return itineraryReceivedString;
	}

	public void setItineraryReceivedString(String itineraryReceivedString) {
		this.itineraryReceivedString = itineraryReceivedString;
	}

	public String getDmPlanningDispatchNotifiedString() {
		return dmPlanningDispatchNotifiedString;
	}

	public void setDmPlanningDispatchNotifiedString(
			String dmPlanningDispatchNotifiedString) {
		this.dmPlanningDispatchNotifiedString = dmPlanningDispatchNotifiedString;
	}

	public String getDmReassignableString() {
		return dmReassignableString;
	}

	public void setDmReassignableString(String dmReassignableString) {
		this.dmReassignableString = dmReassignableString;
	}

	public String getDmRestOvernightString() {
		return dmRestOvernightString;
	}

	public void setDmRestOvernightString(String dmRestOvernightString) {
		this.dmRestOvernightString = dmRestOvernightString;
	}

	public String getCrypticDateFilterCodeForActualReleaseDate() {
		return crypticDateFilterCodeForActualReleaseDate;
	}

	public void setCrypticDateFilterCodeForActualReleaseDate(
			String crypticDateFilterCodeForActualReleaseDate) {
		this.crypticDateFilterCodeForActualReleaseDate = crypticDateFilterCodeForActualReleaseDate;
	}

	public String getCrypticDateFilterCodeForTentativeReleaseDate() {
		return crypticDateFilterCodeForTentativeReleaseDate;
	}

	public void setCrypticDateFilterCodeForTentativeReleaseDate(
			String crypticDateFilterCodeForTentativeReleaseDate) {
		this.crypticDateFilterCodeForTentativeReleaseDate = crypticDateFilterCodeForTentativeReleaseDate;
	}

	public String getCrypticDateFilterCodeForCheckInDate() {
		return crypticDateFilterCodeForCheckInDate;
	}

	public void setCrypticDateFilterCodeForCheckInDate(
			String crypticDateFilterCodeForCheckInDate) {
		this.crypticDateFilterCodeForCheckInDate = crypticDateFilterCodeForCheckInDate;
	}

	public String getCrypticFilterCodeForEstimatedArrivalDate() {
		return crypticFilterCodeForEstimatedArrivalDate;
	}

	public void setCrypticFilterCodeForEstimatedArrivalDate(
			String crypticFilterCodeForEstimatedArrivalDate) {
		this.crypticFilterCodeForEstimatedArrivalDate = crypticFilterCodeForEstimatedArrivalDate;
	}

	public void setCheckboxFilter(IncidentResourceCheckboxFilter checkboxFilter) {
		this.checkboxFilter = checkboxFilter;
	}

	public IncidentResourceCheckboxFilter getCheckboxFilter() {
		return checkboxFilter;
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
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
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

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Boolean getUnRosteredOnly() {
		return unRosteredOnly;
	}

	public void setUnRosteredOnly(Boolean unRosteredOnly) {
		this.unRosteredOnly = unRosteredOnly;
	}

	public Long getExcludeResourceId() {
		return excludeResourceId;
	}

	public void setExcludeResourceId(Long excludeResourceId) {
		this.excludeResourceId = excludeResourceId;
	}

	/**
	 * @return the hierarchal
	 */
	public Boolean getHierarchal() {
		return hierarchal;
	}

	/**
	 * @param hierarchal the hierarchal to set
	 */
	public void setHierarchal(Boolean hierarchal) {
		this.hierarchal = hierarchal;
	}

}