package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface TimeAssignAdjust extends Persistable {

	/**
	 * @return the assignment
	 */
	public Assignment getAssignment();

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(Assignment assignment);

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId();

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId);

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate();

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate);

	/**
	 * @return the adjustmentType
	 */
	public AdjustmentTypeEnum getAdjustmentType();

	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(AdjustmentTypeEnum adjustmentType);

	/**
	 * @return the adjustCategory
	 */
	public AdjustCategory getAdjustCategory();

	/**
	 * @param adjustCategory the adjustCategory to set
	 */
	public void setAdjustCategory(AdjustCategory adjustCategory);

	/**
	 * @return the adjustmentCategoryId
	 */
	public Long getAdjustmentCategoryId();

	/**
	 * @param adjustmentCategoryId the adjustmentCategoryId to set
	 */
	public void setAdjustmentCategoryId(Long adjustmentCategoryId);

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount();

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount);

	/**
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode();

	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode);

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId();

	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId);

	/**
	 * @return the commodity
	 */
	public String getCommodity();

	/**
	 * @param commodity the commodity to set
	 */
	public void setCommodity(String commodity);

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate();

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);

	/**
	 * @return the timeInvoices
	 */
	public Collection<TimeInvoice> getTimeInvoices();

	/**
	 * @param timeInvoices the timeInvoices to set
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices);	

	public IncidentResourceDailyCost getincidentResourceDailyCost() ;

	public void setIncidentResourceDailyCost(IncidentResourceDailyCost incidentResourceDailyCost) ;
	
}
