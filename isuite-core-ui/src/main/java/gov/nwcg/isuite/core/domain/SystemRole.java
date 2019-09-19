package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemRole extends Persistable {
   
	/**
	 * Returns the roleName.
	 *
	 * @return 
	 *		the roleName to return
	 */
	public String getRoleName();

	/**
	 * Sets the roleName.
	 *
	 * @param roleName 
	 *			the roleName to set
	 */
	public void setRoleName(String roleName);

	/**
	 * Returns the privilegedRole.
	 *
	 * @return 
	 *		the privilegedRole to return
	 */
	public Boolean getPrivilegedRole();

	/**
	 * Sets the privilegedRole.
	 *
	 * @param privilegedRole 
	 *			the privilegedRole to set
	 */
	public void setPrivilegedRole(Boolean privilegedRole);

	/**
	 * Returns the systemRolePerms.
	 *
	 * @return 
	 *		the systemRolePerms to return
	 */
	public Collection<SystemRolePerm> getSystemRolePerms() ;

	/**
	 * Sets the systemRolePerms.
	 *
	 * @param systemRolePerms 
	 *			the systemRolePerms to set
	 */
	public void setSystemRolePerms(Collection<SystemRolePerm> systemRolePerms) ;

	/**
	 * Returns the users.
	 *
	 * @return 
	 *		the users to return
	 */
	public Collection<User> getUsers() ;

	/**
	 * Returns the displayName.
	 *
	 * @return 
	 *		the displayName to return
	 */
	public String getDisplayName();

	/**
	 * Sets the displayName.
	 *
	 * @param displayName 
	 *			the displayName to set
	 */
	public void setDisplayName(String displayName);	
	
	/**
	 * @return the systemNotifications
	 */
	public Collection<SystemNotification> getSystemNotifications();

	/**
	 * @param systemNotifications the systemNotifications to set
	 */
	public void setSystemNotifications(
			Collection<SystemNotification> systemNotifications);
	
//	/**
//	 * @return the Custom Report Views
//	 */
//	public Collection<User> getCustomReportViews();
//
//	/**
//	 * @param customReportViews the Custom Report Views to set
//	 */
//	public void setCustomReportViews(Collection<User> customReportViews);
	
}
