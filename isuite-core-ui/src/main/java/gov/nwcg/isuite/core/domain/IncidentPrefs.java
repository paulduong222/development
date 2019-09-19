package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;

public interface IncidentPrefs extends Persistable {

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
    * Accessor for sectionName
    * @return the name of the preferences section, will not be null
    * @see #setSectionName(IncidentPrefsSectionNameEnum)
    */
   public abstract IncidentPrefsSectionNameEnum getSectionName();
   
   /**
    * Mutator for sectionName
    * @param sectionName name of the preferences section, cannot be null
    * @see #getSectionName()
    */
   public abstract void setSectionName(IncidentPrefsSectionNameEnum sectionName);

   /**
    * Accessor for fieldLabel
    * @return the fieldLabel for this item in the incident preferences section, will not be null
    * @see #setFieldLabel(String)
    */
   public abstract String getFieldLabel();
   
   /**
    * Mutator for fieldLabel
    * @param fieldLabel the fieldLabel for this item in the incident preferences section, cannot be null
    * @see #getFieldLabel()
    */
   public abstract void setFieldLabel(String fieldLabel);

   /**
    * Accessor for position
    * @return the position (order) of the fieldLabel to be displayed in the section with these preferences, will not be null
    * @see #setPosition(Integer)
    */
   public abstract Integer getPosition();
   
   /**
    * Mutator for position
    * @param position the position (order) of the fieldLabel to be displayed in the section with these preferences, cannot be null
    * @see #getPosition()
    */
   public abstract void setPosition(Integer position);

   /**
    * Accessor for selected flag.
    * @return the selected flag, will not be null
    * @see #setSelected(Boolean)
    */
   public abstract Boolean isSelected();

   /**
    * Mutator for selected.
    * @param selected the flag to set if the field is checked or not, cannot be null
    * @see #isStandard()
    */
   public abstract void setSelected(Boolean selected);

}
