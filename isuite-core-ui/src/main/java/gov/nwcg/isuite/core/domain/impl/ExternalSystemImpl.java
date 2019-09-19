package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.ExternalSystem;
import gov.nwcg.isuite.core.domain.Schedule;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;
import gov.nwcg.isuite.framework.types.ExternalSystemEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author doug
 *
 */
@Entity
@Table(name = "ISW_EXTERNAL_SYSTEM")
public class ExternalSystemImpl extends PersistableImpl implements
		ExternalSystem {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
	@Column(name = "EXT_SYS_NAME")
	private String name;
	
	@Column(name = "EXT_SYS_TYPE")
	@Enumerated(EnumType.STRING)
	private ExternalSystemEnum type;
	
	@Column(name = "EXT_SYS_DIRECTION")
	@Enumerated(EnumType.STRING)
	private DataFlowDirectionEnum direction;
	
	
	@Embedded
	@AttributeOverrides ( {
		@AttributeOverride(name="type", column=@Column(name="EXT_SYS_SCHED_TYPE")),
		@AttributeOverride(name="time", column=@Column(name="EXT_SYS_SCHED_TIME"))
	})
	private Schedule schedule;
	
	public ExternalSystemImpl(){
		
	}

	public String getName() {
		return name;
	}

	public DataFlowDirectionEnum getDataFlowDirection() {
		return direction;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public ExternalSystemEnum getType() {
		return type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name can not be null");
		}
		this.name = name;	}

	/**
	 * @param type the type to set
	 */
	public void setType(ExternalSystemEnum type) {
		if (type == null) {
			throw new IllegalArgumentException("type can not be null");
		}
		this.type = type;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(DataFlowDirectionEnum direction) {
		if (direction == null) {
			throw new IllegalArgumentException("direction can not be null");
		}
		this.direction = direction;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule schedule) {
		if (schedule == null) {
			throw new IllegalArgumentException("schedule can not be null");
		}
		this.schedule = schedule;
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
      ExternalSystemImpl o = (ExternalSystemImpl)obj;
      return new EqualsBuilder()
      	  .append(new Object[]{id,direction,name,type},
      			  new Object[]{o.id,o.direction,o.name,o.type})
      	  .appendSuper(super.equals(o))
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,direction,name,type})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("direction", direction)
	       .append("name", name)
	       .append("type", type)
	       .appendSuper(super.toString())
	       .toString();
   }   

}
