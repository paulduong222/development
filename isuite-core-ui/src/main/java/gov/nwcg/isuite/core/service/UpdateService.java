package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.other.UpdateStatus;

/**
 * Handles updates.
 * 
 * @author doug
 */
public interface UpdateService extends TransactionService {

   /**
    * Start the update process with on this entry.
    * <p>
    * This should do the following:
    * <ol>
    * <li>save the entry</li>
    * <li>post a message to process entries of this type and unique identifier</li>
    * </ol>
    * </p>
    * <p>
    * called by processes (web service) that has data to input
    * </p>
    * 
    * @param entry
    *           data to be updated
    * @throws ServiceException
    *            if needed
    */
   public void startUpdateProcess(Entry entry) throws ServiceException;

   /**
    * Start the actual processing of this entry.
    * <p>
    * This should do the following:
    * <ol>
    * <li>update that entry to the <code>PROCESSING</code> state </li>
    * <li>save the entry </li>
    * <li>post a message for that entry to be processed</li>
    * </ol>
    * </p>
    * <p>called by the message handler to start processing of a specific entry</p>
    * 
    * @param entry
    *           entry to be processed
    * @throws ServiceException
    *            if needed
    */
   public void processEntry(Entry entry) throws ServiceException;

   /**
    * Import the data from this entry into the system.
    * <p>
    * This should do the following:
    * <ol>
    * <li>extract the data from the entry</li>
    * <li>import that data into the system</li>
    * <li>update the entry to the <code>COMPLETED</code> state</li>
    * <li>post a message to process the next available entry of this type and unique identifier</li>
    * </ol>
    * </p>
    * <p>called by message handlers to process an entry</p>
    * 
    * @param entry
    * @throws ServiceException
    */
   public void importEntry(Entry entry) throws ServiceException;

   
   /**
    * Get the status of an item based on the id of the item.
    * @param itemId id of item
    * @return status of item.
    * @throws ServiceException
    */
   public UpdateStatus getStatus(Long itemId) throws ServiceException;
}
