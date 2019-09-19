package gov.nwcg.isuite.framework.financial.posts;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

import java.util.Collection;
import java.util.Date;

public interface DailyTimePost {

    Collection<IncidentResourceVo> getTimePosts(Long incidentResourceId, Date startDate)
			throws ServiceException;

	void markInvoiced(Date lastIncludeDate,IncidentResourceVo irVo, TimeInvoice timeInvoice, Boolean invoice)
			throws PersistenceException;
	
	void markInvoiced2(Date lastIncludeDate
						,IncidentResourceTimeDataVo irTimeDataVo
						,Collection<IncidentResourceTimePostDataVo> irTimePostDataVos
						,Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos
						, TimeInvoice timeInvoice
						, Boolean invoice)
	throws PersistenceException;
	
	void saveInvoicedAmounts(IncidentResourceVo irVo, TimeReportFilter filter)
			throws ServiceException, PersistenceException;

	void saveInvoicedAmounts2(IncidentResourceTimeDataVo irTimeDataVo
							, TimeReportFilter filter
							)
		throws ServiceException, PersistenceException;
}
