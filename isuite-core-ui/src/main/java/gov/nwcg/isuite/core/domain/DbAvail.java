package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

public interface DbAvail extends Persistable{
   
	/**
	 * @return the id
	 */
	public Long getId();


	/**
	 * @param id the id to set
	 */
	public void setId(Long id);


	/**
	 * @return the name
	 */
	public String getName() ;

	/**
	 * @param name the name to set
	 */
	public void setName(String name) ;

	/**
	 * @return the masterDb
	 */
	public String getMasterDb() ;

	/**
	 * @param masterDb the masterDb to set
	 */
	public void setMasterDb(String masterDb);

	/**
	 * @return the initialVersion
	 */
	public Long getInitialVersion();

	/**
	 * @param initialVersion the initialVersion to set
	 */
	public void setInitialVersion(Long initialVersion) ;

	/**
	 * @return the datasource
	 */
	public String getDatasource() ;

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource) ;

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() ;

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) ;

	/**
	 * @return the additionalBackupDestination
	 */
	public String getAdditionalBackupDestination() ;

	/**
	 * @param additionalBackupDestination the additionalBackupDestination to set
	 */
	public void setAdditionalBackupDestination(String additionalBackupDestination);

	/**
	 * @return the active
	 */
	public StringBooleanEnum getActive();

	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) ;

	/**
	 * @return the backupInterval
	 */
	public Short getBackupInterval();

	/**
	 * @param backupInterval the backupInterval to set
	 */
	public void setBackupInterval(Short backupInterval);

	/**
	 * @return the isBackup
	 */
	public StringBooleanEnum getIsBackup();

	/**
	 * @param isBackup the isBackup to set
	 */
	public void setIsBackup(StringBooleanEnum isBackup);

	/**
	 * @return the lastRecoverCode
	 */
	public String getLastRecoverCode();

	/**
	 * @param lastRecoverCode the lastRecoverCode to set
	 */
	public void setLastRecoverCode(String lastRecoverCode);

	/**
	 * @return the lastAutoBackupDate
	 */
	public Date getLastAutoBackupDate();

	/**
	 * @param lastAutoBackupDate the lastAutoBackupDate to set
	 */
	public void setLastAutoBackupDate(Date lastAutoBackupDate);
	
}
