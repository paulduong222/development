package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.domain.ResInvImportConflict;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "isw_resinv_import")
@SequenceGenerator(name="SEQ_RESINV_IMPORT", sequenceName="SEQ_RESINV_IMPORT")
public class ResInvImportImpl extends PersistableImpl implements ResInvImport {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESINV_IMPORT")
	private Long id = 0L;

	@Column(name="FILENAME",length=75)
	private String fileName;

	@Column(name="DISPATCH_CENTER_CODE",length=10)
	private String dispatchCenterCode;

	@Column(name="GACC_UNIT_CODE",length=10)
	private String gaccUnitCode;

	@Column(name="ERROR_DESCRIPTION",length=300)
	private String errorDescription;

	@Column(name="STATUS",length=20)
	private String status;
	
	@Column(name="PROCESSED_DATE")
	private Date processedDate;
	
	@Column(name="NEW_IMPORT_COUNT")
	private Integer newImportCount;
	
	@Column(name="UPDATED_IMPORT_COUNT")
	private Integer updatedImportCount;

	@Column(name="DELETED_COUNT")
	private Integer deletedCount;
	
	@OneToMany(targetEntity=ResInvImportConflictImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resInvImport")
	private Collection<ResInvImportConflict> resInvImportConflicts;
	
	public ResInvImportImpl(){

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

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the dispatchCenterCode
	 */
	public String getDispatchCenterCode() {
		return dispatchCenterCode;
	}

	/**
	 * @param dispatchCenterCode the dispatchCenterCode to set
	 */
	public void setDispatchCenterCode(String dispatchCenterCode) {
		this.dispatchCenterCode = dispatchCenterCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the processedDate
	 */
	public Date getProcessedDate() {
		return processedDate;
	}

	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	/**
	 * @return the newImportCount
	 */
	public Integer getNewImportCount() {
		return newImportCount;
	}

	/**
	 * @param newImportCount the newImportCount to set
	 */
	public void setNewImportCount(Integer newImportCount) {
		this.newImportCount = newImportCount;
	}

	/**
	 * @return the updatedImportCount
	 */
	public Integer getUpdatedImportCount() {
		return updatedImportCount;
	}

	/**
	 * @param updatedImportCount the updatedImportCount to set
	 */
	public void setUpdatedImportCount(Integer updatedImportCount) {
		this.updatedImportCount = updatedImportCount;
	}

	/**
	 * @return the deletedCount
	 */
	public Integer getDeletedCount() {
		return deletedCount;
	}

	/**
	 * @param deletedCount the deletedCount to set
	 */
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}

	/**
	 * @return the resInvImportConflicts
	 */
	public Collection<ResInvImportConflict> getResInvImportConflicts() {
		if(null==resInvImportConflicts)
			resInvImportConflicts = new ArrayList<ResInvImportConflict>();
		return resInvImportConflicts;
	}

	/**
	 * @param resInvImportConflicts the resInvImportConflicts to set
	 */
	public void setResInvImportConflicts(
			Collection<ResInvImportConflict> resInvImportConflicts) {
		this.resInvImportConflicts = resInvImportConflicts;
	}

	/**
	 * @return the gaccUnitCode
	 */
	public String getGaccUnitCode() {
		return gaccUnitCode;
	}

	/**
	 * @param gaccUnitCode the gaccUnitCode to set
	 */
	public void setGaccUnitCode(String gaccUnitCode) {
		this.gaccUnitCode = gaccUnitCode;
	}
}
