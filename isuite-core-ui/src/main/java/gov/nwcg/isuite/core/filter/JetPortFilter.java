package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


/**
 * @author mgreen
 */

public interface JetPortFilter extends Filter {

   /**
    * @param code the jet port code
    */
   public void setCode(String code);
   
   /**
    * @return the jet port code
    */
   public String getCode();

   /**
    * @param description the jet port description
    */
   public void setDescription(String description);
   
   /**
    * @return the jet port description
    */
   public String getDescription();
   
   /**
    * @return standard
    */
   public Boolean isStandard();

   /**
    * @return standard
    */
   public int getStandard();

   /**
    * @param isStandard the isStandard to set
    */
   public void setStandard(int isStandard);
   
   /**
    * @return the countrySubdivision
    */
   public String getCountrySubdivision();
   
   /**
    * @param countrySubdivision
    */
   public void setCountrySubdivision(String countrySubdivision);
   
}
