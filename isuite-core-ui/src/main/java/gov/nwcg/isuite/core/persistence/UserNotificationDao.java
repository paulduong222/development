package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface UserNotificationDao extends TransactionSupport, CrudDao<UserNotification>{

	/**
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<UserNotification> getUnreadNotifications(Long userId) throws PersistenceException;
	
	/**
	 * @param userId
	 * @throws PersistenceException
	 */
	public void updateReadFlag(Long userId) throws PersistenceException;
}