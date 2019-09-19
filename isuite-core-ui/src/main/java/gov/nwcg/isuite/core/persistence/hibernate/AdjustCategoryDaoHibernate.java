package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AdjustCategory;
import gov.nwcg.isuite.core.domain.SpecialPay;
import gov.nwcg.isuite.core.domain.impl.AdjustCategoryImpl;
import gov.nwcg.isuite.core.domain.impl.SpecialPayImpl;
import gov.nwcg.isuite.core.filter.AdjustCategoryFilter;
import gov.nwcg.isuite.core.persistence.AdjustCategoryDao;
import gov.nwcg.isuite.core.vo.AdjustCategoryVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

public class AdjustCategoryDaoHibernate extends TransactionSupportImpl implements AdjustCategoryDao {
   private final CrudDao<AdjustCategory> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public AdjustCategoryDaoHibernate(final CrudDao<AdjustCategory> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(AdjustCategory persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @SuppressWarnings("unchecked")
   @Deprecated
   public Collection<AdjustCategory> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException("The getPicklist(Filter) should be called.");   
   }
   
   @SuppressWarnings("unchecked")
   public Collection<AdjustCategoryVo> getPicklist(AdjustCategoryFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(AdjustCategoryImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               .add(Projections.property("adjustmentType"),"adjustmentType")
               );
      
      if (filter != null) {
         if (filter.getCode() != null && !"".equals(filter.getCode()))
         {
            crit.add(Expression.ilike("code", filter.getCode(), MatchMode.START));
         }
      }
      crit.addOrder(Order.asc("id"));
      crit.setResultTransformer(Transformers.aliasToBean(AdjustCategoryVo.class));
      Collection<AdjustCategoryVo> vos = crit.list();
      return vos;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public AdjustCategory getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, AdjustCategoryImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(AdjustCategory persistable) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<AdjustCategory> persistables) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

}
