package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.impl.IrwinSearchFilterImpl;
import gov.nwcg.isuite.core.irwin.IncidentModel;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.IrwinException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.TaskException;

public interface IrwinService extends TransactionService {
	
	/**
	 * @param dialogueVo
	 * @param irwinId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIrwinIncident(DialogueVo dialogueVo, String irwinId,  String flag) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param searchString
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo searchIrwinIncidents(DialogueVo dialogueVo, IrwinSearchFilterImpl searchFilter) throws ServiceException;
	
	//public IncidentModel findMatches(String unitCode, String incidentNumber) throws ServiceException;
	
}