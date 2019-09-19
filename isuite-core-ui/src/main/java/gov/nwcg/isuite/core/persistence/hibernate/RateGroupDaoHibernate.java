package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.RateGroup;
import gov.nwcg.isuite.core.domain.impl.RateGroupImpl;
import gov.nwcg.isuite.core.persistence.RateGroupDao;
import gov.nwcg.isuite.core.vo.RateGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class RateGroupDaoHibernate extends TransactionSupportImpl implements RateGroupDao {

	private final CrudDao<RateGroup> crudDao;
	
	public RateGroupDaoHibernate(final CrudDao<RateGroup> crudDao) {
		
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RateGroupDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<RateGroupVo> getPicklist() throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RateGroupImpl.class);
		
		crit.addOrder(Order.asc("this.code"));
		
		Collection<RateGroup> entities = crit.list();
		
		try {
			return RateGroupVo.getInstances(entities, true);
		}catch(Exception e) {
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(RateGroup persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RateGroup getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, RateGroupImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(RateGroup persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RateGroup> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
