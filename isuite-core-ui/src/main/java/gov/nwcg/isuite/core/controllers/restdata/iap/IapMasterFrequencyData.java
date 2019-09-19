package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;

public class IapMasterFrequencyData extends DialogueData {

	private IapMasterFrequencyVo iapMasterFrequencyVo;
	private Collection<IapMasterFrequencyVo> iapMasterFrequencyVos;

	public IapMasterFrequencyVo getIapMasterFrequencyVo() {
		return iapMasterFrequencyVo;
	}

	public void setIapMasterFrequencyVo(IapMasterFrequencyVo iapMasterFrequencyVo) {
		this.iapMasterFrequencyVo = iapMasterFrequencyVo;
	}

	public Collection<IapMasterFrequencyVo> getIapMasterFrequencyVos() {
		return iapMasterFrequencyVos;
	}

	public void setIapMasterFrequencyVos(Collection<IapMasterFrequencyVo> iapMasterFrequencyVos) {
		this.iapMasterFrequencyVos = iapMasterFrequencyVos;
	}
}
