package gov.nwcg.isuite.core.persistence;


import gov.nwcg.isuite.core.reports.data.AircraftDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.data.CostReportSubReportData;
import gov.nwcg.isuite.core.reports.data.CostShareSubSubReportData;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonICSubReportData;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonRESRSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostOHPersonnelSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithDailyCostExceeds10000SubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoActualTimePostedSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoAgencyAssignedSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoDailyCostRecordsSubReportData;
import gov.nwcg.isuite.core.reports.data.SummaryByResourceSubReportData;
import gov.nwcg.isuite.core.reports.data.DetailByResourceSubReportData;
import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;
import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.vo.CheckboxListVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;

public interface ReportCostDao extends TransactionSupport {

   public void setSessionFactory(SessionFactory sf);
   
   /**
    * 
    * @param filter
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public Collection<DailyCostComparisonICSubReportData> getDailyCostComparisonICSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<DailyCostComparisonRESRSubReportData> getDailyCostComparisonRESRSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourcesWithDailyCostExceeds10000SubReportData> getResourcesWithDailyCostExceeds10000SubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData> getResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourcesWithNoActualTimePostedSubReportData> getResourcesWithNoActualTimePostedSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourcesWithNoDailyCostRecordsSubReportData> getResourcesWithNoDailyCostRecordsSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourcesWithNoAgencyAssignedSubReportData> getResourcesWithNoAgencyAssignedSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourceItemCodeByCostSubReportData> getResourceItemCodeByCostSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<ResourceItemCodeByCostOHPersonnelSubReportData> getResourceItemCodeByCostOHPersonnelSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException;
   public Collection<DetailByResourceSubReportData> getDetailByResourceSubReportData(GroupCategoryTotalReportFilter filter, Long incidentId) throws PersistenceException;
      
   public Collection<AircraftDetailSubReportData> getAircraftDetailSubReportData(AircraftDetailReportFilter filter, Long incidentId) throws PersistenceException;
   
   public Collection<CostReportChartReportData> getAircraftDetailSubReportChartData(AircraftDetailReportFilter filter, Long incidentId) throws PersistenceException;
   
   public Collection<CostReportSubReportData> getSummaryForCurrentDayReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<SummaryByResourceSubReportData> getSummaryByResourceReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<CheckboxListVo> getCostReportFilter(Long Id, String filterName, boolean isIncidentGroup) throws PersistenceException;
   
   public Collection<CostReportSubReportData> getGroupCategoryTotalSubReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<CostReportChartReportData> getGroupCategoryTotalSubReportChartData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<CostReportSubReportData> getGroupCategorySummarySubReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<CostReportChartReportData> getGroupCategorySummarySubReportChartData(GroupCategoryTotalReportFilter filter) throws PersistenceException;
   
   public Collection<CostShareSubSubReportData> getCostShareReportData(CostShareReportFilter filter) throws PersistenceException;
   
   public List<String> getAgencyNameList(CostShareReportFilter filter) throws PersistenceException;
   public List<String> getCategoryList(CostShareReportFilter filter) throws PersistenceException; 
}
