package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.ResourceInventoryDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceInventoryQuery;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

@SuppressWarnings("unchecked")
public class ResourceInventoryDaoHibernate extends TransactionSupportImpl implements ResourceInventoryDao {

	private final CrudDao<Resource> crudDao;
	
	public  ResourceInventoryDaoHibernate(final CrudDao<Resource> crudDao) {
		if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao can not be null");
	      }
	      this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#getGrid(java.lang.Long, java.lang.Long)
	 */
	public Collection<ResourceInventoryGridVo> getGrid(Long dispatchCenterId, Long userId) throws PersistenceException {
		String sql = ResourceInventoryQuery.GET_RESOURCES_INVENTORY_GRID_QUERY(dispatchCenterId, userId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(ResourceInventoryGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		//crt.addScalar("nonStandard", Boolean.class.getName());
		crt.addScalar("person", Boolean.class.getName());
		
		query.setResultTransformer(crt);
		
		Collection<ResourceInventoryGridVo> vos = query.list();
		
		return vos;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Resource persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Resource getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, ResourceImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Resource persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Resource> persistables) throws PersistenceException {
		for(Resource persistable : persistables) {
			crudDao.save(persistable);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#removeNonStandardResource(java.lang.Long, java.lang.Long)
	 */
	public void removeNonStandardResource(Long organizationId, Long resourceId) throws PersistenceException {
		Query q = getHibernateSession().createSQLQuery(ResourceInventoryQuery.REMOVE_NON_STANDARD_RESOURCE_QUERY);
		q.setParameter("organizationId", organizationId);
		q.setParameter("resourceId", resourceId);
		q.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#getAllByIds(java.util.Collection)
	 */
	public Collection<Resource> getAllByIds(Collection<Long> ids) throws PersistenceException {
		Collection<Resource> resources = null;
		
		if (ids != null && ids.size() > 0) {
			Criteria crit = (Criteria) getHibernateSession().createCriteria(ResourceImpl.class);
			crit.add(Expression.in("id", ids));
	        resources = crit.list();
		}
	    
	    return resources;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#getNonStdGrid(java.lang.Long, java.lang.Long)
	 */
	public Collection<ResourceInventoryGridVo> getNonStdGrid(Long dispatchCenterId, Long nonStdId) throws PersistenceException {
		String sql = ResourceInventoryQuery.GET_NON_STD_RESOURCES_GRID_QUERY(dispatchCenterId, nonStdId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(ResourceInventoryGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("nonStandard", Boolean.class.getName());
		
		query.setResultTransformer(crt);
		
		Collection<ResourceInventoryGridVo> vos = query.list();
		
		return vos;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#removeExcludedItems(java.lang.Long, java.util.Collection)
	 */
	public int removeExcludedItems(Long userId,Collection<Long> resourceIds) throws PersistenceException {
		int rslt=0;
		String sql = "";
		SQLQuery q = null;	
		
		if(CollectionUtility.hasValue(resourceIds)) {
			sql = ResourceInventoryQuery.REMOVE_EXCLUDED_RESOURCES_QUERY;
			q = getHibernateSession().createSQLQuery(sql);
			q.setParameter("userId", userId);
			q.setParameterList("resourceIds", resourceIds);
			rslt = q.executeUpdate();
		}else {
			sql = ResourceInventoryQuery.REMOVE_ALL_EXCLUDED_RESOURCES_QUERY;
			q = getHibernateSession().createSQLQuery(sql);
			q.setParameter("userId", userId);
			rslt = q.executeUpdate();
		}
		
		return rslt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#createExcludedItems(java.lang.Long, java.util.Collection)
	 */
	public int createExcludedItems(Long userId, Collection<Long> resourceIds) throws PersistenceException {
		int rslt=0;
		
		String sql = null;
		
		for(Long resourceId : resourceIds) {
			sql=ResourceInventoryQuery.createExcludedResourceQuery(userId, resourceId, super.isOracleDialect());
			SQLQuery q = getHibernateSession().createSQLQuery(sql);
	        rslt+=q.executeUpdate();
		}
		
		return rslt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceInventoryDao#getExcludedResources(java.lang.Long)
	 */
	public Collection<ResourceInventoryGridVo> getExcludedResources(Long userId) throws PersistenceException {
		String sql = ResourceInventoryQuery.getUserResourceExcludedQuery(userId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(ResourceInventoryGridVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		Collection<ResourceInventoryGridVo> vos = query.list();
		
		return vos;
	}
	
}
