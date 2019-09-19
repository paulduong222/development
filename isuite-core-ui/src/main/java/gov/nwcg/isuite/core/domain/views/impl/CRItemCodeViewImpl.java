package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRItemCodeView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_item_code")
public class CRItemCodeViewImpl implements CRItemCodeView{

	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false)
	private Long incidentGroupId;
	
	@Id
	@Column(name = "ITEM_CODE", insertable = false, updatable = false)
	private String itemCode;

	@Column(name = "ITEM_DESCRIPTION", insertable = false, updatable = false)
	private String itemDescription;

	@Column(name = "SECTION_CODE", insertable = false, updatable = false)
	private String sectionCode;

	@Column(name = "SIT_209_CODE", insertable = false, updatable = false)
	private String sit209Code;

	@Column(name = "REQUEST_CATEGORY_CODE", insertable = false, updatable = false)
	private String requestCategoryCode;

	@Column(name = "IS_DIRECT", insertable = false, updatable = false)
	private String direct ;

	@Column(name = "DAILY_FORM_CODE", insertable = false, updatable = false)
	private String code;

	@Column(name = "UNITS", insertable = false, updatable = false)
	private Integer units;

	@Column(name = "PEOPLE", insertable = false, updatable = false)
	private Integer people;

	@Column(name = "COST_SUB_GROUP_CATEGORY", insertable = false, updatable = false)
	private String costSubGroupCategory;

	@Column(name = "COST_GROUP_CATEGORY", insertable = false, updatable = false)
	private String costGroupCategory;

	@Column(name = "IS_STANDARD", insertable = false, updatable = false)
	private String standard;

	@Column(name = "IS_LINE_OVERHEAD", insertable = false, updatable = false)
	private String lineOverhead;

	@Column(name = "IS_SUBORDINATE", insertable = false, updatable = false)
	private String subordinate;

	@Column(name = "IS_STRIKETEAM_TASKFORCE", insertable = false, updatable = false)
	private String strikeTeamTaskForce;
	
	public CRItemCodeViewImpl() {
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSit209Code() {
		return sit209Code;
	}

	public void setSit209Code(String sit209Code) {
		this.sit209Code = sit209Code;
	}

	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}

	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public String getCostSubGroupCategory() {
		return costSubGroupCategory;
	}

	public void setCostSubGroupCategory(String costSubGroupCategory) {
		this.costSubGroupCategory = costSubGroupCategory;
	}

	public String getCostGroupCategory() {
		return costGroupCategory;
	}

	public void setCostGroupCategory(String costGroupCategory) {
		this.costGroupCategory = costGroupCategory;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getLineOverhead() {
		return lineOverhead;
	}

	public void setLineOverhead(String lineOverhead) {
		this.lineOverhead = lineOverhead;
	}

	public String getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

	public String getStrikeTeamTaskForce() {
		return strikeTeamTaskForce;
	}

	public void setStrikeTeamTaskForce(String strikeTeamTaskForce) {
		this.strikeTeamTaskForce = strikeTeamTaskForce;
	}
	
}
