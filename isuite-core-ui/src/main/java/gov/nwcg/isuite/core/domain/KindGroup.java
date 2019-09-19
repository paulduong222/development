package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface KindGroup extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the kind group code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the kind group code.
    * 
    * @return 
    * 		the kind group code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the kind group code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the kind group code description.
    * 
    * @return 
    * 		the kind group code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the kind group code is a standard system code.
    * 
    * @return 
    * 		the kind group code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the kind group code is a standard system code.
    * 
    * @param isStandard
    * 			the kind group code standard status
    */
   public void setStandard(Boolean isStandard);

}