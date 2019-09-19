package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface TrainingContact extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

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
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active);

	/**
	 * @return the active
	 */
	public StringBooleanEnum getActive();

}
