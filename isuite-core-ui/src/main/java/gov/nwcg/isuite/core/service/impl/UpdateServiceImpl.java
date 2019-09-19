/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryError;
import gov.nwcg.isuite.core.persistence.EntryDao;
import gov.nwcg.isuite.core.persistence.EntryErrorDao;
import gov.nwcg.isuite.core.service.InputService;
import gov.nwcg.isuite.core.service.InputServiceFactory;
import gov.nwcg.isuite.core.service.JmsPostingService;
import gov.nwcg.isuite.core.service.ProcessQueueResolver;
import gov.nwcg.isuite.core.service.UpdateService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.other.UpdateStatus;
import gov.nwcg.isuite.framework.other.impl.UpdateStatusImpl;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * Implementation of UpdateService.
 * 
 * @author doug
 */
public class UpdateServiceImpl implements UpdateService {
   private static final Logger               LOGGER        = Logger.getLogger(UpdateServiceImpl.class);
   public static final String             FORMAT_STRING = "yyyy.MM.dd 'at' HH:mm:ss";

   private final EntryDao                 entryDao;
   private final EntryErrorDao            entryErrorDao;
   private final JmsPostingService<Entry> inputQ;
   private final ProcessQueueResolver         processQueueResolver;
   private final InputServiceFactory      inputServiceFactory;

   // private final InputServiceFactory inputServiceFactory;

   /**
    * Full Constructor.
    * 
    * @param entryDao
    * @param entryErrorDao
    * @param processQ
    * @param inputQ
    * @param inputServiceFactory
    */
   public UpdateServiceImpl(final EntryDao entryDao, 
		   final EntryErrorDao entryErrorDao,
           final ProcessQueueResolver processQueueResolver, 
           final JmsPostingService<Entry> inputQ,
           final InputServiceFactory inputServiceFactory) {
      if ( entryDao == null ) {
         throw new IllegalArgumentException("entryDao can not be null");
      }
      this.entryDao = entryDao;

      if ( entryErrorDao == null ) {
         throw new IllegalArgumentException("entryErrorDao can not be null");
      }
      this.entryErrorDao = entryErrorDao;

      if ( inputQ == null ) {
         throw new IllegalArgumentException("inputQ can not be null");
      }
      this.inputQ = inputQ;

      if ( processQueueResolver == null ) {
         throw new IllegalArgumentException("processQueueResolver can not be null");
      }
      this.processQueueResolver = processQueueResolver;

      if ( inputServiceFactory == null ) {
         throw new IllegalArgumentException("inputServiceFactory can not be null");
      }
      this.inputServiceFactory = inputServiceFactory;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.UpdateService#importEntry(gov.nwcg.isuite.domain.update.enterprise.Entry)
    */
   public void importEntry(Entry entry) throws ServiceException {
      if ( LOGGER.isInfoEnabled() ) {
         LOGGER.info("import entry: [" + entry + "]");
      }
      InputService inputService = (InputService) inputServiceFactory.getInputService(entry.getUpdateDataType());
      inputService.inputEntry(entry);
      try {
         entry.setUpdateState(UpdateStateEnum.COMPLETED);
         entryDao.save(entry);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.UpdateService#processEntry(gov.nwcg.isuite.domain.update.enterprise.Entry)
    */
   public void processEntry(Entry entry) throws ServiceException {
      try {
         entry.setUpdateState(UpdateStateEnum.PROCESSING);
         if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info("updated:  " + entry);
         }
         entryDao.save(entry);
         inputQ.postMessage(entry);
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
      catch ( Throwable e ) {
         String message = "could not post process message for entry [" + entry + "]";
         LOGGER.error(message, e);
         throw new ServiceException(message, e);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.UpdateService#startUpdateProcess(gov.nwcg.isuite.domain.update.enterprise.Entry)
    */
   public void startUpdateProcess(Entry entry) throws ServiceException {
      try {
         processQueueResolver.getPostingService(entry).postMessage(entry);
      }
      catch ( Throwable t ) {
         String message = "could not post startUpdate message for entry [" + entry + "]";
         LOGGER.error(message, t);
         throw new ServiceException(message, t);
      }
   }

   public UpdateStatus getStatus(Long itemId) throws ServiceException {
      SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_STRING);

      try {
         Entry entry = entryDao.getById(itemId, Entry.class);
         UpdateStateEnum state = entry.getUpdateState();
         StringBuffer explaination = new StringBuffer();
         Calendar date = null;
         EntryError error = null;
         explaination.append(state.toString());
         switch ( state ) {
            case FAILED:
               error = entryErrorDao.getByEntryId(itemId, UpdateStateEnum.FAILED);
               if ( error != null ) {
                  explaination.append(" because ");
                  explaination.append(error.getCause());
                  Calendar errorDate = error.getErrorTimeStamp();
                  if ( errorDate != null ) {
                     explaination.append(" on ");
                     explaination.append(formatter.format(errorDate.getTime()));
                  }
               }
               break;
            case SKIPPED:
               error = entryErrorDao.getByEntryId(itemId, UpdateStateEnum.SKIPPED);
               if ( error != null ) {
                  explaination.append(" because ");
                  explaination.append(error.getCause());
                  Calendar errorDate = error.getErrorTimeStamp();
                  if ( errorDate != null ) {
                     explaination.append(" on ");
                     explaination.append(formatter.format(errorDate.getTime()));
                  }
               }
               break;
            case COMPLETED:
               date = entry.getCompleteTimeStamp();
               if ( date != null ) {
                  explaination.append(" on ");
                  explaination.append(formatter.format(date.getTime()));
               }
               break;
            case INITIAL:
               date = entry.getInitialTimeStamp();
               if ( date != null ) {
                  explaination.append(" on ");
                  explaination.append(formatter.format(date.getTime()));
               }
               break;
            case PROCESSING:
               date = entry.getProcessTimeStamp();
               if ( date != null ) {
                  explaination.append(" on ");
                  explaination.append(formatter.format(date.getTime()));
               }
               break;
            default:
               explaination.append("unknown state [");
               explaination.append(state);
               explaination.append(" ]");
               break;
         }
         return new UpdateStatusImpl(itemId, state, explaination.toString());
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
   }
}
