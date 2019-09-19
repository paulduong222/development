package gov.nwcg.isuite.core.spring;

import java.util.HashMap;

import org.apache.commons.dbcp.BasicDataSource;

public class DatasourceMap {
	private HashMap<String,BasicDataSource> cacheMap = new HashMap<String,BasicDataSource>();
	
	public DatasourceMap(){
		
	}
	
	public Boolean hasDatasource(String name){
		if(cacheMap.containsKey(name))
			return true;
		else
			return false;
	}
	
	public void addDatasource(String name,BasicDataSource ds){
		cacheMap.put(name,ds);
	}
	
	public BasicDataSource getDatasource(String name){
		if(cacheMap.containsKey(name))
			return cacheMap.get(name);
		else
			return null;
	}
	
}
