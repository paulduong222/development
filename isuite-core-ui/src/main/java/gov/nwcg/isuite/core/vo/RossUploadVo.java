package gov.nwcg.isuite.core.vo;

import java.util.HashMap;

public class RossUploadVo {
	private HashMap<String,UploadData> map = new HashMap<String,UploadData>();
	
	public RossUploadVo(){
		
	}

	public void addNewUploadData(String key, String status){
		if(!map.containsKey(key)){
			UploadData ud = new UploadData(key,status);
			map.put(key, ud);
		}
	}

	public void updateUploadData(String key, String status){
		if(map.containsKey(key)){
			UploadData ud = map.get(key);
			ud.setStatus(status);
			map.put(key, ud);
		}
	}
	
	public Boolean isComplete(String key){
		boolean val=false;
		
		if(map.containsKey(key)){
			UploadData ud = map.get(key);
			if(ud.getStatus().equals("COMPLETE"))
				val=true;
		}
		
		return val;
	}
	
	public HashMap<String,UploadData> getMap() {
		return map;
	}

	public void setMap(HashMap<String,UploadData> map) {
		this.map = map;
	}
	
	protected class UploadData{
		private String uploadKey="";
		private String status="";
		
		UploadData(String key, String status){
			this.uploadKey=key;
			this.status=status;
		}
		
		public String getUploadKey(){
			return uploadKey;
		}
		
		public String getStatus(){
			return status;
		}
		
		public void setUploadKey(String key){
			this.uploadKey=key;
		}
		
		public void setStatus(String st){
			this.status = st;
		}
	}
}
