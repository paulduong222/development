package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;

public class CostShareSummaryReportQuery {
/*
select tbl1.incidentName as incidentName
        , tbl1.incidentId as incidentId
        , tbl1.category as category
        , tbl1.costShareDate as costShareDate 
        , tbl1.shift as shift
        , sum(tbl1.dailyCost) as dailyCost
        , tbl1.agency as agency
        , sum(tbl1.cost) as cost
        , tbl1.percentage as percentage
from (
    select i.incident_name as incidentName
            , i.id as incidentId
            , case 
                when igc.id=1 then 'Aircraft' 
                when igc.id=2 then 'Crew  Equipment' 
                when igc.id=3 then 'Crew  Equipment' 
              else 'Support  Overhead' end as category
        , to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate 
        , shift.shift_name as shift
        , sum(round(dc.total_cost_amount)) as dailyCost
        , cgagdspct_ag.agency_code as agency
        , ((sum(round(total_cost_amount)) * cgagdspct.percentage) / 100) as cost
        , cgagdspct.percentage as percentage 
        from
        isw_cost_group cg 
        , isw_incident_shift shift 
        , isw_cost_group_ag_ds cgagds     
            left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id      
            left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id 
        , isw_incident_resource ir 
        , isw_work_period wp 
        , isw_work_period_assignment wpa 
        , isw_assignment a 
        , isw_resource r 
        , iswl_kind k 
            left join iswl_group_category igc on k.request_category_id = igc.id 
        , iswl_agency ag 
        , isw_inc_res_daily_cost dc 
        , isw_incident i 
    where  ir.incident_id = 10389 
    and i.id = ir.incident_id 
    and shift.id = cg.incident_shift_id 
    and cgagds.cost_group_id=cg.id 
    and ir.incident_id = cg.incident_id 
    and r.id = ir.resource_id 
    and ag.id = cgagdspct.agency_id 
    and wp.incident_resource_id = ir.id 
    and wpa.work_period_id = wp.id 
    and a.id = wpa.assignment_id 
    and a.end_date is null 
    and k.id = a.kind_id 
    and dc.incident_resource_id = ir.id 
    and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') 
    and dc.cost_group_id  = cg.id 
    group by i.id
            , i.incident_name
            , case 
                when igc.id=1 then 'Aircraft' 
                when igc.id=2 then 'Crew  Equipment' 
                when igc.id=3 then 'Crew  Equipment' 
                else 'Support  Overhead' end  
            ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY')
            ,shift.shift_name
            ,cgagdspct_ag.agency_code
            ,cgagdspct.percentage 
    union
    select i2.incident_name as incidentName
        , i2.id as incidentId
        , case when igc2.id=1 then 'Aircraft' when igc2.id=2 then 'Crew  Equipment' when igc2.id=3 then 'Crew  Equipment' else 'Support  Overhead' end as category
        , to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate 
        , shift2.shift_name as shift, sum(round(dc2.total_cost_amount)) as dailyCost
        , cgagdspct_ag2.agency_code as agency, ((sum(round(total_cost_amount)) * cgagdspct2.percentage) / 100) as cost
        , cgagdspct2.percentage as percentage 
    from isw_cost_group cg2 
        , isw_incident_shift shift2 
        , isw_cost_group_ag_ds cgagds2     
            left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id      
            left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id 
        , isw_incident_resource_other iro 
        , isw_resource_Other ro 
        , iswl_kind k2     
            left join iswl_group_category igc2 on k2.request_category_id = igc2.id 
            , iswl_agency ag2 
            , isw_inc_res_daily_cost dc2 
            , isw_incident i2 
    where  iro.incident_id = 10389 
    and i2.id = iro.incident_id 
    and shift2.id = cg2.incident_shift_id 
    and cgagds2.cost_group_id=cg2.id 
    and iro.incident_id = cg2.incident_id 
    and ro.id = iro.resource_other_id 
    and ag2.id = cgagdspct2.agency_id
    and k2.id = ro.kind_id 
    and dc2.incident_resource_other_id = iro.id 
    and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY') 
    and dc2.cost_group_id  = cg2.id 
    group by i2.id
        ,i2.incident_name
        , case when igc2.id=1 then 'Aircraft' when igc2.id=2 then 'Crew  Equipment' when igc2.id=3 then 'Crew  Equipment' else 'Support  Overhead' end  
        ,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY'),shift2.shift_name,cgagdspct_ag2.agency_code,cgagdspct2.percentage  
) tbl1 
group by tbl1.incidentName, tbl1.incidentId, tbl1.shift, tbl1.category, tbl1.costShareDate
        , tbl1.agency , tbl1.percentage
order by incidentId,category,costShareDate,shift 
  */
	
	public static String getCostShareSummaryReportQuery(CostShareReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("")
		.append("select tbl1.incidentName as incidentName ")
	    .append("    , tbl1.incidentId as incidentId ")
	    .append("    , tbl1.category as category ")
	    .append("    , tbl1.costShareDate as costShareDate ") 
	    .append("    , tbl1.shift as shift ")
	    .append("    , sum(tbl1.dailyCost) as dailyCost ")
	    .append("    , tbl1.agency as agency ")
	    .append("    , sum(tbl1.cost) as cost ")
	    .append("    , case when sum(tbl1.cost) > 0 then round(sum(tbl1.cost) / sum(tbl1.dailyCost)  * 100)  ")
	    .append("      else 0 ")
	    .append("      end as percentage ")
		.append("from ( ")
		.append("    select i.incident_name as incidentName ")
		.append("            , i.id as incidentId ")
		.append("            , case  ")
		.append("                when igc.id=1 then 'Aircraft' ") 
		.append("                when igc.id=2 then 'Crew & Equipment' ") 
		.append("                when igc.id=3 then 'Crew & Equipment' ") 
		.append("              else 'Support & Overhead' end as category ")
		.append("        , to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append("        , shift.shift_name as shift ")
		.append("        , ( select sum(round(dc4.total_cost_amount)) ")
		.append("			 from isw_inc_res_daily_cost dc4 ")
		.append("			 	  , isw_work_period wp4 ")
		.append("				  , isw_work_period_assignment wpa4 ")
		.append("				  , isw_assignment a4 ")
		.append("				  , iswl_kind k4 ")
		.append("            where dc4.activity_date = dc.activity_date ")
		.append("            and wp4.incident_resource_id = dc4.incident_resource_id ")
		.append("            and wpa4.work_period_id = wp4.id ")
		.append("            and a4.id = wpa4.assignment_id " )
		.append("            and k4.id = a4.kind_id ")
		.append("            and k4.id = k.id ")
		.append("            and dc4.cost_group_id is not null ")
		.append("            and dc4.cost_group_id = dc.cost_group_id ")
		.append("           ) as dailyCost ")
		.append("        , cgagdspct_ag.agency_code as agency ")
		.append(" 		 , ( ")
		.append("             select sum(round(dc5.total_cost_amount) * cgagdspct.percentage) / 100 ")
		.append("			 from isw_inc_res_daily_cost dc5 ")
		.append("			 	  , isw_work_period wp5 ")
		.append("				  , isw_work_period_assignment wpa5 ")
		.append("				  , isw_assignment a5 ")
		.append("				  , iswl_kind k5 ")
		.append("            where dc5.activity_date = dc.activity_date ")
		.append("            and wp5.incident_resource_id = dc5.incident_resource_id ")
		.append("            and wpa5.work_period_id = wp5.id ")
		.append("            and a5.id = wpa5.assignment_id " )
		.append("            and k5.id = a5.kind_id ")
		.append("            and k5.id = k.id ")
		.append("            and dc5.cost_group_id is not null ")
		.append("            and dc5.cost_group_id = dc.cost_group_id ")
		.append("         ) as cost ")
		.append("        , cgagdspct.percentage as percentage ") 
		.append("        from ")
		.append("        isw_cost_group cg ") 
		.append("        , isw_incident_shift shift ") 
		.append("        , isw_cost_group_ag_ds cgagds ")     
		.append("            left join isw_cost_group_ag_ds_pct cgagdspct on cgagds.id = cgagdspct.cost_group_day_share_id ")      
		.append("            left join iswl_agency cgagdspct_ag on cgagdspct.agency_id = cgagdspct_ag.id ")
		.append("        , isw_work_period wp ") 
		.append("        , isw_work_period_assignment wpa ") 
		.append("        , isw_assignment a ") 
		.append("        , iswl_kind k ") 
		.append("            left join iswl_group_category igc on k.group_category_id = igc.id ") 
		.append("        , isw_inc_res_daily_cost dc ") 
		.append("        , isw_incident i "); 
		 if(!filter.isIncidentGroup())
			 sql.append(" where cg.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" where cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		sql.append("    and i.id = cg.incident_id ") 
		.append("    and shift.id = cg.incident_shift_id ") 
		.append("    and cgagds.cost_group_id=cg.id ") 
		.append("    and dc.cost_group_id is not null ")
		.append("    and wp.incident_resource_id = dc.incident_resource_id ") 
		.append("    and dc.incident_resource_id is not null ")
		.append("    and wpa.work_period_id = wp.id ") 
		.append("    and a.id = wpa.assignment_id ") 
		.append("    and a.end_date is null ") 
		.append("    and k.id = a.kind_id ") 
		.append("    and to_char(dc.activity_date,'MM/DD/YYYY') = to_char(cgagds.agency_share_date , 'MM/DD/YYYY') ") 
		.append("    and dc.cost_group_id  = cg.id ") 
		.append("    group by i.id ")
		.append("            , i.incident_name ")
		.append("            , case ") 
		.append("                when igc.id=1 then 'Aircraft' ") 
		.append("                when igc.id=2 then 'Crew & Equipment' ") 
		.append("                when igc.id=3 then 'Crew & Equipment' ") 
		.append("                else 'Support & Overhead' end ")  
		.append("            ,to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append("            ,dc.activity_date ")
		.append("            ,k.id ")
		.append("            ,dc.cost_group_id ")
		.append("            ,shift.shift_name ")
		.append("            ,cgagdspct_ag.agency_code ")
		.append("            ,cgagdspct.percentage ") 
		.append("    union ")
		.append("    select i2.incident_name as incidentName ")
		.append("        , i2.id as incidentId ")
		.append("        , case when igc2.id=1 then 'Aircraft' when igc2.id=2 then 'Crew & Equipment' when igc2.id=3 then 'Crew & Equipment' else 'Support & Overhead' end as category ")
		.append("        , to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ") 
		.append("        , shift2.shift_name as shift")
		.append("        , ( select sum(round(dc4.total_cost_amount)) ")
		.append("			 from isw_inc_res_daily_cost dc4 ")
		.append("			 	  , isw_incident_resource_other iro4 ")
		.append("				  , isw_resource_other ro4 ")
		.append("				  , iswl_kind k4 ")
		.append("            where dc4.activity_date = dc2.activity_date ")
		.append("            and iro4.id = dc4.incident_resource_other_id ")
		.append("            and ro4.id = iro4.resource_other_id  ")
		.append("            and k4.id = ro4.kind_id ")
		.append("            and k4.id = k2.id ")
		.append("            and dc4.cost_group_id is not null ")
		.append("            and dc4.cost_group_id = dc2.cost_group_id ")
		.append("           ) as dailyCost ")
		.append("        , cgagdspct_ag2.agency_code as agency ")
		.append("        , ( select sum(round(dc5.total_cost_amount)) * cgagdspct2.percentage / 100 ")
		.append("			 from isw_inc_res_daily_cost dc5 ")
		.append("			 	  , isw_incident_resource_other iro5 ")
		.append("				  , isw_resource_other ro5 ")
		.append("				  , iswl_kind k5 ")
		.append("            where dc5.activity_date = dc2.activity_date ")
		.append("            and iro5.id = dc5.incident_resource_other_id ")
		.append("            and ro5.id = iro5.resource_other_id  ")
		.append("            and k5.id = ro5.kind_id ")
		.append("            and k5.id = k2.id ")
		.append("            and dc5.cost_group_id is not null ")
		.append("            and dc5.cost_group_id = dc2.cost_group_id ")
		.append("           ) as cost ")
		.append("        , cgagdspct2.percentage as percentage ") 
		.append("    from isw_cost_group cg2 ") 
		.append("        , isw_incident_shift shift2 ") 
		.append("        , isw_cost_group_ag_ds cgagds2 ")     
		.append("            left join isw_cost_group_ag_ds_pct cgagdspct2 on cgagds2.id = cgagdspct2.cost_group_day_share_id ")      
		.append("            left join iswl_agency cgagdspct_ag2 on cgagdspct2.agency_id = cgagdspct_ag2.id ") 
		.append("        , isw_incident_resource_other iro ") 
		.append("        , isw_resource_Other ro ") 
		.append("        , iswl_kind k2 ")     
		.append("            left join iswl_group_category igc2 on k2.group_category_id = igc2.id ") 
		.append("            , isw_inc_res_daily_cost dc2 ") 
		.append("            , isw_incident i2 "); 		 
		if(!filter.isIncidentGroup())
			 sql.append(" where cg2.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" where cg2.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		sql.append("    and iro.id = dc2.incident_resource_other_id ") 
		.append("    and i2.id = cg2.incident_id ") 
		.append("    and shift2.id = cg2.incident_shift_id ") 
		.append("    and cgagds2.cost_group_id=cg2.id ") 
		.append("    and ro.id = iro.resource_other_id ") 
		.append("    and k2.id = ro.kind_id ") 
		.append("    and to_char(dc2.activity_date,'MM/DD/YYYY') = to_char(cgagds2.agency_share_date , 'MM/DD/YYYY') ") 
		.append("    and dc2.cost_group_id  = cg2.id ") 
		.append("    group by i2.id ")
		.append("        ,i2.incident_name ")
		.append("        , case when igc2.id=1 then 'Aircraft' when igc2.id=2 then 'Crew & Equipment' when igc2.id=3 then 'Crew & Equipment' else 'Support & Overhead' end ")  
		.append("        ,to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		.append("        ,shift2.shift_name,k2.id, cgagdspct_ag2.agency_code,cgagdspct2.percentage, cg2.id, dc2.activity_date, dc2.cost_group_id ")  
		.append(") tbl1 ") 
		.append("group by tbl1.incidentName, tbl1.incidentId, tbl1.shift, tbl1.category, tbl1.costShareDate ")
		.append("        , tbl1.agency ") //, tbl1.percentage ")
		.append("order by incidentId,category,costShareDate,shift "); 


		return sql.toString();
	}
}
