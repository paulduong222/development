package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TimeAdjustmentVo {
	private Long id;
	private Date activityDate;
	private Long incidentAccountCodeId;
	private String adjustmentType;
	private BigDecimal adjustmentAmount=BigDecimal.valueOf(0.0);
	private Long assignmentId;
	
	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}

	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	/**
	 * @return the adjustmentType
	 */
	public String getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}
