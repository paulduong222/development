package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;

public class IapPlanPrintOrderData extends DialogueData {

	private IapPlanPrintOrderVo iapPlanPrintOrderVo;
	
	public IapPlanPrintOrderVo getIapPlanPrintOrderVo() {
		return iapPlanPrintOrderVo;
	}
	
	public void setIapPlanPrintOrderVo(IapPlanPrintOrderVo iapPlanPrintOrderVo) {
		this.iapPlanPrintOrderVo = iapPlanPrintOrderVo;
	}
}
