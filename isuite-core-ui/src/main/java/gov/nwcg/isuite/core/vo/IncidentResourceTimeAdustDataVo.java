package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class IncidentResourceTimeAdustDataVo {
	private Long incidentResourceId;
	private Long assignmentId;
	private Long timeAssignAdjustId;
	private Date activityDate;
	private String adjustmentType;
	private BigDecimal adjustmentAmount;
	private String adjustmentCategory;
	private String adjustmentCategoryDesc;
	private String commodity;
	
	private Long incidentAccountCodeId;
	private String accountingCode;
	
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}
	public Long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public Long getTimeAssignAdjustId() {
		return timeAssignAdjustId;
	}
	public void setTimeAssignAdjustId(Long timeAssignAdjustId) {
		this.timeAssignAdjustId = timeAssignAdjustId;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public String getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public String getAdjustmentCategory() {
		return adjustmentCategory;
	}
	public void setAdjustmentCategory(String adjustmentCategory) {
		this.adjustmentCategory = adjustmentCategory;
	}
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}
	public String getAdjustmentCategoryDesc() {
		return adjustmentCategoryDesc;
	}
	public void setAdjustmentCategoryDesc(String adjustmentCategoryDesc) {
		this.adjustmentCategoryDesc = adjustmentCategoryDesc;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getAccountingCode() {
		return accountingCode;
	}
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}
	
}
