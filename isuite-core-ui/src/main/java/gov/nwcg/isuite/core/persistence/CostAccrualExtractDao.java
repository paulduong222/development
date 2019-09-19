package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualSummaryReportData;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.CostAccrualGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.xml.financialexport.AccountingCodeSummaryType;
import gov.nwcg.isuite.xml.financialexport.AccrualDetailType;
import gov.nwcg.isuite.xml.financialexport.RCLineSummaryType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface CostAccrualExtractDao extends TransactionSupport, CrudDao<CostAccrualExtract> {

	/**
	 * @param incidentId
	 * @param incidentGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostAccrualExtractVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public CostAccrualExtract getExtractByDate(Long incidentId, Long incidentGroupId, Date date) throws PersistenceException;
	
	public CostAccrualExtract getLastFinalizedExtract(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public void save(CostAccrualExtract costAccrualExtract) throws PersistenceException;
	
	public void createCostAccrualExtractResources(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;

	public void createCostAccrualExtractResourcesIG(Long extractId
													,Long incidentGroupId
													, String extractDateString
													, String fiscalDate
													, String fiscalYear) throws PersistenceException ;
	
	public void updateExtractResourceChangeAmount(Long extractId,Long incidentId, String extractDateString,String fiscalDate) throws PersistenceException;

	public void updateExtractResourceChangeAmountIG(Long extractId,Long incidentGroupId, String extractDateString,String fiscalDate) throws PersistenceException;
	
	public BigDecimal getTotalAmountByExtractId(Long extractId) throws PersistenceException;
	
	public BigDecimal getTotalChangeAmountByExtractId(Long extractId) throws PersistenceException ;	

	public int getExtractCountBeforeDate(Long incidentId,Long incidentGroupId,Date date) throws PersistenceException ;
	
	public Collection<CostAccrualExtract> getExtractsBeforeDate(Long incidentId,Long incidentGroupId,Date date,String orderDirection) throws PersistenceException ;

	public Collection<CostAccrualExtract> getUnFinalExtractsBeforeDate(Long incidentId,Long incidentGroupId,Date date,String orderDirection) throws PersistenceException;
	
	public Long getPreviousExtractId(Long extractId, Long incidentId, Long incidentGroupId) throws PersistenceException;

	public Collection<CostAccrualSummaryReportData> getCostAccrualSummaryReportData(Long extractId) throws PersistenceException;
	
	public Collection<CostAccrualDetailReportData> getCostAccrualDetailReportData(Long extractId) throws PersistenceException;

	public Collection<CostAccrualAllDetailSubReportData> getCostAccrualAllDetailSubReportData(Long extractId) throws PersistenceException ;
	
	public Collection<CostAccrualAllDetailReportData> getCostAccrualAllDetailReportData(Long extractId) throws PersistenceException;
	
	public int getMissingAccrualsFromPreviousExtract(Long previousExtractId, Long currentExtractId) throws PersistenceException ;

	public int getUpdateMissingAccrualsFromPreviousExtract(Long previousExtractId, Long currentExtractId) throws PersistenceException;
		
	public void createNegativeAccruals(Long previousExtractId, Long currentExtractId) throws PersistenceException;
	
	public void updateNegativeAccruals(Long previousExtractId, Long currentExtractId) throws PersistenceException;
	
	public Collection<AccountingCodeSummaryType> getAccountingCodeSummaryTypes(Long extractId) throws PersistenceException;
	
	public Collection<RCLineSummaryType> getRCLineSummaryTypes(Long extractId, String accountCodeYr) throws PersistenceException;
	
	public Collection<AccrualDetailType> getAccrualDetailTypes(Long extractId, String accountCodeYr, String accrualCode) throws PersistenceException;
	
	public Collection<CostAccrualExtract> getUnexportedExtracts(Collection<Long> ids) throws PersistenceException;
	
	public void updateExportedAccruals(Collection<Long> incidentIds) throws PersistenceException;
	
	public void createADDrawDownResources(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
		
	public void createADDrawDownResourcesIG(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;

	public int getCostAccrualExtractResourcesCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	
	public int getCostAccrualExtractResourcesIGCount(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;

	public void updateAllFinAccrualsAsSingle(Long incidentId) throws PersistenceException;

	public Collection<CostAccrualGroupVo> getGroupNumbers(Long incidentGroupId, Date extractDate) throws PersistenceException;

	public void createGroupExtractRecords(Long extractId, String fiscalYear, Collection<CostAccrualGroupVo> vos) throws PersistenceException;

	public int getExtractFinalizedCountByDate(Long incidentId,Long incidentGroupId,Date date) throws PersistenceException;
	
	public int getCostAccrualExtractResourcesPrevYearCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	public int getCostAccrualExtractOTResourcesPrevYearCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	
	public void createCostAccrualExtResPrevYear(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	public void createCostAccrualExtResOTPrevYear(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	
	public int getCostAccrualExtResPrevYearIGCount(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	
	public void createCostAccrualExtResPrevYearIG(Long extractId
			,Long incidentGroupId
			, String extractDateString
			, String fiscalDate
			, String fiscalYear) throws PersistenceException ;

	public void createCostAccrualExtractResourcesOT(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	public int getCostAccrualExtractOTResourcesCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException;
	public int getMissingAccrualsFromPreviousExtractOT(Long previousExtractId, Long currentExtractId) throws PersistenceException;
	public void createNegativeAccrualsOT(Long previousExtractId, Long currentExtractId) throws PersistenceException;
	public void createGroupExtractRecords2(Long extractId, Long groupId, Date extractDate) throws PersistenceException;

}
