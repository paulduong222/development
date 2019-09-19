package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswlKind", table="iswl_kind")
public class IswlKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description",sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard",sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "Direct", sqlname="IS_DIRECT", type="BOOLEAN")
	private Boolean direct;

	@XmlTransferField(name = "InDirect", sqlname="IS_INDIRECT", type="BOOLEAN")
	private Boolean inDirect;

	@XmlTransferField(name = "Units", sqlname="UNITS", type="INTEGER")
	private Integer units;

	@XmlTransferField(name = "People", sqlname="PEOPLE", type="INTEGER")
	private Integer people;

	@XmlTransferField(name = "LineOverhead", sqlname="IS_LINE_OVERHEAD", type="BOOLEAN")
	private Boolean lineOverhead;

	@XmlTransferField(name = "Subordinate", sqlname="IS_SUBORDINATE", type="BOOLEAN")
	private Boolean subordinate;

	@XmlTransferField(name = "StrikeTeam", sqlname="IS_STRIKE_TEAM", type="BOOLEAN")
	private Boolean strikeTeam;

	@XmlTransferField(name = "Aircraft", sqlname="IS_AIRCRAFT", type="STRING")
	private String aircraft;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Active", sqlname="IS_ACTIVE", type="STRING")
	private String active;

	@XmlTransferField(name = "StandardCost", sqlname="STANDARD_COST", type="BIGDECIMAL")
	private BigDecimal standardCost;

	@XmlTransferField(name = "RequestCategoryTransferableIdentity", alias="reqcatti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="RequestCategoryId"
						, disjoined=true, disjoinedtable="iswl_request_category", disjoinedfield="transferable_identity",disjoinedsource="REQUEST_CATEGORY_ID")
	private String requestCategory;

	@XmlTransferField(name = "RequestCategoryId", sqlname="REQUEST_CATEGORY_ID", type="LONG"
						,derived=true, derivedfield="RequestCategoryTransferableIdentity")
	private Long requestCategoryId;

	@XmlTransferField(name = "DepartmentTransferableIdentity", alias="deptti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="DepartmentId"
						, disjoined=true, disjoinedtable="iswl_department", disjoinedfield="transferable_identity",disjoinedsource="DEPARTMENT_ID")
	private String departmentTransferableIdentity;

	@XmlTransferField(name = "DepartmentId", sqlname="DEPARTMENT_ID", type="LONG"
						,derived=true, derivedfield="DepartmentTransferableIdentity")
	private Long departmentId;

	@XmlTransferField(name = "DailyFormTransferableIdentity", alias="dfti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="DailyFormId"
						, disjoined=true, disjoinedtable="iswl_daily_form", disjoinedfield="transferable_identity",disjoinedsource="DAILY_FORM_ID")
    private String dailyFormTransferableIdentity;

	@XmlTransferField(name = "DailyFormId", sqlname="DAILY_FORM_ID", type="LONG"
					,derived=true, derivedfield="DailyFormTransferableIdentity")
    private Long dailyFormId;
	
	@XmlTransferField(name = "Sit209TransferableIdentity", alias="sitti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="Sit209Id"
						, disjoined=true, disjoinedtable="iswl_sit_209", disjoinedfield="transferable_identity",disjoinedsource="SIT_209_ID")
    private String sit209TransferableIdentity;

	@XmlTransferField(name = "Sit209Id", sqlname="SIT_209_ID", type="LONG"
					,derived=true, derivedfield="Sit209TransferableIdentity")
    private Long sit209Id;

	@XmlTransferField(name = "SubGroupCategoryTransferableIdentity", alias="sbcti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="SubGroupCategoryId"
						, disjoined=true, disjoinedtable="iswl_sub_group_category", disjoinedfield="transferable_identity",disjoinedsource="SUB_GROUP_CATEGORY_ID")
    private String subGroupCategoryTransferableIdentity;

	@XmlTransferField(name = "SubGroupCategoryId", sqlname="SUB_GROUP_CATEGORY_ID", type="LONG"
					,derived=true, derivedfield="SubGroupCategoryTransferableIdentity")
    private Long subGroupCategoryId;
	
	@XmlTransferField(name = "GroupCategoryTransferableIdentity", alias="gcti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="GroupCategoryId"
						, disjoined=true, disjoinedtable="iswl_group_category", disjoinedfield="transferable_identity",disjoinedsource="GROUP_CATEGORY_ID")
    private String groupCategoryTransferableIdentity;

	@XmlTransferField(name = "GroupCategoryId", sqlname="GROUP_CATEGORY_ID", type="LONG"
					,derived=true, derivedfield="GroupCategoryTransferableIdentity")
    private Long groupCategoryId;
	

	public IswlKind() {
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}


	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}


	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}


	/**
	 * @return the direct
	 */
	public Boolean getDirect() {
		return direct;
	}


	/**
	 * @param direct the direct to set
	 */
	public void setDirect(Boolean direct) {
		this.direct = direct;
	}


	/**
	 * @return the inDirect
	 */
	public Boolean getInDirect() {
		return inDirect;
	}


	/**
	 * @param inDirect the inDirect to set
	 */
	public void setInDirect(Boolean inDirect) {
		this.inDirect = inDirect;
	}


	/**
	 * @return the units
	 */
	public Integer getUnits() {
		return units;
	}


	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}


	/**
	 * @return the people
	 */
	public Integer getPeople() {
		return people;
	}


	/**
	 * @param people the people to set
	 */
	public void setPeople(Integer people) {
		this.people = people;
	}


	/**
	 * @return the lineOverhead
	 */
	public Boolean getLineOverhead() {
		return lineOverhead;
	}


	/**
	 * @param lineOverhead the lineOverhead to set
	 */
	public void setLineOverhead(Boolean lineOverhead) {
		this.lineOverhead = lineOverhead;
	}


	/**
	 * @return the subordinate
	 */
	public Boolean getSubordinate() {
		return subordinate;
	}


	/**
	 * @param subordinate the subordinate to set
	 */
	public void setSubordinate(Boolean subordinate) {
		this.subordinate = subordinate;
	}


	/**
	 * @return the strikeTeam
	 */
	public Boolean getStrikeTeam() {
		return strikeTeam;
	}


	/**
	 * @param strikeTeam the strikeTeam to set
	 */
	public void setStrikeTeam(Boolean strikeTeam) {
		this.strikeTeam = strikeTeam;
	}


	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}


	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}


	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}


	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}


	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * @return the standardCost
	 */
	public BigDecimal getStandardCost() {
		return standardCost;
	}


	/**
	 * @param standardCost the standardCost to set
	 */
	public void setStandardCost(BigDecimal standardCost) {
		this.standardCost = standardCost;
	}


	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() {
		return requestCategory;
	}


	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}


	/**
	 * @return the requestCategoryId
	 */
	public Long getRequestCategoryId() {
		return requestCategoryId;
	}


	/**
	 * @param requestCategoryId the requestCategoryId to set
	 */
	public void setRequestCategoryId(Long requestCategoryId) {
		this.requestCategoryId = requestCategoryId;
	}


	/**
	 * @return the departmentTransferableIdentity
	 */
	public String getDepartmentTransferableIdentity() {
		return departmentTransferableIdentity;
	}


	/**
	 * @param departmentTransferableIdentity the departmentTransferableIdentity to set
	 */
	public void setDepartmentTransferableIdentity(
			String departmentTransferableIdentity) {
		this.departmentTransferableIdentity = departmentTransferableIdentity;
	}


	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}


	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	/**
	 * @return the dailyFormTransferableIdentity
	 */
	public String getDailyFormTransferableIdentity() {
		return dailyFormTransferableIdentity;
	}


	/**
	 * @param dailyFormTransferableIdentity the dailyFormTransferableIdentity to set
	 */
	public void setDailyFormTransferableIdentity(
			String dailyFormTransferableIdentity) {
		this.dailyFormTransferableIdentity = dailyFormTransferableIdentity;
	}


	/**
	 * @return the dailyFormId
	 */
	public Long getDailyFormId() {
		return dailyFormId;
	}


	/**
	 * @param dailyFormId the dailyFormId to set
	 */
	public void setDailyFormId(Long dailyFormId) {
		this.dailyFormId = dailyFormId;
	}


	/**
	 * @return the sit209TransferableIdentity
	 */
	public String getSit209TransferableIdentity() {
		return sit209TransferableIdentity;
	}


	/**
	 * @param sit209TransferableIdentity the sit209TransferableIdentity to set
	 */
	public void setSit209TransferableIdentity(String sit209TransferableIdentity) {
		this.sit209TransferableIdentity = sit209TransferableIdentity;
	}


	/**
	 * @return the sit209Id
	 */
	public Long getSit209Id() {
		return sit209Id;
	}


	/**
	 * @param sit209Id the sit209Id to set
	 */
	public void setSit209Id(Long sit209Id) {
		this.sit209Id = sit209Id;
	}


	/**
	 * @return the subGroupCategoryTransferableIdentity
	 */
	public String getSubGroupCategoryTransferableIdentity() {
		return subGroupCategoryTransferableIdentity;
	}


	/**
	 * @param subGroupCategoryTransferableIdentity the subGroupCategoryTransferableIdentity to set
	 */
	public void setSubGroupCategoryTransferableIdentity(
			String subGroupCategoryTransferableIdentity) {
		this.subGroupCategoryTransferableIdentity = subGroupCategoryTransferableIdentity;
	}


	/**
	 * @return the subGroupCategoryId
	 */
	public Long getSubGroupCategoryId() {
		return subGroupCategoryId;
	}


	/**
	 * @param subGroupCategoryId the subGroupCategoryId to set
	 */
	public void setSubGroupCategoryId(Long subGroupCategoryId) {
		this.subGroupCategoryId = subGroupCategoryId;
	}


	/**
	 * @return the groupCategoryTransferableIdentity
	 */
	public String getGroupCategoryTransferableIdentity() {
		return groupCategoryTransferableIdentity;
	}


	/**
	 * @param groupCategoryTransferableIdentity the groupCategoryTransferableIdentity to set
	 */
	public void setGroupCategoryTransferableIdentity(
			String groupCategoryTransferableIdentity) {
		this.groupCategoryTransferableIdentity = groupCategoryTransferableIdentity;
	}


	/**
	 * @return the groupCategoryId
	 */
	public Long getGroupCategoryId() {
		return groupCategoryId;
	}


	/**
	 * @param groupCategoryId the groupCategoryId to set
	 */
	public void setGroupCategoryId(Long groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}



}
