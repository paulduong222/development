package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface UserIncidentViewExclude extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	public Long getUserId();
	
	public void setUserId(Long userId);
	
	public User getUser();
	
	public void setUser(User user);
	
	public Long getIncidentId();
	
	public void setIncidentId(Long id);
	
	public Long getIncidentGroupId();
	
	public void setIncidentGroupId(Long id);
	
}
