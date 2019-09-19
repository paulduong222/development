package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapBranchPersonnelRes", table = "isw_iap_branch_personnel_res")
public class IswIapBranchPersonnelRes {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_PERSONNEL_RES", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapBranchPersResTransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapBranchPersonnelId"
		, disjoined=true, disjoinedtable="isw_iap_branch_personnel", disjoinedfield="transferable_identity",disjoinedsource="iap_branch_personnel_id")
	private String iapBranchPersResTransferableIdentity;

	@XmlTransferField(name = "IapBranchPersonnelId", sqlname="IAP_BRANCH_PERSONNEL_ID", type="LONG"
		,derived=true,derivedfield="IapBranchPersResTransferableIdentity")
	private Long iapBranchPersonnelId;
	
	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsTrainee", sqlname="IS_TRAINEE", type="STRING")
	private String isTrainee;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchPersonnelRes() {
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
	 * @return the iapBranchPersResTransferableIdentity
	 */
	public String getIapBranchPersResTransferableIdentity() {
		return iapBranchPersResTransferableIdentity;
	}

	/**
	 * @param iapBranchPersResTransferableIdentity the iapBranchPersResTransferableIdentity to set
	 */
	public void setIapBranchPersResTransferableIdentity(
			String iapBranchPersResTransferableIdentity) {
		this.iapBranchPersResTransferableIdentity = iapBranchPersResTransferableIdentity;
	}

	/**
	 * @return the iapBranchPersonnelId
	 */
	public Long getIapBranchPersonnelId() {
		return iapBranchPersonnelId;
	}

	/**
	 * @param iapBranchPersonnelId the iapBranchPersonnelId to set
	 */
	public void setIapBranchPersonnelId(Long iapBranchPersonnelId) {
		this.iapBranchPersonnelId = iapBranchPersonnelId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the isTrainee
	 */
	public String getIsTrainee() {
		return isTrainee;
	}

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(String isTrainee) {
		this.isTrainee = isTrainee;
	}



}
