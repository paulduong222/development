package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswBranchSetting", table = "isw_branch_setting")
public class IswBranchSetting {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_BRANCH_SETTING", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "IncGrpId", sqlname = "INCIDENT_GROUP_ID", type = "LONG",updateable=false)
	private Long incGrpId;

	@XmlTransferField(name = "IncId", sqlname = "INCIDENT_ID", type = "LONG",updateable=false)
	private Long incId;

	@XmlTransferField(name = "BrNm", sqlname = "BRANCH_NAME", type = "STRING")
	private String brNm;

	@XmlTransferField(name = "PosNum", sqlname = "POSITION_NUM", type = "INTEGER")
	private Integer posNum;

	/*
	@XmlTransferField(name = "BrSetPos", type = "COMPLEX", target=IswBranchSettingPosition.class
						,lookupname="BrSetId", sourcename="Id"
						, cascade=true)
	private Collection<IswBranchSettingPosition> brSetPoss = new ArrayList<IswBranchSettingPosition>();
	*/
	
	public IswBranchSetting() {
	}


}
