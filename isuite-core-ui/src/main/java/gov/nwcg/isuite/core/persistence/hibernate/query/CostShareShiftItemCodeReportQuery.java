package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;

public class CostShareShiftItemCodeReportQuery {
/*
select i.incident_name as incidentName
       , i.id as incidentId
       , tbl1.category as category
       , tbl1.shift as shift
       , tbl1.itemDesc as itemCode
       , count(tbl1.qty) as qty
       , round(sum(tbl1.totalCost)) as dailyCost
       , tbl1.agencyCode as agency
       , tbl1.costShareDate as costShareDate
       , sum(tbl1.costShare) as cost
       , case when sum(tbl1.totalCost) > 0 then round( (sum(tbl1.costShare) / sum(tbl1.totalCost) ) * 100) 
              else 0
         end as percentage
from isw_incident i
     , isw_cost_group cg
     ,(
        select 
            case
                when igc.id=1 then 'Aircraft' 
                when igc.id=2 then 'Crew & Equipment' 
                when igc.id=3 then 'Crew & Equipment' 
                else 'Support & Overhead'
            end as category
            , ir.incident_id as incidentId
            , to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate
            , shift.shift_name as shift
            , k.description as itemDesc
            , dc.cost_group_id as costGroupId
            , k.code as itemCode
            , sum(dc.total_cost_amount) as totalCost
            , 1 as qty
            ,cgagdspct_ag.agency_code as agencyCode
            , ( sum(dc.total_cost_amount) * cgagdspct.percentage / 100) as costShare
        from isw_incident_resource ir
             , isw_incident_shift shift 
             , isw_cost_group cg
             , isw_inc_res_daily_cost dc
             , isw_work_period wp
             , isw_work_period_assignment wpa
             , isw_assignment a
             , iswl_kind k
                left join iswl_group_category igc on k.group_category_id = igc.id
             , isw_cost_group_ag_ds cgagds     
             , isw_cost_group_ag_ds_pct cgagdspct  
             , iswl_agency cgagdspct_ag 
        where wp.incident_resource_id = ir.id
        and wpa.work_period_id = wp.id
        and a.id = wpa.assignment_id
        and k.id = a.kind_id
        and dc.cost_group_id = cg.id
        and ir.id = dc.incident_resource_id
        and shift.id = cg.incident_shift_id
        and cgagdspct.cost_group_day_share_id = cgagds.id
        and cgagdspct_ag.id = cgagdspct.agency_id
        and cgagds.cost_group_id = dc.cost_group_id 
        and to_char(cgagds.agency_share_date,'MM/DD/YYYY') = to_char(dc.activity_date,'MM/DD/YYYY')
        group by ir.incident_id
                 ,igc.id
                 ,dc.activity_date
                 , shift.shift_name 
                 , k.description 
                 , dc.cost_group_id 
                 , k.code 
                 , cgagdspct_ag.agency_code
                 , cgagdspct.percentage
        union
        select 
            case
                when igc2.id=1 then 'Aircraft' 
                when igc2.id=2 then 'Crew & Equipment' 
                when igc2.id=3 then 'Crew & Equipment' 
                else 'Support & Overhead'
            end as category
            , iro.incident_id as incidentId
            , to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate
            , shift2.shift_name as shift
            , k2.description as itemDesc
            , dc2.cost_group_id as costGroupId
            , k2.code as itemCode
            , sum(dc2.total_cost_amount) as totalCost
            , 1 as qty
            , cgagdspct_ag2.agency_code
            , ( sum(dc2.total_cost_amount) * cgagdspct2.percentage / 100) as costShare
        from isw_incident_resource_other iro
             , isw_resource_other ro
             , isw_incident_shift shift2 
             , isw_cost_group cg2
             , isw_inc_res_daily_cost dc2
             , iswl_kind k2
                left join iswl_group_category igc2 on k2.group_category_id = igc2.id
             , isw_cost_group_ag_ds cgagds2     
             , isw_cost_group_ag_ds_pct cgagdspct2  
             , iswl_agency cgagdspct_ag2 
        where ro.id = iro.resource_other_id 
        and k2.id = ro.kind_id
        and dc2.cost_group_id = cg2.id
        and iro.id = dc2.incident_resource_other_id
        and shift2.id = cg2.incident_shift_id
        and cgagdspct2.cost_group_day_share_id = cgagds2.id
        and cgagdspct_ag2.id = cgagdspct2.agency_id
        and cgagds2.cost_group_id = dc2.cost_group_id 
        and to_char(cgagds2.agency_share_date,'MM/DD/YYYY') = to_char(dc2.activity_date,'MM/DD/YYYY')
        group by iro.incident_id
                 ,igc2.id
                 ,dc2.activity_date
                 , shift2.shift_name 
                 , k2.description 
                 , dc2.cost_group_id 
                 , k2.code 
                 , cgagdspct_ag2.agency_code
                 , cgagdspct2.percentage
     ) tbl1
where cg.incident_id = ? 10460
and i.id = cg.incident_id
and tbl1.incidentId=cg.incident_id
and tbl1.costGroupId=cg.id
group by i.incident_name
        , i.id
        , tbl1.category
        , tbl1.costShareDate
        , tbl1.itemDesc
        , tbl1.shift
        , tbl1.agencyCode
order by i.id,category,costShareDate,shift,itemCode
  */
	
	public static String getCostShareShiftItemCodeReportQuery(CostShareReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("")
		.append("select i.incident_name as incidentName ")
	    .append("   , i.id as incidentId ")
	    .append("   , tbl1.category as category ")
	    .append("   , tbl1.shift as shift ")
	    .append("   , tbl1.itemDesc as itemCode ")
	    .append("   , count(tbl1.qty) as qty ")
	    .append("   , sum(tbl1.totalCost) as dailyCost ")
	    .append("   , tbl1.agencyCode as agency ")
	    .append("   , tbl1.costShareDate as costShareDate ")
	    .append("   , sum(tbl1.costShare) as cost ")
	    .append("   , case when sum(tbl1.totalCost) > 0 then (sum(tbl1.costShare) / sum(tbl1.totalCost)  * 100)  ")
	    .append("          else 0 ")
	    .append("     end as percentage ")
	    .append("from isw_incident i ")
	    .append("     , isw_cost_group cg ")
	    .append("     ,( ")
	    .append("        select  ")
	    .append("	            case ")
	    .append("                when igc.id=1 then 'Aircraft'  ")
	    .append("                when igc.id=2 then 'Crew & Equipment'  ")
	    .append("                when igc.id=3 then 'Crew & Equipment'  ")
	    .append("                else 'Support & Overhead' ")
	    .append("	            end as category ")
	    .append("            , ir.incident_id as incidentId ")
	    .append("            , dc.incident_resource_id as incidentResourceId ")
	    .append("            , to_date(to_char(dc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
	    .append("            , shift.shift_name as shift ")
	    .append("            , k.description as itemDesc ")
	    .append("            , dc.cost_group_id as costGroupId ")
	    .append("            , k.code as itemCode ")
	    .append("            , sum(dc.total_cost_amount) as totalCost ")
	    .append("            , 1 as qty ")
	    .append("            ,cgagdspct_ag.agency_code as agencyCode ")
	    .append("            , ( sum(dc.total_cost_amount) * cgagdspct.percentage / 100) as costShare ")
	    .append("        from isw_incident_resource ir ")
	    .append("             , isw_incident_shift shift  ")
	    .append("             , isw_cost_group cg ")
	    .append("             , isw_inc_res_daily_cost dc ")
	    .append("             , isw_work_period wp ")
	    .append("             , isw_work_period_assignment wpa ")
	    .append("             , isw_assignment a ")
	    .append("             , iswl_kind k ")
	    .append("                left join iswl_group_category igc on k.group_category_id = igc.id ")
		.append("             , isw_cost_group_ag_ds cgagds      ")
		.append("             , isw_cost_group_ag_ds_pct cgagdspct  ") 
		.append("             , iswl_agency cgagdspct_ag  ")
		.append("        where wp.incident_resource_id = ir.id ")
		.append("        and wpa.work_period_id = wp.id ")
		.append("        and a.id = wpa.assignment_id ")
		.append("        and k.id = a.kind_id ");
		 if(!filter.isIncidentGroup())
			// sql.append("		and dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + filter.getIncidentId() +") ");
		 	sql.append("		and cg.incident_id = " + filter.getIncidentId() + " ");
		 else
			// sql.append("		and dc.incident_account_code_id in (select id from isw_incident_account_code where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ")) ");
		 	sql.append("		and cg.incident_id  in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		sql.append("		 and dc.cost_group_id = cg.id " )
		//.append("        and dc.cost_group_id = cg.id ")
		.append("        and ir.id = dc.incident_resource_id ")
		.append("        and shift.id = cg.incident_shift_id ")
		.append("        and cgagdspct.cost_group_day_share_id = cgagds.id ")
		.append("        and cgagdspct_ag.id = cgagdspct.agency_id ")
		.append("        and cgagds.cost_group_id = dc.cost_group_id  ")
		.append("        and to_char(cgagds.agency_share_date,'MM/DD/YYYY') = to_char(dc.activity_date,'MM/DD/YYYY') ")
		.append("        group by ir.incident_id ")
		.append("				  , dc.incident_resource_id " )
		.append("                 ,igc.id ")
		.append("                 ,dc.activity_date ")
		.append("                 , shift.shift_name  ")
		.append("                 , k.description  ")
		.append("                 , dc.cost_group_id  ")
		.append("                 , k.code  ")
		.append("                 , cgagdspct_ag.agency_code ")
		.append("                 , cgagdspct.percentage ")
		.append("        union ")
		.append("        select  ")
		.append("            case ")
		.append("                when igc2.id=1 then 'Aircraft'  ")
		.append("                when igc2.id=2 then 'Crew & Equipment'  ")
		.append("                when igc2.id=3 then 'Crew & Equipment'  ")
		.append("                else 'Support & Overhead' ")
		.append("            end as category ")
		.append("            , iro.incident_id as incidentId ")
		.append("            , dc2.incident_resource_id ")
		.append("            , to_date(to_char(dc2.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') as costShareDate ")
		.append("            , shift2.shift_name as shift ")
		.append("            , k2.description as itemDesc ")
		.append("            , dc2.cost_group_id as costGroupId ")
		.append("            , k2.code as itemCode ")
		.append("            , sum(dc2.total_cost_amount) as totalCost ")
		.append("            , 1 as qty ")
		.append("            , cgagdspct_ag2.agency_code ")
		.append("            , ( sum(dc2.total_cost_amount) * cgagdspct2.percentage / 100) as costShare ")
		.append("        from isw_incident_resource_other iro ")
		.append("             , isw_resource_other ro ")
		.append("             , isw_incident_shift shift2  ")
		.append("             , isw_cost_group cg2 ")
		.append("             , isw_inc_res_daily_cost dc2 ")
		.append("             , iswl_kind k2 ")
		.append("                left join iswl_group_category igc2 on k2.group_category_id = igc2.id ")
		.append("             , isw_cost_group_ag_ds cgagds2      ")
		.append("             , isw_cost_group_ag_ds_pct cgagdspct2  ") 
		.append("             , iswl_agency cgagdspct_ag2  ")
		.append("        where ro.id = iro.resource_other_id  ")
		.append("        and k2.id = ro.kind_id ");
		 if(!filter.isIncidentGroup())
			// sql.append("		and dc2.incident_account_code_id in (select id from isw_incident_account_code where incident_id = " + filter.getIncidentId() +") ");
		 	sql.append("		and cg2.incident_id = " + filter.getIncidentId() + " ");
		 else
			// sql.append("		and dc2.incident_account_code_id in (select id from isw_incident_account_code where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ")) ");
		 	sql.append("		and cg2.incident_id  in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ");
		sql.append("		 and dc2.cost_group_id = cg2.id " )
		//.append("        and dc2.cost_group_id = cg2.id ")
		.append("        and iro.id = dc2.incident_resource_other_id ")
		.append("        and shift2.id = cg2.incident_shift_id ")
		.append("        and cgagdspct2.cost_group_day_share_id = cgagds2.id ")
		.append("        and cgagdspct_ag2.id = cgagdspct2.agency_id ")
		.append("        and cgagds2.cost_group_id = dc2.cost_group_id  ")
		.append("        and to_char(cgagds2.agency_share_date,'MM/DD/YYYY') = to_char(dc2.activity_date,'MM/DD/YYYY') ")
		.append("        group by iro.incident_id ")
		.append("                  , dc2.incident_resource_id ")
		.append("                 ,igc2.id ")
		.append("                 ,dc2.activity_date ")
		.append("                 , shift2.shift_name  ")
		.append("                 , k2.description  ")
		.append("                 , dc2.cost_group_id  ")
		.append("                 , k2.code  ")
		.append("                 , cgagdspct_ag2.agency_code ")
		.append("                 , cgagdspct2.percentage ")
		.append("     ) tbl1 ");
		
		 if(!filter.isIncidentGroup())
			 sql.append(" where cg.id in (")
			 .append(" select cg3.id ")
			 .append(" from isw_cost_group cg3 where incident_id = " + filter.getIncidentId() + " ")
			 .append(" ) ");
			 //sql.append(" where cg.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" where cg.id in (")
			 .append(" select cg3.id ")
			 .append(" from isw_cost_group cg3 where incident_id in (")
		 	.append("    select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId() + ") ")
			 .append(" ) ");
			 //sql.append(" where cg.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ") ");

		sql.append("and i.id = cg.incident_id ")
		//.append("and tbl1.incidentId=cg.incident_id ")
		.append("and tbl1.costGroupId=cg.id ")
		//.append("and tbl1.itemDesc='ENGINE, TYPE 6' ")
		//.append("and to_char(tbl1.costShareDate,'MM/DD/YYYY')='05/19/2015' ")
		.append("group by i.incident_name ")
		.append("        , i.id ")
		.append("        , tbl1.category ")
		.append("        , tbl1.costShareDate ")
		.append("        , tbl1.itemDesc ")
		.append("        , tbl1.shift ")
		.append("        , tbl1.agencyCode ")
		.append("order by i.id,category,costShareDate,shift,itemCode ");

		return sql.toString();
	}
}
