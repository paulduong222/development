package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.SysCostRateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface SysCostRateService extends TransactionService {

	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	public DialogueVo getDefaultRatesGrid(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getSysCostRate(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveSysCostRateKind(SysCostRateKindVo vo, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveSysCostRateOvhd(SysCostRateOvhdVo vo, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveSysCostRateState(SysCostRateStateVo vo, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveSysCostRateStateKind(SysCostRateStateKindVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getSysCostRateOvhd(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getSysCostRateState(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getSysCostRateStateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;	
}
