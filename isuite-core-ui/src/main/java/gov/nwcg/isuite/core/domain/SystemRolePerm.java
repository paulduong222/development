package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemRolePerm extends Persistable {
   
	/**
	 * Returns the systemModulePerm.
	 *
	 * @return 
	 *		the systemModulePerm to return
	 */
	public SystemModulePerm getSystemModulePerm();

	/**
	 * Sets the systemModulePerm.
	 *
	 * @param systemModulePerm 
	 *			the systemModulePerm to set
	 */
	public void setSystemModulePerm(SystemModulePerm systemModulePerm) ;

	/**
	 * Returns the systemModulePermId.
	 *
	 * @return 
	 *		the systemModulePermId to return
	 */
	public Long getSystemModulePermId();

	/**
	 * Sets the systemModulePermId.
	 *
	 * @param systemModulePermId 
	 *			the systemModulePermId to set
	 */
	public void setSystemModulePermId(Long systemModulePermId) ;

	/**
	 * Returns the systemRole.
	 *
	 * @return 
	 *		the systemRole to return
	 */
	public SystemRole getSystemRole() ;

	/**
	 * Sets the systemRole.
	 *
	 * @param systemRole 
	 *			the systemRole to set
	 */
	public void setSystemRole(SystemRole systemRole);

	/**
	 * Returns the systemRoleId.
	 *
	 * @return 
	 *		the systemRoleId to return
	 */
	public Long getSystemRoleId();

	/**
	 * Sets the systemRoleId.
	 *
	 * @param systemRoleId 
	 *			the systemRoleId to set
	 */
	public void setSystemRoleId(Long systemRoleId) ;

	/**
	 * Returns the roleFlag.
	 *
	 * @return 
	 *		the roleFlag to return
	 */
	public Boolean getRoleFlag();

	/**
	 * Sets the roleFlag.
	 *
	 * @param roleFlag 
	 *			the roleFlag to set
	 */
	public void setRoleFlag(Boolean roleFlag) ;
	
}
