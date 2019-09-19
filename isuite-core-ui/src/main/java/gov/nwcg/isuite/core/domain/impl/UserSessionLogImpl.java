package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_USER_SESSION_LOG", sequenceName="SEQ_USER_SESSION_LOG")
@Table(name = "isw_user_session_log")
public class UserSessionLogImpl extends PersistableImpl implements UserSessionLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_SESSION_LOG")
	private Long id = 0L;

	@Column(name = "SESSION_ID", length = 60)
	private String sessionId;
	
	@Column(name = "IP_ADDRESS", length = 30)
	private String ipAddress;
	
	@Column(name = "LAST_STATUS_CHECK_DATE")
	private Date lastStatusCheckDate;
	
	@ManyToOne(targetEntity=UserImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable=true, insertable=true, nullable = false)
	private User user;
	
	@Column(name = "USER_ID", updatable=false, insertable=false, nullable = false)
	private Long userId;
	
	@OneToMany(targetEntity=UserSessionLogActivityImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userSessionLog")
	private Collection<UserSessionLogActivity> userSessionLogActivities = new ArrayList<UserSessionLogActivity>();
	
	public UserSessionLogImpl(){
		
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
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the lastStatusCheckDate
	 */
	public Date getLastStatusCheckDate() {
		return lastStatusCheckDate;
	}

	/**
	 * @param lastStatusCheckDate the lastStatusCheckDate to set
	 */
	public void setLastStatusCheckDate(Date lastStatusCheckDate) {
		this.lastStatusCheckDate = lastStatusCheckDate;
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
	 * @return the userSessionLogActivities
	 */
	public Collection<UserSessionLogActivity> getUserSessionLogActivities() {
		return userSessionLogActivities;
	}

	/**
	 * @param userSessionLogActivities the userSessionLogActivities to set
	 */
	public void setUserSessionLogActivities(
			Collection<UserSessionLogActivity> userSessionLogActivities) {
		this.userSessionLogActivities = userSessionLogActivities;
	}
	
	public void addUserSessionLogActivity(UserSessionActionTypeEnum actionType, UserSessionActionCauseEnum actionCause) {
	  //TODO: add override user 
	  UserSessionLogActivity usla = new UserSessionLogActivityImpl(this, actionType, actionCause, null);
	  userSessionLogActivities.add(usla);
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
		UserSessionLogImpl o = (UserSessionLogImpl)obj;
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
