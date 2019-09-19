package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;

import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.domain.impl.FuelTypeImpl;
import gov.nwcg.isuite.core.persistence.FuelTypeDao;
import gov.nwcg.isuite.core.vo.FuelTypeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class FuelTypeDaoHibernate extends TransactionSupportImpl implements
		FuelTypeDao {
	
	private final CrudDao<FuelType> crudDao;
	
	public FuelTypeDaoHibernate(final CrudDao<FuelType> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(FuelType persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public FuelType getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(FuelType persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<FuelType> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FuelTypeVo> getFuelTypes() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(FuelTypeImpl.class);
		
		Collection<FuelType> entities = crit.list();
		
		try {
			return FuelTypeVo.getInstances(entities, true);
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

}
