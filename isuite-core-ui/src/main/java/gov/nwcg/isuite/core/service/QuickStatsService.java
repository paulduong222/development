package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.QuickStatsVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;


public interface QuickStatsService {

	/**
	 * 
	 * @param incidentOrGroupId
	 * @param isIncident
	 * @return a single {@link QuickStatsVo} object.
	 * @throws ServiceException
	 */
	public QuickStatsVo getQuickStats(Long incidentOrGroupId, Boolean isIncident) throws ServiceException;
}
