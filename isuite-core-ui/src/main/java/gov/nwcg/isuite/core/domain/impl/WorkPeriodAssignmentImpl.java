package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodAssignment;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

//@Entity
@Table(name="isw_work_period_assignment")
public class WorkPeriodAssignmentImpl implements WorkPeriodAssignment {
   
    @OneToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.AssignmentImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
    private Assignment assignment;
   
    @Column(name="ASSIGNMENT_ID", insertable=false, updatable=false, nullable=true, unique=false)
    private Long assignmentId;

    @Id
    @OneToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name="WORK_PERIOD_ID", insertable=true, updatable=true, unique=false, nullable=true)
    private WorkPeriod workPeriod;
   
    @Column(name="WORK_PERIOD_ID", insertable=false, updatable=false, nullable=true, unique=false)
    private Long workPeriodId;

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResourceWorkPeriodAssignment#getAssignment()
	 */
	public Assignment getAssignment() {
		return assignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResourceWorkPeriodAssignment#setAssignment(gov.nwcg.isuite.domain.incident.assignment.Assignment)
	 */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResourceWorkPeriodAssignment#getAssignmentId()
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.resource.IncidentResourceWorkPeriodAssignment#setAssignmentId(java.lang.Long)
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public WorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	public void setWorkPeriod(WorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
	}

	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
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
      WorkPeriodAssignmentImpl o = (WorkPeriodAssignmentImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{assignmentId,workPeriodId},
      			new Object[]{o.assignmentId,o.workPeriodId})
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(new Object[]{assignmentId,workPeriodId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("assignmentId", assignmentId)
	       .append("workPeriodId", workPeriodId)
	       .toString();
   }   
   
}