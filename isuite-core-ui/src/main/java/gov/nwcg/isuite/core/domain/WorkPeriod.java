package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.Collection;
import java.util.Date;

public interface WorkPeriod extends Persistable{

	/**
	 * Returns collection of assignments.
	 * 
	 * @return
	 * 		collection of assignments to return
	 */
	public Collection<Assignment> getAssignments();
	
	/**
	 * Sets the assignments associated with the incident resource work period
	 * 
	 * @param collection
	 * 			assignments associated with the inc res work period to set
	 */
	public void setAssignments(Collection<Assignment> collection);

	/**
	 * Returns the incident resource id.
	 * 
	 * @return
	 * 		the incident resource id to return
	 */
	public Long getIncidentResourceId();
	
	/**
	 * Sets the incident resource id.
	 * 
	 * @param id
	 * 		the incident resource id to set
	 */
	public void setIncidentResourceId(Long id);
	
	/**
	 * Returns the incident resource.
	 * 
	 * @return
	 * 		the incident resource to return
	 */
	public IncidentResource getIncidentResource();
	
	/**
	 * Sets the incident resource.
	 * 
	 * @param incResource
	 * 			the incident resource to set
	 */
	public void setIncidentResource(IncidentResource incResource);
	
	/**
	 * Returns the check in arrival jet port id.
	 * 
	 * @return
	 * 		the check in arrival jet port id to return
	 */
	public Long getCIArrivalJetPortId();
	
	/**
	 * Sets the check in arrival jet port id.
	 * 
	 * @param val
	 * 			the check in arrival jet port to set
	 */
	public void setCIArrivalJetPortId(Long val);
	
   /**
    * Returns the check in arrival jet port.
    * 
    * @return
    *       the check in arrival jet port to return
    */
   public JetPort getCIArrivalJetPort();
   
   /**
    * Sets the check in arrival jet port.
    * 
    * @param val
    *          the check in arrival jet port to set
    */
   public void setCIArrivalJetPort(JetPort val);

   /**
	 * Returns the check in rental location.
	 * 
	 * @return
	 * 		the check in rental location to return
	 */
	public String getCIRentalLocation();
	
	/**
	 * Sets the check in rental location.
	 * 
	 * @param val
	 * 			the check in rental location to set
	 */
	public void setCIRentalLocation(String val);
	
	/**
	 * Returns the check in pre planning remarks.
	 * 
	 * @return
	 * 		the check in pre planning remarks to return
	 */
	public String getCIPrePlanningRemarks();
	
	/**
	 * Sets the check in pre planning remarks.
	 * 
	 * @param val
	 * 			the check in pre planning remarks to set
	 */
	public void setCIPrePlanningRemarks(String val);
	
	/**
	 * Returns the check in first work date.
	 * 
	 * @return
	 * 		the check in  first work date to return
	 */
	public Date getCIFirstWorkDate();
	
	/**
	 * Sets the check in first work date.
	 * 
	 * @param dt
	 * 		the check in first work date to set
	 */
	public void setCIFirstWorkDate(Date dt);
	
	/**
	 * Returns the check in date.
	 * 
	 * @return
	 * 		the check in date to return
	 */
	public Date getCICheckInDate();
	
	/**
	 * Sets the check in date.
	 * 
	 * @param dt
	 * 		the check in date to set
	 */
	public void setCICheckInDate(Date dt);

	/**
	 * Returns the check in length at assignment.
	 * 
	 * @return
	 * 		the check in length at assignment to return
	 */
	public Long getCILengthAtAssignment();
	
	/**
	 * Sets the check in length at assignment.
	 * 
	 * @param assignment
	 * 			the check in length at assignment to set
	 */
	public void setCILengthAtAssignment(Long assignment);
	
	/**
	 * Returns the check in resource mobilization id.
	 * 
	 * @return
	 * 		the check in resource mobilization id to return
	 */
	public Long getCIMobilizationId();
	
	/**
	 * Sets the check in resource mobilization id.
	 * 
	 * @param id
	 * 			the check in resource mobilization id to set
	 */
	public void setCIMobilizationId(Long id);
	
	/**
	 * Returns the check in resource mobilization.
	 * 
	 * @return
	 * 		the check in resource mobilization to return
	 */
	public ResourceMobilization getCIResourceMobilization();
	
	/**
	 * Sets the check in resource mobilization.
	 * 
	 * @param rm
	 * 			the check in resource mobilization to set
	 */
	public void setCIResourceMobilization(ResourceMobilization rm);
	

	/**
	 * Returns the demob tentative demob city.
	 * 
	 * @return
	 * 		the demob tentative demob city to return
	 */
	public String getDMTentativeDemobCity();
	
	/**
	 * Sets the demob tentative demob city.
	 * 
	 * @param city
	 * 		the demob tentative demob city to set
	 */
	public void setDMTentativeDemobCity(String city);
	
	/**
	 * Returns the demob tentative demob state id.
	 * 
	 * @return
	 * 		the demob tentative demob state id to return
	 */
	public Long getDMTentativeDemobStateId();
	
	/**
	 * Sets the demob tentative demob state Id.
	 * 
	 * @param state
	 * 			the demob tentative demob state Id to set
	 */
	public void setDMTentativeDemobStateId(Long state);

   /**
    * Returns the demob tentative demob state.
    * 
    * @return
    *       the demob tentative demob state to return
    */
   public CountrySubdivision getDMTentativeDemobState();
   
   /**
    * Sets the demob tentative demob state.
    * 
    * @param state
    *          the demob tentative demob state to set
    */
   public void setDMTentativeDemobState(CountrySubdivision state);

	/**
	 * Returns the demob release date.
	 * 
	 * @return
	 * 		the demob release date to return
	 */
	public Date getDMReleaseDate();
	
	/**
	 * Sets the demob release date.
	 * 
	 * @param dt
	 * 		the demob release date to set
	 */
	public void setDMReleaseDate(Date dt);

	/**
	 * Returns the demob tentative release date.
	 * 
	 * @return
	 * 		the demob tentative release date to return
	 */
	public Date getDMTentativeReleaseDate();
	
	/**
	 * Sets the demob tentative release date.
	 * 
	 * @param dt
	 * 		the demob tentative release date to set
	 */
	public void setDMTentativeReleaseDate(Date dt);
	
	/**
	 * Returns the demob travel method.
	 * 
	 * @return
	 * 		the demob travel method to return
	 */
	public TravelMethodTypeEnum getDMTravelMethod();
	
	/**
	 * Sets the demob travel method.
	 * 
	 * @param type
	 * 		the demob travel method id to set
	 */
	public void setDMTravelMethod(TravelMethodTypeEnum type);

	/**
	 * Returns the demob reassignable flag.
	 * 
	 * @return
	 * 		the demob reassignable flag to return
	 */
	public Boolean isDMReAssignable();

	/**
	 * Sets the demob reassignable flag.
	 * 
	 * @param val
	 * 		the demob reassignable flag to set
	 */
	public void setDMReAssignable(Boolean val);
	
	/**
	 * Returns the demob checkout form printed flag.
	 * 
	 * @return
	 * 		the demob checkout form printed flag to return
	 */
	public Boolean isDMCheckoutFormPrinted();

	/**
	 * Sets the demob checkout form printed flag.
	 * 
	 * @param val
	 * 		the demob checkout form printed flag to set
	 */
	public void setDMCheckoutFormPrinted(Boolean val);

	/**
	 * Returns the demob planning dispatch notified flag.
	 * 
	 * @return
	 * 		the demob planning dispatch notified flag to return
	 */
	public Boolean isDMPlanningDispatchNotified();

	/**
	 * Sets the demob planning dispatch notified flag.
	 * 
	 * @param val
	 * 		the demob planning dispatch notified flag to set
	 */
	public void setDMPlanningDispatchNotified(Boolean val);

	/**
	 * Returns the demob release dispatch notified flag.
	 * 
	 * @return
	 * 		the demob release dispatch notified flag to return
	 */
	public Boolean isDMReleaseDispatchNotified();

	/**
	 * Sets the demob release dispatch notified flag.
	 * 
	 * @param val
	 * 		the demob release dispatch nofified flag to set
	 */
	public void setDMReleaseDispatchNotified(Boolean val);

	/**
	 * Returns the demob rest overnight notified flag.
	 * 
	 * @return
	 * 		the demob rest overnight flag to return
	 */
	public Boolean isDMRestOvernight();

	/**
	 * Sets the demob rest overnight flag.
	 * 
	 * @param val
	 * 		the demob rest overnight flag to set
	 */
	public void setDMRestOvernight(Boolean val);

	/**
	 * Returns the demob release remarks.
	 * 
	 * @return
	 * 		the demob release remarks to return
	 */
	public String getDMReleaseRemarks();
	
	/**
	 * Sets the demob release remarks.
	 * 
	 * @param val
	 * 		the demob release remarks to set
	 */
	public void setDMReleaseRemarks(String val);
	
	/**
	 * Returns the demob planning remarks.
	 * 
	 * @return
	 * 		the demob planning remarks to return
	 */
	public String getDMPlanningRemarks();
	
	/**
	 * Sets the demob planning remarks.
	 * 
	 * @param val
	 * 		the demob planning remarks to set
	 */
	public void setDMPlanningRemarks(String val);

	/**
	 * Returns the demob air travel id.
	 * 
	 * @return
	 * 		the demob air travel id to return
	 */
	public Long getDMAirTravelId();
	
	/**
	 * Sets the demob air travel id.
	 * 
	 * @param id
	 * 		the demob air travel id to set
	 */
	public void setDMAirTravelId(Long id);
	
	/**
	 * Returns the demob air travel.
	 * 
	 * @return
	 * 		the demob air travel to return
	 */
	public AirTravel getDMAirTravel();
	
	/**
	 * Sets the demob air travel.
	 * 
	 * @param val
	 * 		the demob air travel to set
	 */
	public void setDMAirTravel(AirTravel val);

	/**
	 * Returns the collection of work period question values.
	 * 
	 * @return
	 * 		collection of work period question values to return
	 */
	public Collection<WorkPeriodQuestionValue> getWorkPeriodQuestionValues();

	/**
	 * Sets the collection of work period question values.
	 * 
	 * @param values
	 * 			collection of work period question values to set
	 */
	public void setWorkPeriodQuestionValues(Collection<WorkPeriodQuestionValue> values);
	
	/**
	 * Returns the tentative arrival date
	 * 
	 * @return
	 * 		tentative arrival date
	 */
	public Date getCITentativeArrivalDate();
	
	/**
	 * Sets the tentative arrival date
	 * 
	 * @param date
 	 * 		tentative arrival date to set
	 */
	public void setCITentativeArrivalDate(Date date);

	/**
	 * Returns the wpOvernightStayInfos.
	 *
	 * @return 
	 *		the wpOvernightStayInfos to return
	 */
	public Collection<WorkPeriodOvernightStayInfo> getWpOvernightStayInfos();

	/**
	 * Sets the wpOvernightStayInfos.
	 *
	 * @param wpOvernightStayInfos 
	 *			the wpOvernightStayInfos to set
	 */
	//public void setWpOvernightStayInfos(Collection<WorkPeriodOvernightStayInfo> wpOvernightStayInfos);

	public void addWpWpOvernightStayInfo(WorkPeriodOvernightStayInfo info);
	
	/**
	 * Returns the dmTentativeArrivalDate.
	 *
	 * @return 
	 *		the dmTentativeArrivalDate to return
	 */
	public Date getDmTentativeArrivalDate();

	/**
	 * Sets the dmTentativeArrivalDate.
	 *
	 * @param dmTentativeArrivalDate 
	 *			the dmTentativeArrivalDate to set
	 */
	public void setDmTentativeArrivalDate(Date dmTentativeArrivalDate);	

	/**
	 * Returns the ciTravelMethod.
	 *
	 * @return 
	 *		the ciTravelMethod to return
	 */
	public TravelMethodTypeEnum getCiTravelMethod();

	/**
	 * Sets the ciTravelMethod.
	 *
	 * @param ciTravelMethod 
	 *			the ciTravelMethod to set
	 */
	public void setCiTravelMethod(TravelMethodTypeEnum ciTravelMethod);

	public IncidentAccountCode getDefIncidentAccountCode() ;

	public void setDefIncidentAccountCode(IncidentAccountCode defIncidentAccountCode) ;

	public Long getDefIncidentAccountCodeId() ;

	public void setDefIncidentAccountCodeId(Long defIncidentAccountCodeId) ;

	public Boolean getGroundSupport();

	public void setGroundSupport(Boolean groundSupport);
	
}
