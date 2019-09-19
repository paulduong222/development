package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.core.domain.impl.SystemNotifyUserImpl;
import gov.nwcg.isuite.core.persistence.SystemNotifyUserDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class SystemNotifyUserDaoHibernate extends TransactionSupportImpl implements SystemNotifyUserDao {

	private final CrudDao<SystemNotifyUser> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemNotifyUserDaoHibernate(final CrudDao<SystemNotifyUser> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemNotifyUser persistable) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemNotifyUser getById(Long id, Class clazz) throws PersistenceException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemNotifyUser persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemNotifyUser> persistables) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemNotifyUserDao#getBySystemNotificationId(java.lang.Long)
	 */
	public Collection<SystemNotifyUser> getBySystemNotificationId(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemNotifyUserImpl.class);

		crit.createAlias("this.systemNotification", "sn");
		
		crit.add(Restrictions.eq("sn.id", id));
		
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
       	return crit.list();
	}



}
