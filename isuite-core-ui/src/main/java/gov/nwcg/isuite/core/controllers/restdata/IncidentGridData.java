package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentGridVo;

public class IncidentGridData extends DialogueData {

	private IncidentGridVo incidentGridVo;
	private Collection<IncidentGridVo> incidentGridVos;
	
	public IncidentGridVo getIncidentGridVo() {
		return incidentGridVo;
	}
	
	public void setIncidentGridVo(IncidentGridVo incidentGridVo) {
		this.incidentGridVo = incidentGridVo;
	}
	
	public Collection<IncidentGridVo> getIncidentGridVos() {
		return incidentGridVos;
	}
	
	public void setIncidentGridVos(Collection<IncidentGridVo> incidentGridVos) {
		this.incidentGridVos = incidentGridVos;
	}
}
