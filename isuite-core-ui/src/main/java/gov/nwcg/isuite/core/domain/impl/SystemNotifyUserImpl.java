package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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
@SequenceGenerator(name="SEQ_SYSTEM_NOTIFY_USER", sequenceName="SEQ_SYSTEM_NOTIFY_USER")
@Table(name = "isw_system_notify_user")
public class SystemNotifyUserImpl extends PersistableImpl implements SystemNotifyUser {

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_NOTIFY_USER")
	private Long id;
	
	@ManyToOne(targetEntity=UserImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable=true, updatable=true, nullable = false)
	private User user;
	
	@Column(name = "USER_ID", insertable=false, updatable=false, nullable = false)
	private Long userId;

	@ManyToOne(targetEntity=SystemNotificationImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSTEM_NOTIFICATION_ID", insertable=true, updatable=true,nullable = false)
	private SystemNotification systemNotification;
	
	@Column(name = "USER_ID", insertable=false, updatable=false, nullable = false)
	private Long systemNotificationId;

	@Column(name = "IS_EXCLUDED", nullable = false)
	private Boolean excluded;
	
	public SystemNotifyUserImpl(){
		
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
	 * @return the systemNotification
	 */
	public SystemNotification getSystemNotification() {
		return systemNotification;
	}

	/**
	 * @param systemNotification the systemNotification to set
	 */
	public void setSystemNotification(SystemNotification systemNotification) {
		this.systemNotification = systemNotification;
	}

	/**
	 * @return the systemNotificationId
	 */
	public Long getSystemNotificationId() {
		return systemNotificationId;
	}

	/**
	 * @param systemNotificationId the systemNotificationId to set
	 */
	public void setSystemNotificationId(Long systemNotificationId) {
		this.systemNotificationId = systemNotificationId;
	}

	/**
	 * @return the excluded
	 */
	public Boolean getExcluded() {
		return excluded;
	}

	/**
	 * @param excluded the excluded to set
	 */
	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
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
		SystemNotifyUserImpl o = (SystemNotifyUserImpl)obj;
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

