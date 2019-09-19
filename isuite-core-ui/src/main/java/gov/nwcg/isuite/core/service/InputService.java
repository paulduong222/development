package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.input.InputData;

/**
 * Used to input data into the system.
 * @author doug
 *
 */
public interface InputService extends TransactionService {

   /**
    * Input the data into the system.
    * 
    * @param serializable
    *           data to be input
    * @throws ServiceException
    *            if problems
    */
   public void inputData(InputData data) throws ServiceException;

   /**
    * Retreive the <code>Transferable</code> object.
    * <p>
    * Returns the transferable object associated with the unique identifier, if
    * one exists. If one does not exist, a new placeholder object is created.
    * This new object will have the unique identifier and suitable defaults.
    * </p>
    * 
    * @param uniqueIdentifier
    * @return
    * @throws ServiceException
    */
   public Transferable getByUniqeId(String uniqueIdentifier) throws ServiceException;

   /**
    * Create an entry based on the syncData.
    * 
    * @param inputData
    *           data that will be input into the service
    * @return entry creatd
    * @throws ServiceException
    *            if needed
    */
   public Entry createEntry(InputData inputData) throws ServiceException;

   /**
    * Input the entry into the system.
    * 
    * @param entry
    *           entry containing data to be input
    * @throws ServiceException
    */
   public void inputEntry(Entry entry) throws ServiceException;

}