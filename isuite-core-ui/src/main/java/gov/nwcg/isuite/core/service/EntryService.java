/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryInfo;
import gov.nwcg.isuite.core.vo.EntryVo;
import gov.nwcg.isuite.framework.core.service.PersistableService;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

/**
 * Service for management of <code>Entry</code> items.
 * @author doug
 */
public interface EntryService extends TransactionService, PersistableService<Entry, EntryVo> {

   /**
    * Return next entry to process.
    * <p>
    * If there is an entry of the given <code>UpdateDataTypeEnum</code> and from the specified <code>source</code>
    * with the <code>UpdateStateEnum.PROCESSING</code>, then there is not a <i>next</i> entry to process and the
    * this method returns <code>null</code>.
    * </p>
    * <p>
    * Otherwise, the next entry is the oldest entry of the given <code>UpdateDataTypeEnum</code> from the specified
    * <code>source</code> that is in the <code>UpdateStateEnum.INITIAL</code>.
    * </p>
    * 
    * @param EntryInfo
    *           describes the data to be retrieved unique identifer of that data type
    * @return next entry to process, may be null
    * @throws ServiceException
    *            if needed
    */
   Entry getNextEntry(EntryInfo entryInfo) throws ServiceException;


   /**
    * Indicate that there was an error when dealing with the specified Entry.
    * @param entryInfo which entry was the cause of the error
    * @param t the error
    * @throws ServiceException
    */
   void registerError(EntryInfo entryInfo, Throwable t) throws ServiceException;
   
   public Entry getById(Long id) throws ServiceException;
}
