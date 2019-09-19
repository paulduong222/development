package gov.nwcg.isuite.framework.input.impl;

import gov.nwcg.isuite.framework.input.InputDataSource;



public class OfficeTypeInputData extends InputDataImpl {

   private static final long serialVersionUID = 337104635765833412L;
   private String            name;

   /**
    * Constructor.
    * 
    * @param uniqueIdentifier
    *           unique identifier, can't be null
    * @param source
    *           source of data, can't be null
    * @param name
    *           name of office type, can't be null
    */
   public OfficeTypeInputData(String uniqueIdentifier, InputDataSource source, String name) {
      super(uniqueIdentifier, source);
      this.name = name;
   }

   /**
    * Accessor
    * 
    * @return the name
    */
   final public String getName() {
      return name;
   }

   /**
    * Accessor
    * 
    * @param name
    *           name of office type, can't be null
    */
   final void setName(String name) {
      if ( name == null ) {
         throw new IllegalArgumentException("name can not be null");
      }
      this.name = name;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int PRIME = 31;
      int result = super.hashCode();
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
      final OfficeTypeInputData other = (OfficeTypeInputData) obj;
      if ( name == null ) {
         if ( other.name != null )
            return false;
      }
      else if ( !name.equals(other.name) )
         return false;
      return true;
   }


}
