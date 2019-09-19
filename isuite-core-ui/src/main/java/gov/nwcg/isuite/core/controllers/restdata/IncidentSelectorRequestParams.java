package gov.nwcg.isuite.core.controllers.restdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nwcg.isuite.core.service.IncidentSelectorService;

public class IncidentSelectorRequestParams {

	private static final Logger LOG = LoggerFactory.getLogger(IncidentSelectorRequestParams.class);

	private Long incidentId;
	private Long incidentGroupId;
	private Boolean incidentsOnly;
	private Boolean filterExcluded;

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Boolean getIncidentsOnly() {
		return this.incidentsOnly;
	}

	public void setIncidentsOnly(Boolean incidentsOnly) {
		this.incidentsOnly = incidentsOnly;
	}

	public Boolean getFilterExcluded() {
		return this.filterExcluded;
	}

	public void setFilterExcluded(Boolean filterExcluded) {
		this.filterExcluded = filterExcluded;
	}
	
	public void initializeService(IncidentSelectorService service) {
		if (service != null) {
			if (this.incidentId != null) service.setIncidentId(this.incidentId);
			if (this.incidentGroupId != null) service.setIncidentGroupId(this.incidentGroupId);
			// NOT worrying about defaults here in the filter.  It'll be handled by the defaults in the service.
			if (this.incidentsOnly != null) service.setIncidentsOnly(this.incidentsOnly);
			if (this.filterExcluded != null) service.setFilterExcluded(this.filterExcluded);
		} else {
			LOG.warn("IncidentSelectorService provided is null.  Cannot initialize filters properly.");
		}
	}

}
