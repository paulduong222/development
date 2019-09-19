package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;

public class IncidentResourceGridData extends DialogueData {

	private IncidentResourceGridVo incidentResourceGridVo;
	private Collection<IncidentResourceGridVo> incidentResourceGridVos;
	
	public IncidentResourceGridVo getIncidentResourceGridVo() {
		return incidentResourceGridVo;
	}
	
	public void setIncidentResourceGridVo(IncidentResourceGridVo incidentResourceGridVo) {
		this.incidentResourceGridVo = incidentResourceGridVo;
	}
	
	public Collection<IncidentResourceGridVo> getIncidentResourceGridVos() {
		return incidentResourceGridVos;
	}
	
	public void setIncidentResourceGridVos(Collection<IncidentResourceGridVo> incidentResourceGridVos) {
		this.incidentResourceGridVos = incidentResourceGridVos;
	}
}
