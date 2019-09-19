package gov.nwcg.isuite.framework.customreports.fieldformatters;


public class BooleanFieldFormatter extends AbstractFieldFormatter {
	private enum BooleanFormatTypeEnum{
		
		BOOLEAN_YES_NO("case when {0} = 'Y' then 'YES' else 'NO' end"
				,"case when {0} = true then 'YES' else 'NO' end")
		,BOOLEAN_Y_N("case when {0} = 'Y' then 'Y' else 'N' end"
				,"case when {0} = true then 'Y' else 'N' end")
		,BOOLEAN_YES_BLANK("case when {0} = 'Y' then 'YES' else '' end"
				,"case when {0} = true then 'YES' else '' end")
		,BOOLEAN_Y_BLANK("case when {0} = 'Y' then 'Y' else '' end"
				,"case when {0} = true then 'Y' else '' end")
		,BOOLEAN_X_BLANK("case when {0} = 'Y' then 'X' else '' end"
				,"case when {0} = true then 'X' else '' end")
		,BOOLEAN_BLANK_X("case when {0} = 'Y' then '' else 'X' end"
				,"case when {0} = true then '' else 'X' end")				
		,BOOLEAN_ONE_ZERO("case when {0} = 'Y' then '1' else '0' end"
				,"case when {0} = true then '1' else '0' end")
		,BOOLEAN_TRUE_FALSE("case when {0} = 'Y' then 'TRUE' else 'FALSE' end"
				,"case when {0} = true then 'TRUE' else 'FALSE' end")
		,BOOLEAN_T_F("case when {0} = 'Y' then 'T' else 'F' end"
				,"case when {0} = true then 'T' else 'F' end")
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		BooleanFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(BooleanFormatTypeEnum formatTypeEnum : BooleanFormatTypeEnum.values()){
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
		String sqlTemplate=BooleanFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported BooleanFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName);
		
		return sqlTemplate;
	}

}
