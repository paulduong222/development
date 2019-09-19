package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class BooleanFieldAggregator extends AbstractFieldAggregator {

	private enum BooleanAggregatorTypeEnum{
			
		BOOLEAN_COUNT("count({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		BooleanAggregatorTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		BooleanAggregatorTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(BooleanAggregatorTypeEnum aggregatorTypeEnum : BooleanAggregatorTypeEnum.values()){
				if(aggregatorTypeEnum.name().equals(formatType)){
					if(databaseType.equals("ORACLE"))
						return aggregatorTypeEnum.sqlTemplateOracle;
					else
						return aggregatorTypeEnum.sqlTemplatePostgres;
				}
			}
			return "";
		}
	}
	
	public String toSqlField(String fieldName) throws Exception {
		String sqlTemplate=BooleanAggregatorTypeEnum.getTemplate(aggregateType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported BooleanFieldAggregator type - '"+aggregateType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
