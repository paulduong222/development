package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.TaskQueueImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TaskQueueVo extends AbstractVo implements PersistableVo {
	private TaskTypeVo taskTypeVo;
	private TaskFrequencyTypeVo frequencyVo;
	private UserVo userVo;
	private Integer frequencyTerm;
	private Integer timeout;
	private String frequencyTermStr;
	private String timeoutStr;
	private Date initialRunDate;
	private Date lastRunDate;
	private Date nextScheduledDate;
	private TaskStatusTypeVo statusVo;
	private Date deactivatedDate;
	
	private Collection<SystemNotificationVo> systemNotificationVos = new ArrayList<SystemNotificationVo>();
	private Collection<TaskQueueLogVo> taskQueueLogVos = new ArrayList<TaskQueueLogVo>();

	/**
	 * Constructor
	 */
	public TaskQueueVo() {
		super();
	}


	public static TaskQueueVo getInstance(TaskQueue entity, boolean cascadable) throws Exception {
		TaskQueueVo vo = new TaskQueueVo();

		if(null == entity)
			throw new Exception("Unable to build TaskQueueVo instance from null TaskQueue entity");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setDeactivatedDate(entity.getDeactivatedDate());
			vo.setFrequencyVo(TaskFrequencyTypeEnum.getVo(entity.getFrequency()));
			vo.setFrequencyTerm(entity.getFrequencyTerm());
			vo.setInitialRunDate(entity.getInitialRunDate());
			vo.setLastRunDate(entity.getLastRunDate());
			vo.setNextScheduledDate(entity.getNextScheduledDate());
			vo.setStatusVo(TaskStatusTypeEnum.getVo(entity.getStatus()));
			vo.setTaskTypeVo(TaskTypeEnum.getVo(entity.getTaskType()));
			vo.setTimeout(entity.getTimeout());
			
			if(IntegerUtility.hasValue(vo.getTimeout()))
				vo.setTimeoutStr(String.valueOf(vo.getTimeout()));
			
			if(IntegerUtility.hasValue(vo.getFrequencyTerm()))
				vo.setFrequencyTermStr(String.valueOf(vo.getFrequencyTerm()));

			if(null != entity.getUserId()){
				UserVo userVo = new UserVo();
				userVo.setId(entity.getUserId());
				vo.setUserVo(userVo);
			}
			
			if(null != entity.getTaskQueueLogs()){
				vo.setTaskQueueLogVos(TaskQueueLogVo.getInstances(entity.getTaskQueueLogs(), true));
			}
			
			if(null != entity.getSystemNotifications()){
				vo.setSystemNotificationVos(SystemNotificationVo.getInstances(entity.getSystemNotifications(), true));
			}
			
		}

		return vo;
	}

	public static Collection<TaskQueueVo> getInstances(Collection<TaskQueue> entities, boolean cascadable) throws Exception {
		Collection<TaskQueueVo> vos = new ArrayList<TaskQueueVo>();

		for(TaskQueue entity : entities){
			vos.add(TaskQueueVo.getInstance(entity, cascadable));
		}
		return vos;
	}


	public static TaskQueue toEntity(TaskQueueVo vo,boolean cascadable) throws Exception {
		TaskQueue entity = new TaskQueueImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDeactivatedDate(vo.getDeactivatedDate());
			entity.setFrequency(TaskFrequencyTypeEnum.toEnumType(vo.getFrequencyVo()));
			entity.setFrequencyTerm(vo.getFrequencyTerm());
			entity.setInitialRunDate(vo.getInitialRunDate());
			entity.setLastRunDate(vo.getLastRunDate());
			entity.setNextScheduledDate(vo.getNextScheduledDate());
			entity.setStatus(TaskStatusTypeEnum.toEnumValue(vo.getStatusVo()));
			entity.setTaskType(TaskTypeEnum.toEnumValue(vo.getTaskTypeVo()));
			entity.setTimeout(vo.getTimeout());

			try{
				if(!IntegerUtility.hasValue(entity.getFrequencyTerm()) && StringUtility.hasValue(vo.getFrequencyTermStr()))
					entity.setFrequencyTerm(TypeConverter.convertToInteger(vo.getFrequencyTermStr()));
				
				if(!IntegerUtility.hasValue(entity.getTimeout()) && StringUtility.hasValue(vo.getTimeoutStr()))
					entity.setTimeout(TypeConverter.convertToInteger(vo.getTimeoutStr()));
				
			}catch(Exception eee){
			}

			if(null != entity.getUserId()){
				UserVo userVo = new UserVo();
				userVo.setId(entity.getUserId());
				vo.setUserVo(userVo);
			}
			
			if(null != vo.getUserVo()){
				User user = new UserImpl();
				user.setId(vo.getUserVo().getId());
				entity.setUser(user);
			}
			
			if(null != vo.getTaskQueueLogVos()){
				entity.setTaskQueueLogs(TaskQueueLogVo.toEntityList(vo.getTaskQueueLogVos(), true,entity));
			}
		}
		
		return entity;
	}
	
	
	/**
	 * Returns a collection of TaskQueueVo entities from a collection of TaskQueue vos.
	 * 
	 * @param vos
	 * 			the source collection of TaskQueue vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a TaskQueue entities
	 * @return
	 * 			collection of TaskQueue entities
	 * @throws Exception
	 */
	public static Collection<TaskQueue> toEntityList(Collection<TaskQueueVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<TaskQueue> entities = new ArrayList<TaskQueue>();

		for(TaskQueueVo vo : vos){
			entities.add(TaskQueueVo.toEntity(vo, cascadable));
		}

		return entities;
	}


	/**
	 * @return the taskType
	 */
	public TaskTypeVo getTaskTypeVo() {
		return taskTypeVo;
	}


	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskTypeVo(TaskTypeVo taskTypeVo) {
		this.taskTypeVo = taskTypeVo;
	}


	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}


	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
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
	 * @return the taskQueueLogVos
	 */
	public Collection<TaskQueueLogVo> getTaskQueueLogVos() {
		return taskQueueLogVos;
	}


	/**
	 * @param taskQueueLogVos the taskQueueLogVos to set
	 */
	public void setTaskQueueLogVos(Collection<TaskQueueLogVo> taskQueueLogVos) {
		this.taskQueueLogVos = taskQueueLogVos;
	}


	/**
	 * @return the systemNotificationVos
	 */
	public Collection<SystemNotificationVo> getSystemNotificationVos() {
		return systemNotificationVos;
	}


	/**
	 * @param systemNotificationVos the systemNotificationVos to set
	 */
	public void setSystemNotificationVos(
			Collection<SystemNotificationVo> systemNotificationVos) {
		this.systemNotificationVos = systemNotificationVos;
	}


	/**
	 * @return the frequencyVo
	 */
	public TaskFrequencyTypeVo getFrequencyVo() {
		return frequencyVo;
	}


	/**
	 * @param frequencyVo the frequencyVo to set
	 */
	public void setFrequencyVo(TaskFrequencyTypeVo frequencyVo) {
		this.frequencyVo = frequencyVo;
	}


	/**
	 * @return the statusVo
	 */
	public TaskStatusTypeVo getStatusVo() {
		return statusVo;
	}


	/**
	 * @param statusVo the statusVo to set
	 */
	public void setStatusVo(TaskStatusTypeVo statusVo) {
		this.statusVo = statusVo;
	}


	/**
	 * @return the frequencyTermStr
	 */
	public String getFrequencyTermStr() {
		return frequencyTermStr;
	}


	/**
	 * @param frequencyTermStr the frequencyTermStr to set
	 */
	public void setFrequencyTermStr(String frequencyTermStr) {
		this.frequencyTermStr = frequencyTermStr;
	}


	/**
	 * @return the timeoutStr
	 */
	public String getTimeoutStr() {
		return timeoutStr;
	}


	/**
	 * @param timeoutStr the timeoutStr to set
	 */
	public void setTimeoutStr(String timeoutStr) {
		this.timeoutStr = timeoutStr;
	}



}
