package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.filter.AgencyGroupFilter;
import gov.nwcg.isuite.core.filter.impl.AgencyGroupFilterImpl;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
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
 * This class allows access to the AgencyGroup table in the database
 */
public class AgencyGroupDaoHibernate extends TransactionSupportImpl implements AgencyGroupDao {

   private final CrudDao<AgencyGroup> crudDao;

   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public AgencyGroupDaoHibernate(final CrudDao<AgencyGroup> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AgencyGroupDao#getGrid(gov.nwcg.isuite.core.filter.AgencyGroupFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<AgencyGroupVo> getGrid(AgencyGroupFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(AgencyGroupImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               .add(Projections.property("standard"), "standard")
               );

      crit.setResultTransformer(Transformers.aliasToBean(AgencyGroupVo.class));
      
      if (filter != null) {
         Collection<FilterCriteria> filterCriteria;
         try {
            filterCriteria = AgencyGroupFilterImpl.getFilterCriteria(filter);
            CriteriaBuilder.addCriteria(crit, filterCriteria);
         } catch ( Exception e ) {
            throw new PersistenceException(e);
         }
      }

      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AgencyGroupDao#getPicklist()
    */
   @SuppressWarnings("unchecked")
   public Collection<AgencyGroupVo> getPicklist() throws PersistenceException {
	   
      Criteria crit = getHibernateSession().createCriteria(AgencyGroupImpl.class);
      
      crit.createAlias("this.incident", "i", CriteriaSpecification.LEFT_JOIN);
      
      crit.add(Expression.isNull("i.incidentEndDate"));
      crit.addOrder(Order.asc("this.code"));
     
      Collection<AgencyGroup> entities = crit.list();
      
      try {
    	  return AgencyGroupVo.getInstances(entities, true);
      } catch(Exception e) {
    	  throw new PersistenceException(e);
      }
      
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(AgencyGroup persistable) throws PersistenceException {
	   crudDao.delete(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public AgencyGroup getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, AgencyGroupImpl.class);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(AgencyGroup persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<AgencyGroup> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AgencyGroupDao#isCodeUnique(java.lang.String)
    */
   @Override
   public Boolean isCodeUnique(String code) throws PersistenceException {
      if (code == null || code.length() < 1) {
         throw new PersistenceException("code cannot be null!");
      }
      
      Query agencyGroupQuery = getHibernateSession().createQuery(ReferenceDataQuery.IS_AGENCY_GROUP_CODE_UNIQUE_QUERY);
      agencyGroupQuery.setParameter("code", code);
      return ((Long) agencyGroupQuery.uniqueResult()).equals(0L);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.AgencyGroupDao#getDuplicateCodeCount(java.lang.String, java.lang.Long)
    */
   public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(AgencyGroupImpl.class);
	   
	   crit.add(Expression.eq("code", code.toUpperCase()));
	   crit.add(Expression.ne("id", excludeId));
	   crit.add(Expression.eq("standard", Boolean.TRUE));
	   
	   return crit.list().size();
   }

}
