package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryError;
import gov.nwcg.isuite.core.domain.EntryInfo;
import gov.nwcg.isuite.core.domain.impl.EntryErrorImpl;
import gov.nwcg.isuite.core.persistence.EntryDao;
import gov.nwcg.isuite.core.persistence.EntryErrorDao;
import gov.nwcg.isuite.core.service.EntryService;
import gov.nwcg.isuite.core.vo.EntryVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.service.impl.TransactionServiceImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Calendar;
import java.util.Collection;

/**
 * Implentation of the EntryService.
 * @author doug
 */
public class EntryServiceImpl extends TransactionServiceImpl implements EntryService {
//   private static final Log LOGGER = LogFactory.getLog(EntryServiceImpl.class);
   private final EntryDao entryDao;
   private final EntryErrorDao entryErrorDao;

   /**
    * @param entryDao
    */
   public EntryServiceImpl(final EntryDao entryDao, final EntryErrorDao entryErrorDao) {
      if ( entryDao == null ) {
         throw new IllegalArgumentException("entryDao can not be null");
      }
      this.entryDao = entryDao;
      if ( entryErrorDao == null ) {
         throw new IllegalArgumentException("entryErrorDao can not be null");
      }
      this.entryErrorDao = entryErrorDao;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.EntryService#getByTypeAndIdentifier(gov.nwcg.isuite.domain.update.enterprise.EntryInfo)
    */
   public Entry getNextEntry(EntryInfo entryInfo) throws ServiceException {
      try {
         return entryDao.getNext(entryInfo);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.PersistableService#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(Entry persistable) throws ServiceException {
      try {
         entryDao.delete(persistable);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.PersistableService#getAll(gov.nwcg.isuite.domain.Filter)
    */
   public Collection<Entry> getAll(Filter filter) throws ServiceException {
      throw new ServiceException("getAll(Filter) is not supported");
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.PersistableService#getById(java.lang.Long)
    */
   public Entry getById(Long id) throws ServiceException {
      try {
         return entryDao.getById(id, Entry.class);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.PersistableService#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(Entry persistable) throws ServiceException {
      try {
         entryDao.save(persistable);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.update.enterprise.EntryService#registerError(gov.nwcg.isuite.domain.update.enterprise.EntryInfo, java.lang.Throwable)
    */
   public void registerError(EntryInfo entryInfo, Throwable t) throws ServiceException {
      EntryError entryError = createEntryError(entryInfo, t);

      try {
         entryErrorDao.save(entryError);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }

   }

   EntryError createEntryError(EntryInfo entryInfo, Throwable t) throws ServiceException {
      Calendar now = Calendar.getInstance();
      EntryErrorImpl entryError = new EntryErrorImpl();
      entryError.setErrorTimeStamp(now);
      
      // set data from entry info
      entryError.setEntryId(entryInfo.getDatabaseId());
      entryError.setEntryUniqueIdentifier(entryInfo.getIdentifier());
      entryError.setEntryUpdateDataTypeEnum(entryInfo.getType());
      entryError.setEntryInputDataSource(entryInfo.getSource());

      String extraMessage = "";
      try {
         Entry entry = entryDao.getById(entryInfo.getDatabaseId(), Entry.class);
         entryError.setEntryData(entry.getData());
         entryError.setEntryUpdateStateEnum(entry.getUpdateState());
      }
      catch ( PersistenceException e ) {
         extraMessage = " and could not retrieve entry for id: " + entryInfo.getDatabaseId();
//         LOGGER.error(extraMessage, e);
      }

      String causeMessage = null;
      // now set entry specific data
      if ( t.getCause() != null ) {
         causeMessage = t.getCause().getMessage();
      }
      else {
         causeMessage = t.getMessage();
      }
      entryError.setCause(causeMessage + extraMessage);

      return entryError;
   }

   /**
    * Getter for testing purposes only
    * @return the entryDao
    */
   final EntryDao getEntryDao() {
      return entryDao;
   }

   public Collection<EntryVo> dosToVos(Collection<Entry> theDos) {
      throw new UnsupportedOperationException("not worth my time yet.");
   }

   public Entry voToDo(EntryVo theVo, Entry theDo) {
      throw new UnsupportedOperationException("not worth my time yet.");
   }

   public Collection<Entry> vosToDos(Collection<EntryVo> theVos) {
      throw new UnsupportedOperationException("not worth my time yet.");
   }

   public Entry setAuditInfo(Entry theDo) throws ServiceException {
      throw new UnsupportedOperationException("not worth my time yet.");
   }

}