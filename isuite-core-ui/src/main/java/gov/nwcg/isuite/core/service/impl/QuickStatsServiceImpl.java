package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.QuickStatsDao;
import gov.nwcg.isuite.core.service.QuickStatsService;
import gov.nwcg.isuite.core.vo.QuickStatsVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public class QuickStatsServiceImpl extends BaseService implements QuickStatsService {
	private QuickStatsDao quickStatsDao = null;
	
	public QuickStatsServiceImpl(){
		
	}
	
	public void initialization(){
		quickStatsDao = (QuickStatsDao)super.context.getBean("quickStatsDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.QuickStatsService#getQuickStats(java.lang.Long, java.lang.Boolean)
	 */
	public QuickStatsVo getQuickStats(Long incidentOrGroupId, Boolean isIncident) throws ServiceException {
		IncidentGroupDao igDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
		Collection<Long> incidentIds = new ArrayList<Long>();
		QuickStatsVo vo = new QuickStatsVo();
		
		try{
		
			if(isIncident == false) {
				incidentIds = igDao.getIncidentIdsInGroup(incidentOrGroupId);
				//vo = quickStatsDao.getQuickStats(incidentIds, incidentOrGroupId, isIncident);
			} else {
				//vo = quickStatsDao.getQuickStats(incidentIds, incidentOrGroupId, isIncident);
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vo;
	}


}
