package gov.nwcg.isuite.core.controllers.restdata.iap;

import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;

public class IapMedicalAidData extends DialogueData {

	private IapMedicalAidVo iapMedicalAidVo;
	private Collection<IapMedicalAidVo> iapMedicalAidVos;

	public IapMedicalAidVo getIapMedicalAidVo() {
		return iapMedicalAidVo;
	}
	
	public void setIapMedicalAidVo(IapMedicalAidVo iapMedicalAidVo) {
		this.iapMedicalAidVo = iapMedicalAidVo;
	}
	
	public Collection<IapMedicalAidVo> getIapMedicalAidVos() {
		return iapMedicalAidVos;
	}
	
	public void setIapMedicalAidVos(Collection<IapMedicalAidVo> iapMedicalAidVos) {
		this.iapMedicalAidVos = iapMedicalAidVos;
	}
}
