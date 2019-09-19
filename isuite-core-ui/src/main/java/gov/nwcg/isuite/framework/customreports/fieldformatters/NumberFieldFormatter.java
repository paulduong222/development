package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class NumberFieldFormatter extends AbstractFieldFormatter {

	private enum NumberFormatTypeEnum{
		
		NUMBER_DEFAULT("to_char({0}, '999,999,999,999.99')")
		,NUMBER_ROUNDED("to_char(round({0}), '999,999,999,999')")
		,NUMBER_TRUNCATED("to_char(trunc({0}), '999,999,999,999')")
		,NUMBER_NO_COMMA("to_char({0}, '999999999999.99')")
		,NUMBER_NO_COMMA_ROUNDED("to_char(round({0}), '999999999999')")
		,NUMBER_NO_COMMA_TRUNCATED("to_char(trunc({0}), '999999999999')")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		NumberFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		NumberFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(NumberFormatTypeEnum formatTypeEnum : NumberFormatTypeEnum.values()){
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
		String sqlTemplate=NumberFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported NumberFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}

}
