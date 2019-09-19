package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface RateClassService extends TransactionService {

	
	public RateClassVo getById(Long id) throws ServiceException;

	public Collection<RateClassVo> getPicklist() throws ServiceException;
	
}
