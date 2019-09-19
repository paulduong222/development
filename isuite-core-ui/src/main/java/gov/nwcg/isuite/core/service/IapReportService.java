package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IapPrintFormVo;
import gov.nwcg.isuite.core.vo.IapPrintJobVo;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;

public interface IapReportService extends TransactionService {
	
	
	/**
	 * @param printJobVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo printPlan(IapPrintJobVo printJobVo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param IapPlanPrintOrderVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	
	 public DialogueVo savePlanPrintOrder(IapPlanPrintOrderVo vo3, DialogueVo dialogueVo) throws ServiceException;
}
