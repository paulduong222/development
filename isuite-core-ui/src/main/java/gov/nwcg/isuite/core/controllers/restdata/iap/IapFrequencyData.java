package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;

public class IapFrequencyData extends DialogueData {

	private IapFrequencyVo iapFrequencyVo;
	private Collection<IapFrequencyVo> iapFrequencyVos;

	public IapFrequencyVo getIapFrequencyVo() {
		return iapFrequencyVo;
	}
	
	public void setIapFrequencyVo(IapFrequencyVo iapFrequencyVo) {
		this.iapFrequencyVo = iapFrequencyVo;
	}
	
	public Collection<IapFrequencyVo> getIapFrequencyVos() {
		return iapFrequencyVos;
	}
	
	public void setIapFrequencyVos(Collection<IapFrequencyVo> iapFrequencyVos) {
		this.iapFrequencyVos = iapFrequencyVos;
	}
}
