package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@SequenceGenerator(name="SEQ_USER_SESSION_LOG_ACTIVITY", sequenceName="SEQ_USER_SESSION_LOG_ACTIVITY")
@Table(name = "isw_user_session_log_activity")
public class UserSessionLogActivityImpl extends PersistableImpl implements UserSessionLogActivity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_SESSION_LOG_ACTIVITY")
	private Long id = 0L;
	
	@Column(name = "ACTION_TYPE", length = 60)
	@Enumerated(EnumType.STRING)
	private UserSessionActionTypeEnum actionType;
	
	@Column(name = "ACTION_CAUSE", length = 30)
	@Enumerated(EnumType.STRING)
	private UserSessionActionCauseEnum actionCause;

	@ManyToOne(targetEntity=UserSessionLogImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SESSION_LOG_ID", nullable = false)
	private UserSessionLog userSessionLog;

	@ManyToOne(targetEntity=UserImpl.class,fetch = FetchType.LAZY)
  @JoinColumn(name = "ADMIN_USER_ID", nullable = true)
	private User adminUser;
	
	
	public UserSessionLogActivityImpl(){
		
	}

	UserSessionLogActivityImpl(UserSessionLog usl, 
	                           UserSessionActionTypeEnum actionType, 
	                           UserSessionActionCauseEnum actionCause, 
	                           User adminUser) {
	  this.userSessionLog = usl;
	  this.actionType = actionType;
	  this.actionCause = actionCause;
	  this.adminUser = adminUser;
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
	 * @return the actionType
	 */
	public UserSessionActionTypeEnum getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(UserSessionActionTypeEnum actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the actionCause
	 */
	public UserSessionActionCauseEnum getActionCause() {
		return actionCause;
	}

	/**
	 * @param actionCause the actionCause to set
	 */
	public void setActionCause(UserSessionActionCauseEnum actionCause) {
		this.actionCause = actionCause;
	}

	public void setAdminUser(User adminUser) {
    this.adminUser = adminUser;
  }
	

  public User getAdminUser() {
    return adminUser;
  }
  

  /**
	 * @return the userSessionLog
	 */
	public UserSessionLog getUserSessionLog() {
		return userSessionLog;
	}

	/**
	 * @param userSessionLog the userSessionLog to set
	 */
	public void setUserSessionLog(UserSessionLog userSessionLog) {
		this.userSessionLog = userSessionLog;
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
		UserSessionLogActivityImpl o = (UserSessionLogActivityImpl)obj;
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
