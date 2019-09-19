package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.impl.SysCostRateKindImpl;
import gov.nwcg.isuite.core.persistence.SysCostRateKindDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class SysCostRateKindDaoHibernate extends TransactionSupportImpl implements SysCostRateKindDao{

	private final CrudDao<SysCostRateKind> crudDao;
	
	public SysCostRateKindDaoHibernate(final CrudDao<SysCostRateKind> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateKindDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRateKind getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SysCostRateKindImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateKindDao#save(gov.nwcg.isuite.core.domain.SysCostRateKind)
	 */
	public void save(SysCostRateKind persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateKindDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRateKind> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateKindDao#delete(gov.nwcg.isuite.core.domain.SysCostRateKind)
	 */
	public void delete(SysCostRateKind persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

}