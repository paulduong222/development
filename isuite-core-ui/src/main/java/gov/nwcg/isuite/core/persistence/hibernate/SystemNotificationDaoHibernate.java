package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.impl.SystemNotificationImpl;
import gov.nwcg.isuite.core.persistence.SystemNotificationDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class SystemNotificationDaoHibernate extends TransactionSupportImpl implements SystemNotificationDao {

	private final CrudDao<SystemNotification> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemNotificationDaoHibernate(final CrudDao<SystemNotification> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemNotification persistable) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemNotification getById(Long id, Class clazz) throws PersistenceException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemNotification persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemNotification> persistables) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemNotificationDao#getByTaskQueueId(java.lang.Long)
	 */
	public Collection<SystemNotification> getByTaskQueueId(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemNotificationImpl.class);

		crit.createAlias("this.taskQueues", "tq");
		
		crit.add(Restrictions.eq("tq.id", id));
		
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
       	return crit.list();
	}



}
