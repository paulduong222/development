package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.core.persistence.ContractorAgreementNumberHistoryDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class ContractorAgreementNumberHistoryDaoHibernate extends TransactionSupportImpl implements ContractorAgreementNumberHistoryDao {
	private final CrudDao<ContractorAgreementNumberHistory> crudDao;
	
	public ContractorAgreementNumberHistoryDaoHibernate(final CrudDao<ContractorAgreementNumberHistory> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ContractorAgreementNumberHistory entity) throws PersistenceException {
		crudDao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ContractorAgreementNumberHistory getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ContractorAgreementNumberHistory entity) throws PersistenceException {
		crudDao.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ContractorAgreementNumberHistory> entities) throws PersistenceException {
		crudDao.saveAll(entities);
	}

}
