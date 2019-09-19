/**
 * 
 */
package gov.nwcg.isuite.framework.core.service;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

/**
 * Represents servicing transferable items.
 * @author doug
 *
 */
public interface TransferableService<T extends Transferable> {

   /**
    * Return the transferable object based on its unique identity.
    * @param uniqueIdentity can't be null
    * @return the transferable object based on its unique identity.
    * @throws ServiceException if needed
    * @throws DuplicateItemException if needed
    */
   T getByUniqueIdentity(String uniqueIdentity) throws ServiceException, DuplicateItemException;
   

}
