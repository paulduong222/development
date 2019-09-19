package gov.nwcg.isuite.framework.core.domain.impl;

import gov.nwcg.isuite.framework.core.domain.Transferable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.safehaus.uuid.UUIDGenerator;

/**
 * Implementation of a persistent object.
 * 
 * @author dougAnderson
 */

@Embeddable
public class TransferableImpl implements Transferable {
   @Column(name = "TRANSFERABLE_IDENTITY", unique = true, updatable=false)
   private String identity;

   public TransferableImpl() {
      super();
      //identity = UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
   }

   public TransferableImpl(String identity) {
      if ( identity == null ) {
         this.identity = UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
      }
      else {
         this.identity = identity;
      }
   }

   public String getIdentity() {
      return this.identity;
   }

   public void setIdentity(String identity) {
      if ( identity == null ) {
         throw new IllegalArgumentException("identity can not be null");
      }
      this.identity = identity;
   }
   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      TransferableImpl impl = (TransferableImpl)obj;
      return new EqualsBuilder()
	      .append(identity, impl.identity)
	      .isEquals();
   }   

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(identity)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("identity", identity)
	       .toString();
   }       
}
