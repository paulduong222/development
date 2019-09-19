package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.core.persistence.SystemRoleDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.xml.SystemRoleType;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SystemRoleDaoHibernate extends TransactionSupportImpl implements SystemRoleDao {

	private final CrudDao<SystemRole> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemRoleDaoHibernate(final CrudDao<SystemRole> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemRole persistable) throws PersistenceException {
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemRole getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SystemRoleImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemRole persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemRole> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemRoleDao#getAllRoles()
	 */
	public Collection<SystemRole> getAllRoles() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemRoleImpl.class);
		
		crit.add(Restrictions.ne("this.roleName", SystemRoleType.ROLE_SYSTEM.name()));
		crit.add(Restrictions.ne("this.roleName", "ROLE_ACCOUNT_ADMINISTRATOR"));//This is needed until we remove account admin from the database. -dbudge
		
		crit.addOrder(Order.asc("id"));
		
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemRoleDao#getGrid()
	 */
	public Collection<SystemRole> getGrid() throws PersistenceException {
		return getAllRoles();
	}
	
}
