package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface Cat1A extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the cat 1a code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the cat 1a code.
    * 
    * @return 
    * 		the cat 1a code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the cat 1a code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the cat 1a code description.
    * 
    * @return 
    * 		the cat 1a code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the cat 1a code is a standard system code.
    * 
    * @return 
    * 		the cat 1a code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the cat 1a code is a standard system code.
    * 
    * @param isStandard
    * 			the cat 1a code standard status
    */
   public void setStandard(Boolean isStandard);

}