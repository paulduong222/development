package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Date;

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

@Entity
@SequenceGenerator(name="SEQ_TASK_QUEUE_LOG", sequenceName="SEQ_TASK_QUEUE_LOG")
@Table(name = "isw_task_queue_log")
public class TaskQueueLogImpl extends PersistableImpl implements TaskQueueLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TASK_QUEUE_LOG")
	private Long id;
	
	@ManyToOne(targetEntity=TaskQueueImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name="TASK_QUEUE_ID", insertable=true, updatable=true, nullable=false)
	private TaskQueue taskQueue;

	@Column(name="TASK_QUEUE_ID", insertable=false, updatable=false, nullable=false)
	private Long taskQueueId;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name = "STATUS", length = 20)
	private String status;
	
	@Column(name = "STATUS_MESSAGE", length = 2000)
	private String statusMessage;

	public TaskQueueLogImpl(){
		
	}

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
	 * @return the taskQueue
	 */
	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	/**
	 * @param taskQueue the taskQueue to set
	 */
	public void setTaskQueue(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	/**
	 * @return the taskQueueId
	 */
	public Long getTaskQueueId() {
		return taskQueueId;
	}

	/**
	 * @param taskQueueId the taskQueueId to set
	 */
	public void setTaskQueueId(Long taskQueueId) {
		this.taskQueueId = taskQueueId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
		TaskQueueLogImpl o = (TaskQueueLogImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}
	
}
