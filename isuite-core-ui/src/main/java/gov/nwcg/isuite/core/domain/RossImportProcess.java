package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossImportProcess extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the rossXmlFile
	 */
	public RossXmlFile getRossXmlFile() ;

	/**
	 * @param rossXmlFile the rossXmlFile to set
	 */
	public void setRossXmlFile(RossXmlFile rossXmlFile) ;

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() ;

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId);

	/**
	 * @return the completedStage
	 */
	public String getCompletedStage();

	/**
	 * @param completedStage the completedStage to set
	 */
	public void setCompletedStage(String completedStage) ;
	
	/**
	 * @return the rossImportProcessMatchIncident
	 */
	public RossImportProcessMatchInc getRossImportProcessMatchIncident() ;

	/**
	 * @param rossImportProcessMatchIncident the rossImportProcessMatchIncident to set
	 */
	public void setRossImportProcessMatchIncident(RossImportProcessMatchInc rossImportProcessMatchIncident) ;

}

