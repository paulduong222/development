package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.UserIncidentViewExclude;
import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ForeignKey;

/**
 * Implementation of a User.
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_USER", sequenceName="SEQ_USER")
@NamedQueries({
	@NamedQuery(name=UserQuery.DISABLE_USERS,query=UserQuery.DISABLE_USERS_QUERY)
	,@NamedQuery(name=UserQuery.ENABLE_USERS,query=UserQuery.ENABLE_USERS_QUERY)
	,@NamedQuery(name=UserQuery.DELETE_USERS,query=UserQuery.DELETE_USERS_QUERY)
})
@Table(name = "isw_user")
public class UserImpl extends PersistableImpl implements User {

	@Column(name = "ACCOUNT_EXPIRATION_DATE")
	private Date accountExpirationDate;

	@Column(name = "CELL_PHONE", length = 30)
	private String cellPhone;

	@OneToOne(targetEntity = IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENT_INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	@ForeignKey(name = "FK_USER__INCIDENT_ID")
	private Incident currentIncident;

	@Column(name = "CURRENT_INCIDENT_ID", length = 19, insertable = false, updatable = false)
	private Long currentIncidentId;

	@Column(name="EAUTH_ID")
	private String eauthId;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "ENABLED")
	private Boolean enabled = Boolean.TRUE; // default value, per requirement
	
	@Column(name = "FAILED_LOGIN_ATTEMPTS", nullable = false)
	private Integer failedLoginAttempts = 0;

	@Column(name = "FIRST_NAME", length = 30, nullable = false)
	private String firstName;

	@OneToOne(targetEntity = OrganizationImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "HOME_UNIT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Organization homeUnit;

	@Column(name = "HOME_UNIT_ID", length = 19, insertable = false, updatable = false)
	private Long homeUnitId;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER")
	private Long id = 0L;

	@Column(name = "LAST_LOGIN_DATE", nullable = true)
	private Date lastLoginDate;

	@Column(name = "LAST_NAME", length = 35, nullable = false)
	private String lastName;

	@Column(name = "ACCOUNT_LOCKED_TIMESTAMP")
	private Date lockedDate;

	@Column(name = "LOGIN_NAME", unique = true, length = 255, nullable = false)
	private String loginName;

	@Column(name = "IS_FROM_NAP")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFromNap;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = gov.nwcg.isuite.core.domain.impl.OrganizationImpl.class)
	@JoinTable(name = "isw_user_organization", 
			joinColumns = { @JoinColumn(name = "USER_ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "ORGANIZATION_ID") })
	private Collection<Organization> organizations;

	@Column(name = "USER_PASSWORD", length = 100)
	private String password;

	@Column(name = "USER_PASSWORD_CREATED_DATE")
	private Date passwordCreatedDate;

	@Column(name = "ROB_AGREEMENT_DATE")
	private Date robAgreementDate;
	
	@OneToOne(targetEntity = OrganizationImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIMARY_DISP_CTR_ID", insertable = true, updatable = true, unique = false, nullable = true)
	@ForeignKey(name = "FK_USER__ORG_PDC_ID")
	private Organization primaryDispatchCenter;

	@Column(name = "PRIMARY_DISP_CTR_ID", length = 19, insertable = false, updatable = false)
	private Long primaryDispatchCenterId;

	@Column(name = "RESET_PASSWORD")
	private Boolean reset;
	
	@Column(name = "SHOW_DATA_SAVED_MSG")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum showDataSavedMsg;
	
	@Column(name = "WORK_PHONE", length = 30)
	private String workPhone;

	//@OneToMany(targetEntity=GridColumnUserImpl.class,fetch = FetchType.LAZY, mappedBy = "user")
	@OneToMany(targetEntity=GridColumnUserImpl.class, mappedBy = "user")
	@BatchSize(size=200)
	private Collection<GridColumnUser> gridColumnsUser;

	@OneToMany(targetEntity=RestrictedIncidentUserImpl.class, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<RestrictedIncidentUser> restrictedIncidentUsers;

    @Column(name="DELETED_DATE")
	private Date deletedDate;
	
	@ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_user_role", 
			joinColumns = { 
				@JoinColumn(name = "user_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { 
				@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Collection<SystemRole> systemRoles = new ArrayList<SystemRole>();
	
	@OneToMany(targetEntity=UserNotificationImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<UserNotification> userNotifications = new ArrayList<UserNotification>();
	
	@OneToMany(targetEntity=UserGroupImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupOwner")
	private Collection<UserGroup> userGroups =new ArrayList<UserGroup>();
	
	@OneToMany(targetEntity=UserGroupUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<UserGroupUser> userGroupUsers = new ArrayList<UserGroupUser>();
	
	@OneToMany(targetEntity=IncidentGroupUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<IncidentGroupUser> incidentGroupUsers = new ArrayList<IncidentGroupUser>();

	@OneToMany(targetEntity=SystemNotifyUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<SystemNotifyUser> systemNotifyUsers = new ArrayList<SystemNotifyUser>();

	@OneToMany(targetEntity=UserSessionLogImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<UserSessionLog> userSessionLogs = new ArrayList<UserSessionLog>();

	@OneToMany(targetEntity=UserIncidentViewExcludeImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<UserIncidentViewExclude> userIncidentViewExcludes = new ArrayList<UserIncidentViewExclude>();

	
	public UserImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.site.SiteUser#getAccountExpirationDate()
	 */
	public Date getAccountExpirationDate() {
		return this.accountExpirationDate;
	}

	public Date getAccountLockedDate() {
		return this.lockedDate;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getCurrentIncident()
	 */
	public Incident getCurrentIncident() {
		return currentIncident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getCurrentIncidentId()
	 */
	public Long getCurrentIncidentId() {
		return currentIncidentId;
	}

	public String getEauthId() {
		return eauthId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getFailedLoginAttempts()
	 */
	public int getFailedLoginAttempts() {
		if (this.failedLoginAttempts == null) {
			return 0;
		}
		return this.failedLoginAttempts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getFirstName()
	 */
	public String getFirstName() {
		return firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getHomeUnit()
	 */
	public Organization getHomeUnit() {
		return this.homeUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getHomeUnitId()
	 */
	public Long getHomeUnitId() {
		return homeUnitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getLastLoginDate()
	 */
	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getLastName()
	 */
	public String getLastName() {
		return lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.User#getLoginName()
	 */
	public String getLoginName() {
		return loginName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getOrganizations()
	 */
	public Collection<Organization> getOrganizations() {
		if(null == organizations)
			organizations = new ArrayList<Organization>();
		return organizations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.site.SiteUser#getPassword()
	 */
	public final String getPassword() {
		// Do NOT decrypt this password coming back out.
		// return Encryption.decrypt(this.currentPassword);
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.site.SiteUser#getPasswordCreatedDate()
	 */
	public Date getPasswordCreatedDate() {
		return this.passwordCreatedDate;
	}

	/*
	public Collection<SiteUserPasswordHistory> getPasswordHistory() {
		return this.passwordHistory;
	}
	*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getPrimaryDispatchCenter()
	 */
	public Organization getPrimaryDispatchCenter() {
		return primaryDispatchCenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getPrimaryDispatchCenterId()
	 */
	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#getWorkAreas()
	 */
	public Collection<WorkArea> getWorkAreas() {
		throw new UnsupportedOperationException();
	}

	public String getWorkPhone() {
		return workPhone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.User#isEnabled()
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the reset
	 */
	public Boolean isReset() {
		return reset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#isResetPassword()
	 */
	public Boolean isResetPassword() {
		return reset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.site.SiteUser#setAccountExpirationDate(
	 * java.util.Date)
	 */
	public void setAccountExpirationDate(Date accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	public void setAccountLockedDate(Date timestamp) {
		this.lockedDate = timestamp;

	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	private void setConstructorValues(String loginName, String firstName,
			String lastName) {
		setLoginName(loginName);
		setFirstName(firstName);
		setLastName(lastName);
		setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setCurrentIncident(gov.nwcg.isuite
	 * .domain.incident.Incident)
	 */
	public void setCurrentIncident(Incident currentIncident) {
		this.currentIncident = currentIncident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setCurrentIncidentId(java.lang.Long)
	 */
	public void setCurrentIncidentId(Long currentIncidentId) {
		this.currentIncidentId = currentIncidentId;
	}

	public void setEauthId(String eauthId) {
		this.eauthId = eauthId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.User#setEnabled(boolean)
	 */
	public final void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.admin.site.SiteUser#setPassword(java.lang.String)
	 */
	public final void setEncryptedPassword(String password) throws ValidationException {
		// why are there business rules in an entity?
		
		/*
		if (password == null) {
			throw new IllegalArgumentException("password can not be null");
		}
		// ValidityStrategy passwordValitityStrategy = new
		// SitePasswordValidityStrategy();
		// passwordValitityStrategy.validate(password);

		// Encrypt incoming password
		password = Encryption.encrypt(password);

		int i = 0;
		for (SiteUserPasswordHistory p : getPasswordHistory()) {
			if (i != 5) {
				if (p.getPassword().equals(password)) {
					Collection<Enum<?>> errors = new ArrayList<Enum<?>>();
					errors.add(ErrorEnum._0025_RECENTLY_USED_PASSWORD);
					throw new ValidationException(
							"password has recently been used", errors);
				}
			}
			i++;
		}

		Date createdDate = Calendar.getInstance().getTime();
		setPasswordCreatedDate(createdDate);
		this.password = password;
		addPasswordHistory(password, createdDate);
		// log.debug("break");
		 * 
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setFailedLoginAttempts(int)
	 */
	public void setFailedLoginAttempts(int failedAttempts) {
		this.failedLoginAttempts = failedAttempts;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setFirstName(java.lang.String)
	 */
	public final void setFirstName(String firstName) {

		if (firstName == null) {
			throw new IllegalArgumentException("firstName can not be null");
		}
		this.firstName = firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setHomeUnit(gov.nwcg.isuite.domain
	 * .access.Unit)
	 */
	public void setHomeUnit(Organization unit) {
		this.homeUnit = unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setHomeUnitId(java.lang.Long)
	 */
	public void setHomeUnitId(Long homeUnitId) {
		this.homeUnitId = homeUnitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setLastLoginDate(java.util.Date)
	 */
	public void setLastLoginDate(Date loginDate) {
		this.lastLoginDate = loginDate;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.User#setLastName(java.lang.String)
	 */
	public final void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("last name may not be null");
		}
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.User#setLoginName(java.lang.String)
	 */
	public final void setLoginName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("login name can not be null");
		}
		this.loginName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setOrganizations(java.util.Collection)
	 */
	public void setOrganizations(Collection<Organization> organizations) {
		this.organizations = organizations;
	}

	/**
	 * NOTE: SHOULD ONLY BE USED FOR HIBERNATE PURPOSES. ALL OTHER CODE SHOULD
	 * BE CALLING SETENCRYPTEDPASSWORD!!!!
	 */
	public void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("password can not be null");
		}
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.site.SiteUser#setPasswordCreatedDate(java
	 * .util.Date)
	 */
	public void setPasswordCreatedDate(Date createdDate) {
		// log
		// .debug("Entering SiteUserImpl : setPasswordCreatedDate(createdDate)");
		this.passwordCreatedDate = createdDate;
	}

	/*
	public void setPasswordHistory(Collection<SiteUserPasswordHistory> passwordHistory) {
		this.passwordHistory = passwordHistory;
	}
	*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setPrimaryDispatchCenter(gov.nwcg.
	 * isuite.domain.organization.Organization)
	 */
	public void setPrimaryDispatchCenter(Organization primaryDispatchCenter) {
		this.primaryDispatchCenter = primaryDispatchCenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.domain.access.User#setPrimaryDispatchCenterId(java.lang
	 * .Long)
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId) {
		this.primaryDispatchCenterId = primaryDispatchCenterId;
	}

	/**
	 * @param reset
	 *            the reset to set
	 */
	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.site.SiteUser#resetPassword(boolean)
	 */
	public void setResetPassword(Boolean reset) {
		this.reset = reset;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.User#getGridColumnsUser()
    */
	public Collection<GridColumnUser> getGridColumnsUser() {
		return gridColumnsUser;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.User#setGridColumnsUser(java.util.Collection)
	 */
	public void setGridColumnsUser(Collection<GridColumnUser> gridColumnsUser) {
		this.gridColumnsUser = gridColumnsUser;
	}

	/**
	 * Returns the restrictedIncidentUsers.
	 *
	 * @return 
	 *		the restrictedIncidentUsers to return
	 */
	public Collection<RestrictedIncidentUser> getRestrictedIncidentUsers() {
		return restrictedIncidentUsers;
	}

	/**
	 * Sets the restrictedIncidentUsers.
	 *
	 * @param restrictedIncidentUsers 
	 *			the restrictedIncidentUsers to set
	 */
	public void setRestrictedIncidentUsers(
			Collection<RestrictedIncidentUser> restrictedIncidentUsers) {
		this.restrictedIncidentUsers = restrictedIncidentUsers;
	}

	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
	      UserImpl o = (UserImpl)obj;
	      return new EqualsBuilder()
	      	.append(new Object[]{id,accountExpirationDate,cellPhone,currentIncidentId
	      						,eauthId,email
	      						,enabled,failedLoginAttempts,firstName
	      						,homeUnitId,lastLoginDate,lastName
	      						,lockedDate,loginName,deletedDate},
	      			new Object[]{o.id,o.accountExpirationDate,o.cellPhone,o.currentIncidentId
								,o.eauthId,o.email
								,o.enabled,o.failedLoginAttempts,o.firstName
								,o.homeUnitId,o.lastLoginDate,o.lastName
								,o.lockedDate,o.loginName,o.deletedDate})
	  	    .appendSuper(super.equals(o))
	      	.isEquals();
	   }   
	   
	   /* (non-Javadoc)
	    * @see java.lang.Object#hashCode()
	    */
	   public int hashCode() {
		  return new HashCodeBuilder(31,33)
		  	.append(super.hashCode())
		  	.append(new Object[]{id,accountExpirationDate,cellPhone,currentIncidentId
						,eauthId,email
  						,enabled,failedLoginAttempts,firstName
  						,homeUnitId,lastLoginDate,lastName
  						,lockedDate,loginName,deletedDate})
		  	.toHashCode();
	   }

	   /* (non-Javadoc)
	    * @see java.lang.Object#toString()
	    */
	   public String toString() {
		   return new ToStringBuilder(this)
		       .append("id", id)
		       .append("accountExpirationDate", accountExpirationDate)
		       .append("cellPhone", cellPhone)
		       .append("currentIncidentId",currentIncidentId)
		       .append("cellPhone", cellPhone)
		       .append("eauthId", eauthId)
		       .append("email", email)
		       .append("enabled", enabled)
		       .append("failedLoginAttempts", failedLoginAttempts)
		       .append("firstName", firstName)
		       .append("homeUnitId", homeUnitId)
		       .append("lastLoginDate", lastLoginDate)
		       .append("lastName", lastName)
		       .append("lockedDate", lockedDate)
		       .append("loginName", loginName)
		       .append("deletedDate", deletedDate)
		       .appendSuper(super.toString())
		       .toString();
	   }

	/**
	 * Returns the systemRoles.
	 *
	 * @return 
	 *		the systemRoles to return
	 */
	public Collection<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	/**
	 * Sets the systemRoles.
	 *
	 * @param systemRoles 
	 *			the systemRoles to set
	 */
	public void setSystemRoles(Collection<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
	}

	/**
	 * Returns the userNotifications.
	 *
	 * @return 
	 *		the userNotifications to return
	 */
	public Collection<UserNotification> getUserNotifications() {
		return userNotifications;
	}

	/**
	 * Sets the userNotifications.
	 *
	 * @param userNotifications 
	 *			the userNotifications to set
	 */
	public void setUserNotifications(Collection<UserNotification> userNotifications) {
		this.userNotifications = userNotifications;
	}

	/**
	 * Returns the userGroups.
	 *
	 * @return 
	 *		the userGroups to return
	 */
	public Collection<UserGroup> getUserGroups() {
		return userGroups;
	}

	/**
	 * Sets the userGroups.
	 *
	 * @param userGroups 
	 *			the userGroups to set
	 */
	public void setUserGroups(Collection<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	/**
	 * Returns the userGroupUsers.
	 *
	 * @return 
	 *		the userGroupUsers to return
	 */
	public Collection<UserGroupUser> getUserGroupUsers() {
		return userGroupUsers;
	}

	/**
	 * Sets the userGroupUsers.
	 *
	 * @param userGroupUsers 
	 *			the userGroupUsers to set
	 */
	public void setUserGroupUsers(Collection<UserGroupUser> userGroupUsers) {
		this.userGroupUsers = userGroupUsers;
	}

	/**
	 * Returns the robAgreementDate.
	 *
	 * @return 
	 *		the robAgreementDate to return
	 */
	public Date getRobAgreementDate() {
		return robAgreementDate;
	}

	/**
	 * Sets the robAgreementDate.
	 *
	 * @param robAgreementDate 
	 *			the robAgreementDate to set
	 */
	public void setRobAgreementDate(Date robAgreementDate) {
		this.robAgreementDate = robAgreementDate;
	}

	/**
	 * Returns the incidentGroupUsers.
	 *
	 * @return 
	 *		the incidentGroupUsers to return
	 */
	public Collection<IncidentGroupUser> getIncidentGroupUsers() {
		return incidentGroupUsers;
	}

	/**
	 * Sets the incidentGroupUsers.
	 *
	 * @param incidentGroupUsers 
	 *			the incidentGroupUsers to set
	 */
	public void setIncidentGroupUsers(
			Collection<IncidentGroupUser> incidentGroupUsers) {
		this.incidentGroupUsers = incidentGroupUsers;
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

	/**
	 * @return the userSessionLogs
	 */
	public Collection<UserSessionLog> getUserSessionLogs() {
		return userSessionLogs;
	}

	/**
	 * @param userSessionLogs the userSessionLogs to set
	 */
	public void setUserSessionLogs(Collection<UserSessionLog> userSessionLogs) {
		this.userSessionLogs = userSessionLogs;
	}

	public Collection<UserIncidentViewExclude> getUserIncidentViewExcludes() {
		return userIncidentViewExcludes;
	}

	public void setUserIncidentViewExcludes(
			Collection<UserIncidentViewExclude> userIncidentViewExcludes) {
		this.userIncidentViewExcludes = userIncidentViewExcludes;
	}

	public StringBooleanEnum getShowDataSavedMsg() {
		return showDataSavedMsg;
	}
	
	public void setShowDataSavedMsg(StringBooleanEnum showDataSavedMag) {
		this.showDataSavedMsg = showDataSavedMag;
	}

	/**
	 * @return the isFromNap
	 */
	public StringBooleanEnum getIsFromNap() {
		return isFromNap;
	}

	/**
	 * @param isFromNap the isFromNap to set
	 */
	public void setIsFromNap(StringBooleanEnum isFromNap) {
		this.isFromNap = isFromNap;
	}
}
