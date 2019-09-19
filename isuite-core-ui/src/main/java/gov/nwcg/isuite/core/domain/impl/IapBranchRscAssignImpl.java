package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

/**
 * IapBranchRscAssign entity.
 */
@Entity
@Table(name = "isw_iap_branch_rsc_assign")
@SequenceGenerator(name="SEQ_IAP_BRANCH_RSC_ASSIGN", sequenceName="SEQ_IAP_BRANCH_RSC_ASSIGN")
public class IapBranchRscAssignImpl extends PersistableImpl implements IapBranchRscAssign {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH_RSC_ASSIGN")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapBranchImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_BRANCH_ID", nullable = false)
	private IapBranch iapBranch;
	
	@Column(name = "IAP_BRANCH_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapBranchId;
	
	@Column(name = "RESOURCE_NAME", nullable = true, length = 200)
	private String resourceName;
	
	@Column(name = "LEADER_NAME", length = 50)
	private String leaderName;
	
	@Column(name = "NBR_OF_PERSONNEL")
	private Integer nbrOfPersonnel;
	
	@Column(name = "TRANSPORTATION")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum transportation;
	
	@Column(name = "DROP_OFF_POINT", length = 50)
	private String dropOffPoint;
	
	@Column(name = "DROP_OFF_TIME", length = 20)
	private String dropOffTime;
	
	@Column(name = "PICK_UP_POINT", length = 50)
	private String pickUpPoint;
	
	@Column(name = "PICK_UP_TIME", length = 20)
	private String pickUpTime;
	
	@Column(name = "CONTACT_INFO", length = 200)
	private String contactInfo;
	
	@Column(name = "ADDITIONAL_INFO", length = 200)
	private String additionalInfo;
	
	@Column(name = "LAST_DAY_TO_WORK_DATE")
	private Date lastDayToWorkDate;
	
	@Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable = false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	   
	@Column(name="RESOURCE_ID", nullable=true, unique=false)
	private Long resourceId;

	/** 
	 * Default constructor 
	 */
	public IapBranchRscAssignImpl() {
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
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch) {
		this.iapBranch = iapBranch;
	}

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch() {
		return iapBranch;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param nbrOfPersonnel the nbrOfPersonnel to set
	 */
	public void setNbrOfPersonnel(Integer nbrOfPersonnel) {
		this.nbrOfPersonnel = nbrOfPersonnel;
	}

	/**
	 * @return the nbrOfPersonnel
	 */
	public Integer getNbrOfPersonnel() {
		return nbrOfPersonnel;
	}

	/**
	 * @param transportation the transportation to set
	 */
	public void setTransportation(StringBooleanEnum transportation) {
		this.transportation = transportation;
	}

	/**
	 * @return the transportation
	 */
	public StringBooleanEnum getTransportation() {
		return transportation;
	}

	/**
	 * @param dropOffPoint the dropOffPoint to set
	 */
	public void setDropOffPoint(String dropOffPoint) {
		this.dropOffPoint = dropOffPoint;
	}

	/**
	 * @return the dropOffPoint
	 */
	public String getDropOffPoint() {
		return dropOffPoint;
	}

	/**
	 * @param dropOffTime the dropOffTime to set
	 */
	public void setDropOffTime(String dropOffTime) {
		this.dropOffTime = dropOffTime;
	}

	/**
	 * @return the dropOffTime
	 */
	public String getDropOffTime() {
		return dropOffTime;
	}

	/**
	 * @param pickUpPoint the pickUpPoint to set
	 */
	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	/**
	 * @return the pickUpPoint
	 */
	public String getPickUpPoint() {
		return pickUpPoint;
	}

	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	/**
	 * @return the pickUpTime
	 */
	public String getPickUpTime() {
		return pickUpTime;
	}

	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @return the contactInfo
	 */
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	
	/**
	 * @return the lastDayToWorkDate
	 */
	public Date getLastDayToWorkDate() {
		return lastDayToWorkDate;
	}

	/**
	 * @param lastDayToWorkDate the lastDayToWorkDate to set
	 */
	public void setLastDayToWorkDate(Date lastDayToWorkDate) {
		this.lastDayToWorkDate = lastDayToWorkDate;
	}
	
	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
