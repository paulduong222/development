package gov.nwcg.isuite.core.domain;


public interface WorkAreaResource {

	/**
	 * Gets the work area.
	 * 
	 * @return the work area
	 */
	public WorkArea getWorkArea();
   
   /**
    * Sets the work area.
    * 
    * @param workArea the new work area
    */
   public void setWorkArea(WorkArea workArea);
   
   /**
    * Gets the work area id.
    * 
    * @return the work area id
    */
   public Long getWorkAreaId();
   
   /**
    * Sets the work area id.
    * 
    * @param workAreaId the new work area id
    */
   public void setWorkAreaId(Long workAreaId);
   
   /**
    * Gets the resource.
    * 
    * @return the resource
    */
   public Resource getResource();
   
   /**
    * Sets the resource.
    * 
    * @param resource the new resource
    */
   public void setResource(Resource resource);
   
   /**
    * Gets the resource id.
    * 
    * @return the resource id
    */
   public Long getResourceId();
   
   /**
    * Sets the resource id.
    * 
    * @param resourceId the new resource id
    */
   public void setResourceId(Long resourceId); 
}
   
