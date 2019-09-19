package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;

import java.util.Collection;
import java.util.Date;

public interface TaskQueue extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the taskType
	 */
	public TaskTypeEnum getTaskType() ;

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskTypeEnum taskType) ;

	/**
	 * @return the frequency
	 */
	public TaskFrequencyTypeEnum getFrequency() ;

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(TaskFrequencyTypeEnum frequency) ;

	/**
	 * @return the user
	 */
	public User getUser() ;

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) ;

	/**
	 * @return the userId
	 */
	public Long getUserId() ;

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) ;

	/**
	 * @return the frequencyTerm
	 */
	public Integer getFrequencyTerm() ;

	/**
	 * @param frequencyTerm the frequencyTerm to set
	 */
	public void setFrequencyTerm(Integer frequencyTerm) ;

	/**
	 * @return the timeout
	 */
	public Integer getTimeout() ;

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Integer timeout) ;

	/**
	 * @return the initialRunDate
	 */
	public Date getInitialRunDate() ;

	/**
	 * @param initialRunDate the initialRunDate to set
	 */
	public void setInitialRunDate(Date initialRunDate) ;

	/**
	 * @return the lastRunDate
	 */
	public Date getLastRunDate() ;

	/**
	 * @param lastRunDate the lastRunDate to set
	 */
	public void setLastRunDate(Date lastRunDate) ;

	/**
	 * @return the nextScheduledDate
	 */
	public Date getNextScheduledDate() ;

	/**
	 * @param nextScheduledDate the nextScheduledDate to set
	 */
	public void setNextScheduledDate(Date nextScheduledDate) ;

	/**
	 * @return the deactivatedDate
	 */
	public Date getDeactivatedDate() ;

	/**
	 * @param deactivatedDate the deactivatedDate to set
	 */
	public void setDeactivatedDate(Date deactivatedDate) ;

	/**
	 * @return the systemNotifications
	 */
	public Collection<SystemNotification> getSystemNotifications() ;

	/**
	 * @param systemNotifications the systemNotifications to set
	 */
	public void setSystemNotifications(
			Collection<SystemNotification> systemNotifications) ;

	/**
	 * @return the taskQueueLogs
	 */
	public Collection<TaskQueueLog> getTaskQueueLogs() ;

	/**
	 * @param taskQueueLogs the taskQueueLogs to set
	 */
	public void setTaskQueueLogs(Collection<TaskQueueLog> taskQueueLogs) ;

	/**
	 * @return the status
	 */
	public TaskStatusTypeEnum getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatusTypeEnum status);

//	public String getTaskData();
	
//	public void setTaskData(String taskData);	
}
