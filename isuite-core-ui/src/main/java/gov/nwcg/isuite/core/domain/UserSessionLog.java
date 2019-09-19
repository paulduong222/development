package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

public interface UserSessionLog extends Persistable{

	/**
	 * @return the sessionId
	 */
	public String getSessionId() ;

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) ;

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() ;

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress);

	/**
	 * @return the lastStatusCheckDate
	 */
	public Date getLastStatusCheckDate() ;

	/**
	 * @param lastStatusCheckDate the lastStatusCheckDate to set
	 */
	public void setLastStatusCheckDate(Date lastStatusCheckDate);
	
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
	 * @return the userSessionLogActivities
	 */
	public Collection<UserSessionLogActivity> getUserSessionLogActivities();

	/**
	 * @param userSessionLogActivities the userSessionLogActivities to set
	 */
	public void setUserSessionLogActivities(Collection<UserSessionLogActivity> userSessionLogActivities) ;
	
	public void addUserSessionLogActivity(UserSessionActionTypeEnum actionType, UserSessionActionCauseEnum actionCause);
	
}
