package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface RossIncidentFilter extends Filter{

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() ;

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) ;

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() ;

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) ;

	/**
	 * @return the incidentEventType
	 */
	public String getIncidentEventType() ;

	/**
	 * @param incidentEventType the incidentEventType to set
	 */
	public void setIncidentEventType(String incidentEventType);

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate();

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate);

	/**
	 * @return the creationTime
	 */
	public String getCreationTime();

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(String creationTime) ;

	/**
	 * @return the imported
	 */
	public Boolean getImported() ;

	/**
	 * @param imported the imported to set
	 */
	public void setImported(Boolean imported) ;

	/**
	 * @return the creationDateString
	 */
	public String getCreationDateString() ;

	/**
	 * @param creationDateString the creationDateString to set
	 */
	public void setCreationDateString(String creationDateString) ;

	/**
	 * @return the workAreaId
	 */
	public Long getWorkAreaId() ;
	
	/**
	 * @param workAreaId the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) ;

	/**
	 * @return the importedDate
	 */
	public Date getImportedDate() ;

	/**
	 * @param importedDate the importedDate to set
	 */
	public void setImportedDate(Date importedDate);


	/**
	 * @return the importedDateString
	 */
	public String getImportedDateString();
	
	/**
	 * @param importedDateString the importedDateString to set
	 */
	public void setImportedDateString(String importedDateString);

	/**
	 * @return the importedTime
	 */
	public String getImportedTime();
	
	/**
	 * @param importedTime the importedTime to set
	 */
	public void setImportedTime(String importedTime);
	
	public Boolean getHasExcludedResources() ;

	public void setHasExcludedResources(Boolean hasExcludedResources) ;	
}
