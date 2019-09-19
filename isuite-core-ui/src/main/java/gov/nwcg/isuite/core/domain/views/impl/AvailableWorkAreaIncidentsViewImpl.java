package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.views.AvailableWorkAreaIncidentsView;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents Incidents available to be associated to a Work Area.  This is used 
 * as a representation of a view.
 * 
 * @author bsteiner
 */
@Entity
@Table(name="iswv_available_wa_incidents")
public class AvailableWorkAreaIncidentsViewImpl implements AvailableWorkAreaIncidentsView {
   
//   @Id
   @Column(name="WORK_AREA_ID", insertable=false, updatable=false)
   private Long workAreaId;
   
   @OneToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID", insertable=false, updatable=false)
   private WorkArea workArea;
   
   @Id
   @Column(name="INCIDENT_ID", insertable=false, updatable=false, length=19)
   private Long incidentId;
   
   @OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="INCIDENT_ID", insertable=false, updatable=false)
   private Incident incident;
   
   @Column(name="INCIDENT_NAME", insertable=false, updatable=false)
   private String incidentName;
   
   @Column(name="EVENT_TYPE", insertable=false, updatable=false)
   private String eventType;
   
   @Column(name="STATE_CODE", insertable=false, updatable=false)
   private String stateCode;
   
   @Column(name="UNIT_CODE", insertable=false, updatable=false)
   private String unitCode;
   
   @Column(name="INCIDENT_NUMBER", insertable=false, updatable=false)
   private String incidentNbr;
   
   @Column(name="AGENCY_JURISDICTION", insertable=false, updatable=false)
   private String incidentJurisdiction;
   
   @Column(name="START_DATE", insertable=false, updatable=false)
   private Date startDate;
   
   @Column(name="IS_RESTRICTED", insertable=false, updatable=false)
   private Boolean restrictedFlg;
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getIncident()
    */
   public Incident getIncident() {
      return this.incident;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getIncidentId()
    */
   public Long getIncidentId() {
      return this.incidentId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getWorkArea()
    */
   public WorkArea getWorkArea() {
      return this.workArea;
   }

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getWorkAreaId()
    */
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getEventType()
    */
   public String getEventType() {
      return eventType;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setEventTypeName(java.lang.String)
    */
   public void setEventType(String eventType) {
      this.eventType = eventType;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getIncidentName()
    */
   public String getIncidentName() {
      return incidentName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setIncidentName(java.lang.String)
    */
   public void setIncidentName(String incidentName) {
      this.incidentName = incidentName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getIncidentNumber()
    */
   public String getIncidentNbr() {
      return incidentNbr;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setIncidentNbr(java.lang.String)
    */
   public void setIncidentNbr(String incidentNbr) {
      this.incidentNbr = incidentNbr;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getIncidentJurisdiction()
    */
   public String getIncidentJurisdiction() {
      return incidentJurisdiction;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setJurisdiction(java.lang.String)
    */
   public void setIncidentJurisdiction(String jurisdiction) {
      this.incidentJurisdiction = jurisdiction;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#isRestrictedFlg()
    */
   public Boolean isRestrictedFlg() {
      return restrictedFlg;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setRestrictedFlg(java.lang.Boolean)
    */
   public void setRestrictedFlg(Boolean restricted) {
      this.restrictedFlg = restricted;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getStartDate()
    */
   public Date getStartDate() {
      return startDate;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setStartDate(java.util.Date)
    */
   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getStateCode()
    */
   public String getStateCode() {
      return stateCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setStateCode(java.lang.String)
    */
   public void setStateCode(String stateCode) {
      this.stateCode = stateCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#getUnitCode()
    */
   public String getUnitCode() {
      return unitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setUnitCode(java.lang.String)
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setIncident(gov.nwcg.isuite.domain.incident.Incident)
    */
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setIncidentId(java.lang.Long)
    */
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setWorkArea(gov.nwcg.isuite.domain.access.WorkArea)
    */
   public void setWorkArea(WorkArea workArea) {
      this.workArea = workArea;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.AvailableWorkAreaIncidentsView#setWorkAreaId(java.lang.Long)
    */
   public void setWorkAreaId(Long workAreaId) {
      this.workAreaId = workAreaId;
   }
   
   /**
     * Constructs a <code>String</code> with all attributes in xml format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<AvailableWorkAreaIncidentsViewImpl>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<workAreaId>").append(this.workAreaId).append("</workAreaId>").append(nl)   
           .append(tab).append("<workArea>").append(this.workArea).append("</workArea>").append(nl)   
           .append(tab).append("<incidentId>").append(this.incidentId).append("</incidentId>").append(nl)   
           .append(tab).append("<incident>").append(this.incident).append("</incident>").append(nl)   
           .append(tab).append("<incidentName>").append(this.incidentName).append("</incidentName>").append(nl)   
           .append(tab).append("<eventType>").append(this.eventType).append("</eventType>").append(nl)   
           .append(tab).append("<stateCode>").append(this.stateCode).append("</stateCode>").append(nl)   
           .append(tab).append("<unitCode>").append(this.unitCode).append("</unitCode>").append(nl)   
           .append(tab).append("<incidentNbr>").append(this.incidentNbr).append("</incidentNbr>").append(nl)   
           .append(tab).append("<incidentJurisdiction>").append(this.incidentJurisdiction).append("</incidentJurisdiction>").append(nl)   
           .append(tab).append("<startDate>").append(this.startDate).append("</startDate>").append(nl)   
           .append(tab).append("<restrictedFlg>").append(this.restrictedFlg).append("</restrictedFlg>").append(nl)   
           .append("</AvailableWorkAreaIncidentsViewImpl>");
            
        return retValue.toString();
    }

}
