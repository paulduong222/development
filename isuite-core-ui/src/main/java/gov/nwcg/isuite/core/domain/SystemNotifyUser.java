package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemNotifyUser extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the user
	 */
	public User getUser() ;

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) ;

	/**
	 * @return the userId
	 */
	public Long getUserId() ;

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) ;

	/**
	 * @return the systemNotification
	 */
	public SystemNotification getSystemNotification();

	/**
	 * @param systemNotification the systemNotification to set
	 */
	public void setSystemNotification(SystemNotification systemNotification);

	/**
	 * @return the systemNotificationId
	 */
	public Long getSystemNotificationId();

	/**
	 * @param systemNotificationId the systemNotificationId to set
	 */
	public void setSystemNotificationId(Long systemNotificationId);

	/**
	 * @return the excluded
	 */
	public Boolean getExcluded();
	/**
	 * @param excluded the excluded to set
	 */
	public void setExcluded(Boolean excluded);
	
}
