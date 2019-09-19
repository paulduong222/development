package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class TimeFieldAggregator extends AbstractFieldAggregator {

	private enum TimeAggregatorTypeEnum{
		
		TIME_MAX("max({0})")
		,TIME_MIN("min({0})")
		,TIME_MEDIAN("median({0})")
		,TIME_COUNT("count({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		TimeAggregatorTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		TimeAggregatorTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(TimeAggregatorTypeEnum aggregatorTypeEnum : TimeAggregatorTypeEnum.values()){
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
		String sqlTemplate=TimeAggregatorTypeEnum.getTemplate(aggregateType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported TimeFieldAggregator type - '"+aggregateType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
