package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;
import java.util.List;

import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;


public class SummaryHoursWorkedReportQuery {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
	public static String getSummaryHoursWorkedDateDataQuery(SummaryHoursWorkedReportFilter filter,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		List<String> date = filter.getReportDays();
		
		int daysCounter = 0; 
		int daysCounter1 = 0; 
		int numberOfWeek = filter.getNumOfWeeks();
		
		for (int i = 0; i < numberOfWeek; i++) {
			if(i != 0)
				sql.append(" UNION "); // union different weeks data		
			String selectStr = "SELECT incident_id as incidentId," +
			               "incident_name as incidentName," +
			               "incident_number as incidentNumber," +
				           "resource_id as resourceid," +
				           "MAX(request_number) as requestNumber," +
		                   "sortrequestnumber(request_number) as sortedRequestNumber," +   //call sortrequestnumber() Bill: not sure how this function works?
		                   "first_name as firstName," +
		                   "last_name as lastName," +
		                   "item_code as itemCode," +
		                   "status as status," +
		                   "section_name as sectionName," +
			"SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN hours_of_work else NULL END) as \"hoursWorkedDate1\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate2\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate3\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate4\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate5\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate6\"," +
		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate7\"," +
			"SUM(hours_of_work) as \"total\", " +
			 (i+1) + " as \"week\"";
			
			String fromStr = " FROM ISW_SCRATCH_REPORT_TIME";
				
			String whereStr = " WHERE transaction_id =" + filter.getTransactionId() + 
             " and TO_CHAR(shift_start_date, 'mm/dd/yyyy') >= '" + date.get(i*7) + "'" +  
             " and TO_CHAR(shift_start_date, 'mm/dd/yyyy') <= '" + date.get(i*7+6) + "'"; 
			
			String groupByStr = " group by incident_id, incident_name, incident_number, sortrequestnumber(request_number), first_name, last_name, resource_id, item_code, status, section_name";

			//the following select is for make the empty rows
			String selectStr1 = " UNION SELECT incident_id as incidentId," +
            "incident_name as incidentName," +
            "incident_number as incidentNumber," +
	        "resource_id as resourceid," +
	        "MAX(request_number) as requestNumber," +
            "sortrequestnumber(request_number) as sortedRequestNumber," + 
            "first_name as firstName," +
            "last_name as lastName," +
            "item_code as itemCode," +
            "status as status," +
            "section_name as sectionName," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN hours_of_work else NULL END) as \"hoursWorkedDate1\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate2\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate3\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate4\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate5\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate6\"," +
            "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter1++) + "' " +
            "THEN  hours_of_work else NULL END) as \"hoursWorkedDate7\"," +
            "0 as \"total\", " +
            (i+1) + " as \"week\"";

			String fromStr1 = " FROM ISW_SCRATCH_REPORT_TIME";	
			String whereStr1 = " WHERE transaction_id =" + filter.getTransactionId();
			String groupByStr1 = " group by incident_id, incident_name, incident_number, sortrequestnumber(request_number), first_name, last_name, resource_id, item_code, status, section_name";

			
			sql.append(selectStr);
			sql.append(fromStr);	
			sql.append(whereStr);
			sql.append(groupByStr);
			
			sql.append(selectStr1);
			sql.append(fromStr1);	
			sql.append(whereStr1);
			sql.append(groupByStr1);
		}
		
		String orderByStr; 
		if(filter.isAllResources) {
			if(filter.isSortByResourceName)
				orderByStr = " order by \"week\",incidentId,firstName,lastName";
			else if(filter.isSortByRequestNum)
				orderByStr = " order by \"week\",incidentId,sortedRequestNumber";
			else
				orderByStr = " order by \"week\",incidentId,firstName,lastName";
		}
		else 
			orderByStr = " order by \"week\",incidentId,sortedRequestNumber";
		
		sql.append(orderByStr);
	    return sql.toString();
	}
	
//	public static String getSummaryHoursWorkedDateDataQuery(SummaryHoursWorkedReportFilter filter,Boolean isOracle) {
//		StringBuffer sql = new StringBuffer();
//		List<String> date = filter.getReportDays();
//		
//		int daysCounter = 0; 
//		int numberOfWeek = filter.getNumOfWeeks();
//		
//		for (int i = 0; i < numberOfWeek; i++) {
//			if(i != 0)
//				sql.append(" UNION "); // union different weeks data		
//			String selectStr = "SELECT incident_id as incidentId," +
//			               "incident_name as incidentName," +
//			               "incident_number as incidentNumber," +
//				           "resource_id as resourceid," +
//		                   "request_number as requestNumber," + 
//		                   "first_name as firstName," +
//		                   "last_name as lastName," +
//		                   "item_code as itemCode," +
//		                   "status as status," +
//		                   "section_name as sectionName," +
//			"SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN hours_of_work else NULL END) as \"hoursWorkedDate1\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate2\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate3\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate4\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate5\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate6\"," +
//		    "SUM( CASE WHEN TO_CHAR(shift_start_date, 'mm/dd/yyyy') = '" + date.get(daysCounter++) + "' " +
//			"THEN  hours_of_work else NULL END) as \"hoursWorkedDate7\"," +
//			"SUM(hours_of_work) as \"total\", " +
//			 (i+1) + " as \"week\"";
//			
//			String fromStr = " FROM ISW_SCRATCH_REPORT_TIME";
//				
//			String whereStr = " WHERE transaction_id =" + filter.getTransactionId() + 
//             " and TO_CHAR(shift_start_date, 'mm/dd/yyyy') >= '" + date.get(i*7) + "'" +  
//             " and TO_CHAR(shift_start_date, 'mm/dd/yyyy') <= '" + date.get(i*7+6) + "'"; 
//			
//			String groupByStr = " group by incident_id, incident_name, incident_number, resource_id, request_number, first_name, last_name, item_code, status, section_name";
//				
//			sql.append(selectStr);
//			sql.append(fromStr);	
//			sql.append(whereStr);
//			sql.append(groupByStr);
//		}
//		
//		String orderByStr; 
//		if(filter.isAllResources) {
//			if(filter.isSortByRequestName)
//				orderByStr = " order by incidentId,firstName,lastName";
//			else if(filter.isSortByRequestNum)
//				orderByStr = " order by incidentId,requestNumber";
//			else
//				orderByStr = " order by incidentId,firstName,lastName";
//		}
//		else 
//			orderByStr = " order by incidentId,firstName,lastName";
//		
//		sql.append(orderByStr);
//	    return sql.toString();
//	}
}
