package gov.nwcg.isuite.core.domain.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;


@XmlTransferTable(name = "IswUser", table = "isw_user")
public class IswUser {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_USER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AccountExpirationDate", sqlname = "ACCOUNT_EXPIRATION_DATE", type="DATE")
	private Date accountExpirationDate;

	@XmlTransferField(name = "CellPhone", sqlname = "CELL_PHONE", type="STRING")
	private String cellPhone;

	@XmlTransferField(name = "EauthId", sqlname = "EAUTH_ID", type="STRING")
	private String eauthId;

	@XmlTransferField(name = "Email", sqlname = "EMAIL", type="STRING")
	private String email;

	@XmlTransferField(name = "ENABLED", type="BOOLEAN")
	private Boolean enabled;

	@XmlTransferField(name = "FAILED_LOGIN_ATTEMPTS", type="INTEGER")
	private Integer failedLoginAttempts = 0;

	@XmlTransferField(name = "FIRST_NAME", type="STRING")
	private String firstName;
	
	@XmlTransferField(name = "IswHomeUnit", type="COMPLEX", target=IswOrganization.class
				, lookupname="ID", sourcename="HomeUnitId")
	private IswOrganization iswHomeUnit;
	
	@XmlTransferField(name = "HomeUnitId", sqlname="HOME_UNIT_ID", type="LONG"
				,derived=true, derivedfield="IswHomeUnit")
	private Long homeUnitId;

	@XmlTransferField(name = "LastLoginDate", sqlname = "LAST_LOGIN_DATE", type="DATE")
	private Date lastLoginDate;

	@XmlTransferField(name = "LastName", sqlname = "LAST_NAME", type="STRING")
	private String lastName;

	@XmlTransferField(name = "LockedDate", sqlname = "ACCOUNT_LOCKED_TIMESTAMP", type="DATE")
	private Date lockedDate;

	@XmlTransferField(name = "LoginName", sqlname = "LOGIN_NAME", type="STRING")
	private String loginName;

	@XmlTransferField(name = "IsFromNap", sqlname = "IS_FROM_NAP", type="STRING")
	private String isFromNap;

//	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = gov.nwcg.isuite.core.domain.impl.OrganizationImpl.class)
//	@JoinTable(name = "isw_user_organization", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ORGANIZATION_ID") })
//	private Collection<Organization> organizations;

	@XmlTransferField(name = "Password", sqlname = "USER_PASSWORD", type="STRING")
	private String password;

	@XmlTransferField(name = "PasswordCreatedDate", sqlname = "USER_PASSWORD_CREATED_DATE", type="DATE")
	private Date passwordCreatedDate;

	@XmlTransferField(name = "RobAgreementDate", sqlname = "ROB_AGREEMENT_DATE", type="DATE")
	private Date robAgreementDate;

//	@OneToOne(targetEntity = OrganizationImpl.class, fetch = FetchType.LAZY)
//	@JoinColumn(name = "PRIMARY_DISP_CTR_ID", insertable = true, updatable = true, unique = false, nullable = true)
//	@ForeignKey(name = "FK_USER__ORG_PDC_ID")
//	private Organization primaryDispatchCenter;

//	@Column(name = "PRIMARY_DISP_CTR_ID", length = 19, insertable = false, updatable = false)
//	private Long primaryDispatchCenterId;

	@XmlTransferField(name = "Reset", sqlname = "RESET_PASSWORD", type="BOOLEAN")
	private Boolean reset;

	@XmlTransferField(name = "ShowDataSavedMsg", sqlname = "SHOW_DATA_SAVED_MSG", type="STRING")
	private String showDataSavedMsg;

	@XmlTransferField(name = "WorkPhone", sqlname = "WORK_PHONE", type="STRING")
	private String workPhone;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

//	@ManyToMany(targetEntity = SystemRoleImpl.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@JoinTable(name = "isw_user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
//	private Collection<SystemRole> systemRoles = new ArrayList<SystemRole>();


	public IswUser() {

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
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the accountExpirationDate
	 */
	public Date getAccountExpirationDate() {
		return accountExpirationDate;
	}

	/**
	 * @param accountExpirationDate the accountExpirationDate to set
	 */
	public void setAccountExpirationDate(Date accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the eauthId
	 */
	public String getEauthId() {
		return eauthId;
	}

	/**
	 * @param eauthId the eauthId to set
	 */
	public void setEauthId(String eauthId) {
		this.eauthId = eauthId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the failedLoginAttempts
	 */
	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	/**
	 * @param failedLoginAttempts the failedLoginAttempts to set
	 */
	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the iswHomeUnit
	 */
	public IswOrganization getIswHomeUnit() {
		return iswHomeUnit;
	}

	/**
	 * @param iswHomeUnit the iswHomeUnit to set
	 */
	public void setIswHomeUnit(IswOrganization iswHomeUnit) {
		this.iswHomeUnit = iswHomeUnit;
	}

	/**
	 * @return the homeUnitId
	 */
	public Long getHomeUnitId() {
		return homeUnitId;
	}

	/**
	 * @param homeUnitId the homeUnitId to set
	 */
	public void setHomeUnitId(Long homeUnitId) {
		this.homeUnitId = homeUnitId;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the lockedDate
	 */
	public Date getLockedDate() {
		return lockedDate;
	}

	/**
	 * @param lockedDate the lockedDate to set
	 */
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the isFromNap
	 */
	public String getIsFromNap() {
		return isFromNap;
	}

	/**
	 * @param isFromNap the isFromNap to set
	 */
	public void setIsFromNap(String isFromNap) {
		this.isFromNap = isFromNap;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordCreatedDate
	 */
	public Date getPasswordCreatedDate() {
		return passwordCreatedDate;
	}

	/**
	 * @param passwordCreatedDate the passwordCreatedDate to set
	 */
	public void setPasswordCreatedDate(Date passwordCreatedDate) {
		this.passwordCreatedDate = passwordCreatedDate;
	}

	/**
	 * @return the robAgreementDate
	 */
	public Date getRobAgreementDate() {
		return robAgreementDate;
	}

	/**
	 * @param robAgreementDate the robAgreementDate to set
	 */
	public void setRobAgreementDate(Date robAgreementDate) {
		this.robAgreementDate = robAgreementDate;
	}

	/**
	 * @return the reset
	 */
	public Boolean getReset() {
		return reset;
	}

	/**
	 * @param reset the reset to set
	 */
	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	/**
	 * @return the showDataSavedMsg
	 */
	public String getShowDataSavedMsg() {
		return showDataSavedMsg;
	}

	/**
	 * @param showDataSavedMsg the showDataSavedMsg to set
	 */
	public void setShowDataSavedMsg(String showDataSavedMsg) {
		this.showDataSavedMsg = showDataSavedMsg;
	}

	/**
	 * @return the workPhone
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * @param workPhone the workPhone to set
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}


}
