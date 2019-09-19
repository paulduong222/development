package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;


public class CostReportFilterQuery {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
//	static final String[] groupCategoryNames = {"Accounting Code","Agency","Payment Agency",
//		"Cost Group","Unit Id","Incident Name"};
	static final Map<String,String> selectedColumnMap = new HashMap<String, String>();
	static{
		selectedColumnMap.put("ACCOUNTING_CODE", "ac.account_code as label ");
		selectedColumnMap.put("AGENCY_CODE", "ra.agency_code as label ");
		selectedColumnMap.put("PAYMENT_AGENCY_CODE", "pa.agency_code as label ");
		selectedColumnMap.put("COST_GROUP_NAME", "cg.cost_group_name as label ");
		selectedColumnMap.put("UNIT_CODE", "o.unit_code as label ");
		selectedColumnMap.put("INCIDENT", "sgc.description as label ");
	}
		
	static final Map<String,String> orderByMap = new HashMap<String, String>();
	static {
		orderByMap.put("ACCOUNTING_CODE", "ac.account_code");
		orderByMap.put("AGENCY_CODE", "ra.agency_code");
		orderByMap.put("PAYMENT_AGENCY_CODE", "pa.agency_code");
		orderByMap.put("COST_GROUP_NAME", "cg.cost_group_name");
		orderByMap.put("UNIT_CODE", "o.unit_code");
		orderByMap.put("INCIDENT", "sgc.description");
	}
	
	public static String getCostReportFilterQuery(Long id, String filterName, boolean isIncidentGroup, boolean isOracleDialect) {
		if(filterName.equals("AGENCY_CODE"))
			return getAgencyCodeById(id, isIncidentGroup, isOracleDialect);
		else if(filterName.equals("UNIT_CODE"))
			return getUnitCodeById(id, isIncidentGroup, isOracleDialect);
		else
			return getAllCostReportFilterQuery(id, filterName, isIncidentGroup, isOracleDialect);
	}
	
	public static String getAllCostReportFilterQuery(Long id, String filterName, boolean isIncidentGroup, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		
		    boolean key = selectedColumnMap.containsKey(filterName);
		    String value = selectedColumnMap.get(filterName);
		    
	    	sql.append("SELECT " + selectedColumnMap.get(filterName))    
	    	   
	    	.append(" FROM ")
	    	   .append(" isw_inc_res_daily_cost dc ")
	    	   .append(" LEFT JOIN isw_cost_group cg on dc.cost_group_id = cg.id")
	    	   .append(" LEFT JOIN isw_incident_account_code iac on dc.incident_account_code_id = iac.id")
	    	   .append(" LEFT JOIN isw_account_code ac on iac.account_code_id = ac.id")
	    	   .append(" , isw_incident_resource ir")
	    	   .append(" LEFT JOIN isw_cost_data cd on ir.cost_data_id = cd.id")
	    	   .append(" LEFT JOIN iswl_agency pa on cd.payment_agency_id = pa.id")
	    	   .append(" , isw_incident i")
	    	   .append(" , isw_resource r")
	    	   .append(" LEFT JOIN isw_organization o on r.organization_id = o.id")
	    	   .append(" LEFT JOIN iswl_agency ra on r.agency_id = ra.id")
	    	   .append(" , isw_work_period wp")
	    	   .append(" , isw_work_period_assignment wpa")
	    	   .append(" , isw_assignment a")
	    	   .append(" , iswl_kind k")
               .append(" LEFT JOIN iswl_sub_group_category sgc on K.SUB_GROUP_CATEGORY_ID = sgc.id")
               .append(" LEFT JOIN iswl_kind_group kg on K.KIND_GROUP_ID = kg.id")
               .append(" LEFT JOIN iswl_request_category rc on k.request_category_id = rc.id")
               
            .append(" WHERE ");
            
	      		if(!isIncidentGroup)
	      			sql.append(" ir.incident_id = " + id);
	      		else 
	      			sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + id + ")");	   
	      		
	      		sql.append(" and i.id = ir.incident_id ")
	      		.append(" and dc.incident_resource_id = ir.id ")
	      		.append(" and r.id = ir.resource_id ")
	      		.append(" and r.parent_resource_id is null ")
	      		.append(" and wp.incident_resource_id = ir.id")
	      		.append(" and wpa.work_period_id = wp.id")
	      		.append(" and a.id = wpa.assignment_id")
	      		.append(" and a.end_date is null")
	      		.append(" and k.id = a.kind_id");
	    	
	    	sql.append(" GROUP BY " + orderByMap.get(filterName));
	    	
		return sql.toString();
	}
	   
	public static String getAgencyCodeById(Long id, boolean isIncidentGroup, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		 
	    	sql.append("SELECT " + selectedColumnMap.get("AGENCY_CODE"))    
	    	   
	    	.append(" FROM ")
	    	
	    	.append("iswl_agency ra ")
	    	.append(" left join isw_resource r on r.agency_id = ra.id ")
            .append(" left join isw_incident_resource ir on (ir.resource_id = r.id)")
               
            .append(" WHERE ");
            
	      		if(!isIncidentGroup)
	      			sql.append(" ir.incident_id = " + id);
	      		else 
	      			sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + id + ")");	   
	      		
	    	sql.append(" GROUP BY " + orderByMap.get("AGENCY_CODE"));
	    	
		return sql.toString();
	}
	
	public static String getUnitCodeById(Long id, boolean isIncidentGroup, boolean isOracleDialect) {
		StringBuffer sql = new StringBuffer();
		 
	    	sql.append("SELECT " + selectedColumnMap.get("UNIT_CODE"))    
	    	   
	    	.append(" FROM ")
	    	.append(" isw_organization o ")
            .append(" left join isw_resource r on r.organization_id = o.id ")
            .append(" left join isw_incident_resource ir on (ir.resource_id = r.id)")
               
            .append(" WHERE ");
            
	      		if(!isIncidentGroup)
	      			sql.append(" ir.incident_id = " + id);
	      		else 
	      			sql.append(" ir.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + id + ")");	   
	      			    	
	    	sql.append(" GROUP BY " + orderByMap.get("UNIT_CODE"));
	    	
		return sql.toString();
	}
	   
}
