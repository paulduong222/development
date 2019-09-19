package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CountryCodeFilterImpl extends FilterImpl implements CountryCodeFilter {

   private static final long serialVersionUID = -6904104926787168094L;
   private String countryCodeAbbreviation;
   private String countryCodeName;

   public CountryCodeFilterImpl() {
      // details in here should match the reset() method.
      this.reset();
   }
   
   public void reset() {
      countryCodeAbbreviation = null;
      countryCodeName = null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeAbbreviation()
    */
   public String getCountryCodeAbbreviation() {
      return countryCodeAbbreviation;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeName()
    */
   public String getCountryCodeName() {
      return countryCodeName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeAbbreviation(java.lang.String)
    */
   public void setCountryCodeAbbreviation(String countryCodeAbbreviation) {
      this.countryCodeAbbreviation = countryCodeAbbreviation;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeName(java.lang.String)
    */
   public void setCountryCodeName(String countryCodeName) {
      this.countryCodeName = countryCodeName;
   }

}
