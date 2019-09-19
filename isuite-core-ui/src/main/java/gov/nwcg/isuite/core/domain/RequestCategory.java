package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RequestCategory extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the request category code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the request category code.
    * 
    * @return 
    * 		the request category code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the request category code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the request category code description.
    * 
    * @return 
    * 		the request category code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the request category code is a standard system code.
    * 
    * @return 
    * 		the request category code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the request category code is a standard system code.
    * 
    * @param isStandard
    * 			the request category code standard status
    */
   public void setStandard(Boolean isStandard);

}