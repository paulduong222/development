package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface UserResourceInventoryViewExclude extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the userId
	 */
	public Long getUserId();
	
	/**
	 * @param userId
	 */
	public void setUserId(Long userId);
	
	/**
	 * @return the user
	 */
	public User getUser();
	
	/**
	 * @param user
	 */
	public void setUser(User user);
	
	/**
	 * @return the resourceId
	 */
	public Long getResourceId();
	
	/**
	 * @param id
	 */
	public void setResourceId(Long id);

}
