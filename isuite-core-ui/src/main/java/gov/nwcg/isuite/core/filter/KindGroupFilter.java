package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface KindGroupFilter extends Filter {
   
   /**
    * Retrieves the KindGroup Code
    * @return KindGroup Code
    * @see #setCode(String)
    */
   public String getCode();
   
   /**
    * Sets the KindGroup Code
    * @param code
    * @see #getCode()
    */
   public void setCode(String code);

   /**
    * Retrieves the KindGroup Description
    * @return KindGroup Description
    * @see #setDescription(String)
    */
   public String getDescription();
   
   /**
    * Sets the KindGroup Description
    * @param description
    * @see #getDescription()
    */
   public void setDescription(String description);
   

   public void setStandard(Boolean standard);
   
   public Boolean getStandard();
   
   public void setDeletable(Boolean val);
   
   public Boolean getDeletable();

}
