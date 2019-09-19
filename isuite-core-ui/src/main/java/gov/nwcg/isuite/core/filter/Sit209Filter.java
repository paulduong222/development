package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface Sit209Filter extends Filter {
   
   /**
    * Retrieves the Sit209 Code
    * @return Sit209 Code
    * @see #setCode(String)
    */
   public String getCode();
   
   /**
    * Sets the Sit209 Code
    * @param code
    * @see #getCode()
    */
   public void setCode(String code);

   /**
    * Retrieves the Sit209 Description
    * @return Sit209 Description
    * @see #setDescription(String)
    */
   public String getDescription();
   
   /**
    * Sets the Sit209 Description
    * @param description
    * @see #getDescription()
    */
   public void setDescription(String description);
   

   public void setStandard(Boolean standard);
   
   public Boolean getStandard();
   
   public void setDeletable(Boolean val);
   
   public Boolean getDeletable();
   
   /**
	 * @param incident the incident to set
	 */
	public void setIncident(String incident);
	
	/**
	 * @return the incident
	 */
	public String getIncident();

}
