package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.ContractorRateFilter;
import gov.nwcg.isuite.core.vo.ContractorRateGridVo;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

public interface ContractorRateService extends TransactionService {

	/**
	 * Saves the ContractorRate.
	 * 
	 * @param vo
	 * 			the contractorRateVo to save
	 * @return
	 * 			the id
	 * @throws ServiceException
	 */
	public DialogueVo save(ContractorRateVo vo, WorkPeriodVo workPeriodVo,DialogueVo dialogueVo) throws ServiceException,ValidationException;

	
	/**
	 * Deletes the ContractorRate.
	 * 
	 * @param vo
	 * 			the contractorRateVo to delete
	 * @throws ServiceException
	 */
	public DialogueVo deleteContractorRate(ContractorRateVo vo, WorkPeriodVo workPeriodVo,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns the ContractorRate with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the contractorRateVo with the supplied id
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns collection of contractorRates for a grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of contractorRateVos
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(ContractorRateFilter filter,DialogueVo dialogueVo) throws ServiceException;

}
