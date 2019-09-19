package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;

public class IapAreaLocationCapabilityData extends DialogueData {

	private IapAreaLocationCapabilityVo iapAreaLocationCapabilityVo;
	private Collection<IapAreaLocationCapabilityVo> iapAreaLocationCapabilityVos;

	public IapAreaLocationCapabilityVo getIapAreaLocationCapabilityVo() {
		return iapAreaLocationCapabilityVo;
	}

	public void setIapAreaLocationCapabilityVo(IapAreaLocationCapabilityVo iapAreaLocationCapabilityVo) {
		this.iapAreaLocationCapabilityVo = iapAreaLocationCapabilityVo;
	}
	
	public Collection<IapAreaLocationCapabilityVo> getIapAreaLocationCapabilityVos() {
		return iapAreaLocationCapabilityVos;
	}
	
	public void setIapAreaLocationCapabilityVos(Collection<IapAreaLocationCapabilityVo> iapAreaLocationCapabilityVos) {
		this.iapAreaLocationCapabilityVos = iapAreaLocationCapabilityVos;
	}
}
