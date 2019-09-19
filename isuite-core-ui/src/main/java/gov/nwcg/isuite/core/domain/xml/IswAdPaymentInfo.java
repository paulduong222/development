package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswAdPaymentInfo", table = "isw_ad_payment_info")
public class IswAdPaymentInfo {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_AD_PAYMENT_INFO", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "RateArea", type = "COMPLEX", target = IswlRateArea.class
						, lookupname = "Id", sourcename = "RateAreaId")
	private IswlRateArea rateArea;

	@XmlTransferField(name = "RateAreaId", sqlname = "RATE_AREA_ID", type = "LONG"
							, derived = true, derivedfield = "RateArea")
	private Long rateAreaId;

	@XmlTransferField(name = "RateAreaName", sqlname = "RATE_AREA_NAME", type = "STRING",nullwhenempty=true)
	private String rateAreaName;

	@XmlTransferField(name = "RateClassRateTransferableIdentity", alias="rcrti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="RateClassRateId"
		, disjoined=true, disjoinedtable="iswl_rate_class_rate", disjoinedfield="transferable_identity",disjoinedsource="RATE_CLASS_RATE_ID")
	private String rateClassRateTransferableIdentity;
	
	@XmlTransferField(name = "RateClassRateId", sqlname = "RATE_CLASS_RATE_ID", type = "LONG"
						, derived = true, derivedfield = "RateClassRateTransferableIdentity")
	private Long rateClassRateId;

	@XmlTransferField(name = "AssignmentTimeId", sqlname = "ASSIGNMENT_TIME_ID", type = "LONG",updateable=false)
	private Long assignmentTimeId;

	@XmlTransferField(name = "InitialEmp", sqlname = "IS_INITIAL_EMP", type="BOOLEAN")
	private Boolean initialEmp = false;

	@XmlTransferField(name = "ReturnTravel", sqlname = "IS_RETURN_TRAVEL", type="BOOLEAN")
	private Boolean returnTravel = false;

	@XmlTransferField(name = "PointOfHireOrgTransferableIdentity", alias="pntofhireoti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="PointOfHireId"
						, disjoined=true, disjoinedtable="isw_organization", disjoinedfield="transferable_identity",disjoinedsource="POINT_OF_HIRE_ID")
	private String pointOfHireOrgTransferableIdentity;

	@XmlTransferField(name = "PointOfHireId", sqlname = "POINT_OF_HIRE_ID", type = "LONG"
						,derived=true,derivedfield="pointOfHireOrgTransferableIdentity")
	private Long pointOfHireId;

	@XmlTransferField(name = "PointOfHire", sqlname = "POINT_OF_HIRE", type = "STRING")
	private String pointOfHire;

	@XmlTransferField(name = "SSN", sqlname = "SSN", type = "STRING")
	private String ssn;

	@XmlTransferField(name = "ECI", sqlname = "ECI", type = "STRING")
	private String eci;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "RateYear", sqlname = "RATE_YEAR", type = "INTEGER")
	private Integer rateYear;

	public IswAdPaymentInfo() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rateArea
	 */
	public IswlRateArea getRateArea() {
		return rateArea;
	}

	/**
	 * @param rateArea
	 *            the rateArea to set
	 */
	public void setRateArea(IswlRateArea rateArea) {
		this.rateArea = rateArea;
	}

	/**
	 * @return the rateAreaId
	 */
	public Long getRateAreaId() {
		return rateAreaId;
	}

	/**
	 * @param rateAreaId
	 *            the rateAreaId to set
	 */
	public void setRateAreaId(Long rateAreaId) {
		this.rateAreaId = rateAreaId;
	}

	/**
	 * @return the rateClassRateId
	 */
	public Long getRateClassRateId() {
		return rateClassRateId;
	}

	/**
	 * @param rateClassRateId
	 *            the rateClassRateId to set
	 */
	public void setRateClassRateId(Long rateClassRateId) {
		this.rateClassRateId = rateClassRateId;
	}

	/**
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}

	/**
	 * @param assignmentTimeId
	 *            the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}

	/**
	 * @return the initialEmp
	 */
	public Boolean getInitialEmp() {
		return initialEmp;
	}

	/**
	 * @param initialEmp
	 *            the initialEmp to set
	 */
	public void setInitialEmp(Boolean initialEmp) {
		this.initialEmp = initialEmp;
	}

	/**
	 * @return the returnTravel
	 */
	public Boolean getReturnTravel() {
		return returnTravel;
	}

	/**
	 * @param returnTravel
	 *            the returnTravel to set
	 */
	public void setReturnTravel(Boolean returnTravel) {
		this.returnTravel = returnTravel;
	}

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param pointOfHire
	 *            the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *            the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the eci
	 */
	public String getEci() {
		return eci;
	}

	/**
	 * @param ssn
	 *            the eci to set
	 */
	public void setEci(String eci) {
		this.eci = eci;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Long getPointOfHireId() {
		return pointOfHireId;
	}

	public void setPointOfHireId(Long pointOfHireId) {
		this.pointOfHireId = pointOfHireId;
	}

	public String getRateAreaName() {
		return rateAreaName;
	}

	public void setRateAreaName(String rateAreaName) {
		this.rateAreaName = rateAreaName;
	}

	public Integer getRateYear() {
		return rateYear;
	}

	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
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
	 * @return the rateClassRateTransferableIdentity
	 */
	public String getRateClassRateTransferableIdentity() {
		return rateClassRateTransferableIdentity;
	}

	/**
	 * @param rateClassRateTransferableIdentity the rateClassRateTransferableIdentity to set
	 */
	public void setRateClassRateTransferableIdentity(
			String rateClassRateTransferableIdentity) {
		this.rateClassRateTransferableIdentity = rateClassRateTransferableIdentity;
	}

	/**
	 * @return the pointOfHireOrgTransferableIdentity
	 */
	public String getPointOfHireOrgTransferableIdentity() {
		return pointOfHireOrgTransferableIdentity;
	}

	/**
	 * @param pointOfHireOrgTransferableIdentity the pointOfHireOrgTransferableIdentity to set
	 */
	public void setPointOfHireOrgTransferableIdentity(
			String pointOfHireOrgTransferableIdentity) {
		this.pointOfHireOrgTransferableIdentity = pointOfHireOrgTransferableIdentity;
	}


}
