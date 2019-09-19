package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossImportProcessDataErrorDao extends TransactionSupport {

	/**
	 * @param id
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public RossImportProcessDataError getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(RossImportProcessDataError persistable) throws PersistenceException;

	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<RossImportProcessDataError> persistables) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void delete(RossImportProcessDataError persistable) throws PersistenceException;

	/**
	 * @param rossXmlFileId
	 * @param unitCode
	 * @param agencyCode
	 * @param eventTypeCode
	 * @throws PersistenceException
	 * @throws Exception
	 */
	public void checkForIncidentErrors(Long rossXmlFileId, String unitCode, String agencyCode, String eventTypeCode) throws PersistenceException,Exception ;
	
	/**
	 * @param rossXmlFileId
	 * @throws PersistenceException
	 */
	public void checkForResourceErrors(Long rossXmlFileId) throws PersistenceException,Exception;

	/**
	 * @param rxfId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RossImportProcessDataError> getByRossXmlFileId(Long rxfId) throws PersistenceException;

	public void checkForIncidentPdcConflict(Long rossXmlFileId, String unitCode) throws PersistenceException,Exception;
	
}
