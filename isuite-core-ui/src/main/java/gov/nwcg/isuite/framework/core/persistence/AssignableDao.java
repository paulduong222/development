/**
 * 
 */
package gov.nwcg.isuite.framework.core.persistence;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.core.domain.Assignable;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * Dao for items that can be assigned to an organization
 * @author doug
 *
 */
public interface AssignableDao<A extends Assignable> {


   /**
    * Retrieve all objects that are accessible by the specified workarea.
    * 
    * @param workAreaId
    *           workarea can't be null
    * @param filter
    *           filter parameters, can't be null
    * @return all objects that are accessible by the specified workarea, may be
    *         empty but won't be null
    * @throws PersistenceException
    *            if needed
    */
   public Collection<A> getByWorkAreaId(Long workAreaId, Filter filter) throws PersistenceException;

   /**
    * Retrieve all objects that are accessable by any of the specified
    * workareas.
    * 
    * @param workareas
    *           workareas, can be empty, can't be null
    * @param filter
    *           filter parameters, can't be null
    * @return all objects that are accessable by any of the specified workareas,
    *         can be empty, won't be null
    * @throws PersistenceException
    */
   public Collection<A> getAvailable(Collection<WorkArea> workareas, Filter filter) throws PersistenceException;
   
	/**
	 * Get all objects of the specified type that are accessable from the given workarea.
	 * @param id id of workarea
	 * @param clazz type of object
	 * @return all objects of the specified type that are accessable from the given workarea.
	 * @throws PersistenceException on error
	 */
	public Collection<A> getByWorkAreaId(Long id, Class clazz) throws PersistenceException;
   

}
