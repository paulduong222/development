package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapForm206", table="isw_iap_form_206")
public class IswIapForm206 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_206", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPlanId", sqlname="IAP_PLAN_ID", type="LONG")
	private Long iapPlanId;

	@XmlTransferField(name = "PreparedBy", sqlname="PREPARED_BY", type="STRING")
	private String preparedBy;

	@XmlTransferField(name = "PreparedByPosition", sqlname="PREPARED_BY_POS", type="STRING")
	private String preparedByPosition;

	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;

	@XmlTransferField(name = "ReviewedBy", sqlname="REVIEWED_BY", type="STRING")
	private String reviewedBy;

	@XmlTransferField(name = "ReviewedDate", sqlname="REVIEWED_DATE", type="DATE")
	private Date reviewedDate;

	@XmlTransferField(name = "ApprovedBy", sqlname="APPROVED_BY", type="STRING")
	private String approvedBy;
	
	@XmlTransferField(name = "IsFormLocked", sqlname="IS_FORM_LOCKED", type="STRING")
	private String isFormLocked;
	
	@XmlTransferField(name = "IsAviationUtilized", sqlname="IS_AVIATION_UTILIZED", type="STRING")
	private String isAviationUtilized;

	@XmlTransferField(name = "MedicalEmergencyProcedures", sqlname="MEDICAL_EMERGENCY_PROC", type="STRING")
	private String medicalEmergencyProcedures;

	@XmlTransferField(name = "IapHospital", type="COMPLEX", target=IswIapHospital.class
			, lookupname="IapForm206Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapHospital> iapHospitals = new ArrayList<IswIapHospital>();

	@XmlTransferField(name = "IapMedicalAid", type="COMPLEX", target=IswIapMedicalAid.class
			, lookupname="IapForm206Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapMedicalAid> iapMedicalAids = new ArrayList<IswIapMedicalAid>();

	@XmlTransferField(name = "IapAreaLocationCapability", type="COMPLEX", target=IswIapAreaLocationCapability.class
			, lookupname="IapForm206Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapAreaLocationCapability> iapAreaLocationCapabilitys = new ArrayList<IswIapAreaLocationCapability>();
	
	@XmlTransferField(name = "IapRemoteCampLocation", type="COMPLEX", target=IswIapRemoteCampLocation.class
			, lookupname="IapForm206Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapRemoteCampLocation> iapRemoteCampLocations = new ArrayList<IswIapRemoteCampLocation>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the reviewedDate
	 */
	public Date getReviewedDate() {
		return reviewedDate;
	}

	/**
	 * @param reviewedDate the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the isFormLocked
	 */
	public String getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(String isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the isAviationUtilized
	 */
	public String getIsAviationUtilized() {
		return isAviationUtilized;
	}

	/**
	 * @param isAviationUtilized the isAviationUtilized to set
	 */
	public void setIsAviationUtilized(String isAviationUtilized) {
		this.isAviationUtilized = isAviationUtilized;
	}

	/**
	 * @return the medicalEmergencyProcedures
	 */
	public String getMedicalEmergencyProcedures() {
		return medicalEmergencyProcedures;
	}

	/**
	 * @param medicalEmergencyProcedures the medicalEmergencyProcedures to set
	 */
	public void setMedicalEmergencyProcedures(String medicalEmergencyProcedures) {
		this.medicalEmergencyProcedures = medicalEmergencyProcedures;
	}

	/**
	 * @return the iapHospitals
	 */
	public Collection<IswIapHospital> getIapHospitals() {
		return iapHospitals;
	}

	/**
	 * @param iapHospitals the iapHospitals to set
	 */
	public void setIapHospitals(Collection<IswIapHospital> iapHospitals) {
		this.iapHospitals = iapHospitals;
	}

	/**
	 * @return the iapMedicalAids
	 */
	public Collection<IswIapMedicalAid> getIapMedicalAids() {
		return iapMedicalAids;
	}

	/**
	 * @param iapMedicalAids the iapMedicalAids to set
	 */
	public void setIapMedicalAids(Collection<IswIapMedicalAid> iapMedicalAids) {
		this.iapMedicalAids = iapMedicalAids;
	}

	/**
	 * @return the iapAreaLocationCapabilitys
	 */
	public Collection<IswIapAreaLocationCapability> getIapAreaLocationCapabilitys() {
		return iapAreaLocationCapabilitys;
	}

	/**
	 * @param iapAreaLocationCapabilitys the iapAreaLocationCapabilitys to set
	 */
	public void setIapAreaLocationCapabilitys(
			Collection<IswIapAreaLocationCapability> iapAreaLocationCapabilitys) {
		this.iapAreaLocationCapabilitys = iapAreaLocationCapabilitys;
	}

	/**
	 * @return the iapRemoteCampLocations
	 */
	public Collection<IswIapRemoteCampLocation> getIapRemoteCampLocations() {
		return iapRemoteCampLocations;
	}

	/**
	 * @param iapRemoteCampLocations the iapRemoteCampLocations to set
	 */
	public void setIapRemoteCampLocations(
			Collection<IswIapRemoteCampLocation> iapRemoteCampLocations) {
		this.iapRemoteCampLocations = iapRemoteCampLocations;
	}

}
