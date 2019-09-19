package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface ContractorDao extends TransactionSupport, CrudDao<Contractor>{

	public void merge(Contractor entity) throws PersistenceException;

	public Collection<ContractorVo> getAll(ContractorFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorGridVo> getGrid(ContractorFilter filter) throws PersistenceException;
	
	public Collection<ContractorVo> getLightList(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	/**
	 * 
	 * @param contractor
	 * @return
	 * @throws PersistenceException, DuplicateItemException
	 */
	public void save(Contractor contractor) throws PersistenceException,DuplicateItemException;

	/**
	* Saves or updates a {@link Collection} of {@link Contractor} objects.
	* @param users {@link Collection} of {@link Contractor} objects to be saved or updated.
	* @throws PersistenceException
	*/
	public void saveAll(Collection<Contractor> contractorList) throws PersistenceException;
	
	/**
	 * Enables a {@link Collection} of contractor ids.
	 * @param contractorIds
	 * @throws PersistenceException
	 */
	public void enableContractors(Collection<Long> contractorIds) throws PersistenceException;
	
	/**
	 * Disables a {@link Collection} of contractor ids.
	 * @param contractorIds
	 * @throws PersistenceException
	 */
	public void disableContractors(Collection<Long> contractorIds) throws PersistenceException;
	
	/**
	 * @param contractorName
	 * @param ids
	 * @return Contractor
	 * @throws PersistenceException
	 */
	public Contractor getByContractorNameIds(String contractorName, Collection<Long> incidentIds, Collection<Long> workAreaIds,  Long contractorId) throws PersistenceException;
	
	/**
	 * @param contractorId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<String> getContractedResourcesWithOriginalInvoices(Long contractorId) throws PersistenceException;
	
	/**
	 * @param refContractorId
	 * @return
	 * @throws PersistenceException
	 */
	public int getAssignmentTimePostingCount(Long refContractorId) throws PersistenceException;
	
	/**
	 * @param contractorId
	 * @throws PersistenceException
	 */
	public void removeContrFromContrPaymentInfo(Long contractorId) throws PersistenceException;
	
	/**
	 * @param agreementIds
	 * @throws PersistenceException
	 */
	public void removeContrAgreementFromContrPaymentInfo(Collection<Long> agreementIds) throws PersistenceException;
	
	/**'
	 * @param contractorId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorAgreement> getIncludedInOriginalInvoice(Long contractorId) throws PersistenceException;
	
	public Long getIncidentId(Long contractorId) throws PersistenceException;
	
}