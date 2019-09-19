package gov.nwcg.isuite.framework.core.xmltransfer.importer;

import java.util.HashMap;

/*
 * This table is a just a convenience to store tables that have already been updated. 
 */
public class TablesUpdatedMap  {
	private HashMap<String,HashMap> tableUpdatedMap= new HashMap<String,HashMap>();
	
	public void addTableId(String tableName, Long id){
		// get hashmap by tablename
		if(tableUpdatedMap.containsKey(tableName)){
			HashMap<Long,String> tbMap = tableUpdatedMap.get(tableName);
			if(tbMap.containsKey(id)){
				// do nothing
			}else{
				tbMap.put(id,"updated");
				tableUpdatedMap.put(tableName, tbMap);
			}
		}else{
			HashMap<Long,String> tbMap = new HashMap<Long,String>();
			tbMap.put(id,"updated");
			tableUpdatedMap.put(tableName, tbMap);
		}
	}
	
	public Boolean hasTableBeenUpdated(String tableName, Long id){
		Boolean rtn=false;
		
		if(tableUpdatedMap.containsKey(tableName)){
			HashMap<Long,String> tbMap = tableUpdatedMap.get(tableName);
			if(tbMap.containsKey(id))
				rtn=true;
		}
		
		return rtn;
	}
}
