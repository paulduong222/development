package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface IncidentCostRateOvhdDao extends TransactionSupport, CrudDao<IncidentCostRateOvhd>{

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateOvhd getById(Long id, Class clazz) throws PersistenceException;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentCostRateOvhd persistable) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateOvhd> persistables) throws PersistenceException;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentCostRateOvhd persistable) throws PersistenceException;
	
	/**
	 * @param vo
	 * @throws PersistenceException
	 */
	public void overwriteOvhdKindRates(String rateType,IncidentCostRateOvhdVo vo) throws PersistenceException;

	public void overwriteOvhdKindRatesGroup(String rateType,IncidentCostRateOvhdVo vo,Long groupId, Collection<Long> incidentIds) throws PersistenceException;
	
	public Long getIncidentId(Long icrOvhdId) throws PersistenceException;
	
	public Long getIncidentGroupId(Long icrOvhdId) throws PersistenceException;

	public void saveGroupRecord(IncidentCostRateOvhd persistable, Long groupId) throws PersistenceException;
	
	public void saveGroupIncRecord(IncidentCostRateOvhd source, Long incId) throws PersistenceException;
	
}
