package gov.nwcg.isuite.core.reports.data;

public class DailyCostComparisonICSubReportData {
	private Long id;
	private String requestCategoryCode = "";
	private String itemCodeCategory = "";
	private String itemCode = "";
	private String itemCodeDescription = "";
	private Double averageCost;
	private Double standardCost;
	private Double difference;
	
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
	public String getItemCodeCategory() {
		return itemCodeCategory;
	}
	public void setItemCodeCategory(String itemCodeCategory) {
		this.itemCodeCategory = itemCodeCategory;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemCodeDescription() {
		return itemCodeDescription;
	}
	public void setItemCodeDescription(String itemCodeDescription) {
		this.itemCodeDescription = itemCodeDescription;
	}
	public Double getAverageCost() {
		return averageCost;
	}
	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}
	public Double getStandardCost() {
		return standardCost;
	}
	public void setStandardCost(Double standardCost) {
		this.standardCost = standardCost;
	}
	public Double getDifference() {
		return difference;
	}
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
}
