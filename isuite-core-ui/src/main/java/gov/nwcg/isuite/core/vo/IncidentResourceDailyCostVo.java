package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BigDecimalUtility;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

public class IncidentResourceDailyCostVo extends AbstractVo implements PersistableVo {
	private Collection<IncidentResourceDailyCostVo> children = new ArrayList<IncidentResourceDailyCostVo>();
	private Boolean isChild=false;
	private Boolean isSingle=false;
	private Long parentRecordId=0L;
	
	private AccrualCodeVo accrualCodeVo;
	private IncidentShiftVo incidentShiftVo;
	private CostGroupVo costGroupVo;
	private IncidentAccountCodeVo incidentAccountCodeVo;
	private IncidentResourceVo incidentResourceVo;
	private IncidentResourceOtherVo incidentResourceOtherVo;
	private ResourceCostTypeEnum resourceCostType;
	//private Date activityDate;
	private DateTransferVo activityDateVo=new DateTransferVo();
	private String rateType;
	private BigDecimal units;
	private BigDecimal unitCostAmount;
	private String costLevel;
	private Boolean isLocked=false;
	private Boolean isFlowdown=false;
	private BigDecimal adjustmentTotalAmount;
	private BigDecimal primaryTotalAmount;
	private BigDecimal subordinateTotalAmount;
	private BigDecimal totalCostAmount;
	private BigDecimal aircraftCostAmount;
	private BigDecimal flightHours;
	private Integer numberOfLoads;
	private BigDecimal waterGallons;
	private BigDecimal retardantGallons;
	private BigDecimal cargoPounds;
	private Integer numberOfTrips;
	private Integer numberOfPassengers;
	private Collection<TimeAssignAdjustVo> timeAssignAdjustVos = new ArrayList<TimeAssignAdjustVo>();
	private Collection<AssignmentTimePostVo> assignmentTimePostVos = new ArrayList<AssignmentTimePostVo>();

	/**
	 * Constructor
	 */
	public IncidentResourceDailyCostVo() {
		super();
	}

	public static Collection<IncidentResourceDailyCostVo> getHierarchicalInstances(Collection<IncidentResourceDailyCostVo> vos) throws Exception {
		Collection<IncidentResourceDailyCostVo> newvos = new ArrayList<IncidentResourceDailyCostVo>();
		Hashtable hashDates = new Hashtable();
		Collection<Date> costDates = new ArrayList<Date>();
		
		for(IncidentResourceDailyCostVo vo : vos) {
			String dt="";
			Date cDate=null;
			if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
				cDate=DateTransferVo.getTransferDate(vo.getActivityDateVo());
				dt=DateUtil.toDateString(cDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);
				cDate=DateUtil.addMilitaryTimeToDate(cDate, "1200");
			}
			if(!hashDates.containsKey(dt)){
				hashDates.put(dt, cDate);
				costDates.add(cDate);
			}
		}
		
		int pid=0;
		
		for(Date dt : costDates){
			int cnt = 0;
			DateTransferVo dtVo = new DateTransferVo();
			dtVo.setDateString(DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			dtVo.setTimeString("1200");
			for(IncidentResourceDailyCostVo vo : vos){
				Date cDate=null;
				if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
					cDate=DateTransferVo.getTransferDate(vo.getActivityDateVo());
					cDate=DateUtil.addMilitaryTimeToDate(cDate, "1200");
				}
				if(DateUtil.isSameDate(dt, cDate)){
					cnt+=1;
				}
			}
			if(cnt == 1){
				for(IncidentResourceDailyCostVo vo : vos){
					Date cDate=null;
					if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
						cDate=DateTransferVo.getTransferDate(vo.getActivityDateVo());
						cDate=DateUtil.addMilitaryTimeToDate(cDate, "1200");
					}
					if(DateUtil.isSameDate(dt, cDate)){
						vo.setIsSingle(true);
						newvos.add(vo);
						break;
					}
				}
			}else if(cnt > 1){
				IncidentResourceDailyCostVo parentVo = new IncidentResourceDailyCostVo();
				parentVo.setIsSingle(false);
				parentVo.setActivityDateVo(dtVo);
				BigDecimal primTotal = new BigDecimal(0.0);
				BigDecimal subTotal = new BigDecimal(0.0);
				BigDecimal grandTotal = new BigDecimal(0.0);
				
				for(IncidentResourceDailyCostVo vo : vos){
					Date cDate=null;
					if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
						cDate=DateTransferVo.getTransferDate(vo.getActivityDateVo());
						cDate=DateUtil.addMilitaryTimeToDate(cDate, "1200");
					}
					if(DateUtil.isSameDate(dt, cDate)){
						//vo.setActivityDate(null);
						vo.setParentRecordId(Long.getLong(String.valueOf(pid)));
						vo.setIsChild(true);
						vo.setIsSingle(true);
						parentVo.getChildren().add(vo);
						primTotal = primTotal.add((DecimalUtil.hasValue(vo.getPrimaryTotalAmount()) ? vo.getPrimaryTotalAmount() : BigDecimal.valueOf(0.0)));
						subTotal = subTotal.add((DecimalUtil.hasValue(vo.getSubordinateTotalAmount()) ? vo.getSubordinateTotalAmount() : BigDecimal.valueOf(0.0)));
					}
				}
				
				grandTotal = grandTotal.add(primTotal).add(subTotal);
				parentVo.setPrimaryTotalAmount(primTotal);
				parentVo.setSubordinateTotalAmount(subTotal);
				parentVo.setTotalCostAmount(grandTotal);
				newvos.add(parentVo);
			}
		}

		return newvos;
	}
	
	public static Boolean isCostDataDifferent(IncidentResourceDailyCostVo vo, IncidentResourceDailyCost entity) {
		// check unitcost
		Double voUnitCost=(BigDecimalUtility.hasValue(vo.getUnitCostAmount()) ? vo.getUnitCostAmount().doubleValue() : 0.0);
		Double entityUnitCost=(BigDecimalUtility.hasValue(entity.getUnitCostAmount()) ? entity.getUnitCostAmount().doubleValue() : 0.0);
		if(voUnitCost.compareTo(entityUnitCost)!=0)
			return true;

		// check units
		Double voUnits=(BigDecimalUtility.hasValue(vo.getUnits()) ? vo.getUnits().doubleValue() : 0.0);
		Double entityUnits=(BigDecimalUtility.hasValue(entity.getUnits()) ? entity.getUnits().doubleValue() : 0.0);
		if(voUnits.compareTo(entityUnits)!=0)
			return true;
		
		// check account code
		if(vo.getIncidentAccountCodeVo().getId().compareTo(entity.getIncidentAccountCodeId())!=0)
			return true;
		
		// check uom
		if(!vo.getRateType().equals(entity.getRateType()))
			return true;
		
		// check adjustment
		Double voAdj=(BigDecimalUtility.hasValue(vo.getAdjustmentTotalAmount()) ? vo.getAdjustmentTotalAmount().doubleValue() : 0.0);
		Double entityAdj=(BigDecimalUtility.hasValue(entity.getAdjustmentAmount()) ? entity.getAdjustmentAmount().doubleValue() : 0.0);
		if(voAdj.compareTo(entityAdj)!=0)
			return true;

		// check aircraft cost amount
		Double voAirCost=(BigDecimalUtility.hasValue(vo.getAircraftCostAmount()) ? vo.getAircraftCostAmount().doubleValue() : 0.0);
		Double entityAirCost=(BigDecimalUtility.hasValue(entity.getAircraftCostAmount()) ? entity.getAircraftCostAmount().doubleValue() : 0.0);
		if(voAirCost.compareTo(entityAirCost)!=0)
			return true;
		
		return false;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IncidentResourceDailyCostVo getInstance(IncidentResourceDailyCost entity, boolean cascadable) throws Exception {
		IncidentResourceDailyCostVo vo = new IncidentResourceDailyCostVo();

		if(null == entity)
			throw new Exception("Unable to build IncidentResourceDailyCostVo instance from null IncidentResourceDailyCost entity");

		vo.setId(entity.getId());
		
		if(cascadable){
			if(DateUtil.hasValue(entity.getActivityDate()))
				DateTransferVo.populateDate(vo.getActivityDateVo(), entity.getActivityDate());
			//vo.setActivityDate(entity.getActivityDate());
			vo.setAircraftCostAmount(entity.getAircraftCostAmount());
			vo.setCargoPounds(entity.getCargoPounds());
			vo.setCostLevel(entity.getCostLevel());
			vo.setFlightHours(entity.getFlightHours());
			vo.setIsFlowdown(BooleanUtility.isTrue(entity.getIsFlowdown()) );
			vo.setIsLocked(BooleanUtility.isTrue(entity.getIsLocked()));
			vo.setNumberOfLoads(entity.getNumberOfLoads());
			vo.setNumberOfPassengers(entity.getNumberOfPassengers());
			vo.setNumberOfTrips(entity.getNumberOfTrips());
			vo.setRateType(entity.getRateType());
			vo.setResourceCostType(entity.getResourceCostType());
			vo.setRetardantGallons(entity.getRetardantGallons());
			vo.setUnits(entity.getUnits());
			vo.setUnitCostAmount(entity.getUnitCostAmount());
			vo.setWaterGallons(entity.getWaterGallons());
			vo.setPrimaryTotalAmount(entity.getPrimaryTotalAmount());
			vo.setSubordinateTotalAmount(entity.getSubordinateTotalAmount());
			vo.setTotalCostAmount(entity.getTotalCostAmount());
			vo.setAdjustmentTotalAmount(entity.getAdjustmentAmount());
			
			if(null != entity.getIncidentResource())
				vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity.getIncidentResource(), false));
			
			if(null != entity.getIncidentResourceOther()){
				vo.setIncidentResourceOtherVo(IncidentResourceOtherVo.getInstance(entity.getIncidentResourceOther(), false));
			}
			if(null != entity.getAccrualCode())
				vo.setAccrualCodeVo(AccrualCodeVo.getInstance(entity.getAccrualCode(), true));

			if(null != entity.getCostGroup())
				vo.setCostGroupVo(CostGroupVo.getInstance(entity.getCostGroup(), true));
			
			if(null != entity.getIncidentAccountCode()){
				vo.setIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(entity.getIncidentAccountCode(), true));
			}

			if(null != entity.getIncidentShift())
				vo.setIncidentShiftVo(IncidentShiftVo.getInstance(entity.getIncidentShift(), true));

		}

		return vo;
	}

	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IncidentResourceDailyCostVo> getInstances(Collection<IncidentResourceDailyCost> entities, boolean cascadable) throws Exception {
		Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();

		for(IncidentResourceDailyCost entity : entities){
			vos.add(IncidentResourceDailyCostVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * @param entity
	 * @param sourceVo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IncidentResourceDailyCost toEntity(IncidentResourceDailyCost entity,IncidentResourceDailyCostVo vo,boolean cascadable) throws Exception {
		if(null ==entity) entity = new IncidentResourceDailyCostImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getActivityDateVo());
				dt=DateUtil.addMilitaryTimeToDate(dt, "1200");
				entity.setActivityDate(dt);
			}
			//entity.setActivityDate(vo.getActivityDate());
			entity.setAircraftCostAmount(vo.getAircraftCostAmount());
			entity.setCargoPounds(vo.getCargoPounds());
			entity.setCostLevel(vo.getCostLevel());
			entity.setFlightHours(vo.getFlightHours());
			entity.setIsFlowdown(BooleanUtility.isTrue(vo.getIsFlowdown()) );
			entity.setIsLocked(BooleanUtility.isTrue(vo.getIsLocked()));
			entity.setNumberOfLoads(vo.getNumberOfLoads());
			entity.setNumberOfPassengers(vo.getNumberOfPassengers());
			entity.setNumberOfTrips(vo.getNumberOfTrips());
			entity.setResourceCostType(vo.getResourceCostType());
			entity.setRetardantGallons(vo.getRetardantGallons());
			entity.setRateType(vo.getRateType());
			entity.setUnits(vo.getUnits());
			entity.setUnitCostAmount(vo.getUnitCostAmount());
			entity.setWaterGallons(vo.getWaterGallons());

			if(vo.getResourceCostType()==ResourceCostTypeEnum.AIRCRAFT){
				entity.setUnits(BigDecimal.valueOf(1));
				entity.setRateType("DAILY");
				BigDecimal amt = vo.getAircraftCostAmount();
				entity.setUnitCostAmount(amt);
				entity.setAdjustmentAmount(vo.getAdjustmentTotalAmount());
				BigDecimal adj=vo.getAdjustmentTotalAmount();
				amt=amt.add(adj);
				entity.setPrimaryTotalAmount(amt);
				entity.setSubordinateTotalAmount(vo.getSubordinateTotalAmount());
				entity.setTotalCostAmount(vo.getSubordinateTotalAmount().add(amt));
			}else{
				BigDecimal amt = vo.getUnitCostAmount().multiply(vo.getUnits());
				entity.setAdjustmentAmount(vo.getAdjustmentTotalAmount());
				BigDecimal adj=vo.getAdjustmentTotalAmount();
				amt=amt.add(adj);
				entity.setPrimaryTotalAmount(amt);
				entity.setSubordinateTotalAmount(vo.getSubordinateTotalAmount());
				entity.setTotalCostAmount(vo.getSubordinateTotalAmount().add(amt));
			}
			
			if(null != vo.getIncidentResourceVo()){
				IncidentResource irEntity = new IncidentResourceImpl();
				irEntity.setId(vo.getIncidentResourceVo().getId());
				entity.setIncidentResource(irEntity);
				entity.setIncidentResourceId(vo.getIncidentResourceVo().getId());
			}

			if(null != vo.getIncidentResourceOtherVo()){
				IncidentResourceOther iroEntity = new IncidentResourceOtherImpl();
				iroEntity.setId(vo.getIncidentResourceOtherVo().getId());
				entity.setIncidentResourceOther(iroEntity);
				entity.setIncidentResourceOtherId(vo.getIncidentResourceOtherVo().getId());
			}
			
			if(null != vo.getAccrualCodeVo())
				entity.setAccrualCode(AccrualCodeVo.toEntity(vo.accrualCodeVo, false));

			if(null != vo.getCostGroupVo())
				entity.setCostGroup(CostGroupVo.toEntity(null, vo.getCostGroupVo(), false));
			else
				entity.setCostGroup(null);
			
			if(null != vo.getIncidentAccountCodeVo())
				entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(), false));

			if(null != vo.getIncidentShiftVo())
				entity.setIncidentShift(IncidentShiftVo.toEntity(null, vo.getIncidentShiftVo(), false));
		}
		
		return entity;
	}
	
	public AccrualCodeVo getAccrualCodeVo() {
		return accrualCodeVo;
	}

	public void setAccrualCodeVo(AccrualCodeVo accrualCodeVo) {
		this.accrualCodeVo = accrualCodeVo;
	}

	public IncidentShiftVo getIncidentShiftVo() {
		return incidentShiftVo;
	}

	public void setIncidentShiftVo(IncidentShiftVo incidentShiftVo) {
		this.incidentShiftVo = incidentShiftVo;
	}

	public CostGroupVo getCostGroupVo() {
		return costGroupVo;
	}

	public void setCostGroupVo(CostGroupVo costGroupVo) {
		this.costGroupVo = costGroupVo;
	}

	public IncidentAccountCodeVo getIncidentAccountCodeVo() {
		return incidentAccountCodeVo;
	}

	public void setIncidentAccountCodeVo(IncidentAccountCodeVo incidentAccountCodeVo) {
		this.incidentAccountCodeVo = incidentAccountCodeVo;
	}

	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}

	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
	}

	public ResourceCostTypeEnum getResourceCostType() {
		return resourceCostType;
	}

	public void setResourceCostType(ResourceCostTypeEnum resourceCostType) {
		this.resourceCostType = resourceCostType;
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

	public Collection<TimeAssignAdjustVo> getTimeAssignAdjustVos() {
		return timeAssignAdjustVos;
	}

	public void setTimeAssignAdjustVos(
			Collection<TimeAssignAdjustVo> timeAssignAdjustVos) {
		this.timeAssignAdjustVos = timeAssignAdjustVos;
	}

	public Collection<AssignmentTimePostVo> getAssignmentTimePostVos() {
		return assignmentTimePostVos;
	}

	public void setAssignmentTimePostVos(
			Collection<AssignmentTimePostVo> assignmentTimePostVos) {
		this.assignmentTimePostVos = assignmentTimePostVos;
	}

	/**
	 * @return the adjustmentTotalAmount
	 */
	public BigDecimal getAdjustmentTotalAmount() {
		return adjustmentTotalAmount;
	}

	/**
	 * @param adjustmentTotalAmount the adjustmentTotalAmount to set
	 */
	public void setAdjustmentTotalAmount(BigDecimal adjustmentTotalAmount) {
		this.adjustmentTotalAmount = adjustmentTotalAmount;
	}

	/**
	 * @return the incidentResourceOtherVo
	 */
	public IncidentResourceOtherVo getIncidentResourceOtherVo() {
		return incidentResourceOtherVo;
	}

	/**
	 * @param incidentResourceOtherVo the incidentResourceOtherVo to set
	 */
	public void setIncidentResourceOtherVo(
			IncidentResourceOtherVo incidentResourceOtherVo) {
		this.incidentResourceOtherVo = incidentResourceOtherVo;
	}

	/**
	 * @return the children
	 */
	public Collection<IncidentResourceDailyCostVo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<IncidentResourceDailyCostVo> children) {
		this.children = children;
	}

	/**
	 * @return the isChild
	 */
	public Boolean getIsChild() {
		return isChild;
	}

	/**
	 * @param isChild the isChild to set
	 */
	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}

	/**
	 * @return the isSingle
	 */
	public Boolean getIsSingle() {
		return isSingle;
	}

	/**
	 * @param isSingle the isSingle to set
	 */
	public void setIsSingle(Boolean isSingle) {
		this.isSingle = isSingle;
	}

	/**
	 * @return the parentRecordId
	 */
	public Long getParentRecordId() {
		return parentRecordId;
	}

	/**
	 * @param parentRecordId the parentRecordId to set
	 */
	public void setParentRecordId(Long parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	/**
	 * @return the activityDateVo
	 */
	public DateTransferVo getActivityDateVo() {
		return activityDateVo;
	}

	/**
	 * @param activityDateVo the activityDateVo to set
	 */
	public void setActivityDateVo(DateTransferVo activityDateVo) {
		this.activityDateVo = activityDateVo;
	}



}
