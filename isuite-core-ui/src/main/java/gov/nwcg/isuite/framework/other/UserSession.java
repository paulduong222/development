package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.WorkAreaUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * This object represents important information associated to the logged in
 * user. <br/>
 * When a user does a "refresh activity" (login, change work area, click
 * refresh), this object should be populated with all of the included
 * attributes.
 *  
 *  see--> UserSessionVo
 *  
 * @author bsteiner
 */
public class UserSession {

	private Long userId;
	private String loginName;
	private String firstName;
	private String lastName;
	private Long currentIncidentId;
	private Collection<String> primaryRoles;
	/* The Long represents the restricted incident id. */
	private Map<Long, Collection<String>> restrictedIncidentRoles;

	public UserSession() {
	}

	/**
	 * Constructor used to set all of the details associated to the User to be
	 * stored.
	 * 
	 * @param user
	 *            - populated {@link User} object.
	 * @param waUsers
	 *            - populated {@link Collection} of {@link WorkAreaUser}
	 *            objects.
	 */
	public UserSession(User user, Collection<WorkAreaUser> waUsers) {
		setUserData(user);
	}

	public void setUserData(User user) {
		this.userId = user.getId();
		this.loginName = user.getLoginName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		//this.primaryRoles = convertRolesToStrings(user.getRoles());
	}

	/**
	 * @return the currentIncidentId
	 */
	public Long getCurrentIncidentId() {
		return currentIncidentId;
	}

	/**
	 * @param currentIncidentId
	 *            the currentIncidentId to set
	 */
	public void setCurrentIncidentId(Long currentIncidentId) {
		this.currentIncidentId = currentIncidentId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the primaryRoles
	 */
	public Collection<String> getPrimaryRoles() {
		return primaryRoles;
	}

	/**
	 * @param primaryRoles
	 *            the primaryRoles to set
	 */
	public void setPrimaryRoles(Collection<String> primaryRoles) {
		if (null == this.primaryRoles) {
			this.primaryRoles = new ArrayList<String>();
		}
		this.primaryRoles = primaryRoles;
	}

	/**
	 * @return the restrictedIncidentRoles
	 */
	public Map<Long, Collection<String>> getRestrictedIncidentRoles() {
		return restrictedIncidentRoles;
	}

	/**
	 * @param restrictedIncidentRoles
	 *            the restrictedIncidentRoles to set
	 */
	public void setRestrictedIncidentRoles(
			Map<Long, Collection<String>> restrictedIncidentRoles) {
		this.restrictedIncidentRoles = restrictedIncidentRoles;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
