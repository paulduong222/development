package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapPersonnelRes", table = "isw_iap_personnel_res")
public class IswIapPersonnelRes {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PERSONNEL_RES", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPersResTransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPersonnelId"
		, disjoined=true, disjoinedtable="isw_iap_personnel", disjoinedfield="transferable_identity",disjoinedsource="iap_personnel_id")
	private String iapPersResTransferableIdentity;

	@XmlTransferField(name = "IapPersonnelId", sqlname="IAP_PERSONNEL_ID", type="LONG"
		,derived=true,derivedfield="IapPersResTransferableIdentity")
	private Long iapPersonnelId;
	
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
	public IswIapPersonnelRes() {
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
	 * @return the iapPersResTransferableIdentity
	 */
	public String getIapPersResTransferableIdentity() {
		return iapPersResTransferableIdentity;
	}

	/**
	 * @param iapPersResTransferableIdentity the iapPersResTransferableIdentity to set
	 */
	public void setIapPersResTransferableIdentity(
			String iapPersResTransferableIdentity) {
		this.iapPersResTransferableIdentity = iapPersResTransferableIdentity;
	}

	/**
	 * @return the iapPersonnelId
	 */
	public Long getIapPersonnelId() {
		return iapPersonnelId;
	}

	/**
	 * @param iapPersonnelId the iapPersonnelId to set
	 */
	public void setIapPersonnelId(Long iapPersonnelId) {
		this.iapPersonnelId = iapPersonnelId;
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
