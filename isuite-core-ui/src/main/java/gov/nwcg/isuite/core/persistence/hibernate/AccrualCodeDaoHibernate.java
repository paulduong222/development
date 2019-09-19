package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.persistence.AccrualCodeDao;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class AccrualCodeDaoHibernate extends TransactionSupportImpl implements AccrualCodeDao {
	private final CrudDao<AccrualCode> crudDao;

	/*
	 * Constructor
	 */
	public AccrualCodeDaoHibernate(final CrudDao<AccrualCode> crudDao) {

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(AccrualCode persistable) throws PersistenceException {
		crudDao.save(persistable);
	}   

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<AccrualCode> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
	 */
	public void delete(AccrualCode persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}   

	public Collection<AccrualCode> getAll() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(KindImpl.class);
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public AccrualCode getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, AccrualCodeImpl.class);
	}   

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.AccrualCodeDao#getPicklist()
	 */
	public Collection<AccrualCodeVo> getPicklist() throws PersistenceException {
	      Criteria crit = getHibernateSession().createCriteria(AccrualCodeImpl.class);
	      
	      crit.addOrder(Order.asc("this.code"));
	      Collection<AccrualCode> entities = crit.list();
	      
	      try{
	    	  return AccrualCodeVo.getInstances(entities, true);
	      }catch(Exception e){
	    	  throw new PersistenceException(e);
	      }
	}
}
