package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.OrganizationTypeEnum;


/**
 * @author bsteiner
 *
 */
public interface OrganizationFilter extends Filter {
   
   /**
    * 
    * @param organizationType
    */
   public void setOrganizationType(OrganizationTypeEnum organizationType);
   
   public OrganizationTypeEnum getOrganizationType();
   
   public void setOrganizationAbbreviation(String organizationAbbreviation);
   
   public String getOrganizationAbbreviation();
   
   public String getUnitCode();
   
   public void setOrganizationName(String organizationName);
   
   public String getOrganizationName();
   
   public void setUnitCode(String unitCode);
   
   public String getCountrySubAbbreviation();
   
   public void setCountrySubAbbreviation(String countrySubAbbreviation);

}
