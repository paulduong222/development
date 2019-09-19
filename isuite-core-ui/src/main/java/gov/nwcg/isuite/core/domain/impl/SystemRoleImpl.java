package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.core.domain.User;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "isw_system_role")
@SequenceGenerator(name="SEQ_SYSTEM_ROLE", sequenceName="SEQ_SYSTEM_ROLE")
public class SystemRoleImpl extends PersistableImpl implements SystemRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_ROLE")
	private Long id = 0L;
	
	@Column(name = "role_name", unique = true, nullable = false, length = 35)
	private String roleName;
	
	@Column(name = "display_name", unique = true, nullable = true, length = 30)
	private String displayName;
	
	@Column(name = "is_privileged_role", nullable = false)
	private Boolean privilegedRole;
	
	@OneToMany(targetEntity=SystemRolePermImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemRole")
	private Collection<SystemRolePerm> systemRolePerms = new ArrayList<SystemRolePerm>();
	
	@ManyToMany(targetEntity=UserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemRoles")
	private Collection<User> users = new ArrayList<User>();
	
//	@ManyToMany(targetEntity=CustomReportViewImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemRoles")
//	private Collection<User> customReportViews = new ArrayList<User>();
	
	@ManyToMany(targetEntity=SystemNotificationImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_SYSTEM_NOTIFY_ROLE", 
			joinColumns = { 
				@JoinColumn(name = "SYSTEM_ROLE_ID", nullable = false, updatable = false) }
				, inverseJoinColumns = { 
					@JoinColumn(name = "SYSTEM_NOTIFICATION_ID", nullable = false, updatable = false) })
	private Collection<SystemNotification> systemNotifications = new ArrayList<SystemNotification>();

	public SystemRoleImpl(){
		
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
	 * Returns the roleName.
	 *
	 * @return 
	 *		the roleName to return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the roleName.
	 *
	 * @param roleName 
	 *			the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Returns the privilegedRole.
	 *
	 * @return 
	 *		the privilegedRole to return
	 */
	public Boolean getPrivilegedRole() {
		return privilegedRole;
	}

	/**
	 * Sets the privilegedRole.
	 *
	 * @param privilegedRole 
	 *			the privilegedRole to set
	 */
	public void setPrivilegedRole(Boolean privilegedRole) {
		this.privilegedRole = privilegedRole;
	}

	/**
	 * Returns the systemRolePerms.
	 *
	 * @return 
	 *		the systemRolePerms to return
	 */
	public Collection<SystemRolePerm> getSystemRolePerms() {
		return systemRolePerms;
	}

	/**
	 * Sets the systemRolePerms.
	 *
	 * @param systemRolePerms 
	 *			the systemRolePerms to set
	 */
	public void setSystemRolePerms(Collection<SystemRolePerm> systemRolePerms) {
		this.systemRolePerms = systemRolePerms;
	}

	/**
	 * Returns the users.
	 *
	 * @return 
	 *		the users to return
	 */
	public Collection<User> getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users 
	 *			the users to set
	 */
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	/**
	 * Returns the displayName.
	 *
	 * @return 
	 *		the displayName to return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the displayName.
	 *
	 * @param displayName 
	 *			the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the systemNotifications
	 */
	public Collection<SystemNotification> getSystemNotifications() {
		return systemNotifications;
	}

	/**
	 * @param systemNotifications the systemNotifications to set
	 */
	public void setSystemNotifications(
			Collection<SystemNotification> systemNotifications) {
		this.systemNotifications = systemNotifications;
	}
	
//	public Collection<User> getCustomReportViews() {
//		return customReportViews;
//	}
//
//	public void setCustomReportViews(Collection<User> customReportViews) {
//		this.customReportViews = customReportViews;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		SystemRoleImpl o = (SystemRoleImpl)obj;
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
