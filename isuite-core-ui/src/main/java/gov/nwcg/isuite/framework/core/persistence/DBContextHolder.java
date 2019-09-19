package gov.nwcg.isuite.framework.core.persistence;

import org.apache.commons.dbcp.BasicDataSource;

public class DBContextHolder {
	//private static final ThreadLocal<DynamicDataSourceType> contextHolder =
	//	new ThreadLocal<DynamicDataSourceType>();
	private static ThreadLocal<DynamicDataSourceType> contextHolder =
		new ThreadLocal<DynamicDataSourceType>();

	public static void setDynamicDataSource(DynamicDataSourceType type){
		contextHolder.set(type);
	}

	public static DynamicDataSourceType getDynamicDataSourceType(){
		return (DynamicDataSourceType)contextHolder.get();
	}
	
	public static void clearBasicDataSource(){
		contextHolder.remove();
	}

}
