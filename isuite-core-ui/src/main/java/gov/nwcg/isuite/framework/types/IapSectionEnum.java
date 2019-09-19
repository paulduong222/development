package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.SectionVo;

import java.util.ArrayList;

public enum IapSectionEnum {
	INCIDENT_COMMANDER("Incident Commander and Staff","INCIDENT_COMMANDER"),
	AGENCY_REP("Agency Representative","AGENCY_SECTION"),
	PLANNING_SECTION("Planning Section","PLANNING_SECTION"),
	LOGISTICS_SECTION("Logistics Section","LOGISTICS_SECTION"),
	OPERATIONS_SECTION("Operations Section","OPERATIONS_SECTION"),
	AIR_OPERATIONS("Air Operations Branch","AIR_OPERATIONS"),
	FINANCE_SECTION("Finance/Admin Section","FINANCE_SECTION"),
	OPERATIONS_PERSONNEL("Operations Personnel","OPERATIONS_PERSONNEL"),
	BRANCH_SECTION("Branch Section","BRANCH_SECTION");

	private String description="";
	public String formSection="";
	
	IapSectionEnum(String description,String frmSection) {
		this.description = description;
		this.formSection=frmSection;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public static ArrayList<SectionVo> getSectionList() {
		ArrayList<SectionVo> list = new ArrayList<SectionVo>();
		
		list.add(new SectionVo(1L, IapSectionEnum.INCIDENT_COMMANDER.name(),IapSectionEnum.INCIDENT_COMMANDER.getDescription()));
		list.add(new SectionVo(2L, IapSectionEnum.AGENCY_REP.name(),IapSectionEnum.AGENCY_REP.getDescription()));
		list.add(new SectionVo(3L, IapSectionEnum.PLANNING_SECTION.name(),IapSectionEnum.PLANNING_SECTION.getDescription()));
		list.add(new SectionVo(4L, IapSectionEnum.LOGISTICS_SECTION.name(),IapSectionEnum.LOGISTICS_SECTION.getDescription()));
		list.add(new SectionVo(5L, IapSectionEnum.OPERATIONS_SECTION.name(),IapSectionEnum.OPERATIONS_SECTION.getDescription()));
		list.add(new SectionVo(6L, IapSectionEnum.BRANCH_SECTION.name(),IapSectionEnum.BRANCH_SECTION.getDescription()));
		list.add(new SectionVo(7L, IapSectionEnum.AIR_OPERATIONS.name(),IapSectionEnum.AIR_OPERATIONS.getDescription()));
		list.add(new SectionVo(8L, IapSectionEnum.FINANCE_SECTION.name(),IapSectionEnum.FINANCE_SECTION.getDescription()));

		//list.add(new SectionVo(8L, IapSectionEnum.OPERATIONS_PERSONNEL.name(),IapSectionEnum.OPERATIONS_PERSONNEL.getDescription()));
		
		return list;
	}
	
	public static SectionVo getSectionVoByCode(String code) {
		SectionVo vo = null;
		
		for(SectionVo v : getSectionList()) {
			if(v.getCode().equals(code))
				vo=v;
		}
		
		return vo;
	}

	public static IapSectionEnum toEnumValue(String code) {

		for(IapSectionEnum en : IapSectionEnum.values()){
			if(en.name().equals(code))
				return en;
		}
		return null;
	}
}
