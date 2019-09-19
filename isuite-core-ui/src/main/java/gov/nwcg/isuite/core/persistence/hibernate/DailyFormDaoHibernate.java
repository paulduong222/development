package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;

import gov.nwcg.isuite.core.domain.DailyForm;
import gov.nwcg.isuite.core.domain.impl.DailyFormImpl;
import gov.nwcg.isuite.core.persistence.DailyFormDao;
import gov.nwcg.isuite.core.vo.DailyFormVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class DailyFormDaoHibernate extends TransactionSupportImpl implements DailyFormDao {
	
	private final CrudDao<DailyForm> crudDao;
	
	public DailyFormDaoHibernate(final CrudDao<DailyForm> crudDao) {
		
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.DailyFormDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<DailyFormVo> getPicklist() throws PersistenceException {
	
		Criteria crit = getHibernateSession().createCriteria(DailyFormImpl.class);
		
		Collection<DailyForm> entities = crit.list();
		
		try {
			return DailyFormVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(DailyForm persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public DailyForm getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, DailyFormImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(DailyForm persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<DailyForm> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
