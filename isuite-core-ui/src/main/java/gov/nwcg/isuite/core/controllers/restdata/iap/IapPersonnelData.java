package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;

public class IapPersonnelData extends DialogueData {

	private IapPersonnelVo iapPersonnelVo;
	private Collection<IapPersonnelVo> iapPersonnelVos;

	public IapPersonnelVo getIapPersonnelVo() {
		return iapPersonnelVo;
	}

	public void setIapPersonnelVo(IapPersonnelVo iapPersonnelVo) {
		this.iapPersonnelVo = iapPersonnelVo;
	}
	
	public Collection<IapPersonnelVo> getIapPersonnelVos() {
		return iapPersonnelVos;
	}
	
	public void setIapPersonnelVos(Collection<IapPersonnelVo> iapPersonnelVos) {
		this.iapPersonnelVos = iapPersonnelVos;
	}
}
