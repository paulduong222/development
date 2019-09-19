/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.types.OrganizationTypeEnum;

/**
 * @author bsteiner
 *
 */
public class OrganizationFilterImpl extends FilterImpl implements OrganizationFilter {

   private static final long serialVersionUID = -4993962947637970901L;

   private OrganizationTypeEnum organizationType;
   
   private String organizationName;
   
   private String organizationAbbreviation;
   
   private String unitCode;
   
   private String countrySubAbbreviation;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.OrganizationFilter#getOrganizationType()
    */
   public OrganizationTypeEnum getOrganizationType() {
      return this.organizationType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.OrganizationFilter#setOrganizationType(gov.nwcg.isuite.domain.organization.impl.OrganizationType)
    */
   public void setOrganizationType(OrganizationTypeEnum organizationType) {
      this.organizationType = organizationType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   public void reset() {
      this.organizationAbbreviation = null;
      this.organizationName = null;
      this.organizationType = null;
      this.unitCode = null;
      this.countrySubAbbreviation = null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.OrganizationFilter#getOrganizationAbbreviation()
    */
   public String getOrganizationAbbreviation() {
      return this.organizationAbbreviation;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.OrganizationFilter#getOrganizationName()
    */
   public String getOrganizationName() {
      return this.organizationName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.OrganizationFilter#getUnitCode()
    */
   public String getUnitCode() {
      return this.unitCode;
   }

   /**
    * @param unitCode the unitCode to set
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }

   /**
    * @param organizationAbbreviation the organizationAbbreviation to set
    */
   public void setOrganizationAbbreviation(String organizationAbbreviation) {
      this.organizationAbbreviation = organizationAbbreviation;
   }

   /**
    * @param organizationName the organizationName to set
    */
   public void setOrganizationName(String organizationName) {
      this.organizationName = organizationName;
   }
   
   public void setCountrySubAbbreviation(String countrySubAbbreviation) {
      this.countrySubAbbreviation = countrySubAbbreviation;
   }
   
   public String getCountrySubAbbreviation() {
      return this.countrySubAbbreviation;
   }

}
