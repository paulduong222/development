package gov.nwcg.isuite.core.cost.operations.calculator;

import gov.nwcg.isuite.core.cost.data.DefaultCostRateData;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.math.BigDecimal;
import java.util.Date;

public class EstimateCalculator {
	public IncidentResource irEntity=null;
	public IncidentResourceOther iroEntity=null;
	public IncidentResourceVo irVo=null;
	public ResourceCostTypeEnum resourceCostType=null;
	
	// Default Rate 
	public DefaultCostRateData defaultCostRateData=null;

	public Boolean isZeroEstimate=false;
	
	/**
	 * Creates and returns an estimate for the date specified.
	 * 
	 * @param type
	 * @param date
	 * @param irEntity
	 * @return
	 */
	public IncidentResourceDailyCost generateEstimate(IncidentResourceDailyCost dailyCost, Date date){
		
		/*
		 *  e-ISuite Manage Costs - Daily Cost Use Case.pdf
		 *  Page: 12
		 *  The system must allow a user to identify that a Resource is 
		 *  intermittent at the Incident and only needs costs generated 
		 *  for those times when the Resource has actual time postings 
		 *  (i.e., Resources that will not be there for the duration 
		 *  of their mobilization period, but will be there periodically.) 
		 *  
		 *  NOTE: This will be accomplished by having a Use Actuals Only option.
		*   NOTE: getUseAccrualOnly() is really getUseActualsOnly
		 */
		if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
			if(BooleanUtility.isTrue(iroEntity.getCostData().getUseAccrualsOnly()))
				return dailyCost;
		}else{
			if(BooleanUtility.isTrue(irEntity.getCostData().getUseAccrualsOnly()))
				return null; // no estimates, only actuals
		}
		
		// init the estimate daily cost
		if(null==dailyCost)
			dailyCost=new IncidentResourceDailyCostImpl();
		
		dailyCost.setActivityDate(date);
		
		if(this.isZeroEstimate==true)
			dailyCost.setCostLevel(null);
		else
			dailyCost.setCostLevel("E");
			
		if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER)
			dailyCost.setIncidentResourceOther(iroEntity);
		else
			dailyCost.setIncidentResource(irEntity);

		//TODO: if irEntity has a parent, create default based on parent estimate/actual?

		/*
		 * Create cost estimate
		 *  - use resources default assigned accounting code if avail
	     *  - use resources default accrual code if avail
	     *  
	     *  NOTE: should already be set?
		// not sure what the rules are anymore, see defect 3330?
		 */
		//if(null==dailyCost.getIncidentAccountCode()){
		//	if(null != irEntity.getWorkPeriod().getDefIncidentAccountCode())
		//		dailyCost.setIncidentAccountCode(irEntity.getWorkPeriod().getDefIncidentAccountCode());
		//}
		
		if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
			if(null != iroEntity.getCostData() 
					&& null != iroEntity.getCostData().getAccrualCode()){
				dailyCost.setAccrualCode(iroEntity.getCostData().getAccrualCode());
			}
		}else{
			if(null != irEntity.getCostData() 
					&& null != irEntity.getCostData().getAccrualCode()){
				dailyCost.setAccrualCode(irEntity.getCostData().getAccrualCode());
			}
		}
		
		if(this.resourceCostType==ResourceCostTypeEnum.AIRCRAFT){
			initAircraftEstimate(dailyCost,irEntity);
		}else if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE){
			initResourceEstimate(dailyCost,irEntity);
		}else if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
			initResourceOtherEstimate(dailyCost,iroEntity);
		}
		
		if(null != this.defaultCostRateData.costGroup){
			dailyCost.setCostGroup(this.defaultCostRateData.costGroup);
		}
		
		if(null != this.defaultCostRateData.incidentShift){
			dailyCost.setIncidentShift(this.defaultCostRateData.incidentShift);
		}
		
		return dailyCost;
	}
											
	/**
	 * @param dailyCost
	 */
	private void initAircraftEstimate(IncidentResourceDailyCost dailyCost,IncidentResource irEntity) {
		dailyCost.setResourceCostType(ResourceCostTypeEnum.AIRCRAFT);
		dailyCost.setIsFlowdown(false);
		dailyCost.setIsLocked(false);
		dailyCost.setCargoPounds(BigDecimal.valueOf(0.0));
		dailyCost.setFlightHours(BigDecimal.valueOf(0.0));
		dailyCost.setNumberOfLoads(Integer.valueOf(0));
		dailyCost.setNumberOfPassengers(Integer.valueOf(0));
		dailyCost.setNumberOfTrips(Integer.valueOf(0));
		dailyCost.setRetardantGallons(BigDecimal.valueOf(0.0));
		dailyCost.setWaterGallons(BigDecimal.valueOf(0.0));

		
		dailyCost.setRateType(this.defaultCostRateData.estimateRateType);
		//dailyCost.setUnits(new BigDecimal(this.defaultCostRateData.units.doubleValue()));
		dailyCost.setUnits(new BigDecimal(0.0));
		dailyCost.setUnitCostAmount(this.defaultCostRateData.estimateRateAmount);
		
		BigDecimal total = defaultCostRateData.estimateRateAmount.multiply(BigDecimal.valueOf(defaultCostRateData.estimateUnits));
		
		dailyCost.setAircraftCostAmount(total);
		dailyCost.setPrimaryTotalAmount(total);
		dailyCost.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
		
		total.add(dailyCost.getSubordinateTotalAmount());
		
		dailyCost.setTotalCostAmount(total);
		
	}

	private void initResourceEstimate(IncidentResourceDailyCost dailyCost,IncidentResource irEntity) {
		dailyCost.setResourceCostType(ResourceCostTypeEnum.RESOURCE);
		dailyCost.setIsFlowdown(false);
		dailyCost.setIsLocked(false);
		dailyCost.setAircraftCostAmount(BigDecimal.valueOf(0.0));
		
		if(null != irEntity.getCostData() 
				&& BooleanUtility.isFalse(this.isZeroEstimate)
				&& BooleanUtility.isTrue(irEntity.getCostData().getGenerateCosts())){
			dailyCost.setUnits(new BigDecimal(this.defaultCostRateData.estimateUnits.doubleValue()) );
			dailyCost.setUnitCostAmount(this.defaultCostRateData.estimateRateAmount);
			dailyCost.setRateType(this.defaultCostRateData.estimateRateType);
			
			BigDecimal total = defaultCostRateData.estimateRateAmount.multiply(BigDecimal.valueOf(defaultCostRateData.estimateUnits));
			dailyCost.setPrimaryTotalAmount(total);
			dailyCost.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
			total.add(dailyCost.getSubordinateTotalAmount());
			
			dailyCost.setTotalCostAmount(total);
		}else{
			dailyCost.setUnits(new BigDecimal(0.0));
			dailyCost.setUnitCostAmount(new BigDecimal(0.0));
			dailyCost.setRateType(this.defaultCostRateData.estimateRateType);

			BigDecimal total = new BigDecimal(0.0);
			dailyCost.setPrimaryTotalAmount(total);
			dailyCost.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
			total.add(dailyCost.getSubordinateTotalAmount());
			
			dailyCost.setTotalCostAmount(total);
		}
		
	}	
	
	private void initResourceOtherEstimate(IncidentResourceDailyCost dailyCost,IncidentResourceOther iroEntity) {
		dailyCost.setResourceCostType(ResourceCostTypeEnum.RESOURCE_OTHER);
		dailyCost.setIsFlowdown(false);
		dailyCost.setIsLocked(false);
		
		dailyCost.setUnits(new BigDecimal(this.defaultCostRateData.estimateUnits.doubleValue()) );
		dailyCost.setUnitCostAmount(this.defaultCostRateData.estimateRateAmount);
		dailyCost.setRateType(this.defaultCostRateData.estimateRateType);
		dailyCost.setAircraftCostAmount(BigDecimal.valueOf(0.0));

		BigDecimal total = BigDecimal.valueOf(0.0);
		if(this.isZeroEstimate==false){
			total = defaultCostRateData.estimateRateAmount.multiply(BigDecimal.valueOf(defaultCostRateData.estimateUnits));
		}
		
		dailyCost.setPrimaryTotalAmount(total);
		dailyCost.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
		total.add(dailyCost.getSubordinateTotalAmount());
		dailyCost.setTotalCostAmount(total);
	}	
}
