package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemImpl;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostProjectionItemQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostProjectionQuery;
import gov.nwcg.isuite.core.reports.data.CostProjectionCategoryDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionSubReportData;
import gov.nwcg.isuite.core.reports.data.CostProjectionTotalSubReportData;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



public class CostProjectionDaoHibernate extends TransactionSupportImpl implements CostProjectionDao {

	private final CrudDao<Projection> crudDao;
	
	/**
	 * Constructor.
	 * @param crudDao can't be null
	 * @param crudDaoRsc can't be null
	 */
	public CostProjectionDaoHibernate(final CrudDao<Projection> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionDao#getProjectionGrid(java.lang.Long, java.lang.Long)
	 */
	public Collection<ProjectionVo> getProjectionGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionImpl.class);
		
		if(LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));
		
		if(LongUtility.hasValue(incidentGroupId)){
			crit.createAlias("incident", "i", CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("incident.incidentGroups", "ig", CriteriaSpecification.LEFT_JOIN);
			
			crit.add(Restrictions.disjunction()
					.add(Restrictions.eq("incidentGroupId", incidentGroupId))
					.add(Restrictions.eq("ig.id", incidentGroupId)));
		}
			
		crit.addOrder(Order.asc("startDate"));
		
		Collection<Projection> entities = crit.list();
		
		try{
			Collection<ProjectionVo> voList = ProjectionVo.getInstances(entities, true);
			return voList;
		}catch(Exception e){
			String s = e.getMessage();
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionDao#getProjectionItem(java.lang.Long, java.lang.Long)
	 */
	public Collection<ProjectionItemVo> getIncidentSummaryGrid(Long projectionId) throws PersistenceException {
		Collection<ProjectionItemVo> projectionItems = new ArrayList<ProjectionItemVo>();
		
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getIncidentSummaryGridQuery(projectionId,super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemVo.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("projectionId", Long.class.getName());
		crt.addScalar("itemCode", String.class.getName());   
		//crt.addScalar("itemCodeGroup", String.class.getName()); 
		crt.addScalar("quantity", Integer.class.getName());
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
		crt.addScalar("averageCost", BigDecimal.class.getName());
		crt.addScalar("totalCost", BigDecimal.class.getName());
		crt.addScalar("lastModifiedDate", Date.class.getName());
		

		query.setResultTransformer(crt);
		
		try {
			projectionItems = query.list();
			//return ProjectionItemVo.getInstances(projectionItems,true);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		
		return projectionItems;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionDao#getProjectionItem(java.lang.Long, java.lang.Long)
	 */
	public Collection<ProjectionItemVo> getManuallyAddProjectionGrid(Long projectionId) throws PersistenceException {
		Collection<ProjectionItemVo> projectionItems = new ArrayList<ProjectionItemVo>();
		
		SQLQuery query;

		query = getHibernateSession().createSQLQuery(CostProjectionQuery.getManuallyAddGridQuery(projectionId, super.isOracleDialect()));
		
		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemVo.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("projectionId", Long.class.getName());
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("itemCode", String.class.getName());
		crt.addScalar("quantity", Integer.class.getName());
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
		crt.addScalar("averageCost", BigDecimal.class.getName());
		crt.addScalar("totalCost", BigDecimal.class.getName());
		
		query.setResultTransformer(crt);
		
		try {
			projectionItems = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return projectionItems;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionDao#getProjectionItem(java.lang.Long, java.lang.Long)
	 */
	public Collection<ProjectionItemVo> getSupportingCostGrid(Long projectionId) throws PersistenceException {
		Collection<ProjectionItemVo> projectionItems = new ArrayList<ProjectionItemVo>();
		
		SQLQuery query;

		query = getHibernateSession().createSQLQuery(CostProjectionQuery.getSupportingCostGridQuery(projectionId, super.isOracleDialect()));
		
		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemVo.class);
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
		crt.addScalar("averageCost", BigDecimal.class.getName());
		crt.addScalar("totalCost", BigDecimal.class.getName());

		query.setResultTransformer(crt);
		
		try {
			projectionItems = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return projectionItems;
	}
	
	public Collection<ProjectionItem> getProjectionItemData(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
		
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getProjectionItemDataQuery(incidentId,incidentGroupId, super.isOracleDialect()));
		
		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemImpl.class);
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("quantity", Integer.class.getName());
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
		crt.addScalar("totalCost", BigDecimal.class.getName());
		crt.addScalar("averageCost", BigDecimal.class.getName());
		crt.addScalar("supportCost",Boolean.class.getName());
		query.setResultTransformer(crt);
		
		try {
			projectionItems = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return projectionItems;
	}

	
	public Collection<ProjectionItem> getProjectionItemDataForCurrentDay(Long incidentId, Long incidentGroupId, String today) throws PersistenceException {
		Collection<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getProjectionItemDataForCurrentDayQuery(incidentId,incidentGroupId,today,super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemImpl.class);
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("quantity", Integer.class.getName());
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
		crt.addScalar("totalCost", BigDecimal.class.getName());
		crt.addScalar("averageCost", BigDecimal.class.getName());
		crt.addScalar("supportCost",Boolean.class.getName());
		query.setResultTransformer(crt);
		
		try {
			projectionItems = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return projectionItems;
	}
	
//	public Collection<ProjectionItem> getProjectionItemDataByItemIds(Long incidentId, Long incidentGroupId, List<Long> itemIds) throws PersistenceException {
//		List<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
//		
//		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getProjectionItemDataByItemIdsQuery(incidentId,incidentGroupId,itemIds, super.isOracleDialect()));
//
//		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemImpl.class);
//		crt.addScalar("itemId", Long.class.getName());
//		crt.addScalar("quantity", Integer.class.getName());
//		crt.addScalar("numberOfPersonnel", Integer.class.getName());
//		crt.addScalar("totalCost", BigDecimal.class.getName());
//		crt.addScalar("averageCost", BigDecimal.class.getName());
//		crt.addScalar("activityDate", Date.class.getName());
//		query.setResultTransformer(crt);
//		
//		try {
//			projectionItems = query.list();
//		} catch (Exception e) {
//			throw new PersistenceException(e);
//		}
//
//		return projectionItems;
//	}
	
	public Collection<KindVo> getManuallyAddProjectionItemCode(Long incidentId, Long incidentGroupId) throws PersistenceException {
		List<KindVo> itemCodes = new ArrayList<KindVo>();
		
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getManuallyAddProjectionItemCode(incidentId,incidentGroupId, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(KindVo.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("units", Integer.class.getName());
		crt.addScalar("people", Integer.class.getName());
		
		query.setResultTransformer(crt);
		
		try {
			itemCodes = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return itemCodes;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getGrid(java.lang.Long, java.lang.Long)
	 */
	public Collection<Projection> getProjectionsByName(String projectionName) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionImpl.class);
		projectionName = projectionName.toUpperCase();
		crit.add(Restrictions.eq("projectionName", projectionName));
		//crit.add(Expression.ge("auditable.createdDate", );
		crit.addOrder(Order.asc("auditable.createdDate"));
		
		Collection<Projection> entities = crit.list();
		
		try{
			return entities;
		}catch(Exception e){
			throw new PersistenceException(e);
		}	
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Projection persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Projection getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Projection persistable) throws PersistenceException {		
		for(ProjectionItem pi : persistable.getProjectionItems()) {
			setAuditableInfo(pi);
			for(ProjectionItemWorksheet piw : pi.getProjectionItemWorksheets())
				setAuditableInfo(piw);
		}
				
        crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Projection> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.getCostProjectionSummaryReport()
	 */
	public Collection<CostProjectionCategoryDetailSubReportData> getCostProjectionCategoryDetailReportData(CostProjectionReportFilter filter) throws PersistenceException {
		List<CostProjectionCategoryDetailSubReportData> costProjectionSubReportData = new ArrayList<CostProjectionCategoryDetailSubReportData>();
		
		SQLQuery query;
		
		if(filter.isSuppourtingData)
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailReportQuery2(filter, super.isOracleDialect()));
		else
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailReportQuery1(filter, super.isOracleDialect()));
			

		CustomResultTransformer crt = new CustomResultTransformer(CostProjectionCategoryDetailSubReportData.class);
		crt.addScalar("costAmount_01", Double.class.getName());
		crt.addScalar("costAmount_02", Double.class.getName());
		crt.addScalar("costAmount_03", Double.class.getName());
		crt.addScalar("costAmount_04", Double.class.getName());
		crt.addScalar("costAmount_05", Double.class.getName());
		crt.addScalar("costAmount_06", Double.class.getName());
		crt.addScalar("costAmount_07", Double.class.getName());
		crt.addScalar("week", Integer.class.getName());
		crt.addScalar("total", Double.class.getName());
		if(filter.isByDayReport) {
			crt.addScalar("quantity_01", Integer.class.getName());
			crt.addScalar("quantity_02", Integer.class.getName());
			crt.addScalar("quantity_03", Integer.class.getName());
			crt.addScalar("quantity_04", Integer.class.getName());
			crt.addScalar("quantity_05", Integer.class.getName());
			crt.addScalar("quantity_06", Integer.class.getName());
			crt.addScalar("quantity_07", Integer.class.getName());
		}
		
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return costProjectionSubReportData;		
	}
		
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.getCostProjectionSummaryReport()
	 */
	public Collection<CostProjectionTotalSubReportData> getCostProjectionCategoryDetailTotalReportData(CostProjectionReportFilter filter) throws PersistenceException {
		                                               
		List<CostProjectionTotalSubReportData> costProjectionSubReportData = new ArrayList<CostProjectionTotalSubReportData>();
		SQLQuery query;
		
		//query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalReportQuery(filter, super.isOracleDialect()));
		if(filter.isSuppourtingData)
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalReportQuery1(filter, super.isOracleDialect()));
		else
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalReportQuery2(filter, super.isOracleDialect()));
		
		
		CustomResultTransformer crt = new CustomResultTransformer(CostProjectionTotalSubReportData.class);
		crt.addScalar("projectionCost", Double.class.getName());
		crt.addScalar("costToDate", Double.class.getName());
	
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return costProjectionSubReportData;		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.getCostProjectionSummaryReport()
	 */
	public Collection<CostProjectionTotalSubReportData> getCostProjectionCostToDateTotalReportData(CostProjectionReportFilter filter) throws PersistenceException {
		List<CostProjectionTotalSubReportData> costProjectionSubReportData = new ArrayList<CostProjectionTotalSubReportData>();
		
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCostToDateTotalReportQuery(filter, super.isOracleDialect()));

		CustomResultTransformer crt = new CustomResultTransformer(CostProjectionTotalSubReportData.class);
		crt.addScalar("projectionCost", Double.class.getName());
		crt.addScalar("costToDate", Double.class.getName());
	
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
       
		return costProjectionSubReportData;		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.getCostProjectionSummaryReport()
	 */
	public Collection<CostReportChartReportData> getCostProjectionCategoryDetailChartReportData(CostProjectionReportFilter filter) throws PersistenceException {
		List<CostReportChartReportData> costProjectionSubReportData = new ArrayList<CostReportChartReportData>();
		
		SQLQuery query = null;
		if(!filter.isSuppourtingData && !filter.isByWeekChartReport())
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailGraphReportQuery1(filter, super.isOracleDialect()));
		else if(!filter.isSuppourtingData && filter.isByWeekChartReport())
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailGraphReportQueryByWeek1(filter, super.isOracleDialect()));
		else if(filter.isSuppourtingData && !filter.isByWeekChartReport())
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailGraphReportQuery2(filter, super.isOracleDialect()));
		else if(filter.isSuppourtingData && filter.isByWeekChartReport())
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionCategoryDetailGraphReportQueryByWeek2(filter, super.isOracleDialect()));
		
		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		if(!filter.isByWeekChartReport())
			crt.addScalar("date", Date.class.getName());
		
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return costProjectionSubReportData;		
	}
	
	public Collection<CostReportChartReportData> getCostProjectionTotalGraphReportData(CostProjectionReportFilter filter) throws PersistenceException {
		List<CostReportChartReportData> costProjectionSubReportData = new ArrayList<CostReportChartReportData>();
		
		SQLQuery query;
		if(filter.isSuppourtingData)
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalGraphReportQuery1(filter, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalGraphReportQueryBar1(filter, super.isOracleDialect()));
		else
			query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalGraphReportQuery2(filter, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostProjectionTotalGraphReportQueryBar2(filter, super.isOracleDialect()));
		
		
		
		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return costProjectionSubReportData;		
	}
	
	
	public Collection<CostReportChartReportData> getCostToDateTotalGraphReportData(CostProjectionReportFilter filter) throws PersistenceException {
		List<CostReportChartReportData> costProjectionSubReportData = new ArrayList<CostReportChartReportData>();
		
		SQLQuery query;
		query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostToDateTotalGraphReportQuery(filter, super.isOracleDialect()));
		//query = getHibernateSession().createSQLQuery(CostProjectionQuery.getCostToDateTotalGraphReportQueryBar(filter, super.isOracleDialect()));
		
		CustomResultTransformer crt = new CustomResultTransformer(CostReportChartReportData.class);
		crt.addScalar("totalAmount", Double.class.getName());
		
		query.setResultTransformer(crt);
		
		try {
			costProjectionSubReportData = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return costProjectionSubReportData;		
	}
}

