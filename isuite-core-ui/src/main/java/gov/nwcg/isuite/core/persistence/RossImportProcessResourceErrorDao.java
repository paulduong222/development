package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossImportProcessResourceError;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossImportProcessResourceErrorDao extends TransactionSupport {

	/**
	 * @param id
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public RossImportProcessResourceError getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(RossImportProcessResourceError persistable) throws PersistenceException;

	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<RossImportProcessResourceError> persistables) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void delete(RossImportProcessResourceError persistable) throws PersistenceException;

	/**
	 * @param rossXmlFileId
	 * @throws PersistenceException
	 */
	public void checkForErrors(Long rossXmlFileId) throws PersistenceException,Exception;

	/**
	 * @param rxfId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RossImportProcessResourceError> getByRossXmlFileId(Long rxfId) throws PersistenceException;
	
}
