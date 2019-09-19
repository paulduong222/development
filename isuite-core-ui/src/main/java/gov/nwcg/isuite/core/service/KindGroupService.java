/**
 * 
 */
package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.core.vo.KindGroupVo;

/**
 * @author gdyer
 *
 */
public interface KindGroupService {
	
	/**
	 * Saves the resource.
	 * 
	 * @param persistableVo
	 * 				the resourceVo to save
	 * @throws ServiceException
	 */
	public KindGroupVo save(KindGroupVo persistableVo) throws ServiceException,ValidationException;
	
	/**
	 * Deletes the resource.
	 * 
	 * @param persistableVo
	 * 			the resourceVo to delete
	 * @throws ServiceException
	 */
	public void delete(Collection<Long> kindGroupIds) throws ServiceException;
	
	/**
	 * Returns the resource with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the resourceVo with the supplied id
	 * @throws ServiceException
	 */
	public KindGroupVo getById(Long id) throws ServiceException;
	
	/**
	 * Returns collection of resources for a resource grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of resourceGridVos
	 * @throws ServiceException
	 */
	public Collection<KindGroupVo> getGrid(KindGroupFilter filter) throws ServiceException;
	

}
