package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.AircraftDetailReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.AnalysisReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostReportFilterQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostShareReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostShareShiftItemCodeReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostShareSummaryReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.DetailSummaryReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.GroupCategoryTotalReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.SummaryByResourceReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.DetailByResourceReportQuery;
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
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.SQLQuery;



public class ReportCostDaoHibernate extends TransactionSupportImpl implements ReportCostDao {
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getPersonnelTimeReportData(gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AircraftDetailSubReportData> getAircraftDetailSubReportData(AircraftDetailReportFilter filter, Long incidentId) throws PersistenceException {

		Collection<AircraftDetailSubReportData> subReportData = new ArrayList<AircraftDetailSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AircraftDetailReportQuery.getAircraftDetailSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(AircraftDetailSubReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		crt.addScalar("flightHrs", Double.class.getName());
		crt.addScalar("galWater", Double.class.getName());
		crt.addScalar("galRetard", Double.class.getName());
		crt.addScalar("lbsCargo", Double.class.getName());
		crt.addScalar("numOfLoads", Double.class.getName());
		crt.addScalar("numOfTrips", Double.class.getName());
		crt.addScalar("numOfPax", Double.class.getName());
		
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<CostReportChartReportData> getAircraftDetailSubReportChartData(AircraftDetailReportFilter filter, Long incidentId) throws PersistenceException {

		Collection<CostReportChartReportData> subReportChartData = new ArrayList<CostReportChartReportData>();
			
		SQLQuery query = getHibernateSession().createSQLQuery(AircraftDetailReportQuery.getAircraftDetailSubReportChartDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		query.setResultTransformer(crt);

		try {
			subReportChartData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportChartData;
	}
	
	public Collection<DailyCostComparisonICSubReportData> getDailyCostComparisonICSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		
		Collection<DailyCostComparisonICSubReportData> subReportData = new ArrayList<DailyCostComparisonICSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getDailyCostComparisonICSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(DailyCostComparisonICSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("averageCost", Double.class.getName());
		crt.addScalar("standardCost", Double.class.getName());
		crt.addScalar("difference", Double.class.getName());
				
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
		
	}
	
	public Collection<DailyCostComparisonRESRSubReportData> getDailyCostComparisonRESRSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		
		Collection<DailyCostComparisonRESRSubReportData> subReportData = new ArrayList<DailyCostComparisonRESRSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getDailyCostComparisonRESRSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(DailyCostComparisonRESRSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("averageCost", Double.class.getName());
		crt.addScalar("standardCost", Double.class.getName());
		crt.addScalar("difference", Double.class.getName());
				
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourcesWithDailyCostExceeds10000SubReportData> getResourcesWithDailyCostExceeds10000SubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourcesWithDailyCostExceeds10000SubReportData> subReportData = new ArrayList<ResourcesWithDailyCostExceeds10000SubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourcesWithDailyCostExceeds10000SubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourcesWithDailyCostExceeds10000SubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("costToDate", Double.class.getName());
		crt.addScalar("currentDateCost", Double.class.getName());
		crt.addScalar("maxUnitCost", Double.class.getName());
		crt.addScalar("rateUnits", Integer.class.getName());
						
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData> getResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData> subReportData = new ArrayList<ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourcesWithActualTimePostingButThreeOrMoreDaysSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourcesWithActualTimePostingButThreeOrMoreDaysSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("unpostedDays", Integer.class.getName());
		crt.addScalar("maxUnitCost", Double.class.getName());
		crt.addScalar("rateUnits", Integer.class.getName());
						
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourcesWithNoActualTimePostedSubReportData> getResourcesWithNoActualTimePostedSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourcesWithNoActualTimePostedSubReportData> subReportData = new ArrayList<ResourcesWithNoActualTimePostedSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourcesWithNoActualTimePostedSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourcesWithNoActualTimePostedSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("unpostedDays", Integer.class.getName());
		crt.addScalar("maxUnitCost", Double.class.getName());
		crt.addScalar("rateUnits", Integer.class.getName());
						
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourcesWithNoAgencyAssignedSubReportData> getResourcesWithNoAgencyAssignedSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourcesWithNoAgencyAssignedSubReportData> subReportData = new ArrayList<ResourcesWithNoAgencyAssignedSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourcesWithNoAgencyAssignedSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourcesWithNoAgencyAssignedSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("maxUnitCost", Double.class.getName());
		crt.addScalar("exceptn", String.class.getName());
								
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
		
	public Collection<ResourcesWithNoDailyCostRecordsSubReportData> getResourcesWithNoDailyCostRecordsSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourcesWithNoDailyCostRecordsSubReportData> subReportData = new ArrayList<ResourcesWithNoDailyCostRecordsSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourcesWithNoDailyCostRecordsSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourcesWithNoDailyCostRecordsSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("maxUnitCost", Double.class.getName());
		crt.addScalar("exceptn", String.class.getName());
										
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourceItemCodeByCostSubReportData> getResourceItemCodeByCostSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourceItemCodeByCostSubReportData> subReportData = new ArrayList<ResourceItemCodeByCostSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourceItemCodeByCostSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourceItemCodeByCostSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("unitCost", Double.class.getName());
		crt.addScalar("rateUnits", Integer.class.getName());
						
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	public Collection<ResourceItemCodeByCostOHPersonnelSubReportData> getResourceItemCodeByCostOHPersonnelSubReportData(AnalysisReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<ResourceItemCodeByCostOHPersonnelSubReportData> subReportData = new ArrayList<ResourceItemCodeByCostOHPersonnelSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(AnalysisReportQuery.getResourceItemCodeByCostOHPersonnelSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ResourceItemCodeByCostOHPersonnelSubReportData.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("unitCost", Double.class.getName());
		crt.addScalar("rateUnits", Integer.class.getName());
						
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}



	
	@SuppressWarnings("unchecked")
	public Collection<CheckboxListVo> getCostReportFilter(Long id, String filterName, boolean isIncidentGroup) throws PersistenceException {
		List<CheckboxListVo> costReportFilterList = new ArrayList<CheckboxListVo>();
			
		SQLQuery query = getHibernateSession().createSQLQuery(CostReportFilterQuery.getCostReportFilterQuery(id, filterName, isIncidentGroup,super.isOracleDialect()));			CustomResultTransformer crt = new CustomResultTransformer(CheckboxListVo.class);
		query.setResultTransformer(crt);

		try {
			costReportFilterList = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		CollectionUtils.filter(costReportFilterList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CheckboxListVo data = (CheckboxListVo) object;
				// removed data if the lable is null 
				return data.getLabel()!=null;
			}
		});
			
		for(CheckboxListVo cv : costReportFilterList) 
			cv.setSelected(false);
			
		return costReportFilterList;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DetailByResourceSubReportData> getDetailByResourceSubReportData(GroupCategoryTotalReportFilter filter, Long incidentId) throws PersistenceException {
		Collection<DetailByResourceSubReportData> subReportData = new ArrayList<DetailByResourceSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(DetailByResourceReportQuery.getDetailByResourceSubReportDataQuery(filter, incidentId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(DetailByResourceSubReportData.class);
		//crt.addScalar("id", Long.class.getName());
		crt.addScalar("unitcost", Double.class.getName());
		crt.addScalar("rateunits", Double.class.getName());
		crt.addScalar("costamount", Double.class.getName());
								
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	
	}
	
	@SuppressWarnings("unchecked")
	public Collection<CostReportSubReportData> getGroupCategoryTotalSubReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		List<CostReportSubReportData> groupCategoryTotalReportData = new ArrayList<CostReportSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(GroupCategoryTotalReportQuery.getGroupCategoryTotalReportDataQuery(filter,super.isOracleDialect()));
		CustomResultTransformer crt = new CustomResultTransformer(CostReportSubReportData.class);
		crt.addScalar("costAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		
		query.setResultTransformer(crt);
		try {
			groupCategoryTotalReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return groupCategoryTotalReportData;
	}
		
	@SuppressWarnings("unchecked")
	public Collection<CostReportChartReportData> getGroupCategoryTotalSubReportChartData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		List<CostReportChartReportData> groupCategoryTotalReportChartData = new ArrayList<CostReportChartReportData>();
			
		SQLQuery query = getHibernateSession().createSQLQuery(GroupCategoryTotalReportQuery.getGroupCategoryTotalReportChartDataQuery(filter,super.isOracleDialect()));
		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		query.setResultTransformer(crt);

		try {
			groupCategoryTotalReportChartData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return groupCategoryTotalReportChartData;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<CostReportSubReportData> getGroupCategorySummarySubReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		
		List<CostReportSubReportData> groupCategorySummaryReportData = new ArrayList<CostReportSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(GroupCategoryTotalReportQuery.getGroupCategoryTotalReportDataQuery(filter,super.isOracleDialect()));
		CustomResultTransformer crt = new CustomResultTransformer(CostReportSubReportData.class);
		crt.addScalar("costAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		if(filter.getReportGroupForQuery().equals("Date"))
			crt.addScalar("groupByDate", Date.class.getName());
		query.setResultTransformer(crt);

		try {
			groupCategorySummaryReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return groupCategorySummaryReportData;
	}
		
	@SuppressWarnings("unchecked")
	public Collection<CostReportChartReportData> getGroupCategorySummarySubReportChartData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		List<CostReportChartReportData> groupCategorySummaryReportChartData = new ArrayList<CostReportChartReportData>();
			
		SQLQuery query = getHibernateSession().createSQLQuery(GroupCategoryTotalReportQuery.getGroupCategoryTotalReportChartDataQuery(filter,super.isOracleDialect()));
		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		query.setResultTransformer(crt);

		try {
			groupCategorySummaryReportChartData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return groupCategorySummaryReportChartData;
	}
	
//	@SuppressWarnings("unchecked")
//	public Collection<SummaryByResourceSubSubReportByDateData> getSummaryByResourceReportDataByDate(GroupCategoryTotalReportFilter filter) throws PersistenceException {
//		Collection<SummaryByResourceSubSubReportByDateData> subReportData = new ArrayList<SummaryByResourceSubSubReportByDateData>();
//		SQLQuery query = getHibernateSession().createSQLQuery(SummaryByResourceReportQuery.getSummaryByResourceReportDataQuery(filter,super.isOracleDialect()));
//
//		CustomResultTransformer crt = new CustomResultTransformer(SummaryByResourceSubSubReportByDateData.class);
//		crt.addScalar("costAmount", Double.class.getName());
//		crt.addScalar("resourceid", Long.class.getName());
//		crt.addScalar("incidentId", Long.class.getName());
//		if(filter.getReportGroupForQuery().equals("Date"))
//			crt.addScalar("groupByDate", Date.class.getName());
//		
//		query.setResultTransformer(crt);
//
//		try {
//			subReportData = query.list();
//		} catch (Exception e) {
//			throw new PersistenceException(e);
//		}
//
//		return subReportData;
//	}
	
	@SuppressWarnings("unchecked")
	public Collection<SummaryByResourceSubReportData> getSummaryByResourceReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		Collection<SummaryByResourceSubReportData> subReportData = new ArrayList<SummaryByResourceSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(SummaryByResourceReportQuery.getSummaryByResourceReportDataQuery(filter,super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(SummaryByResourceSubReportData.class);
		crt.addScalar("costAmount", Double.class.getName());
		crt.addScalar("resourceid", Long.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		if(filter.getReportGroupForQuery().equals("Date"))
			crt.addScalar("groupByDate", Date.class.getName());
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<CostReportSubReportData> getSummaryForCurrentDayReportData(GroupCategoryTotalReportFilter filter) throws PersistenceException {
		Collection<CostReportSubReportData> subReportData = new ArrayList<CostReportSubReportData>();
		SQLQuery query = getHibernateSession().createSQLQuery(DetailSummaryReportQuery.getDetailSummaryReportDataQuery(filter, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(CostReportSubReportData.class);
		crt.addScalar("costAmount", Double.class.getName());
		crt.addScalar("incidentId", Long.class.getName());
		crt.addScalar("currentDateCostAmount", Double.class.getName());
		query.setResultTransformer(crt);

		try {
			subReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}
		
	@SuppressWarnings("unchecked")
	public Collection<CostShareSubSubReportData> getCostShareReportData(CostShareReportFilter filter) throws PersistenceException {
		Collection<CostShareSubSubReportData> subsubReportData = new ArrayList<CostShareSubSubReportData>();

		CustomResultTransformer crt = new CustomResultTransformer(CostShareSubSubReportData.class);
		crt.addScalar("incidentId", Long.class.getName());
		crt.addScalar("costShareDate", Date.class.getName());
		crt.addScalar("dailyCost", Double.class.getName());
		crt.addScalar("cost", Double.class.getName());
		crt.addScalar("percentage", BigDecimal.class.getName());
		if(!filter.isSummaryReport)
			crt.addScalar("qty", Integer.class.getName());
		
		SQLQuery query = null;
		if(filter.isSummaryReport){
			//query = getHibernateSession().createSQLQuery(CostShareReportQuery.getSummaryReportDataQuery2(filter, super.isOracleDialect()));
			query = getHibernateSession().createSQLQuery(CostShareSummaryReportQuery.getCostShareSummaryReportQuery(filter, super.isOracleDialect()));
		}else if(filter.isShiftKindReport) {
			query = getHibernateSession().createSQLQuery(CostShareShiftItemCodeReportQuery.getCostShareShiftItemCodeReportQuery(filter, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(CostShareReportQuery.getShiftKindReportDataQuery2(filter, super.isOracleDialect()));
		}else if(filter.isDetailReport)
			query = getHibernateSession().createSQLQuery(CostShareReportQuery.getDetailReportDataQuery2(filter, super.isOracleDialect()));
		else if(filter.isWorkSheetReport)
			query = getHibernateSession().createSQLQuery(CostShareReportQuery.getWorkSheetReportDataQuery2(filter, super.isOracleDialect()));

		query.setResultTransformer(crt);
		
		try {
			subsubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subsubReportData;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getAgencyNameList(CostShareReportFilter filter) throws PersistenceException {
		List<String> agencyNames = new ArrayList<String>();
		SQLQuery query = getHibernateSession().createSQLQuery(CostShareReportQuery.getAgencyNamesQuery(filter, super.isOracleDialect()));

		try {
			agencyNames = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return agencyNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCategoryList(CostShareReportFilter filter) throws PersistenceException {
		List<String> categoryList = new ArrayList<String>();
		SQLQuery query = getHibernateSession().createSQLQuery(CostShareReportQuery.getCategoryListQuery(filter, super.isOracleDialect()));

		try {
			categoryList = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return categoryList;
	}
}
