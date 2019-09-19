package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface Contractor extends Persistable{

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name);

	/**
	 * Returns the tin.
	 *
	 * @return 
	 *		the tin to return
	 */
	public String getTin() ;

	/**
	 * Sets the tin.
	 *
	 * @param tin 
	 *			the tin to set
	 */
	public void setTin(String tin) ;

	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns() ;

	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns);

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone();

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * Returns the fax.
	 *
	 * @return 
	 *		the fax to return
	 */
	public String getFax();

	/**
	 * Sets the fax.
	 *
	 * @param fax 
	 *			the fax to set
	 */
	public void setFax(String fax);

	/**
	 * Returns the address.
	 *
	 * @return 
	 *		the address to return
	 */
	public Address getAddress();

	/**
	 * Sets the address.
	 *
	 * @param address 
	 *			the address to set
	 */
	public void setAddress(Address address);

	/**
	 * Returns the addressId.
	 *
	 * @return 
	 *		the addressId to return
	 */
	public Long getAddressId();

	/**
	 * Sets the addressId.
	 *
	 * @param addressId 
	 *			the addressId to set
	 */
	public void setAddressId(Long addressId);

	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate() ;

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) ;


	/**
	 * Returns the contractorAgreements.
	 *
	 * @return 
	 *		the contractorAgreements to return
	 */
	public Collection<ContractorAgreement> getContractorAgreements();

	/**
	 * Sets the contractorAgreements.
	 *
	 * @param contractorAgreements 
	 *			the contractorAgreements to set
	 */
	public void setContractorAgreements(Collection<ContractorAgreement> contractorAgreements);
	
	 /**
	  * Sets the enabled status of the contractor.
	  * 
	  * @param val
	  * 			the enabled status of the contractor to set
	  */
	public void setEnabled(Boolean val);
   
	/**
	 * Returns if the contractor is enabled.
	 * 
	 * @return
	 * 		the enabled status of the contractor
	 */
  	public Boolean isEnabled();
	   
	/**
	 * Returns incidents.
	 * 
	 * @return
	 * 		incidents
	 */
  	public Collection<Incident> getIncidents();
	
	/**
	 * Sets the incidents.
	 * 
	 * @param incidents
	 */
  	public void setIncidents(Collection<Incident> incidents);

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources() ;

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources);
	
	/**
	 * Sets the timeInvoices
	 * 
	 * @param timeInvoices
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices);
	
	/**
	 * Returns the timeInvoices
	 * 
	 * @return
	 */
	public Collection<TimeInvoice> getTimeInvoices();
	
}
