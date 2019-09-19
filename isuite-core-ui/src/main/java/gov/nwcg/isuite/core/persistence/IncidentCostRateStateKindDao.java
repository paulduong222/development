package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentCostRateStateKindDao extends TransactionSupport, CrudDao<IncidentCostRateStateKind> {

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IncidentCostRateStateKind> getIncidentCostRateStateKinds(CostRateFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(IncidentCostRateStateKind persistable) throws PersistenceException;

	public IncidentCostRateStateKind getByKind(String type, Long incidentId, Long kindId, Long agencyId) throws PersistenceException;

	public Long getIncidentId(Long id) throws PersistenceException;
	
	public Long getIncidentGroupId(Long id) throws PersistenceException;

	public void saveGroupRecord(IncidentCostRateStateKind source, Long groupId) throws PersistenceException;

	public void saveGroupIncRecord(IncidentCostRateStateKind source, Long incId) throws PersistenceException;
	

}
