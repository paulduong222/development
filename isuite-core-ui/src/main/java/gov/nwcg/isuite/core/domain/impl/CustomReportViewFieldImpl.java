package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CustomReportView;
import gov.nwcg.isuite.core.domain.CustomReportViewField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "iswl_custom_report_view_field")
public class CustomReportViewFieldImpl implements CustomReportViewField {
	
	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;
	
	@ManyToOne(targetEntity=CustomReportViewImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name="VIEW_ID", insertable = false, updatable=false, nullable = false)
	private CustomReportView customReportView;
	
	@Column(name="VIEW_ID",insertable=false,updatable=false)
	private Long viewId;
	
	@Column(name = "COLUMN_NAME", insertable = false, updatable = false, nullable = false)
	private String columnName;

	@Column(name = "DISPLAY_NAME", insertable = false, updatable = false, nullable = false)
	private String displayName;

	@Column(name = "DATA_TYPE", insertable = false, updatable = false, nullable = false)
	private String dataType;
	
	@Column(name = "REF_DATA_TYPE", insertable = false, updatable = false)
	private String refDataType;

	@Column(name = "FORMAT_TYPE", insertable = false, updatable = false)
	private String formatType;
	
	public CustomReportViewFieldImpl() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomReportView getCustomReportView() {
		return customReportView;
	}

	public void setCustomReportView(CustomReportView customReportView) {
		this.customReportView = customReportView;
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
	 * @param viewId the viewId to set
	 */
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	/**
	 * @return the viewId
	 */
	public Long getViewId() {
		return viewId;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

}
