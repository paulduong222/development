package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.persistence.UserSessionLogDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

public class UserSessionLogDaoHibernate extends TransactionSupportImpl implements UserSessionLogDao {
	private final CrudDao<UserSessionLog> crudDao;
	private static final Logger LOG = Logger.getLogger(UserSessionLogDaoHibernate.class);

	public UserSessionLogDaoHibernate(final CrudDao<UserSessionLog> crudDao) {
		if ( crudDao == null ) {
			LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * \(non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public UserSessionLog getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(UserSessionLog persistable) throws PersistenceException {
		//crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(UserSessionLog persistable) throws PersistenceException {
		if(null != persistable.getId() && persistable.getId()>0)
			   super.getHibernateSession().merge(persistable);
		   else
			   crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<UserSessionLog> persistables)
			throws PersistenceException {
		// TODO : Auto-generated method stub
	}
	
	public int getUserLoginActivityCount(Long userId) throws PersistenceException {
		int cnt=0;
		
		String sql = "select count(sl.id) " +
					 "from isw_user_session_log sl " +
					 "     , isw_user_session_log_activity sla " +
					 "where sl.user_id = " + userId + " " +
					 "and sla.user_session_log_id = sl.id " +
					 "and sla.action_type = 'LOGIN'";	
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		Object val = q.uniqueResult();
		try{
			if(null != val)
				cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){
		}

		return cnt;
	}
}
