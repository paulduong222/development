package gov.nwcg.isuite.core.reports.data;

public class ResourceItemCodeByCostSubReportData {

	private Long id;
	private String requestCategoryCode = "";
	private String itemCodeCategory = "";
	private String itemCodeDescription = "";
	private String itemCode = "";
	private String requestNumber = "";
	private String name = "";
	private String agencyCode = "";
	private String local;
	private Double unitCost;
	private String rateType;
	private Integer rateUnits;
	private String assignDateChar;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}
	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}
	public String getAssignDateChar() {
		return assignDateChar;
	}
	public void setAssignDateChar(String assignDateChar) {
		this.assignDateChar = assignDateChar;
	}
	public String getItemCodeCategory() {
		return itemCodeCategory;
	}
	public void setItemCodeCategory(String itemCodeCategory) {
		this.itemCodeCategory = itemCodeCategory;
	}
	public String getItemCodeDescription() {
		return itemCodeDescription;
	}
	public void setItemCodeDescription(String itemCodeDescription) {
		this.itemCodeDescription = itemCodeDescription;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public Integer getRateUnits() {
		return rateUnits;
	}
	public void setRateUnits(Integer rateUnits) {
		this.rateUnits = rateUnits;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
		
}



