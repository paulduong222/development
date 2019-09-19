package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

public interface IapForm206 extends Persistable {

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
	 * @return the approvedBy
	 */
	public String getApprovedBy();

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy);

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked();

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked);

	/**
	 * @return the isAviationUtilized
	 */
	public StringBooleanEnum getIsAviationUtilized();

	/**
	 * @param isAviationUtilized the isAviationUtilized to set
	 */
	public void setIsAviationUtilized(StringBooleanEnum isAviationUtilized);

	/**
	 * @return the medicalEmergencyProcedures
	 */
	public String getMedicalEmergencyProcedures();

	/**
	 * @param medicalEmergencyProcedures the medicalEmergencyProcedures to set
	 */
	public void setMedicalEmergencyProcedures(String medicalEmergencyProcedures);

	/**
	 * @return the iapHospitals
	 */
	public Collection<IapHospital> getIapHospitals();

	/**
	 * @param iapHospitals the iapHospitals to set
	 */
	public void setIapHospitals(Collection<IapHospital> iapHospitals);

	/**
	 * @return the iapMedicalAids
	 */
	public Collection<IapMedicalAid> getIapMedicalAids();

	/**
	 * @param iapMedicalAids the iapMedicalAids to set
	 */
	public void setIapMedicalAids(Collection<IapMedicalAid> iapMedicalAids);	
	
	/**
	 * @return the iapAreaLocationCapabilities
	 */
	public Collection<IapAreaLocationCapability> getIapAreaLocationCapabilities() ;

	/**
	 * @param iapAreaLocationCapabilities the iapAreaLocationCapabilities to set
	 */
	public void setIapAreaLocationCapabilities(
			Collection<IapAreaLocationCapability> iapAreaLocationCapabilities) ;

	/**
	 * @return the iapRemoteCampLocations
	 */
	public Collection<IapRemoteCampLocations> getIapRemoteCampLocations() ;

	/**
	 * @param iapRemoteCampLocations the iapRemoteCampLocations to set
	 */
	public void setIapRemoteCampLocations(
			Collection<IapRemoteCampLocations> iapRemoteCampLocations);

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy();

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy);

	/**
	 * @return the reviewedDate
	 */
	public Date getReviewedDate();

	/**
	 * @param reviewedDate the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate);

	
}