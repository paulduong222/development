package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface AdminOffice extends Persistable {

   /**
    * @param address address object
    */
   public void setAddress(Address address);

   /**
    * @return the address object
    */
   public Address getAddress();

   /**
    * @param officeName the office name
    */
   public void setOfficeName(String officeName);
   
   /**
    * @return the office name
    */
   public String getOfficeName();

   
   /**
    * @param phone the phone number
    */
   public void setPhone(String phone);
   
   /**
    * @return the phone number
    */
   public String getPhone();
   
   
   /**
    * Returns the deleted date.
    * 
    * @return
    * 		the date the resource is deleted
    */
   public Date getDeletedDate();
   
   /**
    * Sets the deleted date.
    * 
    * @param deletedDate
    * 		the date the resource is deleted
    */
   public void setDeletedDate(Date deletedDate);
   
   /**
    * @return the standard
    */
   public Boolean isStandard();
   
   /**
    * @param standard the standard to set
    */
   public void setStandard(Boolean standard);

   /**		 
    * Returns the contractorAgreements.		 
    *		 
    * @return 		 
     * the contractorAgreements to return		 
     */		 
   public Collection<ContractorAgreement> getContractorAgreements();		 
   		 
   /**		 
    * Sets the contractorAgreements.		 
    *		 
    * @param contractorAgreements 		 
    * the contractorAgreements to set		 
   */		 
   public void setContractorAgreements(Collection<ContractorAgreement> contractorAgreements);
   
}
