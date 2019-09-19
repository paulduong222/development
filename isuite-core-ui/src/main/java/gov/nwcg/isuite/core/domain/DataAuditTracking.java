package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Date;

public interface DataAuditTracking extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the tablePrimaryKeyId
	 */
	public Long getTablePrimaryKeyId();
	
	/**
	 * @param tablePrimaryKeyId the tablePrimaryKeyId to set
	 */
	public void setTablePrimaryKeyId(Long tablePrimaryKeyId);
	
	/**
	 * @return the oldValue
	 */
	public String getOldValue();
	
	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) ;
	
	/**
	 * @return the newValue
	 */
	public String getNewValue();

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue);

	/**
	 * @return the changeDate
	 */
	public Date getChangeDate();
	

	/**
	 * @param changeDate the changeDate to set
	 */
	public void setChangeDate(Date changeDate) ;
	
	
	/**
	 * @return the dataAuditConfig
	 */
	public DataAuditConfig getDataAuditConfig() ;
	
	/**
	 * @param dataAuditConfig the dataAuditConfig to set
	 */
	public void setDataAuditConfig(DataAuditConfig dataAuditConfig) ;

	/**
	 * @return the auditField1
	 */
	public String getAuditField1() ;

	/**
	 * @param auditField1 the auditField1 to set
	 */
	public void setAuditField1(String auditField1) ;

	/**
	 * @return the auditField2
	 */
	public String getAuditField2() ;

	/**
	 * @param auditField2 the auditField2 to set
	 */
	public void setAuditField2(String auditField2);

	/**
	 * @return the auditField3
	 */
	public String getAuditField3() ;

	/**
	 * @param auditField3 the auditField3 to set
	 */
	public void setAuditField3(String auditField3) ;

	/**
	 * @return the auditField4
	 */
	public String getAuditField4() ;

	/**
	 * @param auditField4 the auditField4 to set
	 */
	public void setAuditField4(String auditField4);

	/**
	 * @return the auditField5
	 */
	public String getAuditField5() ;

	/**
	 * @param auditField5 the auditField5 to set
	 */
	public void setAuditField5(String auditField5);

	/**
	 * @return the userName
	 */
	public String getUserName() ;

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName);

	
}
