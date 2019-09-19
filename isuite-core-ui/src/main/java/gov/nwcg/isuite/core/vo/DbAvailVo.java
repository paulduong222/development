package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.impl.DbAvailImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import com.ibm.xml.crypto.util.Base64;

public class DbAvailVo extends AbstractVo {
	private String databaseName;
	private String databaseNameActual;
	private String password;
	private String confirmPassword;
	private Boolean isAutoBackup;
	private Integer backupInterval;
	private String passwordHash;
	private String backupDestination;
	private String additionalBackupDestination;
	private Boolean isActive;
	private String masterDb;
	private String datasource;
	private Long initialVersion;
	private String lastRecoverCode;
	
	public String createdDateAsString;
	public String createdTime;
	
	// change password fields
	private Boolean isChangePassword;
	private String currentPassword;
	private String changePassword;
	private String confirmChangePassword;

	private DateTransferVo lastAutoBackupDateVo=new DateTransferVo();
	
	/**
	 * Constructor
	 */
	public DbAvailVo() {
		super();
	}


	public static DbAvailVo getInstance(DbAvail entity, boolean cascadable) throws Exception {
		DbAvailVo vo = new DbAvailVo();

		if(null == entity)
			throw new Exception("Unable to build DbAvailVo instance from null DbAvail entity");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDatabaseName(entity.getName().toUpperCase());
			vo.setDatabaseNameActual(entity.getName());
			vo.setDatasource(entity.getDatasource());
			vo.setBackupDestination("C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\");
			vo.setAdditionalBackupDestination(entity.getAdditionalBackupDestination());
			vo.setInitialVersion(entity.getInitialVersion());
			vo.setIsAutoBackup(StringBooleanEnum.toBooleanValue(entity.getIsBackup()));
			vo.setBackupInterval(ShortUtil.toInteger(entity.getBackupInterval()));
			vo.setLastRecoverCode(entity.getLastRecoverCode());

			if(DateUtil.hasValue(entity.getLastAutoBackupDate()))
				DateTransferVo.populateDate(vo.getLastAutoBackupDateVo(), entity.getLastAutoBackupDate());
			
			if(StringUtility.hasValue(entity.getCreatedBy()))
				vo.setCreatedBy(entity.getCreatedBy());

			if(DateUtil.hasValue(entity.getCreatedDate())){
				vo.setCreatedDateAsString(DateUtil.toDateString(entity.getCreatedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				vo.setCreatedTime(DateUtil.toMilitaryTime(entity.getCreatedDate()));
			}
		}

		return vo;
	}

	public static Collection<DbAvailVo> getInstances(Collection<DbAvail> entities, boolean cascadable) throws Exception {
		Collection<DbAvailVo> vos = new ArrayList<DbAvailVo>();

		for(DbAvail entity : entities){
			vos.add(DbAvailVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}


	public static DbAvail toEntity(DbAvail entity,DbAvailVo sourceVo,boolean cascadable) throws Exception {
		if(null==entity) {
			entity = new DbAvailImpl();
			entity.setId(sourceVo.getId());
		}

		if(cascadable){
			entity.setName(sourceVo.getDatabaseName().toLowerCase());
			entity.setActive(StringBooleanEnum.Y);
			entity.setAdditionalBackupDestination(sourceVo.getAdditionalBackupDestination());
			entity.setLastRecoverCode(sourceVo.getLastRecoverCode());
			entity.setCreatedBy(sourceVo.getCreatedBy());
			
			String pwdhash="";

			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			
			if(LongUtility.hasValue(sourceVo.getId())){
				if(BooleanUtility.isTrue(sourceVo.getIsChangePassword())){
					byte[] bytes = enc.encrypt(sourceVo.getChangePassword().getBytes());
					Base64 base64 = new Base64();
					pwdhash=base64.encode(bytes);
					entity.setPasswordHash(pwdhash);
				}
			}else{
				byte[] bytes = enc.encrypt(sourceVo.getPassword().getBytes());
				Base64 base64 = new Base64();
				pwdhash=base64.encode(bytes);
				entity.setPasswordHash(pwdhash);
				
				entity.setInitialVersion(1L);
				entity.setDatasource(sourceVo.getDatasource());
				entity.setMasterDb("master");
			}
			
			entity.setIsBackup(StringBooleanEnum.toEnumValue(sourceVo.getIsAutoBackup()));
			entity.setBackupInterval(new Short(IntegerUtility.hasValue(sourceVo.getBackupInterval()) ? String.valueOf(sourceVo.getBackupInterval()) : "0"));
		}
		
		return entity;
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
	 * @return the isChangePassword
	 */
	public Boolean getIsChangePassword() {
		return isChangePassword;
	}


	/**
	 * @param isChangePassword the isChangePassword to set
	 */
	public void setIsChangePassword(Boolean isChangePassword) {
		this.isChangePassword = isChangePassword;
	}


	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}


	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}


	/**
	 * @return the changePassword
	 */
	public String getChangePassword() {
		return changePassword;
	}


	/**
	 * @param changePassword the changePassword to set
	 */
	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}


	/**
	 * @return the confirmChangePassword
	 */
	public String getConfirmChangePassword() {
		return confirmChangePassword;
	}


	/**
	 * @param confirmChangePassword the confirmChangePassword to set
	 */
	public void setConfirmChangePassword(String confirmChangePassword) {
		this.confirmChangePassword = confirmChangePassword;
	}


	/**
	 * @return the backupDestination
	 */
	public String getBackupDestination() {
		return backupDestination;
	}


	/**
	 * @param backupDestination the backupDestination to set
	 */
	public void setBackupDestination(String backupDestination) {
		this.backupDestination = backupDestination;
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
	 * @return the databaseNameActual
	 */
	public String getDatabaseNameActual() {
		return databaseNameActual;
	}


	/**
	 * @param databaseNameActual the databaseNameActual to set
	 */
	public void setDatabaseNameActual(String databaseNameActual) {
		this.databaseNameActual = databaseNameActual;
	}


	/**
	 * @return the createdDateAsString
	 */
	public String getCreatedDateAsString() {
		return createdDateAsString;
	}


	/**
	 * @param createdDateAsString the createdDateAsString to set
	 */
	public void setCreatedDateAsString(String createdDateAsString) {
		this.createdDateAsString = createdDateAsString;
	}


	/**
	 * @return the createdTime
	 */
	public String getCreatedTime() {
		return createdTime;
	}


	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
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
	 * @return the lastAutoBackupDateVo
	 */
	public DateTransferVo getLastAutoBackupDateVo() {
		return lastAutoBackupDateVo;
	}


	/**
	 * @param lastAutoBackupDateVo the lastAutoBackupDateVo to set
	 */
	public void setLastAutoBackupDateVo(DateTransferVo lastAutoBackupDateVo) {
		this.lastAutoBackupDateVo = lastAutoBackupDateVo;
	}


	
}
