package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface KindFunction extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the kind function code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the kind function code.
    * 
    * @return 
    * 		the kind function code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the kind function code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the kind function code description.
    * 
    * @return 
    * 		the kind function code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the kind function code is a standard system code.
    * 
    * @return 
    * 		the kind function code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the kind function code is a standard system code.
    * 
    * @param isStandard
    * 			the kind function code standard status
    */
   public void setStandard(Boolean isStandard);

}