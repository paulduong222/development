package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.core.domain.impl.UserNotificationImpl;
import gov.nwcg.isuite.core.persistence.UserNotificationDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserNotificationQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Calendar;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class UserNotificationDaoHibernate extends TransactionSupportImpl implements UserNotificationDao {
	private final CrudDao<UserNotification> crudDao;

	public UserNotificationDaoHibernate(final CrudDao<UserNotification> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserNotificationDao#getUnreadNotifications(java.lang.Long)
	 */
	public Collection<UserNotification> getUnreadNotifications(Long userId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(UserNotificationImpl.class);
		
		crit.add(Expression.eq("this.userId", userId));
		crit.add(Restrictions.disjunction()
					.add(Expression.eq("this.readFlag",Boolean.FALSE))
					.add(Expression.isNull("this.readFlag"))
				);
		
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(UserNotification persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public UserNotification getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(UserNotification persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<UserNotification> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.UserNotificationDao#updateReadFlag(java.lang.Long)
	 */
	public void updateReadFlag(Long userId) throws PersistenceException{
	   Query q = getHibernateSession().getNamedQuery(UserNotificationQuery.UPDATE_READFLAG);
	   q.setParameter("flag",Boolean.TRUE);
	   q.setParameter("userid", userId);
	   q.setParameter("readdate",Calendar.getInstance().getTime());
	   q.executeUpdate();
	}

}
