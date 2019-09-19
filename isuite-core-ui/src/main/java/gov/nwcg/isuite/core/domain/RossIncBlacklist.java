package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossIncBlacklist extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the rossIncId
	 */
	public Long getRossIncId() ;


	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(Long rossIncId) ;


	/**
	 * @return the rossIncDataBlacklists
	 */
	public Collection<RossIncDataBlacklist> getRossIncDataBlacklists();

	/**
	 * @param rossIncDataBlacklists the rossIncDataBlacklists to set
	 */
	public void setRossIncDataBlacklists(Collection<RossIncDataBlacklist> rossIncDataBlacklists) ;
	
}
