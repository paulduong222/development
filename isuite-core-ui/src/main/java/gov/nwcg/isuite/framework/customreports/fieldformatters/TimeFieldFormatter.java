package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class TimeFieldFormatter extends AbstractFieldFormatter {

	private enum TimeFormatTypeEnum{
		
		TIME_MILITARY("to_char({0},'HH24MI')")			// 1609
		,TIME_HH_COLON_MI("to_char({0},'HH:MI AM')")  	// 4:09 PM
		,TIME_HH24_COLON_MI("to_char({0},'HH24:MI')") 	// 16:09
		,TIME_HH("to_char({0},'HH AM')")				// 4 PM
		,TIME_HH24("to_char({0},'HH24')")				// 16
		,TIME_MI("to_char({0},'MI')")					// 09
		;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		TimeFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		TimeFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(TimeFormatTypeEnum formatTypeEnum : TimeFormatTypeEnum.values()){
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
	
	public String toSqlField(String fieldName1) throws Exception {
		String sqlTemplate=TimeFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported TimeFieldFormatter type - '"+formatType+"' ");
		
		sqlTemplate=sqlTemplate.replace("{0}", fieldName1);
		
		return sqlTemplate;
	}
}
