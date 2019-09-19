package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface DataTransferDao extends TransactionSupport, CrudDao<DataTransfer> {

	public Collection<DataTransfer> getExportHistory(Long incidentId, Long incidentGroupId) throws PersistenceException;

	public Long executeQueryUniqueId(String sql) throws PersistenceException;
	
	public Object executeQuery(String sql) throws PersistenceException;

	public int executeUpdate(String sql) throws PersistenceException;

	public Collection<DataTransferVo> getFileList() throws PersistenceException;

	public void doGroupSiteManagedFalse(String ti) throws PersistenceException;
	public void doIncidentSiteManagedFalse(String ti) throws PersistenceException;
	public void addGroupUser(String ti, Long userId) throws PersistenceException;
	public void addIncidentUser(String ti, Long userId) throws PersistenceException;
	public void addIncidentUser(Long id, Long userId) throws PersistenceException;
}
