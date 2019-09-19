package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CountrySubdivisionFilterImpl extends FilterImpl implements CountrySubdivisionFilter {

   private static final long serialVersionUID = 6398599848853951079L;
   
   private String countryAbbreviation;
   private Long countryId;
   private String countrySubdivisionCode;
   private String countrySubdivisionName;
   
   
   public CountrySubdivisionFilterImpl() {
      // details in here should match the reset() method.
      this.reset();
   }
   
   public void reset() {
      this.countryAbbreviation = null;
      this.countryId = null;
      this.countrySubdivisionCode = null;
      this.countrySubdivisionName = null;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeAbbreviation()
    */
   public String getCountryAbbreviation() {
      return this.countryAbbreviation;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeId()
    */
   public Long getCountryCodeId() {
      return this.countryId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeSubdivisionCode()
    */
   public String getCountrySubdivisionCode() {
      return this.countrySubdivisionCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#getCountryCodeSubdivisionName()
    */
   public String getCountrySubdivisionName() {
      return this.countrySubdivisionName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeAbbreviation(java.lang.String)
    */
   public void setCountryAbbreviation(String countryCodeAbbreviation) {
      this.countryAbbreviation = countryCodeAbbreviation;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeId(java.lang.Long)
    */
   public void setCountryCodeId(Long countryCodeId) {
      this.countryId = countryCodeId;  
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeSubdivisionCode(java.lang.String)
    */
   public void setCountrySubdivisionCode(String countryCodeSubdivisionCode) {
      this.countrySubdivisionCode = countryCodeSubdivisionCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountrySubdivisionFilter#setCountryCodeSubdivisionName(java.lang.String)
    */
   public void setCountrySubdivisionName(String countryCodeSubdivisionName) {
      this.countrySubdivisionName = countryCodeSubdivisionName;
   }


}
