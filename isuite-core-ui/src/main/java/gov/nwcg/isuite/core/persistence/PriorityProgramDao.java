package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface PriorityProgramDao extends TransactionSupport, CrudDao<PriorityProgram> {
	
	public int getDuplicateCodeCount(PriorityProgramVo vo) throws PersistenceException;
	
	public Collection<PriorityProgramVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException;

	public void syncNewWithGroup(String ppCode, Long groupId) throws PersistenceException;
	public void syncNewFromIncident(String ppCode, Long groupId) throws PersistenceException;
	public void syncUpdateWithGroup(String originalCode,String ppCode, Long groupId) throws PersistenceException;
	public int getIncidentInUseCount(String ppCode, Long groupId) throws PersistenceException;
	public void deleteCodeForGroupIncidents(String ppCode, Long groupId) throws PersistenceException;
	public Long getIdByTransferableIdentity(String ti) throws PersistenceException;
}
