package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.text.SimpleDateFormat;

import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;


public class AircraftDetailReportQuery {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getAircraftDetailSubReportDataQuery(AircraftDetailReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("select ")
		 .append("IRDC.ACTIVITY_DATE as activityDate,")
		 .append("CG.COST_GROUP_NAME as costGrp,")
		 .append("R.RESOURCE_NAME as name,")
		 .append("K.CODE as itemCode,")
		 .append("K.DESCRIPTION as itemDescription,")
		 .append("IRDC.aircraft_cost_amount as totalAmount,")
		 .append("IRDC.flight_hours as flightHrs,")
		 .append("IRDC.water_gallons as galWater,")
		 .append("IRDC.retardant_gallons as galRetard,")
		 .append("IRDC.number_of_loads as numOfLoads,")
		 .append("IRDC.number_of_trips as numOfTrips,")
		 .append("IRDC.number_of_passengers as numOfPax,")
		 .append("IRDC.cargo_pounds as lbsCargo")	 
		 
		 .append(" from ")
		 
   	     .append(" ISW_INCIDENT_ACCOUNT_CODE IAC ")
		 .append(" ,ISW_INCIDENT_RESOURCE IR ")
		 .append(", ISW_WORK_PERIOD WP ")
		 .append(", ISW_WORK_PERIOD_ASSIGNMENT WPA ")
		 .append(", ISW_ASSIGNMENT A ")
		 .append(" LEFT JOIN ISWL_KIND K ON A.KIND_ID = K.ID")
		 .append(", ISW_RESOURCE R")
		 .append(", ISW_INC_RES_DAILY_COST IRDC ")
		 .append(" LEFT JOIN ISW_COST_GROUP CG ON IRDC.COST_GROUP_ID = CG.ID")
		 
		 .append(" where ")
	
		 // dan defect #4506 commenting out below
		 //.append(" IR.INCIDENT_ID = " + incidentId)	   
		 //.append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
		 //.append(" and WPA.WORK_PERIOD_ID = WP.ID ")
		 //.append(" and A.ID = WPA.ASSIGNMENT_ID ")
		 //.append(" and R.ID = IR.RESOURCE_ID ")
		 //.append(" and IRDC.INCIDENT_RESOURCE_ID = IR.ID")
		 //.append(" and IRDC.INCIDENT_ACCOUNT_CODE_ID IN (select id from isw_incident_account_code where incident_id = "+incidentId+") ")
		 //.append(" and IRDC.RESOURCE_COST_TYPE='AIRCRAFT'");
			
		 // dan defect #4506 replacing above with below
		 .append("IAC.ID IN ( select id from isw_incident_account_code where incident_id = " + + incidentId + ") ")
		 .append("AND IRDC.INCIDENT_ACCOUNT_CODE_ID = IAC.ID " ) 
		 .append("AND IR.ID = IRDC.INCIDENT_RESOURCE_ID ")	   
		 .append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
		 .append(" and WPA.WORK_PERIOD_ID = WP.ID ")
		 .append(" and A.ID = WPA.ASSIGNMENT_ID ")
		 .append(" and R.ID = IR.RESOURCE_ID ")
		 .append(" and IRDC.COST_LEVEL in ('A','E','U','F','M') ")
		 .append("and IRDC.RESOURCE_COST_TYPE='AIRCRAFT' ");

		if(filter.isDateRangeSelected) {
			sql.append(" and IRDC.ACTIVITY_DATE between ")
			.append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			.append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
			
		if(filter.isSelectiveAircraftTypes())
			sql.append(" and K.CODE in (" + filter.getFormatedAircraftTypes() + ")");
		
		//sql.append(" order by IR.INCIDENT_ID, K.CODE, IRDC.ACTIVITY_DATE");
		
        ///////////////////////////resource_other////////////////////////////////////
        
        sql.append(" UNION ");
        
		sql.append("select ")
		 .append("IRDC.ACTIVITY_DATE as activityDate,")
		 .append("CG.COST_GROUP_NAME as costGrp,")
		 .append("RO.cost_description as name,")
		 .append("K.CODE as itemCode,")
		 .append("K.DESCRIPTION as itemDescription,")
		 .append("IRDC.aircraft_cost_amount as totalAmount,")
		 .append("IRDC.flight_hours as flightHrs,")
		 .append("IRDC.water_gallons as galWater,")
		 .append("IRDC.retardant_gallons as galRetard,")
		 .append("IRDC.number_of_loads as numOfLoads,")
		 .append("IRDC.number_of_trips as numOfTrips,")
		 .append("IRDC.number_of_passengers as numOfPax,")
		 .append("IRDC.cargo_pounds as lbsCargo")	 
		 
		 .append(" from ")
		 
  	     .append(" ISW_INCIDENT_ACCOUNT_CODE IAC ")
		 .append(" ,ISW_INCIDENT_RESOURCE_OTHER IRO ")
		 .append(", ISW_RESOURCE_OTHER RO")
		 .append(", ISW_INC_RES_DAILY_COST IRDC ")
		 .append(" LEFT JOIN ISW_COST_GROUP CG ON IRDC.COST_GROUP_ID = CG.ID")
		 .append(", iswl_kind k")
		 .append(" where ")
	
		 // dan defect #4506 commenting out below
		 //.append(" IR.INCIDENT_ID = " + incidentId)	   
		 //.append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
		 //.append(" and WPA.WORK_PERIOD_ID = WP.ID ")
		 //.append(" and A.ID = WPA.ASSIGNMENT_ID ")
		 //.append(" and R.ID = IR.RESOURCE_ID ")
		 //.append(" and IRDC.INCIDENT_RESOURCE_ID = IR.ID")
		 //.append(" and IRDC.INCIDENT_ACCOUNT_CODE_ID IN (select id from isw_incident_account_code where incident_id = "+incidentId+") ")
		 //.append(" and IRDC.RESOURCE_COST_TYPE='AIRCRAFT'");
			
		 // dan defect #4506 replacing above with below
		 .append("IAC.ID IN ( select id from isw_incident_account_code where incident_id = " + + incidentId + ") ")
		 .append("AND IRDC.INCIDENT_ACCOUNT_CODE_ID = IAC.ID " ) 
		 .append("AND IRO.ID = IRDC.INCIDENT_RESOURCE_OTHER_ID ")	   
		 .append(" and RO.ID = IRO.RESOURCE_OTHER_ID ")
		 .append(" and k.id = RO.kind_id ")
		 .append(" and IRDC.COST_LEVEL in ('A','E','U','F','M') ")
		 .append("and IRDC.RESOURCE_COST_TYPE='AIRCRAFT' ");

		if(filter.isDateRangeSelected) {
			sql.append(" and IRDC.ACTIVITY_DATE between ")
			.append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
			.append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
			
		if(filter.isSelectiveAircraftTypes())
			sql.append(" and K.CODE in (" + filter.getFormatedAircraftTypes() + ")");
		
		sql.append(" order by itemCode, activityDate");
		
		return sql.toString();
	}

	public static String getAircraftDetailSubReportChartDataQuery(AircraftDetailReportFilter filter, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
//		sql.append("select ")
//		   .append(" K.CODE as itemCode,")
//		   .append(" K.DESCRIPTION as itemDescription,")
//		   .append(" sum(IRDC.aircraft_cost_amount) as totalAmount")
		  
		sql.append("select ")
		   .append(" K.CODE as groupName,")
		   .append(" K.DESCRIPTION as description,")
		   .append(" sum(IRDC.aircraft_cost_amount) as totalAmount,")
		   .append(" ir.incident_id as incidentId") 
		   
		   
		   .append(" from ")
		   
		   .append(" ISW_INCIDENT_ACCOUNT_CODE IAC ")
		   .append(", ISW_INCIDENT_RESOURCE IR ")
		   .append(", ISW_WORK_PERIOD WP ")
		   .append(", ISW_WORK_PERIOD_ASSIGNMENT WPA ")
		   .append(", ISW_ASSIGNMENT A ")
		   .append(" LEFT JOIN ISWL_KIND K ON A.KIND_ID = K.ID")
		   .append(", ISW_RESOURCE R")
		   .append(", ISW_INC_RES_DAILY_COST IRDC ")
		   .append(" LEFT JOIN ISW_COST_GROUP CG ON IRDC.COST_GROUP_ID = CG.ID")
		 
		   .append(" where ")
	
		 // dan defect #4506 commenting out below
		   //.append(" IR.INCIDENT_ID = " + incidentId)	
		   //.append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
		   //.append(" and WPA.WORK_PERIOD_ID = WP.ID ")
		   //.append(" and A.ID = WPA.ASSIGNMENT_ID ")
		   //.append(" and R.ID = IR.RESOURCE_ID ")
		   //.append(" and IRDC.INCIDENT_RESOURCE_ID = IR.ID")
		   //.append(" and IRDC.INCIDENT_ACCOUNT_CODE_ID IN (select id from isw_incident_account_code where incident_id = "+incidentId+") ")
		   //.append(" and IRDC.RESOURCE_COST_TYPE='AIRCRAFT'");
	
		 // dan defect #4506 replacing above with below
		 .append("IAC.ID IN ( select id from isw_incident_account_code where incident_id = " + + incidentId + ") ")
		 .append("AND IRDC.INCIDENT_ACCOUNT_CODE_ID = IAC.ID " ) 
		 .append("AND IR.ID = IRDC.INCIDENT_RESOURCE_ID ")	   
		 .append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
		 .append(" and WPA.WORK_PERIOD_ID = WP.ID ")
		 .append(" and A.ID = WPA.ASSIGNMENT_ID ")
		 .append(" and R.ID = IR.RESOURCE_ID ")
		 .append(" and IRDC.COST_LEVEL in ('A','E','U','F','M') ")
		 .append("and IRDC.RESOURCE_COST_TYPE='AIRCRAFT' ");

		if(filter.isDateRangeSelected) {
				sql.append(" and IRDC.ACTIVITY_DATE between ")
				   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
				   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
			}
	
			if(filter.isSelectiveAircraftTypes()) {
				sql.append(" and K.CODE in (" + filter.getFormatedAircraftTypes() + ")");
			}
			sql.append(" group by k.code, k.description,ir.incident_id");
			
			///////////////////////////resource_other////////////////////////////////////
	        
	        sql.append(" UNION ");
	        
			sql.append("select ")
			   .append(" K.CODE as groupName,")
			   .append(" K.DESCRIPTION as description,")
			   .append(" sum(IRDC.aircraft_cost_amount) as totalAmount,")
			   .append(" iro.incident_id as incidentId") 			   
			   .append(" from ")
			   .append(" ISW_INCIDENT_ACCOUNT_CODE IAC ")
			   .append(", ISW_INCIDENT_RESOURCE_OTHER IRO ")
			   .append(", ISW_RESOURCE_OTHER RO")
			   .append(", ISW_INC_RES_DAILY_COST IRDC ")
			   .append(" LEFT JOIN ISW_COST_GROUP CG ON IRDC.COST_GROUP_ID = CG.ID ")
			   .append(", iswl_kind k")
			   .append(" where ")
		
			 // dan defect #4506 commenting out below
			   //.append(" IR.INCIDENT_ID = " + incidentId)	
			   //.append(" and WP.INCIDENT_RESOURCE_ID = IR.ID ")
			   //.append(" and WPA.WORK_PERIOD_ID = WP.ID ")
			   //.append(" and A.ID = WPA.ASSIGNMENT_ID ")
			   //.append(" and R.ID = IR.RESOURCE_ID ")
			   //.append(" and IRDC.INCIDENT_RESOURCE_ID = IR.ID")
			   //.append(" and IRDC.INCIDENT_ACCOUNT_CODE_ID IN (select id from isw_incident_account_code where incident_id = "+incidentId+") ")
			   //.append(" and IRDC.RESOURCE_COST_TYPE='AIRCRAFT'");
		
			 // dan defect #4506 replacing above with below
			 .append("IAC.ID IN ( select id from isw_incident_account_code where incident_id = " + + incidentId + ") ")
			 .append("AND IRDC.INCIDENT_ACCOUNT_CODE_ID = IAC.ID " ) 
			 .append("AND IRO.ID = IRDC.INCIDENT_RESOURCE_OTHER_ID ")	   
			 .append(" and RO.ID = IRO.RESOURCE_OTHER_ID ")
			 .append(" and k.id = RO.kind_id ")
			 .append(" and IRDC.COST_LEVEL in ('A','E','U','F','M') ")
			 .append("and IRDC.RESOURCE_COST_TYPE='AIRCRAFT' ");

			if(filter.isDateRangeSelected) {
					sql.append(" and IRDC.ACTIVITY_DATE between ")
					   .append(" to_timestamp('" + sdf.format(filter.getStartDate()) + " 00:00:00','yyyy-MM-dd hh24:mi:ss')")
					   .append(" and to_timestamp('" + sdf.format(filter.getEndDate()) + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
		
				if(filter.isSelectiveAircraftTypes()) {
					sql.append(" and K.CODE in (" + filter.getFormatedAircraftTypes() + ")");
				}
				sql.append(" group by k.code, k.description,iro.incident_id");

			return sql.toString();
	}
}
