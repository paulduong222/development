package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Schedule;
import gov.nwcg.isuite.framework.types.ScheduleTypeEnum;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Implementation of Schedule.
 * @author doug
 *
 */
@Embeddable
public class ScheduleImpl implements Schedule {
	
	@Column(name = "SCHED_TYPE")
	@Enumerated(EnumType.STRING)
	private ScheduleTypeEnum type;
	
	@Column(name = "SCHED_TIME")
	private long time;

	public ScheduleImpl(){
		
	}

	public long getTime() {
		return time;
	}

	public ScheduleTypeEnum getType() {
		return type;
	}
	
	void setType(ScheduleTypeEnum type) {
		if (type == null) {
			throw new IllegalArgumentException("type can not be null");
		}
		this.type = type;
	}
	
	void setTime(long time) {
		if (time < 0) {
			throw new IllegalArgumentException("time can not be negative");
		}
		this.time = time;
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
	      ScheduleImpl o = (ScheduleImpl)obj;
	      return new EqualsBuilder()
	      	  .append(new Object[]{time,type},
	      			  new Object[]{o.time,o.type})
		      .isEquals();
	   }   
	   
	   /* (non-Javadoc)
	    * @see java.lang.Object#hashCode()
	    */
	   public int hashCode() {
		  return new HashCodeBuilder(31,33)
		  	.append(new Object[]{time,type})
		  	.toHashCode();
	   }

	   /* (non-Javadoc)
	    * @see java.lang.Object#toString()
	    */
	   public String toString() {
		   return new ToStringBuilder(this)
		       .append("time", time)
		       .append("type", type)
		       .toString();
	   }   
	
	
}
