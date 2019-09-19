package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface AgencyGroupFilter extends Filter {
   
   /**
    * Retrieves the AgencyGroup Code
    * @return AgencyGroup Code
    * @see #setCode(String)
    */
   public String getCode();
   
   /**
    * Sets the AgencyGroup Code
    * @param code
    * @see #getCode()
    */
   public void setCode(String code);

   /**
    * Retrieves the AgencyGroup Description
    * @return AgencyGroup Description
    * @see #setDescription(String)
    */
   public String getDescription();
   
   /**
    * Sets the AgencyGroup Description
    * @param description
    * @see #getDescription()
    */
   public void setDescription(String description);
   

   public void setStandard(Boolean standard);
   
   public Boolean getStandard();
   
   public void setDeletable(Boolean val);
   
   public Boolean getDeletable();

}
