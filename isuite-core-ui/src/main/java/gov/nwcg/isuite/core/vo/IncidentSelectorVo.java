package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.Date;

/** This class will serve as the data provider for 
 * the WorkAreaIncidentSelector.cbxIncident component. */
public class IncidentSelectorVo extends AbstractVo {
   
   private Long incidentId;
   private String incidentNumber;
   private String incidentName;

   private Long incidentGroupId;
   private String incidentGroupName;
   private Boolean isIncidentGroup;
   private Boolean isRestrictedIncident;
   
   private String incidentJurisdictionCode;
   private String defaultAccountingCode;
   private Date incidentStartDate;
   private String eventTypeName;
   
   private String countryCode="US";
   private String countrySubdivisionCode;
   private String unitCode;
   
   public IncidentSelectorVo() {
      
   }

   /**
    * @return the incidentId
    */
   public Long getIncidentId() {
      return incidentId;
   }

   /**
    * @param incidentId the incidentId to set
    */
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /**
    * @return the incidentNumber
    */
   public String getIncidentNumber() {
      return incidentNumber;
   }

   /**
    * @param incidentNumber the incidentNumber to set
    */
   public void setIncidentNumber(String incidentNumber) {
      this.incidentNumber = incidentNumber;
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
    * @return the incidentGroupId
    */
   public Long getIncidentGroupId() {
      return incidentGroupId;
   }

   /**
    * @param incidentGroupId the incidentGroupId to set
    */
   public void setIncidentGroupId(Long incidentGroupId) {
      this.incidentGroupId = incidentGroupId;
   }

   /**
    * @return the incidentGroupName
    */
   public String getIncidentGroupName() {
      return incidentGroupName;
   }

   /**
    * @param incidentGroupName the incidentGroupName to set
    */
   public void setIncidentGroupName(String incidentGroupName) {
      this.incidentGroupName = incidentGroupName;
   }

   /**
    * @return the isIncidentGroup
    */
   public Boolean getIsIncidentGroup() {
      return isIncidentGroup;
   }

   /**
    * @param isIncidentGroup the isIncidentGroup to set
    */
   public void setIsIncidentGroup(Boolean isIncidentGroup) {
      this.isIncidentGroup = isIncidentGroup;
   }

//   /**
//    * @return the incidentJurisdictionCode
//    */
//   public String getIncidentJurisdictionCode() {
//      return incidentJurisdictionCode;
//   }
//
//   /**
//    * @param incidentJurisdictionCode the incidentJurisdictionCode to set
//    */
//   public void setIncidentJurisdictionCode(String incidentJurisdictionCode) {
//      this.incidentJurisdictionCode = incidentJurisdictionCode;
//   }

   /**
    * @return the countrySubdivisionCode
    */
   public String getCountrySubdivisionCode() {
      return countrySubdivisionCode;
   }

   /**
    * @param countrySubdivisionCode the countrySubdivisionCode to set
    */
   public void setCountrySubdivisionCode(String countrySubdivisionCode) {
      this.countrySubdivisionCode = countrySubdivisionCode;
   }

   /**
    * @return the unitCode
    */
   public String getUnitCode() {
      return unitCode;
   }

   /**
    * @param unitCode the unitCode to set
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
   }

   /**
    * @return the countryCode
    */
   public String getCountryCode() {
      return countryCode;
   }

   /**
    * @param countryCode the countryCode to set
    */
   public void setCountryCode(String countryCode) {
      this.countryCode = countryCode;
   }

   /**
    * @return the isRestrictedIncident
    */
   public Boolean getIsRestrictedIncident() {
      return isRestrictedIncident;
   }

   /**
    * @param isRestrictedIncident the isRestrictedIncident to set
    */
   public void setIsRestrictedIncident(Boolean isRestrictedIncident) {
      this.isRestrictedIncident = isRestrictedIncident;
   }

public String getIncidentJurisdictionCode() {
	return incidentJurisdictionCode;
}

public void setIncidentJurisdictionCode(String incidentJurisdictionCode) {
	this.incidentJurisdictionCode = incidentJurisdictionCode;
}

public String getDefaultAccountingCode() {
	return defaultAccountingCode;
}

public void setDefaultAccountingCode(String defaultAccountingCode) {
	this.defaultAccountingCode = defaultAccountingCode;
}

public Date getIncidentStartDate() {
	return incidentStartDate;
}

public void setIncidentStartDate(Date incidentStartDate) {
	this.incidentStartDate = incidentStartDate;
}

public String getEventTypeName() {
	return eventTypeName;
}

public void setEventTypeName(String eventTypeName) {
	this.eventTypeName = eventTypeName;
}
}
