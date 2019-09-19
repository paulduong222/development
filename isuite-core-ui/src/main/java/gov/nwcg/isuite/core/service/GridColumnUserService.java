package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.vo.GridColumnUserVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

public interface GridColumnUserService extends TransactionService {

	public void saveAll(Collection<GridColumnUserVo> vos) throws ServiceException,ValidationException;
	
	public Collection<GridColumnUserVo> getUserGridColumns(GridColumnUserFilter filter) throws ServiceException;
	
}
