package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ResourceTimeReferenceDataVo {
	private Collection<SpecialPayVo> specialPayVos = new ArrayList<SpecialPayVo>();
	private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
	public Collection<SpecialPayVo> getSpecialPayVos() {
		return specialPayVos;
	}
	public void setSpecialPayVos(Collection<SpecialPayVo> specialPayVos) {
		this.specialPayVos = specialPayVos;
	}
	public Collection<RateClassRateVo> getRateClassRateVos() {
		return rateClassRateVos;
	}
	public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
		this.rateClassRateVos = rateClassRateVos;
	}

}
