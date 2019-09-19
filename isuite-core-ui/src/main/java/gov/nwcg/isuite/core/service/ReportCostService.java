package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;
import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;


public interface ReportCostService {
	
	/**
	 * 
	 * @param filter
	 * @param incidentId
	 * @param incidentResourceId
	 * @return
	 * @throws ServiceException
	 * @throws PersistenceException 
	 */
	public DialogueVo generateAircraftDetailReport(AircraftDetailReportFilter filter,DialogueVo dialogueVo) throws Exception;

	public DialogueVo generateGroupCategorySummaryReport(GroupCategoryTotalReportFilter filter,DialogueVo dialogueVo) throws Exception;

	public DialogueVo generateSummaryByResourceReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws Exception;

	public DialogueVo generateSummaryForCurrentDayReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws Exception;

	public DialogueVo generateDetailByResourceReport(GroupCategoryTotalReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo generateAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateDailyCostComparisonICAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateDailyCostComparisonRESRAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourcesWithDailyCostExceeds10000AnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourcesWithActualTimePostingButThreeOrMoreDaysAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourcesWithNoActualTimePostedAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourcesWithNoDailyCostRecordsAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourceItemCodeByCostAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateResourceItemCodeByCostOHPersonnelAnalysisReport(AnalysisReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getGroupCategoryTotalFilter(Long id, String filterName, boolean isIncidentGroup, DialogueVo dialogueVo) throws Exception;
	
	public DialogueVo generateGroupCategoryTotalReport(GroupCategoryTotalReportFilter filter,DialogueVo dialogueVo) throws Exception;
	
	public DialogueVo generateCostShareReport(CostShareReportFilter filter,DialogueVo dialogueVo) throws Exception;
}


