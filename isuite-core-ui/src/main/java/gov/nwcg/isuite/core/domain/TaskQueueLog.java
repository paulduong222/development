package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Date;

public interface TaskQueueLog extends Persistable {
	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the taskQueue
	 */
	public TaskQueue getTaskQueue() ;

	/**
	 * @param taskQueue the taskQueue to set
	 */
	public void setTaskQueue(TaskQueue taskQueue);

	/**
	 * @return the taskQueueId
	 */
	public Long getTaskQueueId() ;

	/**
	 * @param taskQueueId the taskQueueId to set
	 */
	public void setTaskQueueId(Long taskQueueId);

	/**
	 * @return the startDate
	 */
	public Date getStartDate() ;

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) ;

	/**
	 * @return the endDate
	 */
	public Date getEndDate() ;

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) ;

	/**
	 * @return the status
	 */
	public String getStatus() ;

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() ;

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) ;
	

}
