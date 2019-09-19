package gov.nwcg.isuite.framework.core.xmltransferv2.importer;

import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.lang.reflect.Method;
import java.util.Date;

@SuppressWarnings("unused")
public class ParameterTypeConverter {
	private static String[] commonParameterList = { 
		"String"
		,"int"
		,"Date"
		,"Long"
		,"Boolean"
		,"Collection"
		,"boolean"
		,"Calendar"
		,"byte[]"
		,"Integer"
		,"long"
		,"BigDecimal"
		,"List"
		,"Short"
	};

	/**
	 * Returns the index of the type specified.
	 * 
	 * @param type
	 * 			the name of the data type
	 * @return
	 * 			int index
	 */
	private static int getTypeIndex(String type){
		
		for(int i=0;i<commonParameterList.length;i++){
			if(type.equals(commonParameterList[i]))
				return i;
		}
		
		return -1;
	}

	
	public static Object convertToParameterType(Method methodSetter, String value) {
		Object parameterValue=null;
		
		Class<?> param[]=methodSetter.getParameterTypes();
		String ptype=param[0].getSimpleName();
		
		int parameterType=getTypeIndex(ptype);
		
		if(StringUtility.hasValue(value)){
			try{
				switch(parameterType)
				{
					case 0: // String
						parameterValue=value;
						break;
					case 1: // int
						parameterValue=TypeConverter.convertToInt(value);
						break;
					case 2: // Date
						if(null != value && !value.equalsIgnoreCase("NULL")){
							Date val=DateUtil.toDate(value, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
							parameterValue=val;
						}else
							parameterValue=null;
						break;
					case 3: // Long
						parameterValue=TypeConverter.convertToLong(value);
						break;
					case 4: // Boolean
						parameterValue=TypeConverter.convertToBoolean(value);
						break;
					case 5: // Collection
						break;
					case 6: // boolean
						break;
					case 7: // Calendar
						break;
					case 8: // byte[]
						break;
					case 9: // Integer
						parameterValue=TypeConverter.convertToInteger(value);
						break;
					case 10: // long
						break;
					case 11: // BigDecimal
						parameterValue=TypeConverter.convertToBigDecimal(value);
						break;
					case 13: // Short
						if(null==value || ((String)value).equalsIgnoreCase("NULL")){
							value="0";
						}
						if(StringUtility.hasValue(value)){
							Short sh=Short.valueOf(value);
							parameterValue=sh;
						}
						break;
				}
			}catch(Exception e){
				System.out.println("ParameterTypeConverter.Exception:"+e.getMessage());
			}
		}
		
		return parameterValue;
	}
	
}
