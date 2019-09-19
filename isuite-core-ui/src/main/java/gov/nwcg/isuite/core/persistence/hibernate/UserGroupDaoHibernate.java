package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserGroupQuery;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * This class allows access to the UserGroup tables in the database
 * 
 * @author bsteiner
 *
 */
public class UserGroupDaoHibernate extends TransactionSupportImpl implements UserGroupDao {

	private final CrudDao<UserGroup> crudDao;

	/**
	 * Full Constructor.
	 * @param crudDao {@link CrudDao} cannot be null
	 */
	public UserGroupDaoHibernate(final CrudDao<UserGroup> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupDao#getGrid(gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UserGroupGridVo> getGrid(UserGroupFilter filter) throws PersistenceException {
		if (filter == null) {
			throw new PersistenceException("filter cannot be null.");
		}

		if (filter.getCurrentUserId() == null) {
			throw new PersistenceException("currentUserId cannot be null.");
		}

		Criteria crit = getHibernateSession().createCriteria(UserGroupImpl.class);
		/* 
		 * The following line of code has been commented out due to the need to allow
		 * system users to share Work Areas with User Groups.  Non-privileged users cannot 
		 * create user groups and will never be a group owner. Therefore, pre-filtering
		 * based on the group owner will prevent users from ever being able to access this feature.
		 * See defect #744
		 */
		//crit.add(Expression.eq("this.groupOwnerId", filter.getCurrentUserId()));
		crit.setProjection( Projections.projectionList()
				.add( Projections.property("id"), "id")
				.add( Projections.property("groupName"), "groupName")
				.add( Projections.property("groupOwnerId"), "groupOwnerId")
				.add( Projections.property("auditable.createdDate"), "createdDate")
		);
		crit.setResultTransformer(Transformers.aliasToBean(UserGroupGridVo.class));

		if (filter.getGroupName() != null && filter.getGroupName().trim().length() > 0) {
			crit.add(Expression.ilike("this.groupName", filter.getGroupName().trim().toUpperCase(), MatchMode.START));
		}
		if (filter.getCreatedDate() != null) {
			crit.add(Expression.ge("auditable.createdDate", filter.getCreatedDate()));
		}
		
		/*
		if (filter.getDeletable() != null) {
		   if(filter.getDeletable() == true) {
		      crit.add(Expression.eq("this.groupOwnerId", filter.getCurrentUserId()));
		   } else {
		      crit.add(Expression.ne("this.groupOwnerId", filter.getCurrentUserId()));
		   }
		}
		*/
		
		if(LongUtility.hasValue(filter.getCurrentUserId())){
		      crit.add(Expression.eq("this.groupOwnerId", filter.getCurrentUserId()));
		}
		
		if(StringUtility.hasValue(filter.getCrypticDateFilterCode())) {
			try {
				super.applyCrypticDateFilter(crit, filter.getCrypticDateFilterCode(), "this_.created_date");
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}
		
		crit.setMaxResults(getMaxResultSize());
		crit.addOrder(Order.asc("groupName"));
		return crit.list();
	}

	public Collection<UserGridVo> getAvailableUsers(Long userGroupId, UserFilter filter) throws PersistenceException {

		if (userGroupId == null) 
			throw new IllegalArgumentException("userGroupId cannot be null.");

		if (filter == null) 
			throw new IllegalArgumentException("filter cannot be null.");

		StringBuffer b = new StringBuffer();
		//b.append(UserGroupQuery.GET_AVAILABLE_USERS_SQL_QUERY);
		b.append(UserGroupQuery.getAvailableUsersSqlQueryString(super.isOracleDialect()));
		
		b.append("AND U.ENABLED = " + super.getBooleanComparison(true) + " ")
		 // do not filter out currently assigned users
		 //.append("AND U.ID NOT IN (SELECT USER_ID FROM ISW_USER_GROUP_USER WHERE USER_GROUP_ID = " + userGroupId + ") ")
		 .append("AND U.ID != " + filter.getCurrentUserId() + " ");
		
		if (filter.getLoginName() != null && filter.getLoginName().trim().length() > 0) {
			b.append("AND UPPER(U.LOGIN_NAME) LIKE UPPER('")
			.append(createSafeString(filter.getLoginName().trim())).append("%') ");
		}
		if (filter.getFirstName() != null && filter.getFirstName().trim().length() > 0) {
			b.append("AND UPPER(U.FIRST_NAME) LIKE UPPER('")
			.append(createSafeString(filter.getFirstName().trim())).append("%') ");
		}
		if (filter.getLastName() != null && filter.getLastName().trim().length() > 0) {
			b.append("AND UPPER(U.LAST_NAME) LIKE UPPER('")
			.append(createSafeString(filter.getLastName().trim())).append("%') ");
		}
		if (filter.getHomeUnitCode() != null && filter.getHomeUnitCode().trim().length() > 0) {
			b.append("AND UPPER(HUORG.UNIT_CODE) LIKE UPPER('")
			.append(createSafeString(filter.getHomeUnitCode().trim())).append("%') ");
		}
		if (filter.getPdcUnitCode() != null && filter.getPdcUnitCode().trim().length() > 0) {
			b.append("AND UPPER(DCORG.UNIT_CODE) LIKE UPPER('")
			.append(createSafeString(filter.getPdcUnitCode().trim())).append("%') ");
		}
		
		b.append(" ORDER BY U.LOGIN_NAME ASC ");

		SQLQuery query = getHibernateSession().createSQLQuery(b.toString());

		CustomResultTransformer crt = new CustomResultTransformer(UserGridVo.class);
		crt.addProjection("id", "id");
		crt.addProjection("loginName", "loginName");
		crt.addProjection("firstName", "firstName");
		crt.addProjection("lastName", "lastName");
		crt.addProjection("homeUnitCode", "homeUnitCode");
		crt.addProjection("pdcUnitCode", "pdcUnitCode");

		crt.addScalar("id", Long.class.getName());

		query.setResultTransformer(crt); 
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupDao#getPicklist(gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UserGroupPicklistVo> getPicklist(UserGroupFilter filter) throws PersistenceException {
		if (filter == null) {
			throw new PersistenceException("filter cannot be null.");
		}

		if (filter.getCurrentUserId() == null) {
			throw new PersistenceException("currentUserId cannot be null.");
		}

		Criteria crit = getHibernateSession().createCriteria(UserGroupImpl.class);
//		crit.add(Expression.eq("groupOwnerId", filter.getCurrentUserId()));
		crit.setProjection( Projections.projectionList()
				.add( Projections.property("id"), "id")
				.add( Projections.property("groupName"), "groupName")
		);
		crit.setResultTransformer(Transformers.aliasToBean(UserGroupPicklistVo.class));

		crit.setMaxResults(getMaxResultSize());
		crit.addOrder(Order.asc("groupName"));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
	 */
	public void delete(UserGroup persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public UserGroup getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, UserGroupImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(UserGroup persistable) throws PersistenceException {
		if( (null != persistable.getId()) && (persistable.getId()>0) )
			super.getHibernateSession().merge(persistable);
		else
			crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
	 * NOTE: As per requirements, only one User Group can be saved at a time.
	 */
	public void saveAll(Collection<UserGroup> persistables) throws PersistenceException {
		// As per requirements, only one User Group can be saved at a time.
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupDao#isGroupNameUniqueToUser(java.lang.Long, java.lang.String)
	 */
	public boolean isGroupNameUniqueToUser(Long userId, String groupName, Long groupId) throws PersistenceException {
		if (userId == null) {
			throw new PersistenceException("userId cannot be null!");
		}

		if (groupName == null || groupName.length() < 1) {
			throw new PersistenceException("groupName cannot be null!");
		}

		String sql = UserGroupQuery.IS_GROUP_NAME_UNIQUE_TO_USER_QUERY;
		if(null != groupId){
			sql = sql + " AND ug.id != :groupid";
		}
		
		Query uguaQuery = getHibernateSession().createQuery(sql);
		
		uguaQuery.setParameter("groupName", groupName.toUpperCase());
		uguaQuery.setParameter("groupOwnerId", userId);
		
		if(null != groupId){
			uguaQuery.setParameter("groupid", groupId);
		}
		
		return ((Long) uguaQuery.uniqueResult()).equals(0L);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupDao#isGroupNameUniqueToSystem(java.lang.String, java.lang.Long)
	 */
	public boolean isGroupNameUniqueToSystem(String groupName, Long groupId) throws PersistenceException {
		
		if (groupName == null || groupName.length() < 1) {
			throw new PersistenceException("groupName cannot be null!");
		}

		String sql = UserGroupQuery.IS_GROUP_NAME_UNIQUE_TO_SYSTEM_QUERY;
		if(null != groupId){
			sql = sql + " AND ug.id != :groupid";
		}
		
		Query uguaQuery = getHibernateSession().createQuery(sql);
		
		uguaQuery.setParameter("groupName", groupName.toUpperCase());
		
		if(null != groupId){
			uguaQuery.setParameter("groupid", groupId);
		}
		
		return ((Long) uguaQuery.uniqueResult()).equals(0L);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserGroupDao#getUserGroups(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<UserGroup> getUserGroups(Long userId) throws PersistenceException {
		   
		Criteria crit = getHibernateSession().createCriteria(UserGroupImpl.class);
		crit.add(Expression.eq("groupOwnerId", userId));
		
		return crit.list();
    }
	
}
