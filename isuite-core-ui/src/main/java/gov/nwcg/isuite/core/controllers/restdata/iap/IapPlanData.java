package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapPlanVo;

public class IapPlanData extends DialogueData {

	private IapPlanVo iapPlanVo;

	public IapPlanVo getIapPlanVo() {
		return iapPlanVo;
	}

	public void setIapPlanVo(IapPlanVo iapPlanVo) {
		this.iapPlanVo = iapPlanVo;
	}
}
