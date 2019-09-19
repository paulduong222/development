package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

/**
 * Report data object for CrewRosterReport.jrxml.
 */
public class CrewRosterReportData {
	
	private Long resourceId = null;
	private Boolean parent = false;
	private String requestNumber = "";
	private String resourceName = "";
	private String itemCode = "";
	private String status = "";
	private Date checkInDate = null;
	private String employmentType = "";
	private String traineeStatus = "";
	private String unitId = "";
	private Date firstWorkDay = null;
	private Long lengthOfAssignment = null;
	private Date lastWorkDay = null;
	private Date actualReleaseDate = null;
	
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getTraineeStatus() {
		return traineeStatus;
	}
	public void setTraineeStatus(String traineeStatus) {
		this.traineeStatus = traineeStatus;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public Date getFirstWorkDay() {
		return firstWorkDay;
	}
	public void setFirstWorkDay(Date firstWorkDay) {
		this.firstWorkDay = firstWorkDay;
	}
	public Long getLengthOfAssignment() {
		return lengthOfAssignment;
	}
	public void setLengthOfAssignment(Long lengthOfAssignment) {
		this.lengthOfAssignment = lengthOfAssignment;
	}
	public Date getLastWorkDay() {
		return lastWorkDay;
	}
	public void setLastWorkDay(Date lastWorkDay) {
		this.lastWorkDay = lastWorkDay;
	}
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}
	public Boolean getParent() {
		return parent;
	}
	public void setParent(Boolean parent) {
		this.parent = parent;
	}
}
