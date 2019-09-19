package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentPrefsVo;

public class IncidentPrefsData extends DialogueData {

	private IncidentPrefsVo incidentPrefsVo;
	private Collection<IncidentPrefsVo> incidentPrefsVos;

	public IncidentPrefsVo getIncidentPrefsVo() {
		return incidentPrefsVo;
	}

	public void setIncidentPrefsVo(IncidentPrefsVo incidentPrefsVo) {
		this.incidentPrefsVo = incidentPrefsVo;
	}

	public Collection<IncidentPrefsVo> getIncidentPrefsVos() {
		return incidentPrefsVos;
	}

	public void setIncidentPrefsVos(Collection<IncidentPrefsVo> incidentPrefsVos) {
		this.incidentPrefsVos = incidentPrefsVos;
	}
}
