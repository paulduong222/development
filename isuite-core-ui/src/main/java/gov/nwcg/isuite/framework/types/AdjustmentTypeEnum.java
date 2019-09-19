package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.AdjustmentTypeVo;

import java.util.ArrayList;

public enum AdjustmentTypeEnum {
   ADDITION,
   DEDUCTION;
   
   public static ArrayList<AdjustmentTypeVo> getAdjustmentTypeList() {
		ArrayList<AdjustmentTypeVo> list = new ArrayList<AdjustmentTypeVo>();
		
		list.add(new AdjustmentTypeVo(AdjustmentTypeEnum.ADDITION.name()));
		list.add(new AdjustmentTypeVo(AdjustmentTypeEnum.DEDUCTION.name()));
		
		return list;
	}
}
