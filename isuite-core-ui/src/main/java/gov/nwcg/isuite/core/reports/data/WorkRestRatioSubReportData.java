package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

/**
 * Report data object for WorkRestRatioReport.jrxml.
 */
public class WorkRestRatioSubReportData {

	private String section = "";
	private String requestNumber = "";
	private String resourceName = "";
	private String itemCode = "";
	private String status = "";
	private Date shiftStartDate = null;
	private Double hoursOfWork = null;
	private Double hoursOfRest = null;
	private Double hoursOfMitigation = null;
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getShiftStartDate() {
		return shiftStartDate;
	}

	public void setShiftStartDate(Date shiftStartDate) {
		this.shiftStartDate = shiftStartDate;
	}

	public Double getHoursOfWork() {
		return hoursOfWork;
	}

	public void setHoursOfWork(Double hoursOfWork) {
		this.hoursOfWork = hoursOfWork;
	}

	public Double getHoursOfRest() {
		return hoursOfRest;
	}

	public void setHoursOfRest(Double hoursOfRest) {
		this.hoursOfRest = hoursOfRest;
	}

	public Double getHoursOfMitigation() {
		return hoursOfMitigation;
	}

	public void setHoursOfMitigation(Double hoursOfMitigation) {
		this.hoursOfMitigation = hoursOfMitigation;
	}
}
