package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRPersonPlanView;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_person_plan")
public class CRPersonPlanViewImpl implements CRPersonPlanView {
	
	@Id
	@Column(name = "RESOURCE_ID", insertable = false, updatable = false)
	private Long resourceId;
	
	@Column(name = "ACCRUAL", insertable = false, updatable = false)
	private String accrual;
	
	@Column(name="ACTUAL_RELEASE_DATE", insertable = false, updatable = false)
	private Date actualReleaseDate;
	
	@Column(name="ACTUAL_RELEASE_REMARKS", insertable = false, updatable = false)
    private String actualReleaseRemarks;
	
	@Column(name="ACTUAL_RELEASE_TIME", insertable = false, updatable = false)
	private Date actualReleaseTime;
	
	@Column(name = "AGENCY", insertable = false, updatable = false)
	private String agency;
	
	@Column(name = "AGREEMENT_NUMBER", insertable = false, updatable = false)
	private String agreementNumber;
	
	@Column(name = "IS_AIR_TRAVEL_TO_DISPATCH", insertable = false, updatable = false)
	private String airTravelToDispatch;
	
	@Column(name = "AIRLINE", insertable = false, updatable = false)
	private String airline;
	
	@Column(name = "ASSIGN_DATE", insertable = false, updatable = false)
	private Date assignDate;
	
	@Column(name = "CHECK_IN_DATE", insertable = false, updatable = false)
	private Date checkInDate;
	
	@Column(name = "CHECK_IN_TIME", insertable = false, updatable = false)
	private Date checkInTime;
	
	@Column(name = "IS_CHECKOUT_FORM_PRINTED", insertable = false, updatable = false)
	private String checkoutFormPrinted;
	
	@Column(name = "IS_CONTRACTED", insertable = false, updatable = false)
	private String contracted;
	
	@Column(name = "CONTRACTOR_NAME", insertable = false, updatable = false)
	private String contractorName;
	
	@Column(name = "DEMOBILIZATION_CITY", insertable = false, updatable = false)
	private String demobilizationCity;
	
	@Column(name = "DEMOBILIZATION_DATE", insertable = false, updatable = false)
	private Date demobilizationDate;
	
	@Column(name = "DEMOBILIZATION_STATE", insertable = false, updatable = false)
	private String demobilizationState;
	
	@Column(name = "IS_DISP_NOTIFIED_OF_ACT_REL", insertable = false, updatable = false)
	private String dispatchNotifiedOfActualRelease;
	
	@Column(name = "IS_DISP_NOTIFIED_OF_TENT_REL", insertable = false, updatable = false)
	private String dispatchNotifiedOfTentativeRelease;
	
	@Column(name = "EMPLOYMENT_TYPE", insertable = false, updatable = false)
	private String employmentType;
	
	@Column(name = "ESTIMATED_ARRIVAL_TIME", insertable = false, updatable = false)
	private Date estimatedArrivalTime;
	
	@Column(name = "ESTIMATED_DATE_OF_ARRIVAL", insertable = false, updatable = false)
	private Date estimatedDateOfArrival;

	@Column(name = "FAX_NUMBER", insertable = false, updatable = false)
	private String faxNumber;
	
	@Column(name = "FIRST_DAY_OF_WORK", insertable = false, updatable = false)
	private Date firstDayOfWork;
	
	@Column(name = "FIRST_NAME", insertable = false, updatable = false)
	private String firstName;
	
	@Column(name = "FLIGHT_TIME", insertable = false, updatable = false)
	private Integer flightTime;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name = "INCIDENT_JETPORT", insertable = false, updatable = false)
	private String incidentJetport;

	@Column(name = "INCIDENT_NAME", insertable = false, updatable = false)
	private String incidentName;

	@Column(name = "INCIDENT_NUMBER", insertable = false, updatable = false)
	private String incidentNumber;
	
	@Column(name = "IS_ROSTERED", insertable = false, updatable = false)
	private String rostered; 
	
	@Column(name = "ITEM_CODE", insertable = false, updatable = false)
	private String itemCode; 
	
	@Column(name = "ITEM_CODE_DESCRIPTION", insertable = false, updatable = false)
	private String itemCodeDescription; 
	
	@Column(name = "IS_ITINERARY_RECEIVED", insertable = false, updatable = false)
	private String itineraryReceived; 

	@Column(name = "JETPORT", insertable = false, updatable = false)
	private String jetPort;

	@Column(name = "LAST_NAME", insertable = false, updatable = false)
	private String lastName;

	@Column(name = "IS_LEADER", insertable = false, updatable = false)
	private String leader;

	@Column(name = "LEADER_NAME", insertable = false, updatable = false)
	private String leaderName;

	@Column(name = "LENGTH_OF_ASSIGNMENT", insertable = false, updatable = false)
	private Long lengthOfAssignment;
	
	@Column(name = "RESOURCE_LEVEL", insertable = false, updatable = false)
	private Integer resourceLevel;
	
	@Column(name = "MOBILIZATION_DATE", insertable = false, updatable = false)
	private Date mobilizationDate;

	@Column(name = "NAME_ON_ID", insertable = false, updatable = false)
	private String nameOnId;

	@Column(name = "NUMBER_OF_PERSONNEL", insertable = false, updatable = false)
	private Long numberOfPersonnel;

	@Column(name = "OTHER1", insertable = false, updatable = false)
	private String other1;

	@Column(name = "OTHER2", insertable = false, updatable = false)
	private String other2;

	@Column(name = "OTHER3", insertable = false, updatable = false)
	private String other3;

	@Column(name = "IS_OVERHEAD", insertable = false, updatable = false)
	private String overHead;
	
	@Column(name = "IS_LINE_OVERHEAD", insertable = false, updatable = false)
	private String lineOverHead;

	@Column(name = "PARENT_ID", insertable = false, updatable = false)
	private Long parentId;

	@Column(name = "PAYMENT_AGENCY", insertable = false, updatable = false)
	private String paymentAgency;

	@Column(name = "PHONE_NUMBER", insertable = false, updatable = false)
	private String phoneNumber;

	@Column(name = "IS_REASSIGNMENT", insertable = false, updatable = false)
	private String reassignment;

	@Column(name = "REMARKS_COST", insertable = false, updatable = false)
	private String remarksCost;

	@Column(name = "REMARKS_PLANS", insertable = false, updatable = false)
	private String remarksPlans;

	@Column(name = "REQUEST_CATEGORY", insertable = false, updatable = false)
	private String requestCategory;

	@Column(name = "REQUEST_NUMBER", insertable = false, updatable = false)
	private String requestNumber;

	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;

	@Column(name = "RETURN_METHOD_OF_TRAVEL", insertable = false, updatable = false)
	private String returnMethodOfTravel;

	@Column(name = "SECTION_CODE", insertable = false, updatable = false)
	private String sectionCode;
	
	@Column(name = "SECTION_DESCRIPTION", insertable = false, updatable = false)
	private String sectionDescription;

	@Column(name = "SPECIAL_INSTRUCTIONS", insertable = false, updatable = false)
	private String specialInstructions;

	@Column(name = "STATUS", insertable = false, updatable = false)
	private String status;

	@Column(name = "TENTATIVE_RELEASE_DATE", insertable = false, updatable = false)
	private Date tentativeReleaseDate;
	
	@Column(name = "TENTATIVE_RELEASE_REMARKS", insertable = false, updatable = false)
	private String tentativeReleaseRemarks;

	@Column(name = "TENTATIVE_RELEASE_TIME", insertable = false, updatable = false)
	private Date tentativeReleaseTime;
	
	@Column(name = "IS_TRAINEE", insertable = false, updatable = false)
	private String trainee;
	
	@Column(name = "TRAVEL_HOURS", insertable = false, updatable = false)
	private Integer travelHours;

	@Column(name = "TRAVEL_METHOD", insertable = false, updatable = false)
	private String travelMethod;
	
	@Column(name = "TRAVEL_MINUTES", insertable = false, updatable = false)
	private Integer travelMinutes;

	@Column(name = "UNIT_ID", insertable = false, updatable = false)
	private String unitId;
	
	public CRPersonPlanViewImpl() {	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getAccrual() {
		return accrual;
	}

	public void setAccrual(String accrual) {
		this.accrual = accrual;
	}

	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}

	public String getActualReleaseRemarks() {
		return actualReleaseRemarks;
	}

	public void setActualReleaseRemarks(String actualReleaseRemarks) {
		this.actualReleaseRemarks = actualReleaseRemarks;
	}

	public Date getActualReleaseTime() {
		return actualReleaseTime;
	}

	public void setActualReleaseTime(Date actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getAirTravelToDispatch() {
		return airTravelToDispatch;
	}

	public void setAirTravelToDispatch(String airTravelToDispatch) {
		this.airTravelToDispatch = airTravelToDispatch;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckoutFormPrinted() {
		return checkoutFormPrinted;
	}

	public void setCheckoutFormPrinted(String checkoutFormPrinted) {
		this.checkoutFormPrinted = checkoutFormPrinted;
	}

	public String getContracted() {
		return contracted;
	}

	public void setContracted(String contracted) {
		this.contracted = contracted;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getDemobilizationCity() {
		return demobilizationCity;
	}

	public void setDemobilizationCity(String demobilizationCity) {
		this.demobilizationCity = demobilizationCity;
	}

	public Date getDemobilizationDate() {
		return demobilizationDate;
	}

	public void setDemobilizationDate(Date demobilizationDate) {
		this.demobilizationDate = demobilizationDate;
	}

	public String getDemobilizationState() {
		return demobilizationState;
	}

	public void setDemobilizationState(String demobilizationState) {
		this.demobilizationState = demobilizationState;
	}

	public String getDispatchNotifiedOfActualRelease() {
		return dispatchNotifiedOfActualRelease;
	}

	public void setDispatchNotifiedOfActualRelease(
			String dispatchNotifiedOfActualRelease) {
		this.dispatchNotifiedOfActualRelease = dispatchNotifiedOfActualRelease;
	}

	public String getDispatchNotifiedOfTentativeRelease() {
		return dispatchNotifiedOfTentativeRelease;
	}

	public void setDispatchNotifiedOfTentativeRelease(
			String dispatchNotifiedOfTentativeRelease) {
		this.dispatchNotifiedOfTentativeRelease = dispatchNotifiedOfTentativeRelease;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public Date getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}

	public void setEstimatedArrivalTime(Date estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}

	public Date getEstimatedDateOfArrival() {
		return estimatedDateOfArrival;
	}

	public void setEstimatedDateOfArrival(Date estimatedDateOfArrival) {
		this.estimatedDateOfArrival = estimatedDateOfArrival;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Date getFirstDayOfWork() {
		return firstDayOfWork;
	}

	public void setFirstDayOfWork(Date firstDayOfWork) {
		this.firstDayOfWork = firstDayOfWork;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(Integer flightTime) {
		this.flightTime = flightTime;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getIncidentJetport() {
		return incidentJetport;
	}

	public void setIncidentJetport(String incidentJetport) {
		this.incidentJetport = incidentJetport;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getRostered() {
		return rostered;
	}

	public void setRostered(String rostered) {
		this.rostered = rostered;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeDescription() {
		return itemCodeDescription;
	}

	public void setItemCodeDescription(String itemCodeDescription) {
		this.itemCodeDescription = itemCodeDescription;
	}

	public String getItineraryReceived() {
		return itineraryReceived;
	}

	public void setItineraryReceived(String itineraryReceived) {
		this.itineraryReceived = itineraryReceived;
	}

	public String getJetPort() {
		return jetPort;
	}

	public void setJetPort(String jetPort) {
		this.jetPort = jetPort;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Long getLengthOfAssignment() {
		return lengthOfAssignment;
	}

	public void setLengthOfAssignment(Long lengthOfAssignment) {
		this.lengthOfAssignment = lengthOfAssignment;
	}

	public Integer getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(Integer resourceLevel) {
		this.resourceLevel = resourceLevel;
	}

	public Date getMobilizationDate() {
		return mobilizationDate;
	}

	public void setMobilizationDate(Date mobilizationDate) {
		this.mobilizationDate = mobilizationDate;
	}

	public String getNameOnId() {
		return nameOnId;
	}

	public void setNameOnId(String nameOnId) {
		this.nameOnId = nameOnId;
	}

	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	public String getOverHead() {
		return overHead;
	}

	public void setOverHead(String overHead) {
		this.overHead = overHead;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPaymentAgency() {
		return paymentAgency;
	}

	public void setPaymentAgency(String paymentAgency) {
		this.paymentAgency = paymentAgency;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReassignment() {
		return reassignment;
	}

	public void setReassignment(String reassignment) {
		this.reassignment = reassignment;
	}

	public String getRemarksCost() {
		return remarksCost;
	}

	public void setRemarksCost(String remarksCost) {
		this.remarksCost = remarksCost;
	}

	public String getRemarksPlans() {
		return remarksPlans;
	}

	public void setRemarksPlans(String remarksPlans) {
		this.remarksPlans = remarksPlans;
	}

	public String getRequestCategory() {
		return requestCategory;
	}

	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getReturnMethodOfTravel() {
		return returnMethodOfTravel;
	}

	public void setReturnMethodOfTravel(String returnMethodOfTravel) {
		this.returnMethodOfTravel = returnMethodOfTravel;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	public String getSectionDescription() {
		return sectionDescription;
	}

	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	public String getTentativeReleaseRemarks() {
		return tentativeReleaseRemarks;
	}

	public void setTentativeReleaseRemarks(String tentativeReleaseRemarks) {
		this.tentativeReleaseRemarks = tentativeReleaseRemarks;
	}

	public Date getTentativeReleaseTime() {
		return tentativeReleaseTime;
	}

	public void setTentativeReleaseTime(Date tentativeReleaseTime) {
		this.tentativeReleaseTime = tentativeReleaseTime;
	}

	public String getTrainee() {
		return trainee;
	}

	public void setTrainee(String trainee) {
		this.trainee = trainee;
	}

	public Integer getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(Integer travelHours) {
		this.travelHours = travelHours;
	}

	public String getTravelMethod() {
		return travelMethod;
	}

	public void setTravelMethod(String travelMethod) {
		this.travelMethod = travelMethod;
	}

	public Integer getTravelMinutes() {
		return travelMinutes;
	}

	public void setTravelMinutes(Integer travelMinutes) {
		this.travelMinutes = travelMinutes;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setLineOverHead(String lineOverHead) {
		this.lineOverHead = lineOverHead;
	}

	public String getLineOverHead() {
		return lineOverHead;
	}
}
