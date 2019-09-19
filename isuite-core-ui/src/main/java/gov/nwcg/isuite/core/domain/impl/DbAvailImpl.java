package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_DB_AVAIL", sequenceName="SEQ_DB_AVAIL")
@Table(name="isw_db_avail")
public class DbAvailImpl extends PersistableImpl implements DbAvail {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DB_AVAIL")
	private Long id = 0L;

	@Column(name="NAME", length=128, nullable=false, unique = true)
	private String name;

	@Column(name="MASTERDB", length=128, nullable=false, unique = true)
	private String masterDb;

	@Column(name="DATASOURCE", length=128, nullable=false, unique = true)
	private String datasource;

	@Column(name="INITIALVERSION")
	private Long initialVersion;

	@Column(name="PW_HASH", length=512)
	private String passwordHash;
	
	@Column(name="ADDITIONAL_BAK_DEST", length=512)
	private String additionalBackupDestination;

    @Column(name="IS_ACTIVE", length=1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum active;

    @Column(name="IS_BACKUP", length=1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBackup;
    
	@Column(name = "BACKUP_INTERVAL")
    private Short backupInterval;

	@Column(name="LAST_RECOVER_CODE", length=150)
	private String lastRecoverCode;
	
	@Column(name = "LAST_AUTO_BACKUP_DATE")
	private Date lastAutoBackupDate;

	/**
	 * Default constructor.
	 *
	 */
	public DbAvailImpl() {
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
	public StringBooleanEnum getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
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
	public StringBooleanEnum getIsBackup() {
		return isBackup;
	}

	/**
	 * @param isBackup the isBackup to set
	 */
	public void setIsBackup(StringBooleanEnum isBackup) {
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

	/**
	 * @return the lastAutoBackupDate
	 */
	public Date getLastAutoBackupDate() {
		return lastAutoBackupDate;
	}

	/**
	 * @param lastAutoBackupDate the lastAutoBackupDate to set
	 */
	public void setLastAutoBackupDate(Date lastAutoBackupDate) {
		this.lastAutoBackupDate = lastAutoBackupDate;
	}


}
