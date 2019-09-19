package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.cost.utilities.StopDateResolver;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class CostGeneratorOther {
	private ApplicationContext context = null;
	private Boolean updateRatesOnly=false;
	private HashMap<Long,CostResourceDataVo> processedMap = new HashMap<Long,CostResourceDataVo>();
	private IncidentResourceDailyCostDao irdcDao;
	private Boolean thisDayIsLocked=false;
	
	private Collection<DailyCostVo> existingDailyCostVos = new ArrayList<DailyCostVo>();
	
	public CostGeneratorOther(ApplicationContext ctx){
		this.context=ctx;
		this.irdcDao=(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
	}

	protected String getUserSessionDbName(){
		return ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
	}

	public void setUpdateRatesOnly(Boolean val){
		this.updateRatesOnly=val;
	}

	public void generateCosts(CostResourceDataVo voToProcess) throws DailyCostException {

		try{

			//System.out.println("IncidentResourceOtherId: "+voToProcess.getIncidentResourceOtherId());
			
			Collection<String> thisResourceSqls=new ArrayList<String>();
			
			/*
			 * generate costs for voToProcess if not already processed
			 */
			if(!this.processedMap.containsKey(voToProcess.getIncidentResourceOtherId())){
				/* verify we can run costs */
				if(this.canRunCosts(voToProcess)==true){
					// init new collection
					existingDailyCostVos = new ArrayList<DailyCostVo>();
					
					Date startDate=voToProcess.getAssignDate();
					if(DateUtil.hasValue(voToProcess.getIncidentStartDate())){
						Date incStartDate=voToProcess.getIncidentStartDate();
						incStartDate=DateUtil.addMilitaryTimeToDate(incStartDate, "2359");
						
						if(startDate.before(incStartDate)){
							startDate=incStartDate;
						}
					}
					Date stopDate=StopDateResolver.resolveIRStopDate(voToProcess,getUserSessionDbName());
					Date processDate=DateUtil.addMilitaryTimeToDate(startDate,"2359");
					stopDate=DateUtil.addMilitaryTimeToDate(stopDate, "2359");

					/* load any existing daily cost records for this resource */
					existingDailyCostVos = this.irdcDao.getDailyCosts(null,voToProcess.getIncidentResourceOtherId(),null);

					// create costs for each date
					while(!processDate.after(stopDate)){
						this.thisDayIsLocked=false;
						for(DailyCostVo dcvo : existingDailyCostVos){
							if(DateUtil.isSameDate(processDate, dcvo.getActivityDate())){
								if(BooleanUtility.isTrue(dcvo.getIsLocked()))
									this.thisDayIsLocked=true;
							}
						}
						
						if(this.thisDayIsLocked==false){
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

					/* delete any cost records before start date or after stop date */
					this.deleteBeforeAndAfter(voToProcess, startDate, stopDate);
				}

				processedMap.put(voToProcess.getIncidentResourceId(), voToProcess);
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
	private Boolean canRunCosts(CostResourceDataVo vo){

		//if(!StringUtility.hasValue(vo.getAgencyCode()))
		//	return false;

		if(StringUtility.hasValue(vo.getStatus())){
			if(vo.getStatus().equalsIgnoreCase("F")){
				return false;
			}
		}
		
		// have to have a default incident account code id
		if(!LongUtility.hasValue(vo.getDefIncidentAccountCodeId())){
			return false;
		}
		
		if(!DateUtil.hasValue(vo.getAssignDate()))
			return false;

		return true;
	}

	private Boolean hasPreviousDayCostsForCopy(Date costDate
												,Collection<DailyCostVo> dailyCostVos) throws Exception{

		Date previousDay = DateUtil.subtractDaysFromDate(costDate, 1);

		// check for previous day cost record where costlevel is not M or U
		for(DailyCostVo v : dailyCostVos){
			if(DateUtil.isSameDate(previousDay,v.getActivityDate())){
				// cost level must be either an A,E,or F
				if(StringUtility.hasValue(v.getCostLevel())
						&& (!v.getCostLevel().equals("M") )) { //&& !v.getCostLevel().equals("U"))){
					return true;
				}
			}
		}

		return false;
	}
	
	private Collection<String> generateDayCosts(Date costDate ,CostResourceDataVo crdVo) throws Exception {

		Collection<String> daySqls = new ArrayList<String>();

				
		if(this.hasPreviousDayCostsForCopy(costDate, existingDailyCostVos)==true){
			Date previousDate=DateUtil.subtractDaysFromDate(costDate, 1);
				
			/*
			 * for each previous day cost E,U, create a corresponding one for this costDate.
			 */
			Collection<DailyCostVo> previousDayCostVos = new ArrayList<DailyCostVo>();
			for(DailyCostVo v : existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(), previousDate)
							&& this.costLevelContains(v.getCostLevel(), "EU")==true){
					previousDayCostVos.add(v);
				}
			}
			
			// create flowdown for all valid previous Day Costs
			daySqls.addAll(this.createDayFlowdownCopy(costDate, crdVo, previousDayCostVos));

		}else{
			// create estimates
			if(BooleanUtility.isTrue(crdVo.getGenerateCosts()))
				daySqls.addAll(this.createDayEstimates(costDate, crdVo));
			else{
				// delete all estimates for this resource
				daySqls.add("delete from isw_inc_res_daily_cost where incident_resource_other_id="+crdVo.getIncidentResourceOtherId()+" and cost_level = 'E' " +
						"and is_locked = " + (this.irdcDao.isOracleDialect() ? 0 : false) + " ");

				// remove from existingdailycosts
				Collection<DailyCostVo> newExistingDailyCostVos = new ArrayList<DailyCostVo>();
				for(DailyCostVo v : existingDailyCostVos){
					if(DateUtil.isSameDate(costDate, v.getActivityDate()) 
							&& (StringUtility.hasValue(v.getCostLevel()) && v.getCostLevel().equals("E"))
							&& BooleanUtility.isFalse(v.getIsLocked())){
						// skip
					}else
						newExistingDailyCostVos.add(v);
				}
				existingDailyCostVos=newExistingDailyCostVos;
			}
		}

		return daySqls;
	}

	private Collection<String> createDayEstimates(Date costDate ,CostResourceDataVo crdVo) throws Exception {
		Collection<String> estimateSqls = new ArrayList<String>();
		String resourceCostType=crdVo.getResourceCostType();

		// verify generate costs flag is true
		if(BooleanUtility.isTrue(crdVo.getGenerateCosts())){
			// init new estimate DailyCostvo
			DailyCostVo estimateVo=new DailyCostVo();
			Boolean isNew=true;

			// check for an existing estimate record
			for(DailyCostVo v : existingDailyCostVos){
				if(DateUtil.isSameDate(v.getActivityDate(), costDate)
						&& (StringUtility.hasValue(v.getCostLevel()) 
								&& (v.getCostLevel().equals("E") || v.getCostLevel().equals("F") || v.getCostLevel().equals("U") ) ) ){
					estimateVo=v;
					isNew=false;
					break;
				}
			}

			if(BooleanUtility.isTrue(isNew)){
				// populate
				estimateVo.setIsDirty(true);
				estimateVo.setActivityDate(costDate);
				estimateVo.setCostLevel("E");
				estimateVo.setIncidentResourceOtherId(crdVo.getIncidentResourceOtherId());
				estimateVo.setIncidentAccountCodeId(crdVo.getDefIncidentAccountCodeId());
				estimateVo.setCostGroupId(null); // todo
				estimateVo.setResourceCostType(resourceCostType);
				estimateVo.setAccrualCodeId(crdVo.getAccrualCodeId());
				estimateVo.setIsLocked(false);
				estimateVo.setIsFlowdown(false);
				estimateVo.setUnits(BigDecimal.valueOf(0.0));
				estimateVo.setUnitCostAmount(BigDecimal.valueOf(0.0));
				estimateVo.setPrimaryTotalAmount(BigDecimal.valueOf(0.0));
				estimateVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));

				estimateVo.setUnits(new BigDecimal(crdVo.getUnits()));
				estimateVo.setUnitCostAmount(crdVo.getRate());
				estimateVo.setRateType(crdVo.getRateType());

				BigDecimal total = estimateVo.getUnitCostAmount().multiply(estimateVo.getUnits());
				estimateVo.setPrimaryTotalAmount(total);
				estimateVo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
				total.add(estimateVo.getSubordinateTotalAmount());

				estimateVo.setTotalCostAmount(total);

				existingDailyCostVos.add(estimateVo);
				String sql=estimateVo.toSql(this.irdcDao.isOracleDialect());
				estimateSqls.add(sql);
			}
		}

		return estimateSqls;
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
							&& v.getIncidentAccountCodeId().compareTo(previousDayCost.getIncidentAccountCodeId())==0
							&& this.costLevelContains(v.getCostLevel(),"AEFU")==true){
					thisDayCostVo=v;
					break;
				}
			}

			if(null==thisDayCostVo) 
				thisDayCostVo=new DailyCostVo();
			
			if(BooleanUtility.isFalse(thisDayCostVo.getIsLocked())){
				/* if thisDayCostVo is a new vo
				 * or if previous day cost level is an A or F, 
				 * then create copy of it
				 */
				if(!LongUtility.hasValue(thisDayCostVo.getId())
							|| this.costLevelContains(previousDayCost.getCostLevel(), "AF")==true){
					// create a copy from previousDayCost
					Long origId = thisDayCostVo.getId();
					thisDayCostVo=previousDayCost.clone();
					if(previousDayCost.getCostLevel().equals("A"))
						thisDayCostVo.setCostLevel("F");
					thisDayCostVo.setIsDirty(true);
					thisDayCostVo.setId(origId);
					thisDayCostVo.setActivityDate(costDate);
					
					String sql=thisDayCostVo.toSql(this.irdcDao.isOracleDialect());
					flowdownSqls.add(sql);
					
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

	private Boolean costLevelContains(String costLevel,String vals){
		if(StringUtility.hasValue(costLevel)){
			if(vals.indexOf(costLevel)>-1)
				return true;
			else
				return false;
		}else
			return false;
	}
	
	private void deleteBeforeAndAfter(CostResourceDataVo voToProcess,Date startDate, Date stopDate) throws Exception{
		String sql="delete from isw_inc_res_daily_cost where incident_resource_other_id = " + voToProcess.getIncidentResourceOtherId()+" "+
				   "and (" +
				   " to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('"+DateUtil.toDateString(startDate,DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY')" +
				   " or " +
				   " to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+DateUtil.toDateString(DateUtil.addDays(stopDate,1),DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY')" +
				   " ) ";
	
		Collection<String> sqls = new ArrayList<String>();
		sqls.add(sql);
		this.irdcDao.persistSqls(sqls);
	}

}
