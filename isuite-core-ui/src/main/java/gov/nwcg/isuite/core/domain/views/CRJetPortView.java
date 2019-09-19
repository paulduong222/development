package gov.nwcg.isuite.core.domain.views;

public interface CRJetPortView {

	public Long getIncidentId();

	public void setIncidentId(Long incidentId);

	public Long getIncidentGroupId();

	public void setIncidentGroupId(Long incidentGroupId);

	public String getJetportCode();

	public void setJetportCode(String jetportCode);

	public String getJetportDescription();

	public void setJetportDescription(String jetportDescription);

	public String getStandard();

	public void setStandard(String standard);

	public String getStateName();

	public void setStateName(String stateName);

	public String getStateCode();

	public void setStateCode(String stateCode);

}