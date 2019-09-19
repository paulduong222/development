package gov.nwcg.isuite.framework.util;

import java.util.HashMap;
import java.util.Map;

public class RoleConverterUtil {
	private static Map<String,String> roleMap = buildMap();
	
	private static Map<String,String> buildMap(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("Cost", "ROLE_COST");
		map.put("Communications", "ROLE_COMMUNICATIONS");
		map.put("Injury/Illness", "ROLE_INJURY_ILLNESS");
		map.put("Resources", "ROLE_RESOURCES");
		map.put("Supply Clerk", "ROLE_SUPPLY_CLERK");
		map.put("Supply Supervisor", "ROLE_SUPPLY_SUPERVISOR");
		map.put("Time", "ROLE_TIME");
		map.put("Work Space", "ROLE_WORK_SPACE");
		map.put("Iap", "ROLE_IAP");
		map.put("Checkin/Demob", "ROLE_CHECK_IN_DEMOB");
		map.put("Manager", "ROLE_MANAGER");
		
		return map;
	}
	
	public static String convertRole(String name) {
		if(null != name){
			if(name.startsWith("ROLE_")){
				return name;
			}else{
				if(roleMap.containsKey(name))
					return roleMap.get(name);
			}
		}
		
		return name;
	}
}
