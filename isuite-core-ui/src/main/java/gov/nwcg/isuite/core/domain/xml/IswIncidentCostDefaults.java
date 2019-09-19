package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswIncidentCostDefaults", table="isw_incident_cost_defaults")
public class IswIncidentCostDefaults {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_COST_DEFAULTS", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG", updateable=false)
	private Long incidentId;
	
	@XmlTransferField(name = "AirTravelAmount", sqlname="AIR_TRAVEL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal airTravelAmount;
	
	@XmlTransferField(name = "RentalVehicleAmount", sqlname="RENTAL_VEHICLE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal rentalVehicleAmount;
	
	@XmlTransferField(name = "OtherAmount", sqlname="OTHER_AMOUNT", type="BIGDECIMAL")
	private BigDecimal otherAmount;

	@XmlTransferField(name = "OtherDescription", sqlname="OTHER_DESCRIPTION", type="STRING")
	private String otherDescription;
	
	@XmlTransferField(name = "DefaultHours", sqlname="DEFAULT_HOURS", type="INTEGER")
	private Integer defaultHours;
	
	public IswIncidentCostDefaults(){
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the airTravelAmount
	 */
	public BigDecimal getAirTravelAmount() {
		return airTravelAmount;
	}

	/**
	 * @param airTravelAmount the airTravelAmount to set
	 */
	public void setAirTravelAmount(BigDecimal airTravelAmount) {
		this.airTravelAmount = airTravelAmount;
	}

	/**
	 * @return the rentalVehicleAmount
	 */
	public BigDecimal getRentalVehicleAmount() {
		return rentalVehicleAmount;
	}

	/**
	 * @param rentalVehicleAmount the rentalVehicleAmount to set
	 */
	public void setRentalVehicleAmount(BigDecimal rentalVehicleAmount) {
		this.rentalVehicleAmount = rentalVehicleAmount;
	}

	/**
	 * @return the otherAmount
	 */
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @return the otherDescription
	 */
	public String getOtherDescription() {
		return otherDescription;
	}

	/**
	 * @param otherDescription the otherDescription to set
	 */
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	/**
	 * @return the defaultHours
	 */
	public Integer getDefaultHours() {
		return defaultHours;
	}

	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(Integer defaultHours) {
		this.defaultHours = defaultHours;
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

	
}
