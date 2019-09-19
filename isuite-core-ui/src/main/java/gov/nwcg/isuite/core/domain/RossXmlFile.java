package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossXmlFile extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the fileName
	 */
	public String getFileName();

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) ;

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() ;

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus);

	/**
	 * @return the rossXmlFileDatas
	 */
	public Collection<RossXmlFileData> getRossXmlFileDatas() ;

	/**
	 * @param rossXmlFileDatas the rossXmlFileDatas to set
	 */
	public void setRossXmlFileDatas(Collection<RossXmlFileData> rossXmlFileDatas);
	
	/**
	 * @return the incidentName
	 */
	public String getIncidentName();

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber();

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber);

	/**
	 * @return the incidentEventType
	 */
	public String getIncidentEventType() ;

	/**
	 * @param incidentEventType the incidentEventType to set
	 */
	public void setIncidentEventType(String incidentEventType);

	/**
	 * @return the rossImportProcess
	 */
	public RossImportProcess getRossImportProcess();

	/**
	 * @param rossImportProcess the rossImportProcess to set
	 */
	public void setRossImportProcess(RossImportProcess rossImportProcess);	

	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate();

	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate);

	/**
	 * @return the rossIncId
	 */
	public String getRossIncId();

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId);
	
	/**
	 * @return the rossImportProcessDataErrors
	 */
	public Collection<RossImportProcessDataError> getRossImportProcessDataErrors();

	/**
	 * @param rossImportProcessDataErrors the rossImportProcessDataErrors to set
	 */
	public void setRossImportProcessDataErrors(
			Collection<RossImportProcessDataError> rossImportProcessDataErrors) ;

	/**
	 * @return the importedDate
	 */
	public Date getImportedDate();

	/**
	 * @param importedDate the importedDate to set
	 */
	public void setImportedDate(Date importedDate);

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() ;

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode);

}
