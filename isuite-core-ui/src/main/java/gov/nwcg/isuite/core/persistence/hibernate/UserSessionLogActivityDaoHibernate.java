package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.core.persistence.UserSessionLogActivityDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;

public class UserSessionLogActivityDaoHibernate extends TransactionSupportImpl implements UserSessionLogActivityDao {
	private final CrudDao<UserSessionLogActivity> crudDao;
	private static final Logger LOG = Logger.getLogger(UserSessionLogActivityDaoHibernate.class);

	public UserSessionLogActivityDaoHibernate(final CrudDao<UserSessionLogActivity> crudDao) {
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
	public UserSessionLogActivity getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(UserSessionLogActivity persistable) throws PersistenceException {
		//crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(UserSessionLogActivity persistable) throws PersistenceException {
		if(null != persistable.getId() && persistable.getId()>0)
			   super.getHibernateSession().merge(persistable);
		   else
			   crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<UserSessionLogActivity> persistables) throws PersistenceException {
		// TODO : Auto-generated method stub
	}
}
