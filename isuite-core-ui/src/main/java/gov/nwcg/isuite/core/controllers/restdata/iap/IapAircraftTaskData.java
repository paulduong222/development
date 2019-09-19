package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;

public class IapAircraftTaskData extends DialogueData {

	private IapAircraftTaskVo iapAircraftTaskVo;
	private Collection<IapAircraftTaskVo> iapAircraftTaskVos;

	public IapAircraftTaskVo getIapAircraftTaskVo() {
		return iapAircraftTaskVo;
	}

	public void setIapAircraftTaskVo(IapAircraftTaskVo iapAircraftTaskVo) {
		this.iapAircraftTaskVo = iapAircraftTaskVo;
	}
	
	public Collection<IapAircraftTaskVo> getIapAircraftTaskVos() {
		return iapAircraftTaskVos;
	}
	
	public void setIapAircraftTaskVos(Collection<IapAircraftTaskVo> iapAircraftTaskVos) {
		this.iapAircraftTaskVos = iapAircraftTaskVos;
	}

}
