package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
//import gov.nwcg.isuite.core.filter.ContractorPaymentInfoFilter;
//import gov.nwcg.isuite.core.vo.ContractorPaymentInfoGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface ContractorPaymentInfoDao extends TransactionSupport, CrudDao<ContractorPaymentInfo>{

//	/**
//	 * 
//	 * @param filter
//	 * @return
//	 * @throws PersistenceException
//	 */
//	public Collection<ContractorPaymentInfoGridVo> getGrid(ContractorPaymentInfoFilter filter) throws PersistenceException;
	
	/**
	 * 
	 * @param ContractorPaymentInfo
	 * @return
	 * @throws PersistenceException, DuplicateItemException
	 */
	public void save(ContractorPaymentInfo contractorPaymentInfo) throws PersistenceException,DuplicateItemException;

	/**
	* Saves or updates a {@link Collection} of {@link ContractorPaymentInfo} objects.
	* @param users {@link Collection} of {@link ContractorPaymentInfo} objects to be saved or updated.
	* @throws PersistenceException
	*/
	public void saveAll(Collection<ContractorPaymentInfo> contractorPaymentInfoList) throws PersistenceException;
	
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
	 * @param ContractorPaymentInfoId
	 * @return ContractorPaymentInfo
	 * @throws PersistenceException
	 */
	public ContractorPaymentInfo getById(Long contractorPaymentInfoId) throws PersistenceException;
	
	public Collection<ContractorPaymentInfo> getByVinName(String vinName) throws PersistenceException;
	
}