package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class TextFieldAggregator extends AbstractFieldAggregator {

	private enum TextAggregatorTypeEnum{
			
		TEXT_COUNT("count({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		TextAggregatorTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		TextAggregatorTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(TextAggregatorTypeEnum aggregatorTypeEnum : TextAggregatorTypeEnum.values()){
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
		String sqlTemplate=TextAggregatorTypeEnum.getTemplate(aggregateType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported TextFieldAggregator type - '"+aggregateType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
