package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.core.domain.impl.SystemRolePermImpl;
import gov.nwcg.isuite.core.persistence.SystemRolePermDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SystemRolePermDaoHibernate extends TransactionSupportImpl implements SystemRolePermDao {

	private final CrudDao<SystemRolePerm> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemRolePermDaoHibernate(final CrudDao<SystemRolePerm> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemRolePerm persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemRolePerm getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SystemRolePermImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemRolePerm persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemRolePerm> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	@Override
	public Collection<SystemRolePerm> getGrid() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemRolePermImpl.class);
		crit.addOrder(Order.asc("id"));
		return crit.list();
	}

		@Override
	public SystemRolePerm getByModulePermId(Long id) throws PersistenceException {
			Query q = getHibernateSession().createQuery("SELECT model FROM SystemRolePermImpl model " +
			"WHERE model.systemModulePermId = :id");

			q.setParameter("id", id);

			Collection<SystemRolePerm> entities = q.list();

			if( (null != entities) && (entities.size()>0) ){
				return (SystemRolePerm)entities.iterator().next();
			}
			return null;
	}

	@Override
	public SystemRolePerm getByRoleId(Long id) throws PersistenceException {
		Query q = getHibernateSession().createQuery("SELECT model FROM SystemRolePermImpl model " +
		"WHERE model.systemRoleId = :id");

		q.setParameter("id", id);

		Collection<SystemRolePerm> entities = q.list();

		if( (null != entities) && (entities.size()>0) ){
			return (SystemRolePerm)entities.iterator().next();
		}
		return null;
	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.SystemRolePermDao#getBySystemRoles(java.util.Collection)
    */
   @SuppressWarnings("unchecked")
   public Collection<String> getBySystemRoles(Collection<SystemRole> roles) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(SystemRolePermImpl.class);
      crit.createAlias("systemModulePerm", "smp");
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("smp.permissionKey"))
               );
      crit.add(Restrictions.in("systemRole", roles));
      return crit.list();
   }

	

}
