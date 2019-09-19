package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author dprice
 *
 */
@Entity
@SequenceGenerator(name="SEQ_ASSIGNMENT", sequenceName="SEQ_ASSIGNMENT")
@Table(name="isw_assignment")
public class AssignmentImpl extends PersistableImpl implements Assignment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ASSIGNMENT")
	private Long id = 0L;

	@OneToOne(targetEntity=KindImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="KIND_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Kind kind;

	@Column(name="KIND_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long kindId;

	@Column(name="REQUEST_NUMBER",length=25)
	private String requestNumber;

	@Column(name="START_DATE", nullable=true)
	private Date startDate;

	@Column(name="END_DATE", nullable=true)
	private Date endDate;

	@Column(name="IS_TRAINING")
	private Boolean training;

	@Column(name="REASSIGN_INCIDENT_NAME", length=30)
	private String reassignIncidentName;

	@Column(name="REASSIGN_INCIDENT_NUMBER" , length=20)
	private String reassignIncidentNumber;
	
	@Column(name="ASSIGNMENT_STATUS")
	@Enumerated(EnumType.STRING)
	private AssignmentStatusTypeEnum assignmentStatus;

	@ManyToMany(targetEntity=WorkPeriodImpl.class,
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY,mappedBy="assignments")
			private Collection<WorkPeriod> workPeriods;

	@OneToOne(targetEntity=AssignmentTimeImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assignment")
	private AssignmentTime assignmentTime;

	@OneToMany(targetEntity=TimeAssignAdjustImpl.class,fetch = FetchType.LAZY, mappedBy = "assignment")
	@OrderBy("activityDate")
	private Collection<TimeAssignAdjust> timeAssignAdjusts;
	
	public Kind getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Assignment#setKindCode(KindCode)
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Assignment#getKindCodeId()
	 */
	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#getRequestNumber()
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#setRequestNumber(java.lang.String)
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#getStartDate()
	 */
	public Date getStartDate() {
		return startDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#setStartDate(java.util.Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#getEndDate()
	 */
	public Date getEndDate() {
		return endDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#setEndDate(java.util.Date)
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#getTraining()
	 */
	public Boolean isTraining() {
		return training;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.assignment.Assignment#setTraining(java.lang.Boolean)
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.Assignment#getAssignmentStatus()
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.Assignment#setAssignmentStatus(gov.nwcg.isuite.domain.resource.AssignmentStatusTypeEnum)
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.Assignment#getWorkPeriods()
	 */
	public Collection<WorkPeriod> getWorkPeriods() {
		return workPeriods;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.Assignment#setWorkPeriods(java.util.Collection)
	 */
	public void setWorkPeriods(Collection<WorkPeriod> workPeriods) {
		this.workPeriods = workPeriods;
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
		AssignmentImpl o = (AssignmentImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,assignmentStatus,endDate,workPeriods,kindId
				,requestNumber,startDate,training},
				new Object[]{o.id,o.assignmentStatus,o.endDate,o.workPeriods,o.kindId
				,o.requestNumber,o.startDate,o.training})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,assignmentStatus,endDate,workPeriods,kindId
				,requestNumber,startDate,training})
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("requestNumber", requestNumber)
		.append("startDate", startDate)
		.append("endDate", endDate)
		.append("kindId", kindId)
		.append("training", training)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime() {
		return assignmentTime;
	}

	/**
	 * @param assignmentTime the assignmentTime to set
	 */
	public void setAssignmentTime(AssignmentTime assignmentTime) {
		this.assignmentTime = assignmentTime;
	}

	/**
	 * @return the timeAssignAdjusts
	 */
	public Collection<TimeAssignAdjust> getTimeAssignAdjusts() {		
		return timeAssignAdjusts;
	}

	/**
	 * @param timeAssignAdjusts the timeAssignAdjusts to set
	 */
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts) {
		this.timeAssignAdjusts = timeAssignAdjusts;
	}

	public String getReassignIncidentName() {
		return reassignIncidentName;
	}

	public void setReassignIncidentName(String reassignIncidentName) {
		this.reassignIncidentName = reassignIncidentName;
	}

	public String getReassignIncidentNumber() {
		return reassignIncidentNumber;
	}

	public void setReassignIncidentNumber(String reassignIncidentNumber) {
		this.reassignIncidentNumber = reassignIncidentNumber;
	}


}