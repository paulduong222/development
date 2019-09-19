package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.impl.AirTravelImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.StringUtility;

public class AirTravelVo extends AbstractVo implements PersistableVo {
	private JetPortVo jetPortVo;
	private String airline;
	private Boolean dispatchNotified=false;
	private Integer hoursToAirport;
	private Integer minutesToAirport;
	private String leaveTime;
	private String flightNumber;
	private String flightTime;
	private String remarks;
	private Boolean itineraryReceived=false;

	public AirTravelVo(){
		   
	}

	/**
	 * Returns a AirTravelVo instance from a AirTravel entity.
	 * 
	 * @param entity
	 * 			the source AirTravel entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of AirTravelVo
	 * @throws Exception
	 */
	public static AirTravelVo getInstance(AirTravel entity,boolean cascadable) throws Exception {
		AirTravelVo vo = new AirTravelVo();

		if(null == entity)
			throw new Exception("Unable to create AirTravelVo from null AirTravel entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setAirline(entity.getAirline());
			vo.setDispatchNotified(entity.isDispatchNotified());
			vo.setFlightNumber(entity.getFlightNumber());
			vo.setFlightTime( (null != entity.getFlightTime() ? String.valueOf(entity.getFlightTime()) : ""));
			if(null != vo.getFlightTime() && !vo.getFlightTime().isEmpty()){
				if(vo.getFlightTime().length() < 4)
					vo.setFlightTime(StringUtility.leftPad(String.valueOf(vo.getFlightTime()), "0", 4));
			}
			
			vo.setHoursToAirport(entity.getHoursToAirport());
			vo.setItineraryReceived(entity.isItineraryReceived());
			vo.setLeaveTime((null != entity.getLeaveTime() ? String.valueOf(entity.getLeaveTime()) : ""));
			if(null != vo.getLeaveTime() && !vo.getLeaveTime().isEmpty()){
				if(vo.getLeaveTime().length() < 4)
					vo.setLeaveTime(StringUtility.leftPad(String.valueOf(vo.getLeaveTime()), "0", 4));
			}
			vo.setMinutesToAirport(entity.getMinutesToAirport());
			vo.setRemarks(entity.getRemarks());
			if(null!=entity.getJetPort())
				vo.setJetPortVo(JetPortVo.getInstance(entity.getJetPort(), true));
		}

		return vo;
	}

	/**
	 * Returns a AirTravel entity from an AirTravelVo.
	 * 
	 * @param vo
	 * 			the source AirTravelVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			AirTravel entity
	 * @throws Exception
	 */
	public static AirTravel toEntity(AirTravelVo vo,boolean cascadable,Persistable...persistables) throws Exception {

		AirTravel entity = new AirTravelImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setAirline(vo.getAirline());
			entity.setDispatchNotified(vo.getDispatchNotified());
			entity.setFlightNumber(vo.getFlightNumber());
			entity.setFlightTime( (null != vo.getFlightTime() && !vo.getFlightTime().isEmpty() ? Integer.valueOf(vo.getFlightTime()) : 0));
			entity.setHoursToAirport(vo.getHoursToAirport());
			entity.setItineraryReceived(vo.getItineraryReceived());
			entity.setLeaveTime((null != vo.getLeaveTime() && !vo.getLeaveTime().isEmpty() ? Integer.valueOf(vo.getLeaveTime()) : 0));
			entity.setMinutesToAirport(vo.getMinutesToAirport());
			entity.setRemarks(vo.getRemarks());
			if(null != vo.getJetPortVo())
				entity.setJetPort(JetPortVo.toEntity(null,vo.getJetPortVo(), false));
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the JetPort field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source AirTravel entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AirTravel entity) throws ValidationException {
		// todo
	}
	
	/**
	 * Returns the jetPortVo.
	 *
	 * @return 
	 *		the jetPortVo to return
	 */
	public JetPortVo getJetPortVo() {
		return jetPortVo;
	}

	/**
	 * Sets the jetPortVo.
	 *
	 * @param jetPortVo 
	 *			the jetPortVo to set
	 */
	public void setJetPortVo(JetPortVo jetPortVo) {
		this.jetPortVo = jetPortVo;
	}

	/**
	 * Returns the airline.
	 *
	 * @return 
	 *		the airline to return
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * Sets the airline.
	 *
	 * @param airline 
	 *			the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Returns the dispatchNotified.
	 *
	 * @return 
	 *		the dispatchNotified to return
	 */
	public Boolean getDispatchNotified() {
		return dispatchNotified;
	}

	/**
	 * Sets the dispatchNotified.
	 *
	 * @param dispatchNotified 
	 *			the dispatchNotified to set
	 */
	public void setDispatchNotified(Boolean dispatchNotified) {
		this.dispatchNotified = dispatchNotified;
	}

	/**
	 * Returns the hoursToAirport.
	 *
	 * @return 
	 *		the hoursToAirport to return
	 */
	public Integer getHoursToAirport() {
		return hoursToAirport;
	}

	/**
	 * Sets the hoursToAirport.
	 *
	 * @param hoursToAirport 
	 *			the hoursToAirport to set
	 */
	public void setHoursToAirport(Integer hoursToAirport) {
		this.hoursToAirport = hoursToAirport;
	}

	/**
	 * Returns the minutesToAirport.
	 *
	 * @return 
	 *		the minutesToAirport to return
	 */
	public Integer getMinutesToAirport() {
		return minutesToAirport;
	}

	/**
	 * Sets the minutesToAirport.
	 *
	 * @param minutesToAirport 
	 *			the minutesToAirport to set
	 */
	public void setMinutesToAirport(Integer minutesToAirport) {
		this.minutesToAirport = minutesToAirport;
	}

	/**
	 * Returns the leaveTime.
	 *
	 * @return 
	 *		the leaveTime to return
	 */
	public String getLeaveTime() {
		return leaveTime;
	}

	/**
	 * Sets the leaveTime.
	 *
	 * @param leaveTime 
	 *			the leaveTime to set
	 */
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * Returns the flightNumber.
	 *
	 * @return 
	 *		the flightNumber to return
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * Sets the flightNumber.
	 *
	 * @param flightNumber 
	 *			the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * Returns the flightTime.
	 *
	 * @return 
	 *		the flightTime to return
	 */
	public String getFlightTime() {
		return flightTime;
	}

	/**
	 * Sets the flightTime.
	 *
	 * @param flightTime 
	 *			the flightTime to set
	 */
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Returns the remarks.
	 *
	 * @return 
	 *		the remarks to return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks 
	 *			the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Returns the itineraryReceived.
	 *
	 * @return 
	 *		the itineraryReceived to return
	 */
	public Boolean getItineraryReceived() {
		return itineraryReceived;
	}

	/**
	 * Sets the itineraryReceived.
	 *
	 * @param itineraryReceived 
	 *			the itineraryReceived to set
	 */
	public void setItineraryReceived(Boolean itineraryReceived) {
		this.itineraryReceived = itineraryReceived;
	}

	   
}
