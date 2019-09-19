package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RscTrainingTrainer extends Persistable {
	
	/**
	 * @param resourceTraining the resourceTraining to set
	 */
	public void setResourceTraining(ResourceTraining resourceTraining);
	/**
	 * @return the resourceTraining
	 */
	public ResourceTraining getResourceTraining();

	/**
	 * @param resourceTrainingId the resourceTrainingId to set
	 */
	public void setResourceTrainingId(Long resourceTrainingId);

	/**
	 * @return the resourceTrainingId
	 */
	public Long getResourceTrainingId();

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
	 * @param reqestNumber the reqestNumber to set
	 */
	public void setReqestNumber(String reqestNumber);

	/**
	 * @return the reqestNumber
	 */
	public String getReqestNumber();

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email);

	/**
	 * @return the email
	 */
	public String getEmail();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the phone
	 */
	public String getPhone();
	
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
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind);

	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId);

	/**
	 * @return the kindId
	 */
	public Long getKindId();
	
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName);

	/**
	 * @return the resourceName
	 */
	public String getResourceName();
	
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

}
