package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.core.vo.ContractorAgreementGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface ContractorAgreementDao extends TransactionSupport, CrudDao<ContractorAgreement>{

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorAgreementGridVo> getGrid(ContractorAgreementFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param contractor
	 * @return
	 * @throws PersistenceException, DuplicateItemException
	 */
	public void save(ContractorAgreement contractorAgreement) throws PersistenceException,DuplicateItemException;

	/**
	* Saves or updates a {@link Collection} of {@link ContractorAgreement} objects.
	* @param users {@link Collection} of {@link ContractorAgreement} objects to be saved or updated.
	* @throws PersistenceException
	*/
	public void saveAll(Collection<ContractorAgreement> contractorAgreementList) throws PersistenceException;
	
	/**
	 * Enables a {@link Collection} of contractorAgreement ids.
	 * @param contractorAgreementIds
	 * @throws PersistenceException
	 */
	public void enableContractorAgreements(Collection<Long> contractorAgreementIds) throws PersistenceException;
	
	/**
	 * Disables a {@link Collection} of contractorAgreement ids.
	 * @param contractorAgreementIds
	 * @throws PersistenceException
	 */
	public void disableContractorAgreements(Collection<Long> contractorAgreementIds) throws PersistenceException;
	
	/**
	 * @param agreementNumber
	 * @param contractorId
	 * @return ContractorAgreement
	 * @throws PersistenceException
	 */
	public ContractorAgreement getByNameId(String agreementNumber, Long agreementId, Long contractorId) throws PersistenceException;
	
	/**
	 * @param contractorAgreementId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorAgreement> getAgreementsWithOriginalInvoices(Long contractorAgreementId) throws PersistenceException;
	
	/**
	 * @param refContractorAgreementId
	 * @return
	 * @throws PersistenceException
	 */
	public int getAssignmentTimePostingCount(Long refContractorAgreementId) throws PersistenceException;
	
}