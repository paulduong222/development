package gov.nwcg.isuite.core.spring;

import gov.nwcg.isuite.core.vo.GlobalCacheVo;

import java.util.HashMap;

public class GlobalCacheMap {
	private HashMap<String,GlobalCacheVo> cacheMap = new HashMap<String,GlobalCacheVo>();
	
	public GlobalCacheMap(){
		
	}
	
	public Boolean hasVo(String name){
		if(cacheMap.containsKey(name))
			return true;
		else
			return false;
	}
	
	public void addVo(String name,GlobalCacheVo vo){
		cacheMap.put(name,vo);
	}
	
	public GlobalCacheVo getVo(String name){
		if(cacheMap.containsKey(name))
			return cacheMap.get(name);
		else
			return null;
	}
	
}
