package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapHospitalVo;

public class IapHospitalData extends DialogueData {

	private IapHospitalVo iapHospitalVo;
	private Collection<IapHospitalVo> iapHospitalVos;

	public IapHospitalVo getIapHospitalVo() {
		return iapHospitalVo;
	}

	public void setIapHospitalVo(IapHospitalVo iapHospitalVo) {
		this.iapHospitalVo = iapHospitalVo;
	}
	
	public Collection<IapHospitalVo> getIapHospitalVos() {
		return iapHospitalVos;
	}
	
	public void setIapHospitalVos(Collection<IapHospitalVo> iapHospitalVos) {
		this.iapHospitalVos = iapHospitalVos;
	}
}
