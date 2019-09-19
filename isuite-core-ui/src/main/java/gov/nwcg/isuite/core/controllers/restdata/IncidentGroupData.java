package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupConflictVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;

public class IncidentGroupData extends DialogueData {

	private IncidentGroupVo incidentGroupVo;
	private Collection<IncidentGridVo> incidentGridVos;
	private IncidentGroupConflictVo incidentGroupConflictVo;

	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	public Collection<IncidentGridVo> getIncidentGridVos() {
		return incidentGridVos;
	}
	
	public void setIncidentGridVos(Collection<IncidentGridVo> incidentGridVos) {
		this.incidentGridVos = incidentGridVos;
	}
	
	public IncidentGroupConflictVo getIncidentGroupConflictVo() {
		return incidentGroupConflictVo;
	}
	
	public void setIncidentGroupConflictVo(IncidentGroupConflictVo incidentGroupConflictVo) {
		this.incidentGroupConflictVo = incidentGroupConflictVo;
	}
}
