package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.filter.DailyCostFilter;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.core.vo.IacVo;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Data Access for Incident_Resource Daily Cost.
 *  
 */
public interface IncidentResourceDailyCostDao extends TransactionSupport, CrudDao<IncidentResourceDailyCost> {
   
	/**
	 * Returns dailycost records for the resource.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentResourceDailyCostVo> getResourceCosts(Long id) throws PersistenceException,Exception;

	public Collection<IncidentResourceDailyCostVo> getResourceOtherCosts(Long id) throws PersistenceException,Exception;

	public Collection<IncidentResourceDailyCost> getByIncidentResourceId(Long id) throws PersistenceException ;

	public Collection<IncidentResourceDailyCost> getByIncidentResourceId(Long id,Date start,Date end) throws PersistenceException;
	
	public Collection<IncidentResourceDailyCost> getByIncidentResourceOtherId(Long id) throws PersistenceException ;

	public Collection<IncidentResourceDailyCost> getByFilter(DailyCostFilter filter) throws PersistenceException ;

	public BigDecimal getParentRollupAmount(Long incidentResourceId, Long incAcctCodeId, Date activityDate) throws PersistenceException ;

	public Collection<Long> getChildUniqueAcctCodeIdsByDate(Long incResId, Long excludeAcctCodeId, Date dt) throws PersistenceException ;

	public BigDecimal getIncidentTotalCostAmount(Long incidentId) throws PersistenceException ;

	public BigDecimal getIncidentGroupTotalCostAmount(Long incidentGroupId) throws PersistenceException;

	public int getManualCostCount(Long incidentResourceId, Long incidentResourceOtherId, Date dt) throws PersistenceException;

	public void deleteManualCost(Long incidentResourceId, Long incidentResourceOtherId,Date dt) throws PersistenceException ;

	public void setCostGroupId(Long incidentResourceId, Long costGroupId) throws PersistenceException;

	public void setShiftId(Long incidentResourceId, Long shiftId) throws PersistenceException;

	public void deleteCostBeforeDate(String resourceType,Long incidentResourceId, Date dt) throws PersistenceException;

	public void deleteCostAfterDate(String resourceType,Long incidentResourceId, Date dt) throws PersistenceException;
	
	public void deleteResEstCoststByAcctCodeId(Long incidentResourceId, Long incidentResourceOtherId, Long acctCodeId) throws PersistenceException;

	public void deleteResEstCosts(Long incidentResourceId, Long incidentResourceOtherId) throws PersistenceException;

	public int getResourceActualsCount(Long incidentId, Long resourceId) throws PersistenceException;	
	
	public int getSubordinateActualsCount(Long incidentId, Long resourceId) throws PersistenceException ;

	public String getResourceCostException(Long incidentId) throws PersistenceException;
	
	public Collection<DailyCostVo> getDailyCosts(Long incidentResourceId,Long incidentResourceOtherId, Date startDate) throws PersistenceException;

	public void persistSqls(Collection<String> sqls) throws PersistenceException ;

	public Collection<ContractorRateVo> getContractorRateVos(Long cpiId) throws PersistenceException;

	public Collection<IacVo> getChildUniqueCostAcctCodeIds(Long incResId) throws PersistenceException;

	public void deleteAllAfterDate(Date cutoffDate) throws PersistenceException;
	
}
