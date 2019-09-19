package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface SystemNotificationDao extends TransactionSupport, CrudDao<SystemNotification> {

	public Collection<SystemNotification> getByTaskQueueId(Long id) throws PersistenceException;
	
	
}
