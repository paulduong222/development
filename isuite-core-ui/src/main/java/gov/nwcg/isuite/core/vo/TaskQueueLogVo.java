package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.core.domain.impl.TaskQueueImpl;
import gov.nwcg.isuite.core.domain.impl.TaskQueueLogImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TaskQueueLogVo extends AbstractVo implements PersistableVo {
	private Long taskQueueId;
	private Date startDate;
	private Date endDate;
	private String status;
	private String statusMessage;


	/**
	 * Constructor
	 */
	public TaskQueueLogVo() {
		super();
	}


	public static TaskQueueLogVo getInstance(TaskQueueLog entity, boolean cascadable) throws Exception {
		TaskQueueLogVo vo = new TaskQueueLogVo();

		if(null == entity)
			throw new Exception("Unable to build TaskQueueLogVo instance from null TaskQueueLog entity");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setStartDate(entity.getStartDate());
			vo.setEndDate(entity.getEndDate());
			vo.setStatus(entity.getStatus());
			vo.setStatusMessage(entity.getStatusMessage());
			vo.setTaskQueueId(entity.getTaskQueueId());
		}

		return vo;
	}

	public static Collection<TaskQueueLogVo> getInstances(Collection<TaskQueueLog> entities, boolean cascadable) throws Exception {
		Collection<TaskQueueLogVo> vos = new ArrayList<TaskQueueLogVo>();

		for(TaskQueueLog entity : entities){
			vos.add(TaskQueueLogVo.getInstance(entity, cascadable));
		}
		return vos;
	}


	public static TaskQueueLog toEntity(TaskQueueLogVo vo,boolean cascadable,Persistable... persistables) throws Exception {
		TaskQueueLog entity = new TaskQueueLogImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setStartDate(vo.getStartDate());
			entity.setEndDate(vo.getEndDate());
			entity.setStatus(vo.getStatus());
			entity.setStatusMessage(vo.getStatusMessage());
			
			TaskQueue taskQueue=(TaskQueue)AbstractVo.getPersistableObject(persistables, TaskQueueImpl.class);
			if(null != taskQueue)
				entity.setTaskQueue(taskQueue);
		}
		
		return entity;
	}
	
	
	public static Collection<TaskQueueLog> toEntityList(Collection<TaskQueueLogVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<TaskQueueLog> entities = new ArrayList<TaskQueueLog>();

		for(TaskQueueLogVo vo : vos){
			entities.add(TaskQueueLogVo.toEntity(vo, cascadable,persistables));
		}

		return entities;
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


}
