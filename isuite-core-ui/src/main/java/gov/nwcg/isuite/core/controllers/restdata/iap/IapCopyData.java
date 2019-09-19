package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapCopyVo;

public class IapCopyData extends DialogueData {

	private IapCopyVo iapCopyVo;
	
	public IapCopyVo getIapCopyVo() {
		return iapCopyVo;
	}
	
	public void setIapCopyVo(IapCopyVo iapCopyVo) {
		this.iapCopyVo = iapCopyVo;
	}
}
