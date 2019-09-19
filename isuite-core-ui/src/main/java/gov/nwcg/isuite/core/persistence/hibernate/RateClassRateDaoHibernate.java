package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.impl.RateClassRateImpl;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class RateClassRateDaoHibernate extends TransactionSupportImpl implements RateClassRateDao {
	
	private final CrudDao<RateClassRate> crudDao;
	
	public RateClassRateDaoHibernate(final CrudDao<RateClassRate> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(RateClassRate persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RateClassRate getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, RateClassRateImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(RateClassRate persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RateClassRate> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RateClassRateDao#getDuplicateCodeCount(java.lang.String, java.lang.Integer, java.lang.Long)
	 */
	public int getDuplicateCodeCount(String adClass, Integer rateYear, Long excludeId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RateClassRateImpl.class);
		
		crit.add(Expression.eq("rateClassName", adClass.toUpperCase()));
		crit.add(Expression.eq("rateYear", rateYear));
		crit.add(Expression.or(Expression.eq("this.area", "CONUS"), Expression.isNull("this.area")));
		crit.add(Expression.ne("id", excludeId));
		crit.add(Expression.eq("standard", Boolean.TRUE));
		
		return crit.list().size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RateClassRateDao#getPicklist(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<RateClassRateVo> getPicklist(Long rateClassId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RateClassRateImpl.class);
		
		crit.add(Expression.eq("rateClassId", rateClassId));
		crit.add(Expression.or(Expression.eq("this.area", "CONUS"), Expression.isNull("this.area")));
		
		crit.addOrder(Order.asc("this.rateClassName"));
		 
		Collection<RateClassRate> entities = crit.list();
		
		try {
			return RateClassRateVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
	    }
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RateClassRateDao#isTrainingRate()
	 */
	public Boolean isTrainingRate(Long trainingRateId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(RateClassRateImpl.class);
		
		crit.add(Expression.eq("trainingRateClassRateId", trainingRateId));
		
		return crit.list().size() > 0;
	}

}
