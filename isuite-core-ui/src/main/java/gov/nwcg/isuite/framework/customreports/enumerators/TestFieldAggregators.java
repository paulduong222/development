package gov.nwcg.isuite.framework.customreports.enumerators;

import java.util.List;

import gov.nwcg.isuite.framework.customreports.fieldaggregators.AbstractFieldAggregator;
import gov.nwcg.isuite.framework.customreports.fieldformatters.AbstractFieldFormatter;

public class TestFieldAggregators {

	private static final String ORACLE = "ORACLE";
	private static final String POSTGRES = "POSTGRES";
	private static final String BOOLEAN = "BOOLEAN";
	private static final String CURRENCY = "CURRENCY";
	private static final String DATE = "DATE";
	private static final String NUMBER = "NUMBER";
	private static final String TEXT = "TEXT";
	private static final String TIME = "TIME";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		List<AbstractFieldAggregator> aggregatorList;
		String oraSql = "";
		String pgSql = "";
		
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(BOOLEAN, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "IS_ROSTERED");
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(BOOLEAN, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "IS_ROSTERED");

		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(CURRENCY, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_ID"); // Find a real dollar val to test
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(CURRENCY, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_ID"); // Find a real dollar val to test

		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(DATE, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "CHECK_IN_DATE");
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(DATE, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "CHECK_IN_DATE");

		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(NUMBER, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_ID");
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(NUMBER, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_ID");

		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(TEXT, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_NAME");
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(TEXT, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "RESOURCE_NAME");

		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(TIME, ORACLE);
		oraSql += generateSqlFromAggregator(aggregatorList, "CHECK_IN_DATE");
		aggregatorList = FieldAggregatorTypeEnum.getFieldAggregatorsByDataType(TIME, POSTGRES);
		pgSql += generateSqlFromAggregator(aggregatorList, "CHECK_IN_DATE");
		
		System.out.println("ORACLE SQL:\n" + oraSql + "\n");
		System.out.println("POSTGRE SQL:\n" + pgSql);
	}
	
	private static String generateSqlFromAggregator(List<AbstractFieldAggregator> aggregatorList, String colName) throws Exception{
		String sql = "";
		String SELECT = "SELECT ";
		String FROM_VIEW = " FROM iswv_resource_plan ";
		String WHERE_CLAUSE = "WHERE " + colName + " IS NOT NULL;\n";
		
		for(AbstractFieldAggregator aggregator : aggregatorList){
			sql += SELECT + aggregator.toSqlField(colName) + FROM_VIEW + WHERE_CLAUSE;
		}
		sql += "-----------------------------------------\n";
		return sql;
	}

}
