/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;

/**
 * @author mgreen
 *
 */
public class AgencyFilterImpl extends FilterImpl implements AgencyFilter {

   private static final long serialVersionUID = 4433328339581949565L;
   private String agencyCode;
   private String agencyName;
   private AgencyTypeEnum agencyType;
   private Long eventTypeId;
   
   /**
    * 
    */
   public AgencyFilterImpl() {}
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.AgencyFilter#getAgencyCode()
    */
   public String getAgencyCode() {
      return this.agencyCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.AgencyFilter#getAgencyName()
    */
   public String getAgencyName() {
      return this.agencyName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.AgencyFilter#getAgencyType()
    */
   public AgencyTypeEnum getAgencyType() {
      return this.agencyType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Filter#reset()
    */
   public void reset() {
      this.agencyCode = null;
      this.agencyName = null;
      this.agencyType = null;
      this.eventTypeId = null;
   }

   /**
    * @param agencyCode the agencyCode to set
    */
   public void setAgencyCode(String agencyCode) {
      this.agencyCode = agencyCode;
   }

   /*
   public void setAgencyName(String agencyName) {
      this.agencyName = agencyName;
   }

   /*
    * 
    */
   public void setAgencyType(AgencyTypeEnum agencyType) {
      this.agencyType = agencyType;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.organization.AgencyFilter#getEventTypeId()
    */
   public Long getEventTypeId() {
      return eventTypeId;
   }

   /*
    * 
    */
   public void setEventTypeId(Long eventTypeId) {
	   if (eventTypeId.equals(-1L)) {
		   this.eventTypeId = null;
	   } else {
		   this.eventTypeId = eventTypeId;
	   }
   }

   /**
    * @param agencyName the agencyName to set
    */
   public void setAgencyName(String agencyName) {
      this.agencyName = agencyName;
   }

}
