package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface DataTransferService extends TransactionService {

	public DialogueVo getExportHistory(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo exportData(DataTransferVo dataTransferVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo exportDataToEnterprise(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getExportData(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo importDataFromFile(byte[] xmlByteArray, String password, UserVo dsUserVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getEnterpriseFile(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getDataStewardList(DataTransferVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getDataStewardListFromDb(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo unlock(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo isLocked(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException;
	
}
