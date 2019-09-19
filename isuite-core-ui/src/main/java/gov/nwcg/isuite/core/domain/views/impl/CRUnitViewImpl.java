package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRUnitView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_unit")
public class CRUnitViewImpl implements CRUnitView {

	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false)
	private Long incidentGroupId;
	
	@Id
	@Column(name = "UNIT_CODE")
	private String unitCode;

	@Column(name = "UNIT_CODE_DESCRIPTION")
	private String unitCodeDescription;

	@Column(name = "IS_STANDARD")
	private String standard;

	public CRUnitViewImpl() {
	}
	
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitCodeDescription() {
		return unitCodeDescription;
	}

	public void setUnitCodeDescription(String unitCodeDescription) {
		this.unitCodeDescription = unitCodeDescription;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}
