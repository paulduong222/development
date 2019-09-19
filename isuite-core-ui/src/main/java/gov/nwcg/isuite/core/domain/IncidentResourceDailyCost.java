package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface IncidentResourceDailyCost extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	public AccrualCode getAccrualCode();

	public void setAccrualCode(AccrualCode accrualCode);

	public Long getAccrualCodeId();

	public void setAccrualCodeId(Long accrualCodeId);

	public IncidentShift getIncidentShift();

	public void setIncidentShift(IncidentShift incidentShift);

	public Long getIncidentShiftId();

	public void setIncidentShiftId(Long incidentShiftId);

	public CostGroup getCostGroup();

	public void setCostGroup(CostGroup costGroup);

	public Long getCostGroupId();

	public void setCostGroupId(Long costGroupId);

	public IncidentAccountCode getIncidentAccountCode();

	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode);

	public Long getIncidentAccountCodeId();

	public void setIncidentAccountCodeId(Long incidentAccountCodeId);

	public IncidentResource getIncidentResource();

	public void setIncidentResource(IncidentResource incidentResource);

	public ResourceCostTypeEnum getResourceCostType();

	public void setResourceCostType(ResourceCostTypeEnum resourceCostType);

	public Date getActivityDate();

	public void setActivityDate(Date activityDate);

	public String getRateType();

	public void setRateType(String rateType);

	public BigDecimal getUnits();

	public void setUnits(BigDecimal units);

	public BigDecimal getUnitCostAmount();

	public void setUnitCostAmount(BigDecimal unitCostAmount);

	public String getCostLevel();

	public void setCostLevel(String costLevel);

	public Boolean getIsLocked();

	public void setIsLocked(Boolean isLocked);

	public Boolean getIsFlowdown();

	public void setIsFlowdown(Boolean isFlowdown);

	public BigDecimal getPrimaryTotalAmount();

	public void setPrimaryTotalAmount(BigDecimal primaryTotalAmount);

	public BigDecimal getSubordinateTotalAmount();

	public void setSubordinateTotalAmount(BigDecimal subordinateTotalAmount);

	public BigDecimal getTotalCostAmount();

	public void setTotalCostAmount(BigDecimal totalCostAmount);

	public BigDecimal getAircraftCostAmount();

	public void setAircraftCostAmount(BigDecimal aircraftCostAmount);

	public BigDecimal getFlightHours();

	public void setFlightHours(BigDecimal flightHours);

	public Integer getNumberOfLoads();

	public void setNumberOfLoads(Integer numberOfLoads);

	public BigDecimal getWaterGallons();

	public void setWaterGallons(BigDecimal waterGallons);

	public BigDecimal getRetardantGallons();

	public void setRetardantGallons(BigDecimal retardantGallons);

	public BigDecimal getCargoPounds();

	public void setCargoPounds(BigDecimal cargoPounds);

	public Integer getNumberOfTrips();

	public void setNumberOfTrips(Integer numberOfTrips);

	public Integer getNumberOfPassengers() ;

	public void setNumberOfPassengers(Integer numberOfPassengers) ;

	public Collection<TimeAssignAdjust> getTimeAssignAdjusts() ;
	
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts) ;

	public Collection<AssignmentTimePost> getAssignmentTimePosts();

	public void setAssignmentTimePosts(
			Collection<AssignmentTimePost> assignmentTimePosts) ;
	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId();

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount() ;

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount);

	/**
	 * @return the incidentResourceOther
	 */
	public IncidentResourceOther getIncidentResourceOther() ;

	/**
	 * @param incidentResourceOther the incidentResourceOther to set
	 */
	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther) ;

	/**
	 * @return the incidentResourceOtherId
	 */
	public Long getIncidentResourceOtherId() ;

	/**
	 * @param incidentResourceOtherId the incidentResourceOtherId to set
	 */
	public void setIncidentResourceOtherId(Long incidentResourceOtherId);

	
}
