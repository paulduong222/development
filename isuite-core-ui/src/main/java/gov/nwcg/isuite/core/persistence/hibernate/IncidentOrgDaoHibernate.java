package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.persistence.IncidentOrgDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class IncidentOrgDaoHibernate extends TransactionSupportImpl implements IncidentOrgDao {
	private final CrudDao<IncidentOrg> crudDao;
	
	public IncidentOrgDaoHibernate(final CrudDao<IncidentOrg> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentOrg persistable) throws PersistenceException {
		crudDao.delete(persistable);

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentOrg getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentOrg persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentOrg> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
