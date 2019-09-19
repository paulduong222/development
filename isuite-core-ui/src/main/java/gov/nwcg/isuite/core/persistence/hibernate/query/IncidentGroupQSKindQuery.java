package gov.nwcg.isuite.core.persistence.hibernate.query;

public class IncidentGroupQSKindQuery {

   /*
    *  Removes All IncidentQSKind records associated with a specified incidentGroupId
    */
   public static final String DELETE_ALL_WITH_INCIDENT_GROUP_ID="DELETE_ALL_WITH_INCIDENT_GROUP_ID";
   public static final String DELETE_ALL_WITH_INCIDENT_GROUP_ID_QUERY =
      "DELETE from IncidentGroupQSKindImpl ik " +
      "WHERE ik.incidentGroupId = :incidentGroupId";

}
