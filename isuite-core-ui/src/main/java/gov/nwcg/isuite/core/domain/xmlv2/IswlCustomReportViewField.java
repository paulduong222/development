package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlCustomReportViewField", table = "iswl_custom_report_view_field")
public class IswlCustomReportViewField {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "IswlCustomReportView", type = "COMPLEX", target = IswlCustomReportView.class, 
			lookupname = "ID", sourcename = "CustomReportViewId")
	private IswlCustomReportView iswlCustomReportView;

	@XmlTransferField(name = "CustomReportViewId", sqlname = "VIEW_ID", type = "LONG", 
			derived = true, derivedfield = "IswlCustomReportView")
	private Long customReportViewId;

	@XmlTransferField(name = "ColumnName", sqlname = "COLUMN_NAME", type="STRING")
	private String columnName;

	@XmlTransferField(name = "DisplayName", sqlname = "DISPLAY_NAME", type="STRING")
	private String displayName;

	@XmlTransferField(name = "DataType", sqlname = "DATA_TYPE", type="STRING")
	private String dataType;

	@XmlTransferField(name = "RefDataType", sqlname = "REF_DATA_TYPE", type="STRING")
	private String refDataType;

	@XmlTransferField(name = "FormatType", sqlname = "FORMAT_TYPE", type="STRING")
	private String formatType;
	
	public IswlCustomReportViewField() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRefDataType() {
		return refDataType;
	}

	public void setRefDataType(String refDataType) {
		this.refDataType = refDataType;
	}

	/**
	 * @param iswlCustomReportView the iswlCustomReportView to set
	 */
	public void setIswlCustomReportView(IswlCustomReportView iswlCustomReportView) {
		this.iswlCustomReportView = iswlCustomReportView;
	}

	/**
	 * @return the iswlCustomReportView
	 */
	public IswlCustomReportView getIswlCustomReportView() {
		return iswlCustomReportView;
	}

	/**
	 * @param customReportViewId the customReportViewId to set
	 */
	public void setCustomReportViewId(Long customReportViewId) {
		this.customReportViewId = customReportViewId;
	}

	/**
	 * @return the customReportViewId
	 */
	public Long getCustomReportViewId() {
		return customReportViewId;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

}
