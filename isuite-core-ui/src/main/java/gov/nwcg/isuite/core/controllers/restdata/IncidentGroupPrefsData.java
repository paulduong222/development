package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;

public class IncidentGroupPrefsData extends DialogueData {

	private IncidentGroupPrefsVo incidentGroupPrefsVo;
	private Collection<IncidentGroupPrefsVo> incidentGroupPrefsVos;
	
	public IncidentGroupPrefsVo getIncidentGroupPrefsVo() {
		return incidentGroupPrefsVo;
	}
	
	public void setIncidentGroupPrefsVo(IncidentGroupPrefsVo incidentGroupPrefsVo) {
		this.incidentGroupPrefsVo = incidentGroupPrefsVo;
	}
	
	public Collection<IncidentGroupPrefsVo> getIncidentGroupPrefsVos() {
		return incidentGroupPrefsVos;
	}
	
	public void setIncidentGroupPrefsVos(Collection<IncidentGroupPrefsVo> incidentGroupPrefsVos) {
		this.incidentGroupPrefsVos = incidentGroupPrefsVos;
	}
}
