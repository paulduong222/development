/**
 * 
 */
package gov.nwcg.isuite.framework.input.impl;

import gov.nwcg.isuite.framework.input.InputDataSource;

/**
 * Data for a country subdivision code.
 * @author doug
 *
 */
public class CountryCodeSubdivisionInputData extends InputDataImpl {

   private static final long serialVersionUID = -6297866646830332825L;
   private String name;
   private String abbreviation;
   

   /**
    * Constructor.
    * @param uniqueIdentifier, can't be null
    * @param name, can't be null
    * @param abbreviation, can't be null
    */
   public CountryCodeSubdivisionInputData(String uniqueIdentifier, InputDataSource source, String name, String abbreviation) {
      super(uniqueIdentifier, source);
      this.name = name;
      this.abbreviation = abbreviation;
   }

   /**
    * Accessor.
    * @return the abbreviation
    */
   public final String getAbbreviation() {
      return abbreviation;
   }


   /**
    * Accessor.
    * @return the name
    */
   public final String getName() {
      return name;
   }


   /**
    * Accessor.
    * @param abbreviation the abbreviation to set, can't be null
    */
   final void setAbbreviation(String abbreviation) {
      this.abbreviation = abbreviation;
   }


   /**
    * Accessor.
    * @param name the name to set, can't be null
    */
   final void setName(String name) {
      this.name = name;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int PRIME = 31;
      int result = super.hashCode();
      result = PRIME * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
      result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
      final CountryCodeSubdivisionInputData other = (CountryCodeSubdivisionInputData) obj;
      if ( abbreviation == null ) {
         if ( other.abbreviation != null )
            return false;
      }
      else if ( !abbreviation.equals(other.abbreviation) )
         return false;
      if ( name == null ) {
         if ( other.name != null )
            return false;
      }
      else if ( !name.equals(other.name) )
         return false;
      return true;
   }

}
