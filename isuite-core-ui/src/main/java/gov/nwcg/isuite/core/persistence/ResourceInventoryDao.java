package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResourceInventoryDao extends TransactionSupport, CrudDao<Resource> {

	/**
	 * 
	 * @param dispatchCenterId
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ResourceInventoryGridVo> getGrid(Long dispatchCenterId, Long userId) throws PersistenceException;
	
	/**
	 * 
	 * @param organizationId
	 * @param resourceId
	 * @throws PersistenceException
	 */
	public void removeNonStandardResource(Long organizationId, Long resourceId) throws PersistenceException;
	
	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Resource> getAllByIds(Collection<Long> ids) throws PersistenceException;
	
	/**
	 * 
	 * @param dispatchCenterId
	 * @param nonStdId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ResourceInventoryGridVo> getNonStdGrid(Long dispatchCenterId, Long nonStdId) throws PersistenceException;
	
	/**
	 * 
	 * @param userId
	 * @param resourceIds
	 * @return
	 * @throws PersistenceException
	 */
	public int removeExcludedItems(Long userId,Collection<Long> resourceIds) throws PersistenceException;
	
	/**
	 * 
	 * @param userId
	 * @param resourceIds
	 * @return
	 * @throws PersistenceException
	 */
	public int createExcludedItems(Long userId, Collection<Long> resourceIds) throws PersistenceException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ResourceInventoryGridVo> getExcludedResources(Long userId) throws PersistenceException;
	
}
