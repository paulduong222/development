package gov.nwcg.isuite.framework.util;

import java.util.ArrayList;
import java.util.Collection;

public class IntegerUtility {

	public static Long convertToLong(Integer val){
		return val.longValue();
	}

	public static Boolean hasValue(Integer val){
		if(null != val && val > 0)
			return true;
		else
			return false;
	}
	
	public static Collection<Long> convertToLongs(Collection<Integer> integers) {
		Collection<Long> longs = new ArrayList<Long>();
		for(Integer id : integers) {
			longs.add(id.longValue());
		}
		return longs;
	}

}
