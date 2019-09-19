package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.persistence.IapBranchCommSummaryDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public class IapBranchCommSummaryDaoHibernate extends TransactionSupportImpl implements IapBranchCommSummaryDao {
	private final CrudDao<IapBranchCommSummary> crudDao;
	
	public IapBranchCommSummaryDaoHibernate(final CrudDao<IapBranchCommSummary> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapBranchCommSummary persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapBranchCommSummary getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapBranchCommSummary persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapBranchCommSummary> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	

}
