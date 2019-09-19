package gov.nwcg.isuite.core.domain.impl;

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

import gov.nwcg.isuite.core.domain.RegionCode;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_REGION_CODE", sequenceName="SEQ_REGION_CODE")
@Table(name="iswl_region_code")
public class RegionCodeImpl extends PersistableImpl implements RegionCode {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_REGION_CODE")
   private Long id = 0L;
   
   @Column(name="code",length=10)
   private String code;
   
   @Column(name="description",length=75)
   private String description;
   
   /**
    * @return the id
    */
   public Long getId() {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(Long id) {
      this.id = id;
   }

   /**
    * @return the code
    */
   public String getCode() {
      return code;
   }

   /**
    * @param code the code to set
    */
   public void setCode(String code) {
      this.code = code;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
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
		RegionCodeImpl o = (RegionCodeImpl)obj;
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
		.append(new Object[]{id,code,description})
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

}
