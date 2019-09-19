package gov.nwcg.isuite.core.cost.operations.calculator;

import gov.nwcg.isuite.core.cost.data.DefaultCostRateData;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/*
 * This class calculates the cost amount for a single day.
 */
public class SingleDayCalculator2 {
	public IncidentResource irEntity=null;
	public IncidentResourceOther iroEntity=null;
	public IncidentResourceVo irVo=null;
	public Collection<AssignmentTimePost> existingTimePosts = null;
	public Collection<TimeAssignAdjust> existingAdjustments = null;
	public Collection<IncidentResourceDailyCost> existingDailyCosts = null;
	public Collection<Long> otherChildIacs= new ArrayList<Long>();
	public ResourceCostTypeEnum resourceCostType=null;
	
	public Boolean updateRatesOnly=false;
	
	// costs that have not yet been persisted
	public Collection<IncidentResourceDailyCost> pendingSaveDailyCosts=new ArrayList<IncidentResourceDailyCost>();
	
	private IncidentAccountCode resIncidentAccountCode=null;
	private EstimateCalculator estimateCalc = new EstimateCalculator();
	private ActualCalculator actualCalc = new ActualCalculator();
	private FlowdownCalculator flowdownCalc = new FlowdownCalculator();

	private IncidentResourceDailyCost lastActualFlowdownDailyCost=null;
	
	public Boolean isParent=false;
	public Boolean generateCostsFlag=false;
	
	// Default Rate 
	public DefaultCostRateData defaultCostRateData=null;

	public Collection<IncidentResourceDailyCost> costsToSave = new ArrayList<IncidentResourceDailyCost>();
	public Collection<IncidentResourceDailyCost> costsToDelete = new ArrayList<IncidentResourceDailyCost>();

	private Date costDateToProcess=null;
	private Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
	private Collection<TimeAssignAdjust> adjustments = new ArrayList<TimeAssignAdjust>();

	
	public SingleDayCalculator2(){
	}
	
	/**
	 * @param date
	 * 			date to calculate cost for
	 * @return
	 * @throws Exception
	 */
	public void getDayCosts(Date date) throws Exception {
		
		this.costDateToProcess = date;
		
		// init the return object
		Collection<IncidentResourceDailyCost> dailyCosts = new ArrayList<IncidentResourceDailyCost>();
		
		// Establish the default Resource accounting code
		this.resIncidentAccountCode = new IncidentAccountCodeImpl();
		if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE 
				|| this.resourceCostType==ResourceCostTypeEnum.AIRCRAFT){
			if(null != irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo())
				this.resIncidentAccountCode.setId(irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getId());
		}else{
			if(null != this.iroEntity.getResourceOther().getIncidentAccountCode())
				this.resIncidentAccountCode.setId(iroEntity.getResourceOther().getIncidentAccountCode().getId());
		}
		
		// init the estimator
		this.estimateCalc.irEntity=this.irEntity;
		this.estimateCalc.iroEntity=this.iroEntity;
		this.estimateCalc.irVo=this.irVo;
		this.estimateCalc.resourceCostType=this.resourceCostType;
		this.estimateCalc.defaultCostRateData=this.defaultCostRateData;

		// init the actualtor
		this.actualCalc.resourceCostType=this.resourceCostType;
		this.actualCalc.defaultCostRateData=this.defaultCostRateData;
		this.actualCalc.isUpdatingRate=this.updateRatesOnly;
		
		// init the flowdowntor
		this.flowdownCalc.resourceCostType=this.resourceCostType;
		
		/*
		 * get the default daily costs based on date
		 */
		costsToSave = new ArrayList<IncidentResourceDailyCost>();
		costsToDelete = new ArrayList<IncidentResourceDailyCost>();
		this.getCosts(date);
		
	}

	private void getCosts(Date date) throws Exception{
		IncidentResourceDailyCost dailyCost=null;

		/*
		 * are there an existing daily cost records for this date?
		 */
		System.out.println("Start get existing daily costs: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
		Collection<IncidentResourceDailyCost> existingDailyCosts= this.getExistingDailyCosts(date);
		System.out.println("End get existing daily costs: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
		
		// if only updating rates and dailyCost is null (not creating new records), return null if dailyCost is null
		if(!CollectionUtility.hasValue(existingDailyCosts) && this.updateRatesOnly==true){
			return;
		}
		
		timePosts = new ArrayList<AssignmentTimePost>();
		adjustments = new ArrayList<TimeAssignAdjust>();

		Boolean hasActualPostings=false;
		if(this.resourceCostType!=ResourceCostTypeEnum.RESOURCE_OTHER){
			System.out.println("Start get existing time postings: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
			timePosts=this.getTimePostings(date);
			System.out.println("Start get existing time postings: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
			System.out.println("Start get existing adjustments: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
			adjustments = this.getAdjustments(date);
			System.out.println("End get existing adjustments: "+ DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
		}

		if(CollectionUtility.hasValue(timePosts) || CollectionUtility.hasValue(adjustments))
			hasActualPostings=true;
		else{
			// remove all existing actuals for this day
			Collection<IncidentResourceDailyCost> updatedExisting = new ArrayList<IncidentResourceDailyCost>();
			for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
				Boolean bDelete=false;
				if(DateUtil.isSameDate(date, irdc.getActivityDate())){
					if(irdc.getCostLevel().equals("A"))
						bDelete=true;
				}
				if(bDelete==true)
					this.costsToDelete.add(irdc);
				else
					updatedExisting.add(irdc);
			}
			this.existingDailyCosts=updatedExisting;
		}
		
		if(hasActualPostings==true){
			this.createActuals(date);
		}else{
			Collection<IncidentResourceDailyCost> previousDayCosts = this.getPreviousDayDailyCosts(date);
			if(CollectionUtility.hasValue(previousDayCosts) || this.lastActualFlowdownDailyCost!=null){
				// create flowdown
				this.createFlowdown(previousDayCosts,date);
			}else{
				// remove any existing Actual costs
				Collection<IncidentResourceDailyCost> updatedExisting = new ArrayList<IncidentResourceDailyCost>();
				for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
					Boolean bDelete=false;
					if(DateUtil.isSameDate(date, irdc.getActivityDate())){
						for(IncidentResourceDailyCost irdcToDelete : previousDayCosts){
							if(irdc.getId().compareTo(irdcToDelete.getId())==0 && irdc.getCostLevel().equals("A"))
								bDelete=true;
						}
					}
					if(bDelete==true)
						this.costsToDelete.add(irdc);
					else
						updatedExisting.add(irdc);
				}
				this.existingDailyCosts=updatedExisting;
				
				// create estimate
				this.createEstimate(date);
			}
			
		}
		
	}

	private void createActuals(Date activityDate) {
		
		/*
		 * Generate an actual.
		 * Use the existing daily cost record if avail.
		 * for each distinct iac, create the actual cost record
		 */
		Collection<IncidentAccountCode> distinctIacs=this.getDistinctIacsFromTime();
		for(IncidentAccountCode iac : distinctIacs){
			IncidentResourceDailyCost dailyCost=null;
			Boolean skip=false;
			for(IncidentResourceDailyCost irdc : existingDailyCosts){
				if(DateUtil.isSameDate(activityDate, irdc.getActivityDate())){
					if(BooleanUtility.isTrue(irdc.getIsLocked())){
						// do nothing, what to do?
						this.costsToSave.add(irdc);
						skip=true;
					}else{
						if(irdc.getIncidentAccountCodeId().compareTo(iac.getId())==0){
							dailyCost=irdc;
						}
					}
				}
			}

			if(null==dailyCost)
				dailyCost=new IncidentResourceDailyCostImpl();

			if(!skip){
				dailyCost.setIncidentAccountCode(iac);
				dailyCost=actualCalc.generateActual(dailyCost
												,this.resourceCostType
												, this.costDateToProcess
												, irEntity
												, iroEntity
												, timePosts
												, adjustments);
				this.costsToSave.add(dailyCost);
			}
		}

			
		/*
		 * determine which ones to remove
		 */
		this.removeInvalidCosts(activityDate);

		// create parent placeholder record for child roll up costs if applicable
		this.createParentPlaceholder(activityDate);
	}

	private void createFlowdown(Collection<IncidentResourceDailyCost> previousDayCosts,Date activityDate) {

		Boolean hasPrevActualOrFlowdown=false;
		for(IncidentResourceDailyCost prevCost : previousDayCosts){
			if(prevCost.getCostLevel().equals("F") || prevCost.getCostLevel().equals("A"))
				hasPrevActualOrFlowdown=true;
		}
		
		if(hasPrevActualOrFlowdown==false){
			// remove any A's or F's
			Collection<IncidentResourceDailyCost> updatedExisting = new ArrayList<IncidentResourceDailyCost>();
			for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
				Boolean bDelete=false;
				if(DateUtil.isSameDate(activityDate, irdc.getActivityDate()) && (irdc.getCostLevel().equals("F")|| irdc.getCostLevel().equals("A"))){
					bDelete=true;
				}
				if(bDelete==true)
					this.costsToDelete.add(irdc);
				else
					updatedExisting.add(irdc);
			}
			this.existingDailyCosts=updatedExisting;
		}else{
			// remove any F's for acctcodes not in previousDayCosts list
			Collection<IncidentResourceDailyCost> updatedExisting = new ArrayList<IncidentResourceDailyCost>();
			for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
				Boolean bDelete=false;
				if(DateUtil.isSameDate(activityDate, irdc.getActivityDate()) && (irdc.getCostLevel().equals("F"))){
					Boolean bFound=false;
					// check if irdc accounting code exists in previous day costs
					for(IncidentResourceDailyCost prevDayCost : previousDayCosts){
						if(DateUtil.isSameDate(activityDate, prevDayCost.getActivityDate())
								&& (prevDayCost.getIncidentAccountCodeId().compareTo(irdc.getIncidentAccountCodeId())==0 )){
							bFound=true;
						}
					}
					
					if(bFound==false)
						bDelete=true;
				}
				if(bDelete==true)
					this.costsToDelete.add(irdc);
				else
					updatedExisting.add(irdc);
			}
			this.existingDailyCosts=updatedExisting;
		}
		
		for(IncidentResourceDailyCost prevDayCost : previousDayCosts){
			IncidentResourceDailyCost dailyCost=null;

			if(prevDayCost.getCostLevel().equals("A") || prevDayCost.getCostLevel().equals("F")){
				this.lastActualFlowdownDailyCost=prevDayCost;

				// check for existing cost record
				for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
					if(DateUtil.isSameDate(activityDate, irdc.getActivityDate())){
						if(irdc.getIncidentAccountCodeId().compareTo(prevDayCost.getIncidentAccountCodeId())==0){
							dailyCost=irdc;
							break;
						}
					}
				}

				// create flowdown record
				if((null==dailyCost || (null != dailyCost && BooleanUtility.isFalse(dailyCost.getIsLocked())) ) && null != this.lastActualFlowdownDailyCost){
					/*
					 * Generate a flowdown.
					 * Use the existing daily cost record if avail.
					 */
					if(null==dailyCost){
						dailyCost = new IncidentResourceDailyCostImpl();
						if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
							// do nothing, other costs do not get auto flowdown from actuals
						}else{
							// if agency is null, return null since we only create actuals
							if(null == this.irEntity.getResource().getAgency()){
								dailyCost=null;
							}
						}
					}else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
						dailyCost=null;
					}

					if(null != this.lastActualFlowdownDailyCost && null != dailyCost)
						dailyCost = flowdownCalc.generateFlowdown(this.lastActualFlowdownDailyCost, dailyCost, activityDate);

					if(null != dailyCost){
						this.costsToSave.add(dailyCost);
					}
				}
			}else{
				if(prevDayCost.getCostLevel().equals("E") && this.generateCostsFlag==true){

					// check for existing cost record
					for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
						if(DateUtil.isSameDate(activityDate, irdc.getActivityDate())){
							if(irdc.getIncidentAccountCodeId().compareTo(prevDayCost.getIncidentAccountCodeId())==0){
								dailyCost=irdc;
								break;
							}
						}
					}
					
					// create flowdown estimate record
					if((null==dailyCost || (null != dailyCost && BooleanUtility.isFalse(dailyCost.getIsLocked())) )){
						/*
						 * Generate a flowdown (really an estimate here).
						 * Use the existing daily cost record if avail.
						 */
						if(null==dailyCost){
							dailyCost = new IncidentResourceDailyCostImpl();
							if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
								// do nothing, other costs do not get auto flowdown from actuals
							}else{
								// if agency is null, return null since we only create actuals
								if(null == this.irEntity.getResource().getAgency()){
									dailyCost=null;
								}
							}
						}else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
							dailyCost=null;
						}

						if(null != dailyCost){
							dailyCost.setAdjustmentAmount(BigDecimal.valueOf(0.0));
							if(null==dailyCost.getIncidentAccountCode())
								dailyCost.setIncidentAccountCode(this.resIncidentAccountCode);
							if(this.generateCostsFlag==false)
								estimateCalc.isZeroEstimate=true;

							dailyCost=estimateCalc.generateEstimate(dailyCost, activityDate);
							estimateCalc.isZeroEstimate=false;

							this.costsToSave.add(dailyCost);
						}
					}
				}else{
					if(this.generateCostsFlag==false || this.isParent==true){
						//make sure to remove any leftover 'e' records from previous run
						// check for existing cost record
						for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
							if(DateUtil.isSameDate(activityDate, irdc.getActivityDate())){
								if(irdc.getIncidentAccountCodeId().compareTo(prevDayCost.getIncidentAccountCodeId())==0){
									if(null!=irdc.getCostLevel() && irdc.getCostLevel().equals("E")){
										costsToDelete.add(irdc);
										break;
									}else if(irdc.getCostLevel()==null)
										costsToSave.add(irdc);
								}
							}
						}
					}
					
				}
				
			}
		}
		
		
		// create parent placeholder record for child roll up costs if applicable
		this.createParentPlaceholder(activityDate);
	}

	private void createEstimate(Date activityDate) {
		IncidentResourceDailyCost dailyCost=null;
		
		// is there already an estimate record
		for(IncidentResourceDailyCost irdc : this.existingDailyCosts){
			if(DateUtil.isSameDate(activityDate, irdc.getActivityDate()) && (irdc.getCostLevel().equals("E") || null==irdc.getCostLevel())){
				dailyCost=irdc;
			}
			if(DateUtil.isSameDate(activityDate, irdc.getActivityDate()) && irdc.getCostLevel().equals("A")){
				// need to remove any leftover actuals that are not valid
				costsToDelete.add(irdc);
			}
		}

		if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
			// resourceother, no dependency on agency
			if(null==dailyCost)
				dailyCost = new IncidentResourceDailyCostImpl();
			else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
				return;
			}
			
			// 11/16/2013 added to not regenerate
			if(null != dailyCost && this.generateCostsFlag==true && (null != dailyCost.getCostLevel() && !dailyCost.getCostLevel().equals("E")))
				return;
			
		}else{

			// 11/16/2013 added to not regenerate
			// 2/6/2014 commenting out
			//if(null != dailyCost && this.generateCostsFlag==true && (null != dailyCost.getCostLevel() && dailyCost.getCostLevel()!= CostLevelEnum.E))
			//	return;
			
			if(null!=dailyCost && this.generateCostsFlag==false && !dailyCost.getIsLocked() && !dailyCost.getCostLevel().equals("M") && !dailyCost.getCostLevel().equals("U")){
				if(this.isParent==true && dailyCost.getCostLevel()==null){
					this.costsToSave.add(dailyCost);
				}else
					this.costsToDelete.add(dailyCost);
			}else{
				if(null != this.irEntity.getResource().getAgency()){
					if(null==dailyCost)
						dailyCost = new IncidentResourceDailyCostImpl();
					
					if(BooleanUtility.isFalse(dailyCost.getIsLocked())){
						// for estimate reset timepost/adj amounts to 0
						dailyCost.setAdjustmentAmount(BigDecimal.valueOf(0.0));
						if(null==dailyCost.getIncidentAccountCode())
							dailyCost.setIncidentAccountCode(this.resIncidentAccountCode);
						if(this.generateCostsFlag==false)
							estimateCalc.isZeroEstimate=true;

						dailyCost=estimateCalc.generateEstimate(dailyCost, activityDate);
						estimateCalc.isZeroEstimate=false;

						if(this.generateCostsFlag==true)
							this.costsToSave.add(dailyCost);
						else 
							dailyCost=null;
					}

				}
				
			}
			
		}
		
		// create parent placeholder record for child roll up costs if applicable
		this.createParentPlaceholder(activityDate);
		
	}

	private void removeInvalidCosts(Date activityDate){
		/*
		 * determine which ones to remove
		 */
		Collection<IncidentAccountCode> distinctIacs=this.getDistinctIacsFromTime();
		for(IncidentResourceDailyCost irdc : existingDailyCosts){
			if(DateUtil.isSameDate(activityDate, irdc.getActivityDate())){
				Boolean bFound=false;
				for(IncidentResourceDailyCost irdcToSave : this.costsToSave){
					if(LongUtility.hasValue(irdcToSave.getId())){
						// do not remove one in the costsToSave list
						if(irdcToSave.getId().compareTo(irdc.getId())==0)
							bFound=true;
					}
					if(bFound==false){
						// remove any not matching distinctIacs
						for(IncidentAccountCode iac : distinctIacs){
							if(irdc.getIncidentAccountCodeId().compareTo(iac.getId())==0){
								bFound=true;
							}
						}
					}
				}
				
				if(bFound==false)
					this.costsToDelete.add(irdc);
			}
		}
	}
	
	private void createParentPlaceholder(Date activityDate){
		/*
		 * if parent == true,
		 * need to create placeholder record to rollup child costs if it doesn't already exist.
		 */
		if(this.isParent==true){
			if(this.otherChildIacs.size()<1){
				for(IncidentResourceDailyCost irdc : this.costsToSave){
					if(LongUtility.hasValue(irdc.getId()) && irdc.getCostLevel()==null)
						this.costsToDelete.add(irdc);
				}
				return;
			}
			
			for(Long childIacId : this.otherChildIacs){
				Boolean bFoundChildIac=false;
				for(IncidentResourceDailyCost irdc : this.costsToSave){
					Long iacid=0L;
					if(null != irdc.getIncidentAccountCode())
						iacid=irdc.getIncidentAccountCode().getId();
					else if(LongUtility.hasValue(irdc.getIncidentAccountCodeId()))
						iacid=irdc.getIncidentAccountCodeId();
					
					if(iacid.compareTo(childIacId)==0){
						bFoundChildIac=true;
					}
				}
				
				if(bFoundChildIac==false){
					// need to create placeholder record
					IncidentResourceDailyCost placeholder=new IncidentResourceDailyCostImpl();
					placeholder.setActivityDate(activityDate);
					placeholder.setResourceCostType(this.resourceCostType);
					placeholder.setIncidentResource(this.irEntity);
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(childIacId);
					placeholder.setIncidentAccountCode(iac);
					placeholder.setPrimaryTotalAmount(new BigDecimal(0));
					placeholder.setSubordinateTotalAmount(new BigDecimal(0));
					placeholder.setTotalCostAmount(new BigDecimal(0));
					placeholder.setUnitCostAmount(new BigDecimal(0));
					placeholder.setUnits(new BigDecimal(0));
					costsToSave.add(placeholder);
				}
			}
		}
		
	}
	
	private Collection<IncidentAccountCode> getDistinctIacsFromTime(){
		Collection<IncidentAccountCode> iacs = new ArrayList<IncidentAccountCode>();
		Collection<Long> iacIds = new ArrayList<Long>();
		
		for(AssignmentTimePost atp : this.existingTimePosts){
			if(DateUtil.isSameDate(this.costDateToProcess, atp.getPostStartDate())){
				if(!iacIds.contains(atp.getIncidentAccountCodeId())){
					iacIds.add(atp.getIncidentAccountCodeId());
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(atp.getIncidentAccountCodeId());
					iacs.add(iac);
				}
			}
		}

		for(TimeAssignAdjust adj : this.existingAdjustments){
			if(DateUtil.isSameDate(this.costDateToProcess, adj.getActivityDate())){
				if(!iacIds.contains(adj.getIncidentAccountCodeId())){
					iacIds.add(adj.getIncidentAccountCodeId());
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(adj.getIncidentAccountCodeId());
					iacs.add(iac);
				}
			}
		}
		
		return iacs;
	}
	
	/**
	 * Returns the existing daily costs (if avail) 
	 * that has the date specified 
	 * and the cost level is not a manually added cost record by the user.
	 * 
	 * @param date
	 * @return
	 */
	private Collection<IncidentResourceDailyCost> getExistingDailyCosts(Date date) {
		IncidentResourceDailyCost rtnCost = null;
		Collection<IncidentResourceDailyCost> costs = new ArrayList<IncidentResourceDailyCost>();
		
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(!cost.getCostLevel().equals("M")){
				if(DateUtil.isSameDate(date,cost.getActivityDate())){
					costs.add(cost);
					break;
				}
			}
		}
		
		return costs;
	}

	/**
	 * Returns the previous day cost if available.
	 * 
	 * @param date
	 * @return
	 */
	private Collection<IncidentResourceDailyCost> getPreviousDayDailyCosts(Date date) throws Exception {
		Collection<IncidentResourceDailyCost> previousDailyCosts=new ArrayList<IncidentResourceDailyCost>();

		Date previousDay = DateUtil.subtractDaysFromDate(date, 1);
		
		// First check existingDailyCosts (records already in the db)
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
				if(!cost.getCostLevel().equals("M") && !cost.getCostLevel().equals("U"))
					previousDailyCosts.add(cost);
			}
		}

		return previousDailyCosts;
	}
	
	/**
	 * Returns all time posts for the resource where the time post date and accounting code
	 * match the values passed in.
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<AssignmentTimePost> getTimePostings(Date date) {
		Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
		
		for(AssignmentTimePost atp : this.existingTimePosts){
			if(!StringUtility.hasValue(atp.getContractorPostType())){
				// non-contractor
					if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
						timePosts.add(atp);
					}
			}else{
				String postType=atp.getContractorPostType();
				if(postType.equals("SPECIAL")){
						if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
							timePosts.add(atp);
						}
				}else{
						if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
							timePosts.add(atp);
						}
				}
			}
		}
		
		return timePosts;
	}

	/**
	 * Returns all adjustments for the resource where the adjustment date and accounting code
	 * match the values passed in.
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<TimeAssignAdjust> getAdjustments(Date date) {
		Collection<TimeAssignAdjust> adjustments = new ArrayList<TimeAssignAdjust>();
		
		for(TimeAssignAdjust adj : this.existingAdjustments){
				if(DateUtil.isSameDate(adj.getActivityDate(), date)){
					adjustments.add(adj);
				}
		}
		
		return adjustments;
	}

}
