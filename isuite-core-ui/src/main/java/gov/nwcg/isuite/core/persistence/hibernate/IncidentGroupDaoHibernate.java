package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IapDefaultSettingsQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentGroupPrefsQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentGroupQuery;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;

/**
 * Dao for Incident Groups using hibernate.
 * 
 */
@SuppressWarnings("unchecked")
public class IncidentGroupDaoHibernate extends TransactionSupportImpl implements IncidentGroupDao {

	private final CrudDao<IncidentGroup> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao
	 *           can't be null
	 */
	public IncidentGroupDaoHibernate(final CrudDao<IncidentGroup> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#delete(gov.nwcg.isuite.domain.incident.Incident)
	 */
	public void delete(IncidentGroup group) throws PersistenceException {
		if (group == null) 
			throw new PersistenceException("incident can not be null");

		crudDao.delete(group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentGroupDao#getById(java.lang.Long)
	 */
	public IncidentGroup getById(Long id) throws PersistenceException {
		return this.getById(id, IncidentGroupImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public IncidentGroup getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentGroupImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(IncidentGroup group) throws PersistenceException {
		if (group == null) 
			throw new PersistenceException("Incoming object cannot be null.");

		crudDao.save(group);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void saveAll(Collection<IncidentGroup> groups) throws PersistenceException {
		if (groups == null) 
			throw new PersistenceException("Incoming object cannot be null.");

		crudDao.saveAll(groups);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getGrid(gov.nwcg.isuite.core.filter.IncidentGroupFilter)
	 */
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter) throws PersistenceException {
		if (null == filter) 
			throw new IllegalArgumentException("IncidentGroupFilter cannot be null");
		if (null == filter.getWorkAreaId()) 
			throw new IllegalArgumentException("IncidentGroupFilter.workAreaId can't be null!");

		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);

		if(filter.getIsPrivilegedUser() == false) {
			crit.createAlias("this.incidentGroupUsers", "igus");
			crit.add(Restrictions.eq("igus.userId", filter.getCurrentUserId()));
		}

//		crit.add(Restrictions.eq("this.workAreaId", filter.getWorkAreaId()));

// Client side filtering only
//		if(filter != null) {
//			if (filter.getGroupName() != null && filter.getGroupName().trim().length() > 0) 
//				crit.add(Expression.ilike("this.groupName", filter.getGroupName().trim().toUpperCase(), MatchMode.START));
//			if (filter.getCreatedDate() != null) {
//				crit.add(Expression.eq("auditable.createdDate", filter.getCreatedDate()));
//		  } else {
//  			if(StringUtility.hasValue(filter.getCreatedDateString())) {
//  				try {
//  					super.applyCrypticDateFilter(crit, filter.getCreatedDateString(), "this_.created_date");
//  				} catch (Exception e) {
//  					throw new PersistenceException(e);
//  				}
//  			}
//			}
//		}


		//"General Requirements - Grids.
		//When the grid initially displays, the system must sort data in the first column in ascending order."
		crit.addOrder(Order.asc("this.groupName"));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Collection<IncidentGroup> entities = crit.list();
		try {
			return IncidentGroupGridVo.getInstances(entities, true);
		}
		catch ( Exception e ) {
			throw new PersistenceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getGrid(gov.nwcg.isuite.core.filter.IncidentGroupFilter, java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter, Boolean isPrivilegedUser) throws PersistenceException {
		if (null == filter) 
			throw new IllegalArgumentException("IncidentGroupFilter cannot be null");
		if (null == filter.getWorkAreaId()) 
			throw new IllegalArgumentException("IncidentGroupFilter.workAreaId can't be null!");

		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);

		if(isPrivilegedUser == false) {
			crit.createAlias("this.incidentGroupUsers", "igus");
			crit.add(Restrictions.eq("igus.userId", filter.getCurrentUserId()));
		}

		crit.add(Restrictions.eq("this.workAreaId", filter.getWorkAreaId()));

		if(filter != null) {
			if (filter.getGroupName() != null && filter.getGroupName().trim().length() > 0) 
				crit.add(Expression.ilike("this.groupName", filter.getGroupName().trim().toUpperCase(), MatchMode.START));
			if (filter.getCreatedDate() != null) {
				crit.add(Expression.eq("auditable.createdDate", filter.getCreatedDate()));
			} else {
			  if(StringUtility.hasValue(filter.getCreatedDateString())) {
	        try {
	          super.applyCrypticDateFilter(crit, filter.getCreatedDateString(), "this_.created_date");
	        } catch (Exception e) {
	          throw new PersistenceException(e);
	        }
	      }
			}
			if(filter.getDeletable() != null) {
        if(filter.getDeletable() == true) {
          //TODO:  Currently, there is no criteria for a deletable IncidentGroup. -dbudge
        } else {
          //TODO:  Currently, there is no criteria for a non-deletable IncidentGroup. -dbudge
          //If the user selected "No" in the UI filter, return null since all Incident Groups are deletable.
          return null;
        }
      }
		}


		//"General Requirements - Grids.
		//When the grid initially displays, the system must sort data in the first column in ascending order."
		crit.addOrder(Order.asc("this.groupName"));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Collection<IncidentGroup> entities = crit.list();
		try {
			return IncidentGroupGridVo.getInstances(entities, true);
		}
		catch ( Exception e ) {
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAssignedIncidents(java.lang.Long, gov.nwcg.isuite.core.filter.IncidentFilter, java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentGridVo> getAssignedIncidents(Long groupId, IncidentFilter filter, Boolean privilegedUser) throws PersistenceException {
		if (null == filter) 
			throw new IllegalArgumentException("IncidentFilter cannot be null");
		if (null == groupId) 
			throw new IllegalArgumentException("GroupId can't be null!");

		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.createAlias("agency", "ag", Criteria.LEFT_JOIN);
		crit.createAlias("eventType", "et");
		crit.createAlias("this.incidentGroups", "igs");
		//"General Requirements - Grids.
		  //When the grid initially displays, the system must sort data in the first column in ascending order."
		crit.addOrder(Order.asc("this.nbr"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		String sql = "this_.id in (select incident_id from isw_incident_group_incident where incident_group_id = "+groupId+")";
		crit.add(Restrictions.sqlRestriction(sql));
		
		crit.add(Restrictions.eq("igs.id", groupId));
		
		if(null != filter){
			if(StringUtility.hasValue(filter.getIncidentNumber()))
				crit.add(Restrictions.ilike("this.nbr", filter.getIncidentNumber(), MatchMode.START));
			if(StringUtility.hasValue(filter.getIncidentName())) {
				crit.add(Restrictions.ilike("this.incidentName", filter.getIncidentName(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getEventType())) {
				crit.add(Restrictions.ilike("et.eventType", filter.getEventType(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				crit.add(Restrictions.ilike("ag.agencyCode", filter.getAgency(), MatchMode.START));
			}
			
			if(filter.getIncidentStartDate() != null) {
				crit.add(Expression.eq("this.incidentStartDate", filter.getIncidentStartDate()));
			} else if(StringUtility.hasValue(filter.getStartDateString())) {
				try {
					super.applyCrypticDateFilter(crit, filter.getStartDateString(), "this_.incident_start_date");
				} catch (Exception e) {
					throw new PersistenceException(e);
				}
			}
		}
		
		Collection<Incident> incidents = crit.list();
		return IncidentGridVo.getInstances(incidents, filter.getCurrentUserId(), privilegedUser);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAvailableIncidents(java.lang.Long, java.lang.Long, java.lang.Boolean)
	 */
	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long loggedInUserId, Boolean privilegedUser) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
		crit.createAlias("restrictedIncidentUsers", "riu");
		
		// 1)User must have access to the incident
		if(loggedInUserId != null) {
			crit.add(Restrictions.eq("riu.userId", loggedInUserId));
		}
		// 2) Incident must not be in another incident group
		String sqlFilter = "this_.id not in (select igi.incident_id from isw_incident_group_incident igi where igi.incident_group_id != " + incidentGroupId + ")";
		crit.add(Restrictions.sqlRestriction(sqlFilter));
		
		// 3) Incident must be restricted
		crit.add(Restrictions.eq("restricted", true));
				
		crit.add(Restrictions.eq("isSiteManaged", StringBooleanEnum.N));
		
		crit.addOrder(Order.asc("this.incidentName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		Collection<Incident> availableIncidents = crit.list();

		return IncidentGridVo.getInstances(availableIncidents, loggedInUserId, privilegedUser);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAvailableIncidents(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.filter.IncidentFilter, java.lang.Long, java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long workAreaId, IncidentFilter filter, Long loggedInUserId, Boolean privilegedUser) throws PersistenceException {

		if (!LongUtility.hasValue(incidentGroupId))
			throw new IllegalArgumentException("incidentGroupId cannot be null or 0 ");

		if (!LongUtility.hasValue(workAreaId))
			throw new IllegalArgumentException("workAreaId cannot be null or 0 ");

		Criteria crit = getHibernateSession().createCriteria(IncidentImpl.class);
	
		crit.createAlias("agency", "ag");
		crit.createAlias("eventType", "et");
		crit.createAlias("countrySubdivision", "cs", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("homeUnit", "unit");
		crit.createAlias("restrictedIncidentUsers", "riu");
		
		if(loggedInUserId != null) {
			crit.add(Restrictions.eq("riu.userId", loggedInUserId));
		}
		//TODO:  Change these SQL subqueries to DetachedCriteria. -dbudge
		String sqlFilter = "this_.id in (select wai.incident_id from isw_work_area_incident wai where work_area_id = "+workAreaId+")";
		crit.add(Restrictions.sqlRestriction(sqlFilter));
// do not filter out incidents, filtering will happen on client
//		sqlFilter = "this_.id not in (select incident_id from isw_incident_group_incident)";
//		crit.add(Restrictions.sqlRestriction(sqlFilter));
		crit.addOrder(Order.asc("this.incidentName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		if(null != filter) {
			if(StringUtility.hasValue(filter.getIncidentNumber()))
				crit.add(Restrictions.ilike("this.nbr", filter.getIncidentNumber(), MatchMode.START));
			if(StringUtility.hasValue(filter.getIncidentName())) {
				crit.add(Restrictions.ilike("this.incidentName", filter.getIncidentName(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getEventType())) {
				crit.add(Restrictions.ilike("et.eventType", filter.getEventType(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				crit.add(Restrictions.ilike("ag.agencyCode", filter.getAgency(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getCountrySubdivision())) {
				crit.add(Restrictions.ilike("cs.abbreviation", filter.getCountrySubdivision(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getHomeUnit())) {
				crit.add(Restrictions.ilike("unit.unitCode", filter.getHomeUnit(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getDefaultAccountingCode())) {
				DetachedCriteria detachedCrit = DetachedCriteria.forClass(IncidentAccountCodeImpl.class);
				detachedCrit.createAlias("accountCode", "ac");

				detachedCrit.add(Restrictions.eq("defaultFlag", true));
				detachedCrit.add(Restrictions.ilike("ac.accountCode", filter.getDefaultAccountingCode(), MatchMode.START));

				detachedCrit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				detachedCrit.setProjection(Property.forName("incidentId"));

				crit.add(Subqueries.propertyIn("id", detachedCrit));
			}

			if(filter.getIncidentStartDate() != null) {
				crit.add(Expression.eq("this.incidentStartDate", filter.getIncidentStartDate()));
			} else if(StringUtility.hasValue(filter.getStartDateString())) {
				try {
					super.applyCrypticDateFilter(crit, filter.getStartDateString(), "this_.incident_start_date");
				} catch (Exception e) {
					throw new PersistenceException(e);
				}
			}
		}
		
		Collection<Incident> availableIncidents = crit.list();
		return IncidentGridVo.getInstances(availableIncidents, loggedInUserId, privilegedUser);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getByGroupName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IncidentGroup getByGroupName(String groupName, Long workAreaId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);

		crit.add(Restrictions.eq("groupName", groupName));
		//crit.add(Restrictions.eq("workAreaId", workAreaId));

		List<IncidentGroup> results = crit.list();

		if ( results == null || results.size() == 0 ) {
			return null;
		}

		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAssignedUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UserGridVo> getAssignedUsers(Long groupId, UserFilter filter) throws PersistenceException {
		if (null == filter) 
			throw new IllegalArgumentException("UserFilter cannot be null");
		if (null == groupId) 
			throw new IllegalArgumentException("IncidentGroupId can't be null!");

		String sql = IncidentGroupQuery.getIncidentGroupUsersQuery(groupId,filter);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(UserGridVo.class);

		crt.addProjection("id", "id");
		crt.addProjection("firstName","firstName");
		crt.addProjection("lastName","lastName");
		crt.addProjection("loginName","loginName");
		crt.addProjection("unitCode","homeUnitCode");

		crt.addScalar("id", Long.class.getName());

		query.setResultTransformer(crt);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<UserGridVo> getAvailableUsers() throws PersistenceException {
		String sql = IncidentGroupQuery.getAvailableUsersQuery3();

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.setParameter("ispriv", super.getBooleanComparison(true));

		CustomResultTransformer crt = new CustomResultTransformer(UserGridVo.class);

		crt.addProjection("id", "id");
		crt.addProjection("firstName","firstName");
		crt.addProjection("lastName","lastName");
		crt.addProjection("loginName","loginName");
		crt.addProjection("unitCode","homeUnitCode");

		crt.addScalar("id", Long.class.getName());

		query.setResultTransformer(crt);

		return query.list();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAvailableUsers(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UserGridVo> getAvailableUsers(Long workAreaId, Long incidentGroupId, UserFilter filter) throws PersistenceException {
		if (null == filter) 
			throw new IllegalArgumentException("UserFilter cannot be null");
		if (null == incidentGroupId) 
			throw new IllegalArgumentException("IncidentGroupId can't be null!");
		if (null == workAreaId) 
			throw new IllegalArgumentException("WorkAreaId can't be null!");

		String sql = IncidentGroupQuery.getAvailableUsersQuery2(incidentGroupId, filter);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.setParameter("ispriv", super.getBooleanComparison(true));

		CustomResultTransformer crt = new CustomResultTransformer(UserGridVo.class);

		crt.addProjection("id", "id");
		crt.addProjection("firstName","firstName");
		crt.addProjection("lastName","lastName");
		crt.addProjection("loginName","loginName");
		crt.addProjection("unitCode","homeUnitCode");

		crt.addScalar("id", Long.class.getName());

		query.setResultTransformer(crt);

		return query.list();
	}

	public Long getIncidentGroupUserId(Long userId, Long groupId) throws PersistenceException {
		if(null == userId) 
			throw new IllegalArgumentException("userId cannot be null");
		if(null == groupId)
			throw new IllegalArgumentException("groupId cannot be null");

		String sql = IncidentGroupQuery.getIncidentGroupUserIdSQLQuery(userId, groupId);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		sqlQuery.setLong("userid", userId);
		sqlQuery.setLong("groupid", groupId);
		if(super.isOracleDialect() == false) {
			if(sqlQuery.uniqueResult() == null) {
				return null;
			}
			return ((BigInteger)sqlQuery.uniqueResult()).longValue();
		}
		try{
			return TypeConverter.convertToLong(sqlQuery.uniqueResult());
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getIncidentGroupIncidentsByIncidentId(java.lang.Long)
	 */
	public Long getIncidentGroupsIncidentId(Long incidentId) throws PersistenceException {
		String sql = IncidentGroupQuery.getIncidentGroupsIncidentIdSQLQuery(incidentId);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		Object incId = (Object)sqlQuery.uniqueResult();
		if(incId == null) {
			return null;
		}

		if(super.isOracleDialect()) {
			return ((BigDecimal)incId).longValue();//Oracle returns a BigDecimal.
		} else {
			return ((BigInteger)incId).longValue();//PostgreSQL returns a BigInteger.
		}
	}

	public Collection<Long> getGroupIncidentIds(String groupTi) throws PersistenceException {
		String sql = IncidentGroupQuery.getGroupsIncidentIdsSQLQuery(groupTi);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		Collection<Object> results = sqlQuery.list();
		Collection<Long> list = new ArrayList<Long>();
		
		if(null != results){
			try{
				for(Object o : results){
					Long id=TypeConverter.convertToLong(o);
					list.add(id);
				}
			}catch(Exception e){
				
			}
		}
		
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAvailableUserGroups()
	 */
	public Collection<UserGroupGridVo> getAvailableUserGroups() throws PersistenceException {
		String sql = IncidentGroupQuery.getAvailableUserGroupsSQLQuery(null);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(UserGroupGridVo.class);
		crt.addProjection("groupname", "groupName");
		crt.addProjection("groupid", "id");
		crt.addScalar("groupid", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
		return sqlQuery.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getAvailableUserGroups(gov.nwcg.isuite.core.filter.UserGroupFilter)
	 */
//	public Collection<UserGroupGridVo> getAvailableUserGroups(UserGroupFilter filter) throws PersistenceException {
//		String sql = IncidentGroupQuery.getAvailableUserGroupsSQLQuery(filter);
//		
//		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
//		
//		CustomResultTransformer crt = new CustomResultTransformer(UserGroupGridVo.class);
//		crt.addProjection("groupname", "groupName");
//		crt.addProjection("groupid", "id");
//		crt.addScalar("groupid", Long.class.getName());
//		sqlQuery.setResultTransformer(crt);
//		return sqlQuery.list();
//	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getWorkAreaIncidentGroupSelectorData(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentSelectorVo> getWorkAreaIncidentGroupSelectorData(Long workAreaId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "incidentGroupId")
				.add(Projections.property("groupName"), "incidentGroupName")
		);
		crit.add(Restrictions.eq("workAreaId", workAreaId));
		crit.setResultTransformer(Transformers.aliasToBean(IncidentSelectorVo.class));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getByWorkAreaId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IncidentGroup> getByWorkAreaId(Long workAreaId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);
		crit.add(Restrictions.eq("workAreaId", workAreaId));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#removeIncidentFromGroup(java.lang.Long)
	 */
	@Override
	public void removeIncidentFromGroup(Long incidentId) throws PersistenceException {
		String sql = IncidentGroupQuery.removeIncidentFromGroupSQLQuery(incidentId);
		SQLQuery sq = getHibernateSession().createSQLQuery(sql);
		sq.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getIncidentIdsInGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Long> getIncidentIdsInGroup(Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);
		crit.createAlias("incidents", "incs");
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("incs.id"))
				);
		crit.add(Restrictions.eq("id", incidentGroupId));
		return crit.list();
	}

	public void deleteFromUserCustomView(Long incidentGroupId) throws PersistenceException {
		String sql = "DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE incident_group_id = " + incidentGroupId + " ";
        SQLQuery q = getHibernateSession().createSQLQuery(sql);
        q.executeUpdate();
		
	}

	public void executeInsertIncidentGroupNewUser(Long userId) throws PersistenceException,Exception{
		int cnt=0;
		
		String sql = "INSERT INTO ISW_INCIDENT_GROUP_USER (ID,INCIDENT_GROUP_ID, USER_ID) " +
						" VALUES (nextVal('SEQ_INCIDENT_GROUP_USER'),1,"+userId+" ) ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	public void executeInsertIncidentGroupNewIncident(Long incidentId) throws PersistenceException,Exception{
		int cnt=0;
		
		String sql = "INSERT INTO ISW_INCIDENT_GROUP_INCIDENT (INCIDENT_GROUP_ID, INCIDENT_ID) " +
						" VALUES (1,"+incidentId+" ) ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	public int getCrossIncidentTimePostings(Long incidentId) throws PersistenceException {
		int cnt=0;
		
		// get cross incident time postings count
		String sql = IncidentGroupQuery.getCrossIncidentTimePostingsQuery2(incidentId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt = query.uniqueResult();
		
		try{
			cnt = TypeConverter.convertToInt(rslt);
		}catch(Exception e){
			
		}
		
		// get cross incident time adjustments count
		String sql2 = IncidentGroupQuery.getCrossIncidentTimeAdjustmentsQuery(incidentId);
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);

		Object rslt2 = query.uniqueResult();
		
		try{
			int cnt1 = TypeConverter.convertToInt(rslt2);
			cnt=cnt+cnt1;
		}catch(Exception e){
			
		}
		
		return cnt;
	}
	
	public int getCrossIncidentCostRecords(Long incidentId) throws PersistenceException {
		int cnt=0;
		
		// get cross incident time postings count
		String sql = IncidentGroupQuery.getCrossIncidentCostRecordsQuery(incidentId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt = query.uniqueResult();
		
		try{
			cnt = TypeConverter.convertToInt(rslt);
		}catch(Exception e){
			
		}
		
		return cnt;
	}
	
	
	public int getCrossIncidentTimePostingsInverse(Long igId, Long incidentId) throws PersistenceException {
		int cnt=0;
		
		// get cross incident time postings inverse count
		String sql = IncidentGroupQuery.getCrossIncidentTimePostingsInverseQuery(igId,incidentId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt = query.uniqueResult();
		
		try{
			cnt = TypeConverter.convertToInt(rslt);
		}catch(Exception e){
			
		}
		
		// get cross incident time adjustments inverse count
		String sql2 = IncidentGroupQuery.getCrossIncidentTimeAdjustmentsInverseQuery(incidentId);
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);

		Object rslt2 = query.uniqueResult();
		
		try{
			int cnt1 = TypeConverter.convertToInt(rslt2);
			cnt=cnt+cnt1;
		}catch(Exception e){
			
		}
		
		return cnt;
	}
	
	public int getCrossIncidentCostRecordsInverse(Long incidentId) throws PersistenceException {
		int cnt=0;
		
		// get cross incident time postings inverse count
		String sql = IncidentGroupQuery.getCrossIncidentCostRecordsInverseQuery(incidentId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt = query.uniqueResult();
		
		try{
			cnt = TypeConverter.convertToInt(rslt);
		}catch(Exception e){
			
		}
		
		return cnt;
	}

	public Long getIncidentGroupIdByIncidentId(Long incidentId) throws PersistenceException {
		String sql = "SELECT INCIDENT_GROUP_ID " +
					 "FROM ISW_INCIDENT_GROUP_INCIDENT WHERE " +
					 "INCIDENT_ID = " + incidentId + " ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
		try{
			Long igId = TypeConverter.convertToLong(rslt);
			return igId;
		}catch(Exception e){}
		}
		
		return null;
	}
	
	public Date getEarliestIncidentStartDate(Long groupId) throws PersistenceException {
		String sql = "SELECT MIN(INCIDENT_START_DATE) " +
					 "FROM ISW_INCIDENT WHERE " +
					 "ID IN (SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + groupId + " )";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				Date dt = TypeConverter.convertToDate(rslt);
				return dt;
			}catch(Exception e){}
		}
		
		return null;
	}

	public String getSiteGroupTransferableIdentity(Long groupId) throws PersistenceException {
		String sql = "SELECT TRANSFERABLE_IDENTITY " +
					 "FROM ISW_INCIDENT_GROUP WHERE " +
					 "ID = " + groupId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				String ti=TypeConverter.convertToString(rslt);
				return ti;
			}catch(Exception e){}
		}
		
		return "";
	}
	
	public void addIncidentUsers(Long incidentGroupId) throws PersistenceException {
		String sql = "SELECT USER_ID FROM " +
					 "ISW_RESTRICTED_INCIDENT_USER " +
					 "WHERE INCIDENT_ID IN (SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " ) " + 
					 "AND USER_ID NOT IN ( SELECT USER_ID FROM ISW_INCIDENT_GROUP_USER WHERE INCIDENT_GROUP_ID = " + incidentGroupId + ") ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Collection<Object> rslt = (Collection<Object>)query.list();
		
		if(CollectionUtility.hasValue(rslt)){
			for(Object id : rslt){
				try{
					Long userId = TypeConverter.convertToLong(id);
					sql = "INSERT INTO ISW_INCIDENT_GROUP_USER (ID,INCIDENT_GROUP_ID, USER_ID) " +
						  " VALUES (SEQ_INCIDENT_GROUP_USER.nextVal," + incidentGroupId + ","+userId+" ) ";

					query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}catch(Exception e){
					//System.out.println(e.getMessage());
				}
			}
		}
		
	}
	
	public void checkCreateSiteGroupIap() throws PersistenceException {
        Criteria crit = getHibernateSession().createCriteria(IncidentGroupImpl.class);
        Collection<IncidentGroup> igList = crit.list();

        Collection<Long> igIds = new ArrayList<Long>();
        
        for(IncidentGroup ig : igList){
        	igIds.add(ig.getId());
        }
        
        for(Long id : igIds){
        	this.createDefaultIapSettings(id);
        }
	}
	
	public void createDefaultIapSettings(Long incidentGroupId) throws PersistenceException {

		String sql1 = "SELECT COUNT(*) FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql1);
		Object rslt=q.uniqueResult();
		int cnt=0;
		if(null != rslt){
			try{
				cnt=TypeConverter.convertToInt(rslt);
			}catch(Exception e){}
		}
		
		if(cnt==0){
			String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
			
			for(IapDefaultSettingsQuery._203Settings _203Enum : IapDefaultSettingsQuery._203Settings.values()){
				String sql = _203Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_GROUP_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentGroupId));
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.executeUpdate();
				
			}

			for(IapDefaultSettingsQuery._204Settings _204Enum : IapDefaultSettingsQuery._204Settings.values()){
				String sql = _204Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_GROUP_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentGroupId));
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.executeUpdate();
			}
			
			
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#createDefaultGroupSettings(java.lang.Long, java.lang.Long)
	 */
	public void createDefaultGroupSettings(Long incidentGroupId, Long primaryIncidentId) throws PersistenceException {
		// delete existing group questions
		String sql = IncidentGroupQuery.getDeleteSettingsQuery1(incidentGroupId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();

		// delete existing group qs kinds
		sql = IncidentGroupQuery.getDeleteSettingsQuery2(incidentGroupId);
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		// delete existing group prefs
		sql = IncidentGroupQuery.getDeleteSettingsQuery3(incidentGroupId);
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		// delete existing group prefs other fields
		sql = IncidentGroupQuery.getDeleteSettingsQuery4(incidentGroupId);
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();

		// create group prefs based on primary incident
		sql = "SELECT COUNT(*) FROM ISW_INCIDENT_PREFS WHERE INCIDENT_ID = " + primaryIncidentId + " ";
		query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = IncidentGroupQuery.getCopyIncidentPrefsQuery(incidentGroupId, primaryIncidentId);
					query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			// smother
		}

		// create group prefs based on primary incident
		sql = "SELECT COUNT(*) FROM ISW_INCIDENT_PREFS_OTHERFIELDS WHERE INCIDENT_ID = " + primaryIncidentId + " ";
		query = getHibernateSession().createSQLQuery(sql);
		rslt=query.uniqueResult();
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = IncidentGroupQuery.getCopyIncidentPrefsOtherFieldsQuery(incidentGroupId, primaryIncidentId);
					query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			// smother
		}
		
		// create group qs kinds based on primary incident
		sql = "SELECT COUNT(*) FROM ISW_INCIDENT_QS_KIND WHERE INCIDENT_ID = " + primaryIncidentId + " ";
		query = getHibernateSession().createSQLQuery(sql);
		rslt=query.uniqueResult();
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = IncidentGroupQuery.getCopyIncidentQSKindsQuery(incidentGroupId, primaryIncidentId);
					query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			// smother
		}
	}
	
	public void restoreDefault203TemplateSettings(Long incidentGroupId, String sectionCode) throws PersistenceException {
		// Delete existing settings from ISW_IAP_POSITION_ITEM_CODE
		String sql1 = "DELETE FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_GROUP_ID = " 
			+ incidentGroupId 
			+ " AND FORM = 203"
			+ " AND SECTION = "
			+ sectionCode;
		
		//TODO: Debug:
		System.out.println(sql1);
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		query1.executeUpdate();
		
		// Insert default settings for this section
		String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
		
		for(IapDefaultSettingsQuery._203Settings _203Enum : IapDefaultSettingsQuery._203Settings.values()){
			if(_203Enum.section.equals(sectionCode)){
				String sql = _203Enum.sql;
				sql=sql.replaceAll(":fieldName", "INCIDENT_GROUP_ID");
				sql=sql.replaceAll(":seqId", sequence);
				sql=sql.replaceAll(":incidentId", String.valueOf(incidentGroupId));
				
				//TODO: Debug:
				System.out.println(sql);
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}
	}
	
	public void restoreDefault204TemplateSettings(Long incidentGroupId) throws PersistenceException {
		// Delete existing settings from ISW_IAP_POSITION_ITEM_CODE
		String sql1 = "DELETE FROM ISW_IAP_POSITION_ITEM_CODE WHERE INCIDENT_GROUP_ID = " 
			+ incidentGroupId 
			+ " AND FORM = 204";
		
		//TODO: Debug:
		System.out.println(sql1);
		
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
		query1.executeUpdate();
		
		// Insert default settings
		String sequence = (super.isOracleDialect()==true ? "SEQ_IAP_POSITION_ITEM_CODE.nextVal" : "nextVal('SEQ_IAP_POSITION_ITEM_CODE')");
		
		for(IapDefaultSettingsQuery._204Settings _204Enum : IapDefaultSettingsQuery._204Settings.values()){
			String sql = _204Enum.sql;
			sql=sql.replaceAll(":fieldName", "INCIDENT_GROUP_ID");
			sql=sql.replaceAll(":seqId", sequence);
			sql=sql.replaceAll(":incidentId", String.valueOf(incidentGroupId));
			
			//TODO: Debug:
			System.out.println(sql);
			
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.executeUpdate();
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#getGroupsForDataTransfer()
	 */
	public Collection<IncidentGroupGridVo> getGroupsForDataTransfer() throws PersistenceException {
		Collection<IncidentGroupGridVo> vos = new ArrayList<IncidentGroupGridVo>();
		
		String sql = "SELECT id as groupId" +
					" , group_name as groupName"+
					" , transferable_identity as transferableIdentity "+
					"FROM isw_incident_group";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(IncidentGroupGridVo.class);
 	    crt.addProjection("groupId", "id");
 	    crt.addProjection("groupName", "groupName");
 	    crt.addProjection("transferableIdentity", "transferableIdentity");
		
        crt.addScalar("groupId", Long.class.getName());

		query.setResultTransformer(crt); 
		
		try{
			vos=query.list();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return vos;
	}
	
	public void updateSiteManaged(Long id, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident_group set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where id = " + id;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public void updateSiteManaged(String groupTi, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident_group set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where transferable_identity = '" + groupTi + "' ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public void updateGroupIncidentsSiteManaged(Long id, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where id in (select incident_id from isw_incident_group_incident where incident_group_id = " + id + ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public void updateGroupIncidentsSiteManaged(String groupTi, Boolean siteManaged) throws PersistenceException {
		String sql = "UPDATE isw_incident set is_site_managed = '" +
						(siteManaged==true ? "Y" : "N" ) +
					 "' where id in (select incident_id from isw_incident_group_incident where incident_group_id = (select id from isw_incident_group where transferable_identity = '"+groupTi+"') ) ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public void updateGroupName(String groupTi, String name) throws PersistenceException {
		String sql = "UPDATE isw_incident_group set group_name = '" + name +"' "+
					 " where transferable_identity = '" + groupTi + "' ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentGroupDao#propagatePrefs(java.lang.Long)
	 */
	public void propagatePrefs(Long incidentGroupId) throws PersistenceException,Exception {
	
		// update all incident prefs (except for the 2 OtherLabels != Security Unit)
		for(IncidentGroupPrefsQuery.CheckOutPrefsEnum cop : IncidentGroupPrefsQuery.CheckOutPrefsEnum.values()){
			String sql=IncidentGroupPrefsQuery.getUpdateIncidentQuery(incidentGroupId, cop);
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.executeUpdate();
		}
		
		// update the 2 other labels
		Collection<Long> incidentIds = this.getIncidentIdsInGroup(incidentGroupId);
		for(Long incidentId : incidentIds){
			String sql2="select min(id) from isw_incident_prefs " +
					   "where incident_id = " + incidentId + " " +
					   "and section_name = 'OTHER_LABEL' " +
					   "and (field_label is null or field_label != 'Security Unit') " ;
			SQLQuery query2=getHibernateSession().createSQLQuery(sql2);
			Long minIncOtherLabelId=TypeConverter.convertToLong(query2.uniqueResult());
			
			String sql3="select max(id) from isw_incident_prefs " +
						   "where incident_id = " + incidentId + " " +
						   "and section_name = 'OTHER_LABEL' " +
						   "and (field_label is null or field_label != 'Security Unit') " ;
			SQLQuery query3=getHibernateSession().createSQLQuery(sql3);
			Long maxIncOtherLabelId=TypeConverter.convertToLong(query3.uniqueResult());

			String sql4="select min(id) from isw_incident_group_prefs " +
			   			"where incident_group_id = " + incidentGroupId + " " +
			   			"and section_name = 'OTHER_LABEL' " +
			   			"and (field_label is null or field_label != 'Security Unit') " ;
			SQLQuery query4=getHibernateSession().createSQLQuery(sql4);
			Long minIncGrpOtherLabelId=TypeConverter.convertToLong(query4.uniqueResult());
	
			String sql5="select max(id) from isw_incident_group_prefs " +
				   		"where incident_group_id = " + incidentGroupId + " " +
				   		"and section_name = 'OTHER_LABEL' " +
				   		"and (field_label is null or field_label != 'Security Unit') " ;
			SQLQuery query5=getHibernateSession().createSQLQuery(sql5);
			Long maxIncGrpOtherLabelId=TypeConverter.convertToLong(query5.uniqueResult());

			String sql6=IncidentGroupPrefsQuery.getUpdateOtherFieldQuery(minIncOtherLabelId, minIncGrpOtherLabelId);
			SQLQuery query6=getHibernateSession().createSQLQuery(sql6);
			query6.executeUpdate();

			String sql7=IncidentGroupPrefsQuery.getUpdateOtherFieldQuery(maxIncOtherLabelId, maxIncGrpOtherLabelId);
			SQLQuery query7=getHibernateSession().createSQLQuery(sql7);
			query7.executeUpdate();

		}
	}

	public void propagatePrefsFromIncident(Long incidentId,Long incidentGroupId) throws PersistenceException,Exception {
		// update incident group prefs with incident prefs
		for(IncidentGroupPrefsQuery.CheckOutPrefsEnum cop : IncidentGroupPrefsQuery.CheckOutPrefsEnum.values()){
			String sql=IncidentGroupPrefsQuery.getUpdateIncidentGroupQuery(incidentId,incidentGroupId, cop);
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.executeUpdate();
		}
		
		String sql2="select min(id) from isw_incident_prefs " +
				   "where incident_id = " + incidentId + " " +
				   "and section_name = 'OTHER_LABEL' " +
				   "and (field_label is null or field_label != 'Security Unit') " ;
		SQLQuery query2=getHibernateSession().createSQLQuery(sql2);
		Long minIncOtherLabelId=TypeConverter.convertToLong(query2.uniqueResult());
		
		String sql3="select max(id) from isw_incident_prefs " +
					   "where incident_id = " + incidentId + " " +
					   "and section_name = 'OTHER_LABEL' " +
					   "and (field_label is null or field_label != 'Security Unit') " ;
		SQLQuery query3=getHibernateSession().createSQLQuery(sql3);
		Long maxIncOtherLabelId=TypeConverter.convertToLong(query3.uniqueResult());
		
		String sql4="select min(id) from isw_incident_group_prefs " +
					"where incident_group_id = " + incidentGroupId + " " +
					"and section_name = 'OTHER_LABEL' " +
					"and (field_label is null or field_label != 'Security Unit') " ;
		SQLQuery query4=getHibernateSession().createSQLQuery(sql4);
		Long minIncGrpOtherLabelId=TypeConverter.convertToLong(query4.uniqueResult());
		
		String sql5="select max(id) from isw_incident_group_prefs " +
			   		"where incident_group_id = " + incidentGroupId + " " +
			   		"and section_name = 'OTHER_LABEL' " +
			   		"and (field_label is null or field_label != 'Security Unit') " ;
		SQLQuery query5=getHibernateSession().createSQLQuery(sql5);
		Long maxIncGrpOtherLabelId=TypeConverter.convertToLong(query5.uniqueResult());
		
		String sql6=IncidentGroupPrefsQuery.getUpdateOtherFieldGroupQuery(minIncOtherLabelId, minIncGrpOtherLabelId);
		SQLQuery query6=getHibernateSession().createSQLQuery(sql6);
		query6.executeUpdate();
		
		String sql7=IncidentGroupPrefsQuery.getUpdateOtherFieldGroupQuery(maxIncOtherLabelId, maxIncGrpOtherLabelId);
		SQLQuery query7=getHibernateSession().createSQLQuery(sql7);
		query7.executeUpdate();

	}

	public void removeQuestionFromGroup(Long groupId, Collection<String> questions) throws PersistenceException {
		for(String q : questions){
			String sql = "DELETE FROM " +
						 "ISW_INCIDENT_QUESTION " +
						 "WHERE question_id in ( select id from isw_question where question = '"+q+"' ) " +
						 "and incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + groupId + ") ";
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
			
			String sql2 = "DELETE FROM " +
			 			  "ISW_INCIDENT_GROUP_QUESTION " +
						  "WHERE question_id in ( select id from isw_question where question = '"+q+"' ) " +
			 			  "and incident_group_id = " + groupId + " " ;
			SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
			query2.executeUpdate();
		}
	}

	public void addQuestionsToGroup(Long groupId, Collection<String> questions) throws PersistenceException {
		for(String q : questions){
			String sql1="INSERT INTO ISW_QUESTION " +
						" (ID, QUESTION, QUESTION_TYPE, IS_STANDARD) " +
						"VALUES " +
						"(" + (super.isOracleDialect() ? "SEQ_QUESTION.nextVal" : "nextVal('SEQ_QUESTION') ") + " " +
						","+q+" " +
						",'AIRTRAVEL'"+
						","+(super.isOracleDialect() ? 0 : false)+" " +
						") ";
			
			String sql = "INSERT INTO ISW_INCIDENT_QUESTION " +
						 " (ID, QUESTION_ID, IS_VISIBLE, POSITION, INCIDENT_GROUP_ID) " +
						 "VALUES " +
						 "WHERE question_id in ( select id from isw_question where question = '"+q+"' ) " +
						 "and incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + groupId + ") ";
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
			
			String sql2 = "DELETE FROM " +
			 			  "ISW_INCIDENT_GROUP_QUESTION " +
						  "WHERE question_id in ( select id from isw_question where question = '"+q+"' ) " +
			 			  "and incident_group_id = " + groupId + " " ;
			SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
			query2.executeUpdate();
		}
	}

	public void updateQuestionsToGroup(Long groupId, Collection<String> questions) throws PersistenceException {
		
	}

	public Long getGroupQuestionId(String question, Long incidentGroupId) throws PersistenceException {
		Long id=0L;
		
		String sql="select id "+
				   "from isw_incident_group_question "+
				   "where incident_group_id = " + incidentGroupId + " " +
				   "and question_id in ("+
				   " select id from isw_question where upper(question)='"+question.toUpperCase()+"' " +
				   ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				id=TypeConverter.convertToLong(rslt);
			}catch(Exception ee){}
		}
		
		return id;
	}

	public Boolean isSyncedOnce(Long id) throws PersistenceException {
		String sql="";
		
		sql="select is_site_synced_once from isw_incident_group where id = " + id + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		
		try{
			String val = TypeConverter.convertToString(rslt);
			
			if(StringUtility.hasValue(val) && val.equalsIgnoreCase("Y"))
				return true;
		}catch(Exception e){}
		
		return false;
	}

	public void updateIsSyncedOnce(Long id) throws PersistenceException {
		String sql="update isw_incident_group set is_site_synced_once = 'Y' where id = " + id + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
	}

	public Long getIncidentGroupIdByTi(String ti) throws PersistenceException {
		Long incGrpId=0L;
		
		String sql = "select id from isw_incident_group where transferable_identity='"+ti+"' ";
		SQLQuery query=getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				incGrpId=TypeConverter.convertToLong(rslt);
			}catch(Exception e){}
		}
		
		return incGrpId;
	}

	public void cleanUpDuplicateQuestionIssue(Long incidentGroupId) throws PersistenceException {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("")
			   .append("select count(qv.id) ")
			   .append("from isw_work_period_question_value  qv ")
			   .append("where qv.work_period_id in ( ")
				.append("	select id ")
				.append("	from isw_work_period ")
				.append("	where incident_resource_id in (")
				.append("       select id ")
				.append("       from isw_incident_resource ")
				.append("       where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ")
				.append("   ) ")
				.append(") ")
				.append("and qv.id not in (")
				.append("  select min(qv2.id) " )
				.append("  from isw_work_period_question_value qv2 ")
				.append("  where qv2.work_period_id = qv.work_period_id ")
				.append("  group by qv2.work_period_id, qv2.incident_question_id ")
				.append(") ");
			
			SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
			Object rslt = q.uniqueResult();
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				if(cnt > 0){
					sql = new StringBuffer();
					sql.append("")
					   .append("delete  ")
					   .append("from isw_work_period_question_value  qv ")
					   .append("where qv.work_period_id in ( ")
						.append("	select id ")
						.append("	from isw_work_period ")
						.append("	where incident_resource_id in (")
						.append("       select id ")
						.append("       from isw_incident_resource ")
						.append("       where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ")
						.append("   ) ")
						.append(") ")
						.append("and qv.id not in (")
						.append("  select min(qv2.id) " )
						.append("  from isw_work_period_question_value qv2 ")
						.append("  where qv2.work_period_id = qv.work_period_id ")
						.append("  group by qv2.work_period_id, qv2.incident_question_id ")
						.append(") ");
					q = getHibernateSession().createSQLQuery(sql.toString());
					q.executeUpdate();
				}
			}
		}catch(Exception e){
			throw new PersistenceException("cannot cleanup group questions");
		}
	}	
	
	public String getGroupNameByTi(String ti) throws PersistenceException {
		String sql="select group_name from isw_incident_group where transferable_identity='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				String name=TypeConverter.convertToString(rslt);
				return name;
			}catch(Exception e){
			}
		}
		return "";
	}

	public int getGroupNameCountByTi(String name, String ti) throws PersistenceException {
		String sql = "select count(id) from isw_incident_group where group_name='"+name.toUpperCase()+"' and transferable_identity!='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception e){}
		}
		return 0;
	}
	
	public void saveGroupName(String name, String ti) throws PersistenceException {
		String sql = "update isw_incident_group set group_name='"+name.toUpperCase()+"' where transferable_identity='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	public void preDeleteIncidentGroup(Long incidentGroupId) throws PersistenceException {
		String sql="delete from isw_resource_training " +
			"where incident_resource_id in ("+
			"   select id from isw_incident_resource "+
			"   where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") " +
			")";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql="delete from iswl_priority_program where incident_group_id = " + incidentGroupId;
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();

		sql="delete from isw_training_set_fuel_type where training_settings_id in (select id from isw_training_settings where incident_group_id = " + incidentGroupId + ")";
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		sql="delete from isw_training_settings where incident_group_id = " + incidentGroupId;
		query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
}
