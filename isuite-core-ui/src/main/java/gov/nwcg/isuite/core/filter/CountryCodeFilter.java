package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


public interface CountryCodeFilter extends Filter {
   
   /**
    * Set the Country Code Abbreviation
    * 
    * @param countryCodeAbbreviation
    */
   public void setCountryCodeAbbreviation(String countryCodeAbbreviation);
   
   public String getCountryCodeAbbreviation();
   
   /**
    * Set the Country Code Name
    * 
    * @param countryCodeName
    */
   public void setCountryCodeName(String countryCodeName);
   
   public String getCountryCodeName();
}
