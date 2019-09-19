package gov.nwcg.isuite.core.filter;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;


/**
 * @author bsteiner
 *
 */
public interface IncidentResourceFilter extends Filter {
	public Long getIncidentId();
	public void setIncidentId(Long id);
	public Long getIncidentGroupId();
	public void setIncidentGroupId(Long id);

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() ;

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) ;

	/**
	 * Returns the workAreaIncidentId.
	 *
	 * @return 
	 *		the workAreaIncidentId to return
	 */
	public Long getWorkAreaIncidentId();

	/**
	 * Sets the workAreaIncidentId.
	 *
	 * @param workAreaIncidentId 
	 *			the workAreaIncidentId to set
	 */
	public void setWorkAreaIncidentId(Long incidentId);

	public Long getWorkAreaIncidentGroupId();
	
	public void setWorkAreaIncidentGroupId(Long id);
	
	/**
	 * Returns the assignedToIncident.
	 *
	 * @return 
	 *		the assignedToIncident to return
	 */
	public boolean isAssignedToIncident() ;

	/**
	 * Sets the assignedToIncident.
	 *
	 * @param assignedToIncident 
	 *			the assignedToIncident to set
	 */
	public void setAssignedToIncident(boolean assignedToIncident);

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean isPermanent() ;

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean getPermanent() ;

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(boolean permanent) ;


	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
//	public boolean isOverhead() ;

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
//	public boolean getOverhead() ;

	/**
	 * Sets the overhead.
	 *
	 * @param overhead 
	 *			the overhead to set
	 */
//	public void setOverhead(boolean overhead) ;


	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
//	public boolean isAircraft() ;
	
	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
//	public boolean getAircraft() ;

	/**
	 * Sets the aircraft.
	 *
	 * @param aircraft 
	 *			the aircraft to set
	 */
//	public void setAircraft(boolean aircraft) ;


	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
//	public boolean isPersonnel() ;

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
//	public boolean getPersonnel() ;

	/**
	 * Sets the personnel.
	 *
	 * @param personnel 
	 *			the personnel to set
	 */
//	public void setPersonnel(boolean personnel) ;


	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
//	public boolean isCrews() ;

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
//	public boolean getCrews() ;

	/**
	 * Sets the crews.
	 *
	 * @param crews 
	 *			the crews to set
	 */
//	public void setCrews(boolean crews) ;


	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
//	public boolean isEquipment() ;

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
//	public boolean getEquipment() ;

	/**
	 * Sets the equipment.
	 *
	 * @param equipment 
	 *			the equipment to set
	 */
//	public void setEquipment(boolean equipment) ;

	
	/**
	 * Returns the other1.
	 *
	 * @return 
	 *		the other1 to return
	 */
	public String getOther1();

	/**
	 * Sets the other1.
	 *
	 * @param other1 
	 *			the other1 to set
	 */
	public void setOther1(String other1);

	/**
	 * Returns the other2.
	 *
	 * @return 
	 *		the other2 to return
	 */
	public String getOther2();

	/**
	 * Sets the other2.
	 *
	 * @param other2 
	 *			the other2 to set
	 */
	public void setOther2(String other2);

	/**
	 * Returns the other3.
	 *
	 * @return 
	 *		the other3 to return
	 */
	public String getOther3();

	/**
	 * Sets the other3.
	 *
	 * @param other3 
	 *			the other3 to set
	 */
	public void setOther3(String other3);

	/**
	 * Returns the requestCategory.
	 *
	 * @return 
	 *		the requestCategory to return
	 */
	public String getRequestCategory();

	/**
	 * Sets the requestCategory.
	 *
	 * @param requestCategory 
	 *			the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory);

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName();

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName);

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName();

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName);

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName();

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName);

	/**
	 * Returns the resourceStatus.
	 *
	 * @return 
	 *		the resourceStatus to return
	 */
	public String getResourceStatus();

	/**
	 * Sets the resourceStatus.
	 *
	 * @param resourceStatus 
	 *			the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus);

	/**
	 * Returns the numberOfPersonnel.
	 *
	 * @return 
	 *		the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel();

	/**
	 * Sets the numberOfPersonnel.
	 *
	 * @param numberOfPersonnel 
	 *			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel);

	/**
	 * Returns the resourceAgencyName.
	 *
	 * @return 
	 *		the resourceAgencyName to return
	 */
	public String getResourceAgencyName();

	/**
	 * Sets the resourceAgencyName.
	 *
	 * @param resourceAgencyName 
	 *			the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName);

	/**
	 * Returns the resourceOrganizationName.
	 *
	 * @return 
	 *		the resourceOrganizationName to return
	 */
	public String getResourceOrganizationName();

	/**
	 * Sets the resourceOrganizationName.
	 *
	 * @param resourceOrganizationName 
	 *			the resourceOrganizationName to set
	 */
	public void setResourceOrganizationName(String resourceOrganizationName);

	/**
	 * Returns the mobilizationStartDate.
	 *
	 * @return 
	 *		the mobilizationStartDate to return
	 */
	public Date getMobilizationStartDate();

	/**
	 * Sets the mobilizationStartDate.
	 *
	 * @param mobilizationStartDate 
	 *			the mobilizationStartDate to set
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate);

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName();

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * Returns the incidentNumber.
	 *
	 * @return 
	 *		the incidentNumber to return
	 */
	public String getIncidentNumber();

	/**
	 * Sets the incidentNumber.
	 *
	 * @param incidentNumber 
	 *			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber);

	/**
	 * Returns the nameAtIncident.
	 *
	 * @return 
	 *		the nameAtIncident to return
	 */
	public String getNameAtIncident();

	/**
	 * Sets the nameAtIncident.
	 *
	 * @param nameAtIncident 
	 *			the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident);

	/**
	 * Returns the ciArrivalJetPort.
	 *
	 * @return 
	 *		the ciArrivalJetPort to return
	 */
	public String getCiArrivalJetPort();

	/**
	 * Sets the ciArrivalJetPort.
	 *
	 * @param ciArrivalJetPort 
	 *			the ciArrivalJetPort to set
	 */
	public void setCiArrivalJetPort(String ciArrivalJetPort);
	
	/**
	 * Returns the ciCheckInDate.
	 *
	 * @return 
	 *		the ciCheckInDate to return
	 */
	public Date getCiCheckInDate();

	/**
	 * Sets the ciCheckInDate.
	 *
	 * @param ciCheckInDate 
	 *			the ciCheckInDate to set
	 */
	public void setCiCheckInDate(Date ciCheckInDate);

	/**
	 * Returns the ciFirstWorkDate.
	 *
	 * @return 
	 *		the ciFirstWorkDate to return
	 */
	public Date getCiFirstWorkDate();

	/**
	 * Sets the ciFirstWorkDate.
	 *
	 * @param ciFirstWorkDate 
	 *			the ciFirstWorkDate to set
	 */
	public void setCiFirstWorkDate(Date ciFirstWorkDate);

	/**
	 * Returns the ciLengthAtAssignment.
	 *
	 * @return 
	 *		the ciLengthAtAssignment to return
	 */
	public Long getCiLengthAtAssignment();

	/**
	 * Sets the ciLengthAtAssignment.
	 *
	 * @param ciLengthAtAssignment 
	 *			the ciLengthAtAssignment to set
	 */
	public void setCiLengthAtAssignment(Long ciLengthAtAssignment);


	/**
	 * Returns the ciPrePlanningRemarks.
	 *
	 * @return 
	 *		the ciPrePlanningRemarks to return
	 */
	public String getCiPrePlanningRemarks();

	/**
	 * Sets the ciPrePlanningRemarks.
	 *
	 * @param ciPrePlanningRemarks 
	 *			the ciPrePlanningRemarks to set
	 */
	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks);

	/**
	 * Returns the dmTentativeDemobCity.
	 *
	 * @return 
	 *		the dmTentativeDemobCity to return
	 */
	public String getDmTentativeDemobCity();

	/**
	 * Sets the dmTentativeDemobCity.
	 *
	 * @param dmTentativeDemobCity 
	 *			the dmTentativeDemobCity to set
	 */
	public void setDmTentativeDemobCity(String dmTentativeDemobCity);

	/**
	 * Returns the dmTentativeDemobState.
	 *
	 * @return 
	 *		the dmTentativeDemobState to return
	 */
	public String getDmTentativeDemobState();

	/**
	 * Sets the dmTentativeDemobState.
	 *
	 * @param dmTentativeDemobState 
	 *			the dmTentativeDemobState to set
	 */
	public void setDmTentativeDemobState(String dmTentativeDemobState);

	/**
	 * Returns the dmTentativeReleaseDate.
	 *
	 * @return 
	 *		the dmTentativeReleaseDate to return
	 */
	public Date getDmTentativeReleaseDate();

	/**
	 * Sets the dmTentativeReleaseDate.
	 *
	 * @param dmTentativeReleaseDate 
	 *			the dmTentativeReleaseDate to set
	 */
	public void setDmTentativeReleaseDate(Date dmTentativeReleaseDate);

	/**
	 * Returns the dmTravelMethod.
	 *
	 * @return 
	 *		the dmTravelMethod to return
	 */
	public TravelMethodTypeEnum getDmTravelMethod();

	/**
	 * Sets the dmTravelMethod.
	 *
	 * @param dmTravelMethod 
	 *			the dmTravelMethod to set
	 */
	public void setDmTravelMethod(TravelMethodTypeEnum dmTravelMethod);

	/**
	 * Returns the kindCode.
	 *
	 * @return 
	 *		the kindCode to return
	 */
	public String getKindCode();

	/**
	 * Sets the kindCode.
	 *
	 * @param kindCode 
	 *			the kindCode to set
	 */
	public void setKindCode(String kindCode);

	/**
	 * Returns the kindDescription.
	 *
	 * @return 
	 *		the kindDescription to return
	 */
	public String getKindDescription();

	/**
	 * Sets the kindDescription.
	 *
	 * @param kindDescription 
	 *			the kindDescription to set
	 */
	public void setKindDescription(String kindDescription); 

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber();

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber);
		
	/**
	 * Returns the trainee.
	 *
	 * @return 
	 *		the trainee to return
	 */
	public Boolean getTrainee();

	/**
	 * Sets the trainee.
	 *
	 * @param trainee 
	 *			the trainee to set
	 */
	public void setTrainee(Boolean trainee);

	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus();

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus);

	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName();

	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName);

	/**
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
//	public boolean isExcludeFilled();

	/**
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
//	public boolean getExcludeFilled();

	/**
	 * Sets the excludeFilled.
	 *
	 * @param excludeFilled 
	 *			the excludeFilled to set
	 */
//	public void setExcludeFilled(boolean excludeFilled);

	/**
	 * Returns the excludeDemob.
	 *
	 * @return 
	 *		the excludeDemob to return
	 */
//	public boolean isExcludeDemob() ;

	/**
	 * Returns the excludeDemob.
	 *
	 * @return 
	 *		the excludeDemob to return
	 */
//	public boolean getExcludeDemob();

	/**
	 * Sets the excludeDemob.
	 *
	 * @param excludeDemob 
	 *			the excludeDemob to set
	 */
//	public void setExcludeDemob(boolean excludeDemob);
	
	/**
	 * Sets the assignmentStatusCodes
	 * 
	 * @param assignmentStatusCodes
	 */
	public void setAssignmentStatusCodes(Collection<String> assignmentStatusCodes);

	/**
	 * Returns the assignmentStatusCodes
	 * 
	 * @return the assignmentStatusCodes
	 */
	public Collection<String> getAssignmentStatusCodes();

	/**
	 * Sets the requestCategoryIds
	 * 
	 * @param requestCategoryIds
	 */
	public void setRequestCategoryCodes(Collection<String> requestCategoryCodes);

	/**
	 * Returns the requestCategoryIds
	 * 
	 * @return the requestCategoryIds
	 */
	public Collection<String> getRequestCategoryCodes();

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
//	public Boolean isEnabled() ;

	/**
	 * Sets the enabledObject
	 * 
	 * @param enabled
	 * 			the enabledObject to set
	 */
//	public void setEnabledObject(Object object);
	
	/**
	 * Returns the enabledObject
	 * 
	 * @return 
	 * 		the enabledObject to return
	 */
//	public Object getEnabledObject();
	
	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
//	public Boolean getEnabled() ;
	
	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
//	public void setEnabled(Boolean enabled);

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() ;

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) ;
	
	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId();
	
	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId);

	/**
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode();

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode);


	public String getItemCode();

	public void setItemCode(String itemCode);

	public String getItemName();

	public void setItemName(String itemName);

	public String getCellPhoneNumber();

	public void setCellPhoneNumber(String cellPhoneNumber);

	public String getVehicleId();

	public void setVehicleId(String vehicleId);

	public String getAccountingCode();

	public void setAccountingCode(String accountingCode);

	public String getSectionDescription();

	public void setSectionDescription(String sectionDescription);

	public String getSubSectionDescription();

	public void setSubSectionDescription(String subSectionDescription);

	public String getAgency();

	public void setAgency(String agency);

	public String getUnitId();

	public void setUnitId(String unitId);

	public Boolean getLeader();

	public void setLeader(Boolean leader);

	public Date getActualReleaseDate();

	public void setActualReleaseDate(Date actualReleaseDate) ;


	public Boolean getDmReassignable();

	public void setDmReassignable(Boolean dmReassignable) ;

	public Boolean getDmCheckoutFormPrinted();


	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) ;

	public Boolean getDmPlanningDispatchNotified() ;

	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) ;

	public Boolean getDmReleaseDispatchNotified() ;

	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) ;

	public Boolean getDmRestOvernight() ;

	public void setDmRestOvernight(Boolean dmRestOvernight) ;

	public String getDmReleaseRemarks() ;

	public void setDmReleaseRemarks(String dmReleaseRemarks) ;
	
	public Date getDmEstimatedArrivalDate();

	public void setDmEstimatedArrivalDate(Date dmEstimatedArrivalDate);

	public Boolean getDmAirDispatchNotified();

	public void setDmAirDispatchNotified(Boolean dmAirDispatchNotified) ;

	public String getAirRemarks() ;

	public void setAirRemarks(String airRemarks) ;

	public Integer getHoursToAirport() ;

	public void setHoursToAirport(Integer hoursToAirport) ;

	public Integer getMinutesToAirport() ;
	
	public void setMinutesToAirport(Integer minutesToAirport);

	public Boolean getItineraryReceived();

	public void setItineraryReceived(Boolean itineraryReceived);

	public String getNameOnPictureId() ;

	public void setNameOnPictureId(String nameOnPictureId) ;

	public String getDepartFromJetport() ;

	public void setDepartFromJetport(String departFromJetport) ;

	public String getOvernightRemarks() ;

	public void setOvernightRemarks(String overnightRemarks);

	public void setRosterParentId(Long id);
	
	public Long getRosterParentId();
	
	/**
	 * @return the crypticFilterCodeForCiCheckInDate
	 */
	public String getCrypticFilterCodeForCiCheckInDate();

	/**
	 * @param crypticFilterCodeForCiCheckInDate the crypticFilterCodeForCiCheckInDate to set
	 */
	public void setCrypticFilterCodeForCiCheckInDate(String crypticFilterCodeForCiCheckInDate);
	
	/**
	 * @return the crypticFilterCodeForMobilizationStartDate
	 */
	public String getCrypticFilterCodeForMobilizationStartDate();

	/**
	 * @param crypticFilterCodeForMobilizationStartDate the crypticFilterCodeForMobilizationStartDate to set
	 */
	public void setCrypticFilterCodeForMobilizationStartDate(String crypticFilterCodeForMobilizationStartDate);
	
	/**
	 * @return the crypticFilterCodeForCiFirstWorkDate
	 */
	public String getCrypticFilterCodeForCiFirstWorkDate();

	/**
	 * @param crypticFilterCodeForCiFirstWorkDate the crypticFilterCodeForCiFirstWorkDate to set
	 */
	public void setCrypticFilterCodeForCiFirstWorkDate(String crypticFilterCodeForCiFirstWorkDate);

	public Date getDmTentativeDemobDate() ;

	public void setDmTentativeDemobDate(Date dmTentativeDemobDate) ;

	public String getCrypticFilterCodeForTentativeDemobDate() ;
	public void setCrypticFilterCodeForTentativeDemobDate(
			String crypticFilterCodeForTentativeDemobDate) ;	

	public String getLeaderType();

	public void setLeaderType(String leaderType);

	public String getResourceClassification() ;

	public void setResourceClassification(String resourceClassification) ;
	
	public String getTraineeString() ;

	public void setTraineeString(String traineeString) ;

	public String getActualReleaseTime() ;

	public void setActualReleaseTime(String actualReleaseTime) ;

	public String getCheckInTime();
	
	public void setCheckInTime(String checkInTime) ;

	public String getArrivalTime() ;

	public void setArrivalTime(String arrivalTime);

	public String getTentativeReleaseTime() ;

	public void setTentativeReleaseTime(String tentativeReleaseTime) ;
	
	public String getHoursToAirportString();
	
	public void setHoursToAirportString(String hoursToAirportString) ;
	
	public String getMinutesToAirportString() ;

	public void setMinutesToAirportString(String minutesToAirportString) ;
	
	public String getNumberOfPersonnelString() ;
	
	public void setNumberOfPersonnelString(String numberOfPersonnelString) ;
	
	public String getCiLengthAtAssignmentString();
	
	public void setCiLengthAtAssignmentString(String ciLengthAtAssignmentString) ;
	
	public String getContractorCooperatorName() ;
	
	public void setContractorCooperatorName(String contractorCooperatorName) ;
	
	public String getReturnTravelMethod() ;
	
	public void setReturnTravelMethod(String returnTravelMethod) ;
	
	public String getSpecialInstructions() ;
	
	public void setSpecialInstructions(String specialInstructions) ;
	
	public String getSupplies() ;
	
	public void setSupplies(String supplies) ;
	
	public String getDmAirDispatchNotifiedString() ;
	
	public void setDmAirDispatchNotifiedString(String dmAirDispatchNotifiedString) ;
	
	public String getDmCheckoutFormPrintedString() ;

	public void setDmCheckoutFormPrintedString(String dmCheckoutFormPrintedString) ;

	public String getDmReleaseDispatchNotifiedString() ;

	public void setDmReleaseDispatchNotifiedString(
			String dmReleaseDispatchNotifiedString) ;

	public String getContractedString() ;

	public void setContractedString(String contractedString) ;

	public String getItineraryReceivedString() ;

	public void setItineraryReceivedString(String itineraryReceivedString) ;

	public String getDmPlanningDispatchNotifiedString() ;

	public void setDmPlanningDispatchNotifiedString(
			String dmPlanningDispatchNotifiedString) ;

	public String getDmReassignableString();

	public void setDmReassignableString(String dmReassignableString);

	public String getDmRestOvernightString() ;
	
	public void setDmRestOvernightString(String dmRestOvernightString) ;
	
	public String getCrypticDateFilterCodeForActualReleaseDate() ;
	
	public void setCrypticDateFilterCodeForActualReleaseDate(
			String crypticDateFilterCodeForActualReleaseDate) ;

	public String getCrypticDateFilterCodeForTentativeReleaseDate() ;

	public void setCrypticDateFilterCodeForTentativeReleaseDate(
			String crypticDateFilterCodeForTentativeReleaseDate) ;

	public String getCrypticDateFilterCodeForCheckInDate() ;

	public void setCrypticDateFilterCodeForCheckInDate(
			String crypticDateFilterCodeForCheckInDate) ;

	public String getCrypticFilterCodeForEstimatedArrivalDate() ;

	public void setCrypticFilterCodeForEstimatedArrivalDate(
			String crypticFilterCodeForEstimatedArrivalDate) ;

	public void setCheckboxFilter(IncidentResourceCheckboxFilter checkboxFilter);
	
	public IncidentResourceCheckboxFilter getCheckboxFilter();

	/**
	 * @return the vinName
	 */
	public String getVinName() ;

	/**
	 * @param vinName the vinName to set
	 */
	public void setVinName(String vinName) ;

	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() ;

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) ;

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() ;

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);

	/**
	 * @return the contractorAgreementNumber
	 */
	public String getContractorAgreementNumber() ;

	/**
	 * @param contractorAgreementNumber the contractorAgreementNumber to set
	 */
	public void setContractorAgreementNumber(String contractorAgreementNumber);	

	public Long getAssignmentId();
	
	public void setAssignmentId(Long assignmentId);

	public Boolean getUnRosteredOnly() ;
	
	public void setUnRosteredOnly(Boolean unRosteredOnly) ;

	public Long getExcludeResourceId() ;
	
	public void setExcludeResourceId(Long excludeResourceId);

	/**
	 * @return the hierarchal
	 */
	public Boolean getHierarchal();

	/**
	 * @param hierarchal the hierarchal to set
	 */
	public void setHierarchal(Boolean hierarchal);
	
}
