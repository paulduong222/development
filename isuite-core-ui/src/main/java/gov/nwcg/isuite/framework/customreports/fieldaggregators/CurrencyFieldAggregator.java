package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class CurrencyFieldAggregator extends AbstractFieldAggregator {

	private enum CurrecnyAggregatorTypeEnum{
		
		CURRENCY_SUM("sum({0})")
		,CURRENCY_MAX("max({0})")
		,CURRENCY_MIN("min({0})")
		,CURRENCY_AVG("avg({0})")
		,CURRENCY_MEDIAN("median({0})")
		,CURRENCY_COUNT("count({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		CurrecnyAggregatorTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		CurrecnyAggregatorTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(CurrecnyAggregatorTypeEnum aggregatorTypeEnum : CurrecnyAggregatorTypeEnum.values()){
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
		String sqlTemplate=CurrecnyAggregatorTypeEnum.getTemplate(aggregateType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported CurrencyFieldAggregator type - '"+aggregateType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
