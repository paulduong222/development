package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.RossIncidentFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Date;

public class RossIncidentFilterImpl extends FilterImpl implements RossIncidentFilter {
	private String incidentNumber;
	private String incidentName;
	private String incidentEventType;
	private Date creationDate;
	private String creationTime;
	private String creationDateString;
	private Long workAreaId;
	private Boolean imported=Boolean.FALSE;
	private Date importedDate;
	private String importedDateString;
	private String importedTime;
	private Boolean hasExcludedResources=false;
	
	public RossIncidentFilterImpl(){
		
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentEventType
	 */
	public String getIncidentEventType() {
		return incidentEventType;
	}

	/**
	 * @param incidentEventType the incidentEventType to set
	 */
	public void setIncidentEventType(String incidentEventType) {
		this.incidentEventType = incidentEventType;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the creationTime
	 */
	public String getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the imported
	 */
	public Boolean getImported() {
		return imported;
	}

	/**
	 * @param imported the imported to set
	 */
	public void setImported(Boolean imported) {
		this.imported = imported;
	}

	/**
	 * @return the creationDateString
	 */
	public String getCreationDateString() {
		return creationDateString;
	}

	/**
	 * @param creationDateString the creationDateString to set
	 */
	public void setCreationDateString(String creationDateString) {
		this.creationDateString = creationDateString;
	}

	/**
	 * @return the workAreaId
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * @param workAreaId the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * @return the importedDate
	 */
	public Date getImportedDate() {
		return importedDate;
	}

	/**
	 * @param importedDate the importedDate to set
	 */
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	/**
	 * @return the importedDateString
	 */
	public String getImportedDateString() {
		return importedDateString;
	}

	/**
	 * @param importedDateString the importedDateString to set
	 */
	public void setImportedDateString(String importedDateString) {
		this.importedDateString = importedDateString;
	}

	/**
	 * @return the importedTime
	 */
	public String getImportedTime() {
		return importedTime;
	}

	/**
	 * @param importedTime the importedTime to set
	 */
	public void setImportedTime(String importedTime) {
		this.importedTime = importedTime;
	}

	public Boolean getHasExcludedResources() {
		return hasExcludedResources;
	}

	public void setHasExcludedResources(Boolean hasExcludedResources) {
		this.hasExcludedResources = hasExcludedResources;
	}
	
}
