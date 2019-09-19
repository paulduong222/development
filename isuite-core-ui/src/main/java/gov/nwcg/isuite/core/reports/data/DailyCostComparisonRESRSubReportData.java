package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

public class DailyCostComparisonRESRSubReportData {
	private Long id;
	private String requestCategoryCode = "";
	private String itemCodeCategory = "";
	private String itemCode = "";
	private String itemCodeDescription = "";
	private String requestNumber = "";
	private String name = "";
	private String agencyCode = "";
	private Double averageCost;
	private Double standardCost;
	private Double difference;
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
