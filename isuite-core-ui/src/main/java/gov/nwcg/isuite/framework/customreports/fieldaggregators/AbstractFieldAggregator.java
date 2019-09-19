package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public abstract class AbstractFieldAggregator {
	protected String databaseType="";
	protected String aggregateType="";
	
	public abstract String toSqlField(String fieldName) throws Exception ;
	
	public void setDatabaseType(String type){
		this.databaseType=type;
	}
	
	public void setAggregateType(String type){
		this.aggregateType=type;
	}
}
