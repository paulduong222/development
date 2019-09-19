package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.KindGroup;
import gov.nwcg.isuite.core.domain.impl.KindGroupImpl;
import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.core.filter.impl.KindGroupFilterImpl;
import gov.nwcg.isuite.core.persistence.KindGroupDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.core.vo.KindGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * This class allows access to the KindGroup table in the database
 * 
 * @author gdyer
 *
 */
public class KindGroupDaoHibernate extends TransactionSupportImpl implements KindGroupDao {

   private final CrudDao<KindGroup> crudDao;

   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public KindGroupDaoHibernate(final CrudDao<KindGroup> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.KindGroupDao#getGrid(gov.nwcg.isuite.core.filter.KindGroupFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<KindGroupVo> getGrid(KindGroupFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(KindGroupImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               .add(Projections.property("standard"), "standard")
               );

      crit.setResultTransformer(Transformers.aliasToBean(KindGroupVo.class));
      
      if (filter != null) {
         Collection<FilterCriteria> filterCriteria;
         try {
            filterCriteria = KindGroupFilterImpl.getFilterCriteria(filter);
            CriteriaBuilder.addCriteria(crit, filterCriteria);
         } catch ( Exception e ) {
            throw new PersistenceException(e);
         }
      }

      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.KindGroupDao#getPicklist()
    */
   @SuppressWarnings("unchecked")
   public Collection<KindGroupVo> getPicklist() throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(KindGroupImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               );
      crit.setResultTransformer(Transformers.aliasToBean(KindGroupVo.class));
      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(KindGroup persistable) throws PersistenceException {
      if (!persistable.isStandard()) {
         crudDao.delete(persistable);
      } else {
         throw new PersistenceException("Cannot delete a standard code.");
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public KindGroup getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, KindGroupImpl.class);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(KindGroup persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<KindGroup> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.KindGroupDao#isCodeUnique(java.lang.String)
    */
   @Override
   public Boolean isCodeUnique(String code) throws PersistenceException {
      if (code == null || code.length() < 1) {
         throw new PersistenceException("code cannot be null!");
      }
      
      Query kindGroupQuery = getHibernateSession().createQuery(ReferenceDataQuery.IS_KIND_GROUP_CODE_UNIQUE_QUERY);
      kindGroupQuery.setParameter("code", code);
      return ((Long) kindGroupQuery.uniqueResult()).equals(0L);
   }

}
