package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;

public interface DataAuditConfig extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the tableName
	 */
	public String getTableName();

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) ;

	/**
	 * @return the columnName
	 */
	public String getColumnName();

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName);

	/**
	 * @return the dataType
	 */
	public String getDataType();

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) ;

	/**
	 * @return the auditEventType
	 */
	public String getAuditEventType();

	/**
	 * @param auditEventType the auditEventType to set
	 */
	public void setAuditEventType(String auditEventType);

	/**
	 * @return the enabled
	 */
	public StringBooleanEnum getEnabled() ;

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(StringBooleanEnum enabled);

	/**
	 * @return the dataAuditTrackings
	 */
	public Collection<DataAuditTracking> getDataAuditTrackings() ;
	
	/**
	 * @param dataAuditTrackings the dataAuditTrackings to set
	 */
	public void setDataAuditTrackings(
			Collection<DataAuditTracking> dataAuditTrackings);

	
}
