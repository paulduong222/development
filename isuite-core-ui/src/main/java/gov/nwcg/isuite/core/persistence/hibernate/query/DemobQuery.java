package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.framework.util.LongUtility;

public class DemobQuery {

	public static String buildQueryDemobPlanning(DemobPlanningReportFilter filter, Boolean isOracle) {
	  StringBuffer b = new StringBuffer();
	  
    b.append("SELECT i.id as incidentId")
    .append(",i.incident_name as incidentName")
    .append(",i.nbr as incidentNumber")
    .append(",rq.code as category")
    .append(",d.description as section")
    
    .append(",a.request_number as requestNumber");

	if(isOracle==true){
		b.append(", case ")
		.append("  when (wp.dm_is_reassignable is not null and wp.dm_is_reassignable=1) then 'YES' ")
		.append("else 'NO' ")
		.append("end as reassign ");
	}else{
		b.append(", case ")
		.append("  when (wp.dm_is_reassignable is not null and wp.dm_is_reassignable=true) then 'YES' ")
		.append("else 'NO' ")
		.append("end as reassign ");
	}
    
	if(isOracle==true){
		b.append(", case ")
		.append("when r.is_person = 0 then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName ");
	}else{
		b.append(", case ")
		.append("when r.is_person = false then r.resource_name ")
		.append("else ")
		.append("r.last_name || ', ' || r.first_name ")
		.append("end as resourceName ");
	}
    
    if(isOracle){
	    // crew leader:
    	b.append(",(select last_name || ', ' || first_name from isw_resource " +
	        "where leader_type = 1 and parent_resource_id = r.id and deleted_date is null " +
	        "and id = (select max(id) from isw_resource where leader_type=1 and parent_resource_id=r.id ) ) as crewLeader, ");
    }else{
    	b.append(",(select last_name || ', ' || first_name from isw_resource " +
        "where leader_type = 1 and parent_resource_id = r.id and deleted_date is null " +
        "and id = (select max(id) from isw_resource where leader_type=1 and parent_resource_id=r.id  ) ) as crewLeader, ");
    }
    
    //Bill:
    //defect 4979 - only needed in sockeye incident since leader_type was not set to 99 when deleted (multiple hits in sub select statement)
    //Add "and deleted_date is null" in select statement 
    	
    // TODO (june 8, 2011): resource qualifications list, deferred task
	if(isOracle){
		b.append("(SELECT WM_CONCAT(qual.code) FROM iswl_kind qual where id in (select kind_id from isw_resource_kind where resource_id = r.id ) ) as qualifications ");
	}else{
		b.append("(SELECT ARRAY_TO_STRING(ARRAY(SELECT qual.code FROM iswl_kind qual " +
		"WHERE id in (select kind_id from isw_resource_kind where resource_id = r.id )),',') as ARRAY_TO_STRING) AS qualifications ");
	}
    
   b.append(",k.code as itemCode")
    .append(",a.is_training as training")
    .append(",a.assignment_status as status")
    .append(",ag.agency_code as agency")
    .append(",org.unit_code as unit")
    
    .append(",case when wp.dm_travel_method = 'AR' then 'A/R' else wp.dm_travel_method end as returnTravelMethod")
    .append(",wp.dm_tentative_demob_city as demobCity")
    .append(",cs.cs_abbreviation as demobState");
    
    if(isOracle){
	    b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
	            "then (wp.ci_first_work_date + (wp.ci_length_at_assignment-1)) " +
	            "end as lastWorkDay");
    }else{
	    b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
	            "then (wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment - interval '1 day') " +
	            "end as lastWorkDay");
    } 
    
    // daysLeft is calculated from (firstworkdate+lengthatassignment) - today
    if(isOracle){
    	b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) then ")
        .append("case when (wp.ci_first_work_date + (wp.ci_length_at_assignment) < (to_date(to_char(sysdate,'MM/DD/YYYY') || ' 00:00','MM/DD/YYYY HH24:MI')) ) ")
        .append("then 0 else  trunc((wp.ci_first_work_date + wp.ci_length_at_assignment)-(to_date(to_char(sysdate,'MM/DD/YYYY') || ' 00:00','MM/DD/YYYY HH24:MI'))) end ")
        .append("else null end as daysLeft");
    }else{
    	b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) then ")
        //.append("case when ((wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment) < (to_date(to_char(now(),'MM/DD/YYYY') || ' 00:00','MM/DD/YYYY HH24:MI') ) ) ")
        .append("case when ci_first_work_date + (interval '1 day ' * ci_length_at_assignment) < (to_date(to_char(now(),'MM/DD/YYYY') || ' 00:00','MM/DD/YYYY HH24:MI')) ")
        //.append("then 0 else extract(day from ((wp.ci_first_work_date + (interval '1 day' * wp.ci_length_at_assignment)) - now())) end ")
        .append("then 0 else extract(day from (ci_first_work_date + (interval '1 day ' * ci_length_at_assignment) - (to_date(to_char(now(),'MM/DD/YYYY') || ' 00:00','MM/DD/YYYY HH24:MI')) )) end ")
        .append("else null end as daysLeft");
    }
            
    //Bill:
    //defect 4992
    //oracle fix: from 
    //.append("then 0 else (extract(day from wp.ci_first_work_date) + (wp.ci_length_at_assignment) - extract(day from sysdate)) end ")
    //oracle fix: to
    //.append("then 0 else  trunc((wp.ci_first_work_date + wp.ci_length_at_assignment)-sysdate) end ")
    
    //defect 4992
    //postgres fix: from 
    //.append("then 0 else (extract(day from wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment) - extract(day from now())) end ")
    //postgres fix: to
    //.append("then 0 else extract(day from ((wp.ci_first_work_date + (interval '1 day' * wp.ci_length_at_assignment)) - now())) end ")
    
    b.append(",wp.dm_tentative_release_date as releaseDate ")
    // tentative release time is part of release Date
    // reassign (is this a status of "r"?)
    
    .append(", case ")
    .append("when (not r.number_of_personnel is null AND r.number_of_personnel != 0) " +
    		"then r.number_of_personnel ")
    .append("when r.is_person = " + (isOracle ? 1 : true) + " " +
    		"then 1 else 0 ")
    .append("end as personCount ")
    ;
     
    // add firstSort
    for(String field : filter.getSorts()){
      if(field.equals("lastWorkDay")){
    	  if(isOracle){
    	        b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
    	                "then (wp.ci_first_work_date + (wp.ci_length_at_assignment-1)) " +
    	                "end as firstSortDate ");
    	  }else{
    	        b.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) " +
    	                "then (wp.ci_first_work_date + interval '1 day' * (wp.ci_length_at_assignment-1)) " +
    	                "end as firstSortDate ");
    	  }
      } else {
        b.append(", ");
        if(field.equals("requestNumber")){
          b.append("sortrequestnumber(a.request_number)");
        }else if(field.equals("resourceName")){
          b.append(" case ")
          .append("when r.is_person = " + (isOracle ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
          .append("else ")
          .append("r.resource_name ")
          .append("end ");
        }else if(field.equals("itemCode")){
          b.append("k.code");
        }else if(field.equals("status")){
          b.append("a.assignment_status");
        }else if(field.equals("agency")){
          b.append("ag.agency_code");
        }else if(field.equals("unit")){
          b.append("org.unit_code");
        }else if(field.equals("demobCity")){
          b.append("wp.dm_tentative_demob_city");
        }else if(field.equals("demobState")){
          b.append("cs.cs_abbreviation");
        }else if(field.equals("returnTravelMethod")){
          b.append("wp.dm_travel_method");        
        }else if(field.equals("daysLeft")){
            if(isOracle){
            	b.append(" case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) then ")
                .append("case when (wp.ci_first_work_date + (wp.ci_length_at_assignment) < sysdate) ")
                .append("then 0 else  trunc((wp.ci_first_work_date + wp.ci_length_at_assignment)-sysdate) end ")
                .append("else null end ");
            }else{
            	b.append(" case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) then ")
                .append("case when ((wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment) < now()) ")
                .append("then 0 else extract(day from ((wp.ci_first_work_date + (interval '1 day' * wp.ci_length_at_assignment)) - now())) end ")
                .append("else null end ");
            }
        }

        b.append(" as firstSort");
      }
      
      break;
    }
    
    
    b.append(" FROM isw_incident i")
    //.append(" left join isw_organization io on i.unit_id = io.id ")
    .append(",isw_incident_resource ir")
    .append(",isw_resource r")
    .append("  left join iswl_agency ag on r.agency_id = ag.id")
    .append("  left join isw_organization org on r.organization_id = org.id")
    .append(",isw_work_period wp")
    .append("  left join iswl_country_subdivision cs on wp.dm_tentative_demob_state_id = cs.id")
    //.append("  left join iswl_jet_port jp on wp.ci_arrival_jet_port_id = jp.id")
    //.append("  left join isw_resource_mobilization rm on wp.ci_mobilization_id = rm.id ")
    .append(",isw_work_period_assignment wpa")
    .append(",isw_assignment a")
    .append(",iswl_kind k ")
    .append("   left join iswl_department d on k.department_id = d.id ")
    .append(",iswl_request_category rq");
    if(LongUtility.hasValue(filter.getIncidentId()))
    	b.append(" WHERE i.id = "+filter.getIncidentId() + " ");
    else if(LongUtility.hasValue(filter.getIncidentGroupId()))
    	b.append(" WHERE i.id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getIncidentGroupId() + ") ");
    b.append(" AND i.id = ir.incident_id")
    .append(" AND ir.resource_id = r.id ")
    .append(" AND ir.id = wp.incident_resource_id")
    .append(" AND wp.id = wpa.work_period_id")
    .append(" AND wpa.assignment_id = a.id")
    .append(" AND a.kind_id = k.id")
    .append(" AND k.request_category_id = rq.id")
    ;

    
    // add filters
    if(DemobPlanningReportFilter.getTypes(filter).length()>0){
      b.append(" AND rq.code in (" + DemobPlanningReportFilter.getTypes(filter) + ")");
    }
    
    if(DemobPlanningReportFilter.getStatuses(filter).length() > 0){
      b.append(" AND a.assignment_status in (" + DemobPlanningReportFilter.getStatuses(filter) + ")");
    }

    if(DemobPlanningReportFilter.getSections(filter).length() > 0){
      b.append(" AND d.code in (" + DemobPlanningReportFilter.getSections(filter) + " ) ");
    }
    
    if(!filter.getIncludeSTTFComponents()){
		//b.append(" AND k.is_strike_team = " + (isOracle ? 0 : false) + " ");
		b.append(" AND ")
		 .append(" (")
		 .append("   r.parent_resource_id is null ")
		 .append(" ) ");
		/* c.r. only include primary , do not include subordinates
		 .append("    or " )
		 .append("   r.parent_resource_id in ")
		 .append("   ( ")
		 .append("     select resource_id from isw_incident_resource ir2 ")
		 .append("                             ,isw_work_period wp2 ")
		 .append("                             ,isw_work_period_assignment wpa2 ")
		 .append("                             ,isw_assignment a2 ")
		 .append("                             ,iswl_kind k2 ")
		 .append("     where ir2.resource_id = r.parent_resource_id ")
		 .append("     and wp2.incident_resource_id = ir2.id ")
		 .append("     and wpa2.work_period_id = wp2.id ")
		 .append("     and a2.id = wpa2.assignment_id " )
		 .append("     and a2.end_date is null ")
		 .append("      and k2.id = a2.kind_id ")
		 .append("     and k2.is_strike_team = " + (isOracle ? 0 : false) + " ")
		 .append("   ) ")
		 .append(" ) ");
	      */
	      //b.append(" AND (r.resource_classification IS NULL OR r.resource_classification not in ('ST','TF'))"); 
	      //b.append(" AND r.is_component = '0' ");
    } else {
		 b.append(" AND (r.parent_resource_id is null or ")
		 .append(" r.parent_resource_id in ")
		 .append(" (select ir2.resource_id ")
		 .append(" from isw_incident_resource ir2 ")
		 .append(" ,isw_work_period wp2 ")
		 .append(" ,isw_work_period_assignment wpa2 ")
		 .append(" ,isw_assignment a2 ")
		 .append(" ,iswl_kind k2 ")
		 .append(" where ir2.resource_id = r.parent_resource_id ")
		 .append(" and wp2.incident_resource_id = ir2.id ")
		 .append(" and wpa2.work_period_id = wp2.id ")
		 .append(" and a2.id = wpa2.assignment_id ")
		 .append(" and a2.end_date is null ")
		 .append(" and k2.id = a2.kind_id ")
		 .append(" and k2.is_strike_team = " + (isOracle ? 1 : true) + "))");
    }
    
    // add sorting
    b.append(" ORDER BY incidentName, incidentNumber, category, section ");
    
    if(null != filter.getSorts() && filter.getSorts().size() > 0){
      String sortBy="";
      for(String field : filter.getSorts()){
    	  if(field.equals("requestNumber")){
             sortBy=sortBy + ", sortrequestnumber(a.request_number)";
    	  }else {
    		  sortBy=sortBy + "," + field;
    	  }
      }
      b.append(sortBy);
    }
    
    //System.out.println("OUT: " + b.toString());
		return b.toString();
	}
	
}
