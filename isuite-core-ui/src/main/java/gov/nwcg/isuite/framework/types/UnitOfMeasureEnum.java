package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;

import java.util.ArrayList;

public enum UnitOfMeasureEnum {
   DAILY,
   EACH,
   HOURLY,
   MILEAGE;
   
   public static ArrayList<UnitOfMeasureVo> getUnitOfMeasureTypeList() {
		ArrayList<UnitOfMeasureVo> list = new ArrayList<UnitOfMeasureVo>();
		
		list.add(new UnitOfMeasureVo(UnitOfMeasureEnum.DAILY.name()));
		list.add(new UnitOfMeasureVo(UnitOfMeasureEnum.EACH.name()));
		list.add(new UnitOfMeasureVo(UnitOfMeasureEnum.HOURLY.name()));
		list.add(new UnitOfMeasureVo(UnitOfMeasureEnum.MILEAGE.name()));
		
		return list;
	}
}
