package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

/**
 * Object Relational Mapping of ISW_WORK_AREA_RESOURCE.
 * 
 * @author bsteiner
 */
@Entity()
@Table(name="isw_work_area_resource")
public class WorkAreaResourceImpl implements WorkAreaResource {

   @ManyToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID", insertable=false, updatable=false, unique=false, nullable=false)
   @ForeignKey(name="FK_WAR__WORK_AREA_ID")
   private WorkArea workArea;
   
   @Id
   @Column(name="WORK_AREA_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long workAreaId;
   
   @ManyToOne(targetEntity=ResourceImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="RESOURCE_ID", insertable=false, updatable=false, unique=false, nullable=false)
   @ForeignKey(name="FK_WAR__RESOURCE_ID")
   private Resource resource;
   
   @Id
   @Column(name="RESOURCE_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long resourceId;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#getWorkArea()
    */
   public WorkArea getWorkArea() {
      return this.workArea;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#getWorkAreaId()
    */
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#setWorkArea(gov.nwcg.isuite.domain.access.WorkArea)
    */
   public void setWorkArea(WorkArea workArea) {
      this.workArea = workArea;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#setWorkAreaId(java.lang.Long)
    */
   public void setWorkAreaId(Long workAreaId) {
      this.workAreaId = workAreaId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#getResource()
    */
   public Resource getResource() {
      return this.resource;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#getResourceId()
    */
   public Long getResourceId() {
      return this.resourceId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#setResource(gov.nwcg.isuite.domain.resource.Resource)
    */
   public void setResource(Resource resource) {
      this.resource = resource;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResource#setResourceId(java.lang.Long)
    */
   public void setResourceId(Long resourceId) {
      this.resourceId = resourceId;
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
      WorkAreaResourceImpl o = (WorkAreaResourceImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{resourceId,workAreaId},
      			new Object[]{o.resourceId,o.workAreaId})
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(new Object[]{resourceId,workAreaId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("resourceId", resourceId)
	       .append("workAreaId", workAreaId)
	       .toString();
   }   

}
