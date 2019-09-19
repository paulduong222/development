package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentGroupQSKind extends Persistable {

   /**
    * Accessor for incidentGroupId
    * @return the id of the incident group associated with these preferences, will not be null
    * @see #setIncidentGroupId(Long)
    */
   public abstract Long getIncidentGroupId();
   
   /**
    * Mutator for incidentGroupId
    * @param incidentGroupId the id of the incident group associated with these preferences, cannot be null
    * @see #getIncidentGroupId()
    */
   public abstract void setIncidentGroupId(Long incidentGroupId);

   /**
    * Accessor for incident group
    * @return the incident group associated with these preferences
    * @see #setIncidentGroup(IncidentGroup)
    */
   public abstract IncidentGroup getIncidentGroup();
   
   /**
    * Mutator for incident group
    * @param incidentGroup the incident group associated with these preferences
    * @see #getIncidentGroup()
    */
   public abstract void setIncidentGroup(IncidentGroup incidentGroup);

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
