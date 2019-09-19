package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.KindVo;

public class KindData extends DialogueData {

	private KindVo kindVo;
	private Collection<KindVo> kindVos;
	
	public KindVo getKindVo() {
		return kindVo;
	}
	
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}
	
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}
	
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}
}
