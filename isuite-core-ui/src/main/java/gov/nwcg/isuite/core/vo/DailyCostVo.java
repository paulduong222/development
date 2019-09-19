package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.Date;

public class DailyCostVo implements Cloneable{
	private Boolean isDirty=false;
	private Long id = 0L;
    private Long accrualCodeId;
    private Long incidentShiftId;
    private Long costGroupId;
    private Long incidentAccountCodeId;
    private Long incidentResourceId;
    private Long incidentResourceOtherId;
	private String resourceCostType;
	private Date activityDate;
	private String rateType;
	private BigDecimal units=BigDecimal.valueOf(0.0);
	private BigDecimal unitCostAmount=BigDecimal.valueOf(0.0);
	private BigDecimal adjustmentAmount=BigDecimal.valueOf(0.0);
	private String costLevel;
	private Boolean isLocked=false;
	private Boolean isFlowdown=false;
	private BigDecimal primaryTotalAmount=BigDecimal.valueOf(0.0);
	private BigDecimal subordinateTotalAmount=BigDecimal.valueOf(0.0);
	private BigDecimal totalCostAmount=BigDecimal.valueOf(0.0);
	private BigDecimal aircraftCostAmount=BigDecimal.valueOf(0.0);
	private BigDecimal flightHours=BigDecimal.valueOf(0.0);
	private Integer numberOfLoads=Integer.valueOf(0);
	private BigDecimal waterGallons=BigDecimal.valueOf(0.0);
	private BigDecimal retardantGallons=BigDecimal.valueOf(0.0);
	private BigDecimal cargoPounds=BigDecimal.valueOf(0.0);
	private Integer numberOfTrips=Integer.valueOf(0);
	private Integer numberOfPassengers=Integer.valueOf(0);
	
	public DailyCostVo clone() throws CloneNotSupportedException {
		return (DailyCostVo)super.clone();
	}

	public static DailyCostVo getInstance(IncidentResourceDailyCost entity) throws Exception {
		DailyCostVo vo = new DailyCostVo();

		vo.setId(entity.getId());
		vo.setActivityDate(entity.getActivityDate());
		vo.setAircraftCostAmount(entity.getAircraftCostAmount());
		vo.setCargoPounds(entity.getCargoPounds());
		if(null != entity.getCostLevel())
			vo.setCostLevel(entity.getCostLevel().toString());
		else
			vo.setCostLevel("");
		vo.setFlightHours(entity.getFlightHours());
		vo.setIsFlowdown(BooleanUtility.isTrue(entity.getIsFlowdown()) );
		vo.setIsLocked(BooleanUtility.isTrue(entity.getIsLocked()));
		vo.setNumberOfLoads(entity.getNumberOfLoads());
		vo.setNumberOfPassengers(entity.getNumberOfPassengers());
		vo.setNumberOfTrips(entity.getNumberOfTrips());
		vo.setRateType(entity.getRateType());
		vo.setRetardantGallons(entity.getRetardantGallons());
		vo.setUnits(entity.getUnits());
		vo.setUnitCostAmount(entity.getUnitCostAmount());
		vo.setWaterGallons(entity.getWaterGallons());
		vo.setPrimaryTotalAmount(entity.getPrimaryTotalAmount());
		vo.setSubordinateTotalAmount(entity.getSubordinateTotalAmount());
		vo.setTotalCostAmount(entity.getTotalCostAmount());
		vo.setAdjustmentAmount(entity.getAdjustmentAmount());
			
		if(null != entity.getIncidentResource())
			vo.setIncidentResourceId(entity.getIncidentResource().getId());
			
		if(null != entity.getIncidentResourceOther()){
			vo.setIncidentResourceOtherId(entity.getIncidentResourceOther().getId());
		}

		if(null != entity.getAccrualCode())
			vo.setAccrualCodeId(entity.getAccrualCode().getId());

		if(null != entity.getCostGroup())
			vo.setCostGroupId(entity.getCostGroup().getId());
			
		if(null != entity.getIncidentAccountCode()){
			vo.setIncidentAccountCodeId(entity.getIncidentAccountCode().getId());
		}

		if(null != entity.getIncidentShift())
			vo.setIncidentShiftId(entity.getIncidentShift().getId());

		return vo;
	}
	
	public String toSql(Boolean isOracle) throws Exception {
		StringBuffer sql=new StringBuffer();

		if(!LongUtility.hasValue(this.id)){
			Date costDate=DateUtil.addMilitaryTimeToDate(activityDate, "1200");
			
			// create insert sql
			sql.append("insert into isw_inc_res_daily_cost (")
			   .append("id, incident_resource_id,resource_cost_type,activity_date")
			   .append(",incident_account_code_id, cost_group_id, accrual_code_id,incident_shift_id")
			   .append(",rate_type,unit_cost_amount,cost_level,is_locked,is_flowdown")
			   .append(",primary_total_amount, subordinate_total_amount,total_cost_amount,aircraft_cost_amount")
			   .append(",flight_hours,number_of_loads,water_gallons,retardant_gallons,cargo_pounds")
			   .append(",number_of_trips,number_of_passengers")
			   .append(",units,adjustment_amount,incident_resource_other_id")
			   .append(") ")
			   .append("values ( ")
			   .append( (isOracle==true?"SEQ_INC_RES_DAILY_COST.nextVal":"nextVal('SEQ_INC_RES_DAILY_COST')" ))
			   .append(","+this.incidentResourceId)
			   .append(",'"+this.resourceCostType+"'")
			   //.append(",to_date('"+DateUtil.toDateString(this.activityDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY')")
			   .append(", to_timestamp('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') ") 
			   .append(","+this.incidentAccountCodeId)
			   .append(","+(LongUtility.hasValue(this.costGroupId) ? this.costGroupId : null))
			   .append(","+this.accrualCodeId)
			   .append(","+this.incidentShiftId)
			   .append(",'"+this.rateType+"'")
			   .append(","+this.unitCostAmount)
			   .append(",'"+this.costLevel+"'");
				if(isOracle==true)
					sql.append(","+(BooleanUtility.isTrue(this.isLocked)?1:0));
				else
					sql.append(","+(BooleanUtility.isTrue(this.isLocked)?true:false));
				if(isOracle==true)
					sql.append(","+(BooleanUtility.isTrue(this.isFlowdown)?1:0));
				else
					sql.append(","+(BooleanUtility.isTrue(this.isFlowdown)?true:false));
			   sql.append(","+this.primaryTotalAmount)
			   .append(","+this.subordinateTotalAmount)
			   .append(","+this.totalCostAmount)
			   .append(","+this.aircraftCostAmount)
			   .append(","+this.flightHours)
			   .append(","+this.numberOfLoads)
			   .append(","+this.waterGallons)
			   .append(","+this.retardantGallons)
			   .append(","+this.cargoPounds)
			   .append(","+this.numberOfTrips)
			   .append(","+this.numberOfPassengers)
			   .append(","+this.units)
			   .append(","+this.adjustmentAmount)
			   .append(","+this.incidentResourceOtherId)
			   .append(") ");
		}else{
			// create update sql
			sql.append("update isw_inc_res_daily_cost ")
			   .append(" set incident_account_code_id = " + this.incidentAccountCodeId + " ")
			   .append(" , accrual_code_id = " + this.accrualCodeId + " ")
			   .append(" , cost_group_id = " + (LongUtility.hasValue(this.costGroupId) ? this.costGroupId : null) + " ")
			   .append(" , incident_shift_id = " + this.incidentShiftId + " ")
			   .append(" , rate_type = '" + (StringUtility.hasValue(this.rateType) ? this.rateType : "") + "' ")
			   .append(" , unit_cost_amount = " + this.unitCostAmount + " ")
			   .append(" , aircraft_cost_amount = " + this.aircraftCostAmount + " ")
			   .append(" , flight_hours = " + this.flightHours + " ")
			   .append(" , number_of_loads = " + this.numberOfLoads + " ")
			   .append(" , water_gallons = " + this.waterGallons + " ")
			   .append(" , retardant_gallons = " + this.retardantGallons  + " ")
			   .append(" , cargo_pounds = " + this.cargoPounds + " ")
			   .append(" , number_of_trips = " + this.numberOfTrips + " ")
			   .append(" , number_of_passengers = " + this.numberOfPassengers + " ")
			   .append(" , cost_level = '" + (StringUtility.hasValue(this.costLevel) ? this.costLevel : "") + "' ");
				if(isOracle==true)
					sql.append(", is_locked="+(BooleanUtility.isTrue(this.isLocked) ? 1 : 0)+" ");
				else
					sql.append(", is_locked="+(BooleanUtility.isTrue(this.isLocked) ? true : false)+" ");
			   sql.append(" , primary_total_amount = " + this.primaryTotalAmount + " ")
			   .append(",  subordinate_total_amount = " + this.subordinateTotalAmount + " ")
			   .append(",  total_cost_amount = " + this.totalCostAmount + " ")
			   .append(",  units = " + this.units + " ")
			   .append(",  adjustment_amount = " + this.adjustmentAmount + " ")
			   .append("where id = " + this.id);
		}
		
		return sql.toString();
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
	/**
	 * @return the isDirty
	 */
	public Boolean getIsDirty() {
		return isDirty;
	}
	/**
	 * @param isDirty the isDirty to set
	 */
	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}


}
