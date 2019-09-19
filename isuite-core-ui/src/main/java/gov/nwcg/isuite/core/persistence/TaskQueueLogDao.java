package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface TaskQueueLogDao extends TransactionSupport, CrudDao<TaskQueueLog> {

	
	
}
