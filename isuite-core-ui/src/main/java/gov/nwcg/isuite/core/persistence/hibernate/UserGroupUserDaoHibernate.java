package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.UserGroupUserImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.vo.UserGroupUserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;

/**
 * This class allows access to the UserGroupUser tables in the database
 * 
 * @author bsteiner
 *
 */
public class UserGroupUserDaoHibernate extends TransactionSupportImpl implements UserGroupUserDao {

   private final CrudDao<UserGroupUser> crudDao;

   /**
    * Full Constructor.
    * @param crudDao {@link CrudDao} cannot be null
    * @param ugDao {@link UserGroupDao} cannot be null
    * @param roleDao {@link RoleDao} cannot be null
    */
   public UserGroupUserDaoHibernate(final CrudDao<UserGroupUser> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao cannot be null");
      }
      this.crudDao = crudDao;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(UserGroupUser persistable) throws PersistenceException {
	   crudDao.delete(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public UserGroupUser getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, UserGroupUserImpl.class);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(UserGroupUser persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<UserGroupUser> persistables) throws PersistenceException {
      if (persistables.size() > 0) {
         crudDao.saveAll(persistables);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupUserDao#getAssignedUsersGrid(java.lang.Long, gov.nwcg.isuite.domain.access.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGroupUserGridVo> getAssignedUsersGrid(Long userGroupId,
            											UserFilter filter) throws PersistenceException {
      
      if (userGroupId == null) {
         throw new IllegalArgumentException("crudDao cannot be null");
      }
      
      DetachedCriteria dCrit = DetachedCriteria.forClass(UserGroupUserImpl.class);
      dCrit.createAlias("userGroupUserAuthorities", "ugua2");
      dCrit.createAlias("ugua2.role", "role");
      /* todo: dan
      if(null != filter.getRoleVos() && filter.getRoleVos().size() > 0) { 
         dCrit.add(Restrictions.in("role.authority", RoleVo.toRoleNames(filter.getRoleVos())));
      }
      dCrit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
      dCrit.setProjection(Property.forName("id"));     
		*/
      
      Criteria crit = getHibernateSession().createCriteria(UserGroupUserImpl.class);
      crit.createAlias("user", "u");
      crit.createAlias("userGroupUserAuthorities", "ugua", CriteriaSpecification.LEFT_JOIN); //This will bring back a User's roles.
      crit.createAlias("ugua.role", "role", CriteriaSpecification.LEFT_JOIN);
      crit.createAlias("u.primaryDispatchCenter", "pdc", CriteriaSpecification.LEFT_JOIN);
      crit.createAlias("u.homeUnit", "hu", CriteriaSpecification.LEFT_JOIN);

      crit.add(Restrictions.eq("userGroupId", userGroupId));
      
      if (filter != null) {
         if (StringUtility.hasValue(filter.getLoginName())) {
            crit.add(Restrictions.ilike("u.loginName", filter.getLoginName().trim(), MatchMode.START));
         }
         if (StringUtility.hasValue(filter.getFirstName())) {
            crit.add(Restrictions.ilike("u.firstName", filter.getFirstName().trim(), MatchMode.START));
         }
         if (StringUtility.hasValue(filter.getLastName())) {
            crit.add(Restrictions.ilike("u.lastName", filter.getLastName().trim(), MatchMode.START));
         }
         if (StringUtility.hasValue(filter.getPdcUnitCode())) {
        	 crit.add(Restrictions.ilike("pdc.unitCode", filter.getPdcUnitCode().trim(), MatchMode.START));
         }
         if (StringUtility.hasValue(filter.getHomeUnitCode())) {
        	 crit.add(Restrictions.ilike("hu.unitCode", filter.getHomeUnitCode().trim(), MatchMode.START));
         }
      } else {
         throw new IllegalArgumentException("filter cannot be null");
      }

      crit.add(Subqueries.propertyIn("id", dCrit));
      crit.add(Subqueries.exists(dCrit));
      crit.add(Restrictions.eq("u.enabled", Boolean.TRUE));
      crit.add(Restrictions.isNull("u.deletedDate"));
      
      crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
      crit.setMaxResults(getMaxResultSize());
      Collection<UserGroupUser> entities = crit.list();

      try{
	      if(null != entities)
	    	  return UserGroupUserGridVo.getInstances(entities);
	      else
	    	  return null;
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.enterprise.UserGroupUserDao#getAvailableUsersByUserGroupId(java.lang.Long, gov.nwcg.isuite.domain.access.UserFilter)
    * 
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGroupUserGridVo> getAvailableUsersByUserGroupId(Long userGroupId, 
            UserFilter filter) throws PersistenceException {
      
      if (userGroupId == null) {
         throw new IllegalArgumentException("userGroupId cannot be null.");
      }
      //TODO:  Change this to HQL or Criteria.
      if (filter != null) {
         StringBuffer b = new StringBuffer();
         b.append("SELECT U.ID AS id, ")
            .append("U.LOGIN_NAME AS loginNm, ")
            .append("U.FIRST_NAME AS firstNm, ")
            .append("U.LAST_NAME AS lastNm, ")
            .append("HUORG.UNIT_CODE as primaryOrgCd, ")
            .append("DCORG.UNIT_CODE as dispatchCenterCd ")
            .append("FROM ISW_USER U ")
            // primary dispatch center join
            .append("LEFT OUTER JOIN ISW_ORGANIZATION HUORG ON (U.HOME_UNIT_ID = HUORG.ID) ")
            // home unit join
            .append("LEFT OUTER JOIN ISW_ORGANIZATION DCORG ON (U.PRIMARY_DISP_CTR_ID = DCORG.ID)")
            // do not include any administrators..
            .append("WHERE U.ID NOT IN ")
                  .append("(SELECT UA.USER_ID FROM ISW_USER_AUTHORITY UA WHERE AUTHORITY_ID IN (")
                     .append("SELECT ID FROM ISW_AUTHORITY WHERE AUTHORITY_NAME IN ('ROLE_SYS_ADMIN_1', 'ROLE_SYS_ADMIN_2'))) ")
            // do not include currently logged in user..
            .append("AND (U.ID != ").append(filter.getCurrentUserId()).append(") ")
            // do not include disabled users..
            .append("AND (U.ENABLED != 0) ")
            // do not include deleted users..
            .append("AND (U.DELETED_DATE IS NULL) ")
            // do not include users already in the group..
            .append("AND U.ID NOT IN (SELECT UGU.USER_ID ")
               .append("FROM ISW_USER_GROUP_USER UGU ")
               .append("WHERE UGU.USER_GROUP_ID = ").append(userGroupId).append(") ");
      
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
         
         SQLQuery query = getHibernateSession().createSQLQuery(b.toString());
         query.setResultTransformer(Transformers.aliasToBean(UserGroupUserGridVo.class));
         query.setMaxResults(getMaxResultSize());
         return query.list();
      } else {
         throw new IllegalArgumentException("userFilter cannot be null.");
      }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.enterprise.UserGroupUserDao#getUserGroups(java.util.Collection)
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGroupUser> getUserGroupUsers(Collection<Long> userGroupIds) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(UserGroupUserImpl.class);
	   
	   crit.createAlias("user", "u",CriteriaSpecification.LEFT_JOIN);
	   
	   crit.add(Restrictions.in("this.userGroup.id", userGroupIds));
	   crit.add(Restrictions.eq("u.enabled", Boolean.TRUE));
	   
	   crit.setMaxResults(getMaxResultSize());
	   
	   return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserGroupUserDao#getUsersInGroupGrid(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGroupUserVo> getUsersInGroupGrid(Long userGroupId, UserFilter filter) throws PersistenceException {
	   if (filter == null) {
		   throw new PersistenceException("filter cannot be null.");
	   }

	   Criteria crit = getHibernateSession().createCriteria(UserGroupUserImpl.class);
	   crit.createAlias("user", "u");
	   crit.createAlias("u.homeUnit", "upo");

	   crit.add(Expression.eq("userGroupId", userGroupId));

	   if (filter.getLoginName() != null && filter.getLoginName().trim().length() > 0) {
		   crit.add(Expression.ilike("u.loginName", filter.getLoginName().trim(), MatchMode.START));
	   }
	   if (filter.getFirstName() != null && !"".equals(filter.getFirstName())) {
		   crit.add(Expression.ilike("u.firstName", filter.getFirstName().trim(), MatchMode.START));
	   }
	   if (filter.getLastName() != null && !"".equals(filter.getLastName())) {
		   crit.add(Expression.ilike("u.lastName", filter.getLastName().trim(), MatchMode.START));
	   }
	   if (filter.getHomeUnitCode() != null && !"".equals(filter.getHomeUnitCode())) {
		   crit.add(Expression.ilike("upo.unitCode", filter.getHomeUnitCode().trim(), MatchMode.START));
	   }

	   crit.add(Expression.eq("u.enabled", Boolean.TRUE));

	   crit.setMaxResults(getMaxResultSize());
	   crit.addOrder(Order.asc("u.loginName"));
	   Collection<UserGroupUser> entities = crit.list();

	   try {
		   return UserGroupUserVo.getInstances(entities, true);
	   } catch (Exception e) {
		   throw new PersistenceException(e);
	   }
   }

   public void deleteUsersById(Collection<Long> ids) throws PersistenceException {
	   StringBuffer sql = new StringBuffer()
		  	.append("DELETE FROM ISW_USER_GROUP_USER WHERE USER_ID IN ( :ids )");

	   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
	   q.setParameterList("ids", ids);
	   
	   q.executeUpdate();
	   
   }
   
}
