package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

/**
 * Grid VO that will be returned to populate the Manage Account Code screen.
 * 
 * @author bsteiner
 */
public class IncidentAccountCodeGridVo extends AbstractVo {
   private String accountFireCode;
   private String incidentName;
   private String agencyCode;
   private String unitCode;
   private Boolean defaultValue;

   public IncidentAccountCodeGridVo() {}
   
   /**
    * @return the accountFireCode
    */
   public String getAccountFireCode() {
      return accountFireCode;
   }

   /**
    * @param accountFireCode the accountFireCode to set
    */
   public void setAccountFireCode(String accountFireCode) {
      this.accountFireCode = accountFireCode.trim();
   }

   /**
    * @return the agencyCode
    */
   public String getAgencyCode() {
      return agencyCode;
   }

   /**
    * @param agencyCode the agencyCode to set
    */
   public void setAgencyCode(String agencyCode) {
      this.agencyCode = agencyCode;
   }

   /**
    * @return the defaultValue
    */
   public Boolean getDefaultValue() {
      return defaultValue;
   }

   /**
    * @param defaultValue the defaultValue to set
    */
   public void setDefaultValue(Boolean defaultValue) {
      this.defaultValue = defaultValue;
   }

   /**
    * @return the regionUnit
    */
   public String getUnitCode() {
      return unitCode;
   }

   /**
    * @param unitCode the regionUnit to set
    */
   public void setUnitCode(String unitCode) {
      this.unitCode = unitCode;
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
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    @Override
    public String toString()
    {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<IncidentAccountCodeGridVo>").append(nl)
            .append(tab).append("<accountFireCode>").append(this.accountFireCode).append("</accountFireCode>").append(nl)
            .append(tab).append("<incidentName>").append(this.incidentName).append("</incidentName>").append(nl)
            .append(tab).append("<agencyCode>").append(this.agencyCode).append("</agencyCode>").append(nl)
            .append(tab).append("<fsRegionUnit>").append(this.unitCode).append("</fsRegionUnit>").append(nl)
            .append(tab).append("<defaultValue>").append(this.defaultValue).append("</defaultValue>").append(nl)
            .append(tab).append(super.toString()).append(nl)
            .append("</IncidentAccountCodeGridVo>");
            
        return retValue.toString();
    }
}
