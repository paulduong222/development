package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.cost.operations.calculator.ContractorTimeDetail;
import gov.nwcg.isuite.core.cost.utilities.StopDateResolver;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.core.vo.EarliestDateVo;
import gov.nwcg.isuite.core.vo.IacVo;
import gov.nwcg.isuite.core.vo.TimeAdjustmentVo;
import gov.nwcg.isuite.core.vo.TimePostVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;
import gov.nwcg.isuite.framework.util.BigDecimalUtility;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;

public class CostGenerator {
	private ApplicationContext context = null;
	private Boolean updateRatesOnly=false;
	private HashMap<Long,CostResourceDataVo> processedMap = new HashMap<Long,CostResourceDataVo>();
	private IncidentResourceDailyCostDao irdcDao;
	private IncidentResourceDao irDao;
	private TimePostDao tpDao;
	private TimeAssignAdjustDao taaDao;
	private Date lastActualDate=null;
	private Boolean thisDayIsLocked=false;
	
	private Collection<DailyCostVo> existingDailyCostVos = new ArrayList<DailyCostVo>();
	private Collection<TimePostVo> timePostVos = new ArrayList<TimePostVo>();
	private Collection<TimeAdjustmentVo> timeAdjustmentVos = new ArrayList<TimeAdjustmentVo>();
	private Collection<ContractorRateVo> contractorRateVos = new ArrayList<ContractorRateVo>();
	private Collection<IacVo> childIacVos = new ArrayList<IacVo>();

	public CostGenerator(ApplicationContext ctx){
		this.context=ctx;
		this.irdcDao=(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
		this.irDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
		this.tpDao=(TimePostDao)context.getBean("timePostDao");
		this.taaDao=(TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
	}

	protected String getUserSessionDbName(){
		return ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
	}
	
	public void setUpdateRatesOnly(Boolean val){
		this.updateRatesOnly=val;
	}

	public void generateCostsForParent(Long parentResourceId
										,Collection<CostResourceDataVo> costResourceDataVos) throws Exception{

		for(CostResourceDataVo vo : costResourceDataVos){
			if(vo.getResourceId().compareTo(parentResourceId)==0){
				this.generateCosts(vo, costResourceDataVos, true);
				if(LongUtility.hasValue(vo.getParentResourceId())){
					this.generateCostsForParent(vo.getParentResourceId(), costResourceDataVos);
				}
			}
		}
	}

	
	public void generateCosts(CostResourceDataVo voToProcess
								,Collection<CostResourceDataVo> costResourceDataVos
								,Boolean skipCheckSubordinates) throws DailyCostException {

		try{
			//System.out.println(voToProcess.getIncidentResourceId());
			Collection<String> thisResourceSqls=new ArrayList<String>();
			Collection<String> thisResourceExceptionSqls=new ArrayList<String>();
		
			//System.out.println("IncidentResourceId: "+voToProcess.getIncidentResourceId());
			//if(voToProcess.getIncidentResourceId().compareTo(18744L)==0){
			//	System.out.println("");
			//}

			/*
			 * If this resource has children, run costs for children first
			 */
			if(IntegerUtility.hasValue(voToProcess.getSubordinateCount()) && skipCheckSubordinates==false){
				// turn on all subordinates since this child has time postings
				// 3/26/2014 - this change is to turn on all subordinates generate costs flag = true
				// when at least one subordinate has actuals.  - meeting with Donna in sacramento
				Boolean isSubordinateGenerateCosts=false;
				if(this.hasSubordinateActualTimeData(voToProcess)==true){
					irDao.updateChildGenCostsTrue(voToProcess.getResourceId());
					isSubordinateGenerateCosts=true;
					
					// update subordinates in the costResourceDataVos
					Collection<CostResourceDataVo> newCostResourceDataVos = new ArrayList<CostResourceDataVo>();
					for(CostResourceDataVo v : costResourceDataVos){
						if(!LongUtility.hasValue(v.getResourceId())){
							//System.out.println("");
						}
						//System.out.println(v.getIncidentResourceId());
						if(LongUtility.hasValue(v.getParentResourceId()) && v.getParentResourceId().compareTo(voToProcess.getResourceId())==0){
							//System.out.println("subordinate v " + v.getIncidentResourceId() + " cost data " + v.getCostDataId());
							if(v.getIncidentResourceId().compareTo(12009L)==0){
								//System.out.println("");
							}
						}
						
						if(LongUtility.hasValue(v.getParentResourceId()) && v.getParentResourceId().compareTo(voToProcess.getResourceId())==0){
							if(!this.hasPrimaryActualTimeData(v) && BooleanUtility.isFalse(v.getGenerateCostsSys()) ){
								v.setGenerateCosts(true);
								v.setGenerateCostsString("true");
								v.setGenerateCostsSys(true);
								v.setGenerateCostsSysString("Y");
							}
						}else if(v.getResourceId().compareTo(voToProcess.getResourceId())==0){
							// turn parent off if no actual data and sysgencost is No
							if(!this.hasPrimaryActualTimeData(v) && BooleanUtility.isFalse(v.getGenerateCostsSys())
									&& BooleanUtility.isFalse(v.isDailyFormAircraft())){
								v.setGenerateCosts(false);
								v.setGenerateCostsString("false");
								v.setGenerateCostsSys(true);
								v.setGenerateCostsSysString("Y");
								voToProcess.setGenerateCosts(false);
								voToProcess.setGenerateCostsString("false");
								voToProcess.setGenerateCostsSys(true);
								voToProcess.setGenerateCostsSysString("Y");
								String updatesql="update isw_cost_data set is_generate_costs = " + (this.irdcDao.isOracleDialect() ? 0 : false ) + " " +
													" , is_generate_costs_sys = 'Y' "  +
													" where id = " + voToProcess.getCostDataId() + " ";
								thisResourceSqls.add(updatesql);
							}
						}
						
						if(LongUtility.hasValue(v.getParentResourceId()) && v.getParentResourceId().compareTo(voToProcess.getResourceId())==0){
							if(hasSubordinateActualTimeData(v) && !this.hasPrimaryActualTimeData(v)
									&& BooleanUtility.isFalse(v.isDailyFormAircraft())){
								v.setGenerateCosts(false);
								v.setGenerateCostsString("false");
								v.setGenerateCostsSys(true);
								v.setGenerateCostsSysString("Y");
								voToProcess.setGenerateCosts(false);
								voToProcess.setGenerateCostsString("false");
								voToProcess.setGenerateCostsSys(true);
								voToProcess.setGenerateCostsSysString("Y");
								String updatesql="update isw_cost_data set is_generate_costs = " + (this.irdcDao.isOracleDialect() ? 0 : false ) + " " +
													" , is_generate_costs_sys = 'Y' "  +
													" where id = " + voToProcess.getCostDataId() + " ";
								thisResourceSqls.add(updatesql);
								String updatesql2="update isw_cost_data set is_generate_costs = " + (this.irdcDao.isOracleDialect() ? 0 : false ) + " " +
								" , is_generate_costs_sys = 'Y' "  +
								" where id = " + v.getCostDataId() + " ";
								thisResourceSqls.add(updatesql2);
							}
							
						}
						
						newCostResourceDataVos.add(v);
					}
					costResourceDataVos=newCostResourceDataVos;
				}

				/*
				 * Process each child, run costs
				 */
				for(CostResourceDataVo vo : costResourceDataVos){
					if(LongUtility.hasValue(vo.getParentResourceId())){
						if(vo.getParentResourceId().compareTo(voToProcess.getResourceId())==0){
							//if(isSubordinateGenerateCosts==true){
							//	vo.setGenerateCosts(true);
							//}
							this.generateCosts(vo, costResourceDataVos, false);
						}
					}
				}
			}

			//System.out.println("IncidentResourceId: "+voToProcess.getIncidentResourceId());
			if(voToProcess.getIncidentResourceId().compareTo(10051L)==0){
				System.out.println(voToProcess.getIncidentResourceId());
			}
			System.out.println(voToProcess.getIncidentResourceId());

			/*
			 * generate costs for voToProcess if not already processed
			 */
			if(!this.processedMap.containsKey(voToProcess.getIncidentResourceId())){
				String validationError="";
				/* verify we can run costs */
				if(this.canRunCosts(voToProcess, validationError)==true){
					// init new collection
					existingDailyCostVos = new ArrayList<DailyCostVo>();

					// clear out daily cost exception 
					String s = "update isw_incident_resource set daily_cost_exception = '' where id = " + voToProcess.getIncidentResourceId() + " ";
					thisResourceSqls.add(s);
					
					if(BooleanUtility.isTrue(voToProcess.getUseActualsOnly())){
						// update all existing cost records not locked where cost level is E,U,or F and set
						// units,unitcostamount to zero, and costlevel=blank, primarytotal=0
						String sql="update isw_inc_res_daily_cost " +
								   "set cost_level = '' " +
								   ", unit_cost_amount = 0.0 " +
								   ", units = 0 " +
								   ", primary_total_amount = 0.0 " +
								   ", total_cost_amount = subordinate_total_amount " +
								   "where incident_resource_id = " + voToProcess.getIncidentResourceId() + " " +
								   "and is_locked = " + (this.irdcDao.isOracleDialect() ? 0 : false ) + " " +
								   "and cost_level in ('E','U','F') ";
						Collection<String> resetCostsqls=new ArrayList<String>();
						resetCostsqls.add(sql);
						this.irdcDao.persistSqls(resetCostsqls);
					}
					
					Date startDate=voToProcess.getAssignDate();
					Date stopDate=StopDateResolver.resolveIRStopDate(voToProcess,getUserSessionDbName());
					Date processDate=DateUtil.addMilitaryTimeToDate(startDate,"2359");
					stopDate=DateUtil.addMilitaryTimeToDate(stopDate, "2359");

					// verify this resource's assigndate is the earliest activity date
					// this is a copy from ModifyAssignDateRules.java
					// if a change is needed, make change to the rule class as well
					EarliestDateVo edv = (EarliestDateVo) irDao.getEarliestDatesByIncResId(voToProcess.getIncidentResourceId());
					SortedSet<Date> dates = new TreeSet<Date>();
					Date modifiedAssignDate;
					if (edv != null) {
						if (edv.getAssignDate() != null) dates.add(edv.getAssignDate());
						if (edv.getHiredDate() != null) dates.add(edv.getHiredDate());
						if (edv.getCheckInDate() != null) dates.add(edv.getCheckInDate());
						if (edv.getEarliestTimePostDate() != null) dates.add(edv.getEarliestTimePostDate());
						if (edv.getEarliestTimeAdjDate() != null) dates.add(edv.getEarliestTimeAdjDate());
						if (edv.getEarliestSubAssignDate() != null) dates.add(edv.getEarliestSubAssignDate());
						if(dates.size() > 0){
							modifiedAssignDate = DateUtil.addMilitaryTimeToDate(dates.first(), "2359");
							this.irDao.updateCostAssignDate2(edv.getCostDataId(),modifiedAssignDate);
							voToProcess.setAssignDate(modifiedAssignDate);
							startDate=voToProcess.getAssignDate();
							processDate=DateUtil.addMilitaryTimeToDate(startDate,"2359");
						}
					}
					
					if(DateUtil.hasValue(voToProcess.getIncidentStartDate())){
						Date incStartDate=voToProcess.getIncidentStartDate();
						incStartDate=DateUtil.addMilitaryTimeToDate(incStartDate, "2359");
//						QC#5116 - Use resource assign date to calculate costs
//						if(startDate.before(incStartDate)){
//							startDate=incStartDate;
//							processDate=DateUtil.addMilitaryTimeToDate(startDate,"2359");
//						}
					}
					
					/* load any existing daily cost records for this resource */
					existingDailyCostVos = this.irdcDao.getDailyCosts(voToProcess.getIncidentResourceId(),null, null);

					/* load any time postings and adjustments for this resource */
					timePostVos = new ArrayList<TimePostVo>();
					timeAdjustmentVos = new ArrayList<TimeAdjustmentVo>();

					if(IntegerUtility.hasValue(voToProcess.getTimePostCount()))
						timePostVos=tpDao.getTimePostingsByAssignmentTimeId(voToProcess.getAssignmentTimeId());
						
					if(IntegerUtility.hasValue(voToProcess.getAdjustmentCount()))
						this.timeAdjustmentVos = taaDao.getTimeAdjustmentsByAssignmentId(voToProcess.getAssignmentId());

					
					/* 
					 * if resource generatecosts = false and resource has time data, then set generatecosts=true
					 */
					if(BooleanUtility.isFalse(voToProcess.getGenerateCosts())){
						if(this.hasPrimaryActualTimeData(voToProcess)==true){
							voToProcess.setGenerateCosts(true);
							voToProcess.setGenerateCostsString("true");
							String updatesql="update isw_cost_data set is_generate_costs = " + (this.irdcDao.isOracleDialect() ? 1 : true ) + " " +
											"where id = " + voToProcess.getCostDataId() + " ";
							thisResourceSqls.add(updatesql);
						}
					}

					/* 
					 * if resource generatecosts = true, resource has no actuals, is parent,
					 * and subordinates have actuals , and issysgeneratecosts flag is false, then set generatecosts=false
					 * and parent's daily form is not aircraft
					 */
					//System.out.println(voToProcess.getGenerateCostsSys());
					if(BooleanUtility.isTrue(voToProcess.getGenerateCosts())
							&& BooleanUtility.isFalse(voToProcess.getGenerateCostsSys())
							&& BooleanUtility.isFalse(voToProcess.isDailyFormAircraft())){
						if(this.hasPrimaryActualTimeData(voToProcess)==false && this.hasSubordinateActualTimeData(voToProcess)==true){
							voToProcess.setGenerateCosts(false);
							voToProcess.setGenerateCostsString("false");
							voToProcess.setGenerateCostsSys(true);
							voToProcess.setGenerateCostsSysString("Y");
							String updatesql="update isw_cost_data set is_generate_costs = " + (this.irdcDao.isOracleDialect() ? 0 : false ) + " " +
												" , is_generate_costs_sys = 'Y' "  +
											" where id = " + voToProcess.getCostDataId() + " ";
							thisResourceSqls.add(updatesql);
						}
					}
					
					// if contractor, load contractor rates
					if(StringUtility.hasValue(voToProcess.getEmploymentType()) 
								&& LongUtility.hasValue(voToProcess.getContractorPaymentInfoId())
								&& voToProcess.getEmploymentType().equals("CONTRACTOR")){
						contractorRateVos = this.irdcDao.getContractorRateVos(voToProcess.getContractorPaymentInfoId());
					}

					// if this resource has subordinates, load distinct list of child iacs in child timepostings
					if(IntegerUtility.hasValue(voToProcess.getSubordinateCount())){
						childIacVos = this.irdcDao.getChildUniqueCostAcctCodeIds(voToProcess.getIncidentResourceId());
					}else
						childIacVos = new ArrayList<IacVo>();

					
					// create costs for each date
					while(!processDate.after(stopDate)){
						//System.out.println(voToProcess.getAssignmentId());
						if(voToProcess.getAssignmentId().compareTo(10012L)==0){
							//System.out.println("");
						}
						// determine if cost date is locked
						this.thisDayIsLocked=false;
						for(DailyCostVo dcvo : existingDailyCostVos){
							if(DateUtil.isSameDate(processDate, dcvo.getActivityDate())){
								if(BooleanUtility.isTrue(dcvo.getIsLocked()))
									this.thisDayIsLocked=true;
							}
						}
						if(thisDayIsLocked==false){
							Collection<String> daySqls = this.generateDayCosts(processDate
																				, voToProcess);
							thisResourceSqls.addAll(daySqls);
						}
						processDate=DateUtil.addDays(processDate, 1);
					}

						/* persist the cost sqls for the resource */
						if(CollectionUtility.hasValue(thisResourceSqls)){
							this.irdcDao.persistSqls(thisResourceSqls);
						}

						/* if this resource is a parent, update subordinate and resource total amounts*/
						if(IntegerUtility.hasValue(voToProcess.getSubordinateCount())){
							this.updateParentTotals(voToProcess);
						}
						
						/* delete any cost records before start date or after stop date */
						this.deleteBeforeAndAfter(voToProcess, startDate, stopDate);
				}else{
					if(StringUtility.hasValue(validationError)){
						String s = "update isw_incident_resource set daily_cost_exception = '"+validationError+"' where id = " + voToProcess.getIncidentResourceId() + " ";
						thisResourceExceptionSqls.add(s);
						this.irdcDao.persistSqls(thisResourceExceptionSqls);
					}
				}

				processedMap.put(voToProcess.getIncidentResourceId(), voToProcess);
				//System.out.println("Processed Count: "+processedMap.size());
			}
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}

	/**
	 * Returns if costs can be generated.
	 * 
	 * @param vo
	 * @return
	 */
	private Boolean canRunCosts(CostResourceDataVo vo, String validationError){

		//if(!StringUtility.hasValue(vo.getAgencyCode()))
		//	return false;

		if(StringUtility.hasValue(vo.getStatus())){
			if(!vo.getStatus().equalsIgnoreCase("F")){
			//if(vo.getStatus().equalsIgnoreCase("C") || vo.getStatus().equalsIgnoreCase("P")){
				// ok
			}else{
				validationError="Status is F";
				return false;
			}
		}else{
			validationError="Status is F";
			return false;
		}
		
		if(!DateUtil.hasValue(vo.getAssignDate())){
			validationError="Missing Check-In/Assign Date";
			return false;
		}
		
		// have to have a default incident account code id
		if(!LongUtility.hasValue(vo.getDefIncidentAccountCodeId())){
			validationError="Missing Default Accounting Code";
			return false;
		}
		

		return true;
	}

	/**
	 * Get time postings for the costDate from timePost Collection.
	 * 
	 * @param costDate
	 * @param timePostVos
	 * @return
	 */
	private Collection<TimePostVo> getTimePostVosForDate(Date costDate,Collection<TimePostVo> timePostVos){
		Collection<TimePostVo> vos = new ArrayList<TimePostVo>();

		for(TimePostVo v : timePostVos){
			if(DateUtil.isSameDate(v.getPostStartDate(),costDate))
				vos.add(v);
		}

		return vos;
	}

	private Collection<TimeAdjustmentVo> getTimeAdjustmentVosForDate(Date costDate,Collection<TimeAdjustmentVo> timeAdjustmentVos){
		Collection<TimeAdjustmentVo> vos = new ArrayList<TimeAdjustmentVo>();

		for(TimeAdjustmentVo v : timeAdjustmentVos){
			if(DateUtil.isSameDate(v.getActivityDate(),costDate))
				vos.add(v);
		}

		return vos;
	}

	private Collection<Long> getPreviousDistinctTimeIacs(Date costDate,Collection<TimePostVo> timePostVos,Collection<TimeAdjustmentVo> timeAdjustmentVos){
		Collection<Long> iacids= new ArrayList<Long>();

		for(TimePostVo v : timePostVos){
			if(DateUtil.isSameDate(v.getPostStartDate(),costDate)
								|| costDate.after(v.getPostStartDate())){
				Boolean bfound=false;
				for(Long id : iacids){
					if(id.compareTo(v.getIncidentAccountCodeId())==0)
						bfound=true;
				}
				
				if(!bfound)
					iacids.add(v.getIncidentAccountCodeId());
			}
		}

		for(TimeAdjustmentVo v : timeAdjustmentVos){
			if(DateUtil.isSameDate(v.getActivityDate(),costDate)
								|| costDate.after(v.getActivityDate())){
				Boolean bfound=false;
				for(Long id : iacids){
					if(id.compareTo(v.getIncidentAccountCodeId())==0)
						bfound=true;
				}
				
				if(!bfound)
					iacids.add(v.getIncidentAccountCodeId());
			}
		}

		return iacids;
	}
	
	private Boolean hasPreviousDayCostsForCopy(Date costDate
												,Collection<DailyCostVo> dailyCostVos
												,String costLevels) throws Exception{

		Date previousDay = DateUtil.subtractDaysFromDate(costDate, 1);

		// check for previous day cost record where costlevel is not M or U
		for(DailyCostVo v : dailyCostVos){
			if(DateUtil.isSameDate(previousDay,v.getActivityDate())){
				// cost level must be either an A,E,or F
				if(StringUtility.hasValue(v.getCostLevel())
						&& this.costLevelContains(v.getCostLevel(), costLevels)){
						//&& (!v.getCostLevel().equals("M") && !v.getCostLevel().equals("U"))){
					return true;
				}
			}
		}

		return false;
	}
	

	private Boolean hasPreviousActualTimeRecords(Date costDate) throws Exception{
		
		lastActualDate=null;
		
		// check if there is a time posting or adjustment before costdate
		for(TimePostVo v : this.timePostVos){
			Date postDate=v.getPostStartDate();
			postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");
			if(postDate.before(costDate) && (lastActualDate==null || postDate.after(lastActualDate))){
				lastActualDate=postDate;
			}
		}

		for(TimeAdjustmentVo v : this.timeAdjustmentVos){
			Date postDate=v.getActivityDate();
			postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");
			if(postDate.before(costDate) && (lastActualDate==null || postDate.after(lastActualDate))){
				lastActualDate=postDate;
			}
		}

		if(null!=lastActualDate)
			return true;
		else
			return false;
	}

	private Boolean hasUnlockedPreviousActualCostRecords(Date costDate) throws Exception{
		
		lastActualDate=null;
		
		// check if there is a time posting or adjustment before costdate
		for(TimePostVo v : this.timePostVos){
			Date postDate=v.getPostStartDate();
			postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");
			if(postDate.before(costDate) && (lastActualDate==null || postDate.after(lastActualDate))){
				lastActualDate=postDate;
			}
		}

		for(TimeAdjustmentVo v : this.timeAdjustmentVos){
			Date postDate=v.getActivityDate();
			postDate=DateUtil.addMilitaryTimeToDate(postDate, "2359");
			if(postDate.before(costDate) && (lastActualDate==null || postDate.after(lastActualDate))){
				lastActualDate=postDate;
			}
		}

		if(null!=lastActualDate){
			for(DailyCostVo dcvo : existingDailyCostVos){
				if(DateUtil.isSameDate(lastActualDate, dcvo.getActivityDate())){
					if(BooleanUtility.isFalse(dcvo.getIsLocked()))
						return true;
				}
			}
			return false;
		}else
			return true;
	}

	private Boolean hasPreviousDayCostRecordLocked(Date costDate) throws Exception{
		Date dt=DateUtil.subtractDaysFromDate(costDate, 1);
		
		for(DailyCostVo dcvo : existingDailyCostVos){
			if(DateUtil.isSameDate(dt, dcvo.getActivityDate())){
				if(BooleanUtility.isTrue(dcvo.getIsLocked()))
					return true;
			}
		}

		return false;
	}

	private Collection<String> generateDayCosts(Date costDate
												,CostResourceDataVo crdVo) throws Exception {
		Collection<String> daySqls = new ArrayList<String>();
		Collection<TimePostVo> thisDayTimePosts = this.getTimePostVosForDate(costDate, timePostVos);
		Collection<TimeAdjustmentVo> thisDayAdjustments=this.getTimeAdjustmentVosForDate(costDate, timeAdjustmentVos);

		//System.out.println("generateDayCosts: " + DateUtil.toDateString(costDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));

		// remove any F records where previous actual no longer exists
		Collection<Long> previousActualIacIds=this.getPreviousDistinctTimeIacs(costDate, timePostVos, timeAdjustmentVos);
		if(CollectionUtility.hasValue(previousActualIacIds)){
			Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
			for(DailyCostVo v : existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(), costDate)
							&& this.costLevelContains(v.getCostLevel(), "FN")==true){
					Boolean isInvalidFRecord=true;
					for(Long id : previousActualIacIds){
						if(id.compareTo(v.getIncidentAccountCodeId())==0
								&& StringUtility.hasValue(v.getCostLevel())){
							isInvalidFRecord=false;
							break;
						}
					}
					if(isInvalidFRecord==true){
						daySqls.add("delete from isw_inc_res_daily_cost where id = " + v.getId() + " ");
					}else
						newExistingDailyCostVos.add(v);
				}else
					newExistingDailyCostVos.add(v);
			}
			this.existingDailyCostVos=newExistingDailyCostVos;
		}else
			daySqls.addAll(this.deleteByFilter(costDate, "F", null));


		if(crdVo.getGenerateCosts()==false){
			// remove any existing estimates
			Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
			daySqls.addAll(this.deleteByFilter(costDate, "EU",null));
		}
		
		
		if(CollectionUtility.hasValue(thisDayTimePosts) || CollectionUtility.hasValue(thisDayAdjustments)){
			// create actuals
			daySqls.addAll(this.createDayActuals(costDate, crdVo, thisDayTimePosts, thisDayAdjustments));
		}else if(!CollectionUtility.hasValue(thisDayTimePosts) 
					&& this.hasPreviousActualTimeRecords(costDate)==false
					&& !CollectionUtility.hasValue(thisDayAdjustments)
					&& BooleanUtility.isTrue(crdVo.getUseActualsOnly())){
			daySqls.addAll(this.createDayActuals(costDate, crdVo, thisDayTimePosts, thisDayAdjustments));
		}else{
			/*
			 * no time postings/adjustmentsfor the day, remove any A's
			 */
			daySqls.addAll(this.deleteByFilter(costDate, "A", null));
			
			/*
			 * If there are timepostings/adjustment for a date prior to this cost date
			 * , then try to create a flowdown record(s)
			 * else
			 * 		if there are previous day costs create flowdown
			 * else
			 * 		create estimate if applicable (generate costs=true)
			 *
			 */
			if(this.hasPreviousActualTimeRecords(costDate)==true
					&& this.hasPreviousDayCostRecordLocked(costDate)==false
					&& this.hasUnlockedPreviousActualCostRecords(costDate)==true){
				// create flowdown from last actual time records
				daySqls.addAll(this.createDayFlowdownFromLastActual(costDate,crdVo));

				
			}else if(this.hasPreviousDayCostsForCopy(costDate, existingDailyCostVos,"AEF")==true){
				Date previousDate=DateUtil.subtractDaysFromDate(costDate, 1);

				/*
				 * for each previous day cost A,E,F, create a corresponding one for this costDate.
				 */
				Collection<DailyCostVo> previousDayCostVos = new ArrayList<DailyCostVo>();
				for(DailyCostVo v : existingDailyCostVos){
					if(DateUtil.isSameDate(v.getActivityDate(), previousDate)
								&& this.costLevelContains(v.getCostLevel(), "AEFN")==true){
						if (v.getCostLevel() != null) {
							if (!v.getCostLevel().equals("")){
								previousDayCostVos.add(v);
							}
						}
					}
				}
				// create flowdown for all valid previous Day Costs
				daySqls.addAll(this.createDayFlowdownCopy(costDate, crdVo, previousDayCostVos));

			}else{
				// create estimates
				if(BooleanUtility.isTrue(crdVo.getGenerateCosts()) && BooleanUtility.isFalse(crdVo.getUseActualsOnly())){
					daySqls.addAll(this.createDayEstimates(costDate, crdVo, thisDayTimePosts, thisDayAdjustments));
				}else{
					if(BooleanUtility.isFalse(crdVo.getGenerateCosts())){
						// delete all estimates for this resource
						daySqls.add("delete from isw_inc_res_daily_cost where incident_resource_id="+crdVo.getIncidentResourceId()+" and cost_level in ('E','U') ");

						// remove from existingdailycosts
						Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
						for(DailyCostVo v : existingDailyCostVos){
							if(DateUtil.isSameDate(costDate, v.getActivityDate()) 
									&& this.costLevelContains(v.getCostLevel(), "EU")){
								// skip
							}else
								newExistingDailyCostVos.add(v);
						}
						existingDailyCostVos=newExistingDailyCostVos;
					}
				}
				
			}
		}

		// if this resource has subordinates, load distinct list of child iacs for this costDate
		// create any empty placeholder records for roll up amounts if an existing cost record does
		// not already exists
		Collection<Long> childIACs = IacVo.getIdsByDate(costDate, this.childIacVos);
		if(IntegerUtility.hasValue(crdVo.getSubordinateCount())){
			if(CollectionUtility.hasValue(childIACs)){
				daySqls.addAll(this.createDayRollupPlaceholders(costDate, crdVo, childIACs));
			}
		}

		// always remove any invalid placeholders for all resources
		// since we do not know if subordinates were removed from parent roster
		daySqls.addAll(this.removeDayRollupPlaceholders(costDate, crdVo, childIACs));
		
		return daySqls;
	}

	private Collection<String> createDayEstimates(Date costDate
												,CostResourceDataVo crdVo
												,Collection<TimePostVo> thisDayTimePostVos
												,Collection<TimeAdjustmentVo> thisDayTimeAdjustmentVos) throws Exception {
		Collection<String> estimateSqls = new ArrayList<String>();
		String resourceCostType=crdVo.getResourceCostType();

		// verify generate costs flag is true
		if(BooleanUtility.isTrue(crdVo.getGenerateCosts())){
			// init new estimate DailyCostvo
			DailyCostVo estimateVo=new DailyCostVo();
			Boolean isNew=true;
			Boolean isExisting=false;
			Boolean isRateChanged=false;
			
			// check for an existing estimate record
			for(DailyCostVo v : existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(), costDate)
						&& (StringUtility.hasValue(v.getCostLevel()) 
								&& (v.getCostLevel().equals("E") || v.getCostLevel().equals("F") || v.getCostLevel().equals("U") ) ) ){
					estimateVo=v;
					isExisting=true;
					if(v.getUnitCostAmount().compareTo(crdVo.getRate())==0)
						isNew=false;
					else
						isRateChanged=true;
					break;
				}
			}

			if(BooleanUtility.isTrue(isNew)){
				/*
				 * If there is an existing one for this day,
				 * use it as is
				 */
				if(isExisting==true){
					// do nothing, leave existing one alone
					
					// 9/23/2014 defect 4525
					// if resource has default accrual code and cost record not locked, reset to resource's default
					if(null != estimateVo && BooleanUtility.isFalse(estimateVo.getIsLocked())){
						
						// dan - commenting out per donna emails 12/30/2014
						if(isRateChanged==true){
							/*
							estimateVo.setUnitCostAmount(crdVo.getRate());
							BigDecimal total = estimateVo.getUnitCostAmount().multiply(estimateVo.getUnits());
							estimateVo.setPrimaryTotalAmount(total);
							estimateVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
							total.add(estimateVo.getSubordinateTotalAmount());

							estimateVo.setTotalCostAmount(total);
							*/
						}
						
						if(LongUtility.hasValue(crdVo.getAccrualCodeId())){
							estimateVo.setAccrualCodeId(crdVo.getAccrualCodeId());
						}
						
						String sql=estimateVo.toSql(this.irdcDao.isOracleDialect());
						estimateSqls.add(sql);
						
						String sqlLock="update isw_inc_res_daily_cost ";
						if(BooleanUtility.isTrue(estimateVo.getIsLocked())){
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
						}else{
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
						}
						sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
						 "  to_date('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
						 "and incident_resource_id = " + estimateVo.getIncidentResourceId() + " "+
						 "";
						estimateSqls.add(sqlLock);
					}
					
				}else{
					Boolean usingCopy=false;
					
					// check if we have a previous day cost record E or U 
					if(this.hasPreviousDayCostsForCopy(costDate, existingDailyCostVos,"EU")==true
							&& !crdVo.getResourceCostType().equals("AIRCRAFT")){
						Date previousDate=DateUtil.subtractDaysFromDate(costDate, 1);
						
						/*
						 * for each previous day cost A,E,F, create a corresponding one for this costDate.
						 */
						for(DailyCostVo v : existingDailyCostVos){
							if(DateUtil.isSameDate(v.getActivityDate(), previousDate)
										&& this.costLevelContains(v.getCostLevel(), "EU")==true){
								estimateVo=v.clone();
								estimateVo.setId(null);
								estimateVo.setActivityDate(costDate);
								estimateVo.setIsLocked(false);
								estimateVo.setIsFlowdown(false);
								usingCopy=true;
								break;
							}
						}
					}

					// populate
					estimateVo.setIsDirty(true);
					estimateVo.setActivityDate(costDate);
					estimateVo.setIncidentResourceId(crdVo.getIncidentResourceId());
					estimateVo.setIncidentAccountCodeId(crdVo.getDefIncidentAccountCodeId());
					estimateVo.setCostGroupId(crdVo.getCostGroupId()); 
					estimateVo.setResourceCostType(resourceCostType);
					estimateVo.setAccrualCodeId(crdVo.getAccrualCodeId());
					estimateVo.setIsLocked(false);
					estimateVo.setIsFlowdown(false);
					if(usingCopy==false){
						estimateVo.setCostLevel("E");
						estimateVo.setUnits(BigDecimal.valueOf(0.0));
						estimateVo.setUnitCostAmount(BigDecimal.valueOf(0.0));
						estimateVo.setPrimaryTotalAmount(BigDecimal.valueOf(0.0));
						
						if(resourceCostType.equals("RESOURCE")){
							estimateVo.setUnits(new BigDecimal(crdVo.getUnits()));

							//if(DecimalUtil.hasValue(crdVo.getStateCustomRate())){
							//	estimateVo.setUnitCostAmount(crdVo.getStateCustomRate());
							//}else{
								estimateVo.setUnitCostAmount(crdVo.getRate());
							//}
							
							estimateVo.setRateType(crdVo.getRateType());

							BigDecimal total = estimateVo.getUnitCostAmount().multiply(estimateVo.getUnits());
							estimateVo.setPrimaryTotalAmount(total);
							estimateVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
							total.add(estimateVo.getSubordinateTotalAmount());

							estimateVo.setTotalCostAmount(total);
						}else if(resourceCostType.equals("AIRCRAFT")){
							estimateVo.setRateType("DAILY");
							estimateVo.setUnits(BigDecimal.valueOf(1.0));
						}
					}

					existingDailyCostVos.add(estimateVo);
					String sql=estimateVo.toSql(this.irdcDao.isOracleDialect());
					estimateSqls.add(sql);

					String sqlLock="update isw_inc_res_daily_cost ";
					if(BooleanUtility.isTrue(estimateVo.getIsLocked())){
						 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
					}else{
						 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
					}
					sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
					 "  to_date('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
					 "and incident_resource_id = " + estimateVo.getIncidentResourceId() + " "+
					 "";
					estimateSqls.add(sqlLock);
				}
				
			}else{
				// 9/23/2014 defect 4525
				// if resource has default accrual code and cost record not locked, reset to resource's default
				if(null != estimateVo && BooleanUtility.isFalse(estimateVo.getIsLocked())){
					if(LongUtility.hasValue(crdVo.getAccrualCodeId())){
						estimateVo.setAccrualCodeId(crdVo.getAccrualCodeId());
						String sql=estimateVo.toSql(this.irdcDao.isOracleDialect());
						estimateSqls.add(sql);
						
						String sqlLock="update isw_inc_res_daily_cost ";
						if(BooleanUtility.isTrue(estimateVo.getIsLocked())){
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
						}else{
							 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
						}
						sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
						 "  to_date('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
						 "and incident_resource_id = " + estimateVo.getIncidentResourceId() + " "+
						 "";
						estimateSqls.add(sqlLock);
					}
				}
				
			}
			
		}else{
			/*
			 * Remove any existing "E" cost records 
			 */
			Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
			for(DailyCostVo v : this.existingDailyCostVos){
				if(LongUtility.hasValue(v.getId()) && DateUtil.isSameDate(v.getActivityDate(), costDate)
						&& this.costLevelContains(v.getCostLevel(),"E")){
					// remove it
					String sql2 = "delete from isw_inc_res_daily_cost where id="+v.getId();
					estimateSqls.add(sql2);
				}else
					newExistingDailyCostVos.add(v);
			}
			this.existingDailyCostVos=newExistingDailyCostVos;
		}
		
		return estimateSqls;
	}

	private Collection<String> createDayFlowdownFromLastActual(Date costDate
																,CostResourceDataVo crdVo) throws Exception {
		Collection<String> flowdownSqls = new ArrayList<String>();
		
		/*
		 * for each actual on same date as lastactualdate, add to costsToflowdown collection
		 */
		Collection<DailyCostVo> costsToFlowdown = new ArrayList<DailyCostVo>();
		for(DailyCostVo v : this.existingDailyCostVos){
			if(DateUtil.isSameDate(v.getActivityDate(),this.lastActualDate)
					&& this.costLevelContains(v.getCostLevel(), "AU")==true){
				costsToFlowdown.add(v);
			}
		}
		
		/*
		 * For each actual in costsToFlowdown collection, create a corresponding flowdown record
		 * if applicable
		 */
		for(DailyCostVo f : costsToFlowdown){
			DailyCostVo flowDownVo = null;
			
			// use an exsting one if available
			for(DailyCostVo v : this.existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(),costDate)
						&& f.getIncidentAccountCodeId().compareTo(v.getIncidentAccountCodeId())==0
						&& (!StringUtility.hasValue(v.getCostLevel()) || this.costLevelContains(v.getCostLevel(), "EUF")==true)){
					flowDownVo=v;
					break;
				}
			}
			
			if(null != flowDownVo && this.costLevelContains(flowDownVo.getCostLevel(), "U")==true
						&& BooleanUtility.isTrue(flowDownVo.getIsLocked())){
				// skip it
			}else if(null != flowDownVo && BooleanUtility.isTrue(crdVo.getUseActualsOnly())){
				// skip it
			}else{
				Boolean isNew=false;
				if(null==flowDownVo){
					flowDownVo=new DailyCostVo();
					isNew=true;
				}
				
				// create a flowdown
				Long origId=flowDownVo.getId();
				Boolean origLocked=flowDownVo.getIsLocked();
				Long origCostGroupId=0L;
				if(LongUtility.hasValue(flowDownVo.getCostGroupId()))
					origCostGroupId=flowDownVo.getCostGroupId();
				else{
					if(isNew==true 
							&& this.costLevelContains(f.getCostLevel(), "A")
							&& LongUtility.hasValue(f.getCostGroupId()) ){
						origCostGroupId=f.getCostGroupId();
					}
				}
				flowDownVo=f.clone();
				flowDownVo.setId(origId);
				flowDownVo.setIsLocked(origLocked);
				
				if(LongUtility.hasValue(origCostGroupId)){
					flowDownVo.setCostGroupId(origCostGroupId);
				}else
					flowDownVo.setCostGroupId(null);
				
				if(this.costLevelContains(f.getCostLevel(), "A"))
					flowDownVo.setCostLevel("F");
				else if(this.costLevelContains(f.getCostLevel(), "U"))
					flowDownVo.setCostLevel("U");
				
				if(BooleanUtility.isTrue(crdVo.getUseActualsOnly())){
					flowDownVo.setCostLevel("");
					flowDownVo.setUnitCostAmount(BigDecimal.valueOf(0));
					flowDownVo.setUnits(BigDecimal.valueOf(0));
					flowDownVo.setAdjustmentAmount(BigDecimal.valueOf(0));
					flowDownVo.setPrimaryTotalAmount(BigDecimal.valueOf(0));
					flowDownVo.setTotalCostAmount(BigDecimal.valueOf(0));
				}
				flowDownVo.setActivityDate(costDate);
				
				String sql=flowDownVo.toSql(this.irdcDao.isOracleDialect());
				flowdownSqls.add(sql);

				String sqlLock="update isw_inc_res_daily_cost ";
				if(BooleanUtility.isTrue(flowDownVo.getIsLocked())){
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
				}else{
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
				}
				sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
				 "  to_date('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
				 "and incident_resource_id = " + flowDownVo.getIncidentResourceId() + " "+
				 "";
				flowdownSqls.add(sqlLock);
				
				if(!LongUtility.hasValue(flowDownVo.getId())){
					existingDailyCostVos.add(flowDownVo);
				}else{
					//update existingDailyCostVo collection
					Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
					for(DailyCostVo v : existingDailyCostVos){
						if(LongUtility.hasValue(v.getId()) && v.getId().compareTo(flowDownVo.getId())==0)
							newExistingDailyCostVos.add(flowDownVo);
						else
							newExistingDailyCostVos.add(v);
					}
					existingDailyCostVos=newExistingDailyCostVos;
				}
			}
		}
		
		/*
		 * Remove any existing cost records E,F,or U with iac's not valid anymore
		 */
		Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
		for(DailyCostVo v : this.existingDailyCostVos){
			if(LongUtility.hasValue(v.getId()) && DateUtil.isSameDate(v.getActivityDate(), costDate)){
				// is this dailycostvo in the flowdown collection
				Boolean bFound=false;
				for(DailyCostVo f : costsToFlowdown){
					if(f.getIncidentAccountCodeId().compareTo(v.getIncidentAccountCodeId())==0){
						bFound=true;
						break;
					}
				}
				if(bFound==false){
					Boolean bRemove=false;
					// is v.costlevel E,F,or U (notlocked)
					if(this.costLevelContains(v.getCostLevel(), "EF")){
						// remove it
						bRemove=true;
					}else if(this.costLevelContains(v.getCostLevel(), "U") && BooleanUtility.isFalse(v.getIsLocked())){
						// remove it
						bRemove=true;
					}

					if(bRemove==true){
						String sql = "delete from isw_inc_res_daily_cost where id="+v.getId();
						flowdownSqls.add(sql);
					}else{
						newExistingDailyCostVos.add(v);
					}
				}else
					newExistingDailyCostVos.add(v);
			}else
				newExistingDailyCostVos.add(v);
		}
		
		return flowdownSqls;
	}
	
	/**
	 * This method is called when there are 1 or more existing daily cost
	 * records for the previous day with a cost level of A,E, or F.
	 * For each previous day record with A,E,F, create a flowdown record.
	 * 
	 * @param costDate
	 * @param crdVo
	 * @param existingDailyCostVos
	 * @return
	 * @throws Exception
	 */
	private Collection<String> createDayFlowdownCopy(Date costDate
														,CostResourceDataVo crdVo
														,Collection<DailyCostVo> previousDayCostVos) throws Exception {
		Collection<String> flowdownSqls = new ArrayList<String>();
		
		// for previous day cost, create/update one for this cost date
		for(DailyCostVo previousDayCost : previousDayCostVos){

			DailyCostVo thisDayCostVo=null;
			
			/*
			 * Check if we already a cost record for the:
			 *  costDate, incidentAccountCodeId
			 */
			for(DailyCostVo v : existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(), costDate)
							&& v.getIncidentAccountCodeId().compareTo(previousDayCost.getIncidentAccountCodeId())==0) {
							//&& (this.costLevelContains(v.getCostLevel(),"AEFU")==true || v.getCostLevel() == null || v.getCostLevel().equals(""))){
							//&& this.costLevelContains(v.getCostLevel(),"AEFU")==true){
					thisDayCostVo=v;
					break;
				}
			}

			Boolean isNew=false;
			if(null==thisDayCostVo){ 
				thisDayCostVo=new DailyCostVo();
				isNew=true;
			}
			
			if(BooleanUtility.isFalse(thisDayCostVo.getIsLocked())){
				Boolean bLeaveRecordAlone=false;
				
				if(LongUtility.hasValue(thisDayCostVo.getId())){
					// if thisDayCost costlevel=U, leave it
					if(this.costLevelContains(thisDayCostVo.getCostLevel(), "U")){
						bLeaveRecordAlone=true;
					}
				}
				
				/* if thisDayCostVo is a new vo
				 * or if previous day cost level is an A,E or F, 
				 * then create copy of it
				 */
				if(!LongUtility.hasValue(thisDayCostVo.getId())
							|| (this.costLevelContains(previousDayCost.getCostLevel(), "AEF")==true
									&& bLeaveRecordAlone==false)){
					// create a copy from previousDayCost
					Long origId = thisDayCostVo.getId();
					Long origCostGroupId=0L;
					if(isNew==false){
						if(LongUtility.hasValue(thisDayCostVo.getCostGroupId()))
							origCostGroupId=thisDayCostVo.getCostGroupId();
						else{
							if(LongUtility.hasValue(previousDayCost.getCostGroupId())
									&& this.costLevelContains(previousDayCost.getCostLevel(), "AF")){
								origCostGroupId=previousDayCost.getCostGroupId();
							}
						}
					}
					
					Boolean origLocked=thisDayCostVo.getIsLocked();
					if(LongUtility.hasValue(thisDayCostVo.getAccrualCodeId())){
						Long acid = thisDayCostVo.getAccrualCodeId();
						thisDayCostVo=previousDayCost.clone();
						thisDayCostVo.setAccrualCodeId(acid);
					}else{
						thisDayCostVo=previousDayCost.clone();
					}
					
					// if cost record not locked, reset accrual code id back to res default
					if(LongUtility.hasValue(crdVo.getAccrualCodeId()) 
							&& origLocked==false){
						thisDayCostVo.setAccrualCodeId(crdVo.getAccrualCodeId());
					}
					
					if(null != previousDayCost.getCostLevel()){
						if(previousDayCost.getCostLevel().equals("A"))
							thisDayCostVo.setCostLevel("F");
					}
					
					thisDayCostVo.setIsDirty(true);
					thisDayCostVo.setId(origId);
					thisDayCostVo.setIsLocked(origLocked);
					thisDayCostVo.setActivityDate(costDate);
					
					if(isNew==false){
						thisDayCostVo.setCostGroupId(origCostGroupId);
					}
					
					String sql=thisDayCostVo.toSql(this.irdcDao.isOracleDialect());
					flowdownSqls.add(sql);
					
					String sqlLock="update isw_inc_res_daily_cost ";
					if(BooleanUtility.isTrue(thisDayCostVo.getIsLocked())){
						 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
					}else{
						 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
					}
					sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
					 "  to_date('"+DateUtil.toDateString(costDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
					 "and incident_resource_id = " + thisDayCostVo.getIncidentResourceId() + " "+
					 "";
					flowdownSqls.add(sqlLock);
					

					if(!LongUtility.hasValue(thisDayCostVo.getId())){
						existingDailyCostVos.add(thisDayCostVo);
					}else{
						//update existingDailyCostVo collection
						Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
						for(DailyCostVo v : existingDailyCostVos){
							if(LongUtility.hasValue(v.getId()) && v.getId().compareTo(thisDayCostVo.getId())==0)
								newExistingDailyCostVos.add(thisDayCostVo);
							else
								newExistingDailyCostVos.add(v);
						}
						existingDailyCostVos=newExistingDailyCostVos;
					}
				}else{
					/* thisDayCostVo is an existing cost record with an id
					 */
					// do nothing, leave the record as is
				}
				
				
			}

		}
		
		return flowdownSqls;
	}

	private Collection<String> createDayRollupPlaceholders(Date costDate
															,CostResourceDataVo crdVo
															,Collection<Long> childIacs) throws Exception {
		Collection<String> placeholderSqls = new ArrayList<String>();
		
		/*
		 * For each childIac, create an empty cost record for roll up purposes
		 * only if one does not already exists.
		 */
		for(Long childIac : childIacs){
			Boolean bExists=false;
			
			for(DailyCostVo vo : this.existingDailyCostVos){
				if(DateUtil.isSameDate(costDate, vo.getActivityDate())
						&& childIac.compareTo(vo.getIncidentAccountCodeId())==0){
					bExists=true;
					break;
				}
			}
			
			if(bExists==false){
				// create placeholder record
				DailyCostVo placeholderVo = new DailyCostVo();
				placeholderVo.setId(null);
				placeholderVo.setIsDirty(true);
				placeholderVo.setActivityDate(costDate);
				placeholderVo.setCostLevel("");
				placeholderVo.setIncidentResourceId(crdVo.getIncidentResourceId());
				placeholderVo.setIncidentAccountCodeId(childIac);
				placeholderVo.setCostGroupId(null); // todo
				placeholderVo.setResourceCostType(crdVo.getResourceCostType());
				placeholderVo.setAccrualCodeId(crdVo.getAccrualCodeId());
				placeholderVo.setIsLocked(false);
				placeholderVo.setIsFlowdown(false);
				placeholderVo.setRateType("");
				placeholderVo.setUnits(BigDecimal.valueOf(0.0));
				placeholderVo.setUnitCostAmount(BigDecimal.valueOf(0.0));
				placeholderVo.setPrimaryTotalAmount(BigDecimal.valueOf(0.0));
				placeholderVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
				placeholderVo.setTotalCostAmount(BigDecimal.valueOf(0.0));

				String sql=placeholderVo.toSql(this.irdcDao.isOracleDialect());
				placeholderSqls.add(sql);
				existingDailyCostVos.add(placeholderVo);
			}
			
		}

		return placeholderSqls;
	}
	
	private Collection<String> removeDayRollupPlaceholders(Date costDate
															,CostResourceDataVo crdVo
															,Collection<Long> childIacs) throws Exception {
		Collection<String> placeholderSqls = new ArrayList<String>();

		/*
		 * Remove any placeholder records where
		 * cost level is empty
		 * and the incidentaccountcodeid is not in the childIacs ids list
		 * and resource has subordinates
		 */
		Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
		for(DailyCostVo vo : this.existingDailyCostVos){
			if(DateUtil.isSameDate(costDate, vo.getActivityDate())
					&& !StringUtility.hasValue(vo.getCostLevel())){

				if(IntegerUtility.hasValue(crdVo.getSubordinateCount())){
					Boolean isValidPlaceholder=false;
					for(Long childIac : childIacs){
						if(childIac.compareTo(vo.getIncidentAccountCodeId())==0){
							isValidPlaceholder=true;
						}
					}
					if(isValidPlaceholder==false){
						// remove it
						String sql = "delete from isw_inc_res_daily_cost where id = " + vo.getId();
						placeholderSqls.add(sql);
					}else
						newExistingDailyCostVos.add(vo);
				}else
					newExistingDailyCostVos.add(vo);
			}else
				newExistingDailyCostVos.add(vo);
		}
		
		this.existingDailyCostVos=newExistingDailyCostVos;

		/*
		 * Remove any placeholder records where
		 * cost level is empty and a corresponding cost record
		 * now exists with the same incident account code id
		 */
		newExistingDailyCostVos = new ArrayList<DailyCostVo>();
		for(DailyCostVo vo : this.existingDailyCostVos){
			if(DateUtil.isSameDate(costDate, vo.getActivityDate())
						&& !StringUtility.hasValue(vo.getCostLevel())){

				Boolean isValidPlaceholder=true;
				
				// do we now have one with a costlevel?
				for(DailyCostVo vo2 : this.existingDailyCostVos){
					if(DateUtil.isSameDate(costDate, vo2.getActivityDate())
							&& StringUtility.hasValue(vo2.getCostLevel())
							&& vo2.getIncidentAccountCodeId().compareTo(vo.getIncidentAccountCodeId())==0){
						isValidPlaceholder=false;
					}
				}
				
				if(isValidPlaceholder==false){
					// remove it
					String sql = "delete from isw_inc_res_daily_cost where id = " + vo.getId();
					placeholderSqls.add(sql);
				}else
					newExistingDailyCostVos.add(vo);
			}else
				newExistingDailyCostVos.add(vo);
		}
		
		this.existingDailyCostVos=newExistingDailyCostVos;

		return placeholderSqls;
	}

	/*
	 * 
	 */
	private Collection<String> createDayActuals(Date costDate
												,CostResourceDataVo crdVo
												,Collection<TimePostVo> thisDayTimePostVos
												,Collection<TimeAdjustmentVo> thisDayTimeAdjustmentVos) throws Exception {
		Collection<String> actualSqls = new ArrayList<String>();
		String resourceCostType=crdVo.getResourceCostType();
		
		/* get list of distinct iacIds used in time records */
		Collection<Long> iacIds = this.getDistinctIacs(costDate, thisDayTimePostVos, thisDayTimeAdjustmentVos);
		
		if(BooleanUtility.isTrue(crdVo.getUseActualsOnly())
				&& !CollectionUtility.hasValue(iacIds)){
			iacIds.add(crdVo.getDefIncidentAccountCodeId());
		}
		
		/*
		 * Loop through distinct iacids. 
		 * Use existing cost record if found,
		 * otherwise create a new record
		 */
		for(Long iacId : iacIds){
			DailyCostVo thisIacDailyCostVo=null;
			
//			for(DailyCostVo dcvo : existingDailyCostVos){
//				if(DateUtil.isSameDate(costDate, dcvo.getActivityDate())
//						&& dcvo.getIncidentAccountCodeId().compareTo(iacId)==0
//						//&& (StringUtility.hasValue(dcvo.getCostLevel()) && !dcvo.getCostLevel().equals("M") )){
//						&& (!dcvo.getCostLevel().equals("M") )){
//					thisIacDailyCostVo=dcvo;
//					break;
//				}
//			}
			
			for(DailyCostVo dcvo : existingDailyCostVos){
				if(DateUtil.isSameDate(costDate, dcvo.getActivityDate())
						&& dcvo.getIncidentAccountCodeId().compareTo(iacId)==0) {
					if(!StringUtility.hasValue(dcvo.getCostLevel()) 
							|| (StringUtility.hasValue(dcvo.getCostLevel()) && !dcvo.getCostLevel().equals("M") )) {
						thisIacDailyCostVo=dcvo;
						break;
					}
				}
			}
			
			/*
			 * if thisIacDAilyCostVo is not null and costlevel=U and locked=true,
			 * then skip, not what sure to do here?
			 */
//			if(null != thisIacDailyCostVo 
//					&& thisIacDailyCostVo.getCostLevel().equals("U")
//					&& BooleanUtility.isTrue(thisIacDailyCostVo.getIsLocked())){
//				// skip?
//			}else if(null != thisIacDailyCostVo 
//						&& thisIacDailyCostVo.getCostLevel().equals("U")
//						&& crdVo.getEmploymentType().equals("FED")){
//					// skip?
//			}else if(null != thisIacDailyCostVo 
//					&& thisIacDailyCostVo.getCostLevel().equals("U")
//					&& crdVo.getEmploymentType().equals("OTHER")){
//					//&& this.getLastRateFromTimePostings(crdVo, thisDayTimePostVos, thisIacDailyCostVo.getIncidentAccountCodeId())==BigDecimal.valueOf(0.0)
//				// skip?
			if(null != thisIacDailyCostVo 
					&& StringUtility.hasValue(thisIacDailyCostVo.getCostLevel())
					// Defect 5310 turn off && thisIacDailyCostVo.getCostLevel().equals("U")
					&& BooleanUtility.isTrue(thisIacDailyCostVo.getIsLocked())){
				// skip?
			}else if(null != thisIacDailyCostVo 
						&& StringUtility.hasValue(thisIacDailyCostVo.getCostLevel())
					// Defect 5310 turn off && thisIacDailyCostVo.getCostLevel().equals("U")
						&& BooleanUtility.isTrue(thisIacDailyCostVo.getIsLocked())
						&& crdVo.getEmploymentType().equals("FED")){
					// skip?
			}else if(null != thisIacDailyCostVo 
					&& StringUtility.hasValue(thisIacDailyCostVo.getCostLevel())
					// Defect 5310 turn off && thisIacDailyCostVo.getCostLevel().equals("U")
					&& BooleanUtility.isTrue(thisIacDailyCostVo.getIsLocked())
					&& crdVo.getEmploymentType().equals("OTHER")){
					//&& this.getLastRateFromTimePostings(crdVo, thisDayTimePostVos, thisIacDailyCostVo.getIncidentAccountCodeId())==BigDecimal.valueOf(0.0)
				// skip?
			}else if(null != thisIacDailyCostVo){
				if(BooleanUtility.isFalse(this.thisDayIsLocked)){
					if(!CollectionUtility.hasValue(thisDayTimePostVos)
							&& !CollectionUtility.hasValue(thisDayTimeAdjustmentVos)
							&& !StringUtility.hasValue(thisIacDailyCostVo.getCostLevel())){
						// leave the empty 0 value cost record
					}else{
						// update it
						thisIacDailyCostVo.setCostLevel("A");
					}
					
					// 9/23/2014 defect 4525
					// if resource has default accrual code and cost record not locked, reset to resource's default
					if(null != thisIacDailyCostVo && BooleanUtility.isFalse(thisIacDailyCostVo.getIsLocked())){
						if(LongUtility.hasValue(crdVo.getAccrualCodeId())){
							thisIacDailyCostVo.setAccrualCodeId(crdVo.getAccrualCodeId());
						}
					}
					
					this.loadActualCostData(crdVo,thisIacDailyCostVo, existingDailyCostVos, actualSqls, thisDayTimePostVos, thisDayTimeAdjustmentVos);

				}
			}else if(null == thisIacDailyCostVo && BooleanUtility.isFalse(this.thisDayIsLocked)){
				// create new record
				thisIacDailyCostVo = new DailyCostVo();
				thisIacDailyCostVo.setId(null);
				if(BooleanUtility.isTrue(crdVo.getUseActualsOnly())
						&& (
								!CollectionUtility.hasValue(thisDayTimePostVos)
								&&
								!CollectionUtility.hasValue(thisDayTimeAdjustmentVos)
							)){
					thisIacDailyCostVo.setCostLevel("");
				}else
					thisIacDailyCostVo.setCostLevel("A");
				thisIacDailyCostVo.setIncidentResourceId(crdVo.getIncidentResourceId());
				thisIacDailyCostVo.setResourceCostType(resourceCostType);
				thisIacDailyCostVo.setActivityDate(costDate);
				thisIacDailyCostVo.setCostGroupId(crdVo.getCostGroupId());
				thisIacDailyCostVo.setIncidentAccountCodeId(iacId);
				thisIacDailyCostVo.setAccrualCodeId(crdVo.getAccrualCodeId());
				
				this.loadActualCostData(crdVo,thisIacDailyCostVo, existingDailyCostVos, actualSqls, thisDayTimePostVos, thisDayTimeAdjustmentVos);
			}
			
		}
		
		/*
		 * Remove any existing cost records with iac's not valid anymore
		 */
		Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
		for(DailyCostVo dcvo : existingDailyCostVos){
			if(DateUtil.isSameDate(costDate, dcvo.getActivityDate()) && thisDayIsLocked==false){
				Boolean isValidIac=false;
				for(Long iacId : iacIds){
					if(iacId.compareTo(dcvo.getIncidentAccountCodeId())==0)
						isValidIac=true;
				}
				if(BooleanUtility.isFalse(isValidIac)){
					// remove cost record
					String sql = "delete from isw_inc_res_daily_cost where id = " + dcvo.getId();
					actualSqls.add(sql);
				}else
					newExistingDailyCostVos.add(dcvo);
			}else
				newExistingDailyCostVos.add(dcvo);
		}
		existingDailyCostVos=newExistingDailyCostVos;		

		return actualSqls;
	}

	private void loadActualCostData(CostResourceDataVo crdVo
									,DailyCostVo dailyCostVo
									,Collection<DailyCostVo> existingDailyCostVos
									,Collection<String> actualSqls
									,Collection<TimePostVo> thisDayTimePostVos
									,Collection<TimeAdjustmentVo> thisDayTimeAdjustmentVos) throws Exception {

		/*
		 * get the total adjustment amount for same iacId
		 */
		BigDecimal adjustmentAmount = BigDecimal.valueOf(0.0);
		for(TimeAdjustmentVo tavo : thisDayTimeAdjustmentVos){
			if(tavo.getIncidentAccountCodeId().compareTo(dailyCostVo.getIncidentAccountCodeId())==0){
				if(tavo.getAdjustmentType().equals("DEDUCTION"))
					adjustmentAmount=adjustmentAmount.subtract(tavo.getAdjustmentAmount());
				else
					adjustmentAmount=adjustmentAmount.add(tavo.getAdjustmentAmount());
			}
		}
		
		/*
		 * create actual based on employment type
		 */
		if(!crdVo.getEmploymentType().equals("CONTRACTOR")){
			
			if(BooleanUtility.isTrue(this.updateRatesOnly)){
				
			}else{
				dailyCostVo.setAdjustmentAmount(adjustmentAmount);
				dailyCostVo.setRateType(crdVo.getRateType());

				/*
				 * If dailyCostVo is already an existing "U" record
				 * , evaluate whether or not to set a new unit cost amount.
				 * Rules are:
				 * 		if resource is fed or other, and time posting rate is 0
				 * 
				 * 9/2/2014 - not sure if valid anymore
				 */
				
				BigDecimal lastRate=this.getLastRateFromTimePostings(crdVo,thisDayTimePostVos,dailyCostVo.getIncidentAccountCodeId());
				if(BigDecimalUtility.hasValue(lastRate)){
					// use it
					dailyCostVo.setUnitCostAmount(lastRate);
				}else{
					if(BooleanUtility.isTrue(crdVo.getUseActualsOnly())
							&& !CollectionUtility.hasValue(thisDayTimePostVos)){
						
						// set to zero if use actuals only is true
						// and no time postings for this day
						dailyCostVo.setUnitCostAmount(new BigDecimal(0.0));
					}else{
						if(DecimalUtil.hasValue(dailyCostVo.getUnitCostAmount())){
							// keep original rate
						}else
							dailyCostVo.setUnitCostAmount(crdVo.getRate());
					}
				}

				dailyCostVo.setUnits(this.getTotalUnits(thisDayTimePostVos, dailyCostVo.getIncidentAccountCodeId()));
				dailyCostVo.setPrimaryTotalAmount(dailyCostVo.getUnits().multiply(dailyCostVo.getUnitCostAmount()));
				dailyCostVo.setPrimaryTotalAmount(dailyCostVo.getPrimaryTotalAmount().add(adjustmentAmount));
				dailyCostVo.setTotalCostAmount(dailyCostVo.getPrimaryTotalAmount());
				// always set subordinate total to zero, it will get updated later if necessary
				dailyCostVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
				
				if(!LongUtility.hasValue(dailyCostVo.getId())){
					existingDailyCostVos.add(dailyCostVo);
				}else{
					//update existingDailyCostVo collection
					Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
					for(DailyCostVo v : existingDailyCostVos){
						if(LongUtility.hasValue(v.getId()) && v.getId().compareTo(dailyCostVo.getId())==0)
							newExistingDailyCostVos.add(dailyCostVo);
						else
							newExistingDailyCostVos.add(v);
					}
					existingDailyCostVos=newExistingDailyCostVos;
				}
				String sql=dailyCostVo.toSql(this.irdcDao.isOracleDialect());
				actualSqls.add(sql);
				
				String sqlLock="update isw_inc_res_daily_cost ";
				if(BooleanUtility.isTrue(dailyCostVo.getIsLocked())){
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
				}else{
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
				}
				sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
				 "  to_date('"+DateUtil.toDateString(dailyCostVo.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
				 "and incident_resource_id = " + dailyCostVo.getIncidentResourceId() + " "+
				 "";
				actualSqls.add(sqlLock);
				
			}
		}
		
		if(crdVo.getEmploymentType().equals("CONTRACTOR")){
			
			if(BooleanUtility.isTrue(this.updateRatesOnly)){
				
			}else{
				dailyCostVo.setAdjustmentAmount(adjustmentAmount);
				dailyCostVo.setRateType("DAILY");
				dailyCostVo.setUnits(BigDecimal.valueOf(1));

				BigDecimal totalAmountContractor=this.getTotalTimeAmountContractor(dailyCostVo, crdVo, thisDayTimePostVos);
				dailyCostVo.setUnitCostAmount(totalAmountContractor);
				
				dailyCostVo.setPrimaryTotalAmount(dailyCostVo.getUnits().multiply(dailyCostVo.getUnitCostAmount()));
				dailyCostVo.setPrimaryTotalAmount(dailyCostVo.getPrimaryTotalAmount().add(adjustmentAmount));
				dailyCostVo.setTotalCostAmount(dailyCostVo.getPrimaryTotalAmount());
				// always set subordinate total to zero, it will get updated later if necessary
				dailyCostVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
				
				if(!LongUtility.hasValue(dailyCostVo.getId())){
					existingDailyCostVos.add(dailyCostVo);
				}else{
					//update existingDailyCostVo collection
					Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
					for(DailyCostVo v : existingDailyCostVos){
						if(LongUtility.hasValue(v.getId()) && v.getId().compareTo(dailyCostVo.getId())==0)
							newExistingDailyCostVos.add(dailyCostVo);
						else
							newExistingDailyCostVos.add(v);
					}
					existingDailyCostVos=newExistingDailyCostVos;
				}
				String sql=dailyCostVo.toSql(this.irdcDao.isOracleDialect());
				actualSqls.add(sql);
				
				String sqlLock="update isw_inc_res_daily_cost ";
				if(BooleanUtility.isTrue(dailyCostVo.getIsLocked())){
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 1 : true) + " ";
				}else{
					 sqlLock=sqlLock+"set is_locked = " + (irdcDao.isOracleDialect() ? 0 : false) + " ";
				}
				sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
				 "  to_date('"+DateUtil.toDateString(dailyCostVo.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
				 "and incident_resource_id = " + dailyCostVo.getIncidentResourceId() + " "+
				 "";
				actualSqls.add(sqlLock);
				
			}
		}
	}

	/**
	 * @param costDate
	 * @param crdVo
	 * @param thisDayTimePostVos
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getTotalTimeAmountContractor(DailyCostVo dailyCostVo
													,CostResourceDataVo crdVo
													,Collection<TimePostVo> thisDayTimePostVos) throws Exception{

		
		Collection<ContractorTimeDetail> ctDetails = new ArrayList<ContractorTimeDetail>();

		// load ctDetails
		for(TimePostVo tpvo : thisDayTimePostVos){
			if(DateUtil.isSameDate(dailyCostVo.getActivityDate(), tpvo.getPostStartDate())
						&& tpvo.getIncidentAccountCodeId().compareTo(dailyCostVo.getIncidentAccountCodeId())==0) {

				Collection<ContractorTimeDetail> tdList = ContractorTimeBuilder.buildTimeDetails(tpvo,this.contractorRateVos);
				for(ContractorTimeDetail td : tdList){
					ctDetails.add(td);
				}
				
			}
		}

		// resort 
		ctDetails = ContractorTimeBuilder.reSortThisDayData(ctDetails);

		BigDecimal amt=ContractorTimeBuilder.getDayAmount(ctDetails);

		return amt;
	}
	
	private BigDecimal getTotalUnits(Collection<TimePostVo> timePostVos,Long iacId){
		double units=0;
		
		for(TimePostVo v : timePostVos){
			if(v.getIncidentAccountCodeId().compareTo(iacId)==0){
				//units=units+v.getQuantity().doubleValue();
				if(v.getQuantity().doubleValue()>0){
					units=units+v.getQuantity().doubleValue();
				}else{
					// defect 5112 check if no stop time and special is tvl
					if(DateUtil.hasValue(v.getPostStopDate())
							&& DateUtil.isSameDate(v.getPostStartDate(), v.getPostStopDate())){
						String m1=DateUtil.toMilitaryTime(v.getPostStartDate());
						String m2=DateUtil.toMilitaryTime(v.getPostStopDate());
						
						if(m1.equalsIgnoreCase(m2) ){
							if(StringUtility.hasValue(v.getSpecialPayCode())
									&& v.getSpecialPayCode().equalsIgnoreCase("TVL")){
								units=units+Double.valueOf(8);
							}
						}
					}
					/*
					if(!DateUtil.hasValue(v.getPostStopDate())){
						if(StringUtility.hasValue(v.getSpecialPayCode())
								&& v.getSpecialPayCode().equalsIgnoreCase("TVL")){
							units=units+Double.valueOf(8);
						}
					}
					*/
				}
			}
		}
		
		return BigDecimal.valueOf(units);
	}
	
	private BigDecimal getLastRateFromTimePostings(CostResourceDataVo crdVo, Collection<TimePostVo> timePostVos,Long iacId){
		double rate=0;
		
		for(TimePostVo v : timePostVos){
			if(v.getIncidentAccountCodeId().compareTo(iacId)==0){
				if(StringUtility.hasValue(crdVo.getEmploymentType())
						&& crdVo.getEmploymentType().equals("OTHER")){
					if(BigDecimalUtility.hasValue(v.getOtherRate()))
						rate=v.getOtherRate().doubleValue();
				}
				if(StringUtility.hasValue(crdVo.getEmploymentType())
						&& crdVo.getEmploymentType().equals("AD")){
					if(BigDecimalUtility.hasValue(v.getRateAmount()))
						rate=v.getRateAmount().doubleValue();
				}
			}
		}
		
		return BigDecimal.valueOf(rate);
	}

	private void updateParentTotals(CostResourceDataVo voToProcess) throws Exception {
		Collection<String> parentUpdateSqls=new ArrayList<String>();
		String sql1="update isw_inc_res_daily_cost dc " +
					"set subordinate_total_amount= ("+
					"select sum(child.total_cost_amount) from isw_inc_res_daily_cost child "+
					"where child.incident_resource_id in ( "+
					"	select id from isw_incident_resource "+
					"	where resource_id in ( "+
					"	select id from isw_resource where parent_resource_id = ( "+
					"		select resource_id from isw_incident_resource where id = "+voToProcess.getIncidentResourceId()+" "+
					"	) "+
					") "+
					") "+
					"and child.incident_account_code_id = dc.incident_account_code_id "+
					"and to_date(to_char(child.activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') "+
					"  = to_date(to_char(dc.activity_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
					") " +
					" where incident_resource_id = "+voToProcess.getIncidentResourceId()+" "+
					" and (cost_level is null or cost_level != 'M' ) ";
		String sql2="update isw_inc_res_daily_cost set subordinate_total_amount = 0 " +
					"where incident_resource_id = " + voToProcess.getIncidentResourceId()+" "+
					"and subordinate_total_amount is null ";
		String sql3="update isw_inc_res_daily_cost set total_cost_amount = (primary_total_amount + subordinate_total_amount) " +
					"where incident_resource_id = " + voToProcess.getIncidentResourceId()+" ";
		parentUpdateSqls.add(sql1);
		this.irdcDao.persistSqls(parentUpdateSqls);
		parentUpdateSqls=new ArrayList<String>();
		parentUpdateSqls.add(sql2);
		this.irdcDao.persistSqls(parentUpdateSqls);
		parentUpdateSqls=new ArrayList<String>();
		parentUpdateSqls.add(sql3);
		this.irdcDao.persistSqls(parentUpdateSqls);
	}
	
	/**
	 * Returns list of distinct incidentAccountCodeIds 
	 * in time records for the costDate.
	 * 
	 * @param costDate
	 * @param thisDaysTimePosts
	 * @param thisDaysTimeAdjustments
	 * @return
	 */
	private Collection<Long> getDistinctIacs(Date costDate
											,Collection<TimePostVo> thisDaysTimePosts
											,Collection<TimeAdjustmentVo> thisDaysTimeAdjustments) {
		Collection<Long> iacIds = new ArrayList<Long>();

		for(TimePostVo tpvo : thisDaysTimePosts){
			if(DateUtil.isSameDate(tpvo.getPostStartDate(), costDate)){
				if(!iacIds.contains(tpvo.getIncidentAccountCodeId())){
					iacIds.add(tpvo.getIncidentAccountCodeId());
				}
			}
		}
		
		for(TimeAdjustmentVo tavo : thisDaysTimeAdjustments){
			if(DateUtil.isSameDate(tavo.getActivityDate(), costDate)){
				if(!iacIds.contains(tavo.getIncidentAccountCodeId())){
					iacIds.add(tavo.getIncidentAccountCodeId());
				}
			}
		}
		
		return iacIds;
	}
	
	private Boolean isCostLevelAorEorF(String val){
		if(StringUtility.hasValue(val)){
			if(val.equals("A") || val.equals("F") || val.equals("E"))
				return true;
			else
				return false;
		}else
			return false;
	}

	private Boolean costLevelContains(String costLevel,String vals){
		if(StringUtility.hasValue(costLevel)){
			if(vals.indexOf(costLevel)>-1)
				return true;
			else{
				return false;
			}
		}else{
			if(vals.indexOf("N")>-1 && !StringUtility.hasValue(costLevel))
				return true;
			else
				return false;
		}
	}

	private Collection<String> deleteByFilter(Date costDate, String costLevels, Long iacId) {
		Collection<String> sqls = new ArrayList<String>();
		
		Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
		for(DailyCostVo v : existingDailyCostVos){
			Boolean bRemove=false;
			
			if(LongUtility.hasValue(v.getId())){
				if(DateUtil.isSameDate(costDate, v.getActivityDate())){
					if(this.costLevelContains(v.getCostLevel(), costLevels)){
						if(LongUtility.hasValue(iacId)){
							if(iacId.compareTo(v.getIncidentAccountCodeId())==0
									&& BooleanUtility.isFalse(v.getIsLocked())){
								// remove it
								bRemove=true;
							}
						}else{
							// remove it
							bRemove=true;
						}
					}
				}
			}
			
			if(bRemove==false)
				newExistingDailyCostVos.add(v);
			else{
				String sql="delete from isw_inc_res_daily_cost where id = " + v.getId()+" ";
				sqls.add(sql);
			}
		}
		
		existingDailyCostVos=newExistingDailyCostVos;

		return sqls;
	}
	
	private void deleteBeforeAndAfter(CostResourceDataVo voToProcess,Date startDate, Date stopDate) throws Exception{
		String sql="delete from isw_inc_res_daily_cost where incident_resource_id = " + voToProcess.getIncidentResourceId()+" "+
				   "and (" +
				   " to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+DateUtil.toDateString(startDate,DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY')" +
				   " or " +
				   " to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+DateUtil.toDateString(DateUtil.addDays(stopDate,1),DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY')" +
				   " ) ";
	
		Collection<String> sqls = new ArrayList<String>();
		sqls.add(sql);
		this.irdcDao.persistSqls(sqls);
	}

	private Boolean hasActualTimeData(CostResourceDataVo crdVo) {
		if(IntegerUtility.hasValue(crdVo.getTimePostCount())
				|| IntegerUtility.hasValue(crdVo.getAdjustmentCount())
				|| IntegerUtility.hasValue(crdVo.getSubordinateTimeAdjustmentCount())
				|| IntegerUtility.hasValue(crdVo.getSubordinateTimePostCount())){
			return true;
		}else
			return false;
	}
	
	private Boolean hasPrimaryActualTimeData(CostResourceDataVo crdVo) {
		if(IntegerUtility.hasValue(crdVo.getTimePostCount())
				|| IntegerUtility.hasValue(crdVo.getAdjustmentCount())){
			return true;
		}else
			return false;
	}

	private Boolean hasSubordinateActualTimeData(CostResourceDataVo crdVo) {
		if(IntegerUtility.hasValue(crdVo.getSubordinateTimeAdjustmentCount())
				|| IntegerUtility.hasValue(crdVo.getSubordinateTimePostCount())){
			return true;
		}else
			return false;
	}
	
	public void runPropagateCostGroup(IncidentResource parent, Long costGroupId, Long shiftId) throws Exception {
		
		for(Resource child : parent.getResource().getChildResources()){

			// run update sql
			IncidentResourceDailyCostDao irdcDao = (IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
			Long childId = child.getIncidentResources().iterator().next().getId();

			if(LongUtility.hasValue(costGroupId))
				irdcDao.setCostGroupId(childId, costGroupId);
			else
				irdcDao.setCostGroupId(childId, null);
			
			if(LongUtility.hasValue(shiftId))
				irdcDao.setShiftId(childId, shiftId);
			else
				irdcDao.setShiftId(childId, null);
			
			if(CollectionUtility.hasValue(child.getChildResources())){
				this.runPropagateCostGroup(child.getIncidentResources().iterator().next(), costGroupId, shiftId);
			}
		}
		
	}
	
}
