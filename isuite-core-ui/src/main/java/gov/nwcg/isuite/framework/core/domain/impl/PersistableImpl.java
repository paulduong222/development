package gov.nwcg.isuite.framework.core.domain.impl;

import gov.nwcg.isuite.framework.core.domain.Auditable;
import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Implementation of a persistent object.
 * @author dougAnderson
 *
 */
@MappedSuperclass
public abstract class PersistableImpl implements Persistable, Auditable {

   @XStreamOmitField
   @Embedded
   private AuditableImpl auditable = new AuditableImpl();
   
   public PersistableImpl() {}
   
   /*
    * 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public abstract Long getId();

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public abstract void setId(Long id);

   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getCreatedBy()
    */
   public String getCreatedBy() {
	   if(null==auditable)auditable=new AuditableImpl();
      return auditable.getCreatedBy();
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getCreatedDate()
    */
   public Date getCreatedDate() {
	   if(null==auditable)auditable=new AuditableImpl();
	   return auditable.getCreatedDate();
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getLastModifiedBy()
    */
   public String getLastModifiedBy() {
	   if(null==auditable)auditable=new AuditableImpl();
      return auditable.getLastModifiedBy();
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getLastModifiedDate()
    */
   public Date getLastModifiedDate() {
	   if(null==auditable)auditable=new AuditableImpl();
      return auditable.getLastModifiedDate();
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#setCreatedBy(java.lang.String)
    */
   public void setCreatedBy(String createdBy) {
      auditable.setCreatedBy(createdBy);
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#setCreatedDate(java.util.Date)
    */
   public void setCreatedDate(Date createdDate) {
	   if(null==auditable)
		   auditable= new AuditableImpl();
      auditable.setCreatedDate(createdDate);
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#setLastModifiedBy(java.lang.String)
    */
   public void setLastModifiedBy(String lastModifiedBy) {
	   if(null==auditable)
		   auditable= new AuditableImpl();
      auditable.setLastModifiedBy(lastModifiedBy);
   }
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#setLastModifiedDate(java.util.Date)
    */
   public void setLastModifiedDate(Date lastModifiedDate) {
	   if(null==auditable)
		   auditable= new AuditableImpl();
      auditable.setLastModifiedDate(lastModifiedDate);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.Persistable#setCreatedById(java.lang.Long)
    */
   public void setCreatedById(Long userId){
	   if(null==auditable)
		   auditable= new AuditableImpl();
	   auditable.setCreatedById(userId);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.Persistable#getCreatedById()
    */
   public Long getCreatedById(){
	   if(null==auditable)auditable=new AuditableImpl();
	   return auditable.getCreatedById();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.Persistable#setLastModifiedById(java.lang.Long)
    */
   public void setLastModifiedById(Long userId){
	   if(null==auditable)
		   auditable= new AuditableImpl();
	   auditable.setLastModifiedById(userId);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.Persistable#getLastModifiedById()
    */
   public Long getLastModifiedById(){
	   if(null==auditable)auditable=new AuditableImpl();
	   return auditable.getLastModifiedById();
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
       PersistableImpl impl = (PersistableImpl)obj;
       return new EqualsBuilder()
 	      .append(auditable, impl.auditable)
 	      .isEquals();
    }   

    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
 	  return new HashCodeBuilder(31,33)
 	  	.append(auditable)
 	  	.toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
 	   return new ToStringBuilder(this)
 	       .append("auditable", auditable)
 	       .toString();
    }    
}
