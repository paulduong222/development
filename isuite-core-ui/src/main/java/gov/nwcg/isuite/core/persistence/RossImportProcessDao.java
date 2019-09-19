package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossEISuiteResourceVo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RossImportProcessDao extends TransactionSupport {

	/**
	 * @param id
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public RossImportProcess getById(Long id, Class clazz) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void save(RossImportProcess persistable) throws PersistenceException;

	/**
	 * @param persistables
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<RossImportProcess> persistables) throws PersistenceException;

	/**
	 * @param persistable
	 * @throws PersistenceException
	 */
	public void delete(RossImportProcess persistable) throws PersistenceException;

	public Collection<RossImportProcessResourceVo> getValidRossResources(Long rossXmlFileId, Long incidentId) throws PersistenceException;

	public Collection<RossImportProcessEISuiteResourceVo> getEISuiteResources(Long incidentId) throws PersistenceException;
	public Collection<RossEISuiteResourceVo> getEISuiteResources2(Long incidentId) throws PersistenceException ;

	public Collection<RossImportProcessResourceVo> getRossResourcesByResIds(Long rossXmlFileId,Collection<Long> rossResourceIds) throws PersistenceException ;

	public Collection<RossEISuiteResourceVo> getEISuiteResources3(Long incidentId) throws PersistenceException ;
	
}
