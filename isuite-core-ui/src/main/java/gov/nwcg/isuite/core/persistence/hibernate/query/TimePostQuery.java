package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class TimePostQuery {

	public static String getResourceTimePostCountQuery(Long incidentResourceId) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) " +
			") ";
		
		return sql;
	}

	public static String getResourcesTimePostCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id in ( :ids ) "  + 
			"  ) " +
			") ";
		
		return sql;
	}
	
	public static String getResourceInvoicedTimePostCountQuery(Long incidentResourceId) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) " +
			") AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN ( SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL )) ";
		
		return sql;
	}

	public static String getResourcesInvoicedTimePostCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id in ( :ids ) " + 
			"  ) " +
			") AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN ( SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL )) ";
		
		return sql;
	}

	public static String getResourceNonInvoicedTimePostCountQuery(Long incidentResourceId) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) " +
			") " +
			"AND "+
			" (" +
			"	ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) "+
			"   OR "+
			"   ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN ( SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NOT NULL )) " + 
			" ) ";
			
		
		return sql;
	}

	public static String getResourceTimePostingIds(Long incidentResourceId, Date lastDate) {
		String sql = 
			"SELECT ID FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) " +
			") ";
			//") AND ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) "; //+
			if(DateUtil.hasValue(lastDate)){
				sql=sql+
				" AND to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
				" <= " +
				"to_date('"+DateUtil.toDateString(lastDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " ;
			}
			sql=sql+" ORDER BY post_start_date,id ";
		
		return sql;
	}
	
	public static String getDeleteResourceNonInvoicedTimePostsQuery(Long incidentResourceId) {
		String sql = 
			"DELETE FROM ISW_ASSIGN_TIME_POST WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) ) "; //+
			//") AND ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) ";
		
		return sql;
	}
	
	public static String getDuplicateTimePostCountQueryOr(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
		"AND (" +
		"		(" +
		"		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		)  " +
		" 		OR " +
		"		(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" 		OR " +
		"		(to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			>= " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			<=" +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" ) " +
		"AND "+
		"("+
		"  special_pay_id is null " +
		"  or " +
		"  special_pay_id not in (select id from iswl_special_pay where code in ('COP','GUAR','DAY OFF' ) ) " +
		") "
		;
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		if(LongUtility.hasValue(timePostId)){
			sql=sql+" AND ID != " + timePostId + " " ;
		}
		
		return sql;
	}

	public static String getDuplicateTimePostIdsQueryOr(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT ID FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
		"AND (" +
		"		(" +
		"		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		)  " +
		" 		OR " +
		"		(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" 		OR " +
		"		(to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			>= " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			<=" +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" ) " +
		"AND "+
		"("+
		"  special_pay_id is null " +
		"  or " +
		"  special_pay_id not in (select id from iswl_special_pay where code in ('COP','GUAR','DAY OFF' ) ) " +
		") "
		;
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		if(LongUtility.hasValue(timePostId)){
			sql=sql+" AND ID != " + timePostId + " " ;
		}
		
		return sql;
	}
	
	public static String getDuplicateTimePostCountQuery(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
		"AND (" +
		"		(" +
		"		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		)  " +
		" 		AND ( " +
		"		(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between " +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" 		OR " +
		"		(to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			>= " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"		to_timestamp('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			<=" +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		) ) " +
		" ) "
		;
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		if(LongUtility.hasValue(timePostId)){
			sql=sql+" AND ID != " + timePostId + " " ;
		}
		
		return sql;
	}

	public static String getDuplicateTimePostCountSpecialQuery(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
		"AND (" +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" ) "
		;
		
		//if(null==stopTime){
			sql=sql+
			"AND (" +
			"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
			" 			!=  " +
			"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" ) ";
		//}
		
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		if(LongUtility.hasValue(timePostId)){
			sql=sql+" AND ID != " + timePostId + " " ;
		}
		
		return sql;
	}

	public static String getDuplicateTimePostIdsSpecialQuery(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT ID FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
		"AND (" +
		"		to_timestamp('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" ) "
		;
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		if(LongUtility.hasValue(timePostId)){
			sql=sql+" AND ID != " + timePostId + " " ;
		}
		
		return sql;
	}
	
	public static String getDuplicateTimePostIds2Query(Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startTime, Date stopTime) {
		String sql = 
			"SELECT ID FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
			"AND " +
			"to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" < " +
			"to_date('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
			" and " +
			"to_date('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
			" < " +
			"to_date(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" "
			;
		if(invoiceOnly){
			sql = sql + "AND ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) ";
		}else{
			sql = sql + "AND ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) ";
			//sql = sql + "AND ( SELECT COUNT(ID) FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE ASSIGN_TIME_POST_ID = ATP.ID) = 0 ";
		}
		
		return sql;
	}

	public static String getPostByFilter(Boolean isOracle, TimePostQueryFilterImpl filter) {
		String sql = "";
		
		sql = "SELECT ID FROM ISW_ASSIGN_TIME_POST " +
			  "WHERE ASSIGNMENT_TIME_ID = " + filter.getAssignmentTimeId() + " "+
			  "AND  to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			  "  = to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			  "AND  to_date(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			  "  = to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			  " ";
		
		return sql;
	}
	
	public static String getDuplicateTimePostDatesQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql = 
			"SELECT distinct(to_char(post_start_date, 'MM/DD/YYYY')) " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"     ,ISW_CONTRACTOR_RATE CR " +
			"     ,ISW_INCIDENT_ACCOUNT_CODE IAC " +
			"	  ,ISW_ACCOUNT_CODE AC " +
			"WHERE " +
			"IAC.ID = ATP.INCIDENT_ACCOUNT_CODE_ID " +
			"AND AC.ID = IAC.ACCOUNT_CODE_ID " +
			"AND ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;

			if(LongUtility.hasValue(filter.getExcludeParentAssignmentTimePostId()))
				sql=sql+"AND ATP.ID != " + filter.getExcludeParentAssignmentTimePostId() + " " ;
			
		sql=sql+ ") "  ;

		if(StringUtility.hasValue(filter.getAccountingCode())){
			// dan 6/20/2013 defect 3093 sql=sql+"AND AC.ACCOUNT_CODE = '" + filter.getAccountingCode()+ "' ";
		}
		
		if(filter.getIncludeTime()){
			sql=sql+"AND ( " +
			"(to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" between  " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" and " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			")  " +
			" OR " +
			"(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" between " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" and " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" ) "+
			" ) ";
		}else{
			sql=sql+"AND ( " +
			"   ( " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         <= " + 
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"     and " +
			"     to_date(to_char(post_stop_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         >= " +
			"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"    ) " +
			"     OR " +
			"   ( " +
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"         <= " + 
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"     and " +
			"           to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"         >= " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"    ) " +
			" ) ";
		}
		
		
		if(StringUtility.hasValue(filter.getUnitOfMeasure()))
			sql=sql+"AND ATP.UNIT_OF_MEASURE = '" + filter.getUnitOfMeasure()+ "' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY")){
				sql=sql+"AND ref_contractor_rate_id is not null ";
				sql=sql+"AND CR.id = ref_contractor_rate_id ";
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("SPECIAL")){
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
							//"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("GUARANTEE")){
				sql=sql+"AND ref_contractor_rate_id is not null ";
				sql=sql+"AND CR.id = ref_contractor_rate_id ";
				sql = sql + "AND is_guarantee_posting = " + (isOracle ? 1 : true ) + " ";
			}

		}
		
		return sql;
	}

	public static String getDuplicateTimePostIdsQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql = 
			"SELECT distinct(atp.id) " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"     ,ISW_CONTRACTOR_RATE CR " +
			"     ,ISW_INCIDENT_ACCOUNT_CODE IAC " +
			"	  ,ISW_ACCOUNT_CODE AC " +
			"WHERE " +
			"IAC.ID = ATP.INCIDENT_ACCOUNT_CODE_ID " +
			"AND AC.ID = IAC.ACCOUNT_CODE_ID " +
			"AND ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;

			if(LongUtility.hasValue(filter.getExcludeParentAssignmentTimePostId()))
				sql=sql+"AND ATP.ID != " + filter.getExcludeParentAssignmentTimePostId() + " " ;
			
		sql=sql+ ") "  ;

		if(StringUtility.hasValue(filter.getAccountingCode())){
			// dan 6/20/2013 defect 3093 sql=sql+"AND AC.ACCOUNT_CODE = '" + filter.getAccountingCode()+ "' ";
		}
		
		if(filter.getIncludeTime()){
			sql=sql+"AND ( " +
			"(to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" between  " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" and " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			")  " +
			" OR " +
			"(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" between " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" and " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			" ) "+
			/*
			"   ( " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         <= " + 
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"     and " +
			"     to_date(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         > " +
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"   ) " +
			"    OR " +
			"   ( " +
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"         <= " + 
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"     and " +
			"     to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"         > " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"   ) " +
			*/
			" ) ";
		}else{
			sql=sql+"AND ( " +
			"   ( " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         <= " + 
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"     and " +
			"     to_date(to_char(post_stop_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         >= " +
			"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"    ) " +
			"     OR " +
			"   ( " +
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"         <= " + 
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"     and " +
			"           to_date('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"         >= " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"    ) " +
			" ) ";
		}
		
		
		if(StringUtility.hasValue(filter.getUnitOfMeasure()))
			sql=sql+"AND CR.UNIT_OF_MEASURE = '" + filter.getUnitOfMeasure()+ "' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY")){
				sql=sql+"AND ref_contractor_rate_id is not null ";
				sql=sql+"AND CR.id = ref_contractor_rate_id ";
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("SPECIAL")){
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
							//"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("GUARANTEE")){
				sql=sql+"AND ref_contractor_rate_id is not null ";
				sql=sql+"AND CR.id = ref_contractor_rate_id ";
				sql = sql + "AND is_guarantee_posting = " + (isOracle ? 1 : true ) + " ";
			}

		}
		
		return sql;
	}

	public static String getDuplicateTimePostHourlyDatesQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql = 
			"SELECT distinct(to_char(post_start_date, 'MM/DD/YYYY')) " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;

			if(LongUtility.hasValue(filter.getExcludeParentAssignmentTimePostId()))
				sql=sql+"AND ATP.ID != " + filter.getExcludeParentAssignmentTimePostId() + " " ;
			
		sql=sql+ ") "  ;

		sql=sql+"AND (" +
		"		(" +
		"		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		)  " +
		" 		OR " +
		"		(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" 		OR " +
		"		(to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			>= " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			<=" +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" ) "
		;
		
		sql=sql+"AND ATP.UNIT_OF_MEASURE = 'HOURLY' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY")){
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("SPECIAL")){
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
							//"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			}
			
		}
		
		return sql;
	}

	public static String getDuplicateTimePostHourlyIdsQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql = 
			"SELECT distinct(atp.id) " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;

			if(LongUtility.hasValue(filter.getExcludeParentAssignmentTimePostId()))
				sql=sql+"AND ATP.ID != " + filter.getExcludeParentAssignmentTimePostId() + " " ;
			
		sql=sql+ ") "  ;

		sql=sql+"AND (" +
		"		(" +
		"		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between  " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"		)  " +
		" 		OR " +
		"		(to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 			between " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"   		!= " +
		"    	to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" 		OR " +
		"		(to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			>= " +
		"  		to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		and " +
		"		to_timestamp('"+DateUtil.toDateString(filter.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		" 			<=" +
		"  		to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		" 		) " +
		" ) "
		;
			
		sql=sql+"AND ATP.UNIT_OF_MEASURE = 'HOURLY' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY")){
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			}
			
			if(filter.getPostType().equals("SPECIAL")){
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
							//"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			}
			
		}
		
		return sql;
	}

	public static String getUpdateIdsDuplicateTimePostDatesQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql =
			"SELECT ID " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"     ,ISW_CONTRACTOR_RATE CR " +
			"     ,ISW_INCIDENT_ACCOUNT_CODE IAC " +
			"	  ,ISW_ACCOUNT_CODE AC " +
			"WHERE " +
			"IAC.ID = ATP.INCIDENT_ACCOUNT_CODE_ID " +
			"AND AC.ID = IAC.ACCOUNT_CODE_ID " +
			"AND ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;
			sql=sql+ ") "  ;

		if(StringUtility.hasValue(filter.getAccountingCode())){
			// dan 6/20/2013 defect 3093 sql=sql+"AND AC.ACCOUNT_CODE = '" + filter.getAccountingCode()+ "' ";
		}
		
		if(filter.getIncludeTime()){
			sql=sql+"AND ( " +
			"     to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         <= " + 
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"     and " +
			"     to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         > " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"    ) " ;
		}else{
			sql=sql+"AND ( " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         <= " + 
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"     and " +
			"     to_date(to_char(post_stop_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         > " +
			"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"    ) " ;
		}
		
		sql=sql+"AND ref_contractor_rate_id is not null ";
		sql=sql+"AND CR.id = ref_contractor_rate_id ";
		
		if(StringUtility.hasValue(filter.getUnitOfMeasure()))
			sql=sql+"AND CR.UNIT_OF_MEASURE = '" + filter.getUnitOfMeasure()+ "' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY"))
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			if(filter.getPostType().equals("SPECIAL"))
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " " +
							"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			if(filter.getPostType().equals("GUARANTEE"))
				sql = sql + "AND is_guarantee_posting = " + (isOracle ? 1 : true ) + " ";
			if(filter.getPostType().equals("ALL")){
				sql = sql + "AND (is_primary_posting = " + (isOracle ? 1 : true ) + " " +
						    "       OR " +
						    "     is_special_posting = " + (isOracle ? 1 : true ) + " " +
						    "     ) ";  
			}
	
		}
		
		return sql;
	}
	
	public static String getUpdateDuplicateTimePostDatesQuery(Boolean isOracle, TimePostQueryFilterImpl filter){
		String sql =
			"UPDATE ISW_ASSIGN_TIME_POST " +
			"SET POST_START_DATE = " + filter.getStartDate() + " " +
			", POST_STOP_DATE = " + filter.getStopDate() + " " +
			"WHERE ID IN (" +
			"SELECT ID " + 
			"FROM ISW_ASSIGN_TIME_POST ATP " +
			"     ,ISW_CONTRACTOR_RATE CR " +
			"     ,ISW_INCIDENT_ACCOUNT_CODE IAC " +
			"	  ,ISW_ACCOUNT_CODE AC " +
			"WHERE " +
			"IAC.ID = ATP.INCIDENT_ACCOUNT_CODE_ID " +
			"AND AC.ID = IAC.ACCOUNT_CODE_ID " +
			"AND ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + filter.getIncidentResourceId() + " "  + 
			"  ) "; 
		
			if(LongUtility.hasValue(filter.getAssignmentTimePostId()))
					sql=sql+"AND ATP.ID != " + filter.getAssignmentTimePostId() + " " ;
			sql=sql+ ") "  ;

		if(StringUtility.hasValue(filter.getAccountingCode())){
			// dan 6/20/2013 defect 3093 sql=sql+"AND AC.ACCOUNT_CODE = '" + filter.getAccountingCode()+ "' ";
		}
		
		if(filter.getIncludeTime()){
			sql=sql+"AND ( " +
			"     to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         <= " + 
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"     and " +
			"     to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')  " +
			"         > " +
			"     to_timestamp('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS') " +
			"    ) " ;
		}else{
			sql=sql+"AND ( " +
			"     to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         <= " + 
			"     to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"     and " +
			"     to_date(to_char(post_stop_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			"         > " +
			"           to_date('"+DateUtil.toDateString(filter.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			"    ) " ;
		}
		
		sql=sql+"AND ref_contractor_rate_id is not null ";
		sql=sql+"AND CR.id = ref_contractor_rate_id ";
		
		if(StringUtility.hasValue(filter.getUnitOfMeasure()))
			sql=sql+"AND CR.UNIT_OF_MEASURE = '" + filter.getUnitOfMeasure()+ "' ";
		
		if(filter.getInvoiceOnly()){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
			
		if(StringUtility.hasValue(filter.getPostType())){
			if(filter.getPostType().equals("PRIMARY"))
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " +
					 "AND is_special_posting = " + (isOracle ? 0 : false) + " ";
			if(filter.getPostType().equals("SPECIAL"))
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " " +
							"AND is_primary_posting = "  + (isOracle ? 0 : false) + " ";
			if(filter.getPostType().equals("GUARANTEE"))
				sql = sql + "AND is_guarantee_posting = " + (isOracle ? 1 : true ) + " ";
			if(filter.getPostType().equals("ALL")){
				sql = sql + "AND (is_primary_posting = " + (isOracle ? 1 : true ) + " " +
						    "       OR " +
						    "     is_special_posting = " + (isOracle ? 1 : true ) + " " +
						    "     ) ";  
			}
	
		}
		
		sql=sql+" ) ";
		return sql;
	}

	public static String getDuplicateDailyPostQuery(Boolean isOracle, Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startDate, Date endDate, String postType) {
		String sql = 
			"SELECT to_char(post_start_date, 'MM/DD/YYYY') FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
			"AND ( " +
			"to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			" >= " + 
			"to_date('"+DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			" and " +
			"to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			" <= " +
			"to_date('"+DateUtil.toDateString(endDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			" ) " 
			;
		sql=sql+"AND ref_contractor_rate_id is not null ";
		if(invoiceOnly){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
		
		if(StringUtility.hasValue(postType)){
			if(postType.equals("PRIMARY"))
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " " +
					 "AND is_guarantee_posting = " + (isOracle ? 0 : false ) + " " ;
			if(postType.equals("SPECIAL"))
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
			if(postType.equals("GUARANTEE"))
				sql = sql + "AND is_guarantee_posting = " + (isOracle ? 1 : true ) + " ";
		}
		return sql;
	}

	public static String getUpdateDuplicateDailyPostQuery(Boolean isOracle, Long timePostId,Long incidentResourceId, Boolean invoiceOnly, Date startDate, Date endDate, String postType) {
		String sql = 
			"UPDATE ISW_ASSIGN_TIME_POST " +
			"SET  " +
			"  KIND_ID = NULL " +
			", RATE_TYPE = null " +
			", RATE_AMOUNT = 0 " +
			", QUANTITY = 0 " +
			", UNIT_OF_MEASURE = null " +
			", SPECIAL_PAY_ID = null " +
			", REF_CONTRACTOR_ID = null " +
			", REF_CONTRACTOR_RATE_ID = null " +
			",GUARANTEE_AMOUNT=0 " +
			",is_guarantee_posting= " + (isOracle ? 0 : false ) + " " +
			",is_special_posting= " + (isOracle ? 0 : false ) + " " +
			",rate_class_rate_id=null " +
			"WHERE " +
			"ASSIGNMENT_TIME_ID IN " +
			"( " +
			"  SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN " +
			"  ( " +
			"     SELECT a.id FROM " +
			"           ISW_ASSIGNMENT a " +
			"           ,ISW_WORK_PERIOD_ASSIGNMENT wpa " + 
			"           ,ISW_WORK_PERIOD wp " + 
			"     WHERE a.id = wpa.assignment_id " + 
			"     AND wpa.work_period_id = wp.id " + 
			"     AND wp.incident_resource_id = " + incidentResourceId + " "  + 
			"  ) "; 
			if(LongUtility.hasValue(timePostId)){
				sql=sql+"AND ID != " + timePostId + " " ;
			}
		sql=sql+ ") "  +
			"AND ( " +
			"to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			" >= " + 
			"to_date('"+DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			" and " +
			"to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY')  " +
			" <= " +
			"to_date('"+DateUtil.toDateString(endDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
			" ) " 
			;
		if(invoiceOnly){
			sql = sql + "AND ATP.ID IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) ";
		}else{
			sql = sql + "AND ( " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
						"OR " +
						"ATP.ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE WHERE TIME_INVOICE_ID IN (SELECT ID FROM ISW_TIME_INVOICE WHERE DELETED_DATE IS NULL ) ) "  +
						") ";
		}
		
		if(StringUtility.hasValue(postType)){
			if(postType.equals("PRIMARY"))
				sql = sql + "AND is_primary_posting = " + (isOracle ? 1 : true ) + " ";
			if(postType.equals("SPECIAL"))
				sql = sql + "AND is_special_posting = " + (isOracle ? 1 : true ) + " ";
		}
		return sql;
	}
	
	/**
	 * @param incidentResourceId
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	public static String getDeleteTimePostsBeingReplaced(Long incidentResourceId, Date startTime, Date stopTime) {
        String sql = "DELETE FROM ISW_ASSIGN_TIME_POST ATP WHERE " +
        			 "ASSIGNMENT_TIME_ID IN  " +
        			 "( " +  
        			 "    SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN  " +
        			 "    (  " +
        			 "      SELECT a.id FROM  " +
                     "      ISW_ASSIGNMENT a  " +
                     "        ,ISW_WORK_PERIOD_ASSIGNMENT wpa  " +
                     "        ,ISW_WORK_PERIOD wp  " +
                     "      WHERE a.id = wpa.assignment_id  " +
                     "      AND wpa.work_period_id = wp.id " +   
                     "      AND wp.incident_resource_id =  " + incidentResourceId + " " + 
                     "     ) "+  
                     ") " +   
                     " AND  " +
         			"to_date(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
        			" < " +
        			"to_date('"+DateUtil.toDateString(stopTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
        			" and " +
        			"to_date('"+DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
        			" < " +
        			"to_date(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
        			" AND ID NOT IN ( SELECT ASSIGN_TIME_POST_ID FROM ISW_ASSIGN_TIME_POST_INVOICE ) " +
        			" AND ID != ( " +
        			"             SELECT MAX(ID) FROM ISW_ASSIGN_TIME_POST WHERE " +
        			"              ASSIGNMENT_TIME_ID IN  " +
        			"             (  " +
        			"                SELECT ID FROM ISW_ASSIGNMENT_TIME WHERE ASSIGNMENT_ID IN  " +
        			" 				 ( " +  
        			"                  SELECT a.id FROM  " +
                    "                  ISW_ASSIGNMENT a  " +
                    "                  ,ISW_WORK_PERIOD_ASSIGNMENT wpa  " +
                    "                  ,ISW_WORK_PERIOD wp   " +
                    "                  WHERE a.id = wpa.assignment_id  " + 
                    "                  AND wpa.work_period_id = wp.id " +  
                    "                  AND wp.incident_resource_id =  " + incidentResourceId + " " +
                    "				 ) " +  
                    "               ) " +  
                    ") ";

        return sql;
	}
	
	public static String getIncidentResourceIdQuery(Long assignmentTimeId) {
		String sql = 
			"select incident_resource_id from isw_work_period " +
			"where id in ( " +
			"    select work_period_id from isw_work_period_assignment " + 
			"    where assignment_id in ( " +
			"      	select assignment_id from isw_assignment_time " +
			"       where id = " + assignmentTimeId + " " +
			"    ) " +
			") ";
		
		return sql;
	}
	
	public static String getChildUniqueAcctCodeIdsByDate(Long irId, Long excludeAcctCodeId, Date dt) {
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct(atp.incident_account_code_id) ")
			.append("from isw_assign_time_post atp ")
		    .append(", isw_assignment_time at ")
		    .append(", isw_assignment a ")
		    .append(", isw_work_period_assignment wpa ")
		    .append(", isw_work_period wp ")
		    .append("where to_date(to_char(post_start_date, 'MM/DD/YYYY'),'MM/DD/YYYY') ")
		    .append(" = to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ")
		    .append("and atp.incident_account_code_id != "+excludeAcctCodeId + " ")
		    .append("and atp.assignment_time_id = at.id ")
		    .append("and at.assignment_id = a.id ")
		    .append("and a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		    .append("and wp.incident_resource_id in ( ")
		    .append(" select id from isw_incident_resource where resource_id in (")
		    .append("    select id from isw_resource where parent_resource_id in ( ")
		    .append("         select resource_id from isw_incident_resource where id = " + irId + " ")
		    .append("     ) ")
		    .append(" ) ")
		    .append(") ");
		
		return sql.toString();
	}
	
	/**
	 * Generates SQL Query used to generate data for the Vendor Resource Summary Report as
	 * required by UC R-6.07
	 * @param filter
	 * @param isOracle
	 * @return SQL Query String
	 */
	public static String getVendorResourceSummaryReport(VendorResourceSummaryReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.request_number AS requestNumber,")
			.append(" CASE ")
			.append(" WHEN r.is_person = " + (isOracle ? 0 : false) + " THEN r.resource_name")
			.append(" ELSE r.last_name || ', ' || r.first_name")
			.append(" END")
			.append(" AS resourceName, ")
			.append(" kind.code AS itemCode,")
			.append(" a.assignment_status AS status,")
			.append(" c.name || ' ' || ca.agreement_number AS vendorAgreementNumber,")
			.append(" cpi.vin_name AS uniqueName,")
			.append(" TO_DATE(TO_CHAR(cpi.hired_date, 'MM/DD/YYYY'),'MM/DD/YYYY') AS hireDate,")
			.append(" TO_CHAR(cpi.hired_date, 'HH24MI') AS hireTime,")
			.append(" TO_DATE(TO_CHAR(wp.dm_release_date, 'MM/DD/YYYY'),'MM/DD/YYYY') AS releaseDate,")
			.append(" TO_CHAR(wp.dm_release_date, 'HH24MI') AS releaseTime")
			.append(" FROM isw_incident_resource ir JOIN isw_resource r ON (ir.resource_id = r.id)")
			.append(" JOIN isw_work_period wp ON (wp.incident_resource_id = ir.id)")
			.append(" JOIN isw_work_period_assignment wpa ON (wpa.work_period_id = wp.id)")
			.append(" JOIN isw_assignment a ON (a.id = wpa.assignment_id)")
			.append(" JOIN iswl_kind kind ON (kind.id = a.kind_id)")
			.append(" JOIN isw_assignment_time ast on (ast.assignment_id = a.id)")
			.append(" JOIN isw_contr_payment_info cpi ON (cpi.assignment_time_id = ast.id)")
			.append(" LEFT OUTER JOIN isw_contractor_agreement ca ON (ca.id = cpi.contractor_agreement_id)")
			.append(" LEFT OUTER JOIN isw_contractor c ON (c.id = ca.contractor_id)")
			.append(" WHERE");
		
		if (filter.getIncidentId() > 0) {
			sql.append(" ir.incident_id = " + filter.getIncidentId());
		} else if (filter.getIncidentGroupId() > 0) {
			sql.append(" ir.incident_id IN (")
				.append(" SELECT igi.incident_id from isw_incident_group_incident igi")
				.append(" WHERE igi.incident_group_id = " + filter.getIncidentGroupId() + ")");
		}
		
		sql.append(" AND r.is_contracted = " + (isOracle ? 1 : true) + " ")
			.append(" AND a.end_date IS NULL"); // This is to ensure that only the last assignment is used
		
		// Apply Exclude Demobed Resources clause
		if(filter.isExcludeDemob()) {
			sql.append(" AND a.assignment_status != 'D'");
		}
		
		// Apply the grouping and sorting clauses
		String groupSort = null, normalSort = null;
		
		if(filter.isGroupByItemCode()) {
			groupSort = "itemCode";
		} else if(filter.isGroupByVendor()) {
			groupSort = "vendorAgreementNumber";
		} else if(filter.isGroupByHireDate()) {
			groupSort = "hireDate, hireTime";
		}
		
		if(filter.isSortByItemCode()) {
			normalSort = "itemCode";
		} else if(filter.isSortByVendor()) {
			normalSort = "vendorAgreementNumber";
		} else if(filter.isSortByHireDate()){
			normalSort = "hireDate, hireTime";
		} else {
			normalSort = "requestNumber";
		}
		
		sql.append(" ORDER BY ");
		if(groupSort != null) {
			sql.append(groupSort);
			if(!normalSort.equals(groupSort)){
				sql.append(", ");
				sql.append(normalSort);
			}
		} else {
			sql.append(normalSort);
		}
				
		//Test print to console
		//System.out.println("\n\n\ngetVendorResourceSummaryReportQuery:\n\n" + sql.toString() + "\n\n\n\n");
		
		return sql.toString();
	}
	
	/**
	 * Generates SQL Query used to generate data for the Missing Days Of Postings Report as
	 * required by UC R-6.05
	 * @param filter
	 * @param isOracle
	 * @return SQL Query String
	 */
	public static String getDaysOfPostingsReport(MissingDaysOfPostingsReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("SELECT a.request_number AS requestNumber ")
		   .append(",(case ")
		   .append("when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		   .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		   .append("end) as resourceName ")
		   .append(",atp.post_start_date AS postingDate ")
		   .append(",TO_CHAR(atp.post_start_date, 'MM/DD/YYYY') AS postingDateChar ")
		   .append(",TO_CHAR(wp.ci_check_in_date, 'MM/DD/YYYY') AS checkInDateChar ")
		   .append(",ISWL_AGENCY.agency_name AS agencyName ")
		   .append("FROM ISW_ASSIGN_TIME_POST atp ")
		   .append(", ISW_RESOURCE r LEFT OUTER JOIN ISWL_AGENCY ON (r.agency_id = ISWL_AGENCY.id) ")
		   .append(", ISW_ASSIGNMENT a ")
		   .append(", ISW_ASSIGNMENT_TIME at ")
		   .append(", ISW_WORK_PERIOD wp ")
		   .append(", ISW_WORK_PERIOD_ASSIGNMENT wpa ")
		   .append(", ISW_INCIDENT_RESOURCE ir ");
		   		
    	sql.append("WHERE ir.incident_id = " + incidentId)
		   .append("  AND r.id = ir.resource_id ")
		   .append("AND wp.incident_resource_id = ir.id ")
		   .append("AND wpa.work_period_id = wp.id ")
		   .append("AND a.ID = wpa.ASSIGNMENT_ID ")
		   .append("AND at.ASSIGNMENT_ID = a.id ")
		   .append("AND atp.assignment_time_id = at.id ")
		   .append("AND atp.post_start_date >= TO_DATE('" + filter.getStartDateChar() + "', 'MM/DD/YYYY') ")
		   .append("AND atp.post_start_date < TO_DATE('" + filter.getEndDateDbChar() + "', 'MM/DD/YYYY') ");
    	       	   
    	if (filter.isExcludeDemobReassigned()) {
			sql.append("AND a.assignment_status not in ('D','R') ");
			/*
			sql.append("AND a.assignment_status != 'D' ")
			   .append("AND a.assignment_status != 'R' ");
			*/
		}	
    	
    	if (filter.getPersonnelOrVendor().equals("Personnel")) {
			sql.append(" AND r.is_contracted = " + (isOracle ? 0 : false) );
			
			if (filter.getAgencyName() != null) 
				if (filter.getAgencyName().length() > 0) 
				{
					String agencyName = filter.getAgencyName().replaceAll("'", "''");
					sql.append(" AND ISWL_AGENCY.agency_name = '" + agencyName + "' ");
				}
			
            /*
			if (filter.getEmploymentCode().equals("AD") ) {
				sql.append(" AND atp.employment_type = 'AD' ");
			}
			if (filter.getEmploymentCode().equals("FED") ) {
				sql.append(" AND atp.employment_type = 'FED' ");
			}
			if (filter.getEmploymentCode().equals("OTHER") ) {
				sql.append(" AND atp.employment_type != 'AD' ")
				   .append(" AND atp.employment_type != 'FED' ");
			}
			*/
			if (!filter.getEmploymentCode().equals("ALL") ) {
				
			    if (filter.getEmploymentCode().equals("AD") ) {
			    	sql.append(" AND atp.employment_type = 'AD' ");
			    }
				else if (filter.getEmploymentCode().equals("FED") ) {
					     sql.append(" AND atp.employment_type = 'FED' ");
				}    
				else if (filter.getEmploymentCode().equals("OTHER") ) {
					     sql.append(" AND atp.employment_type != 'AD' ")
					        .append(" AND atp.employment_type != 'FED' ");
				} 
				else if (filter.getEmploymentCode().length() > 0) {
					     sql.append(" AND atp.employment_type = '" + filter.getEmploymentCode() +"' ");
				}
			}	
			
			if (filter.getNoneOrAgency().equals("Agency")) {
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Request Number"))
				    sql.append(" order by ISWL_AGENCY.agency_name, a.request_number, post_start_date asc ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Resource Name"))
				    sql.append(" order by ISWL_AGENCY.agency_name, resourceName, post_start_date asc ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Employment Code"))
				    sql.append(" order by ISWL_AGENCY.agency_name, atp.employment_type, post_start_date asc ");
			}
			else {
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Request Number"))
				    sql.append(" order by a.request_number, post_start_date asc ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Resource Name"))
				    sql.append(" order by resourceName, post_start_date asc ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Employment Code"))
				    sql.append(" order by atp.employment_type, post_start_date asc ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Agency"))
				    sql.append(" order by ISWL_AGENCY.agency_name, post_start_date asc ");
			}
		}
		else {
			sql.append(" AND r.is_contracted = " + (isOracle ? 1 : true) );
			if (filter.getReqNumOrResName().equals("Request Number")) {
				sql.append(" order by a.request_number, post_start_date asc ");
			}
			else {
				sql.append(" order by resourceName, post_start_date asc ");
			}
		}
    	    	
		return sql.toString();
	}		
			
	/**
	 * Generates SQL Query used to generate data for the Missing Days Of Postings Report as
	 * required by UC R-6.05
	 * @param filter
	 * @param isOracle
	 * @return SQL Query String
	 */
	public static String getUniqueDaysOfPostingsReport(MissingDaysOfPostingsReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("SELECT a.request_number AS requestNumber ")
		   .append(",(case ")
		   .append("when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		   .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		   .append("end) as resourceName ")
		   .append(",TO_CHAR(wp.ci_check_in_date, 'MM/DD/YYYY') AS checkInDateChar ")
		   .append(",TO_CHAR(MIN(atp.post_start_date), 'MM/DD/YYYY') AS firstPostingDateChar ")
		   .append(",ISWL_AGENCY.agency_name AS agencyName ")
		   .append("FROM ISW_ASSIGN_TIME_POST atp ")
		   .append(", ISW_RESOURCE r LEFT OUTER JOIN ISWL_AGENCY ON (r.agency_id = ISWL_AGENCY.id) ")
		   .append(", ISW_ASSIGNMENT a ")
		   .append(", ISW_ASSIGNMENT_TIME at ")
		   .append(", ISW_WORK_PERIOD wp ")
		   .append(", ISW_WORK_PERIOD_ASSIGNMENT wpa ")
		   .append(", ISW_INCIDENT_RESOURCE ir ");
		   		
    	sql.append("WHERE ir.incident_id = " + incidentId)
		   .append("  AND r.id = ir.resource_id ")
		   .append("AND wp.incident_resource_id = ir.id ")
		   .append("AND wpa.work_period_id = wp.id ")
		   .append("AND a.ID = wpa.ASSIGNMENT_ID ")
		   .append("AND at.ASSIGNMENT_ID = a.id ")
		   .append("AND atp.assignment_time_id = at.id ");
    	       	    	
    	if (filter.isExcludeDemobReassigned()) {
			sql.append("AND a.assignment_status != 'D' ")
			   .append("AND a.assignment_status != 'R' ");
		}	
    	
		if (filter.getPersonnelOrVendor().equals("Personnel")) {
			sql.append(" AND r.is_contracted = " + (isOracle ? 0 : false) );
			
			if (filter.getAgencyName() != null) 
				if (filter.getAgencyName().length() > 0) 
				{
					String agencyName = filter.getAgencyName().replaceAll("'", "''");
					sql.append(" AND ISWL_AGENCY.agency_name = '" + agencyName + "' ");
				}
			
			/*
			if (filter.getEmploymentCode().equals("AD") ) {
				sql.append(" AND atp.employment_type = 'AD' ");
			}
			if (filter.getEmploymentCode().equals("FED") ) {
				sql.append(" AND atp.employment_type = 'FED' ");
			}
			if (filter.getEmploymentCode().equals("OTHER") ) {
				sql.append(" AND atp.employment_type != 'AD' ")
				   .append(" AND atp.employment_type != 'FED' ");
			}
			*/
			
			if (!filter.getEmploymentCode().equals("ALL") ) {
				
			    if (filter.getEmploymentCode().equals("AD") ) {
			    	sql.append(" AND atp.employment_type = 'AD' ");
			    }
				else if (filter.getEmploymentCode().equals("FED") ) {
					     sql.append(" AND atp.employment_type = 'FED' ");
				}    
				else if (filter.getEmploymentCode().equals("OTHER") ) {
					     sql.append(" AND atp.employment_type != 'AD' ")
					        .append(" AND atp.employment_type != 'FED' ");
				} 
				else if (filter.getEmploymentCode().length() > 0) {
					     sql.append(" AND atp.employment_type = '" + filter.getEmploymentCode() +"' ");
				}
			}	
			
			sql.append(" group by a.request_number ")
			   .append(",(case ")
			   .append("when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
			   .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
			   .append("end) ")
			   .append(",TO_CHAR(wp.ci_check_in_date, 'MM/DD/YYYY') ")
			   .append(",atp.employment_type, ISWL_AGENCY.agency_name ");
									
						
			if (filter.getNoneOrAgency().equals("Agency")) {
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Request Number")) 
				    sql.append(" order by ISWL_AGENCY.agency_name, a.request_number ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Resource Name"))
				    sql.append(" order by ISWL_AGENCY.agency_name, resourceName ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Employment Code"))
				    sql.append(" order by ISWL_AGENCY.agency_name, atp.employment_type ");
			}
			else {
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Request Number")) 
				    sql.append(" order by a.request_number ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Resource Name"))
				    sql.append(" order by resourceName ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Employment Code"))
				    sql.append(" order by atp.employment_type ");
				if (filter.getReqNumOrResNameOrAgencyOrCode().equals("Agency")) 
				    sql.append(" order by ISWL_AGENCY.agency_name ");
			}
		}
		else {
			sql.append(" AND r.is_contracted = " + (isOracle ? 1 : true) );
			
			//column alias is not allowed in Oracle: sql.append(" group by requestNumber, resourceName, checkInDateChar, atp.employment_type, ISWL_AGENCY.agency_name ");
			sql.append(" group by a.request_number ")
			   .append(",(case ")
			   .append("when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
			   .append("when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
			   .append("end) ")
			   .append(",TO_CHAR(wp.ci_check_in_date, 'MM/DD/YYYY') ")
			   .append(",atp.employment_type, ISWL_AGENCY.agency_name ");
			
			if (filter.getReqNumOrResName().equals("Request Number")) {
				sql.append(" order by a.request_number ");
			}
			else {
				sql.append(" order by resourceName ");
			}
		}
						
		return sql.toString();
	}
	
	public static String getCrewTimePostingIds(TimePostQueryFilterImpl f) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID ")
			.append("FROM ISW_ASSIGN_TIME_POST ")
			.append("WHERE assignment_time_id in (")
			.append("   SELECT distinct(ID) FROM ISW_ASSIGNMENT_TIME ")
			.append("   WHERE ASSIGNMENT_ID IN (")
			.append("		SELECT distinct(ASSIGNMENT_ID) ")
			.append("       FROM ISW_WORK_PERIOD_ASSIGNMENT ")
			.append("       WHERE WORK_PERIOD_ID IN (")
			.append("       	SELECT ID FROM ISW_WORK_PERIOD WHERE INCIDENT_RESOURCE_ID IN (:irids) ")
			.append("       ) ")
			.append("   )")
			.append(") ");
		
		if(DateUtil.hasValue(f.getStartDate())){
			sql.append("AND ")
         	.append("to_timestamp(to_char(post_start_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
        			" = " +
        			"to_timestamp('"+DateUtil.toDateString(f.getStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') ");
		}
		
		if(DateUtil.hasValue(f.getStopDate())){
			sql.append("AND ")
	     	.append("to_timestamp(to_char(post_stop_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
	    			" = " +
	    			"to_timestamp('"+DateUtil.toDateString(f.getStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') ");
		}

		if(LongUtility.hasValue(f.getExcludeAssignmentTimePostId())){
			sql.append("AND id != " + f.getExcludeAssignmentTimePostId() + " ");
		}

		if(StringUtility.hasValue(f.getSpecialPay())){
			sql.append("AND special_pay_id = (select id from iswl_special_pay where code = '"+f.getSpecialPay()+"') ");
		}
		
		return sql.toString();
	}
	
	public static String getLatestTimePostingDateByResourceIdQuery(Long resourceId) {
		StringBuffer sql = new StringBuffer();

		sql.append("select max(atp.post_start_date) ")
			.append("from isw_assign_time_post atp ")
			.append("     , isw_work_period wp ")
			.append(", isw_work_period_assignment wpa ")
			.append(", isw_assignment_time at ")
			.append(", isw_assignment a ")
			.append("where ATP.ASSIGNMENT_TIME_ID=at.id ")
			.append("and AT.ASSIGNMENT_ID=a.id ")
			.append("and wpa.assignment_id=a.id ")
			.append("and wp.id = wpa.work_period_id ")
			.append("and wp.incident_resource_id = (select id from isw_incident_resource where resource_id = "+resourceId+" ) ")
			.append("and (" )
			.append("    atp.id NOT IN (select  assign_time_post_id from isw_assign_time_post_invoice) ")
			.append("    or ")
			.append("    atp.id not in (select assign_time_post_id from isw_assign_time_post_invoice where time_invoice_id in (select id from isw_time_invoice where deleted_date is null ) ) ")
			.append(") ");
		
		return sql.toString();
	}
	
	public static String getLatestTimePostingDateForParentQuery(Long irId) {
		StringBuffer sql = new StringBuffer();

		sql.append("select max(atp.post_start_date) ")
			.append("from isw_assign_time_post atp ")
			.append("     , isw_work_period wp ")
			.append(", isw_work_period_assignment wpa ")
			.append(", isw_assignment_time at ")
			.append(", isw_assignment a ")
			.append("where ATP.ASSIGNMENT_TIME_ID=at.id ")
			.append("and AT.ASSIGNMENT_ID=a.id ")
			.append("and atp.deleted_date is null ")
			.append("and wpa.assignment_id=a.id ")
			.append("and wp.id = wpa.work_period_id ")
			.append("and wp.incident_resource_id in (")
			.append("   select id from isw_incident_resource where resource_id in (")
			.append("       select id from isw_resource where parent_resource_id=(")
			.append("           select resource_id from isw_incident_resource where id = " + irId + ") ")
			.append("   )")
			.append(") ")
			.append("and (" )
			.append("    atp.id NOT IN (select  assign_time_post_id from isw_assign_time_post_invoice) ")
			.append("    or ")
			.append("    atp.id not in (select assign_time_post_id from isw_assign_time_post_invoice where time_invoice_id in (select id from isw_time_invoice where deleted_date is null ) ) ")
			.append(") ");
		
		return sql.toString();
	}
	
	/**
	 * Generates SQL Query used to generate data for the Crew Roster Report as
	 * required by UC R-6.06
	 * @param filter
	 * @param isOracle
	 * @return SQL Query String
	 */
	public static String getCrewRosterReportQuery(TimeReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		Long resourceId = filter.getResourceId();
		if(resourceId == null || resourceId <=0) return null;
		
		sql.append("SELECT r.id AS resourceId, a.request_number AS requestNumber, ")
			.append(" CASE")
			.append(" WHEN r.id = " + resourceId + " THEN 1")
			.append(" ELSE 0")
			.append(" END AS parent,")
			.append(" CASE ")
			.append(" WHEN r.is_person = " + (isOracle ? 0 : false) + " THEN r.resource_name")
			.append(" ELSE r.last_name || ', ' || r.first_name")
			.append(" END AS resourceName ,")
			.append(" k.code AS itemCode,")
			.append(" wp.ci_check_in_date AS checkInDate,")
			.append(" r.employment_type AS employmentType,")
			.append(" CASE ")
			.append(" WHEN a.is_training = " + (isOracle ? 1 : true) + " THEN 'Trainee' ")
			.append(" ELSE '' ")
			.append(" END AS traineeStatus ,")
			.append(" a.assignment_status AS status, ")
			.append(" rorg.unit_code AS unitId,")
			.append(" wp.ci_first_work_date AS firstWorkDay,")
			.append(" wp.ci_length_at_assignment AS lengthOfAssignment,")
			.append(" CASE ")
			.append(" WHEN (NOT wp.ci_first_work_date IS NULL AND NOT wp.ci_length_at_assignment IS NULL AND wp.ci_length_at_assignment > 0)");
			
		if(isOracle){
		    sql.append(" THEN (wp.ci_first_work_date + (wp.ci_length_at_assignment-1)) ");
	    }else{
		    sql.append( "THEN (wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment - interval '1 day') ");
	    }
			
		sql.append(" END AS lastWorkDay,")
			.append(" wp.dm_release_date AS actualReleaseDate ")
			.append(" FROM isw_incident i ")
			.append(" JOIN isw_incident_resource ir ON i.id = ir.incident_id ")
			.append(" JOIN isw_resource r ON ir.resource_id = r.id ")
			.append(" LEFT JOIN isw_organization rorg ON r.organization_id = rorg.id ")
			.append(" LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id ")
			.append(" LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id ")
			.append(" LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id ")
			.append(" LEFT JOIN iswl_kind k ON a.kind_id = k.id ")
			.append(" WHERE");

		if (filter.getIncidentId() > 0) {
			sql.append(" ir.incident_id = " + filter.getIncidentId());
		} else if (filter.getIncidentGroupId() > 0) {
			sql.append(" ir.incident_id IN (")
				.append(" SELECT igi.incident_id from isw_incident_group_incident igi")
				.append(" WHERE igi.incident_group_id = " + filter.getIncidentGroupId() + ")");
		}
		
		//Not returning the correct data
		sql.append(" AND (r.id = " + resourceId + " OR r.parent_resource_id = " + resourceId + ") ");
		//sql.append(" AND (r.id = (SELECT resource_id from isw_incident_resource where id = " + resourceId + ") OR r.parent_resource_id = (SELECT resource_id from isw_incident_resource where id = " + resourceId + ")) ");
			    
		// Apply Exclude Demobed Resources clause
		if(filter.isExcludeDemob()) {
			sql.append(" AND a.assignment_status != 'D'");
		}
		
		sql.append(" ORDER BY parent desc, sortrequestnumber(a.request_number), resourceName");
				
		//Test print to console
		//System.out.println("\n\n\ngetCrewRosterReportQuery:\n\n" + sql.toString() + "\n\n\n\n");
		
		return sql.toString();
	}
	
	public static String getNonInvoicedUniqueTimePostDatesQuery(Long incidentResourceId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct(atp.post_start_date) ")
			.append("from isw_assign_time_post atp ")
		    .append(" ,isw_assignment_time at ")
		    .append(" ,isw_assignment a  ")
		    .append(" ,isw_work_period_assignment wpa ")
		    .append(" , isw_work_period wp ")
			.append("where wp.incident_resource_id = " + incidentResourceId + " ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ") 
			.append("and at.assignment_id = a.id ")
			.append("and atp.assignment_time_id = at.id ")
			.append("and atp.id NOT IN (select  assign_time_post_id from isw_assign_time_post_invoice where time_invoice_id in (select id from isw_time_invoice where deleted_date is null ) ) ") 
			.append("order by post_start_date asc ");		

		return sql.toString();
	}

	/**
	 * Public static method to return query for the checkbox tree component used in 
	 * Personnel Time Report screen
	 * 
	 *  Business rule: Select agency, fax number, resource name for every resource in this
	 *  incident or incident group that satisfies the following: 
	 *  	A fax number is defined for the Resource in the e-ISuite System.
	 *  	The Employment Type is FED or OTHER
	 *  	The Resource has an Agency defined.
	 *  	The Resource does not have a D (Demobed) Status.
	 * @param incidentOrGroupId
	 * @param isIncidentGroup
	 * @return SQL query string
	 */
	public static String getAgencyTreeQueryForPersonnelTimeRep(
			Long incidentOrGroupId, boolean isIncidentGroup, Boolean isOracle) {

		StringBuffer sql = new StringBuffer();
		
		sql.append("select agency.agency_code as agencyCode, ")
			.append(" agency.id as agencyId, ")
			.append(" at.fax as faxNumber, ")
			.append(" case when not r.resource_name is null ")
			.append("when r.is_person = " + (isOracle ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
			.append("else ")
			.append("r.resource_name ")
			.append("end as resourceName, ")
			.append(" r.id as resourceId ")
			.append(" from isw_resource r, ")
			.append(" isw_incident i, ")
			.append(" isw_incident_resource ir, ")
			.append(" isw_assignment a, ")
			.append(" isw_assignment_time at, ")
			.append(" isw_work_period wp, ")
			.append(" isw_work_period_assignment wpa, ")
			.append(" iswl_agency agency ")
			.append(" where ");
			
		if(!isIncidentGroup) {
			sql.append(" i.id = " + incidentOrGroupId);
		} else if (isIncidentGroup) {
			sql.append(" i.id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentOrGroupId + ")");
		}
			
		sql.append(" and ir.incident_id = i.id  ")
			.append(" and r.id = ir.resource_id  ")
			.append(" and r.is_person = " + (isOracle ? 1 : true))
			.append(" and r.agency_id is not null ")
			.append(" and agency.id = r.agency_id ")
			.append(" and wp.incident_resource_id = ir.id ")
			.append(" and wpa.work_period_id = wp.id  ")
			.append(" and a.id = wpa.assignment_id  ")
			.append(" and a.assignment_status != 'D' ")
			.append(" and at.assignment_id = a.id ")
			.append(" and (at.employment_type = 'FED' or at.employment_type = 'OTHER') ")
			.append(" and at.fax is not null ")
			.append(" order by agencyCode, faxNumber, resourceName"); 

		return sql.toString();
	}
	
	public static String getPostingsByAssignmentTimeId(Long assignmentTimeId){
		StringBuffer sql = new StringBuffer();
		sql.append("select atp.id as id ") 
	       .append(", atp.employment_type as employmentType ")
	       .append(", atp.assignment_time_id as assignmentTimeId ")
	       .append(", atp.post_start_date as postStartDate ")
	       .append(", atp.post_stop_date as postStopDate ")
	       .append(", sp.code as specialPayCode")
	       .append(", atp.other_rate otherRate ")
	       .append(", atp.quantity as quantity ")
	       .append(", atp.rate_type as rateType ")
	       .append(", atp.unit_of_measure as unitOfMeasure ")
	       .append(", atp.rate_amount as rateAmount ")
	       .append(", atp.guarantee_amount as guaranteeAmount ")
	       .append(", atp.incident_account_code_id as incidentAccountCodeId ")
	       .append(", atp.ref_contractor_rate_id as contractorRateId ")
	       .append(", atp.is_half_rate as halfRate ")
	       .append(", atp.is_primary_posting as primaryPosting ")
	       .append(", atp.is_special_posting as specialPosting ")
	       .append(", atp.is_guarantee_posting as guaranteePosting ")
	       .append(", atp.contractor_post_type as contractorPostType ")
	       .append("from isw_assign_time_post atp ")
	       .append("       left join iswl_special_pay sp on atp.special_pay_id = sp.id ")
	       .append("where atp.assignment_time_id = " + assignmentTimeId + " ");
			
		return sql.toString();
	}
	
	public static String getIncidentResourceTimePostDataQuery(Long irParentId, Date postDate,Boolean subsOnly,Boolean isOracle){
		String sql=
		"select ir.id as incidentResourceId " +
		"	, atp.id as assignTimePostId " +
		"	, atp.post_start_date as postStartDate " +
		"	, atp.post_stop_date as postStopDate " +
		"	, k.code as kindCode " +
		"	, atp.rate_type as rateType " +
		"	, atp.quantity as quantity " +
		"	, atp.rate_amount as rateAmount " +
		"	, atp.other_rate as otherRate " +
		"	, atp.employment_type as employmentType " +
		"	, ac.account_code as accountCode " +
		"	, atp.is_training as training " +
		"	, rcr.rate_classname as rateClassName " +
		"	, rcr.rate as rateClassRate " +
		"   , i.nbr as incidentNumber " +
		"   , iorg.unit_code as incidentUnitCode " +
		"   , i.incident_name as incidentName " +
		"   , atp.rtn_travel_start_only as returnTravelStartOnly " +
		"   , sp.code as specialPayCode " +
		"   , sp.id as specialPayId " +
		"   , rc.code as regionCode " +
		"   , atp.is_half_rate as halfRate " +
		"from isw_incident_resource ir " +
		"	, isw_resource r " +
		"	, isw_work_period wp " +
		"	, isw_work_period_assignment wpa " +
		"	, isw_assignment a " +
		"	, isw_assignment_time at " +
		"	, isw_assign_time_post atp " +
		"		left join isw_incident_account_code iac on atp.incident_account_code_id = iac.id " +
		"		left join isw_account_code ac on iac.account_code_id = ac.id " +
		"       left join iswl_region_code rc on ac.region_unit_id = rc.id " +
		"       left join isw_incident i on iac.incident_id = i.id " +
		"       left join isw_organization iorg on i.unit_id = iorg.id " +
		"		left join iswl_kind k on atp.kind_id = k.id " +
		"		left join iswl_rate_class_rate rcr on atp.rate_class_rate_id = rcr.id " +
		"       left join iswl_special_pay sp on atp.special_pay_id = sp.id ";
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
		"and atp.assignment_time_id = at.id "+
		"and atp.deleted_date is null " +
		"and to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
		"			< to_timestamp('"+DateUtil.toDateString(postDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
		"and atp.id not in ( " +
		"	select atpi.assign_time_post_id " +
		"	from isw_assign_time_post_invoice atpi " +
		"	     , isw_time_invoice ti " +
		"	where ti.id = atpi.time_invoice_id " +
		"	and ti.is_draft = " + (isOracle ? 0 : false) + " " +
		"	and ti.deleted_date is null " +
		"	and atpi.assign_time_post_id = atp.id " +
		") " +
		"order by atp.post_start_date asc ";
		return sql;
	}
}
