package gov.nwcg.isuite.core.domain.views;

public interface CRUnitView {

	public Long getIncidentId();

	public void setIncidentId(Long incidentId);

	public Long getIncidentGroupId();

	public void setIncidentGroupId(Long incidentGroupId);

	public String getUnitCode();

	public void setUnitCode(String unitCode);

	public String getUnitCodeDescription();

	public void setUnitCodeDescription(String unitCodeDescription);

	public String getStandard();

	public void setStandard(String standard);

}