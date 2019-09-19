package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TimeReportData2Vo {
	private Long atpId;
	private Long atpAssignmentTimeId;
	private Date atpStartDate;
	private Date atpStopDate;
	private BigDecimal atpQuantity;
	private Long atpNextId;
	private Date atpNextStartDate;
	private Long atpPreviousId;
	private String lastName;
	private String firstName;
	private String itemCode;
	private String groupCategory;
	private String subGroupCategory;
	private String sectionCode;
	private String sectionName;
	private String requestNumber;
	private String sortedRequestNumber;
	private Long resourceId;
	private BigDecimal restBeforeNextStart;
	private String assignmentStatus;

	private int shiftNum=0;
	private BigDecimal shiftTotalHours = new BigDecimal(0.0);
	
	public Long getAtpId() {
		return atpId;
	}
	public void setAtpId(Long atpId) {
		this.atpId = atpId;
	}
	public Long getAtpAssignmentTimeId() {
		return atpAssignmentTimeId;
	}
	public void setAtpAssignmentTimeId(Long atpAssignmentTimeId) {
		this.atpAssignmentTimeId = atpAssignmentTimeId;
	}
	public Date getAtpStartDate() {
		return atpStartDate;
	}
	public void setAtpStartDate(Date atpStartDate) {
		this.atpStartDate = atpStartDate;
	}
	public Date getAtpStopDate() {
		return atpStopDate;
	}
	public void setAtpStopDate(Date atpStopDate) {
		this.atpStopDate = atpStopDate;
	}
	public BigDecimal getAtpQuantity() {
		return atpQuantity;
	}
	public void setAtpQuantity(BigDecimal atpQuantity) {
		this.atpQuantity = atpQuantity;
	}
	public Long getAtpNextId() {
		return atpNextId;
	}
	public void setAtpNextId(Long atpNextId) {
		this.atpNextId = atpNextId;
	}
	public Date getAtpNextStartDate() {
		return atpNextStartDate;
	}
	public void setAtpNextStartDate(Date atpNextStartDate) {
		this.atpNextStartDate = atpNextStartDate;
	}
	public Long getAtpPreviousId() {
		return atpPreviousId;
	}
	public void setAtpPreviousId(Long atpPreviousId) {
		this.atpPreviousId = atpPreviousId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getGroupCategory() {
		return groupCategory;
	}
	public void setGroupCategory(String groupCategory) {
		this.groupCategory = groupCategory;
	}
	public String getSubGroupCategory() {
		return subGroupCategory;
	}
	public void setSubGroupCategory(String subGroupCategory) {
		this.subGroupCategory = subGroupCategory;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public BigDecimal getRestBeforeNextStart() {
		return restBeforeNextStart;
	}
	public void setRestBeforeNextStart(BigDecimal restBeforeNextStart) {
		this.restBeforeNextStart = restBeforeNextStart;
	}
	public int getShiftNum() {
		return shiftNum;
	}
	public void setShiftNum(int shiftNum) {
		this.shiftNum = shiftNum;
	}
	public BigDecimal getShiftTotalHours() {
		return shiftTotalHours;
	}
	public void setShiftTotalHours(BigDecimal shiftTotalHours) {
		this.shiftTotalHours = shiftTotalHours;
	}
	public String getSortedRequestNumber() {
		return sortedRequestNumber;
	}
	public void setSortedRequestNumber(String sortedRequestNumber) {
		this.sortedRequestNumber = sortedRequestNumber;
	}
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

}
