package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;


@XmlTransferTable(name = "IswBranchSettingPosition", table = "isw_branch_setting_position")
public class IswBranchSettingPosition {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_BRANCH_SETTING_POSITION", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Position", sqlname = "POSITION", type = "STRING")
	private String position;

	@XmlTransferField(name = "KindTransferableIdentity", alias="kdti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="KindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname = "KIND_ID", type = "LONG"
						, derived = true, derivedfield = "KindTransferableIdentity")
	private Long kindId;

	@XmlTransferField(name = "BranchSettingId", sqlname = "BRANCH_SETTING_ID", type = "LONG", updateable=false)
	private Long branchSettingId;

	/**
	 * Default constructor
	 */
	public IswBranchSettingPosition() {
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
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId() {
		return branchSettingId;
	}

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId) {
		this.branchSettingId = branchSettingId;
	}


}
