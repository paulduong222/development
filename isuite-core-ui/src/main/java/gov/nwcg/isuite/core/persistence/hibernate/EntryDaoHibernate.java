package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryInfo;
import gov.nwcg.isuite.core.domain.impl.EntryImpl;
import gov.nwcg.isuite.core.persistence.EntryDao;
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
 * Implementation of the CrudDaoHibernate for Entry objects.
 * 
 * @author doug
 */
public class EntryDaoHibernate extends TransactionSupportImpl implements EntryDao {
   private static final Logger     LOGGER = Logger.getLogger(EntryDaoHibernate.class);

   private final CrudDao<Entry> crudDao;

   /**
    * Full Constructor.
    * @param crudDao
    */
   public EntryDaoHibernate(final CrudDao<Entry> crudDao) {
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
   public void delete(Entry persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   public Collection<Entry> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public Entry getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, EntryImpl.class);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(Entry persistable) throws PersistenceException {
      if ( LOGGER.isInfoEnabled() ) {
         LOGGER.info("saving [" + persistable + "]");
      }
      crudDao.save(persistable);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.update.enterprise.EntryDao#getNotCompleted(gov.nwcg.isuite.domain.update.enterprise.EntryInfo)
    */
   public Entry getNext(EntryInfo entryInfo) throws PersistenceException {

      List entries = getEntries(entryInfo);
      Entry result = null;

      if ( entries.size() != 0 ) {
         Entry nextPossible = (Entry) entries.iterator().next();
         if ( nextPossible.getUpdateState() == UpdateStateEnum.INITIAL ) {
            result = nextPossible;
         }
      }
      return result;
      /*
       * select * from Entry where source == entryInfo.getSource() && updateDataType == entryInfo.getType && updateState ==
       * (PROCESS or INITIAL) order by updateState (PROCESS first, then INITIAL) then by updateDate (oldest first) only
       * return the first entry (or null if there are none
       */
      // return crudDao.getById(entryInfo.getDatabaseId(), Entry.class);
   }
   
   List getEntries(EntryInfo entryInfo) throws PersistenceException {
      Criteria criteria = getHibernateSession().createCriteria(Entry.class);
      criteria.setMaxResults(1);
      criteria.add((Restrictions.eq("source.inputSourceEnum", entryInfo.getSource().getInputSourceEnum())));
      criteria.add(Restrictions.or(Restrictions.eq("updateStateEnum", UpdateStateEnum.INITIAL), Restrictions.eq(
               "updateStateEnum", UpdateStateEnum.PROCESSING)));
      criteria.addOrder(Order.desc("updateStateEnum"));
      criteria.addOrder(Order.asc("initialTimeStamp"));
      
      return criteria.list();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<Entry> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

}
