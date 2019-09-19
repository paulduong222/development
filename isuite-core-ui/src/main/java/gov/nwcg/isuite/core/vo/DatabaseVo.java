package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class DatabaseVo extends AbstractVo{
	private String databaseName;
	private String password;
	private String confirmPassword;
	private Boolean isAutoBackup;
	private Integer backupInterval;
	private String passwordHash;
	private String additionalBackupDestination;
	private Boolean isActive;
	private String masterDb;
	private String datasource;
	
	public DatabaseVo(){
		
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the backupInterval
	 */
	public Integer getBackupInterval() {
		return backupInterval;
	}

	/**
	 * @param backupInterval the backupInterval to set
	 */
	public void setBackupInterval(Integer backupInterval) {
		this.backupInterval = backupInterval;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the additionalBackupDestination
	 */
	public String getAdditionalBackupDestination() {
		return additionalBackupDestination;
	}

	/**
	 * @param additionalBackupDestination the additionalBackupDestination to set
	 */
	public void setAdditionalBackupDestination(String additionalBackupDestination) {
		this.additionalBackupDestination = additionalBackupDestination;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the masterDb
	 */
	public String getMasterDb() {
		return masterDb;
	}

	/**
	 * @param masterDb the masterDb to set
	 */
	public void setMasterDb(String masterDb) {
		this.masterDb = masterDb;
	}

	/**
	 * @return the datasource
	 */
	public String getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	/**
	 * @return the isAutoBackup
	 */
	public Boolean getIsAutoBackup() {
		return isAutoBackup;
	}

	/**
	 * @param isAutoBackup the isAutoBackup to set
	 */
	public void setIsAutoBackup(Boolean isAutoBackup) {
		this.isAutoBackup = isAutoBackup;
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
}
