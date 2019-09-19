package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;

public class RestrictedIncidentUserData extends DialogueData {

	private RestrictedIncidentUserVo restrictedIncidentUserVo;
	private Collection<RestrictedIncidentUserVo> restrictedIncidentUserVos;
	
	public RestrictedIncidentUserVo getRestrictedIncidentUserVo() {
		return restrictedIncidentUserVo;
	}
	
	public void setRestrictedIncidentUserVo(RestrictedIncidentUserVo restrictedIncidentUserVo) {
		this.restrictedIncidentUserVo = restrictedIncidentUserVo;
	}
	
	public Collection<RestrictedIncidentUserVo> getRestrictedIncidentUserVos() {
		return restrictedIncidentUserVos;
	}
	
	public void setRestrictedIncidentUserVos(Collection<RestrictedIncidentUserVo> restrictedIncidentUserVos) {
		this.restrictedIncidentUserVos = restrictedIncidentUserVos;
	}
}
