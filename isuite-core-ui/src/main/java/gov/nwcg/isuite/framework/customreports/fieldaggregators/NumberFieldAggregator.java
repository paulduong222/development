package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class NumberFieldAggregator extends AbstractFieldAggregator {

	private enum NumberAggregatorTypeEnum{
		
		NUMBER_SUM("sum({0})")
		,NUMBER_MAX("max({0})")
		,NUMBER_MIN("min({0})")
		,NUMBER_AVG("avg({0})")
		,NUMBER_MEDIAN("median({0})")
		,NUMBER_COUNT("count({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		NumberAggregatorTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		NumberAggregatorTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(NumberAggregatorTypeEnum aggregatorTypeEnum : NumberAggregatorTypeEnum.values()){
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
		String sqlTemplate=NumberAggregatorTypeEnum.getTemplate(aggregateType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported NumberFieldAggregator type - '"+aggregateType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}

}
