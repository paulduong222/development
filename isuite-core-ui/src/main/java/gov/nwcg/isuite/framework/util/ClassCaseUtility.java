package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

public class ClassCaseUtility {

	public static void toUpperCase(Persistable targetClass, String className) throws Exception{
		Object targetObject=Class.forName(className).newInstance();
		
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(targetClass);

		PropertyDescriptor descriptor = null;
		
		try{
			for(PropertyDescriptor d : descriptors){
				Method m = d.getWriteMethod();
				
				if(null != m){
					Class<?> param[]=m.getParameterTypes();
					
					//System.out.println(m.getName());
					// make sure setter method only takes one parameter
					if(param.length>0 && param.length<2){
						if(isStringType(param[0])){
							if(!isExcluded(m.getName())){
								Method getMethod=d.getReadMethod();
								if(null != getMethod){
									String value = (String)getMethod.invoke(targetClass, new Object[0]);
									if(null != value)
										m.invoke(targetClass, new Object[]{value.toUpperCase()});
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
	}

	private static Boolean isExcluded(String name) {
		String[] excludeList = {"PASSWORD"
								,"LOGINNAME"
								,"REMARKS"
								,"FILENAME"
								,"PARAMETERVALUE"
								,"SPECIALINSTRUCTION"
								,"SPECIALINSTRUCTIONS"
								,"WORKASSIGNMENT"
								,"STOREDFILEPATH"
								,"TNSPCOMMENTS"
								};
		Boolean rtn=false;
		
		for(int i=0;i<excludeList.length;i++){
			if(name.toUpperCase().indexOf(excludeList[i])>=0){
				rtn=true;
			}
		}
			
		return rtn;
	}

	private static Boolean isStringType(Class param) throws Exception {
		
		try{
			switch(DataType.getTypeIndex(param.getSimpleName()))
			{
				case DataType._TYPE_STRING:
					return true;
			}
		}catch(Exception e){
			throw e;
		}
		return false;
	}

	static class DataType{
		static final int _TYPE_STRING = 0;
		static final int _TYPE_PRIMITIVE_INT = 1;
		static final int _TYPE_DATE = 2;
		static final int _TYPE_LONG = 3;
		static final int _TYPE_BOOLEAN = 4;
		static final int _TYPE_COLLECTION = 5;
		static final int _TYPE_PRIMITIVE_BOOLEAN = 6;
		static final int _TYPE_CALENDAR=7;
		static final int _TYPE_BYTE_ARRAY=8;
		static final int _TYPE_INTEGER=9;
		static final int _TYPE_PRIMITIVE_LONG=10;
		static final int _TYPE_BIG_DECIMAL=11;
		static final int _TYPE_LIST=12;
		static final int _TYPE_BIG_INTEGER=13;
		
		static final int _TYPE_OTHER=-1;
		
		private static String[] commonTypeList = { 
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
				
		};
		
		/**
		 * Returns the index of the type specified.
		 * 
		 * @param type
		 * 			the name of the data type
		 * @return
		 * 			int index
		 */
		public static int getTypeIndex(String type){
			
			for(int i=0;i<commonTypeList.length;i++){
				if(type.equals(commonTypeList[i]))
					return i;
			}
			
			return _TYPE_OTHER;
		}
		
	}
	
	
}
