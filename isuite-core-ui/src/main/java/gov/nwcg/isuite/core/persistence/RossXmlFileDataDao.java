package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.vo.RossXmlFileDataVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossXmlFileDataDao extends TransactionSupport {

	/**
	 * Returns the RossXmlFileData for the supplied id.
	 * 
	 * @param id Long 
	 * 		id of the entity
	 * @param clazz String
	 * 		name of the class
	 * @return
	 * 		instance of the entity
	 * @throws PersistenceException
	 */
	public RossXmlFileData getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(RossXmlFileData persistable) throws PersistenceException;
	
	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<RossXmlFileData> persistables) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void delete(RossXmlFileData persistable) throws PersistenceException;

	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RossXmlFileDataVo> getAllByRossXmlFileId(Long rossXmlFileId) throws PersistenceException;

	/**
	 * @param rossXmlFileId
	 * @param rossAssignment
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RossXmlFileData> getAllByRossXmlFileId(Long rossXmlFileId, Boolean rossAssignment) throws PersistenceException;
	
	public void updateStatuses(Collection<Long> rossResourceIds, String rossIncId, String status) throws PersistenceException ;

	public Collection<RossResourceVo> getNewResourcesByRossXmlFileId(Long rossXmlFileId, Long incidentId,  Boolean rossAssignment) throws PersistenceException;

	public void updateResourceImportStatus(Long rossResReqId, String rossIncId, String status) throws PersistenceException ;

	public RossResourceVo getExcludedResourceByRossXmlFileId(Long rossResReqId, Long rossXmlFileId, Long incidentId, Boolean rossAssignment) throws PersistenceException;

}
