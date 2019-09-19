package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class AccountCodeFilterImpl extends FilterImpl implements IncidentAccountCodeFilter {

   private static final long serialVersionUID = 3109805558842919394L;
   private String accountCode;
   private Long agencyId;
   private String agencyCode;
   private String regionUnitCode;
   private Long regionUnitId;
   private Boolean deletable=false;
   
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
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#getFsRegionUnitCode()
    */
   public String getRegionUnitCode() {
      return regionUnitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCodeFilter#setFsRegionUnitCode(java.lang.String)
    */
   public void setRegionUnitCode(String regionUnitCode) {
      this.regionUnitCode = regionUnitCode;
   }

   /**
    * Get the Region/Unit ID
    * @return The Region/Unit ID
    */
   public Long getRegionUnitId() {
      return regionUnitId;
   }

   /**
    * Set the Region/Unit ID
    * @param regionUnitId
    */
   public void setRegionUnitId(Long regionUnitId) {
      this.regionUnitId = regionUnitId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.FilterImpl#reset()
    */
   public void reset() {
	   accountCode = null;
	   agencyId = null;
	   agencyCode = null;
	   regionUnitCode = null;
	   regionUnitId = null;
   }

   public Boolean getDefaultFlag() {
	   // TODO Auto-generated method stub
	   return null;
   }

   public String getIncidentName() {
	   // TODO Auto-generated method stub
	   return null;
   }

   public void setDefaultFlag(Boolean defaultFlag) {
	   // TODO Auto-generated method stub

   }

   public void setIncidentName(String incidentName) {
	   // TODO Auto-generated method stub

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
}
