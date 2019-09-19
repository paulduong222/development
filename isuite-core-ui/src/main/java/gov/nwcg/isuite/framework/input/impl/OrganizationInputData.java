package gov.nwcg.isuite.framework.input.impl;

import gov.nwcg.isuite.framework.input.InputDataSource;

/**
 * Represents the data of an organization. 
 * @author doug
 *
 */
public class OrganizationInputData extends InputDataImpl {


   private static final long serialVersionUID = -8046237951403368553L;
   private String            name;
   private String            abbreviation;
   private String            parentOrganizationId;
   private String            officeTypeIdentifier;
   private boolean           providerHost;
   private String            countryCodeIdentifier;
   private String            countryCodeSubdivisionIdentifer;
   private String            unitCode;
   private String            subUnitCode;
   private boolean           inactive;

   /**
    * Constructor.
    * 
    * @param uniqueIdentifier,
    *           can't be null
    * @param source
    *           source of data can't be null
    * @param name,
    *           can't be null
    * @param abbreviation,
    *           can't be null
    * @param parentOrganizationId,
    *           can be null
    * @param officeTypeIdentifier,
    *           can't be null
    * @param providerHost,
    *           can't be null
    * @param countryCodeIdentifier,
    *           can't be null
    * @param countryCodeSubdivisionIdentifer,
    *           can't be null
    * @param unitCode,
    *           can't be null
    * @param subUnitCode
    * @param inactive,
    *           can't be null
    */
   public OrganizationInputData(String uniqueIdentifier, InputDataSource source, String name, String abbreviation,
            String parentOrganizationId, String officeTypeIdentifier, boolean providerHost,
            String countryCodeIdentifier, String countrySubdivisionCodeIdentifer, String unitCode, String subUnitCode,
            boolean inactive) {
      super(uniqueIdentifier, source);
      setName(name);
      setAbbreviation(abbreviation);
      setParentOrganizationId(parentOrganizationId);
      setOfficeTypeIdentifier(officeTypeIdentifier);
      setProviderHost(providerHost);
      setCountryCodeIdentifier(countryCodeIdentifier);
      setCountryCodeSubdivisionIdentifer(countrySubdivisionCodeIdentifer);
      setUnitCode(unitCode);
      setSubUnitCode(subUnitCode);
      setInactive(inactive);
   }

   /**
    * Accessor.
    * 
    * @return the abbreviation
    */
   public final String getAbbreviation() {
      return abbreviation;
   }

   /**
    * Accessor.
    * 
    * @return the parentOrganizationId
    */
   public final String getParentOrganizationId() {
      return parentOrganizationId;
   }

   /**
    * Accessor.
    * 
    * @return the countryCodeIdentifier
    */
   public final String getCountryCodeIdentifier() {
      return countryCodeIdentifier;
   }

   /**
    * Accessor.
    * 
    * @return the countryCodeSubdivisionIdentifer
    */
   public final String getCountryCodeSubdivisionIdentifer() {
      return countryCodeSubdivisionIdentifer;
   }

   /**
    * Accessor.
    * 
    * @return the inactive
    */
   public final boolean isInactive() {
      return inactive;
   }

   /**
    * Accessor.
    * 
    * @return the name
    */
   public final String getName() {
      return name;
   }

   /**
    * Accessor.
    * 
    * @return the officeTypeIdentifier
    */
   public final String getOfficeTypeIdentifier() {
      return officeTypeIdentifier;
   }

   /**
    * Accessor.
    * 
    * @return the providerHost
    */
   public final boolean isProviderHost() {
      return providerHost;
   }

   /**
    * Accessor.
    * 
    * @return the subUnitCode
    */
   public final String getSubUnitCode() {
      return subUnitCode;
   }

   /**
    * Accessor.
    * 
    * @return the unitCode
    */
   public final String getUnitCode() {
      return unitCode;
   }

   /**
    * Accessor.
    * 
    * @param abbreviation
    *           the abbreviation to set, can't be null
    */
   final void setAbbreviation(String abbreviation) {
      if ( abbreviation == null ) {
         throw new IllegalArgumentException("abbreviation can not be null");
      }
      this.abbreviation = abbreviation;
   }

   final void setParentOrganizationId(String parentOrganizationId) {
      this.parentOrganizationId = parentOrganizationId;
   }

   /**
    * Accessor.
    * 
    * @param countryCodeIdentifier
    *           the countryCodeIdentifier to set, can't be null
    */
   final void setCountryCodeIdentifier(String countryCodeIdentifier) {
      if ( countryCodeIdentifier == null ) {
         throw new IllegalArgumentException("countryCodeIdentifier  can not be null");
      }
      this.countryCodeIdentifier = countryCodeIdentifier;
   }

   /**
    * Accessor.
    * 
    * @param countryCodeSubdivisionIdentifer
    *           the countryCodeSubdivisionIdentifer to set, can't be null
    */
   final void setCountryCodeSubdivisionIdentifer(String countrySubdivisionCodeIdentifer) {
      if ( countrySubdivisionCodeIdentifer == null ) {
         throw new IllegalArgumentException("countryCodeSubdivisionIdentifer can not be null");
      }
      this.countryCodeSubdivisionIdentifer = countrySubdivisionCodeIdentifer;
   }

   /**
    * Accessor.
    * 
    * @param inactive
    *           the inactive to set, can't be null
    */
   final void setInactive(boolean inactive) {
      this.inactive = inactive;
   }

   /**
    * Accessor.
    * 
    * @param name
    *           the name to set, can't be null
    */
   final void setName(String name) {
      if ( name == null ) {
         throw new IllegalArgumentException("name can not be null");
      }
      this.name = name;
   }

   /**
    * Accessor.
    * 
    * @param officeTypeIdentifier
    *           the officeTypeIdentifier to set, can't be null
    */
   final void setOfficeTypeIdentifier(String officeTypeIdentifier) {
      if ( officeTypeIdentifier == null ) {
         throw new IllegalArgumentException("officeTypeIdentifier can not be null");
      }
      this.officeTypeIdentifier = officeTypeIdentifier;
   }

   /**
    * Accessor.
    * 
    * @param providerHost
    *           the providerHost to set, can't be null
    */
   final void setProviderHost(boolean providerHost) {
      this.providerHost = providerHost;
   }

   /**
    * Accessor.
    * 
    * @param subUnitCode
    *           the subUnitCode
    */
   final void setSubUnitCode(String subUnitCode) {
      this.subUnitCode = subUnitCode;
   }

   /**
    * Accessor.
    * 
    * @param unitCode
    *           the unitCode to set, can't be null
    */
   final void setUnitCode(String unitCode) {
      if ( unitCode == null ) {
         throw new IllegalArgumentException("unitCode can not be null");
      }
      this.unitCode = unitCode;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int PRIME = 31;
      int result = super.hashCode();
      result = PRIME * result + ((countryCodeIdentifier == null) ? 0 : countryCodeIdentifier.hashCode());
      result = PRIME * result + ((countryCodeSubdivisionIdentifer == null) ? 0 : countryCodeSubdivisionIdentifer.hashCode());
      result = PRIME * result + ((name == null) ? 0 : name.hashCode());
      result = PRIME * result + ((parentOrganizationId == null) ? 0 : parentOrganizationId.hashCode());
      result = PRIME * result + ((subUnitCode == null) ? 0 : subUnitCode.hashCode());
      result = PRIME * result + ((unitCode == null) ? 0 : unitCode.hashCode());
      return result;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj) {
      if ( this == obj )
         return true;
      if ( !super.equals(obj) )
         return false;
      if ( getClass() != obj.getClass() )
         return false;
      final OrganizationInputData other = (OrganizationInputData) obj;
      
      if ( !countryCodeIdentifier.equals(other.countryCodeIdentifier) )
         return false;
      else if ( !countryCodeSubdivisionIdentifer.equals(other.countryCodeSubdivisionIdentifer) )
         return false;
      else if ( !name.equals(other.name) )
         return false;
      else if (parentOrganizationId == null) {
         if (other.parentOrganizationId != null) {
            return false;
         }
      }
      else if ( !parentOrganizationId.equals(other.parentOrganizationId) )
         return false;
      if ( subUnitCode == null ) {
         if ( other.subUnitCode != null )
            return false;
      }
      else if ( !subUnitCode.equals(other.subUnitCode) )
         return false;
      else if ( !unitCode.equals(other.unitCode) )
         return false;
      return true;
   }
   
}
