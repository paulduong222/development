package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.core.vo.ContractorAgreementGridVo;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

public interface ContractorAgreementService extends TransactionService {

	/**
	 * Saves the ContractorAgreement.
	 * 
	 * @param vo
	 * 			the contractorAgreementVo to save
	 * @return
	 * 			the id
	 * @throws ServiceException
	 */
	public ContractorAgreementVo save(ContractorAgreementVo vo) throws ServiceException,ValidationException;
	
	/**
	 * Deletes the ContractorAgreement.
	 * 
	 * @param vo
	 * 			the contractorAgreementVo to delete
	 * @throws ServiceException
	 */
	public void deleteContractorAgreement(ContractorAgreementVo vo) throws ServiceException;
	
	/**
	 * Returns the ContractorAgreement with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the contractorAgreementVo with the supplied id
	 * @throws ServiceException
	 */
	public ContractorAgreementVo getById(Long id) throws ServiceException;
	
	/**
	 * Returns collection of contractorAgreements for a grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of contractorAgreementVos
	 * @throws ServiceException
	 */
	public Collection<ContractorAgreementGridVo> getGrid(ContractorAgreementFilter filter) throws ServiceException;
	
	/**
	 * Enables the contractorAgreements.
	 * 
	 * @param contractorsAgreements
	 * 			collection of ContractorAgreementGridVos to enable
	 * @throws ServiceException
	 */
	public void enableContractorAgreements(Collection<ContractorAgreementGridVo> contractorAgreements) throws ServiceException;
	
	/**
	 * Disables the contractorAgreements.
	 * 
	 * @param contractorAgreements
	 * 			collection of ContractorAgreementGridVos to enable
	 * @throws ServiceException
	 */
	public void disableContractorAgreements(Collection<ContractorAgreementGridVo> contractorAgreements) throws ServiceException;
	
	/**
	 * Checks for original invoice associations
	 * 
	 * @param contractorAgreementId
	 * @return
	 * @throws ServiceException
	 */
	public Boolean hasOriginalInvoices(Long contractorAgreementId) throws ServiceException;

}
