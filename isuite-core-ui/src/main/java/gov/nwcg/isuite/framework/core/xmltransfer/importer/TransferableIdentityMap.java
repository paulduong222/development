package gov.nwcg.isuite.framework.core.xmltransfer.importer;

import java.util.HashMap;

/*
 * This table is a just a convenience to store transferable identities/primary key (id) 
 * relationships for each table as we proces them.  Intead of repeated queries on the
 * same record, the system will check this mapping first to see if we have already
 * resolved the ti/id values.
 */
public class TransferableIdentityMap  {
	private HashMap<String,HashMap> tableTiMap= new HashMap<String,HashMap>();
	
	public void addTransferableIdentityId(String tableName, String ti, Long id){
		// get hashmap by tablename
		if(tableTiMap.containsKey(tableName)){
			HashMap<String,Long> tiMap = tableTiMap.get(tableName);
			if(tiMap.containsKey(ti)){
				// do nothing
			}else{
				tiMap.put(ti, id);
				tableTiMap.put(tableName, tiMap);
			}
		}else{
			HashMap<String,Long> tiMap = new HashMap<String,Long>();
			tiMap.put(ti, id);
			tableTiMap.put(tableName, tiMap);
		}
	}
	
	public Long getIdByTransferableIdentity(String tableName, String ti){
		Long rtn=0L;
		
		if(tableTiMap.containsKey(tableName)){
			HashMap<String,Long> tiMap = tableTiMap.get(tableName);
			if(tiMap.containsKey(ti))
				rtn=tiMap.get(ti);
		}
		
		return rtn;
	}
}
