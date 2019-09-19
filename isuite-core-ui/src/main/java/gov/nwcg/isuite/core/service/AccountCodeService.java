package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface AccountCodeService extends TransactionService {

	/**
	 * Saves the AccountCodeVo.
	 * 
	 * @param vo
	 * 			the accountCodeVo to save
	 * @return
	 * 			the saved accountCodeVo
	 * @throws ServiceException
	 */
	public AccountCodeVo save(AccountCodeVo vo) throws ServiceException;
	
	/**
	 * Returns the AccountCodeVo matching the unique key.
	 * 
	 * @param accountCode
	 * 			the accountCode
	 * @param agencyVo
	 * 			the agencyVo
	 * @return
	 * 		accountCodeVo if found
	 * @throws ServiceException
	 */
	public AccountCodeVo getByKey(String accountCode, AgencyVo agencyVo) throws ServiceException;
	
	/**
	 * Returns the AccountCodeVo by it's primary id.
	 * 
	 * @param id
	 * 			the id of the accountCode to look for
	 * @return
	 * 		accountCodeVo matching the supplied id
	 * @throws ServiceException
	 */
	public AccountCodeVo getById(Long id) throws ServiceException;
	
	
}
