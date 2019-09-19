package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.WorkArea;

import java.util.Date;


/**
 * This represents the view for incidents <b>ELIGIBLE TO BE</b> tied to a Work
 * Area. Represented by the ISWV_AVAILABLE_WA_INCIDENTS view.
 * 
 * @author bsteiner
 */
public interface AvailableWorkAreaIncidentsView {

   /**
    * Gets the work area id.
    * 
    * @return the work area id
    */
   public Long getWorkAreaId();
   
   /**
    * Gets the work area.
    * 
    * @return the work area
    */
   public WorkArea getWorkArea();
   
   /**
    * Gets the incident id.
    * 
    * @return the incident id
    */
   public Long getIncidentId();
   
   /**
    * Gets the incident.
    * 
    * @return the incident
    */
   public Incident getIncident();
   
   /**
    * Gets the incident name.
    * 
    * @return the incident name
    */
   public String getIncidentName();
   
   /**
    * Gets the event type name.
    * 
    * @return the event type name
    */
   public String getEventType();
   
   /**
    * Gets the state code.
    * 
    * @return the state code
    */
   public String getStateCode();
   
   /**
    * Gets the unit code.
    * 
    * @return the unit code
    */
   public String getUnitCode();
   
   /**
    * Gets the incident number.
    * 
    * @return the incident number
    */
   public String getIncidentNbr();
   
   /**
    * Gets the jurisdiction.
    * 
    * @return the jurisdiction
    */
   public String getIncidentJurisdiction();
   
   /**
    * Gets the start date.
    * 
    * @return the start date
    */
   public Date getStartDate();
   
   /**
    * Checks if is restricted flg.
    * 
    * @return the boolean
    */
   public Boolean isRestrictedFlg();
   
   /**
    * Sets the work area id.
    * 
    * @param id
    *           the new work area id
    */
   public void setWorkAreaId(Long id);
   
   /**
    * Sets the work area.
    * 
    * @param workArea
    *           the new work area
    */
   public void setWorkArea(WorkArea workArea);
   
   /**
    * Sets the incident id.
    * 
    * @param id
    *           the new incident id
    */
   public void setIncidentId(Long id);
   
   /**
    * Sets the incident.
    * 
    * @param incident
    *           the new incident
    */
   public void setIncident(Incident incident);
   
   /**
    * Sets the incident name.
    * 
    * @param incidentName
    *           the new incident name
    */
   public void setIncidentName(String incidentName);
   
   /**
    * Sets the event type name.
    * 
    * @param eventTypeName
    *           the new event type name
    */
   public void setEventType(String eventTypeName);
   
   /**
    * Sets the state code.
    * 
    * @param stateCode
    *           the new state code
    */
   public void setStateCode(String stateCode);
   
   /**
    * Sets the unit code.
    * 
    * @param unitCode
    *           the new unit code
    */
   public void setUnitCode(String unitCode);
   
   /**
    * Sets the incident number.
    * 
    * @param incidentNbr
    *           the new incident number
    */
   public void setIncidentNbr(String incidentNbr);
   
   /**
    * Sets the jurisdiction.
    * 
    * @param jurisdiction
    *           the new jurisdiction
    */
   public void setIncidentJurisdiction(String incidentJurisdiction);
   
   /**
    * Sets the start date.
    * 
    * @param startDate
    *           the new start date
    */
   public void setStartDate(Date startDate);
   
   /**
    * Sets the restricted flg.
    * 
    * @param isRestrictedFlg
    *           the new restricted flg
    */
   public void setRestrictedFlg(Boolean isRestrictedFlg);
}
