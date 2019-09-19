package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.impl.PasswordHistoryImpl;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PasswordHistoryDaoHibernate extends TransactionSupportImpl implements PasswordHistoryDao {
	private final CrudDao<PasswordHistory> crudDao;

	public PasswordHistoryDaoHibernate(final CrudDao<PasswordHistory> crudDao) {

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(PasswordHistory entity) throws PersistenceException {
		crudDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public PasswordHistory getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(PasswordHistory entity) throws PersistenceException {
		crudDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<PasswordHistory> entities) throws PersistenceException {
		crudDao.saveAll(entities);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.PasswordHistoryDao#getUserHistory(java.lang.Long)
	 */
	public Collection<PasswordHistory> getUserHistory(Long userId,int historyCount) throws PersistenceException {
		
	   Criteria crit = getHibernateSession().createCriteria(PasswordHistoryImpl.class);
		   
	   crit.add(Restrictions.eq("userId", userId));

	   crit.addOrder(Order.desc("id"));
	   
	   crit.setMaxResults(historyCount);
	   
	   List<PasswordHistory> results = crit.list();
	
		return results;
	}

}
