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

public class CostOtherUpdateRatesGenerator {
	private ApplicationContext context = null;
	private HashMap<Long,CostResourceDataVo> processedMap = new HashMap<Long,CostResourceDataVo>();
	private IncidentResourceDailyCostDao irdcDao;
	private IncidentResourceDao irDao;
	private TimePostDao tpDao;
	private TimeAssignAdjustDao taaDao;
	
	public CostOtherUpdateRatesGenerator(ApplicationContext ctx){
		this.context=ctx;
		this.irdcDao=(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
		this.irDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
		this.tpDao=(TimePostDao)context.getBean("timePostDao");
		this.taaDao=(TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
	}

	public void updateCostRates(CostResourceDataVo voToProcess
								,Collection<CostResourceDataVo> costResourceDataVos
								,Boolean skipCheckSubordinates) throws Exception {

		Collection<String> sqls=this.updateResourceRates(voToProcess);
		this.irdcDao.persistSqls(sqls);


	}

	private Collection<String> updateResourceRates(CostResourceDataVo voToProcess) throws Exception {
		Collection<String> sqls=new ArrayList<String>();
		
		/*
		 * update cost rates for voToProcess if not already processed
		 */
		if(!this.processedMap.containsKey(voToProcess.getIncidentResourceOtherId())){

			String validationError="";
			
			Collection<DailyCostVo> existingDailyCostVos = new ArrayList<DailyCostVo>();
			
			/* load any existing daily cost records for this resource */
			existingDailyCostVos = this.irdcDao.getDailyCosts(null,voToProcess.getIncidentResourceOtherId(), null);

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
			
			processedMap.put(voToProcess.getIncidentResourceOtherId(), voToProcess);
		}
		
		return sqls;
	}
	
	private Boolean needUpdateRate(CostResourceDataVo voToProcess, DailyCostVo dcvo){
		String val="";
		
		val="EU";
		if(StringUtility.hasValue(dcvo.getCostLevel())
				&& BooleanUtility.isFalse(dcvo.getIsLocked())){
			if(val.indexOf(dcvo.getCostLevel())>-1){
				return true;
			}
		}

		return false;
	}
	

}
