package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;


public abstract class CostReportQuery {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	static final Map<String,String> selectedGroupMap = new HashMap<String, String>();
	static{							
		selectedGroupMap.put("Direct/Indirect-Oracle", "SELECT CASE WHEN k.is_direct = 1 then 'DIRECT' ELSE 'INDIRECT' END AS groupName");
		selectedGroupMap.put("Direct/Indirect", "SELECT CASE WHEN k.is_direct =" + Boolean.TRUE +" then 'DIRECT' ELSE 'INDIRECT' END AS groupName");
		//selectedGroupMap.put("Direct/Indirect", "SELECT CASE WHEN k.is_direct =" + (isOracleDialect ? 1 : Boolean.TRUE) +" then 'DIRECT' ELSE 'INDIRECT' END AS groupName");
		selectedGroupMap.put("Cost Group Category", "SELECT kg.description AS groupName");
		selectedGroupMap.put("Cost Sub-Group Category", "SELECT sgc.description AS groupName");
	}
		
	static final Map<String,String> orderByMap = new HashMap<String, String>();
	static {
		orderByMap.put("Direct/Indirect", "k.is_direct");
		orderByMap.put("Cost Group Category","kg.description" );
		orderByMap.put("Cost Sub-Group Category", "sgc.description");
	}
	
	static final Map<String,String> orderByMap2 = new HashMap<String, String>();
	static {
		orderByMap2.put("Direct/Indirect", "directIndirectName");
		orderByMap2.put("Cost Group Category","kindGroupDescription" );
		orderByMap2.put("Cost Sub-Group Category", "subGroupCatDescription");
	}
	
	static protected String getGroupByString (GroupCategoryTotalReportFilter filter) {
		return getGroupByString(filter.getSelectedReportGroup());
	}
	
	static protected String getGroupByString (String selectedGroupName) {
        String groupByStr = "";
      
		if(selectedGroupName.equals("Accounting Code")) {
			groupByStr = "ac.account_code ";
		}
		else if(selectedGroupName.equals("Agency")) {
			groupByStr = "ra.agency_code ";
		}
		else if(selectedGroupName.equals("Payment Agency")) {
			groupByStr = "pa.agency_code ";
		}
		else if(selectedGroupName.equals("Cost Group")) {
			groupByStr = "cg.cost_group_name ";
		}
		else if(selectedGroupName.equals("Unit ID")) {
			groupByStr = "o.unit_code ";
		}
		else if(selectedGroupName.equals("Date")) {
			groupByStr = "dc.activity_date ";
		}
		else if(selectedGroupName.equals("Incident")) {
			groupByStr = "i.incident_name ";
		}

		return groupByStr;
	}
		
	static protected String getGroupName (GroupCategoryTotalReportFilter filter) {
		return getGroupName(filter.getSelectedReportGroup());
	}
	
	static protected String getGroupName (String selectedGroupName) {
        String groupName = "";
      
		if(selectedGroupName.equals("Accounting Code")) {
			groupName = "'Accounting Code'";
		}
		else if(selectedGroupName.equals("Agency")) {
			groupName = "'Agency'";
		}
		else if(selectedGroupName.equals("Payment Agency")) {
			groupName = "'Payment Agency'";
		}
		else if(selectedGroupName.equals("Cost Group")) {
			groupName = "'Cost Group'";
		}
		else if(selectedGroupName.equals("Unit ID")) {
			groupName = "'Unit ID'";
		}
		else if(selectedGroupName.equals("Date")) {
			groupName = "'Date'";
		}
		else if(selectedGroupName.equals("Incident")) {
			//selecting an incident = selecting sgc.descriptions 
			//it is already in the select clause, so no needed to add something here
			//For additional filter='Incident': the groupBy is 'incident'. the filter 
			//includeAll=all the itemCode (Sub-Category). selective=selected itemCode. 
			groupName = "'Incident'"; 
		}
		
		return groupName;
	}
	
	
	static protected String getAdditionalFilterString (GroupCategoryTotalReportFilter filter) {
        String additionalFilterStr = "";
      
		if(filter.getAdditionalFilterType().equals("Accounting Code") && !filter.getAdditionalFilterString().isEmpty()) {
			additionalFilterStr = " and ac.account_code in (" + filter.getAdditionalFilterString() + ")";
		}
		else if(filter.getAdditionalFilterType().equals("Agency") && !filter.getAdditionalFilterString().isEmpty()) {
			additionalFilterStr = " and ra.agency_code in (" + filter.getAdditionalFilterString() + ")";
		}
		else if(filter.getAdditionalFilterType().equals("Payment Agency")) {
			if(filter.isResourcesWherePaymentAgencyIsBlank())
				additionalFilterStr = " and cd.payment_agency_id is null";
			else if(filter.isResourcesWherePaymentAgencyasEntry())
				additionalFilterStr = " and cd.payment_agency_id is not null";
			else if(!filter.getAdditionalFilterString().isEmpty())
				additionalFilterStr = " and pa.agency_code in (" + filter.getAdditionalFilterString() + ")";
		}
		else if(filter.getAdditionalFilterType().equals("Cost Group")) {
			if(filter.isResourcesWhereCostGroupIsBlank())
				additionalFilterStr = " and dc.cost_group_id is null";
			else if(filter.isResourcesWhereCostGroupHasEntry())
				additionalFilterStr = " and dc.cost_group_id is not null";
			else if(!filter.getAdditionalFilterString().isEmpty())
				additionalFilterStr = " and cg.cost_group_name in (" + filter.getAdditionalFilterString() + ")";
		}
		else if(filter.getAdditionalFilterType().equals("Unit ID") && !filter.getAdditionalFilterString().isEmpty()) {
			additionalFilterStr = " and o.unit_code in (" + filter.getAdditionalFilterString() + ")";
		}
		//note:selecting an incident means want add itemCode as filter = selecting sgc.descriptions 
		//it is already in the select clause, so no needed to add something in selecting clause
		//For additional filter='Incident': the groupBy is 'incident'. the filter 
		//includeAll=all the itemCode (Sub-Category). selective=selected itemCode. 
		else if(filter.getAdditionalFilterType().equals("Incident") && !filter.getAdditionalFilterString().isEmpty()) {
			additionalFilterStr = " and sgc.description in (" + filter.getAdditionalFilterString() + ")";
		}
		
		
		return additionalFilterStr;
	}
}
