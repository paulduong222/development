package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentUserQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.RestrictedIncidentUserQuery;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.RoleEnum;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class RestrictedIncidentUserDaoHibernate extends TransactionSupportImpl 
   implements RestrictedIncidentUserDao {

   private final CrudDao<RestrictedIncidentUser> crudDao;
   
   public RestrictedIncidentUserDaoHibernate(final CrudDao<RestrictedIncidentUser> crudDao) {
      
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }
   
   public void delete(Long id) throws PersistenceException {

      RestrictedIncidentUser riu = this.getById(id);
      if(riu == null) throw new PersistenceException("unable to locate object with id: " + id);
      
      this.delete(riu);
   }

   public RestrictedIncidentUser getById(Long id) throws PersistenceException {
      return this.getById(id, RestrictedIncidentUserImpl.class);
   }
   
   @SuppressWarnings("unchecked")
   public RestrictedIncidentUser getById(Long id, Class clazz) throws PersistenceException {
      return this.crudDao.getById(id, clazz);
   }   

   public void delete(RestrictedIncidentUser persistable) throws PersistenceException {

      if(persistable == null) throw new PersistenceException("incoming persistable cannot be null");
      
      this.crudDao.delete(persistable);
   }

   public void save(RestrictedIncidentUser persistable) throws PersistenceException {
      
      if(persistable == null) throw new PersistenceException("incoming persistable cannot be null");
      
      if(persistable.getId()>0)
    	  super.getHibernateSession().merge(persistable);
      else
    	  this.crudDao.save(persistable);
   }

   public void saveAll(Collection<RestrictedIncidentUser> persistables) throws PersistenceException {

      if(persistables == null) throw new PersistenceException("incoming persistable cannot be null");
      
      this.crudDao.saveAll(persistables);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.RestrictedIncidentUserDao#getRestrictedIncidentOwnerCount()
    */
   public int getRestrictedIncidentOwnerCount(Long incidentId) throws PersistenceException {
      if (incidentId == null) {
         throw new PersistenceException("IncidentId cannot be null.");
      }
      String hql = "select count(*) from RestrictedIncidentUserImpl riu where riu.incidentId = :incidentId and riu.userType = 'OWNER' and riu.accessEndDate is null";
      Query query = getHibernateSession().createQuery(hql);
      query.setLong("incidentId", incidentId);
      Long result = (Long)query.uniqueResult();
      return result.intValue();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getUsersInRestrictedIncident(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<RestrictedIncidentUserVo> getUsersInRestrictedIncident(Long restrictedIncidentId, UserFilter filter) throws PersistenceException {
	   if (filter == null) {
		   throw new PersistenceException("filter cannot be null.");
	   }
	   
	   Criteria crit = getHibernateSession().createCriteria(RestrictedIncidentUserImpl.class);
//	   crit.createAlias("userRoles", "ur",CriteriaSpecification.LEFT_JOIN);
	   crit.createAlias("user", "u");
	   crit.createAlias("u.homeUnit", "upo");
	   crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	   
	   crit.add(Expression.eq("incidentId", restrictedIncidentId));
	   
	   if (filter.getLoginName() != null && filter.getLoginName().trim().length() > 0 ) {
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
       
//       if( (filter.getUserRoleVos() != null) && (filter.getUserRoleVos().size()>0 )){
//  		   Collection<Long> ids = new ArrayList<Long>();
//		   for(SystemRoleVo vo : filter.getUserRoleVos()){
//			   ids.add(vo.getId());
//		   }
//           
//           crit.add(Restrictions.in("ur.id", ids));
//         }
       
       crit.add(Expression.eq("u.enabled", Boolean.TRUE));
       //crit.add(Expression.eq("userType", RestrictedIncidentUserTypeEnum.USER));
       crit.add(Expression.isNull("accessEndDate"));
       
       crit.setMaxResults(getMaxResultSize());
       Collection<RestrictedIncidentUser> entities = crit.list();
       
       try {
    	   return RestrictedIncidentUserVo.getInstances(entities, true);
       } catch (Exception e) {
    	   throw new PersistenceException(e);
       }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getAvailableRestrictedIncidentUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
    */
   public Collection<UserVo> getAvailableRestrictedIncidentUsers(Long incidentId, UserFilter filter) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class);

	   crit.createAlias("systemRoles", "role");
	   crit.createAlias("homeUnit","hu");

	   if (null != filter) {
		   if(null != filter.getExcludeRoles() && filter.getExcludeRoles().size()>0){
			   crit.add(Restrictions.not(Restrictions.in("role.roleName", filter.getExcludeRoles())));
		   }

		   if(null != filter.getIncludeRoles() && filter.getIncludeRoles().size()>0){
			   crit.add(Restrictions.in("role.roleName", filter.getIncludeRoles()));
		   }

		   if(null != filter.getEnabled() && filter.getEnabled()){
			   crit.add(Restrictions.eq("this.enabled",Boolean.TRUE));
		   }
		   if(StringUtility.hasValue(filter.getLoginName())){
			   crit.add(Restrictions.ilike("this.loginName", filter.getLoginName().toLowerCase(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getFirstName())){
			   crit.add(Restrictions.ilike("this.firstName", filter.getFirstName().toLowerCase(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getLastName())){
			   crit.add(Restrictions.ilike("this.lastName", filter.getLastName().toLowerCase(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(filter.getHomeUnitCode())){
			   crit.add(Restrictions.ilike("hu.unitCode", filter.getHomeUnitCode().toLowerCase(), MatchMode.START));
		   }
	   }
	   crit.add(Restrictions.isNull("this.deletedDate"));
	   crit.add(Restrictions.sqlRestriction(" this_.id not in (select user_id from isw_restricted_incident_user where incident_id = " + incidentId + " ) "));
	   
	   crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	   crit.addOrder(Order.asc("this.loginName"));
	   crit.setMaxResults(super.getMaxResultSize());

	   Collection<User> entities =crit.list();
	   Collection<UserVo> vos = new ArrayList<UserVo>();
	   
	   try{
		   vos = UserVo.getInstances(entities, true);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	   
	   return vos;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getAvailableIncidentUsers(java.lang.Long)
    */
   public Collection<UserGridVo> getAvailableIncidentUsers(Long incidentId) throws PersistenceException {
		String sql = IncidentUserQuery.getAvailableUsersQuery(incidentId, super.isOracleDialect());

		SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		   
	    CustomResultTransformer crt = new CustomResultTransformer(UserGridVo.class);
		crt.addProjection("userId", "id");
		crt.addProjection("lastName", "lastName");
	    crt.addProjection("firstName", "firstName");
	    crt.addProjection("loginName", "loginName");
	    crt.addProjection("homeUnitCode", "homeUnitCode");
		
	    crt.addScalar("userId", Long.class.getName());
	    
	    q.setResultTransformer(crt); 
	       
        return q.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.RestrictedIncidentUserDao#unRestrictIncidentUsersAndOwners(java.lang.Long)
    */
   public int unRestrictIncidentUsersAndOwners(Long incidentId) throws PersistenceException {
      if (incidentId == null) {
         throw new PersistenceException("IncidentId cannot be null.");
      }

      /*
      String hql = "update RestrictedIncidentUserImpl riu set riu.accessEndDate = CURRENT_DATE where riu.incidentId = :incidentId";
      Query query = getHibernateSession().createQuery(hql);
      query.setLong("incidentId", incidentId);
      return query.executeUpdate();
      */
      String sql = "DELETE FROM isw_restricted_incident_user where incident_id = " + incidentId;
      Query q = getHibernateSession().createSQLQuery(sql);
      return q.executeUpdate();
   }

   private StringBuffer setRestrictedIncidentAvailableUsersOrOwnersFilter(UserFilter filter) {
      StringBuffer sb = new StringBuffer();
      if(filter != null) {
         if(filter.getLoginName() != null && filter.getLoginName().trim().length() > 0) {
            sb.append("and upper(u.login_name) like upper('")
            .append(createSafeString(filter.getLoginName().trim())).append("%') ");
         }
         if(filter.getFirstName() != null && filter.getFirstName().trim().length() > 0) {
            sb.append("and upper(u.first_name) like upper('")
            .append(createSafeString(filter.getFirstName().trim())).append("%') ");
         }
         if(filter.getLastName() != null && filter.getLastName().trim().length() > 0) {
            sb.append("and upper(u.last_name) like upper('")
            .append(createSafeString(filter.getLastName().trim())).append("%') ");
         }
         if(filter.getPdcUnitCode() != null && filter.getPdcUnitCode().trim().length() > 0) {
            sb.append("and upper(o.organization_name) like upper('")
            .append(createSafeString(filter.getPdcUnitCode().trim())).append("%') ");
         }
         if(filter.getHomeUnitCode() != null && filter.getHomeUnitCode().trim().length() > 0) {
            sb.append("and upper (o.unit_code) like upper('")
            .append(createSafeString(filter.getHomeUnitCode().trim())).append("%') ");
         }
      }
      return sb;
   }
   
   //TODO:  Is this ever used? -dbudge
   @SuppressWarnings("unchecked")
   public Collection<UserGridVo> getRestrictedIncidentAvailableUsersOrOwnersGrid(Long incidentId, UserFilter filter, Long currentWorkAreaId, RestrictedIncidentUserTypeEnum type) throws PersistenceException {
      if (incidentId == null || type == null) {
         throw new PersistenceException("IncidentId and RestrictedIncidentUserTypeEnum type must not be null.");
      }
      StringBuffer hql = new StringBuffer();
      hql.append("select distinct u.id, u.login_name as loginNm, u.first_name, u.last_name, pdc.unit_code as primaryDispatchCenter, hu.unit_code as homeUnitCode "); 
      hql.append("from isw_user u ");
      hql.append("inner join isw_organization pdc on u.primary_disp_ctr_id = pdc.id ");
      hql.append("inner join isw_organization hu on u.home_unit_id = hu.id ");
      hql.append("inner join isw_work_area_user wau on u.id = wau.user_id ");
      hql.append("inner join isw_wauser_authority waua on wau.id = waua.work_area_user_id ");
      hql.append("inner join isw_authority a on waua.authority_id = a.id ");
      hql.append("WHERE u.id not in (select riu.user_id from isw_restricted_incident_user riu where riu.incident_id = " + incidentId + ") ");
      
      if (null != currentWorkAreaId) {
         hql.append("and wau.work_area_id = " + currentWorkAreaId + " ");
      }
       
      if (type == RestrictedIncidentUserTypeEnum.OWNER) {
         hql.append("and a.authority_name = '" + RoleEnum.ROLE_MANAGER.toString() + "' " );
      }
      
      hql.append(setRestrictedIncidentAvailableUsersOrOwnersFilter(filter));
      hql.append("or u.id in (select riu.user_id from isw_restricted_incident_user riu where riu.incident_id = " + 
      		incidentId + " and riu.access_end_date is not null) ");
      
      if (null != currentWorkAreaId) {
         hql.append("and wau.work_area_id = " + currentWorkAreaId + " ");
      }
      
      if (type == RestrictedIncidentUserTypeEnum.OWNER) {
         hql.append("and a.authority_name = '" + RoleEnum.ROLE_MANAGER.toString() + "' " );
      }
      
      hql.append(setRestrictedIncidentAvailableUsersOrOwnersFilter(filter));
      
      Query query = getHibernateSession().createSQLQuery(hql.toString()).addScalar("id", Hibernate.LONG);
      query.setResultTransformer(Transformers.aliasToBean(UserGridVo.class));
      
      query.setMaxResults(getMaxResultSize());
      return query.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.RestrictedIncidentUserDao#getRestrictedIncidentUsersAndOwners(java.lang.Long, gov.nwcg.isuite.domain.access.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<Long> getRestrictedIncidentUsersAndOwnersUserIds(Long incidentId) throws PersistenceException {
      if (incidentId == null) {
         throw new PersistenceException("IncidentId must not be null.");
      }
      String hql = "select riu.userId from RestrictedIncidentUserImpl riu where riu.incidentId = :incidentId and riu.accessEndDate is null";
      Query query = getHibernateSession().createQuery(hql);
      query.setLong("incidentId", incidentId);
      return query.list();
   }

   public void deleteUsersById(Collection<Long> ids) throws PersistenceException {
	   StringBuffer sql = new StringBuffer()
		  	.append("DELETE FROM ISW_RESTRICTED_INCIDENT_USER WHERE USER_ID IN ( :ids )");

	   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
	   q.setParameterList("ids", ids);
	   
	   q.executeUpdate();
	   
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getRIUsersByUserIdAndIncidentIds(java.lang.Long, java.util.Collection)
    */
   @SuppressWarnings("unchecked")
   public Collection<RestrictedIncidentUser> getRIUsersByUserIdAndIncidentIds(Long userId, Collection<Long> incIds) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(RestrictedIncidentUserImpl.class);
      crit.add(Restrictions.eq("userId", userId));
      crit.add(Restrictions.in("incidentId", incIds));
      return crit.list();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getUserIdsByIncidentIdAndUserId(java.lang.Long, java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Collection<Long> getUserIdsByIncidentIdAndUserId(Long incidentId, Long userId) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(RestrictedIncidentUserImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("userId"))
               );
      crit.add(Restrictions.eq("incidentId", incidentId));
      crit.add(Restrictions.eq("userId", userId));
      return crit.list();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao#getRestrictedIncidentUserId(java.lang.Long, java.lang.Long)
    */
   public Long getRestrictedIncidentUserId(Long userId, Long incidentId) throws PersistenceException {
	  
	   String sql = RestrictedIncidentUserQuery.getRestrictedIncidentUserId(userId, incidentId);
	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   
	   Object rslt = query.uniqueResult();
	   
	   try{
		   if(null != rslt){
			   Long id = TypeConverter.convertToLong(rslt);
			   return id;
		   }
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	   
	   return null;
   }

   public void updateUserDefaultCheckIn(RestrictedIncidentUserVo riuVo) throws PersistenceException {
	   StringBuffer  sql = new StringBuffer()
	   	.append("UPDATE ISW_RESTRICTED_INCIDENT_USER ")
	   	.append("SET DEFAULT_CHECKIN_TYPE='"+riuVo.getDefaultCheckinType()+"' ")
	   	.append("WHERE USER_ID = "+riuVo.getUserVo().getId() + " ")
	   	.append("AND INCIDENT_ID = " + riuVo.getIncidentVo().getId() + " ");

	   SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
	   
	   query.executeUpdate();
   }
}
