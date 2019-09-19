package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_INC_RES_DAILY_COST", sequenceName="SEQ_INC_RES_DAILY_COST")
@Table(name="isw_inc_res_daily_cost")
public class IncidentResourceDailyCostImpl extends PersistableImpl implements IncidentResourceDailyCost {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INC_RES_DAILY_COST")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=AccrualCodeImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCRUAL_CODE_ID",insertable=true,updatable=true,nullable=true)
	private AccrualCode accrualCode;

    @Column(name="ACCRUAL_CODE_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long accrualCodeId;
	
	@ManyToOne(targetEntity=IncidentShiftImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_SHIFT_ID",insertable=true,updatable=true,nullable=true)
	private IncidentShift incidentShift;

    @Column(name="INCIDENT_SHIFT_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long incidentShiftId;
	
	@ManyToOne(targetEntity=CostGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "COST_GROUP_ID",insertable=true,updatable=true,nullable=true)
	private CostGroup costGroup;

    @Column(name="COST_GROUP_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long costGroupId;
	
	@ManyToOne(targetEntity=IncidentAccountCodeImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ACCOUNT_CODE_ID",insertable=true,updatable=true,nullable=true)
	private IncidentAccountCode incidentAccountCode;

    @Column(name="INCIDENT_ACCOUNT_CODE_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long incidentAccountCodeId;
	
	@ManyToOne(targetEntity=IncidentResourceImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", insertable=true,updatable=true)
	private IncidentResource incidentResource;

    @Column(name="INCIDENT_RESOURCE_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long incidentResourceId;
	
	@ManyToOne(targetEntity=IncidentResourceOtherImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_OTHER_ID", insertable=true,updatable=true)
	private IncidentResourceOther incidentResourceOther;

    @Column(name="INCIDENT_RESOURCE_OTHER_ID", insertable=false,updatable=false,nullable=true, unique=false)
    private Long incidentResourceOtherId;

    @Enumerated(EnumType.STRING)
	@Column(name = "RESOURCE_COST_TYPE", length = 20)
	private ResourceCostTypeEnum resourceCostType;
	
	@Column(name = "ACTIVITY_DATE", length = 11)
	private Date activityDate;
	
	@Column(name = "RATE_TYPE", length = 40)
	private String rateType;
	
	@Column(name = "UNITS", precision = 22)
	private BigDecimal units;
	
	@Column(name = "UNIT_COST_AMOUNT", precision = 22)
	private BigDecimal unitCostAmount;

	@Column(name = "ADJUSTMENT_AMOUNT", precision = 22)
	private BigDecimal adjustmentAmount;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "COST_LEVEL", length = 10)
	//private CostLevelEnum costLevel;
	private String costLevel;
	
	@Column(name = "IS_LOCKED")
	private Boolean isLocked=false;
	
	@Column(name = "IS_FLOWDOWN")
	private Boolean isFlowdown=false;
	
	@Column(name = "PRIMARY_TOTAL_AMOUNT", precision = 22)
	private BigDecimal primaryTotalAmount;
	
	@Column(name = "SUBORDINATE_TOTAL_AMOUNT", precision = 22)
	private BigDecimal subordinateTotalAmount;
	
	@Column(name = "TOTAL_COST_AMOUNT", precision = 22)
	private BigDecimal totalCostAmount;
	
	@Column(name = "AIRCRAFT_COST_AMOUNT", precision = 22)
	private BigDecimal aircraftCostAmount;
	
	@Column(name = "FLIGHT_HOURS", precision = 22)
	private BigDecimal flightHours;
	
	@Column(name = "NUMBER_OF_LOADS", precision = 4, scale = 0)
	private Integer numberOfLoads;
	
	@Column(name = "WATER_GALLONS", precision = 22)
	private BigDecimal waterGallons;
	
	@Column(name = "RETARDANT_GALLONS", precision = 22)
	private BigDecimal retardantGallons;
	
	@Column(name = "CARGO_POUNDS", precision = 22)
	private BigDecimal cargoPounds;
	
	@Column(name = "NUMBER_OF_TRIPS", precision = 4, scale = 0)
	private Integer numberOfTrips;
	
	@Column(name = "NUMBER_OF_PASSENGERS", precision = 4, scale = 0)
	private Integer numberOfPassengers;
	
	@OneToMany(targetEntity=TimeAssignAdjustImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResourceDailyCost")
	private Collection<TimeAssignAdjust> timeAssignAdjusts = new ArrayList<TimeAssignAdjust>();
	
	@OneToMany(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentResourceDailyCost")
	private Collection<AssignmentTimePost> assignmentTimePosts = new ArrayList<AssignmentTimePost>();
	
	public IncidentResourceDailyCostImpl(){
		
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

	public AccrualCode getAccrualCode() {
		return accrualCode;
	}

	public void setAccrualCode(AccrualCode accrualCode) {
		this.accrualCode = accrualCode;
	}

	public Long getAccrualCodeId() {
		return accrualCodeId;
	}

	public void setAccrualCodeId(Long accrualCodeId) {
		this.accrualCodeId = accrualCodeId;
	}

	public IncidentShift getIncidentShift() {
		return incidentShift;
	}

	public void setIncidentShift(IncidentShift incidentShift) {
		this.incidentShift = incidentShift;
	}

	public Long getIncidentShiftId() {
		return incidentShiftId;
	}

	public void setIncidentShiftId(Long incidentShiftId) {
		this.incidentShiftId = incidentShiftId;
	}

	public CostGroup getCostGroup() {
		return costGroup;
	}

	public void setCostGroup(CostGroup costGroup) {
		this.costGroup = costGroup;
	}

	public Long getCostGroupId() {
		return costGroupId;
	}

	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}

	public IncidentAccountCode getIncidentAccountCode() {
		return incidentAccountCode;
	}

	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) {
		this.incidentAccountCode = incidentAccountCode;
	}

	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}

	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}

	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	public ResourceCostTypeEnum getResourceCostType() {
		return resourceCostType;
	}

	public void setResourceCostType(ResourceCostTypeEnum resourceCostType) {
		this.resourceCostType = resourceCostType;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getUnitCostAmount() {
		return unitCostAmount;
	}

	public void setUnitCostAmount(BigDecimal unitCostAmount) {
		this.unitCostAmount = unitCostAmount;
	}

	public String getCostLevel() {
		return costLevel;
	}

	public void setCostLevel(String costLevel) {
		this.costLevel = costLevel;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getIsFlowdown() {
		return isFlowdown;
	}

	public void setIsFlowdown(Boolean isFlowdown) {
		this.isFlowdown = isFlowdown;
	}

	public BigDecimal getPrimaryTotalAmount() {
		return primaryTotalAmount;
	}

	public void setPrimaryTotalAmount(BigDecimal primaryTotalAmount) {
		this.primaryTotalAmount = primaryTotalAmount;
	}

	public BigDecimal getSubordinateTotalAmount() {
		return subordinateTotalAmount;
	}

	public void setSubordinateTotalAmount(BigDecimal subordinateTotalAmount) {
		this.subordinateTotalAmount = subordinateTotalAmount;
	}

	public BigDecimal getTotalCostAmount() {
		return totalCostAmount;
	}

	public void setTotalCostAmount(BigDecimal totalCostAmount) {
		this.totalCostAmount = totalCostAmount;
	}

	public BigDecimal getAircraftCostAmount() {
		return aircraftCostAmount;
	}

	public void setAircraftCostAmount(BigDecimal aircraftCostAmount) {
		this.aircraftCostAmount = aircraftCostAmount;
	}

	public BigDecimal getFlightHours() {
		return flightHours;
	}

	public void setFlightHours(BigDecimal flightHours) {
		this.flightHours = flightHours;
	}

	public Integer getNumberOfLoads() {
		return numberOfLoads;
	}

	public void setNumberOfLoads(Integer numberOfLoads) {
		this.numberOfLoads = numberOfLoads;
	}

	public BigDecimal getWaterGallons() {
		return waterGallons;
	}

	public void setWaterGallons(BigDecimal waterGallons) {
		this.waterGallons = waterGallons;
	}

	public BigDecimal getRetardantGallons() {
		return retardantGallons;
	}

	public void setRetardantGallons(BigDecimal retardantGallons) {
		this.retardantGallons = retardantGallons;
	}

	public BigDecimal getCargoPounds() {
		return cargoPounds;
	}

	public void setCargoPounds(BigDecimal cargoPounds) {
		this.cargoPounds = cargoPounds;
	}

	public Integer getNumberOfTrips() {
		return numberOfTrips;
	}

	public void setNumberOfTrips(Integer numberOfTrips) {
		this.numberOfTrips = numberOfTrips;
	}

	public Integer getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(Integer numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public Collection<TimeAssignAdjust> getTimeAssignAdjusts() {
		return timeAssignAdjusts;
	}

	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts) {
		this.timeAssignAdjusts = timeAssignAdjusts;
	}

	public Collection<AssignmentTimePost> getAssignmentTimePosts() {
		return assignmentTimePosts;
	}

	public void setAssignmentTimePosts(
			Collection<AssignmentTimePost> assignmentTimePosts) {
		this.assignmentTimePosts = assignmentTimePosts;
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
	 * @return the incidentResourceOther
	 */
	public IncidentResourceOther getIncidentResourceOther() {
		return incidentResourceOther;
	}

	/**
	 * @param incidentResourceOther the incidentResourceOther to set
	 */
	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther) {
		this.incidentResourceOther = incidentResourceOther;
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

	
	
}
