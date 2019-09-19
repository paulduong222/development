package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "isw_data_audit_tracking")
@SequenceGenerator(name="SEQ_DATA_AUDIT_TRACKING", sequenceName="SEQ_DATA_AUDIT_TRACKING")
public class DataAuditTrackingImpl extends PersistableImpl implements DataAuditTracking {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DATA_AUDIT_TRACKING")
	private Long id = 0L;

	@Column(name = "TABLE_PRIMARY_KEY_ID", nullable = false)
	private Long tablePrimaryKeyId;

	@Column(name = "OLD_VALUE", length = 2000)
	private String oldValue;

	@Column(name = "NEW_VALUE", length = 2000)
	private String newValue;

	@Column(name="CHANGE_DATE")
	private Date changeDate;

	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	@ManyToOne(targetEntity=DataAuditConfigImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_AUDIT_CONFIG_ID", nullable = false)
	private DataAuditConfig dataAuditConfig;

	@Column(name="AUDIT_FIELD_1")
	private String auditField1;
	
	@Column(name="AUDIT_FIELD_2")
	private String auditField2;
	
	@Column(name="AUDIT_FIELD_3")
	private String auditField3;

	@Column(name="AUDIT_FIELD_4")
	private String auditField4;

	@Column(name="AUDIT_FIELD_5")
	private String auditField5;
	
	/**
	 * Default constructor.
	 * 
	 */
	public DataAuditTrackingImpl() {
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
	 * @return the dataAuditConfig
	 */
	public DataAuditConfig getDataAuditConfig() {
		return dataAuditConfig;
	}

	/**
	 * @param dataAuditConfig the dataAuditConfig to set
	 */
	public void setDataAuditConfig(DataAuditConfig dataAuditConfig) {
		this.dataAuditConfig = dataAuditConfig;
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


}
