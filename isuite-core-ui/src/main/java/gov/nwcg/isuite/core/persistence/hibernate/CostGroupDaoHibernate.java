package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.impl.CostGroupImpl;
import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostGroupQuery;
import gov.nwcg.isuite.core.vo.CostGroupGridVo;
import gov.nwcg.isuite.core.vo.CostGroupResourceGridVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class CostGroupDaoHibernate extends TransactionSupportImpl implements CostGroupDao {
	private final CrudDao<CostGroup> crudDao;
	
	public CostGroupDaoHibernate(final CrudDao<CostGroup> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getGrid(gov.nwcg.isuite.core.filter.CostGroupFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostGroupGridVo> getGrid(CostGroupFilter filter) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(CostGroupImpl.class);
		
		crit.createAlias("incident", "i");
		crit.createAlias("i.incidentGroups", "ig", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("incidentShift", "is");
		
		crit.setProjection(Projections.projectionList()
				 .add(Projections.property("id"), "id")
				  .add(Projections.property("i.incidentName"), "incidentName")
				  .add(Projections.property("costGroupName"), "costGroupName")
				  .add(Projections.property("startDate"), "startDate")
				  .add(Projections.property("description"), "description")
				  .add(Projections.property("is.shiftName"), "shift")
		);
		
		crit.setResultTransformer(Transformers.aliasToBean(CostGroupGridVo.class));
		
		if(LongUtility.hasValue(filter.getIncidentId())) {
			crit.add(Restrictions.eq("incidentId", filter.getIncidentId()));
		}
		
		if(LongUtility.hasValue(filter.getIncidentGroupId())){
			crit.add(Restrictions.eq("ig.id", filter.getIncidentGroupId()));
		}
			
		// exclude deleted ones
		crit.add(Restrictions.isNull("this.deletedDate"));
		
		crit.addOrder(Order.asc("costGroupName"));
		
		return crit.list();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(CostGroup persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public CostGroup getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(CostGroup persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<CostGroup> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getAll(gov.nwcg.isuite.core.filter.CostGroupFilter)
	 */
	public Collection<CostGroupVo> getAll(CostGroupFilter filter) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(CostGroupImpl.class);
		
		crit.createAlias("incident", "i");
		crit.createAlias("i.incidentGroups", "ig", CriteriaSpecification.LEFT_JOIN);
		
		if (filter != null) {
			try {
				Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
				
				criteria.add( null != filter.getCostGroupName() && !filter.getCostGroupName().isEmpty()  ? new FilterCriteria("this.costGroupName",filter.getCostGroupName(),FilterCriteria.TYPE_ILIKE) : null);
				super.applyCrypticDateFilter(crit, filter.getCrypticDateFilterCodeForStartDate(), "this_.START_DATE");
				criteria.add( null != filter.getDescription() && !filter.getDescription().isEmpty()  ? new FilterCriteria("this.description",filter.getDescription(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != filter.getShift() && !filter.getShift().isEmpty()  ? new FilterCriteria("this.shift",filter.getShift(),FilterCriteria.TYPE_ILIKE) : null);
				criteria.add( null != filter.getIncidentName() && !filter.getIncidentName().isEmpty()  ? new FilterCriteria("i.incidentName",filter.getIncidentName(),FilterCriteria.TYPE_ILIKE) : null);
				
				criteria.add( new FilterCriteria("this.deletedDate",null,FilterCriteria.TYPE_ISNULL));
				
				if(null != filter.getIncidentId() && filter.getIncidentId() > 0) {
					criteria.add(new FilterCriteria("i.id", filter.getIncidentId(), FilterCriteria.TYPE_EQUAL));
				}
				
				if(null != filter.getIncidentGroupId() && filter.getIncidentGroupId() > 0) {
					criteria.add(new FilterCriteria("ig.id", filter.getIncidentGroupId(), FilterCriteria.TYPE_EQUAL));
				}
				
				CriteriaBuilder.addCriteria(crit, criteria);
				
			} catch(Exception e) {
				throw new PersistenceException(e);
			}
		}
		
		Collection<CostGroup> entities = crit.list();
		
		if(null != entities){
			try{
				return CostGroupVo.getInstances(entities,true);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}

		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getCostGroupAvailableOtherResourceGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostGroupResourceGridVo> getCostGroupAvailableOtherResourceGrid(Long costGroupId) throws PersistenceException {
		if(!LongUtility.hasValue(costGroupId)) {
			throw new IllegalArgumentException("costGroupId cannot be null.");
		}
		
		StringBuffer b = new StringBuffer();
		b.append(CostGroupQuery.getCostGroupAvailableOtherResourceGridQuery(costGroupId));
		
		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
		
//		if(null != costGroupResourceFilter.getCheckboxFilter()) {
//			if(costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()).size() > 0) {
//				query.setParameterList("requestCategories", costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//		}
		
		CustomResultTransformer crt = new CustomResultTransformer(CostGroupResourceGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getCostGroupSelectedOtherResourceGrid(java.lang.Long)
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupSelectedOtherResourceGrid(Long costGroupId) throws PersistenceException {
		if(!LongUtility.hasValue(costGroupId)) {
			throw new IllegalArgumentException("costGroupId cannot be null.");
		}
		
		StringBuffer b = new StringBuffer();
		b.append(CostGroupQuery.getCostGroupSelectedOtherResourceGridQuery(costGroupId));
		
		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
		
//		if(null != costGroupResourceFilter.getCheckboxFilter()) {
//			if(costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()).size() > 0) {
//				query.setParameterList("requestCategories", costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//		}
		
		CustomResultTransformer crt = new CustomResultTransformer(CostGroupResourceGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getCostGroupAvailableResourceGrid(java.lang.Long)
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupAvailableResourceGrid(Long costGroupId) throws PersistenceException {
		if(!LongUtility.hasValue(costGroupId)) {
			throw new IllegalArgumentException("costGroupId cannot be null.");
		}
		
		StringBuffer b = new StringBuffer();
		b.append(CostGroupQuery.getCostGroupAvailableResourceGridQuery(costGroupId, super.isOracleDialect()));
		
		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
		
//		if(null != costGroupResourceFilter.getCheckboxFilter()) {
//			if(costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()).size() > 0) {
//				query.setParameterList("requestCategories", costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//			if(costGroupResourceFilter.getCheckboxFilter().isExcludeFilled() || costGroupResourceFilter.getCheckboxFilter().isExcludeDemob()) {
//				query.setParameterList("assignmentStatuses", costGroupResourceFilter.getCheckboxFilter().getAssignmentStatusList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//		}
		
		CustomResultTransformer crt = new CustomResultTransformer(CostGroupResourceGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getCostGroupSelectedResourceGrid(java.lang.Long)
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupSelectedResourceGrid(Long costGroupId) throws PersistenceException {
		if(!LongUtility.hasValue(costGroupId)) {
			throw new IllegalArgumentException("costGroupId cannot be null.");
		}
		
		StringBuffer b = new StringBuffer();
		b.append(CostGroupQuery.getCostGroupSelectedResourceGridQuery(costGroupId, super.isOracleDialect()));
		
		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
		
//		if(null != costGroupResourceFilter.getCheckboxFilter()) {
//			if(costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()).size() > 0) {
//				query.setParameterList("requestCategories", costGroupResourceFilter.getCheckboxFilter().getReqCatList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//			if(costGroupResourceFilter.getCheckboxFilter().isExcludeFilled() || costGroupResourceFilter.getCheckboxFilter().isExcludeDemob()) {
//				query.setParameterList("assignmentStatuses", costGroupResourceFilter.getCheckboxFilter().getAssignmentStatusList(costGroupResourceFilter.getCheckboxFilter()));
//			}
//		}
		
		CustomResultTransformer crt = new CustomResultTransformer(CostGroupResourceGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getActiveAssignedResourcesCount(java.lang.Long)
	 */
	public int getActiveAssignedResourcesCount(Long costGroupId) throws PersistenceException {
		try {
			int cnt=0;
			
			String sql = CostGroupQuery.getActiveAssignedResourcesCountQuery(costGroupId);
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			Object rslt = query.uniqueResult();
			if(null != rslt)
				cnt=TypeConverter.convertToInteger(rslt).intValue();
			
			sql = CostGroupQuery.getActiveAssignedOtherResourcesCountQuery(costGroupId);
			query = super.getHibernateSession().createSQLQuery(sql);
			rslt = query.uniqueResult();
			
			if(null != rslt)
				cnt+=TypeConverter.convertToInteger(rslt).intValue();
			
			return cnt;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	public int getDailyCostAssignedCount(Long costGroupId) throws PersistenceException {
		try {
			int cnt=0;
			
			String sql = CostGroupQuery.getDailyCostAssignedCountQuery(costGroupId);
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			Object rslt = query.uniqueResult();
			if(null != rslt)
				cnt=TypeConverter.convertToInteger(rslt).intValue();

			return cnt;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public void removeDailyCostCostGroup(Long id) throws PersistenceException {
		String sql = "UPDATE ISW_INC_RES_DAILY_COST SET INCIDENT_SHIFT_ID = NULL WHERE COST_GROUP_ID = " + id + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		sql = "UPDATE ISW_INC_RES_DAILY_COST SET COST_GROUP_ID = NULL WHERE COST_GROUP_ID = " + id + " ";
		
		query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#removeResourcesFromPriorCostGroups(java.lang.Long, java.util.Collection)
	 */
	public void removeResourcesFromPriorCostGroups(Long costGroupId, Collection<Long> resourceIds) throws PersistenceException {
		if(CollectionUtility.hasValue(resourceIds)) {
			String sql = CostGroupQuery.REMOVE_RESOURCES_FROM_PRIOR_COST_GROUPS_QUERY;
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("costGroupId", costGroupId);
			
			query.setParameterList("resourceIds", resourceIds);
			
			query.executeUpdate();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#removeOtherResourcesFromPriorCostGroups(java.lang.Long, java.util.Collection)
	 */
	public void removeOtherResourcesFromPriorCostGroups(Long costGroupId, Collection<Long> resourceOtherIds) throws PersistenceException {
		if(CollectionUtility.hasValue(resourceOtherIds)) {
			String sql = CostGroupQuery.REMOVE_OTHER_RESOURCES_FROM_PRIOR_COST_GROUPS_QUERY;
			
			SQLQuery query = super.getHibernateSession().createSQLQuery(sql);
			
			query.setParameter("costGroupId", costGroupId);
			
			query.setParameterList("resourceOtherIds", resourceOtherIds);
			
			query.executeUpdate();
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupDao#getCostGroupDayShareDateCount(java.lang.Long, java.util.Date)
	 */
	public int getCostGroupDayShareDateCount(Long costGroupId, Date dt) throws PersistenceException {
	
		String sql = CostGroupQuery.getCostGroupDayShareDateCountQuery(costGroupId, dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		try{
			int cnt=TypeConverter.convertToInteger(rslt).intValue();
			return cnt;
		}catch(Exception e){
			
		}
		return 0;
	}

	public CostGroup getByCostGroupName(String name, Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostGroupImpl.class);
		
		crit.add(Expression.eq("costGroupName", name.toUpperCase()));
		crit.add(Expression.isNull("deletedDate"));
		crit.add(Expression.eq("incidentId", incidentId));
		
		CostGroup entity = (CostGroup)crit.uniqueResult();
		
		return entity;
	}

	public CostGroup getByCostGroupNameShift(String name, String shift, Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostGroupImpl.class);
		
		crit.createAlias("incidentShift", "shift");
		
		crit.add(Expression.eq("costGroupName", name.toUpperCase()));
		crit.add(Expression.isNull("deletedDate"));
		crit.add(Expression.eq("incidentId", incidentId));
		crit.add(Expression.eq("shift.shiftName", shift));
		
		CostGroup entity = (CostGroup)crit.uniqueResult();
		
		return entity;
	}
	
	public Collection<CostGroup> getByIncidentId(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostGroupImpl.class);
		
		crit.add(Expression.eq("incidentId", incidentId));

		return (Collection<CostGroup>)crit.list();
		
	}

	public Long getIncidentId(Long costGroupId) throws PersistenceException {
		String sql="select incident_id from isw_cost_group where id = " + costGroupId + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				Long id = TypeConverter.convertToLong(rslt);
				return id;
			}catch(Exception smother){}
		}
		
		return null;
	}
}
