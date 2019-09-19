package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IapBranch entity.
 */
@Entity
@Table(name = "isw_iap_branch")
@SequenceGenerator(name="SEQ_IAP_BRANCH", sequenceName="SEQ_IAP_BRANCH")
public class IapBranchImpl extends PersistableImpl implements IapBranch {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;
	
	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;
	
	@Column(name = "BRANCH_NAME", length = 50)
	private String branchName;
	
	@Column(name = "DIVISION_NAME", length = 50)
	private String divisionName;
	
	@Column(name = "GROUP_NAME", length = 50)
	private String groupName;
	
	@Column(name = "CONTROL_OPERATIONS", length = 4000)
	private String controlOperations;
	
	@Lob
	@Column(name = "SPECIAL_INSTRUCTIONS")
	private String specialInstructions;
	
	@Column(name = "STAGING_AREA", length = 4000)
	private String stagingArea;
	
	@Lob
	@Column(name = "WORK_ASSIGNMENT")
	private String workAssignment;
	
	@Column(name = "PREPARED_BY", length = 50)
	private String preparedBy;
	
	@Column(name = "PREPARED_BY_POSITION", length = 50)
	private String preparedByPosition;

	@Column(name = "APPROVED_BY", length = 50)
	private String approvedBy;
	
	@Column(name = "PREPARED_DATE")
	private Date preparedDate;

	@Column(name = "IS_FORM_204_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm204Locked;

	
	@OneToMany(targetEntity=IapBranchCommSummaryImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapBranch")
    @OrderBy("positionNum")
	private Collection<IapBranchCommSummary> iapBranchCommSummaries; // = new ArrayList<IapBranchCommSummary>();
	
	@OneToMany(targetEntity=IapBranchPersonnelImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapBranch")
    @OrderBy("positionNum")
	private Collection<IapBranchPersonnel> iapBranchPersonnels; //= new ArrayList<IapBranchPersonnel>();
	
	@OneToMany(targetEntity=IapBranchRscAssignImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapBranch")
	@OrderBy("positionNum")
	private Collection<IapBranchRscAssign> iapBranchRscAssigns; // = new ArrayList<IapBranchRscAssign>();
	
	
	/** 
	 * Default constructor 
	 */
	public IapBranchImpl() {
		super();
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
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param controlOperations the controlOperations to set
	 */
	public void setControlOperations(String controlOperations) {
		this.controlOperations = controlOperations;
	}

	/**
	 * @return the controlOperations
	 */
	public String getControlOperations() {
		return controlOperations;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param stagingArea the stagingArea to set
	 */
	public void setStagingArea(String stagingArea) {
		this.stagingArea = stagingArea;
	}

	/**
	 * @return the stagingArea
	 */
	public String getStagingArea() {
		return stagingArea;
	}

	/**
	 * @param workAssignment the workAssignment to set
	 */
	public void setWorkAssignment(String workAssignment) {
		this.workAssignment = workAssignment;
	}

	/**
	 * @return the workAssignment
	 */
	public String getWorkAssignment() {
		return workAssignment;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param iapBranchCommSummaries the iapBranchCommSummaries to set
	 */
	public void setIapBranchCommSummaries(
			Collection<IapBranchCommSummary> iapBranchCommSummaries) {
		this.iapBranchCommSummaries = iapBranchCommSummaries;
	}

	/**
	 * @return the iapBranchCommSummaries
	 */
	public Collection<IapBranchCommSummary> getIapBranchCommSummaries() {
		return iapBranchCommSummaries;
	}

	/**
	 * @param iapBranchPersonnels the iapBranchPersonnels to set
	 */
	public void setIapBranchPersonnels(Collection<IapBranchPersonnel> iapBranchPersonnels) {
		this.iapBranchPersonnels = iapBranchPersonnels;
	}

	/**
	 * @return the iapBranchPersonnels
	 */
	public Collection<IapBranchPersonnel> getIapBranchPersonnels() {
		return iapBranchPersonnels;
	}

	/**
	 * @param iapBranchRscAssigns the iapBranchRscAssigns to set
	 */
	public void setIapBranchRscAssigns(Collection<IapBranchRscAssign> iapBranchRscAssigns) {
		this.iapBranchRscAssigns = iapBranchRscAssigns;
	}

	/**
	 * @return the iapBranchRscAssigns
	 */
	public Collection<IapBranchRscAssign> getIapBranchRscAssigns() {
		return iapBranchRscAssigns;
	}

	/**
	 * @return the isForm204Locked
	 */
	public StringBooleanEnum getIsForm204Locked() {
		return isForm204Locked;
	}

	/**
	 * @param isForm204Locked the isForm204Locked to set
	 */
	public void setIsForm204Locked(StringBooleanEnum isForm204Locked) {
		this.isForm204Locked = isForm204Locked;
	}

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan() {
		return iapPlan;
	}

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan) {
		this.iapPlan = iapPlan;
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
	
}
