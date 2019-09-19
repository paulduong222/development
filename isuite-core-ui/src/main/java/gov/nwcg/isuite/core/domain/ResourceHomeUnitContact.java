package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ResourceHomeUnitContact extends Persistable {
	
	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource);

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource();

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId);

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId();
	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email);

	/**
	 * @return the email
	 */
	public String getEmail();

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address);

	/**
	 * @return the address
	 */
	public Address getAddress();

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId);

	/**
	 * @return the addressId
	 */
	public Long getAddressId();

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Organization unit);

	/**
	 * @return the unit
	 */
	public Organization getUnit();

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Long unitId);

	/**
	 * @return the unitId
	 */
	public Long getUnitId();
	
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName);

	/**
	 * @return the contactName
	 */
	public String getContactName();

}
