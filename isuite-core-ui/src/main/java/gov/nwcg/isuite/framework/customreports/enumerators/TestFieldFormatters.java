package gov.nwcg.isuite.framework.customreports.enumerators;

import java.util.List;

import gov.nwcg.isuite.framework.customreports.fieldformatters.AbstractFieldFormatter;

public class TestFieldFormatters {

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
		
		List<AbstractFieldFormatter> formatterList;
		String oraSql = "";
		String pgSql = "";
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(BOOLEAN, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "IS_ROSTERED");
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(BOOLEAN, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "IS_ROSTERED");
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(CURRENCY, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "RESOURCE_ID"); // Find a real dollar val to test
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(CURRENCY, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "RESOURCE_ID"); // Find a real dollar val to test
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(DATE, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "CHECK_IN_DATE");
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(DATE, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "CHECK_IN_DATE");
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(NUMBER, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "RESOURCE_ID");
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(NUMBER, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "RESOURCE_ID");
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(TEXT, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "RESOURCE_NAME");
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(TEXT, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "RESOURCE_NAME");
		
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(TIME, ORACLE);
		oraSql += generateSqlFromFormatter(formatterList, "CHECK_IN_DATE");
		formatterList = FieldFormatterTypeEnum.getFieldFormattersByDataType(TIME, POSTGRES);
		pgSql += generateSqlFromFormatter(formatterList, "CHECK_IN_DATE");
		
		System.out.println("ORACLE SQL:\n" + oraSql + "\n");
		System.out.println("POSTGRE SQL:\n" + pgSql);
	}
	
	private static String generateSqlFromFormatter(List<AbstractFieldFormatter> formatterList, String colName) throws Exception{
		String sql = "";
		String SELECT = "SELECT ";
		String FROM_VIEW = " FROM iswv_resource_plan ";
		String WHERE_CLAUSE = "WHERE " + colName + " IS NOT NULL;\n";
		
		for(AbstractFieldFormatter formatter : formatterList){
			sql += SELECT + formatter.toSqlField(colName) + FROM_VIEW + WHERE_CLAUSE;
		}
		sql += "-----------------------------------------\n";
		return sql;
	}

}
