package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


/**
 * 
 * @author dprice
 */
public interface ResourceStatusFilter extends Filter {

   /**
    * @param code
    * 			the code to set
    */
   public void setCode(String code);
   
   /**
    * @return the code
    */
   public String getCode();


   /**
    * @param description
    * 			the description to set
    */
   public void setDescription(String description);
   
   /**
    * @return the description
    */
   public String getDescription();
   
}
