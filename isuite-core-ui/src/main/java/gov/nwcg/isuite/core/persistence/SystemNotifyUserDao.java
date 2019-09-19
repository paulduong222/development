package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface SystemNotifyUserDao extends TransactionSupport, CrudDao<SystemNotifyUser> {

	/**
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<SystemNotifyUser> getBySystemNotificationId(Long id) throws PersistenceException;
	
	
}
