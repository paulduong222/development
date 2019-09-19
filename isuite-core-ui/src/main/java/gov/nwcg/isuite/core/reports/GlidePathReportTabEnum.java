package gov.nwcg.isuite.core.reports;

import java.util.ArrayList;
import java.util.List;

public enum GlidePathReportTabEnum {
	// TAB TITLE, CONTENT TITLE, ORDER, CLASSIFICATION
	
	// SUMMARY TAB
	SUMMARY("SUMMARY", "SUMMARY", 0, "SUMMARY")
	
	// CLASSIFICATION: COMMAND
	,COMMAND("CMD" , "COMMAND", 1, "COMMAND")

	// CLASSIFICATION: OPERATIONS
	,LINE_PERSONNEL("OPS LINE" , "OPERATIONS LINE PERSONNEL", 2, "OPERATIONS")
	,OTHER_PERSONNEL("OPS OTH PERS" , "OPERATIONS OTHER PERSONNEL", 3, "OPERATIONS")
	,DOZERS("DOZ TPLS" , "DOZERS AND TRACTOR PLOWS", 4, "OPERATIONS")
	,ENGINES("ENG SKID" , "ENGINES AND SKIDGINES", 5, "OPERATIONS")
	,ENGINE_STRIKE_TEAMS("ENG ST TMS" , "ENGINE STRIKE TEAMS", 6, "OPERATIONS")
	,EQUIPMENT_OTHER("OTH OPS EQ" , "OTHER OPERATIONS EQUIPMENT", 7, "OPERATIONS")
	,HAND_CREW_TYPE_1("HC 1" , "HANDCREW TYPE 1", 8, "OPERATIONS")
	,HAND_CREW_TYPE_2("HC 2" , "HANDCREW TYPE 2", 9, "OPERATIONS")
	,WATER_TENDERS("WTR TNDRS" , "WATER TENDERS", 10, "OPERATIONS")

	// CLASSIFICATION: PLANS
	,PLANS("PLANS" , "PLANS", 11, "PLANS")

	// CLASSIFICATION: LOGISTICS
	,LOGISTICS_PERSONNEL("LOG PERS" , "LOGISTICS PERSONNEL", 12, "LOGISTICS")
	,LOGISTICS_EQUIPMENT("LOG EQ OTH" , "LOGISTICS EQUIPMENT/OTHER", 13, "LOGISTICS")

	// CLASSIFICATION: FINANCE
	,FINANCE("FINANCE" , "FINANCE", 14, "FINANCE")
	;
	
	private String tabTitle;		// Will be used on excel tab bar.
	private String contentTitle;	// Will be used in main content area in a worksheet.
	private int order;
	private String classification;
	
	GlidePathReportTabEnum(String tabTitle, String contentTitle, int order, String classification) {
		this.tabTitle = tabTitle;
		this.contentTitle = contentTitle;
		this.order = order;
		this.classification = classification;
	}
	
	/**
	 * Returns all tabs except for the summary tab.
	 * @return
	 */
	public static List<GlidePathReportTabEnum> getAllContentTabs() {
		List<GlidePathReportTabEnum> allTabs = new ArrayList<GlidePathReportTabEnum>();
		
		allTabs.add(GlidePathReportTabEnum.COMMAND);
		allTabs.add(GlidePathReportTabEnum.LINE_PERSONNEL);
		allTabs.add(GlidePathReportTabEnum.OTHER_PERSONNEL);
		allTabs.add(GlidePathReportTabEnum.DOZERS);
		allTabs.add(GlidePathReportTabEnum.ENGINES);
		allTabs.add(GlidePathReportTabEnum.ENGINE_STRIKE_TEAMS);
		allTabs.add(GlidePathReportTabEnum.EQUIPMENT_OTHER);
		allTabs.add(GlidePathReportTabEnum.HAND_CREW_TYPE_1);
		allTabs.add(GlidePathReportTabEnum.HAND_CREW_TYPE_2);
		allTabs.add(GlidePathReportTabEnum.WATER_TENDERS);
		allTabs.add(GlidePathReportTabEnum.PLANS);
		allTabs.add(GlidePathReportTabEnum.LOGISTICS_PERSONNEL);
		allTabs.add(GlidePathReportTabEnum.LOGISTICS_EQUIPMENT);
		allTabs.add(GlidePathReportTabEnum.FINANCE);
		
		return allTabs;
	}
	
	public static GlidePathReportTabEnum getFromCode(String code) {
		if(code==null) return null;
		
		if(code.equals("COMMAND")) return GlidePathReportTabEnum.COMMAND;
		if(code.equals("LINE_PERSONNEL")) return GlidePathReportTabEnum.LINE_PERSONNEL;
		if(code.equals("OTHER_PERSONNEL")) return GlidePathReportTabEnum.OTHER_PERSONNEL;
		if(code.equals("DOZERS")) return GlidePathReportTabEnum.DOZERS;
		if(code.equals("ENGINES")) return GlidePathReportTabEnum.ENGINES;
		if(code.equals("ENGINE_STRIKE_TEAMS")) return GlidePathReportTabEnum.ENGINE_STRIKE_TEAMS;
		if(code.equals("EQUIPMENT_OTHER")) return GlidePathReportTabEnum.EQUIPMENT_OTHER;
		if(code.equals("HAND_CREW_TYPE_1")) return GlidePathReportTabEnum.HAND_CREW_TYPE_1;
		if(code.equals("HAND_CREW_TYPE_2")) return GlidePathReportTabEnum.HAND_CREW_TYPE_2;
		if(code.equals("WATER_TENDERS")) return GlidePathReportTabEnum.WATER_TENDERS;
		if(code.equals("PLANS")) return GlidePathReportTabEnum.PLANS;
		if(code.equals("LOGISTICS_PERSONNEL")) return GlidePathReportTabEnum.LOGISTICS_PERSONNEL;
		if(code.equals("LOGISTICS_EQUIPMENT")) return GlidePathReportTabEnum.LOGISTICS_EQUIPMENT;
		if(code.equals("FINANCE")) return GlidePathReportTabEnum.FINANCE;
		
		return null;
	}

	/**
	 * @return the tabTitle
	 */
	public String getTabTitle() {
		return tabTitle;
	}

	/**
	 * @return the contentTitle
	 */
	public String getContentTitle() {
		return contentTitle;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}
}
