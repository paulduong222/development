package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;

public class IncidentResourceOtherGridData extends DialogueData {

	private IncidentResourceOtherGridVo incidentResourceOtherGridVo;
	private Collection<IncidentResourceOtherGridVo> incidentResourceOtherGridVos;
	
	public IncidentResourceOtherGridVo getIncidentResourceOtherGridVo() {
		return incidentResourceOtherGridVo;
	}
	
	public void setIncidentResourceOtherGridVo(IncidentResourceOtherGridVo incidentResourceOtherGridVo) {
		this.incidentResourceOtherGridVo = incidentResourceOtherGridVo;
	}
	
	public Collection<IncidentResourceOtherGridVo> getIncidentResourceOtherGridVos() {
		return incidentResourceOtherGridVos;
	}
	
	public void setIncidentResourceOtherGridVos(Collection<IncidentResourceOtherGridVo> incidentResourceOtherGridVos) {
		this.incidentResourceOtherGridVos = incidentResourceOtherGridVos;
	}
}
