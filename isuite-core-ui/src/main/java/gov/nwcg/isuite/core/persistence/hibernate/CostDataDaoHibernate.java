package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.persistence.CostDataDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class CostDataDaoHibernate extends TransactionSupportImpl implements CostDataDao {
	private final CrudDao<CostData> crudDao;

	public CostDataDaoHibernate(final CrudDao<CostData> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(CostData persistable) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public CostData getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(CostData persistable) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<CostData> persistables) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}


}
