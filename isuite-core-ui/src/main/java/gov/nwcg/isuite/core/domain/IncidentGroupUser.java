package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentGroupUser extends Persistable {

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId();

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id);

	/**
	 * Returns the user.
	 *
	 * @return 
	 *		the user to return
	 */
	public User getUser();

	/**
	 * Sets the user.
	 *
	 * @param user 
	 *			the user to set
	 */
	public void setUser(User user);

	/**
	 * Returns the userId.
	 *
	 * @return 
	 *		the userId to return
	 */
	public Long getUserId();

	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *			the userId to set
	 */
	public void setUserId(Long userId);

	/**
	 * Returns the incidentGroup.
	 *
	 * @return 
	 *		the incidentGroup to return
	 */
	public IncidentGroup getIncidentGroup() ;
	
	/**
	 * Sets the incidentGroup.
	 *
	 * @param incidentGroup 
	 *			the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) ;

	/**
	 * Returns the incidentGroupId.
	 *
	 * @return 
	 *		the incidentGroupId to return
	 */
	public Long getIncidentGroupId();

	/**
	 * Sets the incidentGroupId.
	 *
	 * @param incidentGroupId 
	 *			the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId); 

//	/**
//	 * Returns the incidentGroupUserRoles.
//	 *
//	 * @return 
//	 *		the incidentGroupUserRoles to return
//	 */
//	public Collection<SystemRole> getIncidentGroupUserRoles();
//
//	/**
//	 * Sets the incidentGroupUserRoles.
//	 *
//	 * @param incidentGroupUserRoles 
//	 *			the incidentGroupUserRoles to set
//	 */
//	public void setIncidentGroupUserRoles(Collection<SystemRole> incidentGroupUserRoles) ;

}
