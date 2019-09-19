package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.KindGroup;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_KIND_GROUP", sequenceName="SEQ_KIND_GROUP")
@Table(name = "iswl_kind_group")
@NamedQuery(name=ReferenceDataQuery.IS_KIND_GROUP_CODE_UNIQUE,query=ReferenceDataQuery.IS_KIND_GROUP_CODE_UNIQUE_QUERY)
public class KindGroupImpl extends PersistableImpl implements KindGroup {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_KIND_GROUP")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;
   
   public KindGroupImpl() {
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
    * @see gov.nwcg.isuite.core.domain.KindGroup#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.KindGroup#getCode()
    */
   public String getCode() {
      return this.code;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.KindGroup#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.KindGroup#getDescription()
    */
   public String getDescription() {
      return this.description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.KindGroup#isStandard()
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.KindGroup#setStandard(java.lang.Boolean)
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
      KindGroupImpl o = (KindGroupImpl)obj;
      return new EqualsBuilder()
	      .appendSuper(super.equals(obj))
	      .append(new Object[]{id,code,description,standard},
      			new Object[]{o.id,o.code,o.description,o.standard})
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	    .appendSuper(super.hashCode())
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
	       .append("standard", standard)
	       .appendSuper(super.toString())
	       .toString();
   }      
}
