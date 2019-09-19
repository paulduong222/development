package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.Collection;

public class VoValidator {

	public static Boolean isValidAbstractVo(AbstractVo vo) throws Exception {
		if( (null != vo) && (null != vo.getId()) && (vo.getId() > 0L) )
			return true;
		else 
			return false;
	}
	
	public static Boolean hasOneFilter(Collection<FilterCriteria> criteria) throws Exception {
		if(null == criteria)
			return false;
		
		for(FilterCriteria fc : criteria){
			if(null != fc)
				return true;
		}
		
		return false;
	}
	
}
