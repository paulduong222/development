package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.Date;


/**
 * This represents the view for incident resources. 
 * 
 * @author dprice
 */
public interface IncidentResourceOnlyView {

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
	 * Returns the parentResourceId.
	 *
	 * @return 
	 *		the parentResourceId to return
	 */
	public Long getParentResourceId();


	/**
	 * Sets the parentResourceId.
	 *
	 * @param parentResourceId 
	 *			the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId);


	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public Boolean getPermanent();


	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(Boolean permanent);


	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled();


	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled);


	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted();


	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted);


	/**
	 * Returns the leader.
	 *
	 * @return 
	 *		the leader to return
	 */
	public Boolean getLeader();

	/**
	 * Sets the leader.
	 *
	 * @param leader 
	 *			the leader to set
	 */
	public void setLeader(Boolean leader);

	
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
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId();


	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId);


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
	 * Returns the incidentResourceId.
	 *
	 * @return 
	 *		the incidentResourceId to return
	 */
	public Long getIncidentResourceId();


	/**
	 * Sets the incidentResourceId.
	 *
	 * @param incidentResourceId 
	 *			the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);


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
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId();


	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId);


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
	 * Returns the checkInDate.
	 *
	 * @return 
	 *		the checkInDate to return
	 */
	public Date getCiCheckInDate();


	/**
	 * Sets the checkInDate.
	 *
	 * @param checkInDate 
	 *			the checkInDate to set
	 */
	public void setCiCheckInDate(Date checkInDate);


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
	 * Returns the assignmentId.
	 *
	 * @return 
	 *		the assignmentId to return
	 */
	public Long getAssignmentId();


	/**
	 * Sets the assignmentId.
	 *
	 * @param assignmentId 
	 *			the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId);


	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName() ;


	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName);


	/**
	 * Returns the resourceAgencyCode.
	 *
	 * @return 
	 *		the resourceAgencyCode to return
	 */
	public String getResourceAgencyCode() ;



	/**
	 * Sets the resourceAgencyCode.
	 *
	 * @param resourceAgencyCode 
	 *			the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode) ;



	/**
	 * Returns the resourceUnitcode.
	 *
	 * @return 
	 *		the resourceUnitcode to return
	 */
	public String getResourceUnitCode();



	/**
	 * Sets the resourceUnitcode.
	 *
	 * @param resourceUnitcode 
	 *			the resourceUnitcode to set
	 */
	public void setResourceUnitCode(String resourceUnitcode);

	public ResourceClassificationEnum getResourceClassification();		


	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification);

	
	public Date getDmReleaseDate();

	public void setDmReleaseDate(Date dmReleaseDate);

	public String getCellPhoneNumber();

	public void setCellPhoneNumber(String cellPhoneNumber);
	
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
    
	public String getDepartmentCode();

	public void setDepartmentCode(String departmentCode) ;

	public String getDepartmentSubCode();

	public void setDepartmentSubCode(String departmentSubCode);
	
	public String getDepartmentDesc();

	public void setDepartmentDesc(String val) ;

	public String getDepartmentSubDesc();

	public void setDepartmentSubDesc(String val);

	public Long getAssignmentTimeId();

	public void setAssignmentTimeId(Long assignmentTimeId) ;

}
