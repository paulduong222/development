package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.reports.data.CostProjectionCategoryDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionTotalSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import java.util.Collection;
import java.util.List;

public interface CostProjectionDao extends TransactionSupport, CrudDao<Projection> {
	public Collection<ProjectionVo> getProjectionGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;
	public Collection<ProjectionItemVo> getIncidentSummaryGrid(Long projectionId) throws PersistenceException;
	public Collection<ProjectionItemVo> getManuallyAddProjectionGrid(Long projectionId) throws PersistenceException;
	public Collection<ProjectionItemVo> getSupportingCostGrid(Long projectionId) throws PersistenceException;
	public Collection<ProjectionItem> getProjectionItemData(Long incidentId, Long incidentGroupId) throws PersistenceException;
	public Collection<ProjectionItem> getProjectionItemDataForCurrentDay(Long incidentId, Long incidentGroupId, String today) throws PersistenceException;
	
	public Collection<Projection> getProjectionsByName(String projectionName) throws PersistenceException;
	//public Collection<ProjectionItem> getProjectionItemDataByItemIds(Long incidentId, Long incidentGroupId, List<Long> itemIds) throws PersistenceException;
	public Collection<KindVo> getManuallyAddProjectionItemCode(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public Collection<CostProjectionCategoryDetailSubReportData> getCostProjectionCategoryDetailReportData(CostProjectionReportFilter filter) throws PersistenceException;
	public Collection<CostProjectionTotalSubReportData> getCostProjectionCategoryDetailTotalReportData(CostProjectionReportFilter filter) throws PersistenceException;
	public Collection<CostProjectionTotalSubReportData> getCostProjectionCostToDateTotalReportData(CostProjectionReportFilter filter) throws PersistenceException;
	public Collection<CostReportChartReportData> getCostProjectionCategoryDetailChartReportData(CostProjectionReportFilter filter) throws PersistenceException;
	public Collection<CostReportChartReportData> getCostProjectionTotalGraphReportData(CostProjectionReportFilter filter) throws PersistenceException;
	public Collection<CostReportChartReportData> getCostToDateTotalGraphReportData(CostProjectionReportFilter filter) throws PersistenceException;
}
