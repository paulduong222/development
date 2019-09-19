package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;

public class IapBranchPersonnelData extends DialogueData {

	private IapBranchPersonnelVo iapBranchPersonnelVo;
	private Collection<IapBranchPersonnelVo> iapBranchPersonnelVos;

	public IapBranchPersonnelVo getIapBranchPersonnelVo() {
		return iapBranchPersonnelVo;
	}

	public void setIapBranchPersonnelVo(IapBranchPersonnelVo iapBranchPersonnelVo) {
		this.iapBranchPersonnelVo = iapBranchPersonnelVo;
	}
	
	public Collection<IapBranchPersonnelVo> getIapBranchPersonnelVos() {
		return iapBranchPersonnelVos;
	}
	
	public void setIapBranchPersonnelVos(Collection<IapBranchPersonnelVo> iapBranchPersonnelVos) {
		this.iapBranchPersonnelVos = iapBranchPersonnelVos;
	}
}
