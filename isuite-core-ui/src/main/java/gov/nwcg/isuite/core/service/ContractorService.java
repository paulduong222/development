package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

public interface ContractorService extends TransactionService {

	/**
	 * Saves the Contractor.
	 * 
	 * @param vo
	 * 			the contractorVo to save
	 * @throws ServiceException
	 */
	public ContractorVo save(ContractorVo vo) throws ServiceException,ValidationException;
	
	/**
	 * Deletes the contractor.
	 * 
	 * @param vo
	 * 			the contractorVo to delete
	 * @throws ServiceException
	 */
	public void deleteContractor(ContractorVo vo) throws ServiceException;
	
	/**
	 * Returns the contractor with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the contractorVo with the supplied id
	 * @throws ServiceException
	 */
	public ContractorVo getById(Long id) throws ServiceException;
	
	/**
	 * Returns collection of contractors for a grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of contractorVos
	 * @throws ServiceException
	 */
	public Collection<ContractorGridVo> getGrid(ContractorFilter filter) throws ServiceException;

	/**
	 * @param contractorId
	 * @param vo
	 * @throws ServiceException
	 */
	public void saveAgreement(Long contractorId,ContractorAgreementVo vo) throws ServiceException;
	
	/**
	 * Deletes a contractor's agreement.
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteAgreement(Long id) throws ServiceException;
	
	/**
	 * Enables the contractors.
	 * 
	 * @param contractors
	 * 			collection of ContractorGridVos to enable
	 * @throws ServiceException
	 */
	public void enableContractors(Collection<ContractorGridVo> contractors) throws ServiceException;
	
	/**
	 * Disables the contractors.
	 * 
	 * @param contractors
	 * 			collection of ContractorGridVos to enable
	 * @throws ServiceException
	 */
	public void disableContractors(Collection<ContractorGridVo> contractors) throws ServiceException;
	
	/**
	 * Adds a contractor to an incident.
	 * 
	 * @param contractors
	 * @throws ServiceException
	 */
	public void addContractorsToIncident(Collection<ContractorGridVo> contractors) throws ServiceException;

	/**
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<ContractorVo> getContractors(ContractorFilter filter) throws ServiceException ;	
	
	/**
	 * Return the names of contracted resources with original invoices
	 * 
	 * @param contractorId
	 * @return
	 * @throws ServiceException
	 */
	public Collection<String> getContractedResourcesWithOriginalInvoices(Long contractorId) throws ServiceException;
}
