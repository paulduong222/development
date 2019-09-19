package gov.nwcg.isuite.core.persistence.hibernate.query;


import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;


public class GroupCategoryTotalReportQuery extends CostReportQuery {
	
	public static String getGroupCategoryTotalReportDataQuery(GroupCategoryTotalReportFilter filter, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("SELECT CASE WHEN k.is_direct = "+ (isOracleDialect ? 1 : Boolean.TRUE) +" then 'DIRECT'")
			.append(" ELSE 'INDIRECT' END AS directIndirectName")
			
			.append(", kg.description as kindGroupDescription")
            .append(", sgc.description as subGroupCatDescription");
            
            if(filter.reportGroupForQuery.equals("Date")) 
            	sql.append(", " + getGroupByString(filter.reportGroupForQuery) + "as groupByDate");
            	
            //if(filter.selectedReportGroup.equals("Incident")){
        		//sql.append(", case when k.is_Direct= "+(isOracleDialect ? 1: Boolean.TRUE) + " then 'Incident' else 'Incident' end as groupBy ");
            
            if(!filter.selectedReportGroup.equals("Incident"))
        		sql.append(", " + getGroupByString(filter.selectedReportGroup) + "as groupBy");
            else
            	sql.append(", i.incident_name as groupBy");
            
            sql.append(", SUM(dc.total_cost_amount) as costAmount")
            .append(", i.incident_name as incidentName")
            .append(", i.id as incidentId")
            
		 .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ");
//         change for defect 4039		 
//         .append(" left join isw_cost_group cg on dc.cost_group_id = cg.id")
//         .append(" left join isw_incident_account_code iac on dc.incident_account_code_id = iac.id")
//         .append(" left join isw_account_code ac on iac.account_code_id = ac.id")
		 
//         added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");
		   
           sql.append(" left join isw_incident_resource ir on dc.incident_resource_id = ir.id")
           .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
           .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
           
//         change for defect 4039	         
//         .append(" , isw_incident_resource ir")
//         .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id")
//         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id")
         .append(" , isw_resource r ")
            .append(" left join isw_organization o on r.organization_id = o.id") 
            .append(" left join iswl_agency ra on r.agency_id = ra.id") 
            
//         change for defect 4039	         
//       .append(" , isw_incident i")
         .append(" , isw_incident_account_code iac")
         .append(" , isw_account_code ac")
         .append(" , isw_incident i")
         
         .append(" , isw_work_period wp")
         .append(" , isw_work_period_assignment wpa")
         .append(" , isw_assignment a")
         
         .append(" , iswl_kind k")
         .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
         //.append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
         .append(" left join iswl_group_category kg on K.GROUP_CATEGORY_ID = kg.id")
         .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
         
		 .append(" WHERE ");
	
		 if(!filter.isIncidentGroup())
			 //sql.append(" ir.incident_id = " + filter.getIncidentId());
		 	sql.append(" iac.id in (select id from isw_incident_account_code where incident_id = " + filter.getIncidentId() +") ");
		 else
	         //change for defect 4039	
			 //sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 
		 // is accounting code required for all the cost reports?
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id")
		 
//         change for defect 4039			 
//		 .append(" and i.id = ir.incident_id ")
//		 .append(" and dc.incident_resource_id = ir.id ")
		 
		 .append(" and ir.id = dc.incident_resource_id ")
		 .append(" and r.id = ir.resource_id ")
		 .append(" and r.parent_resource_id is null ")
		 .append(" and wp.incident_resource_id = ir.id")
		 .append(" and wpa.work_period_id = wp.id")
		 .append(" and a.id = wpa.assignment_id")
		 .append(" and a.end_date is null")
		 .append(" and k.id = a.kind_id");
		 
		 if(!getAdditionalFilterString(filter).isEmpty())
			 sql.append(getAdditionalFilterString(filter));
		 
		 if(filter.isDateRangeSelected()) {
				sql.append(" and dc.ACTIVITY_DATE between ")
				   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
				   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		 
//		 if(filter.isDateRangeSelected()) {
//				sql.append(" and dc.ACTIVITY_DATE between ")
//				   .append(" to_date('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
//				   .append(" and to_date('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
//		}
		 
		 sql.append(" GROUP BY  i.id,i.incident_name,k.is_direct,kg.description, sgc.description");
		 
		 if(filter.reportGroupForQuery.equals("Date")) 
         	sql.append(", " + getGroupByString(filter.reportGroupForQuery));
         	
         if(!filter.selectedReportGroup.equals("Incident"))
     		sql.append(", " + getGroupByString(filter.selectedReportGroup));
		 		 
//		 sql.append(" ORDER BY ");
//		 if(!filter.selectedReportGroup.equals("Incident"))
//	     		sql.append(getGroupByString(filter.selectedReportGroup) + ", ");
//		 
//		 if(filter.getReportGroupForQuery().equals("Date")) 
//			sql.append(" groupByDate ,");
//		 
//		 sql.append(" i.id,sgc.description,kg.description, k.is_direct");
		 
		 ///////////////////////////resource_other////////////////////////////////////
         
         sql.append(" UNION ");
		 
			sql.append("SELECT CASE WHEN k.is_direct = "+ (isOracleDialect ? 1 : Boolean.TRUE) +" then 'DIRECT'")
			.append(" ELSE 'INDIRECT' END AS directIndirectName")
			
			.append(", kg.description as kindGroupDescription")
            .append(", sgc.description as subGroupCatDescription");
            
            if(filter.reportGroupForQuery.equals("Date")) 
            	sql.append(", " + getGroupByString(filter.reportGroupForQuery) + "as groupByDate");
            	
            //if(filter.selectedReportGroup.equals("Incident")){
        		//sql.append(", case when k.is_Direct= "+(isOracleDialect ? 1: Boolean.TRUE) + " then 'Incident' else 'Incident' end as groupBy ");
            
            if(!filter.selectedReportGroup.equals("Incident"))
        		sql.append(", " + getGroupByString(filter.selectedReportGroup) + "as groupBy");
            else
            	sql.append(", i.incident_name as groupBy");
            
            sql.append(", SUM(round(dc.total_cost_amount)) as costAmount")
            .append(", i.incident_name as incidentName")
            .append(", i.id as incidentId")
            
		 .append(" FROM ")
		 .append(" isw_inc_res_daily_cost dc ");
//         change for defect 4039		 
//         .append(" left join isw_cost_group cg on dc.cost_group_id = cg.id")
//         .append(" left join isw_incident_account_code iac on dc.incident_account_code_id = iac.id")
//         .append(" left join isw_account_code ac on iac.account_code_id = ac.id")
		 
//         added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");
		   
           sql.append(" left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id")
           .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
           .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
           
//         change for defect 4039	         
//         .append(" , isw_incident_resource ir")
//         .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id")
//         .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id")
         .append(" , isw_resource_other ro ")
            .append(" left join isw_organization o on ro.organization_id = o.id") 
            .append(" left join iswl_agency ra on ro.agency_id = ra.id") 
            
//         change for defect 4039	         
//       .append(" , isw_incident i")
         .append(" , isw_incident_account_code iac")
         .append(" , isw_account_code ac")
         .append(" , isw_incident i")
         
//         .append(" , isw_work_period wp")
//         .append(" , isw_work_period_assignment wpa")
//         .append(" , isw_assignment a")
         
         .append(" , iswl_kind k")
         .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
        // .append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
         .append(" left join iswl_group_category kg on K.GROUP_CATEGORY_ID = kg.id")
         .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
         
		 .append(" WHERE ");
	
		 if(!filter.isIncidentGroup())
			 sql.append(" iro.incident_id = " + filter.getIncidentId());
		 else
	         //change for defect 4039	
			 //sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 
		 // is accounting code required for all the cost reports?
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id")
		 
//         change for defect 4039			 
//		 .append(" and i.id = ir.incident_id ")
//		 .append(" and dc.incident_resource_id = ir.id ")
		 
		 .append(" and ro.id = iro.resource_other_id ")
		 //.append(" and r.parent_resource_id is null ")
		 //.append(" and wp.incident_resource_id = ir.id")
		 //.append(" and wpa.work_period_id = wp.id")
		 //.append(" and a.id = wpa.assignment_id")
		 //.append(" and a.end_date is null")
		 .append(" and k.id = ro.kind_id");
		 
		 if(!getAdditionalFilterString(filter).isEmpty())
			 sql.append(getAdditionalFilterString(filter));
		 
		 if(filter.isDateRangeSelected()) {
				sql.append(" and dc.ACTIVITY_DATE between ")
				   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
				   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		 
//		 if(filter.isDateRangeSelected()) {
//				sql.append(" and dc.ACTIVITY_DATE between ")
//				   .append(" to_date('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
//				   .append(" and to_date('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
//		}
		 
		 sql.append(" GROUP BY  i.id,i.incident_name,k.is_direct,kg.description, sgc.description");
		 
		 if(filter.reportGroupForQuery.equals("Date")) 
         	sql.append(", " + getGroupByString(filter.reportGroupForQuery));
         	
         if(!filter.selectedReportGroup.equals("Incident"))
     		sql.append(", " + getGroupByString(filter.selectedReportGroup));
		 		 
		 sql.append(" ORDER BY ");
		 if(!filter.selectedReportGroup.equals("Incident"))
	     		//sql.append(getGroupByString(filter.selectedReportGroup) + ", ");
			 sql.append(" groupBy ,");
		 
		 
		 if(filter.getReportGroupForQuery().equals("Date")) 
			sql.append(" groupByDate ,");
		 
		 sql.append(" incidentId, subGroupCatDescription, kindGroupDescription, directIndirectName ");
		 
		 return sql.toString();
	}
	
	public static String getGroupCategoryTotalReportChartDataQuery(GroupCategoryTotalReportFilter filter, boolean isOracleDialectIn) {
		StringBuffer sql = new StringBuffer();
		
		//add select from table1 form UNION group
		sql.append("select incidentId,groupname,SUM(totalAmount) as totalAmount from (");	
		
		if(filter.getSelectedGraphGroup().equals("Direct/Indirect") && isOracleDialectIn) 
			sql.append(selectedGroupMap.get("Direct/Indirect-Oracle"));
		else 
		    sql.append(selectedGroupMap.get(filter.getSelectedGraphGroup()));
			
		//Cost Sub-Group Category, Cost Group Category
	       sql.append(", i.id as incidentId") 
		   .append(", SUM(round(dc.total_cost_amount)) as totalAmount")		   
		   .append(" FROM ")
		   .append(" isw_inc_res_daily_cost dc ");
//			defect #4039		   
//		   .append(" left join isw_cost_group cg on dc.cost_group_id = cg.id")
//		   .append(" left join isw_incident_account_code iac on dc.incident_account_code_id = iac.id")
//		   .append(" left join isw_account_code ac on iac.account_code_id = ac.id")
		   
//         added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");
	       
		   sql.append(" left join isw_incident_resource ir on dc.incident_resource_id = ir.id")
           .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id ")
           .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
//			defect: 4039		  
//		   .append(" , isw_incident_resource ir")
//		   .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id")
//		   .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id")
           
           .append(" , isw_resource r ")
           .append(" left join isw_organization o on r.organization_id = o.id") 
           .append(" left join iswl_agency ra on r.agency_id = ra.id") 
           
		   .append(" , isw_incident i")
		   .append(" , isw_incident_account_code iac")
		   .append(" , isw_account_code ac")
		   
		   .append(" , isw_work_period wp")
		   .append(" , isw_work_period_assignment wpa")
		   .append(" , isw_assignment a")
		   .append(" , iswl_kind k")
		   .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
           //.append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
		   .append(" left join iswl_group_category kg on K.GROUP_CATEGORY_ID = kg.id")
           .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
		   
		 .append(" WHERE ");
		 
		 if(!filter.isIncidentGroup())
			 //sql.append(" ir.incident_id = " + filter.getIncidentId());
		 	sql.append(" iac.id in (select id from isw_incident_account_code where incident_id = " + filter.getIncidentId() +") ");
		 else 
			 // defect: 4039
			 //sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		 
		 // defect 4039
		 //sql.append(" and i.id = ir.incident_id ")
		 //.append(" and dc.incident_resource_id = ir.id ")
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id")
		 
		 .append(" and r.id = ir.resource_id ")
		 .append(" and r.parent_resource_id is null ")
		 .append(" and wp.incident_resource_id = ir.id")
		 .append(" and wpa.work_period_id = wp.id")
		 .append(" and a.id = wpa.assignment_id")
		 .append(" and a.end_date is null")
		 .append(" and k.id = a.kind_id");
		 
         sql.append(getAdditionalFilterString(filter));
         
         if(filter.isDateRangeSelected()) {
				sql.append(" and dc.ACTIVITY_DATE between ")
				   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
				   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
		 
//		 if(filter.isDateRangeSelected()) {
//				sql.append(" and dc.ACTIVITY_DATE between ")
//				   .append(" to_date('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
//				   .append(" and to_date('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
//		}
	
		sql.append(" GROUP BY " + orderByMap.get(filter.getSelectedGraphGroup()) + ", i.id");
		
        ///////////////////////////resource_other////////////////////////////////////
        sql.append(" UNION ");
        
		if(filter.getSelectedGraphGroup().equals("Direct/Indirect") && isOracleDialectIn) 
			sql.append(selectedGroupMap.get("Direct/Indirect-Oracle"));
		else 
		    sql.append(selectedGroupMap.get(filter.getSelectedGraphGroup()));
			
		//Cost Sub-Group Category, Cost Group Category
	       sql.append(", i.id as incidentId") 
		   .append(", SUM(dc.total_cost_amount) as totalAmount")	       
		   .append(" FROM ")
		   .append(" isw_inc_res_daily_cost dc ");
//			defect #4039		   
//		   .append(" left join isw_cost_group cg on dc.cost_group_id = cg.id")
//		   .append(" left join isw_incident_account_code iac on dc.incident_account_code_id = iac.id")
//		   .append(" left join isw_account_code ac on iac.account_code_id = ac.id")
		   
//         added back cost group since it will cause server error - bill	
		   if(filter.getSelectedReportGroup().equals("Cost Group"))
			   sql.append(" left join isw_cost_group cg on dc.cost_group_id = cg.id");
	       
		   sql.append(" left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id")
           .append(" left join isw_cost_data cd on iro.cost_data_id = cd.id ")
           .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id ")  
//			defect: 4039		  
//		   .append(" , isw_incident_resource ir")
//		   .append(" left join isw_cost_data cd on ir.cost_data_id = cd.id")
//		   .append(" left join iswl_agency pa on cd.payment_agency_id = pa.id")
           
           .append(" , isw_resource_other ro ")
           .append(" left join isw_organization o on ro.organization_id = o.id") 
           .append(" left join iswl_agency ra on ro.agency_id = ra.id") 
           
		   .append(" , isw_incident i")
		   .append(" , isw_incident_account_code iac")
		   .append(" , isw_account_code ac")
		   .append(" , iswl_kind k")
		   .append(" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
           //.append(" left join iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
           .append(" left join iswl_group_category kg on K.GROUP_CATEGORY_ID = kg.id")
           .append(" left join iswl_request_category rc on k.request_category_id = rc.id")
		   
		 .append(" WHERE ");
		 
		 if(!filter.isIncidentGroup())
			 sql.append(" iro.incident_id = " + filter.getIncidentId());
		 else 
			 // defect: 4039
			 //sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
			 sql.append(" iac.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.getIncidentGroupId()+ ")");
		 
		 // defect 4039
		 //sql.append(" and i.id = ir.incident_id ")
		 //.append(" and dc.incident_resource_id = ir.id ")
		 sql.append(" and dc.incident_account_code_id = iac.id")
		 .append(" and ac.id = iac.account_code_id")
		 .append(" and i.id = iac.incident_id")
		 
		 .append(" and ro.id = iro.resource_other_id ")
		 //.append(" and r.parent_resource_id is null ")
		 .append(" and k.id = ro.kind_id");
		 
         sql.append(getAdditionalFilterString(filter));
         
         if(filter.isDateRangeSelected()) {
				sql.append(" and dc.ACTIVITY_DATE between ")
				   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
				   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
         
        if(isOracleDialectIn) 
        	sql.append(" GROUP BY " + orderByMap.get(filter.getSelectedGraphGroup()) + ", i.id)");
        else
        	sql.append(" GROUP BY " + orderByMap.get(filter.getSelectedGraphGroup()) + ", i.id) as table1");
		
		sql.append(" GROUP BY incidentId, groupname");
		sql.append(" ORDER BY incidentId, groupName");

		return sql.toString();
	}
}
