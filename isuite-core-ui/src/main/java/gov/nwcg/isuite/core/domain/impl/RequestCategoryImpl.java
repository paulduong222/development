package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RequestCategory;
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
@SequenceGenerator(name="SEQ_REQUEST_CATEGORY", sequenceName="SEQ_REQUEST_CATEGORY")
@Table(name = "iswl_request_category")
public class RequestCategoryImpl extends PersistableImpl implements RequestCategory {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_REQUEST_CATEGORY")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;
   
   public RequestCategoryImpl() {
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

   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#getCode()
    */
   public String getCode() {
      return this.code;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#getDescription()
    */
   public String getDescription() {
      return this.description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#isStandard()
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.RequestCategory#setStandard(java.lang.Boolean)
    */
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
      RequestCategoryImpl o = (RequestCategoryImpl)obj;
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
