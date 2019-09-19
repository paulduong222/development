package gov.nwcg.isuite.core.controllers.restdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.nwcg.isuite.core.filter.IncidentResourceCheckboxFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

public class IncidentResourceFilterData {

	private IncidentResourceFilter incidentResourceFilter = new IncidentResourceFilterImpl();
	private List<String> sortFields = new ArrayList<String>();
	
	public IncidentResourceFilter getIncidentResourceFilter() {
		return incidentResourceFilter;
	}
	
	public List<String> getSortFields() {
		return sortFields;
	}
	
	public void setSortFields(List<String> sortFields) {
		this.sortFields = sortFields;
	}

	public void reset() {
		incidentResourceFilter.reset();
	}

	public Long getIncidentId() {
		return incidentResourceFilter.getIncidentId();
	}

	public void setIncidentId(Long id) {
		incidentResourceFilter.setIncidentId(id);
	}

	public Long getIncidentGroupId() {
		return incidentResourceFilter.getIncidentGroupId();
	}

	public void setIncidentGroupId(Long id) {
		incidentResourceFilter.setIncidentGroupId(id);
	}

	public Long getWorkAreaId() {
		return incidentResourceFilter.getWorkAreaId();
	}

	public Long getCurrentUserId() {
		return incidentResourceFilter.getCurrentUserId();
	}

	public void setWorkAreaId(Long workAreaId) {
		incidentResourceFilter.setWorkAreaId(workAreaId);
	}

	public void setCurrentUserId(Long userId) {
		incidentResourceFilter.setCurrentUserId(userId);
	}

	public Long getWorkAreaIncidentId() {
		return incidentResourceFilter.getWorkAreaIncidentId();
	}

	public void setWorkAreaIncidentId(Long incidentId) {
		incidentResourceFilter.setWorkAreaIncidentId(incidentId);
	}

	public Long getWorkAreaIncidentGroupId() {
		return incidentResourceFilter.getWorkAreaIncidentGroupId();
	}

	public void setWorkAreaIncidentGroupId(Long id) {
		incidentResourceFilter.setWorkAreaIncidentGroupId(id);
	}

	public boolean isAssignedToIncident() {
		return incidentResourceFilter.isAssignedToIncident();
	}

	public void setAssignedToIncident(boolean assignedToIncident) {
		incidentResourceFilter.setAssignedToIncident(assignedToIncident);
	}

	public boolean isPermanent() {
		return incidentResourceFilter.isPermanent();
	}

	public boolean getPermanent() {
		return incidentResourceFilter.getPermanent();
	}

	public void setPermanent(boolean permanent) {
		incidentResourceFilter.setPermanent(permanent);
	}

	public String getOther1() {
		return incidentResourceFilter.getOther1();
	}

	public void setOther1(String other1) {
		incidentResourceFilter.setOther1(other1);
	}

	public String getOther2() {
		return incidentResourceFilter.getOther2();
	}

	public void setOther2(String other2) {
		incidentResourceFilter.setOther2(other2);
	}

	public String getOther3() {
		return incidentResourceFilter.getOther3();
	}

	public void setOther3(String other3) {
		incidentResourceFilter.setOther3(other3);
	}

	public String getRequestCategory() {
		return incidentResourceFilter.getRequestCategory();
	}

	public void setRequestCategory(String requestCategory) {
		incidentResourceFilter.setRequestCategory(requestCategory);
	}

	public String getResourceName() {
		return incidentResourceFilter.getResourceName();
	}

	public void setResourceName(String resourceName) {
		incidentResourceFilter.setResourceName(resourceName);
	}

	public String getLastName() {
		return incidentResourceFilter.getLastName();
	}

	public void setLastName(String lastName) {
		incidentResourceFilter.setLastName(lastName);
	}

	public String getFirstName() {
		return incidentResourceFilter.getFirstName();
	}

	public void setFirstName(String firstName) {
		incidentResourceFilter.setFirstName(firstName);
	}

	public String getResourceStatus() {
		return incidentResourceFilter.getResourceStatus();
	}

	public void setResourceStatus(String resourceStatus) {
		incidentResourceFilter.setResourceStatus(resourceStatus);
	}

	public Long getNumberOfPersonnel() {
		return incidentResourceFilter.getNumberOfPersonnel();
	}

	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		incidentResourceFilter.setNumberOfPersonnel(numberOfPersonnel);
	}

	public String getResourceAgencyName() {
		return incidentResourceFilter.getResourceAgencyName();
	}

	public void setResourceAgencyName(String resourceAgencyName) {
		incidentResourceFilter.setResourceAgencyName(resourceAgencyName);
	}

	public String getResourceOrganizationName() {
		return incidentResourceFilter.getResourceOrganizationName();
	}

	public void setResourceOrganizationName(String resourceOrganizationName) {
		incidentResourceFilter.setResourceOrganizationName(resourceOrganizationName);
	}

	public Date getMobilizationStartDate() {
		return incidentResourceFilter.getMobilizationStartDate();
	}

	public void setMobilizationStartDate(Date mobilizationStartDate) {
		incidentResourceFilter.setMobilizationStartDate(mobilizationStartDate);
	}

	public String getIncidentName() {
		return incidentResourceFilter.getIncidentName();
	}

	public void setIncidentName(String incidentName) {
		incidentResourceFilter.setIncidentName(incidentName);
	}

	public String getIncidentNumber() {
		return incidentResourceFilter.getIncidentNumber();
	}

	public void setIncidentNumber(String incidentNumber) {
		incidentResourceFilter.setIncidentNumber(incidentNumber);
	}

	public String getNameAtIncident() {
		return incidentResourceFilter.getNameAtIncident();
	}

	public void setNameAtIncident(String nameAtIncident) {
		incidentResourceFilter.setNameAtIncident(nameAtIncident);
	}

	public String getCiArrivalJetPort() {
		return incidentResourceFilter.getCiArrivalJetPort();
	}

	public void setCiArrivalJetPort(String ciArrivalJetPort) {
		incidentResourceFilter.setCiArrivalJetPort(ciArrivalJetPort);
	}

	public Date getCiCheckInDate() {
		return incidentResourceFilter.getCiCheckInDate();
	}

	public void setCiCheckInDate(Date ciCheckInDate) {
		incidentResourceFilter.setCiCheckInDate(ciCheckInDate);
	}

	public Date getCiFirstWorkDate() {
		return incidentResourceFilter.getCiFirstWorkDate();
	}

	public void setCiFirstWorkDate(Date ciFirstWorkDate) {
		incidentResourceFilter.setCiFirstWorkDate(ciFirstWorkDate);
	}

	public Long getCiLengthAtAssignment() {
		return incidentResourceFilter.getCiLengthAtAssignment();
	}

	public void setCiLengthAtAssignment(Long ciLengthAtAssignment) {
		incidentResourceFilter.setCiLengthAtAssignment(ciLengthAtAssignment);
	}

	public String getCiPrePlanningRemarks() {
		return incidentResourceFilter.getCiPrePlanningRemarks();
	}

	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks) {
		incidentResourceFilter.setCiPrePlanningRemarks(ciPrePlanningRemarks);
	}

	public String getDmTentativeDemobCity() {
		return incidentResourceFilter.getDmTentativeDemobCity();
	}

	public void setDmTentativeDemobCity(String dmTentativeDemobCity) {
		incidentResourceFilter.setDmTentativeDemobCity(dmTentativeDemobCity);
	}

	public String getDmTentativeDemobState() {
		return incidentResourceFilter.getDmTentativeDemobState();
	}

	public void setDmTentativeDemobState(String dmTentativeDemobState) {
		incidentResourceFilter.setDmTentativeDemobState(dmTentativeDemobState);
	}

	public Date getDmTentativeReleaseDate() {
		return incidentResourceFilter.getDmTentativeReleaseDate();
	}

	public void setDmTentativeReleaseDate(Date dmTentativeReleaseDate) {
		incidentResourceFilter.setDmTentativeReleaseDate(dmTentativeReleaseDate);
	}

	public TravelMethodTypeEnum getDmTravelMethod() {
		return incidentResourceFilter.getDmTravelMethod();
	}

	public void setDmTravelMethod(TravelMethodTypeEnum dmTravelMethod) {
		incidentResourceFilter.setDmTravelMethod(dmTravelMethod);
	}

	public String getKindCode() {
		return incidentResourceFilter.getKindCode();
	}

	public void setKindCode(String kindCode) {
		incidentResourceFilter.setKindCode(kindCode);
	}

	public String getKindDescription() {
		return incidentResourceFilter.getKindDescription();
	}

	public void setKindDescription(String kindDescription) {
		incidentResourceFilter.setKindDescription(kindDescription);
	}

	public String getRequestNumber() {
		return incidentResourceFilter.getRequestNumber();
	}

	public void setRequestNumber(String requestNumber) {
		incidentResourceFilter.setRequestNumber(requestNumber);
	}

	public Boolean getTrainee() {
		return incidentResourceFilter.getTrainee();
	}

	public void setTrainee(Boolean trainee) {
		incidentResourceFilter.setTrainee(trainee);
	}

	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return incidentResourceFilter.getAssignmentStatus();
	}

	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		incidentResourceFilter.setAssignmentStatus(assignmentStatus);
	}

	public String getLeaderName() {
		return incidentResourceFilter.getLeaderName();
	}

	public void setLeaderName(String leaderName) {
		incidentResourceFilter.setLeaderName(leaderName);
	}

	public void setAssignmentStatusCodes(Collection<String> assignmentStatusCodes) {
		incidentResourceFilter.setAssignmentStatusCodes(assignmentStatusCodes);
	}

	public Collection<String> getAssignmentStatusCodes() {
		return incidentResourceFilter.getAssignmentStatusCodes();
	}

	public void setRequestCategoryCodes(Collection<String> requestCategoryCodes) {
		incidentResourceFilter.setRequestCategoryCodes(requestCategoryCodes);
	}

	public Collection<String> getRequestCategoryCodes() {
		return incidentResourceFilter.getRequestCategoryCodes();
	}

	public Boolean getDeletable() {
		return incidentResourceFilter.getDeletable();
	}

	public void setDeletable(Boolean deletable) {
		incidentResourceFilter.setDeletable(deletable);
	}

	public Long getResourceId() {
		return incidentResourceFilter.getResourceId();
	}

	public void setResourceId(Long resourceId) {
		incidentResourceFilter.setResourceId(resourceId);
	}

	public String getResourceUnitCode() {
		return incidentResourceFilter.getResourceUnitCode();
	}

	public void setResourceUnitCode(String resourceUnitCode) {
		incidentResourceFilter.setResourceUnitCode(resourceUnitCode);
	}

	public String getItemCode() {
		return incidentResourceFilter.getItemCode();
	}

	public void setItemCode(String itemCode) {
		incidentResourceFilter.setItemCode(itemCode);
	}

	public String getItemName() {
		return incidentResourceFilter.getItemName();
	}

	public void setItemName(String itemName) {
		incidentResourceFilter.setItemName(itemName);
	}

	public String getCellPhoneNumber() {
		return incidentResourceFilter.getCellPhoneNumber();
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		incidentResourceFilter.setCellPhoneNumber(cellPhoneNumber);
	}

	public String getVehicleId() {
		return incidentResourceFilter.getVehicleId();
	}

	public void setVehicleId(String vehicleId) {
		incidentResourceFilter.setVehicleId(vehicleId);
	}

	public String getAccountingCode() {
		return incidentResourceFilter.getAccountingCode();
	}

	public void setAccountingCode(String accountingCode) {
		incidentResourceFilter.setAccountingCode(accountingCode);
	}

	public String getSectionDescription() {
		return incidentResourceFilter.getSectionDescription();
	}

	public void setSectionDescription(String sectionDescription) {
		incidentResourceFilter.setSectionDescription(sectionDescription);
	}

	public String getSubSectionDescription() {
		return incidentResourceFilter.getSubSectionDescription();
	}

	public void setSubSectionDescription(String subSectionDescription) {
		incidentResourceFilter.setSubSectionDescription(subSectionDescription);
	}

	public String getAgency() {
		return incidentResourceFilter.getAgency();
	}

	public void setAgency(String agency) {
		incidentResourceFilter.setAgency(agency);
	}

	public String getUnitId() {
		return incidentResourceFilter.getUnitId();
	}

	public void setUnitId(String unitId) {
		incidentResourceFilter.setUnitId(unitId);
	}

	public Boolean getLeader() {
		return incidentResourceFilter.getLeader();
	}

	public void setLeader(Boolean leader) {
		incidentResourceFilter.setLeader(leader);
	}

	public Date getActualReleaseDate() {
		return incidentResourceFilter.getActualReleaseDate();
	}

	public void setActualReleaseDate(Date actualReleaseDate) {
		incidentResourceFilter.setActualReleaseDate(actualReleaseDate);
	}

	public Boolean getDmReassignable() {
		return incidentResourceFilter.getDmReassignable();
	}

	public void setDmReassignable(Boolean dmReassignable) {
		incidentResourceFilter.setDmReassignable(dmReassignable);
	}

	public Boolean getDmCheckoutFormPrinted() {
		return incidentResourceFilter.getDmCheckoutFormPrinted();
	}

	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		incidentResourceFilter.setDmCheckoutFormPrinted(dmCheckoutFormPrinted);
	}

	public Boolean getDmPlanningDispatchNotified() {
		return incidentResourceFilter.getDmPlanningDispatchNotified();
	}

	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		incidentResourceFilter.setDmPlanningDispatchNotified(dmPlanningDispatchNotified);
	}

	public Boolean getDmReleaseDispatchNotified() {
		return incidentResourceFilter.getDmReleaseDispatchNotified();
	}

	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		incidentResourceFilter.setDmReleaseDispatchNotified(dmReleaseDispatchNotified);
	}

	public Boolean getDmRestOvernight() {
		return incidentResourceFilter.getDmRestOvernight();
	}

	public void setDmRestOvernight(Boolean dmRestOvernight) {
		incidentResourceFilter.setDmRestOvernight(dmRestOvernight);
	}

	public String getDmReleaseRemarks() {
		return incidentResourceFilter.getDmReleaseRemarks();
	}

	public void setDmReleaseRemarks(String dmReleaseRemarks) {
		incidentResourceFilter.setDmReleaseRemarks(dmReleaseRemarks);
	}

	public Date getDmEstimatedArrivalDate() {
		return incidentResourceFilter.getDmEstimatedArrivalDate();
	}

	public void setDmEstimatedArrivalDate(Date dmEstimatedArrivalDate) {
		incidentResourceFilter.setDmEstimatedArrivalDate(dmEstimatedArrivalDate);
	}

	public Boolean getDmAirDispatchNotified() {
		return incidentResourceFilter.getDmAirDispatchNotified();
	}

	public void setDmAirDispatchNotified(Boolean dmAirDispatchNotified) {
		incidentResourceFilter.setDmAirDispatchNotified(dmAirDispatchNotified);
	}

	public String getAirRemarks() {
		return incidentResourceFilter.getAirRemarks();
	}

	public void setAirRemarks(String airRemarks) {
		incidentResourceFilter.setAirRemarks(airRemarks);
	}

	public Integer getHoursToAirport() {
		return incidentResourceFilter.getHoursToAirport();
	}

	public void setHoursToAirport(Integer hoursToAirport) {
		incidentResourceFilter.setHoursToAirport(hoursToAirport);
	}

	public Integer getMinutesToAirport() {
		return incidentResourceFilter.getMinutesToAirport();
	}

	public void setMinutesToAirport(Integer minutesToAirport) {
		incidentResourceFilter.setMinutesToAirport(minutesToAirport);
	}

	public Boolean getItineraryReceived() {
		return incidentResourceFilter.getItineraryReceived();
	}

	public void setItineraryReceived(Boolean itineraryReceived) {
		incidentResourceFilter.setItineraryReceived(itineraryReceived);
	}

	public String getNameOnPictureId() {
		return incidentResourceFilter.getNameOnPictureId();
	}

	public void setNameOnPictureId(String nameOnPictureId) {
		incidentResourceFilter.setNameOnPictureId(nameOnPictureId);
	}

	public String getDepartFromJetport() {
		return incidentResourceFilter.getDepartFromJetport();
	}

	public void setDepartFromJetport(String departFromJetport) {
		incidentResourceFilter.setDepartFromJetport(departFromJetport);
	}

	public String getOvernightRemarks() {
		return incidentResourceFilter.getOvernightRemarks();
	}

	public void setOvernightRemarks(String overnightRemarks) {
		incidentResourceFilter.setOvernightRemarks(overnightRemarks);
	}

	public void setRosterParentId(Long id) {
		incidentResourceFilter.setRosterParentId(id);
	}

	public Long getRosterParentId() {
		return incidentResourceFilter.getRosterParentId();
	}

	public String getCrypticFilterCodeForCiCheckInDate() {
		return incidentResourceFilter.getCrypticFilterCodeForCiCheckInDate();
	}

	public void setCrypticFilterCodeForCiCheckInDate(String crypticFilterCodeForCiCheckInDate) {
		incidentResourceFilter.setCrypticFilterCodeForCiCheckInDate(crypticFilterCodeForCiCheckInDate);
	}

	public String getCrypticFilterCodeForMobilizationStartDate() {
		return incidentResourceFilter.getCrypticFilterCodeForMobilizationStartDate();
	}

	public void setCrypticFilterCodeForMobilizationStartDate(String crypticFilterCodeForMobilizationStartDate) {
		incidentResourceFilter.setCrypticFilterCodeForMobilizationStartDate(crypticFilterCodeForMobilizationStartDate);
	}

	public String getCrypticFilterCodeForCiFirstWorkDate() {
		return incidentResourceFilter.getCrypticFilterCodeForCiFirstWorkDate();
	}

	public void setCrypticFilterCodeForCiFirstWorkDate(String crypticFilterCodeForCiFirstWorkDate) {
		incidentResourceFilter.setCrypticFilterCodeForCiFirstWorkDate(crypticFilterCodeForCiFirstWorkDate);
	}

	public Date getDmTentativeDemobDate() {
		return incidentResourceFilter.getDmTentativeDemobDate();
	}

	public void setDmTentativeDemobDate(Date dmTentativeDemobDate) {
		incidentResourceFilter.setDmTentativeDemobDate(dmTentativeDemobDate);
	}

	public String getCrypticFilterCodeForTentativeDemobDate() {
		return incidentResourceFilter.getCrypticFilterCodeForTentativeDemobDate();
	}

	public void setCrypticFilterCodeForTentativeDemobDate(String crypticFilterCodeForTentativeDemobDate) {
		incidentResourceFilter.setCrypticFilterCodeForTentativeDemobDate(crypticFilterCodeForTentativeDemobDate);
	}

	public String getLeaderType() {
		return incidentResourceFilter.getLeaderType();
	}

	public void setLeaderType(String leaderType) {
		incidentResourceFilter.setLeaderType(leaderType);
	}

	public String getResourceClassification() {
		return incidentResourceFilter.getResourceClassification();
	}

	public void setResourceClassification(String resourceClassification) {
		incidentResourceFilter.setResourceClassification(resourceClassification);
	}

	public String getTraineeString() {
		return incidentResourceFilter.getTraineeString();
	}

	public void setTraineeString(String traineeString) {
		incidentResourceFilter.setTraineeString(traineeString);
	}

	public String getActualReleaseTime() {
		return incidentResourceFilter.getActualReleaseTime();
	}

	public void setActualReleaseTime(String actualReleaseTime) {
		incidentResourceFilter.setActualReleaseTime(actualReleaseTime);
	}

	public String getCheckInTime() {
		return incidentResourceFilter.getCheckInTime();
	}

	public void setCheckInTime(String checkInTime) {
		incidentResourceFilter.setCheckInTime(checkInTime);
	}

	public String getArrivalTime() {
		return incidentResourceFilter.getArrivalTime();
	}

	public void setArrivalTime(String arrivalTime) {
		incidentResourceFilter.setArrivalTime(arrivalTime);
	}

	public String getTentativeReleaseTime() {
		return incidentResourceFilter.getTentativeReleaseTime();
	}

	public void setTentativeReleaseTime(String tentativeReleaseTime) {
		incidentResourceFilter.setTentativeReleaseTime(tentativeReleaseTime);
	}

	public String getHoursToAirportString() {
		return incidentResourceFilter.getHoursToAirportString();
	}

	public void setHoursToAirportString(String hoursToAirportString) {
		incidentResourceFilter.setHoursToAirportString(hoursToAirportString);
	}

	public String getMinutesToAirportString() {
		return incidentResourceFilter.getMinutesToAirportString();
	}

	public void setMinutesToAirportString(String minutesToAirportString) {
		incidentResourceFilter.setMinutesToAirportString(minutesToAirportString);
	}

	public String getNumberOfPersonnelString() {
		return incidentResourceFilter.getNumberOfPersonnelString();
	}

	public void setNumberOfPersonnelString(String numberOfPersonnelString) {
		incidentResourceFilter.setNumberOfPersonnelString(numberOfPersonnelString);
	}

	public String getCiLengthAtAssignmentString() {
		return incidentResourceFilter.getCiLengthAtAssignmentString();
	}

	public void setCiLengthAtAssignmentString(String ciLengthAtAssignmentString) {
		incidentResourceFilter.setCiLengthAtAssignmentString(ciLengthAtAssignmentString);
	}

	public String getContractorCooperatorName() {
		return incidentResourceFilter.getContractorCooperatorName();
	}

	public void setContractorCooperatorName(String contractorCooperatorName) {
		incidentResourceFilter.setContractorCooperatorName(contractorCooperatorName);
	}

	public String getReturnTravelMethod() {
		return incidentResourceFilter.getReturnTravelMethod();
	}

	public void setReturnTravelMethod(String returnTravelMethod) {
		incidentResourceFilter.setReturnTravelMethod(returnTravelMethod);
	}

	public String getSpecialInstructions() {
		return incidentResourceFilter.getSpecialInstructions();
	}

	public void setSpecialInstructions(String specialInstructions) {
		incidentResourceFilter.setSpecialInstructions(specialInstructions);
	}

	public String getSupplies() {
		return incidentResourceFilter.getSupplies();
	}

	public void setSupplies(String supplies) {
		incidentResourceFilter.setSupplies(supplies);
	}

	public String getDmAirDispatchNotifiedString() {
		return incidentResourceFilter.getDmAirDispatchNotifiedString();
	}

	public void setDmAirDispatchNotifiedString(String dmAirDispatchNotifiedString) {
		incidentResourceFilter.setDmAirDispatchNotifiedString(dmAirDispatchNotifiedString);
	}

	public String getDmCheckoutFormPrintedString() {
		return incidentResourceFilter.getDmCheckoutFormPrintedString();
	}

	public void setDmCheckoutFormPrintedString(String dmCheckoutFormPrintedString) {
		incidentResourceFilter.setDmCheckoutFormPrintedString(dmCheckoutFormPrintedString);
	}

	public String getDmReleaseDispatchNotifiedString() {
		return incidentResourceFilter.getDmReleaseDispatchNotifiedString();
	}

	public void setDmReleaseDispatchNotifiedString(String dmReleaseDispatchNotifiedString) {
		incidentResourceFilter.setDmReleaseDispatchNotifiedString(dmReleaseDispatchNotifiedString);
	}

	public String getContractedString() {
		return incidentResourceFilter.getContractedString();
	}

	public void setContractedString(String contractedString) {
		incidentResourceFilter.setContractedString(contractedString);
	}

	public String getItineraryReceivedString() {
		return incidentResourceFilter.getItineraryReceivedString();
	}

	public void setItineraryReceivedString(String itineraryReceivedString) {
		incidentResourceFilter.setItineraryReceivedString(itineraryReceivedString);
	}

	public String getDmPlanningDispatchNotifiedString() {
		return incidentResourceFilter.getDmPlanningDispatchNotifiedString();
	}

	public void setDmPlanningDispatchNotifiedString(String dmPlanningDispatchNotifiedString) {
		incidentResourceFilter.setDmPlanningDispatchNotifiedString(dmPlanningDispatchNotifiedString);
	}

	public String getDmReassignableString() {
		return incidentResourceFilter.getDmReassignableString();
	}

	public void setDmReassignableString(String dmReassignableString) {
		incidentResourceFilter.setDmReassignableString(dmReassignableString);
	}

	public String getDmRestOvernightString() {
		return incidentResourceFilter.getDmRestOvernightString();
	}

	public void setDmRestOvernightString(String dmRestOvernightString) {
		incidentResourceFilter.setDmRestOvernightString(dmRestOvernightString);
	}

	public String getCrypticDateFilterCodeForActualReleaseDate() {
		return incidentResourceFilter.getCrypticDateFilterCodeForActualReleaseDate();
	}

	public void setCrypticDateFilterCodeForActualReleaseDate(String crypticDateFilterCodeForActualReleaseDate) {
		incidentResourceFilter.setCrypticDateFilterCodeForActualReleaseDate(crypticDateFilterCodeForActualReleaseDate);
	}

	public String getCrypticDateFilterCodeForTentativeReleaseDate() {
		return incidentResourceFilter.getCrypticDateFilterCodeForTentativeReleaseDate();
	}

	public void setCrypticDateFilterCodeForTentativeReleaseDate(String crypticDateFilterCodeForTentativeReleaseDate) {
		incidentResourceFilter
				.setCrypticDateFilterCodeForTentativeReleaseDate(crypticDateFilterCodeForTentativeReleaseDate);
	}

	public String getCrypticDateFilterCodeForCheckInDate() {
		return incidentResourceFilter.getCrypticDateFilterCodeForCheckInDate();
	}

	public void setCrypticDateFilterCodeForCheckInDate(String crypticDateFilterCodeForCheckInDate) {
		incidentResourceFilter.setCrypticDateFilterCodeForCheckInDate(crypticDateFilterCodeForCheckInDate);
	}

	public String getCrypticFilterCodeForEstimatedArrivalDate() {
		return incidentResourceFilter.getCrypticFilterCodeForEstimatedArrivalDate();
	}

	public void setCrypticFilterCodeForEstimatedArrivalDate(String crypticFilterCodeForEstimatedArrivalDate) {
		incidentResourceFilter.setCrypticFilterCodeForEstimatedArrivalDate(crypticFilterCodeForEstimatedArrivalDate);
	}

	public void setCheckboxFilter(IncidentResourceCheckboxFilter checkboxFilter) {
		incidentResourceFilter.setCheckboxFilter(checkboxFilter);
	}

	public IncidentResourceCheckboxFilter getCheckboxFilter() {
		return incidentResourceFilter.getCheckboxFilter();
	}

	public String getVinName() {
		return incidentResourceFilter.getVinName();
	}

	public void setVinName(String vinName) {
		incidentResourceFilter.setVinName(vinName);
	}

	public String getEmploymentType() {
		return incidentResourceFilter.getEmploymentType();
	}

	public void setEmploymentType(String employmentType) {
		incidentResourceFilter.setEmploymentType(employmentType);
	}

	public Long getIncidentResourceId() {
		return incidentResourceFilter.getIncidentResourceId();
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		incidentResourceFilter.setIncidentResourceId(incidentResourceId);
	}

	public String getContractorAgreementNumber() {
		return incidentResourceFilter.getContractorAgreementNumber();
	}

	public void setContractorAgreementNumber(String contractorAgreementNumber) {
		incidentResourceFilter.setContractorAgreementNumber(contractorAgreementNumber);
	}

	public Long getAssignmentId() {
		return incidentResourceFilter.getAssignmentId();
	}

	public void setAssignmentId(Long assignmentId) {
		incidentResourceFilter.setAssignmentId(assignmentId);
	}

	public Boolean getUnRosteredOnly() {
		return incidentResourceFilter.getUnRosteredOnly();
	}

	public void setUnRosteredOnly(Boolean unRosteredOnly) {
		incidentResourceFilter.setUnRosteredOnly(unRosteredOnly);
	}

	public Long getExcludeResourceId() {
		return incidentResourceFilter.getExcludeResourceId();
	}

	public void setExcludeResourceId(Long excludeResourceId) {
		incidentResourceFilter.setExcludeResourceId(excludeResourceId);
	}

	public Boolean getHierarchal() {
		return incidentResourceFilter.getHierarchal();
	}

	public void setHierarchal(Boolean hierarchal) {
		incidentResourceFilter.setHierarchal(hierarchal);
	}
}
