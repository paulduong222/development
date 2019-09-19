package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.LongUtility;


public class CostProjectionItemQuery {

	public static String getProjectionItemSql(Long incidentId, Long incidentGroupId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT k.id as itemId ") 
		    .append("        , case  ")
            .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false ) +" ) then 'OD' ")
		    .append("		      when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		    .append("		      else k.code ")
		    .append("		  end as itemCodeGroup ")
		    .append("		, case  ")
		    //.append("             when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		    .append("             when rc.code!='O' and k.is_direct="+(isOracle ? 0 : false ) +" then " + (isOracle ? 1 : true))
		    .append("		      else "+(isOracle ? 0 : false)+" ")
		    .append("       end as supportCost ")
		    .append("		,k.description as costName ")
		    .append("       ,COUNT(k.code) as quantity ")
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    .append("		     else SUM(k.people)  ")
		    .append("	    end as numberOfPersonnel ")
		    .append("      ,SUM(dc.total_cost_amount) as totalCost ")
		    .append("	   ,SUM(dc.total_cost_amount)/COUNT(k.code) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc   ")
		    .append("       ,isw_incident_resource ir ")
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
		    .append("  and dc.incident_resource_id = ir.id  ")
		    .append("  and dc.activity_date = (select min(dc2.activity_date) from isw_inc_res_daily_cost dc2 where dc2.incident_resource_id = dc.incident_resource_id) ")
		    .append("  and wp.incident_resource_id = ir.id  ")
		    .append("  and wpa.work_period_id = wp.id  ")
		    .append("  and a.id = wpa.assignment_id  ")
		    .append("  and k.id = a.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  GROUP BY k.id, k.description, k.code, k.is_indirect,k.is_direct,rc.code,dc.activity_date ")
		    .append("");

		return sql.toString();
	}
	
	public static String getInsertProjectionItemSql(Long projectionId, Long incidentId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into isw_projection_item (id, projection_id, item_id, cost_name, is_manually_added, is_support_cost,quantity,average_cost,number_of_personnel,total_cost,item_code_group,is_item_code_active) ")
			.append("select nextVal('seq_projection_item'),"+projectionId+",tbl1.itemId,tbl1.costName,'N',tbl1.supportCost,tbl1.quantity,tbl1.averageCost,tbl1.numberOfPersonnel,tbl1.totalCost,tbl1.itemCodeGroup,'Y' ")
		    .append("from ")
		    .append(" (SELECT k.id as itemId ") 
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
		    .append("       ,COUNT(k.code) as quantity ")
		    .append("      ,case ")
		    .append("		     when k.people is null then 0 ")
		    .append("		     else SUM(k.people)  ")
		    .append("	    end as numberOfPersonnel ")
		    .append("      ,SUM(dc.total_cost_amount) as totalCost ")
		    .append("	   ,SUM(dc.total_cost_amount)/COUNT(k.code) as averageCost  ")
		    .append("  FROM  isw_inc_res_daily_cost dc   ")
		    .append("       ,isw_incident_resource ir ")
		    .append("       ,isw_incident i ")
		    .append("       ,isw_work_period wp ")
		    .append("       ,isw_work_period_assignment wpa ")
		    .append("       ,isw_assignment a ")
		    .append("       ,iswl_kind k  ")
		    .append("       ,iswl_request_category rc ")
			.append(" WHERE i.id = " + incidentId + " ")
			.append("  and ir.incident_id = i.id ")  
		    .append("  and dc.incident_resource_id = ir.id  ")
		    .append("  and dc.activity_date = (select min(dc2.activity_date) from isw_inc_res_daily_cost dc2 where dc2.incident_resource_id = dc.incident_resource_id) ")
		    .append("  and wp.incident_resource_id = ir.id  ")
		    .append("  and wpa.work_period_id = wp.id  ")
		    .append("  and a.id = wpa.assignment_id  ")
		    .append("  and k.id = a.kind_id  ")
		    .append("  and rc.id = k.request_category_id ")
		    .append("  GROUP BY k.id, k.description, k.code, k.is_indirect,k.is_direct,rc.code,dc.activity_date ")
		    .append(" ) tbl1 ")
		    .append("where tbl1.itemCodeGroup not in (select item_code_group from isw_projection_item where projection_id = "+projectionId+" ) ")
		    .append("");

		return sql.toString();
	}
	
	public static String getUpdateProjectionItemSql(Long projectionId, Long incidentId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update isw_projection_item set total_cost = tbl1.totalCost, average_cost = tbl1.averageCost, quantity=tbl1.quantity ")
		   .append("from ")
		   .append("  (SELECT k.id as itemId ")
		   .append("          , case  ")
		   .append("           		when (rc.code='O' and k.is_direct="+(isOracle ? 1 : true )+" and k.is_indirect="+(isOracle ? 0 : false )+" ) then 'OD' ")
		   .append("           		when (rc.code='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then 'OS' ")
		   .append("           		else k.code ")
		   .append("           end as itemCodeGroup ")
		   .append("          , case  ")
		   .append("           		when (rc.code!='O' and k.is_direct="+(isOracle ? 0 : false )+" and k.is_indirect="+(isOracle ? 1 : true )+" ) then "+(isOracle ? 1 : true)+" ")
		   .append("           		else "+(isOracle ? 0 : false)+" ")
		   .append("           end as supportCost ")
		   .append("          ,k.description as costName ")
		   .append("          ,COUNT(k.code) as quantity ")
		   .append("          ,case ")
		   .append("        when k.people is null then 0 ")
		   .append("        else SUM(k.people)  ")
		   .append("          end as numberOfPersonnel ")
		   .append("          ,SUM(dc.total_cost_amount) as totalCost ")
		   .append("          ,SUM(dc.total_cost_amount)/COUNT(k.code) as averageCost  ")
		   .append("    FROM  isw_inc_res_daily_cost dc   ")
		   .append("          ,isw_incident_resource ir ")
		   .append("         ,isw_incident i ")
		   .append("         ,isw_work_period wp ")
		   .append("         ,isw_work_period_assignment wpa ")
		   .append("         ,isw_assignment a ")
		   .append("         ,iswl_kind k  ")
		   .append("         ,iswl_request_category rc ")
		   .append("    WHERE  i.id = " + incidentId + " ")
		   .append("    and ir.incident_id = i.id ")  
		   .append("    and dc.incident_resource_id = ir.id  ")
		   .append("    and dc.activity_date = (select min(dc2.activity_date) from isw_inc_res_daily_cost dc2 where dc2.incident_resource_id = dc.incident_resource_id) ")
		   .append("    and wp.incident_resource_id = ir.id  ")
		   .append("    and wpa.work_period_id = wp.id  ")
		   .append("    and a.id = wpa.assignment_id  ")
		   .append("    and k.id = a.kind_id  ")
		   .append("    and rc.id = k.request_category_id ")
		   .append("    GROUP BY k.id, k.description, k.code, k.is_indirect,k.is_direct,rc.code,dc.activity_date ")
		   .append(" ) tbl1 ")
		   .append("where projection_id = " + projectionId + " ")
		   .append("and tbl1.itemCodeGroup in (select item_code_group from isw_projection_item where projection_id = "+projectionId+") ")
		   .append("and item_code_group = tbl1.itemCodeGroup ");

		return sql.toString();
		
	}	
	
}
