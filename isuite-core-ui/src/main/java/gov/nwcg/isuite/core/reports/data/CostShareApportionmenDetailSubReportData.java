package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

public class CostShareApportionmenDetailSubReportData {	
	private Long id;
	private Double otherCostPercentage;
	private Double fsCostPercentage;
	private Double stateCostPercentage;
	private Double federalCostPercentage;
	//private Double otherCost;
	//private Double fsCost;
	//private Double stateCost;
	//private Double federalCost;
	private Double dailyCost;
	private String category1a;
	private String itemCode;
	private String agency;
	private String resourceName;
	private String costGroup;
	private String shift;
	private String activityDateChar;
	private Date activityDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Double getDailyCost() {
		if(null != dailyCost){
			long dbl=Math.round(dailyCost.doubleValue());
			return new Double(dbl);
		}
		return dailyCost;
	}
	public void setDailyCost(Double dailyCost) {
		this.dailyCost = dailyCost;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getCostGroup() {
		return costGroup;
	}
	public void setCostGroup(String costGroup) {
		this.costGroup = costGroup;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public Double getFsCost() {
		return (this.dailyCost * this.fsCostPercentage) / 100.0;
	}
	public Double getStateCost() {
		return (this.dailyCost * this.stateCostPercentage) / 100.0;
	}
	public Double getFederalCost() {
		return (this.dailyCost * this.federalCostPercentage) / 100.0;
	}
	public String getCategory1a() {
		return category1a;
	}
	public void setCategory1a(String category1a) {
		this.category1a = category1a;
	}
	public String getActivityDateChar() {
		return activityDateChar;
	}
	public void setActivityDateChar(String activityDateChar) {
		this.activityDateChar = activityDateChar;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public Double getOtherCost() {
		return (this.dailyCost * this.otherCostPercentage) / 100.0;
	}
	public Double getOtherCostPercentage() {
		return otherCostPercentage;
	}
	public void setOtherCostPercentage(Double otherCostPercentage) {
		this.otherCostPercentage = otherCostPercentage;
	}
	public Double getFsCostPercentage() {
		return fsCostPercentage;
	}
	public void setFsCostPercentage(Double fsCostPercentage) {
		this.fsCostPercentage = fsCostPercentage;
	}
	public Double getStateCostPercentage() {
		return stateCostPercentage;
	}
	public void setStateCostPercentage(Double stateCostPercentage) {
		this.stateCostPercentage = stateCostPercentage;
	}
	public Double getFederalCostPercentage() {
		return federalCostPercentage;
	}
	public void setFederalCostPercentage(Double federalCostPercentage) {
		this.federalCostPercentage = federalCostPercentage;
	}
	/*
	public void setFsCost(Double fsCost) {
		this.fsCost = fsCost;
	}
	public void setStateCost(Double stateCost) {
		this.stateCost = stateCost;
	}
	public void setFederalCost(Double federalCost) {
		this.federalCost = federalCost;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	*/
		
}


