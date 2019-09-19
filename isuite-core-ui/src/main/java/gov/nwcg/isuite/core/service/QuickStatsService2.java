package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface QuickStatsService2 {

	/**
	 * 
	 * @param incidentOrGroupId
	 * @param isIncident
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getQuickStats(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
}
