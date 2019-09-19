package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.domain.impl.IapImpl;
import gov.nwcg.isuite.core.persistence.IapDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

public class IapDaoHibernate extends TransactionSupportImpl implements IapDao {
	private final CrudDao<Iap> crudDao;
	
	public IapDaoHibernate(final CrudDao<Iap> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Iap persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Iap getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Iap persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Iap> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapDao#getAllIapPlans(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Iap> getAllIapPlans(Long incidentId,Long incidentGroupId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapImpl.class);

		if(LongUtility.hasValue(incidentId)){
			crit.add(Expression.eq("incidentId", incidentId));
		}else{
			crit.add(Expression.eq("incidentGroupId", incidentGroupId));
		}

		crit.add(Expression.isNull("iapPlanId"));
		
		crit.addOrder(Order.asc("DFromDate"));
		
		return crit.list();
	}


}
