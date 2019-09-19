package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.WorkArea;

import java.util.Date;


/**
 * This represents the view for incidents tied to a Work Area represented by the 
 * ISWV_WORK_AREA_INCIDENTS view.
 * 
 * @author bsteiner
 */
public interface WorkAreaIncidentsView {

   public Long getWorkAreaId();
   public WorkArea getWorkArea();
   public Long getIncidentId();
   public Incident getIncident();
   public String getIncidentName();
   public String getEventType();
   public String getStateCode();
   public String getUnitCode();
   public String getIncidentNbr();
   public String getIncidentJurisdiction();
   public Date getStartDate();
   public Boolean isRestrictedFlg();
   
   public void setIncidentName(String nm);
   public void setEventType(String etNm);
   public void setStateCode(String abb);
   public void setUnitCode(String huc);
   public void setIncidentNbr(String nbr);
   public void setIncidentJurisdiction(String ij);
   public void setStartDate(Date date);
   public void setRestrictedFlg(Boolean isRestricted);
   
   
   /**
    * @param Incident the Incident to set
    */
   public void setIncident(Incident incident);

   /**
    * @param incidentId the incidentId to set
    */
   public void setIncidentId(Long Incidentd);

   /**
    * @param workArea the workArea to set
    */
   public void setWorkArea(WorkArea workArea);

   /**
    * @param workAreaId the workAreaId to set
    */
   public void setWorkAreaId(Long workAreaId);

   public String getDefaultAccountingCode() ;

   public void setDefaultAccountingCode(String defaultAccountingCode) ;
   
	public String getIncidentTagNumber();

	public void setIncidentTagNumber(String incidentTagNumber);
}
