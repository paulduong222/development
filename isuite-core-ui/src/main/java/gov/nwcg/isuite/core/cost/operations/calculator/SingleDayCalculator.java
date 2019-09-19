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
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/*
 * This class calculates the cost amount for a single day.
 */
public class SingleDayCalculator {
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

	public Collection<IncidentResourceDailyCost> costsToDelete = new ArrayList<IncidentResourceDailyCost>();
	
	// Default Rate 
	public DefaultCostRateData defaultCostRateData=null;
	
	public SingleDayCalculator(){
	}
	
	/**
	 * @param date
	 * 			date to calculate cost for
	 * @return
	 * @throws Exception
	 */
	public Collection<IncidentResourceDailyCost> getDayCosts(Date date) throws Exception {
		
		// init the return object
		Collection<IncidentResourceDailyCost> dailyCosts = new ArrayList<IncidentResourceDailyCost>();
		
		Collection<IncidentResourceDailyCost> dailyCostsOther = new ArrayList<IncidentResourceDailyCost>();

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
		 * get the default daily costs based on date/resincidentaccountcode
		 */
		IncidentResourceDailyCost dailyCostPrimary = this.getCostByResourceIncidentAccountCode(date);
		
		/*
		 *  get all other daily costs for the resource that has timepostings/adjustments
		 *  with a different accounting code than the resource's default
		 */
		if(this.resourceCostType != ResourceCostTypeEnum.RESOURCE_OTHER)
			dailyCostsOther.addAll(this.getCostsByOtherIncidentAccountCodes(date));
		

		if(null != dailyCostPrimary)
			dailyCosts.add(dailyCostPrimary);

		dailyCosts.addAll(dailyCostsOther);
		
		return dailyCosts;
	}

	/**
	 * Returns the system generated daily cost record for the resource
	 * based on the date and the resource's default incident account code.
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private IncidentResourceDailyCost getCostByResourceIncidentAccountCode(Date date) throws Exception{
		IncidentResourceDailyCost dailyCost=null;

		/*
		 * Is there an existing daily cost record for this date/resIncidentAccountCode?
		 */
		dailyCost= this.getExistingDailyCost(date,this.resIncidentAccountCode);
		
		// is there an existing daily cost or time post/adj for this date?
		if(null == dailyCost){
			dailyCost=this.getExistingDailyCost(date, null);
		}
		
		// if only updating rates and dailyCost is null (not creating new records), return null if dailyCost is null
		if(null==dailyCost && this.updateRatesOnly==true){
			return null;
		}
		
		Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
		Collection<TimeAssignAdjust> adjustments = new ArrayList<TimeAssignAdjust>();
		
		if(this.resourceCostType!=ResourceCostTypeEnum.RESOURCE_OTHER){
			timePosts=this.getTimePostings(date, this.resIncidentAccountCode);
			adjustments = this.getAdjustments(date, this.resIncidentAccountCode);
		}
		
		/*
		 * Does this resource have timepostings/adjustments for this date
		 * and the resource incidentaccountcode?
		 */
		if(CollectionUtility.hasValue(timePosts) || CollectionUtility.hasValue(adjustments)){
			/*
			 * Generate an actual.
			 * Use the existing daily cost record if avail.
			 */
			if(null==dailyCost)
				dailyCost = new IncidentResourceDailyCostImpl();
			else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
				return dailyCost;
			}
			
			dailyCost.setIncidentAccountCode(this.resIncidentAccountCode);
			dailyCost=actualCalc.generateActual(dailyCost
											,this.resourceCostType
											, date
											, irEntity
											, iroEntity
											, timePosts
											, adjustments);
		}else{
			/*
			 * Do one of the following:
			 *  - Create a flowdown daily cost record if there is one for a previous date
			 *   OR
			 *  - Create an estimated daily cost record
			 */
			IncidentResourceDailyCost previousDayCost = this.getPreviousDayDailyCost(date);
			if(null != previousDayCost && previousDayCost.getCostLevel().equals("A"))
				this.lastActualFlowdownDailyCost=previousDayCost;
			
			//if(null==dailyCost && null != previousDayCost && (previousDayCost.getCostLevel()==CostLevelEnum.F || previousDayCost.getCostLevel()==CostLevelEnum.A)){
			//if((null==dailyCost || (null != dailyCost && BooleanUtility.isFalse(dailyCost.getIsLocked())) ) && null != previousDayCost && (previousDayCost.getCostLevel()==CostLevelEnum.F || previousDayCost.getCostLevel()==CostLevelEnum.A)){
			if((null==dailyCost || (null != dailyCost && BooleanUtility.isFalse(dailyCost.getIsLocked())) ) && null != this.lastActualFlowdownDailyCost){
				/*
				 * Generate a flowdown.
				 * Use the existing daily cost record if avail.
				 */
				if(null==dailyCost){
					dailyCost = new IncidentResourceDailyCostImpl();
					if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
						
					}else{
						// if agency is null, return null since we only create actuals
						if(null == this.irEntity.getResource().getAgency()){
							return null;
						}
					}
				
				}else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
					return dailyCost;
				}

				//if(BooleanUtility.isFalse(this.irEntity.getCostData().getUseAccrualsOnly()))
				if(null != this.lastActualFlowdownDailyCost)
					dailyCost = flowdownCalc.generateFlowdown(this.lastActualFlowdownDailyCost, dailyCost, date);
				else
					return null;
			}else{
				/*
				 * Generate an estimate.
				 * Use the existing daily cost record if avail.
				 * 
				 * If there is no Agency defined for a Resource and there are Actual Costs 
				 * for that Resource (i.e., Time Postings), the system must only generate 
				 * Cost records for the Actual Time Postings. 
				 * It must not generate any estimated costs for that Resource.
				 */
				if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
					// resourceother, no dependency on agency
					if(null==dailyCost)
						dailyCost = new IncidentResourceDailyCostImpl();
					else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
						return dailyCost;
					}
					
					// 11/16/2013 added to not regenerate
					if(null != dailyCost && this.generateCostsFlag==true && (null != dailyCost.getCostLevel() && !dailyCost.getCostLevel().equals("E")))
						return dailyCost;
					
				}else{
					//dailyCost = this.getExistingEstimateDailyCost(date, null);
					dailyCost = this.getExistingDailyCost(date, null);

					// 11/16/2013 added to not regenerate
					if(null != dailyCost && this.generateCostsFlag==true && (null != dailyCost.getCostLevel() && !dailyCost.getCostLevel().equals("E")))
						return dailyCost;
					
					if(null != this.irEntity.getResource().getAgency()){
						if(null==dailyCost)
							dailyCost = new IncidentResourceDailyCostImpl();
						else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
							return dailyCost;
						}else{
							//if(dailyCost.getCostLevel()!=CostLevelEnum.E)
							//	dailyCost=new IncidentResourceDailyCostImpl();
						}
					}else
						return null;
				}
				
				// for estimate reset timepost/adj amounts to 0
				dailyCost.setAdjustmentAmount(BigDecimal.valueOf(0.0));
				if(null==dailyCost.getIncidentAccountCode())
					dailyCost.setIncidentAccountCode(this.resIncidentAccountCode);
				if(this.generateCostsFlag==false)
					estimateCalc.isZeroEstimate=true;

				dailyCost=estimateCalc.generateEstimate(dailyCost, date);
				estimateCalc.isZeroEstimate=false;

			}
			
		}
		
		return dailyCost;
	}

	/**
	 * @param date
	 * @return
	 */
	private Collection<IncidentResourceDailyCost> getCostsByOtherIncidentAccountCodes(Date date) throws Exception{
		Collection<IncidentResourceDailyCost> dailyCosts=new ArrayList<IncidentResourceDailyCost>();
		IncidentResourceDailyCost dailyCost=null;

		Collection<IncidentAccountCode> otherAccountCodes = this.getOtherAccountCodes(date,this.resIncidentAccountCode, this.otherChildIacs);

		for(IncidentAccountCode iac : otherAccountCodes){
			Collection<AssignmentTimePost> timePostsDate=this.getTimePostings(date, iac);
			Collection<TimeAssignAdjust> adjustmentsDate = this.getAdjustments(date, iac);
			
			/*
			 * Is there an existing daily cost record for this date/resIncidentAccountCode?
			 */
			dailyCost= this.getExistingDailyCost(date,iac);
			
			// if only updating rates and dailyCost is null, do nothing (not creating new records)
			if(null==dailyCost && this.updateRatesOnly==true){
				// do nothing
			}else{
				/*
				 * Does this resource have timepostings/adjustments for this date
				 * and the resource incidentaccountcode?
				 */
				if(CollectionUtility.hasValue(timePostsDate) || CollectionUtility.hasValue(adjustmentsDate)){
					/*
					 * Generate an actual.
					 * Use the existing daily cost record if avail.
					 */
					if(null==dailyCost)
						dailyCost = new IncidentResourceDailyCostImpl();
					else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
						dailyCosts.add(dailyCost);
					}
					
					if(BooleanUtility.isFalse(dailyCost.getIsLocked())){
						dailyCost.setIncidentAccountCode(iac);
						dailyCost=actualCalc.generateActual(dailyCost
														,this.resourceCostType
														, date
														, irEntity
														, iroEntity
														, timePostsDate
														, adjustmentsDate);
						dailyCosts.add(dailyCost);
					}
				}else{
					/*
					 * Do one of the following:
					 *  - Create a flowdown daily cost record if there is one for a previous date
					 *   OR
					 *  - Create an estimated daily cost record
					 */
					IncidentResourceDailyCost previousDayCost = this.getPreviousDayDailyCost(date,iac);
					if(null != previousDayCost && (previousDayCost.getCostLevel().equals("F") || previousDayCost.getCostLevel().equals("A"))){
						/*
						 * Generate a flowdown.
						 * Use the existing daily cost record if avail.
						 */
						/*
						if(null==dailyCost)
							dailyCost = new IncidentResourceDailyCostImpl();
						else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
							dailyCosts.add(dailyCost);
						}
						
						if(BooleanUtility.isFalse(dailyCost.getIsLocked())){
							dailyCost = flowdownCalc.generateFlowdown(previousDayCost, dailyCost, date);
						}
						*/
						/*
						 * Generate a flowdown.
						 * Use the existing daily cost record if avail.
						 */
						if(null==dailyCost){
							dailyCost = new IncidentResourceDailyCostImpl();
							if(this.resourceCostType==ResourceCostTypeEnum.RESOURCE_OTHER){
								
							}else{
								// if agency is null, return null since we only create actuals
								if(null == this.irEntity.getResource().getAgency()){
									dailyCost=null;
								}
							}
						}else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
							dailyCosts.add(dailyCost);
						}

						if(BooleanUtility.isFalse(dailyCost.getIsLocked())){
							if(BooleanUtility.isFalse(this.irEntity.getCostData().getUseAccrualsOnly()) && dailyCost != null){
								dailyCost = flowdownCalc.generateFlowdown(previousDayCost, dailyCost, date);
								dailyCosts.add(dailyCost);
							}						
						}
					}else{
						/*
						 * Generate an estimate.
						 * Use the existing daily cost record if avail.
						 * 
						 * If there is no Agency defined for a Resource and there are Actual Costs 
						 * for that Resource (i.e., Time Postings), the system must only generate 
						 * Cost records for the Actual Time Postings. 
						 * It must not generate any estimated costs for that Resource.
						 */
						if(null != dailyCost && this.generateCostsFlag==true){
							
						}else{
							if(null != this.irEntity.getResource().getAgency()){
								if(null==dailyCost)
									dailyCost = new IncidentResourceDailyCostImpl();
								else if(BooleanUtility.isTrue(dailyCost.getIsLocked())){
									dailyCosts.add(dailyCost);
								}
								
								if(BooleanUtility.isFalse(dailyCost.getIsLocked())){
									if(null==dailyCost.getIncidentAccountCode())
										dailyCost.setIncidentAccountCode(this.resIncidentAccountCode);
									dailyCost.setIncidentAccountCode(iac);
									estimateCalc.isZeroEstimate=true;
									dailyCost=estimateCalc.generateEstimate(dailyCost, date);
									estimateCalc.isZeroEstimate=false;
									dailyCosts.add(dailyCost);
								}
							}
						}
						
					}
					
				}
			}
			
			
		}
		

		return dailyCosts;
	}
	
	/**
	 * Returns the existing daily cost (if avail) 
	 * that has the date specified and incaccountcode specified
	 * and the cost level is not a manually added cost record by the user.
	 * 
	 * @param date
	 * @return
	 */
	private IncidentResourceDailyCost getExistingDailyCost(Date date,IncidentAccountCode iac) {
		IncidentResourceDailyCost rtnCost = null;
		
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(!cost.getCostLevel().equals("M")){
				if(DateUtil.isSameDate(date,cost.getActivityDate())){
					if(null != iac){
						if(null != cost.getIncidentAccountCode() 
								&& (cost.getIncidentAccountCode().getId().compareTo(iac.getId())==0 ) ){
							rtnCost=cost;
							break;
						}
					}else{
						rtnCost=cost;
						break;
					}
						
				}
			}
		}
		
		return rtnCost;
	}

	private IncidentResourceDailyCost getExistingEstimateDailyCost(Date date,IncidentAccountCode iac) {
		IncidentResourceDailyCost rtnCost = null;
		
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(cost.getCostLevel().equals("E")){
				if(DateUtil.isSameDate(date,cost.getActivityDate())){
					if(null != iac){
						if(null != cost.getIncidentAccountCode() 
								&& (cost.getIncidentAccountCode().getId().compareTo(iac.getId())==0 ) ){
							rtnCost=cost;
							break;
						}
					}else{
						rtnCost=cost;
						break;
					}
						
				}
			}
		}
		
		return rtnCost;
	}

	/**
	 * Returns whether or not there is already a time posting
	 * for the previous day with the this.resIncidentAccountCode
	 * 
	 * @param date
	 * @return
	 */
	private Boolean hasPreviousDayDailyCost(Date date) throws Exception {
		Boolean rtnVal=false;

		Date previousDay = DateUtil.subtractDaysFromDate(date, 1);
		
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
				if(null != cost.getIncidentAccountCode() 
						&& (cost.getIncidentAccountCode().getId().compareTo(this.resIncidentAccountCode.getId())==0 ) ){
					rtnVal=true;
					break;
				}
			}
		}
		
		return rtnVal;
	}

	/**
	 * Returns the previous day cost if available.
	 * 
	 * @param date
	 * @return
	 */
	private IncidentResourceDailyCost getPreviousDayDailyCost(Date date) throws Exception {
		IncidentResourceDailyCost dailyCost=null;

		Date previousDay = DateUtil.subtractDaysFromDate(date, 1);
		
		// First check existingDailyCosts (records already in the db)
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
				if(null != cost.getIncidentAccountCode() 
						&& (cost.getIncidentAccountCode().getId().compareTo(this.resIncidentAccountCode.getId())==0 ) ){
					dailyCost=cost;
					break;
				}
			}
		}

		//NOTE: if we do not need to use pendingDC's, comment out below.
		//NOTE: implementing filter on 'E' costlevel's, loop will pass these by
		if(null==dailyCost){
			// check the pending collection
			for(IncidentResourceDailyCost cost : this.pendingSaveDailyCosts){
				if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
					if(null != cost.getIncidentAccountCode() 
							&& (cost.getIncidentAccountCode().getId().compareTo(this.resIncidentAccountCode.getId())==0 ) ){

						// TODO: not sure if we need to exclude E's here
						// TODO: if we do not exclude them, subsequent estimates will get a 'F'
						if(!cost.getCostLevel().equals("E")){
							dailyCost=cost;
							break;
						}
					}
				}
			}
		}
		
		return dailyCost;
	}
	
	/**
	 * Returns the previous day cost if available.
	 * 
	 * @param date
	 * @return
	 */
	private IncidentResourceDailyCost getPreviousDayDailyCost(Date date, IncidentAccountCode iac) throws Exception {
		IncidentResourceDailyCost dailyCost=null;

		Date previousDay = DateUtil.subtractDaysFromDate(date, 1);
		
		// First check existingDailyCosts (records already in the db)
		for(IncidentResourceDailyCost cost : this.existingDailyCosts){
			if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
				if(null != cost.getIncidentAccountCode() 
						&& (cost.getIncidentAccountCode().getId().compareTo(iac.getId())==0 ) ){
					dailyCost=cost;
					break;
				}
			}
		}

		//NOTE: if we do not need to use pendingDC's, comment out below.
		//NOTE: implementing filter on 'E' costlevel's, loop will pass these by
		if(null==dailyCost){
			// check the pending collection
			for(IncidentResourceDailyCost cost : this.pendingSaveDailyCosts){
				if(DateUtil.isSameDate(previousDay,cost.getActivityDate())){
					if(null != cost.getIncidentAccountCode() 
							&& (cost.getIncidentAccountCode().getId().compareTo(iac.getId())==0 ) ){

						// TODO: not sure if we need to exclude E's here
						// TODO: if we do not exclude them, subsequent estimates will get a 'F'
						if(!cost.getCostLevel().equals("E")){
							dailyCost=cost;
							break;
						}
					}
				}
			}
		}
		
		return dailyCost;
	}

	/**
	 * Returns all time posts for the resource where the time post date and accounting code
	 * match the values passed in.
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<AssignmentTimePost> getTimePostings(Date date,IncidentAccountCode iac) {
		Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
		
		for(AssignmentTimePost atp : this.existingTimePosts){
			if(!StringUtility.hasValue(atp.getContractorPostType())){
				// non-contractor
				if(atp.getIncidentAccountCode().getId().compareTo(iac.getId())==0){
					if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
						timePosts.add(atp);
					}
				}
			}else{
				String postType=atp.getContractorPostType();
				if(postType.equals("SPECIAL")){
					if(atp.getIncidentAccountCode().getId().compareTo(iac.getId())==0){
						if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
							timePosts.add(atp);
						}
					}
				}else{
					if(atp.getIncidentAccountCode().getId().compareTo(iac.getId())==0){
						if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
							timePosts.add(atp);
						}
					}
				}
			}
		}
		
		return timePosts;
	}

	/**
	 * Returns all time posts for the resource where the time post date matches the value passed
	 * in and the accounting code does not match
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<AssignmentTimePost> getTimePostingsExcludeAC(Date date,IncidentAccountCode iac) {
		Collection<AssignmentTimePost> timePosts = new ArrayList<AssignmentTimePost>();
		
		for(AssignmentTimePost atp : this.existingTimePosts){
			if(atp.getIncidentAccountCode().getId().compareTo(iac.getId())!=0){
				if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
					timePosts.add(atp);
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
	private Collection<TimeAssignAdjust> getAdjustments(Date date,IncidentAccountCode iac) {
		Collection<TimeAssignAdjust> adjustments = new ArrayList<TimeAssignAdjust>();
		
		for(TimeAssignAdjust adj : this.existingAdjustments){
			if(adj.getIncidentAccountCode().getId().compareTo(iac.getId())==0){
				if(DateUtil.isSameDate(adj.getActivityDate(), date)){
					adjustments.add(adj);
				}
			}
		}
		
		return adjustments;
	}

	/**
	 * Returns all adjustments for the resource where the adjustment date matches and accounting code
	 * does not match the values passed in.
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<TimeAssignAdjust> getAdjustmentsExcludeAC(Date date,IncidentAccountCode iac) {
		Collection<TimeAssignAdjust> adjustments = new ArrayList<TimeAssignAdjust>();
		
		for(TimeAssignAdjust adj : this.existingAdjustments){
			if(adj.getIncidentAccountCode().getId().compareTo(iac.getId())!=0){
				if(DateUtil.isSameDate(adj.getActivityDate(), date)){
					adjustments.add(adj);
				}
			}
		}
		
		return adjustments;
	}

	/**
	 * Returns list of other unique account codes by checking timeposts/adjustments
	 * and excluding any records matching the iac passed in.
	 * 
	 * @param date
	 * @param iac
	 * @return
	 */
	private Collection<IncidentAccountCode> getOtherAccountCodes(Date date,IncidentAccountCode iac,Collection<Long> otherIacs) {
		Collection<IncidentAccountCode> iacs = new ArrayList<IncidentAccountCode>();
		HashMap<Long,IncidentAccountCode> map = new HashMap<Long,IncidentAccountCode>();
		
		for(AssignmentTimePost atp : this.existingTimePosts){
			if(atp.getIncidentAccountCode().getId().compareTo(iac.getId())!=0){
				//if(DateUtil.isSameDate(atp.getPostStartDate(), date)){
					if(!map.containsKey(atp.getIncidentAccountCode().getId())){
						iacs.add(atp.getIncidentAccountCode());
						map.put(atp.getIncidentAccountCode().getId(), atp.getIncidentAccountCode());					
					}
				//}
			}
		}
		
		for(TimeAssignAdjust adj : this.existingAdjustments){
			if(adj.getIncidentAccountCode().getId().compareTo(iac.getId())!=0){
				//if(DateUtil.isSameDate(adj.getActivityDate(), date)){
					if(!map.containsKey(adj.getIncidentAccountCode().getId())){
						iacs.add(adj.getIncidentAccountCode());
						map.put(adj.getIncidentAccountCode().getId(), adj.getIncidentAccountCode());					
					}
				//}
			}
		}

		// add other iacs
		for(Long iacid : otherIacs){
			if(!map.containsKey(iacid)){
				IncidentAccountCode iacEntity = new IncidentAccountCodeImpl();
				iacEntity.setId(iacid);
				iacs.add(iacEntity);
				map.put(iacid, iacEntity);					
			}
		}
		
		return iacs;
	}

}
