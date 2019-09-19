package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SpecialPay;
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
@SequenceGenerator(name="SEQ_SPECIAL_PAY", sequenceName="SEQ_SPECIAL_PAY")
@Table(name = "iswl_special_pay")
public class SpecialPayImpl extends PersistableImpl implements SpecialPay {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SPECIAL_PAY")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;

   @Column(name = "IS_AVAIL_TO_AD",nullable=false)
   private Boolean availableToAd;

   @Column(name = "IS_AVAIL_TO_FED",nullable=false)
   private Boolean availableToFed;

   @Column(name = "IS_AVAIL_TO_OTHER",nullable=false)
   private Boolean availableToOther;
   
   @Column(name = "ORDINAL_VALUE")
   private Integer ordinalValue;
   
   public SpecialPayImpl() {
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
      SpecialPayImpl o = (SpecialPayImpl)obj;
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

   public Boolean getAvailableToAd() {
	   return availableToAd;
   }

   public void setAvailableToAd(Boolean availableToAd) {
	   this.availableToAd = availableToAd;
   }

   public Boolean getAvailableToFed() {
	   return availableToFed;
   }

   public void setAvailableToFed(Boolean availableToFed) {
	   this.availableToFed = availableToFed;
   }

   public Boolean getAvailableToOther() {
	   return availableToOther;
   }

   public void setAvailableToOther(Boolean availableToOther) {
	   this.availableToOther = availableToOther;
   }

	public void setOrdinalValue(Integer ordinalValue) {
		this.ordinalValue = ordinalValue;
	}
	
	public Integer getOrdinalValue() {
		return ordinalValue;
	}   
}
