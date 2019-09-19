/**
 * 
 */
package gov.nwcg.isuite.framework.core.persistence;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 * @author doug
 *
 */
public interface TransferableDao<T extends Transferable> {

   /**
    * Return the transferable object based on its unique identity.
    * @param uniqueIdentity can't be null
 * @param clazz TODO
    * @return the transferable object based on its unique identity.
    * @throws PersistenceException if needed
    * @throws DuplicateItemException if needed
    */
   T getByUniqueIdentity(String uniqueIdentity, Class<?> clazz) throws PersistenceException, DuplicateItemException;
   
}
