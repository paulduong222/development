package gov.nwcg.isuite.core.persistence.hibernate.query;

public class WorkPeriodQuestValueQuery {

	public static String getMissingDefaultCountQuery(Long incidentId,Boolean isOracle){
		StringBuffer sql = new StringBuffer()
		.append("select count(iq.id) ")
		.append("from isw_incident_question iq ")
		.append("	, isw_work_period wp ")
		.append("where iq.incident_id = " + incidentId + " ")
		.append("and wp.incident_resource_id in (select id from isw_incident_resource where incident_id = "+incidentId+" ) ")
		.append("and iq.id not in ( ")
		.append("  select incident_question_id ")
		.append("  from isw_work_period_question_value ")
		.append("  where work_period_id = wp.id ")
		.append(") ");
		
		return sql.toString();
	}
	
	public static String getCreateDefaultValuesQuery(Long incidentId,Boolean isOracle){
		StringBuffer sql = new StringBuffer()
		.append("insert into isw_work_period_question_value (id, work_period_id, incident_question_id, question_value) ")
		.append("select "+(isOracle==true ? "SEQ_WORK_PERIOD_QUESTION_VALUE.nextVal" : "nextVal('SEQ_WORK_PERIOD_QUESTION_VALUE')")+" ")
		.append(", wp.id ")
		.append(", iq.id ")
		.append(", "+(isOracle==true ? 0 : false)+" ")
		.append("from isw_incident_question iq ")
		.append("	, isw_work_period wp ")
		.append("where iq.incident_id = " + incidentId + " ")
		.append("and wp.incident_resource_id in (select id from isw_incident_resource where incident_id = "+incidentId+" ) ")
		.append("and iq.id not in ( ")
		.append("  select incident_question_id ")
		.append("  from isw_work_period_question_value ")
		.append("  where work_period_id = wp.id ")
		.append(") ");
		
		return sql.toString();
	}
}
