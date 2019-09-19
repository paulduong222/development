package gov.nwcg.isuite.core.filter;


import gov.nwcg.isuite.framework.core.filter.Filter;


public interface CountrySubdivisionFilter extends Filter {
   
   /**
    * Set the Country Code Abbreviation
    * 
    * @param countryCodeAbbreviation
    */
   public void setCountryAbbreviation(String countryCodeAbbreviation);
   
   public String getCountryAbbreviation();
   
   /**
    * Set the Country Code ID
    * 
    * @param countryCodeId
    */
   public void setCountryCodeId(Long countryCodeId);
   
   public Long getCountryCodeId();
   
   /**
    * Set the Country Code Subdivision Code 
    * 
    * @param countryCodeSubdivisionCode
    */
   public void setCountrySubdivisionCode(String countryCodeSubdivisionCode);
   
   public String getCountrySubdivisionCode();
   
   /**
    * Set the Country Code Subdivision Name
    * 
    * @param countryCodeSubdivisionName
    */
   public void setCountrySubdivisionName(String countryCodeSubdivisionName);
   
   public String getCountrySubdivisionName();
   
   
  
   
}
