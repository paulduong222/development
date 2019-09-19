package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface DataTransfer extends Persistable{
   
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the filename
	 */
	public String getFilename();

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename);

	/**
	 * @return the email
	 */
	public String getEmail();

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the filePassword
	 */
	public String getFilePassword();

	/**
	 * @param filePassword the filePassword to set
	 */
	public void setFilePassword(String filePassword);

	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem();

	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);

	/**
	 * @return the storedFilepath
	 */
	public String getStoredFilepath();

	/**
	 * @param storedFilepath the storedFilepath to set
	 */
	public void setStoredFilepath(String storedFilepath);
	
}
