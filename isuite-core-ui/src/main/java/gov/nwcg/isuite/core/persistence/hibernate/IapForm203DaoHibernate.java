package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.persistence.IapForm203Dao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.SQLQuery;

public class IapForm203DaoHibernate extends TransactionSupportImpl implements IapForm203Dao {
	private final CrudDao<IapForm203> crudDao;
	
	public IapForm203DaoHibernate(final CrudDao<IapForm203> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapForm203 persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapForm203 getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapForm203 persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapForm203> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public void deleteAllBranches(Long id) throws PersistenceException {
		String sql = "delete from isw_iap_personnel where iap_form_203_id = "+ id + " and is_branch_name = 'Y' ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
	public void deleteAllBranchPositions(Long id) throws PersistenceException {
		String sql = "delete from isw_iap_personnel "+
					 "where iap_form_203_id = "+ id + 
					 " and is_branch_name = 'N' " +
					 " and section = 'BRANCH_SECTION' " ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
		
	}
	
}
