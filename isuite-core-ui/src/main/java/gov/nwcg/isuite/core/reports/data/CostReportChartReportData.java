package gov.nwcg.isuite.core.reports.data;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Report data object for GroupCategoryTotalSubChartReport.jrxml.
 */
public class CostReportChartReportData {

	private String groupName;
	private String description;
	private Double totalAmount;
	private Date   date;
	private Long   incidentId;
	private String totalAmounInPercentage;
	
	private String dateStr;
	private int totalAmountInt;
	private String directIndirectName;
	
	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getDescription() {
		return this.groupName + " " + getFormatedTotalAmoun() + " (" + totalAmounInPercentage + ")";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalAmountInt() {
		
		 return totalAmount.intValue();
	}
	
	public String getTotalAmounInPercentage() {
		return totalAmounInPercentage;
	}

	public void setTotalAmounInPercentage(String totalAmounInPercentage) {
		this.totalAmounInPercentage = totalAmounInPercentage;
	}
	
	public String getFormatedTotalAmoun() {
		 //$###,###.00
		 DecimalFormat formatter = new DecimalFormat("$###,###,###,###");
		 return formatter.format(this.totalAmount);

	}

	public Date getDate() {
		 return date;
	}

	public void setDate(Date date) {
		this.date = date;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
		this.dateStr = dateFormat.format(date);
	}
	
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public String getDateStr() {
		 return dateStr;
	}

	public String getGroupNamePlusTotal() {
		return groupName + "( $"+totalAmount.toString()+" )";
	}

	public String getDirectIndirectName() {
		return directIndirectName;
	}

	public void setDirectIndirectName(String directIndirectName) {
		this.directIndirectName = directIndirectName;
	}
}
