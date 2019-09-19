package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface TaskQueueDao extends TransactionSupport, CrudDao<TaskQueue> {

	public Collection<TaskQueue> getTaskQueues() throws PersistenceException;
	
	public void updateStatus(Long id, String status) throws PersistenceException ;

	public void resetAutoBackup() throws PersistenceException;	
}
