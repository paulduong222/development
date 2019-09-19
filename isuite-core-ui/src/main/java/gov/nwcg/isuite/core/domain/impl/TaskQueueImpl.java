package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;

import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_TASKQUEUE", sequenceName="SEQ_TASKQUEUE")
@Table(name = "isw_task_queue")
public class TaskQueueImpl extends PersistableImpl implements TaskQueue {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TASKQUEUE")
	private Long id = 0L;

	@Column(name = "TASK_TYPE", length = 60, nullable=false)
	@Enumerated(EnumType.STRING)
	private TaskTypeEnum taskType;

	@Column(name = "FREQUENCY", length = 20, nullable=false)
	@Enumerated(EnumType.STRING)
	private TaskFrequencyTypeEnum frequency;

	@ManyToOne(targetEntity=UserImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=true, updatable=true,nullable=true)
	private User user;

	@Column(name="USER_ID", insertable=false, updatable=false,nullable=true)
	private Long userId;

	@Column(name="FREQUENCY_TERM")
	private Integer frequencyTerm;

	@Column(name = "TIMEOUT")
	private Integer timeout;

	@Column(name = "INITIAL_RUN_DATE")
	private Date initialRunDate;

	@Column(name = "LAST_RUN_DATE")
	private Date lastRunDate;

	@Column(name = "NEXT_SCHEDULED_DATE")
	private Date nextScheduledDate;

	@Column(name = "STATUS", length = 20)
	@Enumerated(EnumType.STRING)
	private TaskStatusTypeEnum status;

	@Column(name = "DEACTIVATE_DATE")
	private Date deactivatedDate;

	@ManyToMany(targetEntity=SystemNotificationImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_TASK_QUEUE_NOTIFICATION", 
			joinColumns = { 
				@JoinColumn(name = "TASK_QUEUE_ID", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "SYSTEM_NOTIFICATION_ID", nullable = false, updatable = false) })
	private Collection<SystemNotification> systemNotifications = new ArrayList<SystemNotification>();

	@OneToMany(targetEntity=TaskQueueLogImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taskQueue")
	private Collection<TaskQueueLog> taskQueueLogs = new ArrayList<TaskQueueLog>();

	//@Column(name = "TASK_DATA", length=200)
//	private String taskData;
	
	public TaskQueueImpl() {
		super();
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
	 * @return the taskType
	 */
	public TaskTypeEnum getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskTypeEnum taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the frequency
	 */
	public TaskFrequencyTypeEnum getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(TaskFrequencyTypeEnum frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the frequencyTerm
	 */
	public Integer getFrequencyTerm() {
		return frequencyTerm;
	}

	/**
	 * @param frequencyTerm the frequencyTerm to set
	 */
	public void setFrequencyTerm(Integer frequencyTerm) {
		this.frequencyTerm = frequencyTerm;
	}

	/**
	 * @return the timeout
	 */
	public Integer getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the initialRunDate
	 */
	public Date getInitialRunDate() {
		return initialRunDate;
	}

	/**
	 * @param initialRunDate the initialRunDate to set
	 */
	public void setInitialRunDate(Date initialRunDate) {
		this.initialRunDate = initialRunDate;
	}

	/**
	 * @return the lastRunDate
	 */
	public Date getLastRunDate() {
		return lastRunDate;
	}

	/**
	 * @param lastRunDate the lastRunDate to set
	 */
	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	/**
	 * @return the nextScheduledDate
	 */
	public Date getNextScheduledDate() {
		return nextScheduledDate;
	}

	/**
	 * @param nextScheduledDate the nextScheduledDate to set
	 */
	public void setNextScheduledDate(Date nextScheduledDate) {
		this.nextScheduledDate = nextScheduledDate;
	}

	/**
	 * @return the deactivatedDate
	 */
	public Date getDeactivatedDate() {
		return deactivatedDate;
	}

	/**
	 * @param deactivatedDate the deactivatedDate to set
	 */
	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}

	/**
	 * @return the systemNotifications
	 */
	public Collection<SystemNotification> getSystemNotifications() {
		return systemNotifications;
	}

	/**
	 * @param systemNotifications the systemNotifications to set
	 */
	public void setSystemNotifications(
			Collection<SystemNotification> systemNotifications) {
		this.systemNotifications = systemNotifications;
	}

	/**
	 * @return the taskQueueLogs
	 */
	public Collection<TaskQueueLog> getTaskQueueLogs() {
		return taskQueueLogs;
	}

	/**
	 * @param taskQueueLogs the taskQueueLogs to set
	 */
	public void setTaskQueueLogs(Collection<TaskQueueLog> taskQueueLogs) {
		this.taskQueueLogs = taskQueueLogs;
	}

	/**
	 * @return the status
	 */
	public TaskStatusTypeEnum getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatusTypeEnum status) {
		this.status = status;
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
		TaskQueueImpl o = (TaskQueueImpl)obj;
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

//	public String getTaskData() {
//		return taskData;
//	}

//	public void setTaskData(String taskData) {
//		this.taskData = taskData;
//	}


}
