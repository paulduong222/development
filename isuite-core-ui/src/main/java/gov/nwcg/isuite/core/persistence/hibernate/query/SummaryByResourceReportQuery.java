package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;



public class SummaryByResourceReportQuery extends CostReportQuery{

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
//	public static String getSummaryByResourceReportDataQuery(CostReportFilter filter, Long incidentId, Boolean isOracle, String incidentType) {
//		StringBuffer sql = new StringBuffer();
//	
//		sql.append("select ")
//		.append("distinct(r.id) as resourceId, ");
//		if(filter.getGroupingField().equals("Accounting Code")){
//			sql.append("'Accounting Code' as grouping, ")
//			.append("AC.ACCOUNT_CODE as groupingdata, ");
//		} else if(filter.getGroupingField().equals("Agency")){
//			sql.append("'Agency' as grouping, ")
//			.append("ra.agency_name as groupingdata, ");
//		} else if(filter.getGroupingField().equals("Payment Agency")){
//			sql.append("'Payment Agency' as grouping, ")
//			.append("PA.AGENCY_NAME as groupingdata, ");
//		} else if(filter.getGroupingField().equals("Cost Group")){
//			sql.append("'Cost Group' as grouping, ")
//			.append("CG.COST_GROUP_NAME as groupingdata, ");
//		} else if(filter.getGroupingField().equals("Unit ID")){
//			sql.append("'Unit ID' as grouping, ")
//			.append("o.unit_code as groupingdata, ");
//		} else {
//			sql.append("'Cost Sub-Group Category' as grouping, ")
//			.append("'' AS groupingdata, ");
//		}
//		sql.append("TO_CHAR(dc.ACTIVITY_DATE, 'MM/DD/YYYY') AS activitydatechar, ")
//		.append("a.request_number as requestnum, ");
//		if(incidentType == "WF"){
//			sql.append("case ")
//			.append("when k.is_direct = " + (isOracle ? 1 : true) + " then 'DIRECT' ")
//			.append("else 'INDIRECT' ")
//			.append("end as grouping1, ");
//		} else {
//			sql.append("'' as grouping1, ");
//		}
//		sql.append("kg.description as grouping2, ")
//		.append("sgc.description as grouping3, ")
//		.append("sum(dc.total_cost_amount) as cost ")
//		.append("from ")
//		.append("isw_inc_res_daily_cost dc ")
//		.append("left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
//		.append("left join isw_incident_account_code iac on DC.INCIDENT_ACCOUNT_CODE_ID = iac.id ")
//		.append("left join isw_account_code ac on iac.account_code_id = ac.id ")
//		.append(", isw_incident_resource ir ")
//		.append("left join isw_cost_data cd on ir.cost_data_id = cd.id ")
//		.append("left join iswl_agency pa on CD.PAYMENT_AGENCY_ID = pa.id ")
//		.append(", isw_resource r ")
//		.append("left join iswl_agency ra on R.AGENCY_ID = ra.id ")
//		.append(", isw_organization o ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", iswl_kind k ")
//		.append(", iswl_sub_group_category sgc ")
//		.append(", iswl_kind_group kg ")
//		.append(", iswl_request_category rc ")
//		.append("where ")
//		.append("ir.incident_id = " + incidentId + " ");
//		if(filter.getStartDate() != null && filter.getEndDate() != null ){
//			sql.append("and dc.ACTIVITY_DATE between ")
//			.append("to_date('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ")
//			.append("and to_date('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ");
//		}
//		sql.append("and dc.incident_resource_id = ir.id ")
//		.append("and r.id = ir.resource_id ")
//		.append("and o.id = R.ORGANIZATION_ID ")
//		.append("and r.parent_resource_id is null ")
//		.append("and wp.incident_resource_id = ir.id ")
//		.append("and wpa.work_period_id = wp.id ")
//		.append("and a.id = wpa.assignment_id ")
//		.append("and a.end_date is null ")
//		.append("and k.id = a.kind_id ")
//		.append("and sgc.id = K.SUB_GROUP_CATEGORY_ID ")
//		.append("and kg.id = k.kind_group_id ")
//		.append("and rc.id = K.REQUEST_CATEGORY_ID ");
//		if(filter.getReportType().equals("Non Overhead")){
//			sql.append(" and RC.CODE not in ('O') ");
//		} else {
//			sql.append(" and RC.CODE in ('O') ");
//		}
//		if(filter.getGroupingField().equals("Accounting Code") && filter.getGroupingSelections() != null && !filter.getGroupingSelections().isEmpty()){
//			sql.append("and AC.ACCOUNT_CODE IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//		} else if(filter.getGroupingField().equals("Agency") && filter.getGroupingSelections() != null && filter.getGroupingSelections().isEmpty()){
//			sql.append("and ra.agency_code IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//		} else if(filter.getGroupingField().equals("Payment Agency")){
//			if(filter.getGroupingSelections() != null && !filter.getGroupingSelections().isEmpty())
//				sql.append("and PA.AGENCY_CODE IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//			else if(filter.getAdditionalFilterType().equals("Resources where Payment Agency is Blank"))
//				sql.append("and PA.AGENCY_CODE = '' ");
//			else
//				sql.append("and PA.AGENCY_CODE != '' ");
//		} else if(filter.getGroupingField().equals("Cost Group")){
//			if(filter.getGroupingSelections() != null && !filter.getGroupingSelections().isEmpty())
//				sql.append("and CG.COST_GROUP_NAME IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//			else if(filter.getAdditionalFilterType().equals("Resources where Cost Group is Blank"))
//				sql.append("and CG.COST_GROUP_NAME = '' ");
//			else
//				sql.append("and CG.COST_GROUP_NAME != '' ");
//		} else if(filter.getGroupingField().equals("Unit ID")){
//			if(filter.getGroupingSelections() != null && !filter.getGroupingSelections().isEmpty())
//				sql.append("and o.unit_code IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//			//NEED TO ADD IN CODE FOR LOCAL UNIT ID ONLY. APPARENTLY THIS IS A FUTURE CHANGE
//			//filter.getLocalUnitId()
//		} else {
//			if(filter.getGroupingSelections() != null && !filter.getGroupingSelections().isEmpty())
//				sql.append("and sgc.code IN ('" + StringUtils.join(filter.getGroupingSelections().toArray(),"','") + "') ");
//		}
//
//		sql.append("group by ")
//		.append("r.id, rc.code, rc.description, k.id, k.code, kg.code, kg.description, sgc.code, sgc.description,k.is_direct, o.unit_code, o.organization_name ")
//		.append(", CG.COST_GROUP_NAME, pa.agency_name, pa.agency_code, ra.agency_code, ra.agency_name, ac.account_code, dc.ACTIVITY_DATE,a.request_number ")
//		.append("order by ")
//		.append("groupingdata,grouping1,grouping2,grouping3");
//				
//		return sql.toString();
//	}
	
	public static String getSummaryByResourceReportDataQuery(GroupCategoryTotalReportFilter filter,boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("SELECT ")
		.append("distinct(r.id) as resourceId ")
		.append(", i.id as incidentId")
		.append(", i.incident_name as incidentName");
		
		if(filter.getReportGroupForQuery().equals("Date")) 
           	sql.append(", " + getGroupByString(filter.getReportGroupForQuery()) + "as groupByDate");
		
        //if(!filter.selectedReportGroup.equals("Incident")) 
           	sql.append(", " + getGroupByString(filter) + "as groupBy");
        		
		sql.append(", case ")
		.append("when r.is_person = " + (isOracle ? 1 : true) + " then (r.last_name||', '||r.first_name) ")
		.append("else r.resource_name ")
		.append("end as resourceName ");
		
//		if(filter.isNonoverhead()){
//			sql.append(", r.resource_name as resourceName ");
//		} else {
//			sql.append(", (r.last_name||', '||r.first_name) as resourceName");
//		}
		
		sql.append(", a.request_number as requestnum, ")
		.append("case ")
		.append("when k.is_direct = " + (isOracle ? 1 : true) + " then 'DIRECT' ")
		.append("else 'INDIRECT' ")
		.append("end as directIndirectName, ")  //as grouping1  
		.append("k.description as kindGroupDescription, ")  //as grouping2
		.append("k.code as itemCode, ")
		//.append("sgc.description as subGroupCatDescription, ")  //grouping3
		.append("sum(round(dc.total_cost_amount)) as costAmount ")

/*      below code is changed for defect #4039:
 *      When an incident is in an incident group and the user posts time to an accounting code 
 *      for a different incident from the one to which a resource is assigned, the system is 
 *      incorrectly including that cost data in the data for the resource's default accounting code.
 *      The change: join isw_incident_account_code instead of isw_incident_resource  			
//		.append("FROM ")
//		.append("isw_inc_res_daily_cost dc ")
//		.append("left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
//		.append("left join isw_incident_account_code iac on DC.INCIDENT_ACCOUNT_CODE_ID = iac.id ")
//		.append("left join isw_account_code ac on iac.account_code_id = ac.id ")
//		.append(", isw_incident_resource ir ")
//		.append("left join isw_cost_data cd on ir.cost_data_id = cd.id ")
//		.append("left join iswl_agency pa on CD.PAYMENT_AGENCY_ID = pa.id ")
//		.append(", isw_resource r ")
//		.append("left join iswl_agency ra on R.AGENCY_ID = ra.id ")
//		.append(", isw_organization o ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", iswl_kind k ")
//		.append(", iswl_sub_group_category sgc ")
//		.append(", iswl_kind_group kg ")
//		.append(", iswl_request_category rc ")
//		.append(", isw_incident i")
*/		
		 .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ");
//      added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");		
		
         sql.append(" left join isw_incident_resource ir on dc.incident_resource_id = ir.id")
         .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
         .append(" , isw_resource r ")
            .append(" left join isw_organization o on r.organization_id = o.id") 
            .append(" left join iswl_agency ra on r.agency_id = ra.id") 
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
        
		
		.append(" WHERE ");
		
		 if(!filter.isIncidentGroup())
			 //sql.append(" ir.incident_id = " + filter.getIncidentId());
		 	 sql.append(" iac.id in (select id from isw_incident_account_code where incident_id = " + filter.getIncidentId() + ") ");
		 else 
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id ");
		 
		//sql.append(" and i.id = ir.incident_id ");
		
		if(filter.isDateRangeSelected() ){
			sql.append("and dc.ACTIVITY_DATE between ")
			.append("to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ")
			.append("and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ");
		}
		
		sql.append("and r.id = ir.resource_id ")
		.append("and r.parent_resource_id is null ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and a.end_date is null ")
		.append("and k.id = a.kind_id ");

		
		if(filter.isNonoverhead()){
			sql.append(" and RC.CODE not in ('O') ");
		} else {
			sql.append(" and RC.CODE in ('O') ");
		}
		
		if(!getAdditionalFilterString(filter).isEmpty())
			 sql.append(getAdditionalFilterString(filter));
		 
		sql.append(" group by ")
		.append("r.id, i.id, i.incident_name, rc.code, rc.description, k.code, k.description, k.is_direct, a.request_number");
		
		if(filter.getReportGroupForQuery().equals("Date")) 
			sql.append(", " + getGroupByString(filter.getReportGroupForQuery()));
		
		//if(!filter.selectedReportGroup.equals("Incident")) 
			sql.append(", " + getGroupByString(filter.getSelectedReportGroup()));
		
//		if(filter.isNonoverhead()){
//			sql.append(", r.resource_name ");
//		} else {
//			sql.append(", (r.last_name||', '||r.first_name)");
//		}
		
		sql.append(",case ")
		.append("when r.is_person = " + (isOracle ? 1 : true) + " then (r.last_name||', '||r.first_name) ")
		.append("else r.resource_name end");
		
//		sql.append(" ORDER BY ");
//		if(!filter.selectedReportGroup.equals("Incident")) 
//			sql.append(getGroupByString(filter.getSelectedReportGroup())+",");
//		if(filter.getReportGroupForQuery().equals("Date")) 
//			sql.append("groupByDate,");
//		sql.append("directIndirectName,kindGroupDescription");
		
		
		 ///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("SELECT ")
		.append("distinct(ro.id) as resourceId ")
		.append(", i.id as incidentId")
		.append(", i.incident_name as incidentName");
		
		if(filter.getReportGroupForQuery().equals("Date")) 
           	sql.append(", " + getGroupByString(filter.getReportGroupForQuery()) + "as groupByDate");
		
        //if(!filter.selectedReportGroup.equals("Incident")) 
           	sql.append(", " + getGroupByString(filter) + "as groupBy");
		
//		sql.append(", case ")
//		.append("when r.is_person = " + (isOracle ? 1 : true) + " then (r.last_name||', '||r.first_name) ")
//		.append("else r.resource_name ")
//		.append("end as resourceName ");
		
//		if(filter.isNonoverhead()){
//			sql.append(", r.resource_name as resourceName ");
//		} else {
//			sql.append(", (r.last_name||', '||r.first_name) as resourceName");
//		}
        sql.append(", ro.cost_description as resourceName ");
		sql.append(", ro.request_number as requestnum, ")
		.append("case ")
		.append("when k.is_direct = " + (isOracle ? 1 : true) + " then 'DIRECT' ")
		.append("else 'INDIRECT' ")
		.append("end as directIndirectName, ")  //as grouping1  
		.append("k.description as kindGroupDescription, ")  //as grouping2
		.append("k.code as itemCode, ")
		//.append("sgc.description as subGroupCatDescription, ")  //grouping3
		.append("sum(round(dc.total_cost_amount)) as costAmount ")

/*      below code is changed for defect #4039:
 *      When an incident is in an incident group and the user posts time to an accounting code 
 *      for a different incident from the one to which a resource is assigned, the system is 
 *      incorrectly including that cost data in the data for the resource's default accounting code.
 *      The change: join isw_incident_account_code instead of isw_incident_resource  			
//		.append("FROM ")
//		.append("isw_inc_res_daily_cost dc ")
//		.append("left join isw_cost_group cg on DC.COST_GROUP_ID = cg.id ")
//		.append("left join isw_incident_account_code iac on DC.INCIDENT_ACCOUNT_CODE_ID = iac.id ")
//		.append("left join isw_account_code ac on iac.account_code_id = ac.id ")
//		.append(", isw_incident_resource ir ")
//		.append("left join isw_cost_data cd on ir.cost_data_id = cd.id ")
//		.append("left join iswl_agency pa on CD.PAYMENT_AGENCY_ID = pa.id ")
//		.append(", isw_resource r ")
//		.append("left join iswl_agency ra on R.AGENCY_ID = ra.id ")
//		.append(", isw_organization o ")
//		.append(", isw_work_period wp ")
//		.append(", isw_work_period_assignment wpa ")
//		.append(", isw_assignment a ")
//		.append(", iswl_kind k ")
//		.append(", iswl_sub_group_category sgc ")
//		.append(", iswl_kind_group kg ")
//		.append(", iswl_request_category rc ")
//		.append(", isw_incident i")
*/		
		 .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ");
//      added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");		
		
         sql.append(" left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id")
         .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
         .append(" , isw_resource_other ro ")
            .append(" left join isw_organization o on ro.organization_id = o.id") 
            .append(" left join iswl_agency ra on ro.agency_id = ra.id") 
         .append(" , isw_incident_account_code iac")
         .append(" , isw_account_code ac")
         .append(" , isw_incident i")
         .append(" , iswl_kind k")
         .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
         .append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
         .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
        
		
		.append(" WHERE ");
		
		 if(!filter.isIncidentGroup())
			 sql.append(" iro.incident_id = " + filter.getIncidentId());
		 else 
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id ");
		 
		//sql.append(" and i.id = ir.incident_id ");
		
		if(filter.isDateRangeSelected() ){
			sql.append("and dc.ACTIVITY_DATE between ")
			.append("to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ")
			.append("and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ");
		}
		
		sql.append("and ro.id = iro.resource_other_id ")
		.append("and k.id = ro.kind_id ");

		
		if(filter.isNonoverhead()){
			sql.append(" and RC.CODE not in ('O') ");
		} else {
			sql.append(" and RC.CODE in ('O') ");
		}
		
		if(!getAdditionalFilterString(filter).isEmpty())
			 sql.append(getAdditionalFilterString(filter));
		 
		sql.append(" group by ")
		.append("ro.id, i.id, i.incident_name, rc.code, rc.description, k.code, k.description, k.is_direct, ro.request_number");
		
		if(filter.getReportGroupForQuery().equals("Date")) 
			sql.append(", " + getGroupByString(filter.getReportGroupForQuery()));
		
		//if(!filter.selectedReportGroup.equals("Incident")) 
			sql.append(", " + getGroupByString(filter.getSelectedReportGroup()));
		
//		if(filter.isNonoverhead()){
//			sql.append(", r.resource_name ");
//		} else {
//			sql.append(", (r.last_name||', '||r.first_name)");
//		}
		
//		sql.append(",case ")
//		.append("when r.is_person = " + (isOracle ? 1 : true) + " then (r.last_name||', '||r.first_name) ")
//		.append("else r.resource_name end");
		sql.append(",ro.cost_description ");
		
		sql.append(" ORDER BY ");
		if(!filter.selectedReportGroup.equals("Incident")) 
			sql.append("groupBy,");
		if(filter.getReportGroupForQuery().equals("Date")) 
			sql.append("groupByDate,");
		sql.append("directIndirectName,kindGroupDescription");
		
		return sql.toString();
	}
}
