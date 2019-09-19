package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemModulePerm extends Persistable {
   
	/**
	 * Returns the systemModule.
	 *
	 * @return 
	 *		the systemModule to return
	 */
	public SystemModule getSystemModule();

	/**
	 * Sets the systemModule.
	 *
	 * @param systemModule 
	 *			the systemModule to set
	 */
	public void setSystemModule(SystemModule systemModule);

	/**
	 * Returns the systemModuleId.
	 *
	 * @return 
	 *		the systemModuleId to return
	 */
	public Long getSystemModuleId();

	/**
	 * Sets the systemModuleId.
	 *
	 * @param systemModuleId 
	 *			the systemModuleId to set
	 */
	public void setSystemModuleId(Long systemModuleId);

	/**
	 * Returns the permissionKey.
	 *
	 * @return 
	 *		the permissionKey to return
	 */
	public String getPermissionKey();

	/**
	 * Sets the permissionKey.
	 *
	 * @param permissionKey 
	 *			the permissionKey to set
	 */
	public void setPermissionKey(String permissionKey);

	/**
	 * Returns the description.
	 *
	 * @return 
	 *		the description to return
	 */
	public String getDescription();

	/**
	 * Sets the description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description);

	/**
	 * Returns the systemRolePerms.
	 *
	 * @return 
	 *		the systemRolePerms to return
	 */
	public Collection<SystemRolePerm> getSystemRolePerms();

	/**
	 * Sets the systemRolePerms.
	 *
	 * @param systemRolePerms 
	 *			the systemRolePerms to set
	 */
	public void setSystemRolePerms(Collection<SystemRolePerm> systemRolePerms);
	

}
