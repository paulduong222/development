package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.GridColumnFilter;
import gov.nwcg.isuite.core.vo.GridColumnVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface GridColumnService extends TransactionService {

	/**
	 * Returns a collection of GridColumnVos.
	 * 
	 * @param filter
	 * 			the gridColumnFilter to filter by
	 * @return
	 * 			collection of GridColumnVo's 
	 * @throws ServiceException
	 */
	public Collection<GridColumnVo> getGridColumns(GridColumnFilter filter) throws ServiceException;
	
}
