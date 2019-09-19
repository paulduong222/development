package gov.nwcg.isuite.framework.core.domain.impl;

import gov.nwcg.isuite.framework.core.domain.Auditable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Auditable fields to be added to tables.
 * 
 * @author bsteiner
 */
@Embeddable
public class AuditableImpl implements Auditable {

   @Column(name="CREATED_BY_ID", nullable=true)
   private Long createdById;
	
   @Column(name="CREATED_BY", length=50, nullable=true)
   private String createdBy;
   
   @Column(name="CREATED_DATE", nullable=true)
   private Date createdDate;
   
   @Column(name="LAST_MODIFIED_BY_ID", nullable=true)
   private Long lastModifiedById;
   
   @Column(name="LAST_MODIFIED_BY", length=50, nullable=true)
   private String lastModifiedBy;
   
   @Column(name="LAST_MODIFIED_DATE", nullable=true)
   private Date lastModifiedDate;
   

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getCreatedBy()
    */
   public String getCreatedBy() {
      return this.createdBy;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getCreatedDate()
    */
   public Date getCreatedDate() {
      return this.createdDate;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getLastModifiedBy()
    */
   public String getLastModifiedBy() {
      return this.lastModifiedBy;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getLastModifiedDate()
    */
   public Date getLastModifiedDate() {
      return this.lastModifiedDate;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getCreatedBy(java.lang.String)
    */
   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getLastModifiedBy(java.lang.String)
    */
   public void setLastModifiedBy(String lastModifiedBy) {
      this.lastModifiedBy = lastModifiedBy;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#getLastModifiedDate(java.util.Date)
    */
   public void setLastModifiedDate(Date lastModifiedDate) {
      this.lastModifiedDate = lastModifiedDate;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Auditable#setCreatedDate(java.util.Date)
    */
   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
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
       AuditableImpl impl = (AuditableImpl)obj;
       return new EqualsBuilder()
 	      .append(createdBy, impl.createdBy)
 	      .append(createdDate, impl.createdDate)
 	      .append(lastModifiedBy, impl.lastModifiedBy)
 	      .append(lastModifiedDate,impl.lastModifiedDate)
 	      .isEquals();
    }   

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
 	  return new HashCodeBuilder(31,33)
 	  	.append(createdBy)
 	  	.append(createdDate)
 	  	.append(lastModifiedBy)
 	  	.append(lastModifiedDate)
 	  	.toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
 	   return new ToStringBuilder(this)
 	       .append("createdBy", createdBy)
 	       .append("createdDate", createdDate)
 	       .append("lastModifiedBy", lastModifiedBy)
 	       .append("lastModifiedDate", lastModifiedDate)
 	       .toString();
    }

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}       
}
