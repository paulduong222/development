package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemModule extends Persistable {
   
	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name);

	/**
	 * Returns the systemModulePerms.
	 *
	 * @return 
	 *		the systemModulePerms to return
	 */
	public Collection<SystemModulePerm> getSystemModulePerms();

	/**
	 * Sets the systemModulePerms.
	 *
	 * @param systemModulePerms 
	 *			the systemModulePerms to set
	 */
	public void setSystemModulePerms(Collection<SystemModulePerm> systemModulePerms);

}
