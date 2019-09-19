/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.types.InputDataSourceEnum;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Embeddable
public class InputDataSourceImpl implements InputDataSource {

   @Column(name = "INPUT_DATA_SOURCE_ENUM")
   @Enumerated(EnumType.STRING)
   private InputDataSourceEnum inputSourceEnum;
   
   @Column(name = "ORIGINATOR")
   private String originator;
   
   /**
    * Full Constructor.
    * @param sourceEnum can't be null
    * @param originator originator of data, null if not known
    */
   public InputDataSourceImpl(final InputDataSourceEnum sourceEnum, final String originator) {
      if ( sourceEnum == null ) {
         throw new IllegalArgumentException("sourceEnum can not be null");
      }
      this.inputSourceEnum = sourceEnum;
      this.originator = originator;
   }
   
   /**
    * Constructor.
    * @param sourceEnum can't be null
    */
   public InputDataSourceImpl(final InputDataSourceEnum sourceEnum) {
      if ( sourceEnum == null ) {
         throw new IllegalArgumentException("sourceEnum can not be null");
      }
      this.inputSourceEnum = sourceEnum;
      this.originator = null;
   }
   
   public InputDataSourceImpl() {
      this.inputSourceEnum = InputDataSourceEnum.UNKNOWN;
      this.originator = null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.input.InputDataSource#getEnum()
    */
   public InputDataSourceEnum getInputSourceEnum() {
      return inputSourceEnum;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.input.InputDataSource#getOriginator()
    */
   public String getOriginator() {
      return originator;
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
      InputDataSourceImpl o = (InputDataSourceImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{inputSourceEnum,originator},
      			new Object[]{o.inputSourceEnum,o.originator})
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(new Object[]{inputSourceEnum,originator})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("inputSourceEnum", inputSourceEnum)
	       .append("originator", originator)
	       .toString();
   }   

}
