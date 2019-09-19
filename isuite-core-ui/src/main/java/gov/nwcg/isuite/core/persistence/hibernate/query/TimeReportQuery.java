package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TimeReportQuery {
/*
select tbl1.atpId 
        ,tbl1.atpAssignmentTimeId 
        ,tbl1.atpStartDate 
        ,tbl1.atpStopDate 
        ,tbl1.atpQuantity 
        ,tbl1.atpNextId
        ,tbl1.atpNextStartDate
        , tbl1.atpPreviousId
        ,(cast(get_time_diff_in_minutes (tbl1.atpStopDate, tbl1.atpNextStartDate) as numeric(22,2))/ 60) as restBeforeNextStart
        , tbl1.lastName 
        , tbl1.firstName
        , tbl1.itemCode 
        , tbl1.groupCategory
        , tbl1.subGroupCategory
        , tbl1.sectionCode 
        , tbl1.sectionName 
        , tbl1.requestNumber 
        , tbl1.resourceId
from (
	select atp.id as atpId
		, atp.assignment_time_id as atpAssignmentTimeId
		, atp.post_start_date as atpStartDate
		, atp.post_stop_date as atpStopDate
		, atp.quantity as atpQuantity
		,(
			select min(id)
			from isw_assign_time_post
			where assignment_time_id = atp.assignment_time_id
			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') 
				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')
			and post_start_date = (
			select min(post_start_date)
			from isw_assign_time_post
			where assignment_time_id = atp.assignment_time_id
			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') 
				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')
			)
		) as atpNextId
		,(
			select min(post_start_date)
			from isw_assign_time_post
			where assignment_time_id = atp.assignment_time_id
			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') 
				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')
		) as atpNextStartDate
		,(
			select id
			from isw_assign_time_post
			where assignment_time_id = atp.assignment_time_id
			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') 
				< to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')
			and post_start_date = (
			select max(post_start_date)
			from isw_assign_time_post
			where assignment_time_id = atp.assignment_time_id
			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') 
				< to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')
			)
		) as atpPreviousId
		, r.last_name as lastName
		, r.first_name firstName
		, k.code as itemCode
		, gc.code as groupCategory
		, sgc.code as subGroupCategory
		, d.code as sectionCode
		, d.description as sectionName
		, a.request_number as requestNumber
		, r.id as resourceId
	from isw_assign_time_post atp
		, isw_assignment_time at
		, isw_assignment a
		, iswl_kind k
		, iswl_department d
		, iswl_group_category gc
		, iswl_sub_group_category sgc
		, isw_work_period_assignment wpa
		, isw_work_period wp
		, isw_incident_resource ir
		, isw_resource r
	where ir.incident_id = 10000 -- incident id filter
	and to_date(to_char(atp.post_start_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('06/22/2015','MM/DD/YYYY') -- start date filter
	and to_date(to_char(atp.post_stop_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('06/28/2015','MM/DD/YYYY') -- stop date filter
	--and r.id = 12164 -- resource id filter
	-- and ir.id = ? -- incident resource id filter
	--and atp.assignment_time_id in (12152)
	--and d.code in ('L','O','?') -- section filter
	and a.assignment_status not in ('D','R') -- exclude demob/reassigned
	and at.id = atp.assignment_time_id
	and a.id = at.assignment_id
	and k.id = a.kind_id
	and d.id = k.department_id
	and gc.id = k.group_category_id
	and sgc.id = k.sub_group_category_id
	and wpa.assignment_id = a.id
	and wp.id = wpa.work_period_id
	and ir.id = wp.incident_resource_id
	and r.id = ir.resource_id
	and r.is_person = true
	order by atp.post_start_date
) tbl1
order by tbl1.atpAssignmentTimeId ,tbl1.atpstartdate
;
 * 
 * 
 */
	
	public static String getTimeReportDataQuery(TimeReport2FilterImpl filter, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select tbl1.atpId ")
           .append(",tbl1.atpAssignmentTimeId  ")
           .append(",tbl1.atpStartDate  ")
           .append(",tbl1.atpStopDate  ")
           .append(",tbl1.atpQuantity  ")
           .append(",tbl1.atpNextId ")
           .append(",tbl1.atpNextStartDate ")
           .append(", tbl1.atpPreviousId ")
           .append(",(cast(get_time_diff_in_minutes (tbl1.atpStopDate, tbl1.atpNextStartDate) as numeric(22,2))/ 60) as restBeforeNextStart ")
           .append(", tbl1.lastName ") 
           .append(", tbl1.firstName ")
           .append(", tbl1.itemCode  ")
           .append(", tbl1.groupCategory ")
           .append(", tbl1.subGroupCategory ")
           .append(", tbl1.sectionCode  ")
           .append(", tbl1.sectionName  ")
           .append(", tbl1.requestNumber  ")
           .append(", sortrequestnumber(tbl1.requestNumber) as sortedRequestNumber ")
           .append(", tbl1.resourceId ")
           .append(", tbl1.assignmentStatus ")
           .append("from ( ")
           .append("  select atp.id as atpId ")
           .append("         , atp.assignment_time_id as atpAssignmentTimeId ")
           .append("		 , atp.post_start_date as atpStartDate ")
           .append("		, atp.post_stop_date as atpStopDate ")
			.append("		, atp.quantity as atpQuantity ")
			.append("		,( ")
			.append("			select min(id) ")
			.append("			from isw_assign_time_post ")
			.append("			where assignment_time_id = atp.assignment_time_id ")
			.append("			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')  ")
			.append("				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') ")
			.append("			and post_start_date = ( ")
			.append("			select min(post_start_date) ")
			.append("			from isw_assign_time_post ")
			.append("			where assignment_time_id = atp.assignment_time_id ")
			.append("           and deleted_date is null ")
			.append("			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')  ")
			.append("				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') ")
			.append("			) ")
			.append("		) as atpNextId ")
			.append("		,( ")
			.append("			select min(post_start_date) ")
			.append("			from isw_assign_time_post ")
			.append("			where assignment_time_id = atp.assignment_time_id ")
			.append("           and deleted_date is null ")
			.append("			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')  ")
			.append("				> to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') ")
			.append("		) as atpNextStartDate ")
			.append("		,( ")
			.append("			select max(id) ")
			.append("			from isw_assign_time_post ")
			.append("			where assignment_time_id = atp.assignment_time_id ")
			.append("			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')  ")
			.append("				< to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') ")
			.append("			and post_start_date = ( ")
			.append("			select max(post_start_date) ")
			.append("			from isw_assign_time_post ")
			.append("			where assignment_time_id = atp.assignment_time_id ")
			.append("			and to_timestamp(to_char(post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI')  ")
			.append("				< to_timestamp(to_char(atp.post_start_date,'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') ")
			.append("			) ")
			.append("           and deleted_date is null ")
			.append("		) as atpPreviousId ")
			.append("		, r.last_name as lastName ")
			.append("		, r.first_name firstName ")
			.append("		, k.code as itemCode ")
			.append("		, gc.code as groupCategory ")
			.append("		, sgc.code as subGroupCategory ")
			.append("		, d.code as sectionCode ")
			.append("		, d.description as sectionName ")
			.append("		, a.request_number as requestNumber ")
			.append("		, r.id as resourceId ")
			.append("       , a.assignment_status as assignmentStatus ")
			.append("	from isw_assign_time_post atp ")
			.append("		, isw_assignment_time at ")
			.append("		, isw_assignment a ")
			.append("		, iswl_kind k ")
			.append("		, iswl_department d ")
			.append("		, iswl_group_category gc ")
			.append("		, iswl_sub_group_category sgc ")
			.append("		, isw_work_period_assignment wpa ")
			.append("		, isw_work_period wp ")
			.append("		, isw_incident_resource ir ")
			.append("		, isw_resource r ")
			.append("	where ir.incident_id = " + filter.getIncidentId() + " ")
			.append("	and to_date(to_char(atp.post_start_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('"+DateUtil.toDateString(filter.getStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ")
			.append("	and to_date(to_char(atp.post_stop_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('"+DateUtil.toDateString(filter.getStopDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') ");
			//.append("	and to_date(to_char(atp.post_stop_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('06/28/2015','MM/DD/YYYY')  ");
			//.append("   and atp.assignment_time_id = 12152 ")
			if(LongUtility.hasValue(filter.getResourceId())){
				sql.append("	and r.id = " + filter.getResourceId() + " ");
			}

			if(BooleanUtility.isTrue(filter.getReportIsWorkRatio())
					|| BooleanUtility.isTrue(filter.getReportIsSummaryHours())){
				if(BooleanUtility.isFalse(filter.getSectionIncludeAll())
						&& StringUtility.hasValue(filter.getSectionFilter())){
					sql.append("	and d.code in ("+filter.getSectionFilter() +")  ");
				}
			}
			
			if(BooleanUtility.isTrue(filter.getExcludeDemob())){
				sql.append("	and a.assignment_status not in ('D','R')  ");
			}
			sql.append("	and at.id = atp.assignment_time_id ")
			.append("	and a.id = at.assignment_id ")
			.append("   and a.end_date is null ")
			.append("	and k.id = a.kind_id ")
			.append("	and d.id = k.department_id ")
			.append("	and gc.id = k.group_category_id ")
			.append("	and sgc.id = k.sub_group_category_id ")
			.append("	and wpa.assignment_id = a.id ")
			.append("	and wp.id = wpa.work_period_id ")
			.append("	and ir.id = wp.incident_resource_id ")
			.append("	and r.id = ir.resource_id ")
			.append("	and r.is_person = " + (isOracle==true?1:true) + " ")
			.append("	order by atp.post_start_date ")
			.append(") tbl1 ");

			if(BooleanUtility.isTrue(filter.getReportIsShiftsInExcess())){
				if(BooleanUtility.isTrue(filter.getSortByRequestNumber())){
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByLastName())){
					sql.append("order by tbl1.lastName,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByTotal())){
					// this one need to determine how to handle
					// until we figure it out, sort by reqnum
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else{
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}
			}

			if(BooleanUtility.isTrue(filter.getReportIsWorkRatio())){
				if(BooleanUtility.isTrue(filter.getSortByRequestNumber())){
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByLastName())){
					sql.append("order by tbl1.lastName,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByShiftStartDate())){
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else{
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}
			}

			if(BooleanUtility.isTrue(filter.getReportIsSummaryHours())){
				if(BooleanUtility.isTrue(filter.getSortByRequestNumber())){
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByLastName())){
					sql.append("order by tbl1.lastName,tbl1.atpstartdate ");
				}else if(BooleanUtility.isTrue(filter.getSortByShiftStartDate())){
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}else{
					sql.append("order by sortedRequestNumber,tbl1.atpstartdate ");
				}
			}

			return sql.toString();
	}
}
