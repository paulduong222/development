package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gov.nwcg.isuite.core.reports.filter.CostProjectionReportFilter;
import gov.nwcg.isuite.framework.util.LongUtility;

public class CostProjectionQuery {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
	public static String getProjectionItemDataQuery(Long incidentId, Long incidentGroupId, boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT k.id as itemId ") 
		    .append("        , case  ")
            .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'OD' ")
		    .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		    .append("		      else k.code ")
		    .append("		  end as itemCodeGroup ")
		    .append("		, case  ")
		    .append("             when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		    //.append("             when rc.code!='O' and k.is_direct="+(isOracle ? 0 : false ) +" then " + (isOracle ? 1 : true))
		    .append("		      else "+(isOracle ? 0 : false)+" ")
		    .append("       end as supportCost ")
		    .append("		,k.description as costName ")
		    .append("       ,count(distinct(dc.incident_resource_id)) as quantity ")
		    
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    //.append("		     else k.people*count(distinct(dc.incident_resource_id))   ")
		    .append("		     else (count(distinct(dc.incident_resource_id)) * (select k2.people from iswl_kind k2 where k2.id = k.id))  ")
		    .append("	    end as numberOfPersonnel ")
		    .append("      ,SUM(dc.total_cost_amount)/COUNT(distinct(dc.activity_date)) as totalCost ")
		    .append("	   ,SUM(dc.total_cost_amount)/(COUNT(distinct(dc.activity_date))*count(distinct(dc.incident_resource_id))) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc    ")
		    .append("       ,isw_incident_resource ir ")
		    .append("		,isw_resource r")
		    .append("       ,isw_incident i ")
		    .append("       ,isw_work_period wp ")
		    .append("       ,isw_work_period_assignment wpa ")
		    .append("       ,isw_assignment a ")
		    .append("       ,iswl_kind k  ")
		    .append("       ,iswl_request_category rc ")
			.append(" WHERE ");
		
			if(LongUtility.hasValue(incidentId))
				sql.append(" ir.incident_id = " + incidentId);
			else 
				sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+ ")");
			
			sql.append("  and ir.incident_id = i.id ")  
		    .append("  and dc.incident_resource_id = ir.id ")
		    //note: only get one day cost data as the projection data
		    //.append("  and dc.activity_date = (select min(dc2.activity_date) from isw_inc_res_daily_cost dc2 where dc2.incident_resource_id = dc.incident_resource_id) ")
		    .append("  and wp.incident_resource_id = ir.id  ")
		    .append("  and r.parent_resource_id is null ")
		    .append("  and a.assignment_status in ('C','P')")
		    .append("  and wpa.work_period_id = wp.id  ")
		    .append("  and a.id = wpa.assignment_id  ")
		    .append("  and k.id = a.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  and r.id = ir.resource_id ")
		    .append("  GROUP BY k.id, k.description, k.code,k.people, k.is_indirect,k.is_direct,rc.code")
		    .append("")

			.append(" UNION ")
			.append(" SELECT k.id as itemId ") 
		    .append("        , case  ")
            .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'OD' ")
		    .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		    .append("		      else k.code ")
		    .append("		  end as itemCodeGroup ")
		    .append("		, case  ")
		    .append("             when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		    .append("		      else "+(isOracle ? 0 : false)+" ")
		    .append("       end as supportCost ")
		    .append("		,k.description as costName ")
		    .append("       ,COUNT(distinct(dc.incident_resource_other_id)) as quantity ")
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    .append("		     else (count(distinct(dc.incident_resource_other_id)) * (select k2.people from iswl_kind k2 where k2.id = k.id))  ")
		    .append("	    end as numberOfPersonnel ")
		    .append("      ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_other_id)),2)*COUNT(distinct(dc.incident_resource_other_id)) as totalCost ")
		    .append("	   ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_other_id)),2) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc   ")
		    .append("       ,isw_incident_resource_other iro ")
		    .append("		,isw_resource_other ro")
		    .append("       ,isw_incident i ")
		    .append("       ,iswl_kind k  ")
		    .append("       ,iswl_request_category rc ")
			.append(" WHERE ");

			if(LongUtility.hasValue(incidentId))
				sql.append(" iro.incident_id = " + incidentId);
			else 
				sql.append(" iro.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+ ")");
			
			sql.append("  and iro.incident_id = i.id ")  
		    .append("  and dc.incident_resource_other_id = iro.id ")
		    .append("  and k.id = ro.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  and ro.id = iro.resource_other_id")
		    .append("  GROUP BY k.id, k.description, k.code, k.people,k.is_indirect,k.is_direct,rc.code")
		    .append("");
		return sql.toString();
	}
	
	public static String getProjectionItemDataForCurrentDayQuery(Long incidentId, Long incidentGroupId, String today, boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT k.id as itemId ") 
		    .append("        , case  ")
            .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'OD' ")
		    .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		    .append("		      else k.code ")
		    .append("		  end as itemCodeGroup ")
		    .append("		, case  ")
		    .append("             when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		    //.append("             when rc.code!='O' and k.is_direct="+(isOracle ? 0 : false ) +" then " + (isOracle ? 1 : true))
		    .append("		      else "+(isOracle ? 0 : false)+" ")
		    .append("       end as supportCost ")
		    .append("		,k.description as costName ")
		    //.append("       ,COUNT(k.code) as quantity ")
		    .append("       ,COUNT(distinct(dc.incident_resource_id)) as quantity ")
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    //.append("		     else SUM(k.people)  ")
		    .append("		     else (count(distinct(dc.incident_resource_id)) * (select k2.people from iswl_kind k2 where k2.id = k.id))  ")
		    .append("	    end as numberOfPersonnel ")
		    //.append("      ,SUM(dc.total_cost_amount) as totalCost ")
		    //.append("	   ,SUM(dc.total_cost_amount)/COUNT(k.code) as averageCost  ")
		    //.append("      ,ROUND(SUM(dc.total_cost_amount)/COUNT(k.code),2)*COUNT(k.code) as totalCost ")
		    .append("      ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_id)),2)*COUNT(distinct(dc.incident_resource_id)) as totalCost ")
		    //.append("	   ,ROUND(SUM(dc.total_cost_amount)/COUNT(k.code),2) as averageCost  ")
		    .append("	   ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_id)),2) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc   ")
		    .append("       ,isw_incident_resource ir ")
		    .append("		,isw_resource r")
		    .append("       ,isw_incident i ")
		    .append("       ,isw_work_period wp ")
		    .append("       ,isw_work_period_assignment wpa ")
		    .append("       ,isw_assignment a ")
		    .append("       ,iswl_kind k  ")
		    .append("       ,iswl_request_category rc ")
			.append(" WHERE ");
		
			if(LongUtility.hasValue(incidentId))
				sql.append(" ir.incident_id = " + incidentId);
			else 
				sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+ ")");
			
			sql.append("  and ir.incident_id = i.id ")  
		    .append("  and dc.incident_resource_id = ir.id ")
		    .append("  and r.parent_resource_id is null ")
		    .append("  and a.assignment_status in ('C','P')")
		    .append("  and to_char(dc.activity_date,'MM/DD/YYYY') = '"+today+"' ")
		    //.append("  and dc.activity_date = (select max(dc2.activity_date) from isw_inc_res_daily_cost dc2 where dc2.incident_resource_id = dc.incident_resource_id) ")
		    .append("  and wp.incident_resource_id = ir.id  ")
		    .append("  and wpa.work_period_id = wp.id  ")
		    .append("  and a.id = wpa.assignment_id  ")
		    .append("  and k.id = a.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  and r.id = ir.resource_id ")
		    //.append("  GROUP BY k.id, k.description, k.code, k.people,k.is_indirect,k.is_direct,rc.code,dc.activity_date ")
		    .append("  GROUP BY k.id, k.description, k.code, k.people,k.is_indirect,k.is_direct,rc.code")
		    .append("")

			.append(" UNION ")
			.append(" SELECT k.id as itemId ") 
		    .append("        , case  ")
            .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'OD' ")
		    .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		    .append("		      else k.code ")
		    .append("		  end as itemCodeGroup ")
		    .append("		, case  ")
		    .append("             when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		    .append("		      else "+(isOracle ? 0 : false)+" ")
		    .append("       end as supportCost ")
		    .append("		,k.description as costName ")
		    .append("       ,COUNT(distinct(dc.incident_resource_other_id)) as quantity ")
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    .append("		     else (count(distinct(dc.incident_resource_other_id)) * (select k2.people from iswl_kind k2 where k2.id = k.id))  ")
		    .append("	    end as numberOfPersonnel ")
		    .append("      ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_other_id)),2)*COUNT(distinct(dc.incident_resource_other_id)) as totalCost ")
		    .append("	   ,ROUND(SUM(dc.total_cost_amount)/COUNT(distinct(dc.incident_resource_other_id)),2) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc   ")
		    .append("       ,isw_incident_resource_other iro ")
		    .append("		,isw_resource_other ro")
		    .append("       ,isw_incident i ")
		    .append("       ,iswl_kind k  ")
		    .append("       ,iswl_request_category rc ")
			.append(" WHERE ");

			if(LongUtility.hasValue(incidentId))
				sql.append(" iro.incident_id = " + incidentId);
			else 
				sql.append(" iro.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+ ")");
			
			sql.append("  and iro.incident_id = i.id ")  
		    .append("  and dc.incident_resource_other_id = iro.id ")
		    .append("  and to_char(dc.activity_date,'MM/DD/YYYY') = '"+today+"' ")
		    .append("  and k.id = ro.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  and ro.id = iro.resource_other_id")
		    .append("  GROUP BY k.id, k.description, k.code, k.people,k.is_indirect,k.is_direct,rc.code")
		    .append("");
			
		return sql.toString();

	}

		
	public static String getIncidentSummaryGridQuery(Long projectionId, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT pi.projection_id as projectionId" +
                 ", pi.id as id" +
				 ", pi.item_code_group as itemCode" +
				 ", pi.quantity as quantity" +
				 ", pi.number_of_personnel as numberOfPersonnel" +
				 ", pi.average_cost as averageCost" +
				 ", pi.total_cost as  totalCost" +
				 ", pi.last_modified_date as lastModifiedDate")
					 
		   .append(" FROM isw_projection_item pi ")	
		   .append(" WHERE ");
	 	
		if(LongUtility.hasValue(projectionId))
			sql.append(" pi.projection_id = " + projectionId);
		
		sql.append(" and pi.is_manually_added = 'N'") 
		   .append(" and pi.is_support_cost = 'N'")
		   .append(" and pi.is_item_code_active = 'Y'"); 
		
		sql.append(" ORDER BY pi.item_code_group");
		return sql.toString();
	}
	
	public static String getManuallyAddGridQuery(Long projectionId, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT pi.id as id" +	
				 ", pi.item_id as itemId" +
				 ", pi.item_code_group as itemCode" +
				 ", pi.quantity as quantity" +
				 ", pi.number_of_personnel as numberOfPersonnel" +
				 ", pi.total_cost as averageCost" +
				 ", pi.total_cost as  totalCost")
					 			 
		   .append(" FROM isw_projection_item pi ")  
		   .append(" WHERE ");
	
		if(LongUtility.hasValue(projectionId))
			sql.append(" pi.projection_id = " + projectionId);
		
		sql.append(" and pi.is_manually_added = 'Y'") 
		   .append(" and pi.is_item_code_active = 'Y'"); 	 
		sql.append(" ORDER BY pi.item_code_group");
		return sql.toString();
	}
	
	public static String getSupportingCostGridQuery(Long projectionId,boolean isOracleDialect) {
			StringBuffer sql = new StringBuffer();
			
			//In projection_item table, the quantity = the sum of number of supporting items. 
			//the number of people = the total peoples for the non supporting items.  
			//the calculation using round to match with the value in supporting cost work sheet
			  sql.append("SELECT sum(pi.number_of_personnel) as numberOfPersonnel" +
				      ", round(sum(pi.total_cost)/sum(pi.number_of_personnel),2) as averageCost" +
				      ", round(sum(pi.total_cost)/sum(pi.number_of_personnel),2) * sum(pi.number_of_personnel) as  totalCost")
					
		   .append(" FROM isw_projection_item pi ")	
		   .append(" WHERE ");
	 	
		if(LongUtility.hasValue(projectionId))
			sql.append(" pi.projection_id = " + projectionId);
		
		sql.append(" and pi.is_manually_added = 'N'") 
		   .append(" and pi.is_support_cost = 'Y'") 
		   .append(" and pi.is_item_code_active = 'Y'"); 
		
		return sql.toString();
	}
	
	public static String getManuallyAddProjectionItemCode(Long incidentId, Long incidentGroupId, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT id,code,description,people,units ")
		   .append("FROM iswl_kind ")
		   .append("WHERE request_category_id in (select id from iswl_request_category where code in ('A','C','E','S')) and is_direct="+(isOracleDialect ? 1 : true ))
           .append(" and id not in ( SELECT k.id FROM  isw_inc_res_daily_cost dc") 
           .append(" left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id")
           .append(" , isw_incident_resource ir")
           .append(" , isw_resource r")
           .append(" , isw_incident i")
           .append(" , isw_work_period wp")
		   .append(" , isw_work_period_assignment wpa")
		   .append(" , isw_assignment a")
		   .append(" , iswl_kind k");
		   if(LongUtility.hasValue(incidentId))
			sql.append(" WHERE ir.incident_id = " + incidentId);
		   else 
			sql.append(" WHERE ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+ ")");
		   
		   sql.append(" and i.id = ir.incident_id") 
		    .append("  and r.parent_resource_id is null ")
		    .append("  and a.assignment_status in ('C','P')")
		   .append("  and r.id = ir.resource_id")
		   .append("  and dc.incident_resource_id = ir.id") 
		   .append(" and wp.incident_resource_id = ir.id")
		   .append(" and wpa.work_period_id = wp.id")
		   .append(" and a.id = wpa.assignment_id")
		   .append(" and k.id = a.kind_id)")
		   .append(" and is_direct="+(isOracleDialect ? 1 : true ));
		   //.append(" and id not in (select item_id from isw_projection_item where projection_id =" + projectionId + " and item_id is not null)");
           
		return sql.toString();
	}
	
	public static String getProjectionWorksheetPersonnelTotal(Long projectionId) {
	    StringBuffer sql = new StringBuffer();
	
	    sql.append("SELECT projection_date as projectionDate, sum(number_of_personnel) as numberOfPersonnel")
	
	    .append(" FROM isw_projection_item_wksht")	
	    .append(" WHERE projection_item_id in (")
	    .append("select id from isw_projection_item where projection_id =" + projectionId + "and is_support_cost = 'N')")
 	    .append(" group by projection_date order by projection_date");
	
	    return sql.toString();
	}
	
	public static String getItemIdsInString(List<Long> itemIds) {
		String ids = "";
		for(int i=0; i<itemIds.size(); i++) 
			ids += String.valueOf(itemIds.get(i)) + ",";
		
		if(!ids.isEmpty())
			ids = ids.substring(0, ids.length()-1);
		
		return ids;
	}
	
	public static String getCostProjectionCategoryDetailGraphReportQuery1(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		
		String selectStr = "SELECT gc.description as groupName," +
		"CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
				"sum(piwksht.average_cost*piwksht.quantity) as totalAmount,"+
				"piwksht.projection_date as \"date\"";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht,"+ 
						 " iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id ";
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and k.id = pi.item_id"+
						" and pi.item_id is not null ";
		
		String groupByStr = " group by piwksht.projection_date,gc.description,k.is_direct ";
		String orderByStr = " order by \"date\", directIndirectName desc, groupName desc";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}
	                     
	public static String getCostProjectionCategoryDetailGraphReportQuery2(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
        				   " when (pi.item_code_group = 'OS' ) then 'Overhead Support'"+
        				   " when (pi.item_code_group = 'Other Support' ) then 'Other Support'"+
        				   " end as groupName," +
        				   "case when (pi.item_code_group = 'OD' ) then 'DIRECT'" +
        				   	" when (pi.item_code_group = 'OS' ) then 'Indirect'"+
        				   	" when (pi.item_code_group = 'Other Support' ) then 'INDIRECT'"+
        				   	" end as directIndirectName," +
				"case when (pi.item_code_group = 'Other Support') then sum(piwksht.average_cost*piwksht.number_Of_personnel) "+
				" else sum(piwksht.average_cost*piwksht.quantity) end as totalAmount,"+
				" piwksht.projection_date as \"date\"";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht";
						
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+
						" and pi.item_id is null ";
						
		String groupByStr = " group by piwksht.projection_date,pi.item_code_group ";
		String orderByStr = " order by \"date\",directIndirectName,groupName desc ";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}

	public static String getCostProjectionCategoryDetailGraphReportQueryByWeek2(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		int numberOfWeek = filter.getNumOfWeeks();
		Date date = filter.getStartDate();
		
		String startDateStr = "";
		String endDateStr = "";
		
		for (int i = 0; i < numberOfWeek; i++) {
			
			startDateStr = filter.getAfterDateInString1(date,0);
			endDateStr = filter.getAfterDateInString1(date,6);
			date = filter.getAfterDate(date,7);
			
			if(i != 0)
				sql.append(" UNION "); // union different weeks data	
		
			String selectStr = "SELECT case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
			   " when (pi.item_code_group = 'OS' ) then 'Overhead Support'"+
			   " when (pi.item_code_group = 'Other Support' ) then 'Other Support'"+
			   " end as groupName," +
			   "case when (pi.item_code_group = 'OD' ) then 'DIRECT'" +
			   	" when (pi.item_code_group = 'OS' ) then 'Indirect'"+
			   	" when (pi.item_code_group = 'Other Support' ) then 'INDIRECT'"+
			   	" end as directIndirectName," +
			   "case when (pi.item_code_group = 'Other Support') then sum(piwksht.average_cost*piwksht.number_Of_personnel) "+
			   " else sum(piwksht.average_cost*piwksht.quantity) end as totalAmount,"+
			   "'"+startDateStr.substring(0,5)+" - "+endDateStr.substring(0,5)+"' as dateStr";
		
			String fromStr = " FROM isw_projection p,"+
			 " isw_projection_item pi,"+
			 " isw_projection_item_wksht piwksht";
						 
			String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and pi.item_id is null "+
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') >= '" + startDateStr + "'" +  
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') <= '" + endDateStr + "'"; 
		
			String groupByStr = " group by piwksht.projection_date,pi.item_code_group ";
			sql.append(selectStr);
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
		}
		
		String orderByStr = " order by dateStr,directIndirectName,groupName desc ";
		sql.append(orderByStr);
		
		return sql.toString();
	}

	public static String getCostProjectionCategoryDetailGraphReportQueryByWeek1(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		int numberOfWeek = filter.getNumOfWeeks();
		Date date = filter.getStartDate();
		
		String startDateStr = "";
		String endDateStr = "";

		for (int i = 0; i < numberOfWeek; i++) {
			
			startDateStr = filter.getAfterDateInString1(date,0);
			endDateStr = filter.getAfterDateInString1(date,6);	
			date = filter.getAfterDate(date,7);
		
			if(i != 0)
				sql.append(" UNION "); // union different weeks data	
		
			String selectStr = "SELECT gc.description as groupName," +
								"CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
							   "sum(piwksht.average_cost*piwksht.quantity) as totalAmount,"+
							   "'"+startDateStr.substring(0,5)+" - "+endDateStr.substring(0,5)+"' as dateStr";
		
			String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht,"+ 
						 " iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id ";
						 
			String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and k.id = pi.item_id"+
						" and pi.item_id is not null "+
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') >= '" + startDateStr + "'" +  
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') <= '" + endDateStr + "'"; 
		
			String groupByStr = " group by piwksht.projection_date,gc.description,k.is_direct ";
			sql.append(selectStr);
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
		}
		
		String orderByStr = " order by dateStr, directIndirectName desc, groupName desc";
		sql.append(orderByStr);
		
		return sql.toString();
	}

	public static String getCostProjectionTotalGraphReportQuery1(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT gc.description as groupName," +
				"sum(piwksht.average_cost*piwksht.quantity) as \"totalAmount\"";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht,"+ 
						 " iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id ";
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and k.id = pi.item_id" +
						" and pi.item_id is not null ";
		
		String groupByStr = " group by gc.description ";
		String orderByStr = " order by gc.description ";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}

	public static String getCostProjectionTotalGraphReportQuery2(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
               " when (pi.item_code_group = 'OS' ) then 'Overhead Support'"+
               " when (pi.item_code_group = 'Other Support' ) then 'Other Support'"+
               " end as groupName," +
			   " case when (pi.item_code_group = 'Other Support' ) then sum(piwksht.average_cost*piwksht.number_of_personnel)" +
               " else sum(piwksht.average_cost*piwksht.quantity) end as \"totalAmount\"";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht";
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id " +
						" and pi.item_id is null ";
		
		String groupByStr = " group by pi.item_code_group ";
		String orderByStr = " order by pi.item_code_group ";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}

	
	public static String getCostToDateTotalGraphReportQuery(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT case when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'Overhead Direct' "+
                           " when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Overhead Support' "+
                           " when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Other Support' "+
                           " else gc.description end as groupName, "+
                           "sum(dc.total_cost_amount) as \"totalAmount\"";
		
		String fromStr = " FROM isw_incident i,"+
				" isw_incident_resource ir"+
			    " left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id,"+
				" isw_resource r,"+
				" isw_inc_res_daily_cost dc,"+
				" isw_work_period wp ,"+ 
				" isw_work_period_assignment wpa ,"+ 
				" isw_assignment a ,"+ 
				" iswl_kind k"+  
				" left join iswl_group_category gc on k.group_category_id = gc.id, "+
				" iswl_request_category rc";
		
		String whereStr;
		
		if(filter.incidentId != 0)
			whereStr = " WHERE i.id = " + filter.incidentId;
		else 
			whereStr = " WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.incidentGroupId+ ")";
		
		whereStr += " and i.id = ir.incident_id"+ 
					" and dc.incident_resource_id = ir.id"+ 
					" and ir.id = wp.incident_resource_id"+ 
				   // " and r.parent_resource_id is not null "+
				    "  and r.id = ir.resource_id "+
					" and wp.id = wpa.work_period_id"+  
					" and wpa.assignment_id = a.id"+  
					" and a.end_date is null"+ 
					" and a.kind_id = k.id" +
					" and rc.id = k.request_category_id ";  

		
		String groupByStr = " GROUP BY rc.code,k.is_direct,k.is_indirect,gc.description";
		String orderByStr = " ORDER BY gc.description";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}
	
	public static String getCostProjectionCategoryDetailReportQuery1(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		int daysCounter = 0; 
		int daysCounter1 = 0;
		int numberOfWeek = filter.getNumOfWeeks();
		List<String> date = filter.getReportHeaderDate();
		
		for (int i = 0; i < numberOfWeek; i++) {
			
			if(i != 0)
				sql.append(" UNION "); // union different weeks data	
			
			String selectStr = "SELECT CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
			"gc.description as kindGroupDescription, sgc.description as subGroupCatDescription,"+
			"SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_01\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_02\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_03\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_04\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_05\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_06\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN piwksht.average_cost*piwksht.quantity else NULL END) as \"costAmount_07\",";
			
			if(filter.isByDayReport) {
				selectStr = selectStr + "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_01\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_02\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_03\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_04\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_05\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_06\"," +
		    "SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
			"THEN piwksht.quantity else NULL END) as \"quantity_07\"," ;
			}
			selectStr = selectStr +"SUM(piwksht.average_cost*piwksht.quantity) as \"total\", "+
			 (i+1) + " as \"week\"";
		
			String fromStr = " FROM isw_projection p,"+
							 " isw_projection_item pi,"+
							 " isw_projection_item_wksht piwksht,"+
							 " iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id " +
							 " left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id  ";  
							
			
			
			String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and k.id = pi.item_id" +
						" and pi.item_id is not null" +
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') >= '" + date.get(i*7) + "'" +  
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') <= '" + date.get(i*7+6) + "'"; 
			
			String groupByStr = " GROUP BY k.is_direct,gc.description,sgc.description";
			
			sql.append(selectStr);
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
		}
		
		//String orderByStr = " ORDER BY k.is_direct,gc.description,sgc.description";
		
		String orderByStr = " ORDER BY \"week\",directIndirectName,kindGroupDescription,subGroupCatDescription";
		sql.append(orderByStr);
		
		return sql.toString();
	}
	
	public static String getCostProjectionCategoryDetailReportQuery2(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		int daysCounter = 0; 
		int daysCounter1 = 0;
		int numberOfWeek = filter.getNumOfWeeks();
		List<String> date = filter.getReportHeaderDate();
		
		for (int i = 0; i < numberOfWeek; i++) {
			
			if(i != 0)
				sql.append(" UNION "); // union different weeks data	
		
			sql.append(" SELECT case when (pi.item_code_group = 'OD' ) then 'DIRECT'")
	           .append(" when (pi.item_code_group = 'OS' or pi.item_code_group = 'Other Support') then 'INDIRECT'")
	           .append(" end as directIndirectName,") 
		       .append(" case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'")
               .append(" when (pi.item_code_group = 'OS' ) then 'Overhead Support'")
               .append(" when (pi.item_code_group = 'Other Support' ) then 'Other Support'")
               .append(" end as kindGroupDescription, ")
			   .append(" case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'")
               .append(" when (pi.item_code_group = 'OS' ) then 'Overhead Support'")
               .append(" when (pi.item_code_group = 'Other Support' ) then 'Other Support'")
               .append(" end as subGroupCatDescription, ")
			
               .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
               .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_01\"," )
               .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_02\",")
 			   .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_03\",")
 			   .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_04\"," )
 			   .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_05\"," )
 			   .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' ")
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_06\"," )
 			   .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " )
 			   .append("THEN piwksht.average_cost*piwksht.number_Of_Personnel else NULL END) as \"costAmount_07\",");
			
			if(filter.isByDayReport) {
			   sql.append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_01\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_02\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_03\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_04\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_05\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_06\"," )
			      .append("SUM( CASE WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1) + "' and pi.item_code_group = 'Other Support'" )
			      .append(" THEN piwksht.number_Of_personnel")
			      .append(" WHEN TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' and pi.item_code_group != 'Other Support'" ) 
			      .append(" THEN piwksht.quantity else null END) as \"quantity_07\",") ;
			}
			sql.append("SUM(CASE WHEN pi.item_code_group = 'Other Support'")
			.append(" THEN piwksht.average_cost*piwksht.number_Of_Personnel else piwksht.average_cost*piwksht.quantity end)  as \"total\", "+(i+1) + " as \"week\"");
		
			String fromStr = " FROM isw_projection p,"+
							 " isw_projection_item pi,"+
							 " isw_projection_item_wksht piwksht";
			
			String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						"  and pi.item_id is null " +
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') >= '" + date.get(i*7) + "'" +  
						" and TO_CHAR(piwksht.projection_date, 'mm/dd/yyyy') <= '" + date.get(i*7+6) + "'"; 
			
			String groupByStr = " GROUP BY pi.item_code_group,pi.item_id ";
			
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
		}
		
		//String orderByStr = " ORDER BY k.is_direct,gc.description,sgc.description";
		
		String orderByStr = " ORDER BY \"week\",directIndirectName,kindGroupDescription desc,subGroupCatDescription desc";
		sql.append(orderByStr);
		
		return sql.toString();
	}
	
	public static String getCostProjectionTotalReportQuery1(CostProjectionReportFilter filter, Boolean isOracle) {
		
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
			"gc.description as kindGroupDescription, sgc.description as subGroupCatDescription,"+
        	"sum(piwksht.average_cost*piwksht.quantity) as projectionCost,"+
        	"sum(piwksht.average_cost*piwksht.quantity) as costToDate";
		
		String fromStr = " FROM isw_projection p,"+
		" isw_projection_item pi,"+
		" isw_projection_item_wksht piwksht,"+
		" iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id " +
		" left join iswl_sub_group_category sgc on k.sub_group_category_id = sgc.id  ";  
		
		String whereStr = " WHERE p.id = " + filter.projectionId;
		whereStr += " and p.id = pi.projection_id "+
					" and piwksht.projection_item_id = pi.id "+ 
					//" and pi.item_id is not null " +
					" and k.id = pi.item_id";
					
		String groupByStr = " group by k.is_direct,gc.description,sgc.description ";
		
		String orderByStr = " order by directIndirectName,gc.description,sgc.description ";
		
		//String orderByStr = " order by directIndirectName, kindGroupDescription,subGroupCatDescription";
//      test to see do we need k.is_direct in order by because it is a boolean value		

		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		
		sql.append(orderByStr);
		
		return sql.toString();
	}
	
	public static String getCostProjectionTotalReportQuery2(CostProjectionReportFilter filter, Boolean isOracle) {
		
		StringBuffer sql = new StringBuffer();
		

        String selectStr = 	" SELECT case when (pi.item_code_group = 'OD' ) then 'DIRECT'" +
         	" when (pi.item_code_group = 'OS' or pi.item_code_group = 'Other Support') then 'INDIRECT'" +
            " end as directIndirectName," + 
	        " case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
            " when (pi.item_code_group = 'OS' ) then 'Overhead Support'" +
            " when (pi.item_code_group = 'Other Support' ) then 'Other Support'" +
            " end as kindGroupDescription, " +
		    " case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
            " when (pi.item_code_group = 'OS' ) then 'Overhead Support'" +
            " when (pi.item_code_group = 'Other Support' ) then 'Other Support'" +
            " end as subGroupCatDescription, " +
			" case when (pi.item_code_group = 'Other Support') then sum(piwksht.average_cost*piwksht.number_Of_Personnel)" +
			" else sum(piwksht.average_cost*piwksht.quantity) end as projectionCost,"+
			" case when (pi.item_code_group = 'Other Support') then sum(piwksht.average_cost*piwksht.number_Of_Personnel)" +
			" else sum(piwksht.average_cost*piwksht.quantity) end as costToDate";
		
		String fromStr = " FROM isw_projection p,"+
		" isw_projection_item pi,"+
		" isw_projection_item_wksht piwksht";
		
		String whereStr = " WHERE p.id = " + filter.projectionId;
		whereStr += " and p.id = pi.projection_id "+
					" and piwksht.projection_item_id = pi.id " +
		            " and pi.item_id is null" ;
					
		String groupByStr = " GROUP BY pi.item_code_group,pi.item_id";
		String orderByStr = " ORDER BY directIndirectName,kindGroupDescription desc,subGroupCatDescription desc";
		//      test to see do we need k.is_direct in order by because it is a boolean value		

		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}
	                   
	public static String getCostProjectionCostToDateTotalReportQuery(CostProjectionReportFilter filter, Boolean isOracle) {
			
			StringBuffer sql = new StringBuffer();
			
			String selectStr = "SELECT CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
							   " case when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'Overhead Direct' "+
	                           " when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Overhead Support' "+
	                           " when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Other Support' "+
	                           " else gc.description end as kindGroupDescription, "+
	                           " case when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'Overhead Direct' "+
	                           " when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Overhead Support' "+
	                           " when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Other Support' "+
	                           " else sgc.description end as subGroupCatDescription, "+
	                           "sum(dc.total_cost_amount) as costToDate";
			
//			String selectStr = "SELECT CASE WHEN k.is_direct = "+ (isOracle ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS directIndirectName,"+
//			"gc.description as kindGroupDescription, sgc.description as subGroupCatDescription,"+
//        	"sum(dc.total_cost_amount) as costToDate";
			
			String fromStr = " FROM isw_incident i,"+
					" isw_incident_resource ir,"+  
					" isw_resource r,"+
					" isw_inc_res_daily_cost dc"+
				    " left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id,"+
					" isw_work_period wp ,"+ 
					" isw_work_period_assignment wpa ,"+ 
					" isw_assignment a ,"+ 
					" iswl_kind k"+  
					" left join iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id"+ 
					" left join iswl_group_category gc on k.group_category_id = gc.id, "+
					" iswl_request_category rc";
			
			String whereStr;
			
			if(filter.incidentId != 0)
				whereStr = " WHERE i.id = " + filter.incidentId;
			else 
				whereStr = " WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.incidentGroupId+ ")";
			
			whereStr += " and i.id = ir.incident_id"+ 
						" and dc.incident_resource_id = ir.id"+ 
						" and ir.id = wp.incident_resource_id"+ 
					    " and r.parent_resource_id is null "+
					    " and a.assignment_status in ('C','P')"+
					    "  and r.id = ir.resource_id "+
						" and wp.id = wpa.work_period_id"+  
						" and wpa.assignment_id = a.id"+  
						" and a.end_date is null"+ 
						" and a.kind_id = k.id" +
						" and rc.id = k.request_category_id ";  

			
			String groupByStr = " GROUP BY k.is_direct,k.is_indirect,rc.code,gc.description, sgc.description";
			String orderByStr = " ORDER BY k.is_indirect,k.is_direct,gc.description desc, sgc.description desc";
			
			sql.append(selectStr);
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
			sql.append(orderByStr);
			
			return sql.toString();
	}
	
	public static String getCostProjectionTotalGraphReportQueryBar1(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT gc.description as groupName," +
				"sum(piwksht.average_cost*piwksht.quantity) as \"totalAmount\","+
				" cast('projection' as character varying(10)) as dateStr";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht,"+ 
						 " iswl_kind k left join iswl_group_category gc on k.group_category_id = gc.id ";
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id "+ 
						" and k.id = pi.item_id" +
						" and pi.item_id is not null ";
		
		String groupByStr = " group by gc.description ";
		String orderByStr = " order by gc.description ";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}

	public static String getCostProjectionTotalGraphReportQueryBar2(CostProjectionReportFilter filter, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT case when (pi.item_code_group = 'OD' ) then 'Overhead Direct'" +
               " when (pi.item_code_group = 'OS' ) then 'Overhead Support'"+
               " when (pi.item_code_group = 'Other Support' ) then 'Other Support'"+
               " end as groupName," +
			   " case when (pi.item_code_group = 'Other Support' ) then sum(piwksht.average_cost*piwksht.number_of_personnel)" +
               " else sum(piwksht.average_cost*piwksht.quantity) end as \"totalAmount\","+
               " cast('projection' as character varying(10)) as dateStr";
		
		String fromStr = " FROM isw_projection p,"+
						 " isw_projection_item pi,"+
						 " isw_projection_item_wksht piwksht";
						 
		String whereStr = " WHERE p.id = " + filter.projectionId;
			whereStr += " and p.id = pi.projection_id "+
						" and piwksht.projection_item_id = pi.id " +
						" and pi.item_id is null ";
		
		String groupByStr = " group by pi.item_code_group ";
		String orderByStr = " order by pi.item_code_group ";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}
	                     
	public static String getCostToDateTotalGraphReportQueryBar(CostProjectionReportFilter filter, Boolean isOracle) {
		
		StringBuffer sql = new StringBuffer();
		
		String selectStr = "SELECT case when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'Overhead Direct' "+
                           " when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Overhead Support' "+
                           " when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'Other Support' "+
                           " else gc.description end as groupName, "+
                           " sum(dc.total_cost_amount) as \"totalAmount\","+
        				   " cast('To Date' as character varying(10)) as dateStr";
		
		String fromStr = " FROM isw_incident i,"+
				" isw_incident_resource ir,"+  
				" isw_resource r,"+
				" isw_inc_res_daily_cost dc"+
			    " left join isw_incident_resource_other iro on dc.incident_resource_other_id = iro.id,"+
				" isw_work_period wp ,"+ 
				" isw_work_period_assignment wpa ,"+ 
				" isw_assignment a ,"+ 
				" iswl_kind k"+  
				" left join iswl_group_category gc on k.group_category_id = gc.id, "+
				" iswl_request_category rc";
		
		String whereStr;
		
		if(filter.incidentId != 0)
			whereStr = " WHERE i.id = " + filter.incidentId;
		else 
			whereStr = " WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = " + filter.incidentGroupId+ ")";
		
		whereStr += " and i.id = ir.incident_id"+ 
					" and dc.incident_resource_id = ir.id"+ 
					" and ir.id = wp.incident_resource_id"+ 
				    " and r.parent_resource_id is null "+
				    "  and a.assignment_status in ('C','P')"+
				    "  and r.id = ir.resource_id "+
					" and wp.id = wpa.work_period_id"+  
					" and wpa.assignment_id = a.id"+  
					" and a.end_date is null"+ 
					" and a.kind_id = k.id" +
					" and rc.id = k.request_category_id ";  

		
		String groupByStr = " GROUP BY rc.code,k.is_direct,k.is_indirect,gc.description";
		String orderByStr = " ORDER BY gc.description";
		
		sql.append(selectStr);
		sql.append(fromStr);	
		sql.append(whereStr);
		sql.append(groupByStr);
		sql.append(orderByStr);
		
		return sql.toString();
	}
    /******************************************************************************************/		
}
