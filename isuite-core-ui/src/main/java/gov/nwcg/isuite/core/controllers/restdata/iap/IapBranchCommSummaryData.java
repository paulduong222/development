package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;

public class IapBranchCommSummaryData extends DialogueData {

	private IapBranchCommSummaryVo iapBranchCommSummaryVo;
	private Collection<IapBranchCommSummaryVo> iapBranchCommSummaryVos;

	public IapBranchCommSummaryVo getIapBranchCommSummaryVo() {
		return iapBranchCommSummaryVo;
	}

	public void setIapBranchCommSummaryVo(IapBranchCommSummaryVo iapBranchCommSummaryVo) {
		this.iapBranchCommSummaryVo = iapBranchCommSummaryVo;
	}
	
	public Collection<IapBranchCommSummaryVo> getIapBranchCommSummaryVos() {
		return iapBranchCommSummaryVos;
	}
	
	public void setIapBranchCommSummaryVos(Collection<IapBranchCommSummaryVo> iapBranchCommSummaryVos) {
		this.iapBranchCommSummaryVos = iapBranchCommSummaryVos;
	}
}
