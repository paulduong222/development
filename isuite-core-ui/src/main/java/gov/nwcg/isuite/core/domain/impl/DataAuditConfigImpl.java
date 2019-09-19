package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "isw_data_audit_config")
@SequenceGenerator(name="SEQ_DATA_AUDIT_CONFIG", sequenceName="SEQ_DATA_AUDIT_CONFIG")

public class DataAuditConfigImpl extends PersistableImpl implements DataAuditConfig {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DATA_AUDIT_CONFIG")
	private Long id = 0L;

	@Column(name = "TABLE_NAME", length = 60)
	private String tableName;

	@Column(name = "COLUMN_NAME", length = 60)
	private String columnName;

	@Column(name = "DATA_TYPE", length = 20)
	private String dataType;

	@Column(name = "AUDIT_EVENT_TYPE", length = 20)
	private String auditEventType;

	@Column(name="ENABLED",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum enabled;

	@OneToMany(targetEntity=DataAuditTrackingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataAuditConfig")
	private Collection<DataAuditTracking> dataAuditTrackings = new ArrayList<DataAuditTracking>();

	/**
	 * Default constructor.
	 * 
	 */
	public DataAuditConfigImpl() {
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
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the auditEventType
	 */
	public String getAuditEventType() {
		return auditEventType;
	}

	/**
	 * @param auditEventType the auditEventType to set
	 */
	public void setAuditEventType(String auditEventType) {
		this.auditEventType = auditEventType;
	}

	/**
	 * @return the dataAuditTrackings
	 */
	public Collection<DataAuditTracking> getDataAuditTrackings() {
		return dataAuditTrackings;
	}

	/**
	 * @param dataAuditTrackings the dataAuditTrackings to set
	 */
	public void setDataAuditTrackings(
			Collection<DataAuditTracking> dataAuditTrackings) {
		this.dataAuditTrackings = dataAuditTrackings;
	}

	/**
	 * @return the enabled
	 */
	public StringBooleanEnum getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(StringBooleanEnum enabled) {
		this.enabled = enabled;
	}



}
