package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

/**
 * Account Code relational mapping to the database.
 * 
 * @author mpoll
 */
public interface AccountCode extends Persistable {
   
   /**
    * Returns the account code.
    * 
    * @return
    * 		the account code to return
    */
   public String getAccountCode();
   
   /**
    * Sets the account code.
    * 
    * @param accountCode
    * 			the account code to set
    */
   public void setAccountCode(String accountCode);
   
   /**
    * Returns the regionUnit.
    *
    * @return 
    *		the regionUnit to return
    */
   public RegionCode getRegionUnit();

   /**
    * Sets the regionUnit.
    *
    * @param regionUnit 
    *			the regionUnit to set
    */
   public void setRegionUnit(RegionCode regionUnit);
   
   /**
    * Returns the region unit id.
    * 
    * @return
    * 		the region unit id to return
    */
   public Long getRegionUnitId();
   
   /**
    * Sets the region unit id.
    *  
    * @param regionUnitId
    * 			the region unit id to set
    */
   public void setRegionUnitId(Long regionUnitId);
   
   /**
    * Returns the agency.
    * 
    * @return
    * 		the agency to return
    */
   public Agency getAgency();
   
   /**
    * Sets the agency.
    * 
    * @param agency
    * 			the agency to set
    */
   public void setAgency(Agency agency);
   
   /**
    * Returns the agency id.
    * 
    * @return
    * 		the agency id to return
    */
   public Long getAgencyId();
   
   /**
    * Sets the agency id.
    * 
    * @param agencyId
    * 			the agency id to set
    */
   public void setAgencyId(Long agencyId);
   
   /**
    * Returns the incidentAccountCodes.
    *
    * @return 
    *		the incidentAccountCodes to return
    */
   public Collection<IncidentAccountCode> getIncidentAccountCodes();

   /**
    * Sets the incidentAccountCodes.
    *
    * @param incidentAccountCodes 
    *			the incidentAccountCodes to set
    */
   public void setIncidentAccountCodes(Collection<IncidentAccountCode> incidentAccountCodes);


}
