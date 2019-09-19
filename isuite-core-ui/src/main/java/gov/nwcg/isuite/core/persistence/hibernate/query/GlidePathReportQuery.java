package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nwcg.isuite.core.reports.GlidePathReportTabEnum;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.util.DateUtil;

public class GlidePathReportQuery {

	private static final String PARENTHESIS_OPEN = "(";
	private static final String PARENTHESIS_CLOSE = ")";
	private static final String SPACER = " ";
	private static final String AND = " AND ";
	private static final String OR = " OR ";
	private static final String UNION_ALL = " UNION ALL ";
	private static final String SQL_MARKER_STRING_FOR_SPECIFIC_TAB = "#{TABCODE}";
	private static final String ORACLE_PREFIX_FOR_ORDERBY_BEFORE_UNION = "SELECT * FROM "; // Needed in Oracle only to sort within a set(=worksheet) before union is applied. 
	
	private static final String SELECT_PREFIX = "SELECT CAST('" + SQL_MARKER_STRING_FOR_SPECIFIC_TAB + "' AS VARCHAR(50)) "
		+ " AS subSectionName, request_number AS requestNumber, resource_name AS resourceName, kind_code AS itemCode, first_work_date AS firstWorkingDate, length_at_assignment AS lengthOfAssignment, demob_date AS demobDate" 
												+ " FROM glidepathinner WHERE ";

	// ORDER BY CLAUSE
	private static final String ORDER_BY_ITEM_DEMOB_REQUEST = " ORDER BY kind_code, demob_date, sortrequestnumber(request_number) ";
	private static final String ORDER_BY_ITEM_REQUEST_DEMOB = " ORDER BY kind_code, sortrequestnumber(request_number), demob_date ";
	private static final String ORDER_BY_ITEM_DEMOB = " ORDER BY kind_code, demob_date ";
	private static final String ORDER_BY_DEMOB_REQUEST = " ORDER BY demob_date, sortrequestnumber(request_number) ";
	private static final String ORDER_BY_REQUEST_DEMOB = " ORDER BY sortrequestnumber(request_number), demob_date ";
	private static final String ORDER_BY_DEMOB_RESNAME = " ORDER BY demob_date, resource_name ";
		
	private static final String COMMAND = "department_code = 'C' AND is_person = 'Y' AND is_level_1 = 'Y'";
	private static final String PLANS = "department_code = 'P' AND is_person = 'Y' AND is_level_1 = 'Y'";
	private static final String FINANCE = "department_code = 'F' AND is_person = 'Y' AND is_level_1 = 'Y'";
	private static final String LOGISTICS_PERSONNEL = "department_code = 'L' AND sub_group_category_code = 'OS' AND is_person = 'Y' AND is_level_1 = 'Y'"; 
	private static final String LOGISTICS_EQUIPMENT = "department_code = 'L' AND group_category_code = 'M' AND is_person = 'N' AND is_level_1 = 'Y'";
	
	private static final String OPS_LINE_PERSONNEL = "department_code = 'O' AND is_line_overhead = 'Y' AND is_person = 'Y' AND is_level_1 = 'Y'";
	private static final String OPS_OTHER_PERSONNEL = "department_code = 'O' AND is_line_overhead = 'N' AND is_person = 'Y' AND is_level_1 = 'Y'";
	
	
	private static final String OPS_DOZERS_STC = "group_category_code = 'E' " +	
												"AND (sub_group_category_code = 'D' OR sub_group_category_code = 'T') " +
												"AND is_person = 'N' ";
	private static final String OPS_DOZERS= OPS_DOZERS_STC + " AND is_level_1 = 'Y' ";
	
	
	
	private static final String OPS_ENGINES = "group_category_code = 'E' " +
												"AND sub_group_category_code = 'E' " +
												"AND is_strike_team = 'N' AND is_person = 'N' AND is_level_1 = 'Y' ";
	
	
	
	private static final String OPS_ENGINE_STRIKE_TEAMS = "group_category_code = 'E' " +
															"AND sub_group_category_code = 'E' " +
															"AND is_person = 'N' " + 
															"AND is_strike_team = 'Y' AND is_level_1 = 'Y' "; 
	private static final String OPS_ENGINE_STRIKE_TEAMS_STC = "(" + OPS_ENGINE_STRIKE_TEAMS + ") OR (" +
																"group_category_code = 'E' " +
																"AND sub_group_category_code = 'E' " +
																"AND is_person = 'N' " +
																"AND is_component_of_strike_team = 'Y') ";
	
	
	
	private static final String OPS_EQUIPMENT_OTHER = "group_category_code = 'E' " +
												"AND sub_group_category_code = 'EQ' " +
												"AND is_person = 'N' AND is_level_1 = 'Y' "; 
	
	
	
	private static final String OPS_HAND_CREW_TYPE_1_STC = "group_category_code = 'C' " +
												"AND sub_group_category_code = 'C1' " +
												"AND is_person = 'N' "; 
	private static final String OPS_HAND_CREW_TYPE_1 = OPS_HAND_CREW_TYPE_1_STC + " AND is_level_1 = 'Y' ";
	
	
	private static final String OPS_HAND_CREW_TYPE_2_STC = "group_category_code = 'C' " +
												"AND (" +
													"sub_group_category_code = 'C2' " +
													"OR sub_group_category_code = 'C3' " +
													"OR sub_group_category_code = 'C4' " +
													"OR sub_group_category_code = 'MC') " +
												"AND is_person = 'N' "; 
	private static final String OPS_HAND_CREW_TYPE_2 = OPS_HAND_CREW_TYPE_2_STC +  " AND is_level_1 = 'Y' ";
	
	
	private static final String OPS_WATER_TENDERS = "group_category_code = 'E' " +
												"AND sub_group_category_code = 'W' " +
												"AND is_person = 'N' AND is_level_1 = 'Y' "; 
	
	
	private static final Map<GlidePathReportTabEnum, String> tabSpecificClauseMap = new HashMap<GlidePathReportTabEnum, String>();
	private static final Map<GlidePathReportTabEnum, String> tabSpecificClauseForComponentsMap = new HashMap<GlidePathReportTabEnum, String>();
	
	static {
		tabSpecificClauseMap.put(GlidePathReportTabEnum.COMMAND, COMMAND);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.LINE_PERSONNEL, OPS_LINE_PERSONNEL);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.OTHER_PERSONNEL, OPS_OTHER_PERSONNEL);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.DOZERS, OPS_DOZERS);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.ENGINES, OPS_ENGINES);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.ENGINE_STRIKE_TEAMS, OPS_ENGINE_STRIKE_TEAMS);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.EQUIPMENT_OTHER, OPS_EQUIPMENT_OTHER);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.HAND_CREW_TYPE_1, OPS_HAND_CREW_TYPE_1);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.HAND_CREW_TYPE_2, OPS_HAND_CREW_TYPE_2);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.WATER_TENDERS, OPS_WATER_TENDERS);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.PLANS, PLANS);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.LOGISTICS_PERSONNEL, LOGISTICS_PERSONNEL);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.LOGISTICS_EQUIPMENT, LOGISTICS_EQUIPMENT);
		tabSpecificClauseMap.put(GlidePathReportTabEnum.FINANCE, FINANCE);
		
		// To be used when Checkbox for "Include Strike Team Components" is selected
		tabSpecificClauseForComponentsMap.put(GlidePathReportTabEnum.DOZERS, OPS_DOZERS_STC);
		tabSpecificClauseForComponentsMap.put(GlidePathReportTabEnum.ENGINE_STRIKE_TEAMS, OPS_ENGINE_STRIKE_TEAMS_STC);
		tabSpecificClauseForComponentsMap.put(GlidePathReportTabEnum.HAND_CREW_TYPE_1, OPS_HAND_CREW_TYPE_1_STC);
		tabSpecificClauseForComponentsMap.put(GlidePathReportTabEnum.HAND_CREW_TYPE_2, OPS_HAND_CREW_TYPE_2_STC);
	}
	
	/**
	 * Public static method use to generate the query needed to generate the GlidePath report. 
	 * Different query types are returned for Oracle and Postgre. Generates different query parts for each tab 
	 * that are then pre-sorted, and then combined using UNION ALL. 
	 * @param filter
	 * @param tabsToGenerate
	 * @param isOracle
	 * @return Complete SQL Query needed to generate the glidepath report.
	 */
	public static String generate(GlidePathReportFilter filter, List<GlidePathReportTabEnum> tabsToGenerate, boolean isOracle) {
		
		if(tabsToGenerate == null || tabsToGenerate.size()==0) return null;
		
		//String incidentClause = getIncidentClause(filter); // only generate once for all the tabs.
		String initialWithClause = getInitialWithClause(filter, isOracle);
		String sql = null;
		
		for(GlidePathReportTabEnum tab: tabsToGenerate){
			String tabSpecificSql = generateForSpecificTab(filter, tab, isOracle);
			if(sql==null){
				sql = tabSpecificSql;
			} else {
				sql = sql + UNION_ALL + tabSpecificSql;
			}
		}
		
		// Prepend the WITH clause to selects
		sql = initialWithClause + sql;
		
//		System.out.println("\n\n\n");
//		System.out.println(sql);
//		System.out.println("\n\n\n");
		
		return sql;
	}
	
	private static String generateForSpecificTab(GlidePathReportFilter filter, GlidePathReportTabEnum tabEnum, boolean isOracle) {
		String sql = null;
		if(isOracle) {
			sql = ORACLE_PREFIX_FOR_ORDERBY_BEFORE_UNION + PARENTHESIS_OPEN + SELECT_PREFIX + SPACER;// + incidentClause + SPACER;
		} else {
			sql = PARENTHESIS_OPEN + SELECT_PREFIX + SPACER;// + incidentClause + SPACER;;
		}
			
		sql = sql.replace(SQL_MARKER_STRING_FOR_SPECIFIC_TAB, tabEnum.getContentTitle());
		
		if(filter.getOptionIncludeSTComponents()) { // Strike Team Components is selected
			if(tabEnum == GlidePathReportTabEnum.DOZERS || tabEnum == GlidePathReportTabEnum.ENGINE_STRIKE_TEAMS
						|| tabEnum == GlidePathReportTabEnum.HAND_CREW_TYPE_1 || tabEnum == GlidePathReportTabEnum.HAND_CREW_TYPE_2) {
				// Strike Team Components is selected AND the tab is impacted by components
				
				// Not using normal clause map. Using clause map for strike team components.
				sql += tabSpecificClauseForComponentsMap.get(tabEnum);
				
				if(filter.getSortByDemobDateRequestNumber2()){
					sql += ORDER_BY_DEMOB_REQUEST;
				} else if(filter.getSortByDemobDateResourceName2()){
					sql += ORDER_BY_DEMOB_RESNAME;
				} else {
					sql += ORDER_BY_REQUEST_DEMOB;
				}
			} else {
				// Strike Team Components is selected BUT the tab is NOT impacted by components
				sql += tabSpecificClauseMap.get(tabEnum);
				
				//if(filter.getSortByItemCodeDemobDate()){
				//	sql += ORDER_BY_ITEM_DEMOB;
				//} else if(filter.getSortByItemCodeRequestNumber()){
				//	sql += ORDER_BY_ITEM_REQUEST_DEMOB; 
				//}
				//if(filter.getSortByDemobDateRequestNumber()){
					//sql += ORDER_BY_ITEM_DEMOB_REQUEST;
				if(filter.getSortByDemobDateRequestNumber2()){
					sql += ORDER_BY_DEMOB_REQUEST;
				} else if(filter.getSortByDemobDateResourceName2()){
					sql += ORDER_BY_DEMOB_RESNAME;
				} else {
					sql += ORDER_BY_REQUEST_DEMOB;
				}
			}
		} else { // Strike Team Components is NOT selected
			sql += tabSpecificClauseMap.get(tabEnum);

			if(filter.getSortByItemCodeDemobDate()) {
				sql += ORDER_BY_ITEM_DEMOB_REQUEST;
			} else if(filter.getSortByDemobDateRequestNumber()){
				sql += ORDER_BY_DEMOB_REQUEST;
			} else if(filter.getSortByDemobDateResourceName()){
				sql += ORDER_BY_DEMOB_RESNAME;
			} else 
				sql += ORDER_BY_ITEM_REQUEST_DEMOB;
		}
		
		sql += PARENTHESIS_CLOSE;
		return sql;
	}
	
	private static String getIncidentClause(GlidePathReportFilter filter) {
		String sql = null;
		if(filter.getIncidentGroupId() != null && filter.getIncidentGroupId() > 0) {
			sql = "ig.id = " + filter.getIncidentGroupId();
		} else {
			sql = "i.id = " + filter.getIncidentId();
		}
		return sql;
	}
	
	private static String getKindCodeClause(GlidePathReportFilter filter) {
		String sql = null;
		if(filter.getIncidentGroupId() != null && filter.getIncidentGroupId() > 0) {
			sql = " (kind_code_is_standard = 'Y' OR kind_code_incident_group_id = " +  filter.getIncidentGroupId() +
					" ) AND (sub_group_category_is_standard = 'Y' OR sub_group_category_incgrp_id = " +  filter.getIncidentGroupId() +
					" )";
		} else {
			sql = " (kind_code_is_standard = 'Y' OR kind_code_incident_id = " +  filter.getIncidentId() +
					" ) AND (sub_group_category_is_standard = 'Y' OR sub_group_category_inc_id = " +  filter.getIncidentId() +
					" )";
		}
		return sql;
	}
	
	private static String getDateRangeClause(GlidePathReportFilter filter) {
		//Add Date filter to only select resources whose start and demob date is such that they are present in the user defined window. 
		/*
		 * Case A: FWD < UserSelectedDate AND DMD >= UserSelectedDate
		 * OR
		 * Case B: FWD >= UserSelectedDate AND FWD <= (UserSelectedDate + UserSelectedNumberOfDays)
		 */
		
		Date userSelectedReportStartDate = DateTransferVo.getDate(filter.getStartDateVo());
		String userSelectedReportStartDateString = DateUtil.toDateString(userSelectedReportStartDate, "yyyy-MM-dd");
		
		int numberOfDays = filter.getNumberOfDays();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userSelectedReportStartDate);
	    calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
	    Date userDefinedReportEndDate = calendar.getTime();
	    String userDefinedReportEndDateString = DateUtil.toDateString(userDefinedReportEndDate, "yyyy-MM-dd");
	    
	    String sql = " ( " +
					"( " +
						" first_work_date < to_date('" + userSelectedReportStartDateString + "', 'yyyy-MM-dd')" +
						AND +
						" demob_date >= to_date('" + userSelectedReportStartDateString + "', 'yyyy-MM-dd')" +
					" )" +
					OR +
					"( " +
						" first_work_date >= to_date('" + userSelectedReportStartDateString + "', 'yyyy-MM-dd')" +
						AND +
						" first_work_date <= to_date('" + userDefinedReportEndDateString + "', 'yyyy-MM-dd')" +
					" )" +
				" )";
		
		return sql;
	}
	
	private static String getInitialWithClause(GlidePathReportFilter filter, boolean isOracle) {
		StringBuffer innerStringBuffer = isOracle? GlidePathReportQueryInner.INNER_SQL_ORACLE : GlidePathReportQueryInner.INNER_SQL_PG;
		String sqlInner = innerStringBuffer.toString();
		
		sqlInner = sqlInner.replace(GlidePathReportQueryInner.SQL_MARKER_FOR_INCIDENT_SPECIFIC_CLAUSE, getIncidentClause(filter));
		sqlInner = sqlInner.replace(GlidePathReportQueryInner.SQL_MARKER_FOR_KIND_CODE_CLAUSE, getKindCodeClause(filter));
		sqlInner = sqlInner.replace(GlidePathReportQueryInner.SQL_MARKER_FOR_DATE_RANGE_CLAUSE, getDateRangeClause(filter));
		
		String withClause = "WITH glidepathinner AS ( \n" + sqlInner + " \n) ";
		
		return withClause;
	}
}
