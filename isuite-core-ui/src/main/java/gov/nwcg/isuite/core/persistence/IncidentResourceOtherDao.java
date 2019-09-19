package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.filter.OtherResourceCostFilter;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentResourceOtherDao extends TransactionSupport, CrudDao<IncidentResourceOther>{
   

	/**
	 * Returns collection of IncidentResourceOtherGridVo's for the filter criteria supplied.
	 * 
	 * @param filter
	 * 			the filter criteria to search by
	 * @return
	 * 			collection of incidentresourcegridvo's to return
	 * @throws PersistenceException
	 */
	public Collection<IncidentResourceOtherGridVo> getGrid(OtherResourceCostFilter filter) throws PersistenceException;

	public Collection<IncidentResourceOther> getTopLevelResources(Long incidentId) throws PersistenceException ;
	
	public Collection<IncidentResourceOther> getTopLevelResourcesIG(Long incidentGroupId) throws PersistenceException ;

	public Object getCostDataValue(String field, Long incidentResourceOtherId) throws PersistenceException ;

	public void updateDefaultCostGroup(Long costDataId, Long cgId) throws PersistenceException;	

	public Collection<CostResourceDataVo> getCostResourceData(Long incidentResourceOtherId,Long incidentId,Long incidentGroupId) throws PersistenceException;

	public void persistSqls(Collection<String> sqls) throws PersistenceException;

	public int getUnlockedCostRecordCount(Long irId) throws PersistenceException;

	public Long getAccrualCodeId(Long iroId) throws PersistenceException;

	public Boolean getAccrualCodeLocked(Long iroId) throws PersistenceException;

	public Long getIncidentId(Long resOtherId) throws PersistenceException;
	
}
