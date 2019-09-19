package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.persistence.CountryCodeSubdivisionDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * Dao for CountryCodeSubdivision (aka states).
 * @author doug
 */
public class CountryCodeSubdivisionDaoHibernate extends TransactionSupportImpl implements CountryCodeSubdivisionDao {
//	private static final Log LOG = LogFactory.getLog(CountryCodeSubdivisionDaoHibernate.class);
   
   private final CrudDao<CountrySubdivision>         crudDao;

   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public CountryCodeSubdivisionDaoHibernate(final CrudDao<CountrySubdivision> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(CountrySubdivision persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @SuppressWarnings("unchecked")
   @Deprecated
   public Collection<CountrySubdivision> getAll(Filter filter) throws PersistenceException {
//      Criteria crit = getHibernateSession().createCriteria(CountrySubdivisionImpl.class);
//      setWhere((CountrySubdivisionFilter)filter, crit);
//      return crit.list();
      throw new UnsupportedOperationException("The getPicklist(CountrySubdivisionFilter) should be called.");
   }
   
   
   @SuppressWarnings("unchecked")
   public Collection<CountryCodeSubdivisionVo> getPicklist(CountrySubdivisionFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(CountrySubdivisionImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("name"), "countrySubName")
               .add(Projections.property("abbreviation"), "countrySubAbbreviation")
               .add(Projections.property("countryCodeId"), "countryCdId")
               );
      setWhere(filter, crit);
      crit.setResultTransformer(Transformers.aliasToBean(CountryCodeSubdivisionVo.class));
      return crit.list();
   }
   
   @SuppressWarnings("unchecked")
   public Collection<CountryCodeSubdivisionVo> getGrid(CountrySubdivisionFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(CountrySubdivisionImpl.class);
      crit.setFetchMode("countryCode",FetchMode.JOIN);
      setWhere(filter, crit);
      
      Collection<CountrySubdivisionImpl> temp = crit.list();
      
      try
      {
    	  return CountryCodeSubdivisionVo.getInstances(temp, true); 
      }
      catch (Exception e)
      {
    	  throw new PersistenceException(e);
      }
   }
   
   
   
   private void setWhere(CountrySubdivisionFilter f, Criteria crit) {
      if (f != null) {
         if (f.getCountryCodeId() != null) {
            crit.add(Expression.eq("countryCodeId", f.getCountryCodeId()));
         }
         if (f.getCountrySubdivisionCode() != null && !"".equals(f.getCountrySubdivisionCode())) {
            crit.add(Expression.ilike("abbreviation", f.getCountrySubdivisionCode(), MatchMode.ANYWHERE));
         }
         if (f.getCountrySubdivisionName() != null && !"".equals(f.getCountrySubdivisionName())) {
            crit.add(Expression.ilike("name", f.getCountrySubdivisionName(), MatchMode.ANYWHERE));
         }
         if (f.getCountryAbbreviation() != null && !"".equals(f.getCountryAbbreviation()))
	     {
	        crit.add(Expression.ilike("countryCode.abbreviation", f.getCountryAbbreviation(), MatchMode.ANYWHERE));
	     }
      }
      crit.addOrder(Order.asc("abbreviation"));
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long,
    *      java.lang.Class)
    */
   public CountrySubdivision getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, CountrySubdivisionImpl.class);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(CountrySubdivision persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<CountrySubdivision> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.CountrySubdivisionDao#isCodeUnique(java.lang.String)
    */
   @Override
   public Boolean isCodeUnique(String abbrevation) throws PersistenceException {
      if (abbrevation == null || abbrevation.length() < 1) {
         throw new PersistenceException("abbrevation cannot be null!");
      }
      
      Query countrySubdivisionQuery = getHibernateSession().createQuery(ReferenceDataQuery.IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE_QUERY);
      countrySubdivisionQuery.setParameter("abbreviation", abbrevation);
      return ((Long) countrySubdivisionQuery.uniqueResult()).equals(0L);
     //return true;
   }

   
}
