package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

public interface TimeInvoice extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	public Long getInvoiceReportId();
	
	public void setInvoiceReportId(Long invoiceReportId);
	
	public Report getInvoiceReport();
	
	public void setInvoiceReport(Report invoiceReport);
	
	public Long getAdjustmentReportId();
	
	public void setAdjustmentReportId(Long adjustmentReportId);
	
	public Report getAdjustmentReport();
	
	public void setAdjustmentReport(Report adjustmentReport);
	
	public Date getFirstIncludeDate();
	
	public void setFirstIncludeDate(Date firstIncludeDate);
	
	public Boolean getIsDraft();
	
	public void setIsDraft(Boolean isDraft);
	
	public Boolean getIsDuplicate();
	
	public void setIsDuplicate(Boolean isDuplicate);
	
	public StringBooleanEnum getIsExported();
	
	public void setIsExported(StringBooleanEnum isExported);
	
	
	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate();

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);

	/**
	 * Return the reason the time invoice was deleted.
	 * 
	 * @return
	 */
	public String getDeletedReason();

	/**
	 * Set the reason to delete the time invoice
	 * 
	 * @param deletedReason
	 */
	public void setDeletedReason(String deletedReason);
	
	/**
	 * @return the isFinal
	 */
	public Boolean getIsFinal();

	/**
	 * @param isFinal the isFinal to set
	 */
	public void setIsFinal(Boolean isFinal);

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber();

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber);

	/**
	 * @return the lastIncludeDate
	 */
	public Date getLastIncludeDate();

	/**
	 * @param lastIncludeDate the lastIncludeDate to set
	 */
	public void setLastIncludeDate(Date lastIncludeDate);

	/**
	 * @return the isInvoiceAdjust
	 */
	public Boolean getIsInvoiceAdjust();

	/**
	 * @param isInvoiceAdjust the isInvoiceAdjust to set
	 */
	public void setIsInvoiceAdjust(Boolean isInvoiceAdjust);

	/**
	 * @return the isInvoiceOnly
	 */
	public Boolean getIsInvoiceOnly();

	/**
	 * @param isInvoiceOnly the isInvoiceOnly to set
	 */
	public void setIsInvoiceOnly(Boolean isInvoiceOnly);

	/**
	 * @return the isAdjustOnly
	 */
	public Boolean getIsAdjustOnly();

	/**
	 * @param isAdjustOnly the isAdjustOnly to set
	 */
	public void setIsAdjustOnly(Boolean isAdjustOnly);
	
	public Collection<Resource> getResources();
	
	public void setResources(Collection<Resource> resources);
	
	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts();

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(Collection<AssignmentTimePost> assignmentTimePosts) ;	

	/**
	 * @return the timeAssignmentAdjusts
	 */
	public Collection<TimeAssignAdjust> getTimeAssignmentAdjusts();

	/**
	 * @param timeAssignmentAdjusts the timeAssignmentAdjusts to set
	 */
	public void setTimeAssignmentAdjusts(Collection<TimeAssignAdjust> timeAssignmentAdjusts);
	
	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate);

	/**
	 * @return the exportDate
	 */
	public Date getExportDate();
	
}
