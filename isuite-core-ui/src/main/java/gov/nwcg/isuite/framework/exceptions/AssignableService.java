package gov.nwcg.isuite.framework.exceptions;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.core.domain.Assignable;
import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Collection;

/**
 * @author doug
 *
 */
public interface AssignableService<A extends Assignable> {


   /**
    * Retrieve all objects that are accessible by the specified workarea.
    * 
    * @param workAreaId
    *           workarea can't be null
    * @param filter
    *           filter parameters, can't be null
    * @return all objects that are accessible by the specified workarea, may be
    *         empty but won't be null
    * @throws ServiceException
    *            if needed
    */
   public Collection<A> getByWorkAreaId(Long workAreaId, Filter filter) throws ServiceException;

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
    * @throws ServiceException
    */
   public Collection<A> getAvailable(Collection<WorkArea> workareas, Filter filter) throws ServiceException;
   
	/**
	 * Get all objects of the specified type that are accessable from the given workarea.
	 * @param id id of workarea
	 * @param clazz type of object
	 * @return all objects of the specified type that are accessable from the given workarea.
	 * @throws ServiceException on error
	 */
	public Collection<A> getByWorkAreaId(Long id, Class clazz) throws ServiceException;
   


}
