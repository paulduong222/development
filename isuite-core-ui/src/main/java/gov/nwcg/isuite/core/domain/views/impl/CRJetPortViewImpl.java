package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRJetPortView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_jetport")
public class CRJetPortViewImpl implements CRJetPortView{

	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false)
	private Long incidentGroupId;
	
	@Id
	@Column(name = "JETPORT_CODE", insertable = false, updatable = false)
	private String jetportCode;

	@Column(name = "JETPORT_DESCRIPTION", insertable = false, updatable = false)
	private String jetportDescription;

	@Column(name = "IS_STANDARD", insertable = false, updatable = false)
	private String standard;

	@Column(name = "STATE_NAME", insertable = false, updatable = false)
	private String stateName;

	@Column(name = "STATE_CODE", insertable = false, updatable = false)
	private String stateCode;
	
	public CRJetPortViewImpl() {
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

	public String getJetportCode() {
		return jetportCode;
	}

	public void setJetportCode(String jetportCode) {
		this.jetportCode = jetportCode;
	}

	public String getJetportDescription() {
		return jetportDescription;
	}

	public void setJetportDescription(String jetportDescription) {
		this.jetportDescription = jetportDescription;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	
	
}
