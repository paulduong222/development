package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.text.SimpleDateFormat;

public class DetailByResourceReportQuery {
	public static String getDetailByResourceSubReportDataQuery(GroupCategoryTotalReportFilter filter, Long incidentId, Boolean isOracle) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
			
		sql.append("select  ");		
		if(filter.getSelectedReportGroup().equals("Accounting Code")) {
			sql.append(" AC.ACCOUNT_CODE as grouping, ")
			.append("AC.ACCOUNT_CODE as groupingcode, ");
		}
		else if(filter.getSelectedReportGroup().equals("Agency")) {
			sql.append(" ag.agency_code || ' - ' || ag.agency_name as grouping, ")
			.append(" ag.agency_code as groupingcode, ");		
		}
		else if(filter.getSelectedReportGroup().equals("Payment Agency")) {
			sql.append(" pa.agency_code || ' - ' || pa.agency_name as grouping, ")
			.append(" pa.agency_code as groupingcode,");
		}
		else if(filter.getSelectedReportGroup().equals("Cost Group")) {
			sql.append(" CG.COST_GROUP_NAME as grouping, ")
			.append(" CG.COST_GROUP_NAME as groupingcode, ");
		}
		else if(filter.getSelectedReportGroup().equals("Unit ID")) {
			sql.append(" org.unit_code || ' - ' || org.organization_name as grouping, ")
			.append(" org.unit_code as groupingcode, ");	
		}
		sql.append("case ")
		.append("when k.is_direct = " + (isOracle ? 1 : true) + " then 'DIRECT RESOURCES' ")
		.append("else 'INDIRECT RESOURCES' ")
		.append("end as directIndirectName, ")
	    .append(" sgc.description as itemcodecategory ")
	    .append(" , k.code as itemcode ")
	    .append(" , k.description as itemcodedescription ")
	    .append(" , k.code || ' - ' || k.description as itemfulldescription ")	    
	    .append(" , (case ")
		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
		.append(" end ) as resourcename ")
	    .append(" , a.request_number as requestnumber ")
	    .append(" , ag.agency_code as agencycode    ")
	    .append(" , org.unit_code as unitid ")   
	    .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assigndatechar ") 
        .append(" , (case  ") 
	    .append(" when wp.dm_release_date >=  wp.dm_estimated_arrival_date THEN TO_CHAR(wp.dm_release_date, 'MM/DD/YYYY')  ") 
		.append(" when wp.dm_release_date  <  wp.dm_estimated_arrival_date THEN TO_CHAR(wp.dm_estimated_arrival_date, 'MM/DD/YYYY') ") 
        .append(" end ) as costenddatechar  ")    	    
	    .append(" , to_char(dc.activity_date,'MM/DD/YYYY') as activitydatechar ")
	    //.append(" , S.SHIFT_NAME as shift ")
	    .append(" , cg.description as costgroupname ")
	    .append(" , dc.unit_cost_amount as unitcost ")
	    .append(" , dc.units AS rateunits ")
	    .append(" , dc.rate_type as ratetype ")
	    .append(" , sum(round(dc.total_cost_amount)) as costamount ")  

/*      below code is changed for defect #4039:
 *      When an incident is in an incident group and the user posts time to an accounting code 
 *      for a different incident from the one to which a resource is assigned, the system is 
 *      incorrectly including that cost data in the data for the resource's default accounting code.
 *      The change: join isw_incident_account_code instead of isw_incident_resource  	    
//	    .append(" from isw_inc_res_daily_cost dc ")
//	    .append(" left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
//	    .append(" left join isw_incident_account_code iac on DC.INCIDENT_ACCOUNT_CODE_ID = iac.id ")
//		.append(" left join isw_account_code ac on iac.account_code_id = ac.id ")
//		.append(" , isw_incident_resource ir ")
//	    .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
//	    .append(" left join iswl_agency pa on CD.PAYMENT_AGENCY_ID = pa.id ")
//	    .append(" , isw_incident i ")
//	    .append(" left join isw_organization inc_org on i.unit_id = inc_org.id ")
//	    .append(" , isw_resource r ")
//	    .append(" left join iswl_agency ag on r.agency_id = ag.id ")
//	    .append(" , isw_work_period wp ")
//	    .append(" , isw_work_period_assignment wpa ")
//	    .append(" , isw_assignment a ")
//	    .append(" , iswl_kind k ")
//	    .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
//	    .append(" , isw_organization org ")
//	    //.append(" , isw_incident_shift s ")
//	    .append(" , iswl_request_category rc ")	   
 */
	     .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ")
         .append(" left join isw_incident_resource ir on dc.incident_resource_id = ir.id")
         .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
         .append("left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
         .append(" , isw_resource r ")
            .append(" left join isw_organization org on r.organization_id = org.id") 
            .append(" left join iswl_agency ag on r.agency_id = ag.id") 
         .append(" , isw_incident_account_code iac")
         .append(" , isw_account_code ac")
         .append(" , isw_incident i")
         .append(" , isw_work_period wp")
         .append(" , isw_work_period_assignment wpa")
         .append(" , isw_assignment a")
         .append(" , iswl_kind k")
         .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
         .append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
         .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
	    
	    
	    //#4039 .append(" where ir.incident_id = " + incidentId)
         
	    .append(" where ");
		 //if(!filter.isIncidentGroup())
		if(LongUtility.hasValue(incidentId))
			 //#4039 sql.append(" ir.incident_id = " + filter.getIncidentId());
			 //sql.append(" iac.incident_id = " + filter.getIncidentId());
			sql.append(" iac.incident_id = " + incidentId);
		 else 
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id ")
	    
		//#4039
	    //.append(" and i.id = ir.incident_id ")    	    
	    //.append(" and dc.incident_resource_id = ir.id ")
	    
		.append(" and r.id = ir.resource_id ")
	    .append("and r.parent_resource_id is null ")
	    .append(" and wp.incident_resource_id = ir.id ")
	    .append(" and wpa.work_period_id = wp.id ")
	    .append(" and a.id = wpa.assignment_id ")
	    .append(" and a.end_date is null ")
	    .append(" and k.id = a.kind_id ");
	
	    
	    if (filter.isOverhead()) 
	        sql.append(" and RC.CODE in ('O') ");
	    else
	    	sql.append(" and RC.CODE not in ('O') ");
	    
	    if(filter.isDateRangeSelected()) {
			sql.append(" and dc.ACTIVITY_DATE between ")
			   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	    }    
	    
		if(filter.getSelectedReportGroup().equals("Agency")) {
			if (!filter.getAdditionalFilterString().isEmpty())
			    sql.append(" and ag.agency_code in (" + filter.getAdditionalFilterString() + ")");
			    
		    sql.append(" group by ag.agency_code, ag.agency_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by ag.agency_code, ag.agency_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");

		}
		else if(filter.getSelectedReportGroup().equals("Payment Agency")) {
			if(filter.isResourcesWherePaymentAgencyIsBlank())
				sql.append(" and cd.payment_agency_id is null");
			else if(filter.isResourcesWherePaymentAgencyasEntry())
				sql.append(" and cd.payment_agency_id is not null");
			else if(!filter.getAdditionalFilterString().isEmpty())
				sql.append(" and pa.agency_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by pa.agency_code, pa.agency_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by pa.agency_code, pa.agency_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");

		}
		else if(filter.getSelectedReportGroup().equals("Cost Group")) {
			if(filter.isResourcesWhereCostGroupIsBlank())
				sql.append(" and dc.cost_group_id is null");
			else if(filter.isResourcesWhereCostGroupHasEntry())
				sql.append(" and dc.cost_group_id is not null");
			else if(!filter.getAdditionalFilterString().isEmpty())
				sql.append(" and cg.cost_group_name in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by CG.COST_GROUP_NAME, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by CG.COST_GROUP_NAME, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");

		}
		else if(filter.getSelectedReportGroup().equals("Unit ID")) {
			if (!filter.getAdditionalFilterString().isEmpty()) 
			   sql.append(" and org.unit_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by org.unit_code, org.organization_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by org.unit_code, org.organization_name, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");

		}
		else if(filter.getSelectedReportGroup().equals("Incident")) {
			if (!filter.getAdditionalFilterString().isEmpty())
			   sql.append(" and sgc.description in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");

		}
		else {
			if (!filter.getAdditionalFilterString().isEmpty())
			  sql.append(" and ac.account_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by ac.account_code, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		    //.append(" order by ac.account_code, k.is_direct, sgc.description, k.code, k.description, R.IS_PERSON, R.LAST_NAME, R.FIRST_NAME, R.RESOURCE_NAME, a.request_number, ag.agency_code, org.unit_code, cd.assign_date, wp.dm_release_date, wp.dm_estimated_arrival_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ");
		}
		
		///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select  ");		
		if(filter.getSelectedReportGroup().equals("Accounting Code")) {
			sql.append(" AC.ACCOUNT_CODE as grouping, ")
			.append("AC.ACCOUNT_CODE as groupingcode, ");
		}
		else if(filter.getSelectedReportGroup().equals("Agency")) {
			sql.append(" ag.agency_code || ' - ' || ag.agency_name as grouping, ")
			.append(" ag.agency_code as groupingcode, ");		
		}
		else if(filter.getSelectedReportGroup().equals("Payment Agency")) {
			sql.append(" pa.agency_code || ' - ' || pa.agency_name as grouping, ")
			.append(" pa.agency_code as groupingcode,");
		}
		else if(filter.getSelectedReportGroup().equals("Cost Group")) {
			sql.append(" CG.COST_GROUP_NAME as grouping, ")
			.append(" CG.COST_GROUP_NAME as groupingcode, ");
		}
		else if(filter.getSelectedReportGroup().equals("Unit ID")) {
			sql.append(" org.unit_code || ' - ' || org.organization_name as grouping, ")
			.append(" org.unit_code as groupingcode, ");	
		}
		sql.append("case ")
		.append("when k.is_direct = " + (isOracle ? 1 : true) + " then 'DIRECT RESOURCES' ")
		.append("else 'INDIRECT RESOURCES' ")
		.append("end as directIndirectName, ")
	    .append(" sgc.description as itemcodecategory ")
	    .append(" , k.code as itemcode ")
	    .append(" , k.description as itemcodedescription ")
	    .append(" , k.code || ' - ' || k.description as itemfulldescription ")	    
//	    .append(" , (case ")
//		.append(" when r.is_person = " + (isOracle ? 1 : true) + " then r.first_name || ' ' || r.last_name ")
//		.append(" when r.is_person = " + (isOracle ? 0 : false) + " then r.resource_name ")
//		.append(" end ) as resourcename ")
	    .append(" , ro.cost_description as resourcename ")
	    .append(" , ro.request_number as requestnumber ")
	    .append(" , ag.agency_code as agencycode    ")
	    .append(" , org.unit_code as unitid ")
	    .append(" , TO_CHAR(cd.assign_date, 'MM/DD/YYYY') AS assigndatechar ") 
	    .append(" , TO_CHAR(ro.actual_release_date, 'MM/DD/YYYY') as costenddatechar ")
	    .append(" , to_char(dc.activity_date,'MM/DD/YYYY') as activitydatechar ")
	    //.append(" , S.SHIFT_NAME as shift ")
	    .append(" , cg.description as costgroupname ")
	    .append(" , dc.unit_cost_amount as unitcost ")
	    .append(" , dc.units AS rateunits ")
	    .append(" , dc.rate_type as ratetype ")
	    .append(" , sum(round(dc.total_cost_amount)) as costamount ")  

/*      below code is changed for defect #4039:
 *      When an incident is in an incident group and the user posts time to an accounting code 
 *      for a different incident from the one to which a resource is assigned, the system is 
 *      incorrectly including that cost data in the data for the resource's default accounting code.
 *      The change: join isw_incident_account_code instead of isw_incident_resource  	    
//	    .append(" from isw_inc_res_daily_cost dc ")
//	    .append(" left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
//	    .append(" left join isw_incident_account_code iac on DC.INCIDENT_ACCOUNT_CODE_ID = iac.id ")
//		.append(" left join isw_account_code ac on iac.account_code_id = ac.id ")
//		.append(" , isw_incident_resource ir ")
//	    .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
//	    .append(" left join iswl_agency pa on CD.PAYMENT_AGENCY_ID = pa.id ")
//	    .append(" , isw_incident i ")
//	    .append(" left join isw_organization inc_org on i.unit_id = inc_org.id ")
//	    .append(" , isw_resource r ")
//	    .append(" left join iswl_agency ag on r.agency_id = ag.id ")
//	    .append(" , isw_work_period wp ")
//	    .append(" , isw_work_period_assignment wpa ")
//	    .append(" , isw_assignment a ")
//	    .append(" , iswl_kind k ")
//	    .append(" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id ")
//	    .append(" , isw_organization org ")
//	    //.append(" , isw_incident_shift s ")
//	    .append(" , iswl_request_category rc ")	   
 */
	     .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ")
         .append(" left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id")
         .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
         .append("left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
         .append(" , isw_resource_other ro ")
            .append(" left join isw_organization org on ro.organization_id = org.id") 
            .append(" left join iswl_agency ag on ro.agency_id = ag.id") 
         .append(" , isw_incident_account_code iac")
         .append(" , isw_account_code ac")
         .append(" , isw_incident i")
//         .append(" , isw_work_period wp")
//         .append(" , isw_work_period_assignment wpa")
//         .append(" , isw_assignment a")
         .append(" , iswl_kind k")
         .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
         .append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
         .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
	    
	    
	    //#4039 .append(" where ir.incident_id = " + incidentId)
         
	    .append(" where ");
		 //if(!filter.isIncidentGroup())
		if(LongUtility.hasValue(incidentId))
			 //#4039 sql.append(" ir.incident_id = " + filter.getIncidentId());
			 //sql.append(" iac.incident_id = " + filter.getIncidentId());
			sql.append(" iac.incident_id = " + incidentId);
		 else 
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id ")
	    
		//#4039
	    //.append(" and i.id = ir.incident_id ")    	    
	    //.append(" and dc.incident_resource_id = ir.id ")
	    
		.append(" and ro.id = iro.resource_other_id ")
	    //.append("and r.parent_resource_id is null ")
	    //.append(" and wp.incident_resource_id = ir.id ")
	    //.append(" and wpa.work_period_id = wp.id ")
	    //.append(" and a.id = wpa.assignment_id ")
	    //.append(" and a.end_date is null ")
	    .append(" and k.id = ro.kind_id ");
	
	    
	    if (filter.isOverhead()) 
	        sql.append(" and RC.CODE in ('O') ");
	    else
	    	sql.append(" and RC.CODE not in ('O') ");
	    
	    if(filter.isDateRangeSelected()) {
			sql.append(" and dc.ACTIVITY_DATE between ")
			   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	    }    
	    
		if(filter.getSelectedReportGroup().equals("Agency")) {
			if (!filter.getAdditionalFilterString().isEmpty())
			    sql.append(" and ag.agency_code in (" + filter.getAdditionalFilterString() + ")");
			    
		    sql.append(" group by ag.agency_code, ag.agency_name, k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
		    .append(" order by groupingcode, grouping, directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");

		}
		else if(filter.getSelectedReportGroup().equals("Payment Agency")) {
			if(filter.isResourcesWherePaymentAgencyIsBlank())
				sql.append(" and cd.payment_agency_id is null");
			else if(filter.isResourcesWherePaymentAgencyasEntry())
				sql.append(" and cd.payment_agency_id is not null");
			else if(!filter.getAdditionalFilterString().isEmpty())
				sql.append(" and pa.agency_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by pa.agency_code, pa.agency_name, k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
		    .append(" order by groupingcode, grouping, directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");

		}
		else if(filter.getSelectedReportGroup().equals("Cost Group")) {
			if(filter.isResourcesWhereCostGroupIsBlank())
				sql.append(" and dc.cost_group_id is null");
			else if(filter.isResourcesWhereCostGroupHasEntry())
				sql.append(" and dc.cost_group_id is not null");
			else if(!filter.getAdditionalFilterString().isEmpty())
				sql.append(" and cg.cost_group_name in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by CG.COST_GROUP_NAME, k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
		    .append(" order by grouping, directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");

		}
		else if(filter.getSelectedReportGroup().equals("Unit ID")) {
			if (!filter.getAdditionalFilterString().isEmpty()) 
			   sql.append(" and org.unit_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by org.unit_code, org.organization_name, k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
		    .append(" order by groupingcode, grouping, directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");

		}
		else if(filter.getSelectedReportGroup().equals("Incident")) {
			if (!filter.getAdditionalFilterString().isEmpty())
			   sql.append(" and sgc.description in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
			.append(" order by directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");


		}
		else {
			if (!filter.getAdditionalFilterString().isEmpty())
			  sql.append(" and ac.account_code in (" + filter.getAdditionalFilterString() + ")");
			
			sql.append(" group by ac.account_code, k.is_direct, sgc.description, k.code, k.description, ro.cost_description, ro.request_number, ag.agency_code, org.unit_code, cd.assign_date, ro.actual_release_date, dc.activity_date, cg.description, dc.unit_cost_amount, dc.units, dc.rate_type ")
		    .append(" order by groupingcode, directIndirectName, itemcodecategory, itemcode, itemcodedescription, resourcename, requestnumber, agencycode, unitid, assigndatechar, costenddatechar, activitydatechar, costgroupname, unitcost, rateunits, ratetype ");
		}       
	    		
		return sql.toString();
	}
}
