package gov.nwcg.isuite.core.persistence.hibernate.query;

public class IncidentQuestionQuery {
	
   public static final String GET_MAX_INCIDENT_QUESTION_POSITION="GET_MAX_INCIDENT_QUESTION_POSITION";
   public static final String GET_MAX_INCIDENT_QUESTION_POSITION_QUERY =
      "SELECT MAX(model.position) FROM IncidentQuestionImpl model " +
      "WHERE model.incidentId = :id ";

   public static final String GET_MAX_INCIDENT_GRP_QUESTION_POSITION="GET_MAX_INCIDENT_GRP_QUESTION_POSITION";
   public static final String GET_MAX_INCIDENT_GRP_QUESTION_POSITION_QUERY =
      "SELECT MAX(model.position) FROM IncidentGroupQuestionImpl model " +
      "WHERE model.incidentGroupId = :id ";

}
