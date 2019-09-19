package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapMasterFrequencyDao extends TransactionSupport, CrudDao<IapMasterFrequency> {
	
	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<IapMasterFrequencyVo> getGrid(IapMasterFrequencyFilter filter) throws PersistenceException;
	
	/**
	 * @param IapMasterFrequencyVo vo
	 * @return void
	 * @throws PersistenceException
	 */	
	public void propagateChanges(IapMasterFrequencyVo vo) throws PersistenceException;
	
	public void propagateAllFrequencyChanges(Collection<IapMasterFrequencyVo> vos) throws PersistenceException;
	
	public void transferMFLFromIncidentToIncidentGroup(Long incidentId, Long incidentGroupId) throws PersistenceException;
	public void copyMFLFromIncidentGroupToIncident(Long incidentId, Long incidentGroupId) throws Exception;
	
}