package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapPrintJobVo;

public class IapPrintJobData extends DialogueData {

	private IapPrintJobVo iapPrintJobVo;
	
	public IapPrintJobVo getIapPrintJobVo() {
		return iapPrintJobVo;
	}
	
	public void setIapPrintJobVo(IapPrintJobVo iapPrintJobVo) {
		this.iapPrintJobVo = iapPrintJobVo;
	}
}
