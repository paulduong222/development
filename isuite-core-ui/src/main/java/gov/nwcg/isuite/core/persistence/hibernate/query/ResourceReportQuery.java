package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ResourceReportQuery {
	
	public static String STTFReportQuery(Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT r.id as resourceId")
		.append(",a.request_number as requestNumber")
		.append(", case ")
		.append("when r.is_person = " + (isOracle ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("else ")
		.append("r.resource_name ")
		.append("end as resourceName ")
		.append(",k.code as kind")
		.append(",k.description as itemName")
		.append(",a.assignment_status as status")
		.append(", r.number_of_personnel as numPersonnel ")
		.append(",ag.agency_code as agency")
		.append(",rorg.unit_code as unit")
		.append(",wp.dm_travel_method as returnTravelMethod ")
		.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) ");
		
		if(isOracle){
		    sql.append("then (wp.ci_first_work_date + (wp.ci_length_at_assignment-1)) ");
	    }else{
		    sql.append( "then (wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment - interval '1 day') ");
	    }
		
		sql.append("end as lastWorkDay")
		.append(",wp.dm_tentative_release_date as tentativeReleaseDate ")
		.append(",wp.dm_release_date as actualReleaseDate ")
		.append("FROM isw_incident i ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
		.append("left join isw_organization rorg on r.organization_id = rorg.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" where r.id in (:rids) ")
		.append("ORDER BY SORTREQUESTNUMBER(a.request_number)");
		
		return sql.toString();
	}
	
	public static String STTFReportChildrenQuery(Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT r.id as resourceId")
		.append(",a.request_number as requestNumber")
		.append(", case ")
		.append("when r.is_person = " + (isOracle ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		.append("else ")
		.append("r.resource_name ")
		.append("end as resourceName ")
		.append(",k.code as kind")
		.append(",k.description as itemName")
		.append(",a.assignment_status as status")
		.append(", r.number_of_personnel as numPersonnel ")
		.append(",ag.agency_code as agency")
		.append(",rorg.unit_code as unit")
		.append(",wp.dm_travel_method as returnTravelMethod ")
		.append(", case when (not wp.ci_first_work_date is null AND not wp.ci_length_at_assignment is null ) ");
		
		if(isOracle){
		    sql.append("then (wp.ci_first_work_date + (wp.ci_length_at_assignment-1)) ");
	    }else{
		    sql.append( "then (wp.ci_first_work_date + interval '1 day' * wp.ci_length_at_assignment - interval '1 day') ");
	    }
		
		sql.append("end as lastWorkDay")
		.append(",wp.dm_tentative_release_date as tentativeReleaseDate ")
		.append(",wp.dm_release_date as actualReleaseDate ")
		.append("FROM isw_incident i ")
		.append("join isw_incident_resource ir on i.id = ir.incident_id ")
		.append("join isw_resource r on ir.resource_id = r.id ")
		.append("left join isw_organization rorg on r.organization_id = rorg.id ")
		.append("left join isw_work_period wp on ir.id = wp.incident_resource_id ")
		.append("left join isw_work_period_assignment wpa on wp.id = wpa.work_period_id ")
		.append("left join isw_assignment a on wpa.assignment_id = a.id ")
		.append("left join iswl_kind k on a.kind_id = k.id ")
		.append("left join iswl_agency ag on r.agency_id = ag.id ")
		.append(" where r.parent_resource_id in (:prid) ")
		.append("ORDER BY SORTREQUESTNUMBER(a.request_number)");
		
		return sql.toString();
		
	}

}
