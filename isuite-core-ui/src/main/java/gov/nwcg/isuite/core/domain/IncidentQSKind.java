package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentQSKind extends Persistable {

   /**
    * Accessor for incidentId
    * @return the id of the incident associated with these preferences, will not be null
    * @see #setIncidentId(Long)
    */
   public abstract Long getIncidentId();
   
   /**
    * Mutator for incidentId
    * @param incidentId the id of the incident associated with these preferences, cannot be null
    * @see #getIncidentId()
    */
   public abstract void setIncidentId(Long incidentId);

   /**
    * Accessor for incident
    * @return the incident associated with these preferences
    * @see #setIncident(Incident)
    */
   public abstract Incident getIncident();
   
   /**
    * Mutator for incident
    * @param incident the incident associated with these preferences
    * @see #getIncident()
    */
   public abstract void setIncident(Incident incident);

   /**
    * Accessor for kindId
    * @return the id of the kind code associated with these preferences, will not be null
    * @see #setKindId(Long)
    */
   public abstract Long getKindId();
   
   /**
    * Mutator for kindId
    * @param kindId the id of the kind code associated with these preferences, cannot be null
    * @see #getKindId()
    */
   public abstract void setKindId(Long kindId);

   /**
    * Accessor for kindCode
    * @return the kind code associated with these preferences
    * @see #setKind(Kind)
    */
   public abstract Kind getKind();
   
   /**
    * Mutator for kind code
    * @param kind the kind code associated with these preferences
    * @see #getKind()
    */
   public abstract void setKind(Kind kind);

}
