package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;
import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface IapService extends TransactionService {
	
	/**
	 * @param dialogueVo
	 * @param iapMasterFrequencyVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIapMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyVo iapMasterFrequencyVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteIapMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyVo vo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vos
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo propagateChanges(DialogueVo dialogueVo, Collection<IapMasterFrequencyVo> vos) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vos
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo reorderMasterFrequencyPositions(DialogueVo dialogueVo, Collection<IapMasterFrequencyVo> vos) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo exportMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyFilter filter) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param filter
	 * @param byte[]
	 * @return
	 * @throws ServiceException
	 */		
	public DialogueVo importMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyFilter filter, byte[] xmlByteArray) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getMasterFrequencyGrid(DialogueVo dialogueVo, IapMasterFrequencyFilter filter) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param incidentShiftVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveOperationalPeriod(DialogueVo dialogueVo, IncidentShiftVo incidentShiftVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param incidentShiftVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteOperationalPeriod(DialogueVo dialogueVo, IncidentShiftVo incidentShiftVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIapSettings(DialogueVo dialogueVo, IncidentVo vo) throws ServiceException;
		
	/**
	 * @param dialogueVo
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIapSettingsIG(DialogueVo dialogueVo, IncidentGroupVo vo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param incidentId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getPositions(DialogueVo dialogueVo, Long incidentId) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo savePosition(DialogueVo dialogueVo, IapPositionVo vo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deletePosition(DialogueVo dialogueVo, IapPositionVo vo) throws ServiceException;

	/**
	 * @param incidentId
	 * @param incidentGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getBranchSettings(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveBranchSetting(BranchSettingVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteBranchSetting(BranchSettingVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param branchSettingId
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveBranchPosition(Long branchSettingId,BranchPositionVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param branchSettingId
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteBranchPosition(Long branchSettingId,BranchPositionVo vo, DialogueVo dialogueVo) throws ServiceException;
}
