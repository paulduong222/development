package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;


public interface ResourceKind extends Persistable {

	/**
	 * Returns the resource.
	 * 
	 * @return
	 * 		the resource to return
	 */
	public Resource getResource();
	
	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 * 			the resource to set
	 */
	public void setResource(Resource resource);
	
	/**
	 * Returns the resource id.
	 * 
	 * @return
	 * 		the resource id to return
	 */
	public Long getResourceId();
	
	/**
	 * Sets the resource id.
	 * 
	 * @param id
	 * 			the resource id to set
	 */
	public void setResourceId(Long id);

	/**
	 * Returns the kind.
	 * 
	 * @return
	 * 		the kind to return
	 */
	public Kind getKind();
	
	/**
	 * Sets the kind.
	 * 
	 * @param kind
	 * 			the kind to set
	 */
	public void setKind(Kind kind);
	
	/**
	 * Returns the kind id.
	 * 
	 * @return
	 * 		the kind id to return
	 */
	public Long getKindId();
	
	/**
	 * Sets the kind id.
	 * 
	 * @param id
	 * 		the kind id to set
	 */
	public void setKindId(Long id);

	/**
	 * Returns the training flag.
	 * 
	 * @return
	 * 		the training flag value to return
	 */
	public Boolean getTraining();
	
	/**
	 * Sets the training flag.
	 * 
	 * @param val
	 * 		the training flag value to set
	 */
	public void setTraining(Boolean val);
	
	/**
	 * Returns primary
	 * 
	 * @return
	 * 		the primary value to return
	 */
	public Boolean getPrimary();
	
	/**
	 * Sets primary
	 * 
	 * @param primary
	 * 		the primary value to set
	 */
	public void setPrimary(Boolean primary);
	
}
