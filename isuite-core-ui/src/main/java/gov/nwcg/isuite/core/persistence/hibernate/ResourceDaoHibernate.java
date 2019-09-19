package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.filter.ResourcePersonFilter;
import gov.nwcg.isuite.core.filter.impl.ResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceQuery;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.ResourcePersonVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.HibernateProperties;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * 
 * @author bsteiner
 */
public class ResourceDaoHibernate extends TransactionSupportImpl implements ResourceDao {
	private final CrudDao<Resource> crudDao;

	private static final Logger LOG = Logger.getLogger(ResourceDaoHibernate.class);

	/*
	 * Constructor
	 */
	public ResourceDaoHibernate(final CrudDao<Resource> crudDao) {
		if ( crudDao == null ) {
			LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Resource getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	public Resource getByIdInWorkArea(Long id,Long workAreaId) throws PersistenceException {
		try{
			StringBuffer sql = new StringBuffer()
			.append("SELECT ID FROM ISW_RESOURCE WHERE PERMANENT_RESOURCE_ID = " + id + " " +
					"AND ID IN (SELECT RESOURCE_ID FROM ISW_WORK_AREA_RESOURCE WHERE WORK_AREA_ID = " + workAreaId + ") ");

			SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());

			List<Object> list = query.list();

			if( null != list && list.size() > 0){
				Long resId = TypeConverter.convertToLong(list.get(0));

				return this.getById(resId, ResourceImpl.class);
			}

		}catch(Exception e){
			throw new PersistenceException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
	 */
	public void delete(Resource persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(Resource persistable) throws PersistenceException {
		if(null != persistable.getId() && persistable.getId()>0)
			super.getHibernateSession().merge(persistable);
		else
			crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Resource> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.TransferableDao#getByUniqueIdentity(java.lang.String, java.lang.Class)
	 */
	public Resource getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException, DuplicateItemException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceDao#getGrid(gov.nwcg.isuite.core.filter.ResourceFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ResourceGridVo> getGrid(ResourceFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);

		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("resourceName"), "resourceName")
				.add(Projections.property("firstName"), "lastName")
				.add(Projections.property("lastName"), "lastName")
				.add(Projections.property("contactName"), "contactName")
				.add(Projections.property("nameOnPictureId"), "nameOnPictureId")
				.add(Projections.property("permanent"), "permanent")
				.add(Projections.property("leader"), "leader")
				.add(Projections.property("leaderType"), "leaderType")
				.add(Projections.property("enabled"), "enabled")
				.add(Projections.property("person"), "person")
				.add(Projections.property("contracted"), "contracted")
				.add(Projections.property("phone"), "phone")
				.add(Projections.property("email"), "email")
				.add(Projections.property("other1"), "other1")
				.add(Projections.property("other2"), "other2")
				.add(Projections.property("other3"), "other3")
				.add(Projections.property("parentResourceId"), "parentResourceId")
				.add(Projections.property("permanentResourceId"), "permanentResourceId")
				.add(Projections.property("resourceClassification"), "resourceClassification")
				.add(Projections.property("resourceStatus"), "resourceStatus")
		);

		crit.setResultTransformer(Transformers.aliasToBean(ResourceGridVo.class));

		if (filter != null) {

			try{
				/*
				 * Add the criteria
				 */
				Collection<FilterCriteria> filterCriteria = ResourceFilterImpl.getFilterCriteria(filter);

				CriteriaBuilder.addCriteria(crit, filterCriteria);

			}catch(Exception e){
				throw new PersistenceException(e);
			}

		}

		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.resource.ResourceDao#getAllByIds(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Resource> getAllByIds(Collection<Long> ids) throws PersistenceException {
		Collection<Resource> resources;
		if (ids != null && ids.size() > 0) {
			Criteria crit = (Criteria) getHibernateSession().createCriteria(ResourceImpl.class);
			crit.add(Expression.in("id", ids));
			resources = crit.list();
		}
		else {
			resources = new ArrayList<Resource>();
		}
		return resources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.resource.ResourceDao#getByAssignmentId(java.lang.Long)
	 */
	public Resource getByAssignmentId(Long id) throws PersistenceException {
		Criteria crit = (Criteria) getHibernateSession().createCriteria(ResourceImpl.class);

		crit.createAlias("incidentResources", "ir");
		crit.createAlias("ir.incidentResourceWorkPeriod", "wp");
		crit.createAlias("wp.assignments", "a");

		if(null!=id)
			crit.add(Expression.eq("a.id", id));

		return (Resource)crit.uniqueResult();
	}

	public int disableResources(Collection<Long> resourceIds) throws PersistenceException{
		Query q = null;

		q = getHibernateSession().createQuery(ResourceQuery.DISABLE_RESOURCES_REMOVE_PARENT_ASSOC_QUERY );

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameter("component", Boolean.FALSE);
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();

		return rslt;
	}

	public int enableResources(Collection<Long> resourceIds) throws PersistenceException {
		Query q = getHibernateSession().createQuery(ResourceQuery.ENABLE_RESOURCES_QUERY);

		q.setParameter("enabled", Boolean.TRUE);
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();

		return rslt;
	}

	public void unRosterWorkAreaResourcesChildren(Collection<Long> resourceIds) throws PersistenceException {
		Query q = getHibernateSession().createQuery(ResourceQuery.REMOVE_WORK_AREA_RESOURCES_ROSTERED_CHILDREN_QUERY);

		q.setParameter("component", Boolean.FALSE);
		q.setParameterList("ids", resourceIds);
		q.executeUpdate();
	}

	public void unRosterIncidentResourcesChildren(Collection<Long> resourceIds) throws PersistenceException {
		Query q = getHibernateSession().createQuery(ResourceQuery.REMOVE_INCIDENT_RESOURCES_ROSTERED_CHILDREN_QUERY);

		q.setParameter("component", Boolean.FALSE);
		q.setParameterList("ids", resourceIds);
		q.executeUpdate();
	}

	public void deleteQualifications(Collection<Long> resourceKindIds) throws PersistenceException {

		StringBuffer sql = new StringBuffer()
		.append("DELETE FROM ISW_RESOURCE_KIND WHERE ID in ( :ids )");

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());

		query.setParameterList("ids", resourceKindIds);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<Resource> getMatchingResources(ResourceDuplicateFilter filter) throws PersistenceException {
		Criteria crit = (Criteria) getHibernateSession().createCriteria(ResourceImpl.class);

		crit.createAlias("organization", "org");
		crit.createAlias("primaryDispatchCenter", "pdc");

		if (null != filter.getResourceName() && !filter.getResourceName().isEmpty()) {
			crit.add(Expression.ilike("resourceName", (null != filter.getResourceName() ? filter.getResourceName() : "")));
		} else {
			crit.add(Expression.conjunction()
					.add(Expression.ilike("firstName", (null != filter.getFirstName() ? filter.getFirstName() : "")))
					.add(Expression.ilike("lastName", (null != filter.getLastName() ? filter.getLastName() : "")))
			);
		}

		crit.add(Expression.eq("enabled", Boolean.TRUE));
		crit.add(Expression.isNull("deletedDate"));
		if(BooleanUtility.isTrue(filter.getWorkAreaOnly())){
			String str="this_.id in (select resource_id from isw_work_area_resource) ";
			crit.add(Expression.sql(str));
			crit.add(Expression.isNotNull("permanentResourceId"));
		}else
			crit.add(Expression.isNull("permanentResourceId"));


		//do not include resource being edited
		if (filter.getResourceId() > 0L) {
			crit.add(Expression.ne("id", filter.getResourceId()));
		}

		crit.add(Expression.disjunction()
				.add(Expression.eq("org.id", (filter.getOrganizationId()>0L ? filter.getOrganizationId() : null)))
				.add(Expression.eq("pdc.id", (filter.getPrimaryDispatchCenterId()>0L ? filter.getPrimaryDispatchCenterId() : null)))			   	
		);

		return crit.list();
	}

	public void unrosterResource(Long resourceId) throws PersistenceException {
		StringBuffer sql = new StringBuffer()
		.append("UPDATE ISW_RESOURCE SET PARENT_RESOURCE_ID = NULL , LEADER_TYPE = 99, RESOURCE_CLASSIFICATION = '' WHERE ID = :id ");

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());

		query.setParameter("id", resourceId);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<ResourcePersonVo> getPersonResources(ResourcePersonFilter filter, HibernateProperties hp) throws PersistenceException {
		this.setProviderDialect(hp.getDialect());
		SQLQuery sql = getHibernateSession().createSQLQuery(ResourceQuery.getPersonResourcesSQLQuery(this.isOracleDialect(), filter));
		CustomResultTransformer crt = new CustomResultTransformer(ResourcePersonVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("organizationId", Long.class.getName());
		crt.addScalar("firstName", String.class.getName());
		crt.addScalar("lastName", String.class.getName());
		crt.addScalar("unitCode", String.class.getName());
		crt.addScalar("workPhone", String.class.getName());
		crt.addScalar("cellPhone", String.class.getName());
		crt.addScalar("email", String.class.getName());

		//      if(filter != null) {
		//         if(filter.getFirstName() != null && filter.getFirstName().trim().length() > 0) {
		//            sql.setString("firstName", filter.getFirstName());
		//         }
		//         if(filter.getLastName() != null && filter.getLastName().trim().length() > 0) {
		//            sql.setString("lastName", filter.getLastName());
		//         }
		//         if(filter.getUnitCode() != null && filter.getUnitCode().trim().length() > 0) {
		//            sql.setString("unitCode", filter.getUnitCode());
		//         }
		//      }

		sql.setResultTransformer(crt);
		return sql.list();
	}

	public int deleteIncidentResources2(Collection<Long> resourceIds, boolean removeParentAssociations) throws PersistenceException {
		String sql = ResourceQuery.getDeleteResourceQuery();

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameterList("ids", resourceIds);

		int rslt=query.executeUpdate();
		return rslt;
	}

	public int deleteIncidentResources(Collection<Long> resourceIds, boolean removeParentAssociations) throws PersistenceException {
		Query q = null;	   
		if(removeParentAssociations){
			q = getHibernateSession().createQuery(ResourceQuery.DELETE_INCIDENT_RESOURCES_REMOVE_PARENT_ASSOC_QUERY );
			q.setParameter("component", Boolean.FALSE);
		}else
			q = getHibernateSession().createQuery(ResourceQuery.DELETE_RESOURCES_QUERY);

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameter("deletedDate", Calendar.getInstance().getTime());
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();
		return rslt;
	}

	public int deleteWorkAreaResources(Collection<Long> resourceIds) throws PersistenceException {
		Query q = null;	   
		q = getHibernateSession().createQuery(ResourceQuery.DELETE_WORK_AREA_RESOURCES_REMOVE_PARENT_ASSOC_QUERY );

		q.setParameter("enabled", Boolean.FALSE);
		q.setParameter("component", Boolean.FALSE);
		q.setParameter("deletedDate", Calendar.getInstance().getTime());
		q.setParameterList("ids", resourceIds);

		int rslt = q.executeUpdate();
		return rslt;
	}

	public Resource getResourceLeader(Long parentResourceId, Integer leaderType) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);

		crit.add(Restrictions.eq("this.parentResourceId", parentResourceId));
		crit.add(Restrictions.eq("this.leaderType", leaderType));

		Collection<Resource> resources = crit.list();
		if(null != resources && resources.size()>0){
			return resources.iterator().next();
		}else{
			return null;
		}
	}

	public Collection<Resource> getPreviousLeader(Long parentResourceId, Integer leaderType, Long resourceId) throws PersistenceException {
		Resource entity = null;

		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);
		crit.add(Restrictions.ne("this.id", resourceId));
		crit.add(Restrictions.eq("this.leaderType", leaderType));
		crit.add(Restrictions.eq("this.parentResourceId", parentResourceId));

		return crit.list();
	}

	public void removeParentResourceLeader(Long parentResourceId, Integer leaderType, Long resourceId) throws PersistenceException{

		String sql = "UPDATE ISW_RESOURCE SET LEADER_TYPE = 99 WHERE PARENT_RESOURCE_ID = " + parentResourceId + " " + 
		"AND LEADER_TYPE = " + leaderType + " ";
		if(null != resourceId)
			sql = sql + "AND ID != " + resourceId + " ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public void unrosterUnassignedOrphanedChildren(Long resourceId) throws PersistenceException {
		String sql = "update isw_resource r set r.parent_resource_id = null " + 
		"where r.parent_resource_id = " + resourceId + " " +
		"and (select count(id) from isw_incident_resource " +
		"       where resource_id in ( " +
		"         select id from isw_resource where permanent_resource_id = r.id "+
		"       ) " +
		")=0 " ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceDao#getPermanentResourceByResId(java.lang.Long)
	 */
	public Resource getPermanentResourceByResId(Long rossResId) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);
		crit.add(Expression.eq("permanent", Boolean.TRUE));
		crit.add(Expression.eq("rossResId", rossResId));

		Object rslt = crit.uniqueResult();
		if(null != rslt)
			return (Resource)rslt;

		return null;
	}
	
	public Collection<Resource> getTopLevelByIncidentId(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ResourceImpl.class);
		crit.add(Expression.isNull("parentResourceId"));
		
		String sql="this_.id in (select resource_id from isw_incident_resource where incident_id = "+incidentId+" )";
		crit.add(Expression.sql(sql));
		
		return crit.list();
	}

	public Long getPermResourceByRossResId(Long rossResId) throws PersistenceException {
		String sql="select id from isw_resource " +
				   "where is_permanent = " + (super.isOracleDialect() ? 1 : true) + " "+
				   "and ross_res_id = " + rossResId;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		Long id=0L;
		
		try{
			if(null != rslt)
				id=TypeConverter.convertToLong(rslt);
		}catch(Exception smother){}
		
		return id;
	}
	
	public void persistSqls(Collection<String> sqls) throws PersistenceException {
		for(String sql : sqls){	
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}
	
	public Long getTopLevelResourceId(Long id) throws PersistenceException {
		Long topLevelId=id;
		
		boolean bcontinue=true;
		while(bcontinue==true){
			String sql="select parent_resource_id from isw_resource where id = "+topLevelId;
			SQLQuery q = getHibernateSession().createSQLQuery(sql);
			Object rslt=q.uniqueResult();
			if(null != rslt){
				try{
					Long pId=TypeConverter.convertToLong(rslt);
					topLevelId=pId;
				}catch(Exception ee){}
			}else
				bcontinue=false;
		}
		
		return topLevelId;
	}
	
}
