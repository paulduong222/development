package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.impl.TaskQueueImpl;
import gov.nwcg.isuite.core.persistence.TaskQueueDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;

public class TaskQueueDaoHibernate extends TransactionSupportImpl implements TaskQueueDao {

	private final CrudDao<TaskQueue> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public TaskQueueDaoHibernate(final CrudDao<TaskQueue> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TaskQueue persistable) throws PersistenceException {
	   crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TaskQueue getById(Long id, Class clazz) throws PersistenceException {
	   return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TaskQueue persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<TaskQueue> persistables) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.TaskQueueDao#getTaskQueues()
	 */
	public Collection<TaskQueue> getTaskQueues() throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(TaskQueueImpl.class);	
		crit.add(Expression.isNull("this.status"));
        crit.add(Expression.isNull("this.deactivatedDate"));
        
		return crit.list();
	}

	public void updateStatus(Long id, String status) throws PersistenceException {
		String sql = "UPDATE ISW_TASK_QUEUE SET STATUS='"+status+"' WHERE ID = " + id + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void resetAutoBackup() throws PersistenceException {
		String sql="update isw_task_queue set status=null, frequency='ONETIME' where task_type='SITE_AUTO_DB_BACKUP'";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
