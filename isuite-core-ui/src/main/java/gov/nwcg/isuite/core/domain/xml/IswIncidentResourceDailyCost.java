package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswIncidentResourceDailyCost", table="isw_inc_res_daily_cost")
public class IswIncidentResourceDailyCost {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INC_RES_DAILY_COST", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AccrualCodeTransferableIdentity", alias="acccodeti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AccrualCodeId"
						, disjoined=true, disjoinedtable="iswl_accrual_code", disjoinedfield="transferable_identity",disjoinedsource="ACCRUAL_CODE_ID")
	private String accrualCodeTransferableIdentity;

	@XmlTransferField(name = "AccrualCodeId", sqlname="ACCRUAL_CODE_ID", type="LONG"
						,derived=true, derivedfield="AccrualCodeTransferableIdentityTransferableIdentity")
    private Long accrualCodeId;
	
	@XmlTransferField(name = "IncidentShiftTransferableIdentity", alias="incshiftti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentShiftId"
						, disjoined=true, disjoinedtable="isw_incident_shift", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_SHIFT_ID")
	private String incidentShiftTransferableIdentity;

	@XmlTransferField(name = "IncidentShiftId", sqlname="INCIDENT_SHIFT_ID", type="LONG"
						,derived=true, derivedfield="IncidentShiftTransferableIdentity")
    private Long incidentShiftId;
	
	@XmlTransferField(name = "CostGroupTransferableIdentity", alias="cgti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="CostGroupId"
		, disjoined=true, disjoinedtable="isw_cost_group", disjoinedfield="transferable_identity",disjoinedsource="COST_GROUP_ID")
	private String costGroupTransferableIdentity;

	@XmlTransferField(name = "CostGroupId", sqlname="COST_GROUP_ID", type="LONG"
						,derived=true, derivedfield="CostGroupTransferableIdentity")
    private Long costGroupId;
	
	@XmlTransferField(name = "IncidentAccountCodeTransferableIdentity", alias="iacti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentAccountCodeId"
						, disjoined=true, disjoinedtable="isw_incident_account_code", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_ACCOUNT_CODE_ID")
	private String incidentAccountCodeTransferableIdentity;

	@XmlTransferField(name = "IncidentAccountCodeId", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="LONG"
						,derived=true, derivedfield="IncidentAccountCodeTransferableIdentity")
	private Long incidentAccountCodeId;
    
	@XmlTransferField(name = "IncidentResourceId", sqlname="INCIDENT_RESOURCE_ID", type="LONG",updateable=false)
    private Long incidentResourceId;
	
	@XmlTransferField(name = "IncidentResourceOtherId", sqlname="INCIDENT_RESOURCE_OTHER_ID", type="LONG",updateable=false)
    private Long incidentResourceOtherId;

	@XmlTransferField(name = "ResourceCostType", sqlname="RESOURCE_COST_TYPE", type="STRING")
	private String resourceCostType;
	
	@XmlTransferField(name = "ActivityDate", sqlname="ACTIVITY_DATE", type="DATE")
	private Date activityDate;
	
	@XmlTransferField(name = "RateType", sqlname="RATE_TYPE", type="STRING")
	private String rateType;
	
	@XmlTransferField(name = "Units", sqlname="UNITS", type="BIGDECIMAL")
	private BigDecimal units;
	
	@XmlTransferField(name = "UnitCostAmount", sqlname="UNIT_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal unitCostAmount;

	@XmlTransferField(name = "AdjustmentAmount", sqlname="ADJUSTMENT_AMOUNT", type="BIGDECIMAL")
	private BigDecimal adjustmentAmount;
	
	@XmlTransferField(name = "CostLevel", sqlname="COST_LEVEL", type="STRING", nullwhenempty=true)
	private String costLevel;
	
	@XmlTransferField(name = "IsLocked", sqlname="IS_LOCKED", type="BOOLEAN")
	private Boolean isLocked=false;
	
	@XmlTransferField(name = "IsFlowdown", sqlname="IS_FLOWDOWN", type="BOOLEAN")
	private Boolean isFlowdown=false;
	
	@XmlTransferField(name = "PrimaryTotalAmount", sqlname="PRIMARY_TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal primaryTotalAmount;
	
	@XmlTransferField(name = "SubordinateTotalAmount", sqlname="SUBORDINATE_TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal subordinateTotalAmount;
	
	@XmlTransferField(name = "TotalCostAmount", sqlname="TOTAL_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal totalCostAmount;
	
	@XmlTransferField(name = "AircraftCostAmount", sqlname="AIRCRAFT_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal aircraftCostAmount;
	
	@XmlTransferField(name = "FlightHours", sqlname="FLIGHT_HOURS", type="BIGDECIMAL")
	private BigDecimal flightHours;
	
	@XmlTransferField(name = "NumberOfLoads", sqlname="NUMBER_OF_LOADS", type="INTEGER")
	private Integer numberOfLoads;
	
	@XmlTransferField(name = "WaterGallons", sqlname="WATER_GALLONS", type="BIGDECIMAL")
	private BigDecimal waterGallons;
	
	@XmlTransferField(name = "RetardantGallons", sqlname="RETARDANT_GALLONS", type="BIGDECIMAL")
	private BigDecimal retardantGallons;
	
	@XmlTransferField(name = "CargoPounds", sqlname="CARGO_POUNDS", type="BIGDECIMAL")
	private BigDecimal cargoPounds;
	
	@XmlTransferField(name = "NumberOfTrips", sqlname="NUMBER_OF_TRIPS", type="INTEGER")
	private Integer numberOfTrips;
	
	@XmlTransferField(name = "NumberOfPassengers", sqlname="NUMBER_OF_PASSENGERS", type="INTEGER")
	private Integer numberOfPassengers;

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
	 * @return the accrualCodeTransferableIdentity
	 */
	public String getAccrualCodeTransferableIdentity() {
		return accrualCodeTransferableIdentity;
	}

	/**
	 * @param accrualCodeTransferableIdentity the accrualCodeTransferableIdentity to set
	 */
	public void setAccrualCodeTransferableIdentity(
			String accrualCodeTransferableIdentity) {
		this.accrualCodeTransferableIdentity = accrualCodeTransferableIdentity;
	}

	/**
	 * @return the accrualCodeId
	 */
	public Long getAccrualCodeId() {
		return accrualCodeId;
	}

	/**
	 * @param accrualCodeId the accrualCodeId to set
	 */
	public void setAccrualCodeId(Long accrualCodeId) {
		this.accrualCodeId = accrualCodeId;
	}

	/**
	 * @return the incidentShiftTransferableIdentity
	 */
	public String getIncidentShiftTransferableIdentity() {
		return incidentShiftTransferableIdentity;
	}

	/**
	 * @param incidentShiftTransferableIdentity the incidentShiftTransferableIdentity to set
	 */
	public void setIncidentShiftTransferableIdentity(
			String incidentShiftTransferableIdentity) {
		this.incidentShiftTransferableIdentity = incidentShiftTransferableIdentity;
	}

	/**
	 * @return the incidentShiftId
	 */
	public Long getIncidentShiftId() {
		return incidentShiftId;
	}

	/**
	 * @param incidentShiftId the incidentShiftId to set
	 */
	public void setIncidentShiftId(Long incidentShiftId) {
		this.incidentShiftId = incidentShiftId;
	}

	/**
	 * @return the costGroupTransferableIdentity
	 */
	public String getCostGroupTransferableIdentity() {
		return costGroupTransferableIdentity;
	}

	/**
	 * @param costGroupTransferableIdentity the costGroupTransferableIdentity to set
	 */
	public void setCostGroupTransferableIdentity(
			String costGroupTransferableIdentity) {
		this.costGroupTransferableIdentity = costGroupTransferableIdentity;
	}

	/**
	 * @return the costGroupId
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}

	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}

	/**
	 * @return the incidentAccountCodeTransferableIdentity
	 */
	public String getIncidentAccountCodeTransferableIdentity() {
		return incidentAccountCodeTransferableIdentity;
	}

	/**
	 * @param incidentAccountCodeTransferableIdentity the incidentAccountCodeTransferableIdentity to set
	 */
	public void setIncidentAccountCodeTransferableIdentity(
			String incidentAccountCodeTransferableIdentity) {
		this.incidentAccountCodeTransferableIdentity = incidentAccountCodeTransferableIdentity;
	}

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}

	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
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
	 * @return the incidentResourceOtherId
	 */
	public Long getIncidentResourceOtherId() {
		return incidentResourceOtherId;
	}

	/**
	 * @param incidentResourceOtherId the incidentResourceOtherId to set
	 */
	public void setIncidentResourceOtherId(Long incidentResourceOtherId) {
		this.incidentResourceOtherId = incidentResourceOtherId;
	}

	/**
	 * @return the resourceCostType
	 */
	public String getResourceCostType() {
		return resourceCostType;
	}

	/**
	 * @param resourceCostType the resourceCostType to set
	 */
	public void setResourceCostType(String resourceCostType) {
		this.resourceCostType = resourceCostType;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	/**
	 * @return the unitCostAmount
	 */
	public BigDecimal getUnitCostAmount() {
		return unitCostAmount;
	}

	/**
	 * @param unitCostAmount the unitCostAmount to set
	 */
	public void setUnitCostAmount(BigDecimal unitCostAmount) {
		this.unitCostAmount = unitCostAmount;
	}

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	/**
	 * @return the costLevel
	 */
	public String getCostLevel() {
		return costLevel;
	}

	/**
	 * @param costLevel the costLevel to set
	 */
	public void setCostLevel(String costLevel) {
		this.costLevel = costLevel;
	}

	/**
	 * @return the isLocked
	 */
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the isFlowdown
	 */
	public Boolean getIsFlowdown() {
		return isFlowdown;
	}

	/**
	 * @param isFlowdown the isFlowdown to set
	 */
	public void setIsFlowdown(Boolean isFlowdown) {
		this.isFlowdown = isFlowdown;
	}

	/**
	 * @return the primaryTotalAmount
	 */
	public BigDecimal getPrimaryTotalAmount() {
		return primaryTotalAmount;
	}

	/**
	 * @param primaryTotalAmount the primaryTotalAmount to set
	 */
	public void setPrimaryTotalAmount(BigDecimal primaryTotalAmount) {
		this.primaryTotalAmount = primaryTotalAmount;
	}

	/**
	 * @return the subordinateTotalAmount
	 */
	public BigDecimal getSubordinateTotalAmount() {
		return subordinateTotalAmount;
	}

	/**
	 * @param subordinateTotalAmount the subordinateTotalAmount to set
	 */
	public void setSubordinateTotalAmount(BigDecimal subordinateTotalAmount) {
		this.subordinateTotalAmount = subordinateTotalAmount;
	}

	/**
	 * @return the totalCostAmount
	 */
	public BigDecimal getTotalCostAmount() {
		return totalCostAmount;
	}

	/**
	 * @param totalCostAmount the totalCostAmount to set
	 */
	public void setTotalCostAmount(BigDecimal totalCostAmount) {
		this.totalCostAmount = totalCostAmount;
	}

	/**
	 * @return the aircraftCostAmount
	 */
	public BigDecimal getAircraftCostAmount() {
		return aircraftCostAmount;
	}

	/**
	 * @param aircraftCostAmount the aircraftCostAmount to set
	 */
	public void setAircraftCostAmount(BigDecimal aircraftCostAmount) {
		this.aircraftCostAmount = aircraftCostAmount;
	}

	/**
	 * @return the flightHours
	 */
	public BigDecimal getFlightHours() {
		return flightHours;
	}

	/**
	 * @param flightHours the flightHours to set
	 */
	public void setFlightHours(BigDecimal flightHours) {
		this.flightHours = flightHours;
	}

	/**
	 * @return the numberOfLoads
	 */
	public Integer getNumberOfLoads() {
		return numberOfLoads;
	}

	/**
	 * @param numberOfLoads the numberOfLoads to set
	 */
	public void setNumberOfLoads(Integer numberOfLoads) {
		this.numberOfLoads = numberOfLoads;
	}

	/**
	 * @return the waterGallons
	 */
	public BigDecimal getWaterGallons() {
		return waterGallons;
	}

	/**
	 * @param waterGallons the waterGallons to set
	 */
	public void setWaterGallons(BigDecimal waterGallons) {
		this.waterGallons = waterGallons;
	}

	/**
	 * @return the retardantGallons
	 */
	public BigDecimal getRetardantGallons() {
		return retardantGallons;
	}

	/**
	 * @param retardantGallons the retardantGallons to set
	 */
	public void setRetardantGallons(BigDecimal retardantGallons) {
		this.retardantGallons = retardantGallons;
	}

	/**
	 * @return the cargoPounds
	 */
	public BigDecimal getCargoPounds() {
		return cargoPounds;
	}

	/**
	 * @param cargoPounds the cargoPounds to set
	 */
	public void setCargoPounds(BigDecimal cargoPounds) {
		this.cargoPounds = cargoPounds;
	}

	/**
	 * @return the numberOfTrips
	 */
	public Integer getNumberOfTrips() {
		return numberOfTrips;
	}

	/**
	 * @param numberOfTrips the numberOfTrips to set
	 */
	public void setNumberOfTrips(Integer numberOfTrips) {
		this.numberOfTrips = numberOfTrips;
	}

	/**
	 * @return the numberOfPassengers
	 */
	public Integer getNumberOfPassengers() {
		return numberOfPassengers;
	}

	/**
	 * @param numberOfPassengers the numberOfPassengers to set
	 */
	public void setNumberOfPassengers(Integer numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

}
