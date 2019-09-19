package gov.nwcg.isuite.framework.customreports.enumerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.vo.CustomReportColumnFormatVo;
import gov.nwcg.isuite.framework.customreports.fieldformatters.AbstractFieldFormatter;

public enum FieldFormatterTypeEnum {
	
	BOOLEAN_YES_NO("BOOLEAN_YES_NO","YES OR NO","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_Y_N("BOOLEAN_Y_N","Y OR N","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_YES_BLANK("BOOLEAN_YES_BLANK","YES OR BLANK","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_Y_BLANK("BOOLEAN_Y_BLANK","Y OR BLANK","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_X_BLANK("BOOLEAN_X_BLANK","X OR BLANK","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_BLANK_X("BOOLEAN_BLANK_X","BLANK OR X","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_ONE_ZERO("BOOLEAN_ONE_ZERO","1 OR 0","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_TRUE_FALSE("BOOLEAN_TRUE_FALSE","TRUE OR FALSE","BOOLEAN","BooleanFieldFormatter")
	,BOOLEAN_T_F("BOOLEAN_T_F","T OR F","BOOLEAN","BooleanFieldFormatter")
	
	,CURRENCY_DEFAULT("CURRENCY_DEFAULT","$274,985.51","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_ROUNDED("CURRENCY_ROUNDED","$274,986","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_TRUNCATED("CURRENCY_TRUNCATED","$274,985","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL("CURRENCY_NO_SYMBOL","274,985.51","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL_ROUNDED("CURRENCY_NO_SYMBOL_ROUNDED","274,986","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL_TRUNCATED("CURRENCY_NO_SYMBOL_TRUNCATED","274,985","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL_NO_COMMA("CURRENCY_NO_SYMBOL_NO_COMMA","274985.51","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL_NO_COMMA_ROUNDED("CURRENCY_NO_SYMBOL_NO_COMMA_ROUNDED","274986","CURRENCY","CurrencyFieldFormatter")
	,CURRENCY_NO_SYMBOL_NO_COMMA_TRUNCATED("CURRENCY_NO_SYMBOL_NO_COMMA_TRUNCATED","274985","CURRENCY","CurrencyFieldFormatter")
	
	,DATE_MMDDYYYY_SLASH("DATE_MMDDYYYY_SLASH","08/20/1997","DATE","DateFieldFormatter")
	,DATE_MMDDYYYY_DASH("DATE_MMDDYYYY_DASH","08-20-1997","DATE","DateFieldFormatter")
	,DATE_YYYYMMDD_SLASH("DATE_YYYYMMDD_SLASH","1997/08/20","DATE","DateFieldFormatter")
	,DATE_MMDD_SLASH("DATE_MMDD_SLASH","08/20","DATE","DateFieldFormatter")
	,DATE_MMYYYY_SLASH("DATE_MMYYYY_SLASH","08/1997","DATE","DateFieldFormatter")
	,DATE_MON_DAY("DATE_MON_DAY","AUG 20","DATE","DateFieldFormatter")
	,DATE_MONTH_DAY("DATE_MONTH_DAY","AUGUST 20","DATE","DateFieldFormatter")
	,DATE_MON_DAY_YYYY("DATE_MON_DAY_YYYY","AUG 20, 1997","DATE","DateFieldFormatter")
	,DATE_MONTH_DAY_YYYY("DATE_MONTH_DAY_YYYY","AUGUST 20,1997","DATE","DateFieldFormatter")
	,DATE_DAY("DATE_DAY","20","DATE","DateFieldFormatter")
	,DATE_MM("DATE_MM","08","DATE","DateFieldFormatter")
	,DATE_MON("DATE_MON","AUG","DATE","DateFieldFormatter")
	,DATE_MONTH("DATE_MONTH","AUGUST","DATE","DateFieldFormatter")
	,DATE_YY("DATE_YY","97","DATE","DateFieldFormatter")
	,DATE_YYYY("DATE_YYYY","1997","DATE","DateFieldFormatter")
	
	,NUMBER_DEFAULT("NUMBER_DEFAULT","274,985.51","NUMBER","NumberFieldFormatter")
	,NUMBER_ROUNDED("NUMBER_ROUNDED","274,986","NUMBER","NumberFieldFormatter")
	,NUMBER_TRUNCATED("NUMBER_TRUNCATED","274,985","NUMBER","NumberFieldFormatter")
	,NUMBER_NO_COMMA("NUMBER_NO_COMMA","274985.51","NUMBER","NumberFieldFormatter")
	,NUMBER_NO_COMMA_ROUNDED("NUMBER_NO_COMMA_ROUNDED","274986","NUMBER","NumberFieldFormatter")
	,NUMBER_NO_COMMA_TRUNCATED("NUMBER_NO_COMMA_TRUNCATED","274985","NUMBER","NumberFieldFormatter")
	
	,TEXT_UPPER_CASE("TEXT_UPPER_CASE","UPPER CASE","STRING","TextFieldFormatter")
	,TEXT_LOWER_CASE("TEXT_LOWER_CASE","LOWER CASE","STRING","TextFieldFormatter")
	,TEXT_TITLE_CASE("TEXT_TITLE_CASE","TITLE CASE","STRING","TextFieldFormatter")
	
	,TIME_MILITARY("TIME_MILITARY","1855","TIME","TimeFieldFormatter")
	,TIME_HH_COLON_MI("TIME_HH_COLON_MI","6:55 PM","TIME","TimeFieldFormatter")
	,TIME_HH24_COLON_MI("TIME_HH24_COLON_MI","18:55","TIME","TimeFieldFormatter")
	,TIME_HH("TIME_HH","6 PM","TIME","TimeFieldFormatter")
	,TIME_HH24("TIME_HH24","18","TIME","TimeFieldFormatter")
	,TIME_MI("TIME_MI","55","TIME","TimeFieldFormatter")
	
	,PHONE_FULL("PHONE_FULL","123-123-1234", "STRING","PhoneFieldFormatter")
	;
	
	private String formatType="";
	private String displayName="";
	private String dataType="";
	private String formatterClassName;

   	private static final String PACKAGE_PREFIX = "gov.nwcg.isuite.framework.customreports.fieldformatters.";
   
   	FieldFormatterTypeEnum(String formatType,String displayName,String dataType,String formatterClassName){
   		this.displayName=displayName;
   		this.formatType=formatType;
   		this.dataType=dataType;
   		this.formatterClassName=formatterClassName;
   	}

	public static AbstractFieldFormatter getFieldFormatter(String formatType, String databaseType) throws Exception {
	   for(FieldFormatterTypeEnum fieldFormatter : FieldFormatterTypeEnum.values()){
		   if(fieldFormatter.formatType.equals(formatType)){
				Class targetClass=Class.forName(PACKAGE_PREFIX + fieldFormatter.formatterClassName);
				AbstractFieldFormatter formatterClass=(AbstractFieldFormatter)targetClass.newInstance();
				formatterClass.setFormatType(fieldFormatter.formatType);
				formatterClass.setDatabaseType(databaseType);
				
				return formatterClass;
		   }
	   }
	   
	   return null;
	}
	
	/**
	 * This method is used primarily to test this Enum. It returns back a list of 
	 * Formatters based on the datatype of the FormatterTypeEnum.
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public static List<AbstractFieldFormatter> getFieldFormattersByDataType(String dataType, String databaseType) throws Exception {
		List<AbstractFieldFormatter> formatterList = new ArrayList<AbstractFieldFormatter>();
		
		for(FieldFormatterTypeEnum fieldFormatter : FieldFormatterTypeEnum.values()){
		   if(fieldFormatter.dataType.equals(dataType)){
				Class targetClass=Class.forName(PACKAGE_PREFIX + fieldFormatter.formatterClassName);
				AbstractFieldFormatter formatterClass=(AbstractFieldFormatter)targetClass.newInstance();
				formatterClass.setFormatType(fieldFormatter.formatType);
				formatterClass.setDatabaseType(databaseType);
				
				formatterList.add(formatterClass);
		   }
		}
		return formatterList;
	}
	
	/**
	 * Returns Collection of CustomReportColumnFormatVo objects. This will be called by flex to 
	 * populate the drop down showing the formatting options available to a datatype.
	 * @param dataType
	 * @param databaseType
	 * @return
	 */
	public static Collection<CustomReportColumnFormatVo> getCRColumnFormatVos(String dataType) {
		Collection<CustomReportColumnFormatVo> vos = new ArrayList<CustomReportColumnFormatVo>();
		
		for(FieldFormatterTypeEnum fieldFormatter : FieldFormatterTypeEnum.values()){
		   if(fieldFormatter.dataType.equals(dataType)){
				vos.add(new CustomReportColumnFormatVo(fieldFormatter.formatType, fieldFormatter.displayName, fieldFormatter.dataType));
		   }
		}
		return vos;
	}
	
	/**
	 * Returns all values in this enum
	 * @return Collection of CustomReportColumnFormatVo objects
	 */
	public static Collection<CustomReportColumnFormatVo> getCRColumnFormatVos() {
		Collection<CustomReportColumnFormatVo> vos = new ArrayList<CustomReportColumnFormatVo>();
		
		for(FieldFormatterTypeEnum fieldFormatter : FieldFormatterTypeEnum.values()){
		   	vos.add(new CustomReportColumnFormatVo(fieldFormatter.formatType, fieldFormatter.displayName, fieldFormatter.dataType));
		}
		return vos;
	}
	
	/**
	 * @param formatType
	 * @return
	 */
	public static CustomReportColumnFormatVo getCRColomnFormatVo(String formatType) {
		FieldFormatterTypeEnum fieldFormatterTypeEnum = FieldFormatterTypeEnum.valueOf(formatType);
		CustomReportColumnFormatVo vo = CustomReportColumnFormatVo.getInstance(fieldFormatterTypeEnum);
		
		return vo;
		
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the formatType
	 */
	public String getFormatType() {
		return formatType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @return the formatterClassName
	 */
	public String getFormatterClassName() {
		return formatterClassName;
	}
	
	
}
