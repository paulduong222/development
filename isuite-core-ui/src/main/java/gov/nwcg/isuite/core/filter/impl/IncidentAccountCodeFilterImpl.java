package gov.nwcg.isuite.core.filter.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class IncidentAccountCodeFilterImpl extends FilterImpl implements IncidentAccountCodeFilter {
   
   private static final long serialVersionUID = -8758301172732883779L;
   private String accountCode;
   private String incidentName;
   private String agencyCode;
   private String regionUnitCode;
   private Boolean defaultFlag;
   private Long agencyId;
   private Boolean deletable=false;
   private String deletableString;
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getAccountCode()
    */
   public String getAccountCode() {
      return accountCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setAccountCode(java.lang.String)
    */
   public void setAccountCode(String accountCode) {
      this.accountCode = accountCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getAgencyCode()
    */
   public String getAgencyCode() {
      return agencyCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setAgencyCode(java.lang.String)
    */
   public void setAgencyCode(String agencyCode) {
      this.agencyCode = agencyCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setDefaultFlag(java.lang.Boolean)
    */
   public void setDefaultFlag(Boolean defaultFlag) {
      this.defaultFlag = defaultFlag;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getRegionUnitCode()
    */
   public String getRegionUnitCode() {
      return regionUnitCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setRegionUnitCode(java.lang.String)
    */
   public void setRegionUnitCode(String regionUnitCode) {
      this.regionUnitCode = regionUnitCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getIncidentName()
    */
   public String getIncidentName() {
      return incidentName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setIncidentName(java.lang.String)
    */
   public void setIncidentName(String incidentName) {
      this.incidentName = incidentName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getDefaultFlag()
    */
   public Boolean getDefaultFlag() {
      return this.defaultFlag;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getAgencyId()
    */
   public Long getAgencyId() {
	   return agencyId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setAgencyId(java.lang.Long)
    */
   public void setAgencyId(Long agencyId) {
	   this.agencyId = agencyId;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.FilterImpl#reset()
    */
   @JsonIgnore
   public void reset() {
      this.accountCode = null;
      this.incidentName = null;
      this.agencyCode = null;
      this.regionUnitCode = null;
      this.defaultFlag = null;
      this.agencyId = null;
   }

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() {
		return deletable;
	}
	
	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

   /**
    * @return the deletableString
    */
   public String getDeletableString() {
      return deletableString;
   }

   /**
    * @param deletableString the deletableString to set
    */
   public void setDeletableString(String deletableString) {
      this.deletableString = deletableString;
      this.setDeletable(super.determineDeletableValue(this.deletableString));
   }
}
