package gov.nwcg.isuite.framework.util;

import java.util.ArrayList;
import java.util.Collection;

public class LongUtility {

	public static Boolean hasValue(Long val){
		if(null != val && val > 0)
			return true;
		else
			return false;
	}

	public static Collection<Long> convertToLongs(Collection<Object> values) {
		Collection<Long> longs = new ArrayList<Long>();
		for(Object o : values) {
			try{
				longs.add(TypeConverter.convertToLong(o));
			}catch(Exception e){}
		}
		return longs;
	}

	public static Collection<Object> convertToObjects(Collection<Long> values) {
		Collection<Object> longs = new ArrayList<Object>();
		for(Long o : values) {
			try{
				longs.add((Object)o);
			}catch(Exception e){}
		}
		return longs;
	}

	public static Boolean containsLong(Collection<Long> items, Long id) {
		for(Long item : items){
			if(id.compareTo(item)==0)
				return true;
		}
		
		return false;
	}
}
