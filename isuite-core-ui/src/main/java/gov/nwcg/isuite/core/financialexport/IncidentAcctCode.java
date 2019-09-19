package gov.nwcg.isuite.core.financialexport;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentAcctCode {
	private Long incidentId;
	private String incidentName;
	private String incidentNumber;
	private Boolean hasData=false;
	private Collection<String> incidentAccountCodes = new ArrayList<String>();
	
	public Long getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentName() {
		return incidentName;
	}
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	public Collection<String> getIncidentAccountCodes() {
		return incidentAccountCodes;
	}
	public void setIncidentAccountCodes(Collection<String> incidentAccountCodes) {
		this.incidentAccountCodes = incidentAccountCodes;
	}
	public String getIncidentNumber() {
		return incidentNumber;
	}
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	public Boolean getHasData() {
		return hasData;
	}
	public void setHasData(Boolean hasData) {
		this.hasData = hasData;
	}

	
}
