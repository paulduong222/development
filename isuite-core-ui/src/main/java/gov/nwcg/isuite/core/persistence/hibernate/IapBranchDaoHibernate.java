package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class IapBranchDaoHibernate extends TransactionSupportImpl implements IapBranchDao {
	private final CrudDao<IapBranch> crudDao;
	
	public IapBranchDaoHibernate(final CrudDao<IapBranch> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapBranch persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapBranch getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapBranch persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapBranch> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public IapBranch getByBranchDivision(String branch, String division, Long planId, Long excludeId) throws PersistenceException {
		IapBranch entity = null;
		
		Criteria crit = getHibernateSession().createCriteria(IapBranchImpl.class);
		
		crit.add(Restrictions.eq("branchName", branch));
		crit.add(Restrictions.eq("divisionName", division));
		crit.add(Restrictions.eq("iapPlanId", planId));
		
		if(LongUtility.hasValue(excludeId))
			crit.add(Restrictions.ne("id", excludeId));
		
		entity = (IapBranch)crit.uniqueResult();
		
		return entity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapBranchDao#getByPlanId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapBranch> getByPlanId(Long planId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapBranchImpl.class);
		
		crit.add(Restrictions.eq("iapPlanId", planId));
		
		return crit.list();
	}
	
	
}
