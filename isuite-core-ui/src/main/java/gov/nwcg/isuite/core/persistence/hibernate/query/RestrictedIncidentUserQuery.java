package gov.nwcg.isuite.core.persistence.hibernate.query;

public class RestrictedIncidentUserQuery {

	public static String getRestrictedIncidentUserId(Long userId, Long incidentId) {
		   StringBuffer sql = new StringBuffer()
		   .append("select riu.id from ")
		   .append("isw_user u, isw_restricted_incident_user riu ")
		   .append("where u.id = riu.user_id ")
		   .append("and u.id = " + userId + " ")
		   .append("and riu.incident_id = " + incidentId + " ");
		   
		   return sql.toString();
		}
	
}
