package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class DateFieldFormatter extends AbstractFieldFormatter {

	private enum DateFormatTypeEnum{
		
		DATE_MMDDYYYY_SLASH("to_char({0},'MM/DD/YYYY')")
		,DATE_MMDDYYYY_DASH("to_char({0},'MM-DD-YYYY')")
		,DATE_YYYYMMDD_SLASH("to_char({0},'YYYY/MM/DD')")
		,DATE_MMDD_SLASH("to_char({0},'MM/DD')")
		,DATE_MMYYYY_SLASH("to_char({0},'MM/YYYY')")
		,DATE_MON_DAY("to_char({0},'MON DD')")
		,DATE_MONTH_DAY("to_char({0},'MONTH DD')")
		,DATE_MON_DAY_YYYY("to_char({0},'MON DD, YYYY')")
		,DATE_MONTH_DAY_YYYY("to_char({0},'MONTH DD, YYYY')")
		,DATE_DAY("to_char({0},'DD')")
		,DATE_MM("to_char({0},'MM')")
		,DATE_MON("to_char({0},'MON')")
		,DATE_MONTH("to_char({0},'MONTH')")
		,DATE_YY("to_char({0},'YY')")
		,DATE_YYYY("to_char({0},'YYYY')")
		;
		
		/*
		MANU TODO: NEED TO TEST WHICH OF THE THREE WILL WORK FOR POSTGRE
			,PLUS_N_DAYS("to_char({0},'YYYY')","timestamp '{0}' + ({1} * interval '1 day')")
		OR
			,PLUS_N_DAYS("to_char({0},'YYYY')","timestamp {0} + ({1} * interval '1 day')")
		OR
			,PLUS_N_DAYS("to_char({0},'YYYY')","{0} + ({1} * interval '1 day')")
		
		,PLUS_N_DAYS("to_date({0},'YYYY/MM/DD') + interval '{1}' day","timestamp '{0}' + ({1} * interval '1 day')")
		,MINUS_N_DAYS("to_date({0},'YYYY/MM/DD') - interval '{1}' day","timestamp '{0}' - ({1} * interval '1 day')")
		,PLUS_N_MONTHS("to_date({0},'YYYY/MM/DD') + interval '{1}' month","timestamp '{0}' + ({1} * interval '1 month')")
		,MINUS_N_MONTHS("to_date({0},'YYYY/MM/DD') - interval '{1}' month","timestamp '{0}' - ({1} * interval '1 month')")
		
		*/

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		DateFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		DateFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(DateFormatTypeEnum formatTypeEnum : DateFormatTypeEnum.values()){
				if(formatTypeEnum.name().equals(formatType)){
					if(databaseType.equals("ORACLE"))
						return formatTypeEnum.sqlTemplateOracle;
					else
						return formatTypeEnum.sqlTemplatePostgres;
				}
			}
			
			return "";
		}
	}
	
	public String toSqlField(String fieldName) throws Exception {
		String sqlTemplate=DateFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported DateFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
	
/*	public String toSqlField(String fieldName1, String fieldName2) throws Exception {
		String sqlTemplate=DateFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported DateFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName1);
		sqlTemplate=sqlTemplate.replace("{1}", fieldName2);
		
		return sqlTemplate;
	}*/

}
