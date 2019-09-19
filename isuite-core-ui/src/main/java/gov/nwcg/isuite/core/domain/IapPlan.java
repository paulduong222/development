package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Transient;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapPlan extends Persistable {

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);


	/**
	 * @return the incidentName
	 */
	public String getIncidentName();

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * @return the operationPeriod
	 */
	public String getOperationPeriod();

	/**
	 * @param operationPeriod the operationPeriod to set
	 */
	public void setOperationPeriod(String operationPeriod);

	/**
	 * @return the fromDate
	 */
	public Date getFromDate();

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate);

	/**
	 * @return the toDate
	 */
	public Date getToDate();

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate);

	/**
	 * @return the isPlanLocked
	 */
	public StringBooleanEnum getIsPlanLocked();

	/**
	 * @param isPlanLocked the isPlanLocked to set
	 */
	public void setIsPlanLocked(StringBooleanEnum isPlanLocked);

	/**
	 * @return the iapForm202s
	 */
	public Collection<IapForm202> getIapForm202s();

	/**
	 * @param iapForm202s the iapForm202s to set
	 */
	public void setIapForm202s(Collection<IapForm202> iapForm202s);

	/**
	 * @return the iapForm203s
	 */
	public Collection<IapForm203> getIapForm203s();

	/**
	 * @param iapForm203s the iapForm203s to set
	 */
	public void setIapForm203s(Collection<IapForm203> iapForm203s);

	/**
	 * @return the iapForm205s
	 */
	public Collection<IapForm205> getIapForm205s();

	/**
	 * @param iapForm205s the iapForm205s to set
	 */
	public void setIapForm205s(Collection<IapForm205> iapForm205s) ;

	/**
	 * @return the iapForm220s
	 */
	public Collection<IapForm220> getIapForm220s();

	/**
	 * @param iapForm220s the iapForm220s to set
	 */
	public void setIapForm220s(Collection<IapForm220> iapForm220s);

	/**
	 * @return the iapBranchs
	 */
	public Collection<IapBranch> getIapBranchs();

	/**
	 * @param iapBranchs the iapBranchs to set
	 */
	public void setIapBranchs(Collection<IapBranch> iapBranchs);

	/**
	 * @return the iapAttachments
	 */
	public Collection<IapAttachment> getIapAttachments();

	/**
	 * @param iapAttachments the iapAttachments to set
	 */
	public void setIapAttachments(Collection<IapAttachment> iapAttachments);
	/**
	 * @return the iapPlanPrintOrder
	 */
	public Collection<IapPlanPrintOrder> getIapPlanPrintOrder();

	/**
	 * @param iapPlanPrintOrder the iapPlanPrintOrder to set
	 */
	public void setIapPlanPrintOrder(Collection<IapPlanPrintOrder> iapPlanPrintOrder);

	/**
	 * @return the iapForm206s
	 */
	public Collection<IapForm206> getIapForm206s();

	/**
	 * @param iapForm206s the iapForm206s to set
	 */
	public void setIapForm206s(Collection<IapForm206> iapForm206s);
	
	@Transient
	public boolean isPlanEditable();
	
	
}