package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.filter.ResourcePersonFilter;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.ResourcePersonVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.core.persistence.hibernate.HibernateProperties;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

/**
 * Data Access for Resources.
 *  
 * @author bsteiner
 */
public interface ResourceDao extends TransactionSupport, CrudDao<Resource>{

	public Collection<Resource> getPreviousLeader(Long parentResourceId, Integer leaderType, Long resourceId) throws PersistenceException ;

	/**
	 * Retrieve Resources based on the provided filter criterion.  
	 * 
	 * @param filter
	 * @return {@link Collection} of {@link ResourceGridVo} objects
	 * @throws PersistenceException
	 */
	public Collection<ResourceGridVo> getGrid(ResourceFilter filter) throws PersistenceException;



	/**
	 * Delete the incident resources by setting the deleted_date value to current date.
	 * 
	 * Removes the parent associations by setting the parent_id to null.
	 * 
	 * @param resourceIds
	 * 			collection of resource.id's to delete
	 * @param removeParentAssociations
	 * 			flag to indicate whether or not the parent associations should be removed
	 * @return
	 * 		the number of resources deleted
	 * @throws PersistenceException
	 */
	public int deleteIncidentResources(Collection<Long> resourceIds, boolean removeParentAssociations) throws PersistenceException;
	public int deleteIncidentResources2(Collection<Long> resourceIds, boolean removeParentAssociations) throws PersistenceException;


	/**
	 * Delete the work area resources by setting the deleted_date value to current date.
	 * 
	 * Removes the parent associations by setting the parent_id to null.
	 * 
	 * @param resourceIds
	 * 			collection of resource.id's to delete
	 * @return
	 * 		the number of resources deleted
	 * @throws PersistenceException
	 */
	public int deleteWorkAreaResources(Collection<Long> resourceIds) throws PersistenceException;

	/**
	 * Disables the resources by setting the is_enabled flag to false.
	 * 
	 * Optionally removes the parent associations by setting the parent_id to null.
	 * 
	 * @param resourceIds
	 * 			collection of resource.id's to disable
	 * @param removeParentAssociations
	 * 			flag to indicate whether or not the parent associations should be removed
	 * @return
	 * 		the number of resources disabled
	 * @throws PersistenceException
	 */
	public int disableResources(Collection<Long> resourceIds) throws PersistenceException;

	/**
	 * Enables the resources by setting the is_enabled flag to true.
	 * 
	 * @param resourceIds
	 * 			collection of resource.id's to enable
	 * @return
	 * 		the number of resources enabled
	 * @throws PersistenceException
	 */
	public int enableResources(Collection<Long> resourceIds) throws PersistenceException;

	/**
	 * Removes rostered children from a collection of resources.
	 * 
	 * @param resourceIds
	 * @throws PersistenceException
	 */
	public void unRosterWorkAreaResourcesChildren(Collection<Long> resourceIds) throws PersistenceException;

	/**
	 *  * Removes rostered children from a collection of resources.
	 * 
	 * @param resourceIds
	 * @throws PersistenceException
	 */
	public void unRosterIncidentResourcesChildren(Collection<Long> resourceIds) throws PersistenceException;

	/**
	 * Retrieve all Resources by their id.
	 * 
	 * @param resourceIds
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Resource> getAllByIds(Collection<Long> resourceIds) throws PersistenceException;


	public void deleteQualifications(Collection<Long> resourceKindIds) throws PersistenceException;


	public Collection<Resource> getMatchingResources(ResourceDuplicateFilter filter) throws PersistenceException;

	public void unrosterResource(Long resourceId) throws PersistenceException;

	/**
	 * Retrieve all person type resources.
	 * @param filter {@link ResourcePersonFilter}
	 * @param hp {@link HibernateProperties} bean from the {@link ApplicationContext}.
	 * @return {@link Collection} of {@link ResourcePersonVo} objects
	 * @throws PersistenceException
	 */
	public Collection<ResourcePersonVo> getPersonResources(ResourcePersonFilter filter, HibernateProperties hp) throws PersistenceException;

	public Resource getByIdInWorkArea(Long id,Long workAreaId) throws PersistenceException ;

	public Resource getResourceLeader(Long parentResourceId, Integer leaderType) throws PersistenceException ;	

	public void removeParentResourceLeader(Long parentResourceId, Integer leaderType, Long resourceId) throws PersistenceException;

	public void unrosterUnassignedOrphanedChildren(Long resourceId) throws PersistenceException;

	public Resource getPermanentResourceByResId(Long rossResId) throws PersistenceException;

	public Collection<Resource> getTopLevelByIncidentId(Long incidentId) throws PersistenceException;

	public Long getPermResourceByRossResId(Long rossResId) throws PersistenceException;

	public void persistSqls(Collection<String> sqls) throws PersistenceException;

	public Long getTopLevelResourceId(Long id) throws PersistenceException;
}
