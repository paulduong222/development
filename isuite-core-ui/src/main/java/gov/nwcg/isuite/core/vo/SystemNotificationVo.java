package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.impl.SystemNotificationImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class SystemNotificationVo extends AbstractVo{
	private String type;
	private String subject;
	private String message;
	private String messageFormat;
	private String description;
	private Collection<SystemNotifyUserVo> systemNotifyUserVos = new ArrayList<SystemNotifyUserVo>();
	private Collection<TaskQueueVo> taskQueueVos = new ArrayList<TaskQueueVo>();
	private Collection<SystemRoleVo> systemRoleVos = new ArrayList<SystemRoleVo>();

	public SystemNotificationVo(){
		
	}

	public static SystemNotificationVo getInstance(SystemNotification entity, boolean cascadable) throws Exception {
		SystemNotificationVo vo = new SystemNotificationVo();

		if(null == entity)
			throw new Exception("Unable to build SystemNotificationVo instance from null SystemNotification entity");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setDescription(entity.getDescription());
			vo.setMessage(entity.getMessage());
			vo.setMessageFormat(entity.getMessageFormat());
			vo.setSubject(entity.getSubject());
			vo.setType(entity.getType());
			
			//vo.setSystemNotifyUserVos(null);
			//vo.setSystemRoleVos(null);
			//vo.setTaskQueueVos(null);
			
		}

		return vo;
	}

	public static Collection<SystemNotificationVo> getInstances(Collection<SystemNotification> entities, boolean cascadable) throws Exception {
		Collection<SystemNotificationVo> vos = new ArrayList<SystemNotificationVo>();

		for(SystemNotification entity : entities){
			vos.add(SystemNotificationVo.getInstance(entity, cascadable));
		}
		return vos;
	}


	public static SystemNotification toEntity(SystemNotificationVo vo,boolean cascadable) throws Exception {
		SystemNotification entity = new SystemNotificationImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDescription(vo.getDescription());
			entity.setMessage(vo.getMessage());
			entity.setMessageFormat(vo.getMessageFormat());
			entity.setSubject(vo.getSubject());
			entity.setType(vo.getType());
			
			//entity.setSystemNotifyUsers(null);
			//entity.setSystemRoles(null);
			//entity.setTaskQueues(null);
		}
		
		return entity;
	}

	public static Collection<SystemNotification> toEntityList(Collection<SystemNotificationVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SystemNotification> entities = new ArrayList<SystemNotification>();

		for(SystemNotificationVo vo : vos){
			entities.add(SystemNotificationVo.toEntity(vo, cascadable));
		}

		return entities;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the messageFormat
	 */
	public String getMessageFormat() {
		return messageFormat;
	}

	/**
	 * @param messageFormat the messageFormat to set
	 */
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the systemNotifyUserVos
	 */
	public Collection<SystemNotifyUserVo> getSystemNotifyUserVos() {
		return systemNotifyUserVos;
	}

	/**
	 * @param systemNotifyUserVos the systemNotifyUserVos to set
	 */
	public void setSystemNotifyUserVos(
			Collection<SystemNotifyUserVo> systemNotifyUserVos) {
		this.systemNotifyUserVos = systemNotifyUserVos;
	}

	/**
	 * @return the taskQueueVos
	 */
	public Collection<TaskQueueVo> getTaskQueueVos() {
		return taskQueueVos;
	}

	/**
	 * @param taskQueueVos the taskQueueVos to set
	 */
	public void setTaskQueueVos(Collection<TaskQueueVo> taskQueueVos) {
		this.taskQueueVos = taskQueueVos;
	}

	/**
	 * @return the systemRoleVos
	 */
	public Collection<SystemRoleVo> getSystemRoleVos() {
		return systemRoleVos;
	}

	/**
	 * @param systemRoleVos the systemRoleVos to set
	 */
	public void setSystemRoleVos(Collection<SystemRoleVo> systemRoleVos) {
		this.systemRoleVos = systemRoleVos;
	}
	
	
}
