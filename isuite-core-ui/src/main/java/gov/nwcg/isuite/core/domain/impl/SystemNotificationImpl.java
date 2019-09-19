package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_SYSTEM_NOTIFICATION", sequenceName="SEQ_SYSTEM_NOTIFICATION")
@Table(name="isw_system_notification")
public class SystemNotificationImpl extends PersistableImpl implements SystemNotification{

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_NOTIFICATION")
	private Long id;
	
	@Column(name = "TYPE", length = 30)
	private String type;
	
	@Column(name = "SUBJECT", length = 90)
	private String subject;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "MESSAGE_FORMAT", length = 20)
	private String messageFormat;

	@Column(name = "DESCRIPTION", length = 90)
	private String description;

	@OneToMany(targetEntity=SystemNotifyUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemNotification")
	private Collection<SystemNotifyUser> systemNotifyUsers = new ArrayList<SystemNotifyUser>();
	
	@ManyToMany(targetEntity=TaskQueueImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemNotifications")
	private Collection<TaskQueue> taskQueues = new ArrayList<TaskQueue>();
	
	@ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemNotifications")
	private Collection<SystemRole> systemRoles = new ArrayList<SystemRole>();
	
	public SystemNotificationImpl(){
		
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
	 * @return the taskQueues
	 */
	public Collection<TaskQueue> getTaskQueues() {
		return taskQueues;
	}

	/**
	 * @param taskQueues the taskQueues to set
	 */
	public void setTaskQueues(Collection<TaskQueue> taskQueues) {
		this.taskQueues = taskQueues;
	}

	/**
	 * @return the systemRoles
	 */
	public Collection<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	/**
	 * @param systemRoles the systemRoles to set
	 */
	public void setSystemRoles(Collection<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
	}

	/**
	 * @return the systemNotifyUsers
	 */
	public Collection<SystemNotifyUser> getSystemNotifyUsers() {
		return systemNotifyUsers;
	}

	/**
	 * @param systemNotifyUsers the systemNotifyUsers to set
	 */
	public void setSystemNotifyUsers(Collection<SystemNotifyUser> systemNotifyUsers) {
		this.systemNotifyUsers = systemNotifyUsers;
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
		SystemNotificationImpl o = (SystemNotificationImpl)obj;
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
