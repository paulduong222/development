package gov.nwcg.isuite.core.domain.xml;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswDataAuditTracking", table="isw_data_audit_tracking")
public class IswDataAuditTracking {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_DATA_AUDIT_TRACKING", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TablePrimaryKeyId", sqlname = "TABLE_PRIMARY_KEY_ID", type="LONG")
	private Long tablePrimaryKeyId;

	@XmlTransferField(name = "OldValue", sqlname = "OLD_VALUE", type="STRING")
	private String oldValue;

	@XmlTransferField(name = "NewValue", sqlname = "NEW_VALUE", type="STRING")
	private String newValue;

	@XmlTransferField(name = "ChangeDate", sqlname="CHANGE_DATE", type="DATE")
	private Date changeDate;

	@XmlTransferField(name = "UserName", sqlname = "USER_NAME", type="STRING")
	private String userName;
	
	@XmlTransferField(name = "IswDataAuditConfig", type = "COMPLEX", target = IswDataAuditConfig.class, 
			lookupname = "ID", sourcename = "DataAuditConfigId")
	private IswDataAuditConfig iswDataAuditConfig;

	@XmlTransferField(name = "DataAuditConfigId", sqlname = "DATA_AUDIT_CONFIG_ID", type = "LONG", 
			derived = true, derivedfield = "IswDataAuditConfig")
	private Long dataAuditConfigId;
	
	@XmlTransferField(name = "AuditField1", sqlname="AUDIT_FIELD_1", type="STRING")
	private String auditField1;
	
	@XmlTransferField(name = "AuditField2", sqlname="AUDIT_FIELD_2", type="STRING")
	private String auditField2;
	
	@XmlTransferField(name = "AuditField3", sqlname="AUDIT_FIELD_3", type="STRING")
	private String auditField3;

	@XmlTransferField(name = "AuditField4", sqlname="AUDIT_FIELD_4", type="STRING")
	private String auditField4;

	@XmlTransferField(name = "AuditField5", sqlname="AUDIT_FIELD_5", type="STRING")
	private String auditField5;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswDataAuditTracking() {
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
	 * @return the tablePrimaryKeyId
	 */
	public Long getTablePrimaryKeyId() {
		return tablePrimaryKeyId;
	}

	/**
	 * @param tablePrimaryKeyId the tablePrimaryKeyId to set
	 */
	public void setTablePrimaryKeyId(Long tablePrimaryKeyId) {
		this.tablePrimaryKeyId = tablePrimaryKeyId;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate the changeDate to set
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the auditField1
	 */
	public String getAuditField1() {
		return auditField1;
	}

	/**
	 * @param auditField1 the auditField1 to set
	 */
	public void setAuditField1(String auditField1) {
		this.auditField1 = auditField1;
	}

	/**
	 * @return the auditField2
	 */
	public String getAuditField2() {
		return auditField2;
	}

	/**
	 * @param auditField2 the auditField2 to set
	 */
	public void setAuditField2(String auditField2) {
		this.auditField2 = auditField2;
	}

	/**
	 * @return the auditField3
	 */
	public String getAuditField3() {
		return auditField3;
	}

	/**
	 * @param auditField3 the auditField3 to set
	 */
	public void setAuditField3(String auditField3) {
		this.auditField3 = auditField3;
	}

	/**
	 * @return the auditField4
	 */
	public String getAuditField4() {
		return auditField4;
	}

	/**
	 * @param auditField4 the auditField4 to set
	 */
	public void setAuditField4(String auditField4) {
		this.auditField4 = auditField4;
	}

	/**
	 * @return the auditField5
	 */
	public String getAuditField5() {
		return auditField5;
	}

	/**
	 * @param auditField5 the auditField5 to set
	 */
	public void setAuditField5(String auditField5) {
		this.auditField5 = auditField5;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param iswDataAuditConfig the iswDataAuditConfig to set
	 */
	public void setIswDataAuditConfig(IswDataAuditConfig iswDataAuditConfig) {
		this.iswDataAuditConfig = iswDataAuditConfig;
	}

	/**
	 * @return the iswDataAuditConfig
	 */
	public IswDataAuditConfig getIswDataAuditConfig() {
		return iswDataAuditConfig;
	}

	/**
	 * @param dataAuditConfigId the dataAuditConfigId to set
	 */
	public void setDataAuditConfigId(Long dataAuditConfigId) {
		this.dataAuditConfigId = dataAuditConfigId;
	}

	/**
	 * @return the dataAuditConfigId
	 */
	public Long getDataAuditConfigId() {
		return dataAuditConfigId;
	}


}
