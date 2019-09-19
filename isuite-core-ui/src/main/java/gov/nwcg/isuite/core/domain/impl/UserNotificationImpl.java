package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserNotificationQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@SequenceGenerator(name="SEQ_USER_NOTIFICATION", sequenceName="SEQ_USER_NOTIFICATION")
@NamedQueries({
	@NamedQuery(name=UserNotificationQuery.UPDATE_READFLAG,query=UserNotificationQuery.UPDATE_READFLAG_QUERY)
})
@Table(name = "isw_user_notification")
public class UserNotificationImpl extends PersistableImpl implements UserNotification {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_NOTIFICATION")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=UserImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private User user;
	
    @Column(name="USER_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long userId;
	
	@Column(name = "SOURCE", length = 16)
	private String source;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "POSTED_DATE", length = 29)
	private Date postedDate;
	
	@Column(name = "READ_DATE", length = 29)
	private Date readDate;
	
	@Column(name = "READ_FLAG")
	private Boolean readFlag;
	
	public UserNotificationImpl(){

	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the user.
	 *
	 * @return 
	 *		the user to return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user 
	 *			the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the userId.
	 *
	 * @return 
	 *		the userId to return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *			the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the source.
	 *
	 * @return 
	 *		the source to return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source 
	 *			the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Returns the message.
	 *
	 * @return 
	 *		the message to return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message 
	 *			the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the postedDate.
	 *
	 * @return 
	 *		the postedDate to return
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * Sets the postedDate.
	 *
	 * @param postedDate 
	 *			the postedDate to set
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * Returns the readDate.
	 *
	 * @return 
	 *		the readDate to return
	 */
	public Date getReadDate() {
		return readDate;
	}

	/**
	 * Sets the readDate.
	 *
	 * @param readDate 
	 *			the readDate to set
	 */
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	/**
	 * Returns the readFlag.
	 *
	 * @return 
	 *		the readFlag to return
	 */
	public Boolean getReadFlag() {
		return readFlag;
	}

	/**
	 * Sets the readFlag.
	 *
	 * @param readFlag 
	 *			the readFlag to set
	 */
	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
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
		UserNotificationImpl o = (UserNotificationImpl)obj;
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
