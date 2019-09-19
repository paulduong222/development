package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class TextFieldFormatter extends AbstractFieldFormatter {

	private enum TextFormatTypeEnum{
		
		TEXT_UPPER_CASE("upper({0})")
		,TEXT_LOWER_CASE("lower({0})")
		,TEXT_TITLE_CASE("initcap({0})")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		TextFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		TextFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(TextFormatTypeEnum formatTypeEnum : TextFormatTypeEnum.values()){
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
		String sqlTemplate=TextFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported TextFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}
}
