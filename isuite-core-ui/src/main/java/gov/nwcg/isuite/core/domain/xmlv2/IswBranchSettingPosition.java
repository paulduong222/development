package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;


@XmlTransferTable(name = "IswBranchSettingPosition", table = "isw_branch_setting_position")
public class IswBranchSettingPosition {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_BRANCH_SETTING_POSITION", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "Pos", sqlname = "POSITION", type = "STRING")
	private String pos;

	/*
	@XmlTransferField(name = "KdTI", alias="kdti", type="STRING"
						, lookupname="TO", sourcename="KdId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kdTI;

	@XmlTransferField(name = "KdId", sqlname = "KIND_ID", type = "LONG"
						, derived = true, derivedfield = "KdTI")
	private Long kdId;
	*/
	
	@XmlTransferField(name = "BrSetId", sqlname = "BRANCH_SETTING_ID", type = "LONG", updateable=false)
	private Long brSetId;

	/**
	 * Default constructor
	 */
	public IswBranchSettingPosition() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Long getBrSetId() {
		return brSetId;
	}

	public void setBrSetId(Long brSetId) {
		this.brSetId = brSetId;
	}

}
