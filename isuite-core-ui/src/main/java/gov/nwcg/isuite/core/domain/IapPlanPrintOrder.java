package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IapPlanPrintOrder extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);
	
	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan);

	/**
	 * @return the FormType
	 */
	public String getFormType() ;

	/**
	 * @param fileType the formType to set
	 */
	public void setFormType(String formType);
	
	/**
	 * @return the formId
	 */
	public Long getFormId();

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId);
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position);

	/**
	 * @return the position
	 */
	public Integer getPosition();
}
