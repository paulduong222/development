package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapForm205 extends Persistable {

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
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction();

	/**
	 * @param specialInstruction the specialInstruction to set
	 */
	public void setSpecialInstruction(String specialInstruction);

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy();

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy);

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition();

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition);

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate();

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate);

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked();

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked);

	/**
	 * @return the iapFrequencies
	 */
	public Collection<IapFrequency> getIapFrequencies();

	/**
	 * @param iapFrequencies the iapFrequencies to set
	 */
	public void setIapFrequencies(Collection<IapFrequency> iapFrequencies);


}