package gov.nwcg.isuite.core.persistence.hibernate.query;


public class IncidentAccountCodeQuery {

	public static String getRemoveDefaultQuery(Long incidentId, Long incidentAccountCodeId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
	
		sql.append("UPDATE isw_incident_account_code ")
		.append("set default_flg = " + (isOracle ? 0 : false) + " ") 
		.append("where incident_id = " + incidentId + " ")
		.append("and id != " + incidentAccountCodeId);
		
		return sql.toString();
	}

	/**
	 * @param id
	 * @param incidentId
	 * @return
	 */
	public static String getTimePostingCount(Long id, Long incidentId){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(ATP.ID) ")
		    .append("FROM ISW_ASSIGN_TIME_POST ATP ")
			.append("     ,ISW_ASSIGNMENT_TIME AT ")
			.append(",ISW_ASSIGNMENT A ")
			.append(",ISW_WORK_PERIOD_ASSIGNMENT WPA ")
			.append(",ISW_WORK_PERIOD WP ")
		    .append(",ISW_INCIDENT_RESOURCE IR ")
		    .append("WHERE ATP.INCIDENT_ACCOUNT_CODE_ID = " + id + " ")
		    .append("AND ATP.ASSIGNMENT_TIME_ID = AT.ID ")
		    .append("AND AT.ASSIGNMENT_ID = A.ID ")
		    .append("AND A.ID = WPA.ASSIGNMENT_ID ")
		    .append("AND WPA.WORK_PERIOD_ID = WP.ID ")
		    .append("AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
		    .append("AND IR.INCIDENT_ID = " + incidentId + " ");
		
		return sql.toString();
	}
	
	public static String getTimeAdjustmentCount(Long id, Long incidentId){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(TAA.ID) ")
		    .append("FROM ISW_TIME_ASSIGN_ADJUST TAA ")
			.append(",ISW_ASSIGNMENT A ")
			.append(",ISW_WORK_PERIOD_ASSIGNMENT WPA ")
			.append(",ISW_WORK_PERIOD WP ")
		    .append(",ISW_INCIDENT_RESOURCE IR ")
		    .append("WHERE TAA.INCIDENT_ACCOUNT_CODE_ID = " + id + " ")
		    .append("AND TAA.ASSIGNMENT_ID = A.ID ")
		    .append("AND A.ID = WPA.ASSIGNMENT_ID ")
		    .append("AND WPA.WORK_PERIOD_ID = WP.ID ")
		    .append("AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
		    .append("AND IR.INCIDENT_ID = " + incidentId + " ");
		
		return sql.toString();
	}

	/**
	 * @param id
	 * @param incidentId
	 * @return
	 */
	public static String getResourceCount(Long id, Long incidentId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(WP.ID) ")
			.append("FROM ISW_WORK_PERIOD WP ")
		    .append(",ISW_INCIDENT_RESOURCE IR ")
		    .append("WHERE WP.DEF_INCIDENT_ACCOUNT_CODE_ID = " + id + " ")
		    .append("AND WP.INCIDENT_RESOURCE_ID = IR.ID ")
		    .append("AND IR.INCIDENT_ID = " + incidentId + " ")
		;
		
		return sql.toString();
	}

	public static String getUpdateNonDefaultsQuery(Long incidentId, Long excludeIacId,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE ISW_INCIDENT_ACCOUNT_CODE ")
			.append("SET DEFAULT_FLG = " + (isOracle ? 0 : false) + " ")
			.append("WHERE incident_id = " + incidentId + " " )
			.append("AND id != " + excludeIacId + " ")
			.append("AND default_flg = " + (isOracle ? 1 : true) + " ");
		
		return sql.toString();
	}

}
