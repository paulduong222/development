package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


/**
 * @author mgreen
 */

public interface KindFilter extends Filter {

   /**
    * @param code the kind code
    */
   public void setCode(String code);
   
   /**
    * @return the kind code
    */
   public String getCode();

   /**
    * @param description the kind code description
    */
   public void setDescription(String description);
   
   /**
    * @return the kind code description
    */
   public String getDescription();
   
   /**
    * @return standard
    */
   public Boolean isStandard();

   /**
    * @param isStandard the isStandard to set
    */
   public void setStandard(Boolean isStandard);
   
   /**
    * @return the rcCode
    */
   public String getRcCode();

   /**
    * @param rcCode the rcCode to set
    */
   public void setRcCode(String rcCode);
   
}
