package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemModule;
import gov.nwcg.isuite.core.domain.impl.SystemModuleImpl;
import gov.nwcg.isuite.core.persistence.SystemModuleDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;

public class SystemModuleDaoHibernate extends TransactionSupportImpl implements SystemModuleDao {

	private final CrudDao<SystemModule> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemModuleDaoHibernate(final CrudDao<SystemModule> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemModuleDao#getAll()
	 */
	public Collection<SystemModule> getAll() throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(SystemModuleImpl.class);
	   
	   return crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemModule persistable) throws PersistenceException {

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemModule getById(Long id, Class clazz) throws PersistenceException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemModule persistable) throws PersistenceException {

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemModule> persistables) throws PersistenceException {

	}


}
