package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@XmlTransferTable(name = "IswTimeAssignAdjust", table="isw_time_assign_adjust")
public class IswTimeAssignAdjust {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TIME_ASSIGN_ADJUST", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AssignmentTransferableIdentity", alias="asgnti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AssignmentId"
						, disjoined=true, disjoinedtable="isw_assignment", disjoinedfield="transferable_identity",disjoinedsource="ASSIGNMENT_ID")
	private String assignmentTransferableIdentity;
	
	@XmlTransferField(name = "AssignmentId", sqlname="ASSIGNMENT_ID", type="LONG"
						,derived=true, derivedfield="AssignmentTransferableIdentity")
	private Long assignmentId;
	
	@XmlTransferField(name = "ActivityDate", sqlname="activity_date", type="DATE")
	private Date activityDate;
	
	@XmlTransferField(name = "AdjustmentType", sqlname="ADJUSTMENT_TYPE", type="STRING")
	private String adjustmentType;
	
	@XmlTransferField(name = "AdjustCategoryTransferableIdentity", alias="adjcatti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AdjustmentCategoryId"
						, disjoined=true, disjoinedtable="iswl_adjust_category", disjoinedfield="transferable_identity",disjoinedsource="ADJUST_CATEGORY_ID")
	private String adjustCategoryTransferableIdentity;
	
	@XmlTransferField(name = "AdjustmentCategoryId", sqlname="ADJUST_CATEGORY_ID", type="LONG"
			,derived=true, derivedfield="AdjustCategoryTransferableIdentity")
	private Long adjustmentCategoryId;
	
	@XmlTransferField(name = "AdjustmentAmount", sqlname = "adjustment_amount", type="BIGDECIMAL")
	private BigDecimal adjustmentAmount;
	
	@XmlTransferField(name = "IacTransferableIdentity", alias="iacti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IacId"
						, disjoined=true, disjoinedtable="isw_incident_account_code", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_ACCOUNT_CODE_ID")
	private String iacTransferableIdentity;
	
	@XmlTransferField(name = "IacId", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="LONG"
						,derived=true, derivedfield="IacTransferableIdentity")
	private Long iacId;
	
	@XmlTransferField(name = "Commodity", sqlname = "COMMODITY", type="STRING")
	private String commodity;
	
	@XmlTransferField(name = "DeletedDate", sqlname="deleted_date", type="DATE")
	private Date deletedDate;

	@XmlTransferField(name = "TimeAssignAdjustInvoice", type = "COMPLEX", target=IswTimeAssignAdjustInvoice.class
			,lookupname="TimePostAdjustId", sourcename="Id"
			, cascade=true)
	private Collection<IswTimeAssignAdjustInvoice> timeAssignAdjustInvoices = new ArrayList<IswTimeAssignAdjustInvoice>();
	
	//private Collection<TimeInvoice> timeInvoices;

	//private IncidentResourceDailyCost incidentResourceDailyCost;
	
	
	public IswTimeAssignAdjust() {
	}

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
	 * @return the assignmentTransferableIdentity
	 */
	public String getAssignmentTransferableIdentity() {
		return assignmentTransferableIdentity;
	}

	/**
	 * @param assignmentTransferableIdentity the assignmentTransferableIdentity to set
	 */
	public void setAssignmentTransferableIdentity(
			String assignmentTransferableIdentity) {
		this.assignmentTransferableIdentity = assignmentTransferableIdentity;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the adjustmentType
	 */
	public String getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	/**
	 * @return the adjustCategoryTransferableIdentity
	 */
	public String getAdjustCategoryTransferableIdentity() {
		return adjustCategoryTransferableIdentity;
	}

	/**
	 * @param adjustCategoryTransferableIdentity the adjustCategoryTransferableIdentity to set
	 */
	public void setAdjustCategoryTransferableIdentity(
			String adjustCategoryTransferableIdentity) {
		this.adjustCategoryTransferableIdentity = adjustCategoryTransferableIdentity;
	}

	/**
	 * @return the adjustmentCategoryId
	 */
	public Long getAdjustmentCategoryId() {
		return adjustmentCategoryId;
	}

	/**
	 * @param adjustmentCategoryId the adjustmentCategoryId to set
	 */
	public void setAdjustmentCategoryId(Long adjustmentCategoryId) {
		this.adjustmentCategoryId = adjustmentCategoryId;
	}

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	/**
	 * @return the iacTransferableIdentity
	 */
	public String getIacTransferableIdentity() {
		return iacTransferableIdentity;
	}

	/**
	 * @param iacTransferableIdentity the iacTransferableIdentity to set
	 */
	public void setIacTransferableIdentity(String iacTransferableIdentity) {
		this.iacTransferableIdentity = iacTransferableIdentity;
	}

	/**
	 * @return the iacId
	 */
	public Long getIacId() {
		return iacId;
	}

	/**
	 * @param iacId the iacId to set
	 */
	public void setIacId(Long iacId) {
		this.iacId = iacId;
	}

	/**
	 * @return the commodity
	 */
	public String getCommodity() {
		return commodity;
	}

	/**
	 * @param commodity the commodity to set
	 */
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the timeAssignAdjustInvoices
	 */
	public Collection<IswTimeAssignAdjustInvoice> getTimeAssignAdjustInvoices() {
		return timeAssignAdjustInvoices;
	}

	/**
	 * @param timeAssignAdjustInvoices the timeAssignAdjustInvoices to set
	 */
	public void setTimeAssignAdjustInvoices(
			Collection<IswTimeAssignAdjustInvoice> timeAssignAdjustInvoices) {
		this.timeAssignAdjustInvoices = timeAssignAdjustInvoices;
	}
	
	

}
