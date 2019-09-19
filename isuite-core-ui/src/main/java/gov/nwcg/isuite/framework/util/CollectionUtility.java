package gov.nwcg.isuite.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

public class CollectionUtility {
	
	public static Boolean hasValue(Collection<? extends Object> val){
		
		if( (null != val) && (val.size()>0))
			return true;
		
		return false;
	}

	public static Hashtable splitCollection(Collection<? extends Object> list, int splitCount) {
		Hashtable table = new Hashtable();
		int i=0;
		int tableCnt=1;
		int cnt=0;
		int max=list.size();
		Iterator iter = list.iterator();
		
		Collection<Object> objects = new ArrayList<Object>();
		
		while(i < max){
			objects.add(iter.next());

			if(cnt==splitCount){
				table.put(tableCnt, objects);
				objects = new ArrayList<Object>();
				cnt=0;
				
				if((i+1)<max)
					tableCnt++;
			}else{
				cnt++;
			}
			
			i++;
		}
	
		if(table.size()<tableCnt){
			table.put(tableCnt, objects);
		}
		
		return table;
	}
	
	public static String toCommaDelimitedString(Collection<String> collection){
		String rtn="";
		
		if(hasValue(collection)){
			
			for(String s : collection){
				if(rtn.length()>0)
					rtn=rtn+", "+s;
				else
					rtn=s;
			}
		}
		
		return rtn;
	}

	public static String toCommaDelimitedLongs(Collection<Long> collection){
		String rtn="";
		
		if(hasValue(collection)){
			
			for(Long v : collection){
				if(rtn.length()>0)
					rtn=rtn+", "+String.valueOf(v);
				else
					rtn=String.valueOf(v);
			}
		}
		
		return rtn;
	}
}
