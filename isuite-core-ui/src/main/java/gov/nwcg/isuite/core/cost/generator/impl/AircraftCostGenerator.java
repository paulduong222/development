package gov.nwcg.isuite.core.cost.generator.impl;

import gov.nwcg.isuite.core.cost.operations.calculator.SingleDayCalculator2;
import gov.nwcg.isuite.core.cost.utilities.StartDateResolver;
import gov.nwcg.isuite.core.cost.utilities.StopDateResolver;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.filter.DailyCostFilter;
import gov.nwcg.isuite.core.filter.impl.DailyCostFilterImpl;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.cost.generator.AbstractGenerator;
import gov.nwcg.isuite.framework.cost.generator.ICostGenerator;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.ChangeUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class AircraftCostGenerator extends AbstractGenerator implements ICostGenerator {

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.cost.generator.ICostGenerator#userFlowdown(gov.nwcg.isuite.core.domain.IncidentResourceDailyCost, gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo)
	 */
	public void userFlowdown(IncidentResourceDailyCost dailyCostOrigin, IncidentResourceDailyCostVo preSaveVo) throws DailyCostException{
		
		try{
			// get all dailycosts after activity date for this resource
			DailyCostFilter filter = new DailyCostFilterImpl();
			filter.setIncidentResourceId(super.irVo.getId());
			filter.setFromDate(dailyCostOrigin.getActivityDate());
			// rule says to use after date
			filter.setFromDateComparator(">=");
			
			Collection<IncidentResourceDailyCost> dailyCosts 
								= super.irdcDao.getByFilter(filter);
			
			Collection<IncidentResourceDailyCost> dailyCostsToUpdate=new ArrayList<IncidentResourceDailyCost>();

			// Establish the default Resource accounting code
			IncidentAccountCode resIncidentAccountCode = new IncidentAccountCodeImpl();
			resIncidentAccountCode.setId(irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getId());
		
			/*
			 * Flowdown the dailyCostOrigin data (skip Actual cost/Postings found)
			 */
			for(IncidentResourceDailyCost entity : dailyCosts){
				// apply flowdown if costlevel is not Actual
				//if(entity.getCostLevel()!=CostLevelEnum.A){
					
					/*
					 *  TODO: find out if we need to only flowdown 
					 *        records with matching accountingcode?
					 *        
					 *        do we stop on first Locked record encountered?
					 */
					
					// If not locked and not ManualCostRecord, proceed
					if(BooleanUtility.isFalse(entity.getIsLocked()) 
								&& !entity.getCostLevel().equals("M")){

							if(dailyCostOrigin.getCostLevel().equals("A")){
								entity.setCostLevel("F");
							}else{
								entity.setCostLevel("U");
							}

						// is the incident Account code same as resource's defIAC?
						//if(entity.getIncidentAccountCode().getId().compareTo(resIncidentAccountCode.getId())==0){
							
						// only flowdown matching acct code ids - per defect 3810
						if(entity.getIncidentAccountCode().getId().compareTo(dailyCostOrigin.getIncidentAccountCode().getId())==0){
							entity.setIsFlowdown(false);
							entity.setIsLocked(dailyCostOrigin.getIsLocked());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getAircraftCostAmount(), preSaveVo.getAircraftCostAmount())))
								entity.setAircraftCostAmount(dailyCostOrigin.getAircraftCostAmount());
						
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getUnitCostAmount(), preSaveVo.getUnitCostAmount())))
								entity.setUnitCostAmount(dailyCostOrigin.getUnitCostAmount());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getUnits(), preSaveVo.getUnits())))
								entity.setUnits(dailyCostOrigin.getUnits());
							
							//if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getPrimaryTotalAmount(), preSaveVo.getPrimaryTotalAmount())))
							//	entity.setPrimaryTotalAmount(dailyCostOrigin.getPrimaryTotalAmount());
							BigDecimal amt = entity.getUnits().multiply(entity.getUnitCostAmount());
							entity.setPrimaryTotalAmount(amt);
							
							if(DecimalUtil.hasValue(entity.getSubordinateTotalAmount())){
								BigDecimal total = entity.getPrimaryTotalAmount().add(entity.getSubordinateTotalAmount());
								entity.setTotalCostAmount(total);
							}else
								entity.setTotalCostAmount(entity.getPrimaryTotalAmount());
							
							if(BooleanUtility.isTrue(super.isAccrualCodeChanged(dailyCostOrigin, preSaveVo)))
								entity.setAccrualCode(dailyCostOrigin.getAccrualCode());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getCargoPounds(), preSaveVo.getCargoPounds())))
								entity.setCargoPounds(dailyCostOrigin.getCargoPounds());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getFlightHours(), preSaveVo.getFlightHours())))
								entity.setFlightHours(dailyCostOrigin.getFlightHours());
							
							//entity.setAdjustmentAmount(dailyCostOrigin.getAdjustmentAmount());
							
							if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(dailyCostOrigin.getNumberOfLoads(), preSaveVo.getNumberOfLoads())))
								entity.setNumberOfLoads(dailyCostOrigin.getNumberOfLoads());
							
							if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(dailyCostOrigin.getNumberOfPassengers(), preSaveVo.getNumberOfPassengers())))
								entity.setNumberOfPassengers(dailyCostOrigin.getNumberOfPassengers());
	
							if(BooleanUtility.isTrue(ChangeUtil.isIntegerChanged(dailyCostOrigin.getNumberOfTrips(), preSaveVo.getNumberOfTrips())))
								entity.setNumberOfTrips(dailyCostOrigin.getNumberOfTrips());
							
							if(BooleanUtility.isTrue(ChangeUtil.isStringChanged(dailyCostOrigin.getRateType(), preSaveVo.getRateType())))
								entity.setRateType(dailyCostOrigin.getRateType());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getRetardantGallons(), preSaveVo.getRetardantGallons())))
								entity.setRetardantGallons(dailyCostOrigin.getRetardantGallons());
							
							if(BooleanUtility.isTrue(ChangeUtil.isBigDecimalChanged(dailyCostOrigin.getWaterGallons(), preSaveVo.getWaterGallons())))
								entity.setWaterGallons(dailyCostOrigin.getWaterGallons());
	
							if(BooleanUtility.isTrue(super.isCostGroupChanged(dailyCostOrigin, preSaveVo))){
								entity.setCostGroup(dailyCostOrigin.getCostGroup());
								entity.setIncidentShift(dailyCostOrigin.getIncidentShift());
							}
							
							if(BooleanUtility.isTrue(super.isAccountCodeChanged(dailyCostOrigin, preSaveVo))){
								entity.setIncidentAccountCode(dailyCostOrigin.getIncidentAccountCode());
							}
	
							dailyCostsToUpdate.add(entity);				
						}
					}
				//}else{
					// get out of loop
				//	break;
				//}
			}

			// save the flowdown records if avail
			if(CollectionUtility.hasValue(dailyCostsToUpdate))
				irdcDao.saveAll(dailyCostsToUpdate);
			
		}catch(Exception e){
			DailyCostException dce = new DailyCostException(e.getMessage());
			
			throw dce;
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.cost.generator.ICostGenerator#generateCosts()
	 */
	public void generateCosts(Date startDate,Boolean updateRatesOnly) throws Exception {

		try{
		
			// Resolve the dates
			if(null == startDate)
				super.dailyCostStartDate=StartDateResolver.resolveIRStartDate(irVo);
			else
				super.dailyCostStartDate=startDate;
			
			if(null==super.dailyCostEndDate)
				super.dailyCostEndDate=StopDateResolver.resolveIRStopDate(irVo);

			resourceCostStartDate=StartDateResolver.resolveIRStartDate(irVo);
			resourceCostEndDate=StopDateResolver.resolveIRStopDate(irVo);
			
			// only process if we have the start/end dates
			if( (DateUtil.hasValue(dailyCostStartDate) && DateUtil.hasValue(dailyCostEndDate))
					&& (DateUtil.isSameDate(dailyCostStartDate, dailyCostEndDate) || dailyCostEndDate.after(dailyCostStartDate))){

				// remove all costs after enddate
				if(null != resourceCostEndDate)
					super.irdcDao.deleteCostAfterDate("RESOURCE",super.irEntity.getId(), resourceCostEndDate);

				// remove all costs before startdate
				if(null != resourceCostStartDate)
					super.irdcDao.deleteCostBeforeDate("RESOURCE",super.irEntity.getId(), resourceCostStartDate);
				
				loadCollections(CostTypeEnum.RESOURCE_AIRCRAFT);

				// load default rate from incident cost rates tables
				loadDefaultRate();
				loadEstimateDefaultRate();
				
				// load default cost group and shift
				loadDefaultCostGroupShift();
				
				// build list of cost dates to generate
				Collection<Date> resourceCostDates = super.buildDates(dailyCostStartDate, dailyCostEndDate);

				// Cost Records to save
				Collection<IncidentResourceDailyCost> costsToSave = new ArrayList<IncidentResourceDailyCost>();

				
				// init dayCalculator
				SingleDayCalculator2 dayCalculator = new SingleDayCalculator2();
				dayCalculator.irEntity=irEntity;
				dayCalculator.irVo=irVo;
				dayCalculator.existingTimePosts=tpEntities;
				dayCalculator.existingAdjustments=taaEntities;
				dayCalculator.existingDailyCosts=irdcEntities;
				dayCalculator.resourceCostType=ResourceCostTypeEnum.AIRCRAFT;
				dayCalculator.defaultCostRateData=super.defaultCostRateData;
				dayCalculator.updateRatesOnly=updateRatesOnly;
				dayCalculator.isParent=super.isParent;
				
				dayCalculator.generateCostsFlag=super.irEntity.getCostData().getGenerateCosts();

				Collection<IncidentResourceDailyCost> dailyCostsToDelete = new ArrayList<IncidentResourceDailyCost>();
				
				if(null != dayCalculator){
					// Loop through each date, and calculate the costs for the date
					for(Date d : resourceCostDates){

						irdcEntities = irdcDao.getByIncidentResourceId(irVo.getId(),DateUtil.subtractDaysFromDate(d, 1),d);
						
						// get child unique iacs for this date
						Collection<Long> childIACs = super.getChildUniqueAcctCodeIdsByDate(d);

						dayCalculator.otherChildIacs=childIACs;
						
						// get all costs for the date
						dayCalculator.existingDailyCosts=irdcEntities;
						dayCalculator.getDayCosts(d);
						Collection<IncidentResourceDailyCost> dailyCosts = dayCalculator.costsToSave;
						dailyCostsToDelete = dayCalculator.costsToDelete;

						// build dailyCosts to save
						if(CollectionUtility.hasValue(dailyCosts)){
							for(IncidentResourceDailyCost cost : dailyCosts){
								
								/*
								 * if this resource has children, get roll up amount
								 */
								if(CollectionUtility.hasValue(irEntity.getResource().getChildResources())){
									
									//System.out.println(DateUtil.toDateString(cost.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY) + ", " +
									//					"AccountCodeId: "+cost.getIncidentAccountCode().getId() + ", " +
									//					"");
									
									BigDecimal rollupAmount=this.irdcDao.getParentRollupAmount(irEntity.getId(), cost.getIncidentAccountCode().getId(), cost.getActivityDate());
									cost.setSubordinateTotalAmount(rollupAmount);
									
									BigDecimal totalCost=
										cost.getPrimaryTotalAmount().add(cost.getSubordinateTotalAmount());
									cost.setTotalCostAmount(totalCost);
								}
								
								costsToSave.add(cost);
							}
						}

						if(CollectionUtility.hasValue(costsToSave)){
							for(IncidentResourceDailyCost cost : costsToSave){
								irdcDao.save(cost);
								irdcDao.flushAndEvict(cost.getIncidentAccountCode());
								irdcDao.flushAndEvict(cost);
							}
						}
						
						if(CollectionUtility.hasValue(dailyCostsToDelete)){
							for(IncidentResourceDailyCost cost : dailyCostsToDelete){
								if(null != cost && LongUtility.hasValue(cost.getId())){
									try{
										irdcDao.delete(cost);
										irdcDao.flushAndEvict(cost);
									}catch(Exception ignore){}
								}
							}
						}
						
						costsToSave=new ArrayList<IncidentResourceDailyCost>();
						dailyCostsToDelete=new ArrayList<IncidentResourceDailyCost>();
						
					}

				}

			}

		}catch(Exception e){
			throw e;
		}

	}

	public void rollup(Date startDate) throws Exception {
		
		// get list of distinct activitydate/incidentaccountcodes for all children
		
		// 
	}
	
}
