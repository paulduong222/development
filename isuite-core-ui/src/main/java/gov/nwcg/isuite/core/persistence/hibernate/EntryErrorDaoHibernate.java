package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.EntryError;
import gov.nwcg.isuite.core.domain.impl.EntryErrorImpl;
import gov.nwcg.isuite.core.persistence.EntryErrorDao;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author doug
 *
 */
public class EntryErrorDaoHibernate extends TransactionSupportImpl implements EntryErrorDao {

   private static final Logger     LOGGER = Logger.getLogger(EntryDaoHibernate.class);

   private final CrudDao<EntryError> crudDao;
   

   /**
    * Full Constructor.
    * @param crudDao
    */
   public EntryErrorDaoHibernate(final CrudDao<EntryError> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(EntryError persistable) throws PersistenceException {
      crudDao.delete(persistable);
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @Deprecated
   public Collection<EntryError> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public EntryError getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, EntryErrorImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(EntryError persistable) throws PersistenceException {
      if ( LOGGER.isInfoEnabled() ) {
         LOGGER.info("saving [" + persistable + "]");
      }
      crudDao.save(persistable);
   }

   public EntryError getByEntryId(Long entryId, UpdateStateEnum state) throws PersistenceException {
      EntryError result = null;
      List entries = getEntriesByCriteria(entryId, state);
      if (entries != null && entries.size() != 0) {
         result = (EntryError)entries.get(0);
      }
      return result;
   }
   
   List getEntriesByCriteria(Long entryId, UpdateStateEnum state) throws PersistenceException {
      Criteria criteria = getHibernateSession().createCriteria(EntryError.class);
      criteria.setMaxResults(1);
      criteria.add( (Restrictions.eq("entryId", entryId)));
      criteria.add( (Restrictions.eq("entryUpdateStateEnum", state)));
      criteria.addOrder(Order.desc("errorTimeStamp"));
      
      return criteria.list();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<EntryError> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
   
}
