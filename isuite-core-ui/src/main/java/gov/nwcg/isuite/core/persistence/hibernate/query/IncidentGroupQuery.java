package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentGroupQuery {

   //If the user has a Data Steward role, only retrieve restricted Incidents to which they have access.
	public static String getAvailableIncidentsQuery(Long workAreaId, Long incidentGroupId, IncidentFilter filter, Long loggedInUserId) {
		StringBuffer sql = new StringBuffer()
		.append("SELECT distinct i.ID as id,")
		.append("i.INCIDENT_NAME as incidentName,")
		.append("ET.EVENT_TYPE as eventTypeName,")
		.append("cs.cs_abbreviation as stateCode,")
		.append("org.unit_code as unitCode,")
		.append("i.nbr as incidentNumber,")
		.append("I.INCIDENT_START_DATE as startDate,")
		.append("I.RESTRICTED_FLG as restricted, ")
		.append("AG.agency_code as agencyCode ")
		.append("from isw_incident i ")
		.append("  left join iswl_agency ag on i.agency_id = ag.id ")
		.append(",iswl_event_type et ")
		.append(",iswl_country_subdivision cs ")
		.append(",isw_organization org ");
		 if(loggedInUserId != null) {
		    sql.append(",isw_restricted_incident_user riu ");
		 }
		 sql.append("where et.id = i.event_type_id ")
		.append("and cs.id = I.COUNTRY_SUBDIVISION_ID ")
		.append("and org.id = i.unit_id ")
		.append("and I.RESTRICTED_FLG = :restrictedflg ")
		.append("and i.id in (select wai.incident_id from isw_work_area_incident wai where work_area_id = "+workAreaId+") ")
		.append("and i.id not in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentGroupId+") ");
		 if(loggedInUserId != null) {
		    sql.append("and riu.incident_id = i.id ")
		    .append("and riu.user_id = " + loggedInUserId + " ");
		 }
		 
		if(null != filter){
			if(StringUtility.hasValue(filter.getIncidentNumber()))
				sql.append("and i.nbr like '%"+filter.getIncidentNumber()+"%' ");
			if(StringUtility.hasValue(filter.getIncidentName())) {
				sql.append("and (i.incident_name like '%"+filter.getIncidentName()+"%' ");
				sql.append("or i.incident_name like '%"+filter.getIncidentName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getEventType())) {
				sql.append("and (et.event_type like '%"+filter.getEventType()+"%' ");
				sql.append("or et.event_type like '%"+filter.getEventType().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				sql.append("and (ag.agency_code like '%"+filter.getAgency()+"%' ");
				sql.append("or ag.agency_code like '%"+filter.getAgency().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getCountrySubdivision())) {
				sql.append("and (cs.cs_abbreviation like '%" + filter.getCountrySubdivision() + "%' ");
				sql.append("or cs.cs_abbreviation like '%" + filter.getCountrySubdivision().toUpperCase() + "%') ");
			}
			if(StringUtility.hasValue(filter.getHomeUnit())) {
				sql.append("and (org.unit_code like '%" + filter.getHomeUnit() + "%' ");
				sql.append("or org.unit_code like '%" + filter.getHomeUnit().toUpperCase() + "%') ");
			}
			//if(null != filter.getIncidentStartDate())
			//if(null != filter.getRestricted())
		}
		
		//"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
      sql.append("order by i.incident_name ");
		
		return sql.toString();
	}

	public static String getIncidentGroupIncidentsQuery(Long incidentGroupId, IncidentFilter filter) {
		StringBuffer sql = new StringBuffer()
			.append("SELECT distinct(i.ID) as id,")
			.append("i.INCIDENT_NAME as incidentName,")
			.append("ET.EVENT_TYPE as eventTypeName,")
			.append("cs.cs_abbreviation as stateCode,")
			.append("org.unit_code as unitCode,")
			.append("i.nbr as incidentNumber,")
			.append("I.INCIDENT_START_DATE as startDate,")
			.append("I.RESTRICTED_FLG as restricted, ")
			.append("AG.agency_code as agencyCode ")
			.append("from isw_incident_group ig, ")
			.append("isw_incident i ")
			.append("  left join iswl_agency ag on i.agency_id = ag.id ")
			.append("  left join iswl_country_subdivision cs on i.country_subdivision_id = cs.id ")
			.append(",iswl_event_type et ")
			.append(",isw_organization org ")
			.append("where i.id in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentGroupId+") ")
			.append("and et.id = i.event_type_id ")
			.append("and org.id = i.unit_id ")
			.append("and ig.id = " + incidentGroupId + " ");

		if(null != filter){
			if(StringUtility.hasValue(filter.getIncidentNumber()))
				sql.append("and i.nbr like '%"+filter.getIncidentNumber()+"%' ");
			if(StringUtility.hasValue(filter.getIncidentName())) {
				sql.append("and (i.incident_name like '%"+filter.getIncidentName()+"%' ");
				sql.append("or i.incident_name like '%"+filter.getIncidentName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getEventType())) {
				sql.append("and (et.event_type like '%"+filter.getEventType()+"%' ");
				sql.append("or et.event_type like '%"+filter.getEventType().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				sql.append("and (ag.agency_code like '%"+filter.getAgency()+"%' ");
				sql.append("or ag.agency_code like '%"+filter.getAgency().toUpperCase()+"%') ");
			}
		}
		
		//"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
      sql.append("order by i.nbr ");
		
		return sql.toString();
	}

	public static String getIncidentGroupUsersQuery(Long incidentGroupId, UserFilter filter) {
		StringBuffer sql = new StringBuffer()
			.append("SELECT distinct(u.ID) as id, ")
			.append("u.login_name as loginName, ")
			.append("u.first_name as firstName, ")
			.append("u.last_name as lastName, ")
			.append("o.unit_code as unitCode ")
			.append("FROM isw_user u, isw_incident_group_user igu, isw_organization o ")
			.append("WHERE u.id = igu.user_id ")
			.append("and igu.incident_group_id = " + incidentGroupId + " ")
			.append("and o.id = u.home_unit_id ");

		if(null != filter){
			if(StringUtility.hasValue(filter.getLoginName())) {
				sql.append("and (u.login_name like '%"+filter.getLoginName()+"%' ");
			   sql.append("or u.login_name like '%"+filter.getLoginName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getFirstName())) {
				sql.append("and (u.first_name like '%"+filter.getFirstName()+"%' ");
				sql.append("or u.first_name like '%"+filter.getFirstName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getLastName())) {
				sql.append("and (u.last_name like '%"+filter.getLastName()+"%' ");
				sql.append("or u.last_name like '%"+filter.getLastName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getHomeUnitCode())) {
				sql.append("and (o.unit_code like '%"+filter.getHomeUnitCode()+"%' ");
				sql.append("or o.unit_code like '%"+filter.getHomeUnitCode().toUpperCase()+"%') ");
			}
		}
		
		//"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
      sql.append("order by u.login_name ");
		
		return sql.toString();
	}
	
	public static String getIncidentGroupUserIdSQLQuery(Long userId, Long groupId) {
	   StringBuffer sql = new StringBuffer()
	   .append("select igu.id from ")
	   .append("isw_user u, isw_incident_group_user igu ")
	   .append("where u.id = igu.user_id ")
	   .append("and u.id = :userid ")
	   .append("and igu.incident_group_id = :groupid ");
	   
	   return sql.toString();
	}
	
	public static String getAvailableUsersQuery(Long workAreaId, Long incidentGroupId, UserFilter filter) {
		StringBuffer sql = new StringBuffer()
		.append("SELECT distinct(u.ID) as id, ")
		.append("u.login_name as loginName, ")
		.append("u.first_name as firstName, ")
		.append("u.last_name as lastName, ")
		.append("o.unit_code as unitCode ")
		.append("FROM isw_user u, isw_organization o ")
		.append("WHERE ")
		.append("u.id in (select wau.user_id from isw_work_area_user wau where work_area_id = "+workAreaId+") ")
		.append("and u.id not in (select user_id from isw_incident_group_user where incident_group_id = "+incidentGroupId+") ")
		.append("and u.id in (select distinct(user_id) from isw_user_role ur ")
		.append("where ur.role_id not in (select id from isw_system_role where is_privileged_role = :ispriv ) ) ")
		.append("AND o.id = u.home_unit_id ");

		if(null != filter){
			if(StringUtility.hasValue(filter.getLoginName())) {
				sql.append("and (u.login_name like '%"+filter.getLoginName()+"%' ");
				sql.append("or u.login_name like '%"+filter.getLoginName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getFirstName())) {
				sql.append("and (u.first_name like '%"+filter.getFirstName()+"%' ");
				sql.append("or u.first_name like '%"+filter.getFirstName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getLastName())) {
				sql.append("and (u.last_name like '%"+filter.getLastName()+"%' ");
				sql.append("or u.last_name like '%"+filter.getLastName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getHomeUnitCode())) {
				sql.append("and (o.unit_code like '%"+filter.getHomeUnitCode()+"%' ");
				sql.append("or o.unit_code like '%"+filter.getHomeUnitCode().toUpperCase()+"%') ");
			}
		}
		
		//"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
		sql.append("order by u.login_name ");
		
		return sql.toString();
	}
	
	public static String getAvailableUsersQuery2(Long incidentGroupId, UserFilter filter) {
		StringBuffer sql = new StringBuffer()
		.append("SELECT distinct(u.ID) as id, ")
		.append("u.login_name as loginName, ")
		.append("u.first_name as firstName, ")
		.append("u.last_name as lastName, ")
		.append("o.unit_code as unitCode ")
		.append("FROM isw_user u, isw_organization o ")
		.append("WHERE ")
		//.append("u.id in (select wau.user_id from isw_work_area_user wau where work_area_id = "+workAreaId+") ")
		/* let client filter users in group */
		//.append(" u.id not in (select user_id from isw_incident_group_user where incident_group_id = "+incidentGroupId+") ")
		.append(" u.id in (select distinct(user_id) from isw_user_role ur ")
		.append("where ur.role_id not in (select id from isw_system_role where is_privileged_role = :ispriv ) ) ")
		.append("AND o.id = u.home_unit_id ");

		if(null != filter){
			if(StringUtility.hasValue(filter.getLoginName())) {
				sql.append("and (u.login_name like '%"+filter.getLoginName()+"%' ");
				sql.append("or u.login_name like '%"+filter.getLoginName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getFirstName())) {
				sql.append("and (u.first_name like '%"+filter.getFirstName()+"%' ");
				sql.append("or u.first_name like '%"+filter.getFirstName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getLastName())) {
				sql.append("and (u.last_name like '%"+filter.getLastName()+"%' ");
				sql.append("or u.last_name like '%"+filter.getLastName().toUpperCase()+"%') ");
			}
			if(StringUtility.hasValue(filter.getHomeUnitCode())) {
				sql.append("and (o.unit_code like '%"+filter.getHomeUnitCode()+"%' ");
				sql.append("or o.unit_code like '%"+filter.getHomeUnitCode().toUpperCase()+"%') ");
			}
		}
		
		//"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
		sql.append("order by u.login_name ");
		
		return sql.toString();
	}
	
	public static String getAvailableUsersQuery3() {
		StringBuffer sql = new StringBuffer()
		.append("SELECT distinct(u.ID) as id, ")
		.append("u.login_name as loginName, ")
		.append("u.first_name as firstName, ")
		.append("u.last_name as lastName, ")
		.append("o.unit_code as unitCode ")
		.append("FROM isw_user u, isw_organization o ")
		.append("WHERE ")
		.append(" u.id in (select distinct(user_id) from isw_user_role ur ")
		.append("where ur.role_id not in (select id from isw_system_role where is_privileged_role = :ispriv ) ) ")
		.append("AND o.id = u.home_unit_id ");
		
		//"General Requirements - Grids.
		//When the grid initially displays, the system must sort data in the first column in ascending order."
		sql.append("order by u.login_name ");
		
		return sql.toString();
	}

	public static String getIncidentGroupsIncidentIdSQLQuery(Long incidentId) {
	   StringBuffer sql = new StringBuffer()
	   .append("select distinct incident_id ")
	   .append("from isw_incident_group_incident ")
	   .append("where incident_id = " + incidentId + " ");
	   return sql.toString();
	}

	public static String getGroupsIncidentIdsSQLQuery(String groupTi) {
		   StringBuffer sql = new StringBuffer()
		   .append("select distinct incident_id ")
		   .append("from isw_incident_group_incident ")
		   .append("where incident_group_id = (select id from isw_incident_group where transferable_identity='" + groupTi + "') ");
		   return sql.toString();
		}
	
	public static String removeIncidentFromGroupSQLQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("delete from isw_incident_group_incident ")
		.append("where incident_id = " + incidentId + " ");
		return sql.toString();
	}
	
	public static String getAvailableUserGroupsSQLQuery(UserGroupFilter filter) {
	   StringBuffer sql = new StringBuffer()
	   .append("select ")
	   .append("ug.id as groupid, ")
	   .append("ug.group_name as groupname ")
	   .append("from ") 
	   .append("isw_user_group ug ");

	   if(null != filter){
		   if(StringUtility.hasValue(filter.getGroupName())){
			   sql.append("WHERE ug.group_name LIKE '"+filter.getGroupName().toUpperCase()+"%' ");
		   }
	   }
	   
	   //"General Requirements - Grids.
      //When the grid initially displays, the system must sort data in the first column in ascending order."
      sql.append("order by ug.group_name ");
	   
	   return sql.toString();
	}
	
	public static String getCrossIncidentTimePostingsQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct ir.id) ")
		.append("from isw_incident i ")
		.append(", isw_incident_account_code iac ")
		.append(", isw_incident_resource ir ")
		.append(", isw_work_period wp ")
		.append(", isw_work_period_assignment wpa ")
		.append(", isw_assignment a ")
		.append(", isw_assignment_time at ")
		.append(", isw_assign_time_post atp ")
		.append("where i.id = " + incidentId + " ")
		.append("and IAC.INCIDENT_ID = i.id ")
		.append("and ir.incident_id = i.id ")
		.append("and wp.incident_resource_id = ir.id ")
		.append("and wpa.work_period_id = wp.id ")
		.append("and a.id = wpa.assignment_id ")
		.append("and at.assignment_id = a.id ")
		.append("and ( ")
		.append("   iac.id not in ( ")
		.append("    select incident_account_code_id ")
		.append("    from isw_assign_time_post ")
		.append("    where assignment_time_id = at.id ")
		.append("    ) ")
		.append("    or ")
		.append("    iac.id not in ( ")
		.append("        select incident_account_code_id ")
		.append("        from isw_time_assign_adjust ")
		.append("        where assignment_id = a.id ")
		.append("    ) ")
		.append(") ");
	
		return sql.toString();
	}

	public static String getCrossIncidentTimePostingsQuery2(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct atp.id) ")
		.append("from isw_assign_time_post atp ")
		.append("where atp.assignment_time_id in (")
		.append("    select at.id ")
		.append("    from isw_assignment_time at ")
		.append("         ,isw_assignment a ")
		.append("         ,isw_work_period_assignment wpa ")
		.append("         ,isw_work_period wp ")
		.append("         ,isw_incident_resource ir ")
		.append("     where at.assignment_id = a.id ")
		.append("     and a.id = wpa.assignment_id " )
		.append("     and wpa.work_period_id = wp.id " )
		.append("     and wp.incident_resource_id = ir.id " )
		.append("     and ir.incident_id = " + incidentId + " " )
		.append(") ")
		.append("and atp.incident_account_code_id not in (")
		.append("   select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + " ")
		.append(") ");
	
		return sql.toString();
	}
	
	public static String getCrossIncidentCostRecordsQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct irdc.id) ")
		.append("from isw_inc_res_daily_cost irdc ")
		.append("where irdc.incident_resource_id in ")
		.append("    (select ir.id ")
		.append("    from isw_incident_resource ir ")
		.append("    where ir.incident_id = " + incidentId + ") " )
		.append("and irdc.incident_account_code_id not in (")
		.append("   select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + " ")
		.append(") ");
	
		return sql.toString();
	}

	public static String getCrossIncidentTimePostingsInverseQuery(Long igId, Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct atp.id) ")
		.append("from isw_assign_time_post atp ")
		.append("where atp.incident_account_code_id in (")
		.append("   select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + " ")
		.append(") ")
		.append("and atp.assignment_time_id not in (")
		.append("    select at.id ")
		.append("    from isw_assignment_time at ")
		.append("         ,isw_assignment a ")
		.append("         ,isw_work_period_assignment wpa ")
		.append("         ,isw_work_period wp ")
		.append("         ,isw_incident_resource ir ")
		.append("     where at.assignment_id = a.id ")
		.append("     and a.id = wpa.assignment_id " )
		.append("     and wpa.work_period_id = wp.id " )
		.append("     and wp.incident_resource_id = ir.id " )
		.append("     and ir.incident_id = " + incidentId + " " )
		.append(") ");
	
		return sql.toString();
	}

	public static String getCrossIncidentTimeAdjustmentsQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct taa.id) ")
		.append("from isw_time_assign_adjust taa ")
		.append("where taa.assignment_id in (")
		.append("    select a.id ")
		.append("    from isw_assignment a ")
		.append("         ,isw_work_period_assignment wpa ")
		.append("         ,isw_work_period wp ")
		.append("         ,isw_incident_resource ir ")
		.append("     where a.id = wpa.assignment_id " )
		.append("     and wpa.work_period_id = wp.id " )
		.append("     and wp.incident_resource_id = ir.id " )
		.append("     and ir.incident_id = " + incidentId + " " )
		.append(") ")
		.append("and taa.incident_account_code_id not in (")
		.append("   select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + " ")
		.append(") ");
	
		return sql.toString();
	}

	public static String getCrossIncidentTimeAdjustmentsInverseQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct taa.id) ")
		.append("from isw_time_assign_adjust taa ")
		.append("where taa.incident_account_code_id in (")
		.append("   select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + " ")
		.append(") ")
		.append("and taa.assignment_id not in (")
		.append("    select a.id ")
		.append("    from isw_assignment a ")
		.append("         ,isw_work_period_assignment wpa ")
		.append("         ,isw_work_period wp ")
		.append("         ,isw_incident_resource ir ")
		.append("     where a.id = wpa.assignment_id " )
		.append("     and wpa.work_period_id = wp.id " )
		.append("     and wp.incident_resource_id = ir.id " )
		.append("     and ir.incident_id = " + incidentId + " " )
		.append(") ");
	
		return sql.toString();
	}
	
	public static String getCrossIncidentCostRecordsInverseQuery(Long incidentId) {
		StringBuffer sql = new StringBuffer()
		.append("select count(distinct irdc.id) ")
		.append("from isw_inc_res_daily_cost irdc ")
		.append("where irdc.incident_account_code_id in ")
		.append("   (select id ")
		.append("   from isw_incident_account_code iac")
		.append("   where iac.incident_id = " + incidentId + ") ")
		.append("and irdc.incident_resource_id not in ")
		.append("    (select ir.id ")
		.append("    from isw_incident_resource ir ")
		.append("    where ir.incident_id = " + incidentId + ") " );
	
		return sql.toString();
	}

	public static String getDeleteSettingsQuery1(Long incidentGroupId) {
		String sql = "DELETE FROM ISW_INCIDENT_GROUP_QUESTION WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " " ;
		return sql;
	}
	
	public static String getDeleteSettingsQuery2(Long incidentGroupId) {
		String sql = "DELETE FROM ISW_INCIDENT_GROUP_QS_KIND WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " " ;
		return sql;
	}
	
	public static String getDeleteSettingsQuery3(Long incidentGroupId) {
		String sql = "DELETE FROM ISW_INCIDENT_GROUP_PREFS WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " " ;
		return sql;
	}
	
	public static String getDeleteSettingsQuery4(Long incidentGroupId) {
		String sql = "DELETE FROM ISW_INCIDENT_PREFS_OTHERFIELDS WHERE INCIDENT_GROUP_ID = " + incidentGroupId + " " ;
		return sql;
	}

	public static String getCopyIncidentQSKindsQuery(Long incidentGroupId, Long primaryIncidentId) {
		String sql = "insert into isw_incident_group_qs_kind (id, incident_group_id, incident_group_kind_id) "+
				     "select SEQ_INCIDENT_GROUP_QS_KIND.nextVal, "+incidentGroupId + " , ik.incident_kind_id " +
				     "from isw_incident_qs_kind ik where ik.incident_id = " + primaryIncidentId + " " ;
		
		return sql;
	}
	
	public static String getCopyIncidentPrefsQuery(Long incidentGroupId, Long primaryIncidentId) {
		String sql = "insert into isw_incident_group_prefs (id, incident_group_id, section_name, field_label, position, is_selected) "+
				     "select SEQ_INCIDENT_GROUP_PREFS.nextVal, "+incidentGroupId + " , p.section_name, p.field_label, p.position, p.is_selected " +
				     "from isw_incident_prefs p where p.incident_id = " + primaryIncidentId + " " ;
		
		return sql;
	}

	public static String getCopyIncidentPrefsOtherFieldsQuery(Long incidentGroupId, Long primaryIncidentId) {
		String sql = "insert into isw_incident_prefs_otherfields (id, incident_id, incident_group_id, other_1_label, other_2_label, other_3_label) "+
				     "select SEQ_INCIDENT_GROUP_PREFS.nextVal, null,"+incidentGroupId + " , p.other_1_label, p.other_2_label, p.other_3_label " +
				     "from isw_incident_prefs_otherfields p where p.incident_id = " + primaryIncidentId + " " ;
		
		return sql;
	}

}
