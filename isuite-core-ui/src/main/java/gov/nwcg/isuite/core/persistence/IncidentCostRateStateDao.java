package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentCostRateStateDao extends TransactionSupport, CrudDao<IncidentCostRateState>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateState getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentCostRateState persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateState> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentCostRateState persistable) throws PersistenceException;
	
	/**
	 * @param vo
	 * @throws PersistenceException
	 */
	public void overwriteStateOvhdKindRates(String rateType,IncidentCostRateStateVo vo, Long groupId, Collection<Long> incidentIds) throws PersistenceException;

	public IncidentCostRateState getIncidentCostRateState(CostRateFilter filter) throws PersistenceException;

	public Long getIncidentId(Long icrStateId) throws PersistenceException;
	
	public Long getIncidentGroupId(Long icrStateId) throws PersistenceException;

	public void saveGroupRecord(IncidentCostRateState source, Long groupId) throws PersistenceException;
	
	public void saveGroupIncRecord(IncidentCostRateState source, Long incId) throws PersistenceException;
}
