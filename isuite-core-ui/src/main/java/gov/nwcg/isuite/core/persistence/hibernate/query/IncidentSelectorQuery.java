package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.LongUtility;

public class IncidentSelectorQuery {

	public static String REMOVE_EXCLUDED_INCIDENTS_QUERY = 
		"DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE user_id = :userid AND incident_id not in ( :incidentids ) ";

	public static String REMOVE_ALL_EXCLUDED_INCIDENTS_QUERY = 
		"DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE user_id = :userid AND incident_id > 0 ";
	
	public static String REMOVE_EXCLUDED_INCIDENT_GROUPS_QUERY = 
		"DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE user_id = :userid AND incident_group_id not in ( :incidentgroupids ) ";

	public static String REMOVE_ALL_EXCLUDED_INCIDENT_GROUPS_QUERY = 
		"DELETE FROM ISW_USER_INCVIEW_EXCLUDE WHERE user_id = :userid AND incident_group_id > 0 ";

	public static String createExcludedIncidentQuery(Long userId,Long incidentId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ISW_USER_INCVIEW_EXCLUDE (ID, USER_ID, INCIDENT_ID) ")
		   .append("VALUES (" + (isOracle ? "SEQ_USERINCVIEWEX.nextVal" : "nextVal('SEQ_USERINCVIEWEX')") + ", " + userId + ", "+ incidentId + ") ");
		
		return sql.toString();
	}

	public static String createExcludedIncidentGroupQuery(Long userId,Long incidentGroupId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ISW_USER_INCVIEW_EXCLUDE (ID, USER_ID, INCIDENT_GROUP_ID) ")
		   .append("VALUES (" + (isOracle ? "SEQ_USERINCVIEWEX.nextVal" : "nextVal('SEQ_USERINCVIEWEX')") + ", " + userId + ", "+ incidentGroupId + ") ");
		
		return sql.toString();
	}

	/**
	 * Returns the query to retrieve incidents that the user can access.
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUserIncidentQuery(Long userId, Long incidentId, Boolean filterExcluded, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(i.ID) as id ")
		   .append(",i.id as incidentId ")
		   .append(",i.incident_name as incidentName ")
		   .append(",et.event_type as eventTypeName ")
		   .append(",'US-' || org.unit_code || '-' || i.nbr as incidentNumber ")
		   .append(",ag.agency_code as agencyCode ")
		   .append(",to_char(i.incident_start_date,'MM/DD/YYYY') as incidentStartDate ")
		   .append(",ac.account_code as accountCode ")
		   .append(",acag.agency_code as accountCodeAgency ")
		   .append(",'INCIDENT' as type ")
		   .append("FROM isw_incident i ")
		   .append("   LEFT JOIN iswl_agency ag on ag.id = i.agency_id ")
		   .append("   LEFT JOIN isw_incident_account_code iac on (")
		   .append("       iac.incident_id = i.id ")
		   .append("       and ")
		   .append("       iac.default_flg = " + ( isOracle ? 1 : true ) + " ")
		   .append("   ) ")
		   .append("   LEFT JOIN isw_account_code ac on ac.id = iac.account_code_id ")
		   .append("   LEFT JOIN iswl_agency acag on acag.id = ac.agency_id ")
		   .append(", isw_restricted_incident_user riu ")
		   .append(", iswl_event_type et ")
		   .append(", isw_organization org ")
		   .append("WHERE i.id NOT IN (select incident_id from isw_incident_group_incident ) ")
		   .append("AND et.id = i.event_type_id ")
		   .append("AND org.id = i.unit_id ")
		   .append("AND riu.incident_id = i.id ")
		   .append("AND riu.user_id = " + userId + " " );
		
			if(filterExcluded==true)
				sql.append("AND i.id not in (select incident_id from ISW_USER_INCVIEW_EXCLUDE where user_id = " + userId + ") ");
		
			if(LongUtility.hasValue(incidentId))
				sql.append("AND i.id = " + incidentId + " ");
		
		return sql.toString();
	}
	
	/**
	 * Returns the query to retrieve incident groups that the user can access.
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUserIncidentGroupQuery(Long userId, Long incidentGroupId, Boolean filterExcluded) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(ig.ID) as id ")
		   .append(",ig.id as incidentGroupId ")
		   .append(",ig.group_name as groupName ")
		   .append(",'INCIDENTGROUP' as type ")
		   .append("FROM isw_incident_group ig ")
		   .append(", isw_incident_group_user igu ")
		   .append("WHERE igu.incident_group_id = ig.id ")
		   .append("AND igu.user_id = " + userId + " " );
		
		if(filterExcluded==true)
			sql.append("AND igu.id not in (select incident_group_id from ISW_USER_INCVIEW_EXCLUDE where user_id = " + userId + ") ");
		
		if(LongUtility.hasValue(incidentGroupId))
			sql.append("AND ig.id = " + incidentGroupId + " ");
		
		return sql.toString();
	}
	
	/**
	 * Returns the query to retrieve incident group incidents.
	 * 
	 * @return
	 */
	public static String getIncidentGroupIncidentsQuery(Long groupId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(i.ID) as id ")
		   .append(",i.incident_name as incidentName ")
		   .append(",et.event_type as eventTypeName ")
		   .append(",'US' as stateCode ")
		   .append(",org.unit_code as unitCode ")
		   .append(",i.nbr as incidentNumber ")
		   .append(",ag.agency_code as agencyCode ")
		   .append(",i.incident_start_date as incidentStartDate ")
		   .append(",'INCIDENT' as type ")
		   .append(",ac.account_code as accountCode ")
		   .append(",acag.agency_code as accountCodeAgency ")
		   .append("FROM isw_incident i ")
		   .append("   LEFT JOIN iswl_agency ag on ag.id = i.agency_id ")
		   .append("   LEFT JOIN isw_incident_account_code iac on iac.incident_id = i.id ")
		   .append("   LEFT JOIN isw_account_code ac on ac.id = iac.account_code_id ")
		   .append("   LEFT JOIN iswl_agency acag on acag.id = ac.agency_id ")
		   .append("WHERE i.id IN (")
		   .append("       select incident_id from isw_incident_group_incident ")
		   .append("       where incident_group_id = " + groupId + " ")
		   .append(") ")
		   .append("AND et.id = i.event_type_id ")
		   .append("AND iac.default_flg = " + (isOracle ? 1 : true ) + " ")
		   .append("AND org.id = i.unit_id ");
		
		return sql.toString();
	}
	
	public static String getUserIncidentExcludedQuery(Long userId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(i.ID) as id ")
		   .append(",i.id as incidentId ")
		   .append(",i.incident_name as incidentName ")
		   .append(",et.event_type as eventTypeName ")
		   .append(",'US-' || org.unit_code || '-' || i.nbr as incidentNumber ")
		   .append(",ag.agency_code as agencyCode ")
		   .append(",to_char(i.incident_start_date,'MM/DD/YYYY') as incidentStartDate ")
		   .append(",ac.account_code as accountCode ")
		   .append(",acag.agency_code as accountCodeAgency ")
		   .append(",'INCIDENT' as type ")
		   .append("FROM isw_incident i ")
		   .append("   LEFT JOIN iswl_agency ag on ag.id = i.agency_id ")
		   .append("   LEFT JOIN isw_incident_account_code iac on (")
		   .append("       iac.incident_id = i.id ")
		   .append("       and ")
		   .append("       iac.default_flg = " + ( isOracle ? 1 : true ) + " ")
		   .append("   ) ")
		   .append("   LEFT JOIN isw_account_code ac on ac.id = iac.account_code_id ")
		   .append("   LEFT JOIN iswl_agency acag on acag.id = ac.agency_id ")
		   .append(", isw_restricted_incident_user riu ")
		   .append(", iswl_event_type et ")
		   .append(", isw_organization org ")
		   .append("WHERE i.id NOT IN (select incident_id from isw_incident_group_incident ) ")
		   .append("AND et.id = i.event_type_id ")
		   .append("AND org.id = i.unit_id ")
		   .append("AND riu.incident_id = i.id ")
		   .append("AND riu.user_id = " + userId + " " )
		   .append("AND i.id in (select incident_id from ISW_USER_INCVIEW_EXCLUDE where user_id = " + userId + ") ");
		
		return sql.toString();
	}
	
	
	/**
	 * Returns the query to retrieve incident groups that the user has excluded
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUserIncidentGroupExcludedQuery(Long userId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(ig.ID) as id ")
		   .append(",ig.id as incidentGroupId ")
		   .append(",ig.group_name as groupName ")
		   .append(",'INCIDENTGROUP' as type ")
		   .append("FROM isw_incident_group ig ")
		   .append(", isw_incident_group_user igu ")
		   .append("WHERE igu.incident_group_id = ig.id ")
		   .append("AND igu.user_id = " + userId + " " )
		   .append("AND ig.id in (select incident_group_id from ISW_USER_INCVIEW_EXCLUDE where user_id = " + userId + ") ");
		
		return sql.toString();
	}


	
}
