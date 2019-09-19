package gov.nwcg.isuite.framework.core.datatransfer;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.StringTokenizer;

public class EntityXmlData {

	public static String getSpace(int level){
		String space="";
		
		for(int i=0;i<level;i++){
			space=space+"  ";
		}
		
		return space;
	}

	public static Boolean isExcluded(String list, String field){
		StringTokenizer st = new StringTokenizer(list,",");
		Boolean bFound=false;
		while(st.hasMoreTokens()){
			String val=(String)st.nextToken();
			if(val.equalsIgnoreCase(field))
				bFound=true;
		}
		return bFound;
	}
	
	/*
	 * Build list of xml nodes for all simple data types.
	 * Excludes Collections,Entities,etc...
	 */
	public static String buildSimpleFields(Class targetClass, Object targetInstance, int level) throws Exception{
		StringBuffer xml = new StringBuffer();

		// data types to include (also enums)
		String standardTypes="Date,Long,String,int,Boolean,Integer,BigDecimal,Short";
		
		// fields to exclude
		String excludeFieldNames="Class,Identity,TransferableIdentity";
		
		Field[] fields = targetClass.getDeclaredFields();
		Method[] methods=targetClass.getMethods();
		for(Method method : methods){
			
			String innerSpace=getSpace(level);
			
			String methodName=method.getName();
			
			if(methodName.startsWith("get")){
				String returnType=method.getReturnType().getSimpleName();
				String returnType2=method.getReturnType().getCanonicalName();

				String fieldName=methodName.substring(3,methodName.length());

				// check data type is a simple standard type
				if(standardTypes.indexOf(returnType)>-1 && !isExcluded(excludeFieldNames,fieldName)){
					Object value=method.invoke(targetInstance);

					if(null!=value && value.equals("NAN"))value=null;
					
					if(returnType.equals("Date")){
						if(null!=value){
							String dt=DateUtil.toDateString((Date)value,DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
							value=dt;
						}
					}
					
					xml.append(innerSpace+"<"+fieldName+">"+(null==value?"":value)+"</"+fieldName+">"+"\n");
				}else{
					// check data type is an enum
					if(Class.forName(returnType2).isEnum()==true){
						Object value=method.invoke(targetInstance);
						xml.append(innerSpace+"<"+fieldName+">"+(null==value?"":value)+"</"+fieldName+">"+"\n");
					}
				}
				
			}
		}
		
		return xml.toString();
	}
	
	
}
