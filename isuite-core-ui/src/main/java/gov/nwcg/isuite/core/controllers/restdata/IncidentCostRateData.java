package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.IncidentCostRateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;

public class IncidentCostRateData extends DialogueData {

	private IncidentCostRateKindVo incidentCostRateKindVo;
	private IncidentCostRateOvhdVo incidentCostRateOvhdVo;
	private IncidentCostRateStateVo incidentCostRateStateVo;
	private IncidentCostRateStateKindVo incidentCostRateStateKindVo;
	private String category;
	
	public IncidentCostRateKindVo getIncidentCostRateKindVo() {
		return incidentCostRateKindVo;
	}
	
	public void setIncidentCostRateKindVo(IncidentCostRateKindVo incidentCostRateKindVo) {
		this.incidentCostRateKindVo = incidentCostRateKindVo;
	}
	
	public IncidentCostRateOvhdVo getIncidentCostRateOvhdVo() {
		return incidentCostRateOvhdVo;
	}
	
	public void setIncidentCostRateOvhdVo(IncidentCostRateOvhdVo incidentCostRateOvhdVo) {
		this.incidentCostRateOvhdVo = incidentCostRateOvhdVo;
	}
	
	public IncidentCostRateStateVo getIncidentCostRateStateVo() {
		return incidentCostRateStateVo;
	}
	
	public void setIncidentCostRateStateVo(IncidentCostRateStateVo incidentCostRateStateVo) {
		this.incidentCostRateStateVo = incidentCostRateStateVo;
	}
	
	public IncidentCostRateStateKindVo getIncidentCostRateStateKindVo() {
		return incidentCostRateStateKindVo;
	}
	
	public void setIncidentCostRateStateKindVo(IncidentCostRateStateKindVo incidentCostRateStateKindVo) {
		this.incidentCostRateStateKindVo = incidentCostRateStateKindVo;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
