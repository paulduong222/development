package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ContractorNameHistory;
import gov.nwcg.isuite.core.persistence.ContractorNameHistoryDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class ContractorNameHistoryDaoHibernate extends TransactionSupportImpl implements ContractorNameHistoryDao {
	private final CrudDao<ContractorNameHistory> crudDao;
	
	public ContractorNameHistoryDaoHibernate(final CrudDao<ContractorNameHistory> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ContractorNameHistory entity) throws PersistenceException {
		crudDao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ContractorNameHistory getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ContractorNameHistory entity) throws PersistenceException {
		crudDao.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ContractorNameHistory> entities) throws PersistenceException {
		crudDao.saveAll(entities);
	}

}
