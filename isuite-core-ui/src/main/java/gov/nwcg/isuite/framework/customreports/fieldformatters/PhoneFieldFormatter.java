package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class PhoneFieldFormatter extends AbstractFieldFormatter {

	private enum PhoneFormatTypeEnum{
		
		// 123-123-1234
		PHONE_FULL(" case when length(FIELD) = 10 then substr(FIELD,1,3) || '-' || substr(FIELD,4,3) || '-' || substr(FIELD,7,4) else FIELD end "
				, " case when length(FIELD) = 10 then substring(FIELD,1,3) || '-' || substring(FIELD,4,3) || '-' || substring(FIELD,7,4) else FIELD end ")
	;

		private String sqlTemplateOracle;
		private String sqlTemplatePostgres;
		
		PhoneFormatTypeEnum(String sqlText){
			sqlTemplateOracle=sqlText;
			sqlTemplatePostgres=sqlText;
		}
		
		PhoneFormatTypeEnum(String oracleSqlText, String postgresSqlText){
			sqlTemplateOracle=oracleSqlText;
			sqlTemplatePostgres=postgresSqlText;
		}
		
		static String getTemplate(String formatType,String databaseType){
			for(PhoneFormatTypeEnum formatTypeEnum : PhoneFormatTypeEnum.values()){
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
		String sqlTemplate=PhoneFormatTypeEnum.getTemplate(formatType,super.databaseType);

		if(sqlTemplate.equals(""))
			throw new Exception("Unsupported PhoneFieldFormatter type - '"+formatType+"' ");

		sqlTemplate=sqlTemplate.replace("FIELD", fieldName1);
		
		return sqlTemplate;
	}
}
