/**
 * 
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.EntryError;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.util.Collection;

/**
 * @author doug
 *
 */
public interface EntryErrorDao extends CrudDao<EntryError> {
   
   /**
    * Returns the most recent EntryError with the entryId and state.
    * @param entryId id of entry (not entryError)
    * @param state state of entry
    * @return most recent EntryError with the entryId and state
    * @throws PersistenceException if needed
    */
   public EntryError getByEntryId(Long entryId, UpdateStateEnum state) throws PersistenceException;

   @Deprecated
   public Collection<EntryError> getAll(Filter filter) throws PersistenceException;
}
