package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.Date;

public class RossXmlFileGridVo extends AbstractVo{
	private String incidentNumber;
	private String incidentName;
	private String incidentEventName;
	private Date creationDate;
	private String creationTime;
	private String rossIncidentId;
	private Long rossXmlFileId;
	private Date importedDate;
	private String importedTime;
	private String unitCode;
	
	public RossXmlFileGridVo(){
		
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
	 * @return the incidentEventName
	 */
	public String getIncidentEventName() {
		return incidentEventName;
	}

	/**
	 * @param incidentEventName the incidentEventName to set
	 */
	public void setIncidentEventName(String incidentEventName) {
		this.incidentEventName = incidentEventName;
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
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	
}
