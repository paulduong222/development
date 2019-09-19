package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.core.persistence.TaskQueueLogDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public class TaskQueueLogDaoHibernate extends TransactionSupportImpl implements TaskQueueLogDao {

	private final CrudDao<TaskQueueLog> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public TaskQueueLogDaoHibernate(final CrudDao<TaskQueueLog> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TaskQueueLog persistable) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TaskQueueLog getById(Long id, Class clazz) throws PersistenceException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TaskQueueLog persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<TaskQueueLog> persistables) throws PersistenceException {
		
	}



}
