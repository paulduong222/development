package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RateArea extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the rate area code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the rate area code.
    * 
    * @return 
    * 		the rate area code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the rate area code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the rate area code description.
    * 
    * @return 
    * 		the rate area code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the rate area code is a standard system code.
    * 
    * @return 
    * 		the rate area code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the rate area code is a standard system code.
    * 
    * @param isStandard
    * 			the rate area code standard status
    */
   public void setStandard(Boolean isStandard);

}