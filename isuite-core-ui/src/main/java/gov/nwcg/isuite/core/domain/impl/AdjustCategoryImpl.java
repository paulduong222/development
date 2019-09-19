package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdjustCategory;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ADJUST_CATEGORY", sequenceName="SEQ_ADJUST_CATEGORY")
@Table(name = "iswl_adjust_category")
public class AdjustCategoryImpl extends PersistableImpl implements AdjustCategory {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ADJUST_CATEGORY")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;

   @Column(name="ADJUSTMENT_TYPE", length=10)
   @Enumerated(EnumType.STRING)
   private AdjustmentTypeEnum adjustmentType;
   
   @OneToMany(targetEntity=TimeAssignAdjustImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adjustCategory")
   private Collection<TimeAssignAdjust> timeAssignAdjusts;
   
   public AdjustCategoryImpl() {
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
    * @see gov.nwcg.isuite.core.domain.AdjustCategory#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.AdjustCategory#getCode()
    */
   public String getCode() {
      return this.code;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.AdjustCategory#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.AdjustCategory#getDescription()
    */
   public String getDescription() {
      return this.description;
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
      AdjustCategoryImpl o = (AdjustCategoryImpl)obj;
      return new EqualsBuilder()
      	  .append(new Object[]{id,code,description},
      			  new Object[]{o.id,o.code,o.description})
      	  .appendSuper(super.equals(o))
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,code,description,})
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
	       .appendSuper(super.toString())
	       .toString();
   }

	/**
	 * @return the adjustmentType
	 */
	public AdjustmentTypeEnum getAdjustmentType() {
		return adjustmentType;
	}
	
	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(AdjustmentTypeEnum adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	/**
	 * @return the timeAssignAdjust
	 */
	public Collection<TimeAssignAdjust> getTimeAssignAdjusts() {
		return timeAssignAdjusts;
	}

	/**
	 * @param timeAssignAdjust the timeAssignAdjust to set
	 */
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts) {
		this.timeAssignAdjusts = timeAssignAdjusts;
	}   
}
