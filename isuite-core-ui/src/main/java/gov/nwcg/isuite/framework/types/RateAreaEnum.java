package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.RateAreaVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;

import java.util.ArrayList;
import java.util.Collection;

public enum RateAreaEnum {
   ALASKA,
   CONUS,
   HAWAII;
   
   public static ArrayList<RateAreaVo> getRateAreaList(Collection<RateClassRateVo> rcrVos) {
		ArrayList<RateAreaVo> list = new ArrayList<RateAreaVo>();

		RateAreaVo alaska = new RateAreaVo(RateAreaEnum.ALASKA.name());
		alaska.setRateClassRateVos(RateClassRateVo.getByArea("ALASKA",rcrVos));
		list.add(alaska);
		
		RateAreaVo conus = new RateAreaVo(RateAreaEnum.CONUS.name());
		alaska.setRateClassRateVos(RateClassRateVo.getByArea("CONUS",rcrVos));
		list.add(conus);
		
		RateAreaVo hawaii = new RateAreaVo(RateAreaEnum.HAWAII.name());
		alaska.setRateClassRateVos(RateClassRateVo.getByArea("HAWAII",rcrVos));
		list.add(hawaii);
		
		return list;
	}
}
