package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class CostUpdateRatesGenerator {
	private ApplicationContext context = null;
	private HashMap<Long,CostResourceDataVo> processedMap = new HashMap<Long,CostResourceDataVo>();
	private IncidentResourceDailyCostDao irdcDao;
	private IncidentResourceDao irDao;
	private TimePostDao tpDao;
	private TimeAssignAdjustDao taaDao;
	
	public CostUpdateRatesGenerator(ApplicationContext ctx){
		this.context=ctx;
		this.irdcDao=(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
		this.irDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
		this.tpDao=(TimePostDao)context.getBean("timePostDao");
		this.taaDao=(TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
	}

	public void updateCostRates(CostResourceDataVo voToProcess
								,Collection<CostResourceDataVo> costResourceDataVos
								,Boolean skipCheckSubordinates) throws Exception {

		// if resource has subordinates, update rates for subordinates
		if(IntegerUtility.hasValue(voToProcess.getSubordinateCount()) && skipCheckSubordinates==false){
			/*
			 * Process each child, update cost rates
			 */
			for(CostResourceDataVo vo : costResourceDataVos){
				if(LongUtility.hasValue(vo.getParentResourceId())){
					if(vo.getParentResourceId().compareTo(voToProcess.getResourceId())==0){
						this.updateCostRates(vo, costResourceDataVos, false);
					}
				}
			}
			
		}

		Collection<String> sqls=this.updateResourceRates(voToProcess);
		this.irdcDao.persistSqls(sqls);

		/* if this resource is a parent, update subordinate and resource total amounts*/
		if(IntegerUtility.hasValue(voToProcess.getSubordinateCount())){
			this.updateParentTotals(voToProcess);
		}

	}

	private Collection<String> updateResourceRates(CostResourceDataVo voToProcess) throws Exception {
		Collection<String> sqls=new ArrayList<String>();
		
		/*
		 * update cost rates for voToProcess if not already processed
		 */
		if(!this.processedMap.containsKey(voToProcess.getIncidentResourceId())){

			String validationError="";
			
			Collection<DailyCostVo> existingDailyCostVos = new ArrayList<DailyCostVo>();
			Collection<ContractorRateVo> contractorRateVos = new ArrayList<ContractorRateVo>();
			
			/* load any existing daily cost records for this resource */
			existingDailyCostVos = this.irdcDao.getDailyCosts(voToProcess.getIncidentResourceId(),null, null);

			if(StringUtility.hasValue(voToProcess.getEmploymentType())){
				
				for(DailyCostVo dcvo : existingDailyCostVos){
					if(needUpdateRate(voToProcess,dcvo)==true){
						DailyCostVo updatedDcvo=dcvo;
						BigDecimal newRate=voToProcess.getRate();
						updatedDcvo.setUnitCostAmount(newRate);
						updatedDcvo.setPrimaryTotalAmount(updatedDcvo.getUnits().multiply(updatedDcvo.getUnitCostAmount()));
						updatedDcvo.setPrimaryTotalAmount(updatedDcvo.getPrimaryTotalAmount().add(updatedDcvo.getAdjustmentAmount()));
						updatedDcvo.setTotalCostAmount(updatedDcvo.getPrimaryTotalAmount());
						// always set subordinate total to zero, it will get updated later if necessary
						updatedDcvo.setSubordinateTotalAmount(BigDecimal.valueOf(0.0));
						String sql=updatedDcvo.toSql(this.irdcDao.isOracleDialect());
						sqls.add(sql);
					}
				}
			}
			
			processedMap.put(voToProcess.getIncidentResourceId(), voToProcess);
		}
		
		return sqls;
	}
	
	private Boolean needUpdateRate(CostResourceDataVo voToProcess, DailyCostVo dcvo){
		String val="";
		String empType="";
		
		if(StringUtility.hasValue(voToProcess.getEmploymentType()))
			empType=voToProcess.getEmploymentType();
		
		if(empType.equals("FED")){
			val="AEFU";
			if(StringUtility.hasValue(dcvo.getCostLevel())
					&& BooleanUtility.isFalse(dcvo.getIsLocked())){
				if(val.indexOf(dcvo.getCostLevel())>-1){
					return true;
				}
			}
		}
		if(empType.equals("AD")){
			val="EU";
			if(StringUtility.hasValue(dcvo.getCostLevel())
					&& BooleanUtility.isFalse(dcvo.getIsLocked())){
				if(val.indexOf(dcvo.getCostLevel())>-1){
					return true;
				}
			}
		}
		if(empType.equals("OTHER")){
			val="EU";
			if(StringUtility.hasValue(dcvo.getCostLevel())
					&& BooleanUtility.isFalse(dcvo.getIsLocked())){
				if(val.indexOf(dcvo.getCostLevel())>-1){
					return true;
				}
			}
		}
		if(empType.equals("CONTRACTOR")){
			val="EU";
			if(StringUtility.hasValue(dcvo.getCostLevel())
					&& BooleanUtility.isFalse(dcvo.getIsLocked())){
				if(val.indexOf(dcvo.getCostLevel())>-1){
					return true;
				}
			}
		}
		
		return false;
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

}
