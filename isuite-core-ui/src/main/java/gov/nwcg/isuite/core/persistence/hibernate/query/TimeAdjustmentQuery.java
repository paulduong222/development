package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;



public class TimeAdjustmentQuery {

	public static String getResourcesTimeAdjustmentCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_TIME_ASSIGN_ADJUST WHERE " +
			"ASSIGNMENT_ID IN " +
			"( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id in ( :ids ) "  + 
			") ";
		
		return sql;
	}

	public static String getIncidentResourceIdQuery(Long assignmentId) {
		String sql =
			"	SELECT distinct(incident_resource_id) "+
			"	FROM ISW_WORK_PERIOD " +
			"	WHERE ID IN " +
			"	( " +
			"     SELECT wpa.work_period_id FROM " +
			"           ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"     WHERE wpa.assignment_id = " + assignmentId + " " + 
			"	 ) "; 
		
		return sql;
	}
	
	public static String getIncidentIdQuery(Long assignmentId) {
		String sql =
			"SELECT INCIDENT_ID " +
			"FROM ISW_INCIDENT_RESOURCE " +
			"WHERE ID = " +
			"( " +
			"	SELECT distinct(incident_resource_id) "+
			"	FROM ISW_WORK_PERIOD " +
			"	WHERE ID IN " +
			"	( " +
			"     SELECT wpa.work_period_id FROM " +
			"           ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"     WHERE wpa.assignment_id = " + assignmentId + " " + 
			"	 ) " +
			") ";
		
		return sql;
	}

	public static String getDeleteByIncidentResourceIdQuery(Long incidentResourceId){
		String sql = 
			"DELETE FROM ISW_TIME_ASSIGN_ADJUST WHERE " +
			"ASSIGNMENT_ID IN " +
			"( " +
			"  SELECT ASSIGNMENT_ID FROM ISW_WORK_PERIOD_ASSIGNMENT " +
			"    WHERE WORK_PERIOD_ID IN (SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID = " + incidentResourceId + " ) " +
			") " +
			" "; 
		
		return sql;
	}

	public static String getTimeAdjustmentsQuery(Long assignmentId){
		String sql = "select taa.id as id " +
		       		 ",taa.activity_date as activityDate " +
		       		 ", taa.assignment_id as assignmentId " +
		       		 ", taa.adjustment_amount as adjustmentAmount " +
		       		 ", taa.adjustment_type as adjustmentType " +
		       		 ", taa.incident_account_code_id as incidentAccountCodeId " +
		       		 "from isw_time_assign_adjust taa " +
		       		 "where taa.assignment_id = " + assignmentId + " " +
		       		 "and taa.deleted_date is null " +
		       		 "";
		return sql;
	}
	
	public static String getIncidentResourceTimeAdjustDataQuery(Long irParentId, Date postDate, Boolean subsOnly,Boolean isOracle){
		String sql =
		"select taa.id as timeAssignAdjustId " +
		"       , taa.assignment_id as assignmentId " +
		"       , ir.id as incidentResourceId " +
		"       , taa.activity_date as activityDate " +
		"       , taa.adjustment_type as adjustmentType " +
		"       , taa.adjustment_amount as adjustmentAmount " +
		"       , adjc.code as adjustmentCategory " +
		"       , adjc.description as adjustmentCategoryDesc " +
		"       , iac.id as incidentAccountCodeId " +
		"       , ac.account_code as accountingCode " +
		"       , taa.commodity as commodity " +
		"from isw_incident_resource ir " +
		"	, isw_resource r " +
		"	, isw_work_period wp " +
		"	, isw_work_period_assignment wpa " +
		"	, isw_assignment a " +
		"	, isw_assignment_time at " +
		"	, isw_time_assign_adjust taa " +
		"		left join isw_incident_account_code iac on taa.incident_account_code_id = iac.id " +
		"		left join isw_account_code ac on iac.account_code_id = ac.id " +
		"		left join iswl_adjust_category adjc on taa.adjust_category_id = adjc.id ";
		if(BooleanUtility.isTrue(subsOnly)){
			sql=sql+"where ir.resource_id = r.id " +
			"and r.parent_resource_id = (select resource_id from isw_incident_resource where id = " +irParentId + ") ";
		}else{
			sql=sql+"where ir.id = "+irParentId+" " +
			"and r.id = ir.resource_id ";
		}
		sql=sql+"and wp.incident_resource_id = ir.id " +
		"and wpa.work_period_id = wp.id " +
		"and a.id = wpa.assignment_id " +
		"and at.assignment_id = a.id " +
		"and at.employment_type in ('FED','AD','OTHER') " +
		"and taa.assignment_id = a.id " +
		"and taa.deleted_date is null " ;
		if(DateUtil.hasValue(postDate)){
			sql=sql+"and to_timestamp(to_char(taa.activity_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			"			< to_timestamp('"+DateUtil.toDateString(postDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') ";
		}
		sql=sql+"and taa.id not in ( " +
		"	select taai.time_post_adjust_id " +
		"	from isw_time_assign_adj_invoice taai " +
		"	     , isw_time_invoice ti " +
		"	where ti.id = taai.time_invoice_id " +
		"	and ti.is_draft = " + (isOracle ? 0 : false) + " " +
		"	and ti.deleted_date is null " +
		"	and taai.time_post_adjust_id = taa.id " +
		") " +
		"order by taa.activity_date asc ";
		
		return sql;
	}
	
	public static String getIncidentResourceTimeAdjustDataQuery2(Long irParentId, Date postDate, Boolean subsOnly,Boolean isOracle){
		String sql =
		"select taa.id as timeAssignAdjustId " +
		"       , taa.assignment_id as assignmentId " +
		"       , ir.id as incidentResourceId " +
		"       , taa.activity_date as activityDate " +
		"       , taa.adjustment_type as adjustmentType " +
		"       , taa.adjustment_amount as adjustmentAmount " +
		"       , adjc.code as adjustmentCategory " +
		"       , adjc.description as adjustmentCategoryDesc " +
		"       , iac.id as incidentAccountCodeId " +
		"       , ac.account_code as accountingCode " +
		"       , taa.commodity as commodity " +
		"from isw_incident_resource ir " +
		"	, isw_resource r " +
		"	, isw_work_period wp " +
		"	, isw_work_period_assignment wpa " +
		"	, isw_assignment a " +
		"	, isw_assignment_time at " +
		"	, isw_time_assign_adjust taa " +
		"		left join isw_incident_account_code iac on taa.incident_account_code_id = iac.id " +
		"		left join isw_account_code ac on iac.account_code_id = ac.id " +
		"		left join iswl_adjust_category adjc on taa.adjust_category_id = adjc.id ";
		if(BooleanUtility.isTrue(subsOnly)){
			sql=sql+"where ir.resource_id = r.id " +
			"and r.parent_resource_id = (select resource_id from isw_incident_resource where id = " +irParentId + ") ";
		}else{
			sql=sql+"where ir.id = "+irParentId+" " +
			"and r.id = ir.resource_id ";
		}
		sql=sql+"and wp.incident_resource_id = ir.id " +
		"and wpa.work_period_id = wp.id " +
		"and a.id = wpa.assignment_id " +
		"and at.assignment_id = a.id " +
		"and at.employment_type in ('FED','AD','OTHER') " +
		"and taa.assignment_id = a.id " +
		"and taa.deleted_date is null " ;
		if(DateUtil.hasValue(postDate)){
			sql=sql+"and to_timestamp(to_char(taa.activity_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			"			< to_timestamp('"+DateUtil.toDateString(postDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') ";
		}
		/*
		sql=sql+"and taa.id not in ( " +
		"	select taai.time_post_adjust_id " +
		"	from isw_time_assign_adj_invoice taai " +
		"	     , isw_time_invoice ti " +
		"	where ti.id = taai.time_invoice_id " +
		"	and ti.is_draft = " + (isOracle ? 0 : false) + " " +
		"	and ti.deleted_date is null " +
		"	and taai.time_post_adjust_id = taa.id " +
		") " +
		*/
		sql=sql+" order by taa.activity_date asc ";
		
		return sql;
	}

}
