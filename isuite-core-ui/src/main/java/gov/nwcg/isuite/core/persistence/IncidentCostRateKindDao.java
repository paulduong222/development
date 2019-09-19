package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentCostRateKindDao extends TransactionSupport, CrudDao<IncidentCostRateKind>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateKind getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentCostRateKind persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateKind> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentCostRateKind persistable) throws PersistenceException;

	public IncidentCostRateKind getByKind(String type, Long incidentId, Long kindId) throws PersistenceException;

	public void updateIncidents(IncidentCostRateKind irck, Collection<Long> incidentIds) throws PersistenceException;

	public Long getIncidentId(Long icrkId) throws PersistenceException;
	
	public Long getIncidentGroupId(Long icrkId) throws PersistenceException;

	public void updateGroupAndIncidents(IncidentCostRateKind irck, Collection<Long> incidentIds, Long groupId) throws PersistenceException;

}
