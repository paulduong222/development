package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.impl.Sit209Impl;
import gov.nwcg.isuite.core.filter.Sit209Filter;
import gov.nwcg.isuite.core.filter.impl.Sit209FilterImpl;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * This class allows access to the Sit209 table in the database
 * 
 * @author mpoll
 *
 */
public class Sit209DaoHibernate extends TransactionSupportImpl implements Sit209Dao {

   private final CrudDao<Sit209> crudDao;

   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public Sit209DaoHibernate(final CrudDao<Sit209> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.Sit209Dao#getGrid(gov.nwcg.isuite.core.filter.Sit209Filter)
    */
   @SuppressWarnings("unchecked")
   public Collection<Sit209Vo> getGrid(Sit209Filter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(Sit209Impl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               .add(Projections.property("standard"), "standard")
               );

      crit.setResultTransformer(Transformers.aliasToBean(Sit209Vo.class));
      
      if (filter != null) {
         Collection<FilterCriteria> filterCriteria;
         try {
            filterCriteria = Sit209FilterImpl.getFilterCriteria(filter);
            CriteriaBuilder.addCriteria(crit, filterCriteria);
         } catch ( Exception e ) {
            throw new PersistenceException(e);
         }
      }

      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.Sit209Dao#getPicklist()
    */
   @SuppressWarnings("unchecked")
   public Collection<Sit209Vo> getPicklist() throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(Sit209Impl.class);
      
      crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
      
      crit.add(Expression.isNull("i.incidentEndDate"));
      
      crit.addOrder(Order.asc("this.code"));
      
      Collection<Sit209> entities = crit.list();
      
      try {
    	  return Sit209Vo.getInstances(entities, true);
      }	catch(Exception e){
    	  throw new PersistenceException(e);
      }
      
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(Sit209 persistable) throws PersistenceException {
         crudDao.delete(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Sit209 getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, Sit209Impl.class);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(Sit209 persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<Sit209> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.Sit209Dao#isCodeUnique(java.lang.String)
    */
   @Override
   public Boolean isCodeUnique(String code) throws PersistenceException {
      if (code == null || code.length() < 1) {
         throw new PersistenceException("code cannot be null!");
      }
      
      Query sit209Query = getHibernateSession().createQuery(ReferenceDataQuery.IS_SIT_209_CODE_UNIQUE_QUERY);
      sit209Query.setParameter("code", code);
      return ((Long) sit209Query.uniqueResult()).equals(0L);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.Sit209Dao#getDuplicateCodeCount(java.lang.String, java.lang.Long)
    */
   public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(Sit209Impl.class);
	   
	   crit.add(Expression.eq("code", code.toUpperCase()));
	   crit.add(Expression.ne("id", excludeId));
	   crit.add(Expression.eq("standard", Boolean.TRUE));
	   
	   return crit.list().size();
	   
   }
   
}
