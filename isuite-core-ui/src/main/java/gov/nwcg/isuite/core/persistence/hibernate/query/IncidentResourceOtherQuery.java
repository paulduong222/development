package gov.nwcg.isuite.core.persistence.hibernate.query;


public class IncidentResourceOtherQuery {

	public static String getTopLevelResources(Long incidentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID FROM ISW_INCIDENT_RESOURCE_OTHER ")
			.append("WHERE INCIDENT_ID = " + incidentId + " ")
			.append("AND RESOURCE_OTHER_ID IN (")
			.append("   SELECT ID FROM ISW_RESOURCE_OTHER ")
			.append(")" );
		
		return sql.toString();
	}
	
	public static String getTopLevelResourcesIG(Long incidentGroupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID FROM ISW_INCIDENT_RESOURCE_OTHER ")
			.append("WHERE INCIDENT_ID IN ( SELECT INCIDENT_ID FROM ISW_INCIDENT_GROUP_INCIDENT WHERE INCIDENT_GROUP_ID = " + incidentGroupId + ") ")
			.append("AND RESOURCE_OTHER_ID IN (")
			.append("   SELECT ID FROM ISW_RESOURCE_OTHER ")
			.append(")" );
		
		return sql.toString();
	}
	
}
