package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

public class ShiftsInExcessOfStandardHoursSubReportData {

	private String requestNumber;
	private String resourceName;
	private Date shiftStartDate;
	private Date shiftEndDate;
	private Double totalShiftHours;
	private Double amountExcess;

	public ShiftsInExcessOfStandardHoursSubReportData() {
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

	public Date getShiftStartDate() {
		return shiftStartDate;
	}

	public void setShiftStartDate(Date shiftStartDate) {
		this.shiftStartDate = shiftStartDate;
	}

	/**
	 * @return the shiftEndDate
	 */
	public Date getShiftEndDate() {
		return shiftEndDate;
	}

	/**
	 * @param shiftEndDate
	 *            the shiftEndDate to set
	 */
	public void setShiftEndDate(Date shiftEndDate) {
		this.shiftEndDate = shiftEndDate;
	}

	/**
	 * @return the totalShiftHours
	 */
	public Double getTotalShiftHours() {
		return totalShiftHours;
	}

	/**
	 * @param totalShiftHours
	 *            the totalShiftHours to set
	 */
	public void setTotalShiftHours(Double totalShiftHours) {
		this.totalShiftHours = totalShiftHours;
	}

	/**
	 * @return the amountExcess
	 */
	public Double getAmountExcess() {
		return amountExcess;
	}

	/**
	 * @param amountExcess
	 *            the amountExcess to set
	 */
	public void setAmountExcess(Double amountExcess) {
		this.amountExcess = amountExcess;
	}
}
