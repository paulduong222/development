package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author dprice
 */
@Entity
@SequenceGenerator(name="SEQ_RESOURCE_KIND", sequenceName="SEQ_RESOURCE_KIND")
@Table(name="isw_resource_kind")
public class ResourceKindImpl extends PersistableImpl implements ResourceKind{

	
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE_KIND")
   private Long id = 0L;
	
	@ManyToOne(targetEntity=ResourceImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_ID", nullable = false, insertable = true, updatable = true)
	private Resource resource;

	@Column(name="RESOURCE_ID", insertable=false,updatable=false)
	private Long resourceId;
	
	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "KIND_ID", nullable = false, insertable = true, updatable = true)
	private Kind kind;

	@Column(name="KIND_ID",insertable=false,updatable=false)
	private Long kindId;
	
	@Column(name = "IS_TRAINING", nullable = false)
	private Boolean training=false;

	@Column(name = "IS_PRIMARY")
	private Boolean primary = false;
	
	public ResourceKindImpl(){
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceKindCode#getResource()
	 */
	public Resource getResource() {
		return resource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceKindCode#setResource(gov.nwcg.isuite.domain.resource.Resource)
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceKindCode#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceKindCode#setResourceId(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#getKind()
	 */
	public Kind getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#setKind(gov.nwcg.isuite.core.domain.Kind)
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#getKindId()
	 */
	public Long getKindId() {
		return kindId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#setKindId(java.lang.Long)
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#getTraining()
	 */
	public Boolean getTraining() {
		return training;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceKindCode#setTraining(java.lang.Boolean)
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#getPrimary()
	 */
	public Boolean getPrimary() {
		return primary;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceKind#setPrimary(java.lang.Boolean)
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
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

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      ResourceKindImpl o = (ResourceKindImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,kindId,resourceId,training},
      			new Object[]{o.id,o.kindId,o.resourceId,o.training})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,kindId,resourceId,training})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("kindId", kindId)
	       .append("resourceId", resourceId)
	       .append("training",training)
	       .appendSuper(super.toString())
	       .toString();
   }   
   
}
