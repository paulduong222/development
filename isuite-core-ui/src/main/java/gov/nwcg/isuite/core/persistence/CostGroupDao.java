package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.core.vo.CostGroupGridVo;
import gov.nwcg.isuite.core.vo.CostGroupResourceGridVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

public interface CostGroupDao extends TransactionSupport, CrudDao<CostGroup> {
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupGridVo> getGrid(CostGroupFilter filter) throws PersistenceException;
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupVo> getAll(CostGroupFilter filter) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupAvailableOtherResourceGrid(Long costGroupId) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupSelectedOtherResourceGrid(Long costGroupId) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupAvailableResourceGrid(Long costGroupId) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CostGroupResourceGridVo> getCostGroupSelectedResourceGrid(Long costGroupId) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @return
	 * @throws PersistenceException
	 */
	public int getActiveAssignedResourcesCount(Long costGroupId) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @param resourceIds
	 * @throws PersistenceException
	 */
	public void removeResourcesFromPriorCostGroups(Long costGroupId, Collection<Long> resourceIds) throws PersistenceException;
	
	/**
	 * @param costGroupId
	 * @param otherResourceIds
	 * @throws PersistenceException
	 */
	public void removeOtherResourcesFromPriorCostGroups(Long costGroupId, Collection<Long> otherResourceIds) throws PersistenceException;

	public int getCostGroupDayShareDateCount(Long costGroupId, Date dt) throws PersistenceException;

	public CostGroup getByCostGroupName(String name, Long incidentId) throws PersistenceException ;

	public Collection<CostGroup> getByIncidentId(Long incidentId) throws PersistenceException ;

	public int getDailyCostAssignedCount(Long costGroupId) throws PersistenceException;

	public void removeDailyCostCostGroup(Long id) throws PersistenceException ;	

	public CostGroup getByCostGroupNameShift(String name, String shift, Long incidentId) throws PersistenceException;

	public Long getIncidentId(Long costGroupId) throws PersistenceException;
	
}
