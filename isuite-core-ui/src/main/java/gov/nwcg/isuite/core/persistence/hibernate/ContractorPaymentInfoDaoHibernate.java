package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.core.persistence.ContractorPaymentInfoDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


public class ContractorPaymentInfoDaoHibernate extends TransactionSupportImpl implements ContractorPaymentInfoDao {
	private final CrudDao<ContractorPaymentInfo> crudDao;
	
	public ContractorPaymentInfoDaoHibernate(final CrudDao<ContractorPaymentInfo> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ContractorPaymentInfo persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public ContractorPaymentInfo getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ContractorPaymentInfo persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ContractorPaymentInfo> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public ContractorPaymentInfo getById(Long contractorPaymentInfoId) throws PersistenceException {
	Criteria crit = getHibernateSession().createCriteria(ContractorPaymentInfoImpl.class);
		
		crit.add(Restrictions.ne("id", contractorPaymentInfoId));
		crit.add(Restrictions.isNull("deletedDate"));
		
		List<ContractorPaymentInfo> results = crit.list();
		
		if ( results == null || results.size() == 0 ) {
			   return null;
		   }
		return results.get(0);
	}
	
	public Collection<ContractorPaymentInfo> getByVinName(String vinName) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(ContractorPaymentInfoImpl.class);
		
		crit.add(Restrictions.eq("this.vinName",vinName));
		
		List<ContractorPaymentInfo> results = crit.list();

		return results;
	}
	
}