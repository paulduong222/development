package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswBranchSetting", table = "isw_branch_setting")
public class IswBranchSetting {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_BRANCH_SETTING", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname = "INCIDENT_ID", type = "LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "IncidentId", sqlname = "INCIDENT_ID", type = "LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "BranchName", sqlname = "BRANCH_NAME", type = "STRING")
	private String branchName;

	@XmlTransferField(name = "PositionNum", sqlname = "POSITION_NUM", type = "INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "BranchSettingPosition", type = "COMPLEX", target=IswBranchSettingPosition.class
						,lookupname="BranchSettingId", sourcename="Id"
						, cascade=true)
	private Collection<IswBranchSettingPosition> branchSettingPositions = new ArrayList<IswBranchSettingPosition>();

	public IswBranchSetting() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId
	 *            the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId
	 *            the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum
	 *            the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
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
	 * @return the branchSettingPositions
	 */
	public Collection<IswBranchSettingPosition> getBranchSettingPositions() {
		return branchSettingPositions;
	}

	/**
	 * @param branchSettingPositions the branchSettingPositions to set
	 */
	public void setBranchSettingPositions(
			Collection<IswBranchSettingPosition> branchSettingPositions) {
		this.branchSettingPositions = branchSettingPositions;
	}

}
