package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapAttachment extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);
	
	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName);

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName();

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(StringBooleanEnum attached);

	/**
	 * @return the attached
	 */
	public StringBooleanEnum getAttached();

	/**
	 * @return the filename
	 */
	public String getFilename() ;

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename);
	
}
