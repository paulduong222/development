package gov.nwcg.isuite.core.domain;

public interface CustomReportViewField {

	public Long getId();

	public void setId(Long id);
	
	public CustomReportView getCustomReportView();

	public void setCustomReportView(CustomReportView customReportView);

	public String getColumnName();

	public void setColumnName(String columnName);

	public String getDisplayName();

	public void setDisplayName(String displayName);

	public String getDataType();

	public void setDataType(String dataType);

	public String getRefDataType();

	public void setRefDataType(String refDataType);

	public void setFormatType(String type);
	
	public String getFormatType();
}