package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class CurrencyFieldFormatter extends AbstractFieldFormatter {

	private enum CurrencyFormatTypeEnum{
		
		CURRENCY_DEFAULT("to_char({0}, '$999,999,999,999.99')")
		,CURRENCY_ROUNDED("to_char(round({0}), '$999,999,999,999')")
		,CURRENCY_TRUNCATED("to_char(trunc({0}), '$999,999,999,999')")
		
		,CURRENCY_NO_SYMBOL("to_char({0}, '999,999,999,999.99')")
		,CURRENCY_NO_SYMBOL_ROUNDED("to_char(round({0}), '999,999,999,999')")
		,CURRENCY_NO_SYMBOL_TRUNCATED("to_char(trunc({0}), '999,999,999,999')")
		
		,CURRENCY_NO_SYMBOL_NO_COMMA("to_char({0}, '999999999999.99')")
		,CURRENCY_NO_SYMBOL_NO_COMMA_ROUNDED("to_char(round({0}), '999999999999')")
		,CURRENCY_NO_SYMBOL_NO_COMMA_TRUNCATED("to_char(trunc({0}), '999999999999')")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		CurrencyFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		CurrencyFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(CurrencyFormatTypeEnum formatTypeEnum : CurrencyFormatTypeEnum.values()){
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
		String sqlTemplate=CurrencyFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported CurrencyFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
