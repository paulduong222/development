package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;

import gov.nwcg.isuite.core.reports.data.DailyCostComparisonICReportData;
import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;

public class AnalysisReportQuery {
	public static String getDailyCostComparisonICSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
			
		sql.append("select max(distinct(r.id)) as id ")
		.append(" , max(RC.CODE) as requestCategoryCode ")
		.append(" , k.code as itemCode ")
		.append(" , k.description as itemCodeDescription ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) as averageCost, avg(k.standard_cost * DC.UNITS) as standardCost ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) as difference ")      
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource ir ")
		.append(" , isw_resource r ")
		.append(" , isw_work_period wp ")
		.append(" , isw_work_period_assignment wpa ")
		.append(" , isw_assignment a ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		.append(" , iswl_request_category rc ")
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
		.append(" and r.id = ir.resource_id ")
		.append(" and r.parent_resource_id is null ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id ")
		.append(" and a.end_date is null ")
		.append(" and k.id = a.kind_id ")
		.append(" and rc.id = K.REQUEST_CATEGORY_ID ")
		.append(" and RC.CODE not in ('A', 'O' ) ")
		.append(" and k.standard_cost >= 0 ")
		.append(" group by sgc.description, k.code, k.description ");
		//.append(" order by itemCodeCategory ");
		
		///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select max(distinct(ro.id)) as id ")
		.append(" , max(RC.CODE) as requestCategoryCode ")
		.append(" , k.code as itemCode ")
		.append(" , k.description as itemCodeDescription ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) as averageCost, avg(k.standard_cost * DC.UNITS) as standardCost ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) as difference ")      
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource_other iro ")
		.append(" , isw_resource_other ro ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		.append(" , iswl_request_category rc ")
		//.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
		.append(" and ro.id = iro.resource_other_id ")
		.append(" and k.id = ro.kind_id ")
		.append(" and rc.id = K.REQUEST_CATEGORY_ID ")
		.append(" and RC.CODE not in ('A', 'O' ) ")
		.append(" and k.standard_cost >= 0 ")
		.append(" group by sgc.description, k.code, k.description ")
		.append(" order by itemCodeCategory ");
				
		return sql.toString();
	}
		
	public static String getDailyCostComparisonRESRSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
			
		sql.append("select distinct(r.id) ")
		.append(" , RC.CODE as requestCategoryCode ")
		.append(" , A.REQUEST_NUMBER as requestNumber ")
		.append(" , r.resource_name as name ")
		.append(" , ag.agency_code as agencyCode ")
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , k.code as itemCode ")
		.append(" , k.description as itemCodeDescription ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) as averageCost, avg(k.standard_cost * DC.UNITS) as standardCost ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) as difference ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource ir ")
		.append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		.append(" , isw_resource r ")
		.append(" , isw_work_period wp ")
		.append(" , isw_work_period_assignment wpa ")
		.append(" , isw_assignment a ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		.append(" , iswl_request_category rc ")
		.append(" , iswl_agency ag ")
		//.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
		.append(" and r.id = ir.resource_id ")
		.append(" and r.parent_resource_id is null ")
		.append(" and ag.id = r.agency_id ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id ")
		.append(" and a.end_date is null ")
		.append(" and k.id = a.kind_id ")
		.append(" and rc.id = K.REQUEST_CATEGORY_ID ")
		.append(" and RC.CODE not in ('A', 'O' ) ")
		.append(" and k.standard_cost >= 0 ")   
		.append(" group by sgc.description, r.id, rc.code, rc.description, k.code, k.description, a.request_number, r.resource_name, ag.agency_code,cd.assign_date ")
		.append(" having avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) > 0 ");
		//.append(" order by itemCodeCategory ");
		
		///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select distinct(ro.id) ")
		.append(" , RC.CODE as requestCategoryCode ")
		.append(" , ro.REQUEST_NUMBER as requestNumber ")
		.append(" , ro.cost_description as name ")
		.append(" , ag.agency_code as agencyCode ")
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , k.code as itemCode ")
		.append(" , k.description as itemCodeDescription ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) as averageCost, avg(k.standard_cost * DC.UNITS) as standardCost ")
		.append(" , avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) as difference ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource_other iro ")
		.append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
		.append(" , isw_resource_other ro ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		.append(" , iswl_request_category rc ")
		.append(" , iswl_agency ag ")
		//.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
		.append(" and ro.id = iro.resource_other_id ")
		.append(" and ag.id = ro.agency_id ")
		.append(" and k.id = ro.kind_id ")
		.append(" and rc.id = K.REQUEST_CATEGORY_ID ")
		.append(" and RC.CODE not in ('A', 'O' ) ")
		.append(" and k.standard_cost >= 0 ")   
		.append(" group by sgc.description, ro.id, rc.code, rc.description, k.code, k.description, ro.request_number, ro.cost_description, ag.agency_code,cd.assign_date ")
		.append(" having avg(DC.TOTAL_COST_AMOUNT) - avg(k.standard_cost * DC.UNITS) > 0 ")
		.append(" order by itemCodeCategory ");
				
		return sql.toString();
	}
	
	public static String getResourcesWithDailyCostExceeds10000SubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
				
		sql.append("select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
		.append(" , ag.agency_code as agencyCode     ")    
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , ( ")
		.append(" case  ")
		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append(" end ")
		.append(" ) as name ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , k.code as itemCode ")
		.append(" , sum(total_cost_amount) as costToDate ")
		.append(" , MAX(unit_cost_amount) as maxUnitCost ")
		.append(" , MAX(rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
		.append(" , MAX(( ")
		.append(" case  ")
		.append(" when to_char(activity_date,'MM/DD/YYYY') = to_char( " + (isOracle ? "sysdate" : "now()") + " ,'MM/DD/YYYY') then total_cost_amount ")
		.append(" else null ")
		.append(" end ")
		.append(" )) as currentDateCost ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource ir ")
		.append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		.append(" , isw_resource r ")
		.append(" left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" , isw_work_period wp ")
		.append(" , isw_work_period_assignment wpa ")
		.append(" , isw_assignment a ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		//.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
		.append(" and r.id = ir.resource_id ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id ")
		.append(" and a.end_date is null ")
		.append(" and k.id = a.kind_id ")
		.append(" and sgc.id = K.SUB_GROUP_CATEGORY_ID ")
		//.append(" and (select sum(total_cost_amount) from isw_inc_res_daily_cost where incident_resource_id = ir.id )  >= " + filter.getExceeds10000())
		.append(" and (select total_cost_amount from isw_inc_res_daily_cost where incident_resource_id = ir.id and to_char(activity_date,'MM/DD/YYYY') = to_char( " + (isOracle ? "sysdate" : "now()") + " ,'MM/DD/YYYY') )  > " + filter.getExceeds10000())
		.append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, ag.agency_code, cd.assign_date ");
		//.append(" order by itemCodeCategory ");
		
		//System.out.println(sql.toString());
		
		///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select  ")
		.append(" ro.id ")
		.append(" , ro.request_number as requestNumber ")
		.append(" , ag.agency_code as agencyCode     ")    
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , ro.cost_description as name ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , k.code as itemCode ")
		.append(" , sum(total_cost_amount) as costToDate ")
		.append(" , MAX(unit_cost_amount) as maxUnitCost ")
		.append(" , MAX(rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
		.append(" , MAX(( ")
		.append(" case  ")
		.append(" when to_char(activity_date,'MM/DD/YYYY') = to_char( " + (isOracle ? "sysdate" : "now()") + " ,'MM/DD/YYYY') then total_cost_amount ")
		.append(" else null ")
		.append(" end ")
		.append(" )) as currentDateCost ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource_other iro ")
		.append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
		.append(" , isw_resource_other ro ")
		.append(" left join iswl_agency ag on ro.agency_id = ag.id ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		//.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
		.append(" and ro.id = iro.resource_other_id ")
		.append(" and k.id = ro.kind_id ")
		.append(" and sgc.id = K.SUB_GROUP_CATEGORY_ID ")
		//.append(" and (select sum(total_cost_amount) from isw_inc_res_daily_cost where incident_resource_id = ir.id )  >= " + filter.getExceeds10000())
		.append(" and (select total_cost_amount from isw_inc_res_daily_cost where incident_resource_other_id = iro.id and to_char(activity_date,'MM/DD/YYYY') = to_char( " + (isOracle ? "sysdate" : "now()") + " ,'MM/DD/YYYY') )  > " + filter.getExceeds10000())
		.append(" group by ro.id, ro.cost_description, sgc.description, k.code, ro.request_number, ag.agency_code, cd.assign_date ")
		.append(" order by itemCodeCategory ");
				
		return sql.toString();
	}
	
	
	public static String getResourcesWithActualTimePostingButThreeOrMoreDaysSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
						
		sql.append("SELECT R.ID ")
		.append(" ,(case  ")
	    .append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
	    .append(" end ) as name ")
	    .append(" , K.CODE as itemCode ")
	    .append(" ,A.REQUEST_NUMBER as requestNumber ")
	    .append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
	    .append(" , (to_number(to_char( " + (isOracle ? "sysdate" : "now()") + " ,'J'),'9999999') - to_number(to_char(max(atp.post_start_date),'J'),'9999999') ) as unpostedDays ")
	    .append(" , MAX(dc.unit_cost_amount) as maxUnitCost ")
	    .append(" , MAX(dc.rate_type) as rateType ")
	    .append(" , MAX(dc.units) AS rateUnits ")
	    .append(" , ag.agency_code as agencyCode ")
	    .append(" , sgc.description as itemCodeCategory ")
	    .append(" FROM ISW_INCIDENT_RESOURCE IR ")
	    .append(" LEFT JOIN ISW_COST_DATA CD on IR.COST_DATA_ID=CD.ID ")
	    .append(" , isw_inc_res_daily_cost dc ")
	    .append(" , ISW_RESOURCE R ")
	    .append(" left join iswl_agency ag on r.agency_id = ag.id ")
	    .append(" , ISW_WORK_PERIOD WP ")
	    .append(" , ISW_WORK_PERIOD_ASSIGNMENT WPA ")
	    .append(" , ISW_ASSIGNMENT A ")
	    .append(" LEFT JOIN (ISWL_KIND K left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ) on A.KIND_ID = K.ID ")
	    .append(" , ISW_ASSIGNMENT_TIME AT ")
	    .append(" , ISW_ASSIGN_TIME_POST ATP ")
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
	    .append(" and R.ID = IR.RESOURCE_ID ")
	    .append(" AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
	    .append(" AND WPA.WORK_PERIOD_ID = WP.ID ")
	    .append(" AND A.ID = WPA.ASSIGNMENT_ID ")
	    .append(" AND AT.ASSIGNMENT_ID = A.ID ")
	    .append(" AND ATP.ASSIGNMENT_TIME_ID = AT.ID ")
	    .append(" AND ir.id = dc.incident_resource_id ") 
	    .append(" AND AT.ID IN ( ")
		.append(" SELECT ASSIGNMENT_TIME_ID FROM ISW_ASSIGN_TIME_POST ")
	    .append(" ) ")
	    .append(" group by r.id, r.resource_name, r.first_name, r.last_name ")
	    .append(" , cd.assign_date, r.is_person, k.code, sgc.description, ag.agency_code, a.request_number ")
	    .append(" having ( ")
		.append(" to_number(to_char( " + (isOracle ? "sysdate" : "now()") + " ,'J'),'9999999') - to_number(to_char(max(atp.post_start_date),'J'),'9999999') ")
	    .append(" ) >= " + filter.getThreeOrMoreDays())
		.append(" order by sgc.description ");
						
		return sql.toString();
	}		
	
	public static String getResourcesWithNoActualTimePostedSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT R.ID ")
		.append(" ,(case  ")
		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append(" end ) as name ")
		.append(" , K.CODE as itemCode ")
		.append(" ,A.REQUEST_NUMBER as requestNumber ")
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , (to_number(to_char( " + (isOracle ? "sysdate" : "now()") + " ,'J'),'9999999') - to_number(to_char(cd.assign_date,'J'),'9999999') ) as unpostedDays ")
		.append(" , MAX(dc.unit_cost_amount) as maxUnitCost ")
		.append(" , MAX(dc.rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
		.append(" , ag.agency_code as agencyCode ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" FROM ISW_INCIDENT_RESOURCE IR ")
		.append(" LEFT JOIN ISW_COST_DATA CD on IR.COST_DATA_ID=CD.ID ")
		.append(" , isw_inc_res_daily_cost dc ")
		.append(" , ISW_RESOURCE R ")
		.append(" left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" , ISW_WORK_PERIOD WP ")
		.append(" , ISW_WORK_PERIOD_ASSIGNMENT WPA ")
	    .append(" , ISW_ASSIGNMENT A ")
	    .append(" LEFT JOIN (ISWL_KIND K left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ) on A.KIND_ID = K.ID ")
	    .append(" , ISW_ASSIGNMENT_TIME AT ")
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
	    .append(" and R.ID = IR.RESOURCE_ID ")
	    .append(" AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
	    .append(" AND WPA.WORK_PERIOD_ID = WP.ID ")
	    .append(" AND A.ID = WPA.ASSIGNMENT_ID ")
	    .append(" AND AT.ASSIGNMENT_ID = A.ID ")
	    //.append(" AND ir.incident_id = " + incidentId)
	    .append(" AND AT.ID NOT IN ( ")
		.append(" SELECT ASSIGNMENT_TIME_ID FROM ISW_ASSIGN_TIME_POST ")
	    .append(" ) ")
	    .append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, ag.agency_code, cd.assign_date ")
		.append(" order by sgc.description ");

		
		//System.out.println("sql: " + sql.toString());
		
		return sql.toString();
	}	
	
	public static String getResourcesWithNoAgencyAssignedSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
				
		sql.append("select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ( ")
    	.append(" case  ")
        .append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
        .append(" end ")
        .append(" ) as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('MISSING AGENCY') AS exceptn ")       //using MAX to let hibernate think this is the string type
        .append(" from isw_incident_resource ir ")
        .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc ")
        .append(" , isw_resource r ")
        .append(" , isw_work_period wp ")
        .append(" , isw_work_period_assignment wpa ")
        .append(" , isw_assignment a ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
        .append(" and r.id = ir.resource_id ")
        .append(" and wp.incident_resource_id = ir.id ")
        .append(" and wpa.work_period_id = wp.id ")
        .append(" and a.id = wpa.assignment_id ")
        .append(" and a.end_date is null ")
        .append(" and k.id = a.kind_id ")
        .append(" and r.agency_id is null ")
		.append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, cd.assign_date, rate_type ");
		//.append(" order by itemCodeCategory ");
		
		
		//.append(" and dc.incident_resource_id = ir.id ")
        //  and ir.daily_cost_exception in ('Status is F', 'Missing Dates','Missing Agency')
		
//		String exceptionStr;
//		
//			if (!filter.isAllExceptionsIncluded() && !filter.isMissingAgencyIncluded() && !filter.isMissingDatesIncluded() && !filter.isStatusIsFIncluded()) 
//				exceptionStr = " ";
//			else {
//				if (filter.isAllExceptionsIncluded())
//				    //exceptionStr = " and ir.daily_cost_exception is not null ";
//					exceptionStr = " and upper(ir.daily_cost_exception) in ('MISSING AGENCY','MISSING CHECK-IN/ASSIGN DATE','STATUS IS F') ";
//				else {		
//					exceptionStr = " and upper(ir.daily_cost_exception) in ( ";
//					
//					if (filter.isMissingAgencyIncluded())
//						exceptionStr = exceptionStr + " 'MISSING AGENCY' ,";
//					if (filter.isMissingDatesIncluded())
//						exceptionStr = exceptionStr +  " 'MISSING CHECK-IN/ASSIGN DATE' ,";
//					if (filter.isStatusIsFIncluded())
//						exceptionStr = exceptionStr +  " 'STATUS IS F' ,";
//					
//					exceptionStr = exceptionStr.substring(0, exceptionStr.length()-2) + " ) ";
//				}	
//			}
//		
//	    sql.append(exceptionStr.toUpperCase())
//           .append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, cd.assign_date ");
		
		
		///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select  ")
		.append(" ro.id ")
		.append(" , ro.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ro.cost_description as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('MISSING AGENCY') AS exceptn ")       //using MAX to let hibernate think this is the string type
        .append(" from isw_incident_resource_other iro ")
        .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc ") 
        .append(" , isw_resource_other ro ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
        .append(" and ro.id = iro.resource_other_id ")
        .append(" and k.id = ro.kind_id ")
        .append(" and ro.agency_id is null ")
		.append(" group by ro.id, ro.cost_description, sgc.description, k.code, ro.request_number, cd.assign_date, rate_type ")
		.append(" order by itemCodeCategory ");
	
		return sql.toString();
	}	
	
	
	public static String getResourcesWithNoDailyCostRecordsSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlAssignDates = new StringBuffer();
		StringBuffer sqlStatusIsF = new StringBuffer();
		StringBuffer sqlNoRecords = new StringBuffer();
						
		sqlAssignDates.append(" select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ( ")
    	.append(" case  ")
        .append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
        .append(" end ")
        .append(" ) as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('MISSING ASSIGN DATE') AS exceptn ")
        .append(" from isw_incident_resource ir ")
        .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc ")
        .append(" , isw_resource r ")
        .append(" , isw_work_period wp ")
        .append(" , isw_work_period_assignment wpa ")
        .append(" , isw_assignment a ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
        .append(" and r.id = ir.resource_id ")
        .append(" and wp.incident_resource_id = ir.id ")
        .append(" and wpa.work_period_id = wp.id ")
        .append(" and a.id = wpa.assignment_id ")
        .append(" and a.end_date is null ")
        .append(" and k.id = a.kind_id ") 
		.append(" and cd.assign_date is null ")
        .append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, cd.assign_date, rate_type  ");
		
		///////////////////////////resource_other////////////////////////////////////
        
		sqlAssignDates.append(" UNION ");
		
		sqlAssignDates.append(" select  ")
		.append(" ro.id ")
		.append(" , ro.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ro.cost_description as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('MISSING ASSIGN DATE') AS exceptn ")
        .append(" from isw_incident_resource_other iro ")
        .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc ")
        .append(" , isw_resource_other ro ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
        .append(" and ro.id = iro.resource_other_id ")
        .append(" and k.id = ro.kind_id ") 
		.append(" and cd.assign_date is null ")
        .append(" group by ro.id, ro.cost_description, sgc.description, k.code, ro.request_number, cd.assign_date, rate_type  ");

		
		sqlStatusIsF.append(" select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ( ")
    	.append(" case  ")
        .append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
        .append(" end ")
        .append(" ) as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('STATUS IS F') AS exceptn ")
        .append(" from isw_incident_resource ir ")
        .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc  ")
        .append(" , isw_resource r ")
        .append(" , isw_work_period wp ")
        .append(" , isw_work_period_assignment wpa ")
        .append(" , isw_assignment a ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
        .append(" and r.id = ir.resource_id ")
        .append(" and wp.incident_resource_id = ir.id ")
        .append(" and wpa.work_period_id = wp.id ")
        .append(" and a.id = wpa.assignment_id ")
        .append(" and a.end_date is null ")
        .append(" and k.id = a.kind_id ")
		.append(" and a.assignment_status = 'F' ")
        .append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, cd.assign_date, rate_type  ");
		
		///////////////////////////resource_other////////////////////////////////////
        
		sqlStatusIsF.append(" UNION ");
		
		sqlStatusIsF.append(" select  ")
		.append(" ro.id ")
		.append(" , ro.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ro.cost_description as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('STATUS IS F') AS exceptn ")
        .append(" from isw_incident_resource_other iro ")
        .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc ")
        .append(" , isw_resource_other ro ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
		.append(" and ro.id = iro.resource_other_id ")
        .append(" and k.id = ro.kind_id ")
		.append(" and iro.assignment_status = 'F' ")
        .append(" group by ro.id, ro.cost_description, sgc.description, k.code, ro.request_number, cd.assign_date, rate_type  ");
		
				
		sqlNoRecords.append(" select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
        .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
        .append(" , ( ")
    	.append(" case  ")
        .append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
        .append(" end ")
        .append(" ) as name ")
	    .append(" , sgc.description as itemCodeCategory ")
        .append(" , k.code as itemCode ")
        .append(" , MAX(unit_cost_amount) as maxUnitCost ")
        .append(" , rate_type as rateType ")
        .append(" , MAX('STATUS IS F') AS exceptn ")
        .append(" from isw_incident_resource ir ")
        .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
        .append(" , isw_inc_res_daily_cost dc on ")
        .append(" , isw_resource r ")
        .append(" , isw_work_period wp ")
        .append(" , isw_work_period_assignment wpa ")
        .append(" , isw_assignment a ")
        .append(" , iswl_kind k ")
        .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
        //.append(" where ir.incident_id = -1")
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
        .append(" and r.id = ir.resource_id ")
        .append(" and wp.incident_resource_id = ir.id ")
        .append(" and wpa.work_period_id = wp.id ")
        .append(" and a.id = wpa.assignment_id ")
        .append(" and a.end_date is null ")
        .append(" and k.id = a.kind_id ")
		.append(" and a.assignment_status = 'F' ")
        .append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, cd.assign_date, rate_type  ");
				
		
		if (!filter.isAllExceptionsIncluded() && !filter.isMissingDatesIncluded() && !filter.isStatusIsFIncluded()) {
			//should return 0 record?
			//sql.append(sqlAssignDates.toString())
			//.append(" union ")
			//.append(sqlStatusIsF.toString());
			sql.append(sqlNoRecords.toString());  //Guaranteed Return no records
		}
		else {
			if (filter.isAllExceptionsIncluded()) {
				sql.append(" ( ")
				.append(sqlAssignDates.toString())
				.append(" ) ")
				.append(" union ")
				.append(" ( ")
				.append(sqlStatusIsF.toString())
				.append(" ) ")
				.append(" order by itemCodeCategory ");
			}
			else {		
				if (filter.isMissingDatesIncluded() && filter.isStatusIsFIncluded()) {
					sql.append(" ( ")
					.append(sqlAssignDates.toString())
					.append(" ) ")
					.append(" union ")
					.append(" ( ")
					.append(sqlStatusIsF.toString())
					.append(" ) ")
					.append(" order by itemCodeCategory ");
				}
				else {
					if (filter.isStatusIsFIncluded()) {
						sql.append(sqlStatusIsF.toString())
						   .append(" order by itemCodeCategory ");
					}
					else {
						if (filter.isMissingDatesIncluded()) {
							sql.append(sqlAssignDates.toString())
							   .append(" order by itemCodeCategory ");
						}
					}
				}
			}	
		}
					
		//System.out.println("sql: " + sql.toString());
		
		return sql.toString();
	}	
	
	
	public static String getResourceItemCodeByCostSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
				
		sql.append("select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
		.append(" , ag.agency_code as agencyCode     ")    
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , ( ")
		.append(" case  ")
		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append(" end ")
		.append(" ) as name ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , k.code as itemCode ")
		.append(" , MAX(unit_cost_amount) as unitCost ")
		.append(" , MAX(rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
		.append(" , MAX( ")
		.append(" case  ")
		.append(" when org.type = 'L' then 'Yes'  ")
		.append(" else 'No' ")
		.append(" end ")
		.append(" ) as local ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource ir ")
		.append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		.append(" , isw_resource r ")
		.append(" left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" left join isw_incident_org org on (r.organization_id = org.organization_id) and (org.incident_id = " + incidentId + ")")
		.append(" , isw_work_period wp ")
		.append(" , isw_work_period_assignment wpa ")
		.append(" , isw_assignment a ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		//.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
		.append(" and r.id = ir.resource_id ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id ")
		.append(" and a.end_date is null ")
		.append(" and k.id = a.kind_id ")
		.append(" and sgc.id = K.SUB_GROUP_CATEGORY_ID ")
		.append(" and r.is_person = " + (isOracle ? 0 : false) + " ");
		
//		if (filter.isDateRangeIncluded()) {
//			sql.append(" and dc.activity_date >= to_date('" + filter.getStartDateChar() + "', 'MM/DD/YYYY') ")
//			.append(" and dc.activity_date <= to_date('" + filter.getEndDateChar() + "', 'MM/DD/YYYY') ");
//		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (filter.isDateRangeIncluded()) {
			sql.append(" and dc.activity_date >= to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			.append(" and dc.activity_date <= to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
						
		sql.append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, ag.agency_code, cd.assign_date ");
		//.append(" order by itemCodeCategory ");
		
		//System.out.println(sql.toString());
		
		///////////////////////////resource_other////////////////////////////////////
        
		sql.append(" UNION ");
		
		sql.append("select  ")
		.append(" ro.id ")
		.append(" , ro.request_number as requestNumber ")
		.append(" , ag.agency_code as agencyCode     ")    
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , ro.cost_description as name ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , k.code as itemCode ")
		.append(" , MAX(unit_cost_amount) as unitCost ")
		.append(" , MAX(rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
//		.append(" , MAX( ")
//		.append(" '') as local ")
		.append(" , MAX( ")
		.append(" case  ")
		.append(" when org.type = 'L' then 'Yes'  ")
		.append(" else 'No' ")
		.append(" end ")
		.append(" ) as local ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource_other iro ")
		.append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
		.append(" , isw_resource_other ro ")
		.append(" left join iswl_agency ag on ro.agency_id = ag.id ")
		.append(" left join isw_incident_org org on (ro.organization_id = org.organization_id) and (org.incident_id = " + incidentId + ")")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		//.append(" where iro.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and iro.id = dc.incident_resource_other_id ")
		.append(" and ro.id = iro.resource_other_id ")
		.append(" and k.id = ro.kind_id ")
		.append(" and sgc.id = K.SUB_GROUP_CATEGORY_ID ");
		//.append(" and r.is_person = " + (isOracle ? 0 : false) + " ");
		
//		if (filter.isDateRangeIncluded()) {
//			sql.append(" and dc.activity_date >= to_date('" + filter.getStartDateChar() + "', 'MM/DD/YYYY') ")
//			.append(" and dc.activity_date <= to_date('" + filter.getEndDateChar() + "', 'MM/DD/YYYY') ");
//		}
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		if (filter.isDateRangeIncluded()) {
			sql.append(" and dc.activity_date >= to_timestamp('" + sdf2.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			.append(" and dc.activity_date <= to_timestamp('" + sdf2.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
						
		sql.append(" group by ro.id, ro.cost_description, sgc.description, k.code, ro.request_number, ag.agency_code, cd.assign_date ")
		.append(" order by itemCodeCategory ");
				
		return sql.toString();
	}		
	
	
	public static String getResourceItemCodeByCostOHPersonnelSubReportDataQuery(AnalysisReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select  ")
		.append(" r.id ")
		.append(" , a.request_number as requestNumber ")
		.append(" , ag.agency_code as agencyCode     ")    
		.append(" ,TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assignDateChar ")
		.append(" , ( ")
		.append(" case  ")
		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append(" end ")
		.append(" ) as name ")
		.append(" , sgc.description as itemCodeCategory ")
		.append(" , k.code as itemCode ")
		.append(" , MAX(unit_cost_amount) as unitCost ")
		.append(" , MAX(rate_type) as rateType ")
		.append(" , MAX(dc.units) AS rateUnits ")
		.append(" , MAX( ")
		.append(" case  ")
		.append(" when org.type = 'L' then 'Yes'  ")
		.append(" else 'No' ")
		.append(" end ")
		.append(" ) as local ")
		.append(" from isw_inc_res_daily_cost dc ")
		.append(" , isw_incident_resource ir ")
		.append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
		.append(" , isw_resource r ")
		.append(" left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" left join isw_incident_org org on (r.organization_id = org.organization_id) and (org.incident_id = " + incidentId + ")")
		.append(" , isw_work_period wp ")
		.append(" , isw_work_period_assignment wpa ")
		.append(" , isw_assignment a ")
		.append(" , iswl_kind k ")
		.append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
		//.append(" where ir.incident_id = " + incidentId)
		.append(" where dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + incidentId + ") ")
		.append(" and ir.id = dc.incident_resource_id ")
		.append(" and r.id = ir.resource_id ")
		.append(" and wp.incident_resource_id = ir.id ")
		.append(" and wpa.work_period_id = wp.id ")
		.append(" and a.id = wpa.assignment_id ")
		.append(" and a.end_date is null ")
		.append(" and k.id = a.kind_id ")
		.append(" and sgc.id = K.SUB_GROUP_CATEGORY_ID ")
		.append(" and r.is_person = " + (isOracle ? 1 : true) + " ");
		
//		if (filter.isDateRangeIncluded()) {
//			sql.append(" and dc.activity_date >= to_date('" + filter.getStartDateChar() + "', 'MM/DD/YYYY') ")
//			.append(" and dc.activity_date <= to_date('" + filter.getEndDateChar() + "', 'MM/DD/YYYY') ");
//		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (filter.isDateRangeIncluded()) {
			sql.append(" and dc.activity_date >= to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			.append(" and dc.activity_date <= to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		
		sql.append(" group by r.id, r.last_name, r.first_name, r.is_person, r.resource_name, sgc.description, k.code, a.request_number, ag.agency_code, cd.assign_date ")
		.append(" order by itemCodeCategory ");
		
		//System.out.println(sql.toString());
				
		return sql.toString();
	}		
	
	
}
