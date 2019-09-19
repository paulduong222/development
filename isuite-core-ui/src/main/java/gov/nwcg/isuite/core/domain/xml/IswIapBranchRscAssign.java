package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapBranchRscAssign", table = "isw_iap_branch_rsc_assign")
public class IswIapBranchRscAssign {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_RSC_ASSIGN", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapBranchTransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapBranchId"
		, disjoined=true, disjoinedtable="isw_iap_branch", disjoinedfield="transferable_identity",disjoinedsource="iap_branch_id")
	private String iapBranchTransferableIdentity;

	@XmlTransferField(name = "IapBranchId", sqlname="IAP_BRANCH_ID", type="LONG"
		,derived=true,derivedfield="IapBranchTransferableIdentity")
	private Long iapBranchId;
	
	@XmlTransferField(name = "ResourceName", sqlname="RESOURCE_NAME", type="STRING")
	private String resourceName;
	
	@XmlTransferField(name = "LeaderName", sqlname="LEADER_NAME", type="STRING")
	private String leaderName;
	
	@XmlTransferField(name = "NbrOfPersonnel", sqlname="NBR_OF_PERSONNEL", type="INTEGER")
	private Integer nbrOfPersonnel;
	
	@XmlTransferField(name = "Transportation", sqlname="TRANSPORTATION", type="STRING")
	private String transportation;
	
	@XmlTransferField(name = "DropOffPoint", sqlname="DROP_OFF_POINT", type="STRING")
	private String dropOffPoint;
	
	@XmlTransferField(name = "DropOffTime", sqlname="DROP_OFF_TIME", type="STRING")
	private String dropOffTime;
	
	@XmlTransferField(name = "PickUpPoint", sqlname="PICK_UP_POINT", type="STRING")
	private String pickUpPoint;
	
	@XmlTransferField(name = "PickUpTime", sqlname="PICK_UP_TIME", type="STRING")
	private String pickUpTime;
	
	@XmlTransferField(name = "ContactInfo", sqlname="CONTACT_INFO", type="STRING")
	private String contactInfo;
	
	@XmlTransferField(name = "AdditionalInfo", sqlname="ADDITIONAL_INFO", type="STRING")
	private String additionalInfo;
	
	@XmlTransferField(name = "LastDayToWorkDate", sqlname="LAST_DAY_TO_WORK_DATE", type="DATE")
	private Date lastDayToWorkDate;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchRscAssign() {
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
	 * @return the iapBranchTransferableIdentity
	 */
	public String getIapBranchTransferableIdentity() {
		return iapBranchTransferableIdentity;
	}

	/**
	 * @param iapBranchTransferableIdentity the iapBranchTransferableIdentity to set
	 */
	public void setIapBranchTransferableIdentity(
			String iapBranchTransferableIdentity) {
		this.iapBranchTransferableIdentity = iapBranchTransferableIdentity;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the nbrOfPersonnel
	 */
	public Integer getNbrOfPersonnel() {
		return nbrOfPersonnel;
	}

	/**
	 * @param nbrOfPersonnel the nbrOfPersonnel to set
	 */
	public void setNbrOfPersonnel(Integer nbrOfPersonnel) {
		this.nbrOfPersonnel = nbrOfPersonnel;
	}

	/**
	 * @return the transportation
	 */
	public String getTransportation() {
		return transportation;
	}

	/**
	 * @param transportation the transportation to set
	 */
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	/**
	 * @return the dropOffPoint
	 */
	public String getDropOffPoint() {
		return dropOffPoint;
	}

	/**
	 * @param dropOffPoint the dropOffPoint to set
	 */
	public void setDropOffPoint(String dropOffPoint) {
		this.dropOffPoint = dropOffPoint;
	}

	/**
	 * @return the dropOffTime
	 */
	public String getDropOffTime() {
		return dropOffTime;
	}

	/**
	 * @param dropOffTime the dropOffTime to set
	 */
	public void setDropOffTime(String dropOffTime) {
		this.dropOffTime = dropOffTime;
	}

	/**
	 * @return the pickUpPoint
	 */
	public String getPickUpPoint() {
		return pickUpPoint;
	}

	/**
	 * @param pickUpPoint the pickUpPoint to set
	 */
	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	/**
	 * @return the pickUpTime
	 */
	public String getPickUpTime() {
		return pickUpTime;
	}

	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	/**
	 * @return the contactInfo
	 */
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
	public String getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(String isBlankLine) {
		this.isBlankLine = isBlankLine;
	}


}
