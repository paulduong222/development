package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.impl.ComplexityImpl;
import gov.nwcg.isuite.core.persistence.ComplexityDao;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class ComplexityDaoHibernate extends TransactionSupportImpl implements
		ComplexityDao {
	
	private final CrudDao<Complexity> crudDao;

	public ComplexityDaoHibernate(final CrudDao<Complexity> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
	    this.crudDao = crudDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ComplexityVo> getPicklist() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ComplexityImpl.class);
		crit.addOrder(Order.asc("id"));
		Collection<Complexity> entities = crit.list();
		
		 try{
			 return ComplexityVo.getInstances(entities, true);
		 }catch (Exception e) {
			 throw new PersistenceException(e);
		 }
	}

	@Override
	public void delete(Complexity persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public Complexity getById(Long id, Class<?> clazz)
			throws PersistenceException {
		
		return crudDao.getById(id, ComplexityImpl.class);
	}

	@Override
	public void save(Complexity persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void saveAll(Collection<Complexity> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
