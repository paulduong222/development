package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.impl.DataAuditTrackingFilterImpl;
import gov.nwcg.isuite.core.vo.DataAuditTrackingGridVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface DataAuditTrackingService extends TransactionService {
	
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo getGrid(DialogueVo dialogueVo, DataAuditTrackingFilterImpl dataAuditTrackingFilter) throws ServiceException;
}
