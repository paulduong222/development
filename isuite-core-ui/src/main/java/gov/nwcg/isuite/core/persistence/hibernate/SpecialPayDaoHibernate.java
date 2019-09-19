package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SpecialPay;
import gov.nwcg.isuite.core.domain.impl.SpecialPayImpl;
import gov.nwcg.isuite.core.filter.SpecialPayFilter;
import gov.nwcg.isuite.core.persistence.SpecialPayDao;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

public class SpecialPayDaoHibernate extends TransactionSupportImpl implements SpecialPayDao {
   private final CrudDao<SpecialPay> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public SpecialPayDaoHibernate(final CrudDao<SpecialPay> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(SpecialPay persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @SuppressWarnings("unchecked")
   @Deprecated
   public Collection<SpecialPay> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException("The getPicklist(Filter) should be called.");   
   }
   
   @SuppressWarnings("unchecked")
   public Collection<SpecialPayVo> getPicklist(SpecialPayFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(SpecialPayImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               .add(Projections.property("availableToFed"), "availableToFed")
               .add(Projections.property("availableToAd"), "availableToAd")
               .add(Projections.property("availableToOther"), "availableToOther")
               .add(Projections.property("ordinalValue"), "ordinalValue")
               );
      
      if (filter != null) {
         if (filter.getCode() != null && !"".equals(filter.getCode()))
         {
            crit.add(Expression.ilike("code", filter.getCode(), MatchMode.START));
         }
      }
      crit.addOrder(Order.asc("ordinalValue"));
//      crit.addOrder(Order.asc("id"));
      crit.setResultTransformer(Transformers.aliasToBean(SpecialPayVo.class));
      Collection<SpecialPayVo> vos = crit.list();
      
      Collection<SpecialPayVo> vos2 = new ArrayList<SpecialPayVo>();
      for(SpecialPayVo vo : vos){
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToAd())){
			   vo.setAvailableToFedAd(true);
		   }else
			   vo.setAvailableToFedAd(false);
		   
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToFedOther(true);
		   }else
			   vo.setAvailableToFedOther(false);
		   
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToAd())
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToFedAdOther(true);
		   }else
			   vo.setAvailableToFedAdOther(false);

		   if(BooleanUtility.isTrue(vo.getAvailableToAd()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToAdOther(true);
		   }else
			   vo.setAvailableToAdOther(false);
		   
		   vos2.add(vo);
      }
      
      return vos2;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public SpecialPay getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, SpecialPayImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(SpecialPay persistable) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<SpecialPay> persistables) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

}
