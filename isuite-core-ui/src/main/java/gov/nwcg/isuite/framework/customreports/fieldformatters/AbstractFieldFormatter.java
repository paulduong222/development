package gov.nwcg.isuite.framework.customreports.fieldformatters;

public abstract class AbstractFieldFormatter {
	protected String databaseType="";
	protected String formatType="";
	
	public abstract String toSqlField(String fieldName) throws Exception ;
	
	public void setDatabaseType(String type){
		this.databaseType=type;
	}
	
	public void setFormatType(String type){
		this.formatType=type;
	}
}
