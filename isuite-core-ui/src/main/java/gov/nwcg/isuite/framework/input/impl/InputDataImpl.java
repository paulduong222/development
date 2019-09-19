package gov.nwcg.isuite.framework.input.impl;

import gov.nwcg.isuite.framework.input.InputData;
import gov.nwcg.isuite.framework.input.InputDataSource;

public class InputDataImpl implements InputData {
   
   private static final long serialVersionUID = -1365830610631764083L;
   private String uniqueIdentifier;
   private InputDataSource source;
   

   /**
    * Constructor.
    * @param uniqueIdentifier, can't be null
    * @param source, can't be null
    */
   public InputDataImpl(String uniqueIdentifier, InputDataSource source) {
      setUniqueIdentifier(uniqueIdentifier);
      setSource(source);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.sync.SyncData#getSource()
    */
   public InputDataSource getSource() {
      return source;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.sync.SyncData#getUniqueIdentifier()
    */
   public String getUniqueIdentifier() {
      return uniqueIdentifier;
   }

   /**
    * @param source the source to set
    */
   final void setSource(InputDataSource source) {
      if ( source == null ) {
         throw new IllegalArgumentException("source can not be null");
      }
      this.source = source;
   }

   /**
    * @param uniqueIdentifier the uniqueIdentifier to set
    */
   final void setUniqueIdentifier(String uniqueIdentifier) {
      if ( uniqueIdentifier == null ) {
         throw new IllegalArgumentException("uniqueIdentifier can not be null");
      }
      this.uniqueIdentifier = uniqueIdentifier;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int PRIME = 31;
      int result = 1;
      result = PRIME * result + ((source == null) ? 0 : source.hashCode());
      result = PRIME * result + ((uniqueIdentifier == null) ? 0 : uniqueIdentifier.hashCode());
      return result;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj) {
      if ( this == obj )
         return true;
      if ( obj == null )
         return false;
      if ( getClass() != obj.getClass() )
         return false;
      final InputDataImpl other = (InputDataImpl) obj;
      if ( source == null ) {
         if ( other.source != null )
            return false;
      }
      else if ( !source.equals(other.source) )
         return false;
      if ( uniqueIdentifier == null ) {
         if ( other.uniqueIdentifier != null )
            return false;
      }
      else if ( !uniqueIdentifier.equals(other.uniqueIdentifier) )
         return false;
      return true;
   }

}
