package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;

public class IapBranchRscAssignData extends DialogueData {

	private IapBranchRscAssignVo iapBranchRscAssignVo;
	private Collection<IapBranchRscAssignVo> iapBranchRscAssignVos;

	public IapBranchRscAssignVo getIapBranchRscAssignVo() {
		return iapBranchRscAssignVo;
	}

	public void setIapBranchRscAssignVo(IapBranchRscAssignVo iapBranchRscAssignVo) {
		this.iapBranchRscAssignVo = iapBranchRscAssignVo;
	}
	
	public Collection<IapBranchRscAssignVo> getIapBranchRscAssignVos() {
		return iapBranchRscAssignVos;
	}
	
	public void setIapBranchRscAssignVos(Collection<IapBranchRscAssignVo> iapBranchRscAssignVos) {
		this.iapBranchRscAssignVos = iapBranchRscAssignVos;
	}
}
