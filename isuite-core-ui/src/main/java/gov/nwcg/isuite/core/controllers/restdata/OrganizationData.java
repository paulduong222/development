package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;

public class OrganizationData extends DialogueData {

	private OrganizationVo organizationVo;
	private IncidentVo incidentVo;

	public OrganizationVo getOrganizationVo() {
		return organizationVo;
	}

	public void setOrganizationVo(OrganizationVo organizationVo) {
		this.organizationVo = organizationVo;
	}

	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

}
