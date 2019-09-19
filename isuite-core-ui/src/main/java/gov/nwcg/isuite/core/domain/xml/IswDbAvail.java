package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswDbAvail", table="isw_db_avail")
public class IswDbAvail {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_DB_AVAIL", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;

	@XmlTransferField(name = "MasterDb", sqlname="MASTERDB", type="STRING")
	private String masterDb;

	@XmlTransferField(name = "Datasource", sqlname="DATASOURCE", type="STRING")
	private String datasource;

	@XmlTransferField(name = "InitialVersion", sqlname="INITIALVERSION", type="LONG")
	private Long initialVersion;

	@XmlTransferField(name = "PasswordHash", sqlname="PW_HASH", type="STRING")
	private String passwordHash;
	
	@XmlTransferField(name = "AdditionalBackupDestination", sqlname="ADDITIONAL_BAK_DEST", type="STRING")
	private String additionalBackupDestination;

	@XmlTransferField(name = "Active", sqlname="IS_ACTIVE", type="STRING")
	private String active;

	@XmlTransferField(name = "IsBackup", sqlname="IS_BACKUP", type="STRING")
	private String isBackup;
    
	@XmlTransferField(name = "BackupInterval", sqlname = "BACKUP_INTERVAL", type="SHORT")
    private Short backupInterval;

	@XmlTransferField(name = "LastRecoverCode", sqlname="LAST_RECOVER_CODE", type="STRING")
	private String lastRecoverCode;
	
	/**
	 * Default constructor.
	 *
	 */
	public IswDbAvail() {
		super();
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the initialVersion
	 */
	public Long getInitialVersion() {
		return initialVersion;
	}

	/**
	 * @param initialVersion the initialVersion to set
	 */
	public void setInitialVersion(Long initialVersion) {
		this.initialVersion = initialVersion;
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
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the backupInterval
	 */
	public Short getBackupInterval() {
		return backupInterval;
	}

	/**
	 * @param backupInterval the backupInterval to set
	 */
	public void setBackupInterval(Short backupInterval) {
		this.backupInterval = backupInterval;
	}

	/**
	 * @return the isBackup
	 */
	public String getIsBackup() {
		return isBackup;
	}

	/**
	 * @param isBackup the isBackup to set
	 */
	public void setIsBackup(String isBackup) {
		this.isBackup = isBackup;
	}

	/**
	 * @return the lastRecoverCode
	 */
	public String getLastRecoverCode() {
		return lastRecoverCode;
	}

	/**
	 * @param lastRecoverCode the lastRecoverCode to set
	 */
	public void setLastRecoverCode(String lastRecoverCode) {
		this.lastRecoverCode = lastRecoverCode;
	}


}
