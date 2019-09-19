package gov.nwcg.isuite.core.persistence.hibernate.query;



public class IncidentUserQuery {

	// want to use these next queries as triggers, but use straight manual sql for now
	public static String getInsertIncidentUserCount(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT COUNT(U.ID) ")
		   .append("FROM ISW_USER U ")
		   .append("WHERE U.ID NOT IN (")
		   .append("   SELECT USER_ID FROM ISW_RESTRICTED_INCIDENT_USER ")
		   .append("   WHERE INCIDENT_ID = "+incidentId + " ")
		   .append(" ) ")
		   .append("AND U.ID NOT IN (select user_id from isw_user_role where role_id in (select id from isw_system_role where is_privileged_role = "+(isOracle ? 1 : true)+" ) )")
		;		
		
		return sql.toString();
	}

	public static String getInsertIncidentUser(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_RESTRICTED_INCIDENT_USER (ID, INCIDENT_ID, USER_ID, USER_TYPE) ")
		   .append("SELECT "+(isOracle ? "SEQ_RESTRICTED_INCIDENT_USER.nextVal" : "nextVal('SEQ_RESTRICTED_INCIDENT_USER') ")+","+incidentId+", U.id, 'USER' ")
		   .append("FROM ISW_USER U ")
		   .append("WHERE U.ID NOT IN (SELECT USER_ID FROM ISW_RESTRICTED_INCIDENT_USER WHERE INCIDENT_ID = "+incidentId+" ) ")
		   .append("AND U.ID NOT IN (select user_id from isw_user_role where role_id in (select id from isw_system_role where is_privileged_role = "+(isOracle ? 1 : true)+" ) )")
		   ;
		
		return sql.toString();
	}
	
	public static String getInsertIncidentNewUserCount(Long userId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(I.ID) ")
			.append("FROM ISW_INCIDENT I ")
			.append("WHERE I.ID NOT IN (SELECT DISTINCT(INCIDENT_ID) FROM ISW_RESTRICTED_INCIDENT_USER WHERE USER_ID = "+userId+") ");
		
		return sql.toString();
	}
	
	public static String getInsertIncidentNewUser(Long userId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ISW_RESTRICTED_INCIDENT_USER (ID, INCIDENT_ID, USER_ID, USER_TYPE) ")
			.append("SELECT "+(isOracle ? "SEQ_RESTRICTED_INCIDENT_USER.nextVal" : "nextVal('SEQ_RESTRICTED_INCIDENT_USER')")+",I.ID, "+userId+", 'USER' ")
			.append("FROM ISW_INCIDENT I ")
			.append("WHERE I.ID NOT IN (SELECT DISTINCT(INCIDENT_ID) FROM ISW_RESTRICTED_INCIDENT_USER WHERE USER_ID = "+userId+") ");
		
		return sql.toString();
	}

	public static String getAvailableUsersQuery(Long incidentId, Boolean isOracle){
		StringBuffer sql = new StringBuffer();
			
		sql.append("select u.id as userId ")
		   .append(",u.LOGIN_NAME as loginName ")
	       .append(",u.first_name as firstName ")
	       .append(",u.last_name as lastName ")
	       .append(",org.unit_code as homeUnitCode ")
	       .append("from isw_user u ")
	       .append("        left join isw_organization org on u.HOME_UNIT_ID = org.id ")
	       .append("where u.enabled = "+(isOracle ? 1 : true ) + " ")
	       .append("and u.deleted_date is null ")
	       .append("and u.id in " +
	       		    "(" +
	       				"select user_id from isw_user_role where role_id not in " +
	       				"(  select id from isw_system_role where is_privileged_role = " + (isOracle==true ? 1 : true) + ") " +
	       			") ");
		/*
		if(LongUtility.hasValue(incidentId)){
		   sql.append("and u.id not in (")
		      .append("   select user_id from isw_restricted_incident_user where incident_id = "+incidentId+" ")
		      .append("   ) ");
		}
		*/
		return sql.toString();
	}
}
