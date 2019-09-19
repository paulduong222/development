package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.domain.impl.WorkAreaUserImpl;
import gov.nwcg.isuite.core.filter.SharedWorkAreaFilter;
import gov.nwcg.isuite.core.persistence.WorkAreaUserDao;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * Data Access for Work Areas.
 *  
 * @author bsteiner
 */
public class WorkAreaUserDaoHibernate extends TransactionSupportImpl implements WorkAreaUserDao {
   
   private final CrudDao<WorkAreaUser>         crudDao;
   static final Logger LOG = Logger.getLogger(WorkAreaUserDaoHibernate.class);
   
   public WorkAreaUserDaoHibernate(final CrudDao<WorkAreaUser> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(WorkAreaUser persistable) throws PersistenceException {
//      if (null != persistable.getWorkAreaUserRoles()) {
//    	  persistable.getWorkAreaUserRoles().clear();
//      }
      crudDao.delete(persistable);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaUserDao#getWorkAreaUserByUserId(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<WorkAreaUser> getWorkAreaUsersByUserId(Long userId) throws PersistenceException {
      Query query = getHibernateSession().createQuery("select i from WorkAreaUserImpl i where i.userId = :userId");
      query.setLong("userId", userId);
      return query.list();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public WorkAreaUser getById(Long id, Class clazz) throws PersistenceException {
	   return crudDao.getById(id, WorkAreaUserImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(WorkAreaUser persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<WorkAreaUser> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaUserDao#getByWorkAreaIdAndUserId(java.lang.Long, java.lang.Long)
    */
   public WorkAreaUser getByWorkAreaIdAndUserId(Long workAreaId, Long userId) throws PersistenceException {
	   Query query = getHibernateSession().createQuery("from WorkAreaUserImpl wau where wau.userId = :userid and wau.workAreaId = :workareaid");
	   query.setLong("userid", userId);
	   query.setLong("workareaid", workAreaId);
	   return (WorkAreaUser)query.uniqueResult();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkAreaUserDao#getWorkAreaUserCountByWorkAreaId(java.lang.Long)
    */
   public int getWorkAreaUserCountByWorkAreaId(Long workAreaId) throws PersistenceException {
	   if (null == workAreaId) {
		   throw new PersistenceException("workAreaId cannot be null.");
	   }
	   Query query = getHibernateSession().createQuery("select count(*) from WorkAreaUserImpl wau where wau.workAreaId = :workAreaId");
	   query.setLong("workAreaId", workAreaId);
	   Long result = (Long)query.uniqueResult();
	   return result.intValue();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaUserDao#getUsersAvailableForSharedWorkArea(gov.nwcg.isuite.domain.access.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGridVo> getUsersAvailableForSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) throws PersistenceException {
	   if (filter == null || filter.getCurrentUserId() == 0 || filter.getCurrentUserId() == null || workAreaId == 0) {
		   throw new PersistenceException("Work area id and current user id required to call this method.");
	   }
      
	   StringBuffer hql = new StringBuffer();
	   hql.append("select distinct u.id as id, ")
      	 .append("u.loginName as loginName, ")
      	 .append("u.firstName as firstName, ")
      	 .append("u.lastName as lastName, ")
      	 .append("huorg.unitCode as homeUnitCode, ")
      	 .append("dcorg.unitCode as pdcUnitCode ")
         .append("from UserImpl u join u.systemRoles role, OrganizationImpl huorg, OrganizationImpl dcorg  ")
         .append("where u.primaryDispatchCenterId = dcorg.id ")
         .append("and u.homeUnitId = huorg.id ")
         .append("and u.id not in ")
         .append("(select wau.userId from WorkAreaUserImpl wau ")
         .append("where wau.workAreaId = ")
         .append(workAreaId)
         .append(")" + " and role.privilegedRole = false ")
         /*id not in ")
         .append("(select ur.userId from UserImpl ua ")
         .append("where ua.authorityId in ")
         .append("(select ua.role.id from ua.role where ua.role.authority in ('ROLE_ADMINISTRATOR')))")
         */
         .append(" and u.id != ").append(filter.getCurrentUserId())
         .append(" and u.enabled = true ");
      
	   if (filter != null) {
		 try{
			 if (null != filter.getRoleVoIdsFromIntegers() && filter.getRoleVoIdsFromIntegers().size() > 0){
				 hql.append("and role.id in (" + filter.getRoleVoIdsFromIntegers() + ") ");
			 }
		 }catch(Exception smother){}
		 
		 if (filter.getUserName() != null && filter.getUserName().trim().length() > 0) {
            hql.append("and upper(u.loginName) like upper('")
               .append(createSafeString(filter.getUserName().trim())).append("%') ");
         }
         if (filter.getFirstName() != null && filter.getFirstName().trim().length() > 0) {
            hql.append("and upper(u.firstName) like upper('")
               .append(createSafeString(filter.getFirstName().trim())).append("%') ");
         }
         if (filter.getLastName() != null && filter.getLastName().trim().length() > 0) {
            hql.append("and upper(u.lastName) like upper('")
               .append(createSafeString(filter.getLastName().trim())).append("%') ");
         }
         if (filter.getPrimaryOrganization() != null && filter.getPrimaryOrganization().trim().length() > 0) {
            hql.append("and upper(huorg.unitCode) like upper('")
               .append(createSafeString(filter.getPrimaryOrganization().trim())).append("%') ");
         }
         if (filter.getPrimaryDispatchCenter() != null && filter.getPrimaryDispatchCenter().trim().length() > 0) {
            hql.append("and upper(dcorg.unitCode) like upper('")
               .append(createSafeString(filter.getPrimaryDispatchCenter().trim())).append("%') ");
         }
         
	   }
	   
	   if(super.isOracleDialect())
		   hql.append(" order by upper(u.loginName) asc " );
	   else
		   hql.append(" order by u.loginName asc " );
		   
	   LOG.debug("generated query: " + hql.toString());
	   Query query = getHibernateSession().createQuery(hql.toString());
	   query.setResultTransformer(Transformers.aliasToBean(UserGridVo.class));
	   query.setMaxResults(getMaxResultSize());
	   
	   return query.list();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.WorkAreaUserDao#getWorkAreaUsersByWorkAreaId(gov.nwcg.isuite.domain.access.UserFilter, java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGridVo> getUsersByWorkAreaId(SharedWorkAreaFilter filter, Long workAreaId) throws PersistenceException {
	   if (workAreaId == 0) {
		   throw new PersistenceException("Work Area ID required to call this method.");
	   }

	   Collection<UserGridVo> userGridVos = new ArrayList<UserGridVo>();

	   Criteria crit = getHibernateSession().createCriteria(WorkAreaUserImpl.class, "wa");

	   crit.setFetchMode("user", FetchMode.JOIN);
	   crit.createAlias("user", "u");
	   crit.createAlias("u.homeUnit", "hu"); 
	   crit.createAlias("u.primaryDispatchCenter", "pdc");
	   crit.setFetchMode("u.systemRoles", FetchMode.JOIN);
	   crit.createAlias("u.systemRoles", "role");
//	   crit.createAlias("workAreaUserRoles", "waur",CriteriaSpecification.LEFT_JOIN); 

	   crit.add(Restrictions.eq("wa.workAreaId", workAreaId));

	   if(filter != null) {
		   if(StringUtility.hasValue(filter.getUserName())) {
			   crit.add(Restrictions.ilike("u.loginName", filter.getUserName(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getFirstName())) {
			   crit.add(Restrictions.ilike("u.firstName", filter.getFirstName(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getLastName())) {
			   crit.add(Restrictions.ilike("u.lastName", filter.getLastName(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getPrimaryDispatchCenter())) {
			   crit.add(Restrictions.ilike("pdc.unitCode", filter.getPrimaryDispatchCenter(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getPrimaryOrganization())) {
			   crit.add(Restrictions.ilike("hu.unitCode", filter.getPrimaryOrganization(), MatchMode.START));
		   }
		   if(null != filter.getCurrentUserId() && filter.getCurrentUserId() > 0) {
			 //make not equal to current user
			   crit.add(Restrictions.ne("wa.userId", filter.getCurrentUserId()));
		   }
		   
		   if(filter.getSharedUsers()) {
			   //shared users only
			   crit.add(Restrictions.eq("wa.userType", WorkAreaUserTypeEnum.SHARED));
		   }
		   
		   try{
			   if(null != filter.getRoleVoIdsFromIntegers() && filter.getRoleVoIdsFromIntegers().size() > 0){
				   crit.add(Restrictions.in("role.id", filter.getRoleVoIdsFromIntegers()));
			   }
		   }catch(Exception smother){}
		   
		   //TODO:  Change this filter to check for a user's default system roles. -dbudge
//		   if(filter.getRoleVoIds() != null && filter.getRoleVoIds().size() > 0 ){
//			   try{
//				   crit.add(Restrictions.in("waur.id", filter.getRoleVoIdsFromIntegers()));
//			   }catch(Exception e){
//				   throw new PersistenceException(e);
//			   }
//		   }
	   }

	   crit.setMaxResults(getMaxResultSize());

       crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

       crit.addOrder(Order.asc("u.loginName").ignoreCase());
       
	   Collection<WorkAreaUser> entities = crit.list();

	   try{
		   userGridVos = UserGridVo.getInstancesFromWorkAreaUser(entities, true);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }

	   return userGridVos;
   }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.persistence.WorkAreaUserDao#deleteUserFromSharedWorkAreas(java.util.Collection)
   * 
   * Removes the user from work areas shared to them 
   * and if they have shared any works areas, it removes the users they have shared to
   */
   public void deleteUserFromSharedWorkAreas(Collection<Long> ids) throws PersistenceException {
	   StringBuffer sql = new StringBuffer()
		  	.append("DELETE FROM ISW_WORK_AREA_USER WHERE USER_TYPE = 'SHARED' AND (USER_ID IN ( :ids ) OR SHARED_BY_USER_ID in ( :ids)) ");

	   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
	   q.setParameterList("ids", ids);
	   
	   q.executeUpdate();
	   
   }   
}
