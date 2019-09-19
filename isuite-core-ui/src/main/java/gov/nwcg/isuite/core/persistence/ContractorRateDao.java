package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.filter.ContractorRateFilter;
import gov.nwcg.isuite.core.vo.ContractorRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface ContractorRateDao extends TransactionSupport, CrudDao<ContractorRate>{

	/**
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorRateGridVo> getGrid(ContractorRateFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param contractor
	 * @return
	 * @throws PersistenceException, DuplicateItemException
	 */
	public void save(ContractorRate contractorRate) throws PersistenceException,DuplicateItemException;

	/**
	* Saves or updates a {@link Collection} of {@link ContractorRate} objects.
	* @param users {@link Collection} of {@link ContractorRate} objects to be saved or updated.
	* @throws PersistenceException
	*/
	public void saveAll(Collection<ContractorRate> contractorRateList) throws PersistenceException;
	
//	/**
//	 * Enables a {@link Collection} of contractorRate ids.
//	 * @param contractorRateIds
//	 * @throws PersistenceException
//	 */
//	public void enableContractorRates(Collection<Long> contractorRateIds) throws PersistenceException;
//	
//	/**
//	 * Disables a {@link Collection} of contractorRate ids.
//	 * @param contractorRateIds
//	 * @throws PersistenceException
//	 */
//	public void disableContractorRates(Collection<Long> contractorRateIds) throws PersistenceException;
//	
	/**
	 * @param RateNumber
	 * @param contractorId
	 * @return ContractorRate
	 * @throws PersistenceException
	 */
	public ContractorRate getById(Long RateId, Long contractorId) throws PersistenceException;
	
	/**
	 * @param refContractorRateId
	 * @return
	 * @throws PersistenceException
	 */
	public int getAssignmentTimePostingCount(Long refContractorRateId) throws PersistenceException;
	
}