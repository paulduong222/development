package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapBranch", table = "isw_iap_branch")
public class IswIapBranch {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPlanTransferableIdentity", alias="planti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPlanId"
		, disjoined=true, disjoinedtable="isw_iap_plan", disjoinedfield="transferable_identity",disjoinedsource="iap_plan_id")
	private String iapPlanTransferableIdentity;

	@XmlTransferField(name = "IapPlanId", sqlname="IAP_PLAN_ID", type="LONG"
		,derived=true,derivedfield="IapPlanTransferableIdentity")
	private Long iapPlanId;
	
	@XmlTransferField(name = "BranchName", sqlname="BRANCH_NAME", type="STRING")
	private String branchName;
	
	@XmlTransferField(name = "DivisionName", sqlname="DIVISION_NAME", type="STRING")
	private String divisionName;
	
	@XmlTransferField(name = "GroupName", sqlname="GROUP_NAME", type="STRING")
	private String groupName;
	
	@XmlTransferField(name = "ControlOperations", sqlname="CONTROL_OPERATIONS", type="STRING", ischardata=true)
	private String controlOperations;
	
	@XmlTransferField(name = "SpecialInstructions", sqlname="SPECIAL_INSTRUCTIONS", type="STRING", ischardata=true)
	private String specialInstructions;
	
	@XmlTransferField(name = "StagingArea", sqlname="STAGING_AREA", type="STRING")
	private String stagingArea;
	
	@XmlTransferField(name = "WorkAssignment", sqlname="WORK_ASSIGNMENT", type="STRING", ischardata=true)
	private String workAssignment;
	
	@XmlTransferField(name = "PreparedBy", sqlname="PREPARED_BY", type="STRING")
	private String preparedBy;
	
	@XmlTransferField(name = "PreparedByPosition", sqlname="PREPARED_BY_POSITION", type="STRING")
	private String preparedByPosition;

	@XmlTransferField(name = "ApprovedBy", sqlname="APPROVED_BY", type="STRING")
	private String approvedBy;
	
	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;

	@XmlTransferField(name = "IsForm204Locked", sqlname="IS_FORM_204_LOCKED", type="STRING")
	private String isForm204Locked;

	@XmlTransferField(name = "IapBranchCommSummary", type="COMPLEX", target=IswIapBranchCommSummary.class
			, lookupname="IapBranchId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapBranchCommSummary> iapBranchCommSummarys = new ArrayList<IswIapBranchCommSummary>();
	
	@XmlTransferField(name = "IapBranchPersonnel", type="COMPLEX", target=IswIapBranchPersonnel.class
			, lookupname="IapBranchId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapBranchPersonnel> iapBranchPersonnels = new ArrayList<IswIapBranchPersonnel>();
	
	@XmlTransferField(name = "IapBranchRscAssign", type="COMPLEX", target=IswIapBranchRscAssign.class
			, lookupname="IapBranchId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapBranchRscAssign> iapBranchRscAssigns = new ArrayList<IswIapBranchRscAssign>();

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranch() {
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
	 * @return the iapPlanTransferableIdentity
	 */
	public String getIapPlanTransferableIdentity() {
		return iapPlanTransferableIdentity;
	}

	/**
	 * @param iapPlanTransferableIdentity the iapPlanTransferableIdentity to set
	 */
	public void setIapPlanTransferableIdentity(String iapPlanTransferableIdentity) {
		this.iapPlanTransferableIdentity = iapPlanTransferableIdentity;
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
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the controlOperations
	 */
	public String getControlOperations() {
		return controlOperations;
	}

	/**
	 * @param controlOperations the controlOperations to set
	 */
	public void setControlOperations(String controlOperations) {
		this.controlOperations = controlOperations;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the stagingArea
	 */
	public String getStagingArea() {
		return stagingArea;
	}

	/**
	 * @param stagingArea the stagingArea to set
	 */
	public void setStagingArea(String stagingArea) {
		this.stagingArea = stagingArea;
	}

	/**
	 * @return the workAssignment
	 */
	public String getWorkAssignment() {
		return workAssignment;
	}

	/**
	 * @param workAssignment the workAssignment to set
	 */
	public void setWorkAssignment(String workAssignment) {
		this.workAssignment = workAssignment;
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
	 * @return the isForm204Locked
	 */
	public String getIsForm204Locked() {
		return isForm204Locked;
	}

	/**
	 * @param isForm204Locked the isForm204Locked to set
	 */
	public void setIsForm204Locked(String isForm204Locked) {
		this.isForm204Locked = isForm204Locked;
	}

	/**
	 * @return the iapBranchCommSummarys
	 */
	public Collection<IswIapBranchCommSummary> getIapBranchCommSummarys() {
		return iapBranchCommSummarys;
	}

	/**
	 * @param iapBranchCommSummarys the iapBranchCommSummarys to set
	 */
	public void setIapBranchCommSummarys(
			Collection<IswIapBranchCommSummary> iapBranchCommSummarys) {
		this.iapBranchCommSummarys = iapBranchCommSummarys;
	}

	/**
	 * @return the iapBranchPersonnels
	 */
	public Collection<IswIapBranchPersonnel> getIapBranchPersonnels() {
		return iapBranchPersonnels;
	}

	/**
	 * @param iapBranchPersonnels the iapBranchPersonnels to set
	 */
	public void setIapBranchPersonnels(
			Collection<IswIapBranchPersonnel> iapBranchPersonnels) {
		this.iapBranchPersonnels = iapBranchPersonnels;
	}

	/**
	 * @return the iapBranchRscAssigns
	 */
	public Collection<IswIapBranchRscAssign> getIapBranchRscAssigns() {
		return iapBranchRscAssigns;
	}

	/**
	 * @param iapBranchRscAssigns the iapBranchRscAssigns to set
	 */
	public void setIapBranchRscAssigns(
			Collection<IswIapBranchRscAssign> iapBranchRscAssigns) {
		this.iapBranchRscAssigns = iapBranchRscAssigns;
	}


}
