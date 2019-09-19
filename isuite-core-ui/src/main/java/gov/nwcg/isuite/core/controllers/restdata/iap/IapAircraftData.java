package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapAircraftVo;

public class IapAircraftData extends DialogueData {

	private IapAircraftVo iapAircraftVo;
	private Collection<IapAircraftVo> iapAircraftVos;

	public IapAircraftVo getIapAircraftVo() {
		return iapAircraftVo;
	}

	public void setIapAircraftVo(IapAircraftVo iapAircraftVo) {
		this.iapAircraftVo = iapAircraftVo;
	}
	
	public Collection<IapAircraftVo> getIapAircraftVos() {
		return iapAircraftVos;
	}
	
	public void setIapAircraftVos(Collection<IapAircraftVo> iapAircraftVos) {
		this.iapAircraftVos = iapAircraftVos;
	}
	
}
