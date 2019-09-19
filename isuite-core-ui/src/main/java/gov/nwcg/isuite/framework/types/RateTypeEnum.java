package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.RateTypeVo;

import java.util.ArrayList;

public enum RateTypeEnum {
   PRIMARY,
   SPECIAL;
   
   public static ArrayList<RateTypeVo> getRateTypeList() {
		ArrayList<RateTypeVo> list = new ArrayList<RateTypeVo>();
		
		list.add(new RateTypeVo(RateTypeEnum.PRIMARY.name()));
		list.add(new RateTypeVo(RateTypeEnum.SPECIAL.name()));
		
		return list;
	}
}
