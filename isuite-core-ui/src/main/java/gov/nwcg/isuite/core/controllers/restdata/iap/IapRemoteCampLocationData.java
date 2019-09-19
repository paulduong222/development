package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;

public class IapRemoteCampLocationData extends DialogueData {

	private IapRemoteCampLocationsVo iapRemoteCampLocationsVo;
	private Collection<IapRemoteCampLocationsVo> iapRemoteCampLocationsVos;

	public IapRemoteCampLocationsVo getIapRemoteCampLocationsVo() {
		return iapRemoteCampLocationsVo;
	}

	public void setIapRemoteCampLocationsVo(IapRemoteCampLocationsVo iapRemoteCampLocationsVo) {
		this.iapRemoteCampLocationsVo = iapRemoteCampLocationsVo;
	}
	
	public Collection<IapRemoteCampLocationsVo> getIapRemoteCampLocationsVos() {
		return iapRemoteCampLocationsVos;
	}
	
	public void setIapRemoteCampLocationsVos(Collection<IapRemoteCampLocationsVo> iapRemoteCampLocationsVos) {
		this.iapRemoteCampLocationsVos = iapRemoteCampLocationsVos;
	}
}
