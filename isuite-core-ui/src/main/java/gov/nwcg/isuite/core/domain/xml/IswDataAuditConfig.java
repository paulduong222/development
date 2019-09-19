package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswDataAuditConfig", table="isw_data_audit_config")
public class IswDataAuditConfig {
	
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_DATA_AUDIT_CONFIG", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TableName", sqlname = "TABLE_NAME", type="STRING")
	private String tableName;

	@XmlTransferField(name = "ColumnName", sqlname = "COLUMN_NAME", type="STRING")
	private String columnName;

	@XmlTransferField(name = "DataType", sqlname = "DATA_TYPE", type="STRING")
	private String dataType;

	@XmlTransferField(name = "AuditEventType", sqlname = "AUDIT_EVENT_TYPE", type="STRING")
	private String auditEventType;

	@XmlTransferField(name = "Enabled", sqlname="ENABLED", type="STRING")
	private String enabled;


	/**
	 * Default constructor.
	 * 
	 */
	public IswDataAuditConfig() {
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
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
