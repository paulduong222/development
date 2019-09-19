package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ResInvImport extends Persistable {

	/**
	 * @return the fileName
	 */
	public String getFileName();

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName);

	/**
	 * @return the dispatchCenterCode
	 */
	public String getDispatchCenterCode();

	/**
	 * @param dispatchCenterCode the dispatchCenterCode to set
	 */
	public void setDispatchCenterCode(String dispatchCenterCode);

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription();

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription);

	/**
	 * @return the status
	 */
	public String getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);

	/**
	 * @return the processedDate
	 */
	public Date getProcessedDate();

	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(Date processedDate);

	/**
	 * @return the newImportCount
	 */
	public Integer getNewImportCount();

	/**
	 * @param newImportCount the newImportCount to set
	 */
	public void setNewImportCount(Integer newImportCount);

	/**
	 * @return the updatedImportCount
	 */
	public Integer getUpdatedImportCount();

	/**
	 * @param updatedImportCount the updatedImportCount to set
	 */
	public void setUpdatedImportCount(Integer updatedImportCount);

	/**
	 * @return the deletedCount
	 */
	public Integer getDeletedCount();

	/**
	 * @param deletedCount the deletedCount to set
	 */
	public void setDeletedCount(Integer deletedCount);

	/**
	 * @return the resInvImportConflicts
	 */
	public Collection<ResInvImportConflict> getResInvImportConflicts();

	/**
	 * @param resInvImportConflits the resInvImportConflicts to set
	 */
	public void setResInvImportConflicts(
			Collection<ResInvImportConflict> resInvImportConflicts);

	/**
	 * @return the gaccUnitCode
	 */
	public String getGaccUnitCode() ;

	/**
	 * @param gaccUnitCode the gaccUnitCode to set
	 */
	public void setGaccUnitCode(String gaccUnitCode);
	
}