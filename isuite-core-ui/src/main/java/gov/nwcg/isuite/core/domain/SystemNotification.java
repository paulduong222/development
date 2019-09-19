package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

public interface SystemNotification extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the type
	 */
	public String getType() ;

	/**
	 * @param type the type to set
	 */
	public void setType(String type) ;

	/**
	 * @return the subject
	 */
	public String getSubject() ;

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject);

	/**
	 * @return the message
	 */
	public String getMessage();

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message);

	/**
	 * @return the messageFormat
	 */
	public String getMessageFormat();

	/**
	 * @param messageFormat the messageFormat to set
	 */
	public void setMessageFormat(String messageFormat);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description);

	/**
	 * @return the taskQueues
	 */
	public Collection<TaskQueue> getTaskQueues();

	/**
	 * @param taskQueues the taskQueues to set
	 */
	public void setTaskQueues(Collection<TaskQueue> taskQueues);

	/**
	 * @return the systemRoles
	 */
	public Collection<SystemRole> getSystemRoles();

	/**
	 * @param systemRoles the systemRoles to set
	 */
	public void setSystemRoles(Collection<SystemRole> systemRoles);

	/**
	 * @return the systemNotifyUsers
	 */
	public Collection<SystemNotifyUser> getSystemNotifyUsers();

	/**
	 * @param systemNotifyUsers the systemNotifyUsers to set
	 */
	public void setSystemNotifyUsers(Collection<SystemNotifyUser> systemNotifyUsers);
	
}
