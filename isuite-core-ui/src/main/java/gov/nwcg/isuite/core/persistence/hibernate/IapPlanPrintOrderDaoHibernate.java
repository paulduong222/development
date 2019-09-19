package gov.nwcg.isuite.core.persistence.hibernate;
 

import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;  
import gov.nwcg.isuite.core.domain.impl.IapPlanPrintOrderImpl;
import gov.nwcg.isuite.core.persistence.IapPlanPrintOrderDao;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class IapPlanPrintOrderDaoHibernate extends TransactionSupportImpl implements IapPlanPrintOrderDao {
	private final CrudDao<IapPlanPrintOrder> crudDao;
	
	public IapPlanPrintOrderDaoHibernate(final CrudDao<IapPlanPrintOrder> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	 
	public void merge(IapPlanPrintOrder iapPlanPrintOrder) throws PersistenceException {
		   	super.fixCasing(iapPlanPrintOrder);
			super.getHibernateTemplate().merge(iapPlanPrintOrder);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapPlanPrintOrder persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapPlanPrintOrder getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}
	
	public IapPlanPrintOrder getByKey(Long planId, String formType, Long formId) throws PersistenceException{
		  
			Criteria crit = getHibernateSession().createCriteria(IapPlanPrintOrderImpl.class);
			crit.add(Expression.eq("this.iapPlanId", planId));
			crit.add(Expression.eq("this.formType", formType.trim()));
			crit.add(Expression.eq("this.formId", formId));
			 
			IapPlanPrintOrder entity = (IapPlanPrintOrder)crit.uniqueResult();
			
			return entity;
	 }
	
	 @SuppressWarnings("unchecked")
	   public Collection<IapPlanPrintOrderVo> getPicklist(Long planId) throws PersistenceException {
		   Criteria crit = getHibernateSession().createCriteria(IapPlanPrintOrderImpl.class);
		   crit.add(Restrictions.eq("iapPlanId", planId));
		   Collection<IapPlanPrintOrder> entities = crit.list();

		   if(null != entities){
				try{
					return IapPlanPrintOrderVo.getInstances(entities,true);
				}catch(Exception e){
					throw new PersistenceException(e);
				}
		   }

		   return null;
	   }
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapPlanPrintOrder persistable) throws PersistenceException {
		
		crudDao.setSkipFixCasing(true);
		crudDao.save(persistable);
		crudDao.setSkipFixCasing(false);
		
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapPlanPrintOrder> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	

}
