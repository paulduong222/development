package gov.nwcg.isuite.framework.util;

import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

public class SQLUtility {
	
	public static String toSqlStringList(Collection<? extends Object> list, Class targetClass, String field) throws Exception{
		StringBuffer buffer = new StringBuffer();
		
		for(Object obj : list){
			String val = (String)PropertyUtils.getProperty(obj, field);
			if(buffer.length()==0)
				buffer.append(wrapQuote(val));
			else
				buffer.append(","+wrapQuote(val));
		}
		
		return buffer.toString();
	}
	
	public static String wrapQuote(String val){
		return "'" + val + "'";
	}
	
}
