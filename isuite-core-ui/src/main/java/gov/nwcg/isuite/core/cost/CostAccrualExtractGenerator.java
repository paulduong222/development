package gov.nwcg.isuite.core.cost;


import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.CostAccrualGroupVo;
import gov.nwcg.isuite.framework.cost.BaseCostAccrualGenerator;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CostAccrualExtractGenerator extends BaseCostAccrualGenerator{
	public Collection<Long> deletedExtractIds = new ArrayList<Long>();
	
	public CostAccrualExtractGenerator(CostAccrualExtractDao costAccrualExtractDao, ApplicationContext ctx){
		super.costAccrualExtractDao = costAccrualExtractDao;
		super.context=ctx;
	}
	
	public CostAccrualExtractVo runExtractIncident(String extractDateString, Long incidentId) throws Exception{
		CostAccrualExtractVo extractVo = null;
		
		try {
			Date extractDate = DateUtil.toDate(extractDateString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			// check for existing extract by incident id and date
			CostAccrualExtract costAccrualExtract = costAccrualExtractDao.getExtractByDate(incidentId, null,extractDate);

			/*
			 * if extract doesn't exist, create one
			 */
			if(null == costAccrualExtract){
				/*
				 * per defect 4182
				 *  check for previous un-finalized extract
				 *   and delete it
				 */
				Collection<CostAccrualExtract> extracts = costAccrualExtractDao.getUnFinalExtractsBeforeDate(incidentId, null, extractDate, "DESC");
				if(CollectionUtility.hasValue(extracts)){
					for(CostAccrualExtract extract : extracts){
						costAccrualExtractDao.flushAndEvict(extract);
						this.deletedExtractIds.add(extract.getId());
						costAccrualExtractDao.delete(extract);
					}
				}
				
				costAccrualExtract = new CostAccrualExtractImpl();
				Incident incident = new IncidentImpl();
				incident.setId(incidentId);
				
				costAccrualExtract.setIncident(incident);
				costAccrualExtract.setExtractDate(extractDate);
				costAccrualExtract.setFinalized(StringBooleanEnum.N);
				costAccrualExtract.setIsExported(StringBooleanEnum.N);
				costAccrualExtract.setIsFromSingleIncident(StringBooleanEnum.N);
				
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
				
			}else{
				/*
				 *  clear any existing extract_rsc records
				 *  all extract rsc records for this date will get rebuilt
				 */
				costAccrualExtract.getCostAccrualExtractRscs().clear();
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			}

			// get the last fiscal date cutoff
			String fiscalDate=this.getLastFiscalDate(extractDate);
			String fiscalYear=this.getFiscalYear(extractDate);

			// create the cost accrual extract resource records for previous fiscal year
			int cnt = costAccrualExtractDao.getCostAccrualExtractResourcesPrevYearCount(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0)
				costAccrualExtractDao.createCostAccrualExtResPrevYear(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			
			// (Other Resources) create the cost accrual extract (Other) resource records for previous fiscal year
			cnt = costAccrualExtractDao.getCostAccrualExtractOTResourcesPrevYearCount(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0)
				costAccrualExtractDao.createCostAccrualExtResOTPrevYear(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			
			// create the cost accrual extract resource records
			cnt = costAccrualExtractDao.getCostAccrualExtractResourcesCount(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0)
				costAccrualExtractDao.createCostAccrualExtractResources(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			
			//  (OTHER Resources) create the cost accrual extract resource records
			cnt = costAccrualExtractDao.getCostAccrualExtractOTResourcesCount(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0)
				costAccrualExtractDao.createCostAccrualExtractResourcesOT(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);

			// create ad draw down records
			costAccrualExtractDao.createADDrawDownResources(costAccrualExtract.getId(), incidentId, extractDateString, fiscalDate, fiscalYear);
			
			// get previous extractId if it exists
			Long previousExtractId=costAccrualExtractDao.getPreviousExtractId(costAccrualExtract.getId(), incidentId, null);

			// update the change_amount field
			costAccrualExtractDao.updateExtractResourceChangeAmount(costAccrualExtract.getId(), previousExtractId, extractDateString,fiscalDate);

			// create any negative cost accrual extract resource records
			if(LongUtility.hasValue(previousExtractId)){
				int count2=costAccrualExtractDao.getUpdateMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count2 > 0){
					//costAccrualExtractDao.updateNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
				
				int count=costAccrualExtractDao.getMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count>0){
					costAccrualExtractDao.createNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
				count=costAccrualExtractDao.getMissingAccrualsFromPreviousExtractOT(previousExtractId, costAccrualExtract.getId());
				if(count>0){
					costAccrualExtractDao.createNegativeAccrualsOT(previousExtractId, costAccrualExtract.getId());
				}
			}
			
			// get updated entity
			costAccrualExtract=costAccrualExtractDao.getById(costAccrualExtract.getId(), CostAccrualExtractImpl.class);
			
			// build the extractVo
			extractVo = CostAccrualExtractVo.getInstance(costAccrualExtract, true);

			// get the totals
			BigDecimal totalAmt=costAccrualExtractDao.getTotalAmountByExtractId(costAccrualExtract.getId());
			BigDecimal changeAmt=costAccrualExtractDao.getTotalChangeAmountByExtractId(costAccrualExtract.getId());
			extractVo.setTotalAmount(totalAmt);
			extractVo.setChangeAmount(changeAmt);
			
			// flush
			costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			
		}catch(Exception e){
			throw e;
		}
		
		return extractVo;
	}
	
	public CostAccrualExtractVo runExtractIncidentGroup(String extractDateString, Long incidentGroupId) throws Exception{
		CostAccrualExtractVo extractVo = null;
		
		try {
			Date extractDate = DateUtil.toDate(extractDateString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			// check for existing extract by incident group id and date
			CostAccrualExtract costAccrualExtract = costAccrualExtractDao.getExtractByDate(null,incidentGroupId,extractDate);	

			/*
			 * if extract doesn't exist, create one
			 */
			if(null == costAccrualExtract){
				/*
				 * per defect 4182
				 *  check for previous un-finalized extract
				 *   and delete it
				 */
				Collection<CostAccrualExtract> extracts = costAccrualExtractDao.getUnFinalExtractsBeforeDate(null, incidentGroupId, extractDate, "DESC");
				if(CollectionUtility.hasValue(extracts)){
					for(CostAccrualExtract extract : extracts){
						costAccrualExtractDao.flushAndEvict(extract);
						this.deletedExtractIds.add(extract.getId());
						costAccrualExtractDao.delete(extract);
					}
				}
				
				costAccrualExtract = new CostAccrualExtractImpl();
				IncidentGroup incidentGroup = new IncidentGroupImpl();
				incidentGroup.setId(incidentGroupId);
				
				costAccrualExtract.setIncidentGroup(incidentGroup);
				costAccrualExtract.setExtractDate(extractDate);
				costAccrualExtract.setFinalized(StringBooleanEnum.N);
				costAccrualExtract.setIsExported(StringBooleanEnum.N);
				costAccrualExtract.setIsFromSingleIncident(StringBooleanEnum.N);
				
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			}else{
				/*
				 *  clear any existing extract_rsc records
				 *  all extract rsc records for this date will get rebuilt
				 */
				costAccrualExtract.getCostAccrualExtractRscs().clear();
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			}

			// get the last fiscal date cutoff
			String fiscalDate=this.getLastFiscalDate(extractDate);
			String fiscalYear=this.getFiscalYear(extractDate);

			// create the cost accrual extract resource records for previous fiscal year
			int cnt = costAccrualExtractDao.getCostAccrualExtResPrevYearIGCount(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0)
				costAccrualExtractDao.createCostAccrualExtResPrevYearIG(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			
			// get previous extractId if it exists
			Long previousExtractId=costAccrualExtractDao.getPreviousExtractId(costAccrualExtract.getId(), null, incidentGroupId);
			
			cnt=costAccrualExtractDao.getCostAccrualExtractResourcesIGCount(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0){
				// create the cost accrual extract resource records
				costAccrualExtractDao.createCostAccrualExtractResourcesIG(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			}
			
			// create ad draw down records
			costAccrualExtractDao.createADDrawDownResourcesIG(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			
			// update the change_amount field
			costAccrualExtractDao.updateExtractResourceChangeAmountIG(costAccrualExtract.getId(), previousExtractId, extractDateString,fiscalDate);
			
			// create any negative cost accrual extract resource records
			if(LongUtility.hasValue(previousExtractId)){
				int count2=costAccrualExtractDao.getUpdateMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count2 > 0){
					//costAccrualExtractDao.updateNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
				
				int count=costAccrualExtractDao.getMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count>0){
					costAccrualExtractDao.createNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
			}
			
			// get updated entity
			costAccrualExtract=costAccrualExtractDao.getById(costAccrualExtract.getId(), CostAccrualExtractImpl.class);
			
			// build the extractVo
			extractVo = CostAccrualExtractVo.getInstance(costAccrualExtract, true);

			// get the totals
			BigDecimal totalAmt=costAccrualExtractDao.getTotalAmountByExtractId(costAccrualExtract.getId());
			BigDecimal changeAmt=costAccrualExtractDao.getTotalChangeAmountByExtractId(costAccrualExtract.getId());
			extractVo.setTotalAmount(totalAmt);
			extractVo.setChangeAmount(changeAmt);
			
			// flush
			costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			
		}catch(Exception e){
			throw e;
		}
		
		return extractVo;
	}

	public CostAccrualExtractVo runExtractIncidentGroup2(String extractDateString, Long incidentGroupId) throws Exception{
		CostAccrualExtractVo extractVo = null;
		
		try {
			Date extractDate = DateUtil.toDate(extractDateString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			// check for existing extract by incident group id and date
			CostAccrualExtract costAccrualExtract = costAccrualExtractDao.getExtractByDate(null,incidentGroupId,extractDate);	

			/*
			 * if extract doesn't exist, create one
			 */
			if(null == costAccrualExtract){
				/*
				 * per defect 4182
				 *  check for previous un-finalized extract
				 *   and delete it
				 */
				Collection<CostAccrualExtract> extracts = costAccrualExtractDao.getUnFinalExtractsBeforeDate(null, incidentGroupId, extractDate, "DESC");
				if(CollectionUtility.hasValue(extracts)){
					for(CostAccrualExtract extract : extracts){
						costAccrualExtractDao.flushAndEvict(extract);
						this.deletedExtractIds.add(extract.getId());
						costAccrualExtractDao.delete(extract);
					}
				}
				
				costAccrualExtract = new CostAccrualExtractImpl();
				IncidentGroup incidentGroup = new IncidentGroupImpl();
				incidentGroup.setId(incidentGroupId);
				
				costAccrualExtract.setIncidentGroup(incidentGroup);
				costAccrualExtract.setExtractDate(extractDate);
				costAccrualExtract.setFinalized(StringBooleanEnum.N);
				costAccrualExtract.setIsExported(StringBooleanEnum.N);
				costAccrualExtract.setIsFromSingleIncident(StringBooleanEnum.N);
				
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			}else{
				/*
				 *  clear any existing extract_rsc records
				 *  all extract rsc records for this date will get rebuilt
				 */
				costAccrualExtract.getCostAccrualExtractRscs().clear();
				costAccrualExtractDao.save(costAccrualExtract);
				costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			}

			// get the last fiscal date cutoff
			String fiscalDate=this.getLastFiscalDate(extractDate);
			String fiscalYear=this.getFiscalYear(extractDate);

			//Collection<CostAccrualGroupVo> vos = costAccrualExtractDao.getGroupNumbers(incidentGroupId, extractDate);
			//if(CollectionUtility.hasValue(vos)){
			//	costAccrualExtractDao.createGroupExtractRecords(costAccrualExtract.getId(), fiscalYear, vos);
			//}
			costAccrualExtractDao.createGroupExtractRecords2(costAccrualExtract.getId(), incidentGroupId, extractDate);

			// get updated entity
			costAccrualExtract=costAccrualExtractDao.getById(costAccrualExtract.getId(), CostAccrualExtractImpl.class);
			
			// build the extractVo
			extractVo = CostAccrualExtractVo.getInstance(costAccrualExtract, true);

			// get the totals
			BigDecimal totalAmt=costAccrualExtractDao.getTotalAmountByExtractId(costAccrualExtract.getId());
			BigDecimal changeAmt=costAccrualExtractDao.getTotalChangeAmountByExtractId(costAccrualExtract.getId());
			extractVo.setTotalAmount(totalAmt);
			extractVo.setChangeAmount(changeAmt);
			
			// flush
			costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			
			/*
			// get previous extractId if it exists
			Long previousExtractId=costAccrualExtractDao.getPreviousExtractId(costAccrualExtract.getId(), null, incidentGroupId);
			
			int cnt=costAccrualExtractDao.getCostAccrualExtractResourcesIGCount(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			if(cnt > 0){
				// create the cost accrual extract resource records
				costAccrualExtractDao.createCostAccrualExtractResourcesIG(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			}
			
			// create ad draw down records
			costAccrualExtractDao.createADDrawDownResourcesIG(costAccrualExtract.getId(), incidentGroupId, extractDateString, fiscalDate, fiscalYear);
			
			// update the change_amount field
			costAccrualExtractDao.updateExtractResourceChangeAmountIG(costAccrualExtract.getId(), incidentGroupId, extractDateString);

			
			// create any negative cost accrual extract resource records
			if(LongUtility.hasValue(previousExtractId)){
				int count2=costAccrualExtractDao.getUpdateMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count2 > 0){
					//costAccrualExtractDao.updateNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
				
				int count=costAccrualExtractDao.getMissingAccrualsFromPreviousExtract(previousExtractId, costAccrualExtract.getId());
				if(count>0){
					costAccrualExtractDao.createNegativeAccruals(previousExtractId, costAccrualExtract.getId());
				}
			}
			
			// get updated entity
			costAccrualExtract=costAccrualExtractDao.getById(costAccrualExtract.getId(), CostAccrualExtractImpl.class);
			
			// build the extractVo
			extractVo = CostAccrualExtractVo.getInstance(costAccrualExtract, true);

			// get the totals
			BigDecimal totalAmt=costAccrualExtractDao.getTotalAmountByExtractId(costAccrualExtract.getId());
			BigDecimal changeAmt=costAccrualExtractDao.getTotalChangeAmountByExtractId(costAccrualExtract.getId());
			extractVo.setTotalAmount(totalAmt);
			extractVo.setChangeAmount(changeAmt);
			
			// flush
			costAccrualExtractDao.flushAndEvict(costAccrualExtract);
			*/
			
		}catch(Exception e){
			throw e;
		}
		
		return extractVo;
	}

	private String getLastFiscalDate(Date extractDate) throws Exception {

		int year=DateUtil.getYearAsInt(extractDate);
		Date fDate=DateUtil.toDate("10/1/"+year, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		
		// if extractDate before fdate, then subtract 1 year from fdate
		if(extractDate.before(fDate)){
			year=year-1;
			fDate=DateUtil.toDate("10/1/"+year, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			return DateUtil.toDateString(fDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}
		
		// if extractDate equal/after fdate, then use fdate
		if(extractDate.after(fDate) || DateUtil.isSameDate(extractDate, fDate)){
			return DateUtil.toDateString(fDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}

		throw new Exception("Unable to resolve Last Fiscal Date");
	}
	
	private String getFiscalYear(Date extractDate) throws Exception {
		
		int year=DateUtil.getYearAsInt(extractDate);
		Date fDate=DateUtil.toDate("10/1/"+year, DateUtil.MM_SLASH_DD_SLASH_YYYY);
		String fiscalYear = null;
		
		if(extractDate.before(fDate)){
			fiscalYear = DateUtil.toDateString(fDate, DateUtil.YYYY).substring(2);
		}else {
			year=year+1;
			fDate=DateUtil.toDate("10/1/"+year, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			fiscalYear = DateUtil.toDateString(fDate, DateUtil.YYYY).substring(2);
		}
		return fiscalYear;
	}
	
}
