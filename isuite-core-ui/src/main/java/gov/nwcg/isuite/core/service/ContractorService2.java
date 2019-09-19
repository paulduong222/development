package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface ContractorService2 extends TransactionService {

	/**
	 * Saves the Contractor.
	 * 
	 * @param vo
	 * 			the contractorVo to save
	 * @throws ServiceException
	 */
	public DialogueVo save(ContractorVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Deletes the contractor.
	 * 
	 * @param vo
	 * 			the contractorVo to delete
	 * @throws ServiceException
	 */
	public DialogueVo deleteContractor(ContractorVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns the contractor with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the contractorVo with the supplied id
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns collection of contractors for a grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of contractorVos
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(ContractorFilter filter,DialogueVo dialogueVo) throws ServiceException;

	//public DialogueVo getComboList(ContractorFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @throws ServiceException
	 */
	public DialogueVo saveAgreement(ContractorAgreementVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Deletes a contractor's agreement.
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public DialogueVo deleteAgreement(Long id,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Enables the contractors.
	 * 
	 * @param contractors
	 * 			collection of ContractorGridVos to enable
	 * @throws ServiceException
	 */
	public DialogueVo enableContractors(Collection<ContractorGridVo> contractors,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Disables the contractors.
	 * 
	 * @param contractors
	 * 			collection of ContractorGridVos to enable
	 * @throws ServiceException
	 */
	public DialogueVo disableContractors(Collection<ContractorGridVo> contractors,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Adds a contractor to an incident.
	 * 
	 * @param contractors
	 * @throws ServiceException
	 */
	public DialogueVo addContractorsToIncident(Collection<ContractorGridVo> contractors,DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getContractors(ContractorFilter filter,DialogueVo dialogueVo) throws ServiceException ;	
	
	/**
	 * Return the names of contracted resources with original invoices
	 * 
	 * @param contractorId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getContractedResourcesWithOriginalInvoices(Long contractorId, DialogueVo dialogueVo) throws ServiceException;
}
