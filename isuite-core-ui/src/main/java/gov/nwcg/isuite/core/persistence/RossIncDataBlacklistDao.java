package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.RossIncDataBlacklistGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossIncDataBlacklistDao extends TransactionSupport,CrudDao<RossIncDataBlacklist> {

	/**
	 * @param rossXmlFileId
	 * @param rossResourceIds
	 * @throws PersistenceException
	 */
	public void blacklistResources(Long rossXmlFileId, Collection<Long> rossResourceIds) throws PersistenceException;
	
	public Collection<IncidentGridVo> getRossIncidents(IncidentFilter filter) throws PersistenceException;
	
	public Collection<RossIncDataBlacklistGridVo> getByRossIncId(String rossIncidentId) throws PersistenceException;
	
	public void updateStatuses(Collection<Long> rossResourceIds, Long rxfId, String status) throws PersistenceException;

	public void deleteByRossIncidentId(String rossIncidentId) throws PersistenceException ;	

	public void deleteByRossIncIdResReqId(String rossIncidentId, Long reqId) throws PersistenceException ;

	public void createExcludedResources(Collection<Long> rossXmlFileDataIds,String rossIncId) throws PersistenceException;
	
}
