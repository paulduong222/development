package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.EmploymentTypeVo;

import java.util.ArrayList;

public enum EmploymentTypeEnum {
	AD,
	FED,
	OTHER,
	CONTRACTOR;
	
	public static ArrayList<EmploymentTypeVo> getEmploymentTypeList() {
		ArrayList<EmploymentTypeVo> list = new ArrayList<EmploymentTypeVo>();
		
		list.add(new EmploymentTypeVo(EmploymentTypeEnum.AD.name()));
		list.add(new EmploymentTypeVo(EmploymentTypeEnum.FED.name()));
		list.add(new EmploymentTypeVo(EmploymentTypeEnum.OTHER.name()));
		
		// exclude contractor from default list
		
		return list;
	}
}
