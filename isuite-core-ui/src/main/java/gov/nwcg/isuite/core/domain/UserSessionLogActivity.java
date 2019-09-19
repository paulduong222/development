package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

public interface UserSessionLogActivity extends Persistable {

	/**
	 * @return the actionType
	 */
	public UserSessionActionTypeEnum getActionType();

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(UserSessionActionTypeEnum actionType) ;

	/**
	 * @return the actionCause
	 */
	public UserSessionActionCauseEnum getActionCause();

	/**
	 * @param actionCause the actionCause to set
	 */
	public void setActionCause(UserSessionActionCauseEnum actionCause);

	/**
	 * @return the userSessionLog
	 */
	public UserSessionLog getUserSessionLog();

	/**
	 * @param userSessionLog the userSessionLog to set
	 */
	public void setUserSessionLog(UserSessionLog userSessionLog);
	
	public User getAdminUser();
	
	public void setAdminUser(User user);
	
}
