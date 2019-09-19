package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;


public interface User extends Persistable{

	/**
	 * Name used to login to the system.
	 * 
	 * @return name used to login to the system, never null, nor empty
	 * @see #setLoginName(String)
	 * @see #setCountryName(String)
	 */
	public String getLoginName();

	/**
	 * Name used to login to the system.
	 * 
	 * @param name
	 *            name used to login to the system, can not be null, nor empty
	 * @see #getLoginName()
	 * @see #getCountryName()
	 */
	public void setLoginName(String name);

	/**
	 * Accessor for first name of the user.
	 * 
	 * @return first name of user, may be null
	 * @see org.nwcg.isuite.domain.access.User.getFirstName()
	 * @see #setFirstName(String)
	 */
	public String getFirstName();

	/**
	 * Setter for first name of the user.
	 * 
	 * @param firstName
	 *            first name of the user, may be null
	 * @see #getFirstName()
	 */
	public void setFirstName(String firstName);

	/**
	 * Accessor for last name of the user.
	 * 
	 * @return last name of user, will NOT be null, nor empty
	 * @see org.nwcg.isuite.domain.access.User.getLastName()
	 * @see #setLastName(String)
	 */
	public String getLastName();

	/**
	 * Setter for last name of the user.
	 * 
	 * @param lastName
	 *            last name of the user, will NOT be null, nor empty
	 * @see #getLastName()
	 */
	public void setLastName(String lastName);

	/**
	 * Determines if user is enabled.
	 * <p>
	 * An non enabled user is one that is known to the system and is valid in
	 * all respects, but is not allowed to access the system.
	 * </p>
	 * 
	 * @return true if user is allowed to access the system
	 * @see #setEnabled(boolean)
	 */
	public Boolean isEnabled();

	/**
	 * Determines if user is enabled.
	 * <p>
	 * An non enabled user is one that is known to the system and is valid in
	 * all respects, but is not allowed to access the system.
	 * </p>
	 * 
	 * @param enabled
	 *            true if user is allowed to access the system
	 * @see #isEnabled()
	 */
	public void setEnabled(Boolean enabled);

	/**
	 * Accessor for the current workArea of the user.
	 * <p>
	 * if no current workArea is set, defaults to the default workArea
	 * </p>
	 * 
	 * @return current workArea of user or default workArea if no current
	 *         workArea is set
	 * @see #setCurrentWorkArea(WorkArea)
	 */
	public Incident getCurrentIncident();

	/**
	 * Accessor for the current WorkArea of the user.
	 * <p>
	 * if the user is not already associated with the workArea, the workArea
	 * will be added
	 * </p>
	 * 
	 * @param workArea
	 *            new currentWorkArea of the user, can be null
	 * @see #getCurrentWorkArea()
	 */
	public void setCurrentIncident(Incident incident);

	/**
	 * Retrieve the id for the current work area associated to the user
	 * @return
	 */
	public Long getCurrentIncidentId();

	/**
	 * Set the id for the current work area associated to the user
	 * @param workAreaId
	 */
	public void setCurrentIncidentId(Long incidentId);

	/**
	 * Accessor to last login date and time the user successfully loggedin.
	 * @param loginDate
	 * 			the date and time of login
	 * @see #getLastLoginDate()
	 */
	public void setLastLoginDate(Date loginDate);

	/**
	 * Accessor to last login date and time the user successfully logged in.
	 * 
	 * @return the date and time the user last successfully logged in
	 * @see #setLastLoginDate(Date)
	 * 
	 */
	public Date getLastLoginDate();

	/**
	 * Accessor to number of failed user login attempts.
	 *@param failedAttempts
	 *		the number of failed login attempts
	 * 
	 * @see #getFailedLoginAttempts()
	 */
	public void setFailedLoginAttempts(int failedAttempts);

	/**
	 * Accessor to number of failed user login attemts.
	 * 
	 * @return the number of failed login attempts made by user
	 * 
	 * @see #setFailedLoginAttempts(int)
	 */
	public int getFailedLoginAttempts();

	/**
	 * Get the User's home or base unit.
	 * @return a populated <code>Organization</code> object.
	 */
	public Organization getHomeUnit();

	/**
	 * Get the User's home or base unit id.
	 * @return the id for that organization.
	 */
	public Long getHomeUnitId();

	/**
	 * Set the User's home or base unit.
	 * @param unit a populated <code>Organization</code> object.
	 */
	public void setHomeUnit(Organization unit);

	/**
	 * Set the User's home or base unit id.
	 * @param the id of the home unit (organization).
	 */
	public void setHomeUnitId(Long homeUnitId);

	/* -------------------------------------------------------------
	 * site user specific methods
	 * -------------------------------------------------------------*/
	/**
	 * Password used to log into the system.
	 * 
	 * @return password used to log into the system, will not be null
	 * @see #setPassword(String)
	 */
	public String getPassword();

	/**
	 * Date user password was created.
	 * 
	 * @return date user password was created, will not be null
	 * @see #setPasswordCreatedDate(Date)
	 */
	public Date getPasswordCreatedDate();

	/**
	 * Date user password was created.
	 * 
	 * @param passwordCreatedDate
	 *       date that the password was created
	 * @see #getPasswordCreatedDate()
	 */
	public void setPasswordCreatedDate(Date passwordCreatedDate);

	/**
	 * Date of the user password expiration date.
	 * 
	 * @return date user password expires, will not be null
	 * @see #setAccountExpirationDate(Date)
	 */
	public Date getAccountExpirationDate();

	/**
	 * Expiration Date of user password.
	 * 
	 * @param accountExpirationDate
	 *       date that the password expires
	 * @see #getAccountExpirationDate()
	 */
	public void setAccountExpirationDate(Date accountExpirationDate);

	/**
	 * Password used to log into the system.
	 * 
	 * @param password
	 *            password used to log into the system, can not be null
	 *            NOTE: SHOULD ONLY BE USED FOR HIBERNATE PURPOSES.  ALL OTHER
	 *                  CODE SHOULD BE CALLING SetEncryptedPassword!!!!
	 *
	 * @throws ValidationException 
	 * @throws Exception 
	 * @see #getPassword()
	 */
	public void setPassword(String password) throws ValidationException, Exception;

	/**
	 * Indicates that the user must reset the password.
	 * @param resetPassword
	 *            indicates if the user must reset the password
	 *            @see #setResetPassword(boolean)
	 * 
	 * @see #isResetPassword()
	 */
	public void setResetPassword(Boolean reset);

	/**
	 * Indicates that the user must reset the password.
	 * 
	 * @return true if the user must reset the password
	 */
	public Boolean isResetPassword();


	/**
	 * Date and time that user was disabled after failed login attempts.
	 * 
	 * @param timestamp
	 *          date and time the user was disabled after failed login attempts
	 * 
	 * @see #getAccountLockedDate()
	 */
	public void setAccountLockedDate(Date timestamp);

	/**
	 * Date and time that user was disabled after failed login attempts.
	 * 
	 * @return date and time stamp for when user was disabled from failed login 
	 *       attempts
	 * @see #setAccountLockedDate(Date)
	 * 
	 */
	public Date getAccountLockedDate();

	/**
	 * This method will be used to set the password as an encrypted password.
	 */
	public void setEncryptedPassword(String passwordToEncrypt) throws ValidationException;


	/**
	 * @return
	 */
	public Collection<Organization> getOrganizations();

	/**
	 * @param organizations
	 */
	public void setOrganizations(Collection<Organization> organizations);

	/**
	 * Retrieve the user's primary dispatch center
	 * @return
	 */
	public Organization getPrimaryDispatchCenter();

	/**
	 * Set the user's primary dispatch center
	 * @param primaryDispatchCenter the primaryDispatchCenter to set
	 */
	public void setPrimaryDispatchCenter(Organization primaryDispatchCenter);

	/**
	 * Retrieve the id of the user's primary dispatch center
	 * @return
	 */
	public Long getPrimaryDispatchCenterId();

	/**
	 * Set the id of the user's primary dispatch center
	 * @param primaryDispatchCenterId the primaryDispatchCenterId to set
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId);

	/**
	 * Retrieve the user's  work phone number.
	 * 
	 * @return 
	 * 		the user's work phone number to return
	 */
	public String getWorkPhone();

	/**
	 * Set the user's work phone number.
	 *  
	 * @param phone
	 * 			the work phone number to set
	 */
	public void setWorkPhone(String phone);

	/**
	 * Retrieve the user's  cell phone number.
	 * 
	 * @return 
	 * 		the user's cell phone number to return
	 */
	public String getCellPhone();

	/**
	 * Set the user's cell phone number.
	 *  
	 * @param phone
	 * 			the cell phone number to set
	 */
	public void setCellPhone(String phone);

	/**
	 * Retrieve the user's email address.
	 * 
	 * @return 
	 * 		the user email address to return
	 */
	public String getEmail();

	/**
	 * Set the user's email address.
	 * 
	 * @param email
	 * 			the email to set
	 */
	public void setEmail(String email);

	/**
	 * Retuen the user e-auth id.
	 * 
	 * @return
	 * 		the e-auth id to return
	 */
	public String getEauthId();

	/**
	 * Sets the user e-auth id.
	 * 
	 * @param eauthid
	 * 		the e-auth id to set
	 */
	public void setEauthId(String eauthid);

	public Collection<GridColumnUser> getGridColumnsUser(); 

	public void setGridColumnsUser(Collection<GridColumnUser> list); 

	/**
	 * Returns the restrictedIncidentUsers.
	 *
	 * @return 
	 *		the restrictedIncidentUsers to return
	 */
	public Collection<RestrictedIncidentUser> getRestrictedIncidentUsers();

	/**
	 * Sets the restrictedIncidentUsers.
	 *
	 * @param restrictedIncidentUsers 
	 *			the restrictedIncidentUsers to set
	 */
	public void setRestrictedIncidentUsers(Collection<RestrictedIncidentUser> restrictedIncidentUsers);

	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate();

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);

	/**
	 * Returns the systemRoles.
	 *
	 * @return 
	 *		the systemRoles to return
	 */
	public Collection<SystemRole> getSystemRoles();

	/**
	 * Sets the systemRoles.
	 *
	 * @param systemRoles 
	 *			the systemRoles to set
	 */
	public void setSystemRoles(Collection<SystemRole> systemRoles);

	/**
	 * Returns the userNotifications.
	 *
	 * @return 
	 *		the userNotifications to return
	 */
	public Collection<UserNotification> getUserNotifications();

	/**
	 * Sets the userNotifications.
	 *
	 * @param userNotifications 
	 *			the userNotifications to set
	 */
	public void setUserNotifications(Collection<UserNotification> userNotifications) ;

	/**
	 * Returns the userGroups.
	 *
	 * @return 
	 *		the userGroups to return
	 */
	public Collection<UserGroup> getUserGroups();

	/**
	 * Sets the userGroups.
	 *
	 * @param userGroups 
	 *			the userGroups to set
	 */
	public void setUserGroups(Collection<UserGroup> userGroups);

	/**
	 * Returns the userGroupUsers.
	 *
	 * @return 
	 *		the userGroupUsers to return
	 */
	public Collection<UserGroupUser> getUserGroupUsers();

	/**
	 * Sets the userGroupUsers.
	 *
	 * @param userGroupUsers 
	 *			the userGroupUsers to set
	 */
	public void setUserGroupUsers(Collection<UserGroupUser> userGroupUsers);

	/**
	 * Returns the robAgreementDate.
	 *
	 * @return 
	 *		the robAgreementDate to return
	 */
	public Date getRobAgreementDate();

	/**
	 * Sets the robAgreementDate.
	 *
	 * @param robAgreementDate 
	 *			the robAgreementDate to set
	 */
	public void setRobAgreementDate(Date robAgreementDate) ;

	/**
	 * Returns the incidentGroupUsers.
	 *
	 * @return 
	 *		the incidentGroupUsers to return
	 */
	public Collection<IncidentGroupUser> getIncidentGroupUsers();

	/**
	 * Sets the incidentGroupUsers.
	 *
	 * @param incidentGroupUsers 
	 *			the incidentGroupUsers to set
	 */
	public void setIncidentGroupUsers(Collection<IncidentGroupUser> incidentGroupUsers);

	/**
	 * @return the systemNotifyUsers
	 */
	public Collection<SystemNotifyUser> getSystemNotifyUsers() ;

	/**
	 * @param systemNotifyUsers the systemNotifyUsers to set
	 */
	public void setSystemNotifyUsers(Collection<SystemNotifyUser> systemNotifyUsers) ;


	/**
	 * @return the userSessionLogs
	 */
	public Collection<UserSessionLog> getUserSessionLogs() ;

	/**
	 * @param userSessionLogs the userSessionLogs to set
	 */
	public void setUserSessionLogs(Collection<UserSessionLog> userSessionLogs) ;

	public Collection<UserIncidentViewExclude> getUserIncidentViewExcludes() ;

	public void setUserIncidentViewExcludes(
			Collection<UserIncidentViewExclude> userIncidentViewExcludes) ;	

	public StringBooleanEnum getShowDataSavedMsg(); 

	public void setShowDataSavedMsg(StringBooleanEnum showDataSavedMag);


	/**
	 * @return the isFromNap
	 */
	public StringBooleanEnum getIsFromNap() ;

	/**
	 * @param isFromNap the isFromNap to set
	 */
	public void setIsFromNap(StringBooleanEnum isFromNap) ;
}	
