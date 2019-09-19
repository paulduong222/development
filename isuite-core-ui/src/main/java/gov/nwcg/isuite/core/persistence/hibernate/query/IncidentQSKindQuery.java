/**
 * 
 */
package gov.nwcg.isuite.core.persistence.hibernate.query;

/**
 * @author mpoll
 *
 */
public class IncidentQSKindQuery {
   /*
    *  Removes All IncidentQSKind records associated with a specified incidentId
    */
   public static final String DELETE_ALL_WITH_INCIDENT_ID="DELETE_ALL_WITH_INCIDENT_ID";
   public static final String DELETE_ALL_WITH_INCIDENT_ID_QUERY =
      "DELETE from IncidentQSKindImpl ik " +
      "WHERE ik.incidentId = :incidentId";

}
