package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.filter.RossIncidentFilter;
import gov.nwcg.isuite.core.vo.RossXmlFileGridVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossXmlFileDao extends TransactionSupport {

	/**
	 * Returns the RossXmlFile for the supplied id.
	 * 
	 * @param id Long 
	 * 		id of the entity
	 * @param clazz String
	 * 		name of the class
	 * @return
	 * 		instance of the entity
	 * @throws PersistenceException
	 */
	public RossXmlFile getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(RossXmlFile persistable) throws PersistenceException;
	
	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<RossXmlFile> persistables) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void delete(RossXmlFile persistable) throws PersistenceException;

	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<RossXmlFileGridVo> getGrid(RossIncidentFilter filter) throws PersistenceException;

	public Collection<RossXmlFile> getByIncidentNumber(String incNumber, Long excludeId) throws PersistenceException ;

	public void deleteByRossIncidentId(String rossIncidentId) throws PersistenceException ;

	public void purgeImportedResources(Long rxfId) throws PersistenceException;

	public void purgeDeletedFileData(Long rxfId) throws PersistenceException ;	
	
	public void updateAllExcluded() throws PersistenceException;	

	public void updateIdByReqId(Long rxfId,Object reqId) throws PersistenceException;
}
