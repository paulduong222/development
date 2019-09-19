package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.views.WorkAreaIncidentsView;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Data Object representative of Resources associated to a Work Area.  These
 * are both those associated directly and those associated via incident.
 * 
 * @author bsteiner
 */
@Entity
@Table(name="iswv_work_area_incidents")
public class WorkAreaIncidentsViewImpl implements WorkAreaIncidentsView {
   
   @Column(name="WORK_AREA_ID", insertable=false, updatable=false)
   private Long workAreaId;
   
   @OneToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID", insertable=false, updatable=false)
   private WorkArea workArea;
   
   @Id
   @Column(name="INCIDENT_ID", insertable=false, updatable=false)
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
   
   @Column(name="DEFAULT_ACCOUNT_CODE", insertable=false, updatable=false)
   private String defaultAccountingCode;
   
   @Column(name="INCIDENT_TAG_NUMBER")
   private String incidentTagNumber;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getWorkArea()
    */
   public WorkArea getWorkArea() {
      return this.workArea;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaResourcesView#getWorkAreaId()
    */
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#setWorkArea(gov.nwcg.isuite.domain.access.WorkArea)
    */
   public void setWorkArea(WorkArea workArea) {
      this.workArea = workArea;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#setWorkAreaId(java.lang.Long)
    */
   public void setWorkAreaId(Long workAreaId) {
      this.workAreaId = workAreaId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#getIncident()
    */
   public Incident getIncident() {
      return incident;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#setIncident(gov.nwcg.isuite.domain.incident.Incident)
    */
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#getIncidentId()
    */
   public Long getIncidentId() {
      return incidentId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaIncidentsView#setIncidentId(java.lang.Long)
    */
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /**
    * @return the eventType
    */
   public String getEventType() {
      return eventType;
   }

   /**
    * @param eventTypeNm the eventTypeNm to set
    */
   public void setEventType(String eventTypeNm) {
      this.eventType = eventTypeNm;
   }

   /**
    * @return the homeUnitCode
    */
   public String getUnitCode() {
      return unitCode;
   }

   /**
    * @param homeUnitCode the homeUnitCode to set
    */
   public void setUnitCode(String homeUnitCode) {
      this.unitCode = homeUnitCode;
   }

   /**
    * @return the incidentJurisdiction
    */
   public String getIncidentJurisdiction() {
      return incidentJurisdiction;
   }

   /**
    * @param incidentJurisdiction the incidentJurisdiction to set
    */
   public void setIncidentJurisdiction(String incidentJurisdiction) {
      this.incidentJurisdiction = incidentJurisdiction;
   }

   /**
    * @return the incidentName
    */
   public String getIncidentName() {
      return incidentName;
   }

   /**
    * @param incidentName the incidentName to set
    */
   public void setIncidentName(String incidentName) {
      this.incidentName = incidentName;
   }

   /**
    * @return the incidentNbr
    */
   public String getIncidentNbr() {
      return incidentNbr;
   }

   /**
    * @param incidentNbr the incidentNbr to set
    */
   public void setIncidentNbr(String incidentNbr) {
      this.incidentNbr = incidentNbr;
   }

   /**
    * @return the restrictedFlg
    */
   public Boolean isRestrictedFlg() {
      return restrictedFlg;
   }

   /**
    * @param restrictedFlg the restrictedFlg to set
    */
   public void setRestrictedFlg(Boolean restrictedFlg) {
      this.restrictedFlg = restrictedFlg;
   }

   /**
    * @return the startDate
    */
   public Date getStartDate() {
      return startDate;
   }

   /**
    * @param startDate the startDate to set
    */
   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   /**
    * @return the stateAbbreviation
    */
   public String getStateCode() {
      return stateCode;
   }

   /**
    * @param stateCode the stateCode to set
    */
   public void setStateCode(String stateAbbreviation) {
      this.stateCode = stateAbbreviation;
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
        
        retValue.append("<WorkAreaIncidentsViewImpl>").append(nl)
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
           .append("</WorkAreaIncidentsViewImpl>");
            
        return retValue.toString();
    }

    public String getDefaultAccountingCode() {
    	return defaultAccountingCode;
    }

    public void setDefaultAccountingCode(String defaultAccountingCode) {
    	this.defaultAccountingCode = defaultAccountingCode;
    }

	public String getIncidentTagNumber() {
		return incidentTagNumber;
	}

	public void setIncidentTagNumber(String incidentTagNumber) {
		this.incidentTagNumber = incidentTagNumber;
	}

}
