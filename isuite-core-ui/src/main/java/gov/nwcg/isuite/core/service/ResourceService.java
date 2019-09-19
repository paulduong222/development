package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ResourcePersonFilter;
import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.ResourcePersonVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

/**
 * @author bsteiner
 *
 */
public interface ResourceService extends TransactionService {

	/**
	 * Saves the resource.
	 * 
	 * @param persistableVo
	 * 				the resourceVo to save
	 * @throws ServiceException
	 */
	public void save(ResourceVo persistableVo) throws ServiceException,ValidationException;
	
	/**
	 * Deletes the resource.
	 * 
	 * @param persistableVo
	 * 			the resourceVo to delete
	 * @throws ServiceException
	 */
	public void delete(ResourceVo persistableVo) throws ServiceException;
	
	/**
	 * Saves the collection of resources.
	 * 
	 * @param persistableList
	 * 			the collection of resourceVos to save
	 * @throws ServiceException
	 */
	public void saveAll(Collection<ResourceVo> persistableList) throws ServiceException,ValidationException;
	
	/**
	 * Returns the resource with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the resourceVo with the supplied id
	 * @throws ServiceException
	 */
	public ResourceVo getById(Long id) throws ServiceException;
	
	/**
	 * Returns collection of resources for a resource grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of resourceGridVos
	 * @throws ServiceException
	 */
	public Collection<ResourceGridVo> getGrid(ResourceFilter filter) throws ServiceException;
	
	public Collection<ResourceVo> getMatchingResources(ResourceDuplicateFilter filter) throws ServiceException;
	
	/**
	 * Retrieve all person type resources.
	 * @param filter {@link ResourcePersonFilter}
	 * @return {@link Collection} of {@link ResourcePersonVo} objects.
	 * @throws ServiceException
	 */
	public Collection<ResourcePersonVo> getPersonResources(ResourcePersonFilter filter) throws ServiceException;
	
}
