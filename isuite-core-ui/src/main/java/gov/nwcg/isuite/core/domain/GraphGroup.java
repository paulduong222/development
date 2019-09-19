package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface GraphGroup extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the graph code code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the graph code code.
    * 
    * @return 
    * 		the graph code code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the graph code code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the graph code code description.
    * 
    * @return 
    * 		the graph code code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the graph code code is a standard system code.
    * 
    * @return 
    * 		the graph code code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the graph code code is a standard system code.
    * 
    * @param isStandard
    * 			the graph code code standard status
    */
   public void setStandard(Boolean isStandard);

}