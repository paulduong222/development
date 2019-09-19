package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@SequenceGenerator(name="SEQ_PASSWORD_HISTORY", sequenceName="SEQ_PASSWORD_HISTORY")
@Table(name = "isw_password_history")
public class PasswordHistoryImpl extends PersistableImpl implements PasswordHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_PASSWORD_HISTORY")
	private Long id = 0L;

	@Column(name="USER_PASSWORD", length=255)
	private String userPassword;
	
	@Column(name = "USER_PASSWORD_CREATED_DATE")
	private Date userPasswordCreatedDate;
	
    @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private User user;
    
    @Column(name="USER_ID", insertable = false, updatable = false, nullable = true)
	private Long userId;

	public PasswordHistoryImpl(){

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
	 * Returns the userPassword.
	 *
	 * @return 
	 *		the userPassword to return
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * Sets the userPassword.
	 *
	 * @param userPassword 
	 *			the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * Returns the userPasswordCreatedDate.
	 *
	 * @return 
	 *		the userPasswordCreatedDate to return
	 */
	public Date getUserPasswordCreatedDate() {
		return userPasswordCreatedDate;
	}

	/**
	 * Sets the userPasswordCreatedDate.
	 *
	 * @param userPasswordCreatedDate 
	 *			the userPasswordCreatedDate to set
	 */
	public void setUserPasswordCreatedDate(Date userPasswordCreatedDate) {
		this.userPasswordCreatedDate = userPasswordCreatedDate;
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
		PasswordHistoryImpl o = (PasswordHistoryImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,userPassword,userPasswordCreatedDate,userId	},
				new Object[]{o.id,o.userPassword,o.userPasswordCreatedDate,o.userId	})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,userPassword,userPasswordCreatedDate,userId	})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("userPassword", userPassword)
		.append("userPassword", userPasswordCreatedDate)
		.append("userId", userId)
		.appendSuper(super.toString())
		.toString();
	}

}
