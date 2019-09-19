package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface ResourcePersonFilter extends Filter {

   /**
    * @return the firstName
    */
   public String getFirstName();

   /**
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName);

   /**
    * @return the lastName
    */
   public String getLastName();

   /**
    * @param lastName the lastName to set
    */
   public void setLastName(String lastName);

   /**
    * @return the unitCode
    */
   public String getUnitCode();

   /**
    * @param unitCode the unitCode to set
    */
   public void setUnitCode(String unitCode);
   
}
