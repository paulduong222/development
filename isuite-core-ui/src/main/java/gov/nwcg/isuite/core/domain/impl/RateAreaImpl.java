package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RateArea;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_RATE_AREA", sequenceName="SEQ_RATE_AREA")
@Table(name = "iswl_rate_area")
public class RateAreaImpl extends PersistableImpl implements RateArea {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RATE_AREA")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;
   
   public RateAreaImpl() {
      super();
   }

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public Long getId() {
      return this.id;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   } 

   
   public void setCode(String code) {
      this.code = code;
   }
   
   public String getCode() {
      return this.code;
   }

   public void setDescription(String description) {
      this.description = description;
   }
   
   public String getDescription() {
      return this.description;
   }
   
   public Boolean isStandard() {
      return this.standard;
   }

   public void setStandard(Boolean isStandard) {
      this.standard = isStandard;
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
      RateAreaImpl o = (RateAreaImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,code,description,standard},
      			new Object[]{o.id,o.code,o.description,o.standard})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,code,description,standard})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("code", code)
	       .append("description", description)
	       .append("standard",standard)
	       .appendSuper(super.toString())
	       .toString();
   }   
}
