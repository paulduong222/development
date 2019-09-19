package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelImpl;
import gov.nwcg.isuite.core.persistence.IapPersonnelDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class IapPersonnelDaoHibernate extends TransactionSupportImpl implements IapPersonnelDao {
	private final CrudDao<IapPersonnel> crudDao;
	
	public IapPersonnelDaoHibernate(final CrudDao<IapPersonnel> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapPersonnel persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapPersonnel getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapPersonnel persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapPersonnel> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	public IapPersonnel getBranchByName(Long iapForm203Id, String name,Long excludeId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IapPersonnelImpl.class);
		
		crit.add(Restrictions.eq("iapForm203Id", iapForm203Id));
		crit.add(Restrictions.eq("name", name));
		
		if(LongUtility.hasValue(excludeId)){
			crit.add(Restrictions.ne("id", excludeId));
		}
		
		IapPersonnel entity = (IapPersonnel)crit.uniqueResult();
		
		return entity;
	}

}
