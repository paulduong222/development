package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class CostShareReportData extends CostReportData{

	private String category;   //iswl_cat_1_a.description
	private Date costShareDate;
	private String shift;
	private Double dailyCost;

	private String costGroup;
	private String resourceName;
	private String resourceAgency;
	private String itemCode;
	private String itemDescription;
	private int qty;
	
	private String agency;
	private Double cost;
	private BigDecimal percentage;
	
	private String requestNumber;
	
	public CostShareReportData() {}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCostShareDate() {
		return costShareDate;
	}

	public void setCostShareDate(Date costShareDate) {
		this.costShareDate = costShareDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Double getDailyCost() {
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getCostGroup() {
		return costGroup;
	}

	public void setCostGroup(String costGroup) {
		this.costGroup = costGroup;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceAgency() {
		return resourceAgency;
	}

	public void setResourceAgency(String resourceAgency) {
		this.resourceAgency = resourceAgency;
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

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public boolean isInAgencyGroup(List<String> agencyGroupNames) {
		for(String s : agencyGroupNames)
			if(agency.equalsIgnoreCase(s))
				return true;
		
		return false;
	}
	
	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
}
