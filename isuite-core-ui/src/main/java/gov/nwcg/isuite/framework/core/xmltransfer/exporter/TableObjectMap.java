package gov.nwcg.isuite.framework.core.xmltransfer.exporter;

import java.util.HashMap;

public class TableObjectMap {
	private HashMap<String,HashMap> tableObjectMap= new HashMap<String,HashMap>();


	public void addTableObject(String tableName, String ti, Object object){
		// get hashmap by tablename
		if(tableObjectMap.containsKey(tableName)){
			HashMap<String,Object> tiMap = tableObjectMap.get(tableName);
			if(tiMap.containsKey(ti)){
				// do nothing
			}else{
				tiMap.put(ti,object);
				tableObjectMap.put(tableName, tiMap);
			}
		}else{
			HashMap<String,Object> tiMap = new HashMap<String,Object>();
			tiMap.put(ti,object);
			tableObjectMap.put(tableName, tiMap);
		}
	}
	
	public Object getObjectByTransferableIdentity(String tableName, String ti){
		Object rtn=null;
		
		if(tableObjectMap.containsKey(tableName)){
			HashMap<String,Object> keyMap = tableObjectMap.get(tableName);
			if(keyMap.containsKey(ti))
				rtn=keyMap.get(ti);
		}
		
		return rtn;
	}
	
}
