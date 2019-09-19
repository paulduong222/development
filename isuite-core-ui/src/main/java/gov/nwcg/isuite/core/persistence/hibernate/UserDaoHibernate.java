package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserQuery;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

//@SuppressWarnings("unchecked")
public class UserDaoHibernate extends TransactionSupportImpl implements UserDao{
   private final CrudDao<User>         crudDao;

   /**
    * Constructor.
    * 
    * @param crudDao
    *           can't be null
    */
   public UserDaoHibernate(final CrudDao<User> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   protected CrudDao<User> getCrudDao() {
      return crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserDao#save(gov.nwcg.isuite.core.domain.User)
    */
   public void save(User user) throws PersistenceException, DuplicateItemException {
	   getCrudDao().setSkipSetAuditInfo(super.getSkipSetAuditInfo());
	   getCrudDao().save(user);
   }
   
   public void merge(User entity) throws PersistenceException {
	   super.getHibernateTemplate().merge(entity);
   }

   @SuppressWarnings("unchecked")
   public User getByLoginName(String loginName) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class);

	   crit.add(Restrictions.eq("loginName", loginName).ignoreCase());
	   crit.add(Restrictions.isNull("deletedDate"));
	   List<User> results = crit.list();

	   if ( results == null || results.size() == 0 ) {
		   return null;
	   }

	   return results.get(0);
   }

   public void delete(User user) throws PersistenceException {
	   if(user == null) {
		   throw new PersistenceException("User cannot be null.");
	   }
	   crudDao.delete(user);
   }

   public User getById(Long id, Class<?> clazz) throws PersistenceException {
       return crudDao.getById(id, UserImpl.class);
   }

   public void saveAll(Collection<User> users) throws PersistenceException {
      getCrudDao().saveAll(users);
   }

   /**
    * Retrieve a {@link Collection} of {@link UserGridVo} objects based on the
    * provided filter.
    * @param userFilter {@link UserFilter}
    * @return A {@link Collection} of {@link UserGridVo} objects
    * @throws PersistenceException
    */
   @SuppressWarnings("unchecked")
   public Collection<UserGridVo> getGrid(UserFilter userFilter) throws PersistenceException {

	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class);

	   crit.createAlias("systemRoles", "sr",CriteriaSpecification.LEFT_JOIN);
	   crit.createAlias("homeUnit","hu");
	   // crit.createAlias("primaryDispatchCenter","pdc");

	   if (null != userFilter) {
		   if(LongUtility.hasValue(userFilter.getCurrentUserId())){
			   crit.add(Restrictions.eq("id", userFilter.getCurrentUserId()));
		   }
		   if(StringUtility.hasValue(userFilter.getLoginName())){
			   crit.add(Restrictions.ilike("this.loginName", userFilter.getLoginName(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(userFilter.getFirstName())){
			   crit.add(Restrictions.ilike("this.firstName", userFilter.getFirstName().toUpperCase(), MatchMode.START));
		   }
		   if(StringUtility.hasValue(userFilter.getLastName())){
			   crit.add(Restrictions.ilike("this.lastName", userFilter.getLastName().toUpperCase(), MatchMode.START));
		   }
//		   if(userFilter.getCreatedDate() != null){
//			   crit.add(Restrictions.eq("auditable.createdDate", userFilter.getCreatedDate()));
//		   }
		   if(StringUtility.hasValue(userFilter.getCrypticDateFilterCode())) {
			   try {
				   super.applyCrypticDateFilter(crit, userFilter.getCrypticDateFilterCode(), "this_.created_date");
			   } catch (Exception e) {
				   throw new PersistenceException(e);
			   }
		   }
		   if(StringUtility.hasValue(userFilter.getHomeUnitCode())){
			   crit.add(Restrictions.ilike("hu.unitCode", userFilter.getHomeUnitCode().toUpperCase(), MatchMode.START));
		   }
//		   if((null != userFilter.getPdcUnitCode()) && (StringUtility.hasValue(userFilter.getPdcUnitCode()))){
//			   crit.add(Restrictions.ilike("pdc.unitCode", userFilter.getPdcUnitCode().toUpperCase(), MatchMode.START));
//		   }
		   if( userFilter.getEnabledString() != null  && !userFilter.getEnabledString().equals("")) {
			   if(userFilter.getEnabledString().toUpperCase().equals("ENABLED")){
				   crit.add(Restrictions.eq("this.enabled", Boolean.TRUE));
			   }
			   if(userFilter.getEnabledString().toUpperCase().equals("DISABLED")){
				   crit.add(Restrictions.eq("this.enabled", Boolean.FALSE));
			   }
		   }
		   if(userFilter.getCurrentUserId() != null) {
			   //crit.add(Restrictions.ne("this.id", userFilter.getCurrentUserId()));//Exclude the currently logged in user.
		   }
		   
		   if( (userFilter.getUserRoleVos() != null) && (userFilter.getUserRoleVos().size()>0 )){
			   Collection<Long> ids = new ArrayList<Long>();
			   for(SystemRoleVo vo : userFilter.getUserRoleVos()){
				   ids.add(vo.getId());
			   }

			   crit.add(Restrictions.in("sr.id", ids));
		   }
	   }

	   crit.add(Restrictions.isNull("this.deletedDate"));
	   crit.addOrder(Order.asc("this.loginName").ignoreCase());
	   //crit.setMaxResults(super.getMaxResultSize());

	   crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

	   Collection<User> entities = crit.list();

	   try{
		   return UserGridVo.getInstances(entities, true);
		   
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }

   }

   @SuppressWarnings("unchecked")
   public Collection<User> getByIds(Collection<Long> userIds) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class);
	   crit.add(Restrictions.in("id", userIds));
	   return crit.list();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserDao#enableUsers(java.util.Collection)
    */
   public void enableUsers(Collection<Long> userIds) throws PersistenceException {
	   Query q = getHibernateSession().getNamedQuery(UserQuery.ENABLE_USERS);

	   q.setParameter("enabled", Boolean.TRUE);
	   q.setParameterList("ids", userIds);

	   q.executeUpdate();
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserDao#disableUsers(java.util.Collection)
    */
   public void disableUsers(Collection<Long> userIds) throws PersistenceException {
	   Query q = getHibernateSession().getNamedQuery(UserQuery.DISABLE_USERS);

	   q.setParameter("enabled", Boolean.FALSE);
	   q.setParameterList("ids", userIds);

	   q.executeUpdate();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserDao#deleteUsers(java.util.Collection)
    */
   public void deleteUsers(Collection<Long> userIds) throws PersistenceException {
	   Query q = getHibernateSession().getNamedQuery(UserQuery.DELETE_USERS);
	   q.setParameterList("ids", userIds);
	   q.executeUpdate();
   }
  
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.UserDao#getUsers(gov.nwcg.isuite.core.filter.UserFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<User> getUsers(UserFilter filter) throws PersistenceException {

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

	   crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	   crit.addOrder(Order.asc("this.loginName"));
	   crit.setMaxResults(super.getMaxResultSize());

	   return crit.list();
   }
   
   public void updateLastLogin(Long id) throws PersistenceException {
	   User user = getById(id, UserImpl.class);
	   if(null != user){
		   user.setLastLoginDate(Calendar.getInstance().getTime());
		   getCrudDao().setSkipSetAuditInfo(super.getSkipSetAuditInfo());
		   crudDao.save(user);
	   }
   }
  
   public UserVo getUserById(Long userId) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(UserImpl.class);
	   
	   crit.setFetchMode("systemRoles",FetchMode.JOIN);

	   crit.add(Restrictions.eq("this.id", userId));

	   User user = (User)crit.uniqueResult();
	   
	   try {
		   return UserVo.getInstance(user, true);
	   }
	   catch ( Exception e ) {
		   throw new PersistenceException(e);
	   }
   }

   public Long getUserId(String loginName) throws PersistenceException {
	   String sql="select id from isw_user where login_name='"+loginName+"'";
	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   
	   Object rslt=query.uniqueResult();
	   if(null != rslt){
		   try{
			   Long id=TypeConverter.convertToLong(rslt);
			   return id;
		   }catch(Exception e){
			   
		   }
	   }
	   
	   return 0L;
   }
   /**
    * Returns list of permission keys that the user has authorizations to execute.
    * 
    * @param userId
    * 			the user id
    * @return
    * 			collection of permission keys
    * @throws PersistenceException
    */
   @SuppressWarnings("unchecked")
   public Collection<String> getPermissionsForUser(Long userId) throws PersistenceException {
	   StringBuffer sql = new StringBuffer()
	   .append(UserQuery.GET_PERMISSIONS_FOR_USER_SQL_QUERY)
	   .append("AND SRP.ROLE_FLAG = " + super.getBooleanComparison(true));

	   SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
	   q.setLong("userid", userId);

	   Collection<String> list = q.list();

	   return list;
   }
 
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserDao#getUsersByIds(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public Collection<User> getUsersByIds(Collection<Long> ids) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(UserImpl.class);
		   
		crit.setFetchMode("systemRoles",FetchMode.JOIN);

		crit.add(Restrictions.in("this.id", ids));
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}
 
	@SuppressWarnings("unchecked")
	public Collection<User> getUserPasswordExpirations(int pwdDays, Date dt) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(UserImpl.class);

		String strDate = DateUtil.toDateString(dt,DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS );
		
		if(super.isOracleDialect()){
			crit.add(Restrictions.sqlRestriction("user_password_created_date + " + pwdDays + " < " + "to_date('"+strDate+"', 'MM/DD/YYYY HH24:MI:SS') " ));
		}else{
			crit.add(Restrictions.sqlRestriction("user_password_created_date + interval '1 day' * " + pwdDays + " < " + dt + " "));
		}
		crit.add(Restrictions.eq("this.enabled", Boolean.TRUE));
		
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
        try{
        	return crit.list();
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
        return null;
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserDao#resetSkipAuditInfo()
	 */
	public void resetSkipAuditInfo() {
		super.skipSetAuditInfo=false;
		getCrudDao().setSkipSetAuditInfo(false);
	}

	public void createWorkAreaUserAssoc1(Long userId,Long pdcId) throws PersistenceException {
		SQLQuery q = getHibernateSession().createSQLQuery(UserQuery.getCreateWorkAreaUserAssocQuery1(super.isOracleDialect()));
		q.setLong("userid", userId);
		q.setLong("pdc", pdcId);
		
		q.executeUpdate();
	}
	
	public void createWorkAreaUserAssoc2(Long userId) throws PersistenceException {
		SQLQuery q = getHibernateSession().createSQLQuery(UserQuery.getCreateWorkAreaUserAssocQuery2(super.isOracleDialect()));
		q.setLong("userid", userId);
		
		q.executeUpdate();
	}
	
	public void removeUserFromAccessLists(Long userId) throws PersistenceException {
		SQLQuery q = getHibernateSession().createSQLQuery(UserQuery.getRemoveUserFromIncidentAccessList(userId));
		q.executeUpdate();
		
		q = getHibernateSession().createSQLQuery(UserQuery.getRemoveUserFromIncidentGroupAccessList(userId));
		q.executeUpdate();
		
	}
	
	public Collection<UserVo> getDataStewardUsers() throws PersistenceException {
		Collection<UserVo> vos = new ArrayList<UserVo>();

		String sql = "SELECT id as id, login_name as loginName, first_name as firstName, last_name as lastName "+
				     "FROM isw_user " +
				     "WHERE deleted_date is null "+
				     "AND id in ("+
				     "   select user_id "+
				     "   from isw_user_role "+
				     "   where role_id = (select id from isw_system_role where role_name = 'ROLE_DATA_STEWARD')" +
				     ") ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(UserVo.class);
 	    crt.addProjection("id", "id");
 	    crt.addProjection("loginName", "loginName");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
		
        crt.addScalar("id", Long.class.getName());

		query.setResultTransformer(crt); 
		
		try{
			vos=query.list();
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return vos;
	}
}