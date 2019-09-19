package gov.nwcg.isuite.core.domain.views;

public interface CRItemCodeView {

	public Long getIncidentId();

	public void setIncidentId(Long incidentId);

	public Long getIncidentGroupId();

	public void setIncidentGroupId(Long incidentGroupId);

	public String getItemCode();

	public void setItemCode(String itemCode);

	public String getItemDescription();

	public void setItemDescription(String itemDescription);

	public String getSectionCode();

	public void setSectionCode(String sectionCode);

	public String getSit209Code();

	public void setSit209Code(String sit209Code);

	public String getRequestCategoryCode();

	public void setRequestCategoryCode(String requestCategoryCode);

	public String getDirect();

	public void setDirect(String direct);

	public String getCode();

	public void setCode(String code);

	public Integer getUnits();

	public void setUnits(Integer units);

	public Integer getPeople();

	public void setPeople(Integer people);

	public String getCostSubGroupCategory();

	public void setCostSubGroupCategory(String costSubGroupCategory);

	public String getCostGroupCategory();

	public void setCostGroupCategory(String costGroupCategory);

	public String getStandard();

	public void setStandard(String standard);

	public String getLineOverhead();

	public void setLineOverhead(String lineOverhead);

	public String getSubordinate();

	public void setSubordinate(String subordinate);

	public String getStrikeTeamTaskForce();

	public void setStrikeTeamTaskForce(String strikeTeamTaskForce);

}