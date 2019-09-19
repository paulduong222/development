package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface ResInvImportConflict extends Persistable {
	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description);

	/**
	 * @return the resInvImport
	 */
	public ResInvImport getResInvImport();

	/**
	 * @param resInvImport the resInvImport to set
	 */
	public void setResInvImport(ResInvImport resInvImport);

	/**
	 * @return the resInvImportId
	 */
	public Long getResInvImportId() ;

	/**
	 * @param resInvImportId the resInvImportId to set
	 */
	public void setResInvImportId(Long resInvImportId);

	/**
	 * @return the isResolved
	 */
	public StringBooleanEnum getIsResolved();

	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(StringBooleanEnum isResolved);

	/**
	 * @return the rossResId
	 */
	public String getRossResId();

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(String rossResId);

	/**
	 * @return the resourceName
	 */
	public String getResourceName();

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName);

	/**
	 * @return the firstName
	 */
	public String getFirstName();

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName);

	/**
	 * @return the lastName
	 */
	public String getLastName();

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName);

	/**
	 * @return the gaccOrgUnitCodeName
	 */
	public String getGaccOrgUnitCodeName();

	/**
	 * @param gaccOrgUnitCodeName the gaccOrgUnitCodeName to set
	 */
	public void setGaccOrgUnitCodeName(String gaccOrgUnitCodeName);
	/**
	 * @return the gaccDispUnitCodeName
	 */
	public String getGaccDispUnitCodeName();

	/**
	 * @param gaccDispUnitCodeName the gaccDispUnitCodeName to set
	 */
	public void setGaccDispUnitCodeName(String gaccDispUnitCodeName);

	/**
	 * @return the resProvUnitCodeName
	 */
	public String getResProvUnitCodeName();

	/**
	 * @param resProvUnitCodeName the resProvUnitCodeName to set
	 */
	public void setResProvUnitCodeName(String resProvUnitCodeName);

}