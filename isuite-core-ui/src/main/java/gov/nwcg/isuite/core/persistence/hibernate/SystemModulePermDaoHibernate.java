package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.SystemModulePermImpl;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.persistence.SystemModulePermDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SystemParameterQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

public class SystemModulePermDaoHibernate extends TransactionSupportImpl implements SystemModulePermDao {

	private final CrudDao<SystemModulePerm> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao can't be null
	 */
	public SystemModulePermDaoHibernate(final CrudDao<SystemModulePerm> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(SystemModulePerm persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SystemModulePerm getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SystemModulePermImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(SystemModulePerm persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SystemModulePerm> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	@Override
	public SystemModulePerm getByModuleId(Long id)	throws PersistenceException {
		Query q = getHibernateSession().createQuery("SELECT model FROM SystemModulePermImpl model " +
		"WHERE model.systemModuleId = :id");

		q.setParameter("id", id);

		Collection<SystemModulePerm> entities = q.list();

		if( (null != entities) && (entities.size()>0) ){
			return (SystemModulePerm)entities.iterator().next();
		}
		return null;
	}

	@Override
	public Collection<SystemModulePerm> getGrid() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemModulePermImpl.class);
		crit.addOrder(Order.asc("id"));
		return crit.list();
	}

	

}
