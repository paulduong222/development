package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswAirTravel", table="isw_air_travel")
public class IswAirTravel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_AIR_TRAVEL", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "JetPort", type="COMPLEX", target=IswlJetPort.class
			, lookupname="Id", sourcename="JetPortId")
    private IswlJetPort jetPort;
	
	@XmlTransferField(name = "JetPortId", sqlname = "JET_PORT_ID", type = "LONG"
						, derived = true, derivedfield = "JetPort")
	private Long jetPortId;
	
	@XmlTransferField(name = "Airline", sqlname = "AIRLINE", type="STRING")
	private String airline;

	@XmlTransferField(name = "DispatchNotified", sqlname = "IS_DISPATCH_NOTIFIED", type="BOOLEAN")
	private Boolean dispatchNotified;

	@XmlTransferField(name = "HoursToAirport", sqlname = "HOURS_TO_AIRPORT", type="INTEGER")
	private Integer hoursToAirport;

	@XmlTransferField(name = "MinutesToAirport", sqlname = "MINUTES_TO_AIRPORT", type="INTEGER")
	private Integer minutesToAirport;

	@XmlTransferField(name = "LeaveTime", sqlname = "LEAVE_TIME", type="INTEGER")
	private Integer leaveTime;

	@XmlTransferField(name = "FlightNumber", sqlname = "FLIGHT_NUMBER", type="STRING")
	private String flightNumber;

	@XmlTransferField(name = "FlightTime", sqlname = "FLIGHT_TIME", type="INTEGER")
	private Integer flightTime;

	@XmlTransferField(name = "Remarks", sqlname = "REMARKS", type="STRING")
	private String remarks;

	@XmlTransferField(name = "ItineraryReceived", sqlname = "IS_ITINERARY_RECEIVED", type="BOOLEAN")
	private Boolean itineraryReceived;

	public IswAirTravel() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public Long getJetPortId() {
		return jetPortId;
	}

	/**
	 * @param jetPortId
	 */
	public void setJetPortId(Long jetPortId) {
		this.jetPortId = jetPortId;
	}

	/**
	 * @return
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return
	 */
	public Boolean isDispatchNotified() {
		return dispatchNotified;
	}

	/**
	 * @param dispatchNotified
	 */
	public void setDispatchNotified(Boolean dispatchNotified) {
		this.dispatchNotified = dispatchNotified;
	}

	/**
	 * @return
	 */
	public Integer getHoursToAirport() {
		return hoursToAirport;
	}

	/**
	 * @param hoursToAirport
	 */
	public void setHoursToAirport(Integer hoursToAirport) {
		this.hoursToAirport = hoursToAirport;
	}

	/**
	 * @return
	 */
	public Integer getMinutesToAirport() {
		return minutesToAirport;
	}

	/**
	 * @param minutesToAirport
	 */
	public void setMinutesToAirport(Integer minutesToAirport) {
		this.minutesToAirport = minutesToAirport;
	}

	/**
	 * @return
	 */
	public Integer getLeaveTime() {
		return leaveTime;
	}

	/**
	 * @param leaveTime
	 */
	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * @return
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return
	 */
	public Integer getFlightTime() {
		return flightTime;
	}

	/**
	 * @param flightTime
	 */
	public void setFlightTime(Integer flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return
	 */
	public Boolean isItineraryReceived() {
		return itineraryReceived;
	}

	/**
	 * @param itineraryReceived
	 */
	public void setItineraryReceived(Boolean itineraryReceived) {
		this.itineraryReceived = itineraryReceived;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the dispatchNotified
	 */
	public Boolean getDispatchNotified() {
		return dispatchNotified;
	}

	/**
	 * @return the itineraryReceived
	 */
	public Boolean getItineraryReceived() {
		return itineraryReceived;
	}

	/**
	 * @return the jetPort
	 */
	public IswlJetPort getJetPort() {
		return jetPort;
	}

	/**
	 * @param jetPort the jetPort to set
	 */
	public void setJetPort(IswlJetPort jetPort) {
		this.jetPort = jetPort;
	}

}
