package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.core.domain.impl.CountryCodeImpl;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.persistence.CountryCodeDao;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 * Dao for CountryCodes.
 * 
 * @author doug
 * 
 */
public class CountryCodeDaoHibernate extends TransactionSupportImpl implements CountryCodeDao {

   private final CrudDao<CountryCode> crudDao;

   /**
    * Constructor.
    * 
    * @param crudDao
    *           can't be null
    * @param transferableDao
    *           can't be null
    */
   public CountryCodeDaoHibernate(final CrudDao<CountryCode> crudDao) {
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
   public void delete(CountryCode persistable) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(java.lang.Class)
    */
   @Deprecated
   public Collection<CountryCode> getAll(Class clazz) throws PersistenceException {
      throw new UnsupportedOperationException("The getPicklist(Filter) should be called.");
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @SuppressWarnings("unchecked")
   @Deprecated
   public Collection<CountryCode> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException("The getPicklist(Filter) should be called.");
   }
  
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.CountryCodeDao#getPicklist(gov.nwcg.isuite.domain.access.CountryCodeFilter)
    */
   @SuppressWarnings("unchecked")
   public Collection<CountryCodeVo> getPicklist(CountryCodeFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(CountryCodeImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("name"), "countryName")
               .add(Projections.property("abbreviation"), "countryAbbreviation")
               );
      crit.setResultTransformer(Transformers.aliasToBean(CountryCodeVo.class));
      if ( filter != null ) {
         if ( filter.getCountryCodeName() != null && !"".equals(filter.getCountryCodeName()) ) {
            crit.add(Expression.ilike("name", filter.getCountryCodeName(), MatchMode.START));
         }
         if ( filter.getCountryCodeAbbreviation() != null && !"".equals(filter.getCountryCodeAbbreviation()) ) {
            crit.add(Expression.ilike("abbreviation", filter.getCountryCodeAbbreviation(), MatchMode.START));
         }
      }
      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long,
    *      java.lang.Class)
    */
   public CountryCode getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, CountryCodeImpl.class);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(CountryCode persistable) throws PersistenceException {
      throw new UnsupportedOperationException("This data is only modifiable via script.");
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<CountryCode> persistables) throws PersistenceException {
      throw new UnsupportedOperationException("This data is only modifiable via script.");
   }

}
