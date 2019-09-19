package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.SystemTypeVo;

import java.util.ArrayList;

public enum SystemTypeEnum {
	KING,
	NIFC;
	
	public static ArrayList<SystemTypeVo> getSystemTypeList() {
		ArrayList<SystemTypeVo> list = new ArrayList<SystemTypeVo>();
		
		list.add(new SystemTypeVo(1L, SystemTypeEnum.KING.name()));
		list.add(new SystemTypeVo(2L, SystemTypeEnum.NIFC.name()));
		
		return list;
	}
	
	public static SystemTypeVo getSystemTypeVoByCode(String code) {
		SystemTypeVo vo = null;
		
		for(SystemTypeVo v : getSystemTypeList()) {
			if(v.getCode().equals(code))
				vo=v;
		}
		
		return vo;
	}

}
