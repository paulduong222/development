package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;

public class CostShareReportQuery extends CostReportQuery{
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getSummaryReportDataQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        //.append(", cat1a.description as category")
        //.append(", to_char(CGAGDS.AGENCY_SHARE_DATE,'MM/DD/YYYY hh:mm:ss') as date")
        //.append(", case when igc.id=1 then 'Aircraft' " +
        //		"when igc.id=2 then 'Crew & Equipment' " +
        //		"when igc.id=3 then 'Crew & Equipment' " +
        //		"when igc.id=4 then 'Support & Overhead' " +
        //		"when igc.id=5 then 'Support & Overhead' " +
        //		"when igc.id=6 then 'Support & Overhead'" +
        //		"when igc.id=7 then 'Support & Overhead'" +
        //		"else 'Unknow Type' end as category") 
        .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category")         		
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append(", shift.shift_name as shift")
		.append(", sum(dc.total_cost_amount) as dailyCost")
		.append(", cgagdspct_ag.agency_code as agency")
		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
		.append(", cgdfagpct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.request_category_id = igc.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" ir.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i.id = ir.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgdfagpct.cost_group_id=cg.id ")
		.append("and ir.incident_id = cg.incident_id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = R.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		.append("and dc.cost_group_id  = cg.id"); //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift.shift_name,cgagdspct_ag.agency_code,cgdfagpct.percentage");
		sql.append(" order by i.id,category,costShareDate,shift.shift_name");
		
		
		
//		sql.append("select i.incident_name as incidentName")
//		.append(", i.id as incidentId")
//		.append(", dc.activity_date as costShareDate")
//		.append(", shift.shift_name as shift")
//		.append(", sum(dc.total_cost_amount) as dailyCost")
//		.append(", cgagdspct_ag.agency_code as agency")
//		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
//		.append(", cgdfagpct.percentage as percentage")
//		
//		.append(" from ")
//		
//		.append("isw_cost_group cg ")
//		.append(", isw_incident_shift shift ")
//		.append(", isw_cost_group_ag_ds cgagds ")
//		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
//		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
//		.append(", isw_incident_resource ir ")
//		.append(", isw_resource r ")
//		.append(", iswl_agency ag ")
//		.append(", isw_inc_res_daily_cost dc ")
//		.append(", isw_incident i")
//
//		.append(" where ");
//		 if(!filter.isIncidentGroup())
//			 sql.append(" ir.incident_id = " + filter.getIncidentId());
//		 else 
//			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		
//		sql.append(" and i.id = ir.incident_id ")
//		.append("and shift.id = cg.incident_shift_id ")
//		.append("and cgdfagpct.cost_group_id=cg.id ")
//		.append("and ir.incident_id = cg.incident_id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and ag.id = R.agency_id ")
//		.append("and dc.incident_resource_id = ir.id ")
//		.append("and dc.cost_group_id  = cg.id");
//		
//		sql.append(" group by i.id,i.incident_name,dc.activity_date,shift.shift_name,cgagdspct_ag.agency_code,cgdfagpct.percentage");
//		sql.append(" order by i.id,shift.shift_name,dc.activity_date");

		
		return sql.toString();
	}
	
	public static String getSummaryReportDataQuery2(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category")         		
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append(", shift.shift_name as shift")
		.append(", sum(round(dc.total_cost_amount)) as dailyCost")
		.append(", cgagdspct_ag.agency_code as agency")
		.append(", ((sum(round(total_cost_amount)) * cgagdspct.percentage) / 100) as cost")
		.append(", cgagdspct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		.append(", isw_cost_group_ag_ds cgagds ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.request_category_id = igc.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" ir.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i.id = ir.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgagds.cost_group_id=cg.id ")
		.append("and ir.incident_id = cg.incident_id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = cgagdspct.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		.append("and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') ")
		.append("and dc.cost_group_id  = cg.id"); //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift.shift_name,cgagdspct_ag.agency_code,cgagdspct.percentage ");
		//sql.append(" order by i.id,category,costShareDate,shift.shift_name");
		sql.append("union ");
		sql.append("select i2.incident_name as incidentName")
        .append(", i2.id as incidentId")
        .append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category")         		
        .append(", to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append(", shift2.shift_name as shift")
		.append(", sum(round(dc2.total_cost_amount)) as dailyCost")
		.append(", cgagdspct_ag2.agency_code as agency")
		.append(", ((sum(round(total_cost_amount)) * cgagdspct2.percentage) / 100) as cost")
		.append(", cgagdspct2.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg2 ")
		.append(", isw_incident_shift shift2 ")
		.append(", isw_cost_group_ag_ds cgagds2 ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id ")
		.append(", isw_incident_resource_other iro ")
		.append(", isw_resource_Other ro ")
		.append(", iswl_kind k2 ")
		.append("	left join iswl_group_category igc2 on k2.request_category_id = igc2.id ")
		.append(", iswl_agency ag2 ")
		.append(", isw_inc_res_daily_cost dc2 ")
		.append(", isw_incident i2")
		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" iro.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" iro.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i2.id = iro.incident_id ")
		.append("and shift2.id = cg2.incident_shift_id ")
		.append("and cgagds2.cost_group_id=cg2.id ")
		.append("and iro.incident_id = cg2.incident_id ")
		.append("and ro.id = iro.resource_other_id ")
		.append("and ag2.id = cgagdspct2.agency_id ")
		.append("and k2.id = ro.kind_id ")
		.append("and dc2.incident_resource_other_id = iro.id ")
		.append("and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY') ")
		.append("and dc2.cost_group_id  = cg2.id"); //-- 7/25/2014 take this out for test
		
		sql.append(" group by i2.id,i2.incident_name");
		sql.append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift2.shift_name,cgagdspct_ag2.agency_code,cgagdspct2.percentage ");
		sql.append(" order by incidentId,category,costShareDate,shift");
		
		
//		sql.append("select i.incident_name as incidentName")
//		.append(", i.id as incidentId")
//		.append(", dc.activity_date as costShareDate")
//		.append(", shift.shift_name as shift")
//		.append(", sum(dc.total_cost_amount) as dailyCost")
//		.append(", cgagdspct_ag.agency_code as agency")
//		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
//		.append(", cgdfagpct.percentage as percentage")
//		
//		.append(" from ")
//		
//		.append("isw_cost_group cg ")
//		.append(", isw_incident_shift shift ")
//		.append(", isw_cost_group_ag_ds cgagds ")
//		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
//		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
//		.append(", isw_incident_resource ir ")
//		.append(", isw_resource r ")
//		.append(", iswl_agency ag ")
//		.append(", isw_inc_res_daily_cost dc ")
//		.append(", isw_incident i")
//
//		.append(" where ");
//		 if(!filter.isIncidentGroup())
//			 sql.append(" ir.incident_id = " + filter.getIncidentId());
//		 else 
//			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		
//		sql.append(" and i.id = ir.incident_id ")
//		.append("and shift.id = cg.incident_shift_id ")
//		.append("and cgdfagpct.cost_group_id=cg.id ")
//		.append("and ir.incident_id = cg.incident_id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and ag.id = R.agency_id ")
//		.append("and dc.incident_resource_id = ir.id ")
//		.append("and dc.cost_group_id  = cg.id");
//		
//		sql.append(" group by i.id,i.incident_name,dc.activity_date,shift.shift_name,cgagdspct_ag.agency_code,cgdfagpct.percentage");
//		sql.append(" order by i.id,shift.shift_name,dc.activity_date");

		
		return sql.toString();
	}

	public static String getShiftKindReportDataQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        //.append(", cat1a.description as category")
        //.append(", to_char(CGAGDS.AGENCY_SHARE_DATE,'MM/DD/YYYY hh:mm:ss') as date")
         .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", shift.shift_name as shift")
		//.append(", k.code as itemCode")
		//.append(", k.description as itemDescription")
		.append(", sgc.description as itemCode")
		//.append(", k.description as itemCode")
		.append(", count(sgc.description) as qty")
		.append(", sum(dc.total_cost_amount) as dailyCost")
		.append(", cgagdspct_ag.agency_code as agency")
		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
		.append(", cgdfagpct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.request_category_id = igc.id ")
		.append("left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id  ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" ir.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i.id = ir.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgdfagpct.cost_group_id=cg.id ")
		.append("and ir.incident_id = cg.incident_id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = R.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		.append("and dc.cost_group_id  = cg.id ");  //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift.shift_name,sgc.description,cgagdspct_ag.agency_code,cgdfagpct.percentage");
		sql.append(" order by i.id,category,costShareDate,shift.shift_name,sgc.description");
		
//		sql.append("select i.incident_name as incidentName")
//        .append(", i.id as incidentId")
//        .append(", dc.activity_date as costShareDate")
//		.append(", shift.shift_name as shift")
//		.append(", k.code as itemCode")
//		.append(", dc.units as qty")
//		.append(", sum(dc.total_cost_amount) as dailyCost")
//		.append(", cgagdspct_ag.agency_code as agency")
//		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
//		.append(", cgdfagpct.percentage as percentage")
//		
//		.append(" from ")
//		
//		.append("isw_cost_group cg ")
//		.append(", isw_incident_shift shift ")
//		.append(", isw_cost_group_ag_ds cgagds ")
//		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
//		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
//		.append(", isw_incident_resource ir ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", isw_resource r ")
//		.append(", iswl_kind k ")
//		.append(", iswl_agency ag ")
//		.append(", isw_inc_res_daily_cost dc ")
//		.append(", isw_incident i")
//
//		.append(" where ");
//		 if(!filter.isIncidentGroup())
//			 sql.append(" ir.incident_id = " + filter.getIncidentId());
//		 else 
//			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		
//		sql.append(" and i.id = ir.incident_id ")
//		.append("and shift.id = cg.incident_shift_id ")
//		.append("and cgdfagpct.cost_group_id=cg.id ")
//		.append("and ir.incident_id = cg.incident_id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and ag.id = R.agency_id ")
//		.append("and wp.incident_resource_id = ir.id ")
//		.append("and wpa.work_period_id = wp.id ")
//		.append("and a.id = wpa.assignment_id ")
//		.append("and a.end_date is null ")
//		.append("and k.id = a.kind_id ")
//		.append("and dc.incident_resource_id = ir.id ")
//		.append("and dc.cost_group_id  = cg.id ");
//		
//		sql.append(" group by i.id,i.incident_name,dc.activity_date,shift.shift_name,k.code,dc.units,cgagdspct_ag.agency_code,cgdfagpct.percentage");
//		sql.append(" order by i.id,shift.shift_name,dc.activity_date,k.code,dc.units");

		return sql.toString();
	}
	
	public static String getShiftKindReportDataQuery2(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
         .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", shift.shift_name as shift")
		//.append(", sgc.description as itemCode") --03/09/2015 (should use k.description?)
		.append(", k.description as itemCode")
		//.append(", count(sgc.description) as qty") --03/09/2015
		.append(",  (select count(distinct(dc3.incident_resource_id)) ")
		.append("      from isw_inc_res_daily_cost dc3 ")
		.append("      		,isw_work_period wp3 ")
		.append("           , isw_work_period_assignment wpa3 ")
		.append("      		, isw_assignment a3 ")
		.append("      		, iswl_kind k3 ")
		.append("      where dc3.activity_date = dc.activity_date ")
		.append("      and wp3.incident_resource_id = dc3.incident_resource_id ")
		.append("      and wpa3.work_period_id = wp3.id ")
		.append("      and a3.id = wpa3.assignment_id ")
		.append("      and k3.id = a3.kind_id ")
		.append("      and k3.id = k.id ")
		.append("      and dc3.cost_group_id is not null ")
		.append("      and dc3.cost_group_id  = dc.cost_group_id ) as qty ")
		//.append(", sum(round(dc.total_cost_amount)) as dailyCost")
		.append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4 ")
		.append("      		,isw_work_period wp4 ")
		.append("           , isw_work_period_assignment wpa4 ")
		.append("      		, isw_assignment a4 ")
		.append("      		, iswl_kind k4 ")
        .append("    where dc4.activity_date = dc.activity_date ")
		.append("      and wp4.incident_resource_id = dc4.incident_resource_id ")
		.append("      and wpa4.work_period_id = wp4.id ")
		.append("      and a4.id = wpa4.assignment_id ")
		.append("      and k4.id = a4.kind_id ")
		.append("      and k4.id = k.id ")
        .append("    and dc4.cost_group_id is not null ")
        .append("    and dc4.cost_group_id=dc.cost_group_id  ")
        .append("    ) as dailyCost ")
		.append(", cgagdspct_ag.agency_code as agency")
		//.append(", ((sum(round(total_cost_amount)) * cgagdspct.percentage) / 100) as cost")
        .append(", ((select sum(round(dc5.total_cost_amount) * cgagdspct.percentage) / 100 ")
        .append("    from isw_inc_res_daily_cost dc5 ")
		.append("      		,isw_work_period wp5 ")
		.append("           , isw_work_period_assignment wpa5 ")
		.append("      		, isw_assignment a5 ")
		.append("      		, iswl_kind k5 ")
        .append("    where dc5.activity_date = dc.activity_date ")
		.append("      and wp5.incident_resource_id = dc5.incident_resource_id ")
		.append("      and wpa5.work_period_id = wp5.id ")
		.append("      and a5.id = wpa5.assignment_id ")
		.append("      and k5.id = a5.kind_id ")
		.append("      and k5.id = k.id ")
        .append("    and dc5.cost_group_id is not null ")
        .append("    and dc5.cost_group_id=dc.cost_group_id  ")
        .append("    )) as cost ")
		.append(", cgagdspct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append(", isw_cost_group_ag_ds cgagds ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.group_category_id = igc.id ")
		.append("left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id  ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" cg.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and ir.id = dc.incident_resource_id ")
		.append("and i.id = cg.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgagds.cost_group_id=cg.id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = cgagdspct.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') ")
		.append("and dc.cost_group_id  = cg.id ");  //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		   .append(" ,k.id,shift.shift_name,k.description,cgagdspct_ag.agency_code,cgagdspct.percentage,cg.id,ag.id, dc.activity_date, dc.cost_group_id ");
		
		sql.append("union ");
		
		sql.append("select i2.incident_name as incidentName")
        .append(", i2.id as incidentId")
         .append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", shift2.shift_name as shift")
		//.append(", sgc.description as itemCode") --03/09/2015 (should use k.description?)
		.append(", k2.description as itemCode")
		//.append(", count(sgc.description) as qty") --03/09/2015
		.append(",  (select count(distinct(dc3.incident_resource_other_id)) ")
		.append("      from isw_inc_res_daily_cost dc3 ")
		.append("           , isw_incident_resource_other iro3 ")
		.append("           , isw_resource_other ro3 ")
		.append("           , iswl_kind k3 ")
		.append("      where dc3.activity_date = dc2.activity_date ")
		.append("      and iro3.id = dc3.incident_resource_other_id ")
		.append("     and ro3.id = iro3.resource_other_id ")
		.append("      and k3.id = ro3.kind_id ")
		.append("      and k3.id = k2.id ")
		.append("      and dc3.cost_group_id  = dc2.cost_group_id ) as qty ")
		//.append(", 1 as qty")
		//.append(", sum(round(dc2.total_cost_amount)) as dailyCost")
        .append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4  ")
		.append("           , isw_incident_resource_other iro4 ")
		.append("           , isw_resource_other ro4 ")
		.append("           , iswl_kind k4 ")
		.append("      where dc4.activity_date = dc2.activity_date ")
		.append("      and iro4.id = dc4.incident_resource_other_id ")
		.append("      and ro4.id = iro4.resource_other_id ")
		.append("      and k4.id = ro4.kind_id ")
		.append("      and k4.id = k2.id ")
        .append("      and dc4.cost_group_id is not null ")
        .append("      and dc4.cost_group_id = dc2.cost_group_id ")
        .append("    ) as dailyCost ")
		.append(", cgagdspct_ag2.agency_code as agency")
		//.append(", ((sum(round(total_cost_amount)) * cgagdspct2.percentage) / 100) as cost")
        .append(", ((select sum(round(dc5.total_cost_amount) * cgagdspct2.percentage) / 100 ")
        .append("    from isw_inc_res_daily_cost dc5 ")
		.append("           , isw_incident_resource_other iro5 ")
		.append("           , isw_resource_other ro5 ")
		.append("           , iswl_kind k5 ")
		.append("      where dc5.activity_date = dc2.activity_date ")
		.append("      and iro5.id = dc5.incident_resource_other_id ")
		.append("      and ro5.id = iro5.resource_other_id ")
		.append("      and k5.id = ro5.kind_id ")
		.append("      and k5.id = k2.id ")
        .append("      and dc5.cost_group_id is not null ")
        .append("      and dc5.cost_group_id = dc2.cost_group_id ")
        .append("    )) as cost ")
		.append(", cgagdspct2.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg2 ")
		.append(", isw_incident_shift shift2 ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct2 ")
		.append(", isw_cost_group_ag_ds cgagds2 ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id ")
		.append(", isw_incident_resource_other iro ")
		.append(", isw_resource_other ro ")
		.append(", iswl_kind k2 ")
		.append("left join iswl_group_category igc2 on k2.group_category_id = igc2.id ")
		.append("left join iswl_sub_group_category sgc2 on K2.SUB_GROUP_CATEGORY_ID = sgc2.id  ")
		.append(", iswl_agency ag2 ")
		.append(", isw_inc_res_daily_cost dc2 ")
		.append(", isw_incident i2")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" cg2.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" cg2.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and iro.id = dc2.incident_resource_other_id ")
		.append("and i2.id = cg2.incident_id ")
		.append("and shift2.id = cg2.incident_shift_id ")
		.append("and cgagds2.cost_group_id=cg2.id ")
		.append("and ro.id = iro.resource_other_id ")
		.append("and ag2.id = cgagdspct2.agency_id ")
		.append("and k2.id = ro.kind_id ")
		.append("and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY') ")
		.append("and dc2.cost_group_id  = cg2.id ");  //-- 7/25/2014 take this out for test
		
		sql.append(" group by i2.id,i2.incident_name ");
		sql.append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift2.shift_name, k2.id,k2.description,cgagdspct_ag2.agency_code,cgagdspct2.percentage,cg2.id,ag2.id, dc2.activity_date, dc2.cost_group_id ");
		
		sql.append(" order by incidentId,category,costShareDate,shift,itemCode");
		

		return sql.toString();
	}

	public static String getDetailReportDataQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        //.append(", cat1a.description as category")
        //.append(", to_char(CGAGDS.AGENCY_SHARE_DATE,'MM/DD/YYYY hh:mm:ss') as date")
         .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", shift.shift_name as shift")
		.append(", cg.cost_group_name as costGroup")
		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end as resourcename ")
		.append(", ag.agency_code as resourceAgency")
		.append(", k.code as itemCode")
		.append(", k.description as itemDescription")
		.append(", dc.units as qty")
		.append(", sum(dc.total_cost_amount) as dailyCost")
		.append(", cgagdspct_ag.agency_code as agency")
		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
		.append(", cgdfagpct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.request_category_id = igc.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" ir.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i.id = ir.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgdfagpct.cost_group_id=cg.id ")
		.append("and ir.incident_id = cg.incident_id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = R.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		.append("and dc.cost_group_id = cg.id" );   //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift.shift_name,cg.cost_group_name, ")
		.append(" case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
		.append(" ag.agency_code,k.code,k.description,dc.units, cgagdspct_ag.agency_code,cgdfagpct.percentage ");
		
		sql.append(" order by i.id,category,costShareDate,shift.shift_name,k.code,k.description,cg.cost_group_name,")
		.append(" case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
		.append(" ag.agency_code,dc.units");
		
//		sql.append("select i.incident_name as incidentName")
//        .append(", i.id as incidentId")
//        .append(", dc.activity_date as costShareDate")
//		.append(", shift.shift_name as shift")
//		.append(", cg.cost_group_name as costGroup")
//		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end as resourcename ")
//		.append(", ag.agency_code as resourceAgency")
//		.append(", k.code as itemCode")
//		.append(", dc.units as qty")
//		.append(", sum(dc.total_cost_amount) as dailyCost")
//		.append(", cgagdspct_ag.agency_code as agency")
//		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
//		.append(", cgdfagpct.percentage as percentage")
//		
//		.append(" from ")
//		
//		.append("isw_cost_group cg ")
//		.append(", isw_incident_shift shift ")
//		.append(", isw_cost_group_ag_ds cgagds ")
//		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
//		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
//		.append(", isw_incident_resource ir ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", isw_resource r ")
//		.append(", iswl_kind k ")
//		.append(", iswl_agency ag ")
//		.append(", isw_inc_res_daily_cost dc ")
//		.append(", isw_incident i")
//
//		.append(" where ");
//		 if(!filter.isIncidentGroup())
//			 sql.append(" ir.incident_id = " + filter.getIncidentId());
//		 else 
//			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		
//		sql.append(" and i.id = ir.incident_id ")
//		.append("and shift.id = cg.incident_shift_id ")
//		.append("and cgdfagpct.cost_group_id=cg.id ")
//		.append("and ir.incident_id = cg.incident_id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and ag.id = R.agency_id ")
//		.append("and wp.incident_resource_id = ir.id ")
//		.append("and wpa.work_period_id = wp.id ")
//		.append("and a.id = wpa.assignment_id ")
//		.append("and a.end_date is null ")
//		.append("and k.id = a.kind_id ")
//		.append("and dc.incident_resource_id = ir.id ")
//		.append("and dc.cost_group_id = cg.id" );
//		
//		sql.append(" group by i.id,i.incident_name,dc.activity_date,shift.shift_name,cg.cost_group_name, ")
//		.append("case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
//		.append(" ag.agency_code,k.code,dc.units, cgagdspct_ag.agency_code,cgdfagpct.percentage ");
//		sql.append(" order by i.id,shift.shift_name,dc.activity_date,cg.cost_group_name,")
//		.append("case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
//		.append(" ag.agency_code,k.code,dc.units");

		
		return sql.toString();
	}
	
	public static String getDetailReportDataQuery2(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
         .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", shift.shift_name as shift")
		.append(", cg.cost_group_name as costGroup")
		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end as resourcename ")
		.append(", ag.agency_code as resourceAgency")
		.append(", k.code as itemCode")
		.append(", k.description as itemDescription")
		.append(", dc.units as qty")
		//.append(", sum(round(dc.total_cost_amount)) as dailyCost")
        .append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4  ")
		.append("      		,isw_work_period wp4 ")
		.append("           , isw_work_period_assignment wpa4 ")
		.append("      		, isw_assignment a4 ")
		.append("      		, iswl_kind k4 ")
        .append("    where dc4.activity_date = dc.activity_date ")
		.append("      and dc4.incident_resource_id = dc.incident_resource_id ")
		.append("      and wp4.incident_resource_id = dc4.incident_resource_id ")
		.append("      and wpa4.work_period_id = wp4.id ")
		.append("      and a4.id = wpa4.assignment_id ")
		.append("      and k4.id = a4.kind_id ")
		.append("      and k4.id = k.id ")
        .append("    and dc4.cost_group_id is not null ")
        .append("    and dc4.cost_group_id=dc.cost_group_id  ")
        .append("    ) as dailyCost ")
		.append(", cgagdspct_ag.agency_code as agency")
		//.append(", ((sum(round(total_cost_amount)) * cgagdspct.percentage) / 100) as cost")
        .append(", (select sum(round(dc5.total_cost_amount)) * cgagdspct.percentage / 100 ")
        .append("    from isw_inc_res_daily_cost dc5  ")
		.append("      		,isw_work_period wp5 ")
		.append("           , isw_work_period_assignment wpa5 ")
		.append("      		, isw_assignment a5 ")
		.append("      		, iswl_kind k5 ")
        .append("    where dc5.activity_date = dc.activity_date ")
		.append("      and dc5.incident_resource_id = dc.incident_resource_id ")
		.append("      and wp5.incident_resource_id = dc5.incident_resource_id ")
		.append("      and wpa5.work_period_id = wp5.id ")
		.append("      and a5.id = wpa5.assignment_id ")
		.append("      and k5.id = a5.kind_id ")
		.append("      and k5.id = k.id ")
        .append("    and dc5.cost_group_id is not null ")
        .append("    and dc5.cost_group_id=dc.cost_group_id  ")
        .append("    ) as cost ")
		.append(", cgagdspct.percentage as percentage")
		.append(", a.request_number as requestNumber")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append(", isw_cost_group_ag_ds cgagds ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		.append(", iswl_kind k ")
		.append("left join iswl_group_category igc on k.group_category_id = igc.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" cg.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and ir.id = dc.incident_resource_id ")
		.append("and i.id = cg.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgagds.cost_group_id=cg.id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = r.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		.append("and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') ")
		.append("and dc.cost_group_id = cg.id" );   //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name,cgagdspct_ag.id ");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end "); 
		sql.append(" ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift.shift_name,cg.cost_group_name, ")
		.append(" case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
		.append(" ag.agency_code,ag.id, cgagdspct_ag.id ,r.agency_id,dc.incident_resource_id,k.code,k.description,dc.units, cg.id,cgagdspct_ag.agency_code,cgagdspct.percentage, a.request_number ")
		.append(" , dc.activity_date, k.id, dc.cost_group_id ")
		.append("union ")
		.append("select i2.incident_name as incidentName ")
		.append("        , i2.id as incidentId ")
		.append("        , case  ")
		.append("            when igc2.id=1 then 'Aircraft' ") 
		.append("            when igc2.id=2 then 'Crew & Equipment' ") 
		.append("            when igc2.id=3 then 'Crew & Equipment' ") 
		.append("            else 'Support & Overhead' end as category ")
		.append("        , to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append("        , shift2.shift_name as shift ")
		.append("        , cg2.cost_group_name as costGroup ")
		.append("        , ro.cost_description as resourcename ") 
		.append("        , ag2.agency_code as resourceAgency ")
		.append("        , k2.code as itemCode ")
		.append("        , k2.description as itemDescription ")
		.append("        , dc2.units as qty ")
        .append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4  ")
		.append("           , isw_incident_resource_other iro4 ")
		.append("           , isw_resource_other ro4 ")
		.append("           , iswl_kind k4 ")
		.append("      where dc4.activity_date = dc2.activity_date ")
		.append("      and dc4.incident_resource_other_id = dc2.incident_resource_other_id ")
		.append("      and iro4.id = dc4.incident_resource_other_id ")
		.append("      and ro4.id = iro4.resource_other_id ")
		.append("      and k4.id = ro4.kind_id ")
		.append("      and k4.id = k2.id ")
        .append("      and dc4.cost_group_id is not null ")
        .append("      and dc4.cost_group_id = dc2.cost_group_id ")
        .append("    ) as dailyCost ")
		.append("        , cgagdspct_ag2.agency_code as agency ")
        .append(", (select sum(round(dc5.total_cost_amount)) * cgagdspct2.percentage / 100 ")
        .append("    from isw_inc_res_daily_cost dc5  ")
		.append("           , isw_incident_resource_other iro5 ")
		.append("           , isw_resource_other ro5 ")
		.append("           , iswl_kind k5 ")
		.append("      where dc5.activity_date = dc2.activity_date ")
		.append("      and dc5.incident_resource_other_id = dc2.incident_resource_other_id ")
		.append("      and iro5.id = dc5.incident_resource_other_id ")
		.append("      and ro5.id = iro5.resource_other_id ")
		.append("      and k5.id = ro5.kind_id ")
		.append("      and k5.id = k2.id ")
        .append("      and dc5.cost_group_id is not null ")
        .append("      and dc5.cost_group_id = dc2.cost_group_id ")
        .append("    ) as cost ")
		.append("        , cgagdspct2.percentage as percentage ")
		.append("        , ro.request_number as requestNumber ") 
		.append("from isw_cost_group cg2 ") 
		.append("        , isw_incident_shift shift2 ") 
		.append("        , isw_cost_group_df_ag_pct cgdfagpct2 ") 
		.append("        , isw_cost_group_ag_ds cgagds2 ")     
		.append("            left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id ")      
		.append("            left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id ") 
		.append("        , isw_incident_resource_other iro ") 
		.append("        , isw_resource_other ro ") 
		.append("        , iswl_kind k2 ") 
		.append("            left join iswl_group_category igc2 on k2.group_category_id = igc2.id ") 
		.append("        , iswl_agency ag2 ") 
		.append("        , isw_inc_res_daily_cost dc2 ") 
		.append("        , isw_incident i2 "); 
		 if(!filter.isIncidentGroup())
			 sql.append("where cg2.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append("where cg2.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		sql.append("and iro.id = dc2.incident_resource_other_id ") 
		.append("and i2.id = cg2.incident_id ") 
		.append("and shift2.id = cg2.incident_shift_id ") 
		.append("and cgagds2.cost_group_id=cg2.id ") 
		.append("and ro.id = iro.resource_other_id ") 
		.append("and ag2.id = ro.agency_id ") 
		.append("and k2.id = ro.kind_id ") 
		.append("and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY')  ")
		.append("and dc2.cost_group_id = cg2.id  ")
		.append("group by i2.id ")
		.append("        ,i2.incident_name ")
		.append("        , case  ")
		.append("            when igc2.id=1 then 'Aircraft'  ")
		.append("            when igc2.id=2 then 'Crew & Equipment' ") 
		.append("            when igc2.id=3 then 'Crew & Equipment'  ")
		.append("            else 'Support & Overhead' end  ") 
		.append("      ,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append("      ,shift2.shift_name ")
		.append("      ,cg2.cost_group_name ")
		.append("      ,  ro.cost_description ")
		.append("      ,  ag2.agency_code ")
		.append("      , ag2.id ")
		.append("      , dc2.incident_resource_id ")
		.append("      ,k2.code ")
		.append("      ,k2.description ")
		.append("      ,dc2.units ")
		.append("      ,ro.request_number ")
		.append("      ,ro.agency_id ")
		.append("      ,cg2.id ")
		.append(",     cgagdspct_ag2.id ")
		.append("      , cgagdspct_ag2.agency_code ")
		.append("      ,cgagdspct2.percentage ")
		.append("      , cgagdspct_ag2.id ")
		.append("      ,dc2.incident_resource_other_id ")
		.append("      ,dc2.cost_group_id ")
		.append("      ,dc2.activity_date ")
		.append("      ,k2.id ")
		;
		      
		sql.append(" order by incidentId,category,costShareDate,shift,itemCode,itemDescription,costGroup,")
		.append(" resourcename ")
	    .append(" , " )
		.append(" resourceAgency,qty");
		
//		sql.append("select i.incident_name as incidentName")
//        .append(", i.id as incidentId")
//        .append(", dc.activity_date as costShareDate")
//		.append(", shift.shift_name as shift")
//		.append(", cg.cost_group_name as costGroup")
//		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end as resourcename ")
//		.append(", ag.agency_code as resourceAgency")
//		.append(", k.code as itemCode")
//		.append(", dc.units as qty")
//		.append(", sum(dc.total_cost_amount) as dailyCost")
//		.append(", cgagdspct_ag.agency_code as agency")
//		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
//		.append(", cgdfagpct.percentage as percentage")
//		
//		.append(" from ")
//		
//		.append("isw_cost_group cg ")
//		.append(", isw_incident_shift shift ")
//		.append(", isw_cost_group_ag_ds cgagds ")
//		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
//		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
//		.append(", isw_incident_resource ir ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", isw_resource r ")
//		.append(", iswl_kind k ")
//		.append(", iswl_agency ag ")
//		.append(", isw_inc_res_daily_cost dc ")
//		.append(", isw_incident i")
//
//		.append(" where ");
//		 if(!filter.isIncidentGroup())
//			 sql.append(" ir.incident_id = " + filter.getIncidentId());
//		 else 
//			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		
//		sql.append(" and i.id = ir.incident_id ")
//		.append("and shift.id = cg.incident_shift_id ")
//		.append("and cgdfagpct.cost_group_id=cg.id ")
//		.append("and ir.incident_id = cg.incident_id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and ag.id = R.agency_id ")
//		.append("and wp.incident_resource_id = ir.id ")
//		.append("and wpa.work_period_id = wp.id ")
//		.append("and a.id = wpa.assignment_id ")
//		.append("and a.end_date is null ")
//		.append("and k.id = a.kind_id ")
//		.append("and dc.incident_resource_id = ir.id ")
//		.append("and dc.cost_group_id = cg.id" );
//		
//		sql.append(" group by i.id,i.incident_name,dc.activity_date,shift.shift_name,cg.cost_group_name, ")
//		.append("case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
//		.append(" ag.agency_code,k.code,dc.units, cgagdspct_ag.agency_code,cgdfagpct.percentage ");
//		sql.append(" order by i.id,shift.shift_name,dc.activity_date,cg.cost_group_name,")
//		.append("case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
//		.append(" ag.agency_code,k.code,dc.units");

		
		return sql.toString();
	}

	public static String getWorkSheetReportDataQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end  as resourcename ")
        .append(", ag.agency_code as resourceAgency")
		.append(", k.code as itemCode")
		.append(", cg.cost_group_name as costGroup")
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", dc.units as qty")
		.append(", sum(dc.total_cost_amount) as dailyCost")
		.append(", cgagdspct_ag.agency_code as agency")
		.append(", ((sum(total_cost_amount) * cgdfagpct.percentage) / 100) as cost")
		.append(", cgdfagpct.percentage as percentage")
		
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_df_ag_pct cgdfagpct ")
		.append("left join iswl_agency cgagdspct_ag on cgdfagpct.agency_id=cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		//.append(", iswl_kind k ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.request_category_id = igc.id ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" ir.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and i.id = ir.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgdfagpct.cost_group_id=cg.id ")
		.append("and ir.incident_id = cg.incident_id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = R.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		.append("and dc.incident_resource_id = ir.id ")
		//.append("and dc.cost_group_id is not null ")
		.append("and DC.COST_GROUP_ID  = cg.id");   //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end ")
		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
	    .append(" ag.agency_code,k.code,cg.cost_group_name,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),dc.units,cgagdspct_ag.agency_code,cgdfagpct.percentage ");
		
		sql.append(" order by i.id,category,")
		.append(" case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
		.append(" costShareDate,cg.cost_group_name,k.code,cgagdspct_ag.agency_code");
		
		return sql.toString();
	}
	
	public static String getWorkSheetReportDataQuery2(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select i.incident_name as incidentName")
        .append(", i.id as incidentId")
        .append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end  as resourcename ")
        .append(", ag.agency_code as resourceAgency")
		.append(", k.code as itemCode")
		.append(", cg.cost_group_name as costGroup")
        .append(", to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		//.append(", dc.units as qty")
		.append(", 1 as qty")
        .append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4  ")
		.append("      		,isw_work_period wp4 ")
		.append("           , isw_work_period_assignment wpa4 ")
		.append("      		, isw_assignment a4 ")
		.append("      		, iswl_kind k4 ")
        .append("    where dc4.activity_date = dc.activity_date ")
		.append("      and dc4.incident_resource_id = dc.incident_resource_id ")
		.append("      and wp4.incident_resource_id = dc4.incident_resource_id ")
		.append("      and wpa4.work_period_id = wp4.id ")
		.append("      and a4.id = wpa4.assignment_id ")
		.append("      and k4.id = a4.kind_id ")
		.append("      and k4.id = k.id ")
        .append("    and dc4.cost_group_id is not null ")
        .append("    and dc4.cost_group_id=dc.cost_group_id  ")
        .append("    ) as dailyCost ")
		.append(", cgagdspct_ag.agency_code as agency")
        .append(", (select sum(round(dc5.total_cost_amount)) * cgagdspct.percentage / 100 ")
        .append("    from isw_inc_res_daily_cost dc5  ")
		.append("      		,isw_work_period wp5 ")
		.append("           , isw_work_period_assignment wpa5 ")
		.append("      		, isw_assignment a5 ")
		.append("      		, iswl_kind k5 ")
        .append("    where dc5.activity_date = dc.activity_date ")
		.append("      and dc5.incident_resource_id = dc.incident_resource_id ")
		.append("      and wp5.incident_resource_id = dc5.incident_resource_id ")
		.append("      and wpa5.work_period_id = wp5.id ")
		.append("      and a5.id = wpa5.assignment_id ")
		.append("      and k5.id = a5.kind_id ")
		.append("      and k5.id = k.id ")
        .append("    and dc5.cost_group_id is not null ")
        .append("    and dc5.cost_group_id=dc.cost_group_id  ")
        .append("    ) as cost ")
		.append(", cgagdspct.percentage as percentage")
		.append(", a.request_number as requestNumber")
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_incident_shift shift ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_ag_ds cgagds ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_resource r ")
		//.append(", iswl_kind k ")
		.append(", iswl_kind k ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append("left join iswl_group_category igc on k.group_category_id = igc.id ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append(", iswl_agency ag ")
		.append(", isw_inc_res_daily_cost dc ")
		.append(", isw_incident i")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" cg.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and ir.id = dc.incident_resource_id ")
		.append("and i.id = cg.incident_id ")
		.append("and shift.id = cg.incident_shift_id ")
		.append("and cgagds.cost_group_id=cg.id ")
		.append("and r.id = ir.resource_id ")
		.append("and ag.id = R.agency_id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ")
		//.append("and dc.cost_group_id is not null ")
		.append("and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') ")
		//.append("and to_char(dc.activity_date,'MM/DD/YYYY') = '05/05/2015' ")
		//.append("and igc.id > 3 ")
		.append("and DC.COST_GROUP_ID  = cg.id");   //-- 7/25/2014 take this out for test
		
		sql.append(" group by i.id,i.incident_name");
		sql.append(", case when igc.id=1 then 'Aircraft' " +
        		"when igc.id=2 then 'Crew & Equipment' " +
        		"when igc.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end ")
		.append(", case when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
	    .append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name end, " )
	    .append(" ag.agency_code,k.code,cg.cost_group_name,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),dc.units,cgagdspct_ag.agency_code,cgagdspct.percentage, a.request_number ")
	    .append(", dc.incident_resource_id, dc.cost_group_id, dc.activity_date, k.id ");

		sql.append(" union ");
		
		sql.append("select i2.incident_name as incidentName")
        .append(", i2.id as incidentId")
        .append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end as category") 
        .append(", ro.cost_description as resourcename ")
        .append(", ag2.agency_code as resourceAgency")
		.append(", k2.code as itemCode")
		.append(", cg2.cost_group_name as costGroup")
        .append(", to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate")
		.append(", 1 as qty")
        .append(", (select sum(round(dc4.total_cost_amount)) ")
        .append("    from isw_inc_res_daily_cost dc4  ")
		.append("           , isw_incident_resource_other iro4 ")
		.append("           , isw_resource_other ro4 ")
		.append("           , iswl_kind k4 ")
		.append("      where dc4.activity_date = dc2.activity_date ")
		.append("      and dc4.incident_resource_other_id = dc2.incident_resource_other_id ")
		.append("      and iro4.id = dc4.incident_resource_other_id ")
		.append("      and ro4.id = iro4.resource_other_id ")
		.append("      and k4.id = ro4.kind_id ")
		.append("      and k4.id = k2.id ")
        .append("      and dc4.cost_group_id is not null ")
        .append("      and dc4.cost_group_id = dc2.cost_group_id ")
        .append("    ) as dailyCost ")
		.append(", cgagdspct_ag2.agency_code as agency")
        .append(", (select sum(round(dc5.total_cost_amount)) * cgagdspct2.percentage / 100 ")
        .append("    from isw_inc_res_daily_cost dc5  ")
		.append("           , isw_incident_resource_other iro5 ")
		.append("           , isw_resource_other ro5 ")
		.append("           , iswl_kind k5 ")
		.append("      where dc5.activity_date = dc2.activity_date ")
		.append("      and dc5.incident_resource_other_id = dc2.incident_resource_other_id ")
		.append("      and iro5.id = dc5.incident_resource_other_id ")
		.append("      and ro5.id = iro5.resource_other_id ")
		.append("      and k5.id = ro5.kind_id ")
		.append("      and k5.id = k2.id ")
        .append("      and dc5.cost_group_id is not null ")
        .append("      and dc5.cost_group_id = dc2.cost_group_id ")
        .append("    ) as cost ")
		.append(", cgagdspct2.percentage as percentage")
		.append(", ro.request_number as requestNumber")
		.append(" from ")
		
		.append("isw_cost_group cg2 ")
		.append(", isw_incident_shift shift2 ")
		//.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_ag_ds cgagds2 ")
		.append("    left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id ")
		.append("     left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id ")
		.append(", isw_incident_resource_other iro ")
		.append(", isw_resource_other ro ")
		.append(", iswl_kind k2 ")
		.append("left join iswl_group_category igc2 on k2.group_category_id = igc2.id ")
		//.append("left join iswl_cat_1_a cat1a on K.cat1a_id=cat1a.id ")
		.append(", iswl_agency ag2 ")
		.append(", isw_inc_res_daily_cost dc2 ")
		.append(", isw_incident i2")

		.append(" where ");
		 if(!filter.isIncidentGroup())
			 sql.append(" cg2.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" cg2.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		sql.append(" and iro.id = dc2.incident_resource_other_id ")
		.append("and i2.id = cg2.incident_id ")
		.append("and shift2.id = cg2.incident_shift_id ")
		.append("and cgagds2.cost_group_id=cg2.id ")
		.append("and ro.id = iro.resource_other_id ")
		.append("and ag2.id = ro.agency_id ")
		.append("and k2.id = ro.kind_id ")
		//.append("and dc.cost_group_id is not null ")
		.append("and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY') ")
		//.append("and to_char(dc2.activity_date,'MM/DD/YYYY') = '05/05/2015' ")
		//.append("and igc2.id > 3 ")
		.append("and DC2.COST_GROUP_ID  = cg2.id");   //-- 7/25/2014 take this out for test
		
		sql.append(" group by i2.id,i2.incident_name");
		sql.append(", case when igc2.id=1 then 'Aircraft' " +
        		"when igc2.id=2 then 'Crew & Equipment' " +
        		"when igc2.id=3 then 'Crew & Equipment' " +
        		"else 'Support & Overhead' end ")
		.append(", ro.cost_description, ")
	    .append(" ag2.agency_code,k2.code,cg2.cost_group_name,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),cgagdspct_ag2.agency_code,cgagdspct2.percentage, ro.request_number ")
	    .append(", k2.id, dc2.incident_resource_other_id, dc2.activity_date, dc2.cost_group_id ");
		
		sql.append(" order by incidentId,category,costShareDate")
		.append(" ,resourcename, ")
		.append(" costGroup,itemCode,resourceAgency");
		
		return sql.toString();
	}

	public static String getAgencyNamesQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select distinct(ag.agency_code) as agency ")
		.append(" from ")
		
		.append("isw_cost_group cg ")
		.append(", isw_cost_group_ag_ds cgagds ")
		.append(", isw_cost_group_ag_ds_pct cgagdspct ")
		.append("   left join iswl_agency ag on cgagdspct.agency_id=ag.id ")

		.append(" where ");
//		for worksheet report we need get agency list by incidentGroup not by each incident
//		if(filter.isWorkSheetReport && filter.isIncidentGroup())
//			 sql.append(" cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
//		for the other report we need to get agency list by each incident even the incidentgroup is selected
//		else 
		sql.append(" cg.incident_id = " + filter.getIncidentId())
		.append(" and cgagds.cost_group_id = cg.id ")
		.append(" and cgagdspct.cost_group_day_share_id = cgagds.id ");
		//cg.id = cgdfagpct.cost_group_id");
		
		sql.append(" order by ag.agency_code");
		
		return sql.toString();
	}
	
	public static String getCategoryListQuery(CostShareReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select distinct(case when igc.id=1 then 'Aircraft' "+
			      " when igc.id=2 then 'Crew & Equipment' "+
			      " when igc.id=3 then 'Crew & Equipment' "+
			      " else 'Support & Overhead' end) as category "+
			"from isw_incident_resource ir ,  "+
			"isw_work_period wp , "+ 
			"isw_work_period_assignment wpa ,  "+
			"isw_assignment a ,  "+
			"isw_resource r , "+ 
			"iswl_kind k left join iswl_group_category igc on k.group_category_id = igc.id ,  "+
			"isw_incident i , "+
			"isw_inc_res_daily_cost dc, " + 
			"isw_cost_group cg " +
			"where cg.id in (select id from isw_cost_group where incident_id = " + filter.getIncidentId() + ") " +
			//"where  i.id = "+ filter.getIncidentId() +
			"and i.id = cg.incident_id " +
			"and dc.cost_group_id = cg.id " +
			" and ir.id = dc.incident_resource_id  "+
			"and wp.incident_resource_id = ir.id  "+
			"and wpa.work_period_id = wp.id  "+
			"and a.id = wpa.assignment_id  "+
			"and a.end_date is null  "+
			"and k.id = a.kind_id   ");
		
		sql.append("union ")
			.append("select distinct(case when igc2.id=1 then 'Aircraft' "+
				      " when igc2.id=2 then 'Crew & Equipment' "+
				      " when igc2.id=3 then 'Crew & Equipment' "+
				      " else 'Support & Overhead' end) as category "+
				"from isw_incident_resource_other iro ,  "+
				"isw_resource_other ro , "+ 
				"iswl_kind k2 left join iswl_group_category igc2 on k2.group_category_id = igc2.id ,  "+
				"isw_incident i2,  "+
				"isw_inc_res_daily_cost dc2, " +
				"isw_cost_group cg2 " +
				"where cg2.id in (select id from isw_cost_group where incident_id = " + filter.getIncidentId() + ") " +
				//"where  i.id = "+ filter.getIncidentId() +
				"and i2.id = cg2.incident_id " +
				"and dc2.cost_group_id = cg2.id " +
				" and iro.id = dc2.incident_resource_other_id  "+
				//"where  i2.id = "+ filter.getIncidentId() +
				//" and iro.incident_id = i2.id  "+
				"and ro.id = iro.resource_other_id " +
				"and k2.id = ro.kind_id   "+
			"order by category");

		
		return sql.toString();
	}
}
