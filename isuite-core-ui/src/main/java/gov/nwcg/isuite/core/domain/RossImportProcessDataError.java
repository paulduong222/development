package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossImportProcessDataError extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the rossXmlFileData
	 */
	public RossXmlFileData getRossXmlFileData() ;

	/**
	 * @param rossXmlFileData the rossXmlFileData to set
	 */
	public void setRossXmlFileData(RossXmlFileData rossXmlFileData) ;

	/**
	 * @return the rossXmlFileDataId
	 */
	public Long getRossXmlFileDataId();

	/**
	 * @param rossXmlFileDataId the rossXmlFileDataId to set
	 */
	public void setRossXmlFileDataId(Long rossXmlFileDataId);

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
	public Long getRossXmlFileId();

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId);
	
	/**
	 * @return the excludeFromImport
	 */
	public Short getExcludeFromImport();
	
	/**
	 * @param excludeFromImport the excludeFromImport to set
	 */
	public void setExcludeFromImport(Short excludeFromImport);

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() ;

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) ;

	/**
	 * @return the newValue
	 */
	public String getNewValue() ;

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue);

	/**
	 * @return the newValueType
	 */
	public String getNewValueType();

	/**
	 * @param newValueType the newValueType to set
	 */
	public void setNewValueType(String newValueType);

	/**
	 * @return the rossResError
	 */
	public RossResError getRossResError() ;

	/**
	 * @param rossResError the rossResError to set
	 */
	public void setRossResError(RossResError rossResError);

	/**
	 * @return the rossResErrorId
	 */
	public Long getRossResErrorId();

	/**
	 * @param rossResErrorId the rossResErrorId to set
	 */
	public void setRossResErrorId(Long rossResErrorId);
	
}
